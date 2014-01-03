package com.touchtype.keyboard.inputeventmodel.handlers;

import com.touchtype.keyboard.inputeventmodel.InputConnectionProxy;
import com.touchtype.keyboard.inputeventmodel.events.KeyInputEvent;
import com.touchtype_fluency.service.ImmutableExtractedText;

public abstract interface Punctuator
{
  public abstract String getPredictionTrigger();
  
  public abstract String getWordSeparator(ImmutableExtractedText paramImmutableExtractedText);
  
  public abstract void punctuate(KeyInputEvent paramKeyInputEvent, InputConnectionProxy paramInputConnectionProxy, String paramString);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.handlers.Punctuator
 * JD-Core Version:    0.7.0.1
 */