package org.spongycastle.jce.provider;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.spongycastle.jce.X509LDAPCertStoreParameters;
import org.spongycastle.util.Selector;
import org.spongycastle.util.StoreException;
import org.spongycastle.x509.X509CertPairStoreSelector;
import org.spongycastle.x509.X509CertStoreSelector;
import org.spongycastle.x509.X509CertificatePair;
import org.spongycastle.x509.X509StoreParameters;
import org.spongycastle.x509.X509StoreSpi;
import org.spongycastle.x509.util.LDAPStoreHelper;

public class X509StoreLDAPCerts
  extends X509StoreSpi
{
  private LDAPStoreHelper helper;
  
  private Collection getCertificatesFromCrossCertificatePairs(X509CertStoreSelector paramX509CertStoreSelector)
    throws StoreException
  {
    HashSet localHashSet1 = new HashSet();
    X509CertPairStoreSelector localX509CertPairStoreSelector = new X509CertPairStoreSelector();
    localX509CertPairStoreSelector.setForwardSelector(paramX509CertStoreSelector);
    localX509CertPairStoreSelector.setReverseSelector(new X509CertStoreSelector());
    HashSet localHashSet2 = new HashSet(this.helper.getCrossCertificatePairs(localX509CertPairStoreSelector));
    HashSet localHashSet3 = new HashSet();
    HashSet localHashSet4 = new HashSet();
    Iterator localIterator = localHashSet2.iterator();
    while (localIterator.hasNext())
    {
      X509CertificatePair localX509CertificatePair = (X509CertificatePair)localIterator.next();
      if (localX509CertificatePair.getForward() != null) {
        localHashSet3.add(localX509CertificatePair.getForward());
      }
      if (localX509CertificatePair.getReverse() != null) {
        localHashSet4.add(localX509CertificatePair.getReverse());
      }
    }
    localHashSet1.addAll(localHashSet3);
    localHashSet1.addAll(localHashSet4);
    return localHashSet1;
  }
  
  public Collection engineGetMatches(Selector paramSelector)
    throws StoreException
  {
    if (!(paramSelector instanceof X509CertStoreSelector)) {
      return Collections.EMPTY_SET;
    }
    X509CertStoreSelector localX509CertStoreSelector = (X509CertStoreSelector)paramSelector;
    HashSet localHashSet = new HashSet();
    if (localX509CertStoreSelector.getBasicConstraints() > 0)
    {
      localHashSet.addAll(this.helper.getCACertificates(localX509CertStoreSelector));
      localHashSet.addAll(getCertificatesFromCrossCertificatePairs(localX509CertStoreSelector));
      return localHashSet;
    }
    if (localX509CertStoreSelector.getBasicConstraints() == -2)
    {
      localHashSet.addAll(this.helper.getUserCertificates(localX509CertStoreSelector));
      return localHashSet;
    }
    localHashSet.addAll(this.helper.getUserCertificates(localX509CertStoreSelector));
    localHashSet.addAll(this.helper.getCACertificates(localX509CertStoreSelector));
    localHashSet.addAll(getCertificatesFromCrossCertificatePairs(localX509CertStoreSelector));
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
 * Qualified Name:     org.spongycastle.jce.provider.X509StoreLDAPCerts
 * JD-Core Version:    0.7.0.1
 */