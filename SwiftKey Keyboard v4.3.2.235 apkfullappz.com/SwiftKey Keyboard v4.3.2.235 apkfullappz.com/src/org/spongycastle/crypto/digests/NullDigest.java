package org.spongycastle.crypto.digests;

import java.io.ByteArrayOutputStream;
import org.spongycastle.crypto.Digest;

public class NullDigest
  implements Digest
{
  private ByteArrayOutputStream bOut = new ByteArrayOutputStream();
  
  public int doFinal(byte[] paramArrayOfByte, int paramInt)
  {
    byte[] arrayOfByte = this.bOut.toByteArray();
    System.arraycopy(arrayOfByte, 0, paramArrayOfByte, paramInt, arrayOfByte.length);
    reset();
    return arrayOfByte.length;
  }
  
  public String getAlgorithmName()
  {
    return "NULL";
  }
  
  public int getDigestSize()
  {
    return this.bOut.size();
  }
  
  public void reset()
  {
    this.bOut.reset();
  }
  
  public void update(byte paramByte)
  {
    this.bOut.write(paramByte);
  }
  
  public void update(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    this.bOut.write(paramArrayOfByte, paramInt1, paramInt2);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.digests.NullDigest
 * JD-Core Version:    0.7.0.1
 */