package org.spongycastle.operator;

import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public abstract interface DigestAlgorithmIdentifierFinder
{
  public abstract AlgorithmIdentifier find(String paramString);
  
  public abstract AlgorithmIdentifier find(AlgorithmIdentifier paramAlgorithmIdentifier);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.operator.DigestAlgorithmIdentifierFinder
 * JD-Core Version:    0.7.0.1
 */