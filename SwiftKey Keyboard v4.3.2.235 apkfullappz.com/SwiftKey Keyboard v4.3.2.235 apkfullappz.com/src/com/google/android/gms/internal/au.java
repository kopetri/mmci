package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;

public final class au
  implements Parcelable.Creator<at>
{
  static void a(at paramat, Parcel paramParcel, int paramInt)
  {
    int i = ad.d(paramParcel);
    ad.c(paramParcel, 1, paramat.u());
    ad.a(paramParcel, 2, paramat.Y(), false);
    ad.a(paramParcel, 3, paramat.Z(), paramInt, false);
    ad.C(paramParcel, i);
  }
  
  public at m(Parcel paramParcel)
  {
    aq localaq = null;
    int i = ac.c(paramParcel);
    int j = 0;
    Parcel localParcel = null;
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
        localParcel = ac.y(paramParcel, k);
        break;
      case 3: 
        localaq = (aq)ac.a(paramParcel, k, aq.CREATOR);
      }
    }
    if (paramParcel.dataPosition() != i) {
      throw new ac.a("Overread allowed size end=" + i, paramParcel);
    }
    return new at(j, localParcel, localaq);
  }
  
  public at[] s(int paramInt)
  {
    return new at[paramInt];
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.au
 * JD-Core Version:    0.7.0.1
 */