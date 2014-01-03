package com.touchtype.report.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.annotations.SerializedName;
import com.touchtype.preferences.SwiftKeyPreferences;
import com.touchtype.report.TouchTypeStats;

final class Debug
{
  @SerializedName("corruptDynamicModelDeletions")
  private int mCorruptDynamicModelDeletions;
  @SerializedName("keyCounts")
  private JsonObject mKeyCounts;
  
  public static Debug newInstance(SwiftKeyPreferences paramSwiftKeyPreferences, TouchTypeStats paramTouchTypeStats)
  {
    Debug localDebug = new Debug();
    if (paramTouchTypeStats.getStatisticInt("stats_entered_characters") > 200) {}
    for (String str = paramSwiftKeyPreferences.getString("stats_key_counts", "{}");; str = "{}")
    {
      localDebug.mKeyCounts = new JsonParser().parse(str).getAsJsonObject();
      localDebug.mCorruptDynamicModelDeletions = paramTouchTypeStats.getStatisticInt("stats_corrupt_dynamic_model_deletions");
      return localDebug;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.report.json.Debug
 * JD-Core Version:    0.7.0.1
 */