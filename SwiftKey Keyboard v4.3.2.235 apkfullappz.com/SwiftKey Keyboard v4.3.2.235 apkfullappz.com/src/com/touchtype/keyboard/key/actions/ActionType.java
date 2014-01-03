package com.touchtype.keyboard.key.actions;

import java.util.EnumSet;

public enum ActionType
{
  public static final EnumSet<ActionType> SWIPES = EnumSet.of(SWIPE_DOWN, SWIPE_LEFT, SWIPE_RIGHT, SWIPE_UP);
  
  static
  {
    SLIDE_IN = new ActionType("SLIDE_IN", 2);
    SLIDE_OUT = new ActionType("SLIDE_OUT", 3);
    DRAG = new ActionType("DRAG", 4);
    DRAG_CLICK = new ActionType("DRAG_CLICK", 5);
    CANCEL = new ActionType("CANCEL", 6);
    CLICK = new ActionType("CLICK", 7);
    MULTITAP = new ActionType("MULTITAP", 8);
    LONGPRESS = new ActionType("LONGPRESS", 9);
    LONGCLICK = new ActionType("LONGCLICK", 10);
    REPEAT = new ActionType("REPEAT", 11);
    SWIPE_RIGHT = new ActionType("SWIPE_RIGHT", 12);
    SWIPE_LEFT = new ActionType("SWIPE_LEFT", 13);
    SWIPE_DOWN = new ActionType("SWIPE_DOWN", 14);
    SWIPE_UP = new ActionType("SWIPE_UP", 15);
    FLOW = new ActionType("FLOW", 16);
    ActionType[] arrayOfActionType = new ActionType[17];
    arrayOfActionType[0] = DOWN;
    arrayOfActionType[1] = UP;
    arrayOfActionType[2] = SLIDE_IN;
    arrayOfActionType[3] = SLIDE_OUT;
    arrayOfActionType[4] = DRAG;
    arrayOfActionType[5] = DRAG_CLICK;
    arrayOfActionType[6] = CANCEL;
    arrayOfActionType[7] = CLICK;
    arrayOfActionType[8] = MULTITAP;
    arrayOfActionType[9] = LONGPRESS;
    arrayOfActionType[10] = LONGCLICK;
    arrayOfActionType[11] = REPEAT;
    arrayOfActionType[12] = SWIPE_RIGHT;
    arrayOfActionType[13] = SWIPE_LEFT;
    arrayOfActionType[14] = SWIPE_DOWN;
    arrayOfActionType[15] = SWIPE_UP;
    arrayOfActionType[16] = FLOW;
    $VALUES = arrayOfActionType;
  }
  
  private ActionType() {}
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.actions.ActionType
 * JD-Core Version:    0.7.0.1
 */