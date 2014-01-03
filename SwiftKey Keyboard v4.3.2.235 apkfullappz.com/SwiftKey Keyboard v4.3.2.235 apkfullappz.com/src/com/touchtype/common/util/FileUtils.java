package com.touchtype.common.util;

import com.google.common.collect.Sets;
import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.Locale;
import java.util.Set;
import org.apache.commons.io.IOExceptionWithCause;

public final class FileUtils
{
  private static final Set<String> PLAIN_TEXT_FILES = Sets.newHashSet(new String[] { ".log" });
  private static final String TAG = FileUtils.class.getSimpleName();
  
  /* Error */
  public static File compressFileToZip(File paramFile, String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 45	java/io/File:getName	()Ljava/lang/String;
    //   4: aload_1
    //   5: ldc 47
    //   7: invokevirtual 51	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
    //   10: astore_2
    //   11: new 53	java/lang/StringBuilder
    //   14: dup
    //   15: invokespecial 54	java/lang/StringBuilder:<init>	()V
    //   18: aload_0
    //   19: invokevirtual 57	java/io/File:getParent	()Ljava/lang/String;
    //   22: invokevirtual 61	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   25: getstatic 64	java/io/File:separator	Ljava/lang/String;
    //   28: invokevirtual 61	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   31: aload_2
    //   32: invokevirtual 61	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   35: invokevirtual 67	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   38: astore_3
    //   39: new 69	java/io/FileInputStream
    //   42: dup
    //   43: aload_0
    //   44: invokevirtual 72	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   47: invokespecial 75	java/io/FileInputStream:<init>	(Ljava/lang/String;)V
    //   50: astore 4
    //   52: new 77	java/util/zip/ZipOutputStream
    //   55: dup
    //   56: new 79	java/io/FileOutputStream
    //   59: dup
    //   60: aload_3
    //   61: invokespecial 80	java/io/FileOutputStream:<init>	(Ljava/lang/String;)V
    //   64: invokespecial 83	java/util/zip/ZipOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   67: astore 5
    //   69: aload 5
    //   71: new 85	java/util/zip/ZipEntry
    //   74: dup
    //   75: aload_0
    //   76: invokevirtual 45	java/io/File:getName	()Ljava/lang/String;
    //   79: invokespecial 86	java/util/zip/ZipEntry:<init>	(Ljava/lang/String;)V
    //   82: invokevirtual 90	java/util/zip/ZipOutputStream:putNextEntry	(Ljava/util/zip/ZipEntry;)V
    //   85: sipush 1024
    //   88: newarray byte
    //   90: astore 12
    //   92: aload 4
    //   94: aload 12
    //   96: invokevirtual 94	java/io/FileInputStream:read	([B)I
    //   99: istore 13
    //   101: iload 13
    //   103: ifle +52 -> 155
    //   106: aload 5
    //   108: aload 12
    //   110: iconst_0
    //   111: iload 13
    //   113: invokevirtual 98	java/util/zip/ZipOutputStream:write	([BII)V
    //   116: goto -24 -> 92
    //   119: astore 11
    //   121: getstatic 19	com/touchtype/common/util/FileUtils:TAG	Ljava/lang/String;
    //   124: aload 11
    //   126: invokevirtual 101	java/io/IOException:getMessage	()Ljava/lang/String;
    //   129: invokestatic 107	com/touchtype/util/LogUtil:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   132: aload 5
    //   134: invokestatic 113	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
    //   137: aload 4
    //   139: invokestatic 113	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
    //   142: new 42	java/io/File
    //   145: dup
    //   146: aload_3
    //   147: invokespecial 114	java/io/File:<init>	(Ljava/lang/String;)V
    //   150: astore 10
    //   152: aload 10
    //   154: areturn
    //   155: aload 5
    //   157: invokestatic 113	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
    //   160: goto -23 -> 137
    //   163: astore 9
    //   165: getstatic 19	com/touchtype/common/util/FileUtils:TAG	Ljava/lang/String;
    //   168: aload 9
    //   170: invokevirtual 115	java/io/FileNotFoundException:getMessage	()Ljava/lang/String;
    //   173: invokestatic 107	com/touchtype/util/LogUtil:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   176: aload 4
    //   178: invokestatic 113	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
    //   181: goto -39 -> 142
    //   184: astore 8
    //   186: getstatic 19	com/touchtype/common/util/FileUtils:TAG	Ljava/lang/String;
    //   189: aload 8
    //   191: invokevirtual 115	java/io/FileNotFoundException:getMessage	()Ljava/lang/String;
    //   194: invokestatic 107	com/touchtype/util/LogUtil:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   197: aconst_null
    //   198: areturn
    //   199: astore 6
    //   201: aload 5
    //   203: invokestatic 113	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
    //   206: aload 6
    //   208: athrow
    //   209: astore 7
    //   211: aload 4
    //   213: invokestatic 113	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
    //   216: aload 7
    //   218: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	219	0	paramFile	File
    //   0	219	1	paramString	String
    //   10	22	2	str1	String
    //   38	109	3	str2	String
    //   50	162	4	localFileInputStream	java.io.FileInputStream
    //   67	135	5	localZipOutputStream	java.util.zip.ZipOutputStream
    //   199	8	6	localObject1	Object
    //   209	8	7	localObject2	Object
    //   184	6	8	localFileNotFoundException1	java.io.FileNotFoundException
    //   163	6	9	localFileNotFoundException2	java.io.FileNotFoundException
    //   150	3	10	localFile	File
    //   119	6	11	localIOException	IOException
    //   90	19	12	arrayOfByte	byte[]
    //   99	13	13	i	int
    // Exception table:
    //   from	to	target	type
    //   69	92	119	java/io/IOException
    //   92	101	119	java/io/IOException
    //   106	116	119	java/io/IOException
    //   52	69	163	java/io/FileNotFoundException
    //   132	137	163	java/io/FileNotFoundException
    //   155	160	163	java/io/FileNotFoundException
    //   201	209	163	java/io/FileNotFoundException
    //   39	52	184	java/io/FileNotFoundException
    //   137	142	184	java/io/FileNotFoundException
    //   142	152	184	java/io/FileNotFoundException
    //   176	181	184	java/io/FileNotFoundException
    //   211	219	184	java/io/FileNotFoundException
    //   69	92	199	finally
    //   92	101	199	finally
    //   106	116	199	finally
    //   121	132	199	finally
    //   52	69	209	finally
    //   132	137	209	finally
    //   155	160	209	finally
    //   165	176	209	finally
    //   201	209	209	finally
  }
  
