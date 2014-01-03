package org.spongycastle.crypto.params;

import java.security.SecureRandom;
import org.spongycastle.crypto.CipherParameters;

public class ParametersWithRandom
  implements CipherParameters
{
  private CipherParameters parameters;
  private SecureRandom random;
  
  public ParametersWithRandom(CipherParameters paramCipherParameters)
  {
    this(paramCipherParameters, new SecureRandom());
  }
  
  public ParametersWithRandom(CipherParameters paramCipherParameters, SecureRandom paramSecureRandom)
  {
    this.random = paramSecureRandom;
    this.parameters = paramCipherParameters;
  }
  
  public CipherParameters getParameters()
  {
    return this.parameters;
  }
  
  public SecureRandom getRandom()
  {
    return this.random;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.params.ParametersWithRandom
 * JD-Core Version:    0.7.0.1
 */