package org.spongycastle.asn1.esf;

import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.oiw.OIWObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public class OtherHash
  extends ASN1Object
  implements ASN1Choice
{
  private OtherHashAlgAndValue otherHash;
  private ASN1OctetString sha1Hash;
  
  private OtherHash(ASN1OctetString paramASN1OctetString)
  {
    this.sha1Hash = paramASN1OctetString;
  }
  
  public OtherHash(OtherHashAlgAndValue paramOtherHashAlgAndValue)
  {
    this.otherHash = paramOtherHashAlgAndValue;
  }
  
  public OtherHash(byte[] paramArrayOfByte)
  {
    this.sha1Hash = new DEROctetString(paramArrayOfByte);
  }
  
  public static OtherHash getInstance(Object paramObject)
  {
    if ((paramObject instanceof OtherHash)) {
      return (OtherHash)paramObject;
    }
    if ((paramObject instanceof ASN1OctetString)) {
      return new OtherHash((ASN1OctetString)paramObject);
    }
    return new OtherHash(OtherHashAlgAndValue.getInstance(paramObject));
  }
  
  public AlgorithmIdentifier getHashAlgorithm()
  {
    if (this.otherHash == null) {
      return new AlgorithmIdentifier(OIWObjectIdentifiers.idSHA1);
    }
    return this.otherHash.getHashAlgorithm();
  }
  
  public byte[] getHashValue()
  {
    if (this.otherHash == null) {
      return this.sha1Hash.getOctets();
    }
    return this.otherHash.getHashValue().getOctets();
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    if (this.otherHash == null) {
      return this.sha1Hash;
    }
    return this.otherHash.toASN1Primitive();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.esf.OtherHash
 * JD-Core Version:    0.7.0.1
 */