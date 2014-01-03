package org.spongycastle.crypto.examples;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import org.spongycastle.crypto.CryptoException;
import org.spongycastle.crypto.engines.DESedeEngine;
import org.spongycastle.crypto.modes.CBCBlockCipher;
import org.spongycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.util.encoders.Hex;

public class DESExample
{
  private PaddedBufferedBlockCipher cipher = null;
  private boolean encrypt = true;
  private BufferedInputStream in = null;
  private byte[] key = null;
  private BufferedOutputStream out = null;
  
  public DESExample() {}
  
  /* Error */
  public DESExample(String paramString1, String paramString2, String paramString3, boolean paramBoolean)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokespecial 18	java/lang/Object:<init>	()V
    //   4: aload_0
    //   5: iconst_1
    //   6: putfield 20	org/spongycastle/crypto/examples/DESExample:encrypt	Z
    //   9: aload_0
    //   10: aconst_null
    //   11: putfield 22	org/spongycastle/crypto/examples/DESExample:cipher	Lorg/spongycastle/crypto/paddings/PaddedBufferedBlockCipher;
    //   14: aload_0
    //   15: aconst_null
    //   16: putfield 24	org/spongycastle/crypto/examples/DESExample:in	Ljava/io/BufferedInputStream;
    //   19: aload_0
    //   20: aconst_null
    //   21: putfield 26	org/spongycastle/crypto/examples/DESExample:out	Ljava/io/BufferedOutputStream;
    //   24: aload_0
    //   25: aconst_null
    //   26: putfield 28	org/spongycastle/crypto/examples/DESExample:key	[B
    //   29: aload_0
    //   30: iload 4
    //   32: putfield 20	org/spongycastle/crypto/examples/DESExample:encrypt	Z
    //   35: aload_0
    //   36: new 37	java/io/BufferedInputStream
    //   39: dup
    //   40: new 39	java/io/FileInputStream
    //   43: dup
    //   44: aload_1
    //   45: invokespecial 42	java/io/FileInputStream:<init>	(Ljava/lang/String;)V
    //   48: invokespecial 45	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   51: putfield 24	org/spongycastle/crypto/examples/DESExample:in	Ljava/io/BufferedInputStream;
    //   54: aload_0
    //   55: new 47	java/io/BufferedOutputStream
    //   58: dup
    //   59: new 49	java/io/FileOutputStream
    //   62: dup
    //   63: aload_2
    //   64: invokespecial 50	java/io/FileOutputStream:<init>	(Ljava/lang/String;)V
    //   67: invokespecial 53	java/io/BufferedOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   70: putfield 26	org/spongycastle/crypto/examples/DESExample:out	Ljava/io/BufferedOutputStream;
    //   73: iload 4
    //   75: ifeq +239 -> 314
    //   78: aconst_null
    //   79: astore 12
    //   81: new 55	java/security/SecureRandom
    //   84: dup
    //   85: invokespecial 56	java/security/SecureRandom:<init>	()V
    //   88: astore 13
    //   90: aload 13
    //   92: ldc 58
    //   94: invokevirtual 64	java/lang/String:getBytes	()[B
    //   97: invokevirtual 68	java/security/SecureRandom:setSeed	([B)V
    //   100: aload 13
    //   102: astore 12
    //   104: new 70	org/spongycastle/crypto/KeyGenerationParameters
    //   107: dup
    //   108: aload 12
    //   110: sipush 192
    //   113: invokespecial 73	org/spongycastle/crypto/KeyGenerationParameters:<init>	(Ljava/security/SecureRandom;I)V
    //   116: astore 16
    //   118: new 75	org/spongycastle/crypto/generators/DESedeKeyGenerator
    //   121: dup
    //   122: invokespecial 76	org/spongycastle/crypto/generators/DESedeKeyGenerator:<init>	()V
    //   125: astore 17
    //   127: aload 17
    //   129: aload 16
    //   131: invokevirtual 80	org/spongycastle/crypto/generators/DESedeKeyGenerator:init	(Lorg/spongycastle/crypto/KeyGenerationParameters;)V
    //   134: aload_0
    //   135: aload 17
    //   137: invokevirtual 83	org/spongycastle/crypto/generators/DESedeKeyGenerator:generateKey	()[B
    //   140: putfield 28	org/spongycastle/crypto/examples/DESExample:key	[B
    //   143: new 47	java/io/BufferedOutputStream
    //   146: dup
    //   147: new 49	java/io/FileOutputStream
    //   150: dup
    //   151: aload_3
    //   152: invokespecial 50	java/io/FileOutputStream:<init>	(Ljava/lang/String;)V
    //   155: invokespecial 53	java/io/BufferedOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   158: astore 18
    //   160: aload_0
    //   161: getfield 28	org/spongycastle/crypto/examples/DESExample:key	[B
    //   164: invokestatic 89	org/spongycastle/util/encoders/Hex:encode	([B)[B
    //   167: astore 19
    //   169: aload 18
    //   171: aload 19
    //   173: iconst_0
    //   174: aload 19
    //   176: arraylength
    //   177: invokevirtual 93	java/io/BufferedOutputStream:write	([BII)V
    //   180: aload 18
    //   182: invokevirtual 96	java/io/BufferedOutputStream:flush	()V
    //   185: aload 18
    //   187: invokevirtual 99	java/io/BufferedOutputStream:close	()V
    //   190: return
    //   191: astore 5
    //   193: getstatic 105	java/lang/System:err	Ljava/io/PrintStream;
    //   196: new 107	java/lang/StringBuilder
    //   199: dup
    //   200: ldc 109
    //   202: invokespecial 110	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   205: aload_1
    //   206: invokevirtual 114	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   209: ldc 116
    //   211: invokevirtual 114	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   214: invokevirtual 120	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   217: invokevirtual 125	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   220: iconst_1
    //   221: invokestatic 129	java/lang/System:exit	(I)V
    //   224: goto -170 -> 54
    //   227: astore 6
    //   229: getstatic 105	java/lang/System:err	Ljava/io/PrintStream;
    //   232: new 107	java/lang/StringBuilder
    //   235: dup
    //   236: ldc 131
    //   238: invokespecial 110	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   241: aload_2
    //   242: invokevirtual 114	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   245: ldc 116
    //   247: invokevirtual 114	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   250: invokevirtual 120	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   253: invokevirtual 125	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   256: iconst_1
    //   257: invokestatic 129	java/lang/System:exit	(I)V
    //   260: goto -187 -> 73
    //   263: astore 21
    //   265: getstatic 105	java/lang/System:err	Ljava/io/PrintStream;
    //   268: ldc 133
    //   270: invokevirtual 125	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   273: iconst_1
    //   274: invokestatic 129	java/lang/System:exit	(I)V
    //   277: goto -173 -> 104
    //   280: astore 15
    //   282: getstatic 105	java/lang/System:err	Ljava/io/PrintStream;
    //   285: new 107	java/lang/StringBuilder
    //   288: dup
    //   289: ldc 135
    //   291: invokespecial 110	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   294: aload_3
    //   295: invokevirtual 114	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   298: ldc 116
    //   300: invokevirtual 114	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   303: invokevirtual 120	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   306: invokevirtual 125	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   309: iconst_1
    //   310: invokestatic 129	java/lang/System:exit	(I)V
    //   313: return
    //   314: new 37	java/io/BufferedInputStream
    //   317: dup
    //   318: new 39	java/io/FileInputStream
    //   321: dup
    //   322: aload_3
    //   323: invokespecial 42	java/io/FileInputStream:<init>	(Ljava/lang/String;)V
    //   326: invokespecial 45	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   329: astore 7
    //   331: aload 7
    //   333: invokevirtual 139	java/io/BufferedInputStream:available	()I
    //   336: istore 9
    //   338: iload 9
    //   340: newarray byte
    //   342: astore 10
    //   344: aload 7
    //   346: aload 10
    //   348: iconst_0
    //   349: iload 9
    //   351: invokevirtual 143	java/io/BufferedInputStream:read	([BII)I
    //   354: pop
    //   355: aload_0
    //   356: aload 10
    //   358: invokestatic 146	org/spongycastle/util/encoders/Hex:decode	([B)[B
    //   361: putfield 28	org/spongycastle/crypto/examples/DESExample:key	[B
    //   364: return
    //   365: astore 8
    //   367: getstatic 105	java/lang/System:err	Ljava/io/PrintStream;
    //   370: new 107	java/lang/StringBuilder
    //   373: dup
    //   374: ldc 148
    //   376: invokespecial 110	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   379: aload_3
    //   380: invokevirtual 114	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   383: ldc 116
    //   385: invokevirtual 114	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   388: invokevirtual 120	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   391: invokevirtual 125	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   394: iconst_1
    //   395: invokestatic 129	java/lang/System:exit	(I)V
    //   398: return
    //   399: astore 20
    //   401: goto -119 -> 282
    //   404: astore 14
    //   406: aload 13
    //   408: astore 12
    //   410: goto -145 -> 265
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	413	0	this	DESExample
    //   0	413	1	paramString1	String
    //   0	413	2	paramString2	String
    //   0	413	3	paramString3	String
    //   0	413	4	paramBoolean	boolean
    //   191	1	5	localFileNotFoundException	java.io.FileNotFoundException
    //   227	1	6	localIOException1	IOException
    //   329	16	7	localBufferedInputStream	BufferedInputStream
    //   365	1	8	localIOException2	IOException
    //   336	14	9	i	int
    //   342	15	10	arrayOfByte1	byte[]
    //   79	330	12	localObject	Object
    //   88	319	13	localSecureRandom	java.security.SecureRandom
    //   404	1	14	localException1	java.lang.Exception
    //   280	1	15	localIOException3	IOException
    //   116	14	16	localKeyGenerationParameters	org.spongycastle.crypto.KeyGenerationParameters
    //   125	11	17	localDESedeKeyGenerator	org.spongycastle.crypto.generators.DESedeKeyGenerator
    //   158	28	18	localBufferedOutputStream	BufferedOutputStream
    //   167	8	19	arrayOfByte2	byte[]
    //   399	1	20	localIOException4	IOException
    //   263	1	21	localException2	java.lang.Exception
    // Exception table:
    //   from	to	target	type
    //   35	54	191	java/io/FileNotFoundException
    //   54	73	227	java/io/IOException
    //   81	90	263	java/lang/Exception
    //   81	90	280	java/io/IOException
    //   104	190	280	java/io/IOException
    //   265	277	280	java/io/IOException
    //   314	364	365	java/io/IOException
    //   90	100	399	java/io/IOException
    //   90	100	404	java/lang/Exception
  }
  
  public static void main(String[] paramArrayOfString)
  {
    boolean bool = true;
    if (paramArrayOfString.length < 2)
    {
      DESExample localDESExample = new DESExample();
      System.err.println("Usage: java " + localDESExample.getClass().getName() + " infile outfile [keyfile]");
      System.exit(1);
    }
    String str1 = "deskey.dat";
    String str2 = paramArrayOfString[0];
    String str3 = paramArrayOfString[1];
    if (paramArrayOfString.length > 2)
    {
      bool = false;
      str1 = paramArrayOfString[2];
    }
    new DESExample(str2, str3, str1, bool).process();
  }
  
  private void performDecrypt(byte[] paramArrayOfByte)
  {
    this.cipher.init(false, new KeyParameter(paramArrayOfByte));
    BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(this.in));
    byte[] arrayOfByte1 = null;
    try
    {
      for (;;)
      {
        String str = localBufferedReader.readLine();
        if (str == null) {
          break;
        }
        byte[] arrayOfByte2 = Hex.decode(str);
        arrayOfByte1 = new byte[this.cipher.getOutputSize(arrayOfByte2.length)];
        int i = this.cipher.processBytes(arrayOfByte2, 0, arrayOfByte2.length, arrayOfByte1, 0);
        if (i > 0) {
          this.out.write(arrayOfByte1, 0, i);
        }
      }
      try
      {
        int j;
        do
        {
          j = this.cipher.doFinal(arrayOfByte1, 0);
        } while (j <= 0);
        this.out.write(arrayOfByte1, 0, j);
        return;
      }
      catch (CryptoException localCryptoException) {}
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
      return;
    }
  }
  
  private void performEncrypt(byte[] paramArrayOfByte)
  {
    this.cipher.init(true, new KeyParameter(paramArrayOfByte));
    int i = this.cipher.getOutputSize(47);
    byte[] arrayOfByte1 = new byte[47];
    byte[] arrayOfByte2 = new byte[i];
    try
    {
      for (;;)
      {
        int j = this.in.read(arrayOfByte1, 0, 47);
        if (j <= 0) {
          break;
        }
        int k = this.cipher.processBytes(arrayOfByte1, 0, j, arrayOfByte2, 0);
        if (k > 0)
        {
          byte[] arrayOfByte3 = Hex.encode(arrayOfByte2, 0, k);
          this.out.write(arrayOfByte3, 0, arrayOfByte3.length);
          this.out.write(10);
        }
      }
      try
      {
        int m;
        do
        {
          m = this.cipher.doFinal(arrayOfByte2, 0);
        } while (m <= 0);
        byte[] arrayOfByte4 = Hex.encode(arrayOfByte2, 0, m);
        this.out.write(arrayOfByte4, 0, arrayOfByte4.length);
        this.out.write(10);
        return;
      }
      catch (CryptoException localCryptoException) {}
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
      return;
    }
  }
  
  private void process()
  {
    this.cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new DESedeEngine()));
    if (this.encrypt) {
      performEncrypt(this.key);
    }
    for (;;)
    {
      try
      {
        this.in.close();
        this.out.flush();
        this.out.close();
        return;
      }
      catch (IOException localIOException) {}
      performDecrypt(this.key);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.examples.DESExample
 * JD-Core Version:    0.7.0.1
 */