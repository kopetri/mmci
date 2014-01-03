package org.spongycastle.crypto;

public class StreamBlockCipher
  implements StreamCipher
{
  private BlockCipher cipher;
  private byte[] oneByte = new byte[1];
  
  public StreamBlockCipher(BlockCipher paramBlockCipher)
  {
    if (paramBlockCipher.getBlockSize() != 1) {
      throw new IllegalArgumentException("block cipher block size != 1.");
    }
    this.cipher = paramBlockCipher;
  }
  
  public String getAlgorithmName()
  {
    return this.cipher.getAlgorithmName();
  }
  
  public void init(boolean paramBoolean, CipherParameters paramCipherParameters)
  {
    this.cipher.init(paramBoolean, paramCipherParameters);
  }
  
  public void processBytes(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2, int paramInt3)
    throws DataLengthException
  {
    if (paramInt3 + paramInt2 > paramArrayOfByte2.length) {
      throw new DataLengthException("output buffer too small in processBytes()");
    }
    for (int i = 0; i != paramInt2; i++) {
      this.cipher.processBlock(paramArrayOfByte1, paramInt1 + i, paramArrayOfByte2, paramInt3 + i);
    }
  }
  
  public void reset()
  {
    this.cipher.reset();
  }
  
  public byte returnByte(byte paramByte)
  {
    this.oneByte[0] = paramByte;
    this.cipher.processBlock(this.oneByte, 0, this.oneByte, 0);
    return this.oneByte[0];
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.StreamBlockCipher
 * JD-Core Version:    0.7.0.1
 */