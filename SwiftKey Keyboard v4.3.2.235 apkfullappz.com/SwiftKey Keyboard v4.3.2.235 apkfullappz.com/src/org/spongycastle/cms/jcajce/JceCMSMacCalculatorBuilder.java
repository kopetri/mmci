package org.spongycastle.cms.jcajce;

import java.io.OutputStream;
import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.RC2ParameterSpec;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.cms.CMSException;
import org.spongycastle.jcajce.io.MacOutputStream;
import org.spongycastle.operator.GenericKey;
import org.spongycastle.operator.MacCalculator;

public class JceCMSMacCalculatorBuilder
{
  private EnvelopedDataHelper helper = new EnvelopedDataHelper(new DefaultJcaJceExtHelper());
  private final int keySize;
  private final ASN1ObjectIdentifier macOID;
  private MacOutputStream macOutputStream;
  private SecureRandom random;
  
  public JceCMSMacCalculatorBuilder(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
  {
    this(paramASN1ObjectIdentifier, -1);
  }
  
  public JceCMSMacCalculatorBuilder(ASN1ObjectIdentifier paramASN1ObjectIdentifier, int paramInt)
  {
    this.macOID = paramASN1ObjectIdentifier;
    this.keySize = paramInt;
  }
  
  public MacCalculator build()
    throws CMSException
  {
    return new CMSOutputEncryptor(this.macOID, this.keySize, this.random);
  }
  
  public JceCMSMacCalculatorBuilder setProvider(String paramString)
  {
    this.helper = new EnvelopedDataHelper(new NamedJcaJceExtHelper(paramString));
    return this;
  }
  
  public JceCMSMacCalculatorBuilder setProvider(Provider paramProvider)
  {
    this.helper = new EnvelopedDataHelper(new ProviderJcaJceExtHelper(paramProvider));
    return this;
  }
  
  public JceCMSMacCalculatorBuilder setSecureRandom(SecureRandom paramSecureRandom)
  {
    this.random = paramSecureRandom;
    return this;
  }
  
  private class CMSOutputEncryptor
    implements MacCalculator
  {
    private AlgorithmIdentifier algorithmIdentifier;
    private SecretKey encKey;
    private Mac mac;
    private SecureRandom random;
    
    CMSOutputEncryptor(ASN1ObjectIdentifier paramASN1ObjectIdentifier, int paramInt, SecureRandom paramSecureRandom)
      throws CMSException
    {
      KeyGenerator localKeyGenerator = JceCMSMacCalculatorBuilder.this.helper.createKeyGenerator(paramASN1ObjectIdentifier);
      if (paramSecureRandom == null) {
        paramSecureRandom = new SecureRandom();
      }
      this.random = paramSecureRandom;
      if (paramInt < 0) {
        localKeyGenerator.init(paramSecureRandom);
      }
      for (;;)
      {
        this.encKey = localKeyGenerator.generateKey();
        AlgorithmParameterSpec localAlgorithmParameterSpec = generateParameterSpec(paramASN1ObjectIdentifier, this.encKey);
        this.algorithmIdentifier = JceCMSMacCalculatorBuilder.this.helper.getAlgorithmIdentifier(paramASN1ObjectIdentifier, localAlgorithmParameterSpec);
        this.mac = JceCMSMacCalculatorBuilder.this.helper.createContentMac(this.encKey, this.algorithmIdentifier);
        return;
        localKeyGenerator.init(paramInt, paramSecureRandom);
      }
    }
    
    protected AlgorithmParameterSpec generateParameterSpec(ASN1ObjectIdentifier paramASN1ObjectIdentifier, SecretKey paramSecretKey)
      throws CMSException
    {
      try
      {
        if (paramASN1ObjectIdentifier.equals(PKCSObjectIdentifiers.RC2_CBC))
        {
          byte[] arrayOfByte = new byte[8];
          this.random.nextBytes(arrayOfByte);
          return new RC2ParameterSpec(8 * paramSecretKey.getEncoded().length, arrayOfByte);
        }
        AlgorithmParameterSpec localAlgorithmParameterSpec = JceCMSMacCalculatorBuilder.this.helper.createAlgorithmParameterGenerator(paramASN1ObjectIdentifier).generateParameters().getParameterSpec(IvParameterSpec.class);
        return localAlgorithmParameterSpec;
      }
      catch (GeneralSecurityException localGeneralSecurityException) {}
      return null;
    }
    
    public AlgorithmIdentifier getAlgorithmIdentifier()
    {
      return this.algorithmIdentifier;
    }
    
    public GenericKey getKey()
    {
      return new GenericKey(this.encKey);
    }
    
    public byte[] getMac()
    {
      return this.mac.doFinal();
    }
    
    public OutputStream getOutputStream()
    {
      return new MacOutputStream(this.mac);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.jcajce.JceCMSMacCalculatorBuilder
 * JD-Core Version:    0.7.0.1
 */