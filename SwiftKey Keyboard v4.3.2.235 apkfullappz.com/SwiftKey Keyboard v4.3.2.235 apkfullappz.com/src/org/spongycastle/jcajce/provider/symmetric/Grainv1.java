package org.spongycastle.jcajce.provider.symmetric;

import org.spongycastle.crypto.CipherKeyGenerator;
import org.spongycastle.crypto.engines.Grainv1Engine;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.spongycastle.jcajce.provider.symmetric.util.BaseStreamCipher;
import org.spongycastle.jcajce.provider.util.AlgorithmProvider;

public final class Grainv1
{
  public static class Base
    extends BaseStreamCipher
  {
    public Base()
    {
      super(8);
    }
  }
  
  public static class KeyGen
    extends BaseKeyGenerator
  {
    public KeyGen()
    {
      super(80, new CipherKeyGenerator());
    }
  }
  
  public static class Mappings
    extends AlgorithmProvider
  {
    private static final String PREFIX = Grainv1.class.getName();
    
    public void configure(ConfigurableProvider paramConfigurableProvider)
    {
      paramConfigurableProvider.addAlgorithm("Cipher.Grainv1", PREFIX + "$Base");
      paramConfigurableProvider.addAlgorithm("KeyGenerator.Grainv1", PREFIX + "$KeyGen");
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jcajce.provider.symmetric.Grainv1
 * JD-Core Version:    0.7.0.1
 */