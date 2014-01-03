package org.spongycastle.asn1.crmf;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public class POPOSigningKey
  extends ASN1Object
{
  private AlgorithmIdentifier algorithmIdentifier;
  private POPOSigningKeyInput poposkInput;
  private DERBitString signature;
  
  private POPOSigningKey(ASN1Sequence paramASN1Sequence)
  {
    boolean bool = paramASN1Sequence.getObjectAt(0) instanceof ASN1TaggedObject;
    int i = 0;
    if (bool)
    {
      i = 0 + 1;
      ASN1TaggedObject localASN1TaggedObject = (ASN1TaggedObject)paramASN1Sequence.getObjectAt(0);
      if (localASN1TaggedObject.getTagNo() != 0) {
        throw new IllegalArgumentException("Unknown POPOSigningKeyInput tag: " + localASN1TaggedObject.getTagNo());
      }
      this.poposkInput = POPOSigningKeyInput.getInstance(localASN1TaggedObject.getObject());
    }
    int j = i + 1;
    this.algorithmIdentifier = AlgorithmIdentifier.getInstance(paramASN1Sequence.getObjectAt(i));
    this.signature = DERBitString.getInstance(paramASN1Sequence.getObjectAt(j));
  }
  
  public POPOSigningKey(POPOSigningKeyInput paramPOPOSigningKeyInput, AlgorithmIdentifier paramAlgorithmIdentifier, DERBitString paramDERBitString)
  {
    this.poposkInput = paramPOPOSigningKeyInput;
    this.algorithmIdentifier = paramAlgorithmIdentifier;
    this.signature = paramDERBitString;
  }
  
  public static POPOSigningKey getInstance(Object paramObject)
  {
    if ((paramObject instanceof POPOSigningKey)) {
      return (POPOSigningKey)paramObject;
    }
    if (paramObject != null) {
      return new POPOSigningKey(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public static POPOSigningKey getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    return getInstance(ASN1Sequence.getInstance(paramASN1TaggedObject, paramBoolean));
  }
  
  public AlgorithmIdentifier getAlgorithmIdentifier()
  {
    return this.algorithmIdentifier;
  }
  
  public POPOSigningKeyInput getPoposkInput()
  {
    return this.poposkInput;
  }
  
  public DERBitString getSignature()
  {
    return this.signature;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    if (this.poposkInput != null) {
      localASN1EncodableVector.add(new DERTaggedObject(false, 0, this.poposkInput));
    }
    localASN1EncodableVector.add(this.algorithmIdentifier);
    localASN1EncodableVector.add(this.signature);
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.crmf.POPOSigningKey
 * JD-Core Version:    0.7.0.1
 */