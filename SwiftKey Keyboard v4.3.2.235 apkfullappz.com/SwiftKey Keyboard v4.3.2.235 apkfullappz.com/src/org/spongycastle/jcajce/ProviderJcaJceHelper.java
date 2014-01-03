package org.spongycastle.jcajce;

import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;
import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Signature;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;

public class ProviderJcaJceHelper
  implements JcaJceHelper
{
  protected final Provider provider;
  
  public ProviderJcaJceHelper(Provider paramProvider)
  {
    this.provider = paramProvider;
  }
  
  public AlgorithmParameterGenerator createAlgorithmParameterGenerator(String paramString)
    throws NoSuchAlgorithmException
  {
    return AlgorithmParameterGenerator.getInstance(paramString, this.provider);
  }
  
  public AlgorithmParameters createAlgorithmParameters(String paramString)
    throws NoSuchAlgorithmException
  {
    return AlgorithmParameters.getInstance(paramString, this.provider);
  }
  
  public CertificateFactory createCertificateFactory(String paramString)
    throws NoSuchAlgorithmException, CertificateException
  {
    return CertificateFactory.getInstance(paramString, this.provider);
  }
  
  public Cipher createCipher(String paramString)
    throws NoSuchAlgorithmException, NoSuchPaddingException
  {
    return Cipher.getInstance(paramString, this.provider);
  }
  
  public MessageDigest createDigest(String paramString)
    throws NoSuchAlgorithmException
  {
    return MessageDigest.getInstance(paramString, this.provider);
  }
  
  public KeyAgreement createKeyAgreement(String paramString)
    throws NoSuchAlgorithmException
  {
    return KeyAgreement.getInstance(paramString, this.provider);
  }
  
  public KeyFactory createKeyFactory(String paramString)
    throws NoSuchAlgorithmException
  {
    return KeyFactory.getInstance(paramString, this.provider);
  }
  
  public KeyGenerator createKeyGenerator(String paramString)
    throws NoSuchAlgorithmException
  {
    return KeyGenerator.getInstance(paramString, this.provider);
  }
  
  public KeyPairGenerator createKeyPairGenerator(String paramString)
    throws NoSuchAlgorithmException
  {
    return KeyPairGenerator.getInstance(paramString, this.provider);
  }
  
  public Mac createMac(String paramString)
    throws NoSuchAlgorithmException
  {
    return Mac.getInstance(paramString, this.provider);
  }
  
  public Signature createSignature(String paramString)
    throws NoSuchAlgorithmException
  {
    return Signature.getInstance(paramString, this.provider);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jcajce.ProviderJcaJceHelper
 * JD-Core Version:    0.7.0.1
 */