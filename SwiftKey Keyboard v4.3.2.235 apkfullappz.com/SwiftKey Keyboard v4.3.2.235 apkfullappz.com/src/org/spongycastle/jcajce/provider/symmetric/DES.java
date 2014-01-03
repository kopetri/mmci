package org.spongycastle.jcajce.provider.symmetric;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.SecretKey;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.oiw.OIWObjectIdentifiers;
import org.spongycastle.crypto.CipherKeyGenerator;
import org.spongycastle.crypto.KeyGenerationParameters;
import org.spongycastle.crypto.engines.DESEngine;
import org.spongycastle.crypto.engines.RFC3211WrapEngine;
import org.spongycastle.crypto.generators.DESKeyGenerator;
import org.spongycastle.crypto.macs.CBCBlockCipherMac;
import org.spongycastle.crypto.macs.CFBBlockCipherMac;
import org.spongycastle.crypto.macs.CMac;
import org.spongycastle.crypto.modes.CBCBlockCipher;
import org.spongycastle.crypto.paddings.ISO7816d4Padding;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameterGenerator;
import org.spongycastle.jcajce.provider.symmetric.util.BaseBlockCipher;
import org.spongycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.spongycastle.jcajce.provider.symmetric.util.BaseMac;
import org.spongycastle.jcajce.provider.symmetric.util.BaseSecretKeyFactory;
import org.spongycastle.jcajce.provider.symmetric.util.BaseWrapCipher;
import org.spongycastle.jcajce.provider.util.AlgorithmProvider;
import org.spongycastle.jce.provider.BouncyCastleProvider;

public final class DES
{
  public static class AlgParamGen
    extends BaseAlgorithmParameterGenerator
  {
    protected AlgorithmParameters engineGenerateParameters()
    {
      byte[] arrayOfByte = new byte[8];
      if (this.random == null) {
        this.random = new SecureRandom();
      }
      this.random.nextBytes(arrayOfByte);
      try
      {
        AlgorithmParameters localAlgorithmParameters = AlgorithmParameters.getInstance("DES", BouncyCastleProvider.PROVIDER_NAME);
        localAlgorithmParameters.init(new IvParameterSpec(arrayOfByte));
        return localAlgorithmParameters;
      }
      catch (Exception localException)
      {
        throw new RuntimeException(localException.getMessage());
      }
    }
    
    protected void engineInit(AlgorithmParameterSpec paramAlgorithmParameterSpec, SecureRandom paramSecureRandom)
      throws InvalidAlgorithmParameterException
    {
      throw new InvalidAlgorithmParameterException("No supported AlgorithmParameterSpec for DES parameter generation.");
    }
  }
  
  public static class CBC
    extends BaseBlockCipher
  {
    public CBC()
    {
      super(64);
    }
  }
  
  public static class CBCMAC
    extends BaseMac
  {
    public CBCMAC()
    {
      super();
    }
  }
  
  public static class CMAC
    extends BaseMac
  {
    public CMAC()
    {
      super();
    }
  }
  
  public static class DES64
    extends BaseMac
  {
    public DES64()
    {
      super();
    }
  }
  
  public static class DES64with7816d4
    extends BaseMac
  {
    public DES64with7816d4()
    {
      super();
    }
  }
  
  public static class DESCFB8
    extends BaseMac
  {
    public DESCFB8()
    {
      super();
    }
  }
  
  public static class ECB
    extends BaseBlockCipher
  {
    public ECB()
    {
      super();
    }
  }
  
  public static class KeyFactory
    extends BaseSecretKeyFactory
  {
    public KeyFactory()
    {
      super(null);
    }
    
    protected SecretKey engineGenerateSecret(KeySpec paramKeySpec)
      throws InvalidKeySpecException
    {
      if ((paramKeySpec instanceof DESKeySpec)) {
        return new SecretKeySpec(((DESKeySpec)paramKeySpec).getKey(), "DES");
      }
      return super.engineGenerateSecret(paramKeySpec);
    }
    
    protected KeySpec engineGetKeySpec(SecretKey paramSecretKey, Class paramClass)
      throws InvalidKeySpecException
    {
      if (paramClass == null) {
        throw new InvalidKeySpecException("keySpec parameter is null");
      }
      if (paramSecretKey == null) {
        throw new InvalidKeySpecException("key parameter is null");
      }
      if (SecretKeySpec.class.isAssignableFrom(paramClass)) {
        return new SecretKeySpec(paramSecretKey.getEncoded(), this.algName);
      }
      if (DESKeySpec.class.isAssignableFrom(paramClass))
      {
        byte[] arrayOfByte = paramSecretKey.getEncoded();
        try
        {
          DESKeySpec localDESKeySpec = new DESKeySpec(arrayOfByte);
          return localDESKeySpec;
        }
        catch (Exception localException)
        {
          throw new InvalidKeySpecException(localException.toString());
        }
      }
      throw new InvalidKeySpecException("Invalid KeySpec");
    }
  }
  
