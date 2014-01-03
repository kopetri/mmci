package com.touchtype.keyboard.inputeventmodel.handlers;

import com.touchtype.keyboard.inputeventmodel.events.ConnectionlessInputEvent;

public abstract interface ConnectionlessInputEventHandler
{
  public abstract void handleInput(ConnectionlessInputEvent paramConnectionlessInputEvent)
    throws UnhandledInputEventException;
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.handlers.ConnectionlessInputEventHandler
 * JD-Core Version:    0.7.0.1
 */