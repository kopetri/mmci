package com.touchtype_fluency.service.personalize;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.touchtype.util.InstallationId;
import com.touchtype_fluency.service.personalize.auth.TokenRetriever;
import com.touchtype_fluency.service.personalize.auth.TokenRetriever.TokenRetrieverListener;
import com.touchtype_fluency.service.personalize.auth.TokenRetrievers;
import com.touchtype_fluency.service.personalize.auth.YahooTokenRefresher;
import com.touchtype_fluency.service.personalize.auth.YahooTokenRefresher.ParamsRefresherCallback;
import java.util.List;
import junit.framework.Assert;

public class Personalizer
{
  private static final String ACCESS_TOKEN = "access_token";
  public static final String ID = "id";
  public static final String INSTALLER_PACKAGE = "packagename";
  public static final String MODELNAME = "modelname";
  public static final String PARAMS = "params";
  public static final String PERSONALIZER_KEY = "personalizerkey";
  public static final String SERVICEID = "serviceid";
  public static final String SERVICENAME = "name";
  public static final String SERVICEPATH = "path";
  private static final String TAG = Personalizer.class.getSimpleName();
  private final PersonalizerAuthenticationCallback callback;
  private final Context context;
  private boolean fromInstaller;
  private final String installationId;
  private boolean mActive = false;
  private DynamicPersonalizerModel mDynamicPersonalizer = null;
  private final ServiceConfiguration service;
  
  public Personalizer(Context paramContext, ServiceConfiguration paramServiceConfiguration, PersonalizerAuthenticationCallback paramPersonalizerAuthenticationCallback)
  {
    this.context = paramContext;
    this.installationId = InstallationId.getId(paramContext);
    this.service = paramServiceConfiguration;
    this.callback = paramPersonalizerAuthenticationCallback;
    Assert.assertNotNull(paramPersonalizerAuthenticationCallback);
    Assert.assertNotNull(this.installationId);
  }
  
  public String getModelFilename()
  {
    return getService().getPath() + ".lm";
  }
  
  public ServiceConfiguration getService()
  {
    return this.service;
  }
  
  public boolean isActive()
  {
    return this.mActive;
  }
  
  public void setActive(boolean paramBoolean)
  {
    this.mActive = paramBoolean;
  }
  
  public void setFromInstaller(boolean paramBoolean)
  {
    this.fromInstaller = paramBoolean;
  }
  
  public void startPersonalization(Activity paramActivity)
  {
    setActive(true);
    if (!this.service.requiresCredentials())
    {
      startPersonalizationRequest("", DynamicPersonalizerModel.generateKey(this.service.getName(), null));
      this.callback.onAuthenticationSuccess(this.service.getName(), null, null, null);
      return;
    }
    List localList = PersonalizationUtils.getServiceRegisteredAccounts(paramActivity, this.service);
    PersonalizerTokenRetrieverListener localPersonalizerTokenRetrieverListener = new PersonalizerTokenRetrieverListener(paramActivity);
    TokenRetriever localTokenRetriever = TokenRetrievers.getRetrieverByName(this.service.getName(), paramActivity, localPersonalizerTokenRetrieverListener);
    if (localTokenRetriever != null)
    {
      this.callback.onAuthenticationStarted(this.service.getName(), null);
      localTokenRetriever.startTokenRetrieving(localList);
      return;
    }
    this.service.startPersonalization(paramActivity);
  }
  
