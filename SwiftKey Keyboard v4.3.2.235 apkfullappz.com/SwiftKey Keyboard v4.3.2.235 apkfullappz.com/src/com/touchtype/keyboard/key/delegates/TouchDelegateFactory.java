package com.touchtype.keyboard.key.delegates;

import com.touchtype.keyboard.key.actions.Action;
import com.touchtype.keyboard.key.actions.ActionType;
import com.touchtype.keyboard.key.callbacks.KeyCallbacks;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

final class TouchDelegateFactory
{
  private static TouchSubDelegate createDelegate(KeyCallbacks paramKeyCallbacks, ActionType paramActionType, Action paramAction)
  {
    switch (1.$SwitchMap$com$touchtype$keyboard$key$actions$ActionType[paramActionType.ordinal()])
    {
    default: 
      return null;
    case 1: 
      return new ClickDelegate(paramKeyCallbacks);
    case 2: 
      return new MultiTapDelegate(paramKeyCallbacks, paramAction.getMultitapResetDelay());
    case 3: 
    case 4: 
      return new DragDelegate(paramKeyCallbacks, paramAction.getDragBehaviour());
    case 5: 
      return new FlowDelegate(paramKeyCallbacks, paramAction.getFlowXActivationThreshold(), paramAction.getFlowYActivationThreshold());
    case 6: 
    case 7: 
      return new LongPressDelegate(paramKeyCallbacks, paramAction.getLongPressTimeOut(), paramAction.shouldBlockClicks());
    case 8: 
      return new RepeatsDelegate(paramKeyCallbacks, paramAction.getRepeatBehaviour());
    }
    return new SwipeGesturingDelegate(paramKeyCallbacks, paramAction.getSwipeXActivationThreshold(), paramAction.getSwipeYActivationThreshold(), paramAction.getSwipeMinimumXVelocity(), paramAction.getSwipeMinimumYVelocity());
  }
  
  public static List<TouchSubDelegate> createDelegates(KeyCallbacks paramKeyCallbacks, EnumSet<ActionType> paramEnumSet, Action paramAction)
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = groupActionTypes(paramEnumSet).iterator();
    while (localIterator.hasNext())
    {
      TouchSubDelegate localTouchSubDelegate = createDelegate(paramKeyCallbacks, (ActionType)localIterator.next(), paramAction);
      if (localTouchSubDelegate != null) {
        localArrayList.add(localTouchSubDelegate);
      }
    }
    return localArrayList;
  }
  
  private static EnumSet<ActionType> groupActionTypes(EnumSet<ActionType> paramEnumSet)
  {
    EnumSet localEnumSet = EnumSet.noneOf(ActionType.class);
    Iterator localIterator = paramEnumSet.iterator();
    while (localIterator.hasNext())
    {
      ActionType localActionType = (ActionType)localIterator.next();
      switch (1.$SwitchMap$com$touchtype$keyboard$key$actions$ActionType[localActionType.ordinal()])
      {
      case 5: 
      case 8: 
      default: 
        localEnumSet.add(localActionType);
        break;
      case 1: 
        if (localEnumSet.contains(ActionType.MULTITAP)) {}
        break;
      case 2: 
        localEnumSet.remove(ActionType.CLICK);
        localEnumSet.add(localActionType);
        break;
      case 6: 
      case 7: 
        if (Collections.disjoint(localEnumSet, EnumSet.of(ActionType.LONGCLICK, ActionType.LONGPRESS))) {
          localEnumSet.add(localActionType);
        }
        break;
      case 9: 
      case 10: 
      case 11: 
      case 12: 
        if (Collections.disjoint(localEnumSet, ActionType.SWIPES)) {
          localEnumSet.add(localActionType);
        }
        break;
      case 3: 
      case 4: 
        if (Collections.disjoint(localEnumSet, EnumSet.of(ActionType.DRAG, ActionType.DRAG_CLICK))) {
          localEnumSet.add(localActionType);
        }
        break;
      }
    }
    return localEnumSet;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.delegates.TouchDelegateFactory
 * JD-Core Version:    0.7.0.1
 */