package com.facebook;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.CookieSyncManager;
import com.facebook.android.R.string;
import com.facebook.internal.ServerProtocol;
import com.facebook.internal.Utility;
import com.facebook.model.GraphMultiResult;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphObjectList;
import com.facebook.model.GraphUser;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.Builder;
import com.facebook.widget.WebDialog.OnCompleteListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

class AuthorizationClient
  implements Serializable
{
  private static final String TAG = "Facebook-AuthorizationClient";
  private static final String WEB_VIEW_AUTH_HANDLER_STORE = "com.facebook.AuthorizationClient.WebViewAuthHandler.TOKEN_STORE_KEY";
  private static final String WEB_VIEW_AUTH_HANDLER_TOKEN_KEY = "TOKEN";
  private static final long serialVersionUID = 1L;
  transient BackgroundProcessingListener backgroundProcessingListener;
  transient boolean checkedInternetPermission;
  transient Context context;
  AuthHandler currentHandler;
  List<AuthHandler> handlersToTry;
  transient OnCompletedListener onCompletedListener;
  AuthorizationRequest pendingRequest;
  transient StartActivityDelegate startActivityDelegate;
  
  private void completeWithFailure()
  {
    complete(Result.createErrorResult("Login attempt failed.", null));
  }
  
  private List<AuthHandler> getHandlerTypes(AuthorizationRequest paramAuthorizationRequest)
  {
    ArrayList localArrayList = new ArrayList();
    SessionLoginBehavior localSessionLoginBehavior = paramAuthorizationRequest.getLoginBehavior();
    if (localSessionLoginBehavior.allowsKatanaAuth())
    {
      if (!paramAuthorizationRequest.isLegacy())
      {
        localArrayList.add(new GetTokenAuthHandler());
        localArrayList.add(new KatanaLoginDialogAuthHandler());
      }
      localArrayList.add(new KatanaProxyAuthHandler());
    }
    if (localSessionLoginBehavior.allowsWebViewAuth()) {
      localArrayList.add(new WebViewAuthHandler());
    }
    return localArrayList;
  }
  
  private void notifyBackgroundProcessingStart()
  {
    if (this.backgroundProcessingListener != null) {
      this.backgroundProcessingListener.onBackgroundProcessingStarted();
    }
  }
  
  private void notifyBackgroundProcessingStop()
  {
    if (this.backgroundProcessingListener != null) {
      this.backgroundProcessingListener.onBackgroundProcessingStopped();
    }
  }
  
  private void notifyOnCompleteListener(Result paramResult)
  {
    if (this.onCompletedListener != null) {
      this.onCompletedListener.onCompleted(paramResult);
    }
  }
  
  void authorize(AuthorizationRequest paramAuthorizationRequest)
  {
    if (paramAuthorizationRequest == null) {}
    do
    {
      return;
      if (this.pendingRequest != null) {
        throw new FacebookException("Attempted to authorize while a request is pending.");
      }
    } while ((paramAuthorizationRequest.needsNewTokenValidation()) && (!checkInternetPermission()));
    this.pendingRequest = paramAuthorizationRequest;
    this.handlersToTry = getHandlerTypes(paramAuthorizationRequest);
    tryNextHandler();
  }
  
  void cancelCurrentHandler()
  {
    if (this.currentHandler != null) {
      this.currentHandler.cancel();
    }
  }
  
  boolean checkInternetPermission()
  {
    if (this.checkedInternetPermission) {
      return true;
    }
    if (checkPermission("android.permission.INTERNET") != 0)
    {
      complete(Result.createErrorResult(this.context.getString(R.string.com_facebook_internet_permission_error_title), this.context.getString(R.string.com_facebook_internet_permission_error_message)));
      return false;
    }
    this.checkedInternetPermission = true;
    return true;
  }
  
  int checkPermission(String paramString)
  {
    return this.context.checkCallingOrSelfPermission(paramString);
  }
  
  void complete(Result paramResult)
  {
    this.handlersToTry = null;
    this.currentHandler = null;
    this.pendingRequest = null;
    notifyOnCompleteListener(paramResult);
  }
  
  void completeAndValidate(Result paramResult)
  {
    if ((paramResult.token != null) && (this.pendingRequest.needsNewTokenValidation()))
    {
      validateSameFbidAndFinish(paramResult);
      return;
    }
    complete(paramResult);
  }
  
  void continueAuth()
  {
    if ((this.pendingRequest == null) || (this.currentHandler == null)) {
      throw new FacebookException("Attempted to continue authorization without a pending request.");
    }
    if (this.currentHandler.needsRestart())
    {
      this.currentHandler.cancel();
      tryCurrentHandler();
    }
  }
  
  Request createGetPermissionsRequest(String paramString)
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("fields", "id");
    localBundle.putString("access_token", paramString);
    return new Request(null, "me/permissions", localBundle, HttpMethod.GET, null);
  }
  
  Request createGetProfileIdRequest(String paramString)
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("fields", "id");
    localBundle.putString("access_token", paramString);
    return new Request(null, "me", localBundle, HttpMethod.GET, null);
  }
  
  RequestBatch createReauthValidationBatch(final Result paramResult)
  {
    final ArrayList localArrayList1 = new ArrayList();
    final ArrayList localArrayList2 = new ArrayList();
    String str1 = paramResult.token.getToken();
    Request.Callback local3 = new Request.Callback()
    {
      public void onCompleted(Response paramAnonymousResponse)
      {
        try
        {
          GraphUser localGraphUser = (GraphUser)paramAnonymousResponse.getGraphObjectAs(GraphUser.class);
          if (localGraphUser != null) {
            localArrayList1.add(localGraphUser.getId());
          }
          return;
        }
        catch (Exception localException) {}
      }
    };
    String str2 = this.pendingRequest.getPreviousAccessToken();
    Request localRequest1 = createGetProfileIdRequest(str2);
    localRequest1.setCallback(local3);
    Request localRequest2 = createGetProfileIdRequest(str1);
    localRequest2.setCallback(local3);
    Request localRequest3 = createGetPermissionsRequest(str2);
    localRequest3.setCallback(new Request.Callback()
    {
      public void onCompleted(Response paramAnonymousResponse)
      {
        try
        {
          GraphMultiResult localGraphMultiResult = (GraphMultiResult)paramAnonymousResponse.getGraphObjectAs(GraphMultiResult.class);
          if (localGraphMultiResult != null)
          {
            GraphObjectList localGraphObjectList = localGraphMultiResult.getData();
            if ((localGraphObjectList != null) && (localGraphObjectList.size() == 1))
            {
              GraphObject localGraphObject = (GraphObject)localGraphObjectList.get(0);
              localArrayList2.addAll(localGraphObject.asMap().keySet());
            }
          }
          return;
        }
        catch (Exception localException) {}
      }
    });
    RequestBatch localRequestBatch = new RequestBatch(new Request[] { localRequest1, localRequest2, localRequest3 });
    localRequestBatch.setBatchApplicationId(this.pendingRequest.getApplicationId());
    localRequestBatch.addCallback(new RequestBatch.Callback()
    {
      /* Error */
      public void onBatchCompleted(RequestBatch paramAnonymousRequestBatch)
      {
        // Byte code:
        //   0: aload_0
        //   1: getfield 24	com/facebook/AuthorizationClient$5:val$fbids	Ljava/util/ArrayList;
        //   4: invokevirtual 41	java/util/ArrayList:size	()I
        //   7: iconst_2
        //   8: if_icmpne +86 -> 94
        //   11: aload_0
        //   12: getfield 24	com/facebook/AuthorizationClient$5:val$fbids	Ljava/util/ArrayList;
        //   15: iconst_0
        //   16: invokevirtual 45	java/util/ArrayList:get	(I)Ljava/lang/Object;
        //   19: ifnull +75 -> 94
        //   22: aload_0
        //   23: getfield 24	com/facebook/AuthorizationClient$5:val$fbids	Ljava/util/ArrayList;
        //   26: iconst_1
        //   27: invokevirtual 45	java/util/ArrayList:get	(I)Ljava/lang/Object;
        //   30: ifnull +64 -> 94
        //   33: aload_0
        //   34: getfield 24	com/facebook/AuthorizationClient$5:val$fbids	Ljava/util/ArrayList;
        //   37: iconst_0
        //   38: invokevirtual 45	java/util/ArrayList:get	(I)Ljava/lang/Object;
        //   41: checkcast 47	java/lang/String
        //   44: aload_0
        //   45: getfield 24	com/facebook/AuthorizationClient$5:val$fbids	Ljava/util/ArrayList;
        //   48: iconst_1
        //   49: invokevirtual 45	java/util/ArrayList:get	(I)Ljava/lang/Object;
        //   52: invokevirtual 51	java/lang/String:equals	(Ljava/lang/Object;)Z
        //   55: ifeq +39 -> 94
        //   58: aload_0
        //   59: getfield 26	com/facebook/AuthorizationClient$5:val$pendingResult	Lcom/facebook/AuthorizationClient$Result;
        //   62: getfield 57	com/facebook/AuthorizationClient$Result:token	Lcom/facebook/AccessToken;
        //   65: aload_0
        //   66: getfield 28	com/facebook/AuthorizationClient$5:val$tokenPermissions	Ljava/util/ArrayList;
        //   69: invokestatic 63	com/facebook/AccessToken:createFromTokenWithRefreshedPermissions	(Lcom/facebook/AccessToken;Ljava/util/List;)Lcom/facebook/AccessToken;
        //   72: invokestatic 67	com/facebook/AuthorizationClient$Result:createTokenResult	(Lcom/facebook/AccessToken;)Lcom/facebook/AuthorizationClient$Result;
        //   75: astore 5
        //   77: aload_0
        //   78: getfield 22	com/facebook/AuthorizationClient$5:this$0	Lcom/facebook/AuthorizationClient;
        //   81: aload 5
        //   83: invokevirtual 71	com/facebook/AuthorizationClient:complete	(Lcom/facebook/AuthorizationClient$Result;)V
        //   86: aload_0
        //   87: getfield 22	com/facebook/AuthorizationClient$5:this$0	Lcom/facebook/AuthorizationClient;
        //   90: invokestatic 75	com/facebook/AuthorizationClient:access$000	(Lcom/facebook/AuthorizationClient;)V
        //   93: return
        //   94: ldc 77
        //   96: aconst_null
        //   97: invokestatic 81	com/facebook/AuthorizationClient$Result:createErrorResult	(Ljava/lang/String;Ljava/lang/String;)Lcom/facebook/AuthorizationClient$Result;
        //   100: astore 4
        //   102: aload 4
        //   104: astore 5
        //   106: goto -29 -> 77
        //   109: astore_3
        //   110: aload_0
        //   111: getfield 22	com/facebook/AuthorizationClient$5:this$0	Lcom/facebook/AuthorizationClient;
        //   114: ldc 83
        //   116: aload_3
        //   117: invokevirtual 87	java/lang/Exception:getMessage	()Ljava/lang/String;
        //   120: invokestatic 81	com/facebook/AuthorizationClient$Result:createErrorResult	(Ljava/lang/String;Ljava/lang/String;)Lcom/facebook/AuthorizationClient$Result;
        //   123: invokevirtual 71	com/facebook/AuthorizationClient:complete	(Lcom/facebook/AuthorizationClient$Result;)V
        //   126: aload_0
        //   127: getfield 22	com/facebook/AuthorizationClient$5:this$0	Lcom/facebook/AuthorizationClient;
        //   130: invokestatic 75	com/facebook/AuthorizationClient:access$000	(Lcom/facebook/AuthorizationClient;)V
        //   133: return
        //   134: astore_2
        //   135: aload_0
        //   136: getfield 22	com/facebook/AuthorizationClient$5:this$0	Lcom/facebook/AuthorizationClient;
        //   139: invokestatic 75	com/facebook/AuthorizationClient:access$000	(Lcom/facebook/AuthorizationClient;)V
        //   142: aload_2
        //   143: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	144	0	this	5
        //   0	144	1	paramAnonymousRequestBatch	RequestBatch
        //   134	9	2	localObject1	Object
        //   109	8	3	localException	Exception
        //   100	3	4	localResult	AuthorizationClient.Result
        //   75	30	5	localObject2	Object
        // Exception table:
        //   from	to	target	type
        //   0	77	109	java/lang/Exception
        //   77	86	109	java/lang/Exception
        //   94	102	109	java/lang/Exception
        //   0	77	134	finally
        //   77	86	134	finally
        //   94	102	134	finally
        //   110	126	134	finally
      }
    });
    return localRequestBatch;
  }
  
  BackgroundProcessingListener getBackgroundProcessingListener()
  {
    return this.backgroundProcessingListener;
  }
  
  boolean getInProgress()
  {
    return (this.pendingRequest != null) && (this.currentHandler != null);
  }
  
  OnCompletedListener getOnCompletedListener()
  {
    return this.onCompletedListener;
  }
  
  StartActivityDelegate getStartActivityDelegate()
  {
    if (this.startActivityDelegate != null) {
      return this.startActivityDelegate;
    }
    if (this.pendingRequest != null) {
      new StartActivityDelegate()
      {
        public Activity getActivityContext()
        {
          return AuthorizationClient.this.pendingRequest.getStartActivityDelegate().getActivityContext();
        }
        
        public void startActivityForResult(Intent paramAnonymousIntent, int paramAnonymousInt)
        {
          AuthorizationClient.this.pendingRequest.getStartActivityDelegate().startActivityForResult(paramAnonymousIntent, paramAnonymousInt);
        }
      };
    }
    return null;
  }
  
  boolean onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if (paramInt1 == this.pendingRequest.getRequestCode()) {
      return this.currentHandler.onActivityResult(paramInt1, paramInt2, paramIntent);
    }
    return false;
  }
  
  void setBackgroundProcessingListener(BackgroundProcessingListener paramBackgroundProcessingListener)
  {
    this.backgroundProcessingListener = paramBackgroundProcessingListener;
  }
  
  void setContext(final Activity paramActivity)
  {
    this.context = paramActivity;
    this.startActivityDelegate = new StartActivityDelegate()
    {
      public Activity getActivityContext()
      {
        return paramActivity;
      }
      
      public void startActivityForResult(Intent paramAnonymousIntent, int paramAnonymousInt)
      {
        paramActivity.startActivityForResult(paramAnonymousIntent, paramAnonymousInt);
      }
    };
  }
  
  void setContext(Context paramContext)
  {
    this.context = paramContext;
    this.startActivityDelegate = null;
  }
  
  void setOnCompletedListener(OnCompletedListener paramOnCompletedListener)
  {
    this.onCompletedListener = paramOnCompletedListener;
  }
  
  void startOrContinueAuth(AuthorizationRequest paramAuthorizationRequest)
  {
    if (getInProgress())
    {
      continueAuth();
      return;
    }
    authorize(paramAuthorizationRequest);
  }
  
  boolean tryCurrentHandler()
  {
    if ((this.currentHandler.needsInternetPermission()) && (!checkInternetPermission())) {
      return false;
    }
    return this.currentHandler.tryAuthorize(this.pendingRequest);
  }
  
  void tryNextHandler()
  {
    do
    {
      if ((this.handlersToTry == null) || (this.handlersToTry.isEmpty())) {
        break;
      }
      this.currentHandler = ((AuthHandler)this.handlersToTry.remove(0));
    } while (!tryCurrentHandler());
    while (this.pendingRequest == null) {
      return;
    }
    completeWithFailure();
  }
  
  void validateSameFbidAndFinish(Result paramResult)
  {
    if (paramResult.token == null) {
      throw new FacebookException("Can't validate without a token");
    }
    RequestBatch localRequestBatch = createReauthValidationBatch(paramResult);
    notifyBackgroundProcessingStart();
    localRequestBatch.executeAsync();
  }
  
  static class AuthDialogBuilder
    extends WebDialog.Builder
  {
    private static final String OAUTH_DIALOG = "oauth";
    static final String REDIRECT_URI = "fbconnect://success";
    
    public AuthDialogBuilder(Context paramContext, String paramString, Bundle paramBundle)
    {
      super(paramString, "oauth", paramBundle);
    }
    
    public WebDialog build()
    {
      Bundle localBundle = getParameters();
      localBundle.putString("redirect_uri", "fbconnect://success");
      localBundle.putString("client_id", getApplicationId());
      return new WebDialog(getContext(), "oauth", localBundle, getTheme(), getListener());
    }
  }
  
  abstract class AuthHandler
    implements Serializable
  {
    private static final long serialVersionUID = 1L;
    
    AuthHandler() {}
    
    void cancel() {}
    
    boolean needsInternetPermission()
    {
      return false;
    }
    
    boolean needsRestart()
    {
      return false;
    }
    
    boolean onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
    {
      return false;
    }
    
    abstract boolean tryAuthorize(AuthorizationClient.AuthorizationRequest paramAuthorizationRequest);
  }
  
  static class AuthorizationRequest
    implements Serializable
  {
    private static final long serialVersionUID = 1L;
    private String applicationId;
    private SessionDefaultAudience defaultAudience;
    private boolean isLegacy = false;
    private SessionLoginBehavior loginBehavior;
    private List<String> permissions;
    private String previousAccessToken;
    private int requestCode;
    private final transient AuthorizationClient.StartActivityDelegate startActivityDelegate;
    
    AuthorizationRequest(SessionLoginBehavior paramSessionLoginBehavior, int paramInt, boolean paramBoolean, List<String> paramList, SessionDefaultAudience paramSessionDefaultAudience, String paramString1, String paramString2, AuthorizationClient.StartActivityDelegate paramStartActivityDelegate)
    {
      this.loginBehavior = paramSessionLoginBehavior;
      this.requestCode = paramInt;
      this.isLegacy = paramBoolean;
      this.permissions = paramList;
      this.defaultAudience = paramSessionDefaultAudience;
      this.applicationId = paramString1;
      this.previousAccessToken = paramString2;
      this.startActivityDelegate = paramStartActivityDelegate;
    }
    
    String getApplicationId()
    {
      return this.applicationId;
    }
    
    SessionDefaultAudience getDefaultAudience()
    {
      return this.defaultAudience;
    }
    
    SessionLoginBehavior getLoginBehavior()
    {
      return this.loginBehavior;
    }
    
    List<String> getPermissions()
    {
      return this.permissions;
    }
    
    String getPreviousAccessToken()
    {
      return this.previousAccessToken;
    }
    
    int getRequestCode()
    {
      return this.requestCode;
    }
    
    AuthorizationClient.StartActivityDelegate getStartActivityDelegate()
    {
      return this.startActivityDelegate;
    }
    
    boolean isLegacy()
    {
      return this.isLegacy;
    }
    
    boolean needsNewTokenValidation()
    {
      return (this.previousAccessToken != null) && (!this.isLegacy);
    }
    
    void setIsLegacy(boolean paramBoolean)
    {
      this.isLegacy = paramBoolean;
    }
    
    void setPermissions(List<String> paramList)
    {
      this.permissions = paramList;
    }
  }
  
  static abstract interface BackgroundProcessingListener
  {
    public abstract void onBackgroundProcessingStarted();
    
    public abstract void onBackgroundProcessingStopped();
  }
  
  class GetTokenAuthHandler
    extends AuthorizationClient.AuthHandler
  {
    private static final long serialVersionUID = 1L;
    private transient GetTokenClient getTokenClient;
    
    GetTokenAuthHandler()
    {
      super();
    }
    
    void cancel()
    {
      if (this.getTokenClient != null)
      {
        this.getTokenClient.cancel();
        this.getTokenClient = null;
      }
    }
    
    void getTokenCompleted(AuthorizationClient.AuthorizationRequest paramAuthorizationRequest, Bundle paramBundle)
    {
      this.getTokenClient = null;
      AuthorizationClient.this.notifyBackgroundProcessingStop();
      if (paramBundle != null)
      {
        ArrayList localArrayList1 = paramBundle.getStringArrayList("com.facebook.platform.extra.PERMISSIONS");
        List localList = paramAuthorizationRequest.getPermissions();
        if ((localArrayList1 != null) && ((localList == null) || (localArrayList1.containsAll(localList))))
        {
          AuthorizationClient.Result localResult = AuthorizationClient.Result.createTokenResult(AccessToken.createFromNativeLogin(paramBundle, AccessTokenSource.FACEBOOK_APPLICATION_SERVICE));
          AuthorizationClient.this.completeAndValidate(localResult);
          return;
        }
        ArrayList localArrayList2 = new ArrayList();
        Iterator localIterator = localList.iterator();
        while (localIterator.hasNext())
        {
          String str = (String)localIterator.next();
          if (!localArrayList1.contains(str)) {
            localArrayList2.add(str);
          }
        }
        paramAuthorizationRequest.setPermissions(localArrayList2);
      }
      AuthorizationClient.this.tryNextHandler();
    }
    
    boolean tryAuthorize(final AuthorizationClient.AuthorizationRequest paramAuthorizationRequest)
    {
      this.getTokenClient = new GetTokenClient(AuthorizationClient.this.context, paramAuthorizationRequest.getApplicationId());
      if (!this.getTokenClient.start()) {
        return false;
      }
      AuthorizationClient.this.notifyBackgroundProcessingStart();
      GetTokenClient.CompletedListener local1 = new GetTokenClient.CompletedListener()
      {
        public void completed(Bundle paramAnonymousBundle)
        {
          AuthorizationClient.GetTokenAuthHandler.this.getTokenCompleted(paramAuthorizationRequest, paramAnonymousBundle);
        }
      };
      this.getTokenClient.setCompletedListener(local1);
      return true;
    }
  }
  
  abstract class KatanaAuthHandler
    extends AuthorizationClient.AuthHandler
  {
    private static final long serialVersionUID = 1L;
    
    KatanaAuthHandler()
    {
      super();
    }
    
    protected boolean tryIntent(Intent paramIntent, int paramInt)
    {
      if (paramIntent == null) {
        return false;
      }
      try
      {
        AuthorizationClient.this.getStartActivityDelegate().startActivityForResult(paramIntent, paramInt);
        return true;
      }
      catch (ActivityNotFoundException localActivityNotFoundException) {}
      return false;
    }
  }
  
  class KatanaLoginDialogAuthHandler
    extends AuthorizationClient.KatanaAuthHandler
  {
    private static final long serialVersionUID = 1L;
    
    KatanaLoginDialogAuthHandler()
    {
      super();
    }
    
    private AuthorizationClient.Result handleResultOk(Intent paramIntent)
    {
      Bundle localBundle = paramIntent.getExtras();
      String str = localBundle.getString("com.facebook.platform.status.ERROR_TYPE");
      AuthorizationClient.Result localResult;
      if (str == null) {
        localResult = AuthorizationClient.Result.createTokenResult(AccessToken.createFromNativeLogin(localBundle, AccessTokenSource.FACEBOOK_APPLICATION_NATIVE));
      }
      boolean bool;
      do
      {
        return localResult;
        bool = "ServiceDisabled".equals(str);
        localResult = null;
      } while (bool);
      if ("UserCanceled".equals(str)) {
        return AuthorizationClient.Result.createCancelResult(null);
      }
      return AuthorizationClient.Result.createErrorResult(str, localBundle.getString("error_description"));
    }
    
    boolean onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
    {
      AuthorizationClient.Result localResult;
      if (paramIntent == null)
      {
        localResult = AuthorizationClient.Result.createCancelResult("Operation canceled");
        if (localResult == null) {
          break label84;
        }
        AuthorizationClient.this.completeAndValidate(localResult);
      }
      for (;;)
      {
        return true;
        if (NativeProtocol.isServiceDisabledResult20121101(paramIntent))
        {
          localResult = null;
          break;
        }
        if (paramInt2 == 0)
        {
          localResult = AuthorizationClient.Result.createCancelResult(paramIntent.getStringExtra("com.facebook.platform.status.ERROR_DESCRIPTION"));
          break;
        }
        if (paramInt2 != -1)
        {
          localResult = AuthorizationClient.Result.createErrorResult("Unexpected resultCode from authorization.", null);
          break;
        }
        localResult = handleResultOk(paramIntent);
        break;
        label84:
        AuthorizationClient.this.tryNextHandler();
      }
    }
    
    boolean tryAuthorize(AuthorizationClient.AuthorizationRequest paramAuthorizationRequest)
    {
      return tryIntent(NativeProtocol.createLoginDialog20121101Intent(AuthorizationClient.this.context, paramAuthorizationRequest.getApplicationId(), new ArrayList(paramAuthorizationRequest.getPermissions()), paramAuthorizationRequest.getDefaultAudience().getNativeProtocolAudience()), paramAuthorizationRequest.getRequestCode());
    }
  }
  
  class KatanaProxyAuthHandler
    extends AuthorizationClient.KatanaAuthHandler
  {
    private static final long serialVersionUID = 1L;
    
    KatanaProxyAuthHandler()
    {
      super();
    }
    
    private AuthorizationClient.Result handleResultOk(Intent paramIntent)
    {
      Bundle localBundle = paramIntent.getExtras();
      String str = localBundle.getString("error");
      if (str == null) {
        str = localBundle.getString("error_type");
      }
      AuthorizationClient.Result localResult;
      if (str == null) {
        localResult = AuthorizationClient.Result.createTokenResult(AccessToken.createFromWebBundle(AuthorizationClient.this.pendingRequest.getPermissions(), localBundle, AccessTokenSource.FACEBOOK_APPLICATION_WEB));
      }
      boolean bool;
      do
      {
        return localResult;
        bool = ServerProtocol.errorsProxyAuthDisabled.contains(str);
        localResult = null;
      } while (bool);
      if (ServerProtocol.errorsUserCanceled.contains(str)) {
        return AuthorizationClient.Result.createCancelResult(null);
      }
      return AuthorizationClient.Result.createErrorResult(str, localBundle.getString("error_description"));
    }
    
    boolean onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
    {
      AuthorizationClient.Result localResult;
      if (paramIntent == null)
      {
        localResult = AuthorizationClient.Result.createCancelResult("Operation canceled");
        if (localResult == null) {
          break label71;
        }
        AuthorizationClient.this.completeAndValidate(localResult);
      }
      for (;;)
      {
        return true;
        if (paramInt2 == 0)
        {
          localResult = AuthorizationClient.Result.createCancelResult(paramIntent.getStringExtra("error"));
          break;
        }
        if (paramInt2 != -1)
        {
          localResult = AuthorizationClient.Result.createErrorResult("Unexpected resultCode from authorization.", null);
          break;
        }
        localResult = handleResultOk(paramIntent);
        break;
        label71:
        AuthorizationClient.this.tryNextHandler();
      }
    }
    
    boolean tryAuthorize(AuthorizationClient.AuthorizationRequest paramAuthorizationRequest)
    {
      return tryIntent(NativeProtocol.createProxyAuthIntent(AuthorizationClient.this.context, paramAuthorizationRequest.getApplicationId(), paramAuthorizationRequest.getPermissions()), paramAuthorizationRequest.getRequestCode());
    }
  }
  
  static abstract interface OnCompletedListener
  {
    public abstract void onCompleted(AuthorizationClient.Result paramResult);
  }
  
  static class Result
    implements Serializable
  {
    private static final long serialVersionUID = 1L;
    final Code code;
    final String errorMessage;
    final AccessToken token;
    
    private Result(Code paramCode, AccessToken paramAccessToken, String paramString)
    {
      this.token = paramAccessToken;
      this.errorMessage = paramString;
      this.code = paramCode;
    }
    
    static Result createCancelResult(String paramString)
    {
      return new Result(Code.CANCEL, null, paramString);
    }
    
    static Result createErrorResult(String paramString1, String paramString2)
    {
      String str = paramString1;
      if (paramString2 != null) {
        str = str + ": " + paramString2;
      }
      return new Result(Code.ERROR, null, str);
    }
    
    static Result createTokenResult(AccessToken paramAccessToken)
    {
      return new Result(Code.SUCCESS, paramAccessToken, null);
    }
    
    static enum Code
    {
      static
      {
        CANCEL = new Code("CANCEL", 1);
        ERROR = new Code("ERROR", 2);
        Code[] arrayOfCode = new Code[3];
        arrayOfCode[0] = SUCCESS;
        arrayOfCode[1] = CANCEL;
        arrayOfCode[2] = ERROR;
        $VALUES = arrayOfCode;
      }
      
      private Code() {}
    }
  }
  
  static abstract interface StartActivityDelegate
  {
    public abstract Activity getActivityContext();
    
    public abstract void startActivityForResult(Intent paramIntent, int paramInt);
  }
  
  class WebViewAuthHandler
    extends AuthorizationClient.AuthHandler
  {
    private static final long serialVersionUID = 1L;
    private transient WebDialog loginDialog;
    
    WebViewAuthHandler()
    {
      super();
    }
    
    private String loadCookieToken()
    {
      return AuthorizationClient.this.getStartActivityDelegate().getActivityContext().getSharedPreferences("com.facebook.AuthorizationClient.WebViewAuthHandler.TOKEN_STORE_KEY", 0).getString("TOKEN", "");
    }
    
    private void saveCookieToken(String paramString)
    {
      SharedPreferences.Editor localEditor = AuthorizationClient.this.getStartActivityDelegate().getActivityContext().getSharedPreferences("com.facebook.AuthorizationClient.WebViewAuthHandler.TOKEN_STORE_KEY", 0).edit();
      localEditor.putString("TOKEN", paramString);
      if (!localEditor.commit()) {
        Utility.logd("Facebook-AuthorizationClient", "Could not update saved web view auth handler token.");
      }
    }
    
    void cancel()
    {
      if (this.loginDialog != null)
      {
        this.loginDialog.dismiss();
        this.loginDialog = null;
      }
    }
    
    boolean needsInternetPermission()
    {
      return true;
    }
    
    boolean needsRestart()
    {
      return true;
    }
    
    void onWebDialogComplete(AuthorizationClient.AuthorizationRequest paramAuthorizationRequest, Bundle paramBundle, FacebookException paramFacebookException)
    {
      AuthorizationClient.Result localResult;
      if (paramBundle != null)
      {
        AccessToken localAccessToken = AccessToken.createFromWebBundle(paramAuthorizationRequest.getPermissions(), paramBundle, AccessTokenSource.WEB_VIEW);
        localResult = AuthorizationClient.Result.createTokenResult(localAccessToken);
        CookieSyncManager.createInstance(AuthorizationClient.this.context).sync();
        saveCookieToken(localAccessToken.getToken());
      }
      for (;;)
      {
        AuthorizationClient.this.completeAndValidate(localResult);
        return;
        if ((paramFacebookException instanceof FacebookOperationCanceledException)) {
          localResult = AuthorizationClient.Result.createCancelResult("User canceled log in.");
        } else {
          localResult = AuthorizationClient.Result.createErrorResult(paramFacebookException.getMessage(), null);
        }
      }
    }
    
    boolean tryAuthorize(final AuthorizationClient.AuthorizationRequest paramAuthorizationRequest)
    {
      String str1 = paramAuthorizationRequest.getApplicationId();
      Bundle localBundle = new Bundle();
      if (!Utility.isNullOrEmpty(paramAuthorizationRequest.getPermissions())) {
        localBundle.putString("scope", TextUtils.join(",", paramAuthorizationRequest.getPermissions()));
      }
      String str2 = paramAuthorizationRequest.getPreviousAccessToken();
      if ((!Utility.isNullOrEmpty(str2)) && (str2.equals(loadCookieToken()))) {
        localBundle.putString("access_token", str2);
      }
      for (;;)
      {
        WebDialog.OnCompleteListener local1 = new WebDialog.OnCompleteListener()
        {
          public void onComplete(Bundle paramAnonymousBundle, FacebookException paramAnonymousFacebookException)
          {
            AuthorizationClient.WebViewAuthHandler.this.onWebDialogComplete(paramAuthorizationRequest, paramAnonymousBundle, paramAnonymousFacebookException);
          }
        };
        this.loginDialog = ((WebDialog.Builder)new AuthorizationClient.AuthDialogBuilder(AuthorizationClient.this.getStartActivityDelegate().getActivityContext(), str1, localBundle).setOnCompleteListener(local1)).build();
        this.loginDialog.show();
        return true;
        Utility.clearFacebookCookies(AuthorizationClient.this.context);
      }
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.facebook.AuthorizationClient
 * JD-Core Version:    0.7.0.1
 */