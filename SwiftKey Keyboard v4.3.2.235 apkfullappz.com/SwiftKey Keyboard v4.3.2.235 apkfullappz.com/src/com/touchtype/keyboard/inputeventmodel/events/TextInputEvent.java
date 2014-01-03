package com.touchtype.keyboard.inputeventmodel.events;

public class TextInputEvent
  extends ConnectionInputEvent
{
  private CharSequence mInputText;
  
  public TextInputEvent(CharSequence paramCharSequence)
  {
    this.mInputText = paramCharSequence;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof TextInputEvent)) {
      return false;
    }
    TextInputEvent localTextInputEvent = (TextInputEvent)paramObject;
    return this.mInputText.equals(localTextInputEvent.getText());
  }
  
  public CharSequence getText()
  {
    return this.mInputText;
  }
  
  public int hashCode()
  {
    return 527 + this.mInputText.toString().hashCode();
  }
  
  public String toString()
  {
    return "Text(" + this.mInputText + ")";
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.events.TextInputEvent
 * JD-Core Version:    0.7.0.1
 */