package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.maps.GoogleMapOptions;

public final class ci
{
  public static void a(GoogleMapOptions paramGoogleMapOptions, Parcel paramParcel, int paramInt)
  {
    int i = ad.d(paramParcel);
    ad.c(paramParcel, 1, paramGoogleMapOptions.u());
    ad.a(paramParcel, 2, paramGoogleMapOptions.aH());
    ad.a(paramParcel, 3, paramGoogleMapOptions.aI());
    ad.c(paramParcel, 4, paramGoogleMapOptions.getMapType());
    ad.a(paramParcel, 5, paramGoogleMapOptions.getCamera(), paramInt, false);
    ad.a(paramParcel, 6, paramGoogleMapOptions.aJ());
    ad.a(paramParcel, 7, paramGoogleMapOptions.aK());
    ad.a(paramParcel, 8, paramGoogleMapOptions.aL());
    ad.a(paramParcel, 9, paramGoogleMapOptions.aM());
    ad.a(paramParcel, 10, paramGoogleMapOptions.aN());
    ad.a(paramParcel, 11, paramGoogleMapOptions.aO());
    ad.C(paramParcel, i);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.ci
 * JD-Core Version:    0.7.0.1
 */