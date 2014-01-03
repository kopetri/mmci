package com.touchtype.keyboard;

import android.graphics.Rect;
import com.touchtype.keyboard.inputeventmodel.KeyPressModel;
import com.touchtype.keyboard.inputeventmodel.KeyPressModelLayout;

public final class SingularKeyPressModelUpdater
  extends KeyPressModelUpdater
{
  private KeyPressModel mKeyPressModel;
  private KeyPressModelLayout mLayout;
  private Rect mPreviousBounds;
  
  SingularKeyPressModelUpdater(KeyPressModelLayout paramKeyPressModelLayout, KeyPressModel paramKeyPressModel)
  {
    this.mLayout = paramKeyPressModelLayout;
    this.mKeyPressModel = paramKeyPressModel;
  }
  
  public void onBoundsUpdated(Rect paramRect)
  {
    if (paramRect.equals(this.mPreviousBounds)) {
      return;
    }
    this.mPreviousBounds = new Rect(paramRect);
    this.mKeyPressModel.updateKeyPressModel(transformed(this.mLayout, paramRect));
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.SingularKeyPressModelUpdater
 * JD-Core Version:    0.7.0.1
 */