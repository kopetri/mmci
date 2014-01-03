package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.maps.model.PolygonOptions;

public final class de
{
  public static void a(PolygonOptions paramPolygonOptions, Parcel paramParcel, int paramInt)
  {
    int i = ad.d(paramParcel);
    ad.c(paramParcel, 1, paramPolygonOptions.u());
    ad.b(paramParcel, 2, paramPolygonOptions.getPoints(), false);
    ad.c(paramParcel, 3, paramPolygonOptions.ba(), false);
    ad.a(paramParcel, 4, paramPolygonOptions.getStrokeWidth());
    ad.c(paramParcel, 5, paramPolygonOptions.getStrokeColor());
    ad.c(paramParcel, 6, paramPolygonOptions.getFillColor());
    ad.a(paramParcel, 7, paramPolygonOptions.getZIndex());
    ad.a(paramParcel, 8, paramPolygonOptions.isVisible());
    ad.a(paramParcel, 9, paramPolygonOptions.isGeodesic());
    ad.C(paramParcel, i);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.de
 * JD-Core Version:    0.7.0.1
 */