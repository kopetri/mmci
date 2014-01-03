package com.touchtype.keyboard.key;

import com.touchtype.keyboard.BufferedInputListener;
import com.touchtype.keyboard.InputFilterListener;
import com.touchtype.keyboard.OptionStateListener;
import com.touchtype.keyboard.inputeventmodel.InputEventModel;
import com.touchtype.keyboard.inputeventmodel.listeners.OnFlowStateChangedListener;
import com.touchtype.util.WeakHashSet;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class KeyStateImpl
  implements BufferedInputListener, InputFilterListener, OptionStateListener, OnFlowStateChangedListener, KeyState
{
  private static final int[] KEY_STATE_NORMAL = new int[0];
  private static final int[] KEY_STATE_PRESSED = { 16842919 };
  private static final int[] STATE_DONE = { 16843518 };
  private static final int[] STATE_GO = { 16842914 };
  private static final int[] STATE_NEXT = { 16842911 };
  private static final int[] STATE_NONE = { 16842912 };
  private static final int[] STATE_PREVIOUS = { 16842910 };
  private static final int[] STATE_SEARCH = { 16842916 };
  private static final int[] STATE_SEND = { 16842908 };
  private static final int[] STATE_SMILEY = { 16842917 };
  private static final int[] STATE_UNSPECIFIED = { 16842918 };
  private boolean mHasBufferedInput;
  private String mInputFilter;
  private KeyState.InterimMenu mInterim = KeyState.InterimMenu.NONE;
  private boolean mIsFlowing;
  private boolean mIsPressed;
  private KeyState.OptionState mOptionState = KeyState.OptionState.UNSPECIFIED;
  private PopupContent mPopupContent = PopupContent.EMPTY_CONTENT;
  private final Map<KeyState.StateType, WeakHashSet<KeyStateListener>> mRegister = new HashMap();
  
  public KeyStateImpl() {}
  
  public KeyStateImpl(InputEventModel paramInputEventModel)
  {
    paramInputEventModel.addFlowStateChangedListener(this);
  }
  
  private void notifyListeners(KeyState.StateType paramStateType)
  {
    Set localSet = (Set)this.mRegister.get(paramStateType);
    if (localSet == null) {}
    for (;;)
    {
      return;
      Iterator localIterator = localSet.iterator();
      while (localIterator.hasNext()) {
        ((KeyStateListener)localIterator.next()).onKeyStateChanged(this);
      }
    }
  }
  
  private static int[] toDrawableState(KeyState.OptionState paramOptionState)
  {
    switch (1.$SwitchMap$com$touchtype$keyboard$key$KeyState$OptionState[paramOptionState.ordinal()])
    {
    default: 
      return STATE_UNSPECIFIED;
    case 1: 
      return STATE_DONE;
    case 2: 
      return STATE_GO;
    case 3: 
      return STATE_NEXT;
    case 4: 
      return STATE_NONE;
    case 5: 
      return STATE_PREVIOUS;
    case 6: 
      return STATE_SEARCH;
    case 7: 
      return STATE_SEND;
    }
    return STATE_SMILEY;
  }
  
  public void addListener(KeyState.StateType paramStateType, KeyStateListener paramKeyStateListener)
  {
    WeakHashSet localWeakHashSet = (WeakHashSet)this.mRegister.get(paramStateType);
    if (localWeakHashSet == null)
    {
      localWeakHashSet = new WeakHashSet();
      this.mRegister.put(paramStateType, localWeakHashSet);
    }
    localWeakHashSet.add(paramKeyStateListener);
  }
  
  public void addListener(EnumSet<KeyState.StateType> paramEnumSet, KeyStateListener paramKeyStateListener)
  {
    Iterator localIterator = paramEnumSet.iterator();
    while (localIterator.hasNext()) {
      addListener((KeyState.StateType)localIterator.next(), paramKeyStateListener);
    }
  }
  
  public String getInputFilter()
  {
    return this.mInputFilter;
  }
  
  public KeyState.InterimMenu getInterimState()
  {
    return this.mInterim;
  }
  
  public int[] getOptionDrawableState()
  {
    return toDrawableState(this.mOptionState);
  }
  
  public KeyState.OptionState getOptionState()
  {
    return this.mOptionState;
  }
  
  public PopupContent getPopupContent()
  {
    return this.mPopupContent;
  }
  
  public int[] getPressedDrawableState()
  {
    if (this.mIsPressed) {
      return KEY_STATE_PRESSED;
    }
    return KEY_STATE_NORMAL;
  }
  
  public boolean getPressedState()
  {
    return this.mIsPressed;
  }
  
  public void handleFlowStateChanged(boolean paramBoolean)
  {
    this.mIsFlowing = paramBoolean;
    notifyListeners(KeyState.StateType.FLOW);
  }
  
  public boolean hasBufferedInput()
  {
    return this.mHasBufferedInput;
  }
  
  public void invalidateKey()
  {
    notifyListeners(KeyState.StateType.REDRAW);
  }
  
  public boolean isFlowing()
  {
    return this.mIsFlowing;
  }
  
  public void onBufferedInputStateChanged(boolean paramBoolean)
  {
    if (this.mHasBufferedInput != paramBoolean)
    {
      this.mHasBufferedInput = paramBoolean;
      notifyListeners(KeyState.StateType.BUFFERED_INPUT);
    }
  }
  
  public void onInputChanged(String paramString)
  {
    this.mInputFilter = paramString;
    notifyListeners(KeyState.StateType.INPUT_FILTER);
  }
  
  public void onOptionStateChanged(KeyState.OptionState paramOptionState)
  {
    if (this.mOptionState != paramOptionState)
    {
      this.mOptionState = paramOptionState;
      notifyListeners(KeyState.StateType.OPTIONS);
    }
  }
  
  public void setInterimState(KeyState.InterimMenu paramInterimMenu)
  {
    if (paramInterimMenu != this.mInterim)
    {
      this.mInterim = paramInterimMenu;
      notifyListeners(KeyState.StateType.INTERIM);
    }
  }
  
  public void setPopupContent(PopupContent paramPopupContent)
  {
    if (paramPopupContent == null) {
      paramPopupContent = PopupContent.EMPTY_CONTENT;
    }
    this.mPopupContent = paramPopupContent;
    notifyListeners(KeyState.StateType.POPUP);
  }
  
  public void setPressed(boolean paramBoolean)
  {
    if (this.mIsPressed != paramBoolean)
    {
      this.mIsPressed = paramBoolean;
      notifyListeners(KeyState.StateType.PRESSED);
    }
  }
  
  public static final class EmptyState
    implements KeyState
  {
    public void addListener(KeyState.StateType paramStateType, KeyStateListener paramKeyStateListener) {}
    
    public void addListener(EnumSet<KeyState.StateType> paramEnumSet, KeyStateListener paramKeyStateListener) {}
    
    public String getInputFilter()
    {
      return null;
    }
    
    public KeyState.InterimMenu getInterimState()
    {
      return KeyState.InterimMenu.NONE;
    }
    
    public int[] getOptionDrawableState()
    {
      return null;
    }
    
    public KeyState.OptionState getOptionState()
    {
      return null;
    }
    
    public PopupContent getPopupContent()
    {
      return PopupContent.EMPTY_CONTENT;
    }
    
    public int[] getPressedDrawableState()
    {
      return null;
    }
    
    public boolean getPressedState()
    {
      return false;
    }
    
    public boolean hasBufferedInput()
    {
      return false;
    }
    
    public void invalidateKey() {}
    
    public boolean isFlowing()
    {
      return false;
    }
    
    public void setInterimState(KeyState.InterimMenu paramInterimMenu) {}
    
    public void setPopupContent(PopupContent paramPopupContent) {}
    
    public void setPressed(boolean paramBoolean) {}
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.KeyStateImpl
 * JD-Core Version:    0.7.0.1
 */