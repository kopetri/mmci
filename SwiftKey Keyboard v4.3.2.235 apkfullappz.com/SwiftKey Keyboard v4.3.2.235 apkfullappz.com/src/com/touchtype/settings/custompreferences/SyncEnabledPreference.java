package com.touchtype.settings.custompreferences;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

public class SyncEnabledPreference
  extends Preference
{
  private CheckBox enabledCheckBox;
  private boolean isSyncing = false;
  private AnimationDrawable syncingAnimation;
  private ImageView syncingIndicator;
  
  public SyncEnabledPreference(Context paramContext)
  {
    super(paramContext);
  }
  
  public SyncEnabledPreference(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public SyncEnabledPreference(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  private void updateSyncingIndicator()
  {
    ImageView localImageView = this.syncingIndicator;
    if (this.isSyncing) {}
    for (int i = 0;; i = 4)
    {
      localImageView.setVisibility(i);
      if (!this.isSyncing) {
        break;
      }
      this.syncingAnimation.start();
      return;
    }
    this.syncingAnimation.stop();
  }
  
  public boolean isChecked()
  {
    return getPersistedBoolean(false);
  }
  
  protected void onBindView(View paramView)
  {
    super.onBindView(paramView);
    this.syncingIndicator = ((ImageView)paramView.findViewById(2131230907));
    this.syncingAnimation = ((AnimationDrawable)this.syncingIndicator.getBackground());
    this.enabledCheckBox = ((CheckBox)paramView.findViewById(2131230908));
    this.enabledCheckBox.setChecked(getPersistedBoolean(false));
    updateSyncingIndicator();
  }
  
  protected void onClick()
  {
    super.onClick();
    if (!this.enabledCheckBox.isChecked()) {}
    for (boolean bool = true;; bool = false)
    {
      this.enabledCheckBox.setChecked(bool);
      persistBoolean(bool);
      notifyChanged();
      return;
    }
  }
  
  public void setSyncingState(boolean paramBoolean)
  {
    this.isSyncing = paramBoolean;
    if (!paramBoolean) {}
    for (boolean bool = true;; bool = false)
    {
      setEnabled(bool);
      if (this.syncingIndicator != null) {
        updateSyncingIndicator();
      }
      return;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.settings.custompreferences.SyncEnabledPreference
 * JD-Core Version:    0.7.0.1
 */