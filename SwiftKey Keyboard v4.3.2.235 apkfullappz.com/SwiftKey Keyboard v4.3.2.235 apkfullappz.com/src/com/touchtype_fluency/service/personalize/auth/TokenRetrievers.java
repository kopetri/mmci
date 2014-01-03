package com.touchtype_fluency.service.personalize.auth;

import android.app.Activity;

public enum TokenRetrievers
{
  private final String name;
  
  static
  {
    TokenRetrievers[] arrayOfTokenRetrievers = new TokenRetrievers[2];
    arrayOfTokenRetrievers[0] = FACEBOOK;
    arrayOfTokenRetrievers[1] = GMAIL;
    $VALUES = arrayOfTokenRetrievers;
  }
  
  private TokenRetrievers(String paramString)
  {
    this.name = paramString;
  }
  
  public static TokenRetriever getRetrieverByName(String paramString, Activity paramActivity, TokenRetriever.TokenRetrieverListener paramTokenRetrieverListener)
  {
    for (TokenRetrievers localTokenRetrievers : ) {
      if (localTokenRetrievers.getName().equals(paramString)) {
        return localTokenRetrievers.createTokenRetriever(paramActivity, paramTokenRetrieverListener);
      }
    }
    return null;
  }
  
  abstract TokenRetriever createTokenRetriever(Activity paramActivity, TokenRetriever.TokenRetrieverListener paramTokenRetrieverListener);
  
  public String getName()
  {
    return this.name;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.personalize.auth.TokenRetrievers
 * JD-Core Version:    0.7.0.1
 */