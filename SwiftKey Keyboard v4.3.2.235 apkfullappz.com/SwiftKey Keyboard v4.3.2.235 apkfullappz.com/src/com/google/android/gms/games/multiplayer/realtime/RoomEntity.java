package com.google.android.gms.games.multiplayer.realtime;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.games.multiplayer.Participant;
import com.google.android.gms.games.multiplayer.ParticipantEntity;
import com.google.android.gms.internal.w;
import com.google.android.gms.internal.w.a;
import java.util.ArrayList;

public final class RoomEntity
  implements Room
{
  public static final Parcelable.Creator<RoomEntity> CREATOR = new Parcelable.Creator()
  {
    public RoomEntity[] G(int paramAnonymousInt)
    {
      return new RoomEntity[paramAnonymousInt];
    }
    
    public RoomEntity s(Parcel paramAnonymousParcel)
    {
      String str1 = paramAnonymousParcel.readString();
      String str2 = paramAnonymousParcel.readString();
      long l = paramAnonymousParcel.readLong();
      int i = paramAnonymousParcel.readInt();
      String str3 = paramAnonymousParcel.readString();
      int j = paramAnonymousParcel.readInt();
      Bundle localBundle = paramAnonymousParcel.readBundle();
      int k = paramAnonymousParcel.readInt();
      ArrayList localArrayList = new ArrayList(k);
      for (int m = 0; m < k; m++) {
        localArrayList.add(ParticipantEntity.CREATOR.createFromParcel(paramAnonymousParcel));
      }
      return new RoomEntity(str1, str2, l, i, str3, j, localBundle, localArrayList, null);
    }
  };
  private final String cY;
  private final String ck;
  private final long dK;
  private final ArrayList<Participant> dN;
  private final int eb;
  private final Bundle ed;
  private final String eh;
  private final int ei;
  
  public RoomEntity(Room paramRoom)
  {
    this.cY = paramRoom.getRoomId();
    this.eh = paramRoom.getCreatorId();
    this.dK = paramRoom.getCreationTimestamp();
    this.ei = paramRoom.getStatus();
    this.ck = paramRoom.getDescription();
    this.eb = paramRoom.getVariant();
    this.ed = paramRoom.getAutoMatchCriteria();
    ArrayList localArrayList = paramRoom.getParticipants();
    int i = localArrayList.size();
    this.dN = new ArrayList(i);
    for (int j = 0; j < i; j++) {
      this.dN.add(((Participant)localArrayList.get(j)).freeze());
    }
  }
  
  private RoomEntity(String paramString1, String paramString2, long paramLong, int paramInt1, String paramString3, int paramInt2, Bundle paramBundle, ArrayList<Participant> paramArrayList)
  {
    this.cY = paramString1;
    this.eh = paramString2;
    this.dK = paramLong;
    this.ei = paramInt1;
    this.ck = paramString3;
    this.eb = paramInt2;
    this.ed = paramBundle;
    this.dN = paramArrayList;
  }
  
  public static int a(Room paramRoom)
  {
    Object[] arrayOfObject = new Object[8];
    arrayOfObject[0] = paramRoom.getRoomId();
    arrayOfObject[1] = paramRoom.getCreatorId();
    arrayOfObject[2] = Long.valueOf(paramRoom.getCreationTimestamp());
    arrayOfObject[3] = Integer.valueOf(paramRoom.getStatus());
    arrayOfObject[4] = paramRoom.getDescription();
    arrayOfObject[5] = Integer.valueOf(paramRoom.getVariant());
    arrayOfObject[6] = paramRoom.getAutoMatchCriteria();
    arrayOfObject[7] = paramRoom.getParticipants();
    return w.hashCode(arrayOfObject);
  }
  
  public static boolean a(Room paramRoom, Object paramObject)
  {
    if (!(paramObject instanceof Room)) {}
    Room localRoom;
    do
    {
      return false;
      if (paramRoom == paramObject) {
        return true;
      }
      localRoom = (Room)paramObject;
    } while ((!w.a(localRoom.getRoomId(), paramRoom.getRoomId())) || (!w.a(localRoom.getCreatorId(), paramRoom.getCreatorId())) || (!w.a(Long.valueOf(localRoom.getCreationTimestamp()), Long.valueOf(paramRoom.getCreationTimestamp()))) || (!w.a(Integer.valueOf(localRoom.getStatus()), Integer.valueOf(paramRoom.getStatus()))) || (!w.a(localRoom.getDescription(), paramRoom.getDescription())) || (!w.a(Integer.valueOf(localRoom.getVariant()), Integer.valueOf(paramRoom.getVariant()))) || (!w.a(localRoom.getAutoMatchCriteria(), paramRoom.getAutoMatchCriteria())) || (!w.a(localRoom.getParticipants(), paramRoom.getParticipants())));
    return true;
  }
  
  public static String b(Room paramRoom)
  {
    return w.c(paramRoom).a("RoomId", paramRoom.getRoomId()).a("CreatorId", paramRoom.getCreatorId()).a("CreationTimestamp", Long.valueOf(paramRoom.getCreationTimestamp())).a("RoomStatus", Integer.valueOf(paramRoom.getStatus())).a("Description", paramRoom.getDescription()).a("Variant", Integer.valueOf(paramRoom.getVariant())).a("AutoMatchCriteria", paramRoom.getAutoMatchCriteria()).a("Participants", paramRoom.getParticipants()).toString();
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public boolean equals(Object paramObject)
  {
    return a(this, paramObject);
  }
  
  public Room freeze()
  {
    return this;
  }
  
  public Bundle getAutoMatchCriteria()
  {
    return this.ed;
  }
  
  public long getCreationTimestamp()
  {
    return this.dK;
  }
  
  public String getCreatorId()
  {
    return this.eh;
  }
  
  public String getDescription()
  {
    return this.ck;
  }
  
  public ArrayList<Participant> getParticipants()
  {
    return this.dN;
  }
  
  public String getRoomId()
  {
    return this.cY;
  }
  
  public int getStatus()
  {
    return this.ei;
  }
  
  public int getVariant()
  {
    return this.eb;
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
    paramParcel.writeString(this.cY);
    paramParcel.writeString(this.eh);
    paramParcel.writeLong(this.dK);
    paramParcel.writeInt(this.ei);
    paramParcel.writeString(this.ck);
    paramParcel.writeInt(this.eb);
    paramParcel.writeBundle(this.ed);
    int i = this.dN.size();
    paramParcel.writeInt(i);
    for (int j = 0; j < i; j++) {
      ((Participant)this.dN.get(j)).writeToParcel(paramParcel, paramInt);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.games.multiplayer.realtime.RoomEntity
 * JD-Core Version:    0.7.0.1
 */