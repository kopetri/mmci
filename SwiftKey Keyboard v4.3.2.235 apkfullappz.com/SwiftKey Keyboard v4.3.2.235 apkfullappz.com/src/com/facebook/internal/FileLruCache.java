package com.facebook.internal;

import android.content.Context;
import com.facebook.LoggingBehavior;
import com.facebook.Settings;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicLong;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public final class FileLruCache
{
  private static final String HEADER_CACHEKEY_KEY = "key";
  private static final String HEADER_CACHE_CONTENT_TAG_KEY = "tag";
  static final String TAG = FileLruCache.class.getSimpleName();
  private static final AtomicLong bufferIndex = new AtomicLong();
  private final File directory;
  private boolean isTrimPending;
  private final Limits limits;
  private final Object lock;
  private final String tag;
  
  public FileLruCache(Context paramContext, String paramString, Limits paramLimits)
  {
    this.tag = paramString;
    this.limits = paramLimits;
    this.directory = new File(paramContext.getCacheDir(), paramString);
    this.lock = new Object();
    this.directory.mkdirs();
    BufferFile.deleteAll(this.directory);
  }
  
  private void postTrim()
  {
    synchronized (this.lock)
    {
      if (!this.isTrimPending)
      {
        this.isTrimPending = true;
        Settings.getExecutor().execute(new Runnable()
        {
          public void run()
          {
            FileLruCache.this.trim();
          }
        });
      }
      return;
    }
  }
  
  private void renameToTargetAndTrim(String paramString, File paramFile)
  {
    if (!paramFile.renameTo(new File(this.directory, Utility.md5hash(paramString)))) {
      paramFile.delete();
    }
    postTrim();
  }
  
  /* Error */
  private void trim()
  {
    // Byte code:
    //   0: getstatic 123	com/facebook/LoggingBehavior:CACHE	Lcom/facebook/LoggingBehavior;
    //   3: getstatic 32	com/facebook/internal/FileLruCache:TAG	Ljava/lang/String;
    //   6: ldc 125
    //   8: invokestatic 131	com/facebook/internal/Logger:log	(Lcom/facebook/LoggingBehavior;Ljava/lang/String;Ljava/lang/String;)V
    //   11: new 133	java/util/PriorityQueue
    //   14: dup
    //   15: invokespecial 134	java/util/PriorityQueue:<init>	()V
    //   18: astore 4
    //   20: lconst_0
    //   21: lstore 5
    //   23: lconst_0
    //   24: lstore 7
    //   26: aload_0
    //   27: getfield 58	com/facebook/internal/FileLruCache:directory	Ljava/io/File;
    //   30: invokestatic 138	com/facebook/internal/FileLruCache$BufferFile:excludeBufferFiles	()Ljava/io/FilenameFilter;
    //   33: invokevirtual 142	java/io/File:listFiles	(Ljava/io/FilenameFilter;)[Ljava/io/File;
    //   36: astore 9
    //   38: aload 9
    //   40: arraylength
    //   41: istore 10
    //   43: iconst_0
    //   44: istore 11
    //   46: iload 11
    //   48: iload 10
    //   50: if_icmpge +99 -> 149
    //   53: aload 9
    //   55: iload 11
    //   57: aaload
    //   58: astore 12
    //   60: new 144	com/facebook/internal/FileLruCache$ModifiedFile
    //   63: dup
    //   64: aload 12
    //   66: invokespecial 146	com/facebook/internal/FileLruCache$ModifiedFile:<init>	(Ljava/io/File;)V
    //   69: astore 13
    //   71: aload 4
    //   73: aload 13
    //   75: invokevirtual 150	java/util/PriorityQueue:add	(Ljava/lang/Object;)Z
    //   78: pop
    //   79: getstatic 123	com/facebook/LoggingBehavior:CACHE	Lcom/facebook/LoggingBehavior;
    //   82: getstatic 32	com/facebook/internal/FileLruCache:TAG	Ljava/lang/String;
    //   85: new 152	java/lang/StringBuilder
    //   88: dup
    //   89: ldc 154
    //   91: invokespecial 157	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   94: aload 13
    //   96: invokevirtual 161	com/facebook/internal/FileLruCache$ModifiedFile:getModified	()J
    //   99: invokestatic 167	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   102: invokevirtual 171	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   105: ldc 173
    //   107: invokevirtual 176	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   110: aload 13
    //   112: invokevirtual 179	com/facebook/internal/FileLruCache$ModifiedFile:getFile	()Ljava/io/File;
    //   115: invokevirtual 182	java/io/File:getName	()Ljava/lang/String;
    //   118: invokevirtual 176	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   121: invokevirtual 185	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   124: invokestatic 131	com/facebook/internal/Logger:log	(Lcom/facebook/LoggingBehavior;Ljava/lang/String;Ljava/lang/String;)V
    //   127: lload 5
    //   129: aload 12
    //   131: invokevirtual 188	java/io/File:length	()J
    //   134: ladd
    //   135: lstore 5
    //   137: lload 7
    //   139: lconst_1
    //   140: ladd
    //   141: lstore 7
    //   143: iinc 11 1
    //   146: goto -100 -> 46
    //   149: lload 5
    //   151: aload_0
    //   152: getfield 45	com/facebook/internal/FileLruCache:limits	Lcom/facebook/internal/FileLruCache$Limits;
    //   155: invokevirtual 194	com/facebook/internal/FileLruCache$Limits:getByteCount	()I
    //   158: i2l
    //   159: lcmp
    //   160: ifgt +17 -> 177
    //   163: lload 7
    //   165: aload_0
    //   166: getfield 45	com/facebook/internal/FileLruCache:limits	Lcom/facebook/internal/FileLruCache$Limits;
    //   169: invokevirtual 197	com/facebook/internal/FileLruCache$Limits:getFileCount	()I
    //   172: i2l
    //   173: lcmp
    //   174: ifle +94 -> 268
    //   177: aload 4
    //   179: invokevirtual 201	java/util/PriorityQueue:remove	()Ljava/lang/Object;
    //   182: checkcast 144	com/facebook/internal/FileLruCache$ModifiedFile
    //   185: invokevirtual 179	com/facebook/internal/FileLruCache$ModifiedFile:getFile	()Ljava/io/File;
    //   188: astore 15
    //   190: getstatic 123	com/facebook/LoggingBehavior:CACHE	Lcom/facebook/LoggingBehavior;
    //   193: getstatic 32	com/facebook/internal/FileLruCache:TAG	Ljava/lang/String;
    //   196: new 152	java/lang/StringBuilder
    //   199: dup
    //   200: ldc 203
    //   202: invokespecial 157	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   205: aload 15
    //   207: invokevirtual 182	java/io/File:getName	()Ljava/lang/String;
    //   210: invokevirtual 176	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   213: invokevirtual 185	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   216: invokestatic 131	com/facebook/internal/Logger:log	(Lcom/facebook/LoggingBehavior;Ljava/lang/String;Ljava/lang/String;)V
    //   219: lload 5
    //   221: aload 15
    //   223: invokevirtual 188	java/io/File:length	()J
    //   226: lsub
    //   227: lstore 5
    //   229: lload 7
    //   231: lconst_1
    //   232: lsub
    //   233: lstore 7
    //   235: aload 15
    //   237: invokevirtual 115	java/io/File:delete	()Z
    //   240: pop
    //   241: goto -92 -> 149
    //   244: astore_1
    //   245: aload_0
    //   246: getfield 60	com/facebook/internal/FileLruCache:lock	Ljava/lang/Object;
    //   249: astore_2
    //   250: aload_2
    //   251: monitorenter
    //   252: aload_0
    //   253: iconst_0
    //   254: putfield 86	com/facebook/internal/FileLruCache:isTrimPending	Z
    //   257: aload_0
    //   258: getfield 60	com/facebook/internal/FileLruCache:lock	Ljava/lang/Object;
    //   261: invokevirtual 206	java/lang/Object:notifyAll	()V
    //   264: aload_2
    //   265: monitorexit
    //   266: aload_1
    //   267: athrow
    //   268: aload_0
    //   269: getfield 60	com/facebook/internal/FileLruCache:lock	Ljava/lang/Object;
    //   272: astore 17
    //   274: aload 17
    //   276: monitorenter
    //   277: aload_0
    //   278: iconst_0
    //   279: putfield 86	com/facebook/internal/FileLruCache:isTrimPending	Z
    //   282: aload_0
    //   283: getfield 60	com/facebook/internal/FileLruCache:lock	Ljava/lang/Object;
    //   286: invokevirtual 206	java/lang/Object:notifyAll	()V
    //   289: aload 17
    //   291: monitorexit
    //   292: return
    //   293: astore 18
    //   295: aload 17
    //   297: monitorexit
    //   298: aload 18
    //   300: athrow
    //   301: astore_3
    //   302: aload_2
    //   303: monitorexit
    //   304: aload_3
    //   305: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	306	0	this	FileLruCache
    //   244	23	1	localObject1	Object
    //   301	4	3	localObject3	Object
    //   18	160	4	localPriorityQueue	java.util.PriorityQueue
    //   21	207	5	l1	long
    //   24	210	7	l2	long
    //   36	18	9	arrayOfFile	File[]
    //   41	10	10	i	int
    //   44	100	11	j	int
    //   58	72	12	localFile1	File
    //   69	42	13	localModifiedFile	ModifiedFile
    //   188	48	15	localFile2	File
    //   293	6	18	localObject5	Object
    // Exception table:
    //   from	to	target	type
    //   0	20	244	finally
    //   26	43	244	finally
    //   53	137	244	finally
    //   149	177	244	finally
    //   177	229	244	finally
    //   235	241	244	finally
    //   277	292	293	finally
    //   252	266	301	finally
  }
  
  public void clearForTest()
    throws IOException
  {
    File[] arrayOfFile = this.directory.listFiles();
    int i = arrayOfFile.length;
    for (int j = 0; j < i; j++) {
      arrayOfFile[j].delete();
    }
  }
  
  public InputStream get(String paramString)
    throws IOException
  {
    return get(paramString, null);
  }
  
  /* Error */
  public InputStream get(String paramString1, String paramString2)
    throws IOException
  {
    // Byte code:
    //   0: new 47	java/io/File
    //   3: dup
    //   4: aload_0
    //   5: getfield 58	com/facebook/internal/FileLruCache:directory	Ljava/io/File;
    //   8: aload_1
    //   9: invokestatic 108	com/facebook/internal/Utility:md5hash	(Ljava/lang/String;)Ljava/lang/String;
    //   12: invokespecial 56	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   15: astore_3
    //   16: new 219	java/io/FileInputStream
    //   19: dup
    //   20: aload_3
    //   21: invokespecial 220	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   24: astore 4
    //   26: new 222	java/io/BufferedInputStream
    //   29: dup
    //   30: aload 4
    //   32: sipush 8192
    //   35: invokespecial 225	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;I)V
    //   38: astore 5
    //   40: aload 5
    //   42: invokestatic 231	com/facebook/internal/FileLruCache$StreamHeader:readHeader	(Ljava/io/InputStream;)Lorg/json/JSONObject;
    //   45: astore 7
    //   47: aload 7
    //   49: ifnonnull +14 -> 63
    //   52: aload 5
    //   54: invokevirtual 234	java/io/BufferedInputStream:close	()V
    //   57: aconst_null
    //   58: areturn
    //   59: astore 15
    //   61: aconst_null
    //   62: areturn
    //   63: aload 7
    //   65: ldc 8
    //   67: invokevirtual 239	org/json/JSONObject:optString	(Ljava/lang/String;)Ljava/lang/String;
    //   70: astore 8
    //   72: aload 8
    //   74: ifnull +16 -> 90
    //   77: aload 8
    //   79: aload_1
    //   80: invokevirtual 244	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   83: istore 9
    //   85: iload 9
    //   87: ifne +10 -> 97
    //   90: aload 5
    //   92: invokevirtual 234	java/io/BufferedInputStream:close	()V
    //   95: aconst_null
    //   96: areturn
    //   97: aload 7
    //   99: ldc 11
    //   101: aconst_null
    //   102: invokevirtual 247	org/json/JSONObject:optString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   105: astore 10
    //   107: aload_2
    //   108: ifnonnull +8 -> 116
    //   111: aload 10
    //   113: ifnonnull +20 -> 133
    //   116: aload_2
    //   117: ifnull +23 -> 140
    //   120: aload_2
    //   121: aload 10
    //   123: invokevirtual 244	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   126: istore 11
    //   128: iload 11
    //   130: ifne +10 -> 140
    //   133: aload 5
    //   135: invokevirtual 234	java/io/BufferedInputStream:close	()V
    //   138: aconst_null
    //   139: areturn
    //   140: new 249	java/util/Date
    //   143: dup
    //   144: invokespecial 250	java/util/Date:<init>	()V
    //   147: invokevirtual 253	java/util/Date:getTime	()J
    //   150: lstore 12
    //   152: getstatic 123	com/facebook/LoggingBehavior:CACHE	Lcom/facebook/LoggingBehavior;
    //   155: getstatic 32	com/facebook/internal/FileLruCache:TAG	Ljava/lang/String;
    //   158: new 152	java/lang/StringBuilder
    //   161: dup
    //   162: ldc 255
    //   164: invokespecial 157	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   167: lload 12
    //   169: invokestatic 167	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   172: invokevirtual 171	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   175: ldc_w 257
    //   178: invokevirtual 176	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   181: aload_3
    //   182: invokevirtual 182	java/io/File:getName	()Ljava/lang/String;
    //   185: invokevirtual 176	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   188: invokevirtual 185	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   191: invokestatic 131	com/facebook/internal/Logger:log	(Lcom/facebook/LoggingBehavior;Ljava/lang/String;Ljava/lang/String;)V
    //   194: aload_3
    //   195: lload 12
    //   197: invokevirtual 261	java/io/File:setLastModified	(J)Z
    //   200: pop
    //   201: aload 5
    //   203: areturn
    //   204: astore 6
    //   206: iconst_0
    //   207: ifne +8 -> 215
    //   210: aload 5
    //   212: invokevirtual 234	java/io/BufferedInputStream:close	()V
    //   215: aload 6
    //   217: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	218	0	this	FileLruCache
    //   0	218	1	paramString1	String
    //   0	218	2	paramString2	String
    //   15	180	3	localFile	File
    //   24	7	4	localFileInputStream	java.io.FileInputStream
    //   38	173	5	localBufferedInputStream	java.io.BufferedInputStream
    //   204	12	6	localObject	Object
    //   45	53	7	localJSONObject	JSONObject
    //   70	8	8	str1	String
    //   83	3	9	bool1	boolean
    //   105	17	10	str2	String
    //   126	3	11	bool2	boolean
    //   150	46	12	l	long
    //   59	1	15	localIOException	IOException
    // Exception table:
    //   from	to	target	type
    //   16	26	59	java/io/IOException
    //   40	47	204	finally
    //   63	72	204	finally
    //   77	85	204	finally
    //   97	107	204	finally
    //   120	128	204	finally
    //   140	201	204	finally
  }
  
  public InputStream interceptAndPut(String paramString, InputStream paramInputStream)
    throws IOException
  {
    return new CopyingInputStream(paramInputStream, openPutStream(paramString));
  }
  
  OutputStream openPutStream(String paramString)
    throws IOException
  {
    return openPutStream(paramString, null);
  }
  
  /* Error */
  public OutputStream openPutStream(final String paramString1, String paramString2)
    throws IOException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 58	com/facebook/internal/FileLruCache:directory	Ljava/io/File;
    //   4: invokestatic 283	com/facebook/internal/FileLruCache$BufferFile:newFile	(Ljava/io/File;)Ljava/io/File;
    //   7: astore_3
    //   8: aload_3
    //   9: invokevirtual 115	java/io/File:delete	()Z
    //   12: pop
    //   13: aload_3
    //   14: invokevirtual 286	java/io/File:createNewFile	()Z
    //   17: ifne +31 -> 48
    //   20: new 209	java/io/IOException
    //   23: dup
    //   24: new 152	java/lang/StringBuilder
    //   27: dup
    //   28: ldc_w 288
    //   31: invokespecial 157	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   34: aload_3
    //   35: invokevirtual 291	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   38: invokevirtual 176	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   41: invokevirtual 185	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   44: invokespecial 292	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   47: athrow
    //   48: new 294	java/io/FileOutputStream
    //   51: dup
    //   52: aload_3
    //   53: invokespecial 295	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   56: astore 5
    //   58: new 297	java/io/BufferedOutputStream
    //   61: dup
    //   62: new 299	com/facebook/internal/FileLruCache$CloseCallbackOutputStream
    //   65: dup
    //   66: aload 5
    //   68: new 301	com/facebook/internal/FileLruCache$1
    //   71: dup
    //   72: aload_0
    //   73: aload_1
    //   74: aload_3
    //   75: invokespecial 303	com/facebook/internal/FileLruCache$1:<init>	(Lcom/facebook/internal/FileLruCache;Ljava/lang/String;Ljava/io/File;)V
    //   78: invokespecial 306	com/facebook/internal/FileLruCache$CloseCallbackOutputStream:<init>	(Ljava/io/OutputStream;Lcom/facebook/internal/FileLruCache$StreamCloseCallback;)V
    //   81: sipush 8192
    //   84: invokespecial 309	java/io/BufferedOutputStream:<init>	(Ljava/io/OutputStream;I)V
    //   87: astore 6
    //   89: new 236	org/json/JSONObject
    //   92: dup
    //   93: invokespecial 310	org/json/JSONObject:<init>	()V
    //   96: astore 7
    //   98: aload 7
    //   100: ldc 8
    //   102: aload_1
    //   103: invokevirtual 314	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   106: pop
    //   107: aload_2
    //   108: invokestatic 318	com/facebook/internal/Utility:isNullOrEmpty	(Ljava/lang/String;)Z
    //   111: ifne +12 -> 123
    //   114: aload 7
    //   116: ldc 11
    //   118: aload_2
    //   119: invokevirtual 314	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   122: pop
    //   123: aload 6
    //   125: aload 7
    //   127: invokestatic 322	com/facebook/internal/FileLruCache$StreamHeader:writeHeader	(Ljava/io/OutputStream;Lorg/json/JSONObject;)V
    //   130: aload 6
    //   132: areturn
    //   133: astore 12
    //   135: getstatic 123	com/facebook/LoggingBehavior:CACHE	Lcom/facebook/LoggingBehavior;
    //   138: iconst_5
    //   139: getstatic 32	com/facebook/internal/FileLruCache:TAG	Ljava/lang/String;
    //   142: new 152	java/lang/StringBuilder
    //   145: dup
    //   146: ldc_w 324
    //   149: invokespecial 157	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   152: aload 12
    //   154: invokevirtual 171	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   157: invokevirtual 185	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   160: invokestatic 327	com/facebook/internal/Logger:log	(Lcom/facebook/LoggingBehavior;ILjava/lang/String;Ljava/lang/String;)V
    //   163: new 209	java/io/IOException
    //   166: dup
    //   167: aload 12
    //   169: invokevirtual 330	java/io/FileNotFoundException:getMessage	()Ljava/lang/String;
    //   172: invokespecial 292	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   175: athrow
    //   176: astore 9
    //   178: getstatic 123	com/facebook/LoggingBehavior:CACHE	Lcom/facebook/LoggingBehavior;
    //   181: iconst_5
    //   182: getstatic 32	com/facebook/internal/FileLruCache:TAG	Ljava/lang/String;
    //   185: new 152	java/lang/StringBuilder
    //   188: dup
    //   189: ldc_w 332
    //   192: invokespecial 157	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   195: aload 9
    //   197: invokevirtual 171	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   200: invokevirtual 185	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   203: invokestatic 327	com/facebook/internal/Logger:log	(Lcom/facebook/LoggingBehavior;ILjava/lang/String;Ljava/lang/String;)V
    //   206: new 209	java/io/IOException
    //   209: dup
    //   210: aload 9
    //   212: invokevirtual 333	org/json/JSONException:getMessage	()Ljava/lang/String;
    //   215: invokespecial 292	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   218: athrow
    //   219: astore 8
    //   221: iconst_0
    //   222: ifne +8 -> 230
    //   225: aload 6
    //   227: invokevirtual 334	java/io/BufferedOutputStream:close	()V
    //   230: aload 8
    //   232: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	233	0	this	FileLruCache
    //   0	233	1	paramString1	String
    //   0	233	2	paramString2	String
    //   7	68	3	localFile	File
    //   56	11	5	localFileOutputStream	java.io.FileOutputStream
    //   87	139	6	localBufferedOutputStream	java.io.BufferedOutputStream
    //   96	30	7	localJSONObject	JSONObject
    //   219	12	8	localObject	Object
    //   176	35	9	localJSONException	JSONException
    //   133	35	12	localFileNotFoundException	java.io.FileNotFoundException
    // Exception table:
    //   from	to	target	type
    //   48	58	133	java/io/FileNotFoundException
    //   89	123	176	org/json/JSONException
    //   123	130	176	org/json/JSONException
    //   89	123	219	finally
    //   123	130	219	finally
    //   178	219	219	finally
  }
  
  long sizeInBytesForTest()
  {
    long l;
    synchronized (this.lock)
    {
      for (;;)
      {
        boolean bool = this.isTrimPending;
        if (!bool) {
          break;
        }
        try
        {
          this.lock.wait();
        }
        catch (InterruptedException localInterruptedException) {}
      }
      File[] arrayOfFile = this.directory.listFiles();
      l = 0L;
      int i = arrayOfFile.length;
      int j = 0;
      if (j < i)
      {
        l += arrayOfFile[j].length();
        j++;
      }
    }
    return l;
  }
  
  public String toString()
  {
    return "{FileLruCache: tag:" + this.tag + " file:" + this.directory.getName() + "}";
  }
  
  private static class BufferFile
  {
    private static final String FILE_NAME_PREFIX = "buffer";
    private static final FilenameFilter filterExcludeBufferFiles = new FilenameFilter()
    {
      public boolean accept(File paramAnonymousFile, String paramAnonymousString)
      {
        return !paramAnonymousString.startsWith("buffer");
      }
    };
    private static final FilenameFilter filterExcludeNonBufferFiles = new FilenameFilter()
    {
      public boolean accept(File paramAnonymousFile, String paramAnonymousString)
      {
        return paramAnonymousString.startsWith("buffer");
      }
    };
    
    static void deleteAll(File paramFile)
    {
      File[] arrayOfFile = paramFile.listFiles(excludeNonBufferFiles());
      int i = arrayOfFile.length;
      for (int j = 0; j < i; j++) {
        arrayOfFile[j].delete();
      }
    }
    
    static FilenameFilter excludeBufferFiles()
    {
      return filterExcludeBufferFiles;
    }
    
    static FilenameFilter excludeNonBufferFiles()
    {
      return filterExcludeNonBufferFiles;
    }
    
    static File newFile(File paramFile)
    {
      return new File(paramFile, "buffer" + Long.valueOf(FileLruCache.bufferIndex.incrementAndGet()).toString());
    }
  }
  
  private static class CloseCallbackOutputStream
    extends OutputStream
  {
    final FileLruCache.StreamCloseCallback callback;
    final OutputStream innerStream;
    
    CloseCallbackOutputStream(OutputStream paramOutputStream, FileLruCache.StreamCloseCallback paramStreamCloseCallback)
    {
      this.innerStream = paramOutputStream;
      this.callback = paramStreamCloseCallback;
    }
    
    public void close()
      throws IOException
    {
      try
      {
        this.innerStream.close();
        return;
      }
      finally
      {
        this.callback.onClose();
      }
    }
    
    public void flush()
      throws IOException
    {
      this.innerStream.flush();
    }
    
    public void write(int paramInt)
      throws IOException
    {
      this.innerStream.write(paramInt);
    }
    
    public void write(byte[] paramArrayOfByte)
      throws IOException
    {
      this.innerStream.write(paramArrayOfByte);
    }
    
    public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
      throws IOException
    {
      this.innerStream.write(paramArrayOfByte, paramInt1, paramInt2);
    }
  }
  
  private static final class CopyingInputStream
    extends InputStream
  {
    final InputStream input;
    final OutputStream output;
    
    CopyingInputStream(InputStream paramInputStream, OutputStream paramOutputStream)
    {
      this.input = paramInputStream;
      this.output = paramOutputStream;
    }
    
    public int available()
      throws IOException
    {
      return this.input.available();
    }
    
    public void close()
      throws IOException
    {
      try
      {
        this.input.close();
        return;
      }
      finally
      {
        this.output.close();
      }
    }
    
    public void mark(int paramInt)
    {
      throw new UnsupportedOperationException();
    }
    
    public boolean markSupported()
    {
      return false;
    }
    
    public int read()
      throws IOException
    {
      int i = this.input.read();
      if (i >= 0) {
        this.output.write(i);
      }
      return i;
    }
    
    public int read(byte[] paramArrayOfByte)
      throws IOException
    {
      int i = this.input.read(paramArrayOfByte);
      if (i > 0) {
        this.output.write(paramArrayOfByte, 0, i);
      }
      return i;
    }
    
    public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
      throws IOException
    {
      int i = this.input.read(paramArrayOfByte, paramInt1, paramInt2);
      if (i > 0) {
        this.output.write(paramArrayOfByte, paramInt1, i);
      }
      return i;
    }
    
    public void reset()
    {
      try
      {
        throw new UnsupportedOperationException();
      }
      finally {}
    }
    
    public long skip(long paramLong)
      throws IOException
    {
      byte[] arrayOfByte = new byte[1024];
      int i;
      for (long l = 0L;; l += i) {
        if (l < paramLong)
        {
          i = read(arrayOfByte, 0, (int)Math.min(paramLong - l, arrayOfByte.length));
          if (i >= 0) {}
        }
        else
        {
          return l;
        }
      }
    }
  }
  
  public static final class Limits
  {
    private int byteCount = 1048576;
    private int fileCount = 1024;
    
    int getByteCount()
    {
      return this.byteCount;
    }
    
    int getFileCount()
    {
      return this.fileCount;
    }
    
    void setByteCount(int paramInt)
    {
      if (paramInt < 0) {
        throw new InvalidParameterException("Cache byte-count limit must be >= 0");
      }
      this.byteCount = paramInt;
    }
    
    void setFileCount(int paramInt)
    {
      if (paramInt < 0) {
        throw new InvalidParameterException("Cache file count limit must be >= 0");
      }
      this.fileCount = paramInt;
    }
  }
  
  private static final class ModifiedFile
    implements Comparable<ModifiedFile>
  {
    private final File file;
    private final long modified;
    
    ModifiedFile(File paramFile)
    {
      this.file = paramFile;
      this.modified = paramFile.lastModified();
    }
    
    public int compareTo(ModifiedFile paramModifiedFile)
    {
      if (getModified() < paramModifiedFile.getModified()) {
        return -1;
      }
      if (getModified() > paramModifiedFile.getModified()) {
        return 1;
      }
      return getFile().compareTo(paramModifiedFile.getFile());
    }
    
    public boolean equals(Object paramObject)
    {
      return ((paramObject instanceof ModifiedFile)) && (compareTo((ModifiedFile)paramObject) == 0);
    }
    
    File getFile()
    {
      return this.file;
    }
    
    long getModified()
    {
      return this.modified;
    }
  }
  
  private static abstract interface StreamCloseCallback
  {
    public abstract void onClose();
  }
  
  private static final class StreamHeader
  {
    private static final int HEADER_VERSION;
    
    static JSONObject readHeader(InputStream paramInputStream)
      throws IOException
    {
      if (paramInputStream.read() != 0) {
        return null;
      }
      int i = 0;
      for (int j = 0; j < 3; j++)
      {
        int n = paramInputStream.read();
        if (n == -1)
        {
          Logger.log(LoggingBehavior.CACHE, FileLruCache.TAG, "readHeader: stream.read returned -1 while reading header size");
          return null;
        }
        i = (i << 8) + (n & 0xFF);
      }
      byte[] arrayOfByte = new byte[i];
      int k = 0;
      while (k < arrayOfByte.length)
      {
        int m = paramInputStream.read(arrayOfByte, k, arrayOfByte.length - k);
        if (m <= 0)
        {
          Logger.log(LoggingBehavior.CACHE, FileLruCache.TAG, "readHeader: stream.read stopped at " + Integer.valueOf(k) + " when expected " + arrayOfByte.length);
          return null;
        }
        k += m;
      }
      JSONTokener localJSONTokener = new JSONTokener(new String(arrayOfByte));
      Object localObject;
      try
      {
        localObject = localJSONTokener.nextValue();
        if (!(localObject instanceof JSONObject))
        {
          Logger.log(LoggingBehavior.CACHE, FileLruCache.TAG, "readHeader: expected JSONObject, got " + localObject.getClass().getCanonicalName());
          return null;
        }
      }
      catch (JSONException localJSONException)
      {
        throw new IOException(localJSONException.getMessage());
      }
      JSONObject localJSONObject = (JSONObject)localObject;
      return localJSONObject;
    }
    
    static void writeHeader(OutputStream paramOutputStream, JSONObject paramJSONObject)
      throws IOException
    {
      byte[] arrayOfByte = paramJSONObject.toString().getBytes();
      paramOutputStream.write(0);
      paramOutputStream.write(0xFF & arrayOfByte.length >> 16);
      paramOutputStream.write(0xFF & arrayOfByte.length >> 8);
      paramOutputStream.write(0xFF & arrayOfByte.length >> 0);
      paramOutputStream.write(arrayOfByte);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.facebook.internal.FileLruCache
 * JD-Core Version:    0.7.0.1
 */