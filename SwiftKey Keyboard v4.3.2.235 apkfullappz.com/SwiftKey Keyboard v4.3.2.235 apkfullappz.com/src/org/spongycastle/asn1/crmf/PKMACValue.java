package org.spongycastle.asn1.crmf;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.cmp.CMPObjectIdentifiers;
import org.spongycastle.asn1.cmp.PBMParameter;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public class PKMACValue
  extends ASN1Object
{
  private AlgorithmIdentifier algId;
  private DERBitString value;
  
  private PKMACValue(ASN1Sequence paramASN1Sequence)
  {
    this.algId = AlgorithmIdentifier.getInstance(paramASN1Sequence.getObjectAt(0));
    this.value = DERBitString.getInstance(paramASN1Sequence.getObjectAt(1));
  }
  
  public PKMACValue(PBMParameter paramPBMParameter, DERBitString paramDERBitString)
  {
    this(new AlgorithmIdentifier(CMPObjectIdentifiers.passwordBasedMac, paramPBMParameter), paramDERBitString);
  }
  
  public PKMACValue(AlgorithmIdentifier paramAlgorithmIdentifier, DERBitString paramDERBitString)
  {
    this.algId = paramAlgorithmIdentifier;
    this.value = paramDERBitString;
  }
  
  public static PKMACValue getInstance(Object paramObject)
  {
    if ((paramObject instanceof PKMACValue)) {
      return (PKMACValue)paramObject;
    }
    if (paramObject != null) {
      return new PKMACValue(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public static PKMACValue getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    return getInstance(ASN1Sequence.getInstance(paramASN1TaggedObject, paramBoolean));
  }
  
  public AlgorithmIdentifier getAlgId()
  {
    return this.algId;
  }
  
  public DERBitString getValue()
  {
    return this.value;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.algId);
    localASN1EncodableVector.add(this.value);
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.crmf.PKMACValue
 * JD-Core Version:    0.7.0.1
 */