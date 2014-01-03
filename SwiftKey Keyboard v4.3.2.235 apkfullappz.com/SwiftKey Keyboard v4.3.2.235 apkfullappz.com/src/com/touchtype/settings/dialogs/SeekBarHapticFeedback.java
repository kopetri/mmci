package com.touchtype.settings.dialogs;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.os.Vibrator;
import android.util.AttributeSet;

public class SeekBarHapticFeedback
  extends SeekBarPreference
{
  private static final String TAG = SeekBarHapticFeedback.class.getSimpleName();
  private Vibrator vibrator;
  
  public SeekBarHapticFeedback(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  protected Object onGetDefaultValue(TypedArray paramTypedArray, int paramInt)
  {
    int i = 2131558421;
    if (Build.VERSION.SDK_INT >= 14) {
      i = 2131558422;
    }
    int j = getContext().getResources().getInteger(i);
    setDefaultValue(Integer.valueOf(j));
    return Integer.valueOf(j);
  }
  
  protected void preview(int paramInt)
  {
    if (this.vibrator == null) {
      this.vibrator = ((Vibrator)getContext().getSystemService("vibrator"));
    }
    this.vibrator.vibrate(paramInt);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.settings.dialogs.SeekBarHapticFeedback
 * JD-Core Version:    0.7.0.1
 */