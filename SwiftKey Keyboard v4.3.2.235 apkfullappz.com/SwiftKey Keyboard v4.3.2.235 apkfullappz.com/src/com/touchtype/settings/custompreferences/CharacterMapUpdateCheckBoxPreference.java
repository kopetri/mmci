package com.touchtype.settings.custompreferences;

import android.content.Context;
import android.preference.CheckBoxPreference;
import android.util.AttributeSet;
import com.touchtype.util.LogUtil;
import com.touchtype_fluency.service.FluencyServiceProxy;
import com.touchtype_fluency.service.Predictor;

public class CharacterMapUpdateCheckBoxPreference
  extends CheckBoxPreference
{
  private static final String TAG = CharacterMapUpdateCheckBoxPreference.class.getSimpleName();
  
  public CharacterMapUpdateCheckBoxPreference(Context paramContext)
  {
    super(paramContext);
  }
  
  public CharacterMapUpdateCheckBoxPreference(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public CharacterMapUpdateCheckBoxPreference(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  protected void onClick()
  {
    super.onClick();
    final FluencyServiceProxy local1 = new FluencyServiceProxy() {};
    local1.onCreate(getContext());
    local1.runWhenConnected(new Runnable()
    {
      public void run()
      {
        Predictor localPredictor = local1.getPredictor();
        if (localPredictor != null)
        {
          localPredictor.reloadCharacterMaps();
          local1.onDestroy(CharacterMapUpdateCheckBoxPreference.this.getContext());
          return;
        }
        LogUtil.e(CharacterMapUpdateCheckBoxPreference.TAG, "Predictor not set");
      }
    });
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.settings.custompreferences.CharacterMapUpdateCheckBoxPreference
 * JD-Core Version:    0.7.0.1
 */