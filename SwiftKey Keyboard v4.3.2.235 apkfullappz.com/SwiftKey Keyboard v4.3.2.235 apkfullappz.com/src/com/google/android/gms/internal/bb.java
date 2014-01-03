package com.google.android.gms.internal;

import android.os.Bundle;
import com.google.android.gms.dynamic.LifecycleDelegate;
import java.util.Iterator;
import java.util.LinkedList;

public abstract class bb<T extends LifecycleDelegate>
{
  private T bT;
  private Bundle bU;
  private LinkedList<bb.a> bV;
  private final be<T> bW = new be()
  {
    public void a(T paramAnonymousT)
    {
      bb.a(bb.this, paramAnonymousT);
      Iterator localIterator = bb.a(bb.this).iterator();
      while (localIterator.hasNext())
      {
        localIterator.next();
        bb.b(bb.this);
      }
      bb.a(bb.this).clear();
      bb.a(bb.this, null);
    }
  };
  
  public T ag()
  {
    return this.bT;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.bb
 * JD-Core Version:    0.7.0.1
 */