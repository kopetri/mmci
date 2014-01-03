package org.spongycastle.jcajce;

import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;
import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Signature;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;

public class NamedJcaJceHelper
  implements JcaJceHelper
{
  protected final String providerName;
  
  public NamedJcaJceHelper(String paramString)
  {
    this.providerName = paramString;
  }
  
  public AlgorithmParameterGenerator createAlgorithmParameterGenerator(String paramString)
    throws NoSuchAlgorithmException, NoSuchProviderException
  {
    return AlgorithmParameterGenerator.getInstance(paramString, this.providerName);
  }
  
  public AlgorithmParameters createAlgorithmParameters(String paramString)
    throws NoSuchAlgorithmException, NoSuchProviderException
  {
    return AlgorithmParameters.getInstance(paramString, this.providerName);
  }
  
  public CertificateFactory createCertificateFactory(String paramString)
    throws NoSuchAlgorithmException, CertificateException, NoSuchProviderException
  {
    return CertificateFactory.getInstance(paramString, this.providerName);
  }
  
  public Cipher createCipher(String paramString)
    throws NoSuchAlgorithmException, NoSuchPaddingException, NoSuchProviderException
  {
    return Cipher.getInstance(paramString, this.providerName);
  }
  
  public MessageDigest createDigest(String paramString)
    throws NoSuchAlgorithmException, NoSuchProviderException
  {
    return MessageDigest.getInstance(paramString, this.providerName);
  }
  
  public KeyAgreement createKeyAgreement(String paramString)
    throws NoSuchAlgorithmException, NoSuchProviderException
  {
    return KeyAgreement.getInstance(paramString, this.providerName);
  }
  
  public KeyFactory createKeyFactory(String paramString)
    throws NoSuchAlgorithmException, NoSuchProviderException
  {
    return KeyFactory.getInstance(paramString, this.providerName);
  }
  
  public KeyGenerator createKeyGenerator(String paramString)
    throws NoSuchAlgorithmException, NoSuchProviderException
  {
    return KeyGenerator.getInstance(paramString, this.providerName);
  }
  
  public KeyPairGenerator createKeyPairGenerator(String paramString)
    throws NoSuchAlgorithmException, NoSuchProviderException
  {
    return KeyPairGenerator.getInstance(paramString, this.providerName);
  }
  
  public Mac createMac(String paramString)
    throws NoSuchAlgorithmException, NoSuchProviderException
  {
    return Mac.getInstance(paramString, this.providerName);
  }
  
  public Signature createSignature(String paramString)
    throws NoSuchAlgorithmException, NoSuchProviderException
  {
    return Signature.getInstance(paramString, this.providerName);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jcajce.NamedJcaJceHelper
 * JD-Core Version:    0.7.0.1
 */