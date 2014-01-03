package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Enumerated;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DEREnumerated;
import org.spongycastle.asn1.DERSequence;

public class ObjectDigestInfo
  extends ASN1Object
{
  public static final int otherObjectDigest = 2;
  public static final int publicKey = 0;
  public static final int publicKeyCert = 1;
  AlgorithmIdentifier digestAlgorithm;
  ASN1Enumerated digestedObjectType;
  DERBitString objectDigest;
  ASN1ObjectIdentifier otherObjectTypeID;
  
  public ObjectDigestInfo(int paramInt, ASN1ObjectIdentifier paramASN1ObjectIdentifier, AlgorithmIdentifier paramAlgorithmIdentifier, byte[] paramArrayOfByte)
  {
    this.digestedObjectType = new ASN1Enumerated(paramInt);
    if (paramInt == 2) {
      this.otherObjectTypeID = paramASN1ObjectIdentifier;
    }
    this.digestAlgorithm = paramAlgorithmIdentifier;
    this.objectDigest = new DERBitString(paramArrayOfByte);
  }
  
  private ObjectDigestInfo(ASN1Sequence paramASN1Sequence)
  {
    if ((paramASN1Sequence.size() > 4) || (paramASN1Sequence.size() < 3)) {
      throw new IllegalArgumentException("Bad sequence size: " + paramASN1Sequence.size());
    }
    this.digestedObjectType = DEREnumerated.getInstance(paramASN1Sequence.getObjectAt(0));
    int i = paramASN1Sequence.size();
    int j = 0;
    if (i == 4)
    {
      this.otherObjectTypeID = ASN1ObjectIdentifier.getInstance(paramASN1Sequence.getObjectAt(1));
      j = 0 + 1;
    }
    this.digestAlgorithm = AlgorithmIdentifier.getInstance(paramASN1Sequence.getObjectAt(j + 1));
    this.objectDigest = DERBitString.getInstance(paramASN1Sequence.getObjectAt(j + 2));
  }
  
  public static ObjectDigestInfo getInstance(Object paramObject)
  {
    if ((paramObject instanceof ObjectDigestInfo)) {
      return (ObjectDigestInfo)paramObject;
    }
    if (paramObject != null) {
      return new ObjectDigestInfo(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public static ObjectDigestInfo getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    return getInstance(ASN1Sequence.getInstance(paramASN1TaggedObject, paramBoolean));
  }
  
  public AlgorithmIdentifier getDigestAlgorithm()
  {
    return this.digestAlgorithm;
  }
  
  public DEREnumerated getDigestedObjectType()
  {
    return this.digestedObjectType;
  }
  
  public DERBitString getObjectDigest()
  {
    return this.objectDigest;
  }
  
  public ASN1ObjectIdentifier getOtherObjectTypeID()
  {
    return this.otherObjectTypeID;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.digestedObjectType);
    if (this.otherObjectTypeID != null) {
      localASN1EncodableVector.add(this.otherObjectTypeID);
    }
    localASN1EncodableVector.add(this.digestAlgorithm);
    localASN1EncodableVector.add(this.objectDigest);
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x509.ObjectDigestInfo
 * JD-Core Version:    0.7.0.1
 */