package org.spongycastle.jce.spec;

import java.security.spec.KeySpec;

public class ElGamalKeySpec
  implements KeySpec
{
  private ElGamalParameterSpec spec;
  
  public ElGamalKeySpec(ElGamalParameterSpec paramElGamalParameterSpec)
  {
    this.spec = paramElGamalParameterSpec;
  }
  
  public ElGamalParameterSpec getParams()
  {
    return this.spec;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jce.spec.ElGamalKeySpec
 * JD-Core Version:    0.7.0.1
 */