package org.spongycastle.jcajce.provider.symmetric;

import org.spongycastle.crypto.CipherKeyGenerator;
import org.spongycastle.crypto.engines.RC4Engine;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.spongycastle.jcajce.provider.symmetric.util.BaseStreamCipher;
import org.spongycastle.jcajce.provider.util.AlgorithmProvider;

public final class ARC4
{
  public static class Base
    extends BaseStreamCipher
  {
    public Base()
    {
      super(0);
    }
  }
  
  public static class KeyGen
    extends BaseKeyGenerator
  {
    public KeyGen()
    {
      super(128, new CipherKeyGenerator());
    }
  }
  
  public static class Mappings
    extends AlgorithmProvider
  {
    private static final String PREFIX = ARC4.class.getName();
    
    public void configure(ConfigurableProvider paramConfigurableProvider)
    {
      paramConfigurableProvider.addAlgorithm("Cipher.ARC4", PREFIX + "$Base");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.Cipher.1.2.840.113549.3.4", "ARC4");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.Cipher.ARCFOUR", "ARC4");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.Cipher.RC4", "ARC4");
      paramConfigurableProvider.addAlgorithm("KeyGenerator.ARC4", PREFIX + "$KeyGen");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.KeyGenerator.RC4", "ARC4");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.KeyGenerator.1.2.840.113549.3.4", "ARC4");
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jcajce.provider.symmetric.ARC4
 * JD-Core Version:    0.7.0.1
 */