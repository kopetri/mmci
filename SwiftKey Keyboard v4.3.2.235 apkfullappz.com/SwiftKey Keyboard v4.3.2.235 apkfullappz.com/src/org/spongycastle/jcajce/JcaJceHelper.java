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

public abstract interface JcaJceHelper
{
  public abstract AlgorithmParameterGenerator createAlgorithmParameterGenerator(String paramString)
    throws NoSuchAlgorithmException, NoSuchProviderException;
  
  public abstract AlgorithmParameters createAlgorithmParameters(String paramString)
    throws NoSuchAlgorithmException, NoSuchProviderException;
  
  public abstract CertificateFactory createCertificateFactory(String paramString)
    throws NoSuchAlgorithmException, NoSuchProviderException, CertificateException;
  
  public abstract Cipher createCipher(String paramString)
    throws NoSuchAlgorithmException, NoSuchPaddingException, NoSuchProviderException;
  
  public abstract MessageDigest createDigest(String paramString)
    throws NoSuchAlgorithmException, NoSuchProviderException;
  
  public abstract KeyAgreement createKeyAgreement(String paramString)
    throws NoSuchAlgorithmException, NoSuchProviderException;
  
  public abstract KeyFactory createKeyFactory(String paramString)
    throws NoSuchAlgorithmException, NoSuchProviderException;
  
  public abstract KeyGenerator createKeyGenerator(String paramString)
    throws NoSuchAlgorithmException, NoSuchProviderException;
  
  public abstract KeyPairGenerator createKeyPairGenerator(String paramString)
    throws NoSuchAlgorithmException, NoSuchProviderException;
  
  public abstract Mac createMac(String paramString)
    throws NoSuchAlgorithmException, NoSuchProviderException;
  
  public abstract Signature createSignature(String paramString)
    throws NoSuchAlgorithmException, NoSuchProviderException;
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jcajce.JcaJceHelper
 * JD-Core Version:    0.7.0.1
 */