package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;

public final class ao
  implements Parcelable.Creator<an.a>
{
  static void a(an.a parama, Parcel paramParcel, int paramInt)
  {
    int i = ad.d(paramParcel);
    ad.c(paramParcel, 1, parama.u());
    ad.c(paramParcel, 2, parama.E());
    ad.a(paramParcel, 3, parama.K());
    ad.c(paramParcel, 4, parama.F());
    ad.a(paramParcel, 5, parama.L());
    ad.a(paramParcel, 6, parama.M(), false);
    ad.c(paramParcel, 7, parama.N());
    ad.a(paramParcel, 8, parama.P(), false);
    ad.a(paramParcel, 9, parama.R(), paramInt, false);
    ad.C(paramParcel, i);
  }
  
  public an.a i(Parcel paramParcel)
  {
    ai localai = null;
    int i = 0;
    int j = ac.c(paramParcel);
    String str1 = null;
    String str2 = null;
    boolean bool1 = false;
    int k = 0;
    boolean bool2 = false;
    int m = 0;
    int n = 0;
    while (paramParcel.dataPosition() < j)
    {
      int i1 = ac.b(paramParcel);
      switch (ac.j(i1))
      {
      default: 
        ac.b(paramParcel, i1);
        break;
      case 1: 
        n = ac.f(paramParcel, i1);
        break;
      case 2: 
        m = ac.f(paramParcel, i1);
        break;
      case 3: 
        bool2 = ac.c(paramParcel, i1);
        break;
      case 4: 
        k = ac.f(paramParcel, i1);
        break;
      case 5: 
        bool1 = ac.c(paramParcel, i1);
        break;
      case 6: 
        str2 = ac.l(paramParcel, i1);
        break;
      case 7: 
        i = ac.f(paramParcel, i1);
        break;
      case 8: 
        str1 = ac.l(paramParcel, i1);
        break;
      case 9: 
        localai = (ai)ac.a(paramParcel, i1, ai.CREATOR);
      }
    }
    if (paramParcel.dataPosition() != j) {
      throw new ac.a("Overread allowed size end=" + j, paramParcel);
    }
    return new an.a(n, m, bool2, k, bool1, str2, i, str1, localai);
  }
  
  public an.a[] o(int paramInt)
  {
    return new an.a[paramInt];
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.ao
 * JD-Core Version:    0.7.0.1
 */