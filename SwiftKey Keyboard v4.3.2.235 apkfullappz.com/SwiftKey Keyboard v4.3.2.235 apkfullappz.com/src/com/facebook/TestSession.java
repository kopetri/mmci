package com.facebook;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.facebook.internal.Validate;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphObjectList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class TestSession
  extends Session
{
  private static final String LOG_TAG = "FacebookSDK.TestSession";
  private static Map<String, TestAccount> appTestAccounts;
  private static final long serialVersionUID = 1L;
  private static String testApplicationId;
  private static String testApplicationSecret;
  private final Mode mode;
  private final List<String> requestedPermissions;
  private final String sessionUniqueUserTag;
  private String testAccountId;
  private boolean wasAskedToExtendAccessToken;
  
  static
  {
    if (!TestSession.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  TestSession(Activity paramActivity, List<String> paramList, TokenCachingStrategy paramTokenCachingStrategy, String paramString, Mode paramMode)
  {
    super(paramActivity, testApplicationId, paramTokenCachingStrategy);
    Validate.notNull(paramList, "permissions");
    Validate.notNullOrEmpty(testApplicationId, "testApplicationId");
    Validate.notNullOrEmpty(testApplicationSecret, "testApplicationSecret");
    this.sessionUniqueUserTag = paramString;
    this.mode = paramMode;
    this.requestedPermissions = paramList;
  }
  
  public static TestSession createSessionWithPrivateUser(Activity paramActivity, List<String> paramList)
  {
    return createTestSession(paramActivity, paramList, Mode.PRIVATE, null);
  }
  
  public static TestSession createSessionWithSharedUser(Activity paramActivity, List<String> paramList)
  {
    return createSessionWithSharedUser(paramActivity, paramList, null);
  }
  
  public static TestSession createSessionWithSharedUser(Activity paramActivity, List<String> paramList, String paramString)
  {
    return createTestSession(paramActivity, paramList, Mode.SHARED, paramString);
  }
  
  private TestAccount createTestAccountAndFinishAuth()
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("installed", "true");
    localBundle.putString("permissions", getPermissionsString());
    localBundle.putString("access_token", getAppAccessToken());
    if (this.mode == Mode.SHARED)
    {
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = getSharedTestAccountIdentifier();
      localBundle.putString("name", String.format("Shared %s Testuser", arrayOfObject2));
    }
    Object[] arrayOfObject1 = new Object[1];
    arrayOfObject1[0] = testApplicationId;
    Response localResponse = new Request(null, String.format("%s/accounts/test-users", arrayOfObject1), localBundle, HttpMethod.POST).executeAndWait();
    FacebookRequestError localFacebookRequestError = localResponse.getError();
    TestAccount localTestAccount = (TestAccount)localResponse.getGraphObjectAs(TestAccount.class);
    if (localFacebookRequestError != null)
    {
      finishAuthOrReauth(null, localFacebookRequestError.getException());
      return null;
    }
    assert (localTestAccount != null);
    if (this.mode == Mode.SHARED)
    {
      localTestAccount.setName(localBundle.getString("name"));
      storeTestAccount(localTestAccount);
    }
    finishAuthWithTestAccount(localTestAccount);
    return localTestAccount;
  }
  
  /* Error */
  private static TestSession createTestSession(Activity paramActivity, List<String> paramList, Mode paramMode, String paramString)
  {
    // Byte code:
    //   0: ldc 2
    //   2: monitorenter
    //   3: getstatic 42	com/facebook/TestSession:testApplicationId	Ljava/lang/String;
    //   6: invokestatic 185	com/facebook/internal/Utility:isNullOrEmpty	(Ljava/lang/String;)Z
    //   9: ifne +12 -> 21
    //   12: getstatic 60	com/facebook/TestSession:testApplicationSecret	Ljava/lang/String;
    //   15: invokestatic 185	com/facebook/internal/Utility:isNullOrEmpty	(Ljava/lang/String;)Z
    //   18: ifeq +21 -> 39
    //   21: new 187	com/facebook/FacebookException
    //   24: dup
    //   25: ldc 189
    //   27: invokespecial 191	com/facebook/FacebookException:<init>	(Ljava/lang/String;)V
    //   30: athrow
    //   31: astore 4
    //   33: ldc 2
    //   35: monitorexit
    //   36: aload 4
    //   38: athrow
    //   39: aload_1
    //   40: invokestatic 194	com/facebook/internal/Utility:isNullOrEmpty	(Ljava/util/Collection;)Z
    //   43: ifeq +21 -> 64
    //   46: iconst_2
    //   47: anewarray 118	java/lang/String
    //   50: dup
    //   51: iconst_0
    //   52: ldc 196
    //   54: aastore
    //   55: dup
    //   56: iconst_1
    //   57: ldc 198
    //   59: aastore
    //   60: invokestatic 204	java/util/Arrays:asList	([Ljava/lang/Object;)Ljava/util/List;
    //   63: astore_1
    //   64: new 206	com/facebook/TestSession$TestTokenCachingStrategy
    //   67: dup
    //   68: aconst_null
    //   69: invokespecial 209	com/facebook/TestSession$TestTokenCachingStrategy:<init>	(Lcom/facebook/TestSession$1;)V
    //   72: astore 5
    //   74: new 2	com/facebook/TestSession
    //   77: dup
    //   78: aload_0
    //   79: aload_1
    //   80: aload 5
    //   82: aload_3
    //   83: aload_2
    //   84: invokespecial 211	com/facebook/TestSession:<init>	(Landroid/app/Activity;Ljava/util/List;Lcom/facebook/TokenCachingStrategy;Ljava/lang/String;Lcom/facebook/TestSession$Mode;)V
    //   87: astore 6
    //   89: ldc 2
    //   91: monitorexit
    //   92: aload 6
    //   94: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	95	0	paramActivity	Activity
    //   0	95	1	paramList	List<String>
    //   0	95	2	paramMode	Mode
    //   0	95	3	paramString	String
    //   31	6	4	localObject	Object
    //   72	9	5	localTestTokenCachingStrategy	TestTokenCachingStrategy
    //   87	6	6	localTestSession	TestSession
    // Exception table:
    //   from	to	target	type
    //   3	21	31	finally
    //   21	31	31	finally
    //   39	64	31	finally
    //   64	89	31	finally
  }
  
  private void deleteTestAccount(String paramString1, String paramString2)
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("access_token", paramString2);
    Response localResponse = new Request(null, paramString1, localBundle, HttpMethod.DELETE).executeAndWait();
    FacebookRequestError localFacebookRequestError = localResponse.getError();
    GraphObject localGraphObject = localResponse.getGraphObject();
    if (localFacebookRequestError != null)
    {
      arrayOfObject = new Object[2];
      arrayOfObject[0] = paramString1;
      arrayOfObject[1] = localFacebookRequestError.getException().toString();
      Log.w("FacebookSDK.TestSession", String.format("Could not delete test account %s: %s", arrayOfObject));
    }
    while (localGraphObject.getProperty("FACEBOOK_NON_JSON_RESULT") != Boolean.valueOf(false))
    {
      Object[] arrayOfObject;
      return;
    }
    Log.w("FacebookSDK.TestSession", String.format("Could not delete test account %s: unknown reason", new Object[] { paramString1 }));
  }
  
  private void findOrCreateSharedTestAccount()
  {
    TestAccount localTestAccount = findTestAccountMatchingIdentifier(getSharedTestAccountIdentifier());
    if (localTestAccount != null)
    {
      finishAuthWithTestAccount(localTestAccount);
      return;
    }
    createTestAccountAndFinishAuth();
  }
  
  /* Error */
  private static TestAccount findTestAccountMatchingIdentifier(String paramString)
  {
    // Byte code:
    //   0: ldc 2
    //   2: monitorenter
    //   3: invokestatic 256	com/facebook/TestSession:retrieveTestAccountsForAppIfNeeded	()V
    //   6: getstatic 258	com/facebook/TestSession:appTestAccounts	Ljava/util/Map;
    //   9: invokeinterface 264 1 0
    //   14: invokeinterface 270 1 0
    //   19: astore_2
    //   20: aload_2
    //   21: invokeinterface 275 1 0
    //   26: ifeq +35 -> 61
    //   29: aload_2
    //   30: invokeinterface 279 1 0
    //   35: checkcast 147	com/facebook/TestSession$TestAccount
    //   38: astore_3
    //   39: aload_3
    //   40: invokeinterface 282 1 0
    //   45: aload_0
    //   46: invokevirtual 286	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   49: istore 4
    //   51: iload 4
    //   53: ifeq -33 -> 20
    //   56: ldc 2
    //   58: monitorexit
    //   59: aload_3
    //   60: areturn
    //   61: aconst_null
    //   62: astore_3
    //   63: goto -7 -> 56
    //   66: astore_1
    //   67: ldc 2
    //   69: monitorexit
    //   70: aload_1
    //   71: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	72	0	paramString	String
    //   66	5	1	localObject	Object
    //   19	11	2	localIterator	Iterator
    //   38	25	3	localTestAccount	TestAccount
    //   49	3	4	bool	boolean
    // Exception table:
    //   from	to	target	type
    //   3	20	66	finally
    //   20	51	66	finally
  }
  
  private void finishAuthWithTestAccount(TestAccount paramTestAccount)
  {
    this.testAccountId = paramTestAccount.getId();
    finishAuthOrReauth(AccessToken.createFromString(paramTestAccount.getAccessToken(), this.requestedPermissions, AccessTokenSource.TEST_USER), null);
  }
  
  static final String getAppAccessToken()
  {
    return testApplicationId + "|" + testApplicationSecret;
  }
  
  private String getPermissionsString()
  {
    return TextUtils.join(",", this.requestedPermissions);
  }
  
  private String getSharedTestAccountIdentifier()
  {
    long l1 = 0xFFFFFFFF & getPermissionsString().hashCode();
    if (this.sessionUniqueUserTag != null) {}
    for (long l2 = 0xFFFFFFFF & this.sessionUniqueUserTag.hashCode();; l2 = 0L) {
      return validNameStringFromInteger(l1 ^ l2);
    }
  }
  
  public static String getTestApplicationId()
  {
    try
    {
      String str = testApplicationId;
      return str;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public static String getTestApplicationSecret()
  {
    try
    {
      String str = testApplicationSecret;
      return str;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  private static void populateTestAccounts(Collection<TestAccount> paramCollection, Collection<UserAccount> paramCollection1)
  {
    try
    {
      Iterator localIterator1 = paramCollection.iterator();
      while (localIterator1.hasNext()) {
        storeTestAccount((TestAccount)localIterator1.next());
      }
      localIterator2 = paramCollection1.iterator();
    }
    finally {}
    Iterator localIterator2;
    while (localIterator2.hasNext())
    {
      UserAccount localUserAccount = (UserAccount)localIterator2.next();
      TestAccount localTestAccount = (TestAccount)appTestAccounts.get(localUserAccount.getUid());
      if (localTestAccount != null) {
        localTestAccount.setName(localUserAccount.getName());
      }
    }
  }
  
  private static void retrieveTestAccountsForAppIfNeeded()
  {
    for (;;)
    {
      try
      {
        Map localMap = appTestAccounts;
        if (localMap != null) {
          return;
        }
        appTestAccounts = new HashMap();
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = testApplicationId;
        String str = String.format("SELECT id,access_token FROM test_account WHERE app_id = %s", arrayOfObject);
        Bundle localBundle = new Bundle();
        JSONObject localJSONObject;
        Response localResponse;
        localGraphObjectList = ((FqlResponse)localResponse.getGraphObjectAs(FqlResponse.class)).getData();
      }
      finally
      {
        try
        {
          localJSONObject = new JSONObject();
          localJSONObject.put("test_accounts", str);
          localJSONObject.put("users", "SELECT uid,name FROM user WHERE uid IN (SELECT id FROM #test_accounts)");
          localBundle.putString("q", localJSONObject.toString());
          localBundle.putString("access_token", getAppAccessToken());
          localResponse = new Request(null, "fql", localBundle, null).executeAndWait();
          if (localResponse.getError() == null) {
            break label161;
          }
          throw localResponse.getError().getException();
        }
        catch (JSONException localJSONException)
        {
          throw new FacebookException(localJSONException);
        }
        localObject = finally;
      }
      label161:
      GraphObjectList localGraphObjectList;
      if ((localGraphObjectList == null) || (localGraphObjectList.size() != 2)) {
        throw new FacebookException("Unexpected number of results from FQL query");
      }
      populateTestAccounts(((FqlResult)localGraphObjectList.get(0)).getFqlResultSet().castToListOf(TestAccount.class), ((FqlResult)localGraphObjectList.get(1)).getFqlResultSet().castToListOf(UserAccount.class));
    }
  }
  
  public static void setTestApplicationId(String paramString)
  {
    try
    {
      if ((testApplicationId != null) && (!testApplicationId.equals(paramString))) {
        throw new FacebookException("Can't have more than one test application ID");
      }
    }
    finally {}
    testApplicationId = paramString;
  }
  
  public static void setTestApplicationSecret(String paramString)
  {
    try
    {
      if ((testApplicationSecret != null) && (!testApplicationSecret.equals(paramString))) {
        throw new FacebookException("Can't have more than one test application secret");
      }
    }
    finally {}
    testApplicationSecret = paramString;
  }
  
  private static void storeTestAccount(TestAccount paramTestAccount)
  {
    try
    {
      appTestAccounts.put(paramTestAccount.getId(), paramTestAccount);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  private String validNameStringFromInteger(long paramLong)
  {
    String str = Long.toString(paramLong);
    StringBuilder localStringBuilder = new StringBuilder("Perm");
    int i = 0;
    for (int m : str.toCharArray())
    {
      if (m == i) {
        m = (char)(m + 10);
      }
      localStringBuilder.append((char)(-48 + (m + 97)));
      i = m;
    }
    return localStringBuilder.toString();
  }
  
  void authorize(Session.AuthorizationRequest paramAuthorizationRequest)
  {
    if (this.mode == Mode.PRIVATE)
    {
      createTestAccountAndFinishAuth();
      return;
    }
    findOrCreateSharedTestAccount();
  }
  
  void extendAccessToken()
  {
    this.wasAskedToExtendAccessToken = true;
    super.extendAccessToken();
  }
  
  void fakeTokenRefreshAttempt()
  {
    setCurrentTokenRefreshRequest(new Session.TokenRefreshRequest(this));
  }
  
  void forceExtendAccessToken(boolean paramBoolean)
  {
    AccessToken localAccessToken = getTokenInfo();
    setTokenInfo(new AccessToken(localAccessToken.getToken(), new Date(), localAccessToken.getPermissions(), AccessTokenSource.TEST_USER, new Date(0L)));
    setLastAttemptedTokenExtendDate(new Date(0L));
  }
  
  public final String getTestUserId()
  {
    return this.testAccountId;
  }
  
  boolean getWasAskedToExtendAccessToken()
  {
    return this.wasAskedToExtendAccessToken;
  }
  
  void postStateChange(SessionState paramSessionState1, SessionState paramSessionState2, Exception paramException)
  {
    String str = this.testAccountId;
    super.postStateChange(paramSessionState1, paramSessionState2, paramException);
    if ((paramSessionState2.isClosed()) && (str != null) && (this.mode == Mode.PRIVATE)) {
      deleteTestAccount(str, getAppAccessToken());
    }
  }
  
  boolean shouldExtendAccessToken()
  {
    boolean bool = super.shouldExtendAccessToken();
    this.wasAskedToExtendAccessToken = false;
    return bool;
  }
  
  public final String toString()
  {
    String str = super.toString();
    return "{TestSession testUserId:" + this.testAccountId + " " + str + "}";
  }
  
  private static abstract interface FqlResponse
    extends GraphObject
  {
    public abstract GraphObjectList<TestSession.FqlResult> getData();
  }
  
  private static abstract interface FqlResult
    extends GraphObject
  {
    public abstract GraphObjectList<GraphObject> getFqlResultSet();
  }
  
  private static enum Mode
  {
    static
    {
      Mode[] arrayOfMode = new Mode[2];
      arrayOfMode[0] = PRIVATE;
      arrayOfMode[1] = SHARED;
      $VALUES = arrayOfMode;
    }
    
    private Mode() {}
  }
  
  private static abstract interface TestAccount
    extends GraphObject
  {
    public abstract String getAccessToken();
    
    public abstract String getId();
    
    public abstract String getName();
    
    public abstract void setName(String paramString);
  }
  
  private static final class TestTokenCachingStrategy
    extends TokenCachingStrategy
  {
    private Bundle bundle;
    
    public void clear()
    {
      this.bundle = null;
    }
    
    public Bundle load()
    {
      return this.bundle;
    }
    
    public void save(Bundle paramBundle)
    {
      this.bundle = paramBundle;
    }
  }
  
  private static abstract interface UserAccount
    extends GraphObject
  {
    public abstract String getName();
    
    public abstract String getUid();
    
    public abstract void setName(String paramString);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.facebook.TestSession
 * JD-Core Version:    0.7.0.1
 */