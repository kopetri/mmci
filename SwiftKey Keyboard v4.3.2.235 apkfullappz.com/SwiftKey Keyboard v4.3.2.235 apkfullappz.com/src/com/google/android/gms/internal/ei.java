package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import java.util.HashSet;
import java.util.Set;

public final class ei
  implements Parcelable.Creator<eq.a>
{
  static void a(eq.a parama, Parcel paramParcel, int paramInt)
  {
    int i = ad.d(paramParcel);
    Set localSet = parama.bz();
    if (localSet.contains(Integer.valueOf(1))) {
      ad.c(paramParcel, 1, parama.u());
    }
    if (localSet.contains(Integer.valueOf(2))) {
      ad.c(paramParcel, 2, parama.getMax());
    }
    if (localSet.contains(Integer.valueOf(3))) {
      ad.c(paramParcel, 3, parama.getMin());
    }
    ad.C(paramParcel, i);
  }
  
  public eq.a[] S(int paramInt)
  {
    return new eq.a[paramInt];
  }
  
  public eq.a x(Parcel paramParcel)
  {
    int i = 0;
    int j = ac.c(paramParcel);
    HashSet localHashSet = new HashSet();
    int k = 0;
    int m = 0;
    while (paramParcel.dataPosition() < j)
    {
      int n = ac.b(paramParcel);
      switch (ac.j(n))
      {
      default: 
        ac.b(paramParcel, n);
        break;
      case 1: 
        m = ac.f(paramParcel, n);
        localHashSet.add(Integer.valueOf(1));
        break;
      case 2: 
        k = ac.f(paramParcel, n);
        localHashSet.add(Integer.valueOf(2));
        break;
      case 3: 
        i = ac.f(paramParcel, n);
        localHashSet.add(Integer.valueOf(3));
      }
    }
    if (paramParcel.dataPosition() != j) {
      throw new ac.a("Overread allowed size end=" + j, paramParcel);
    }
    return new eq.a(localHashSet, m, k, i);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.ei
 * JD-Core Version:    0.7.0.1
 */