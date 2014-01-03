package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import java.util.HashSet;
import java.util.Set;

public final class em
  implements Parcelable.Creator<eq.c>
{
  static void a(eq.c paramc, Parcel paramParcel, int paramInt)
  {
    int i = ad.d(paramParcel);
    Set localSet = paramc.bz();
    if (localSet.contains(Integer.valueOf(1))) {
      ad.c(paramParcel, 1, paramc.u());
    }
    if (localSet.contains(Integer.valueOf(2))) {
      ad.a(paramParcel, 2, paramc.isPrimary());
    }
    if (localSet.contains(Integer.valueOf(3))) {
      ad.c(paramParcel, 3, paramc.getType());
    }
    if (localSet.contains(Integer.valueOf(4))) {
      ad.a(paramParcel, 4, paramc.getValue(), true);
    }
    ad.C(paramParcel, i);
  }
  
  public eq.c B(Parcel paramParcel)
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
    return new eq.c(localHashSet, k, bool, i, str);
  }
  
  public eq.c[] W(int paramInt)
  {
    return new eq.c[paramInt];
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.em
 * JD-Core Version:    0.7.0.1
 */