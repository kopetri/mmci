package com.touchtype.preferences;

import android.content.Context;
import android.os.Vibrator;
import com.touchtype.media.SoundPlayer;

public final class HapticsUtil
{
  public static void hapticClick(Context paramContext)
  {
    TouchTypePreferences localTouchTypePreferences = TouchTypePreferences.getInstance(paramContext);
    if (localTouchTypePreferences.isVibrateEnabled()) {
      ((Vibrator)paramContext.getSystemService("vibrator")).vibrate(localTouchTypePreferences.getVibrationDuration());
    }
    if (localTouchTypePreferences.isSoundFeedbackEnabled()) {
      SoundPlayer.getInstance(paramContext).playSound(0, localTouchTypePreferences.getSoundVolume());
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.preferences.HapticsUtil
 * JD-Core Version:    0.7.0.1
 */