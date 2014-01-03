package org.spongycastle.crypto.params;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.crypto.KeyGenerationParameters;

public class GOST3410KeyGenerationParameters
  extends KeyGenerationParameters
{
  private GOST3410Parameters params;
  
  public GOST3410KeyGenerationParameters(SecureRandom paramSecureRandom, GOST3410Parameters paramGOST3410Parameters)
  {
    super(paramSecureRandom, -1 + paramGOST3410Parameters.getP().bitLength());
    this.params = paramGOST3410Parameters;
  }
  
  public GOST3410Parameters getParameters()
  {
    return this.params;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.params.GOST3410KeyGenerationParameters
 * JD-Core Version:    0.7.0.1
 */