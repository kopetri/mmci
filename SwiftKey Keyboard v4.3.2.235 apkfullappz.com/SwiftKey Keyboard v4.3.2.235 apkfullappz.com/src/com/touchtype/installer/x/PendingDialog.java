package com.touchtype.installer.x;

public final class PendingDialog
  implements Runnable
{
  private int mDialogId;
  private XInstaller mParent;
  
  public PendingDialog(int paramInt, XInstaller paramXInstaller)
  {
    this.mDialogId = paramInt;
    this.mParent = paramXInstaller;
  }
  
  public void run()
  {
    if (!this.mParent.mBackgrounded) {
      this.mParent.showDialogFragment(this.mDialogId);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.installer.x.PendingDialog
 * JD-Core Version:    0.7.0.1
 */