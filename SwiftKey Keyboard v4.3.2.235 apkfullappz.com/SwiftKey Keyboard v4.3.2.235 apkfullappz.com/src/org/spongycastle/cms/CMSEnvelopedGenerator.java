package org.spongycastle.cms;

import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.crypto.SecretKey;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.cms.KEKIdentifier;
import org.spongycastle.asn1.cms.OriginatorInfo;
import org.spongycastle.asn1.kisa.KISAObjectIdentifiers;
import org.spongycastle.asn1.nist.NISTObjectIdentifiers;
import org.spongycastle.asn1.ntt.NTTObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x9.X9ObjectIdentifiers;
import org.spongycastle.cms.jcajce.JceKEKRecipientInfoGenerator;
import org.spongycastle.cms.jcajce.JceKeyAgreeRecipientInfoGenerator;
import org.spongycastle.cms.jcajce.JceKeyTransRecipientInfoGenerator;
import org.spongycastle.cms.jcajce.JcePasswordRecipientInfoGenerator;

public class CMSEnvelopedGenerator
{
  public static final String AES128_CBC;
  public static final String AES128_WRAP;
  public static final String AES192_CBC;
  public static final String AES192_WRAP;
  public static final String AES256_CBC;
  public static final String AES256_WRAP;
  public static final String CAMELLIA128_CBC;
  public static final String CAMELLIA128_WRAP;
  public static final String CAMELLIA192_CBC;
  public static final String CAMELLIA192_WRAP;
  public static final String CAMELLIA256_CBC;
  public static final String CAMELLIA256_WRAP;
  public static final String CAST5_CBC = "1.2.840.113533.7.66.10";
  public static final String DES_EDE3_CBC = PKCSObjectIdentifiers.des_EDE3_CBC.getId();
  public static final String DES_EDE3_WRAP;
  public static final String ECDH_SHA1KDF = X9ObjectIdentifiers.dhSinglePass_stdDH_sha1kdf_scheme.getId();
  public static final String ECMQV_SHA1KDF = X9ObjectIdentifiers.mqvSinglePass_sha1kdf_scheme.getId();
  public static final String IDEA_CBC = "1.3.6.1.4.1.188.7.1.1.2";
  public static final String RC2_CBC = PKCSObjectIdentifiers.RC2_CBC.getId();
  public static final String SEED_CBC;
  public static final String SEED_WRAP;
  final List oldRecipientInfoGenerators = new ArrayList();
  protected OriginatorInfo originatorInfo;
  final SecureRandom rand;
  final List recipientInfoGenerators = new ArrayList();
  protected CMSAttributeTableGenerator unprotectedAttributeGenerator = null;
  
  static
  {
    AES128_CBC = NISTObjectIdentifiers.id_aes128_CBC.getId();
    AES192_CBC = NISTObjectIdentifiers.id_aes192_CBC.getId();
    AES256_CBC = NISTObjectIdentifiers.id_aes256_CBC.getId();
    CAMELLIA128_CBC = NTTObjectIdentifiers.id_camellia128_cbc.getId();
    CAMELLIA192_CBC = NTTObjectIdentifiers.id_camellia192_cbc.getId();
    CAMELLIA256_CBC = NTTObjectIdentifiers.id_camellia256_cbc.getId();
    SEED_CBC = KISAObjectIdentifiers.id_seedCBC.getId();
    DES_EDE3_WRAP = PKCSObjectIdentifiers.id_alg_CMS3DESwrap.getId();
    AES128_WRAP = NISTObjectIdentifiers.id_aes128_wrap.getId();
    AES192_WRAP = NISTObjectIdentifiers.id_aes192_wrap.getId();
    AES256_WRAP = NISTObjectIdentifiers.id_aes256_wrap.getId();
    CAMELLIA128_WRAP = NTTObjectIdentifiers.id_camellia128_wrap.getId();
    CAMELLIA192_WRAP = NTTObjectIdentifiers.id_camellia192_wrap.getId();
    CAMELLIA256_WRAP = NTTObjectIdentifiers.id_camellia256_wrap.getId();
    SEED_WRAP = KISAObjectIdentifiers.id_npki_app_cmsSeed_wrap.getId();
  }
  
