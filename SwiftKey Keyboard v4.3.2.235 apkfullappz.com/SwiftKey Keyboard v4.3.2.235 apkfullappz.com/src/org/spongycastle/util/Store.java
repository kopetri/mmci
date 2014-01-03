package org.spongycastle.util;

import java.util.Collection;

public abstract interface Store
{
  public abstract Collection getMatches(Selector paramSelector)
    throws StoreException;
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.util.Store
 * JD-Core Version:    0.7.0.1
 */