package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.ac;
import com.google.android.gms.internal.ac.a;
import com.google.android.gms.internal.ad;

public final class CircleOptionsCreator
  implements Parcelable.Creator<CircleOptions>
{
  static void a(CircleOptions paramCircleOptions, Parcel paramParcel, int paramInt)
  {
    int i = ad.d(paramParcel);
    ad.c(paramParcel, 1, paramCircleOptions.u());
    ad.a(paramParcel, 2, paramCircleOptions.getCenter(), paramInt, false);
    ad.a(paramParcel, 3, paramCircleOptions.getRadius());
    ad.a(paramParcel, 4, paramCircleOptions.getStrokeWidth());
    ad.c(paramParcel, 5, paramCircleOptions.getStrokeColor());
    ad.c(paramParcel, 6, paramCircleOptions.getFillColor());
    ad.a(paramParcel, 7, paramCircleOptions.getZIndex());
    ad.a(paramParcel, 8, paramCircleOptions.isVisible());
    ad.C(paramParcel, i);
  }
  
  public CircleOptions createFromParcel(Parcel paramParcel)
  {
    float f1 = 0.0F;
    boolean bool = false;
    int i = ac.c(paramParcel);
    LatLng localLatLng = null;
    double d = 0.0D;
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
        localLatLng = (LatLng)ac.a(paramParcel, n, LatLng.CREATOR);
        break;
      case 3: 
        d = ac.j(paramParcel, n);
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
        bool = ac.c(paramParcel, n);
      }
    }
    if (paramParcel.dataPosition() != i) {
      throw new ac.a("Overread allowed size end=" + i, paramParcel);
    }
    return new CircleOptions(m, localLatLng, d, f2, k, j, f1, bool);
  }
  
  public CircleOptions[] newArray(int paramInt)
  {
    return new CircleOptions[paramInt];
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.CircleOptionsCreator
 * JD-Core Version:    0.7.0.1
 */