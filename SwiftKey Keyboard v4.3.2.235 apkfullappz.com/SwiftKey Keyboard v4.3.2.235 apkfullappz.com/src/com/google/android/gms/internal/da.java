package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.maps.model.GroundOverlayOptions;

public final class da
{
  public static void a(GroundOverlayOptions paramGroundOverlayOptions, Parcel paramParcel, int paramInt)
  {
    int i = ad.d(paramParcel);
    ad.c(paramParcel, 1, paramGroundOverlayOptions.u());
    ad.a(paramParcel, 2, paramGroundOverlayOptions.aY(), false);
    ad.a(paramParcel, 3, paramGroundOverlayOptions.getLocation(), paramInt, false);
    ad.a(paramParcel, 4, paramGroundOverlayOptions.getWidth());
    ad.a(paramParcel, 5, paramGroundOverlayOptions.getHeight());
    ad.a(paramParcel, 6, paramGroundOverlayOptions.getBounds(), paramInt, false);
    ad.a(paramParcel, 7, paramGroundOverlayOptions.getBearing());
    ad.a(paramParcel, 8, paramGroundOverlayOptions.getZIndex());
    ad.a(paramParcel, 9, paramGroundOverlayOptions.isVisible());
    ad.a(paramParcel, 10, paramGroundOverlayOptions.getTransparency());
    ad.a(paramParcel, 11, paramGroundOverlayOptions.getAnchorU());
    ad.a(paramParcel, 12, paramGroundOverlayOptions.getAnchorV());
    ad.C(paramParcel, i);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.da
 * JD-Core Version:    0.7.0.1
 */