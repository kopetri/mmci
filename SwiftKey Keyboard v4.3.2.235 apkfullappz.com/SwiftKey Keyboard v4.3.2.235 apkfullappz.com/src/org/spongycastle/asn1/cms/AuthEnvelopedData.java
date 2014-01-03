package org.spongycastle.asn1.cms;

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

public class AuthEnvelopedData
  extends ASN1Object
{
  private ASN1Set authAttrs;
  private EncryptedContentInfo authEncryptedContentInfo;
  private ASN1OctetString mac;
  private OriginatorInfo originatorInfo;
  private ASN1Set recipientInfos;
  private ASN1Set unauthAttrs;
  private ASN1Integer version;
  
  public AuthEnvelopedData(ASN1Sequence paramASN1Sequence)
  {
    int i = 0 + 1;
    this.version = ((ASN1Integer)paramASN1Sequence.getObjectAt(0).toASN1Primitive());
    int j = i + 1;
    ASN1Primitive localASN1Primitive1 = paramASN1Sequence.getObjectAt(1).toASN1Primitive();
    if ((localASN1Primitive1 instanceof ASN1TaggedObject))
    {
      this.originatorInfo = OriginatorInfo.getInstance((ASN1TaggedObject)localASN1Primitive1, false);
      j++;
      localASN1Primitive1 = paramASN1Sequence.getObjectAt(2).toASN1Primitive();
    }
    this.recipientInfos = ASN1Set.getInstance(localASN1Primitive1);
    int k = j + 1;
    this.authEncryptedContentInfo = EncryptedContentInfo.getInstance(paramASN1Sequence.getObjectAt(j).toASN1Primitive());
    int m = k + 1;
    ASN1Primitive localASN1Primitive2 = paramASN1Sequence.getObjectAt(k).toASN1Primitive();
    if ((localASN1Primitive2 instanceof ASN1TaggedObject))
    {
      this.authAttrs = ASN1Set.getInstance((ASN1TaggedObject)localASN1Primitive2, false);
      int n = m + 1;
      localASN1Primitive2 = paramASN1Sequence.getObjectAt(m).toASN1Primitive();
      m = n;
    }
    this.mac = ASN1OctetString.getInstance(localASN1Primitive2);
    if (paramASN1Sequence.size() > m) {
      this.unauthAttrs = ASN1Set.getInstance((ASN1TaggedObject)paramASN1Sequence.getObjectAt(m).toASN1Primitive(), false);
    }
  }
  
  public AuthEnvelopedData(OriginatorInfo paramOriginatorInfo, ASN1Set paramASN1Set1, EncryptedContentInfo paramEncryptedContentInfo, ASN1Set paramASN1Set2, ASN1OctetString paramASN1OctetString, ASN1Set paramASN1Set3)
  {
    this.version = new ASN1Integer(0);
    this.originatorInfo = paramOriginatorInfo;
    this.recipientInfos = paramASN1Set1;
    this.authEncryptedContentInfo = paramEncryptedContentInfo;
    this.authAttrs = paramASN1Set2;
    this.mac = paramASN1OctetString;
    this.unauthAttrs = paramASN1Set3;
  }
  
  public static AuthEnvelopedData getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof AuthEnvelopedData))) {
      return (AuthEnvelopedData)paramObject;
    }
    if ((paramObject instanceof ASN1Sequence)) {
      return new AuthEnvelopedData((ASN1Sequence)paramObject);
    }
    throw new IllegalArgumentException("Invalid AuthEnvelopedData: " + paramObject.getClass().getName());
  }
  
  public static AuthEnvelopedData getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    return getInstance(ASN1Sequence.getInstance(paramASN1TaggedObject, paramBoolean));
  }
  
  public ASN1Set getAuthAttrs()
  {
    return this.authAttrs;
  }
  
  public EncryptedContentInfo getAuthEncryptedContentInfo()
  {
    return this.authEncryptedContentInfo;
  }
  
  public ASN1OctetString getMac()
  {
    return this.mac;
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
    localASN1EncodableVector.add(this.authEncryptedContentInfo);
    if (this.authAttrs != null) {
      localASN1EncodableVector.add(new DERTaggedObject(false, 1, this.authAttrs));
    }
    localASN1EncodableVector.add(this.mac);
    if (this.unauthAttrs != null) {
      localASN1EncodableVector.add(new DERTaggedObject(false, 2, this.unauthAttrs));
    }
    return new BERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.cms.AuthEnvelopedData
 * JD-Core Version:    0.7.0.1
 */