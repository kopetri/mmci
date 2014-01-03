package org.spongycastle.crypto;

public abstract interface SignerWithRecovery
  extends Signer
{
  public abstract byte[] getRecoveredMessage();
  
  public abstract boolean hasFullMessage();
  
  public abstract void updateWithRecoveredMessage(byte[] paramArrayOfByte)
    throws InvalidCipherTextException;
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.SignerWithRecovery
 * JD-Core Version:    0.7.0.1
 */