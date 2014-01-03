package com.google.android.gms.internal;

import android.net.Uri;
import android.os.Parcel;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.multiplayer.Participant;
import com.google.android.gms.games.multiplayer.ParticipantEntity;

public final class bx
  extends j
  implements Participant
{
  private final bg dU;
  
  public bx(k paramk, int paramInt)
  {
    super(paramk, paramInt);
    this.dU = new bg(paramk, paramInt);
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public boolean equals(Object paramObject)
  {
    return ParticipantEntity.a(this, paramObject);
  }
  
  public Participant freeze()
  {
    return new ParticipantEntity(this);
  }
  
  public String getClientAddress()
  {
    return getString("client_address");
  }
  
  public String getDisplayName()
  {
    if (d("external_player_id")) {
      return getString("default_display_name");
    }
    return this.dU.getDisplayName();
  }
  
  public Uri getHiResImageUri()
  {
    if (d("external_player_id")) {
      return null;
    }
    return this.dU.getHiResImageUri();
  }
  
  public Uri getIconImageUri()
  {
    if (d("external_player_id")) {
      return c("default_display_image_uri");
    }
    return this.dU.getIconImageUri();
  }
  
  public String getParticipantId()
  {
    return getString("external_participant_id");
  }
  
  public Player getPlayer()
  {
    if (d("external_player_id")) {
      return null;
    }
    return this.dU;
  }
  
  public int getStatus()
  {
    return getInteger("player_status");
  }
  
  public int hashCode()
  {
    return ParticipantEntity.a(this);
  }
  
  public boolean isConnectedToRoom()
  {
    return getInteger("connected") > 0;
  }
  
  public String toString()
  {
    return ParticipantEntity.b(this);
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    ((ParticipantEntity)freeze()).writeToParcel(paramParcel, paramInt);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.bx
 * JD-Core Version:    0.7.0.1
 */