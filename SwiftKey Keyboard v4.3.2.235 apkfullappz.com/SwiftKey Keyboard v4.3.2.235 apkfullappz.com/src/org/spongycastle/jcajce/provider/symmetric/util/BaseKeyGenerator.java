package org.spongycastle.jcajce.provider.symmetric.util;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.KeyGeneratorSpi;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.spongycastle.crypto.CipherKeyGenerator;
import org.spongycastle.crypto.KeyGenerationParameters;

public class BaseKeyGenerator
  extends KeyGeneratorSpi
{
  protected String algName;
  protected int defaultKeySize;
  protected CipherKeyGenerator engine;
  protected int keySize;
  protected boolean uninitialised = true;
  
  protected BaseKeyGenerator(String paramString, int paramInt, CipherKeyGenerator paramCipherKeyGenerator)
  {
    this.algName = paramString;
    this.defaultKeySize = paramInt;
    this.keySize = paramInt;
    this.engine = paramCipherKeyGenerator;
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
    try
    {
      this.engine.init(new KeyGenerationParameters(paramSecureRandom, paramInt));
      this.uninitialised = false;
      return;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      throw new InvalidParameterException(localIllegalArgumentException.getMessage());
    }
  }
  
  protected void engineInit(SecureRandom paramSecureRandom)
  {
    if (paramSecureRandom != null)
    {
      this.engine.init(new KeyGenerationParameters(paramSecureRandom, this.defaultKeySize));
      this.uninitialised = false;
    }
  }
  
  protected void engineInit(AlgorithmParameterSpec paramAlgorithmParameterSpec, SecureRandom paramSecureRandom)
    throws InvalidAlgorithmParameterException
  {
    throw new InvalidAlgorithmParameterException("Not Implemented");
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jcajce.provider.symmetric.util.BaseKeyGenerator
 * JD-Core Version:    0.7.0.1
 */