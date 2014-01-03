package org.spongycastle.asn1.x509;

import java.io.IOException;
import java.util.Enumeration;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERSequence;

public class SubjectPublicKeyInfo
  extends ASN1Object
{
  private AlgorithmIdentifier algId;
  private DERBitString keyData;
  
  public SubjectPublicKeyInfo(ASN1Sequence paramASN1Sequence)
  {
    if (paramASN1Sequence.size() != 2) {
      throw new IllegalArgumentException("Bad sequence size: " + paramASN1Sequence.size());
    }
    Enumeration localEnumeration = paramASN1Sequence.getObjects();
    this.algId = AlgorithmIdentifier.getInstance(localEnumeration.nextElement());
    this.keyData = DERBitString.getInstance(localEnumeration.nextElement());
  }
  
  public SubjectPublicKeyInfo(AlgorithmIdentifier paramAlgorithmIdentifier, ASN1Encodable paramASN1Encodable)
  {
    this.keyData = new DERBitString(paramASN1Encodable);
    this.algId = paramAlgorithmIdentifier;
  }
  
  public SubjectPublicKeyInfo(AlgorithmIdentifier paramAlgorithmIdentifier, byte[] paramArrayOfByte)
  {
    this.keyData = new DERBitString(paramArrayOfByte);
    this.algId = paramAlgorithmIdentifier;
  }
  
  public static SubjectPublicKeyInfo getInstance(Object paramObject)
  {
    if ((paramObject instanceof SubjectPublicKeyInfo)) {
      return (SubjectPublicKeyInfo)paramObject;
    }
    if (paramObject != null) {
      return new SubjectPublicKeyInfo(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public static SubjectPublicKeyInfo getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    return getInstance(ASN1Sequence.getInstance(paramASN1TaggedObject, paramBoolean));
  }
  
  public AlgorithmIdentifier getAlgorithm()
  {
    return this.algId;
  }
  
  public AlgorithmIdentifier getAlgorithmId()
  {
    return this.algId;
  }
  
  public ASN1Primitive getPublicKey()
    throws IOException
  {
    return new ASN1InputStream(this.keyData.getBytes()).readObject();
  }
  
  public DERBitString getPublicKeyData()
  {
    return this.keyData;
  }
  
  public ASN1Primitive parsePublicKey()
    throws IOException
  {
    return new ASN1InputStream(this.keyData.getBytes()).readObject();
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.algId);
    localASN1EncodableVector.add(this.keyData);
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x509.SubjectPublicKeyInfo
 * JD-Core Version:    0.7.0.1
 */