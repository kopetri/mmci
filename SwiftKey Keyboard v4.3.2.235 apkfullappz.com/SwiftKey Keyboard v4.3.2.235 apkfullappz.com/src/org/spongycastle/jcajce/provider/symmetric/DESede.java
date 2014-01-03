package org.spongycastle.jcajce.provider.symmetric;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.SecretKey;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.crypto.CipherKeyGenerator;
import org.spongycastle.crypto.KeyGenerationParameters;
import org.spongycastle.crypto.engines.DESedeEngine;
import org.spongycastle.crypto.engines.DESedeWrapEngine;
import org.spongycastle.crypto.engines.RFC3211WrapEngine;
import org.spongycastle.crypto.generators.DESedeKeyGenerator;
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

public final class DESede
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
  
  public static class DESede64
    extends BaseMac
  {
    public DESede64()
    {
      super();
    }
  }
  
  public static class DESede64with7816d4
    extends BaseMac
  {
    public DESede64with7816d4()
    {
      super();
    }
  }
  
  public static class DESedeCFB8
    extends BaseMac
  {
    public DESedeCFB8()
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
      if ((paramKeySpec instanceof DESedeKeySpec)) {
        return new SecretKeySpec(((DESedeKeySpec)paramKeySpec).getKey(), "DESede");
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
      if (DESedeKeySpec.class.isAssignableFrom(paramClass))
      {
        byte[] arrayOfByte1 = paramSecretKey.getEncoded();
        try
        {
          if (arrayOfByte1.length == 16)
          {
            byte[] arrayOfByte2 = new byte[24];
            System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, 16);
            System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 16, 8);
            DESedeKeySpec localDESedeKeySpec2 = new DESedeKeySpec(arrayOfByte2);
            return localDESedeKeySpec2;
          }
        }
        catch (Exception localException)
        {
          throw new InvalidKeySpecException(localException.toString());
        }
        DESedeKeySpec localDESedeKeySpec1 = new DESedeKeySpec(arrayOfByte1);
        return localDESedeKeySpec1;
      }
      throw new InvalidKeySpecException("Invalid KeySpec");
    }
  }
  
  public static class KeyGenerator
    extends BaseKeyGenerator
  {
    private boolean keySizeSet = false;
    
    public KeyGenerator()
    {
      super(192, new DESedeKeyGenerator());
    }
    
    protected SecretKey engineGenerateKey()
    {
      if (this.uninitialised)
      {
        this.engine.init(new KeyGenerationParameters(new SecureRandom(), this.defaultKeySize));
        this.uninitialised = false;
      }
      if (!this.keySizeSet)
      {
        byte[] arrayOfByte = this.engine.generateKey();
        System.arraycopy(arrayOfByte, 0, arrayOfByte, 16, 8);
        return new SecretKeySpec(arrayOfByte, this.algName);
      }
      return new SecretKeySpec(this.engine.generateKey(), this.algName);
    }
    
    protected void engineInit(int paramInt, SecureRandom paramSecureRandom)
    {
      super.engineInit(paramInt, paramSecureRandom);
      this.keySizeSet = true;
    }
  }
  
  public static class KeyGenerator3
    extends BaseKeyGenerator
  {
    public KeyGenerator3()
    {
      super(192, new DESedeKeyGenerator());
    }
  }
  
  public static class Mappings
    extends AlgorithmProvider
  {
    private static final String PACKAGE = "org.spongycastle.jcajce.provider.symmetric";
    private static final String PREFIX = DESede.class.getName();
    
    public void configure(ConfigurableProvider paramConfigurableProvider)
    {
      paramConfigurableProvider.addAlgorithm("Cipher.DESEDE", PREFIX + "$ECB");
      paramConfigurableProvider.addAlgorithm("Cipher." + PKCSObjectIdentifiers.des_EDE3_CBC, PREFIX + "$CBC");
      paramConfigurableProvider.addAlgorithm("Cipher.DESEDEWRAP", PREFIX + "$Wrap");
      paramConfigurableProvider.addAlgorithm("Cipher." + PKCSObjectIdentifiers.id_alg_CMS3DESwrap, PREFIX + "$Wrap");
      paramConfigurableProvider.addAlgorithm("Cipher.DESEDERFC3211WRAP", PREFIX + "$RFC3211");
      if (paramConfigurableProvider.hasAlgorithm("MessageDigest", "SHA-1"))
      {
        paramConfigurableProvider.addAlgorithm("Cipher.PBEWITHSHAAND3-KEYTRIPLEDES-CBC", PREFIX + "$PBEWithSHAAndDES3Key");
        paramConfigurableProvider.addAlgorithm("Cipher.BROKENPBEWITHSHAAND3-KEYTRIPLEDES-CBC", PREFIX + "$BrokePBEWithSHAAndDES3Key");
        paramConfigurableProvider.addAlgorithm("Cipher.OLDPBEWITHSHAAND3-KEYTRIPLEDES-CBC", PREFIX + "$OldPBEWithSHAAndDES3Key");
        paramConfigurableProvider.addAlgorithm("Cipher.PBEWITHSHAAND2-KEYTRIPLEDES-CBC", PREFIX + "$PBEWithSHAAndDES2Key");
        paramConfigurableProvider.addAlgorithm("Cipher.BROKENPBEWITHSHAAND2-KEYTRIPLEDES-CBC", PREFIX + "$BrokePBEWithSHAAndDES2Key");
        paramConfigurableProvider.addAlgorithm("Alg.Alias.Cipher." + PKCSObjectIdentifiers.pbeWithSHAAnd3_KeyTripleDES_CBC, "PBEWITHSHAAND3-KEYTRIPLEDES-CBC");
        paramConfigurableProvider.addAlgorithm("Alg.Alias.Cipher." + PKCSObjectIdentifiers.pbeWithSHAAnd2_KeyTripleDES_CBC, "PBEWITHSHAAND2-KEYTRIPLEDES-CBC");
        paramConfigurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1ANDDESEDE", "PBEWITHSHAAND3-KEYTRIPLEDES-CBC");
        paramConfigurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1AND3-KEYTRIPLEDES-CBC", "PBEWITHSHAAND3-KEYTRIPLEDES-CBC");
        paramConfigurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1AND2-KEYTRIPLEDES-CBC", "PBEWITHSHAAND2-KEYTRIPLEDES-CBC");
      }
      paramConfigurableProvider.addAlgorithm("KeyGenerator.DESEDE", PREFIX + "$KeyGenerator");
      paramConfigurableProvider.addAlgorithm("KeyGenerator." + PKCSObjectIdentifiers.des_EDE3_CBC, PREFIX + "$KeyGenerator3");
      paramConfigurableProvider.addAlgorithm("KeyGenerator.DESEDEWRAP", PREFIX + "$KeyGenerator");
      paramConfigurableProvider.addAlgorithm("SecretKeyFactory.DESEDE", PREFIX + "$KeyFactory");
      paramConfigurableProvider.addAlgorithm("Mac.DESEDECMAC", PREFIX + "$CMAC");
      paramConfigurableProvider.addAlgorithm("Mac.DESEDEMAC", PREFIX + "$CBCMAC");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.Mac.DESEDE", "DESEDEMAC");
      paramConfigurableProvider.addAlgorithm("Mac.DESEDEMAC/CFB8", PREFIX + "$DESedeCFB8");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.Mac.DESEDE/CFB8", "DESEDEMAC/CFB8");
      paramConfigurableProvider.addAlgorithm("Mac.DESEDEMAC64", PREFIX + "$DESede64");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.Mac.DESEDE64", "DESEDEMAC64");
      paramConfigurableProvider.addAlgorithm("Mac.DESEDEMAC64WITHISO7816-4PADDING", PREFIX + "$DESede64with7816d4");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.Mac.DESEDE64WITHISO7816-4PADDING", "DESEDEMAC64WITHISO7816-4PADDING");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.Mac.DESEDEISO9797ALG1MACWITHISO7816-4PADDING", "DESEDEMAC64WITHISO7816-4PADDING");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.Mac.DESEDEISO9797ALG1WITHISO7816-4PADDING", "DESEDEMAC64WITHISO7816-4PADDING");
      paramConfigurableProvider.addAlgorithm("AlgorithmParameters.DESEDE", "org.spongycastle.jcajce.provider.symmetric.util.IvAlgorithmParameters");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters." + PKCSObjectIdentifiers.des_EDE3_CBC, "DESEDE");
      paramConfigurableProvider.addAlgorithm("AlgorithmParameterGenerator.DESEDE", PREFIX + "$AlgParamGen");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator." + PKCSObjectIdentifiers.des_EDE3_CBC, "DESEDE");
    }
  }
  
  public static class PBEWithSHAAndDES2Key
    extends BaseBlockCipher
  {
    public PBEWithSHAAndDES2Key()
    {
      super();
    }
  }
  
  public static class PBEWithSHAAndDES3Key
    extends BaseBlockCipher
  {
    public PBEWithSHAAndDES3Key()
    {
      super();
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
  
  public static class Wrap
    extends BaseWrapCipher
  {
    public Wrap()
    {
      super();
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jcajce.provider.symmetric.DESede
 * JD-Core Version:    0.7.0.1
 */