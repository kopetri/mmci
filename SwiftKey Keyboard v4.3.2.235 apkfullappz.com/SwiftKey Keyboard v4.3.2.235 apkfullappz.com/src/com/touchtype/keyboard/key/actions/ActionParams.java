package com.touchtype.keyboard.key.actions;

import com.touchtype.keyboard.key.callbacks.DragBehaviour.DragThreshold;
import com.touchtype.keyboard.key.callbacks.DragFilter;
import com.touchtype.keyboard.key.callbacks.DragFilterFactory;

public final class ActionParams
{
  public static ActionParams EMPTY_PARAMS = new ActionParamsBuilder().build();
  final boolean mBlockClicks;
  final DragFilter mDragFilter;
  final DragBehaviour.DragThreshold mDragThreshold;
  final float mFlowXActivationThreshold;
  final float mFlowYActivationThreshold;
  final int mLongPressTimeout;
  final int mMultitapResetDelay;
  final RepeatBehaviour mRepeatBehaviour;
  final float mSwipeMinXVelocity;
  final float mSwipeMinYVelocity;
  final float mSwipeXActivationThreshold;
  final float mSwipeYActivationThreshold;
  
  private ActionParams(int paramInt1, int paramInt2, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, DragBehaviour.DragThreshold paramDragThreshold, DragFilter paramDragFilter, RepeatBehaviour paramRepeatBehaviour, boolean paramBoolean)
  {
    this.mLongPressTimeout = paramInt1;
    this.mMultitapResetDelay = paramInt2;
    this.mFlowXActivationThreshold = paramFloat1;
    this.mFlowYActivationThreshold = paramFloat2;
    this.mSwipeXActivationThreshold = paramFloat3;
    this.mSwipeYActivationThreshold = paramFloat4;
    this.mSwipeMinXVelocity = paramFloat5;
    this.mSwipeMinYVelocity = paramFloat6;
    this.mDragThreshold = paramDragThreshold;
    this.mDragFilter = paramDragFilter;
    this.mRepeatBehaviour = paramRepeatBehaviour;
    this.mBlockClicks = paramBoolean;
  }
  
  public static final class ActionParamsBuilder
  {
    private boolean mBlockClicks = true;
    private DragFilter mDragFilter = DragFilterFactory.EMPTY_FILTER;
    private DragBehaviour.DragThreshold mDragThreshold;
    private float mFlowXActivationThreshold;
    private float mFlowYActivationThreshold;
    private int mLongPressTimeout;
    private int mMultitapResetDelay = 2147483647;
    private RepeatBehaviour mRepeatBehaviour = RepeatBehaviour.EMPTY_REPEAT_BEHAVIOUR;
    private float mSwipeMinXVelocity;
    private float mSwipeMinYVelocity;
    private float mSwipeXActivationThreshold;
    private float mSwipeYActivationThreshold;
    
    public ActionParamsBuilder blockClicksOnLongpress(boolean paramBoolean)
    {
      this.mBlockClicks = paramBoolean;
      return this;
    }
    
    public ActionParams build()
    {
      return new ActionParams(this.mLongPressTimeout, this.mMultitapResetDelay, this.mFlowXActivationThreshold, this.mFlowYActivationThreshold, this.mSwipeXActivationThreshold, this.mSwipeYActivationThreshold, this.mSwipeMinXVelocity, this.mSwipeMinYVelocity, this.mDragThreshold, this.mDragFilter, this.mRepeatBehaviour, this.mBlockClicks, null);
    }
    
    public ActionParamsBuilder dragBehaviour(DragFilter paramDragFilter, float paramFloat1, float paramFloat2)
    {
      this.mDragFilter = paramDragFilter;
      this.mDragThreshold = new DragBehaviour.DragThreshold(paramFloat1, paramFloat2);
      return this;
    }
    
    public ActionParamsBuilder flowXActivationThreshold(float paramFloat)
    {
      this.mFlowXActivationThreshold = paramFloat;
      return this;
    }
    
    public ActionParamsBuilder flowYActivationThreshold(float paramFloat)
    {
      this.mFlowYActivationThreshold = paramFloat;
      return this;
    }
    
    public ActionParamsBuilder longPressTimeout(int paramInt)
    {
      this.mLongPressTimeout = paramInt;
      return this;
    }
    
    public ActionParamsBuilder repeatBehaviour(RepeatBehaviour paramRepeatBehaviour)
    {
      this.mRepeatBehaviour = paramRepeatBehaviour;
      return this;
    }
    
    public ActionParamsBuilder swipeMinXVelocity(float paramFloat)
    {
      this.mSwipeMinXVelocity = paramFloat;
      return this;
    }
    
    public ActionParamsBuilder swipeMinYVelocity(float paramFloat)
    {
      this.mSwipeMinYVelocity = paramFloat;
      return this;
    }
    
    public ActionParamsBuilder swipeXActivationThreshold(float paramFloat)
    {
      this.mSwipeXActivationThreshold = paramFloat;
      return this;
    }
    
    public ActionParamsBuilder swipeYActivationThreshold(float paramFloat)
    {
      this.mSwipeYActivationThreshold = paramFloat;
      return this;
    }
  }
  
  public static final class RepeatBehaviourPresets
  {
    public static RepeatBehaviour getAcceleratingDeleteRepeatBehaviour(int paramInt)
    {
      new RepeatBehaviour()
      {
        public int getRepeatInterval(int paramAnonymousInt)
        {
          return Math.max(250 - paramAnonymousInt * 14, 120);
        }
        
        public int getRepeatStartDelay()
        {
          return 500 + this.val$longPressTimeOut;
        }
      };
    }
    
    public static RepeatBehaviour getDefaultRepeatBehaviour()
    {
      new RepeatBehaviour()
      {
        public int getRepeatInterval(int paramAnonymousInt)
        {
          return 120;
        }
        
        public int getRepeatStartDelay()
        {
          return 500;
        }
      };
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.actions.ActionParams
 * JD-Core Version:    0.7.0.1
 */