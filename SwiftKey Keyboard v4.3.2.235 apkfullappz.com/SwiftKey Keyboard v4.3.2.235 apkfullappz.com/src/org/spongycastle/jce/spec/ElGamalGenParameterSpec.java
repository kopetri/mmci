package org.spongycastle.jce.spec;

import java.security.spec.AlgorithmParameterSpec;

public class ElGamalGenParameterSpec
  implements AlgorithmParameterSpec
{
  private int primeSize;
  
  public ElGamalGenParameterSpec(int paramInt)
  {
    this.primeSize = paramInt;
  }
  
  public int getPrimeSize()
  {
    return this.primeSize;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jce.spec.ElGamalGenParameterSpec
 * JD-Core Version:    0.7.0.1
 */