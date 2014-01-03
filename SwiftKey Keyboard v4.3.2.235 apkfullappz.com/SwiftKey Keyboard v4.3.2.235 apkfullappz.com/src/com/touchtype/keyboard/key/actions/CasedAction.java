package com.touchtype.keyboard.key.actions;

import com.google.common.collect.Maps;
import com.touchtype.keyboard.inputeventmodel.InputEventModel;
import com.touchtype.keyboard.inputeventmodel.listeners.OnShiftStateChangedListener;
import com.touchtype.keyboard.service.TouchTypeSoftKeyboard.ShiftState;
import java.util.Collection;
import java.util.Map;

public final class CasedAction
  extends StateSwitchingAction
  implements OnShiftStateChangedListener
{
  private static final String TAG = CasedAction.class.getSimpleName();
  private Map<TouchTypeSoftKeyboard.ShiftState, Action> mActions = Maps.newEnumMap(TouchTypeSoftKeyboard.ShiftState.class);
  private TouchTypeSoftKeyboard.ShiftState mShiftState;
  
  public CasedAction(InputEventModel paramInputEventModel, Action paramAction1, Action paramAction2)
  {
    this.mActions.put(TouchTypeSoftKeyboard.ShiftState.UNSHIFTED, paramAction1);
    this.mActions.put(TouchTypeSoftKeyboard.ShiftState.SHIFTED, paramAction2);
    this.mActions.put(TouchTypeSoftKeyboard.ShiftState.CAPSLOCKED, paramAction2);
    paramInputEventModel.addShiftStateChangedListener(this);
    this.mShiftState = paramInputEventModel.getShiftState();
  }
  
  protected Collection<Action> getAll()
  {
    return this.mActions.values();
  }
  
  protected Action getCurrent()
  {
    return (Action)this.mActions.get(this.mShiftState);
  }
  
  public void handleShiftStateChanged(TouchTypeSoftKeyboard.ShiftState paramShiftState)
  {
    this.mShiftState = paramShiftState;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.actions.CasedAction
 * JD-Core Version:    0.7.0.1
 */