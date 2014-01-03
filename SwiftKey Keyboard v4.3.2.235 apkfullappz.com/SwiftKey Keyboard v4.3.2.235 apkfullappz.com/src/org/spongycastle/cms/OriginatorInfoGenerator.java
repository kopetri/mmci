package org.spongycastle.cms;

import java.util.ArrayList;
import java.util.List;
import org.spongycastle.asn1.cms.OriginatorInfo;
import org.spongycastle.cert.X509CertificateHolder;
import org.spongycastle.util.Store;

public class OriginatorInfoGenerator
{
  private final List origCRLs;
  private final List origCerts;
  
  public OriginatorInfoGenerator(X509CertificateHolder paramX509CertificateHolder)
  {
    this.origCerts = new ArrayList(1);
    this.origCRLs = null;
    this.origCerts.add(paramX509CertificateHolder.toASN1Structure());
  }
  
  public OriginatorInfoGenerator(Store paramStore)
    throws CMSException
  {
    this(paramStore, null);
  }
  
  public OriginatorInfoGenerator(Store paramStore1, Store paramStore2)
    throws CMSException
  {
    this.origCerts = CMSUtils.getCertificatesFromStore(paramStore1);
    if (paramStore2 != null)
    {
      this.origCRLs = CMSUtils.getCRLsFromStore(paramStore2);
      return;
    }
    this.origCRLs = null;
  }
  
  public OriginatorInformation generate()
  {
    if (this.origCRLs != null) {
      return new OriginatorInformation(new OriginatorInfo(CMSUtils.createDerSetFromList(this.origCerts), CMSUtils.createDerSetFromList(this.origCRLs)));
    }
    return new OriginatorInformation(new OriginatorInfo(CMSUtils.createDerSetFromList(this.origCerts), null));
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.OriginatorInfoGenerator
 * JD-Core Version:    0.7.0.1
 */