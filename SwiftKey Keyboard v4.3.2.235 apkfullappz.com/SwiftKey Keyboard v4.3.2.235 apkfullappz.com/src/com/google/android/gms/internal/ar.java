package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import java.util.ArrayList;

public final class ar
  implements Parcelable.Creator<aq>
{
  static void a(aq paramaq, Parcel paramParcel, int paramInt)
  {
    int i = ad.d(paramParcel);
    ad.c(paramParcel, 1, paramaq.u());
    ad.b(paramParcel, 2, paramaq.V(), false);
    ad.a(paramParcel, 3, paramaq.W(), false);
    ad.C(paramParcel, i);
  }
  
  public aq k(Parcel paramParcel)
  {
    String str = null;
    int i = ac.c(paramParcel);
    int j = 0;
    ArrayList localArrayList = null;
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
        break;
      case 2: 
        localArrayList = ac.c(paramParcel, k, aq.a.CREATOR);
        break;
      case 3: 
        str = ac.l(paramParcel, k);
      }
    }
    if (paramParcel.dataPosition() != i) {
      throw new ac.a("Overread allowed size end=" + i, paramParcel);
    }
    return new aq(j, localArrayList, str);
  }
  
  public aq[] q(int paramInt)
  {
    return new aq[paramInt];
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.ar
 * JD-Core Version:    0.7.0.1
 */