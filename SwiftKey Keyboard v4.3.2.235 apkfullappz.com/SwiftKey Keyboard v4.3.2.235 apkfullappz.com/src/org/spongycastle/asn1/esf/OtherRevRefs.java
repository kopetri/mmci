package org.spongycastle.asn1.esf;

import java.io.IOException;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class OtherRevRefs
  extends ASN1Object
{
  private ASN1ObjectIdentifier otherRevRefType;
  private ASN1Encodable otherRevRefs;
  
  public OtherRevRefs(ASN1ObjectIdentifier paramASN1ObjectIdentifier, ASN1Encodable paramASN1Encodable)
  {
    this.otherRevRefType = paramASN1ObjectIdentifier;
    this.otherRevRefs = paramASN1Encodable;
  }
  
  private OtherRevRefs(ASN1Sequence paramASN1Sequence)
  {
    if (paramASN1Sequence.size() != 2) {
      throw new IllegalArgumentException("Bad sequence size: " + paramASN1Sequence.size());
    }
    this.otherRevRefType = new ASN1ObjectIdentifier(((ASN1ObjectIdentifier)paramASN1Sequence.getObjectAt(0)).getId());
    try
    {
      this.otherRevRefs = ASN1Primitive.fromByteArray(paramASN1Sequence.getObjectAt(1).toASN1Primitive().getEncoded("DER"));
      return;
    }
    catch (IOException localIOException)
    {
      throw new IllegalStateException();
    }
  }
  
  public static OtherRevRefs getInstance(Object paramObject)
  {
    if ((paramObject instanceof OtherRevRefs)) {
      return (OtherRevRefs)paramObject;
    }
    if (paramObject != null) {
      return new OtherRevRefs(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public ASN1ObjectIdentifier getOtherRevRefType()
  {
    return this.otherRevRefType;
  }
  
  public ASN1Encodable getOtherRevRefs()
  {
    return this.otherRevRefs;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.otherRevRefType);
    localASN1EncodableVector.add(this.otherRevRefs);
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.esf.OtherRevRefs
 * JD-Core Version:    0.7.0.1
 */