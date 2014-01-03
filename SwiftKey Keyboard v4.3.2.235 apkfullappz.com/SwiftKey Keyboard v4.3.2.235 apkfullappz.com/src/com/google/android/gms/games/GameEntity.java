package com.google.android.gms.games;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.w;
import com.google.android.gms.internal.w.a;

public final class GameEntity
  implements Game
{
  public static final Parcelable.Creator<GameEntity> CREATOR = new Parcelable.Creator()
  {
    public GameEntity n(Parcel paramAnonymousParcel)
    {
      String str1 = paramAnonymousParcel.readString();
      String str2 = paramAnonymousParcel.readString();
      String str3 = paramAnonymousParcel.readString();
      String str4 = paramAnonymousParcel.readString();
      String str5 = paramAnonymousParcel.readString();
      String str6 = paramAnonymousParcel.readString();
      String str7 = paramAnonymousParcel.readString();
      Uri localUri1;
      String str8;
      Uri localUri2;
      label62:
      String str9;
      Uri localUri3;
      label76:
      boolean bool1;
      if (str7 == null)
      {
        localUri1 = null;
        str8 = paramAnonymousParcel.readString();
        if (str8 != null) {
          break label151;
        }
        localUri2 = null;
        str9 = paramAnonymousParcel.readString();
        if (str9 != null) {
          break label161;
        }
        localUri3 = null;
        if (paramAnonymousParcel.readInt() <= 0) {
          break label171;
        }
        bool1 = true;
        label86:
        if (paramAnonymousParcel.readInt() <= 0) {
          break label177;
        }
      }
      label151:
      label161:
      label171:
      label177:
      for (boolean bool2 = true;; bool2 = false)
      {
        return new GameEntity(str1, str2, str3, str4, str5, str6, localUri1, localUri2, localUri3, bool1, bool2, paramAnonymousParcel.readString(), paramAnonymousParcel.readInt(), paramAnonymousParcel.readInt(), paramAnonymousParcel.readInt(), null);
        localUri1 = Uri.parse(str7);
        break;
        localUri2 = Uri.parse(str8);
        break label62;
        localUri3 = Uri.parse(str9);
        break label76;
        bool1 = false;
        break label86;
      }
    }
    
    public GameEntity[] v(int paramAnonymousInt)
    {
      return new GameEntity[paramAnonymousInt];
    }
  };
  private final String bp;
  private final String ch;
  private final String ci;
  private final String cj;
  private final String ck;
  private final String cl;
  private final Uri cm;
  private final Uri cn;
  private final Uri co;
  private final boolean cp;
  private final boolean cq;
  private final String cr;
  private final int cs;
  private final int ct;
  private final int cu;
  
  public GameEntity(Game paramGame)
  {
    this.ch = paramGame.getApplicationId();
    this.ci = paramGame.getPrimaryCategory();
    this.cj = paramGame.getSecondaryCategory();
    this.ck = paramGame.getDescription();
    this.cl = paramGame.getDeveloperName();
    this.bp = paramGame.getDisplayName();
    this.cm = paramGame.getIconImageUri();
    this.cn = paramGame.getHiResImageUri();
    this.co = paramGame.getFeaturedImageUri();
    this.cp = paramGame.isPlayEnabledGame();
    this.cq = paramGame.isInstanceInstalled();
    this.cr = paramGame.getInstancePackageName();
    this.cs = paramGame.getGameplayAclStatus();
    this.ct = paramGame.getAchievementTotalCount();
    this.cu = paramGame.getLeaderboardCount();
  }
  
  private GameEntity(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, Uri paramUri1, Uri paramUri2, Uri paramUri3, boolean paramBoolean1, boolean paramBoolean2, String paramString7, int paramInt1, int paramInt2, int paramInt3)
  {
    this.ch = paramString1;
    this.bp = paramString2;
    this.ci = paramString3;
    this.cj = paramString4;
    this.ck = paramString5;
    this.cl = paramString6;
    this.cm = paramUri1;
    this.cn = paramUri2;
    this.co = paramUri3;
    this.cp = paramBoolean1;
    this.cq = paramBoolean2;
    this.cr = paramString7;
    this.cs = paramInt1;
    this.ct = paramInt2;
    this.cu = paramInt3;
  }
  
  public static int a(Game paramGame)
  {
    Object[] arrayOfObject = new Object[15];
    arrayOfObject[0] = paramGame.getApplicationId();
    arrayOfObject[1] = paramGame.getDisplayName();
    arrayOfObject[2] = paramGame.getPrimaryCategory();
    arrayOfObject[3] = paramGame.getSecondaryCategory();
    arrayOfObject[4] = paramGame.getDescription();
    arrayOfObject[5] = paramGame.getDeveloperName();
    arrayOfObject[6] = paramGame.getIconImageUri();
    arrayOfObject[7] = paramGame.getHiResImageUri();
    arrayOfObject[8] = paramGame.getFeaturedImageUri();
    arrayOfObject[9] = Boolean.valueOf(paramGame.isPlayEnabledGame());
    arrayOfObject[10] = Boolean.valueOf(paramGame.isInstanceInstalled());
    arrayOfObject[11] = paramGame.getInstancePackageName();
    arrayOfObject[12] = Integer.valueOf(paramGame.getGameplayAclStatus());
    arrayOfObject[13] = Integer.valueOf(paramGame.getAchievementTotalCount());
    arrayOfObject[14] = Integer.valueOf(paramGame.getLeaderboardCount());
    return w.hashCode(arrayOfObject);
  }
  
  public static boolean a(Game paramGame, Object paramObject)
  {
    if (!(paramObject instanceof Game)) {}
    Game localGame;
    do
    {
      return false;
      if (paramGame == paramObject) {
        return true;
      }
      localGame = (Game)paramObject;
    } while ((!w.a(localGame.getApplicationId(), paramGame.getApplicationId())) || (!w.a(localGame.getDisplayName(), paramGame.getDisplayName())) || (!w.a(localGame.getPrimaryCategory(), paramGame.getPrimaryCategory())) || (!w.a(localGame.getSecondaryCategory(), paramGame.getSecondaryCategory())) || (!w.a(localGame.getDescription(), paramGame.getDescription())) || (!w.a(localGame.getDeveloperName(), paramGame.getDeveloperName())) || (!w.a(localGame.getIconImageUri(), paramGame.getIconImageUri())) || (!w.a(localGame.getHiResImageUri(), paramGame.getHiResImageUri())) || (!w.a(localGame.getFeaturedImageUri(), paramGame.getFeaturedImageUri())) || (!w.a(Boolean.valueOf(localGame.isPlayEnabledGame()), Boolean.valueOf(paramGame.isPlayEnabledGame()))) || (!w.a(Boolean.valueOf(localGame.isInstanceInstalled()), Boolean.valueOf(paramGame.isInstanceInstalled()))) || (!w.a(localGame.getInstancePackageName(), paramGame.getInstancePackageName())) || (!w.a(Integer.valueOf(localGame.getGameplayAclStatus()), Integer.valueOf(paramGame.getGameplayAclStatus()))) || (!w.a(Integer.valueOf(localGame.getAchievementTotalCount()), Integer.valueOf(paramGame.getAchievementTotalCount()))) || (!w.a(Integer.valueOf(localGame.getLeaderboardCount()), Integer.valueOf(paramGame.getLeaderboardCount()))));
    return true;
  }
  
  public static String b(Game paramGame)
  {
    return w.c(paramGame).a("ApplicationId", paramGame.getApplicationId()).a("DisplayName", paramGame.getDisplayName()).a("PrimaryCategory", paramGame.getPrimaryCategory()).a("SecondaryCategory", paramGame.getSecondaryCategory()).a("Description", paramGame.getDescription()).a("DeveloperName", paramGame.getDeveloperName()).a("IconImageUri", paramGame.getIconImageUri()).a("HiResImageUri", paramGame.getHiResImageUri()).a("FeaturedImageUri", paramGame.getFeaturedImageUri()).a("PlayEnabledGame", Boolean.valueOf(paramGame.isPlayEnabledGame())).a("InstanceInstalled", Boolean.valueOf(paramGame.isInstanceInstalled())).a("InstancePackageName", paramGame.getInstancePackageName()).a("GameplayAclStatus", Integer.valueOf(paramGame.getGameplayAclStatus())).a("AchievementTotalCount", Integer.valueOf(paramGame.getAchievementTotalCount())).a("LeaderboardCount", Integer.valueOf(paramGame.getLeaderboardCount())).toString();
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public boolean equals(Object paramObject)
  {
    return a(this, paramObject);
  }
  
  public Game freeze()
  {
    return this;
  }
  
  public int getAchievementTotalCount()
  {
    return this.ct;
  }
  
  public String getApplicationId()
  {
    return this.ch;
  }
  
  public String getDescription()
  {
    return this.ck;
  }
  
  public String getDeveloperName()
  {
    return this.cl;
  }
  
  public String getDisplayName()
  {
    return this.bp;
  }
  
  public Uri getFeaturedImageUri()
  {
    return this.co;
  }
  
  public int getGameplayAclStatus()
  {
    return this.cs;
  }
  
  public Uri getHiResImageUri()
  {
    return this.cn;
  }
  
  public Uri getIconImageUri()
  {
    return this.cm;
  }
  
  public String getInstancePackageName()
  {
    return this.cr;
  }
  
  public int getLeaderboardCount()
  {
    return this.cu;
  }
  
  public String getPrimaryCategory()
  {
    return this.ci;
  }
  
  public String getSecondaryCategory()
  {
    return this.cj;
  }
  
  public int hashCode()
  {
    return a(this);
  }
  
  public boolean isInstanceInstalled()
  {
    return this.cq;
  }
  
  public boolean isPlayEnabledGame()
  {
    return this.cp;
  }
  
  public String toString()
  {
    return b(this);
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    int i = 1;
    paramParcel.writeString(this.ch);
    paramParcel.writeString(this.bp);
    paramParcel.writeString(this.ci);
    paramParcel.writeString(this.cj);
    paramParcel.writeString(this.ck);
    paramParcel.writeString(this.cl);
    String str1;
    String str2;
    label76:
    String str3;
    label96:
    int j;
    if (this.cm == null)
    {
      str1 = null;
      paramParcel.writeString(str1);
      if (this.cn != null) {
        break label175;
      }
      str2 = null;
      paramParcel.writeString(str2);
      Uri localUri = this.co;
      str3 = null;
      if (localUri != null) {
        break label187;
      }
      paramParcel.writeString(str3);
      if (!this.cp) {
        break label199;
      }
      j = i;
      label112:
      paramParcel.writeInt(j);
      if (!this.cq) {
        break label205;
      }
    }
    for (;;)
    {
      paramParcel.writeInt(i);
      paramParcel.writeString(this.cr);
      paramParcel.writeInt(this.cs);
      paramParcel.writeInt(this.ct);
      paramParcel.writeInt(this.cu);
      return;
      str1 = this.cm.toString();
      break;
      label175:
      str2 = this.cn.toString();
      break label76;
      label187:
      str3 = this.co.toString();
      break label96;
      label199:
      j = 0;
      break label112;
      label205:
      i = 0;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.games.GameEntity
 * JD-Core Version:    0.7.0.1
 */