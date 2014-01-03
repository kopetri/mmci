package org.spongycastle.asn1.pkcs;

import java.math.BigInteger;
import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;

public class PBKDF2Params
  extends ASN1Object
{
  private ASN1Integer iterationCount;
  private ASN1Integer keyLength;
  private ASN1OctetString octStr;
  
  private PBKDF2Params(ASN1Sequence paramASN1Sequence)
  {
    Enumeration localEnumeration = paramASN1Sequence.getObjects();
    this.octStr = ((ASN1OctetString)localEnumeration.nextElement());
    this.iterationCount = ((ASN1Integer)localEnumeration.nextElement());
    if (localEnumeration.hasMoreElements())
    {
      this.keyLength = ((ASN1Integer)localEnumeration.nextElement());
      return;
    }
    this.keyLength = null;
  }
  
  public PBKDF2Params(byte[] paramArrayOfByte, int paramInt)
  {
    this.octStr = new DEROctetString(paramArrayOfByte);
    this.iterationCount = new ASN1Integer(paramInt);
  }
  
  public PBKDF2Params(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    this(paramArrayOfByte, paramInt1);
    this.keyLength = new ASN1Integer(paramInt2);
  }
  
  public static PBKDF2Params getInstance(Object paramObject)
  {
    if ((paramObject instanceof PBKDF2Params)) {
      return (PBKDF2Params)paramObject;
    }
    if (paramObject != null) {
      return new PBKDF2Params(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public BigInteger getIterationCount()
  {
    return this.iterationCount.getValue();
  }
  
  public BigInteger getKeyLength()
  {
    if (this.keyLength != null) {
      return this.keyLength.getValue();
    }
    return null;
  }
  
  public byte[] getSalt()
  {
    return this.octStr.getOctets();
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.octStr);
    localASN1EncodableVector.add(this.iterationCount);
    if (this.keyLength != null) {
      localASN1EncodableVector.add(this.keyLength);
    }
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.pkcs.PBKDF2Params
 * JD-Core Version:    0.7.0.1
 */