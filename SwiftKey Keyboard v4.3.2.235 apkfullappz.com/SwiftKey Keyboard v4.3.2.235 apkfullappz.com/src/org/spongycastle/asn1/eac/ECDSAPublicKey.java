package org.spongycastle.asn1.eac;

import java.math.BigInteger;
import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;

public class ECDSAPublicKey
  extends PublicKeyDataObject
{
  private static final int A = 2;
  private static final int B = 4;
  private static final int F = 64;
  private static final int G = 8;
  private static final int P = 1;
  private static final int R = 16;
  private static final int Y = 32;
  private byte[] basePointG;
  private BigInteger cofactorF;
  private BigInteger firstCoefA;
  private int options;
  private BigInteger orderOfBasePointR;
  private BigInteger primeModulusP;
  private byte[] publicPointY;
  private BigInteger secondCoefB;
  private ASN1ObjectIdentifier usage;
  
  public ECDSAPublicKey(ASN1ObjectIdentifier paramASN1ObjectIdentifier, BigInteger paramBigInteger1, BigInteger paramBigInteger2, BigInteger paramBigInteger3, byte[] paramArrayOfByte1, BigInteger paramBigInteger4, byte[] paramArrayOfByte2, int paramInt)
  {
    this.usage = paramASN1ObjectIdentifier;
    setPrimeModulusP(paramBigInteger1);
    setFirstCoefA(paramBigInteger2);
    setSecondCoefB(paramBigInteger3);
    setBasePointG(new DEROctetString(paramArrayOfByte1));
    setOrderOfBasePointR(paramBigInteger4);
    setPublicPointY(new DEROctetString(paramArrayOfByte2));
    setCofactorF(BigInteger.valueOf(paramInt));
  }
  
  public ECDSAPublicKey(ASN1ObjectIdentifier paramASN1ObjectIdentifier, byte[] paramArrayOfByte)
    throws IllegalArgumentException
  {
    this.usage = paramASN1ObjectIdentifier;
    setPublicPointY(new DEROctetString(paramArrayOfByte));
  }
  
  ECDSAPublicKey(ASN1Sequence paramASN1Sequence)
    throws IllegalArgumentException
  {
    Enumeration localEnumeration = paramASN1Sequence.getObjects();
    this.usage = ASN1ObjectIdentifier.getInstance(localEnumeration.nextElement());
    this.options = 0;
    while (localEnumeration.hasMoreElements())
    {
      Object localObject = localEnumeration.nextElement();
      if ((localObject instanceof ASN1TaggedObject))
      {
        ASN1TaggedObject localASN1TaggedObject = (ASN1TaggedObject)localObject;
        switch (localASN1TaggedObject.getTagNo())
        {
        default: 
          this.options = 0;
          throw new IllegalArgumentException("Unknown Object Identifier!");
        case 1: 
          setPrimeModulusP(UnsignedInteger.getInstance(localASN1TaggedObject).getValue());
          break;
        case 2: 
          setFirstCoefA(UnsignedInteger.getInstance(localASN1TaggedObject).getValue());
          break;
        case 3: 
          setSecondCoefB(UnsignedInteger.getInstance(localASN1TaggedObject).getValue());
          break;
        case 4: 
          setBasePointG(ASN1OctetString.getInstance(localASN1TaggedObject, false));
          break;
        case 5: 
          setOrderOfBasePointR(UnsignedInteger.getInstance(localASN1TaggedObject).getValue());
          break;
        case 6: 
          setPublicPointY(ASN1OctetString.getInstance(localASN1TaggedObject, false));
          break;
        case 7: 
          setCofactorF(UnsignedInteger.getInstance(localASN1TaggedObject).getValue());
          break;
        }
      }
      else
      {
        throw new IllegalArgumentException("Unknown Object Identifier!");
      }
    }
    if ((this.options != 32) && (this.options != 127)) {
      throw new IllegalArgumentException("All options must be either present or absent!");
    }
  }
  
  private void setBasePointG(ASN1OctetString paramASN1OctetString)
    throws IllegalArgumentException
  {
    if ((0x8 & this.options) == 0)
    {
      this.options = (0x8 | this.options);
      this.basePointG = paramASN1OctetString.getOctets();
      return;
    }
    throw new IllegalArgumentException("Base Point G already set");
  }
  
  private void setCofactorF(BigInteger paramBigInteger)
    throws IllegalArgumentException
  {
    if ((0x40 & this.options) == 0)
    {
      this.options = (0x40 | this.options);
      this.cofactorF = paramBigInteger;
      return;
    }
    throw new IllegalArgumentException("Cofactor F already set");
  }
  
  private void setFirstCoefA(BigInteger paramBigInteger)
    throws IllegalArgumentException
  {
    if ((0x2 & this.options) == 0)
    {
      this.options = (0x2 | this.options);
      this.firstCoefA = paramBigInteger;
      return;
    }
    throw new IllegalArgumentException("First Coef A already set");
  }
  
  private void setOrderOfBasePointR(BigInteger paramBigInteger)
    throws IllegalArgumentException
  {
    if ((0x10 & this.options) == 0)
    {
      this.options = (0x10 | this.options);
      this.orderOfBasePointR = paramBigInteger;
      return;
    }
    throw new IllegalArgumentException("Order of base point R already set");
  }
  
  private void setPrimeModulusP(BigInteger paramBigInteger)
  {
    if ((0x1 & this.options) == 0)
    {
      this.options = (0x1 | this.options);
      this.primeModulusP = paramBigInteger;
      return;
    }
    throw new IllegalArgumentException("Prime Modulus P already set");
  }
  
  private void setPublicPointY(ASN1OctetString paramASN1OctetString)
    throws IllegalArgumentException
  {
    if ((0x20 & this.options) == 0)
    {
      this.options = (0x20 | this.options);
      this.publicPointY = paramASN1OctetString.getOctets();
      return;
    }
    throw new IllegalArgumentException("Public Point Y already set");
  }
  
  private void setSecondCoefB(BigInteger paramBigInteger)
    throws IllegalArgumentException
  {
    if ((0x4 & this.options) == 0)
    {
      this.options = (0x4 | this.options);
      this.secondCoefB = paramBigInteger;
      return;
    }
    throw new IllegalArgumentException("Second Coef B already set");
  }
  
  public ASN1EncodableVector getASN1EncodableVector(ASN1ObjectIdentifier paramASN1ObjectIdentifier, boolean paramBoolean)
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(paramASN1ObjectIdentifier);
    if (!paramBoolean)
    {
      localASN1EncodableVector.add(new UnsignedInteger(1, getPrimeModulusP()));
      localASN1EncodableVector.add(new UnsignedInteger(2, getFirstCoefA()));
      localASN1EncodableVector.add(new UnsignedInteger(3, getSecondCoefB()));
      localASN1EncodableVector.add(new DERTaggedObject(false, 4, new DEROctetString(getBasePointG())));
      localASN1EncodableVector.add(new UnsignedInteger(5, getOrderOfBasePointR()));
    }
    localASN1EncodableVector.add(new DERTaggedObject(false, 6, new DEROctetString(getPublicPointY())));
    if (!paramBoolean) {
      localASN1EncodableVector.add(new UnsignedInteger(7, getCofactorF()));
    }
    return localASN1EncodableVector;
  }
  
  public byte[] getBasePointG()
  {
    if ((0x8 & this.options) != 0) {
      return this.basePointG;
    }
    return null;
  }
  
  public BigInteger getCofactorF()
  {
    if ((0x40 & this.options) != 0) {
      return this.cofactorF;
    }
    return null;
  }
  
  public BigInteger getFirstCoefA()
  {
    if ((0x2 & this.options) != 0) {
      return this.firstCoefA;
    }
    return null;
  }
  
  public BigInteger getOrderOfBasePointR()
  {
    if ((0x10 & this.options) != 0) {
      return this.orderOfBasePointR;
    }
    return null;
  }
  
  public BigInteger getPrimeModulusP()
  {
    if ((0x1 & this.options) != 0) {
      return this.primeModulusP;
    }
    return null;
  }
  
  public byte[] getPublicPointY()
  {
    if ((0x20 & this.options) != 0) {
      return this.publicPointY;
    }
    return null;
  }
  
  public BigInteger getSecondCoefB()
  {
    if ((0x4 & this.options) != 0) {
      return this.secondCoefB;
    }
    return null;
  }
  
  public ASN1ObjectIdentifier getUsage()
  {
    return this.usage;
  }
  
  public boolean hasParameters()
  {
    return this.primeModulusP != null;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return new DERSequence(getASN1EncodableVector(this.usage, false));
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.eac.ECDSAPublicKey
 * JD-Core Version:    0.7.0.1
 */