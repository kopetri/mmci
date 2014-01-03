package org.spongycastle.operator.bc;

import java.io.IOException;
import java.io.OutputStream;
import org.spongycastle.crypto.CryptoException;
import org.spongycastle.crypto.Signer;

public class BcSignerOutputStream
  extends OutputStream
{
  private Signer sig;
  
  BcSignerOutputStream(Signer paramSigner)
  {
    this.sig = paramSigner;
  }
  
  byte[] getSignature()
    throws CryptoException
  {
    return this.sig.generateSignature();
  }
  
  boolean verify(byte[] paramArrayOfByte)
  {
    return this.sig.verifySignature(paramArrayOfByte);
  }
  
  public void write(int paramInt)
    throws IOException
  {
    this.sig.update((byte)paramInt);
  }
  
  public void write(byte[] paramArrayOfByte)
    throws IOException
  {
    this.sig.update(paramArrayOfByte, 0, paramArrayOfByte.length);
  }
  
  public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    this.sig.update(paramArrayOfByte, paramInt1, paramInt2);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.operator.bc.BcSignerOutputStream
 * JD-Core Version:    0.7.0.1
 */