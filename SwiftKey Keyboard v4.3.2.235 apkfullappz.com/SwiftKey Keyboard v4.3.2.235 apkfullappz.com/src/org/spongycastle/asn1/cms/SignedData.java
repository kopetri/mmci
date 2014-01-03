package org.spongycastle.asn1.cms;

import java.math.BigInteger;
import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.BERSequence;
import org.spongycastle.asn1.BERSet;
import org.spongycastle.asn1.BERTaggedObject;
import org.spongycastle.asn1.DERTaggedObject;

public class SignedData
  extends ASN1Object
{
  private ASN1Set certificates;
  private boolean certsBer;
  private ContentInfo contentInfo;
  private ASN1Set crls;
  private boolean crlsBer;
  private ASN1Set digestAlgorithms;
  private ASN1Set signerInfos;
  private ASN1Integer version;
  
  private SignedData(ASN1Sequence paramASN1Sequence)
  {
    Enumeration localEnumeration = paramASN1Sequence.getObjects();
    this.version = ASN1Integer.getInstance(localEnumeration.nextElement());
    this.digestAlgorithms = ((ASN1Set)localEnumeration.nextElement());
    this.contentInfo = ContentInfo.getInstance(localEnumeration.nextElement());
    while (localEnumeration.hasMoreElements())
    {
      ASN1Primitive localASN1Primitive = (ASN1Primitive)localEnumeration.nextElement();
      if ((localASN1Primitive instanceof ASN1TaggedObject))
      {
        ASN1TaggedObject localASN1TaggedObject = (ASN1TaggedObject)localASN1Primitive;
        switch (localASN1TaggedObject.getTagNo())
        {
        default: 
          throw new IllegalArgumentException("unknown tag value " + localASN1TaggedObject.getTagNo());
        case 0: 
          this.certsBer = (localASN1TaggedObject instanceof BERTaggedObject);
          this.certificates = ASN1Set.getInstance(localASN1TaggedObject, false);
          break;
        case 1: 
          this.crlsBer = (localASN1TaggedObject instanceof BERTaggedObject);
          this.crls = ASN1Set.getInstance(localASN1TaggedObject, false);
          break;
        }
      }
      else
      {
        this.signerInfos = ((ASN1Set)localASN1Primitive);
      }
    }
  }
  
  public SignedData(ASN1Set paramASN1Set1, ContentInfo paramContentInfo, ASN1Set paramASN1Set2, ASN1Set paramASN1Set3, ASN1Set paramASN1Set4)
  {
    this.version = calculateVersion(paramContentInfo.getContentType(), paramASN1Set2, paramASN1Set3, paramASN1Set4);
    this.digestAlgorithms = paramASN1Set1;
    this.contentInfo = paramContentInfo;
    this.certificates = paramASN1Set2;
    this.crls = paramASN1Set3;
    this.signerInfos = paramASN1Set4;
    this.crlsBer = (paramASN1Set3 instanceof BERSet);
    this.certsBer = (paramASN1Set2 instanceof BERSet);
  }
  
  private ASN1Integer calculateVersion(ASN1ObjectIdentifier paramASN1ObjectIdentifier, ASN1Set paramASN1Set1, ASN1Set paramASN1Set2, ASN1Set paramASN1Set3)
  {
    int i = 0;
    int j = 0;
    int k = 0;
    if (paramASN1Set1 != null)
    {
      Enumeration localEnumeration2 = paramASN1Set1.getObjects();
      while (localEnumeration2.hasMoreElements())
      {
        Object localObject = localEnumeration2.nextElement();
        if ((localObject instanceof ASN1TaggedObject))
        {
          ASN1TaggedObject localASN1TaggedObject = ASN1TaggedObject.getInstance(localObject);
          if (localASN1TaggedObject.getTagNo() == 1) {
            i = 1;
          } else if (localASN1TaggedObject.getTagNo() == 2) {
            j = 1;
          } else if (localASN1TaggedObject.getTagNo() == 3) {
            k = 1;
          }
        }
      }
    }
    if (k != 0) {
      return new ASN1Integer(5);
    }
    int m = 0;
    if (paramASN1Set2 != null)
    {
      Enumeration localEnumeration1 = paramASN1Set2.getObjects();
      while (localEnumeration1.hasMoreElements()) {
        if ((localEnumeration1.nextElement() instanceof ASN1TaggedObject)) {
          m = 1;
        }
      }
    }
    if (m != 0) {
      return new ASN1Integer(5);
    }
    if (j != 0) {
      return new ASN1Integer(4);
    }
    if (i != 0) {
      return new ASN1Integer(3);
    }
    if (checkForVersion3(paramASN1Set3)) {
      return new ASN1Integer(3);
    }
    if (!CMSObjectIdentifiers.data.equals(paramASN1ObjectIdentifier)) {
      return new ASN1Integer(3);
    }
    return new ASN1Integer(1);
  }
  
  private boolean checkForVersion3(ASN1Set paramASN1Set)
  {
    Enumeration localEnumeration = paramASN1Set.getObjects();
    while (localEnumeration.hasMoreElements()) {
      if (SignerInfo.getInstance(localEnumeration.nextElement()).getVersion().getValue().intValue() == 3) {
        return true;
      }
    }
    return false;
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
  
  public ASN1Set getDigestAlgorithms()
  {
    return this.digestAlgorithms;
  }
  
  public ContentInfo getEncapContentInfo()
  {
    return this.contentInfo;
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
    if (this.certificates != null)
    {
      if (this.certsBer) {
        localASN1EncodableVector.add(new BERTaggedObject(false, 0, this.certificates));
      }
    }
    else if (this.crls != null)
    {
      if (!this.crlsBer) {
        break label131;
      }
      localASN1EncodableVector.add(new BERTaggedObject(false, 1, this.crls));
    }
    for (;;)
    {
      localASN1EncodableVector.add(this.signerInfos);
      return new BERSequence(localASN1EncodableVector);
      localASN1EncodableVector.add(new DERTaggedObject(false, 0, this.certificates));
      break;
      label131:
      localASN1EncodableVector.add(new DERTaggedObject(false, 1, this.crls));
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.cms.SignedData
 * JD-Core Version:    0.7.0.1
 */