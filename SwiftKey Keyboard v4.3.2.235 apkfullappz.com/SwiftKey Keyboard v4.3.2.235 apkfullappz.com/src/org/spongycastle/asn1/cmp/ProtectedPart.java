package org.spongycastle.asn1.cmp;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class ProtectedPart
  extends ASN1Object
{
  private PKIBody body;
  private PKIHeader header;
  
  private ProtectedPart(ASN1Sequence paramASN1Sequence)
  {
    this.header = PKIHeader.getInstance(paramASN1Sequence.getObjectAt(0));
    this.body = PKIBody.getInstance(paramASN1Sequence.getObjectAt(1));
  }
  
  public ProtectedPart(PKIHeader paramPKIHeader, PKIBody paramPKIBody)
  {
    this.header = paramPKIHeader;
    this.body = paramPKIBody;
  }
  
  public static ProtectedPart getInstance(Object paramObject)
  {
    if ((paramObject instanceof ProtectedPart)) {
      return (ProtectedPart)paramObject;
    }
    if (paramObject != null) {
      return new ProtectedPart(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public PKIBody getBody()
  {
    return this.body;
  }
  
  public PKIHeader getHeader()
  {
    return this.header;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.header);
    localASN1EncodableVector.add(this.body);
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.cmp.ProtectedPart
 * JD-Core Version:    0.7.0.1
 */