package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.maps.model.LatLngBounds;

public final class db
{
  public static void a(LatLngBounds paramLatLngBounds, Parcel paramParcel, int paramInt)
  {
    int i = ad.d(paramParcel);
    ad.c(paramParcel, 1, paramLatLngBounds.u());
    ad.a(paramParcel, 2, paramLatLngBounds.southwest, paramInt, false);
    ad.a(paramParcel, 3, paramLatLngBounds.northeast, paramInt, false);
    ad.C(paramParcel, i);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.db
 * JD-Core Version:    0.7.0.1
 */