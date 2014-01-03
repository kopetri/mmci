package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;

public final class am
  implements Parcelable.Creator<ak.a>
{
  static void a(ak.a parama, Parcel paramParcel, int paramInt)
  {
    int i = ad.d(paramParcel);
    ad.c(paramParcel, 1, parama.versionCode);
    ad.a(paramParcel, 2, parama.bv, false);
    ad.c(paramParcel, 3, parama.bw);
    ad.C(paramParcel, i);
  }
  
  public ak.a h(Parcel paramParcel)
  {
    int i = 0;
    int j = ac.c(paramParcel);
    String str = null;
    int k = 0;
    while (paramParcel.dataPosition() < j)
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
        str = ac.l(paramParcel, m);
        break;
      case 3: 
        i = ac.f(paramParcel, m);
      }
    }
    if (paramParcel.dataPosition() != j) {
      throw new ac.a("Overread allowed size end=" + j, paramParcel);
    }
    return new ak.a(k, str, i);
  }
  
  public ak.a[] n(int paramInt)
  {
    return new ak.a[paramInt];
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.am
 * JD-Core Version:    0.7.0.1
 */