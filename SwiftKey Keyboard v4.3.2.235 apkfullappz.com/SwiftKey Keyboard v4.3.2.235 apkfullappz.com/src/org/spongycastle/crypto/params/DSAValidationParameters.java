package org.spongycastle.crypto.params;

import org.spongycastle.util.Arrays;

public class DSAValidationParameters
{
  private int counter;
  private byte[] seed;
  
  public DSAValidationParameters(byte[] paramArrayOfByte, int paramInt)
  {
    this.seed = paramArrayOfByte;
    this.counter = paramInt;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof DSAValidationParameters)) {}
    DSAValidationParameters localDSAValidationParameters;
    do
    {
      return false;
      localDSAValidationParameters = (DSAValidationParameters)paramObject;
    } while (localDSAValidationParameters.counter != this.counter);
    return Arrays.areEqual(this.seed, localDSAValidationParameters.seed);
  }
  
  public int getCounter()
  {
    return this.counter;
  }
  
  public byte[] getSeed()
  {
    return this.seed;
  }
  
  public int hashCode()
  {
    return this.counter ^ Arrays.hashCode(this.seed);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.params.DSAValidationParameters
 * JD-Core Version:    0.7.0.1
 */