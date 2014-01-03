package org.spongycastle.asn1.x509;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;

public class DigestInfo
  extends ASN1Object
{
  private AlgorithmIdentifier algId;
  private byte[] digest;
  
  public DigestInfo(ASN1Sequence paramASN1Sequence)
  {
    Enumeration localEnumeration = paramASN1Sequence.getObjects();
    this.algId = AlgorithmIdentifier.getInstance(localEnumeration.nextElement());
    this.digest = ASN1OctetString.getInstance(localEnumeration.nextElement()).getOctets();
  }
  
  public DigestInfo(AlgorithmIdentifier paramAlgorithmIdentifier, byte[] paramArrayOfByte)
  {
    this.digest = paramArrayOfByte;
    this.algId = paramAlgorithmIdentifier;
  }
  
  public static DigestInfo getInstance(Object paramObject)
  {
    if ((paramObject instanceof DigestInfo)) {
      return (DigestInfo)paramObject;
    }
    if (paramObject != null) {
      return new DigestInfo(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public static DigestInfo getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    return getInstance(ASN1Sequence.getInstance(paramASN1TaggedObject, paramBoolean));
  }
  
  public AlgorithmIdentifier getAlgorithmId()
  {
    return this.algId;
  }
  
  public byte[] getDigest()
  {
    return this.digest;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.algId);
    localASN1EncodableVector.add(new DEROctetString(this.digest));
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x509.DigestInfo
 * JD-Core Version:    0.7.0.1
 */