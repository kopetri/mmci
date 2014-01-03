package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.internal.ae;
import com.google.android.gms.internal.cx;
import com.google.android.gms.internal.de;
import java.util.ArrayList;
import java.util.List;

public final class PolygonOptions
  implements ae
{
  public static final PolygonOptionsCreator CREATOR = new PolygonOptionsCreator();
  private final int T;
  private float fV = 10.0F;
  private int fW = -16777216;
  private int fX = 0;
  private float fY = 0.0F;
  private boolean fZ = true;
  private final List<LatLng> gu;
  private final List<List<LatLng>> gv;
  private boolean gw = false;
  
  public PolygonOptions()
  {
    this.T = 1;
    this.gu = new ArrayList();
    this.gv = new ArrayList();
  }
  
  PolygonOptions(int paramInt1, List<LatLng> paramList, List paramList1, float paramFloat1, int paramInt2, int paramInt3, float paramFloat2, boolean paramBoolean1, boolean paramBoolean2)
  {
    this.T = paramInt1;
    this.gu = paramList;
    this.gv = paramList1;
    this.fV = paramFloat1;
    this.fW = paramInt2;
    this.fX = paramInt3;
    this.fY = paramFloat2;
    this.fZ = paramBoolean1;
    this.gw = paramBoolean2;
  }
  
  public List ba()
  {
    return this.gv;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public int getFillColor()
  {
    return this.fX;
  }
  
  public List<LatLng> getPoints()
  {
    return this.gu;
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
      de.a(this, paramParcel, paramInt);
      return;
    }
    PolygonOptionsCreator.a(this, paramParcel, paramInt);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.PolygonOptions
 * JD-Core Version:    0.7.0.1
 */