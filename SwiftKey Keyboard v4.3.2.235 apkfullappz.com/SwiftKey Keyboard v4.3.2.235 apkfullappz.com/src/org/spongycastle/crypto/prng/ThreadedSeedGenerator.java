package org.spongycastle.crypto.prng;

public class ThreadedSeedGenerator
{
  public byte[] generateSeed(int paramInt, boolean paramBoolean)
  {
    return new SeedGenerator(null).generateSeed(paramInt, paramBoolean);
  }
  
  private class SeedGenerator
    implements Runnable
  {
    private volatile int counter = 0;
    private volatile boolean stop = false;
    
    private SeedGenerator() {}
    
    public byte[] generateSeed(int paramInt, boolean paramBoolean)
    {
      Thread localThread = new Thread(this);
      byte[] arrayOfByte = new byte[paramInt];
      this.counter = 0;
      this.stop = false;
      int i = 0;
      localThread.start();
      if (paramBoolean) {}
      int k;
      for (int j = paramInt;; j = paramInt * 8)
      {
        k = 0;
        if (k >= j) {
          break label134;
        }
        while (this.counter == i) {
          try
          {
            Thread.sleep(1L);
          }
          catch (InterruptedException localInterruptedException) {}
        }
      }
      i = this.counter;
      if (paramBoolean) {
        arrayOfByte[k] = ((byte)(i & 0xFF));
      }
      for (;;)
      {
        k++;
        break;
        int m = k / 8;
        arrayOfByte[m] = ((byte)(arrayOfByte[m] << 1 | i & 0x1));
      }
      label134:
      this.stop = true;
      return arrayOfByte;
    }
    
    public void run()
    {
      while (!this.stop) {
        this.counter = (1 + this.counter);
      }
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.prng.ThreadedSeedGenerator
 * JD-Core Version:    0.7.0.1
 */