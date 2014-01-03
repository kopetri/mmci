package org.spongycastle.cert.crmf.jcajce;

import java.io.OutputStream;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.Provider;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.cert.crmf.CRMFException;
import org.spongycastle.jcajce.DefaultJcaJceHelper;
import org.spongycastle.jcajce.NamedJcaJceHelper;
import org.spongycastle.jcajce.ProviderJcaJceHelper;
import org.spongycastle.operator.GenericKey;
import org.spongycastle.operator.OutputEncryptor;

public class JceCRMFEncryptorBuilder
{
  private final ASN1ObjectIdentifier encryptionOID;
  private CRMFHelper helper = new CRMFHelper(new DefaultJcaJceHelper());
  private final int keySize;
  private SecureRandom random;
  
  public JceCRMFEncryptorBuilder(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
  {
    this(paramASN1ObjectIdentifier, -1);
  }
  
  public JceCRMFEncryptorBuilder(ASN1ObjectIdentifier paramASN1ObjectIdentifier, int paramInt)
  {
    this.encryptionOID = paramASN1ObjectIdentifier;
    this.keySize = paramInt;
  }
  
  public OutputEncryptor build()
    throws CRMFException
  {
    return new CRMFOutputEncryptor(this.encryptionOID, this.keySize, this.random);
  }
  
  public JceCRMFEncryptorBuilder setProvider(String paramString)
  {
    this.helper = new CRMFHelper(new NamedJcaJceHelper(paramString));
    return this;
  }
  
  public JceCRMFEncryptorBuilder setProvider(Provider paramProvider)
  {
    this.helper = new CRMFHelper(new ProviderJcaJceHelper(paramProvider));
    return this;
  }
  
  public JceCRMFEncryptorBuilder setSecureRandom(SecureRandom paramSecureRandom)
  {
    this.random = paramSecureRandom;
    return this;
  }
  
  private class CRMFOutputEncryptor
    implements OutputEncryptor
  {
    private AlgorithmIdentifier algorithmIdentifier;
    private Cipher cipher;
    private SecretKey encKey;
    
    CRMFOutputEncryptor(ASN1ObjectIdentifier paramASN1ObjectIdentifier, int paramInt, SecureRandom paramSecureRandom)
      throws CRMFException
    {
      KeyGenerator localKeyGenerator = JceCRMFEncryptorBuilder.this.helper.createKeyGenerator(paramASN1ObjectIdentifier);
      if (paramSecureRandom == null) {
        paramSecureRandom = new SecureRandom();
      }
      if (paramInt < 0) {
        localKeyGenerator.init(paramSecureRandom);
      }
      for (;;)
      {
        this.cipher = JceCRMFEncryptorBuilder.this.helper.createCipher(paramASN1ObjectIdentifier);
        this.encKey = localKeyGenerator.generateKey();
        AlgorithmParameters localAlgorithmParameters = JceCRMFEncryptorBuilder.this.helper.generateParameters(paramASN1ObjectIdentifier, this.encKey, paramSecureRandom);
        try
        {
          this.cipher.init(1, this.encKey, localAlgorithmParameters, paramSecureRandom);
          if (localAlgorithmParameters == null) {
            localAlgorithmParameters = this.cipher.getParameters();
          }
          this.algorithmIdentifier = JceCRMFEncryptorBuilder.this.helper.getAlgorithmIdentifier(paramASN1ObjectIdentifier, localAlgorithmParameters);
          return;
        }
        catch (GeneralSecurityException localGeneralSecurityException)
        {
          throw new CRMFException("unable to initialize cipher: " + localGeneralSecurityException.getMessage(), localGeneralSecurityException);
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
 * Qualified Name:     org.spongycastle.cert.crmf.jcajce.JceCRMFEncryptorBuilder
 * JD-Core Version:    0.7.0.1
 */