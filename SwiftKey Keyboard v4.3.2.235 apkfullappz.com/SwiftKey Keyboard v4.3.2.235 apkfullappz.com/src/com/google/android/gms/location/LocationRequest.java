package com.google.android.gms.location;

import android.os.Parcel;
import android.os.SystemClock;
import com.google.android.gms.internal.ae;
import com.google.android.gms.internal.w;

public final class LocationRequest
  implements ae
{
  public static final LocationRequestCreator CREATOR = new LocationRequestCreator();
  int T;
  long eD = 3600000L;
  long eE = (this.eD / 6.0D);
  boolean eF = false;
  int eG = 2147483647;
  float eH = 0.0F;
  long ey = 9223372036854775807L;
  int mPriority = 102;
  
  public static String J(int paramInt)
  {
    switch (paramInt)
    {
    case 101: 
    case 103: 
    default: 
      return "???";
    case 100: 
      return "PRIORITY_HIGH_ACCURACY";
    case 102: 
      return "PRIORITY_BALANCED_POWER_ACCURACY";
    case 104: 
      return "PRIORITY_LOW_POWER";
    }
    return "PRIORITY_NO_POWER";
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    LocationRequest localLocationRequest;
    do
    {
      return true;
      if (!(paramObject instanceof LocationRequest)) {
        return false;
      }
      localLocationRequest = (LocationRequest)paramObject;
    } while ((this.mPriority == localLocationRequest.mPriority) && (this.eD == localLocationRequest.eD) && (this.eE == localLocationRequest.eE) && (this.eF == localLocationRequest.eF) && (this.ey == localLocationRequest.ey) && (this.eG == localLocationRequest.eG) && (this.eH == localLocationRequest.eH));
    return false;
  }
  
  public int hashCode()
  {
    Object[] arrayOfObject = new Object[7];
    arrayOfObject[0] = Integer.valueOf(this.mPriority);
    arrayOfObject[1] = Long.valueOf(this.eD);
    arrayOfObject[2] = Long.valueOf(this.eE);
    arrayOfObject[3] = Boolean.valueOf(this.eF);
    arrayOfObject[4] = Long.valueOf(this.ey);
    arrayOfObject[5] = Integer.valueOf(this.eG);
    arrayOfObject[6] = Float.valueOf(this.eH);
    return w.hashCode(arrayOfObject);
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Request[").append(J(this.mPriority));
    if (this.mPriority != 105)
    {
      localStringBuilder.append(" requested=");
      localStringBuilder.append(this.eD + "ms");
    }
    localStringBuilder.append(" fastest=");
    localStringBuilder.append(this.eE + "ms");
    if (this.ey != 9223372036854775807L)
    {
      long l = this.ey - SystemClock.elapsedRealtime();
      localStringBuilder.append(" expireIn=");
      localStringBuilder.append(l + "ms");
    }
    if (this.eG != 2147483647) {
      localStringBuilder.append(" num=").append(this.eG);
    }
    localStringBuilder.append(']');
    return localStringBuilder.toString();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    LocationRequestCreator.a(this, paramParcel, paramInt);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.LocationRequest
 * JD-Core Version:    0.7.0.1
 */