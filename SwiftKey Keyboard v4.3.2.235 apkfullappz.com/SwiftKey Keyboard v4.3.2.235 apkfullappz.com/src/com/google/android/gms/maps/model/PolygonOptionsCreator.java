package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.ac;
import com.google.android.gms.internal.ac.a;
import com.google.android.gms.internal.ad;
import java.util.ArrayList;

public final class PolygonOptionsCreator
  implements Parcelable.Creator<PolygonOptions>
{
  static void a(PolygonOptions paramPolygonOptions, Parcel paramParcel, int paramInt)
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
  
  public PolygonOptions createFromParcel(Parcel paramParcel)
  {
    float f1 = 0.0F;
    boolean bool1 = false;
    int i = ac.c(paramParcel);
    ArrayList localArrayList1 = null;
    ArrayList localArrayList2 = new ArrayList();
    boolean bool2 = false;
    int j = 0;
    int k = 0;
    float f2 = 0.0F;
    int m = 0;
    while (paramParcel.dataPosition() < i)
    {
      int n = ac.b(paramParcel);
      switch (ac.j(n))
      {
      default: 
        ac.b(paramParcel, n);
        break;
      case 1: 
        m = ac.f(paramParcel, n);
        break;
      case 2: 
        localArrayList1 = ac.c(paramParcel, n, LatLng.CREATOR);
        break;
      case 3: 
        ac.a(paramParcel, n, localArrayList2, getClass().getClassLoader());
        break;
      case 4: 
        f2 = ac.i(paramParcel, n);
        break;
      case 5: 
        k = ac.f(paramParcel, n);
        break;
      case 6: 
        j = ac.f(paramParcel, n);
        break;
      case 7: 
        f1 = ac.i(paramParcel, n);
        break;
      case 8: 
        bool2 = ac.c(paramParcel, n);
        break;
      case 9: 
        bool1 = ac.c(paramParcel, n);
      }
    }
    if (paramParcel.dataPosition() != i) {
      throw new ac.a("Overread allowed size end=" + i, paramParcel);
    }
    return new PolygonOptions(m, localArrayList1, localArrayList2, f2, k, j, f1, bool2, bool1);
  }
  
  public PolygonOptions[] newArray(int paramInt)
  {
    return new PolygonOptions[paramInt];
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.PolygonOptionsCreator
 * JD-Core Version:    0.7.0.1
 */