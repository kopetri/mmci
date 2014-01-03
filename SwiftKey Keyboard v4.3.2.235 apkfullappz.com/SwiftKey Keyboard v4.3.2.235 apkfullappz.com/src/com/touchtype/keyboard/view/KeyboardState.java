package com.touchtype.keyboard.view;

import junit.framework.Assert;

public enum KeyboardState
{
  private final String mName;
  private final String mOpenedCountStatsKeyLandscape;
  private final String mOpenedCountStatsKeyPortrait;
  
  static
  {
    FULL_DOCKED = new KeyboardState("FULL_DOCKED", 1, "docked_full", "stats_keyboard_docked_portrait_full_opened_count", "stats_keyboard_docked_landscape_full_opened_count");
    SPLIT_FLOATING = new KeyboardState("SPLIT_FLOATING", 2, "floating_split", "stats_keyboard_floating_portrait_split_opened_count", "stats_keyboard_floating_landscape_split_opened_count");
    SPLIT_DOCKED = new KeyboardState("SPLIT_DOCKED", 3, "docked_split", "stats_keyboard_docked_portrait_split_opened_count", "stats_keyboard_docked_landscape_split_opened_count");
    COMPACT_FLOATING = new KeyboardState("COMPACT_FLOATING", 4, "floating_compact", "stats_keyboard_floating_portrait_compact_opened_count", "stats_keyboard_floating_landscape_compact_opened_count");
    COMPACT_DOCKED = new KeyboardState("COMPACT_DOCKED", 5, "docked_compact", "stats_keyboard_docked_portrait_compact_opened_count", "stats_keyboard_docked_landscape_compact_opened_count");
    KeyboardState[] arrayOfKeyboardState = new KeyboardState[6];
    arrayOfKeyboardState[0] = FULL_FLOATING;
    arrayOfKeyboardState[1] = FULL_DOCKED;
    arrayOfKeyboardState[2] = SPLIT_FLOATING;
    arrayOfKeyboardState[3] = SPLIT_DOCKED;
    arrayOfKeyboardState[4] = COMPACT_FLOATING;
    arrayOfKeyboardState[5] = COMPACT_DOCKED;
    $VALUES = arrayOfKeyboardState;
  }
  
  private KeyboardState(String paramString1, String paramString2, String paramString3)
  {
    this.mName = paramString1;
    this.mOpenedCountStatsKeyPortrait = paramString2;
    this.mOpenedCountStatsKeyLandscape = paramString3;
  }
  
  public static String translateStyle(int paramInt, boolean paramBoolean)
  {
    if (paramBoolean) {
      switch (paramInt)
      {
      default: 
        Assert.assertTrue("FloatingKeyboardState cannot translate style: " + paramInt, false);
      }
    }
    for (;;)
    {
      return "";
      return FULL_DOCKED.getName();
      return SPLIT_DOCKED.getName();
      return COMPACT_DOCKED.getName();
      switch (paramInt)
      {
      default: 
        Assert.assertTrue("FloatingKeyboardState cannot translate style: " + paramInt, false);
      }
    }
    return FULL_FLOATING.getName();
    return SPLIT_FLOATING.getName();
    return COMPACT_FLOATING.getName();
  }
  
  public String getName()
  {
    return this.mName;
  }
  
  public String getOpenedCountStatsKey(boolean paramBoolean)
  {
    if (paramBoolean) {
      return this.mOpenedCountStatsKeyLandscape;
    }
    return this.mOpenedCountStatsKeyPortrait;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.view.KeyboardState
 * JD-Core Version:    0.7.0.1
 */