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

public class PKCS12PBEParams
  extends ASN1Object
{
  ASN1Integer iterations;
  ASN1OctetString iv;
  
  private PKCS12PBEParams(ASN1Sequence paramASN1Sequence)
  {
    this.iv = ((ASN1OctetString)paramASN1Sequence.getObjectAt(0));
    this.iterations = ASN1Integer.getInstance(paramASN1Sequence.getObjectAt(1));
  }
  
  public PKCS12PBEParams(byte[] paramArrayOfByte, int paramInt)
  {
    this.iv = new DEROctetString(paramArrayOfByte);
    this.iterations = new ASN1Integer(paramInt);
  }
  
  public static PKCS12PBEParams getInstance(Object paramObject)
  {
    if ((paramObject instanceof PKCS12PBEParams)) {
      return (PKCS12PBEParams)paramObject;
    }
    if (paramObject != null) {
      return new PKCS12PBEParams(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public byte[] getIV()
  {
    return this.iv.getOctets();
  }
  
  public BigInteger getIterations()
  {
    return this.iterations.getValue();
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.iv);
    localASN1EncodableVector.add(this.iterations);
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.pkcs.PKCS12PBEParams
 * JD-Core Version:    0.7.0.1
 */