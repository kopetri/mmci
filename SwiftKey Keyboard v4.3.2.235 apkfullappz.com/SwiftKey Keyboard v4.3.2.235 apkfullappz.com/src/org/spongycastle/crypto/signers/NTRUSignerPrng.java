package org.spongycastle.crypto.signers;

import java.nio.ByteBuffer;
import org.spongycastle.crypto.Digest;

public class NTRUSignerPrng
{
  private int counter = 0;
  private Digest hashAlg;
  private byte[] seed;
  
  NTRUSignerPrng(byte[] paramArrayOfByte, Digest paramDigest)
  {
    this.seed = paramArrayOfByte;
    this.hashAlg = paramDigest;
  }
  
  byte[] nextBytes(int paramInt)
  {
    ByteBuffer localByteBuffer1 = ByteBuffer.allocate(paramInt);
    if (localByteBuffer1.hasRemaining())
    {
      ByteBuffer localByteBuffer2 = ByteBuffer.allocate(4 + this.seed.length);
      localByteBuffer2.put(this.seed);
      localByteBuffer2.putInt(this.counter);
      byte[] arrayOfByte1 = localByteBuffer2.array();
      byte[] arrayOfByte2 = new byte[this.hashAlg.getDigestSize()];
      this.hashAlg.update(arrayOfByte1, 0, arrayOfByte1.length);
      this.hashAlg.doFinal(arrayOfByte2, 0);
      if (localByteBuffer1.remaining() < arrayOfByte2.length) {
        localByteBuffer1.put(arrayOfByte2, 0, localByteBuffer1.remaining());
      }
      for (;;)
      {
        this.counter = (1 + this.counter);
        break;
        localByteBuffer1.put(arrayOfByte2);
      }
    }
    return localByteBuffer1.array();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.signers.NTRUSignerPrng
 * JD-Core Version:    0.7.0.1
 */