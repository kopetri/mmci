package org.spongycastle.jce.provider;

import java.security.InvalidAlgorithmParameterException;
import java.security.cert.CRLSelector;
import java.security.cert.CertSelector;
import java.security.cert.CertStore;
import java.security.cert.CertStoreException;
import java.security.cert.CertStoreParameters;
import java.security.cert.CertStoreSpi;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.spongycastle.jce.MultiCertStoreParameters;

public class MultiCertStoreSpi
  extends CertStoreSpi
{
  private MultiCertStoreParameters params;
  
  public MultiCertStoreSpi(CertStoreParameters paramCertStoreParameters)
    throws InvalidAlgorithmParameterException
  {
    super(paramCertStoreParameters);
    if (!(paramCertStoreParameters instanceof MultiCertStoreParameters)) {
      throw new InvalidAlgorithmParameterException("org.spongycastle.jce.provider.MultiCertStoreSpi: parameter must be a MultiCertStoreParameters object\n" + paramCertStoreParameters.toString());
    }
    this.params = ((MultiCertStoreParameters)paramCertStoreParameters);
  }
  
  public Collection engineGetCRLs(CRLSelector paramCRLSelector)
    throws CertStoreException
  {
    boolean bool = this.params.getSearchAllStores();
    Iterator localIterator = this.params.getCertStores().iterator();
    Object localObject;
    if (bool) {
      localObject = new ArrayList();
    }
    while (localIterator.hasNext())
    {
      Collection localCollection = ((CertStore)localIterator.next()).getCRLs(paramCRLSelector);
      if (bool)
      {
        ((List)localObject).addAll(localCollection);
        continue;
        localObject = Collections.EMPTY_LIST;
      }
      else if (!localCollection.isEmpty())
      {
        return localCollection;
      }
    }
    return localObject;
  }
  
  public Collection engineGetCertificates(CertSelector paramCertSelector)
    throws CertStoreException
  {
    boolean bool = this.params.getSearchAllStores();
    Iterator localIterator = this.params.getCertStores().iterator();
    Object localObject;
    if (bool) {
      localObject = new ArrayList();
    }
    while (localIterator.hasNext())
    {
      Collection localCollection = ((CertStore)localIterator.next()).getCertificates(paramCertSelector);
      if (bool)
      {
        ((List)localObject).addAll(localCollection);
        continue;
        localObject = Collections.EMPTY_LIST;
      }
      else if (!localCollection.isEmpty())
      {
        return localCollection;
      }
    }
    return localObject;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jce.provider.MultiCertStoreSpi
 * JD-Core Version:    0.7.0.1
 */