package org.spongycastle.crypto.engines;

public class VMPCKSA3Engine
  extends VMPCEngine
{
  public String getAlgorithmName()
  {
    return "VMPC-KSA3";
  }
  
  protected void initKey(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    this.s = 0;
    this.P = new byte[256];
    for (int i = 0; i < 256; i++) {
      this.P[i] = ((byte)i);
    }
    for (int j = 0; j < 768; j++)
    {
      this.s = this.P[(0xFF & this.s + this.P[(j & 0xFF)] + paramArrayOfByte1[(j % paramArrayOfByte1.length)])];
      int i2 = this.P[(j & 0xFF)];
      this.P[(j & 0xFF)] = this.P[(0xFF & this.s)];
      this.P[(0xFF & this.s)] = i2;
    }
    for (int k = 0; k < 768; k++)
    {
      this.s = this.P[(0xFF & this.s + this.P[(k & 0xFF)] + paramArrayOfByte2[(k % paramArrayOfByte2.length)])];
      int i1 = this.P[(k & 0xFF)];
      this.P[(k & 0xFF)] = this.P[(0xFF & this.s)];
      this.P[(0xFF & this.s)] = i1;
    }
    for (int m = 0; m < 768; m++)
    {
      this.s = this.P[(0xFF & this.s + this.P[(m & 0xFF)] + paramArrayOfByte1[(m % paramArrayOfByte1.length)])];
      int n = this.P[(m & 0xFF)];
      this.P[(m & 0xFF)] = this.P[(0xFF & this.s)];
      this.P[(0xFF & this.s)] = n;
    }
    this.n = 0;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.engines.VMPCKSA3Engine
 * JD-Core Version:    0.7.0.1
 */