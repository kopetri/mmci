package org.spongycastle.asn1.cms;

import java.math.BigInteger;
import java.util.Enumeration;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.BERSequence;
import org.spongycastle.asn1.DERTaggedObject;

public class EnvelopedData
  extends ASN1Object
{
  private EncryptedContentInfo encryptedContentInfo;
  private OriginatorInfo originatorInfo;
  private ASN1Set recipientInfos;
  private ASN1Set unprotectedAttrs;
  private ASN1Integer version;
  
  public EnvelopedData(ASN1Sequence paramASN1Sequence)
  {
    int i = 0 + 1;
    this.version = ((ASN1Integer)paramASN1Sequence.getObjectAt(0));
    int j = i + 1;
    ASN1Encodable localASN1Encodable = paramASN1Sequence.getObjectAt(1);
    if ((localASN1Encodable instanceof ASN1TaggedObject))
    {
      this.originatorInfo = OriginatorInfo.getInstance((ASN1TaggedObject)localASN1Encodable, false);
      j++;
      localASN1Encodable = paramASN1Sequence.getObjectAt(2);
    }
    this.recipientInfos = ASN1Set.getInstance(localASN1Encodable);
    int k = j + 1;
    this.encryptedContentInfo = EncryptedContentInfo.getInstance(paramASN1Sequence.getObjectAt(j));
    if (paramASN1Sequence.size() > k) {
      this.unprotectedAttrs = ASN1Set.getInstance((ASN1TaggedObject)paramASN1Sequence.getObjectAt(k), false);
    }
  }
  
  public EnvelopedData(OriginatorInfo paramOriginatorInfo, ASN1Set paramASN1Set1, EncryptedContentInfo paramEncryptedContentInfo, ASN1Set paramASN1Set2)
  {
    this.version = new ASN1Integer(calculateVersion(paramOriginatorInfo, paramASN1Set1, paramASN1Set2));
    this.originatorInfo = paramOriginatorInfo;
    this.recipientInfos = paramASN1Set1;
    this.encryptedContentInfo = paramEncryptedContentInfo;
    this.unprotectedAttrs = paramASN1Set2;
  }
  
  public static int calculateVersion(OriginatorInfo paramOriginatorInfo, ASN1Set paramASN1Set1, ASN1Set paramASN1Set2)
  {
    int i;
    if ((paramOriginatorInfo != null) || (paramASN1Set2 != null))
    {
      i = 2;
      return i;
    }
    Enumeration localEnumeration = paramASN1Set1.getObjects();
    do
    {
      boolean bool = localEnumeration.hasMoreElements();
      i = 0;
      if (!bool) {
        break;
      }
    } while (RecipientInfo.getInstance(localEnumeration.nextElement()).getVersion().getValue().intValue() == 0);
    return 2;
  }
  
  public static EnvelopedData getInstance(Object paramObject)
  {
    if ((paramObject instanceof EnvelopedData)) {
      return (EnvelopedData)paramObject;
    }
    if (paramObject != null) {
      return new EnvelopedData(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public static EnvelopedData getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    return getInstance(ASN1Sequence.getInstance(paramASN1TaggedObject, paramBoolean));
  }
  
  public EncryptedContentInfo getEncryptedContentInfo()
  {
    return this.encryptedContentInfo;
  }
  
  public OriginatorInfo getOriginatorInfo()
  {
    return this.originatorInfo;
  }
  
  public ASN1Set getRecipientInfos()
  {
    return this.recipientInfos;
  }
  
  public ASN1Set getUnprotectedAttrs()
  {
    return this.unprotectedAttrs;
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
    localASN1EncodableVector.add(this.encryptedContentInfo);
    if (this.unprotectedAttrs != null) {
      localASN1EncodableVector.add(new DERTaggedObject(false, 1, this.unprotectedAttrs));
    }
    return new BERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.cms.EnvelopedData
 * JD-Core Version:    0.7.0.1
 */