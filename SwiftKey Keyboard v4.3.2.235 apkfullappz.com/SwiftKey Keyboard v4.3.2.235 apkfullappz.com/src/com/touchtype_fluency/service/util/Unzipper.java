package com.touchtype_fluency.service.util;

import com.touchtype_fluency.service.ProgressListener;
import java.io.File;

public class Unzipper
{
  private static final int DEFAULT_BUFFER_SIZE = 4096;
  private static final String TAG = Unzipper.class.getSimpleName();
  private static final long UPDATE_DELAY_MS = 50L;
  private long lastUpdateTime = 0L;
  private File mZipFile;
  
  public Unzipper(File paramFile)
  {
    this.mZipFile = paramFile;
  }
  
  private void updateListener(int paramInt1, int paramInt2, ProgressListener paramProgressListener)
  {
    if (System.currentTimeMillis() - this.lastUpdateTime > 50L)
    {
      paramProgressListener.onProgress(paramInt1, paramInt2);
      this.lastUpdateTime = System.currentTimeMillis();
    }
  }
  
  /* Error */
  public java.util.List<String> unZip(File paramFile, ProgressListener paramProgressListener)
    throws java.io.IOException
  {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual 58	java/io/File:exists	()Z
    //   4: ifne +34 -> 38
    //   7: aload_1
    //   8: invokevirtual 61	java/io/File:mkdirs	()Z
    //   11: ifne +27 -> 38
    //   14: new 52	java/io/IOException
    //   17: dup
    //   18: new 63	java/lang/StringBuilder
    //   21: dup
    //   22: ldc 65
    //   24: invokespecial 68	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   27: aload_1
    //   28: invokevirtual 72	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   31: invokevirtual 75	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   34: invokespecial 76	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   37: athrow
    //   38: new 78	java/util/ArrayList
    //   41: dup
    //   42: invokespecial 79	java/util/ArrayList:<init>	()V
    //   45: astore_3
    //   46: aload_0
    //   47: getfield 34	com/touchtype_fluency/service/util/Unzipper:mZipFile	Ljava/io/File;
    //   50: invokevirtual 82	java/io/File:length	()J
    //   53: l2i
    //   54: istore 7
    //   56: new 84	java/io/FileInputStream
    //   59: dup
    //   60: aload_0
    //   61: getfield 34	com/touchtype_fluency/service/util/Unzipper:mZipFile	Ljava/io/File;
    //   64: invokespecial 86	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   67: astore 8
    //   69: new 88	org/apache/commons/io/input/CountingInputStream
    //   72: dup
    //   73: aload 8
    //   75: invokespecial 91	org/apache/commons/io/input/CountingInputStream:<init>	(Ljava/io/InputStream;)V
    //   78: astore 9
    //   80: new 93	java/util/zip/ZipInputStream
    //   83: dup
    //   84: aload 9
    //   86: invokespecial 94	java/util/zip/ZipInputStream:<init>	(Ljava/io/InputStream;)V
    //   89: astore 10
    //   91: aload 10
    //   93: invokevirtual 98	java/util/zip/ZipInputStream:getNextEntry	()Ljava/util/zip/ZipEntry;
    //   96: astore 11
    //   98: aload 11
    //   100: ifnull +199 -> 299
    //   103: aload 11
    //   105: invokevirtual 103	java/util/zip/ZipEntry:getName	()Ljava/lang/String;
    //   108: astore 12
    //   110: new 54	java/io/File
    //   113: dup
    //   114: aload_1
    //   115: aload 12
    //   117: invokespecial 106	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   120: astore 13
    //   122: aload 11
    //   124: invokevirtual 109	java/util/zip/ZipEntry:isDirectory	()Z
    //   127: ifeq +35 -> 162
    //   130: aload 13
    //   132: invokevirtual 112	java/io/File:mkdir	()Z
    //   135: pop
    //   136: goto -45 -> 91
    //   139: astore 4
    //   141: aload 9
    //   143: astore 6
    //   145: aload 8
    //   147: astore 5
    //   149: aload 6
    //   151: invokestatic 118	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
    //   154: aload 5
    //   156: invokestatic 118	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
    //   159: aload 4
    //   161: athrow
    //   162: aload 13
    //   164: invokevirtual 122	java/io/File:getParentFile	()Ljava/io/File;
    //   167: astore 14
    //   169: aload 14
    //   171: ifnull +9 -> 180
    //   174: aload 14
    //   176: invokevirtual 112	java/io/File:mkdir	()Z
    //   179: pop
    //   180: aload_3
    //   181: aload 12
    //   183: invokeinterface 128 2 0
    //   188: pop
    //   189: new 130	java/io/FileOutputStream
    //   192: dup
    //   193: aload 13
    //   195: invokespecial 131	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   198: astore 17
    //   200: new 133	java/io/BufferedOutputStream
    //   203: dup
    //   204: aload 17
    //   206: invokespecial 136	java/io/BufferedOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   209: astore 18
    //   211: sipush 4096
    //   214: newarray byte
    //   216: astore 21
    //   218: aload 10
    //   220: aload 21
    //   222: invokevirtual 140	java/util/zip/ZipInputStream:read	([B)I
    //   225: istore 22
    //   227: iload 22
    //   229: iconst_m1
    //   230: if_icmpeq +51 -> 281
    //   233: iload 22
    //   235: ifle -17 -> 218
    //   238: aload 18
    //   240: aload 21
    //   242: iconst_0
    //   243: iload 22
    //   245: invokevirtual 144	java/io/BufferedOutputStream:write	([BII)V
    //   248: aload_2
    //   249: ifnull -31 -> 218
    //   252: aload_0
    //   253: aload 9
    //   255: invokevirtual 148	org/apache/commons/io/input/CountingInputStream:getCount	()I
    //   258: iload 7
    //   260: aload_2
    //   261: invokespecial 150	com/touchtype_fluency/service/util/Unzipper:updateListener	(IILcom/touchtype_fluency/service/ProgressListener;)V
    //   264: goto -46 -> 218
    //   267: astore 19
    //   269: aload 18
    //   271: astore 20
    //   273: aload 20
    //   275: invokestatic 118	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
    //   278: aload 19
    //   280: athrow
    //   281: aload 18
    //   283: invokevirtual 153	java/io/BufferedOutputStream:flush	()V
    //   286: aload 18
    //   288: invokestatic 118	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
    //   291: aload 10
    //   293: invokevirtual 156	java/util/zip/ZipInputStream:closeEntry	()V
    //   296: goto -205 -> 91
    //   299: aload 10
    //   301: invokestatic 118	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
    //   304: aload 9
    //   306: invokestatic 118	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
    //   309: aload 8
    //   311: invokestatic 118	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
    //   314: aload_3
    //   315: areturn
    //   316: astore 4
    //   318: aconst_null
    //   319: astore 5
    //   321: aconst_null
    //   322: astore 6
    //   324: goto -175 -> 149
    //   327: astore 4
    //   329: aload 8
    //   331: astore 5
    //   333: aconst_null
    //   334: astore 6
    //   336: goto -187 -> 149
    //   339: astore 4
    //   341: aload 9
    //   343: astore 6
    //   345: aload 8
    //   347: astore 5
    //   349: goto -200 -> 149
    //   352: astore 19
    //   354: aconst_null
    //   355: astore 20
    //   357: goto -84 -> 273
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	360	0	this	Unzipper
    //   0	360	1	paramFile	File
    //   0	360	2	paramProgressListener	ProgressListener
    //   45	270	3	localArrayList	java.util.ArrayList
    //   139	21	4	localObject1	Object
    //   316	1	4	localObject2	Object
    //   327	1	4	localObject3	Object
    //   339	1	4	localObject4	Object
    //   147	201	5	localFileInputStream1	java.io.FileInputStream
    //   143	201	6	localCountingInputStream1	org.apache.commons.io.input.CountingInputStream
    //   54	205	7	i	int
    //   67	279	8	localFileInputStream2	java.io.FileInputStream
    //   78	264	9	localCountingInputStream2	org.apache.commons.io.input.CountingInputStream
    //   89	211	10	localZipInputStream	java.util.zip.ZipInputStream
    //   96	27	11	localZipEntry	java.util.zip.ZipEntry
    //   108	74	12	str	String
    //   120	74	13	localFile1	File
    //   167	8	14	localFile2	File
    //   198	7	17	localFileOutputStream	java.io.FileOutputStream
    //   209	78	18	localBufferedOutputStream1	java.io.BufferedOutputStream
    //   267	12	19	localObject5	Object
    //   352	1	19	localObject6	Object
    //   271	85	20	localBufferedOutputStream2	java.io.BufferedOutputStream
    //   216	25	21	arrayOfByte	byte[]
    //   225	19	22	j	int
    // Exception table:
    //   from	to	target	type
    //   91	98	139	finally
    //   103	136	139	finally
    //   162	169	139	finally
    //   174	180	139	finally
    //   180	189	139	finally
    //   273	281	139	finally
    //   286	296	139	finally
    //   299	304	139	finally
    //   211	218	267	finally
    //   218	227	267	finally
    //   238	248	267	finally
    //   252	264	267	finally
    //   281	286	267	finally
    //   46	69	316	finally
    //   69	80	327	finally
    //   80	91	339	finally
    //   189	211	352	finally
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.util.Unzipper
 * JD-Core Version:    0.7.0.1
 */