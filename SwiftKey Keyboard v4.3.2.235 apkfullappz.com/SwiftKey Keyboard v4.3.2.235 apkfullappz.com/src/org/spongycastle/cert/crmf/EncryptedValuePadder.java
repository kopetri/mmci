package org.spongycastle.cert.crmf;

public abstract interface EncryptedValuePadder
{
  public abstract byte[] getPaddedData(byte[] paramArrayOfByte);
  
  public abstract byte[] getUnpaddedData(byte[] paramArrayOfByte);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.crmf.EncryptedValuePadder
 * JD-Core Version:    0.7.0.1
 */