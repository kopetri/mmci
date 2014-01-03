package com.google.android.gms.internal;

import android.os.Parcel;

public class ai
  implements ae
{
  public static final aj CREATOR = new aj();
  private final int T;
  private final ak br;
  
  ai(int paramInt, ak paramak)
  {
    this.T = paramInt;
    this.br = paramak;
  }
  
  private ai(ak paramak)
  {
    this.T = 1;
    this.br = paramak;
  }
  
  public static ai a(an.b<?, ?> paramb)
  {
    if ((paramb instanceof ak)) {
      return new ai((ak)paramb);
    }
    throw new IllegalArgumentException("Unsupported safe parcelable field converter class.");
  }
  
  ak B()
  {
    return this.br;
  }
  
  public an.b<?, ?> C()
  {
    if (this.br != null) {
      return this.br;
    }
    throw new IllegalStateException("There was no converter wrapped in this ConverterWrapper.");
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  int u()
  {
    return this.T;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    aj.a(this, paramParcel, paramInt);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.ai
 * JD-Core Version:    0.7.0.1
 */