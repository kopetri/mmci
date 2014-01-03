package org.spongycastle.asn1.cms;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.BERSequence;
import org.spongycastle.asn1.BERTaggedObject;

public class EncryptedData
  extends ASN1Object
{
  private EncryptedContentInfo encryptedContentInfo;
  private ASN1Set unprotectedAttrs;
  private ASN1Integer version;
  
  private EncryptedData(ASN1Sequence paramASN1Sequence)
  {
    this.version = ASN1Integer.getInstance(paramASN1Sequence.getObjectAt(0));
    this.encryptedContentInfo = EncryptedContentInfo.getInstance(paramASN1Sequence.getObjectAt(1));
    if (paramASN1Sequence.size() == 3) {
      this.unprotectedAttrs = ASN1Set.getInstance(paramASN1Sequence.getObjectAt(2));
    }
  }
  
  public EncryptedData(EncryptedContentInfo paramEncryptedContentInfo)
  {
    this(paramEncryptedContentInfo, null);
  }
  
  public EncryptedData(EncryptedContentInfo paramEncryptedContentInfo, ASN1Set paramASN1Set)
  {
    if (paramASN1Set == null) {}
    for (int i = 0;; i = 2)
    {
      this.version = new ASN1Integer(i);
      this.encryptedContentInfo = paramEncryptedContentInfo;
      this.unprotectedAttrs = paramASN1Set;
      return;
    }
  }
  
  public static EncryptedData getInstance(Object paramObject)
  {
    if ((paramObject instanceof EncryptedData)) {
      return (EncryptedData)paramObject;
    }
    if (paramObject != null) {
      return new EncryptedData(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public EncryptedContentInfo getEncryptedContentInfo()
  {
    return this.encryptedContentInfo;
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
    localASN1EncodableVector.add(this.encryptedContentInfo);
    if (this.unprotectedAttrs != null) {
      localASN1EncodableVector.add(new BERTaggedObject(false, 1, this.unprotectedAttrs));
    }
    return new BERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.cms.EncryptedData
 * JD-Core Version:    0.7.0.1
 */