  public static void deleteRecursively(File paramFile)
    throws IOException
  {
    if (!paramFile.exists()) {}
    do
    {
      return;
      if (!paramFile.isFile()) {
        break;
      }
    } while (paramFile.delete());
    throw new IOException("Couldn't delete: " + paramFile);
    if (System.getProperty("os.name").toLowerCase(Locale.ENGLISH).contains("win")) {}
    for (String str = "rmdir /s /q " + paramFile.getAbsolutePath();; str = "rm -r " + paramFile.getAbsolutePath()) {
      for (;;)
      {
        Runtime localRuntime = Runtime.getRuntime();
        try
        {
          localRuntime.exec(str).waitFor();
          if (!paramFile.exists()) {
            break;
          }
          throw new IOException("Failed to delete: " + paramFile);
        }
        catch (InterruptedException localInterruptedException)
        {
          throw new IOExceptionWithCause(localInterruptedException);
        }
      }
    }
  }
  
  public static String getFileContentType(String paramString)
  {
    if (PLAIN_TEXT_FILES.contains(getFileNameEnding(paramString))) {
      return "plain/text";
    }
    return URLConnection.getFileNameMap().getContentTypeFor(paramString);
  }
  
  private static String getFileNameEnding(String paramString)
  {
    return paramString.substring(paramString.lastIndexOf("."));
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.common.util.FileUtils
 * JD-Core Version:    0.7.0.1
 */