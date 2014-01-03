package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.digests.SHA1Digest;

public class SubjectKeyIdentifier
  extends ASN1Object
{
  private byte[] keyidentifier;
  
  protected SubjectKeyIdentifier(ASN1OctetString paramASN1OctetString)
  {
    this.keyidentifier = paramASN1OctetString.getOctets();
  }
  
  public SubjectKeyIdentifier(SubjectPublicKeyInfo paramSubjectPublicKeyInfo)
  {
    this.keyidentifier = getDigest(paramSubjectPublicKeyInfo);
  }
  
  public SubjectKeyIdentifier(byte[] paramArrayOfByte)
  {
    this.keyidentifier = paramArrayOfByte;
  }
  
  public static SubjectKeyIdentifier createSHA1KeyIdentifier(SubjectPublicKeyInfo paramSubjectPublicKeyInfo)
  {
    return new SubjectKeyIdentifier(paramSubjectPublicKeyInfo);
  }
  
  public static SubjectKeyIdentifier createTruncatedSHA1KeyIdentifier(SubjectPublicKeyInfo paramSubjectPublicKeyInfo)
  {
    byte[] arrayOfByte1 = getDigest(paramSubjectPublicKeyInfo);
    byte[] arrayOfByte2 = new byte[8];
    System.arraycopy(arrayOfByte1, -8 + arrayOfByte1.length, arrayOfByte2, 0, arrayOfByte2.length);
    arrayOfByte2[0] = ((byte)(0xF & arrayOfByte2[0]));
    arrayOfByte2[0] = ((byte)(0x40 | arrayOfByte2[0]));
    return new SubjectKeyIdentifier(arrayOfByte2);
  }
  
  private static byte[] getDigest(SubjectPublicKeyInfo paramSubjectPublicKeyInfo)
  {
    SHA1Digest localSHA1Digest = new SHA1Digest();
    byte[] arrayOfByte1 = new byte[localSHA1Digest.getDigestSize()];
    byte[] arrayOfByte2 = paramSubjectPublicKeyInfo.getPublicKeyData().getBytes();
    localSHA1Digest.update(arrayOfByte2, 0, arrayOfByte2.length);
    localSHA1Digest.doFinal(arrayOfByte1, 0);
    return arrayOfByte1;
  }
  
  public static SubjectKeyIdentifier getInstance(Object paramObject)
  {
    if ((paramObject instanceof SubjectKeyIdentifier)) {
      return (SubjectKeyIdentifier)paramObject;
    }
    if (paramObject != null) {
      return new SubjectKeyIdentifier(ASN1OctetString.getInstance(paramObject));
    }
    return null;
  }
  
  public static SubjectKeyIdentifier getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    return getInstance(ASN1OctetString.getInstance(paramASN1TaggedObject, paramBoolean));
  }
  
  public byte[] getKeyIdentifier()
  {
    return this.keyidentifier;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return new DEROctetString(this.keyidentifier);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x509.SubjectKeyIdentifier
 * JD-Core Version:    0.7.0.1
 */