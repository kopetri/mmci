package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.ac;
import com.google.android.gms.internal.ac.a;
import com.google.android.gms.internal.ad;

public final class LatLngBoundsCreator
  implements Parcelable.Creator<LatLngBounds>
{
  static void a(LatLngBounds paramLatLngBounds, Parcel paramParcel, int paramInt)
  {
    int i = ad.d(paramParcel);
    ad.c(paramParcel, 1, paramLatLngBounds.u());
    ad.a(paramParcel, 2, paramLatLngBounds.southwest, paramInt, false);
    ad.a(paramParcel, 3, paramLatLngBounds.northeast, paramInt, false);
    ad.C(paramParcel, i);
  }
  
  public LatLngBounds createFromParcel(Parcel paramParcel)
  {
    int i = ac.c(paramParcel);
    LatLng localLatLng1 = null;
    int j = 0;
    LatLng localLatLng2 = null;
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
        localLatLng1 = (LatLng)ac.a(paramParcel, k, LatLng.CREATOR);
        break;
      case 3: 
        localLatLng2 = (LatLng)ac.a(paramParcel, k, LatLng.CREATOR);
      }
    }
    if (paramParcel.dataPosition() != i) {
      throw new ac.a("Overread allowed size end=" + i, paramParcel);
    }
    return new LatLngBounds(j, localLatLng1, localLatLng2);
  }
  
  public LatLngBounds[] newArray(int paramInt)
  {
    return new LatLngBounds[paramInt];
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.LatLngBoundsCreator
 * JD-Core Version:    0.7.0.1
 */