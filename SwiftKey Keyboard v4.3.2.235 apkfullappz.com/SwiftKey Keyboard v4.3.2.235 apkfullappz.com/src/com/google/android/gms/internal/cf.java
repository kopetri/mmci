package com.google.android.gms.internal;

import android.os.Parcel;

public class cf
  implements ae
{
  public static final cg CREATOR = new cg();
  private final int T;
  private final double eA;
  private final double eB;
  private final float eC;
  private final long eU;
  private final String ew;
  private final int ex;
  private final short ez;
  
  public cf(int paramInt1, String paramString, int paramInt2, short paramShort, double paramDouble1, double paramDouble2, float paramFloat, long paramLong)
  {
    w(paramString);
    b(paramFloat);
    a(paramDouble1, paramDouble2);
    int i = L(paramInt2);
    this.T = paramInt1;
    this.ez = paramShort;
    this.ew = paramString;
    this.eA = paramDouble1;
    this.eB = paramDouble2;
    this.eC = paramFloat;
    this.eU = paramLong;
    this.ex = i;
  }
  
  private static int L(int paramInt)
  {
    int i = paramInt & 0x3;
    if (i == 0) {
      throw new IllegalArgumentException("No supported transition specified: " + paramInt);
    }
    return i;
  }
  
  private static String M(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return null;
    }
    return "CIRCLE";
  }
  
  private static void a(double paramDouble1, double paramDouble2)
  {
    if ((paramDouble1 > 90.0D) || (paramDouble1 < -90.0D)) {
      throw new IllegalArgumentException("invalid latitude: " + paramDouble1);
    }
    if ((paramDouble2 > 180.0D) || (paramDouble2 < -180.0D)) {
      throw new IllegalArgumentException("invalid longitude: " + paramDouble2);
    }
  }
  
  private static void b(float paramFloat)
  {
    if (paramFloat <= 0.0F) {
      throw new IllegalArgumentException("invalid radius: " + paramFloat);
    }
  }
  
  private static void w(String paramString)
  {
    if ((paramString == null) || (paramString.length() > 100)) {
      throw new IllegalArgumentException("requestId is null or too long: " + paramString);
    }
  }
  
  public short aB()
  {
    return this.ez;
  }
  
  public float aC()
  {
    return this.eC;
  }
  
  public int aD()
  {
    return this.ex;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    cf localcf;
    do
    {
      return true;
      if (paramObject == null) {
        return false;
      }
      if (!(paramObject instanceof cf)) {
        return false;
      }
      localcf = (cf)paramObject;
      if (this.eC != localcf.eC) {
        return false;
      }
      if (this.eA != localcf.eA) {
        return false;
      }
      if (this.eB != localcf.eB) {
        return false;
      }
    } while (this.ez == localcf.ez);
    return false;
  }
  
  public long getExpirationTime()
  {
    return this.eU;
  }
  
  public double getLatitude()
  {
    return this.eA;
  }
  
  public double getLongitude()
  {
    return this.eB;
  }
  
  public String getRequestId()
  {
    return this.ew;
  }
  
  public int hashCode()
  {
    long l1 = Double.doubleToLongBits(this.eA);
    int i = 31 + (int)(l1 ^ l1 >>> 32);
    long l2 = Double.doubleToLongBits(this.eB);
    return 31 * (31 * (31 * (i * 31 + (int)(l2 ^ l2 >>> 32)) + Float.floatToIntBits(this.eC)) + this.ez) + this.ex;
  }
  
  public String toString()
  {
    Object[] arrayOfObject = new Object[7];
    arrayOfObject[0] = M(this.ez);
    arrayOfObject[1] = this.ew;
    arrayOfObject[2] = Integer.valueOf(this.ex);
    arrayOfObject[3] = Double.valueOf(this.eA);
    arrayOfObject[4] = Double.valueOf(this.eB);
    arrayOfObject[5] = Float.valueOf(this.eC);
    arrayOfObject[6] = Long.valueOf(this.eU);
    return String.format("Geofence[%s id:%s transitions:%d %.6f, %.6f %.0fm, @%d]", arrayOfObject);
  }
  
  public int u()
  {
    return this.T;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    cg.a(this, paramParcel, paramInt);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.cf
 * JD-Core Version:    0.7.0.1
 */