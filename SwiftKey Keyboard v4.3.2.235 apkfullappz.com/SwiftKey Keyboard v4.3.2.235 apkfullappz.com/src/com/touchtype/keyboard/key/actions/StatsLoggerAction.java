package com.touchtype.keyboard.key.actions;

import com.touchtype.keyboard.key.callbacks.DragBehaviour;
import com.touchtype.keyboard.key.callbacks.DragEvent;
import com.touchtype.keyboard.key.callbacks.DragFilter;
import com.touchtype.keyboard.view.touch.FlowEvent;
import com.touchtype.keyboard.view.touch.TouchEvent.Touch;
import com.touchtype.preferences.TouchTypePreferences;
import com.touchtype.report.TouchTypeStats;
import java.util.EnumSet;
import java.util.List;

public final class StatsLoggerAction
  implements Action
{
  private EnumSet<ActionType> mLogTypes = EnumSet.noneOf(ActionType.class);
  private final TouchTypeStats mStats;
  
  public StatsLoggerAction(TouchTypePreferences paramTouchTypePreferences)
  {
    this.mStats = paramTouchTypePreferences.getTouchTypeStats();
  }
  
  public void cancel() {}
  
  public void click(TouchEvent.Touch paramTouch) {}
  
  public void down(TouchEvent.Touch paramTouch) {}
  
  public void drag(DragEvent paramDragEvent) {}
  
  public void dragClick(DragEvent paramDragEvent) {}
  
  public void flow(List<FlowEvent> paramList) {}
  
  public void flowComplete(FlowEvent paramFlowEvent) {}
  
  public void flowStarted() {}
  
  public EnumSet<ActionType> getActions()
  {
    return EnumSet.noneOf(ActionType.class);
  }
  
  public DragBehaviour getDragBehaviour()
  {
    return ActionParams.EMPTY_PARAMS.mDragFilter.createDragBehaviour(ActionParams.EMPTY_PARAMS.mDragThreshold);
  }
  
  public float getFlowXActivationThreshold()
  {
    return ActionParams.EMPTY_PARAMS.mFlowXActivationThreshold;
  }
  
  public float getFlowYActivationThreshold()
  {
    return ActionParams.EMPTY_PARAMS.mFlowYActivationThreshold;
  }
  
  public int getLongPressTimeOut()
  {
    return ActionParams.EMPTY_PARAMS.mLongPressTimeout;
  }
  
  public int getMultitapResetDelay()
  {
    return ActionParams.EMPTY_PARAMS.mMultitapResetDelay;
  }
  
  public RepeatBehaviour getRepeatBehaviour()
  {
    return ActionParams.EMPTY_PARAMS.mRepeatBehaviour;
  }
  
  public float getSwipeMinimumXVelocity()
  {
    return ActionParams.EMPTY_PARAMS.mSwipeMinXVelocity;
  }
  
  public float getSwipeMinimumYVelocity()
  {
    return ActionParams.EMPTY_PARAMS.mSwipeMinYVelocity;
  }
  
  public float getSwipeXActivationThreshold()
  {
    return ActionParams.EMPTY_PARAMS.mSwipeXActivationThreshold;
  }
  
  public float getSwipeYActivationThreshold()
  {
    return ActionParams.EMPTY_PARAMS.mSwipeYActivationThreshold;
  }
  
  public void longClick(TouchEvent.Touch paramTouch) {}
  
  public void longPress() {}
  
  public void multitap(TouchEvent.Touch paramTouch, int paramInt) {}
  
  public void repeat(int paramInt) {}
  
  public void setLoggableActions(EnumSet<ActionType> paramEnumSet)
  {
    this.mLogTypes = paramEnumSet;
  }
  
  public boolean shouldBlockClicks()
  {
    return ActionParams.EMPTY_PARAMS.mBlockClicks;
  }
  
  public void slideIn(TouchEvent.Touch paramTouch) {}
  
  public void slideOut(TouchEvent.Touch paramTouch) {}
  
  public void swipeDown()
  {
    if (this.mLogTypes.contains(ActionType.SWIPE_DOWN)) {
      this.mStats.incrementStatistic("stats_swipedown_uses");
    }
  }
  
  public void swipeLeft() {}
  
  public void swipeRight() {}
  
  public void swipeUp()
  {
    if (this.mLogTypes.contains(ActionType.SWIPE_UP)) {
      this.mStats.incrementStatistic("stats_swipeup_uses");
    }
  }
  
  public void up(TouchEvent.Touch paramTouch) {}
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.actions.StatsLoggerAction
 * JD-Core Version:    0.7.0.1
 */