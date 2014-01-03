package com.google.android.gms.internal;

import android.os.Parcel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class aq
  implements ae
{
  public static final ar CREATOR = new ar();
  private final int T;
  private final HashMap<String, HashMap<String, an.a<?, ?>>> bH;
  private final ArrayList<a> bI;
  private final String bJ;
  
  aq(int paramInt, ArrayList<a> paramArrayList, String paramString)
  {
    this.T = paramInt;
    this.bI = null;
    this.bH = b(paramArrayList);
    this.bJ = ((String)x.d(paramString));
    T();
  }
  
  private static HashMap<String, HashMap<String, an.a<?, ?>>> b(ArrayList<a> paramArrayList)
  {
    HashMap localHashMap = new HashMap();
    int i = paramArrayList.size();
    for (int j = 0; j < i; j++)
    {
      a locala = (a)paramArrayList.get(j);
      localHashMap.put(locala.className, locala.X());
    }
    return localHashMap;
  }
  
  public void T()
  {
    Iterator localIterator1 = this.bH.keySet().iterator();
    while (localIterator1.hasNext())
    {
      String str = (String)localIterator1.next();
      HashMap localHashMap = (HashMap)this.bH.get(str);
      Iterator localIterator2 = localHashMap.keySet().iterator();
      while (localIterator2.hasNext()) {
        ((an.a)localHashMap.get((String)localIterator2.next())).a(this);
      }
    }
  }
  
  ArrayList<a> V()
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = this.bH.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      localArrayList.add(new a(str, (HashMap)this.bH.get(str)));
    }
    return localArrayList;
  }
  
  public String W()
  {
    return this.bJ;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public HashMap<String, an.a<?, ?>> n(String paramString)
  {
    return (HashMap)this.bH.get(paramString);
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    Iterator localIterator1 = this.bH.keySet().iterator();
    while (localIterator1.hasNext())
    {
      String str1 = (String)localIterator1.next();
      localStringBuilder.append(str1).append(":\n");
      HashMap localHashMap = (HashMap)this.bH.get(str1);
      Iterator localIterator2 = localHashMap.keySet().iterator();
      while (localIterator2.hasNext())
      {
        String str2 = (String)localIterator2.next();
        localStringBuilder.append("  ").append(str2).append(": ");
        localStringBuilder.append(localHashMap.get(str2));
      }
    }
    return localStringBuilder.toString();
  }
  
  int u()
  {
    return this.T;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    ar.a(this, paramParcel, paramInt);
  }
  
  public static class a
    implements ae
  {
    public static final as CREATOR = new as();
    final ArrayList<aq.b> bK;
    final String className;
    final int versionCode;
    
    a(int paramInt, String paramString, ArrayList<aq.b> paramArrayList)
    {
      this.versionCode = paramInt;
      this.className = paramString;
      this.bK = paramArrayList;
    }
    
    a(String paramString, HashMap<String, an.a<?, ?>> paramHashMap)
    {
      this.versionCode = 1;
      this.className = paramString;
      this.bK = a(paramHashMap);
    }
    
    private static ArrayList<aq.b> a(HashMap<String, an.a<?, ?>> paramHashMap)
    {
      if (paramHashMap == null) {
        return null;
      }
      ArrayList localArrayList = new ArrayList();
      Iterator localIterator = paramHashMap.keySet().iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        localArrayList.add(new aq.b(str, (an.a)paramHashMap.get(str)));
      }
      return localArrayList;
    }
    
    HashMap<String, an.a<?, ?>> X()
    {
      HashMap localHashMap = new HashMap();
      int i = this.bK.size();
      for (int j = 0; j < i; j++)
      {
        aq.b localb = (aq.b)this.bK.get(j);
        localHashMap.put(localb.bL, localb.bM);
      }
      return localHashMap;
    }
    
    public int describeContents()
    {
      return 0;
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      as.a(this, paramParcel, paramInt);
    }
  }
  
  public static class b
    implements ae
  {
    public static final ap CREATOR = new ap();
    final String bL;
    final an.a<?, ?> bM;
    final int versionCode;
    
    b(int paramInt, String paramString, an.a<?, ?> parama)
    {
      this.versionCode = paramInt;
      this.bL = paramString;
      this.bM = parama;
    }
    
    b(String paramString, an.a<?, ?> parama)
    {
      this.versionCode = 1;
      this.bL = paramString;
      this.bM = parama;
    }
    
    public int describeContents()
    {
      return 0;
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      ap.a(this, paramParcel, paramInt);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.aq
 * JD-Core Version:    0.7.0.1
 */