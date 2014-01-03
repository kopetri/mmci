package com.touchtype.keyboard.inputeventmodel.handlers;

import com.touchtype.keyboard.inputeventmodel.InputConnectionProxy;
import com.touchtype.keyboard.inputeventmodel.events.ConnectionInputEvent;
import com.touchtype.keyboard.inputeventmodel.events.ConnectionlessInputEvent;
import com.touchtype_fluency.Punctuator;

public abstract interface InputEventHandler
{
  public abstract void handleInput(InputConnectionProxy paramInputConnectionProxy, ConnectionInputEvent paramConnectionInputEvent)
    throws UnhandledInputEventException;
  
  public abstract void handleInput(ConnectionlessInputEvent paramConnectionlessInputEvent)
    throws UnhandledInputEventException;
  
  public abstract void setFluencyPunctuator(Punctuator paramPunctuator);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.handlers.InputEventHandler
 * JD-Core Version:    0.7.0.1
 */