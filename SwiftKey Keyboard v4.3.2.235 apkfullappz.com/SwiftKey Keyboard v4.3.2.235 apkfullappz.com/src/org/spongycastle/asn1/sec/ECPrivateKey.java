package org.spongycastle.asn1.sec;

import java.math.BigInteger;
import java.util.Enumeration;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.util.BigIntegers;

public class ECPrivateKey
  extends ASN1Object
{
  private ASN1Sequence seq;
  
  public ECPrivateKey(BigInteger paramBigInteger)
  {
    byte[] arrayOfByte = BigIntegers.asUnsignedByteArray(paramBigInteger);
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(new ASN1Integer(1));
    localASN1EncodableVector.add(new DEROctetString(arrayOfByte));
    this.seq = new DERSequence(localASN1EncodableVector);
  }
  
  public ECPrivateKey(BigInteger paramBigInteger, ASN1Object paramASN1Object)
  {
    this(paramBigInteger, null, paramASN1Object);
  }
  
  public ECPrivateKey(BigInteger paramBigInteger, DERBitString paramDERBitString, ASN1Object paramASN1Object)
  {
    byte[] arrayOfByte = BigIntegers.asUnsignedByteArray(paramBigInteger);
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(new ASN1Integer(1));
    localASN1EncodableVector.add(new DEROctetString(arrayOfByte));
    if (paramASN1Object != null) {
      localASN1EncodableVector.add(new DERTaggedObject(true, 0, paramASN1Object));
    }
    if (paramDERBitString != null) {
      localASN1EncodableVector.add(new DERTaggedObject(true, 1, paramDERBitString));
    }
    this.seq = new DERSequence(localASN1EncodableVector);
  }
  
  private ECPrivateKey(ASN1Sequence paramASN1Sequence)
  {
    this.seq = paramASN1Sequence;
  }
  
  public static ECPrivateKey getInstance(Object paramObject)
  {
    if ((paramObject instanceof ECPrivateKey)) {
      return (ECPrivateKey)paramObject;
    }
    if (paramObject != null) {
      return new ECPrivateKey(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  private ASN1Primitive getObjectInTag(int paramInt)
  {
    Enumeration localEnumeration = this.seq.getObjects();
    while (localEnumeration.hasMoreElements())
    {
      ASN1Encodable localASN1Encodable = (ASN1Encodable)localEnumeration.nextElement();
      if ((localASN1Encodable instanceof ASN1TaggedObject))
      {
        ASN1TaggedObject localASN1TaggedObject = (ASN1TaggedObject)localASN1Encodable;
        if (localASN1TaggedObject.getTagNo() == paramInt) {
          return localASN1TaggedObject.getObject().toASN1Primitive();
        }
      }
    }
    return null;
  }
  
  public BigInteger getKey()
  {
    return new BigInteger(1, ((ASN1OctetString)this.seq.getObjectAt(1)).getOctets());
  }
  
  public ASN1Primitive getParameters()
  {
    return getObjectInTag(0);
  }
  
  public DERBitString getPublicKey()
  {
    return (DERBitString)getObjectInTag(1);
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return this.seq;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.sec.ECPrivateKey
 * JD-Core Version:    0.7.0.1
 */