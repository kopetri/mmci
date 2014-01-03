package com.google.android.gms.maps;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.ac;
import com.google.android.gms.internal.ac.a;
import com.google.android.gms.internal.ad;
import com.google.android.gms.maps.model.CameraPosition;

public final class GoogleMapOptionsCreator
  implements Parcelable.Creator<GoogleMapOptions>
{
  static void a(GoogleMapOptions paramGoogleMapOptions, Parcel paramParcel, int paramInt)
  {
    int i = ad.d(paramParcel);
    ad.c(paramParcel, 1, paramGoogleMapOptions.u());
    ad.a(paramParcel, 2, paramGoogleMapOptions.aH());
    ad.a(paramParcel, 3, paramGoogleMapOptions.aI());
    ad.c(paramParcel, 4, paramGoogleMapOptions.getMapType());
    ad.a(paramParcel, 5, paramGoogleMapOptions.getCamera(), paramInt, false);
    ad.a(paramParcel, 6, paramGoogleMapOptions.aJ());
    ad.a(paramParcel, 7, paramGoogleMapOptions.aK());
    ad.a(paramParcel, 8, paramGoogleMapOptions.aL());
    ad.a(paramParcel, 9, paramGoogleMapOptions.aM());
    ad.a(paramParcel, 10, paramGoogleMapOptions.aN());
    ad.a(paramParcel, 11, paramGoogleMapOptions.aO());
    ad.C(paramParcel, i);
  }
  
  public GoogleMapOptions createFromParcel(Parcel paramParcel)
  {
    byte b1 = 0;
    int i = ac.c(paramParcel);
    CameraPosition localCameraPosition = null;
    byte b2 = 0;
    byte b3 = 0;
    byte b4 = 0;
    byte b5 = 0;
    byte b6 = 0;
    int j = 0;
    byte b7 = 0;
    byte b8 = 0;
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
        b8 = ac.d(paramParcel, m);
        break;
      case 3: 
        b7 = ac.d(paramParcel, m);
        break;
      case 4: 
        j = ac.f(paramParcel, m);
        break;
      case 5: 
        localCameraPosition = (CameraPosition)ac.a(paramParcel, m, CameraPosition.CREATOR);
        break;
      case 6: 
        b6 = ac.d(paramParcel, m);
        break;
      case 7: 
        b5 = ac.d(paramParcel, m);
        break;
      case 8: 
        b4 = ac.d(paramParcel, m);
        break;
      case 9: 
        b3 = ac.d(paramParcel, m);
        break;
      case 10: 
        b2 = ac.d(paramParcel, m);
        break;
      case 11: 
        b1 = ac.d(paramParcel, m);
      }
    }
    if (paramParcel.dataPosition() != i) {
      throw new ac.a("Overread allowed size end=" + i, paramParcel);
    }
    return new GoogleMapOptions(k, b8, b7, j, localCameraPosition, b6, b5, b4, b3, b2, b1);
  }
  
  public GoogleMapOptions[] newArray(int paramInt)
  {
    return new GoogleMapOptions[paramInt];
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.GoogleMapOptionsCreator
 * JD-Core Version:    0.7.0.1
 */