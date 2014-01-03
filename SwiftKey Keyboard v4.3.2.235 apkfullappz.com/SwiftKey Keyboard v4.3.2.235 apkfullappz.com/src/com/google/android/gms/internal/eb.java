package com.google.android.gms.internal;

import android.os.Parcel;
import java.util.ArrayList;

public class eb
  implements ae
{
  public static final ec CREATOR = new ec();
  private final int T;
  private final String ck;
  private final ArrayList<ag> hV;
  private final ArrayList<ag> hW;
  private final boolean hX;
  
  public eb(int paramInt, String paramString, ArrayList<ag> paramArrayList1, ArrayList<ag> paramArrayList2, boolean paramBoolean)
  {
    this.T = paramInt;
    this.ck = paramString;
    this.hV = paramArrayList1;
    this.hW = paramArrayList2;
    this.hX = paramBoolean;
  }
  
  public ArrayList<ag> bw()
  {
    return this.hV;
  }
  
  public ArrayList<ag> bx()
  {
    return this.hW;
  }
  
  public boolean by()
  {
    return this.hX;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public String getDescription()
  {
    return this.ck;
  }
  
  public int u()
  {
    return this.T;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    ec.a(this, paramParcel, paramInt);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.eb
 * JD-Core Version:    0.7.0.1
 */