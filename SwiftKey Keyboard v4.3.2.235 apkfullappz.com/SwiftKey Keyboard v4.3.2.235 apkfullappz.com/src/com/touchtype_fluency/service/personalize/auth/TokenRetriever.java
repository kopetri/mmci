package com.touchtype_fluency.service.personalize.auth;

import android.app.Activity;
import com.google.common.base.Preconditions;
import java.util.List;

public abstract class TokenRetriever
{
  protected final Activity mCallerActivity;
  protected final TokenRetrieverListener mListener;
  
  public TokenRetriever(Activity paramActivity, TokenRetrieverListener paramTokenRetrieverListener)
  {
    this.mCallerActivity = paramActivity;
    Preconditions.checkNotNull(paramTokenRetrieverListener);
    this.mListener = paramTokenRetrieverListener;
  }
  
  public abstract void refreshCredentials(String paramString);
  
  public void startTokenRetrieving()
  {
    startTokenRetrieving(null);
  }
  
  public abstract void startTokenRetrieving(List<String> paramList);
  
  public static abstract interface TokenRetrieverListener
  {
    public abstract void onAuthenticationRequired();
    
    public abstract void onCancel();
    
    public abstract void onError(String paramString);
    
    public abstract void onTokenRetrieved(String paramString1, String paramString2);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.personalize.auth.TokenRetriever
 * JD-Core Version:    0.7.0.1
 */