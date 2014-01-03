package org.spongycastle.jcajce.provider.util;

import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;

public abstract class AsymmetricAlgorithmProvider
  extends AlgorithmProvider
{
  protected void addSignatureAlgorithm(ConfigurableProvider paramConfigurableProvider, String paramString1, String paramString2, String paramString3, ASN1ObjectIdentifier paramASN1ObjectIdentifier)
  {
    String str1 = paramString1 + "WITH" + paramString2;
    String str2 = paramString1 + "with" + paramString2;
    String str3 = paramString1 + "With" + paramString2;
    String str4 = paramString1 + "/" + paramString2;
    paramConfigurableProvider.addAlgorithm("Signature." + str1, paramString3);
    paramConfigurableProvider.addAlgorithm("Alg.Alias.Signature." + str2, str1);
    paramConfigurableProvider.addAlgorithm("Alg.Alias.Signature." + str3, str1);
    paramConfigurableProvider.addAlgorithm("Alg.Alias.Signature." + str4, str1);
    paramConfigurableProvider.addAlgorithm("Alg.Alias.Signature." + paramASN1ObjectIdentifier, str1);
    paramConfigurableProvider.addAlgorithm("Alg.Alias.Signature.OID." + paramASN1ObjectIdentifier, str1);
  }
  
  protected void registerOid(ConfigurableProvider paramConfigurableProvider, ASN1ObjectIdentifier paramASN1ObjectIdentifier, String paramString, AsymmetricKeyInfoConverter paramAsymmetricKeyInfoConverter)
  {
    paramConfigurableProvider.addAlgorithm("Alg.Alias.KeyFactory." + paramASN1ObjectIdentifier, paramString);
    paramConfigurableProvider.addAlgorithm("Alg.Alias.KeyPairGenerator." + paramASN1ObjectIdentifier, paramString);
    paramConfigurableProvider.addKeyInfoConverter(paramASN1ObjectIdentifier, paramAsymmetricKeyInfoConverter);
  }
  
  protected void registerOidAlgorithmParameters(ConfigurableProvider paramConfigurableProvider, ASN1ObjectIdentifier paramASN1ObjectIdentifier, String paramString)
  {
    paramConfigurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameterGenerator." + paramASN1ObjectIdentifier, paramString);
    paramConfigurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters." + paramASN1ObjectIdentifier, paramString);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jcajce.provider.util.AsymmetricAlgorithmProvider
 * JD-Core Version:    0.7.0.1
 */