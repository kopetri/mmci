package org.spongycastle.cert.crmf;

import java.security.SecureRandom;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.digests.SHA1Digest;
import org.spongycastle.crypto.generators.MGF1BytesGenerator;
import org.spongycastle.crypto.params.MGFParameters;

public class FixedLengthMGF1Padder
  implements EncryptedValuePadder
{
  private Digest dig = new SHA1Digest();
  private int length;
  private SecureRandom random;
  
  public FixedLengthMGF1Padder(int paramInt)
  {
    this(paramInt, null);
  }
  
  public FixedLengthMGF1Padder(int paramInt, SecureRandom paramSecureRandom)
  {
    this.length = paramInt;
    this.random = paramSecureRandom;
  }
  
  public byte[] getPaddedData(byte[] paramArrayOfByte)
  {
    byte[] arrayOfByte1 = new byte[this.length];
    byte[] arrayOfByte2 = new byte[this.dig.getDigestSize()];
    byte[] arrayOfByte3 = new byte[this.length - this.dig.getDigestSize()];
    if (this.random == null) {
      this.random = new SecureRandom();
    }
    this.random.nextBytes(arrayOfByte2);
    MGF1BytesGenerator localMGF1BytesGenerator = new MGF1BytesGenerator(this.dig);
    localMGF1BytesGenerator.init(new MGFParameters(arrayOfByte2));
    localMGF1BytesGenerator.generateBytes(arrayOfByte3, 0, arrayOfByte3.length);
    System.arraycopy(arrayOfByte2, 0, arrayOfByte1, 0, arrayOfByte2.length);
    System.arraycopy(paramArrayOfByte, 0, arrayOfByte1, arrayOfByte2.length, paramArrayOfByte.length);
    for (int i = 1 + (arrayOfByte2.length + paramArrayOfByte.length); i != arrayOfByte1.length; i++) {
      arrayOfByte1[i] = ((byte)(1 + this.random.nextInt(255)));
    }
    for (int j = 0; j != arrayOfByte3.length; j++)
    {
      int k = j + arrayOfByte2.length;
      arrayOfByte1[k] = ((byte)(arrayOfByte1[k] ^ arrayOfByte3[j]));
    }
    return arrayOfByte1;
  }
  
  public byte[] getUnpaddedData(byte[] paramArrayOfByte)
  {
    byte[] arrayOfByte1 = new byte[this.dig.getDigestSize()];
    byte[] arrayOfByte2 = new byte[this.length - this.dig.getDigestSize()];
    System.arraycopy(paramArrayOfByte, 0, arrayOfByte1, 0, arrayOfByte1.length);
    MGF1BytesGenerator localMGF1BytesGenerator = new MGF1BytesGenerator(this.dig);
    localMGF1BytesGenerator.init(new MGFParameters(arrayOfByte1));
    localMGF1BytesGenerator.generateBytes(arrayOfByte2, 0, arrayOfByte2.length);
    for (int i = 0; i != arrayOfByte2.length; i++)
    {
      int n = i + arrayOfByte1.length;
      paramArrayOfByte[n] = ((byte)(paramArrayOfByte[n] ^ arrayOfByte2[i]));
    }
    int m;
    for (int j = -1 + paramArrayOfByte.length;; j--)
    {
      int k = arrayOfByte1.length;
      m = 0;
      if (j != k)
      {
        if (paramArrayOfByte[j] == 0) {
          m = j;
        }
      }
      else
      {
        if (m != 0) {
          break;
        }
        throw new IllegalStateException("bad padding in encoding");
      }
    }
    byte[] arrayOfByte3 = new byte[m - arrayOfByte1.length];
    System.arraycopy(paramArrayOfByte, arrayOfByte1.length, arrayOfByte3, 0, arrayOfByte3.length);
    return arrayOfByte3;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.crmf.FixedLengthMGF1Padder
 * JD-Core Version:    0.7.0.1
 */