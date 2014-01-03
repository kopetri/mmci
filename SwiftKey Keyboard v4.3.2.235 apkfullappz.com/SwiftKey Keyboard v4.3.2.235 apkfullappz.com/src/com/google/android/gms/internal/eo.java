package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import java.util.HashSet;
import java.util.Set;

public final class eo
  implements Parcelable.Creator<eq.e>
{
  static void a(eq.e parame, Parcel paramParcel, int paramInt)
  {
    int i = ad.d(paramParcel);
    Set localSet = parame.bz();
    if (localSet.contains(Integer.valueOf(1))) {
      ad.c(paramParcel, 1, parame.u());
    }
    if (localSet.contains(Integer.valueOf(2))) {
      ad.a(paramParcel, 2, parame.getFamilyName(), true);
    }
    if (localSet.contains(Integer.valueOf(3))) {
      ad.a(paramParcel, 3, parame.getFormatted(), true);
    }
    if (localSet.contains(Integer.valueOf(4))) {
      ad.a(paramParcel, 4, parame.getGivenName(), true);
    }
    if (localSet.contains(Integer.valueOf(5))) {
      ad.a(paramParcel, 5, parame.getHonorificPrefix(), true);
    }
    if (localSet.contains(Integer.valueOf(6))) {
      ad.a(paramParcel, 6, parame.getHonorificSuffix(), true);
    }
    if (localSet.contains(Integer.valueOf(7))) {
      ad.a(paramParcel, 7, parame.getMiddleName(), true);
    }
    ad.C(paramParcel, i);
  }
  
  public eq.e D(Parcel paramParcel)
  {
    String str1 = null;
    int i = ac.c(paramParcel);
    HashSet localHashSet = new HashSet();
    int j = 0;
    String str2 = null;
    String str3 = null;
    String str4 = null;
    String str5 = null;
    String str6 = null;
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
        localHashSet.add(Integer.valueOf(1));
        break;
      case 2: 
        str6 = ac.l(paramParcel, k);
        localHashSet.add(Integer.valueOf(2));
        break;
      case 3: 
        str5 = ac.l(paramParcel, k);
        localHashSet.add(Integer.valueOf(3));
        break;
      case 4: 
        str4 = ac.l(paramParcel, k);
        localHashSet.add(Integer.valueOf(4));
        break;
      case 5: 
        str3 = ac.l(paramParcel, k);
        localHashSet.add(Integer.valueOf(5));
        break;
      case 6: 
        str2 = ac.l(paramParcel, k);
        localHashSet.add(Integer.valueOf(6));
        break;
      case 7: 
        str1 = ac.l(paramParcel, k);
        localHashSet.add(Integer.valueOf(7));
      }
    }
    if (paramParcel.dataPosition() != i) {
      throw new ac.a("Overread allowed size end=" + i, paramParcel);
    }
    return new eq.e(localHashSet, j, str6, str5, str4, str3, str2, str1);
  }
  
  public eq.e[] Y(int paramInt)
  {
    return new eq.e[paramInt];
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.eo
 * JD-Core Version:    0.7.0.1
 */