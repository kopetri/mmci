package org.spongycastle.cms;

import java.io.IOException;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.cms.KeyAgreeRecipientInfo;
import org.spongycastle.asn1.cms.OriginatorIdentifierOrKey;
import org.spongycastle.asn1.cms.OriginatorPublicKey;
import org.spongycastle.asn1.cms.RecipientInfo;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.operator.GenericKey;

public abstract class KeyAgreeRecipientInfoGenerator
  implements RecipientInfoGenerator
{
  private ASN1ObjectIdentifier keyAgreementOID;
  private ASN1ObjectIdentifier keyEncryptionOID;
  private SubjectPublicKeyInfo originatorKeyInfo;
  
  protected KeyAgreeRecipientInfoGenerator(ASN1ObjectIdentifier paramASN1ObjectIdentifier1, SubjectPublicKeyInfo paramSubjectPublicKeyInfo, ASN1ObjectIdentifier paramASN1ObjectIdentifier2)
  {
    this.originatorKeyInfo = paramSubjectPublicKeyInfo;
    this.keyAgreementOID = paramASN1ObjectIdentifier1;
    this.keyEncryptionOID = paramASN1ObjectIdentifier2;
  }
  
  protected OriginatorPublicKey createOriginatorPublicKey(SubjectPublicKeyInfo paramSubjectPublicKeyInfo)
  {
    return new OriginatorPublicKey(new AlgorithmIdentifier(paramSubjectPublicKeyInfo.getAlgorithmId().getAlgorithm(), DERNull.INSTANCE), paramSubjectPublicKeyInfo.getPublicKeyData().getBytes());
  }
  
  public RecipientInfo generate(GenericKey paramGenericKey)
    throws CMSException
  {
    OriginatorIdentifierOrKey localOriginatorIdentifierOrKey = new OriginatorIdentifierOrKey(createOriginatorPublicKey(this.originatorKeyInfo));
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.keyEncryptionOID);
    localASN1EncodableVector.add(DERNull.INSTANCE);
    AlgorithmIdentifier localAlgorithmIdentifier1 = new AlgorithmIdentifier(this.keyEncryptionOID, DERNull.INSTANCE);
    AlgorithmIdentifier localAlgorithmIdentifier2 = new AlgorithmIdentifier(this.keyAgreementOID, localAlgorithmIdentifier1);
    ASN1Sequence localASN1Sequence = generateRecipientEncryptedKeys(localAlgorithmIdentifier2, localAlgorithmIdentifier1, paramGenericKey);
    ASN1Encodable localASN1Encodable = getUserKeyingMaterial(localAlgorithmIdentifier2);
    if (localASN1Encodable != null) {
      try
      {
        RecipientInfo localRecipientInfo = new RecipientInfo(new KeyAgreeRecipientInfo(localOriginatorIdentifierOrKey, new DEROctetString(localASN1Encodable), localAlgorithmIdentifier2, localASN1Sequence));
        return localRecipientInfo;
      }
      catch (IOException localIOException)
      {
        throw new CMSException("unable to encode userKeyingMaterial: " + localIOException.getMessage(), localIOException);
      }
    }
    return new RecipientInfo(new KeyAgreeRecipientInfo(localOriginatorIdentifierOrKey, null, localAlgorithmIdentifier2, localASN1Sequence));
  }
  
  protected abstract ASN1Sequence generateRecipientEncryptedKeys(AlgorithmIdentifier paramAlgorithmIdentifier1, AlgorithmIdentifier paramAlgorithmIdentifier2, GenericKey paramGenericKey)
    throws CMSException;
  
  protected abstract ASN1Encodable getUserKeyingMaterial(AlgorithmIdentifier paramAlgorithmIdentifier)
    throws CMSException;
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.KeyAgreeRecipientInfoGenerator
 * JD-Core Version:    0.7.0.1
 */