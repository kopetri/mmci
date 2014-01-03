package com.touchtype.cloud.ui;

public final class CloudSetupFragmentCallbacks
{
  public static abstract interface CloudSetupBasicDialogCallback {}
  
  public static abstract interface CloudSetupChooseGoogleAccountCallback
  {
    public abstract void onChoseAccount(String paramString);
  }
  
  public static abstract interface CloudSetupSignedInCallback
  {
    public abstract void onPressedNo();
    
    public abstract void onPressedOk();
    
    public abstract void onPressedYes();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.cloud.ui.CloudSetupFragmentCallbacks
 * JD-Core Version:    0.7.0.1
 */