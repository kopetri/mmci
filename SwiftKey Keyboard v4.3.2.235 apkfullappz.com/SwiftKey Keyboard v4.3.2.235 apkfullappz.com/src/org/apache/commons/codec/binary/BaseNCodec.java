package org.apache.commons.codec.binary;

public abstract class BaseNCodec
{
  protected final byte PAD = 61;
  protected byte[] buffer;
  private final int chunkSeparatorLength;
  protected int currentLinePos;
  private final int encodedBlockSize;
  protected boolean eof;
  protected final int lineLength;
  protected int modulus;
  protected int pos;
  private int readPos;
  private final int unencodedBlockSize;
  
  protected BaseNCodec(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.unencodedBlockSize = paramInt1;
    this.encodedBlockSize = paramInt2;
    if ((paramInt3 > 0) && (paramInt4 > 0)) {}
    for (int i = paramInt2 * (paramInt3 / paramInt2);; i = 0)
    {
      this.lineLength = i;
      this.chunkSeparatorLength = paramInt4;
      return;
    }
  }
  
  private void reset()
  {
    this.buffer = null;
    this.pos = 0;
    this.readPos = 0;
    this.currentLinePos = 0;
    this.modulus = 0;
    this.eof = false;
  }
  
  private void resizeBuffer()
  {
    if (this.buffer == null)
    {
      this.buffer = new byte[getDefaultBufferSize()];
      this.pos = 0;
      this.readPos = 0;
      return;
    }
    byte[] arrayOfByte = new byte[2 * this.buffer.length];
    System.arraycopy(this.buffer, 0, arrayOfByte, 0, this.buffer.length);
    this.buffer = arrayOfByte;
  }
  
  int available()
  {
    if (this.buffer != null) {
      return this.pos - this.readPos;
    }
    return 0;
  }
  
  protected boolean containsAlphabetOrPad(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte == null) {}
    for (;;)
    {
      return false;
      for (int i = 0; i < paramArrayOfByte.length; i++) {
        if ((61 == paramArrayOfByte[i]) || (isInAlphabet(paramArrayOfByte[i]))) {
          return true;
        }
      }
    }
  }
  
  abstract void encode(byte[] paramArrayOfByte, int paramInt1, int paramInt2);
  
  public byte[] encode(byte[] paramArrayOfByte)
  {
    reset();
    if ((paramArrayOfByte == null) || (paramArrayOfByte.length == 0)) {
      return paramArrayOfByte;
    }
    encode(paramArrayOfByte, 0, paramArrayOfByte.length);
    encode(paramArrayOfByte, 0, -1);
    byte[] arrayOfByte = new byte[this.pos - this.readPos];
    readResults(arrayOfByte, 0, arrayOfByte.length);
    return arrayOfByte;
  }
  
  protected void ensureBufferSize(int paramInt)
  {
    if ((this.buffer == null) || (this.buffer.length < paramInt + this.pos)) {
      resizeBuffer();
    }
  }
  
  protected int getDefaultBufferSize()
  {
    return 8192;
  }
  
  public long getEncodedLength(byte[] paramArrayOfByte)
  {
    long l = (-1 + (paramArrayOfByte.length + this.unencodedBlockSize)) / this.unencodedBlockSize * this.encodedBlockSize;
    if (this.lineLength > 0) {
      l += (l + this.lineLength - 1L) / this.lineLength * this.chunkSeparatorLength;
    }
    return l;
  }
  
  protected abstract boolean isInAlphabet(byte paramByte);
  
  int readResults(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    if (this.buffer != null)
    {
      int i = Math.min(available(), paramInt2);
      System.arraycopy(this.buffer, this.readPos, paramArrayOfByte, paramInt1, i);
      this.readPos = (i + this.readPos);
      if (this.readPos >= this.pos) {
        this.buffer = null;
      }
      return i;
    }
    if (this.eof) {
      return -1;
    }
    return 0;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.apache.commons.codec.binary.BaseNCodec
 * JD-Core Version:    0.7.0.1
 */