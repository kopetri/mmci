package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.maps.model.PolylineOptions;

public final class df
{
  public static void a(PolylineOptions paramPolylineOptions, Parcel paramParcel, int paramInt)
  {
    int i = ad.d(paramParcel);
    ad.c(paramParcel, 1, paramPolylineOptions.u());
    ad.b(paramParcel, 2, paramPolylineOptions.getPoints(), false);
    ad.a(paramParcel, 3, paramPolylineOptions.getWidth());
    ad.c(paramParcel, 4, paramPolylineOptions.getColor());
    ad.a(paramParcel, 5, paramPolylineOptions.getZIndex());
    ad.a(paramParcel, 6, paramPolylineOptions.isVisible());
    ad.a(paramParcel, 7, paramPolylineOptions.isGeodesic());
    ad.C(paramParcel, i);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.df
 * JD-Core Version:    0.7.0.1
 */