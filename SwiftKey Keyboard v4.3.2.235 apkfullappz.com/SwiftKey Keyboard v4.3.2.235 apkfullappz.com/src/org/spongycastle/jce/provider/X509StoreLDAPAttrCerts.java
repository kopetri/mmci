package org.spongycastle.jce.provider;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.spongycastle.jce.X509LDAPCertStoreParameters;
import org.spongycastle.util.Selector;
import org.spongycastle.util.StoreException;
import org.spongycastle.x509.X509AttributeCertStoreSelector;
import org.spongycastle.x509.X509StoreParameters;
import org.spongycastle.x509.X509StoreSpi;
import org.spongycastle.x509.util.LDAPStoreHelper;

public class X509StoreLDAPAttrCerts
  extends X509StoreSpi
{
  private LDAPStoreHelper helper;
  
  public Collection engineGetMatches(Selector paramSelector)
    throws StoreException
  {
    if (!(paramSelector instanceof X509AttributeCertStoreSelector)) {
      return Collections.EMPTY_SET;
    }
    X509AttributeCertStoreSelector localX509AttributeCertStoreSelector = (X509AttributeCertStoreSelector)paramSelector;
    HashSet localHashSet = new HashSet();
    localHashSet.addAll(this.helper.getAACertificates(localX509AttributeCertStoreSelector));
    localHashSet.addAll(this.helper.getAttributeCertificateAttributes(localX509AttributeCertStoreSelector));
    localHashSet.addAll(this.helper.getAttributeDescriptorCertificates(localX509AttributeCertStoreSelector));
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
 * Qualified Name:     org.spongycastle.jce.provider.X509StoreLDAPAttrCerts
 * JD-Core Version:    0.7.0.1
 */