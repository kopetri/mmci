package org.spongycastle.asn1.x509;

import java.math.BigInteger;
import java.util.Enumeration;
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
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.digests.SHA1Digest;

public class AuthorityKeyIdentifier
  extends ASN1Object
{
  GeneralNames certissuer = null;
  ASN1Integer certserno = null;
  ASN1OctetString keyidentifier = null;
  
  protected AuthorityKeyIdentifier(ASN1Sequence paramASN1Sequence)
  {
    Enumeration localEnumeration = paramASN1Sequence.getObjects();
    while (localEnumeration.hasMoreElements())
    {
      ASN1TaggedObject localASN1TaggedObject = DERTaggedObject.getInstance(localEnumeration.nextElement());
      switch (localASN1TaggedObject.getTagNo())
      {
      default: 
        throw new IllegalArgumentException("illegal tag");
      case 0: 
        this.keyidentifier = ASN1OctetString.getInstance(localASN1TaggedObject, false);
        break;
      case 1: 
        this.certissuer = GeneralNames.getInstance(localASN1TaggedObject, false);
        break;
      case 2: 
        this.certserno = ASN1Integer.getInstance(localASN1TaggedObject, false);
      }
    }
  }
  
  public AuthorityKeyIdentifier(GeneralNames paramGeneralNames, BigInteger paramBigInteger)
  {
    this.keyidentifier = null;
    this.certissuer = GeneralNames.getInstance(paramGeneralNames.toASN1Primitive());
    this.certserno = new ASN1Integer(paramBigInteger);
  }
  
  public AuthorityKeyIdentifier(SubjectPublicKeyInfo paramSubjectPublicKeyInfo)
  {
    SHA1Digest localSHA1Digest = new SHA1Digest();
    byte[] arrayOfByte1 = new byte[localSHA1Digest.getDigestSize()];
    byte[] arrayOfByte2 = paramSubjectPublicKeyInfo.getPublicKeyData().getBytes();
    localSHA1Digest.update(arrayOfByte2, 0, arrayOfByte2.length);
    localSHA1Digest.doFinal(arrayOfByte1, 0);
    this.keyidentifier = new DEROctetString(arrayOfByte1);
  }
  
  public AuthorityKeyIdentifier(SubjectPublicKeyInfo paramSubjectPublicKeyInfo, GeneralNames paramGeneralNames, BigInteger paramBigInteger)
  {
    SHA1Digest localSHA1Digest = new SHA1Digest();
    byte[] arrayOfByte1 = new byte[localSHA1Digest.getDigestSize()];
    byte[] arrayOfByte2 = paramSubjectPublicKeyInfo.getPublicKeyData().getBytes();
    localSHA1Digest.update(arrayOfByte2, 0, arrayOfByte2.length);
    localSHA1Digest.doFinal(arrayOfByte1, 0);
    this.keyidentifier = new DEROctetString(arrayOfByte1);
    this.certissuer = GeneralNames.getInstance(paramGeneralNames.toASN1Primitive());
    this.certserno = new ASN1Integer(paramBigInteger);
  }
  
  public AuthorityKeyIdentifier(byte[] paramArrayOfByte)
  {
    this.keyidentifier = new DEROctetString(paramArrayOfByte);
    this.certissuer = null;
    this.certserno = null;
  }
  
  public AuthorityKeyIdentifier(byte[] paramArrayOfByte, GeneralNames paramGeneralNames, BigInteger paramBigInteger)
  {
    this.keyidentifier = new DEROctetString(paramArrayOfByte);
    this.certissuer = GeneralNames.getInstance(paramGeneralNames.toASN1Primitive());
    this.certserno = new ASN1Integer(paramBigInteger);
  }
  
  public static AuthorityKeyIdentifier getInstance(Object paramObject)
  {
    if ((paramObject instanceof AuthorityKeyIdentifier)) {
      return (AuthorityKeyIdentifier)paramObject;
    }
    if (paramObject != null) {
      return new AuthorityKeyIdentifier(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public static AuthorityKeyIdentifier getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    return getInstance(ASN1Sequence.getInstance(paramASN1TaggedObject, paramBoolean));
  }
  
  public GeneralNames getAuthorityCertIssuer()
  {
    return this.certissuer;
  }
  
  public BigInteger getAuthorityCertSerialNumber()
  {
    if (this.certserno != null) {
      return this.certserno.getValue();
    }
    return null;
  }
  
  public byte[] getKeyIdentifier()
  {
    if (this.keyidentifier != null) {
      return this.keyidentifier.getOctets();
    }
    return null;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    if (this.keyidentifier != null) {
      localASN1EncodableVector.add(new DERTaggedObject(false, 0, this.keyidentifier));
    }
    if (this.certissuer != null) {
      localASN1EncodableVector.add(new DERTaggedObject(false, 1, this.certissuer));
    }
    if (this.certserno != null) {
      localASN1EncodableVector.add(new DERTaggedObject(false, 2, this.certserno));
    }
    return new DERSequence(localASN1EncodableVector);
  }
  
  public String toString()
  {
    return "AuthorityKeyIdentifier: KeyID(" + this.keyidentifier.getOctets() + ")";
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x509.AuthorityKeyIdentifier
 * JD-Core Version:    0.7.0.1
 */