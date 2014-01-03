package com.touchtype.keyboard.inputeventmodel.events;

public final class StateChangeInputEvent
  extends ConnectionInputEvent
{
  private Modifier mModifierType;
  private int mValue;
  
  public Modifier getModifierType()
  {
    return this.mModifierType;
  }
  
  public String toString()
  {
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = Integer.valueOf(this.mValue);
    arrayOfObject[1] = this.mModifierType.toString();
    return String.format("StateChange(%d, %s)", arrayOfObject);
  }
  
  public static enum Modifier
  {
    static
    {
      Modifier[] arrayOfModifier = new Modifier[1];
      arrayOfModifier[0] = SHIFT;
      $VALUES = arrayOfModifier;
    }
    
    private Modifier() {}
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.events.StateChangeInputEvent
 * JD-Core Version:    0.7.0.1
 */