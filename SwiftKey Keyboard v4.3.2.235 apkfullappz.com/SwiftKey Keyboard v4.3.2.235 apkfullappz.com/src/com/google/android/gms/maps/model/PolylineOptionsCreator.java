package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.ac;
import com.google.android.gms.internal.ac.a;
import com.google.android.gms.internal.ad;
import java.util.ArrayList;

public final class PolylineOptionsCreator
  implements Parcelable.Creator<PolylineOptions>
{
  static void a(PolylineOptions paramPolylineOptions, Parcel paramParcel, int paramInt)
  {
    int i = ad.d(paramParcel);
    ad.c(paramParcel, 1, paramPolylineOptions.u());
    ad.b(paramParcel, 2, paramPolylineOptions.getPoints(), false);
    ad.a(paramParcel, 3, paramPolylineOptions.getWidth());
    ad.c(paramParcel, 4, paramPolylineOptions.getColor());
    ad.a(paramParcel, 5, paramPolylineOptions.getZIndex());
    ad.a(paramParcel, 6, paramPolylineOptions.isVisible());
    ad.a(paramParcel, 7, paramPolylineOptions.isGeodesic());
    ad.C(paramParcel, i);
  }
  
  public PolylineOptions createFromParcel(Parcel paramParcel)
  {
    float f1 = 0.0F;
    boolean bool1 = false;
    int i = ac.c(paramParcel);
    ArrayList localArrayList = null;
    boolean bool2 = false;
    int j = 0;
    float f2 = 0.0F;
    int k = 0;
    while (paramParcel.dataPosition() < i)
    {
      int m = ac.b(paramParcel);
      switch (ac.j(m))
      {
      default: 
        ac.b(paramParcel, m);
        break;
      case 1: 
        k = ac.f(paramParcel, m);
        break;
      case 2: 
        localArrayList = ac.c(paramParcel, m, LatLng.CREATOR);
        break;
      case 3: 
        f2 = ac.i(paramParcel, m);
        break;
      case 4: 
        j = ac.f(paramParcel, m);
        break;
      case 5: 
        f1 = ac.i(paramParcel, m);
        break;
      case 6: 
        bool2 = ac.c(paramParcel, m);
        break;
      case 7: 
        bool1 = ac.c(paramParcel, m);
      }
    }
    if (paramParcel.dataPosition() != i) {
      throw new ac.a("Overread allowed size end=" + i, paramParcel);
    }
    return new PolylineOptions(k, localArrayList, f2, j, f1, bool2, bool1);
  }
  
  public PolylineOptions[] newArray(int paramInt)
  {
    return new PolylineOptions[paramInt];
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.PolylineOptionsCreator
 * JD-Core Version:    0.7.0.1
 */