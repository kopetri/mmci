package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.maps.model.Tile;

public final class dg
{
  public static void a(Tile paramTile, Parcel paramParcel, int paramInt)
  {
    int i = ad.d(paramParcel);
    ad.c(paramParcel, 1, paramTile.u());
    ad.c(paramParcel, 2, paramTile.width);
    ad.c(paramParcel, 3, paramTile.height);
    ad.a(paramParcel, 4, paramTile.data, false);
    ad.C(paramParcel, i);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.dg
 * JD-Core Version:    0.7.0.1
 */