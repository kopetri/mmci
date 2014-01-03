package com.google.android.gms.internal;

import android.net.Uri;

public abstract class j
{
  protected final k O;
  protected final int R;
  private final int S;
  
  public j(k paramk, int paramInt)
  {
    this.O = ((k)x.d(paramk));
    if ((paramInt >= 0) && (paramInt < paramk.getCount())) {}
    for (boolean bool = true;; bool = false)
    {
      x.a(bool);
      this.R = paramInt;
      this.S = paramk.d(this.R);
      return;
    }
  }
  
  protected Uri c(String paramString)
  {
    return this.O.f(paramString, this.R, this.S);
  }
  
  protected boolean d(String paramString)
  {
    return this.O.g(paramString, this.R, this.S);
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool1 = paramObject instanceof j;
    boolean bool2 = false;
    if (bool1)
    {
      j localj = (j)paramObject;
      boolean bool3 = w.a(Integer.valueOf(localj.R), Integer.valueOf(this.R));
      bool2 = false;
      if (bool3)
      {
        boolean bool4 = w.a(Integer.valueOf(localj.S), Integer.valueOf(this.S));
        bool2 = false;
        if (bool4)
        {
          k localk1 = localj.O;
          k localk2 = this.O;
          bool2 = false;
          if (localk1 == localk2) {
            bool2 = true;
          }
        }
      }
    }
    return bool2;
  }
  
  protected boolean getBoolean(String paramString)
  {
    return this.O.d(paramString, this.R, this.S);
  }
  
  protected int getInteger(String paramString)
  {
    return this.O.b(paramString, this.R, this.S);
  }
  
  protected long getLong(String paramString)
  {
    return this.O.a(paramString, this.R, this.S);
  }
  
  protected String getString(String paramString)
  {
    return this.O.c(paramString, this.R, this.S);
  }
  
  public int hashCode()
  {
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = Integer.valueOf(this.R);
    arrayOfObject[1] = Integer.valueOf(this.S);
    arrayOfObject[2] = this.O;
    return w.hashCode(arrayOfObject);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.j
 * JD-Core Version:    0.7.0.1
 */