package org.spongycastle.asn1.pkcs;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.oiw.OIWObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public class RSASSAPSSparams
  extends ASN1Object
{
  public static final AlgorithmIdentifier DEFAULT_HASH_ALGORITHM = new AlgorithmIdentifier(OIWObjectIdentifiers.idSHA1, new DERNull());
  public static final AlgorithmIdentifier DEFAULT_MASK_GEN_FUNCTION = new AlgorithmIdentifier(PKCSObjectIdentifiers.id_mgf1, DEFAULT_HASH_ALGORITHM);
  public static final ASN1Integer DEFAULT_SALT_LENGTH = new ASN1Integer(20);
  public static final ASN1Integer DEFAULT_TRAILER_FIELD = new ASN1Integer(1);
  private AlgorithmIdentifier hashAlgorithm;
  private AlgorithmIdentifier maskGenAlgorithm;
  private ASN1Integer saltLength;
  private ASN1Integer trailerField;
  
  public RSASSAPSSparams()
  {
    this.hashAlgorithm = DEFAULT_HASH_ALGORITHM;
    this.maskGenAlgorithm = DEFAULT_MASK_GEN_FUNCTION;
    this.saltLength = DEFAULT_SALT_LENGTH;
    this.trailerField = DEFAULT_TRAILER_FIELD;
  }
  
  private RSASSAPSSparams(ASN1Sequence paramASN1Sequence)
  {
    this.hashAlgorithm = DEFAULT_HASH_ALGORITHM;
    this.maskGenAlgorithm = DEFAULT_MASK_GEN_FUNCTION;
    this.saltLength = DEFAULT_SALT_LENGTH;
    this.trailerField = DEFAULT_TRAILER_FIELD;
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
        this.saltLength = ASN1Integer.getInstance(localASN1TaggedObject, true);
        continue;
        this.trailerField = ASN1Integer.getInstance(localASN1TaggedObject, true);
      }
    }
  }
  
  public RSASSAPSSparams(AlgorithmIdentifier paramAlgorithmIdentifier1, AlgorithmIdentifier paramAlgorithmIdentifier2, ASN1Integer paramASN1Integer1, ASN1Integer paramASN1Integer2)
  {
    this.hashAlgorithm = paramAlgorithmIdentifier1;
    this.maskGenAlgorithm = paramAlgorithmIdentifier2;
    this.saltLength = paramASN1Integer1;
    this.trailerField = paramASN1Integer2;
  }
  
  public static RSASSAPSSparams getInstance(Object paramObject)
  {
    if ((paramObject instanceof RSASSAPSSparams)) {
      return (RSASSAPSSparams)paramObject;
    }
    if (paramObject != null) {
      return new RSASSAPSSparams(ASN1Sequence.getInstance(paramObject));
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
  
  public BigInteger getSaltLength()
  {
    return this.saltLength.getValue();
  }
  
  public BigInteger getTrailerField()
  {
    return this.trailerField.getValue();
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
    if (!this.saltLength.equals(DEFAULT_SALT_LENGTH)) {
      localASN1EncodableVector.add(new DERTaggedObject(true, 2, this.saltLength));
    }
    if (!this.trailerField.equals(DEFAULT_TRAILER_FIELD)) {
      localASN1EncodableVector.add(new DERTaggedObject(true, 3, this.trailerField));
    }
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.pkcs.RSASSAPSSparams
 * JD-Core Version:    0.7.0.1
 */