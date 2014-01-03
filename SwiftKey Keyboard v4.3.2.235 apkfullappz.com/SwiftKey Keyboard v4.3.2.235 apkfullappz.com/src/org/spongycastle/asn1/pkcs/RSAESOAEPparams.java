package org.spongycastle.asn1.pkcs;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.oiw.OIWObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public class RSAESOAEPparams
  extends ASN1Object
{
  public static final AlgorithmIdentifier DEFAULT_HASH_ALGORITHM = new AlgorithmIdentifier(OIWObjectIdentifiers.idSHA1, new DERNull());
  public static final AlgorithmIdentifier DEFAULT_MASK_GEN_FUNCTION = new AlgorithmIdentifier(PKCSObjectIdentifiers.id_mgf1, DEFAULT_HASH_ALGORITHM);
  public static final AlgorithmIdentifier DEFAULT_P_SOURCE_ALGORITHM = new AlgorithmIdentifier(PKCSObjectIdentifiers.id_pSpecified, new DEROctetString(new byte[0]));
  private AlgorithmIdentifier hashAlgorithm;
  private AlgorithmIdentifier maskGenAlgorithm;
  private AlgorithmIdentifier pSourceAlgorithm;
  
  public RSAESOAEPparams()
  {
    this.hashAlgorithm = DEFAULT_HASH_ALGORITHM;
    this.maskGenAlgorithm = DEFAULT_MASK_GEN_FUNCTION;
    this.pSourceAlgorithm = DEFAULT_P_SOURCE_ALGORITHM;
  }
  
  public RSAESOAEPparams(ASN1Sequence paramASN1Sequence)
  {
    this.hashAlgorithm = DEFAULT_HASH_ALGORITHM;
    this.maskGenAlgorithm = DEFAULT_MASK_GEN_FUNCTION;
    this.pSourceAlgorithm = DEFAULT_P_SOURCE_ALGORITHM;
    int i = 0;
    if (i != paramASN1Sequence.size())
    {
      ASN1TaggedObject localASN1TaggedObject = (ASN1TaggedObject)paramASN1Sequence.getObjectAt(i);
      switch (localASN1TaggedObject.getTagNo())
      {
      default: 
        throw new IllegalArgumentException("unknown tag");
      case 0: 
        this.hashAlgorithm = AlgorithmIdentifier.getInstance(localASN1TaggedObject, true);
      }
      for (;;)
      {
        i++;
        break;
        this.maskGenAlgorithm = AlgorithmIdentifier.getInstance(localASN1TaggedObject, true);
        continue;
        this.pSourceAlgorithm = AlgorithmIdentifier.getInstance(localASN1TaggedObject, true);
      }
    }
  }
  
  public RSAESOAEPparams(AlgorithmIdentifier paramAlgorithmIdentifier1, AlgorithmIdentifier paramAlgorithmIdentifier2, AlgorithmIdentifier paramAlgorithmIdentifier3)
  {
    this.hashAlgorithm = paramAlgorithmIdentifier1;
    this.maskGenAlgorithm = paramAlgorithmIdentifier2;
    this.pSourceAlgorithm = paramAlgorithmIdentifier3;
  }
  
  public static RSAESOAEPparams getInstance(Object paramObject)
  {
    if ((paramObject instanceof RSAESOAEPparams)) {
      return (RSAESOAEPparams)paramObject;
    }
    if (paramObject != null) {
      return new RSAESOAEPparams(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public AlgorithmIdentifier getHashAlgorithm()
  {
    return this.hashAlgorithm;
  }
  
  public AlgorithmIdentifier getMaskGenAlgorithm()
  {
    return this.maskGenAlgorithm;
  }
  
  public AlgorithmIdentifier getPSourceAlgorithm()
  {
    return this.pSourceAlgorithm;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    if (!this.hashAlgorithm.equals(DEFAULT_HASH_ALGORITHM)) {
      localASN1EncodableVector.add(new DERTaggedObject(true, 0, this.hashAlgorithm));
    }
    if (!this.maskGenAlgorithm.equals(DEFAULT_MASK_GEN_FUNCTION)) {
      localASN1EncodableVector.add(new DERTaggedObject(true, 1, this.maskGenAlgorithm));
    }
    if (!this.pSourceAlgorithm.equals(DEFAULT_P_SOURCE_ALGORITHM)) {
      localASN1EncodableVector.add(new DERTaggedObject(true, 2, this.pSourceAlgorithm));
    }
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.pkcs.RSAESOAEPparams
 * JD-Core Version:    0.7.0.1
 */