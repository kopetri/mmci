package com.google.android.gms.games;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.n;
import com.google.android.gms.internal.w;
import com.google.android.gms.internal.w.a;

public final class PlayerEntity
  implements Player
{
  public static final Parcelable.Creator<PlayerEntity> CREATOR = new Parcelable.Creator()
  {
    public PlayerEntity o(Parcel paramAnonymousParcel)
    {
      String str1 = paramAnonymousParcel.readString();
      String str2 = paramAnonymousParcel.readString();
      String str3 = paramAnonymousParcel.readString();
      String str4 = paramAnonymousParcel.readString();
      Uri localUri1;
      if (str3 == null)
      {
        localUri1 = null;
        if (str4 != null) {
          break label67;
        }
      }
      label67:
      for (Uri localUri2 = null;; localUri2 = Uri.parse(str4))
      {
        return new PlayerEntity(str1, str2, localUri1, localUri2, paramAnonymousParcel.readLong(), null);
        localUri1 = Uri.parse(str3);
        break;
      }
    }
    
    public PlayerEntity[] w(int paramAnonymousInt)
    {
      return new PlayerEntity[paramAnonymousInt];
    }
  };
  private final String bp;
  private final long cA;
  private final Uri cm;
  private final Uri cn;
  private final String cz;
  
  public PlayerEntity(Player paramPlayer)
  {
    this.cz = paramPlayer.getPlayerId();
    this.bp = paramPlayer.getDisplayName();
    this.cm = paramPlayer.getIconImageUri();
    this.cn = paramPlayer.getHiResImageUri();
    this.cA = paramPlayer.getRetrievedTimestamp();
    n.b(this.cz);
    n.b(this.bp);
    if (this.cA > 0L) {}
    for (boolean bool = true;; bool = false)
    {
      n.a(bool);
      return;
    }
  }
  
  private PlayerEntity(String paramString1, String paramString2, Uri paramUri1, Uri paramUri2, long paramLong)
  {
    this.cz = paramString1;
    this.bp = paramString2;
    this.cm = paramUri1;
    this.cn = paramUri2;
    this.cA = paramLong;
  }
  
  public static int a(Player paramPlayer)
  {
    Object[] arrayOfObject = new Object[5];
    arrayOfObject[0] = paramPlayer.getPlayerId();
    arrayOfObject[1] = paramPlayer.getDisplayName();
    arrayOfObject[2] = paramPlayer.getIconImageUri();
    arrayOfObject[3] = paramPlayer.getHiResImageUri();
    arrayOfObject[4] = Long.valueOf(paramPlayer.getRetrievedTimestamp());
    return w.hashCode(arrayOfObject);
  }
  
  public static boolean a(Player paramPlayer, Object paramObject)
  {
    if (!(paramObject instanceof Player)) {}
    Player localPlayer;
    do
    {
      return false;
      if (paramPlayer == paramObject) {
        return true;
      }
      localPlayer = (Player)paramObject;
    } while ((!w.a(localPlayer.getPlayerId(), paramPlayer.getPlayerId())) || (!w.a(localPlayer.getDisplayName(), paramPlayer.getDisplayName())) || (!w.a(localPlayer.getIconImageUri(), paramPlayer.getIconImageUri())) || (!w.a(localPlayer.getHiResImageUri(), paramPlayer.getHiResImageUri())) || (!w.a(Long.valueOf(localPlayer.getRetrievedTimestamp()), Long.valueOf(paramPlayer.getRetrievedTimestamp()))));
    return true;
  }
  
  public static String b(Player paramPlayer)
  {
    return w.c(paramPlayer).a("PlayerId", paramPlayer.getPlayerId()).a("DisplayName", paramPlayer.getDisplayName()).a("IconImageUri", paramPlayer.getIconImageUri()).a("HiResImageUri", paramPlayer.getHiResImageUri()).a("RetrievedTimestamp", Long.valueOf(paramPlayer.getRetrievedTimestamp())).toString();
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public boolean equals(Object paramObject)
  {
    return a(this, paramObject);
  }
  
  public Player freeze()
  {
    return this;
  }
  
  public String getDisplayName()
  {
    return this.bp;
  }
  
  public Uri getHiResImageUri()
  {
    return this.cn;
  }
  
  public Uri getIconImageUri()
  {
    return this.cm;
  }
  
  public String getPlayerId()
  {
    return this.cz;
  }
  
  public long getRetrievedTimestamp()
  {
    return this.cA;
  }
  
  public int hashCode()
  {
    return a(this);
  }
  
  public String toString()
  {
    return b(this);
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(this.cz);
    paramParcel.writeString(this.bp);
    String str1;
    String str2;
    if (this.cm == null)
    {
      str1 = null;
      paramParcel.writeString(str1);
      Uri localUri = this.cn;
      str2 = null;
      if (localUri != null) {
        break label70;
      }
    }
    for (;;)
    {
      paramParcel.writeString(str2);
      paramParcel.writeLong(this.cA);
      return;
      str1 = this.cm.toString();
      break;
      label70:
      str2 = this.cn.toString();
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.games.PlayerEntity
 * JD-Core Version:    0.7.0.1
 */