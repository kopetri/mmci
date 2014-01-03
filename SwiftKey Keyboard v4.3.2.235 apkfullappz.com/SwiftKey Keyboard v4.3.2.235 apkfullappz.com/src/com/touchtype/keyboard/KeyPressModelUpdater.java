package com.touchtype.keyboard;

import android.graphics.Matrix;
import android.graphics.Rect;
import com.touchtype.keyboard.inputeventmodel.KeyPressModelLayout;

public abstract class KeyPressModelUpdater
{
  protected KeyPressModelLayout transformed(KeyPressModelLayout paramKeyPressModelLayout, Rect paramRect)
  {
    Matrix localMatrix = new Matrix();
    localMatrix.postScale(paramRect.width(), paramRect.height());
    localMatrix.postTranslate(paramRect.left, paramRect.top);
    return paramKeyPressModelLayout.transformed(localMatrix, paramRect.width(), paramRect.height());
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.KeyPressModelUpdater
 * JD-Core Version:    0.7.0.1
 */