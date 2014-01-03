package org.spongycastle.cms.jcajce;

import java.io.OutputStream;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.Provider;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.cms.CMSAlgorithm;
import org.spongycastle.cms.CMSException;
import org.spongycastle.operator.GenericKey;
import org.spongycastle.operator.OutputEncryptor;

public class JceCMSContentEncryptorBuilder
{
  private static Map keySizes;
  private final ASN1ObjectIdentifier encryptionOID;
  private EnvelopedDataHelper helper = new EnvelopedDataHelper(new DefaultJcaJceExtHelper());
  private final int keySize;
  private SecureRandom random;
  
  static
  {
    HashMap localHashMap = new HashMap();
    keySizes = localHashMap;
    localHashMap.put(CMSAlgorithm.AES128_CBC, new Integer(128));
    keySizes.put(CMSAlgorithm.AES192_CBC, new Integer(192));
    keySizes.put(CMSAlgorithm.AES256_CBC, new Integer(256));
    keySizes.put(CMSAlgorithm.CAMELLIA128_CBC, new Integer(128));
    keySizes.put(CMSAlgorithm.CAMELLIA192_CBC, new Integer(192));
    keySizes.put(CMSAlgorithm.CAMELLIA256_CBC, new Integer(256));
  }
  
  public JceCMSContentEncryptorBuilder(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
  {
    this(paramASN1ObjectIdentifier, getKeySize(paramASN1ObjectIdentifier));
  }
  
  public JceCMSContentEncryptorBuilder(ASN1ObjectIdentifier paramASN1ObjectIdentifier, int paramInt)
  {
    this.encryptionOID = paramASN1ObjectIdentifier;
    this.keySize = paramInt;
  }
  
  private static int getKeySize(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
  {
    Integer localInteger = (Integer)keySizes.get(paramASN1ObjectIdentifier);
    if (localInteger != null) {
      return localInteger.intValue();
    }
    return -1;
  }
  
  public OutputEncryptor build()
    throws CMSException
  {
    return new CMSOutputEncryptor(this.encryptionOID, this.keySize, this.random);
  }
  
  public JceCMSContentEncryptorBuilder setProvider(String paramString)
  {
    this.helper = new EnvelopedDataHelper(new NamedJcaJceExtHelper(paramString));
    return this;
  }
  
  public JceCMSContentEncryptorBuilder setProvider(Provider paramProvider)
  {
    this.helper = new EnvelopedDataHelper(new ProviderJcaJceExtHelper(paramProvider));
    return this;
  }
  
  public JceCMSContentEncryptorBuilder setSecureRandom(SecureRandom paramSecureRandom)
  {
    this.random = paramSecureRandom;
    return this;
  }
  
  private class CMSOutputEncryptor
    implements OutputEncryptor
  {
    private AlgorithmIdentifier algorithmIdentifier;
    private Cipher cipher;
    private SecretKey encKey;
    
    CMSOutputEncryptor(ASN1ObjectIdentifier paramASN1ObjectIdentifier, int paramInt, SecureRandom paramSecureRandom)
      throws CMSException
    {
      KeyGenerator localKeyGenerator = JceCMSContentEncryptorBuilder.this.helper.createKeyGenerator(paramASN1ObjectIdentifier);
      if (paramSecureRandom == null) {
        paramSecureRandom = new SecureRandom();
      }
      if (paramInt < 0) {
        localKeyGenerator.init(paramSecureRandom);
      }
      for (;;)
      {
        this.cipher = JceCMSContentEncryptorBuilder.this.helper.createCipher(paramASN1ObjectIdentifier);
        this.encKey = localKeyGenerator.generateKey();
        AlgorithmParameters localAlgorithmParameters = JceCMSContentEncryptorBuilder.this.helper.generateParameters(paramASN1ObjectIdentifier, this.encKey, paramSecureRandom);
        try
        {
          this.cipher.init(1, this.encKey, localAlgorithmParameters, paramSecureRandom);
          if (localAlgorithmParameters == null) {
            localAlgorithmParameters = this.cipher.getParameters();
          }
          this.algorithmIdentifier = JceCMSContentEncryptorBuilder.this.helper.getAlgorithmIdentifier(paramASN1ObjectIdentifier, localAlgorithmParameters);
          return;
        }
        catch (GeneralSecurityException localGeneralSecurityException)
        {
          throw new CMSException("unable to initialize cipher: " + localGeneralSecurityException.getMessage(), localGeneralSecurityException);
        }
        localKeyGenerator.init(paramInt, paramSecureRandom);
      }
    }
    
    public AlgorithmIdentifier getAlgorithmIdentifier()
    {
      return this.algorithmIdentifier;
    }
    
    public GenericKey getKey()
    {
      return new GenericKey(this.encKey);
    }
    
    public OutputStream getOutputStream(OutputStream paramOutputStream)
    {
      return new CipherOutputStream(paramOutputStream, this.cipher);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.jcajce.JceCMSContentEncryptorBuilder
 * JD-Core Version:    0.7.0.1
 */