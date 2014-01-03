package com.google.android.gms.internal;

import android.net.Uri;
import android.os.Parcel;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.GameEntity;

public final class bf
  extends j
  implements Game
{
  public int describeContents()
  {
    return 0;
  }
  
  public boolean equals(Object paramObject)
  {
    return GameEntity.a(this, paramObject);
  }
  
  public Game freeze()
  {
    return new GameEntity(this);
  }
  
  public int getAchievementTotalCount()
  {
    return getInteger("achievement_total_count");
  }
  
  public String getApplicationId()
  {
    return getString("external_game_id");
  }
  
  public String getDescription()
  {
    return getString("game_description");
  }
  
  public String getDeveloperName()
  {
    return getString("developer_name");
  }
  
  public String getDisplayName()
  {
    return getString("display_name");
  }
  
  public Uri getFeaturedImageUri()
  {
    return c("featured_image_uri");
  }
  
  public int getGameplayAclStatus()
  {
    return getInteger("gameplay_acl_status");
  }
  
  public Uri getHiResImageUri()
  {
    return c("game_hi_res_image_uri");
  }
  
  public Uri getIconImageUri()
  {
    return c("game_icon_image_uri");
  }
  
  public String getInstancePackageName()
  {
    return getString("package_name");
  }
  
  public int getLeaderboardCount()
  {
    return getInteger("leaderboard_count");
  }
  
  public String getPrimaryCategory()
  {
    return getString("primary_category");
  }
  
  public String getSecondaryCategory()
  {
    return getString("secondary_category");
  }
  
  public int hashCode()
  {
    return GameEntity.a(this);
  }
  
  public boolean isInstanceInstalled()
  {
    return getInteger("installed") > 0;
  }
  
  public boolean isPlayEnabledGame()
  {
    return getBoolean("play_enabled_game");
  }
  
  public String toString()
  {
    return GameEntity.b(this);
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    ((GameEntity)freeze()).writeToParcel(paramParcel, paramInt);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.bf
 * JD-Core Version:    0.7.0.1
 */