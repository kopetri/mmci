package org.spongycastle.cms.jcajce;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.cms.OriginatorPublicKey;
import org.spongycastle.asn1.cms.ecc.MQVuserKeyingMaterial;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.cms.CMSEnvelopedGenerator;
import org.spongycastle.cms.CMSException;
import org.spongycastle.cms.KeyAgreeRecipient;
import org.spongycastle.jce.spec.MQVPrivateKeySpec;
import org.spongycastle.jce.spec.MQVPublicKeySpec;

public abstract class JceKeyAgreeRecipient
  implements KeyAgreeRecipient
{
  protected EnvelopedDataHelper contentHelper = this.helper;
  protected EnvelopedDataHelper helper = new EnvelopedDataHelper(new DefaultJcaJceExtHelper());
  private PrivateKey recipientKey;
  
  public JceKeyAgreeRecipient(PrivateKey paramPrivateKey)
  {
    this.recipientKey = paramPrivateKey;
  }
  
  private SecretKey calculateAgreedWrapKey(AlgorithmIdentifier paramAlgorithmIdentifier, ASN1ObjectIdentifier paramASN1ObjectIdentifier, PublicKey paramPublicKey, ASN1OctetString paramASN1OctetString, PrivateKey paramPrivateKey)
    throws CMSException, GeneralSecurityException, IOException
  {
    if (paramAlgorithmIdentifier.getAlgorithm().getId().equals(CMSEnvelopedGenerator.ECMQV_SHA1KDF))
    {
      MQVuserKeyingMaterial localMQVuserKeyingMaterial = MQVuserKeyingMaterial.getInstance(ASN1Primitive.fromByteArray(paramASN1OctetString.getOctets()));
      X509EncodedKeySpec localX509EncodedKeySpec = new X509EncodedKeySpec(new SubjectPublicKeyInfo(getPrivateKeyAlgorithmIdentifier(), localMQVuserKeyingMaterial.getEphemeralPublicKey().getPublicKey().getBytes()).getEncoded());
      MQVPublicKeySpec localMQVPublicKeySpec = new MQVPublicKeySpec(paramPublicKey, this.helper.createKeyFactory(paramAlgorithmIdentifier.getAlgorithm()).generatePublic(localX509EncodedKeySpec));
      paramPrivateKey = new MQVPrivateKeySpec(paramPrivateKey, paramPrivateKey);
      paramPublicKey = localMQVPublicKeySpec;
    }
    KeyAgreement localKeyAgreement = this.helper.createKeyAgreement(paramAlgorithmIdentifier.getAlgorithm());
    localKeyAgreement.init(paramPrivateKey);
    localKeyAgreement.doPhase(paramPublicKey, true);
    return localKeyAgreement.generateSecret(paramASN1ObjectIdentifier.getId());
  }
  
  private Key unwrapSessionKey(ASN1ObjectIdentifier paramASN1ObjectIdentifier1, SecretKey paramSecretKey, ASN1ObjectIdentifier paramASN1ObjectIdentifier2, byte[] paramArrayOfByte)
    throws CMSException, InvalidKeyException, NoSuchAlgorithmException
  {
    Cipher localCipher = this.helper.createCipher(paramASN1ObjectIdentifier1);
    localCipher.init(4, paramSecretKey);
    return localCipher.unwrap(paramArrayOfByte, this.helper.getBaseCipherName(paramASN1ObjectIdentifier2), 3);
  }
  
  protected Key extractSecretKey(AlgorithmIdentifier paramAlgorithmIdentifier1, AlgorithmIdentifier paramAlgorithmIdentifier2, SubjectPublicKeyInfo paramSubjectPublicKeyInfo, ASN1OctetString paramASN1OctetString, byte[] paramArrayOfByte)
    throws CMSException
  {
    try
    {
      ASN1ObjectIdentifier localASN1ObjectIdentifier = AlgorithmIdentifier.getInstance(paramAlgorithmIdentifier1.getParameters()).getAlgorithm();
      X509EncodedKeySpec localX509EncodedKeySpec = new X509EncodedKeySpec(paramSubjectPublicKeyInfo.getEncoded());
      Key localKey = unwrapSessionKey(localASN1ObjectIdentifier, calculateAgreedWrapKey(paramAlgorithmIdentifier1, localASN1ObjectIdentifier, this.helper.createKeyFactory(paramAlgorithmIdentifier1.getAlgorithm()).generatePublic(localX509EncodedKeySpec), paramASN1OctetString, this.recipientKey), paramAlgorithmIdentifier2.getAlgorithm(), paramArrayOfByte);
      return localKey;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      throw new CMSException("can't find algorithm.", localNoSuchAlgorithmException);
    }
    catch (InvalidKeyException localInvalidKeyException)
    {
      throw new CMSException("key invalid in message.", localInvalidKeyException);
    }
    catch (InvalidKeySpecException localInvalidKeySpecException)
    {
      throw new CMSException("originator key spec invalid.", localInvalidKeySpecException);
    }
    catch (NoSuchPaddingException localNoSuchPaddingException)
    {
      throw new CMSException("required padding not supported.", localNoSuchPaddingException);
    }
    catch (Exception localException)
    {
      throw new CMSException("originator key invalid.", localException);
    }
  }
  
  public AlgorithmIdentifier getPrivateKeyAlgorithmIdentifier()
  {
    return PrivateKeyInfo.getInstance(this.recipientKey.getEncoded()).getAlgorithmId();
  }
  
  public JceKeyAgreeRecipient setContentProvider(String paramString)
  {
    this.contentHelper = CMSUtils.createContentHelper(paramString);
    return this;
  }
  
  public JceKeyAgreeRecipient setContentProvider(Provider paramProvider)
  {
    this.contentHelper = CMSUtils.createContentHelper(paramProvider);
    return this;
  }
  
  public JceKeyAgreeRecipient setProvider(String paramString)
  {
    this.helper = new EnvelopedDataHelper(new NamedJcaJceExtHelper(paramString));
    this.contentHelper = this.helper;
    return this;
  }
  
  public JceKeyAgreeRecipient setProvider(Provider paramProvider)
  {
    this.helper = new EnvelopedDataHelper(new ProviderJcaJceExtHelper(paramProvider));
    this.contentHelper = this.helper;
    return this;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.jcajce.JceKeyAgreeRecipient
 * JD-Core Version:    0.7.0.1
 */