  public void startPersonalization(Activity paramActivity, DynamicPersonalizerModel paramDynamicPersonalizerModel)
  {
    this.mDynamicPersonalizer = paramDynamicPersonalizerModel;
    setActive(true);
    if ((this.mDynamicPersonalizer == null) || (!this.service.requiresCredentials()))
    {
      startPersonalization(paramActivity);
      return;
    }
    this.callback.onAuthenticationStarted(this.service.getName(), this.mDynamicPersonalizer.getAccountName());
    switch (1.$SwitchMap$com$touchtype_fluency$service$personalize$ServiceConfiguration[this.service.ordinal()])
    {
    default: 
      startPersonalizationRequest(this.mDynamicPersonalizer.getAuthParams(), this.mDynamicPersonalizer.getKey());
      return;
    case 1: 
      PersonalizerTokenRetrieverListener localPersonalizerTokenRetrieverListener2 = new PersonalizerTokenRetrieverListener(paramActivity);
      TokenRetrievers.getRetrieverByName(this.service.getName(), paramActivity, localPersonalizerTokenRetrieverListener2).refreshCredentials(this.mDynamicPersonalizer.getAccountName());
      return;
    case 2: 
      if (PersonalizationUtils.getServiceRegisteredAccounts(paramActivity, this.service).contains(this.mDynamicPersonalizer.getAccountName()))
      {
        PersonalizerTokenRetrieverListener localPersonalizerTokenRetrieverListener1 = new PersonalizerTokenRetrieverListener(paramActivity);
        TokenRetrievers.getRetrieverByName(this.service.getName(), paramActivity, localPersonalizerTokenRetrieverListener1).refreshCredentials(this.mDynamicPersonalizer.getAccountName());
        return;
      }
      this.service.startPersonalization(paramActivity);
      return;
    case 3: 
      RefreshParamsCallback localRefreshParamsCallback = new RefreshParamsCallback(paramActivity);
      YahooTokenRefresher localYahooTokenRefresher = new YahooTokenRefresher(this.mDynamicPersonalizer.getAuthParams(), this.mDynamicPersonalizer.getSession(), localRefreshParamsCallback);
      this.callback.onAuthenticationStarted(this.service.getName(), this.mDynamicPersonalizer.getAccountName());
      localYahooTokenRefresher.refreshToken();
      return;
    }
    this.service.startPersonalization(paramActivity);
  }
  
  public void startPersonalizationRequest(String paramString1, String paramString2)
  {
    setActive(false);
    Intent localIntent = new Intent(this.context, PersonalizerService.class);
    localIntent.putExtra("id", this.installationId);
    localIntent.putExtra("path", getService().getPath());
    localIntent.putExtra("name", getService().getName());
    localIntent.putExtra("params", paramString1);
    localIntent.putExtra("modelname", getModelFilename());
    localIntent.putExtra("personalizerkey", paramString2);
    localIntent.putExtra("packagename", this.fromInstaller);
    localIntent.putExtra("serviceid", getService().ordinal());
    this.context.startService(localIntent);
  }
  
  public static abstract interface PersonalizerAuthenticationCallback
  {
    public abstract void onAuthenticationFailed(String paramString);
    
    public abstract void onAuthenticationStarted(String paramString1, String paramString2);
    
    public abstract void onAuthenticationSuccess(String paramString1, String paramString2, String paramString3, String paramString4);
  }
  
  private final class PersonalizerTokenRetrieverListener
    implements TokenRetriever.TokenRetrieverListener
  {
    private final Activity mParentActivity;
    
    public PersonalizerTokenRetrieverListener(Activity paramActivity)
    {
      this.mParentActivity = paramActivity;
    }
    
    public void onAuthenticationRequired()
    {
      Personalizer.this.service.startPersonalization(this.mParentActivity);
    }
    
    public void onCancel()
    {
      Personalizer.this.setActive(false);
    }
    
    public void onError(String paramString)
    {
      Personalizer.this.setActive(false);
      Personalizer.this.callback.onAuthenticationFailed(Personalizer.this.service.getName());
    }
    
    public void onTokenRetrieved(String paramString1, String paramString2)
    {
      PostParams localPostParams = new PostParams();
      localPostParams.add("access_token", paramString1);
      Personalizer.this.startPersonalizationRequest(localPostParams.toString(), DynamicPersonalizerModel.generateKey(Personalizer.this.service.getName(), paramString2));
      Personalizer.this.callback.onAuthenticationSuccess(Personalizer.this.service.getName(), paramString2, localPostParams.toString(), null);
    }
  }
  
  private final class RefreshParamsCallback
    implements YahooTokenRefresher.ParamsRefresherCallback
  {
    private final Activity mParentActivity;
    
    public RefreshParamsCallback(Activity paramActivity)
    {
      this.mParentActivity = paramActivity;
    }
    
    public void onError()
    {
      Personalizer.this.setActive(false);
      Personalizer.this.service.startPersonalization(this.mParentActivity);
    }
    
    public void onParamsRefreshed(String paramString1, String paramString2)
    {
      Personalizer.this.startPersonalizationRequest(paramString1, Personalizer.this.mDynamicPersonalizer.getKey());
      Personalizer.this.callback.onAuthenticationSuccess(Personalizer.this.service.getName(), Personalizer.this.mDynamicPersonalizer.getAccountName(), paramString1, paramString2);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.personalize.Personalizer
 * JD-Core Version:    0.7.0.1
 */