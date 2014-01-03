package org.spongycastle.operator.jcajce;

import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.Provider;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.DERInteger;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.DERObjectIdentifier;
import org.spongycastle.asn1.kisa.KISAObjectIdentifiers;
import org.spongycastle.asn1.nist.NISTObjectIdentifiers;
import org.spongycastle.asn1.ntt.NTTObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.jcajce.DefaultJcaJceHelper;
import org.spongycastle.jcajce.NamedJcaJceHelper;
import org.spongycastle.jcajce.ProviderJcaJceHelper;
import org.spongycastle.operator.GenericKey;
import org.spongycastle.operator.OperatorException;
import org.spongycastle.operator.SymmetricKeyWrapper;

public class JceSymmetricKeyWrapper
  extends SymmetricKeyWrapper
{
  private OperatorHelper helper = new OperatorHelper(new DefaultJcaJceHelper());
  private SecureRandom random;
  private SecretKey wrappingKey;
  
  public JceSymmetricKeyWrapper(SecretKey paramSecretKey)
  {
    super(determineKeyEncAlg(paramSecretKey));
    this.wrappingKey = paramSecretKey;
  }
  
  private static AlgorithmIdentifier determineKeyEncAlg(SecretKey paramSecretKey)
  {
    String str = paramSecretKey.getAlgorithm();
    if (str.startsWith("DES")) {
      return new AlgorithmIdentifier(new DERObjectIdentifier("1.2.840.113549.1.9.16.3.6"), new DERNull());
    }
    if (str.startsWith("RC2")) {
      return new AlgorithmIdentifier(new DERObjectIdentifier("1.2.840.113549.1.9.16.3.7"), new DERInteger(58));
    }
    if (str.startsWith("AES"))
    {
      int j = 8 * paramSecretKey.getEncoded().length;
      ASN1ObjectIdentifier localASN1ObjectIdentifier2;
      if (j == 128) {
        localASN1ObjectIdentifier2 = NISTObjectIdentifiers.id_aes128_wrap;
      }
      for (;;)
      {
        return new AlgorithmIdentifier(localASN1ObjectIdentifier2);
        if (j == 192)
        {
          localASN1ObjectIdentifier2 = NISTObjectIdentifiers.id_aes192_wrap;
        }
        else
        {
          if (j != 256) {
            break;
          }
          localASN1ObjectIdentifier2 = NISTObjectIdentifiers.id_aes256_wrap;
        }
      }
      throw new IllegalArgumentException("illegal keysize in AES");
    }
    if (str.startsWith("SEED")) {
      return new AlgorithmIdentifier(KISAObjectIdentifiers.id_npki_app_cmsSeed_wrap);
    }
    if (str.startsWith("Camellia"))
    {
      int i = 8 * paramSecretKey.getEncoded().length;
      ASN1ObjectIdentifier localASN1ObjectIdentifier1;
      if (i == 128) {
        localASN1ObjectIdentifier1 = NTTObjectIdentifiers.id_camellia128_wrap;
      }
      for (;;)
      {
        return new AlgorithmIdentifier(localASN1ObjectIdentifier1);
        if (i == 192)
        {
          localASN1ObjectIdentifier1 = NTTObjectIdentifiers.id_camellia192_wrap;
        }
        else
        {
          if (i != 256) {
            break;
          }
          localASN1ObjectIdentifier1 = NTTObjectIdentifiers.id_camellia256_wrap;
        }
      }
      throw new IllegalArgumentException("illegal keysize in Camellia");
    }
    throw new IllegalArgumentException("unknown algorithm");
  }
  
  public byte[] generateWrappedKey(GenericKey paramGenericKey)
    throws OperatorException
  {
    Key localKey = OperatorUtils.getJceKey(paramGenericKey);
    Cipher localCipher = this.helper.createSymmetricWrapper(getAlgorithmIdentifier().getAlgorithm());
    try
    {
      localCipher.init(3, this.wrappingKey, this.random);
      byte[] arrayOfByte = localCipher.wrap(localKey);
      return arrayOfByte;
    }
    catch (GeneralSecurityException localGeneralSecurityException)
    {
      throw new OperatorException("cannot wrap key: " + localGeneralSecurityException.getMessage(), localGeneralSecurityException);
    }
  }
  
  public JceSymmetricKeyWrapper setProvider(String paramString)
  {
    this.helper = new OperatorHelper(new NamedJcaJceHelper(paramString));
    return this;
  }
  
  public JceSymmetricKeyWrapper setProvider(Provider paramProvider)
  {
    this.helper = new OperatorHelper(new ProviderJcaJceHelper(paramProvider));
    return this;
  }
  
  public JceSymmetricKeyWrapper setSecureRandom(SecureRandom paramSecureRandom)
  {
    this.random = paramSecureRandom;
    return this;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.operator.jcajce.JceSymmetricKeyWrapper
 * JD-Core Version:    0.7.0.1
 */