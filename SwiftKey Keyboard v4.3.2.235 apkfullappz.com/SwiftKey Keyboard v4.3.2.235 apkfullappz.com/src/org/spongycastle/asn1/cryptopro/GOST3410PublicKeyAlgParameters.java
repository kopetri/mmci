package org.spongycastle.asn1.cryptopro;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;

public class GOST3410PublicKeyAlgParameters
  extends ASN1Object
{
  private ASN1ObjectIdentifier digestParamSet;
  private ASN1ObjectIdentifier encryptionParamSet;
  private ASN1ObjectIdentifier publicKeyParamSet;
  
  public GOST3410PublicKeyAlgParameters(ASN1ObjectIdentifier paramASN1ObjectIdentifier1, ASN1ObjectIdentifier paramASN1ObjectIdentifier2)
  {
    this.publicKeyParamSet = paramASN1ObjectIdentifier1;
    this.digestParamSet = paramASN1ObjectIdentifier2;
    this.encryptionParamSet = null;
  }
  
  public GOST3410PublicKeyAlgParameters(ASN1ObjectIdentifier paramASN1ObjectIdentifier1, ASN1ObjectIdentifier paramASN1ObjectIdentifier2, ASN1ObjectIdentifier paramASN1ObjectIdentifier3)
  {
    this.publicKeyParamSet = paramASN1ObjectIdentifier1;
    this.digestParamSet = paramASN1ObjectIdentifier2;
    this.encryptionParamSet = paramASN1ObjectIdentifier3;
  }
  
  public GOST3410PublicKeyAlgParameters(ASN1Sequence paramASN1Sequence)
  {
    this.publicKeyParamSet = ((ASN1ObjectIdentifier)paramASN1Sequence.getObjectAt(0));
    this.digestParamSet = ((ASN1ObjectIdentifier)paramASN1Sequence.getObjectAt(1));
    if (paramASN1Sequence.size() > 2) {
      this.encryptionParamSet = ((ASN1ObjectIdentifier)paramASN1Sequence.getObjectAt(2));
    }
  }
  
  public static GOST3410PublicKeyAlgParameters getInstance(Object paramObject)
  {
    if ((paramObject instanceof GOST3410PublicKeyAlgParameters)) {
      return (GOST3410PublicKeyAlgParameters)paramObject;
    }
    if (paramObject != null) {
      return new GOST3410PublicKeyAlgParameters(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public static GOST3410PublicKeyAlgParameters getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    return getInstance(ASN1Sequence.getInstance(paramASN1TaggedObject, paramBoolean));
  }
  
  public ASN1ObjectIdentifier getDigestParamSet()
  {
    return this.digestParamSet;
  }
  
  public ASN1ObjectIdentifier getEncryptionParamSet()
  {
    return this.encryptionParamSet;
  }
  
  public ASN1ObjectIdentifier getPublicKeyParamSet()
  {
    return this.publicKeyParamSet;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.publicKeyParamSet);
    localASN1EncodableVector.add(this.digestParamSet);
    if (this.encryptionParamSet != null) {
      localASN1EncodableVector.add(this.encryptionParamSet);
    }
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.cryptopro.GOST3410PublicKeyAlgParameters
 * JD-Core Version:    0.7.0.1
 */