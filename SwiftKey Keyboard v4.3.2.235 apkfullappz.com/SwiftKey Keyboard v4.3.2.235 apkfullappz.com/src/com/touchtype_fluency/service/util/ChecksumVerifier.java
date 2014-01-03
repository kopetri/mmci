package com.touchtype_fluency.service.util;

import java.io.File;
import java.io.IOException;

public abstract interface ChecksumVerifier
{
  public abstract String getEncodingName();
  
  public abstract boolean verifyChecksum(String paramString, File paramFile)
    throws IOException;
  
  public static final class MD5
    implements ChecksumVerifier
  {
    private static final String ENCODING_NAME = "MD5";
    private static final String TAG = MD5.class.getSimpleName();
    
    public String getEncodingName()
    {
      return "MD5";
    }
    
    /* Error */
    public boolean verifyChecksum(String paramString, File paramFile)
      throws IOException
    {
      // Byte code:
      //   0: aconst_null
      //   1: astore_3
      //   2: aconst_null
      //   3: astore 4
      //   5: ldc 10
      //   7: invokestatic 37	java/security/MessageDigest:getInstance	(Ljava/lang/String;)Ljava/security/MessageDigest;
      //   10: astore 7
      //   12: new 39	java/io/FileInputStream
      //   15: dup
      //   16: aload_2
      //   17: invokespecial 42	java/io/FileInputStream:<init>	(Ljava/io/File;)V
      //   20: astore 8
      //   22: new 44	java/security/DigestInputStream
      //   25: dup
      //   26: aload 8
      //   28: aload 7
      //   30: invokespecial 47	java/security/DigestInputStream:<init>	(Ljava/io/InputStream;Ljava/security/MessageDigest;)V
      //   33: astore 9
      //   35: aload 9
      //   37: invokevirtual 51	java/security/DigestInputStream:read	()I
      //   40: ifgt -5 -> 35
      //   43: new 53	java/lang/String
      //   46: dup
      //   47: aload 7
      //   49: invokevirtual 57	java/security/MessageDigest:digest	()[B
      //   52: ldc 59
      //   54: invokespecial 62	java/lang/String:<init>	([BLjava/lang/String;)V
      //   57: aload_1
      //   58: invokevirtual 66	java/lang/String:equals	(Ljava/lang/Object;)Z
      //   61: istore 10
      //   63: aload 9
      //   65: invokestatic 72	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
      //   68: aload 8
      //   70: invokestatic 72	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
      //   73: iload 10
      //   75: ireturn
      //   76: astore 6
      //   78: getstatic 21	com/touchtype_fluency/service/util/ChecksumVerifier$MD5:TAG	Ljava/lang/String;
      //   81: aload 6
      //   83: invokevirtual 75	java/security/NoSuchAlgorithmException:getMessage	()Ljava/lang/String;
      //   86: aload 6
      //   88: invokestatic 81	com/touchtype/util/LogUtil:w	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
      //   91: aload_3
      //   92: invokestatic 72	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
      //   95: aload 4
      //   97: invokestatic 72	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
      //   100: iconst_0
      //   101: ireturn
      //   102: astore 5
      //   104: aload_3
      //   105: invokestatic 72	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
      //   108: aload 4
      //   110: invokestatic 72	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
      //   113: aload 5
      //   115: athrow
      //   116: astore 5
      //   118: aload 8
      //   120: astore 4
      //   122: aconst_null
      //   123: astore_3
      //   124: goto -20 -> 104
      //   127: astore 5
      //   129: aload 8
      //   131: astore 4
      //   133: aload 9
      //   135: astore_3
      //   136: goto -32 -> 104
      //   139: astore 6
      //   141: aload 8
      //   143: astore 4
      //   145: aconst_null
      //   146: astore_3
      //   147: goto -69 -> 78
      //   150: astore 6
      //   152: aload 8
      //   154: astore 4
      //   156: aload 9
      //   158: astore_3
      //   159: goto -81 -> 78
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	162	0	this	MD5
      //   0	162	1	paramString	String
      //   0	162	2	paramFile	File
      //   1	158	3	localObject1	Object
      //   3	152	4	localObject2	Object
      //   102	12	5	localObject3	Object
      //   116	1	5	localObject4	Object
      //   127	1	5	localObject5	Object
      //   76	11	6	localNoSuchAlgorithmException1	java.security.NoSuchAlgorithmException
      //   139	1	6	localNoSuchAlgorithmException2	java.security.NoSuchAlgorithmException
      //   150	1	6	localNoSuchAlgorithmException3	java.security.NoSuchAlgorithmException
      //   10	38	7	localMessageDigest	java.security.MessageDigest
      //   20	133	8	localFileInputStream	java.io.FileInputStream
      //   33	124	9	localDigestInputStream	java.security.DigestInputStream
      //   61	13	10	bool	boolean
      // Exception table:
      //   from	to	target	type
      //   5	22	76	java/security/NoSuchAlgorithmException
      //   5	22	102	finally
      //   78	91	102	finally
      //   22	35	116	finally
      //   35	63	127	finally
      //   22	35	139	java/security/NoSuchAlgorithmException
      //   35	63	150	java/security/NoSuchAlgorithmException
    }
  }
  
