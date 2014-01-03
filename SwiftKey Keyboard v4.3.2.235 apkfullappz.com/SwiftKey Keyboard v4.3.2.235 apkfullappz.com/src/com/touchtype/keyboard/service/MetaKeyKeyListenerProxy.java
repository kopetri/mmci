package com.touchtype.keyboard.service;

import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.method.MetaKeyKeyListener;
import android.view.KeyEvent;

public final class MetaKeyKeyListenerProxy
{
  private MetaKeyKeyListener mMetaKeyKeyListener = new MetaKeyKeyListener() {};
  private Editable mMetaState = new SpannableStringBuilder();
  
  public void adjustMetaAfterKeypress()
  {
    MetaKeyKeyListener.adjustMetaAfterKeypress(this.mMetaState);
  }
  
  public int getMetaState()
  {
    return MetaKeyKeyListener.getMetaState(this.mMetaState);
  }
  
  public int getMetaState(int paramInt)
  {
    return MetaKeyKeyListener.getMetaState(this.mMetaState, paramInt);
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    return this.mMetaKeyKeyListener.onKeyDown(null, this.mMetaState, paramInt, paramKeyEvent);
  }
  
  public boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent)
  {
    return this.mMetaKeyKeyListener.onKeyUp(null, this.mMetaState, paramInt, paramKeyEvent);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.service.MetaKeyKeyListenerProxy
 * JD-Core Version:    0.7.0.1
 */