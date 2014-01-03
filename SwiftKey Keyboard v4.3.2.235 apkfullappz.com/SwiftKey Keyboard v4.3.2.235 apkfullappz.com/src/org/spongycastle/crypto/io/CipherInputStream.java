package org.spongycastle.crypto.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.spongycastle.crypto.BufferedBlockCipher;
import org.spongycastle.crypto.StreamCipher;

public class CipherInputStream
  extends FilterInputStream
{
  private static final int INPUT_BUF_SIZE = 2048;
  private byte[] buf;
  private int bufOff;
  private BufferedBlockCipher bufferedBlockCipher;
  private boolean finalized;
  private byte[] inBuf;
  private int maxBuf;
  private StreamCipher streamCipher;
  
  public CipherInputStream(InputStream paramInputStream, BufferedBlockCipher paramBufferedBlockCipher)
  {
    super(paramInputStream);
    this.bufferedBlockCipher = paramBufferedBlockCipher;
    this.buf = new byte[paramBufferedBlockCipher.getOutputSize(2048)];
    this.inBuf = new byte[2048];
  }
  
  public CipherInputStream(InputStream paramInputStream, StreamCipher paramStreamCipher)
  {
    super(paramInputStream);
    this.streamCipher = paramStreamCipher;
    this.buf = new byte[2048];
    this.inBuf = new byte[2048];
  }
  
  /* Error */
  private int nextChunk()
    throws IOException
  {
    // Byte code:
    //   0: aload_0
    //   1: invokespecial 47	java/io/FilterInputStream:available	()I
    //   4: istore_1
    //   5: iload_1
    //   6: ifgt +5 -> 11
    //   9: iconst_1
    //   10: istore_1
    //   11: iload_1
    //   12: aload_0
    //   13: getfield 35	org/spongycastle/crypto/io/CipherInputStream:inBuf	[B
    //   16: arraylength
    //   17: if_icmple +31 -> 48
    //   20: aload_0
    //   21: aload_0
    //   22: getfield 35	org/spongycastle/crypto/io/CipherInputStream:inBuf	[B
    //   25: iconst_0
    //   26: aload_0
    //   27: getfield 35	org/spongycastle/crypto/io/CipherInputStream:inBuf	[B
    //   30: arraylength
    //   31: invokespecial 51	java/io/FilterInputStream:read	([BII)I
    //   34: istore_2
    //   35: iload_2
    //   36: ifge +113 -> 149
    //   39: aload_0
    //   40: getfield 53	org/spongycastle/crypto/io/CipherInputStream:finalized	Z
    //   43: ifeq +19 -> 62
    //   46: iconst_m1
    //   47: ireturn
    //   48: aload_0
    //   49: aload_0
    //   50: getfield 35	org/spongycastle/crypto/io/CipherInputStream:inBuf	[B
    //   53: iconst_0
    //   54: iload_1
    //   55: invokespecial 51	java/io/FilterInputStream:read	([BII)I
    //   58: istore_2
    //   59: goto -24 -> 35
    //   62: aload_0
    //   63: getfield 25	org/spongycastle/crypto/io/CipherInputStream:bufferedBlockCipher	Lorg/spongycastle/crypto/BufferedBlockCipher;
    //   66: ifnull +45 -> 111
    //   69: aload_0
    //   70: aload_0
    //   71: getfield 25	org/spongycastle/crypto/io/CipherInputStream:bufferedBlockCipher	Lorg/spongycastle/crypto/BufferedBlockCipher;
    //   74: aload_0
    //   75: getfield 33	org/spongycastle/crypto/io/CipherInputStream:buf	[B
    //   78: iconst_0
    //   79: invokevirtual 57	org/spongycastle/crypto/BufferedBlockCipher:doFinal	([BI)I
    //   82: putfield 59	org/spongycastle/crypto/io/CipherInputStream:maxBuf	I
    //   85: aload_0
    //   86: iconst_0
    //   87: putfield 61	org/spongycastle/crypto/io/CipherInputStream:bufOff	I
    //   90: aload_0
    //   91: iconst_1
    //   92: putfield 53	org/spongycastle/crypto/io/CipherInputStream:finalized	Z
    //   95: aload_0
    //   96: getfield 61	org/spongycastle/crypto/io/CipherInputStream:bufOff	I
    //   99: aload_0
    //   100: getfield 59	org/spongycastle/crypto/io/CipherInputStream:maxBuf	I
    //   103: if_icmpeq -57 -> 46
    //   106: aload_0
    //   107: getfield 59	org/spongycastle/crypto/io/CipherInputStream:maxBuf	I
    //   110: ireturn
    //   111: aload_0
    //   112: iconst_0
    //   113: putfield 59	org/spongycastle/crypto/io/CipherInputStream:maxBuf	I
    //   116: goto -31 -> 85
    //   119: astore 4
    //   121: new 42	java/io/IOException
    //   124: dup
    //   125: new 63	java/lang/StringBuilder
    //   128: dup
    //   129: ldc 65
    //   131: invokespecial 68	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   134: aload 4
    //   136: invokevirtual 72	java/lang/Exception:toString	()Ljava/lang/String;
    //   139: invokevirtual 76	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   142: invokevirtual 77	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   145: invokespecial 78	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   148: athrow
    //   149: aload_0
    //   150: iconst_0
    //   151: putfield 61	org/spongycastle/crypto/io/CipherInputStream:bufOff	I
    //   154: aload_0
    //   155: getfield 25	org/spongycastle/crypto/io/CipherInputStream:bufferedBlockCipher	Lorg/spongycastle/crypto/BufferedBlockCipher;
    //   158: ifnull +37 -> 195
    //   161: aload_0
    //   162: aload_0
    //   163: getfield 25	org/spongycastle/crypto/io/CipherInputStream:bufferedBlockCipher	Lorg/spongycastle/crypto/BufferedBlockCipher;
    //   166: aload_0
    //   167: getfield 35	org/spongycastle/crypto/io/CipherInputStream:inBuf	[B
    //   170: iconst_0
    //   171: iload_2
    //   172: aload_0
    //   173: getfield 33	org/spongycastle/crypto/io/CipherInputStream:buf	[B
    //   176: iconst_0
    //   177: invokevirtual 82	org/spongycastle/crypto/BufferedBlockCipher:processBytes	([BII[BI)I
    //   180: putfield 59	org/spongycastle/crypto/io/CipherInputStream:maxBuf	I
    //   183: aload_0
    //   184: getfield 59	org/spongycastle/crypto/io/CipherInputStream:maxBuf	I
    //   187: ifne -81 -> 106
    //   190: aload_0
    //   191: invokespecial 84	org/spongycastle/crypto/io/CipherInputStream:nextChunk	()I
    //   194: ireturn
    //   195: aload_0
    //   196: getfield 38	org/spongycastle/crypto/io/CipherInputStream:streamCipher	Lorg/spongycastle/crypto/StreamCipher;
    //   199: aload_0
    //   200: getfield 35	org/spongycastle/crypto/io/CipherInputStream:inBuf	[B
    //   203: iconst_0
    //   204: iload_2
    //   205: aload_0
    //   206: getfield 33	org/spongycastle/crypto/io/CipherInputStream:buf	[B
    //   209: iconst_0
    //   210: invokeinterface 89 6 0
    //   215: aload_0
    //   216: iload_2
    //   217: putfield 59	org/spongycastle/crypto/io/CipherInputStream:maxBuf	I
    //   220: goto -37 -> 183
    //   223: astore_3
    //   224: new 42	java/io/IOException
    //   227: dup
    //   228: new 63	java/lang/StringBuilder
    //   231: dup
    //   232: ldc 65
    //   234: invokespecial 68	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   237: aload_3
    //   238: invokevirtual 72	java/lang/Exception:toString	()Ljava/lang/String;
    //   241: invokevirtual 76	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   244: invokevirtual 77	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   247: invokespecial 78	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   250: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	251	0	this	CipherInputStream
    //   4	51	1	i	int
    //   34	183	2	j	int
    //   223	15	3	localException1	java.lang.Exception
    //   119	16	4	localException2	java.lang.Exception
    // Exception table:
    //   from	to	target	type
    //   62	85	119	java/lang/Exception
    //   111	116	119	java/lang/Exception
    //   154	183	223	java/lang/Exception
    //   195	220	223	java/lang/Exception
  }
  
  public int available()
    throws IOException
  {
    return this.maxBuf - this.bufOff;
  }
  
  public void close()
    throws IOException
  {
    super.close();
  }
  
  public boolean markSupported()
  {
    return false;
  }
  
  public int read()
    throws IOException
  {
    if ((this.bufOff == this.maxBuf) && (nextChunk() < 0)) {
      return -1;
    }
    byte[] arrayOfByte = this.buf;
    int i = this.bufOff;
    this.bufOff = (i + 1);
    return 0xFF & arrayOfByte[i];
  }
  
  public int read(byte[] paramArrayOfByte)
    throws IOException
  {
    return read(paramArrayOfByte, 0, paramArrayOfByte.length);
  }
  
  public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    if ((this.bufOff == this.maxBuf) && (nextChunk() < 0)) {
      return -1;
    }
    int i = this.maxBuf - this.bufOff;
    if (paramInt2 > i)
    {
      System.arraycopy(this.buf, this.bufOff, paramArrayOfByte, paramInt1, i);
      this.bufOff = this.maxBuf;
      return i;
    }
    System.arraycopy(this.buf, this.bufOff, paramArrayOfByte, paramInt1, paramInt2);
    this.bufOff = (paramInt2 + this.bufOff);
    return paramInt2;
  }
  
  public long skip(long paramLong)
    throws IOException
  {
    if (paramLong <= 0L) {
      return 0L;
    }
    int i = this.maxBuf - this.bufOff;
    if (paramLong > i)
    {
      this.bufOff = this.maxBuf;
      return i;
    }
    this.bufOff += (int)paramLong;
    return (int)paramLong;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.io.CipherInputStream
 * JD-Core Version:    0.7.0.1
 */