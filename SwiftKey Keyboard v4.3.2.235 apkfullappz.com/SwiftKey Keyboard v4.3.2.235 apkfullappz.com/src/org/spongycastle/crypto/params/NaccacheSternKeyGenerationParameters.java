package org.spongycastle.crypto.params;

import java.security.SecureRandom;
import org.spongycastle.crypto.KeyGenerationParameters;

public class NaccacheSternKeyGenerationParameters
  extends KeyGenerationParameters
{
  private int certainty;
  private int cntSmallPrimes;
  private boolean debug = false;
  
  public NaccacheSternKeyGenerationParameters(SecureRandom paramSecureRandom, int paramInt1, int paramInt2, int paramInt3)
  {
    this(paramSecureRandom, paramInt1, paramInt2, paramInt3, false);
  }
  
  public NaccacheSternKeyGenerationParameters(SecureRandom paramSecureRandom, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
  {
    super(paramSecureRandom, paramInt1);
    this.certainty = paramInt2;
    if (paramInt3 % 2 == 1) {
      throw new IllegalArgumentException("cntSmallPrimes must be a multiple of 2");
    }
    if (paramInt3 < 30) {
      throw new IllegalArgumentException("cntSmallPrimes must be >= 30 for security reasons");
    }
    this.cntSmallPrimes = paramInt3;
    this.debug = paramBoolean;
  }
  
  public int getCertainty()
  {
    return this.certainty;
  }
  
  public int getCntSmallPrimes()
  {
    return this.cntSmallPrimes;
  }
  
  public boolean isDebug()
  {
    return this.debug;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.params.NaccacheSternKeyGenerationParameters
 * JD-Core Version:    0.7.0.1
 */