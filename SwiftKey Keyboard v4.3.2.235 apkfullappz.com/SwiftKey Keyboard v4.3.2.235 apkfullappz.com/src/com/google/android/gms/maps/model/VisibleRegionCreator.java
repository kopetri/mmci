package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.ac;
import com.google.android.gms.internal.ac.a;
import com.google.android.gms.internal.ad;

public final class VisibleRegionCreator
  implements Parcelable.Creator<VisibleRegion>
{
  static void a(VisibleRegion paramVisibleRegion, Parcel paramParcel, int paramInt)
  {
    int i = ad.d(paramParcel);
    ad.c(paramParcel, 1, paramVisibleRegion.u());
    ad.a(paramParcel, 2, paramVisibleRegion.nearLeft, paramInt, false);
    ad.a(paramParcel, 3, paramVisibleRegion.nearRight, paramInt, false);
    ad.a(paramParcel, 4, paramVisibleRegion.farLeft, paramInt, false);
    ad.a(paramParcel, 5, paramVisibleRegion.farRight, paramInt, false);
    ad.a(paramParcel, 6, paramVisibleRegion.latLngBounds, paramInt, false);
    ad.C(paramParcel, i);
  }
  
  public VisibleRegion createFromParcel(Parcel paramParcel)
  {
    LatLngBounds localLatLngBounds = null;
    int i = ac.c(paramParcel);
    int j = 0;
    LatLng localLatLng1 = null;
    LatLng localLatLng2 = null;
    LatLng localLatLng3 = null;
    LatLng localLatLng4 = null;
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
        localLatLng4 = (LatLng)ac.a(paramParcel, k, LatLng.CREATOR);
        break;
      case 3: 
        localLatLng3 = (LatLng)ac.a(paramParcel, k, LatLng.CREATOR);
        break;
      case 4: 
        localLatLng2 = (LatLng)ac.a(paramParcel, k, LatLng.CREATOR);
        break;
      case 5: 
        localLatLng1 = (LatLng)ac.a(paramParcel, k, LatLng.CREATOR);
        break;
      case 6: 
        localLatLngBounds = (LatLngBounds)ac.a(paramParcel, k, LatLngBounds.CREATOR);
      }
    }
    if (paramParcel.dataPosition() != i) {
      throw new ac.a("Overread allowed size end=" + i, paramParcel);
    }
    return new VisibleRegion(j, localLatLng4, localLatLng3, localLatLng2, localLatLng1, localLatLngBounds);
  }
  
  public VisibleRegion[] newArray(int paramInt)
  {
    return new VisibleRegion[paramInt];
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.VisibleRegionCreator
 * JD-Core Version:    0.7.0.1
 */