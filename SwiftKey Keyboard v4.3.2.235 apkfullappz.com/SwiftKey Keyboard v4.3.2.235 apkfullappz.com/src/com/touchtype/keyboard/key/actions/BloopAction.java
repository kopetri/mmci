package com.touchtype.keyboard.key.actions;

import android.content.Context;
import android.os.Vibrator;
import com.touchtype.media.SoundPlayer;
import com.touchtype.preferences.TouchTypePreferences;
import java.util.EnumSet;

public final class BloopAction
  extends GenericActionDecorator
{
  private final Context mContext;
  public final int mSound;
  private Vibrator mVibrator;
  
  public BloopAction(Context paramContext, Action paramAction)
  {
    this(paramAction.getActions(), paramContext, 0, ActionParams.EMPTY_PARAMS, paramAction);
  }
  
  public BloopAction(EnumSet<ActionType> paramEnumSet, Context paramContext, int paramInt, ActionParams paramActionParams, Action paramAction)
  {
    super(paramEnumSet, paramActionParams, paramAction);
    this.mContext = paramContext;
    this.mSound = paramInt;
  }
  
  public BloopAction(EnumSet<ActionType> paramEnumSet, Context paramContext, ActionParams paramActionParams, Action paramAction)
  {
    this(paramEnumSet, paramContext, 0, paramActionParams, paramAction);
  }
  
  protected void act()
  {
    TouchTypePreferences localTouchTypePreferences = TouchTypePreferences.getInstance(this.mContext);
    if (localTouchTypePreferences.isVibrateEnabled())
    {
      if (this.mVibrator == null) {
        this.mVibrator = ((Vibrator)this.mContext.getSystemService("vibrator"));
      }
      this.mVibrator.vibrate(localTouchTypePreferences.getVibrationDuration());
    }
    for (;;)
    {
      if (localTouchTypePreferences.isSoundFeedbackEnabled()) {
        SoundPlayer.getInstance(this.mContext).playSound(this.mSound, localTouchTypePreferences.getSoundVolume());
      }
      return;
      this.mVibrator = null;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.actions.BloopAction
 * JD-Core Version:    0.7.0.1
 */