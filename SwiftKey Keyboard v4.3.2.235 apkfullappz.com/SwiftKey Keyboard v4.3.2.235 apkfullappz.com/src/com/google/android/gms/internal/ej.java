package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import java.util.HashSet;
import java.util.Set;

public final class ej
  implements Parcelable.Creator<eq.b>
{
  static void a(eq.b paramb, Parcel paramParcel, int paramInt)
  {
    int i = ad.d(paramParcel);
    Set localSet = paramb.bz();
    if (localSet.contains(Integer.valueOf(1))) {
      ad.c(paramParcel, 1, paramb.u());
    }
    if (localSet.contains(Integer.valueOf(2))) {
      ad.a(paramParcel, 2, paramb.cf(), paramInt, true);
    }
    if (localSet.contains(Integer.valueOf(3))) {
      ad.a(paramParcel, 3, paramb.cg(), paramInt, true);
    }
    if (localSet.contains(Integer.valueOf(4))) {
      ad.c(paramParcel, 4, paramb.getLayout());
    }
    ad.C(paramParcel, i);
  }
  
  public eq.b[] T(int paramInt)
  {
    return new eq.b[paramInt];
  }
  
  public eq.b y(Parcel paramParcel)
  {
    Object localObject1 = null;
    int i = 0;
    int j = ac.c(paramParcel);
    HashSet localHashSet = new HashSet();
    Object localObject2 = null;
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
        eq.b.a locala = (eq.b.a)ac.a(paramParcel, m, eq.b.a.CREATOR);
        localHashSet.add(Integer.valueOf(2));
        localObject2 = locala;
        break;
      case 3: 
        eq.b.b localb = (eq.b.b)ac.a(paramParcel, m, eq.b.b.CREATOR);
        localHashSet.add(Integer.valueOf(3));
        localObject1 = localb;
        break;
      case 4: 
        i = ac.f(paramParcel, m);
        localHashSet.add(Integer.valueOf(4));
      }
    }
    if (paramParcel.dataPosition() != j) {
      throw new ac.a("Overread allowed size end=" + j, paramParcel);
    }
    return new eq.b(localHashSet, k, localObject2, localObject1, i);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.ej
 * JD-Core Version:    0.7.0.1
 */