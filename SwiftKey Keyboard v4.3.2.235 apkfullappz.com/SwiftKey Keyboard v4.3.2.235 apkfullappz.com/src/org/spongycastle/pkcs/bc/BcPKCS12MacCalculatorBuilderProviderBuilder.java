package org.spongycastle.pkcs.bc;

import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.oiw.OIWObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PKCS12PBEParams;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.crypto.ExtendedDigest;
import org.spongycastle.crypto.digests.SHA1Digest;
import org.spongycastle.operator.MacCalculator;
import org.spongycastle.pkcs.PKCS12MacCalculatorBuilder;
import org.spongycastle.pkcs.PKCS12MacCalculatorBuilderProvider;

public class BcPKCS12MacCalculatorBuilderProviderBuilder
  implements PKCS12MacCalculatorBuilderProvider
{
  private ExtendedDigest digest;
  private AlgorithmIdentifier digestAlgorithmIdentifier;
  
  public BcPKCS12MacCalculatorBuilderProviderBuilder()
  {
    this(new SHA1Digest(), new AlgorithmIdentifier(OIWObjectIdentifiers.idSHA1, DERNull.INSTANCE));
  }
  
  public BcPKCS12MacCalculatorBuilderProviderBuilder(ExtendedDigest paramExtendedDigest, AlgorithmIdentifier paramAlgorithmIdentifier)
  {
    this.digest = paramExtendedDigest;
    this.digestAlgorithmIdentifier = paramAlgorithmIdentifier;
  }
  
  public PKCS12MacCalculatorBuilder get(final AlgorithmIdentifier paramAlgorithmIdentifier)
  {
    new PKCS12MacCalculatorBuilder()
    {
      public MacCalculator build(char[] paramAnonymousArrayOfChar)
      {
        PKCS12PBEParams localPKCS12PBEParams = PKCS12PBEParams.getInstance(paramAlgorithmIdentifier.getParameters());
        return PKCS12PBEUtils.createMacCalculator(BcPKCS12MacCalculatorBuilderProviderBuilder.this.digestAlgorithmIdentifier.getAlgorithm(), BcPKCS12MacCalculatorBuilderProviderBuilder.this.digest, localPKCS12PBEParams, paramAnonymousArrayOfChar);
      }
      
      public AlgorithmIdentifier getDigestAlgorithmIdentifier()
      {
        return BcPKCS12MacCalculatorBuilderProviderBuilder.this.digestAlgorithmIdentifier;
      }
    };
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.pkcs.bc.BcPKCS12MacCalculatorBuilderProviderBuilder
 * JD-Core Version:    0.7.0.1
 */