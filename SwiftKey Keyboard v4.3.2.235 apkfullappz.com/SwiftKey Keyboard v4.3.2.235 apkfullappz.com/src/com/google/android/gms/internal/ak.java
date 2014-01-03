package com.google.android.gms.internal;

import android.os.Parcel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public final class ak
  implements ae, an.b<String, Integer>
{
  public static final al CREATOR = new al();
  private final int T;
  private final HashMap<String, Integer> bs;
  private final HashMap<Integer, String> bt;
  private final ArrayList<a> bu;
  
  public ak()
  {
    this.T = 1;
    this.bs = new HashMap();
    this.bt = new HashMap();
    this.bu = null;
  }
  
  ak(int paramInt, ArrayList<a> paramArrayList)
  {
    this.T = paramInt;
    this.bs = new HashMap();
    this.bt = new HashMap();
    this.bu = null;
    a(paramArrayList);
  }
  
  private void a(ArrayList<a> paramArrayList)
  {
    Iterator localIterator = paramArrayList.iterator();
    while (localIterator.hasNext())
    {
      a locala = (a)localIterator.next();
      b(locala.bv, locala.bw);
    }
  }
  
  ArrayList<a> D()
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = this.bs.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      localArrayList.add(new a(str, ((Integer)this.bs.get(str)).intValue()));
    }
    return localArrayList;
  }
  
  public int E()
  {
    return 7;
  }
  
  public int F()
  {
    return 0;
  }
  
  public String a(Integer paramInteger)
  {
    String str = (String)this.bt.get(paramInteger);
    if ((str == null) && (this.bs.containsKey("gms_unknown"))) {
      str = "gms_unknown";
    }
    return str;
  }
  
  public ak b(String paramString, int paramInt)
  {
    this.bs.put(paramString, Integer.valueOf(paramInt));
    this.bt.put(Integer.valueOf(paramInt), paramString);
    return this;
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
    al.a(this, paramParcel, paramInt);
  }
  
  public static final class a
    implements ae
  {
    public static final am CREATOR = new am();
    final String bv;
    final int bw;
    final int versionCode;
    
    a(int paramInt1, String paramString, int paramInt2)
    {
      this.versionCode = paramInt1;
      this.bv = paramString;
      this.bw = paramInt2;
    }
    
    a(String paramString, int paramInt)
    {
      this.versionCode = 1;
      this.bv = paramString;
      this.bw = paramInt;
    }
    
    public int describeContents()
    {
      return 0;
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      am.a(this, paramParcel, paramInt);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.ak
 * JD-Core Version:    0.7.0.1
 */