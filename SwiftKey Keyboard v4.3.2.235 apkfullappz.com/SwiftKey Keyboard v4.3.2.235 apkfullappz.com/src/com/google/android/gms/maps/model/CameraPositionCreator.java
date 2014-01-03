package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.ac;
import com.google.android.gms.internal.ac.a;
import com.google.android.gms.internal.ad;

public final class CameraPositionCreator
  implements Parcelable.Creator<CameraPosition>
{
  static void a(CameraPosition paramCameraPosition, Parcel paramParcel, int paramInt)
  {
    int i = ad.d(paramParcel);
    ad.c(paramParcel, 1, paramCameraPosition.u());
    ad.a(paramParcel, 2, paramCameraPosition.target, paramInt, false);
    ad.a(paramParcel, 3, paramCameraPosition.zoom);
    ad.a(paramParcel, 4, paramCameraPosition.tilt);
    ad.a(paramParcel, 5, paramCameraPosition.bearing);
    ad.C(paramParcel, i);
  }
  
  public CameraPosition createFromParcel(Parcel paramParcel)
  {
    float f1 = 0.0F;
    int i = ac.c(paramParcel);
    int j = 0;
    LatLng localLatLng = null;
    float f2 = 0.0F;
    float f3 = 0.0F;
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
        f3 = ac.i(paramParcel, k);
        break;
      case 4: 
        f2 = ac.i(paramParcel, k);
        break;
      case 5: 
        f1 = ac.i(paramParcel, k);
      }
    }
    if (paramParcel.dataPosition() != i) {
      throw new ac.a("Overread allowed size end=" + i, paramParcel);
    }
    return new CameraPosition(j, localLatLng, f3, f2, f1);
  }
  
  public CameraPosition[] newArray(int paramInt)
  {
    return new CameraPosition[paramInt];
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.CameraPositionCreator
 * JD-Core Version:    0.7.0.1
 */