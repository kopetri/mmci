package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.maps.model.LatLng;

public final class dc
{
  public static void a(LatLng paramLatLng, Parcel paramParcel, int paramInt)
  {
    int i = ad.d(paramParcel);
    ad.c(paramParcel, 1, paramLatLng.u());
    ad.a(paramParcel, 2, paramLatLng.latitude);
    ad.a(paramParcel, 3, paramLatLng.longitude);
    ad.C(paramParcel, i);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.dc
 * JD-Core Version:    0.7.0.1
 */