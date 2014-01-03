package com.touchtype.keyboard.key.callbacks;

import com.touchtype.keyboard.key.delegates.FlowDelegate.FlowCallback;
import com.touchtype.keyboard.key.delegates.KeyTouches;
import com.touchtype.keyboard.key.delegates.LongPressDelegate.LongPressFiredCallback;
import com.touchtype.keyboard.key.delegates.MultiTapDelegate.MultiTapCallback;
import com.touchtype.keyboard.key.delegates.RepeatsDelegate.RepeatFiredCallback;
import com.touchtype.keyboard.view.SwipeGestureDetector.SwipeGestureListener;

public abstract interface KeyCallbacks
  extends DragCallback, FlowDelegate.FlowCallback, KeyTouches, LongPressDelegate.LongPressFiredCallback, MultiTapDelegate.MultiTapCallback, RepeatsDelegate.RepeatFiredCallback, SwipeGestureDetector.SwipeGestureListener
{}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.callbacks.KeyCallbacks
 * JD-Core Version:    0.7.0.1
 */