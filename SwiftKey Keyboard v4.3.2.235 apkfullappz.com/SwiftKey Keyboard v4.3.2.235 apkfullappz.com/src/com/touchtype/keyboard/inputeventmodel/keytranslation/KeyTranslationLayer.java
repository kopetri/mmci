package com.touchtype.keyboard.inputeventmodel.keytranslation;

import android.content.Context;
import android.view.KeyEvent;

public abstract interface KeyTranslationLayer
{
  public abstract int getUnicodeChar(TranslationKeyEvent paramTranslationKeyEvent, int paramInt);
  
  public abstract void onCreate(Context paramContext);
  
  public static class TranslationKeyEvent
  {
    private static final String TAG = TranslationKeyEvent.class.getSimpleName();
    private KeyEvent m_keyEvent;
    
    public TranslationKeyEvent()
    {
      this.m_keyEvent = null;
    }
    
    public TranslationKeyEvent(KeyEvent paramKeyEvent)
    {
      this.m_keyEvent = paramKeyEvent;
    }
    
    private int getKeyCodeWith105KeysSupport()
    {
      if ((this.m_keyEvent.getKeyCode() == 73) && (this.m_keyEvent.getScanCode() == 86)) {
        return 500;
      }
      return this.m_keyEvent.getKeyCode();
    }
    
    public int getKeyCode()
    {
      return getKeyCodeWith105KeysSupport();
    }
    
    public int getMetaState()
    {
      return this.m_keyEvent.getMetaState();
    }
    
    public int getUnicodeChar(int paramInt)
    {
      return this.m_keyEvent.getUnicodeChar(paramInt);
    }
    
    public boolean isCapsLockOn()
    {
      return this.m_keyEvent.isCapsLockOn();
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.keytranslation.KeyTranslationLayer
 * JD-Core Version:    0.7.0.1
 */