package com.touchtype.keyboard.key.actions;

import com.google.common.collect.Lists;
import com.touchtype.keyboard.key.callbacks.DragBehaviour;
import com.touchtype.keyboard.key.callbacks.DragEvent;
import com.touchtype.keyboard.view.touch.FlowEvent;
import com.touchtype.keyboard.view.touch.TouchEvent.Touch;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

public abstract class StateSwitchingAction
  implements Action
{
  public void cancel()
  {
    getCurrent().cancel();
  }
  
  public void click(TouchEvent.Touch paramTouch)
  {
    getCurrent().click(paramTouch);
  }
  
  public void down(TouchEvent.Touch paramTouch)
  {
    getCurrent().down(paramTouch);
  }
  
  public void drag(DragEvent paramDragEvent)
  {
    getCurrent().drag(paramDragEvent);
  }
  
  public void dragClick(DragEvent paramDragEvent)
  {
    getCurrent().dragClick(paramDragEvent);
  }
  
  public void flow(List<FlowEvent> paramList)
  {
    getCurrent().flow(paramList);
  }
  
  public void flowComplete(FlowEvent paramFlowEvent)
  {
    getCurrent().flowComplete(paramFlowEvent);
  }
  
  public void flowStarted()
  {
    getCurrent().flowStarted();
  }
  
  public EnumSet<ActionType> getActions()
  {
    EnumSet localEnumSet = EnumSet.noneOf(ActionType.class);
    Iterator localIterator = getAll().iterator();
    while (localIterator.hasNext()) {
      localEnumSet.addAll(((Action)localIterator.next()).getActions());
    }
    return localEnumSet;
  }
  
  protected abstract Collection<Action> getAll();
  
  protected abstract Action getCurrent();
  
  public DragBehaviour getDragBehaviour()
  {
    ArrayList localArrayList = Lists.newArrayList();
    Iterator localIterator = getAll().iterator();
    while (localIterator.hasNext()) {
      localArrayList.add(((Action)localIterator.next()).getDragBehaviour());
    }
    return DragBehaviour.combineBehaviours(localArrayList);
  }
  
  public float getFlowXActivationThreshold()
  {
    ArrayList localArrayList = Lists.newArrayList();
    Iterator localIterator = getAll().iterator();
    while (localIterator.hasNext()) {
      localArrayList.add(Float.valueOf(((Action)localIterator.next()).getFlowXActivationThreshold()));
    }
    return ((Float)Collections.max(localArrayList)).floatValue();
  }
  
  public float getFlowYActivationThreshold()
  {
    ArrayList localArrayList = Lists.newArrayList();
    Iterator localIterator = getAll().iterator();
    while (localIterator.hasNext()) {
      localArrayList.add(Float.valueOf(((Action)localIterator.next()).getFlowYActivationThreshold()));
    }
    return ((Float)Collections.max(localArrayList)).floatValue();
  }
  
  public int getLongPressTimeOut()
  {
    ArrayList localArrayList = Lists.newArrayList();
    Iterator localIterator = getAll().iterator();
    while (localIterator.hasNext()) {
      localArrayList.add(Integer.valueOf(((Action)localIterator.next()).getLongPressTimeOut()));
    }
    return ((Integer)Collections.max(localArrayList)).intValue();
  }
  
  public int getMultitapResetDelay()
  {
    ArrayList localArrayList = Lists.newArrayList();
    Iterator localIterator = getAll().iterator();
    while (localIterator.hasNext()) {
      localArrayList.add(Integer.valueOf(((Action)localIterator.next()).getMultitapResetDelay()));
    }
    return ((Integer)Collections.min(localArrayList)).intValue();
  }
  
  public RepeatBehaviour getRepeatBehaviour()
  {
    RepeatBehaviour localRepeatBehaviour = RepeatBehaviour.EMPTY_REPEAT_BEHAVIOUR;
    Iterator localIterator = getAll().iterator();
    while (localIterator.hasNext()) {
      localRepeatBehaviour = localRepeatBehaviour.mergeWith(((Action)localIterator.next()).getRepeatBehaviour());
    }
    return localRepeatBehaviour;
  }
  
  public float getSwipeMinimumXVelocity()
  {
    ArrayList localArrayList = Lists.newArrayList();
    Iterator localIterator = getAll().iterator();
    while (localIterator.hasNext()) {
      localArrayList.add(Float.valueOf(((Action)localIterator.next()).getSwipeMinimumXVelocity()));
    }
    return ((Float)Collections.max(localArrayList)).floatValue();
  }
  
  public float getSwipeMinimumYVelocity()
  {
    ArrayList localArrayList = Lists.newArrayList();
    Iterator localIterator = getAll().iterator();
    while (localIterator.hasNext()) {
      localArrayList.add(Float.valueOf(((Action)localIterator.next()).getSwipeMinimumYVelocity()));
    }
    return ((Float)Collections.max(localArrayList)).floatValue();
  }
  
  public float getSwipeXActivationThreshold()
  {
    ArrayList localArrayList = Lists.newArrayList();
    Iterator localIterator = getAll().iterator();
    while (localIterator.hasNext()) {
      localArrayList.add(Float.valueOf(((Action)localIterator.next()).getSwipeXActivationThreshold()));
    }
    return ((Float)Collections.max(localArrayList)).floatValue();
  }
  
  public float getSwipeYActivationThreshold()
  {
    ArrayList localArrayList = Lists.newArrayList();
    Iterator localIterator = getAll().iterator();
    while (localIterator.hasNext()) {
      localArrayList.add(Float.valueOf(((Action)localIterator.next()).getSwipeYActivationThreshold()));
    }
    return ((Float)Collections.max(localArrayList)).floatValue();
  }
  
  public void longClick(TouchEvent.Touch paramTouch)
  {
    getCurrent().longClick(paramTouch);
  }
  
  public void longPress()
  {
    getCurrent().longPress();
  }
  
  public void multitap(TouchEvent.Touch paramTouch, int paramInt)
  {
    getCurrent().multitap(paramTouch, paramInt);
  }
  
  public void repeat(int paramInt)
  {
    getCurrent().repeat(paramInt);
  }
  
  public void setLoggableActions(EnumSet<ActionType> paramEnumSet)
  {
    Iterator localIterator = getAll().iterator();
    while (localIterator.hasNext()) {
      ((Action)localIterator.next()).setLoggableActions(paramEnumSet);
    }
  }
  
  public boolean shouldBlockClicks()
  {
    boolean bool = true;
    Iterator localIterator = getAll().iterator();
    if (localIterator.hasNext())
    {
      Action localAction = (Action)localIterator.next();
      if ((bool) && (localAction.shouldBlockClicks())) {}
      for (bool = true;; bool = false) {
        break;
      }
    }
    return bool;
  }
  
  public void slideIn(TouchEvent.Touch paramTouch)
  {
    getCurrent().slideIn(paramTouch);
  }
  
  public void slideOut(TouchEvent.Touch paramTouch)
  {
    getCurrent().slideOut(paramTouch);
  }
  
  public void swipeDown()
  {
    getCurrent().swipeDown();
  }
  
  public void swipeLeft()
  {
    getCurrent().swipeLeft();
  }
  
  public void swipeRight()
  {
    getCurrent().swipeRight();
  }
  
  public void swipeUp()
  {
    getCurrent().swipeUp();
  }
  
  public void up(TouchEvent.Touch paramTouch)
  {
    getCurrent().up(paramTouch);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.actions.StateSwitchingAction
 * JD-Core Version:    0.7.0.1
 */