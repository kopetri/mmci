package org.spongycastle.util.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.SecureRandom;

public class FixedSecureRandom
  extends SecureRandom
{
  private byte[] _data;
  private int _index;
  private int _intPad;
  
  public FixedSecureRandom(boolean paramBoolean, byte[] paramArrayOfByte)
  {
    this(paramBoolean, new byte[][] { paramArrayOfByte });
  }
  
  public FixedSecureRandom(boolean paramBoolean, byte[][] paramArrayOfByte)
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    int i = 0;
    while (i != paramArrayOfByte.length) {
      try
      {
        localByteArrayOutputStream.write(paramArrayOfByte[i]);
        i++;
      }
      catch (IOException localIOException)
      {
        throw new IllegalArgumentException("can't save value array.");
      }
    }
    this._data = localByteArrayOutputStream.toByteArray();
    if (paramBoolean) {
      this._intPad = (this._data.length % 4);
    }
  }
  
  public FixedSecureRandom(byte[] paramArrayOfByte)
  {
    this(false, new byte[][] { paramArrayOfByte });
  }
  
  public FixedSecureRandom(byte[][] paramArrayOfByte)
  {
    this(false, paramArrayOfByte);
  }
  
  private int nextValue()
  {
    byte[] arrayOfByte = this._data;
    int i = this._index;
    this._index = (i + 1);
    return 0xFF & arrayOfByte[i];
  }
  
  public boolean isExhausted()
  {
    return this._index == this._data.length;
  }
  
  public void nextBytes(byte[] paramArrayOfByte)
  {
    System.arraycopy(this._data, this._index, paramArrayOfByte, 0, paramArrayOfByte.length);
    this._index += paramArrayOfByte.length;
  }
  
  public int nextInt()
  {
    int i = 0x0 | nextValue() << 24 | nextValue() << 16;
    if (this._intPad == 2) {
      this._intPad = (-1 + this._intPad);
    }
    while (this._intPad == 1)
    {
      this._intPad = (-1 + this._intPad);
      return i;
      i |= nextValue() << 8;
    }
    return i | nextValue();
  }
  
  public long nextLong()
  {
    return 0L | nextValue() << 56 | nextValue() << 48 | nextValue() << 40 | nextValue() << 32 | nextValue() << 24 | nextValue() << 16 | nextValue() << 8 | nextValue();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.util.test.FixedSecureRandom
 * JD-Core Version:    0.7.0.1
 */