package com.touchtype.keyboard.inputeventmodel.events;

import android.view.inputmethod.CompletionInfo;

public final class CompletionInputEvent
  extends ConnectionInputEvent
{
  private CompletionInfo mCompletion;
  
  public CompletionInputEvent(CompletionInfo paramCompletionInfo)
  {
    this.mCompletion = paramCompletionInfo;
  }
  
  public CompletionInfo getCompletion()
  {
    return this.mCompletion;
  }
  
  public String toString()
  {
    Object[] arrayOfObject = new Object[1];
    if (this.mCompletion != null) {}
    for (Object localObject = this.mCompletion.getText();; localObject = "null")
    {
      arrayOfObject[0] = localObject;
      return String.format("Completion(%s)", arrayOfObject);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.events.CompletionInputEvent
 * JD-Core Version:    0.7.0.1
 */