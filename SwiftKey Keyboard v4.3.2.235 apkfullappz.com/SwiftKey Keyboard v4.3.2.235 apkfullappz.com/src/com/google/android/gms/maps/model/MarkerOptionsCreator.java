package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.ac;
import com.google.android.gms.internal.ac.a;
import com.google.android.gms.internal.ad;

public final class MarkerOptionsCreator
  implements Parcelable.Creator<MarkerOptions>
{
  static void a(MarkerOptions paramMarkerOptions, Parcel paramParcel, int paramInt)
  {
    int i = ad.d(paramParcel);
    ad.c(paramParcel, 1, paramMarkerOptions.u());
    ad.a(paramParcel, 2, paramMarkerOptions.getPosition(), paramInt, false);
    ad.a(paramParcel, 3, paramMarkerOptions.getTitle(), false);
    ad.a(paramParcel, 4, paramMarkerOptions.getSnippet(), false);
    ad.a(paramParcel, 5, paramMarkerOptions.aZ(), false);
    ad.a(paramParcel, 6, paramMarkerOptions.getAnchorU());
    ad.a(paramParcel, 7, paramMarkerOptions.getAnchorV());
    ad.a(paramParcel, 8, paramMarkerOptions.isDraggable());
    ad.a(paramParcel, 9, paramMarkerOptions.isVisible());
    ad.C(paramParcel, i);
  }
  
  public MarkerOptions createFromParcel(Parcel paramParcel)
  {
    float f1 = 0.0F;
    boolean bool1 = false;
    IBinder localIBinder = null;
    int i = ac.c(paramParcel);
    boolean bool2 = false;
    float f2 = 0.0F;
    String str1 = null;
    String str2 = null;
    LatLng localLatLng = null;
    int j = 0;
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
        localLatLng = (LatLng)ac.a(paramParcel, k, LatLng.CREATOR);
        break;
      case 3: 
        str2 = ac.l(paramParcel, k);
        break;
      case 4: 
        str1 = ac.l(paramParcel, k);
        break;
      case 5: 
        localIBinder = ac.m(paramParcel, k);
        break;
      case 6: 
        f2 = ac.i(paramParcel, k);
        break;
      case 7: 
        f1 = ac.i(paramParcel, k);
        break;
      case 8: 
        bool2 = ac.c(paramParcel, k);
        break;
      case 9: 
        bool1 = ac.c(paramParcel, k);
      }
    }
    if (paramParcel.dataPosition() != i) {
      throw new ac.a("Overread allowed size end=" + i, paramParcel);
    }
    return new MarkerOptions(j, localLatLng, str2, str1, localIBinder, f2, f1, bool2, bool1);
  }
  
  public MarkerOptions[] newArray(int paramInt)
  {
    return new MarkerOptions[paramInt];
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.MarkerOptionsCreator
 * JD-Core Version:    0.7.0.1
 */