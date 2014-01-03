package org.spongycastle.jce;

import java.security.cert.CertStoreParameters;
import java.util.Collection;

public class MultiCertStoreParameters
  implements CertStoreParameters
{
  private Collection certStores;
  private boolean searchAllStores;
  
  public MultiCertStoreParameters(Collection paramCollection)
  {
    this(paramCollection, true);
  }
  
  public MultiCertStoreParameters(Collection paramCollection, boolean paramBoolean)
  {
    this.certStores = paramCollection;
    this.searchAllStores = paramBoolean;
  }
  
  public Object clone()
  {
    return this;
  }
  
  public Collection getCertStores()
  {
    return this.certStores;
  }
  
  public boolean getSearchAllStores()
  {
    return this.searchAllStores;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jce.MultiCertStoreParameters
 * JD-Core Version:    0.7.0.1
 */