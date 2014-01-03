package org.spongycastle.asn1.cms;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.BERSequence;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public class CompressedData
  extends ASN1Object
{
  private AlgorithmIdentifier compressionAlgorithm;
  private ContentInfo encapContentInfo;
  private ASN1Integer version;
  
  public CompressedData(ASN1Sequence paramASN1Sequence)
  {
    this.version = ((ASN1Integer)paramASN1Sequence.getObjectAt(0));
    this.compressionAlgorithm = AlgorithmIdentifier.getInstance(paramASN1Sequence.getObjectAt(1));
    this.encapContentInfo = ContentInfo.getInstance(paramASN1Sequence.getObjectAt(2));
  }
  
  public CompressedData(AlgorithmIdentifier paramAlgorithmIdentifier, ContentInfo paramContentInfo)
  {
    this.version = new ASN1Integer(0);
    this.compressionAlgorithm = paramAlgorithmIdentifier;
    this.encapContentInfo = paramContentInfo;
  }
  
  public static CompressedData getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof CompressedData))) {
      return (CompressedData)paramObject;
    }
    if ((paramObject instanceof ASN1Sequence)) {
      return new CompressedData((ASN1Sequence)paramObject);
    }
    throw new IllegalArgumentException("Invalid CompressedData: " + paramObject.getClass().getName());
  }
  
  public static CompressedData getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    return getInstance(ASN1Sequence.getInstance(paramASN1TaggedObject, paramBoolean));
  }
  
  public AlgorithmIdentifier getCompressionAlgorithmIdentifier()
  {
    return this.compressionAlgorithm;
  }
  
  public ContentInfo getEncapContentInfo()
  {
    return this.encapContentInfo;
  }
  
  public ASN1Integer getVersion()
  {
    return this.version;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.version);
    localASN1EncodableVector.add(this.compressionAlgorithm);
    localASN1EncodableVector.add(this.encapContentInfo);
    return new BERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.cms.CompressedData
 * JD-Core Version:    0.7.0.1
 */