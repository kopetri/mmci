package org.spongycastle.asn1.pkcs;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.BERSequence;
import org.spongycastle.asn1.DERTaggedObject;

public class SignedData
  extends ASN1Object
  implements PKCSObjectIdentifiers
{
  private ASN1Set certificates;
  private ContentInfo contentInfo;
  private ASN1Set crls;
  private ASN1Set digestAlgorithms;
  private ASN1Set signerInfos;
  private ASN1Integer version;
  
  public SignedData(ASN1Integer paramASN1Integer, ASN1Set paramASN1Set1, ContentInfo paramContentInfo, ASN1Set paramASN1Set2, ASN1Set paramASN1Set3, ASN1Set paramASN1Set4)
  {
    this.version = paramASN1Integer;
    this.digestAlgorithms = paramASN1Set1;
    this.contentInfo = paramContentInfo;
    this.certificates = paramASN1Set2;
    this.crls = paramASN1Set3;
    this.signerInfos = paramASN1Set4;
  }
  
  public SignedData(ASN1Sequence paramASN1Sequence)
  {
    Enumeration localEnumeration = paramASN1Sequence.getObjects();
    this.version = ((ASN1Integer)localEnumeration.nextElement());
    this.digestAlgorithms = ((ASN1Set)localEnumeration.nextElement());
    this.contentInfo = ContentInfo.getInstance(localEnumeration.nextElement());
    while (localEnumeration.hasMoreElements())
    {
      ASN1Primitive localASN1Primitive = (ASN1Primitive)localEnumeration.nextElement();
      if ((localASN1Primitive instanceof DERTaggedObject))
      {
        DERTaggedObject localDERTaggedObject = (DERTaggedObject)localASN1Primitive;
        switch (localDERTaggedObject.getTagNo())
        {
        default: 
          throw new IllegalArgumentException("unknown tag value " + localDERTaggedObject.getTagNo());
        case 0: 
          this.certificates = ASN1Set.getInstance(localDERTaggedObject, false);
          break;
        case 1: 
          this.crls = ASN1Set.getInstance(localDERTaggedObject, false);
          break;
        }
      }
      else
      {
        this.signerInfos = ((ASN1Set)localASN1Primitive);
      }
    }
  }
  
  public static SignedData getInstance(Object paramObject)
  {
    if ((paramObject instanceof SignedData)) {
      return (SignedData)paramObject;
    }
    if (paramObject != null) {
      return new SignedData(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public ASN1Set getCRLs()
  {
    return this.crls;
  }
  
  public ASN1Set getCertificates()
  {
    return this.certificates;
  }
  
  public ContentInfo getContentInfo()
  {
    return this.contentInfo;
  }
  
  public ASN1Set getDigestAlgorithms()
  {
    return this.digestAlgorithms;
  }
  
  public ASN1Set getSignerInfos()
  {
    return this.signerInfos;
  }
  
  public ASN1Integer getVersion()
  {
    return this.version;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.version);
    localASN1EncodableVector.add(this.digestAlgorithms);
    localASN1EncodableVector.add(this.contentInfo);
    if (this.certificates != null) {
      localASN1EncodableVector.add(new DERTaggedObject(false, 0, this.certificates));
    }
    if (this.crls != null) {
      localASN1EncodableVector.add(new DERTaggedObject(false, 1, this.crls));
    }
    localASN1EncodableVector.add(this.signerInfos);
    return new BERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.pkcs.SignedData
 * JD-Core Version:    0.7.0.1
 */