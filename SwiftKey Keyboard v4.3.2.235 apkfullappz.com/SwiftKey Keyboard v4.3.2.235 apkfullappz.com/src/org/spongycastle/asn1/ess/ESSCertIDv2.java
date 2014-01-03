package org.spongycastle.asn1.ess;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.nist.NISTObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.IssuerSerial;

public class ESSCertIDv2
  extends ASN1Object
{
  private static final AlgorithmIdentifier DEFAULT_ALG_ID = new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha256);
  private byte[] certHash;
  private AlgorithmIdentifier hashAlgorithm;
  private IssuerSerial issuerSerial;
  
  private ESSCertIDv2(ASN1Sequence paramASN1Sequence)
  {
    if (paramASN1Sequence.size() > 3) {
      throw new IllegalArgumentException("Bad sequence size: " + paramASN1Sequence.size());
    }
    int i = 0;
    if ((paramASN1Sequence.getObjectAt(0) instanceof ASN1OctetString)) {}
    for (this.hashAlgorithm = DEFAULT_ALG_ID;; this.hashAlgorithm = AlgorithmIdentifier.getInstance(paramASN1Sequence.getObjectAt(0).toASN1Primitive()))
    {
      int j = i + 1;
      this.certHash = ASN1OctetString.getInstance(paramASN1Sequence.getObjectAt(i).toASN1Primitive()).getOctets();
      if (paramASN1Sequence.size() > j) {
        this.issuerSerial = new IssuerSerial(ASN1Sequence.getInstance(paramASN1Sequence.getObjectAt(j).toASN1Primitive()));
      }
      return;
      i = 0 + 1;
    }
  }
  
  public ESSCertIDv2(AlgorithmIdentifier paramAlgorithmIdentifier, byte[] paramArrayOfByte)
  {
    this(paramAlgorithmIdentifier, paramArrayOfByte, null);
  }
  
  public ESSCertIDv2(AlgorithmIdentifier paramAlgorithmIdentifier, byte[] paramArrayOfByte, IssuerSerial paramIssuerSerial)
  {
    if (paramAlgorithmIdentifier == null) {}
    for (this.hashAlgorithm = DEFAULT_ALG_ID;; this.hashAlgorithm = paramAlgorithmIdentifier)
    {
      this.certHash = paramArrayOfByte;
      this.issuerSerial = paramIssuerSerial;
      return;
    }
  }
  
  public static ESSCertIDv2 getInstance(Object paramObject)
  {
    if ((paramObject instanceof ESSCertIDv2)) {
      return (ESSCertIDv2)paramObject;
    }
    if (paramObject != null) {
      return new ESSCertIDv2(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public byte[] getCertHash()
  {
    return this.certHash;
  }
  
  public AlgorithmIdentifier getHashAlgorithm()
  {
    return this.hashAlgorithm;
  }
  
  public IssuerSerial getIssuerSerial()
  {
    return this.issuerSerial;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    if (!this.hashAlgorithm.equals(DEFAULT_ALG_ID)) {
      localASN1EncodableVector.add(this.hashAlgorithm);
    }
    localASN1EncodableVector.add(new DEROctetString(this.certHash).toASN1Primitive());
    if (this.issuerSerial != null) {
      localASN1EncodableVector.add(this.issuerSerial);
    }
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.ess.ESSCertIDv2
 * JD-Core Version:    0.7.0.1
 */