  public static final class SHA1
    implements ChecksumVerifier
  {
    private static final String ENCODING_NAME = "SHA-1";
    private static final String TAG = SHA1.class.getSimpleName();
    
    public String getEncodingName()
    {
      return "SHA-1";
    }
    
    /* Error */
    public boolean verifyChecksum(String paramString, File paramFile)
      throws IOException
    {
      // Byte code:
      //   0: aconst_null
      //   1: astore_3
      //   2: ldc 10
      //   4: invokestatic 37	java/security/MessageDigest:getInstance	(Ljava/lang/String;)Ljava/security/MessageDigest;
      //   7: astore 6
      //   9: new 39	java/io/FileInputStream
      //   12: dup
      //   13: aload_2
      //   14: invokespecial 42	java/io/FileInputStream:<init>	(Ljava/io/File;)V
      //   17: astore 7
      //   19: sipush 1024
      //   22: newarray byte
      //   24: astore 8
      //   26: aload 7
      //   28: aload 8
      //   30: invokevirtual 46	java/io/FileInputStream:read	([B)I
      //   33: istore 9
      //   35: iload 9
      //   37: iconst_m1
      //   38: if_icmpeq +40 -> 78
      //   41: aload 6
      //   43: aload 8
      //   45: iconst_0
      //   46: iload 9
      //   48: invokevirtual 50	java/security/MessageDigest:update	([BII)V
      //   51: goto -25 -> 26
      //   54: astore 4
      //   56: aload 7
      //   58: astore_3
      //   59: getstatic 21	com/touchtype_fluency/service/util/ChecksumVerifier$SHA1:TAG	Ljava/lang/String;
      //   62: aload 4
      //   64: invokevirtual 53	java/security/NoSuchAlgorithmException:getMessage	()Ljava/lang/String;
      //   67: aload 4
      //   69: invokestatic 59	com/touchtype/util/LogUtil:w	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
      //   72: aload_3
      //   73: invokestatic 65	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
      //   76: iconst_0
      //   77: ireturn
      //   78: aload 6
      //   80: invokevirtual 69	java/security/MessageDigest:digest	()[B
      //   83: invokestatic 75	com/touchtype_fluency/service/util/StringUtil:digestToString	([B)Ljava/lang/String;
      //   86: aload_1
      //   87: invokevirtual 80	java/lang/String:toLowerCase	()Ljava/lang/String;
      //   90: invokevirtual 84	java/lang/String:equals	(Ljava/lang/Object;)Z
      //   93: istore 10
      //   95: aload 7
      //   97: invokestatic 65	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
      //   100: iload 10
      //   102: ireturn
      //   103: astore 5
      //   105: aload_3
      //   106: invokestatic 65	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
      //   109: aload 5
      //   111: athrow
      //   112: astore 5
      //   114: aload 7
      //   116: astore_3
      //   117: goto -12 -> 105
      //   120: astore 4
      //   122: aconst_null
      //   123: astore_3
      //   124: goto -65 -> 59
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	127	0	this	SHA1
      //   0	127	1	paramString	String
      //   0	127	2	paramFile	File
      //   1	123	3	localObject1	Object
      //   54	14	4	localNoSuchAlgorithmException1	java.security.NoSuchAlgorithmException
      //   120	1	4	localNoSuchAlgorithmException2	java.security.NoSuchAlgorithmException
      //   103	7	5	localObject2	Object
      //   112	1	5	localObject3	Object
      //   7	72	6	localMessageDigest	java.security.MessageDigest
      //   17	98	7	localFileInputStream	java.io.FileInputStream
      //   24	20	8	arrayOfByte	byte[]
      //   33	14	9	i	int
      //   93	8	10	bool	boolean
      // Exception table:
      //   from	to	target	type
      //   19	26	54	java/security/NoSuchAlgorithmException
      //   26	35	54	java/security/NoSuchAlgorithmException
      //   41	51	54	java/security/NoSuchAlgorithmException
      //   78	95	54	java/security/NoSuchAlgorithmException
      //   2	19	103	finally
      //   59	72	103	finally
      //   19	26	112	finally
      //   26	35	112	finally
      //   41	51	112	finally
      //   78	95	112	finally
      //   2	19	120	java/security/NoSuchAlgorithmException
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.util.ChecksumVerifier
 * JD-Core Version:    0.7.0.1
 */