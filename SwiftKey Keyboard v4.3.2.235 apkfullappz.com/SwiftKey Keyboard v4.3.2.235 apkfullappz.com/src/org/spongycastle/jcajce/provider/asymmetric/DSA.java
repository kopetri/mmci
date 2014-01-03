package org.spongycastle.jcajce.provider.asymmetric;

import org.spongycastle.asn1.nist.NISTObjectIdentifiers;
import org.spongycastle.jcajce.provider.asymmetric.dsa.DSAUtil;
import org.spongycastle.jcajce.provider.asymmetric.dsa.KeyFactorySpi;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.util.AsymmetricAlgorithmProvider;

public class DSA
{
  private static final String PREFIX = "org.spongycastle.jcajce.provider.asymmetric.dsa.";
  
  public static class Mappings
    extends AsymmetricAlgorithmProvider
  {
    public void configure(ConfigurableProvider paramConfigurableProvider)
    {
      paramConfigurableProvider.addAlgorithm("AlgorithmParameters.DSA", "org.spongycastle.jcajce.provider.asymmetric.dsa.AlgorithmParametersSpi");
      paramConfigurableProvider.addAlgorithm("AlgorithmParameterGenerator.DSA", "org.spongycastle.jcajce.provider.asymmetric.dsa.AlgorithmParameterGeneratorSpi");
      paramConfigurableProvider.addAlgorithm("KeyPairGenerator.DSA", "org.spongycastle.jcajce.provider.asymmetric.dsa.KeyPairGeneratorSpi");
      paramConfigurableProvider.addAlgorithm("KeyFactory.DSA", "org.spongycastle.jcajce.provider.asymmetric.dsa.KeyFactorySpi");
      paramConfigurableProvider.addAlgorithm("Signature.DSA", "org.spongycastle.jcajce.provider.asymmetric.dsa.DSASigner$stdDSA");
      paramConfigurableProvider.addAlgorithm("Signature.NONEWITHDSA", "org.spongycastle.jcajce.provider.asymmetric.dsa.DSASigner$noneDSA");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.Signature.RAWDSA", "NONEWITHDSA");
      addSignatureAlgorithm(paramConfigurableProvider, "SHA224", "DSA", "org.spongycastle.jcajce.provider.asymmetric.dsa.DSASigner$dsa224", NISTObjectIdentifiers.dsa_with_sha224);
      addSignatureAlgorithm(paramConfigurableProvider, "SHA256", "DSA", "org.spongycastle.jcajce.provider.asymmetric.dsa.DSASigner$dsa256", NISTObjectIdentifiers.dsa_with_sha256);
      addSignatureAlgorithm(paramConfigurableProvider, "SHA384", "DSA", "org.spongycastle.jcajce.provider.asymmetric.dsa.DSASigner$dsa384", NISTObjectIdentifiers.dsa_with_sha384);
      addSignatureAlgorithm(paramConfigurableProvider, "SHA512", "DSA", "org.spongycastle.jcajce.provider.asymmetric.dsa.DSASigner$dsa512", NISTObjectIdentifiers.dsa_with_sha512);
      paramConfigurableProvider.addAlgorithm("Alg.Alias.Signature.SHA/DSA", "DSA");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.Signature.SHA1withDSA", "DSA");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.Signature.SHA1WITHDSA", "DSA");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.Signature.1.3.14.3.2.26with1.2.840.10040.4.1", "DSA");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.Signature.1.3.14.3.2.26with1.2.840.10040.4.3", "DSA");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.Signature.DSAwithSHA1", "DSA");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.Signature.DSAWITHSHA1", "DSA");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.Signature.SHA1WithDSA", "DSA");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.Signature.DSAWithSHA1", "DSA");
      paramConfigurableProvider.addAlgorithm("Alg.Alias.Signature.1.2.840.10040.4.3", "DSA");
      KeyFactorySpi localKeyFactorySpi = new KeyFactorySpi();
      for (int i = 0; i != DSAUtil.dsaOids.length; i++)
      {
        registerOid(paramConfigurableProvider, DSAUtil.dsaOids[i], "DSA", localKeyFactorySpi);
        registerOidAlgorithmParameters(paramConfigurableProvider, DSAUtil.dsaOids[i], "DSA");
      }
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jcajce.provider.asymmetric.DSA
 * JD-Core Version:    0.7.0.1
 */