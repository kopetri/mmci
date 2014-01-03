package com.touchtype.installer.x;

import android.content.res.Resources;
import android.os.Handler;
import com.touchtype.TouchTypeUtilities;

public final class IMEDetector
  implements Runnable
{
  private XInstaller mInstaller;
  
  public IMEDetector(XInstaller paramXInstaller)
  {
    this.mInstaller = paramXInstaller;
  }
  
  public void run()
  {
    if ((this.mInstaller.mHandler != null) && (this.mInstaller.mTimedTask != null)) {
      this.mInstaller.mHandler.removeCallbacks(this.mInstaller.mTimedTask);
    }
    if (TouchTypeUtilities.checkIMEStatus(this.mInstaller))
    {
      this.mInstaller.nextFrame();
      if (!this.mInstaller.getResources().getBoolean(2131492889))
      {
        this.mInstaller.nextFrame();
        if (!this.mInstaller.getResources().getBoolean(2131492935)) {
          this.mInstaller.nextFrame();
        }
      }
      return;
    }
    if (this.mInstaller.mHandler == null) {
      this.mInstaller.mHandler = new Handler();
    }
    this.mInstaller.mHandler.postDelayed(this, 200L);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.installer.x.IMEDetector
 * JD-Core Version:    0.7.0.1
 */