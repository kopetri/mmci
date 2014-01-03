package com.touchtype.settings;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import com.touchtype.preferences.TouchTypePreferences;
import com.touchtype.report.TouchTypeStats;
import com.touchtype.settings.custompreferences.UsageStatPreference;
import junit.framework.Assert;

public class UsageStatsPreferenceConfiguration
  extends PreferenceWrapper
{
  private static final String TAG = UsageStatsPreferenceConfiguration.class.getSimpleName();
  private UsageStatPreference mDistanceFlowedPreference;
  private UsageStatPreference mEfficiencyPreference;
  private Preference mHeatmapPreference;
  private final Preference.OnPreferenceClickListener mIncrementStatsListener = new Preference.OnPreferenceClickListener()
  {
    public boolean onPreferenceClick(Preference paramAnonymousPreference)
    {
      if (paramAnonymousPreference.equals(UsageStatsPreferenceConfiguration.this.mEfficiencyPreference)) {
        UsageStatsPreferenceConfiguration.this.mTouchTypeStats.incrementStatistic("stats_shared_efficiency");
      }
      for (;;)
      {
        return false;
        if (paramAnonymousPreference.equals(UsageStatsPreferenceConfiguration.this.mDistanceFlowedPreference)) {
          UsageStatsPreferenceConfiguration.this.mTouchTypeStats.incrementStatistic("stats_shared_distance_flowed");
        } else if (paramAnonymousPreference.equals(UsageStatsPreferenceConfiguration.this.mKeystrokesPreference)) {
          UsageStatsPreferenceConfiguration.this.mTouchTypeStats.incrementStatistic("stats_shared_keystrokes_saved");
        } else if (paramAnonymousPreference.equals(UsageStatsPreferenceConfiguration.this.mLettersCorrectedPreference)) {
          UsageStatsPreferenceConfiguration.this.mTouchTypeStats.incrementStatistic("stats_shared_letters_corrected");
        } else if (paramAnonymousPreference.equals(UsageStatsPreferenceConfiguration.this.mWordsFlowedPreference)) {
          UsageStatsPreferenceConfiguration.this.mTouchTypeStats.incrementStatistic("stats_shared_words_flowed");
        } else if (paramAnonymousPreference.equals(UsageStatsPreferenceConfiguration.this.mWordsPredictedPreference)) {
          UsageStatsPreferenceConfiguration.this.mTouchTypeStats.incrementStatistic("stats_shared_words_predicted");
        } else if (paramAnonymousPreference.equals(UsageStatsPreferenceConfiguration.this.mWordsCompletedPreference)) {
          UsageStatsPreferenceConfiguration.this.mTouchTypeStats.incrementStatistic("stats_shared_words_completed");
        }
      }
    }
  };
  private UsageStatPreference mKeystrokesPreference;
  private UsageStatPreference mLettersCorrectedPreference;
  private TouchTypeStats mTouchTypeStats;
  private UsageStatPreference mWordsCompletedPreference;
  private UsageStatPreference mWordsFlowedPreference;
  private UsageStatPreference mWordsPredictedPreference;
  
  public UsageStatsPreferenceConfiguration(PreferenceActivity paramPreferenceActivity)
  {
    super(paramPreferenceActivity);
    addPreference(2131034805);
  }
  
  public UsageStatsPreferenceConfiguration(PreferenceFragment paramPreferenceFragment)
  {
    super(paramPreferenceFragment);
    addPreference(2131034805);
  }
  
  public static int efficiency(TouchTypeStats paramTouchTypeStats)
  {
    if (paramTouchTypeStats.getStatisticInt("stats_entered_characters") == 0) {
      return 0;
    }
    return (int)(100.0D * paramTouchTypeStats.getKeyStrokesSaved()) / paramTouchTypeStats.getStatisticInt("stats_entered_characters");
  }
  
  private String getString(int paramInt)
  {
    return getContext().getResources().getString(paramInt);
  }
  
  public void setup(PreferenceActivity paramPreferenceActivity)
  {
    this.mTouchTypeStats = TouchTypePreferences.getInstance(getContext()).getTouchTypeStats();
    this.mEfficiencyPreference = ((UsageStatPreference)findPreference(getString(2131296616)));
    Assert.assertNotNull("Has the effiency preference been removed?", this.mEfficiencyPreference);
    int i = efficiency(this.mTouchTypeStats);
    UsageStatPreference localUsageStatPreference1 = this.mEfficiencyPreference;
    String str1 = getString(2131296618);
    Object[] arrayOfObject1 = new Object[1];
    arrayOfObject1[0] = Integer.valueOf(i);
    localUsageStatPreference1.setTitleValue(String.format(str1, arrayOfObject1));
    UsageStatPreference localUsageStatPreference2 = this.mEfficiencyPreference;
    String str2 = getString(2131296619);
    Object[] arrayOfObject2 = new Object[1];
    arrayOfObject2[0] = Integer.valueOf(i);
    localUsageStatPreference2.setSummary(String.format(str2, arrayOfObject2));
    this.mEfficiencyPreference.setOnPreferenceClickListener(this.mIncrementStatsListener);
    this.mKeystrokesPreference = ((UsageStatPreference)findPreference(getString(2131296624)));
    Assert.assertNotNull("Has the keystrokes preference been removed?", this.mKeystrokesPreference);
    Object[] arrayOfObject3 = new Object[1];
    arrayOfObject3[0] = Integer.valueOf(this.mTouchTypeStats.getKeyStrokesSaved());
    String str3 = String.format("%,d", arrayOfObject3);
    this.mKeystrokesPreference.setTitleValue(str3);
    this.mKeystrokesPreference.setSummary(String.format(getString(2131296626), new Object[] { str3 }));
    this.mKeystrokesPreference.setOnPreferenceClickListener(this.mIncrementStatsListener);
    this.mLettersCorrectedPreference = ((UsageStatPreference)findPreference(getString(2131296627)));
    Assert.assertNotNull("Has the letters corrected preference been removed?", this.mLettersCorrectedPreference);
    Object[] arrayOfObject4 = new Object[1];
    arrayOfObject4[0] = Integer.valueOf(this.mTouchTypeStats.getStatisticInt("stats_chars_corrected"));
    String str4 = String.format("%,d", arrayOfObject4);
    this.mLettersCorrectedPreference.setTitleValue(str4);
    this.mLettersCorrectedPreference.setSummary(String.format(getString(2131296629), new Object[] { str4 }));
    this.mLettersCorrectedPreference.setOnPreferenceClickListener(this.mIncrementStatsListener);
    this.mWordsPredictedPreference = ((UsageStatPreference)findPreference(getString(2131296633)));
    Assert.assertNotNull("Has the words predicted preference been removed?", this.mWordsPredictedPreference);
    Object[] arrayOfObject5 = new Object[1];
    arrayOfObject5[0] = Integer.valueOf(this.mTouchTypeStats.getStatisticInt("stats_words_predicted"));
    String str5 = String.format("%,d", arrayOfObject5);
    this.mWordsPredictedPreference.setTitleValue(str5);
    this.mWordsPredictedPreference.setSummary(String.format(getString(2131296635), new Object[] { str5 }));
    this.mWordsPredictedPreference.setOnPreferenceClickListener(this.mIncrementStatsListener);
    this.mWordsCompletedPreference = ((UsageStatPreference)findPreference(getString(2131296636)));
    Assert.assertNotNull("Has the words corrected preference been removed?", this.mWordsCompletedPreference);
    Object[] arrayOfObject6 = new Object[1];
    arrayOfObject6[0] = Integer.valueOf(this.mTouchTypeStats.getStatisticInt("stats_words_completed"));
    String str6 = String.format("%,d", arrayOfObject6);
    this.mWordsCompletedPreference.setTitleValue(str6);
    this.mWordsCompletedPreference.setSummary(String.format(getString(2131296638), new Object[] { str6 }));
    this.mWordsCompletedPreference.setOnPreferenceClickListener(this.mIncrementStatsListener);
    this.mHeatmapPreference = findPreference(getString(2131296642));
    if (this.mHeatmapPreference != null)
    {
      Intent localIntent = new Intent();
      localIntent.setClassName(getContext().getPackageName(), getContext().getResources().getString(2131296758));
      localIntent.setAction("android.intent.action.VIEW");
      this.mHeatmapPreference.setIntent(localIntent);
    }
    Preference localPreference1 = findPreference(getString(2131296620));
    Preference localPreference2 = findPreference(getString(2131296630));
    if (getContext().getResources().getBoolean(2131492922))
    {
      this.mDistanceFlowedPreference = ((UsageStatPreference)localPreference1);
      Assert.assertNotNull("Has the distance flowed preference been removed?", this.mDistanceFlowedPreference);
      Object[] arrayOfObject7 = new Object[1];
      arrayOfObject7[0] = Float.valueOf(this.mTouchTypeStats.getStatisticFloat("stats_distance_flowed"));
      String str7 = String.format("%.2f", arrayOfObject7);
      this.mDistanceFlowedPreference.setTitleValue(str7 + getString(2131296622));
      this.mDistanceFlowedPreference.setSummary(String.format(getString(2131296623), new Object[] { str7 }));
      this.mDistanceFlowedPreference.setOnPreferenceClickListener(this.mIncrementStatsListener);
      this.mWordsFlowedPreference = ((UsageStatPreference)localPreference2);
      Assert.assertNotNull("Has the words flowed preference been removed?", this.mWordsFlowedPreference);
      Object[] arrayOfObject8 = new Object[1];
      arrayOfObject8[0] = Integer.valueOf(this.mTouchTypeStats.getStatisticInt("stats_words_flowed"));
      String str8 = String.format("%,d", arrayOfObject8);
      this.mWordsFlowedPreference.setTitleValue(str8);
      this.mWordsFlowedPreference.setSummary(String.format(getString(2131296632), new Object[] { str8 }));
      this.mWordsFlowedPreference.setOnPreferenceClickListener(this.mIncrementStatsListener);
      return;
    }
    removePreference(localPreference1);
    removePreference(localPreference2);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.settings.UsageStatsPreferenceConfiguration
 * JD-Core Version:    0.7.0.1
 */