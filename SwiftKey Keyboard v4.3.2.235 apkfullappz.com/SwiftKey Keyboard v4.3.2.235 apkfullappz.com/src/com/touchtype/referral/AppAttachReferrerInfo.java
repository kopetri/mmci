package com.touchtype.referral;

import android.content.Context;

public class AppAttachReferrerInfo
  implements ReferrerInfo
{
  /* Error */
  private String getPromoId()
  {
    // Byte code:
    //   0: new 18	java/io/File
    //   3: dup
    //   4: ldc 20
    //   6: invokespecial 23	java/io/File:<init>	(Ljava/lang/String;)V
    //   9: astore_1
    //   10: aconst_null
    //   11: astore_2
    //   12: new 25	java/io/BufferedReader
    //   15: dup
    //   16: new 27	java/io/FileReader
    //   19: dup
    //   20: aload_1
    //   21: invokespecial 30	java/io/FileReader:<init>	(Ljava/io/File;)V
    //   24: invokespecial 33	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   27: astore_3
    //   28: aload_3
    //   29: invokevirtual 36	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   32: astore 7
    //   34: aconst_null
    //   35: astore 8
    //   37: aload 7
    //   39: ifnull +14 -> 53
    //   42: aload 7
    //   44: invokevirtual 41	java/lang/String:trim	()Ljava/lang/String;
    //   47: astore 9
    //   49: aload 9
    //   51: astore 8
    //   53: aload_3
    //   54: invokestatic 47	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
    //   57: aload 8
    //   59: areturn
    //   60: astore 11
    //   62: aload_2
    //   63: invokestatic 47	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
    //   66: aconst_null
    //   67: areturn
    //   68: astore 10
    //   70: aload_2
    //   71: invokestatic 47	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
    //   74: aconst_null
    //   75: areturn
    //   76: astore 6
    //   78: aload_2
    //   79: invokestatic 47	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
    //   82: aload 6
    //   84: athrow
    //   85: astore 6
    //   87: aload_3
    //   88: astore_2
    //   89: goto -11 -> 78
    //   92: astore 5
    //   94: aload_3
    //   95: astore_2
    //   96: goto -26 -> 70
    //   99: astore 4
    //   101: aload_3
    //   102: astore_2
    //   103: goto -41 -> 62
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	106	0	this	AppAttachReferrerInfo
    //   9	12	1	localFile	java.io.File
    //   11	92	2	localObject1	Object
    //   27	75	3	localBufferedReader	java.io.BufferedReader
    //   99	1	4	localFileNotFoundException1	java.io.FileNotFoundException
    //   92	1	5	localIOException1	java.io.IOException
    //   76	7	6	localObject2	Object
    //   85	1	6	localObject3	Object
    //   32	11	7	str1	String
    //   35	23	8	localObject4	Object
    //   47	3	9	str2	String
    //   68	1	10	localIOException2	java.io.IOException
    //   60	1	11	localFileNotFoundException2	java.io.FileNotFoundException
    // Exception table:
    //   from	to	target	type
    //   12	28	60	java/io/FileNotFoundException
    //   12	28	68	java/io/IOException
    //   12	28	76	finally
    //   28	34	85	finally
    //   42	49	85	finally
    //   28	34	92	java/io/IOException
    //   42	49	92	java/io/IOException
    //   28	34	99	java/io/FileNotFoundException
    //   42	49	99	java/io/FileNotFoundException
  }
  
  public String campaign()
  {
    return "sk";
  }
  
  public String medium()
  {
    return "Upgrade";
  }
  
  public String source(Context paramContext, ReferralSource paramReferralSource)
  {
    String str1 = getPromoId();
    StringBuilder localStringBuilder = new StringBuilder("AppAttach");
    if (str1 == null) {}
    for (String str2 = "";; str2 = "_" + str1) {
      return str2;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.referral.AppAttachReferrerInfo
 * JD-Core Version:    0.7.0.1
 */