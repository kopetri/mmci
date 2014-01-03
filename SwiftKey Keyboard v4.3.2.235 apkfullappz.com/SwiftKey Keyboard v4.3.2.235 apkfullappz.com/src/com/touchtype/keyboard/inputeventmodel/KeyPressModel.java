package com.touchtype.keyboard.inputeventmodel;

import java.util.Set;

public abstract interface KeyPressModel
{
  public abstract Integer getModelId();
  
  public abstract void onKeyboardHidden();
  
  public abstract void onKeyboardVisible(Set<String> paramSet1, Set<String> paramSet2);
  
  public abstract void updateKeyPressModel(KeyPressModelLayout paramKeyPressModelLayout);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.KeyPressModel
 * JD-Core Version:    0.7.0.1
 */