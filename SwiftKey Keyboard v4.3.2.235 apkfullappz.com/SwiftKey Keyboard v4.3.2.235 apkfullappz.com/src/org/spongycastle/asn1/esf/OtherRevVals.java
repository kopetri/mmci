package org.spongycastle.asn1.esf;

import java.io.IOException;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class OtherRevVals
  extends ASN1Object
{
  private ASN1ObjectIdentifier otherRevValType;
  private ASN1Encodable otherRevVals;
  
  public OtherRevVals(ASN1ObjectIdentifier paramASN1ObjectIdentifier, ASN1Encodable paramASN1Encodable)
  {
    this.otherRevValType = paramASN1ObjectIdentifier;
    this.otherRevVals = paramASN1Encodable;
  }
  
  private OtherRevVals(ASN1Sequence paramASN1Sequence)
  {
    if (paramASN1Sequence.size() != 2) {
      throw new IllegalArgumentException("Bad sequence size: " + paramASN1Sequence.size());
    }
    this.otherRevValType = ((ASN1ObjectIdentifier)paramASN1Sequence.getObjectAt(0));
    try
    {
      this.otherRevVals = ASN1Primitive.fromByteArray(paramASN1Sequence.getObjectAt(1).toASN1Primitive().getEncoded("DER"));
      return;
    }
    catch (IOException localIOException)
    {
      throw new IllegalStateException();
    }
  }
  
  public static OtherRevVals getInstance(Object paramObject)
  {
    if ((paramObject instanceof OtherRevVals)) {
      return (OtherRevVals)paramObject;
    }
    if (paramObject != null) {
      return new OtherRevVals(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public ASN1ObjectIdentifier getOtherRevValType()
  {
    return this.otherRevValType;
  }
  
  public ASN1Encodable getOtherRevVals()
  {
    return this.otherRevVals;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.otherRevValType);
    localASN1EncodableVector.add(this.otherRevVals);
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.esf.OtherRevVals
 * JD-Core Version:    0.7.0.1
 */