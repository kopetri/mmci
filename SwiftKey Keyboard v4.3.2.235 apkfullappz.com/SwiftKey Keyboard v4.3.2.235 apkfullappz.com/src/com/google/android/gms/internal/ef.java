package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.plus.model.moments.Moment;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public final class ef
  extends an
  implements ae, Moment
{
  public static final eg CREATOR = new eg();
  private static final HashMap<String, an.a<?, ?>> hY;
  private final int T;
  private final Set<Integer> hZ;
  private String iD;
  private String iO;
  private String iU;
  private ed iX;
  private ed iY;
  
  static
  {
    HashMap localHashMap = new HashMap();
    hY = localHashMap;
    localHashMap.put("id", an.a.f("id", 2));
    hY.put("result", an.a.a("result", 4, ed.class));
    hY.put("startDate", an.a.f("startDate", 5));
    hY.put("target", an.a.a("target", 6, ed.class));
    hY.put("type", an.a.f("type", 7));
  }
  
  public ef()
  {
    this.T = 1;
    this.hZ = new HashSet();
  }
  
  ef(Set<Integer> paramSet, int paramInt, String paramString1, ed paramed1, String paramString2, ed paramed2, String paramString3)
  {
    this.hZ = paramSet;
    this.T = paramInt;
    this.iD = paramString1;
    this.iX = paramed1;
    this.iO = paramString2;
    this.iY = paramed2;
    this.iU = paramString3;
  }
  
  public HashMap<String, an.a<?, ?>> G()
  {
    return hY;
  }
  
  protected boolean a(an.a parama)
  {
    return this.hZ.contains(Integer.valueOf(parama.N()));
  }
  
  protected Object b(an.a parama)
  {
    switch (parama.N())
    {
    case 3: 
    default: 
      throw new IllegalStateException("Unknown safe parcelable id=" + parama.N());
    case 2: 
      return this.iD;
    case 4: 
      return this.iX;
    case 5: 
      return this.iO;
    case 6: 
      return this.iY;
    }
    return this.iU;
  }
  
  ed bQ()
  {
    return this.iX;
  }
  
  ed bR()
  {
    return this.iY;
  }
  
  public ef bS()
  {
    return this;
  }
  
  Set<Integer> bz()
  {
    return this.hZ;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof ef)) {
      return false;
    }
    if (this == paramObject) {
      return true;
    }
    ef localef = (ef)paramObject;
    Iterator localIterator = hY.values().iterator();
    while (localIterator.hasNext())
    {
      an.a locala = (an.a)localIterator.next();
      if (a(locala))
      {
        if (localef.a(locala))
        {
          if (!b(locala).equals(localef.b(locala))) {
            return false;
          }
        }
        else {
          return false;
        }
      }
      else if (localef.a(locala)) {
        return false;
      }
    }
    return true;
  }
  
  public String getId()
  {
    return this.iD;
  }
  
  public String getStartDate()
  {
    return this.iO;
  }
  
  public String getType()
  {
    return this.iU;
  }
  
  public int hashCode()
  {
    Iterator localIterator = hY.values().iterator();
    int i = 0;
    an.a locala;
    if (localIterator.hasNext())
    {
      locala = (an.a)localIterator.next();
      if (!a(locala)) {
        break label66;
      }
    }
    label66:
    for (int j = i + locala.N() + b(locala).hashCode();; j = i)
    {
      i = j;
      break;
      return i;
    }
  }
  
  protected Object j(String paramString)
  {
    return null;
  }
  
  protected boolean k(String paramString)
  {
    return false;
  }
  
  int u()
  {
    return this.T;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    eg.a(this, paramParcel, paramInt);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.ef
 * JD-Core Version:    0.7.0.1
 */