  public CMSEnvelopedGenerator()
  {
    this(new SecureRandom());
  }
  
  public CMSEnvelopedGenerator(SecureRandom paramSecureRandom)
  {
    this.rand = paramSecureRandom;
  }
  
  public void addKEKRecipient(SecretKey paramSecretKey, KEKIdentifier paramKEKIdentifier)
  {
    this.oldRecipientInfoGenerators.add(new JceKEKRecipientInfoGenerator(paramKEKIdentifier, paramSecretKey));
  }
  
  public void addKEKRecipient(SecretKey paramSecretKey, byte[] paramArrayOfByte)
  {
    addKEKRecipient(paramSecretKey, new KEKIdentifier(paramArrayOfByte, null, null));
  }
  
  public void addKeyAgreementRecipient(String paramString1, PrivateKey paramPrivateKey, PublicKey paramPublicKey, X509Certificate paramX509Certificate, String paramString2, String paramString3)
    throws NoSuchProviderException, NoSuchAlgorithmException, InvalidKeyException
  {
    addKeyAgreementRecipient(paramString1, paramPrivateKey, paramPublicKey, paramX509Certificate, paramString2, CMSUtils.getProvider(paramString3));
  }
  
  public void addKeyAgreementRecipient(String paramString1, PrivateKey paramPrivateKey, PublicKey paramPublicKey, X509Certificate paramX509Certificate, String paramString2, Provider paramProvider)
    throws NoSuchAlgorithmException, InvalidKeyException
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(paramX509Certificate);
    addKeyAgreementRecipients(paramString1, paramPrivateKey, paramPublicKey, localArrayList, paramString2, paramProvider);
  }
  
  public void addKeyAgreementRecipients(String paramString1, PrivateKey paramPrivateKey, PublicKey paramPublicKey, Collection paramCollection, String paramString2, String paramString3)
    throws NoSuchProviderException, NoSuchAlgorithmException, InvalidKeyException
  {
    addKeyAgreementRecipients(paramString1, paramPrivateKey, paramPublicKey, paramCollection, paramString2, CMSUtils.getProvider(paramString3));
  }
  
  public void addKeyAgreementRecipients(String paramString1, PrivateKey paramPrivateKey, PublicKey paramPublicKey, Collection paramCollection, String paramString2, Provider paramProvider)
    throws NoSuchAlgorithmException, InvalidKeyException
  {
    JceKeyAgreeRecipientInfoGenerator localJceKeyAgreeRecipientInfoGenerator = new JceKeyAgreeRecipientInfoGenerator(new ASN1ObjectIdentifier(paramString1), paramPrivateKey, paramPublicKey, new ASN1ObjectIdentifier(paramString2)).setProvider(paramProvider);
    Iterator localIterator = paramCollection.iterator();
    while (localIterator.hasNext()) {
      try
      {
        localJceKeyAgreeRecipientInfoGenerator.addRecipient((X509Certificate)localIterator.next());
      }
      catch (CertificateEncodingException localCertificateEncodingException)
      {
        throw new IllegalArgumentException("unable to encode certificate: " + localCertificateEncodingException.getMessage());
      }
    }
    this.oldRecipientInfoGenerators.add(localJceKeyAgreeRecipientInfoGenerator);
  }
  
  public void addKeyTransRecipient(PublicKey paramPublicKey, byte[] paramArrayOfByte)
    throws IllegalArgumentException
  {
    this.oldRecipientInfoGenerators.add(new JceKeyTransRecipientInfoGenerator(paramArrayOfByte, paramPublicKey));
  }
  
  public void addKeyTransRecipient(X509Certificate paramX509Certificate)
    throws IllegalArgumentException
  {
    try
    {
      this.oldRecipientInfoGenerators.add(new JceKeyTransRecipientInfoGenerator(paramX509Certificate));
      return;
    }
    catch (CertificateEncodingException localCertificateEncodingException)
    {
      throw new IllegalArgumentException("unable to encode certificate: " + localCertificateEncodingException.getMessage());
    }
  }
  
  public void addPasswordRecipient(CMSPBEKey paramCMSPBEKey, String paramString)
  {
    List localList = this.oldRecipientInfoGenerators;
    PasswordRecipientInfoGenerator localPasswordRecipientInfoGenerator = new JcePasswordRecipientInfoGenerator(new ASN1ObjectIdentifier(paramString), paramCMSPBEKey.getPassword()).setSaltAndIterationCount(paramCMSPBEKey.getSalt(), paramCMSPBEKey.getIterationCount());
    if ((paramCMSPBEKey instanceof PKCS5Scheme2UTF8PBEKey)) {}
    for (int i = 1;; i = 0)
    {
      localList.add(localPasswordRecipientInfoGenerator.setPasswordConversionScheme(i));
      return;
    }
  }
  
  public void addRecipientInfoGenerator(RecipientInfoGenerator paramRecipientInfoGenerator)
  {
    this.recipientInfoGenerators.add(paramRecipientInfoGenerator);
  }
  
  protected void convertOldRecipients(SecureRandom paramSecureRandom, Provider paramProvider)
  {
    Iterator localIterator = this.oldRecipientInfoGenerators.iterator();
    while (localIterator.hasNext())
    {
      Object localObject = localIterator.next();
      if ((localObject instanceof JceKeyTransRecipientInfoGenerator))
      {
        JceKeyTransRecipientInfoGenerator localJceKeyTransRecipientInfoGenerator = (JceKeyTransRecipientInfoGenerator)localObject;
        if (paramProvider != null) {
          localJceKeyTransRecipientInfoGenerator.setProvider(paramProvider);
        }
        this.recipientInfoGenerators.add(localJceKeyTransRecipientInfoGenerator);
      }
      else if ((localObject instanceof KEKRecipientInfoGenerator))
      {
        JceKEKRecipientInfoGenerator localJceKEKRecipientInfoGenerator = (JceKEKRecipientInfoGenerator)localObject;
        if (paramProvider != null) {
          localJceKEKRecipientInfoGenerator.setProvider(paramProvider);
        }
        localJceKEKRecipientInfoGenerator.setSecureRandom(paramSecureRandom);
        this.recipientInfoGenerators.add(localJceKEKRecipientInfoGenerator);
      }
      else if ((localObject instanceof JcePasswordRecipientInfoGenerator))
      {
        JcePasswordRecipientInfoGenerator localJcePasswordRecipientInfoGenerator = (JcePasswordRecipientInfoGenerator)localObject;
        if (paramProvider != null) {
          localJcePasswordRecipientInfoGenerator.setProvider(paramProvider);
        }
        localJcePasswordRecipientInfoGenerator.setSecureRandom(paramSecureRandom);
        this.recipientInfoGenerators.add(localJcePasswordRecipientInfoGenerator);
      }
      else if ((localObject instanceof JceKeyAgreeRecipientInfoGenerator))
      {
        JceKeyAgreeRecipientInfoGenerator localJceKeyAgreeRecipientInfoGenerator = (JceKeyAgreeRecipientInfoGenerator)localObject;
        if (paramProvider != null) {
          localJceKeyAgreeRecipientInfoGenerator.setProvider(paramProvider);
        }
        localJceKeyAgreeRecipientInfoGenerator.setSecureRandom(paramSecureRandom);
        this.recipientInfoGenerators.add(localJceKeyAgreeRecipientInfoGenerator);
      }
    }
    this.oldRecipientInfoGenerators.clear();
  }
  
  protected AlgorithmIdentifier getAlgorithmIdentifier(String paramString, AlgorithmParameters paramAlgorithmParameters)
    throws IOException
  {
    if (paramAlgorithmParameters != null) {}
    for (Object localObject = ASN1Primitive.fromByteArray(paramAlgorithmParameters.getEncoded("ASN.1"));; localObject = DERNull.INSTANCE) {
      return new AlgorithmIdentifier(new ASN1ObjectIdentifier(paramString), (ASN1Encodable)localObject);
    }
  }
  
  public void setOriginatorInfo(OriginatorInformation paramOriginatorInformation)
  {
    this.originatorInfo = paramOriginatorInformation.toASN1Structure();
  }
  
  public void setUnprotectedAttributeGenerator(CMSAttributeTableGenerator paramCMSAttributeTableGenerator)
  {
    this.unprotectedAttributeGenerator = paramCMSAttributeTableGenerator;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.CMSEnvelopedGenerator
 * JD-Core Version:    0.7.0.1
 */