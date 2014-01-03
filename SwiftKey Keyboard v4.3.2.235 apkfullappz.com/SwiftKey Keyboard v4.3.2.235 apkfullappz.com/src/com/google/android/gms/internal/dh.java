package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.maps.model.TileOverlayOptions;

public final class dh
{
  public static void a(TileOverlayOptions paramTileOverlayOptions, Parcel paramParcel, int paramInt)
  {
    int i = ad.d(paramParcel);
    ad.c(paramParcel, 1, paramTileOverlayOptions.u());
    ad.a(paramParcel, 2, paramTileOverlayOptions.bb(), false);
    ad.a(paramParcel, 3, paramTileOverlayOptions.isVisible());
    ad.a(paramParcel, 4, paramTileOverlayOptions.getZIndex());
    ad.C(paramParcel, i);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.dh
 * JD-Core Version:    0.7.0.1
 */