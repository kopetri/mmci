package com.touchtype.settings.dialogs;

import android.content.Context;
import android.util.AttributeSet;
import com.touchtype.media.SoundPlayer;

public class SeekBarSoundFeedback
  extends SeekBarPreference
{
  private static final String TAG = SeekBarSoundFeedback.class.getSimpleName();
  
  public SeekBarSoundFeedback(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  protected void preview(int paramInt)
  {
    SoundPlayer.getInstance(getContext()).playSound(0, paramInt);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.settings.dialogs.SeekBarSoundFeedback
 * JD-Core Version:    0.7.0.1
 */