package org.spongycastle.jce.provider;

import java.util.Collection;
import org.spongycastle.util.CollectionStore;
import org.spongycastle.util.Selector;
import org.spongycastle.x509.X509CollectionStoreParameters;
import org.spongycastle.x509.X509StoreParameters;
import org.spongycastle.x509.X509StoreSpi;

public class X509StoreCertPairCollection
  extends X509StoreSpi
{
  private CollectionStore _store;
  
  public Collection engineGetMatches(Selector paramSelector)
  {
    return this._store.getMatches(paramSelector);
  }
  
  public void engineInit(X509StoreParameters paramX509StoreParameters)
  {
    if (!(paramX509StoreParameters instanceof X509CollectionStoreParameters)) {
      throw new IllegalArgumentException("Initialization parameters must be an instance of " + X509CollectionStoreParameters.class.getName() + ".");
    }
    this._store = new CollectionStore(((X509CollectionStoreParameters)paramX509StoreParameters).getCollection());
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jce.provider.X509StoreCertPairCollection
 * JD-Core Version:    0.7.0.1
 */