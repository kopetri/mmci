package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.internal.ae;
import com.google.android.gms.internal.cx;
import com.google.android.gms.internal.df;
import java.util.ArrayList;
import java.util.List;

public final class PolylineOptions
  implements ae
{
  public static final PolylineOptionsCreator CREATOR = new PolylineOptionsCreator();
  private int L = -16777216;
  private final int T;
  private float fY = 0.0F;
  private boolean fZ = true;
  private float gd = 10.0F;
  private final List<LatLng> gu;
  private boolean gw = false;
  
  public PolylineOptions()
  {
    this.T = 1;
    this.gu = new ArrayList();
  }
  
  PolylineOptions(int paramInt1, List paramList, float paramFloat1, int paramInt2, float paramFloat2, boolean paramBoolean1, boolean paramBoolean2)
  {
    this.T = paramInt1;
    this.gu = paramList;
    this.gd = paramFloat1;
    this.L = paramInt2;
    this.fY = paramFloat2;
    this.fZ = paramBoolean1;
    this.gw = paramBoolean2;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public int getColor()
  {
    return this.L;
  }
  
  public List<LatLng> getPoints()
  {
    return this.gu;
  }
  
  public float getWidth()
  {
    return this.gd;
  }
  
  public float getZIndex()
  {
    return this.fY;
  }
  
  public boolean isGeodesic()
  {
    return this.gw;
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
      df.a(this, paramParcel, paramInt);
      return;
    }
    PolylineOptionsCreator.a(this, paramParcel, paramInt);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.PolylineOptions
 * JD-Core Version:    0.7.0.1
 */