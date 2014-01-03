package org.spongycastle.jcajce.provider.digest;

import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.util.AlgorithmProvider;

abstract class DigestAlgorithmProvider
  extends AlgorithmProvider
{
  protected void addHMACAlgorithm(ConfigurableProvider paramConfigurableProvider, String paramString1, String paramString2, String paramString3)
  {
    String str = "HMAC" + paramString1;
    paramConfigurableProvider.addAlgorithm("Mac." + str, paramString2);
    paramConfigurableProvider.addAlgorithm("Alg.Alias.Mac.HMAC-" + paramString1, str);
    paramConfigurableProvider.addAlgorithm("Alg.Alias.Mac.HMAC/" + paramString1, str);
    paramConfigurableProvider.addAlgorithm("KeyGenerator." + str, paramString3);
    paramConfigurableProvider.addAlgorithm("Alg.Alias.KeyGenerator.HMAC-" + paramString1, str);
    paramConfigurableProvider.addAlgorithm("Alg.Alias.KeyGenerator.HMAC/" + paramString1, str);
  }
  
  protected void addHMACAlias(ConfigurableProvider paramConfigurableProvider, String paramString, ASN1ObjectIdentifier paramASN1ObjectIdentifier)
  {
    String str = "HMAC" + paramString;
    paramConfigurableProvider.addAlgorithm("Alg.Alias.Mac." + paramASN1ObjectIdentifier, str);
    paramConfigurableProvider.addAlgorithm("Alg.Alias.KeyGenerator." + paramASN1ObjectIdentifier, str);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jcajce.provider.digest.DigestAlgorithmProvider
 * JD-Core Version:    0.7.0.1
 */