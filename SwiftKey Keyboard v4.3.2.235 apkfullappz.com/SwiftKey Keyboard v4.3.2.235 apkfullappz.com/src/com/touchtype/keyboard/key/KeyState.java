package com.touchtype.keyboard.key;

import java.util.EnumSet;

public abstract interface KeyState
{
  public abstract void addListener(StateType paramStateType, KeyStateListener paramKeyStateListener);
  
  public abstract void addListener(EnumSet<StateType> paramEnumSet, KeyStateListener paramKeyStateListener);
  
  public abstract String getInputFilter();
  
  public abstract InterimMenu getInterimState();
  
  public abstract int[] getOptionDrawableState();
  
  public abstract OptionState getOptionState();
  
  public abstract PopupContent getPopupContent();
  
  public abstract int[] getPressedDrawableState();
  
  public abstract boolean getPressedState();
  
  public abstract boolean hasBufferedInput();
  
  public abstract void invalidateKey();
  
  public abstract boolean isFlowing();
  
  public abstract void setInterimState(InterimMenu paramInterimMenu);
  
  public abstract void setPopupContent(PopupContent paramPopupContent);
  
  public abstract void setPressed(boolean paramBoolean);
  
  public static enum InterimMenu
  {
    static
    {
      REMOVE_CANDIDATE = new InterimMenu("REMOVE_CANDIDATE", 1);
      LAYOUT = new InterimMenu("LAYOUT", 2);
      RESIZE = new InterimMenu("RESIZE", 3);
      NONE = new InterimMenu("NONE", 4);
      InterimMenu[] arrayOfInterimMenu = new InterimMenu[5];
      arrayOfInterimMenu[0] = SETTINGS;
      arrayOfInterimMenu[1] = REMOVE_CANDIDATE;
      arrayOfInterimMenu[2] = LAYOUT;
      arrayOfInterimMenu[3] = RESIZE;
      arrayOfInterimMenu[4] = NONE;
      $VALUES = arrayOfInterimMenu;
    }
    
    private InterimMenu() {}
  }
  
  public static enum OptionState
  {
    static
    {
      SMILEY = new OptionState("SMILEY", 8);
      OptionState[] arrayOfOptionState = new OptionState[9];
      arrayOfOptionState[0] = DONE;
      arrayOfOptionState[1] = GO;
      arrayOfOptionState[2] = NEXT;
      arrayOfOptionState[3] = NONE;
      arrayOfOptionState[4] = PREVIOUS;
      arrayOfOptionState[5] = SEARCH;
      arrayOfOptionState[6] = SEND;
      arrayOfOptionState[7] = UNSPECIFIED;
      arrayOfOptionState[8] = SMILEY;
      $VALUES = arrayOfOptionState;
    }
    
    private OptionState() {}
  }
  
  public static enum StateType
  {
    static
    {
      PRESSED = new StateType("PRESSED", 1);
      OPTIONS = new StateType("OPTIONS", 2);
      INPUT_FILTER = new StateType("INPUT_FILTER", 3);
      POPUP = new StateType("POPUP", 4);
      INTERIM = new StateType("INTERIM", 5);
      LAYOUT_MENU = new StateType("LAYOUT_MENU", 6);
      FLOW = new StateType("FLOW", 7);
      BUFFERED_INPUT = new StateType("BUFFERED_INPUT", 8);
      NONE = new StateType("NONE", 9);
      StateType[] arrayOfStateType = new StateType[10];
      arrayOfStateType[0] = REDRAW;
      arrayOfStateType[1] = PRESSED;
      arrayOfStateType[2] = OPTIONS;
      arrayOfStateType[3] = INPUT_FILTER;
      arrayOfStateType[4] = POPUP;
      arrayOfStateType[5] = INTERIM;
      arrayOfStateType[6] = LAYOUT_MENU;
      arrayOfStateType[7] = FLOW;
      arrayOfStateType[8] = BUFFERED_INPUT;
      arrayOfStateType[9] = NONE;
      $VALUES = arrayOfStateType;
    }
    
    private StateType() {}
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.KeyState
 * JD-Core Version:    0.7.0.1
 */