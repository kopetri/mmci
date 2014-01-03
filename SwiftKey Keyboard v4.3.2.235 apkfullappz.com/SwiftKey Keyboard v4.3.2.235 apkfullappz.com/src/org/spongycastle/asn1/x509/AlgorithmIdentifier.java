package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1SequenceParser;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.DERObjectIdentifier;
import org.spongycastle.asn1.DERSequence;

public class AlgorithmIdentifier
  extends ASN1Object
{
  private ASN1ObjectIdentifier objectId;
  private ASN1Encodable parameters;
  private boolean parametersDefined = false;
  
  public AlgorithmIdentifier(String paramString)
  {
    this.objectId = new ASN1ObjectIdentifier(paramString);
  }
  
  public AlgorithmIdentifier(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
  {
    this.objectId = paramASN1ObjectIdentifier;
  }
  
  public AlgorithmIdentifier(ASN1ObjectIdentifier paramASN1ObjectIdentifier, ASN1Encodable paramASN1Encodable)
  {
    this.parametersDefined = true;
    this.objectId = paramASN1ObjectIdentifier;
    this.parameters = paramASN1Encodable;
  }
  
  public AlgorithmIdentifier(ASN1Sequence paramASN1Sequence)
  {
    if ((paramASN1Sequence.size() <= 0) || (paramASN1Sequence.size() > 2)) {
      throw new IllegalArgumentException("Bad sequence size: " + paramASN1Sequence.size());
    }
    this.objectId = ASN1ObjectIdentifier.getInstance(paramASN1Sequence.getObjectAt(0));
    if (paramASN1Sequence.size() == 2)
    {
      this.parametersDefined = true;
      this.parameters = paramASN1Sequence.getObjectAt(1);
      return;
    }
    this.parameters = null;
  }
  
  public AlgorithmIdentifier(DERObjectIdentifier paramDERObjectIdentifier)
  {
    this.objectId = new ASN1ObjectIdentifier(paramDERObjectIdentifier.getId());
  }
  
  public AlgorithmIdentifier(DERObjectIdentifier paramDERObjectIdentifier, ASN1Encodable paramASN1Encodable)
  {
    this.parametersDefined = true;
    this.objectId = new ASN1ObjectIdentifier(paramDERObjectIdentifier.getId());
    this.parameters = paramASN1Encodable;
  }
  
  public static AlgorithmIdentifier getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof AlgorithmIdentifier))) {
      return (AlgorithmIdentifier)paramObject;
    }
    if ((paramObject instanceof ASN1ObjectIdentifier)) {
      return new AlgorithmIdentifier((ASN1ObjectIdentifier)paramObject);
    }
    if ((paramObject instanceof String)) {
      return new AlgorithmIdentifier((String)paramObject);
    }
    if (((paramObject instanceof ASN1Sequence)) || ((paramObject instanceof ASN1SequenceParser))) {
      return new AlgorithmIdentifier(ASN1Sequence.getInstance(paramObject));
    }
    throw new IllegalArgumentException("unknown object in factory: " + paramObject.getClass().getName());
  }
  
  public static AlgorithmIdentifier getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    return getInstance(ASN1Sequence.getInstance(paramASN1TaggedObject, paramBoolean));
  }
  
  public ASN1ObjectIdentifier getAlgorithm()
  {
    return new ASN1ObjectIdentifier(this.objectId.getId());
  }
  
  public ASN1ObjectIdentifier getObjectId()
  {
    return this.objectId;
  }
  
  public ASN1Encodable getParameters()
  {
    return this.parameters;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.objectId);
    if (this.parametersDefined)
    {
      if (this.parameters == null) {
        break label47;
      }
      localASN1EncodableVector.add(this.parameters);
    }
    for (;;)
    {
      return new DERSequence(localASN1EncodableVector);
      label47:
      localASN1EncodableVector.add(DERNull.INSTANCE);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x509.AlgorithmIdentifier
 * JD-Core Version:    0.7.0.1
 */