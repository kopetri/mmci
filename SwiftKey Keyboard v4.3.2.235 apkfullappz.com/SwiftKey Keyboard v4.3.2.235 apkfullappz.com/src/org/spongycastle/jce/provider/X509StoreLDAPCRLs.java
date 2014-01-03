package org.spongycastle.jce.provider;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.spongycastle.jce.X509LDAPCertStoreParameters;
import org.spongycastle.util.Selector;
import org.spongycastle.util.StoreException;
import org.spongycastle.x509.X509CRLStoreSelector;
import org.spongycastle.x509.X509StoreParameters;
import org.spongycastle.x509.X509StoreSpi;
import org.spongycastle.x509.util.LDAPStoreHelper;

public class X509StoreLDAPCRLs
  extends X509StoreSpi
{
  private LDAPStoreHelper helper;
  
  public Collection engineGetMatches(Selector paramSelector)
    throws StoreException
  {
    if (!(paramSelector instanceof X509CRLStoreSelector)) {
      return Collections.EMPTY_SET;
    }
    X509CRLStoreSelector localX509CRLStoreSelector = (X509CRLStoreSelector)paramSelector;
    HashSet localHashSet = new HashSet();
    if (localX509CRLStoreSelector.isDeltaCRLIndicatorEnabled())
    {
      localHashSet.addAll(this.helper.getDeltaCertificateRevocationLists(localX509CRLStoreSelector));
      return localHashSet;
    }
    localHashSet.addAll(this.helper.getDeltaCertificateRevocationLists(localX509CRLStoreSelector));
    localHashSet.addAll(this.helper.getAttributeAuthorityRevocationLists(localX509CRLStoreSelector));
    localHashSet.addAll(this.helper.getAttributeCertificateRevocationLists(localX509CRLStoreSelector));
    localHashSet.addAll(this.helper.getAuthorityRevocationLists(localX509CRLStoreSelector));
    localHashSet.addAll(this.helper.getCertificateRevocationLists(localX509CRLStoreSelector));
    return localHashSet;
  }
  
  public void engineInit(X509StoreParameters paramX509StoreParameters)
  {
    if (!(paramX509StoreParameters instanceof X509LDAPCertStoreParameters)) {
      throw new IllegalArgumentException("Initialization parameters must be an instance of " + X509LDAPCertStoreParameters.class.getName() + ".");
    }
    this.helper = new LDAPStoreHelper((X509LDAPCertStoreParameters)paramX509StoreParameters);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jce.provider.X509StoreLDAPCRLs
 * JD-Core Version:    0.7.0.1
 */