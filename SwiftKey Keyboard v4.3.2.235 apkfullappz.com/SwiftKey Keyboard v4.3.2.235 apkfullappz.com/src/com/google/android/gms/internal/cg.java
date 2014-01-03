package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;

public final class cg
  implements Parcelable.Creator<cf>
{
  static void a(cf paramcf, Parcel paramParcel, int paramInt)
  {
    int i = ad.d(paramParcel);
    ad.a(paramParcel, 1, paramcf.getRequestId(), false);
    ad.c(paramParcel, 1000, paramcf.u());
    ad.a(paramParcel, 2, paramcf.getExpirationTime());
    ad.a(paramParcel, 3, paramcf.aB());
    ad.a(paramParcel, 4, paramcf.getLatitude());
    ad.a(paramParcel, 5, paramcf.getLongitude());
    ad.a(paramParcel, 6, paramcf.aC());
    ad.c(paramParcel, 7, paramcf.aD());
    ad.C(paramParcel, i);
  }
  
  public cf[] N(int paramInt)
  {
    return new cf[paramInt];
  }
  
  public cf t(Parcel paramParcel)
  {
    double d1 = 0.0D;
    short s = 0;
    int i = ac.c(paramParcel);
    String str = null;
    float f = 0.0F;
    long l = 0L;
    double d2 = d1;
    int j = 0;
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
        str = ac.l(paramParcel, m);
        break;
      case 1000: 
        k = ac.f(paramParcel, m);
        break;
      case 2: 
        l = ac.g(paramParcel, m);
        break;
      case 3: 
        s = ac.e(paramParcel, m);
        break;
      case 4: 
        d2 = ac.j(paramParcel, m);
        break;
      case 5: 
        d1 = ac.j(paramParcel, m);
        break;
      case 6: 
        f = ac.i(paramParcel, m);
        break;
      case 7: 
        j = ac.f(paramParcel, m);
      }
    }
    if (paramParcel.dataPosition() != i) {
      throw new ac.a("Overread allowed size end=" + i, paramParcel);
    }
    return new cf(k, str, j, s, d2, d1, f, l);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.cg
 * JD-Core Version:    0.7.0.1
 */