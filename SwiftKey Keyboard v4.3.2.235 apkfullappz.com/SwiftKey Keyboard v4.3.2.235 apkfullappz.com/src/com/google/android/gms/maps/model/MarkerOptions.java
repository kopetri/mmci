package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import com.google.android.gms.internal.ae;
import com.google.android.gms.internal.bc;
import com.google.android.gms.internal.bc.a;
import com.google.android.gms.internal.cx;
import com.google.android.gms.internal.dd;

public final class MarkerOptions
  implements ae
{
  public static final MarkerOptionsCreator CREATOR = new MarkerOptionsCreator();
  private final int T;
  private boolean fZ = true;
  private float gh = 0.5F;
  private float gi = 1.0F;
  private LatLng go;
  private String gp;
  private String gq;
  private BitmapDescriptor gr;
  private boolean gs;
  
  public MarkerOptions()
  {
    this.T = 1;
  }
  
  MarkerOptions(int paramInt, LatLng paramLatLng, String paramString1, String paramString2, IBinder paramIBinder, float paramFloat1, float paramFloat2, boolean paramBoolean1, boolean paramBoolean2)
  {
    this.T = paramInt;
    this.go = paramLatLng;
    this.gp = paramString1;
    this.gq = paramString2;
    if (paramIBinder == null) {}
    for (BitmapDescriptor localBitmapDescriptor = null;; localBitmapDescriptor = new BitmapDescriptor(bc.a.j(paramIBinder)))
    {
      this.gr = localBitmapDescriptor;
      this.gh = paramFloat1;
      this.gi = paramFloat2;
      this.gs = paramBoolean1;
      this.fZ = paramBoolean2;
      return;
    }
  }
  
  public IBinder aZ()
  {
    if (this.gr == null) {
      return null;
    }
    return this.gr.aE().asBinder();
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
  
  public LatLng getPosition()
  {
    return this.go;
  }
  
  public String getSnippet()
  {
    return this.gq;
  }
  
  public String getTitle()
  {
    return this.gp;
  }
  
  public boolean isDraggable()
  {
    return this.gs;
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
      dd.a(this, paramParcel, paramInt);
      return;
    }
    MarkerOptionsCreator.a(this, paramParcel, paramInt);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.MarkerOptions
 * JD-Core Version:    0.7.0.1
 */