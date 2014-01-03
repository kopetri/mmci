package com.touchtype_fluency.service.personalize;

import com.google.common.base.Preconditions;

public class DynamicPersonalizerModel
{
  private static final String KEY_PREFIX = "PERSONALIZER_KEY_";
  private static final String PARAMS_PREFIX = "PARAMS_";
  private static final String SESSION_PREFIX = "SESSION_";
  private final String mAccountName;
  private String mAuthParams;
  private final String mKey;
  private final ServiceConfiguration mService;
  private String mSession;
  
  public DynamicPersonalizerModel(String paramString1, String paramString2, String paramString3)
  {
    Preconditions.checkNotNull(paramString1);
    this.mKey = paramString1;
    this.mAuthParams = paramString2;
    this.mSession = paramString3;
    this.mService = getServiceFromKey();
    Preconditions.checkNotNull(this.mService);
    this.mAccountName = getAccountFromKey();
  }
  
  public static String createAuthParamsKey(String paramString)
  {
    return "PARAMS_" + paramString;
  }
  
  public static String createPersonalizerKey(String paramString)
  {
    return "PERSONALIZER_KEY_" + paramString;
  }
  
  public static String createSessionKey(String paramString)
  {
    return "SESSION_" + paramString;
  }
  
  public static String generateKey(String paramString1, String paramString2)
  {
    if (paramString2 != null) {
      paramString1 = paramString1 + paramString2;
    }
    return paramString1;
  }
  
  private String getAccountFromKey()
  {
    if (this.mKey.equals(this.mService.getName())) {
      return null;
    }
    return this.mKey.substring(this.mService.getName().length());
  }
  
  private ServiceConfiguration getServiceFromKey()
  {
    for (ServiceConfiguration localServiceConfiguration : ) {
      if (this.mKey.startsWith(localServiceConfiguration.getName())) {
        return localServiceConfiguration;
      }
    }
    return null;
  }
  
  public static ServiceConfiguration getServiceFromKey(String paramString)
  {
    for (ServiceConfiguration localServiceConfiguration : ) {
      if (paramString.startsWith(localServiceConfiguration.getName())) {
        return localServiceConfiguration;
      }
    }
    return null;
  }
  
  public static boolean isDynamicPersonalizerKey(String paramString)
  {
    return paramString.startsWith("PERSONALIZER_KEY_");
  }
  
  public String getAccountName()
  {
    return this.mAccountName;
  }
  
  public String getAuthParams()
  {
    return this.mAuthParams;
  }
  
  public String getKey()
  {
    return this.mKey;
  }
  
  public ServiceConfiguration getService()
  {
    return this.mService;
  }
  
  public String getSession()
  {
    return this.mSession;
  }
  
  public void update(String paramString1, String paramString2)
  {
    this.mAuthParams = paramString1;
    this.mSession = paramString2;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.personalize.DynamicPersonalizerModel
 * JD-Core Version:    0.7.0.1
 */