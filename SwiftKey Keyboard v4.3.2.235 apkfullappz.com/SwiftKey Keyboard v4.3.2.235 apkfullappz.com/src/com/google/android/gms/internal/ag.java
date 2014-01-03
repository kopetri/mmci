package com.google.android.gms.internal;

import android.os.Parcel;

public final class ag
  implements ae
{
  public static final ah CREATOR = new ah();
  private final int T;
  private final int bl;
  private final int bm;
  private final String bn;
  private final String bo;
  private final String bp;
  private final String bq;
  
  public ag(int paramInt1, int paramInt2, int paramInt3, String paramString1, String paramString2, String paramString3, String paramString4)
  {
    this.T = paramInt1;
    this.bl = paramInt2;
    this.bm = paramInt3;
    this.bn = paramString1;
    this.bo = paramString2;
    this.bp = paramString3;
    this.bq = paramString4;
  }
  
  public boolean A()
  {
    return this.bl == 2;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof ag)) {}
    ag localag;
    do
    {
      return false;
      localag = (ag)paramObject;
    } while ((this.T != localag.T) || (this.bl != localag.bl) || (this.bm != localag.bm) || (!w.a(this.bn, localag.bn)) || (!w.a(this.bo, localag.bo)));
    return true;
  }
  
  public String getDisplayName()
  {
    return this.bp;
  }
  
  public int getType()
  {
    return this.bl;
  }
  
  public int hashCode()
  {
    Object[] arrayOfObject = new Object[5];
    arrayOfObject[0] = Integer.valueOf(this.T);
    arrayOfObject[1] = Integer.valueOf(this.bl);
    arrayOfObject[2] = Integer.valueOf(this.bm);
    arrayOfObject[3] = this.bn;
    arrayOfObject[4] = this.bo;
    return w.hashCode(arrayOfObject);
  }
  
  public String toString()
  {
    if (A())
    {
      Object[] arrayOfObject3 = new Object[2];
      arrayOfObject3[0] = x();
      arrayOfObject3[1] = getDisplayName();
      return String.format("Person [%s] %s", arrayOfObject3);
    }
    if (z())
    {
      Object[] arrayOfObject2 = new Object[2];
      arrayOfObject2[0] = w();
      arrayOfObject2[1] = getDisplayName();
      return String.format("Circle [%s] %s", arrayOfObject2);
    }
    Object[] arrayOfObject1 = new Object[2];
    arrayOfObject1[0] = w();
    arrayOfObject1[1] = getDisplayName();
    return String.format("Group [%s] %s", arrayOfObject1);
  }
  
  public int u()
  {
    return this.T;
  }
  
  public int v()
  {
    return this.bm;
  }
  
  public String w()
  {
    return this.bn;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    ah.a(this, paramParcel, paramInt);
  }
  
  public String x()
  {
    return this.bo;
  }
  
  public String y()
  {
    return this.bq;
  }
  
  public boolean z()
  {
    return (this.bl == 1) && (this.bm == -1);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.ag
 * JD-Core Version:    0.7.0.1
 */