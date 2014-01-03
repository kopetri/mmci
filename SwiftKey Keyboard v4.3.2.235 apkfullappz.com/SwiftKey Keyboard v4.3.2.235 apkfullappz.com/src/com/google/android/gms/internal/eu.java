package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import java.util.HashSet;
import java.util.Set;

public final class eu
  implements Parcelable.Creator<eq.i>
{
  static void a(eq.i parami, Parcel paramParcel, int paramInt)
  {
    int i = ad.d(paramParcel);
    Set localSet = parami.bz();
    if (localSet.contains(Integer.valueOf(1))) {
      ad.c(paramParcel, 1, parami.u());
    }
    if (localSet.contains(Integer.valueOf(2))) {
      ad.a(paramParcel, 2, parami.isPrimary());
    }
    if (localSet.contains(Integer.valueOf(3))) {
      ad.c(paramParcel, 3, parami.getType());
    }
    if (localSet.contains(Integer.valueOf(4))) {
      ad.a(paramParcel, 4, parami.getValue(), true);
    }
    ad.C(paramParcel, i);
  }
  
  public eq.i H(Parcel paramParcel)
  {
    int i = 0;
    int j = ac.c(paramParcel);
    HashSet localHashSet = new HashSet();
    String str = null;
    boolean bool = false;
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
        localHashSet.add(Integer.valueOf(1));
        break;
      case 2: 
        bool = ac.c(paramParcel, m);
        localHashSet.add(Integer.valueOf(2));
        break;
      case 3: 
        i = ac.f(paramParcel, m);
        localHashSet.add(Integer.valueOf(3));
        break;
      case 4: 
        str = ac.l(paramParcel, m);
        localHashSet.add(Integer.valueOf(4));
      }
    }
    if (paramParcel.dataPosition() != j) {
      throw new ac.a("Overread allowed size end=" + j, paramParcel);
    }
    return new eq.i(localHashSet, k, bool, i, str);
  }
  
  public eq.i[] ac(int paramInt)
  {
    return new eq.i[paramInt];
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.eu
 * JD-Core Version:    0.7.0.1
 */