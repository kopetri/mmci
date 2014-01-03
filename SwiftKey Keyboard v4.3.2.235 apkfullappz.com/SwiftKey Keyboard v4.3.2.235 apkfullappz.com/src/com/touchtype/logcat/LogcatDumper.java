package com.touchtype.logcat;

import android.content.Context;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.touchtype.storage.AndroidStorageUtils;
import com.touchtype.util.EnvironmentInfoUtil;
import com.touchtype.util.InstallationId;
import com.touchtype.util.LogUtil;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import org.apache.commons.io.filefilter.WildcardFileFilter;

public class LogcatDumper
{
  private static final String TAG = LogcatDumper.class.getSimpleName();
  
  public static void createNewLogFile(Context paramContext, File paramFile)
  {
    try
    {
      paramFile.createNewFile();
      Files.append(EnvironmentInfoUtil.getApplicationInfo(paramContext) + "\n", paramFile, Charsets.UTF_8);
      return;
    }
    catch (IOException localIOException)
    {
      LogUtil.e(TAG, localIOException.getMessage());
    }
  }
  
  /* Error */
  public static void createOrAppendLogsIfRequired(Context paramContext)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 75	android/content/Context:getResources	()Landroid/content/res/Resources;
    //   4: ldc 76
    //   6: invokevirtual 82	android/content/res/Resources:getBoolean	(I)Z
    //   9: ifeq +94 -> 103
    //   12: aload_0
    //   13: invokestatic 86	com/touchtype/logcat/LogcatDumper:getLatestLogFile	(Landroid/content/Context;)Ljava/io/File;
    //   16: astore_1
    //   17: aload_1
    //   18: ifnull +15 -> 33
    //   21: aload_1
    //   22: invokevirtual 89	java/io/File:exists	()Z
    //   25: ifne +8 -> 33
    //   28: aload_0
    //   29: aload_1
    //   30: invokestatic 91	com/touchtype/logcat/LogcatDumper:createNewLogFile	(Landroid/content/Context;Ljava/io/File;)V
    //   33: invokestatic 97	java/lang/Runtime:getRuntime	()Ljava/lang/Runtime;
    //   36: ldc 99
    //   38: invokevirtual 103	java/lang/Runtime:exec	(Ljava/lang/String;)Ljava/lang/Process;
    //   41: astore_3
    //   42: new 105	java/io/InputStreamReader
    //   45: dup
    //   46: aload_3
    //   47: invokevirtual 111	java/lang/Process:getInputStream	()Ljava/io/InputStream;
    //   50: getstatic 53	com/google/common/base/Charsets:UTF_8	Ljava/nio/charset/Charset;
    //   53: invokespecial 114	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;Ljava/nio/charset/Charset;)V
    //   56: invokestatic 119	com/google/common/io/CharStreams:toString	(Ljava/lang/Readable;)Ljava/lang/String;
    //   59: astore 5
    //   61: new 121	java/io/BufferedWriter
    //   64: dup
    //   65: new 123	java/io/FileWriter
    //   68: dup
    //   69: aload_1
    //   70: invokevirtual 127	java/io/File:getAbsoluteFile	()Ljava/io/File;
    //   73: iconst_1
    //   74: invokespecial 130	java/io/FileWriter:<init>	(Ljava/io/File;Z)V
    //   77: invokespecial 133	java/io/BufferedWriter:<init>	(Ljava/io/Writer;)V
    //   80: astore 6
    //   82: aload 6
    //   84: aload 5
    //   86: invokevirtual 137	java/io/BufferedWriter:write	(Ljava/lang/String;)V
    //   89: invokestatic 97	java/lang/Runtime:getRuntime	()Ljava/lang/Runtime;
    //   92: ldc 139
    //   94: invokevirtual 103	java/lang/Runtime:exec	(Ljava/lang/String;)Ljava/lang/Process;
    //   97: pop
    //   98: aload 6
    //   100: invokestatic 145	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
    //   103: return
    //   104: astore 9
    //   106: getstatic 16	com/touchtype/logcat/LogcatDumper:TAG	Ljava/lang/String;
    //   109: aload 9
    //   111: invokevirtual 61	java/io/IOException:getMessage	()Ljava/lang/String;
    //   114: invokestatic 67	com/touchtype/util/LogUtil:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   117: goto -19 -> 98
    //   120: astore 8
    //   122: getstatic 16	com/touchtype/logcat/LogcatDumper:TAG	Ljava/lang/String;
    //   125: aload 8
    //   127: invokevirtual 61	java/io/IOException:getMessage	()Ljava/lang/String;
    //   130: invokestatic 67	com/touchtype/util/LogUtil:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   133: aload 6
    //   135: invokestatic 145	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
    //   138: return
    //   139: astore 4
    //   141: getstatic 16	com/touchtype/logcat/LogcatDumper:TAG	Ljava/lang/String;
    //   144: aload 4
    //   146: invokevirtual 61	java/io/IOException:getMessage	()Ljava/lang/String;
    //   149: invokestatic 67	com/touchtype/util/LogUtil:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   152: return
    //   153: astore_2
    //   154: getstatic 16	com/touchtype/logcat/LogcatDumper:TAG	Ljava/lang/String;
    //   157: aload_2
    //   158: invokevirtual 61	java/io/IOException:getMessage	()Ljava/lang/String;
    //   161: invokestatic 67	com/touchtype/util/LogUtil:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   164: return
    //   165: astore 7
    //   167: aload 6
    //   169: invokestatic 145	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
    //   172: aload 7
    //   174: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	175	0	paramContext	Context
    //   16	54	1	localFile	File
    //   153	5	2	localIOException1	IOException
    //   41	6	3	localProcess	java.lang.Process
    //   139	6	4	localIOException2	IOException
    //   59	26	5	str	String
    //   80	88	6	localBufferedWriter	java.io.BufferedWriter
    //   165	8	7	localObject	Object
    //   120	6	8	localIOException3	IOException
    //   104	6	9	localIOException4	IOException
    // Exception table:
    //   from	to	target	type
    //   89	98	104	java/io/IOException
    //   82	89	120	java/io/IOException
    //   106	117	120	java/io/IOException
    //   42	82	139	java/io/IOException
    //   98	103	139	java/io/IOException
    //   133	138	139	java/io/IOException
    //   167	175	139	java/io/IOException
    //   33	42	153	java/io/IOException
    //   141	152	153	java/io/IOException
    //   82	89	165	finally
    //   89	98	165	finally
    //   106	117	165	finally
    //   122	133	165	finally
  }
  
  public static File getLatestLogFile(Context paramContext)
  {
    String str1 = AndroidStorageUtils.getExternalDirectory(paramContext) + paramContext.getString(2131296844);
    File localFile = new File(str1);
    if (!localFile.exists()) {
      localFile.mkdir();
    }
    if (localFile.listFiles().length == 0)
    {
      StringBuilder localStringBuilder = new StringBuilder().append(InstallationId.getId(paramContext)).append("-");
      String str2 = paramContext.getString(2131296845);
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(0);
      return new File(str1, String.format(null, str2, arrayOfObject));
    }
    File[] arrayOfFile = localFile.listFiles(new WildcardFileFilter("*-logcat*.log"));
    if ((arrayOfFile != null) && (arrayOfFile.length > 0))
    {
      Arrays.sort(arrayOfFile);
      return arrayOfFile[0];
    }
    return null;
  }
  
  public static File getNewLogFile(Context paramContext)
  {
    String str1 = AndroidStorageUtils.getExternalDirectory(paramContext) + paramContext.getString(2131296844);
    File localFile = new File(str1);
    boolean bool = localFile.isDirectory();
    int i = 0;
    if (bool)
    {
      File[] arrayOfFile = localFile.listFiles(new WildcardFileFilter("*-logcat*.log"));
      i = 0;
      if (arrayOfFile != null)
      {
        int j = arrayOfFile.length;
        i = 0;
        if (j > 0)
        {
          Arrays.sort(arrayOfFile);
          String str3 = arrayOfFile[0].getName();
          i = 1 + Integer.valueOf(str3.subSequence(7 + str3.lastIndexOf("-logcat"), str3.lastIndexOf(".log")).toString()).intValue();
        }
      }
    }
    StringBuilder localStringBuilder = new StringBuilder().append(InstallationId.getId(paramContext)).append("-");
    String str2 = paramContext.getString(2131296845);
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Integer.valueOf(i);
    return new File(str1, String.format(null, str2, arrayOfObject));
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.logcat.LogcatDumper
 * JD-Core Version:    0.7.0.1
 */