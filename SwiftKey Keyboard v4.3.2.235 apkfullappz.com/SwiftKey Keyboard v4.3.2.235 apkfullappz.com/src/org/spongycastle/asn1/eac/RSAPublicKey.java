package org.spongycastle.asn1.eac;

import java.math.BigInteger;
import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class RSAPublicKey
  extends PublicKeyDataObject
{
  private static int exponentValid = 2;
  private static int modulusValid = 1;
  private BigInteger exponent;
  private BigInteger modulus;
  private ASN1ObjectIdentifier usage;
  private int valid = 0;
  
  public RSAPublicKey(ASN1ObjectIdentifier paramASN1ObjectIdentifier, BigInteger paramBigInteger1, BigInteger paramBigInteger2)
  {
    this.usage = paramASN1ObjectIdentifier;
    this.modulus = paramBigInteger1;
    this.exponent = paramBigInteger2;
  }
  
  RSAPublicKey(ASN1Sequence paramASN1Sequence)
  {
    Enumeration localEnumeration = paramASN1Sequence.getObjects();
    this.usage = ASN1ObjectIdentifier.getInstance(localEnumeration.nextElement());
    while (localEnumeration.hasMoreElements())
    {
      UnsignedInteger localUnsignedInteger = UnsignedInteger.getInstance(localEnumeration.nextElement());
      switch (localUnsignedInteger.getTagNo())
      {
      default: 
        throw new IllegalArgumentException("Unknown DERTaggedObject :" + localUnsignedInteger.getTagNo() + "-> not an Iso7816RSAPublicKeyStructure");
      case 1: 
        setModulus(localUnsignedInteger);
        break;
      case 2: 
        setExponent(localUnsignedInteger);
      }
    }
    if (this.valid != 3) {
      throw new IllegalArgumentException("missing argument -> not an Iso7816RSAPublicKeyStructure");
    }
  }
  
  private void setExponent(UnsignedInteger paramUnsignedInteger)
  {
    if ((this.valid & exponentValid) == 0)
    {
      this.valid |= exponentValid;
      this.exponent = paramUnsignedInteger.getValue();
      return;
    }
    throw new IllegalArgumentException("Exponent already set");
  }
  
  private void setModulus(UnsignedInteger paramUnsignedInteger)
  {
    if ((this.valid & modulusValid) == 0)
    {
      this.valid |= modulusValid;
      this.modulus = paramUnsignedInteger.getValue();
      return;
    }
    throw new IllegalArgumentException("Modulus already set");
  }
  
  public BigInteger getModulus()
  {
    return this.modulus;
  }
  
  public BigInteger getPublicExponent()
  {
    return this.exponent;
  }
  
  public ASN1ObjectIdentifier getUsage()
  {
    return this.usage;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.usage);
    localASN1EncodableVector.add(new UnsignedInteger(1, getModulus()));
    localASN1EncodableVector.add(new UnsignedInteger(2, getPublicExponent()));
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.eac.RSAPublicKey
 * JD-Core Version:    0.7.0.1
 */