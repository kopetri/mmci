package com.google.android.gms.internal;

import android.os.Parcel;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public abstract class an
{
  private void a(StringBuilder paramStringBuilder, a parama, Object paramObject)
  {
    if (parama.E() == 11)
    {
      paramStringBuilder.append(((an)parama.O().cast(paramObject)).toString());
      return;
    }
    if (parama.E() == 7)
    {
      paramStringBuilder.append("\"");
      paramStringBuilder.append(ay.o((String)paramObject));
      paramStringBuilder.append("\"");
      return;
    }
    paramStringBuilder.append(paramObject);
  }
  
  private void a(StringBuilder paramStringBuilder, a parama, ArrayList<Object> paramArrayList)
  {
    paramStringBuilder.append("[");
    int i = 0;
    int j = paramArrayList.size();
    while (i < j)
    {
      if (i > 0) {
        paramStringBuilder.append(",");
      }
      Object localObject = paramArrayList.get(i);
      if (localObject != null) {
        a(paramStringBuilder, parama, localObject);
      }
      i++;
    }
    paramStringBuilder.append("]");
  }
  
  public abstract HashMap<String, a<?, ?>> G();
  
  public HashMap<String, Object> H()
  {
    return null;
  }
  
  public HashMap<String, Object> I()
  {
    return null;
  }
  
  protected <O, I> I a(a<I, O> parama, Object paramObject)
  {
    if (a.c(parama) != null) {
      paramObject = parama.e(paramObject);
    }
    return paramObject;
  }
  
  protected boolean a(a parama)
  {
    if (parama.F() == 11)
    {
      if (parama.L()) {
        return m(parama.M());
      }
      return l(parama.M());
    }
    return k(parama.M());
  }
  
  protected Object b(a parama)
  {
    boolean bool = true;
    String str1 = parama.M();
    if (parama.O() != null)
    {
      if (j(parama.M()) == null)
      {
        x.a(bool, "Concrete field shouldn't be value object: " + parama.M());
        if (!parama.L()) {
          break label80;
        }
      }
      label80:
      for (HashMap localHashMap = I();; localHashMap = H())
      {
        if (localHashMap == null) {
          break label89;
        }
        return localHashMap.get(str1);
        bool = false;
        break;
      }
      try
      {
        label89:
        String str2 = "get" + Character.toUpperCase(str1.charAt(0)) + str1.substring(1);
        Object localObject = getClass().getMethod(str2, new Class[0]).invoke(this, new Object[0]);
        return localObject;
      }
      catch (Exception localException)
      {
        throw new RuntimeException(localException);
      }
    }
    return j(parama.M());
  }
  
  protected abstract Object j(String paramString);
  
  protected abstract boolean k(String paramString);
  
  protected boolean l(String paramString)
  {
    throw new UnsupportedOperationException("Concrete types not supported");
  }
  
  protected boolean m(String paramString)
  {
    throw new UnsupportedOperationException("Concrete type arrays not supported");
  }
  
  public String toString()
  {
    HashMap localHashMap = G();
    StringBuilder localStringBuilder = new StringBuilder(100);
    Iterator localIterator = localHashMap.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      a locala = (a)localHashMap.get(str);
      if (a(locala))
      {
        Object localObject = a(locala, b(locala));
        if (localStringBuilder.length() == 0) {
          localStringBuilder.append("{");
        }
        for (;;)
        {
          localStringBuilder.append("\"").append(str).append("\":");
          if (localObject != null) {
            break label135;
          }
          localStringBuilder.append("null");
          break;
          localStringBuilder.append(",");
        }
        label135:
        switch (locala.F())
        {
        default: 
          if (locala.K()) {
            a(localStringBuilder, locala, (ArrayList)localObject);
          }
          break;
        case 8: 
          localStringBuilder.append("\"").append(aw.a((byte[])localObject)).append("\"");
          break;
        case 9: 
          localStringBuilder.append("\"").append(aw.b((byte[])localObject)).append("\"");
          break;
        case 10: 
          az.a(localStringBuilder, (HashMap)localObject);
          continue;
          a(localStringBuilder, locala, localObject);
        }
      }
    }
    if (localStringBuilder.length() > 0) {
      localStringBuilder.append("}");
    }
    for (;;)
    {
      return localStringBuilder.toString();
      localStringBuilder.append("{}");
    }
  }
  
  public static class a<I, O>
    implements ae
  {
    public static final ao CREATOR = new ao();
    private final int T;
    protected final boolean bA;
    protected final String bB;
    protected final int bC;
    protected final Class<? extends an> bD;
    protected final String bE;
    private aq bF;
    private an.b<I, O> bG;
    protected final int bx;
    protected final boolean by;
    protected final int bz;
    
    a(int paramInt1, int paramInt2, boolean paramBoolean1, int paramInt3, boolean paramBoolean2, String paramString1, int paramInt4, String paramString2, ai paramai)
    {
      this.T = paramInt1;
      this.bx = paramInt2;
      this.by = paramBoolean1;
      this.bz = paramInt3;
      this.bA = paramBoolean2;
      this.bB = paramString1;
      this.bC = paramInt4;
      if (paramString2 == null) {
        this.bD = null;
      }
      for (this.bE = null; paramai == null; this.bE = paramString2)
      {
        this.bG = null;
        return;
        this.bD = at.class;
      }
      this.bG = paramai.C();
    }
    
    protected a(int paramInt1, boolean paramBoolean1, int paramInt2, boolean paramBoolean2, String paramString, int paramInt3, Class<? extends an> paramClass, an.b<I, O> paramb)
    {
      this.T = 1;
      this.bx = paramInt1;
      this.by = paramBoolean1;
      this.bz = paramInt2;
      this.bA = paramBoolean2;
      this.bB = paramString;
      this.bC = paramInt3;
      this.bD = paramClass;
      if (paramClass == null) {}
      for (this.bE = null;; this.bE = paramClass.getCanonicalName())
      {
        this.bG = paramb;
        return;
      }
    }
    
    public static a a(String paramString, int paramInt, an.b<?, ?> paramb, boolean paramBoolean)
    {
      return new a(paramb.E(), paramBoolean, paramb.F(), false, paramString, paramInt, null, paramb);
    }
    
    public static <T extends an> a<T, T> a(String paramString, int paramInt, Class<T> paramClass)
    {
      return new a(11, false, 11, false, paramString, paramInt, paramClass, null);
    }
    
    public static <T extends an> a<ArrayList<T>, ArrayList<T>> b(String paramString, int paramInt, Class<T> paramClass)
    {
      return new a(11, true, 11, true, paramString, paramInt, paramClass, null);
    }
    
    public static a<Integer, Integer> c(String paramString, int paramInt)
    {
      return new a(0, false, 0, false, paramString, paramInt, null, null);
    }
    
    public static a<Double, Double> d(String paramString, int paramInt)
    {
      return new a(4, false, 4, false, paramString, paramInt, null, null);
    }
    
    public static a<Boolean, Boolean> e(String paramString, int paramInt)
    {
      return new a(6, false, 6, false, paramString, paramInt, null, null);
    }
    
    public static a<String, String> f(String paramString, int paramInt)
    {
      return new a(7, false, 7, false, paramString, paramInt, null, null);
    }
    
    public static a<ArrayList<String>, ArrayList<String>> g(String paramString, int paramInt)
    {
      return new a(7, true, 7, true, paramString, paramInt, null, null);
    }
    
    public int E()
    {
      return this.bx;
    }
    
    public int F()
    {
      return this.bz;
    }
    
    public boolean K()
    {
      return this.by;
    }
    
    public boolean L()
    {
      return this.bA;
    }
    
    public String M()
    {
      return this.bB;
    }
    
    public int N()
    {
      return this.bC;
    }
    
    public Class<? extends an> O()
    {
      return this.bD;
    }
    
    String P()
    {
      if (this.bE == null) {
        return null;
      }
      return this.bE;
    }
    
    public boolean Q()
    {
      return this.bG != null;
    }
    
    ai R()
    {
      if (this.bG == null) {
        return null;
      }
      return ai.a(this.bG);
    }
    
    public HashMap<String, a<?, ?>> S()
    {
      x.d(this.bE);
      x.d(this.bF);
      return this.bF.n(this.bE);
    }
    
    public void a(aq paramaq)
    {
      this.bF = paramaq;
    }
    
    public int describeContents()
    {
      return 0;
    }
    
    public I e(O paramO)
    {
      return this.bG.e(paramO);
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder1 = new StringBuilder();
      localStringBuilder1.append("Field\n");
      localStringBuilder1.append("            versionCode=").append(this.T).append('\n');
      localStringBuilder1.append("                 typeIn=").append(this.bx).append('\n');
      localStringBuilder1.append("            typeInArray=").append(this.by).append('\n');
      localStringBuilder1.append("                typeOut=").append(this.bz).append('\n');
      localStringBuilder1.append("           typeOutArray=").append(this.bA).append('\n');
      localStringBuilder1.append("        outputFieldName=").append(this.bB).append('\n');
      localStringBuilder1.append("      safeParcelFieldId=").append(this.bC).append('\n');
      localStringBuilder1.append("       concreteTypeName=").append(P()).append('\n');
      if (O() != null) {
        localStringBuilder1.append("     concreteType.class=").append(O().getCanonicalName()).append('\n');
      }
      StringBuilder localStringBuilder2 = localStringBuilder1.append("          converterName=");
      if (this.bG == null) {}
      for (String str = "null";; str = this.bG.getClass().getCanonicalName())
      {
        localStringBuilder2.append(str).append('\n');
        return localStringBuilder1.toString();
      }
    }
    
    public int u()
    {
      return this.T;
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      ao.a(this, paramParcel, paramInt);
    }
  }
  
  public static abstract interface b<I, O>
  {
    public abstract int E();
    
    public abstract int F();
    
    public abstract I e(O paramO);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.an
 * JD-Core Version:    0.7.0.1
 */