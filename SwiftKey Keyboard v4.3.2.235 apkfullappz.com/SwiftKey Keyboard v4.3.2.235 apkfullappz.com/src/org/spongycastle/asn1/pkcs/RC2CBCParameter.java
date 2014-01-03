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

public class RC2CBCParameter
  extends ASN1Object
{
  ASN1OctetString iv;
  ASN1Integer version;
  
  public RC2CBCParameter(int paramInt, byte[] paramArrayOfByte)
  {
    this.version = new ASN1Integer(paramInt);
    this.iv = new DEROctetString(paramArrayOfByte);
  }
  
  private RC2CBCParameter(ASN1Sequence paramASN1Sequence)
  {
    if (paramASN1Sequence.size() == 1)
    {
      this.version = null;
      this.iv = ((ASN1OctetString)paramASN1Sequence.getObjectAt(0));
      return;
    }
    this.version = ((ASN1Integer)paramASN1Sequence.getObjectAt(0));
    this.iv = ((ASN1OctetString)paramASN1Sequence.getObjectAt(1));
  }
  
  public RC2CBCParameter(byte[] paramArrayOfByte)
  {
    this.version = null;
    this.iv = new DEROctetString(paramArrayOfByte);
  }
  
  public static RC2CBCParameter getInstance(Object paramObject)
  {
    if ((paramObject instanceof RC2CBCParameter)) {
      return (RC2CBCParameter)paramObject;
    }
    if (paramObject != null) {
      return new RC2CBCParameter(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public byte[] getIV()
  {
    return this.iv.getOctets();
  }
  
  public BigInteger getRC2ParameterVersion()
  {
    if (this.version == null) {
      return null;
    }
    return this.version.getValue();
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    if (this.version != null) {
      localASN1EncodableVector.add(this.version);
    }
    localASN1EncodableVector.add(this.iv);
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.pkcs.RC2CBCParameter
 * JD-Core Version:    0.7.0.1
 */