package org.spongycastle.asn1.pkcs;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.BERSequence;
import org.spongycastle.asn1.BERTaggedObject;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public class EncryptedData
  extends ASN1Object
{
  ASN1ObjectIdentifier bagId;
  ASN1Primitive bagValue;
  ASN1Sequence data;
  
  public EncryptedData(ASN1ObjectIdentifier paramASN1ObjectIdentifier, AlgorithmIdentifier paramAlgorithmIdentifier, ASN1Encodable paramASN1Encodable)
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(paramASN1ObjectIdentifier);
    localASN1EncodableVector.add(paramAlgorithmIdentifier.toASN1Primitive());
    localASN1EncodableVector.add(new BERTaggedObject(false, 0, paramASN1Encodable));
    this.data = new BERSequence(localASN1EncodableVector);
  }
  
  private EncryptedData(ASN1Sequence paramASN1Sequence)
  {
    if (((ASN1Integer)paramASN1Sequence.getObjectAt(0)).getValue().intValue() != 0) {
      throw new IllegalArgumentException("sequence not version 0");
    }
    this.data = ASN1Sequence.getInstance(paramASN1Sequence.getObjectAt(1));
  }
  
  public static EncryptedData getInstance(Object paramObject)
  {
    if ((paramObject instanceof EncryptedData)) {
      return (EncryptedData)paramObject;
    }
    if (paramObject != null) {
      return new EncryptedData(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public ASN1OctetString getContent()
  {
    if (this.data.size() == 3) {
      return ASN1OctetString.getInstance(ASN1TaggedObject.getInstance(this.data.getObjectAt(2)), false);
    }
    return null;
  }
  
  public ASN1ObjectIdentifier getContentType()
  {
    return ASN1ObjectIdentifier.getInstance(this.data.getObjectAt(0));
  }
  
  public AlgorithmIdentifier getEncryptionAlgorithm()
  {
    return AlgorithmIdentifier.getInstance(this.data.getObjectAt(1));
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(new ASN1Integer(0));
    localASN1EncodableVector.add(this.data);
    return new BERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.pkcs.EncryptedData
 * JD-Core Version:    0.7.0.1
 */