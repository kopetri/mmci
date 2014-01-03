package org.spongycastle.crypto.io;

import java.io.IOException;
import java.io.OutputStream;
import org.spongycastle.crypto.Signer;

public class SignerOutputStream
  extends OutputStream
{
  protected Signer signer;
  
  public SignerOutputStream(Signer paramSigner)
  {
    this.signer = paramSigner;
  }
  
  public Signer getSigner()
  {
    return this.signer;
  }
  
  public void write(int paramInt)
    throws IOException
  {
    this.signer.update((byte)paramInt);
  }
  
  public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    this.signer.update(paramArrayOfByte, paramInt1, paramInt2);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.io.SignerOutputStream
 * JD-Core Version:    0.7.0.1
 */