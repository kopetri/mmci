package org.spongycastle.crypto.engines;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.MaxBytesExceededException;
import org.spongycastle.crypto.StreamCipher;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.crypto.util.Pack;
import org.spongycastle.util.Strings;

public class Salsa20Engine
  implements StreamCipher
{
  private static final int STATE_SIZE = 16;
  private static final byte[] sigma = Strings.toByteArray("expand 32-byte k");
  private static final byte[] tau = Strings.toByteArray("expand 16-byte k");
  private int cW0;
  private int cW1;
  private int cW2;
  private int[] engineState = new int[16];
  private int index = 0;
  private boolean initialised = false;
  private byte[] keyStream = new byte[64];
  private byte[] workingIV = null;
  private byte[] workingKey = null;
  private int[] x = new int[16];
  
  private void generateKeyStream(byte[] paramArrayOfByte)
  {
    salsaCore(20, this.engineState, this.x);
    Pack.intToLittleEndian(this.x, paramArrayOfByte, 0);
  }
  
  private boolean limitExceeded()
  {
    int i = 1 + this.cW0;
    this.cW0 = i;
    boolean bool = false;
    if (i == 0)
    {
      int j = 1 + this.cW1;
      this.cW1 = j;
      bool = false;
      if (j == 0)
      {
        int k = 1 + this.cW2;
        this.cW2 = k;
        int m = k & 0x20;
        bool = false;
        if (m != 0) {
          bool = true;
        }
      }
    }
    return bool;
  }
  
  private boolean limitExceeded(int paramInt)
  {
    this.cW0 = (paramInt + this.cW0);
    int i = this.cW0;
    boolean bool = false;
    if (i < paramInt)
    {
      int j = this.cW0;
      bool = false;
      if (j >= 0)
      {
        int k = 1 + this.cW1;
        this.cW1 = k;
        bool = false;
        if (k == 0)
        {
          int m = 1 + this.cW2;
          this.cW2 = m;
          int n = m & 0x20;
          bool = false;
          if (n != 0) {
            bool = true;
          }
        }
      }
    }
    return bool;
  }
  
  private void resetCounter()
  {
    this.cW0 = 0;
    this.cW1 = 0;
    this.cW2 = 0;
  }
  
  private static int rotl(int paramInt1, int paramInt2)
  {
    return paramInt1 << paramInt2 | paramInt1 >>> -paramInt2;
  }
  
  public static void salsaCore(int paramInt, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    System.arraycopy(paramArrayOfInt1, 0, paramArrayOfInt2, 0, paramArrayOfInt1.length);
    for (int i = paramInt; i > 0; i -= 2)
    {
      paramArrayOfInt2[4] ^= rotl(paramArrayOfInt2[0] + paramArrayOfInt2[12], 7);
      paramArrayOfInt2[8] ^= rotl(paramArrayOfInt2[4] + paramArrayOfInt2[0], 9);
      paramArrayOfInt2[12] ^= rotl(paramArrayOfInt2[8] + paramArrayOfInt2[4], 13);
      paramArrayOfInt2[0] ^= rotl(paramArrayOfInt2[12] + paramArrayOfInt2[8], 18);
      paramArrayOfInt2[9] ^= rotl(paramArrayOfInt2[5] + paramArrayOfInt2[1], 7);
      paramArrayOfInt2[13] ^= rotl(paramArrayOfInt2[9] + paramArrayOfInt2[5], 9);
      paramArrayOfInt2[1] ^= rotl(paramArrayOfInt2[13] + paramArrayOfInt2[9], 13);
      paramArrayOfInt2[5] ^= rotl(paramArrayOfInt2[1] + paramArrayOfInt2[13], 18);
      paramArrayOfInt2[14] ^= rotl(paramArrayOfInt2[10] + paramArrayOfInt2[6], 7);
      paramArrayOfInt2[2] ^= rotl(paramArrayOfInt2[14] + paramArrayOfInt2[10], 9);
      paramArrayOfInt2[6] ^= rotl(paramArrayOfInt2[2] + paramArrayOfInt2[14], 13);
      paramArrayOfInt2[10] ^= rotl(paramArrayOfInt2[6] + paramArrayOfInt2[2], 18);
      paramArrayOfInt2[3] ^= rotl(paramArrayOfInt2[15] + paramArrayOfInt2[11], 7);
      paramArrayOfInt2[7] ^= rotl(paramArrayOfInt2[3] + paramArrayOfInt2[15], 9);
      paramArrayOfInt2[11] ^= rotl(paramArrayOfInt2[7] + paramArrayOfInt2[3], 13);
      paramArrayOfInt2[15] ^= rotl(paramArrayOfInt2[11] + paramArrayOfInt2[7], 18);
      paramArrayOfInt2[1] ^= rotl(paramArrayOfInt2[0] + paramArrayOfInt2[3], 7);
      paramArrayOfInt2[2] ^= rotl(paramArrayOfInt2[1] + paramArrayOfInt2[0], 9);
      paramArrayOfInt2[3] ^= rotl(paramArrayOfInt2[2] + paramArrayOfInt2[1], 13);
      paramArrayOfInt2[0] ^= rotl(paramArrayOfInt2[3] + paramArrayOfInt2[2], 18);
      paramArrayOfInt2[6] ^= rotl(paramArrayOfInt2[5] + paramArrayOfInt2[4], 7);
      paramArrayOfInt2[7] ^= rotl(paramArrayOfInt2[6] + paramArrayOfInt2[5], 9);
      paramArrayOfInt2[4] ^= rotl(paramArrayOfInt2[7] + paramArrayOfInt2[6], 13);
      paramArrayOfInt2[5] ^= rotl(paramArrayOfInt2[4] + paramArrayOfInt2[7], 18);
      paramArrayOfInt2[11] ^= rotl(paramArrayOfInt2[10] + paramArrayOfInt2[9], 7);
      paramArrayOfInt2[8] ^= rotl(paramArrayOfInt2[11] + paramArrayOfInt2[10], 9);
      paramArrayOfInt2[9] ^= rotl(paramArrayOfInt2[8] + paramArrayOfInt2[11], 13);
      paramArrayOfInt2[10] ^= rotl(paramArrayOfInt2[9] + paramArrayOfInt2[8], 18);
      paramArrayOfInt2[12] ^= rotl(paramArrayOfInt2[15] + paramArrayOfInt2[14], 7);
      paramArrayOfInt2[13] ^= rotl(paramArrayOfInt2[12] + paramArrayOfInt2[15], 9);
      paramArrayOfInt2[14] ^= rotl(paramArrayOfInt2[13] + paramArrayOfInt2[12], 13);
      paramArrayOfInt2[15] ^= rotl(paramArrayOfInt2[14] + paramArrayOfInt2[13], 18);
    }
    for (int j = 0; j < 16; j++) {
      paramArrayOfInt2[j] += paramArrayOfInt1[j];
    }
  }
  
  private void setKey(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    this.workingKey = paramArrayOfByte1;
    this.workingIV = paramArrayOfByte2;
    this.index = 0;
    resetCounter();
    this.engineState[1] = Pack.littleEndianToInt(this.workingKey, 0);
    this.engineState[2] = Pack.littleEndianToInt(this.workingKey, 4);
    this.engineState[3] = Pack.littleEndianToInt(this.workingKey, 8);
    this.engineState[4] = Pack.littleEndianToInt(this.workingKey, 12);
    byte[] arrayOfByte;
    if (this.workingKey.length == 32) {
      arrayOfByte = sigma;
    }
    for (int i = 16;; i = 0)
    {
      this.engineState[11] = Pack.littleEndianToInt(this.workingKey, i);
      this.engineState[12] = Pack.littleEndianToInt(this.workingKey, i + 4);
      this.engineState[13] = Pack.littleEndianToInt(this.workingKey, i + 8);
      this.engineState[14] = Pack.littleEndianToInt(this.workingKey, i + 12);
      this.engineState[0] = Pack.littleEndianToInt(arrayOfByte, 0);
      this.engineState[5] = Pack.littleEndianToInt(arrayOfByte, 4);
      this.engineState[10] = Pack.littleEndianToInt(arrayOfByte, 8);
      this.engineState[15] = Pack.littleEndianToInt(arrayOfByte, 12);
      this.engineState[6] = Pack.littleEndianToInt(this.workingIV, 0);
      this.engineState[7] = Pack.littleEndianToInt(this.workingIV, 4);
      int[] arrayOfInt = this.engineState;
      this.engineState[9] = 0;
      arrayOfInt[8] = 0;
      this.initialised = true;
      return;
      arrayOfByte = tau;
    }
  }
  
  public String getAlgorithmName()
  {
    return "Salsa20";
  }
  
  public void init(boolean paramBoolean, CipherParameters paramCipherParameters)
  {
    if (!(paramCipherParameters instanceof ParametersWithIV)) {
      throw new IllegalArgumentException("Salsa20 Init parameters must include an IV");
    }
    ParametersWithIV localParametersWithIV = (ParametersWithIV)paramCipherParameters;
    byte[] arrayOfByte = localParametersWithIV.getIV();
    if ((arrayOfByte == null) || (arrayOfByte.length != 8)) {
      throw new IllegalArgumentException("Salsa20 requires exactly 8 bytes of IV");
    }
    if (!(localParametersWithIV.getParameters() instanceof KeyParameter)) {
      throw new IllegalArgumentException("Salsa20 Init parameters must include a key");
    }
    this.workingKey = ((KeyParameter)localParametersWithIV.getParameters()).getKey();
    this.workingIV = arrayOfByte;
    setKey(this.workingKey, this.workingIV);
  }
  
  public void processBytes(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2, int paramInt3)
  {
    if (!this.initialised) {
      throw new IllegalStateException(getAlgorithmName() + " not initialised");
    }
    if (paramInt1 + paramInt2 > paramArrayOfByte1.length) {
      throw new DataLengthException("input buffer too short");
    }
    if (paramInt3 + paramInt2 > paramArrayOfByte2.length) {
      throw new DataLengthException("output buffer too short");
    }
    if (limitExceeded(paramInt2)) {
      throw new MaxBytesExceededException("2^70 byte limit per IV would be exceeded; Change IV");
    }
    for (int i = 0; i < paramInt2; i++)
    {
      if (this.index == 0)
      {
        generateKeyStream(this.keyStream);
        int[] arrayOfInt1 = this.engineState;
        int j = 1 + arrayOfInt1[8];
        arrayOfInt1[8] = j;
        if (j == 0)
        {
          int[] arrayOfInt2 = this.engineState;
          arrayOfInt2[9] = (1 + arrayOfInt2[9]);
        }
      }
      paramArrayOfByte2[(i + paramInt3)] = ((byte)(this.keyStream[this.index] ^ paramArrayOfByte1[(i + paramInt1)]));
      this.index = (0x3F & 1 + this.index);
    }
  }
  
  public void reset()
  {
    setKey(this.workingKey, this.workingIV);
  }
  
  public byte returnByte(byte paramByte)
  {
    if (limitExceeded()) {
      throw new MaxBytesExceededException("2^70 byte limit per IV; Change IV");
    }
    if (this.index == 0)
    {
      generateKeyStream(this.keyStream);
      int[] arrayOfInt1 = this.engineState;
      int i = 1 + arrayOfInt1[8];
      arrayOfInt1[8] = i;
      if (i == 0)
      {
        int[] arrayOfInt2 = this.engineState;
        arrayOfInt2[9] = (1 + arrayOfInt2[9]);
      }
    }
    byte b = (byte)(paramByte ^ this.keyStream[this.index]);
    this.index = (0x3F & 1 + this.index);
    return b;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.engines.Salsa20Engine
 * JD-Core Version:    0.7.0.1
 */