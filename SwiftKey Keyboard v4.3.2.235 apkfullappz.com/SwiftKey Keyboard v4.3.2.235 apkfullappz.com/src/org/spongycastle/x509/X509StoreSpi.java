package org.spongycastle.x509;

import java.util.Collection;
import org.spongycastle.util.Selector;

public abstract class X509StoreSpi
{
  public abstract Collection engineGetMatches(Selector paramSelector);
  
  public abstract void engineInit(X509StoreParameters paramX509StoreParameters);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.x509.X509StoreSpi
 * JD-Core Version:    0.7.0.1
 */