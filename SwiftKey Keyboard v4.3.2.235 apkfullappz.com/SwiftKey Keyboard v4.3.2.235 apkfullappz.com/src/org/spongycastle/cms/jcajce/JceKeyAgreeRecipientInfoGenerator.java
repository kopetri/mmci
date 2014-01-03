package org.spongycastle.cms.jcajce;

import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECParameterSpec;
import java.util.ArrayList;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.cms.KeyAgreeRecipientIdentifier;
import org.spongycastle.asn1.cms.RecipientEncryptedKey;
import org.spongycastle.asn1.cms.RecipientKeyIdentifier;
import org.spongycastle.asn1.cms.ecc.MQVuserKeyingMaterial;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.cms.CMSAlgorithm;
import org.spongycastle.cms.CMSEnvelopedGenerator;
import org.spongycastle.cms.CMSException;
import org.spongycastle.cms.KeyAgreeRecipientInfoGenerator;
import org.spongycastle.jce.spec.MQVPrivateKeySpec;
import org.spongycastle.jce.spec.MQVPublicKeySpec;
import org.spongycastle.operator.GenericKey;

public class JceKeyAgreeRecipientInfoGenerator
  extends KeyAgreeRecipientInfoGenerator
{
  private KeyPair ephemeralKP;
  private EnvelopedDataHelper helper = new EnvelopedDataHelper(new DefaultJcaJceExtHelper());
  private SecureRandom random;
  private List recipientIDs = new ArrayList();
  private List recipientKeys = new ArrayList();
  private PrivateKey senderPrivateKey;
  private PublicKey senderPublicKey;
  
  public JceKeyAgreeRecipientInfoGenerator(ASN1ObjectIdentifier paramASN1ObjectIdentifier1, PrivateKey paramPrivateKey, PublicKey paramPublicKey, ASN1ObjectIdentifier paramASN1ObjectIdentifier2)
  {
    super(paramASN1ObjectIdentifier1, SubjectPublicKeyInfo.getInstance(paramPublicKey.getEncoded()), paramASN1ObjectIdentifier2);
    this.senderPublicKey = paramPublicKey;
    this.senderPrivateKey = paramPrivateKey;
  }
  
  private void init(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
    throws CMSException
  {
    if (this.random == null) {
      this.random = new SecureRandom();
    }
    if ((paramASN1ObjectIdentifier.equals(CMSAlgorithm.ECMQV_SHA1KDF)) && (this.ephemeralKP == null)) {}
    try
    {
      ECParameterSpec localECParameterSpec = ((ECPublicKey)this.senderPublicKey).getParams();
      KeyPairGenerator localKeyPairGenerator = this.helper.createKeyPairGenerator(paramASN1ObjectIdentifier);
      localKeyPairGenerator.initialize(localECParameterSpec, this.random);
      this.ephemeralKP = localKeyPairGenerator.generateKeyPair();
      return;
    }
    catch (InvalidAlgorithmParameterException localInvalidAlgorithmParameterException)
    {
      throw new CMSException("cannot determine MQV ephemeral key pair parameters from public key: " + localInvalidAlgorithmParameterException);
    }
  }
  
  public JceKeyAgreeRecipientInfoGenerator addRecipient(X509Certificate paramX509Certificate)
    throws CertificateEncodingException
  {
    this.recipientIDs.add(new KeyAgreeRecipientIdentifier(CMSUtils.getIssuerAndSerialNumber(paramX509Certificate)));
    this.recipientKeys.add(paramX509Certificate.getPublicKey());
    return this;
  }
  
  public JceKeyAgreeRecipientInfoGenerator addRecipient(byte[] paramArrayOfByte, PublicKey paramPublicKey)
    throws CertificateEncodingException
  {
    this.recipientIDs.add(new KeyAgreeRecipientIdentifier(new RecipientKeyIdentifier(paramArrayOfByte)));
    this.recipientKeys.add(paramPublicKey);
    return this;
  }
  
  public ASN1Sequence generateRecipientEncryptedKeys(AlgorithmIdentifier paramAlgorithmIdentifier1, AlgorithmIdentifier paramAlgorithmIdentifier2, GenericKey paramGenericKey)
    throws CMSException
  {
    init(paramAlgorithmIdentifier1.getAlgorithm());
    Object localObject1 = this.senderPrivateKey;
    ASN1ObjectIdentifier localASN1ObjectIdentifier = paramAlgorithmIdentifier1.getAlgorithm();
    if (localASN1ObjectIdentifier.getId().equals(CMSEnvelopedGenerator.ECMQV_SHA1KDF)) {
      localObject1 = new MQVPrivateKeySpec((PrivateKey)localObject1, this.ephemeralKP.getPrivate(), this.ephemeralKP.getPublic());
    }
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    int i = 0;
    while (i != this.recipientIDs.size())
    {
      Object localObject2 = (PublicKey)this.recipientKeys.get(i);
      KeyAgreeRecipientIdentifier localKeyAgreeRecipientIdentifier = (KeyAgreeRecipientIdentifier)this.recipientIDs.get(i);
      if (localASN1ObjectIdentifier.getId().equals(CMSEnvelopedGenerator.ECMQV_SHA1KDF)) {
        localObject2 = new MQVPublicKeySpec((PublicKey)localObject2, (PublicKey)localObject2);
      }
      try
      {
        KeyAgreement localKeyAgreement = this.helper.createKeyAgreement(localASN1ObjectIdentifier);
        localKeyAgreement.init((Key)localObject1, this.random);
        localKeyAgreement.doPhase((Key)localObject2, true);
        SecretKey localSecretKey = localKeyAgreement.generateSecret(paramAlgorithmIdentifier2.getAlgorithm().getId());
        Cipher localCipher = this.helper.createCipher(paramAlgorithmIdentifier2.getAlgorithm());
        localCipher.init(3, localSecretKey, this.random);
        localASN1EncodableVector.add(new RecipientEncryptedKey(localKeyAgreeRecipientIdentifier, new DEROctetString(localCipher.wrap(this.helper.getJceKey(paramGenericKey)))));
        i++;
      }
      catch (GeneralSecurityException localGeneralSecurityException)
      {
        throw new CMSException("cannot perform agreement step: " + localGeneralSecurityException.getMessage(), localGeneralSecurityException);
      }
    }
    return new DERSequence(localASN1EncodableVector);
  }
  
  protected ASN1Encodable getUserKeyingMaterial(AlgorithmIdentifier paramAlgorithmIdentifier)
    throws CMSException
  {
    init(paramAlgorithmIdentifier.getAlgorithm());
    if (this.ephemeralKP != null) {
      return new MQVuserKeyingMaterial(createOriginatorPublicKey(SubjectPublicKeyInfo.getInstance(this.ephemeralKP.getPublic().getEncoded())), null);
    }
    return null;
  }
  
  public JceKeyAgreeRecipientInfoGenerator setProvider(String paramString)
  {
    this.helper = new EnvelopedDataHelper(new NamedJcaJceExtHelper(paramString));
    return this;
  }
  
  public JceKeyAgreeRecipientInfoGenerator setProvider(Provider paramProvider)
  {
    this.helper = new EnvelopedDataHelper(new ProviderJcaJceExtHelper(paramProvider));
    return this;
  }
  
  public JceKeyAgreeRecipientInfoGenerator setSecureRandom(SecureRandom paramSecureRandom)
  {
    this.random = paramSecureRandom;
    return this;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.jcajce.JceKeyAgreeRecipientInfoGenerator
 * JD-Core Version:    0.7.0.1
 */