package org.spongycastle.crypto.prng;

public class ReversedWindowGenerator
  implements RandomGenerator
{
  private final RandomGenerator generator;
  private byte[] window;
  private int windowCount;
  
  public ReversedWindowGenerator(RandomGenerator paramRandomGenerator, int paramInt)
  {
    if (paramRandomGenerator == null) {
      throw new IllegalArgumentException("generator cannot be null");
    }
    if (paramInt < 2) {
      throw new IllegalArgumentException("windowSize must be at least 2");
    }
    this.generator = paramRandomGenerator;
    this.window = new byte[paramInt];
  }
  
  private void doNextBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    int i = 0;
    for (;;)
    {
      if (i < paramInt2) {}
      int j;
      int k;
      byte[] arrayOfByte;
      int m;
      try
      {
        if (this.windowCount <= 0)
        {
          this.generator.nextBytes(this.window, 0, this.window.length);
          this.windowCount = this.window.length;
        }
        j = i + 1;
        k = paramInt1 + i;
      }
      finally {}
      try
      {
        arrayOfByte = this.window;
        m = -1 + this.windowCount;
        this.windowCount = m;
        paramArrayOfByte[k] = arrayOfByte[m];
        i = j;
      }
      finally {}
    }
    return;
    throw localObject1;
  }
  
  public void addSeedMaterial(long paramLong)
  {
    try
    {
      this.windowCount = 0;
      this.generator.addSeedMaterial(paramLong);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public void addSeedMaterial(byte[] paramArrayOfByte)
  {
    try
    {
      this.windowCount = 0;
      this.generator.addSeedMaterial(paramArrayOfByte);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public void nextBytes(byte[] paramArrayOfByte)
  {
    doNextBytes(paramArrayOfByte, 0, paramArrayOfByte.length);
  }
  
  public void nextBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    doNextBytes(paramArrayOfByte, paramInt1, paramInt2);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.prng.ReversedWindowGenerator
 * JD-Core Version:    0.7.0.1
 */