package com.touchtype.keyboard.inputeventmodel.events;

import com.touchtype.keyboard.key.actions.ActionType;
import java.util.EnumSet;

public final class DeleteInputEvent
  extends ConnectionInputEvent
{
  private final EnumSet<ActionType> mLogTypes;
  private final DeleteType mType;
  
  public DeleteInputEvent(DeleteType paramDeleteType, EnumSet<ActionType> paramEnumSet)
  {
    this.mType = paramDeleteType;
    this.mLogTypes = paramEnumSet;
  }
  
  public EnumSet<ActionType> getLogTypes()
  {
    return this.mLogTypes;
  }
  
  public DeleteType getType()
  {
    return this.mType;
  }
  
  public String toString()
  {
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = this.mType.toString();
    return String.format("Delete(%s)", arrayOfObject);
  }
  
  public static enum DeleteType
  {
    static
    {
      DeleteType[] arrayOfDeleteType = new DeleteType[2];
      arrayOfDeleteType[0] = CHARACTER;
      arrayOfDeleteType[1] = WORD;
      $VALUES = arrayOfDeleteType;
    }
    
    private DeleteType() {}
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.events.DeleteInputEvent
 * JD-Core Version:    0.7.0.1
 */