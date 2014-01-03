package org.spongycastle.pkcs;

import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.operator.MacCalculator;

public abstract interface PKCS12MacCalculatorBuilder
{
  public abstract MacCalculator build(char[] paramArrayOfChar);
  
  public abstract AlgorithmIdentifier getDigestAlgorithmIdentifier();
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.pkcs.PKCS12MacCalculatorBuilder
 * JD-Core Version:    0.7.0.1
 */