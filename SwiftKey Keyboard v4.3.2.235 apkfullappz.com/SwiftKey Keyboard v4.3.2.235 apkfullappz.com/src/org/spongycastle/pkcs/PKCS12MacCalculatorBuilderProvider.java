package org.spongycastle.pkcs;

import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public abstract interface PKCS12MacCalculatorBuilderProvider
{
  public abstract PKCS12MacCalculatorBuilder get(AlgorithmIdentifier paramAlgorithmIdentifier);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.pkcs.PKCS12MacCalculatorBuilderProvider
 * JD-Core Version:    0.7.0.1
 */