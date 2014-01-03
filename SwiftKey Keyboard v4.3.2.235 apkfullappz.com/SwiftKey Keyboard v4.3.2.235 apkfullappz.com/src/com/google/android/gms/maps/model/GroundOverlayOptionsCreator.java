package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.ac;
import com.google.android.gms.internal.ac.a;
import com.google.android.gms.internal.ad;

public final class GroundOverlayOptionsCreator
  implements Parcelable.Creator<GroundOverlayOptions>
{
  static void a(GroundOverlayOptions paramGroundOverlayOptions, Parcel paramParcel, int paramInt)
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
  
  public GroundOverlayOptions createFromParcel(Parcel paramParcel)
  {
    int i = ac.c(paramParcel);
    int j = 0;
    IBinder localIBinder = null;
    LatLng localLatLng = null;
    float f1 = 0.0F;
    float f2 = 0.0F;
    LatLngBounds localLatLngBounds = null;
    float f3 = 0.0F;
    float f4 = 0.0F;
    boolean bool = false;
    float f5 = 0.0F;
    float f6 = 0.0F;
    float f7 = 0.0F;
    while (paramParcel.dataPosition() < i)
    {
      int k = ac.b(paramParcel);
      switch (ac.j(k))
      {
      default: 
        ac.b(paramParcel, k);
        break;
      case 1: 
        j = ac.f(paramParcel, k);
        break;
      case 2: 
        localIBinder = ac.m(paramParcel, k);
        break;
      case 3: 
        localLatLng = (LatLng)ac.a(paramParcel, k, LatLng.CREATOR);
        break;
      case 4: 
        f1 = ac.i(paramParcel, k);
        break;
      case 5: 
        f2 = ac.i(paramParcel, k);
        break;
      case 6: 
        localLatLngBounds = (LatLngBounds)ac.a(paramParcel, k, LatLngBounds.CREATOR);
        break;
      case 7: 
        f3 = ac.i(paramParcel, k);
        break;
      case 8: 
        f4 = ac.i(paramParcel, k);
        break;
      case 9: 
        bool = ac.c(paramParcel, k);
        break;
      case 10: 
        f5 = ac.i(paramParcel, k);
        break;
      case 11: 
        f6 = ac.i(paramParcel, k);
        break;
      case 12: 
        f7 = ac.i(paramParcel, k);
      }
    }
    if (paramParcel.dataPosition() != i) {
      throw new ac.a("Overread allowed size end=" + i, paramParcel);
    }
    return new GroundOverlayOptions(j, localIBinder, localLatLng, f1, f2, localLatLngBounds, f3, f4, bool, f5, f6, f7);
  }
  
  public GroundOverlayOptions[] newArray(int paramInt)
  {
    return new GroundOverlayOptions[paramInt];
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.GroundOverlayOptionsCreator
 * JD-Core Version:    0.7.0.1
 */