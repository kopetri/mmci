package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.ac;
import com.google.android.gms.internal.ac.a;
import com.google.android.gms.internal.ad;

public final class ActivityRecognitionResultCreator
  implements Parcelable.Creator<ActivityRecognitionResult>
{
  static void a(ActivityRecognitionResult paramActivityRecognitionResult, Parcel paramParcel, int paramInt)
  {
    int i = ad.d(paramParcel);
    ad.b(paramParcel, 1, paramActivityRecognitionResult.er, false);
    ad.c(paramParcel, 1000, paramActivityRecognitionResult.T);
    ad.a(paramParcel, 2, paramActivityRecognitionResult.es);
    ad.a(paramParcel, 3, paramActivityRecognitionResult.et);
    ad.C(paramParcel, i);
  }
  
  public ActivityRecognitionResult createFromParcel(Parcel paramParcel)
  {
    ActivityRecognitionResult localActivityRecognitionResult = new ActivityRecognitionResult();
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
        localActivityRecognitionResult.er = ac.c(paramParcel, j, DetectedActivity.CREATOR);
        break;
      case 1000: 
        localActivityRecognitionResult.T = ac.f(paramParcel, j);
        break;
      case 2: 
        localActivityRecognitionResult.es = ac.g(paramParcel, j);
        break;
      case 3: 
        localActivityRecognitionResult.et = ac.g(paramParcel, j);
      }
    }
    if (paramParcel.dataPosition() != i) {
      throw new ac.a("Overread allowed size end=" + i, paramParcel);
    }
    return localActivityRecognitionResult;
  }
  
  public ActivityRecognitionResult[] newArray(int paramInt)
  {
    return new ActivityRecognitionResult[paramInt];
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.ActivityRecognitionResultCreator
 * JD-Core Version:    0.7.0.1
 */