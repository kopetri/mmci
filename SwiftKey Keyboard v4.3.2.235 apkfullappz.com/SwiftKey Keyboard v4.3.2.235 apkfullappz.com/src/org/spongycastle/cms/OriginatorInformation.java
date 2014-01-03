package org.spongycastle.cms;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.cms.OriginatorInfo;
import org.spongycastle.asn1.x509.Certificate;
import org.spongycastle.asn1.x509.CertificateList;
import org.spongycastle.cert.X509CRLHolder;
import org.spongycastle.cert.X509CertificateHolder;
import org.spongycastle.util.CollectionStore;
import org.spongycastle.util.Store;

public class OriginatorInformation
{
  private OriginatorInfo originatorInfo;
  
  OriginatorInformation(OriginatorInfo paramOriginatorInfo)
  {
    this.originatorInfo = paramOriginatorInfo;
  }
  
  public Store getCRLs()
  {
    ASN1Set localASN1Set = this.originatorInfo.getCRLs();
    if (localASN1Set != null)
    {
      ArrayList localArrayList = new ArrayList(localASN1Set.size());
      Enumeration localEnumeration = localASN1Set.getObjects();
      while (localEnumeration.hasMoreElements())
      {
        ASN1Primitive localASN1Primitive = ((ASN1Encodable)localEnumeration.nextElement()).toASN1Primitive();
        if ((localASN1Primitive instanceof ASN1Sequence)) {
          localArrayList.add(new X509CRLHolder(CertificateList.getInstance(localASN1Primitive)));
        }
      }
      return new CollectionStore(localArrayList);
    }
    return new CollectionStore(new ArrayList());
  }
  
  public Store getCertificates()
  {
    ASN1Set localASN1Set = this.originatorInfo.getCertificates();
    if (localASN1Set != null)
    {
      ArrayList localArrayList = new ArrayList(localASN1Set.size());
      Enumeration localEnumeration = localASN1Set.getObjects();
      while (localEnumeration.hasMoreElements())
      {
        ASN1Primitive localASN1Primitive = ((ASN1Encodable)localEnumeration.nextElement()).toASN1Primitive();
        if ((localASN1Primitive instanceof ASN1Sequence)) {
          localArrayList.add(new X509CertificateHolder(Certificate.getInstance(localASN1Primitive)));
        }
      }
      return new CollectionStore(localArrayList);
    }
    return new CollectionStore(new ArrayList());
  }
  
  public OriginatorInfo toASN1Structure()
  {
    return this.originatorInfo;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.OriginatorInformation
 * JD-Core Version:    0.7.0.1
 */