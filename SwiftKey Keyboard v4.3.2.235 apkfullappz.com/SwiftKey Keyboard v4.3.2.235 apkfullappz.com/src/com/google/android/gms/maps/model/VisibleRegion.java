package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.internal.ae;
import com.google.android.gms.internal.cx;
import com.google.android.gms.internal.di;
import com.google.android.gms.internal.w;
import com.google.android.gms.internal.w.a;

public final class VisibleRegion
  implements ae
{
  public static final VisibleRegionCreator CREATOR = new VisibleRegionCreator();
  private final int T;
  public final LatLng farLeft;
  public final LatLng farRight;
  public final LatLngBounds latLngBounds;
  public final LatLng nearLeft;
  public final LatLng nearRight;
  
  VisibleRegion(int paramInt, LatLng paramLatLng1, LatLng paramLatLng2, LatLng paramLatLng3, LatLng paramLatLng4, LatLngBounds paramLatLngBounds)
  {
    this.T = paramInt;
    this.nearLeft = paramLatLng1;
    this.nearRight = paramLatLng2;
    this.farLeft = paramLatLng3;
    this.farRight = paramLatLng4;
    this.latLngBounds = paramLatLngBounds;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    VisibleRegion localVisibleRegion;
    do
    {
      return true;
      if (!(paramObject instanceof VisibleRegion)) {
        return false;
      }
      localVisibleRegion = (VisibleRegion)paramObject;
    } while ((this.nearLeft.equals(localVisibleRegion.nearLeft)) && (this.nearRight.equals(localVisibleRegion.nearRight)) && (this.farLeft.equals(localVisibleRegion.farLeft)) && (this.farRight.equals(localVisibleRegion.farRight)) && (this.latLngBounds.equals(localVisibleRegion.latLngBounds)));
    return false;
  }
  
  public int hashCode()
  {
    Object[] arrayOfObject = new Object[5];
    arrayOfObject[0] = this.nearLeft;
    arrayOfObject[1] = this.nearRight;
    arrayOfObject[2] = this.farLeft;
    arrayOfObject[3] = this.farRight;
    arrayOfObject[4] = this.latLngBounds;
    return w.hashCode(arrayOfObject);
  }
  
  public String toString()
  {
    return w.c(this).a("nearLeft", this.nearLeft).a("nearRight", this.nearRight).a("farLeft", this.farLeft).a("farRight", this.farRight).a("latLngBounds", this.latLngBounds).toString();
  }
  
  public int u()
  {
    return this.T;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    if (cx.aW())
    {
      di.a(this, paramParcel, paramInt);
      return;
    }
    VisibleRegionCreator.a(this, paramParcel, paramInt);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.VisibleRegion
 * JD-Core Version:    0.7.0.1
 */