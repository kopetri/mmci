package org.spongycastle.asn1.pkcs;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public class EncryptedPrivateKeyInfo
  extends ASN1Object
{
  private AlgorithmIdentifier algId;
  private ASN1OctetString data;
  
  private EncryptedPrivateKeyInfo(ASN1Sequence paramASN1Sequence)
  {
    Enumeration localEnumeration = paramASN1Sequence.getObjects();
    this.algId = AlgorithmIdentifier.getInstance(localEnumeration.nextElement());
    this.data = ASN1OctetString.getInstance(localEnumeration.nextElement());
  }
  
  public EncryptedPrivateKeyInfo(AlgorithmIdentifier paramAlgorithmIdentifier, byte[] paramArrayOfByte)
  {
    this.algId = paramAlgorithmIdentifier;
    this.data = new DEROctetString(paramArrayOfByte);
  }
  
  public static EncryptedPrivateKeyInfo getInstance(Object paramObject)
  {
    if ((paramObject instanceof EncryptedData)) {
      return (EncryptedPrivateKeyInfo)paramObject;
    }
    if (paramObject != null) {
      return new EncryptedPrivateKeyInfo(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public byte[] getEncryptedData()
  {
    return this.data.getOctets();
  }
  
  public AlgorithmIdentifier getEncryptionAlgorithm()
  {
    return this.algId;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.algId);
    localASN1EncodableVector.add(this.data);
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.pkcs.EncryptedPrivateKeyInfo
 * JD-Core Version:    0.7.0.1
 */