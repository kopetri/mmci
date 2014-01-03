package org.spongycastle.crypto.macs;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;

public class VMPCMac
  implements Mac
{
  private byte[] P = null;
  private byte[] T;
  private byte g;
  private byte n = 0;
  private byte s = 0;
  private byte[] workingIV;
  private byte[] workingKey;
  private byte x1;
  private byte x2;
  private byte x3;
  private byte x4;
  
  private void initKey(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    this.s = 0;
    this.P = new byte[256];
    for (int i = 0; i < 256; i++) {
      this.P[i] = ((byte)i);
    }
    for (int j = 0; j < 768; j++)
    {
      this.s = this.P[(0xFF & this.s + this.P[(j & 0xFF)] + paramArrayOfByte1[(j % paramArrayOfByte1.length)])];
      int i1 = this.P[(j & 0xFF)];
      this.P[(j & 0xFF)] = this.P[(0xFF & this.s)];
      this.P[(0xFF & this.s)] = i1;
    }
    for (int k = 0; k < 768; k++)
    {
      this.s = this.P[(0xFF & this.s + this.P[(k & 0xFF)] + paramArrayOfByte2[(k % paramArrayOfByte2.length)])];
      int m = this.P[(k & 0xFF)];
      this.P[(k & 0xFF)] = this.P[(0xFF & this.s)];
      this.P[(0xFF & this.s)] = m;
    }
    this.n = 0;
  }
  
  public int doFinal(byte[] paramArrayOfByte, int paramInt)
    throws DataLengthException, IllegalStateException
  {
    for (int i = 1; i < 25; i++)
    {
      this.s = this.P[(0xFF & this.s + this.P[(0xFF & this.n)])];
      this.x4 = this.P[(0xFF & i + (this.x4 + this.x3))];
      this.x3 = this.P[(0xFF & i + (this.x3 + this.x2))];
      this.x2 = this.P[(0xFF & i + (this.x2 + this.x1))];
      this.x1 = this.P[(0xFF & i + (this.x1 + this.s))];
      this.T[(0x1F & this.g)] = ((byte)(this.T[(0x1F & this.g)] ^ this.x1));
      this.T[(0x1F & 1 + this.g)] = ((byte)(this.T[(0x1F & 1 + this.g)] ^ this.x2));
      this.T[(0x1F & 2 + this.g)] = ((byte)(this.T[(0x1F & 2 + this.g)] ^ this.x3));
      this.T[(0x1F & 3 + this.g)] = ((byte)(this.T[(0x1F & 3 + this.g)] ^ this.x4));
      this.g = ((byte)(0x1F & 4 + this.g));
      int i2 = this.P[(0xFF & this.n)];
      this.P[(0xFF & this.n)] = this.P[(0xFF & this.s)];
      this.P[(0xFF & this.s)] = i2;
      this.n = ((byte)(0xFF & 1 + this.n));
    }
    for (int j = 0; j < 768; j++)
    {
      this.s = this.P[(0xFF & this.s + this.P[(j & 0xFF)] + this.T[(j & 0x1F)])];
      int i1 = this.P[(j & 0xFF)];
      this.P[(j & 0xFF)] = this.P[(0xFF & this.s)];
      this.P[(0xFF & this.s)] = i1;
    }
    byte[] arrayOfByte = new byte[20];
    for (int k = 0; k < 20; k++)
    {
      this.s = this.P[(0xFF & this.s + this.P[(k & 0xFF)])];
      arrayOfByte[k] = this.P[(0xFF & 1 + this.P[(0xFF & this.P[(0xFF & this.s)])])];
      int m = this.P[(k & 0xFF)];
      this.P[(k & 0xFF)] = this.P[(0xFF & this.s)];
      this.P[(0xFF & this.s)] = m;
    }
    System.arraycopy(arrayOfByte, 0, paramArrayOfByte, paramInt, arrayOfByte.length);
    reset();
    return arrayOfByte.length;
  }
  
  public String getAlgorithmName()
  {
    return "VMPC-MAC";
  }
  
  public int getMacSize()
  {
    return 20;
  }
  
  public void init(CipherParameters paramCipherParameters)
    throws IllegalArgumentException
  {
    if (!(paramCipherParameters instanceof ParametersWithIV)) {
      throw new IllegalArgumentException("VMPC-MAC Init parameters must include an IV");
    }
    ParametersWithIV localParametersWithIV = (ParametersWithIV)paramCipherParameters;
    KeyParameter localKeyParameter = (KeyParameter)localParametersWithIV.getParameters();
    if (!(localParametersWithIV.getParameters() instanceof KeyParameter)) {
      throw new IllegalArgumentException("VMPC-MAC Init parameters must include a key");
    }
    this.workingIV = localParametersWithIV.getIV();
    if ((this.workingIV == null) || (this.workingIV.length <= 0) || (this.workingIV.length > 768)) {
      throw new IllegalArgumentException("VMPC-MAC requires 1 to 768 bytes of IV");
    }
    this.workingKey = localKeyParameter.getKey();
    reset();
  }
  
  public void reset()
  {
    initKey(this.workingKey, this.workingIV);
    this.n = 0;
    this.x4 = 0;
    this.x3 = 0;
    this.x2 = 0;
    this.x1 = 0;
    this.g = 0;
    this.T = new byte[32];
    for (int i = 0; i < 32; i++) {
      this.T[i] = 0;
    }
  }
  
  public void update(byte paramByte)
    throws IllegalStateException
  {
    this.s = this.P[(0xFF & this.s + this.P[(0xFF & this.n)])];
    int i = (byte)(paramByte ^ this.P[(0xFF & 1 + this.P[(0xFF & this.P[(0xFF & this.s)])])]);
    this.x4 = this.P[(0xFF & this.x4 + this.x3)];
    this.x3 = this.P[(0xFF & this.x3 + this.x2)];
    this.x2 = this.P[(0xFF & this.x2 + this.x1)];
    this.x1 = this.P[(0xFF & i + (this.x1 + this.s))];
    this.T[(0x1F & this.g)] = ((byte)(this.T[(0x1F & this.g)] ^ this.x1));
    this.T[(0x1F & 1 + this.g)] = ((byte)(this.T[(0x1F & 1 + this.g)] ^ this.x2));
    this.T[(0x1F & 2 + this.g)] = ((byte)(this.T[(0x1F & 2 + this.g)] ^ this.x3));
    this.T[(0x1F & 3 + this.g)] = ((byte)(this.T[(0x1F & 3 + this.g)] ^ this.x4));
    this.g = ((byte)(0x1F & 4 + this.g));
    int j = this.P[(0xFF & this.n)];
    this.P[(0xFF & this.n)] = this.P[(0xFF & this.s)];
    this.P[(0xFF & this.s)] = j;
    this.n = ((byte)(0xFF & 1 + this.n));
  }
  
  public void update(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws DataLengthException, IllegalStateException
  {
    if (paramInt1 + paramInt2 > paramArrayOfByte.length) {
      throw new DataLengthException("input buffer too short");
    }
    for (int i = 0; i < paramInt2; i++) {
      update(paramArrayOfByte[i]);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.macs.VMPCMac
 * JD-Core Version:    0.7.0.1
 */