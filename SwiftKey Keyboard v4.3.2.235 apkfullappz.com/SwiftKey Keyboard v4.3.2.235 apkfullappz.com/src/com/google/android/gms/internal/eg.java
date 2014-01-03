package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import java.util.HashSet;
import java.util.Set;

public final class eg
  implements Parcelable.Creator<ef>
{
  static void a(ef paramef, Parcel paramParcel, int paramInt)
  {
    int i = ad.d(paramParcel);
    Set localSet = paramef.bz();
    if (localSet.contains(Integer.valueOf(1))) {
      ad.c(paramParcel, 1, paramef.u());
    }
    if (localSet.contains(Integer.valueOf(2))) {
      ad.a(paramParcel, 2, paramef.getId(), true);
    }
    if (localSet.contains(Integer.valueOf(4))) {
      ad.a(paramParcel, 4, paramef.bQ(), paramInt, true);
    }
    if (localSet.contains(Integer.valueOf(5))) {
      ad.a(paramParcel, 5, paramef.getStartDate(), true);
    }
    if (localSet.contains(Integer.valueOf(6))) {
      ad.a(paramParcel, 6, paramef.bR(), paramInt, true);
    }
    if (localSet.contains(Integer.valueOf(7))) {
      ad.a(paramParcel, 7, paramef.getType(), true);
    }
    ad.C(paramParcel, i);
  }
  
  public ef[] R(int paramInt)
  {
    return new ef[paramInt];
  }
  
  public ef w(Parcel paramParcel)
  {
    String str1 = null;
    int i = ac.c(paramParcel);
    HashSet localHashSet = new HashSet();
    int j = 0;
    Object localObject1 = null;
    String str2 = null;
    Object localObject2 = null;
    String str3 = null;
    while (paramParcel.dataPosition() < i)
    {
      int k = ac.b(paramParcel);
      switch (ac.j(k))
      {
      case 3: 
      default: 
        ac.b(paramParcel, k);
        break;
      case 1: 
        j = ac.f(paramParcel, k);
        localHashSet.add(Integer.valueOf(1));
        break;
      case 2: 
        str3 = ac.l(paramParcel, k);
        localHashSet.add(Integer.valueOf(2));
        break;
      case 4: 
        ed localed2 = (ed)ac.a(paramParcel, k, ed.CREATOR);
        localHashSet.add(Integer.valueOf(4));
        localObject2 = localed2;
        break;
      case 5: 
        str2 = ac.l(paramParcel, k);
        localHashSet.add(Integer.valueOf(5));
        break;
      case 6: 
        ed localed1 = (ed)ac.a(paramParcel, k, ed.CREATOR);
        localHashSet.add(Integer.valueOf(6));
        localObject1 = localed1;
        break;
      case 7: 
        str1 = ac.l(paramParcel, k);
        localHashSet.add(Integer.valueOf(7));
      }
    }
    if (paramParcel.dataPosition() != i) {
      throw new ac.a("Overread allowed size end=" + i, paramParcel);
    }
    return new ef(localHashSet, j, str3, localObject2, str2, localObject1, str1);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.eg
 * JD-Core Version:    0.7.0.1
 */