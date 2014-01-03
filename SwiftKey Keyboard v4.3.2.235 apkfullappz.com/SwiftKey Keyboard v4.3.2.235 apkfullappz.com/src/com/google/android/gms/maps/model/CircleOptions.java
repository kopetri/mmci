package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.internal.ae;
import com.google.android.gms.internal.cx;
import com.google.android.gms.internal.cz;

public final class CircleOptions
  implements ae
{
  public static final CircleOptionsCreator CREATOR = new CircleOptionsCreator();
  private final int T;
  private LatLng fT = null;
  private double fU = 0.0D;
  private float fV = 10.0F;
  private int fW = -16777216;
  private int fX = 0;
  private float fY = 0.0F;
  private boolean fZ = true;
  
  public CircleOptions()
  {
    this.T = 1;
  }
  
  CircleOptions(int paramInt1, LatLng paramLatLng, double paramDouble, float paramFloat1, int paramInt2, int paramInt3, float paramFloat2, boolean paramBoolean)
  {
    this.T = paramInt1;
    this.fT = paramLatLng;
    this.fU = paramDouble;
    this.fV = paramFloat1;
    this.fW = paramInt2;
    this.fX = paramInt3;
    this.fY = paramFloat2;
    this.fZ = paramBoolean;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public LatLng getCenter()
  {
    return this.fT;
  }
  
  public int getFillColor()
  {
    return this.fX;
  }
  
  public double getRadius()
  {
    return this.fU;
  }
  
  public int getStrokeColor()
  {
    return this.fW;
  }
  
  public float getStrokeWidth()
  {
    return this.fV;
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
      cz.a(this, paramParcel, paramInt);
      return;
    }
    CircleOptionsCreator.a(this, paramParcel, paramInt);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.CircleOptions
 * JD-Core Version:    0.7.0.1
 */