package org.spongycastle.crypto.prng;

import org.spongycastle.crypto.Digest;

public class DigestRandomGenerator
  implements RandomGenerator
{
  private static long CYCLE_COUNT = 10L;
  private Digest digest;
  private byte[] seed;
  private long seedCounter;
  private byte[] state;
  private long stateCounter;
  
  public DigestRandomGenerator(Digest paramDigest)
  {
    this.digest = paramDigest;
    this.seed = new byte[paramDigest.getDigestSize()];
    this.seedCounter = 1L;
    this.state = new byte[paramDigest.getDigestSize()];
    this.stateCounter = 1L;
  }
  
  private void cycleSeed()
  {
    digestUpdate(this.seed);
    long l = this.seedCounter;
    this.seedCounter = (1L + l);
    digestAddCounter(l);
    digestDoFinal(this.seed);
  }
  
  private void digestAddCounter(long paramLong)
  {
    for (int i = 0; i != 8; i++)
    {
      this.digest.update((byte)(int)paramLong);
      paramLong >>>= 8;
    }
  }
  
  private void digestDoFinal(byte[] paramArrayOfByte)
  {
    this.digest.doFinal(paramArrayOfByte, 0);
  }
  
  private void digestUpdate(byte[] paramArrayOfByte)
  {
    this.digest.update(paramArrayOfByte, 0, paramArrayOfByte.length);
  }
  
  private void generateState()
  {
    long l = this.stateCounter;
    this.stateCounter = (1L + l);
    digestAddCounter(l);
    digestUpdate(this.state);
    digestUpdate(this.seed);
    digestDoFinal(this.state);
    if (this.stateCounter % CYCLE_COUNT == 0L) {
      cycleSeed();
    }
  }
  
  public void addSeedMaterial(long paramLong)
  {
    try
    {
      digestAddCounter(paramLong);
      digestUpdate(this.seed);
      digestDoFinal(this.seed);
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
      digestUpdate(paramArrayOfByte);
      digestUpdate(this.seed);
      digestDoFinal(this.seed);
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
    nextBytes(paramArrayOfByte, 0, paramArrayOfByte.length);
  }
  
  public void nextBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    int i = 0;
    for (;;)
    {
      int k;
      byte[] arrayOfByte;
      try
      {
        generateState();
        int j = paramInt1 + paramInt2;
        k = paramInt1;
        if (k != j)
        {
          if (i != this.state.length) {
            break label86;
          }
          generateState();
          m = 0;
        }
      }
      finally {}
      try
      {
        arrayOfByte = this.state;
        i = m + 1;
        paramArrayOfByte[k] = arrayOfByte[m];
        k++;
        continue;
      }
      finally {}
      return;
      label86:
      int m = i;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.prng.DigestRandomGenerator
 * JD-Core Version:    0.7.0.1
 */