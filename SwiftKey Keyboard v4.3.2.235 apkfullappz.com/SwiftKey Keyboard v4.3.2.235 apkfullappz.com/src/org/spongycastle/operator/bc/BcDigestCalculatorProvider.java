package org.spongycastle.operator.bc;

import java.io.IOException;
import java.io.OutputStream;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.crypto.Digest;
import org.spongycastle.operator.DigestCalculator;
import org.spongycastle.operator.DigestCalculatorProvider;
import org.spongycastle.operator.OperatorCreationException;

public class BcDigestCalculatorProvider
  implements DigestCalculatorProvider
{
  public DigestCalculator get(final AlgorithmIdentifier paramAlgorithmIdentifier)
    throws OperatorCreationException
  {
    new DigestCalculator()
    {
      public AlgorithmIdentifier getAlgorithmIdentifier()
      {
        return paramAlgorithmIdentifier;
      }
      
      public byte[] getDigest()
      {
        return this.val$stream.getDigest();
      }
      
      public OutputStream getOutputStream()
      {
        return this.val$stream;
      }
    };
  }
  
  private class DigestOutputStream
    extends OutputStream
  {
    private Digest dig;
    
    DigestOutputStream(Digest paramDigest)
    {
      this.dig = paramDigest;
    }
    
    byte[] getDigest()
    {
      byte[] arrayOfByte = new byte[this.dig.getDigestSize()];
      this.dig.doFinal(arrayOfByte, 0);
      return arrayOfByte;
    }
    
    public void write(int paramInt)
      throws IOException
    {
      this.dig.update((byte)paramInt);
    }
    
    public void write(byte[] paramArrayOfByte)
      throws IOException
    {
      this.dig.update(paramArrayOfByte, 0, paramArrayOfByte.length);
    }
    
    public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
      throws IOException
    {
      this.dig.update(paramArrayOfByte, paramInt1, paramInt2);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.operator.bc.BcDigestCalculatorProvider
 * JD-Core Version:    0.7.0.1
 */