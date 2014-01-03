package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import com.google.android.gms.internal.ae;
import com.google.android.gms.internal.bc;
import com.google.android.gms.internal.bc.a;
import com.google.android.gms.internal.cx;
import com.google.android.gms.internal.da;

public final class GroundOverlayOptions
  implements ae
{
  public static final GroundOverlayOptionsCreator CREATOR = new GroundOverlayOptionsCreator();
  private final int T;
  private float fR;
  private float fY;
  private boolean fZ = true;
  private BitmapDescriptor gb;
  private LatLng gc;
  private float gd;
  private float ge;
  private LatLngBounds gf;
  private float gg = 0.0F;
  private float gh = 0.5F;
  private float gi = 0.5F;
  
  public GroundOverlayOptions()
  {
    this.T = 1;
  }
  
  GroundOverlayOptions(int paramInt, IBinder paramIBinder, LatLng paramLatLng, float paramFloat1, float paramFloat2, LatLngBounds paramLatLngBounds, float paramFloat3, float paramFloat4, boolean paramBoolean, float paramFloat5, float paramFloat6, float paramFloat7)
  {
    this.T = paramInt;
    this.gb = new BitmapDescriptor(bc.a.j(paramIBinder));
    this.gc = paramLatLng;
    this.gd = paramFloat1;
    this.ge = paramFloat2;
    this.gf = paramLatLngBounds;
    this.fR = paramFloat3;
    this.fY = paramFloat4;
    this.fZ = paramBoolean;
    this.gg = paramFloat5;
    this.gh = paramFloat6;
    this.gi = paramFloat7;
  }
  
  public IBinder aY()
  {
    return this.gb.aE().asBinder();
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public float getAnchorU()
  {
    return this.gh;
  }
  
  public float getAnchorV()
  {
    return this.gi;
  }
  
  public float getBearing()
  {
    return this.fR;
  }
  
  public LatLngBounds getBounds()
  {
    return this.gf;
  }
  
  public float getHeight()
  {
    return this.ge;
  }
  
  public LatLng getLocation()
  {
    return this.gc;
  }
  
  public float getTransparency()
  {
    return this.gg;
  }
  
  public float getWidth()
  {
    return this.gd;
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
      da.a(this, paramParcel, paramInt);
      return;
    }
    GroundOverlayOptionsCreator.a(this, paramParcel, paramInt);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.GroundOverlayOptions
 * JD-Core Version:    0.7.0.1
 */