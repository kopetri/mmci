package org.spongycastle.crypto.macs;

import java.util.Hashtable;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.ExtendedDigest;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.params.KeyParameter;

public class HMac
  implements Mac
{
  private static final byte IPAD = 54;
  private static final byte OPAD = 92;
  private static Hashtable blockLengths;
  private int blockLength;
  private Digest digest;
  private int digestSize;
  private byte[] inputPad;
  private byte[] outputPad;
  
  static
  {
    Hashtable localHashtable = new Hashtable();
    blockLengths = localHashtable;
    localHashtable.put("GOST3411", new Integer(32));
    blockLengths.put("MD2", new Integer(16));
    blockLengths.put("MD4", new Integer(64));
    blockLengths.put("MD5", new Integer(64));
    blockLengths.put("RIPEMD128", new Integer(64));
    blockLengths.put("RIPEMD160", new Integer(64));
    blockLengths.put("SHA-1", new Integer(64));
    blockLengths.put("SHA-224", new Integer(64));
    blockLengths.put("SHA-256", new Integer(64));
    blockLengths.put("SHA-384", new Integer(128));
    blockLengths.put("SHA-512", new Integer(128));
    blockLengths.put("Tiger", new Integer(64));
    blockLengths.put("Whirlpool", new Integer(64));
  }
  
  public HMac(Digest paramDigest)
  {
    this(paramDigest, getByteLength(paramDigest));
  }
  
  private HMac(Digest paramDigest, int paramInt)
  {
    this.digest = paramDigest;
    this.digestSize = paramDigest.getDigestSize();
    this.blockLength = paramInt;
    this.inputPad = new byte[this.blockLength];
    this.outputPad = new byte[this.blockLength];
  }
  
  private static int getByteLength(Digest paramDigest)
  {
    if ((paramDigest instanceof ExtendedDigest)) {
      return ((ExtendedDigest)paramDigest).getByteLength();
    }
    Integer localInteger = (Integer)blockLengths.get(paramDigest.getAlgorithmName());
    if (localInteger == null) {
      throw new IllegalArgumentException("unknown digest passed: " + paramDigest.getAlgorithmName());
    }
    return localInteger.intValue();
  }
  
  public int doFinal(byte[] paramArrayOfByte, int paramInt)
  {
    byte[] arrayOfByte = new byte[this.digestSize];
    this.digest.doFinal(arrayOfByte, 0);
    this.digest.update(this.outputPad, 0, this.outputPad.length);
    this.digest.update(arrayOfByte, 0, arrayOfByte.length);
    int i = this.digest.doFinal(paramArrayOfByte, paramInt);
    reset();
    return i;
  }
  
  public String getAlgorithmName()
  {
    return this.digest.getAlgorithmName() + "/HMAC";
  }
  
  public int getMacSize()
  {
    return this.digestSize;
  }
  
  public Digest getUnderlyingDigest()
  {
    return this.digest;
  }
  
  public void init(CipherParameters paramCipherParameters)
  {
    this.digest.reset();
    byte[] arrayOfByte1 = ((KeyParameter)paramCipherParameters).getKey();
    if (arrayOfByte1.length > this.blockLength)
    {
      this.digest.update(arrayOfByte1, 0, arrayOfByte1.length);
      this.digest.doFinal(this.inputPad, 0);
      for (int m = this.digestSize; m < this.inputPad.length; m++) {
        this.inputPad[m] = 0;
      }
    }
    System.arraycopy(arrayOfByte1, 0, this.inputPad, 0, arrayOfByte1.length);
    for (int i = arrayOfByte1.length; i < this.inputPad.length; i++) {
      this.inputPad[i] = 0;
    }
    this.outputPad = new byte[this.inputPad.length];
    System.arraycopy(this.inputPad, 0, this.outputPad, 0, this.inputPad.length);
    for (int j = 0; j < this.inputPad.length; j++)
    {
      byte[] arrayOfByte3 = this.inputPad;
      arrayOfByte3[j] = ((byte)(0x36 ^ arrayOfByte3[j]));
    }
    for (int k = 0; k < this.outputPad.length; k++)
    {
      byte[] arrayOfByte2 = this.outputPad;
      arrayOfByte2[k] = ((byte)(0x5C ^ arrayOfByte2[k]));
    }
    this.digest.update(this.inputPad, 0, this.inputPad.length);
  }
  
  public void reset()
  {
    this.digest.reset();
    this.digest.update(this.inputPad, 0, this.inputPad.length);
  }
  
  public void update(byte paramByte)
  {
    this.digest.update(paramByte);
  }
  
  public void update(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    this.digest.update(paramArrayOfByte, paramInt1, paramInt2);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.macs.HMac
 * JD-Core Version:    0.7.0.1
 */