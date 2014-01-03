package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import java.util.ArrayList;

public final class ec
  implements Parcelable.Creator<eb>
{
  static void a(eb parameb, Parcel paramParcel, int paramInt)
  {
    int i = ad.d(paramParcel);
    ad.a(paramParcel, 1, parameb.getDescription(), false);
    ad.c(paramParcel, 1000, parameb.u());
    ad.b(paramParcel, 2, parameb.bw(), false);
    ad.b(paramParcel, 3, parameb.bx(), false);
    ad.a(paramParcel, 4, parameb.by());
    ad.C(paramParcel, i);
  }
  
  public eb[] P(int paramInt)
  {
    return new eb[paramInt];
  }
  
  public eb u(Parcel paramParcel)
  {
    boolean bool = false;
    ArrayList localArrayList1 = null;
    int i = ac.c(paramParcel);
    ArrayList localArrayList2 = null;
    String str = null;
    int j = 0;
    while (paramParcel.dataPosition() < i)
    {
      int k = ac.b(paramParcel);
      switch (ac.j(k))
      {
      default: 
        ac.b(paramParcel, k);
        break;
      case 1: 
        str = ac.l(paramParcel, k);
        break;
      case 1000: 
        j = ac.f(paramParcel, k);
        break;
      case 2: 
        localArrayList2 = ac.c(paramParcel, k, ag.CREATOR);
        break;
      case 3: 
        localArrayList1 = ac.c(paramParcel, k, ag.CREATOR);
        break;
      case 4: 
        bool = ac.c(paramParcel, k);
      }
    }
    if (paramParcel.dataPosition() != i) {
      throw new ac.a("Overread allowed size end=" + i, paramParcel);
    }
    return new eb(j, str, localArrayList2, localArrayList1, bool);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.ec
 * JD-Core Version:    0.7.0.1
 */