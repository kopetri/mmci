package org.spongycastle.asn1.pkcs;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.BERSequence;

public class Pfx
  extends ASN1Object
  implements PKCSObjectIdentifiers
{
  private ContentInfo contentInfo;
  private MacData macData = null;
  
  private Pfx(ASN1Sequence paramASN1Sequence)
  {
    if (((ASN1Integer)paramASN1Sequence.getObjectAt(0)).getValue().intValue() != 3) {
      throw new IllegalArgumentException("wrong version for PFX PDU");
    }
    this.contentInfo = ContentInfo.getInstance(paramASN1Sequence.getObjectAt(1));
    if (paramASN1Sequence.size() == 3) {
      this.macData = MacData.getInstance(paramASN1Sequence.getObjectAt(2));
    }
  }
  
  public Pfx(ContentInfo paramContentInfo, MacData paramMacData)
  {
    this.contentInfo = paramContentInfo;
    this.macData = paramMacData;
  }
  
  public static Pfx getInstance(Object paramObject)
  {
    if ((paramObject instanceof Pfx)) {
      return (Pfx)paramObject;
    }
    if (paramObject != null) {
      return new Pfx(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public ContentInfo getAuthSafe()
  {
    return this.contentInfo;
  }
  
  public MacData getMacData()
  {
    return this.macData;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(new ASN1Integer(3));
    localASN1EncodableVector.add(this.contentInfo);
    if (this.macData != null) {
      localASN1EncodableVector.add(this.macData);
    }
    return new BERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.pkcs.Pfx
 * JD-Core Version:    0.7.0.1
 */