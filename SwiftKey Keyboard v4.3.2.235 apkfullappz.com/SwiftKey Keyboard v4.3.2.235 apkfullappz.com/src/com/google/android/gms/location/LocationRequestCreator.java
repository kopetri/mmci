package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.ac;
import com.google.android.gms.internal.ac.a;
import com.google.android.gms.internal.ad;

public final class LocationRequestCreator
  implements Parcelable.Creator<LocationRequest>
{
  static void a(LocationRequest paramLocationRequest, Parcel paramParcel, int paramInt)
  {
    int i = ad.d(paramParcel);
    ad.c(paramParcel, 1, paramLocationRequest.mPriority);
    ad.c(paramParcel, 1000, paramLocationRequest.T);
    ad.a(paramParcel, 2, paramLocationRequest.eD);
    ad.a(paramParcel, 3, paramLocationRequest.eE);
    ad.a(paramParcel, 4, paramLocationRequest.eF);
    ad.a(paramParcel, 5, paramLocationRequest.ey);
    ad.c(paramParcel, 6, paramLocationRequest.eG);
    ad.a(paramParcel, 7, paramLocationRequest.eH);
    ad.C(paramParcel, i);
  }
  
  public LocationRequest createFromParcel(Parcel paramParcel)
  {
    LocationRequest localLocationRequest = new LocationRequest();
    int i = ac.c(paramParcel);
    while (paramParcel.dataPosition() < i)
    {
      int j = ac.b(paramParcel);
      switch (ac.j(j))
      {
      default: 
        ac.b(paramParcel, j);
        break;
      case 1: 
        localLocationRequest.mPriority = ac.f(paramParcel, j);
        break;
      case 1000: 
        localLocationRequest.T = ac.f(paramParcel, j);
        break;
      case 2: 
        localLocationRequest.eD = ac.g(paramParcel, j);
        break;
      case 3: 
        localLocationRequest.eE = ac.g(paramParcel, j);
        break;
      case 4: 
        localLocationRequest.eF = ac.c(paramParcel, j);
        break;
      case 5: 
        localLocationRequest.ey = ac.g(paramParcel, j);
        break;
      case 6: 
        localLocationRequest.eG = ac.f(paramParcel, j);
        break;
      case 7: 
        localLocationRequest.eH = ac.i(paramParcel, j);
      }
    }
    if (paramParcel.dataPosition() != i) {
      throw new ac.a("Overread allowed size end=" + i, paramParcel);
    }
    return localLocationRequest;
  }
  
  public LocationRequest[] newArray(int paramInt)
  {
    return new LocationRequest[paramInt];
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.LocationRequestCreator
 * JD-Core Version:    0.7.0.1
 */