package com.touchtype.keyboard.inputeventmodel.handlers;

import com.touchtype.keyboard.inputeventmodel.InputConnectionProxy;
import com.touchtype.keyboard.inputeventmodel.events.ConnectionInputEvent;

public abstract interface ConnectionInputEventHandler
{
  public abstract void handleInput(InputConnectionProxy paramInputConnectionProxy, ConnectionInputEvent paramConnectionInputEvent)
    throws UnhandledInputEventException;
  
  public abstract boolean logKeyStroke();
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.handlers.ConnectionInputEventHandler
 * JD-Core Version:    0.7.0.1
 */