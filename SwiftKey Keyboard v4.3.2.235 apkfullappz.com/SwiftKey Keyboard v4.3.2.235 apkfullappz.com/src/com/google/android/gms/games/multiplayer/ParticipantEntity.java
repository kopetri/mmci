package com.google.android.gms.games.multiplayer;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayerEntity;
import com.google.android.gms.internal.w;
import com.google.android.gms.internal.w.a;

public final class ParticipantEntity
  implements Parcelable, Participant
{
  public static final Parcelable.Creator<ParticipantEntity> CREATOR = new Parcelable.Creator()
  {
    public ParticipantEntity[] E(int paramAnonymousInt)
    {
      return new ParticipantEntity[paramAnonymousInt];
    }
    
    public ParticipantEntity q(Parcel paramAnonymousParcel)
    {
      boolean bool1 = true;
      String str1 = paramAnonymousParcel.readString();
      String str2 = paramAnonymousParcel.readString();
      String str3 = paramAnonymousParcel.readString();
      Uri localUri1;
      String str4;
      Uri localUri2;
      label41:
      int i;
      String str5;
      boolean bool2;
      if (str3 == null)
      {
        localUri1 = null;
        str4 = paramAnonymousParcel.readString();
        if (str4 != null) {
          break label122;
        }
        localUri2 = null;
        i = paramAnonymousParcel.readInt();
        str5 = paramAnonymousParcel.readString();
        if (paramAnonymousParcel.readInt() <= 0) {
          break label132;
        }
        bool2 = bool1;
        label63:
        if (paramAnonymousParcel.readInt() <= 0) {
          break label138;
        }
        label70:
        if (!bool1) {
          break label143;
        }
      }
      label132:
      label138:
      label143:
      for (PlayerEntity localPlayerEntity = (PlayerEntity)PlayerEntity.CREATOR.createFromParcel(paramAnonymousParcel);; localPlayerEntity = null)
      {
        return new ParticipantEntity(str1, str2, localUri1, localUri2, i, str5, bool2, localPlayerEntity, null);
        localUri1 = Uri.parse(str3);
        break;
        label122:
        localUri2 = Uri.parse(str4);
        break label41;
        bool2 = false;
        break label63;
        bool1 = false;
        break label70;
      }
    }
  };
  private final String bp;
  private final Uri cm;
  private final Uri cn;
  private final PlayerEntity dQ;
  private final int dR;
  private final String dS;
  private final boolean dT;
  private final String dq;
  
  public ParticipantEntity(Participant paramParticipant)
  {
    Player localPlayer = paramParticipant.getPlayer();
    if (localPlayer == null) {}
    for (PlayerEntity localPlayerEntity = null;; localPlayerEntity = new PlayerEntity(localPlayer))
    {
      this.dQ = localPlayerEntity;
      this.dq = paramParticipant.getParticipantId();
      this.bp = paramParticipant.getDisplayName();
      this.cm = paramParticipant.getIconImageUri();
      this.cn = paramParticipant.getHiResImageUri();
      this.dR = paramParticipant.getStatus();
      this.dS = paramParticipant.getClientAddress();
      this.dT = paramParticipant.isConnectedToRoom();
      return;
    }
  }
  
  private ParticipantEntity(String paramString1, String paramString2, Uri paramUri1, Uri paramUri2, int paramInt, String paramString3, boolean paramBoolean, PlayerEntity paramPlayerEntity)
  {
    this.dq = paramString1;
    this.bp = paramString2;
    this.cm = paramUri1;
    this.cn = paramUri2;
    this.dR = paramInt;
    this.dS = paramString3;
    this.dT = paramBoolean;
    this.dQ = paramPlayerEntity;
  }
  
  public static int a(Participant paramParticipant)
  {
    Object[] arrayOfObject = new Object[7];
    arrayOfObject[0] = paramParticipant.getPlayer();
    arrayOfObject[1] = Integer.valueOf(paramParticipant.getStatus());
    arrayOfObject[2] = paramParticipant.getClientAddress();
    arrayOfObject[3] = Boolean.valueOf(paramParticipant.isConnectedToRoom());
    arrayOfObject[4] = paramParticipant.getDisplayName();
    arrayOfObject[5] = paramParticipant.getIconImageUri();
    arrayOfObject[6] = paramParticipant.getHiResImageUri();
    return w.hashCode(arrayOfObject);
  }
  
  public static boolean a(Participant paramParticipant, Object paramObject)
  {
    if (!(paramObject instanceof Participant)) {}
    Participant localParticipant;
    do
    {
      return false;
      if (paramParticipant == paramObject) {
        return true;
      }
      localParticipant = (Participant)paramObject;
    } while ((!w.a(localParticipant.getPlayer(), paramParticipant.getPlayer())) || (!w.a(Integer.valueOf(localParticipant.getStatus()), Integer.valueOf(paramParticipant.getStatus()))) || (!w.a(localParticipant.getClientAddress(), paramParticipant.getClientAddress())) || (!w.a(Boolean.valueOf(localParticipant.isConnectedToRoom()), Boolean.valueOf(paramParticipant.isConnectedToRoom()))) || (!w.a(localParticipant.getDisplayName(), paramParticipant.getDisplayName())) || (!w.a(localParticipant.getIconImageUri(), paramParticipant.getIconImageUri())) || (!w.a(localParticipant.getHiResImageUri(), paramParticipant.getHiResImageUri())));
    return true;
  }
  
  public static String b(Participant paramParticipant)
  {
    return w.c(paramParticipant).a("Player", paramParticipant.getPlayer()).a("Status", Integer.valueOf(paramParticipant.getStatus())).a("ClientAddress", paramParticipant.getClientAddress()).a("ConnectedToRoom", Boolean.valueOf(paramParticipant.isConnectedToRoom())).a("DisplayName", paramParticipant.getDisplayName()).a("IconImage", paramParticipant.getIconImageUri()).a("HiResImage", paramParticipant.getHiResImageUri()).toString();
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public boolean equals(Object paramObject)
  {
    return a(this, paramObject);
  }
  
  public Participant freeze()
  {
    return this;
  }
  
  public String getClientAddress()
  {
    return this.dS;
  }
  
  public String getDisplayName()
  {
    if (this.dQ == null) {
      return this.bp;
    }
    return this.dQ.getDisplayName();
  }
  
  public Uri getHiResImageUri()
  {
    if (this.dQ == null) {
      return this.cn;
    }
    return this.dQ.getHiResImageUri();
  }
  
  public Uri getIconImageUri()
  {
    if (this.dQ == null) {
      return this.cm;
    }
    return this.dQ.getIconImageUri();
  }
  
  public String getParticipantId()
  {
    return this.dq;
  }
  
  public Player getPlayer()
  {
    return this.dQ;
  }
  
  public int getStatus()
  {
    return this.dR;
  }
  
  public int hashCode()
  {
    return a(this);
  }
  
  public boolean isConnectedToRoom()
  {
    return this.dT;
  }
  
  public String toString()
  {
    return b(this);
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(this.dq);
    paramParcel.writeString(this.bp);
    String str1;
    String str2;
    label44:
    int i;
    label76:
    int j;
    if (this.cm == null)
    {
      str1 = null;
      paramParcel.writeString(str1);
      Uri localUri = this.cn;
      str2 = null;
      if (localUri != null) {
        break label130;
      }
      paramParcel.writeString(str2);
      paramParcel.writeInt(this.dR);
      paramParcel.writeString(this.dS);
      if (!this.dT) {
        break label142;
      }
      i = 1;
      paramParcel.writeInt(i);
      PlayerEntity localPlayerEntity = this.dQ;
      j = 0;
      if (localPlayerEntity != null) {
        break label148;
      }
    }
    for (;;)
    {
      paramParcel.writeInt(j);
      if (this.dQ != null) {
        this.dQ.writeToParcel(paramParcel, paramInt);
      }
      return;
      str1 = this.cm.toString();
      break;
      label130:
      str2 = this.cn.toString();
      break label44;
      label142:
      i = 0;
      break label76;
      label148:
      j = 1;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.games.multiplayer.ParticipantEntity
 * JD-Core Version:    0.7.0.1
 */