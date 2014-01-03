package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.ac;
import com.google.android.gms.internal.ac.a;
import com.google.android.gms.internal.ad;

public final class DetectedActivityCreator
  implements Parcelable.Creator<DetectedActivity>
{
  static void a(DetectedActivity paramDetectedActivity, Parcel paramParcel, int paramInt)
  {
    int i = ad.d(paramParcel);
    ad.c(paramParcel, 1, paramDetectedActivity.eu);
    ad.c(paramParcel, 1000, paramDetectedActivity.T);
    ad.c(paramParcel, 2, paramDetectedActivity.ev);
    ad.C(paramParcel, i);
  }
  
  public DetectedActivity createFromParcel(Parcel paramParcel)
  {
    DetectedActivity localDetectedActivity = new DetectedActivity();
    int i = ac.c(paramParcel);
    while (paramParcel.dataPosition() < i)
    {
      int j = ac.b(paramParcel);
      switch (ac.j(j))
      {
      default: 
        ac.b(paramParcel, j);
        break;
      case 1: 
        localDetectedActivity.eu = ac.f(paramParcel, j);
        break;
      case 1000: 
        localDetectedActivity.T = ac.f(paramParcel, j);
        break;
      case 2: 
        localDetectedActivity.ev = ac.f(paramParcel, j);
      }
    }
    if (paramParcel.dataPosition() != i) {
      throw new ac.a("Overread allowed size end=" + i, paramParcel);
    }
    return localDetectedActivity;
  }
  
  public DetectedActivity[] newArray(int paramInt)
  {
    return new DetectedActivity[paramInt];
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.DetectedActivityCreator
 * JD-Core Version:    0.7.0.1
 */