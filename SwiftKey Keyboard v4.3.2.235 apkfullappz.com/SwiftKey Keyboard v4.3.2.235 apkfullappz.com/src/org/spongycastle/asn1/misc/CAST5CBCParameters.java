package org.spongycastle.asn1.misc;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;

public class CAST5CBCParameters
  extends ASN1Object
{
  ASN1OctetString iv;
  ASN1Integer keyLength;
  
  public CAST5CBCParameters(ASN1Sequence paramASN1Sequence)
  {
    this.iv = ((ASN1OctetString)paramASN1Sequence.getObjectAt(0));
    this.keyLength = ((ASN1Integer)paramASN1Sequence.getObjectAt(1));
  }
  
  public CAST5CBCParameters(byte[] paramArrayOfByte, int paramInt)
  {
    this.iv = new DEROctetString(paramArrayOfByte);
    this.keyLength = new ASN1Integer(paramInt);
  }
  
  public static CAST5CBCParameters getInstance(Object paramObject)
  {
    if ((paramObject instanceof CAST5CBCParameters)) {
      return (CAST5CBCParameters)paramObject;
    }
    if (paramObject != null) {
      return new CAST5CBCParameters(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public byte[] getIV()
  {
    return this.iv.getOctets();
  }
  
  public int getKeyLength()
  {
    return this.keyLength.getValue().intValue();
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.iv);
    localASN1EncodableVector.add(this.keyLength);
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.misc.CAST5CBCParameters
 * JD-Core Version:    0.7.0.1
 */