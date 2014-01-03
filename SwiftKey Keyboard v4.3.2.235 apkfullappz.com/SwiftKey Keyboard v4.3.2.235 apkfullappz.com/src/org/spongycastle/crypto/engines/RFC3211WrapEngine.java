package org.spongycastle.crypto.engines;

import java.security.SecureRandom;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.Wrapper;
import org.spongycastle.crypto.modes.CBCBlockCipher;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.crypto.params.ParametersWithRandom;

public class RFC3211WrapEngine
  implements Wrapper
{
  private CBCBlockCipher engine;
  private boolean forWrapping;
  private ParametersWithIV param;
  private SecureRandom rand;
  
  public RFC3211WrapEngine(BlockCipher paramBlockCipher)
  {
    this.engine = new CBCBlockCipher(paramBlockCipher);
  }
  
  public String getAlgorithmName()
  {
    return this.engine.getUnderlyingCipher().getAlgorithmName() + "/RFC3211Wrap";
  }
  
  public void init(boolean paramBoolean, CipherParameters paramCipherParameters)
  {
    this.forWrapping = paramBoolean;
    if ((paramCipherParameters instanceof ParametersWithRandom))
    {
      ParametersWithRandom localParametersWithRandom = (ParametersWithRandom)paramCipherParameters;
      this.rand = localParametersWithRandom.getRandom();
      this.param = ((ParametersWithIV)localParametersWithRandom.getParameters());
      return;
    }
    if (paramBoolean) {
      this.rand = new SecureRandom();
    }
    this.param = ((ParametersWithIV)paramCipherParameters);
  }
  
  public byte[] unwrap(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws InvalidCipherTextException
  {
    if (this.forWrapping) {
      throw new IllegalStateException("not set for unwrapping");
    }
    int i = this.engine.getBlockSize();
    if (paramInt2 < i * 2) {
      throw new InvalidCipherTextException("input too short");
    }
    byte[] arrayOfByte1 = new byte[paramInt2];
    byte[] arrayOfByte2 = new byte[i];
    System.arraycopy(paramArrayOfByte, paramInt1, arrayOfByte1, 0, paramInt2);
    System.arraycopy(paramArrayOfByte, paramInt1, arrayOfByte2, 0, arrayOfByte2.length);
    this.engine.init(false, new ParametersWithIV(this.param.getParameters(), arrayOfByte2));
    int j = i;
    while (j < arrayOfByte1.length)
    {
      this.engine.processBlock(arrayOfByte1, j, arrayOfByte1, j);
      j += i;
    }
    System.arraycopy(arrayOfByte1, arrayOfByte1.length - arrayOfByte2.length, arrayOfByte2, 0, arrayOfByte2.length);
    this.engine.init(false, new ParametersWithIV(this.param.getParameters(), arrayOfByte2));
    this.engine.processBlock(arrayOfByte1, 0, arrayOfByte1, 0);
    this.engine.init(false, this.param);
    int k = 0;
    while (k < arrayOfByte1.length)
    {
      this.engine.processBlock(arrayOfByte1, k, arrayOfByte1, k);
      k += i;
    }
    if ((0xFF & arrayOfByte1[0]) > -4 + arrayOfByte1.length) {
      throw new InvalidCipherTextException("wrapped key corrupted");
    }
    byte[] arrayOfByte3 = new byte[0xFF & arrayOfByte1[0]];
    System.arraycopy(arrayOfByte1, 4, arrayOfByte3, 0, arrayOfByte1[0]);
    int m = 0;
    for (int n = 0; n != 3; n++) {
      m |= (byte)(0xFFFFFFFF ^ arrayOfByte1[(n + 1)]) ^ arrayOfByte3[n];
    }
    if (m != 0) {
      throw new InvalidCipherTextException("wrapped key fails checksum");
    }
    return arrayOfByte3;
  }
  
  public byte[] wrap(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    if (!this.forWrapping) {
      throw new IllegalStateException("not set for wrapping");
    }
    this.engine.init(true, this.param);
    int i = this.engine.getBlockSize();
    int j;
    if (paramInt2 + 4 < i * 2) {
      j = i * 2;
    }
    byte[] arrayOfByte;
    for (;;)
    {
      arrayOfByte = new byte[j];
      arrayOfByte[0] = ((byte)paramInt2);
      arrayOfByte[1] = ((byte)(0xFFFFFFFF ^ paramArrayOfByte[paramInt1]));
      arrayOfByte[2] = ((byte)(0xFFFFFFFF ^ paramArrayOfByte[(paramInt1 + 1)]));
      arrayOfByte[3] = ((byte)(0xFFFFFFFF ^ paramArrayOfByte[(paramInt1 + 2)]));
      System.arraycopy(paramArrayOfByte, paramInt1, arrayOfByte, 4, paramInt2);
      for (int k = paramInt2 + 4; k < arrayOfByte.length; k++) {
        arrayOfByte[k] = ((byte)this.rand.nextInt());
      }
      if ((paramInt2 + 4) % i == 0) {
        j = paramInt2 + 4;
      } else {
        j = i * (1 + (paramInt2 + 4) / i);
      }
    }
    int m = 0;
    while (m < arrayOfByte.length)
    {
      this.engine.processBlock(arrayOfByte, m, arrayOfByte, m);
      m += i;
    }
    int n = 0;
    while (n < arrayOfByte.length)
    {
      this.engine.processBlock(arrayOfByte, n, arrayOfByte, n);
      n += i;
    }
    return arrayOfByte;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.engines.RFC3211WrapEngine
 * JD-Core Version:    0.7.0.1
 */