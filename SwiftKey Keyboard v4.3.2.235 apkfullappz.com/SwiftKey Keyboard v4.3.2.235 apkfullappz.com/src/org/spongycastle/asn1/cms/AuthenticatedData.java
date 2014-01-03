package org.spongycastle.asn1.cms;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.BERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public class AuthenticatedData
  extends ASN1Object
{
  private ASN1Set authAttrs;
  private AlgorithmIdentifier digestAlgorithm;
  private ContentInfo encapsulatedContentInfo;
  private ASN1OctetString mac;
  private AlgorithmIdentifier macAlgorithm;
  private OriginatorInfo originatorInfo;
  private ASN1Set recipientInfos;
  private ASN1Set unauthAttrs;
  private ASN1Integer version;
  
  public AuthenticatedData(ASN1Sequence paramASN1Sequence)
  {
    int i = 0 + 1;
    this.version = ((ASN1Integer)paramASN1Sequence.getObjectAt(0));
    int j = i + 1;
    ASN1Encodable localASN1Encodable1 = paramASN1Sequence.getObjectAt(1);
    if ((localASN1Encodable1 instanceof ASN1TaggedObject))
    {
      this.originatorInfo = OriginatorInfo.getInstance((ASN1TaggedObject)localASN1Encodable1, false);
      j++;
      localASN1Encodable1 = paramASN1Sequence.getObjectAt(2);
    }
    this.recipientInfos = ASN1Set.getInstance(localASN1Encodable1);
    int k = j + 1;
    this.macAlgorithm = AlgorithmIdentifier.getInstance(paramASN1Sequence.getObjectAt(j));
    int m = k + 1;
    ASN1Encodable localASN1Encodable2 = paramASN1Sequence.getObjectAt(k);
    if ((localASN1Encodable2 instanceof ASN1TaggedObject))
    {
      this.digestAlgorithm = AlgorithmIdentifier.getInstance((ASN1TaggedObject)localASN1Encodable2, false);
      int i2 = m + 1;
      localASN1Encodable2 = paramASN1Sequence.getObjectAt(m);
      m = i2;
    }
    this.encapsulatedContentInfo = ContentInfo.getInstance(localASN1Encodable2);
    int n = m + 1;
    ASN1Encodable localASN1Encodable3 = paramASN1Sequence.getObjectAt(m);
    int i1;
    if ((localASN1Encodable3 instanceof ASN1TaggedObject))
    {
      this.authAttrs = ASN1Set.getInstance((ASN1TaggedObject)localASN1Encodable3, false);
      i1 = n + 1;
      localASN1Encodable3 = paramASN1Sequence.getObjectAt(n);
    }
    for (;;)
    {
      this.mac = ASN1OctetString.getInstance(localASN1Encodable3);
      if (paramASN1Sequence.size() > i1) {
        this.unauthAttrs = ASN1Set.getInstance((ASN1TaggedObject)paramASN1Sequence.getObjectAt(i1), false);
      }
      return;
      i1 = n;
    }
  }
  
  public AuthenticatedData(OriginatorInfo paramOriginatorInfo, ASN1Set paramASN1Set1, AlgorithmIdentifier paramAlgorithmIdentifier1, AlgorithmIdentifier paramAlgorithmIdentifier2, ContentInfo paramContentInfo, ASN1Set paramASN1Set2, ASN1OctetString paramASN1OctetString, ASN1Set paramASN1Set3)
  {
    if (((paramAlgorithmIdentifier2 != null) || (paramASN1Set2 != null)) && ((paramAlgorithmIdentifier2 == null) || (paramASN1Set2 == null))) {
      throw new IllegalArgumentException("digestAlgorithm and authAttrs must be set together");
    }
    this.version = new ASN1Integer(calculateVersion(paramOriginatorInfo));
    this.originatorInfo = paramOriginatorInfo;
    this.macAlgorithm = paramAlgorithmIdentifier1;
    this.digestAlgorithm = paramAlgorithmIdentifier2;
    this.recipientInfos = paramASN1Set1;
    this.encapsulatedContentInfo = paramContentInfo;
    this.authAttrs = paramASN1Set2;
    this.mac = paramASN1OctetString;
    this.unauthAttrs = paramASN1Set3;
  }
  
  public static int calculateVersion(OriginatorInfo paramOriginatorInfo)
  {
    if (paramOriginatorInfo == null) {}
    Object localObject1;
    do
    {
      int i = 0;
      Enumeration localEnumeration2;
      while (!localEnumeration2.hasMoreElements())
      {
        do
        {
          return i;
          i = 0;
          Enumeration localEnumeration1 = paramOriginatorInfo.getCertificates().getObjects();
          while (localEnumeration1.hasMoreElements())
          {
            Object localObject2 = localEnumeration1.nextElement();
            if ((localObject2 instanceof ASN1TaggedObject))
            {
              ASN1TaggedObject localASN1TaggedObject = (ASN1TaggedObject)localObject2;
              if (localASN1TaggedObject.getTagNo() == 2) {
                i = 1;
              } else if (localASN1TaggedObject.getTagNo() == 3) {
                i = 3;
              }
            }
          }
        } while (paramOriginatorInfo.getCRLs() == null);
        localEnumeration2 = paramOriginatorInfo.getCRLs().getObjects();
      }
      localObject1 = localEnumeration2.nextElement();
    } while ((!(localObject1 instanceof ASN1TaggedObject)) || (((ASN1TaggedObject)localObject1).getTagNo() != 1));
    return 3;
  }
  
  public static AuthenticatedData getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof AuthenticatedData))) {
      return (AuthenticatedData)paramObject;
    }
    if ((paramObject instanceof ASN1Sequence)) {
      return new AuthenticatedData((ASN1Sequence)paramObject);
    }
    throw new IllegalArgumentException("Invalid AuthenticatedData: " + paramObject.getClass().getName());
  }
  
  public static AuthenticatedData getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    return getInstance(ASN1Sequence.getInstance(paramASN1TaggedObject, paramBoolean));
  }
  
  public ASN1Set getAuthAttrs()
  {
    return this.authAttrs;
  }
  
  public AlgorithmIdentifier getDigestAlgorithm()
  {
    return this.digestAlgorithm;
  }
  
  public ContentInfo getEncapsulatedContentInfo()
  {
    return this.encapsulatedContentInfo;
  }
  
  public ASN1OctetString getMac()
  {
    return this.mac;
  }
  
  public AlgorithmIdentifier getMacAlgorithm()
  {
    return this.macAlgorithm;
  }
  
  public OriginatorInfo getOriginatorInfo()
  {
    return this.originatorInfo;
  }
  
  public ASN1Set getRecipientInfos()
  {
    return this.recipientInfos;
  }
  
  public ASN1Set getUnauthAttrs()
  {
    return this.unauthAttrs;
  }
  
  public ASN1Integer getVersion()
  {
    return this.version;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.version);
    if (this.originatorInfo != null) {
      localASN1EncodableVector.add(new DERTaggedObject(false, 0, this.originatorInfo));
    }
    localASN1EncodableVector.add(this.recipientInfos);
    localASN1EncodableVector.add(this.macAlgorithm);
    if (this.digestAlgorithm != null) {
      localASN1EncodableVector.add(new DERTaggedObject(false, 1, this.digestAlgorithm));
    }
    localASN1EncodableVector.add(this.encapsulatedContentInfo);
    if (this.authAttrs != null) {
      localASN1EncodableVector.add(new DERTaggedObject(false, 2, this.authAttrs));
    }
    localASN1EncodableVector.add(this.mac);
    if (this.unauthAttrs != null) {
      localASN1EncodableVector.add(new DERTaggedObject(false, 3, this.unauthAttrs));
    }
    return new BERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.cms.AuthenticatedData
 * JD-Core Version:    0.7.0.1
 */