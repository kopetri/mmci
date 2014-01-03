package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import java.util.HashSet;
import java.util.Set;

public final class ep
  implements Parcelable.Creator<eq.g>
{
  static void a(eq.g paramg, Parcel paramParcel, int paramInt)
  {
    int i = ad.d(paramParcel);
    Set localSet = paramg.bz();
    if (localSet.contains(Integer.valueOf(1))) {
      ad.c(paramParcel, 1, paramg.u());
    }
    if (localSet.contains(Integer.valueOf(2))) {
      ad.a(paramParcel, 2, paramg.getDepartment(), true);
    }
    if (localSet.contains(Integer.valueOf(3))) {
      ad.a(paramParcel, 3, paramg.getDescription(), true);
    }
    if (localSet.contains(Integer.valueOf(4))) {
      ad.a(paramParcel, 4, paramg.getEndDate(), true);
    }
    if (localSet.contains(Integer.valueOf(5))) {
      ad.a(paramParcel, 5, paramg.getLocation(), true);
    }
    if (localSet.contains(Integer.valueOf(6))) {
      ad.a(paramParcel, 6, paramg.getName(), true);
    }
    if (localSet.contains(Integer.valueOf(7))) {
      ad.a(paramParcel, 7, paramg.isPrimary());
    }
    if (localSet.contains(Integer.valueOf(8))) {
      ad.a(paramParcel, 8, paramg.getStartDate(), true);
    }
    if (localSet.contains(Integer.valueOf(9))) {
      ad.a(paramParcel, 9, paramg.getTitle(), true);
    }
    if (localSet.contains(Integer.valueOf(10))) {
      ad.c(paramParcel, 10, paramg.getType());
    }
    ad.C(paramParcel, i);
  }
  
  public eq.g E(Parcel paramParcel)
  {
    int i = 0;
    String str1 = null;
    int j = ac.c(paramParcel);
    HashSet localHashSet = new HashSet();
    String str2 = null;
    boolean bool = false;
    String str3 = null;
    String str4 = null;
    String str5 = null;
    String str6 = null;
    String str7 = null;
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
        str7 = ac.l(paramParcel, m);
        localHashSet.add(Integer.valueOf(2));
        break;
      case 3: 
        str6 = ac.l(paramParcel, m);
        localHashSet.add(Integer.valueOf(3));
        break;
      case 4: 
        str5 = ac.l(paramParcel, m);
        localHashSet.add(Integer.valueOf(4));
        break;
      case 5: 
        str4 = ac.l(paramParcel, m);
        localHashSet.add(Integer.valueOf(5));
        break;
      case 6: 
        str3 = ac.l(paramParcel, m);
        localHashSet.add(Integer.valueOf(6));
        break;
      case 7: 
        bool = ac.c(paramParcel, m);
        localHashSet.add(Integer.valueOf(7));
        break;
      case 8: 
        str2 = ac.l(paramParcel, m);
        localHashSet.add(Integer.valueOf(8));
        break;
      case 9: 
        str1 = ac.l(paramParcel, m);
        localHashSet.add(Integer.valueOf(9));
        break;
      case 10: 
        i = ac.f(paramParcel, m);
        localHashSet.add(Integer.valueOf(10));
      }
    }
    if (paramParcel.dataPosition() != j) {
      throw new ac.a("Overread allowed size end=" + j, paramParcel);
    }
    return new eq.g(localHashSet, k, str7, str6, str5, str4, str3, bool, str2, str1, i);
  }
  
  public eq.g[] Z(int paramInt)
  {
    return new eq.g[paramInt];
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.ep
 * JD-Core Version:    0.7.0.1
 */