package org.spongycastle.cert.jcajce;

import java.security.GeneralSecurityException;
import java.security.Provider;
import java.security.cert.CRLException;
import java.security.cert.CertStore;
import java.security.cert.CertificateException;
import java.security.cert.CollectionCertStoreParameters;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.spongycastle.cert.X509CRLHolder;
import org.spongycastle.cert.X509CertificateHolder;
import org.spongycastle.util.Store;

public class JcaCertStoreBuilder
{
  private JcaX509CertificateConverter certificateConverter = new JcaX509CertificateConverter();
  private List certs = new ArrayList();
  private JcaX509CRLConverter crlConverter = new JcaX509CRLConverter();
  private List crls = new ArrayList();
  private Object provider;
  
  private CollectionCertStoreParameters convertHolders(JcaX509CertificateConverter paramJcaX509CertificateConverter, JcaX509CRLConverter paramJcaX509CRLConverter)
    throws CertificateException, CRLException
  {
    ArrayList localArrayList = new ArrayList(this.certs.size() + this.crls.size());
    Iterator localIterator1 = this.certs.iterator();
    while (localIterator1.hasNext()) {
      localArrayList.add(paramJcaX509CertificateConverter.getCertificate((X509CertificateHolder)localIterator1.next()));
    }
    Iterator localIterator2 = this.crls.iterator();
    while (localIterator2.hasNext()) {
      localArrayList.add(paramJcaX509CRLConverter.getCRL((X509CRLHolder)localIterator2.next()));
    }
    return new CollectionCertStoreParameters(localArrayList);
  }
  
  public JcaCertStoreBuilder addCRL(X509CRLHolder paramX509CRLHolder)
  {
    this.crls.add(paramX509CRLHolder);
    return this;
  }
  
  public JcaCertStoreBuilder addCRLs(Store paramStore)
  {
    this.crls.addAll(paramStore.getMatches(null));
    return this;
  }
  
  public JcaCertStoreBuilder addCertificate(X509CertificateHolder paramX509CertificateHolder)
  {
    this.certs.add(paramX509CertificateHolder);
    return this;
  }
  
  public JcaCertStoreBuilder addCertificates(Store paramStore)
  {
    this.certs.addAll(paramStore.getMatches(null));
    return this;
  }
  
  public CertStore build()
    throws GeneralSecurityException
  {
    CollectionCertStoreParameters localCollectionCertStoreParameters = convertHolders(this.certificateConverter, this.crlConverter);
    if ((this.provider instanceof String)) {
      return CertStore.getInstance("Collection", localCollectionCertStoreParameters, (String)this.provider);
    }
    if ((this.provider instanceof Provider)) {
      return CertStore.getInstance("Collection", localCollectionCertStoreParameters, (Provider)this.provider);
    }
    return CertStore.getInstance("Collection", localCollectionCertStoreParameters);
  }
  
  public JcaCertStoreBuilder setProvider(String paramString)
    throws GeneralSecurityException
  {
    this.certificateConverter.setProvider(paramString);
    this.crlConverter.setProvider(paramString);
    this.provider = paramString;
    return this;
  }
  
  public JcaCertStoreBuilder setProvider(Provider paramProvider)
    throws GeneralSecurityException
  {
    this.certificateConverter.setProvider(paramProvider);
    this.crlConverter.setProvider(paramProvider);
    this.provider = paramProvider;
    return this;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.jcajce.JcaCertStoreBuilder
 * JD-Core Version:    0.7.0.1
 */