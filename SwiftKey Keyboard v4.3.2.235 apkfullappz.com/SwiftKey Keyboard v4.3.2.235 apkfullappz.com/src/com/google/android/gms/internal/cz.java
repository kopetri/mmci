package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.maps.model.CircleOptions;

public final class cz
{
  public static void a(CircleOptions paramCircleOptions, Parcel paramParcel, int paramInt)
  {
    int i = ad.d(paramParcel);
    ad.c(paramParcel, 1, paramCircleOptions.u());
    ad.a(paramParcel, 2, paramCircleOptions.getCenter(), paramInt, false);
    ad.a(paramParcel, 3, paramCircleOptions.getRadius());
    ad.a(paramParcel, 4, paramCircleOptions.getStrokeWidth());
    ad.c(paramParcel, 5, paramCircleOptions.getStrokeColor());
    ad.c(paramParcel, 6, paramCircleOptions.getFillColor());
    ad.a(paramParcel, 7, paramCircleOptions.getZIndex());
    ad.a(paramParcel, 8, paramCircleOptions.isVisible());
    ad.C(paramParcel, i);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.cz
 * JD-Core Version:    0.7.0.1
 */