  public static class KeyGenerator
    extends BaseKeyGenerator
  {
    public KeyGenerator()
    {
      super(64, new DESKeyGenerator());
    }
    
    protected SecretKey engineGenerateKey()
    {
      if (this.uninitialised)
      {
        this.engine.init(new KeyGenerationParameters(new SecureRandom(), this.defaultKeySize));
        this.uninitialised = false;
      }
      return new SecretKeySpec(this.engine.generateKey(), this.algName);
    }
    
    protected void engineInit(int paramInt, SecureRandom paramSecureRandom)
    {
      super.engineInit(paramInt, paramSecureRandom);
    }
  }
  
  public static class Mappings
    extends AlgorithmProvider
  {
    private static final String PACKAGE = "org.spongycastle.jcajce.provider.symmetric";
    private static final String PREFIX = DES.class.getName();
    
    private void addAlias(ConfigurableProvider paramConfigurableProvider, ASN1ObjectIdentifier paramASN1ObjectIdentifier, String paramString)
    {
      paramConfigurableProvider.addAlgorithm("Alg.Alias.KeyGenerator." + paramASN1ObjectIdentifier.getId(), paramString);
      paramConfigurableProvider.addAlgorithm("Alg.Alias.KeyFactory." + paramASN1ObjectIdentifier.getId(), paramString);
    }
    
    public void configure(ConfigurableProvider paramConfigurableProvider)
    {
      paramConfigurableProvider.addAlgorithm("Cipher.DES", PREFIX + "$ECB");
      paramConfigurableProvider.addAlgorithm("Cipher." + OIWObjectIdentifiers.desCBC, PREFIX + "$CBC");
      addAlias(paramConfigurableProvider, OIWObjectIdentifiers.desCBC, "DES");
      paramConfigurableProvider.addAlgorithm("Cipher.DESRFC3211WRAP", PREFIX + "$RFC3211");
      paramConfigurableProvider.addAlgorithm("KeyGenerator.DES", PREFIX + "$KeyGenerator");
      paramConfigurableProvider.addAlgorithm("SecretKeyFactory.DES", PREFIX + "$KeyFactory");
      paramConfigurableProvider.addAlgorithm("Mac.DESCMAC", PREFIX + "$CMAC");
      paramConfigurableProvider.addAlgorithm("Mac.DESMAC", PREFIX + "$CBCMAC");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.Mac.DES", "DESMAC");
      paramConfigurableProvider.addAlgorithm("Mac.DESMAC/CFB8", PREFIX + "$DESCFB8");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.Mac.DES/CFB8", "DESMAC/CFB8");
      paramConfigurableProvider.addAlgorithm("Mac.DESMAC64", PREFIX + "$DES64");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.Mac.DES64", "DESMAC64");
      paramConfigurableProvider.addAlgorithm("Mac.DESMAC64WITHISO7816-4PADDING", PREFIX + "$DES64with7816d4");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.Mac.DES64WITHISO7816-4PADDING", "DESMAC64WITHISO7816-4PADDING");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.Mac.DESISO9797ALG1MACWITHISO7816-4PADDING", "DESMAC64WITHISO7816-4PADDING");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.Mac.DESISO9797ALG1WITHISO7816-4PADDING", "DESMAC64WITHISO7816-4PADDING");
      paramConfigurableProvider.addAlgorithm("AlgorithmParameters.DES", "org.spongycastle.jcajce.provider.symmetric.util.IvAlgorithmParameters");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters." + OIWObjectIdentifiers.desCBC, "DES");
      paramConfigurableProvider.addAlgorithm("AlgorithmParameterGenerator.DES", PREFIX + "$AlgParamGen");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator." + OIWObjectIdentifiers.desCBC, "DES");
    }
  }
  
  public static class RFC3211
    extends BaseWrapCipher
  {
    public RFC3211()
    {
      super(8);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jcajce.provider.symmetric.DES
 * JD-Core Version:    0.7.0.1
 */