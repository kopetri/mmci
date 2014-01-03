package org.spongycastle.asn1.pkcs;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;

public class PBEParameter
  extends ASN1Object
{
  ASN1Integer iterations;
  ASN1OctetString salt;
  
  private PBEParameter(ASN1Sequence paramASN1Sequence)
  {
    this.salt = ((ASN1OctetString)paramASN1Sequence.getObjectAt(0));
    this.iterations = ((ASN1Integer)paramASN1Sequence.getObjectAt(1));
  }
  
  public PBEParameter(byte[] paramArrayOfByte, int paramInt)
  {
    if (paramArrayOfByte.length != 8) {
      throw new IllegalArgumentException("salt length must be 8");
    }
    this.salt = new DEROctetString(paramArrayOfByte);
    this.iterations = new ASN1Integer(paramInt);
  }
  
  public static PBEParameter getInstance(Object paramObject)
  {
    if ((paramObject instanceof PBEParameter)) {
      return (PBEParameter)paramObject;
    }
    if (paramObject != null) {
      return new PBEParameter(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public BigInteger getIterationCount()
  {
    return this.iterations.getValue();
  }
  
  public byte[] getSalt()
  {
    return this.salt.getOctets();
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.salt);
    localASN1EncodableVector.add(this.iterations);
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.pkcs.PBEParameter
 * JD-Core Version:    0.7.0.1
 */