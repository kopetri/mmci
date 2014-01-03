package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;

public final class ah
  implements Parcelable.Creator<ag>
{
  static void a(ag paramag, Parcel paramParcel, int paramInt)
  {
    int i = ad.d(paramParcel);
    ad.c(paramParcel, 1, paramag.getType());
    ad.c(paramParcel, 1000, paramag.u());
    ad.c(paramParcel, 2, paramag.v());
    ad.a(paramParcel, 3, paramag.w(), false);
    ad.a(paramParcel, 4, paramag.x(), false);
    ad.a(paramParcel, 5, paramag.getDisplayName(), false);
    ad.a(paramParcel, 6, paramag.y(), false);
    ad.C(paramParcel, i);
  }
  
  public ag e(Parcel paramParcel)
  {
    int i = 0;
    String str1 = null;
    int j = ac.c(paramParcel);
    String str2 = null;
    String str3 = null;
    String str4 = null;
    int k = 0;
    int m = 0;
    while (paramParcel.dataPosition() < j)
    {
      int n = ac.b(paramParcel);
      switch (ac.j(n))
      {
      default: 
        ac.b(paramParcel, n);
        break;
      case 1: 
        k = ac.f(paramParcel, n);
        break;
      case 1000: 
        m = ac.f(paramParcel, n);
        break;
      case 2: 
        i = ac.f(paramParcel, n);
        break;
      case 3: 
        str4 = ac.l(paramParcel, n);
        break;
      case 4: 
        str3 = ac.l(paramParcel, n);
        break;
      case 5: 
        str2 = ac.l(paramParcel, n);
        break;
      case 6: 
        str1 = ac.l(paramParcel, n);
      }
    }
    if (paramParcel.dataPosition() != j) {
      throw new ac.a("Overread allowed size end=" + j, paramParcel);
    }
    return new ag(m, k, i, str4, str3, str2, str1);
  }
  
  public ag[] k(int paramInt)
  {
    return new ag[paramInt];
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.ah
 * JD-Core Version:    0.7.0.1
 */