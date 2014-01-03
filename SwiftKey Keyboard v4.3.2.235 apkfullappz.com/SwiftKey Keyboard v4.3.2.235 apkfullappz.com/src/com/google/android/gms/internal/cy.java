package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.maps.model.CameraPosition;

public final class cy
{
  public static void a(CameraPosition paramCameraPosition, Parcel paramParcel, int paramInt)
  {
    int i = ad.d(paramParcel);
    ad.c(paramParcel, 1, paramCameraPosition.u());
    ad.a(paramParcel, 2, paramCameraPosition.target, paramInt, false);
    ad.a(paramParcel, 3, paramCameraPosition.zoom);
    ad.a(paramParcel, 4, paramCameraPosition.tilt);
    ad.a(paramParcel, 5, paramCameraPosition.bearing);
    ad.C(paramParcel, i);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.cy
 * JD-Core Version:    0.7.0.1
 */