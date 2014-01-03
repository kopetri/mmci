package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import com.google.android.gms.internal.ae;
import com.google.android.gms.internal.cx;
import com.google.android.gms.internal.dh;
import com.google.android.gms.internal.dp;
import com.google.android.gms.internal.dp.a;

public final class TileOverlayOptions
  implements ae
{
  public static final TileOverlayOptionsCreator CREATOR = new TileOverlayOptionsCreator();
  private final int T;
  private float fY;
  private boolean fZ = true;
  private TileProvider gA;
  private dp gz;
  
  public TileOverlayOptions()
  {
    this.T = 1;
  }
  
  TileOverlayOptions(int paramInt, IBinder paramIBinder, boolean paramBoolean, float paramFloat)
  {
    this.T = paramInt;
    this.gz = dp.a.Q(paramIBinder);
    if (this.gz == null) {}
    for (TileProvider local1 = null;; local1 = new TileProvider()
        {
          private final dp gB = TileOverlayOptions.a(TileOverlayOptions.this);
        })
    {
      this.gA = local1;
      this.fZ = paramBoolean;
      this.fY = paramFloat;
      return;
    }
  }
  
  public IBinder bb()
  {
    return this.gz.asBinder();
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public float getZIndex()
  {
    return this.fY;
  }
  
  public boolean isVisible()
  {
    return this.fZ;
  }
  
  public int u()
  {
    return this.T;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    if (cx.aW())
    {
      dh.a(this, paramParcel, paramInt);
      return;
    }
    TileOverlayOptionsCreator.a(this, paramParcel, paramInt);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.TileOverlayOptions
 * JD-Core Version:    0.7.0.1
 */