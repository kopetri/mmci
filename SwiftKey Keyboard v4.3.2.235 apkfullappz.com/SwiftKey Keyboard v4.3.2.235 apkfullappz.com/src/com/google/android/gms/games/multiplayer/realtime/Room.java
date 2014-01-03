package com.google.android.gms.games.multiplayer.realtime;

import android.os.Bundle;
import android.os.Parcelable;
import com.google.android.gms.common.data.Freezable;
import com.google.android.gms.games.multiplayer.Participatable;

public abstract interface Room
  extends Parcelable, Freezable<Room>, Participatable
{
  public abstract Bundle getAutoMatchCriteria();
  
  public abstract long getCreationTimestamp();
  
  public abstract String getCreatorId();
  
  public abstract String getDescription();
  
  public abstract String getRoomId();
  
  public abstract int getStatus();
  
  public abstract int getVariant();
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.games.multiplayer.realtime.Room
 * JD-Core Version:    0.7.0.1
 */