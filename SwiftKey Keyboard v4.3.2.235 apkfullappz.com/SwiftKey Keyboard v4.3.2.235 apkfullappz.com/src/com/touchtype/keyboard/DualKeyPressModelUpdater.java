package com.touchtype.keyboard;

import android.graphics.Rect;
import com.touchtype.keyboard.inputeventmodel.KeyPressModel;
import com.touchtype.keyboard.inputeventmodel.KeyPressModelLayout;

public final class DualKeyPressModelUpdater
  extends KeyPressModelUpdater
{
  private KeyPressModel mKeyPressModel;
  private KeyPressModelLayout mLayoutLeft;
  private KeyPressModelLayout mLayoutRight;
  private Rect mPreviousBoundsLeft;
  private Rect mPreviousBoundsRight;
  
  DualKeyPressModelUpdater(KeyPressModelLayout paramKeyPressModelLayout1, KeyPressModelLayout paramKeyPressModelLayout2, KeyPressModel paramKeyPressModel)
  {
    this.mLayoutLeft = paramKeyPressModelLayout1;
    this.mLayoutRight = paramKeyPressModelLayout2;
    this.mKeyPressModel = paramKeyPressModel;
  }
  
  public void onBoundsUpdated(Rect paramRect1, Rect paramRect2)
  {
    if ((paramRect1.equals(this.mPreviousBoundsLeft)) && (paramRect2.equals(this.mPreviousBoundsRight))) {
      return;
    }
    this.mPreviousBoundsLeft = paramRect1;
    this.mPreviousBoundsRight = paramRect2;
    KeyPressModelLayout localKeyPressModelLayout1 = transformed(this.mLayoutLeft, paramRect1);
    KeyPressModelLayout localKeyPressModelLayout2 = transformed(this.mLayoutRight, paramRect2);
    this.mKeyPressModel.updateKeyPressModel(localKeyPressModelLayout1.mergedWith(localKeyPressModelLayout2, paramRect2.right - paramRect1.left, paramRect1.bottom - paramRect1.top));
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.DualKeyPressModelUpdater
 * JD-Core Version:    0.7.0.1
 */