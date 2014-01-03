package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public final class er
  implements Parcelable.Creator<eq>
{
  static void a(eq parameq, Parcel paramParcel, int paramInt)
  {
    int i = ad.d(paramParcel);
    Set localSet = parameq.bz();
    if (localSet.contains(Integer.valueOf(1))) {
      ad.c(paramParcel, 1, parameq.u());
    }
    if (localSet.contains(Integer.valueOf(2))) {
      ad.a(paramParcel, 2, parameq.getAboutMe(), true);
    }
    if (localSet.contains(Integer.valueOf(3))) {
      ad.a(paramParcel, 3, parameq.bU(), paramInt, true);
    }
    if (localSet.contains(Integer.valueOf(4))) {
      ad.a(paramParcel, 4, parameq.getBirthday(), true);
    }
    if (localSet.contains(Integer.valueOf(5))) {
      ad.a(paramParcel, 5, parameq.getBraggingRights(), true);
    }
    if (localSet.contains(Integer.valueOf(6))) {
      ad.c(paramParcel, 6, parameq.getCircledByCount());
    }
    if (localSet.contains(Integer.valueOf(7))) {
      ad.a(paramParcel, 7, parameq.bV(), paramInt, true);
    }
    if (localSet.contains(Integer.valueOf(8))) {
      ad.a(paramParcel, 8, parameq.getCurrentLocation(), true);
    }
    if (localSet.contains(Integer.valueOf(9))) {
      ad.a(paramParcel, 9, parameq.getDisplayName(), true);
    }
    if (localSet.contains(Integer.valueOf(10))) {
      ad.b(paramParcel, 10, parameq.bW(), true);
    }
    if (localSet.contains(Integer.valueOf(11))) {
      ad.a(paramParcel, 11, parameq.bX(), true);
    }
    if (localSet.contains(Integer.valueOf(12))) {
      ad.c(paramParcel, 12, parameq.getGender());
    }
    if (localSet.contains(Integer.valueOf(13))) {
      ad.a(paramParcel, 13, parameq.isHasApp());
    }
    if (localSet.contains(Integer.valueOf(14))) {
      ad.a(paramParcel, 14, parameq.getId(), true);
    }
    if (localSet.contains(Integer.valueOf(15))) {
      ad.a(paramParcel, 15, parameq.bY(), paramInt, true);
    }
    if (localSet.contains(Integer.valueOf(16))) {
      ad.a(paramParcel, 16, parameq.isPlusUser());
    }
    if (localSet.contains(Integer.valueOf(19))) {
      ad.a(paramParcel, 19, parameq.bZ(), paramInt, true);
    }
    if (localSet.contains(Integer.valueOf(18))) {
      ad.a(paramParcel, 18, parameq.getLanguage(), true);
    }
    if (localSet.contains(Integer.valueOf(21))) {
      ad.c(paramParcel, 21, parameq.getObjectType());
    }
    if (localSet.contains(Integer.valueOf(20))) {
      ad.a(paramParcel, 20, parameq.getNickname(), true);
    }
    if (localSet.contains(Integer.valueOf(23))) {
      ad.b(paramParcel, 23, parameq.cb(), true);
    }
    if (localSet.contains(Integer.valueOf(22))) {
      ad.b(paramParcel, 22, parameq.ca(), true);
    }
    if (localSet.contains(Integer.valueOf(25))) {
      ad.c(paramParcel, 25, parameq.getRelationshipStatus());
    }
    if (localSet.contains(Integer.valueOf(24))) {
      ad.c(paramParcel, 24, parameq.getPlusOneCount());
    }
    if (localSet.contains(Integer.valueOf(27))) {
      ad.a(paramParcel, 27, parameq.getUrl(), true);
    }
    if (localSet.contains(Integer.valueOf(26))) {
      ad.a(paramParcel, 26, parameq.getTagline(), true);
    }
    if (localSet.contains(Integer.valueOf(29))) {
      ad.a(paramParcel, 29, parameq.isVerified());
    }
    if (localSet.contains(Integer.valueOf(28))) {
      ad.b(paramParcel, 28, parameq.cc(), true);
    }
    ad.C(paramParcel, i);
  }
  
  public eq F(Parcel paramParcel)
  {
    int i = ac.c(paramParcel);
    HashSet localHashSet = new HashSet();
    int j = 0;
    String str1 = null;
    Object localObject1 = null;
    String str2 = null;
    String str3 = null;
    int k = 0;
    Object localObject2 = null;
    String str4 = null;
    String str5 = null;
    ArrayList localArrayList1 = null;
    String str6 = null;
    int m = 0;
    boolean bool1 = false;
    String str7 = null;
    Object localObject3 = null;
    boolean bool2 = false;
    String str8 = null;
    Object localObject4 = null;
    String str9 = null;
    int n = 0;
    ArrayList localArrayList2 = null;
    ArrayList localArrayList3 = null;
    int i1 = 0;
    int i2 = 0;
    String str10 = null;
    String str11 = null;
    ArrayList localArrayList4 = null;
    boolean bool3 = false;
    while (paramParcel.dataPosition() < i)
    {
      int i3 = ac.b(paramParcel);
      switch (ac.j(i3))
      {
      case 17: 
      default: 
        ac.b(paramParcel, i3);
        break;
      case 1: 
        j = ac.f(paramParcel, i3);
        localHashSet.add(Integer.valueOf(1));
        break;
      case 2: 
        str1 = ac.l(paramParcel, i3);
        localHashSet.add(Integer.valueOf(2));
        break;
      case 3: 
        eq.a locala = (eq.a)ac.a(paramParcel, i3, eq.a.CREATOR);
        localHashSet.add(Integer.valueOf(3));
        localObject1 = locala;
        break;
      case 4: 
        str2 = ac.l(paramParcel, i3);
        localHashSet.add(Integer.valueOf(4));
        break;
      case 5: 
        str3 = ac.l(paramParcel, i3);
        localHashSet.add(Integer.valueOf(5));
        break;
      case 6: 
        k = ac.f(paramParcel, i3);
        localHashSet.add(Integer.valueOf(6));
        break;
      case 7: 
        eq.b localb = (eq.b)ac.a(paramParcel, i3, eq.b.CREATOR);
        localHashSet.add(Integer.valueOf(7));
        localObject2 = localb;
        break;
      case 8: 
        str4 = ac.l(paramParcel, i3);
        localHashSet.add(Integer.valueOf(8));
        break;
      case 9: 
        str5 = ac.l(paramParcel, i3);
        localHashSet.add(Integer.valueOf(9));
        break;
      case 10: 
        localArrayList1 = ac.c(paramParcel, i3, eq.c.CREATOR);
        localHashSet.add(Integer.valueOf(10));
        break;
      case 11: 
        str6 = ac.l(paramParcel, i3);
        localHashSet.add(Integer.valueOf(11));
        break;
      case 12: 
        m = ac.f(paramParcel, i3);
        localHashSet.add(Integer.valueOf(12));
        break;
      case 13: 
        bool1 = ac.c(paramParcel, i3);
        localHashSet.add(Integer.valueOf(13));
        break;
      case 14: 
        str7 = ac.l(paramParcel, i3);
        localHashSet.add(Integer.valueOf(14));
        break;
      case 15: 
        eq.d locald = (eq.d)ac.a(paramParcel, i3, eq.d.CREATOR);
        localHashSet.add(Integer.valueOf(15));
        localObject3 = locald;
        break;
      case 16: 
        bool2 = ac.c(paramParcel, i3);
        localHashSet.add(Integer.valueOf(16));
        break;
      case 19: 
        eq.e locale = (eq.e)ac.a(paramParcel, i3, eq.e.CREATOR);
        localHashSet.add(Integer.valueOf(19));
        localObject4 = locale;
        break;
      case 18: 
        str8 = ac.l(paramParcel, i3);
        localHashSet.add(Integer.valueOf(18));
        break;
      case 21: 
        n = ac.f(paramParcel, i3);
        localHashSet.add(Integer.valueOf(21));
        break;
      case 20: 
        str9 = ac.l(paramParcel, i3);
        localHashSet.add(Integer.valueOf(20));
        break;
      case 23: 
        localArrayList3 = ac.c(paramParcel, i3, eq.h.CREATOR);
        localHashSet.add(Integer.valueOf(23));
        break;
      case 22: 
        localArrayList2 = ac.c(paramParcel, i3, eq.g.CREATOR);
        localHashSet.add(Integer.valueOf(22));
        break;
      case 25: 
        i2 = ac.f(paramParcel, i3);
        localHashSet.add(Integer.valueOf(25));
        break;
      case 24: 
        i1 = ac.f(paramParcel, i3);
        localHashSet.add(Integer.valueOf(24));
        break;
      case 27: 
        str11 = ac.l(paramParcel, i3);
        localHashSet.add(Integer.valueOf(27));
        break;
      case 26: 
        str10 = ac.l(paramParcel, i3);
        localHashSet.add(Integer.valueOf(26));
        break;
      case 29: 
        bool3 = ac.c(paramParcel, i3);
        localHashSet.add(Integer.valueOf(29));
        break;
      case 28: 
        localArrayList4 = ac.c(paramParcel, i3, eq.i.CREATOR);
        localHashSet.add(Integer.valueOf(28));
      }
    }
    if (paramParcel.dataPosition() != i) {
      throw new ac.a("Overread allowed size end=" + i, paramParcel);
    }
    return new eq(localHashSet, j, str1, localObject1, str2, str3, k, localObject2, str4, str5, localArrayList1, str6, m, bool1, str7, localObject3, bool2, str8, localObject4, str9, n, localArrayList2, localArrayList3, i1, i2, str10, str11, localArrayList4, bool3);
  }
  
  public eq[] aa(int paramInt)
  {
    return new eq[paramInt];
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.er
 * JD-Core Version:    0.7.0.1
 */