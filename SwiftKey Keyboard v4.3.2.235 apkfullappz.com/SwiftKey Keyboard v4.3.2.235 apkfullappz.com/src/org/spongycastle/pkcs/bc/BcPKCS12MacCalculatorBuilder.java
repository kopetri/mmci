package org.spongycastle.pkcs.bc;

import java.security.SecureRandom;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.oiw.OIWObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PKCS12PBEParams;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.crypto.ExtendedDigest;
import org.spongycastle.crypto.digests.SHA1Digest;
import org.spongycastle.operator.MacCalculator;
import org.spongycastle.pkcs.PKCS12MacCalculatorBuilder;

public class BcPKCS12MacCalculatorBuilder
  implements PKCS12MacCalculatorBuilder
{
  private AlgorithmIdentifier algorithmIdentifier;
  private ExtendedDigest digest;
  private int iterationCount = 1024;
  private SecureRandom random;
  private int saltLength;
  
  public BcPKCS12MacCalculatorBuilder()
  {
    this(new SHA1Digest(), new AlgorithmIdentifier(OIWObjectIdentifiers.idSHA1, DERNull.INSTANCE));
  }
  
  public BcPKCS12MacCalculatorBuilder(ExtendedDigest paramExtendedDigest, AlgorithmIdentifier paramAlgorithmIdentifier)
  {
    this.digest = paramExtendedDigest;
    this.algorithmIdentifier = paramAlgorithmIdentifier;
    this.saltLength = paramExtendedDigest.getDigestSize();
  }
  
  public MacCalculator build(char[] paramArrayOfChar)
  {
    if (this.random == null) {
      this.random = new SecureRandom();
    }
    byte[] arrayOfByte = new byte[this.saltLength];
    this.random.nextBytes(arrayOfByte);
    return PKCS12PBEUtils.createMacCalculator(this.algorithmIdentifier.getAlgorithm(), this.digest, new PKCS12PBEParams(arrayOfByte, this.iterationCount), paramArrayOfChar);
  }
  
  public AlgorithmIdentifier getDigestAlgorithmIdentifier()
  {
    return this.algorithmIdentifier;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.pkcs.bc.BcPKCS12MacCalculatorBuilder
 * JD-Core Version:    0.7.0.1
 */