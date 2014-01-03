package com.google.android.gms.location;

import android.os.Parcel;
import com.google.android.gms.internal.ae;

public class DetectedActivity
  implements ae
{
  public static final DetectedActivityCreator CREATOR = new DetectedActivityCreator();
  int T = 1;
  int eu;
  int ev;
  
  private int H(int paramInt)
  {
    if (paramInt > 5) {
      paramInt = 4;
    }
    return paramInt;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public int getType()
  {
    return H(this.eu);
  }
  
  public String toString()
  {
    return "DetectedActivity [type=" + getType() + ", confidence=" + this.ev + "]";
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    DetectedActivityCreator.a(this, paramParcel, paramInt);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.DetectedActivity
 * JD-Core Version:    0.7.0.1
 */