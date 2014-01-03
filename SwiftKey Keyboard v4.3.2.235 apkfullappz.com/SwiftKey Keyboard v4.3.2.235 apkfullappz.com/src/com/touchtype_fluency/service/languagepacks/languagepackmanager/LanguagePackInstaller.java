package com.touchtype_fluency.service.languagepacks.languagepackmanager;

import android.content.Context;
import android.content.res.AssetManager;
import com.touchtype.resources.ProductConfiguration;
import com.touchtype.storage.FolderDecorator;
import com.touchtype.util.LogUtil;
import com.touchtype_fluency.service.languagepacks.LanguagePackManager;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

public final class LanguagePackInstaller
{
  private static final String TAG = LanguagePackInstaller.class.getSimpleName();
  
  /* Error */
  private static void copyConfiguration(java.io.InputStream paramInputStream, File paramFile)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: new 25	java/io/FileOutputStream
    //   5: dup
    //   6: aload_1
    //   7: invokespecial 28	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   10: astore_3
    //   11: aload_0
    //   12: aload_3
    //   13: invokestatic 34	com/google/common/io/ByteStreams:copy	(Ljava/io/InputStream;Ljava/io/OutputStream;)J
    //   16: pop2
    //   17: aload_0
    //   18: invokestatic 40	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
    //   21: aload_3
    //   22: invokestatic 40	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
    //   25: return
    //   26: astore 4
    //   28: getstatic 16	com/touchtype_fluency/service/languagepacks/languagepackmanager/LanguagePackInstaller:TAG	Ljava/lang/String;
    //   31: aload 4
    //   33: invokevirtual 43	java/io/IOException:getMessage	()Ljava/lang/String;
    //   36: invokestatic 49	com/touchtype/util/LogUtil:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   39: aload_0
    //   40: invokestatic 40	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
    //   43: aload_2
    //   44: invokestatic 40	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
    //   47: return
    //   48: astore 5
    //   50: aload_0
    //   51: invokestatic 40	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
    //   54: aload_2
    //   55: invokestatic 40	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
    //   58: aload 5
    //   60: athrow
    //   61: astore 5
    //   63: aload_3
    //   64: astore_2
    //   65: goto -15 -> 50
    //   68: astore 4
    //   70: aload_3
    //   71: astore_2
    //   72: goto -44 -> 28
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	75	0	paramInputStream	java.io.InputStream
    //   0	75	1	paramFile	File
    //   1	71	2	localObject1	Object
    //   10	61	3	localFileOutputStream	java.io.FileOutputStream
    //   26	6	4	localIOException1	IOException
    //   68	1	4	localIOException2	IOException
    //   48	11	5	localObject2	Object
    //   61	1	5	localObject3	Object
    // Exception table:
    //   from	to	target	type
    //   2	11	26	java/io/IOException
    //   2	11	48	finally
    //   28	39	48	finally
    //   11	17	61	finally
    //   11	17	68	java/io/IOException
  }
  
  public static void installBundledLanguagePacks(Context paramContext)
    throws IOException
  {
    installConfiguration(paramContext);
    LanguagePackManager localLanguagePackManager = new LanguagePackManager(paramContext);
    localLanguagePackManager.onCreate();
    Iterator localIterator = localLanguagePackManager.getLanguagePacks().iterator();
    while (localIterator.hasNext())
    {
      LanguagePack localLanguagePack = (LanguagePack)localIterator.next();
      String str = localLanguagePack.getID();
      File localFile = new File(AndroidLanguagePackModelStorage.getInstance(paramContext).getStaticModelDirectory().getBaseFolder(), str);
      try
      {
        unzipLanguagePack(paramContext.getAssets().open(str + ".zip"), localFile);
        localLanguagePackManager.enableLanguage(localLanguagePack, true);
      }
      catch (DownloadRequiredException localDownloadRequiredException)
      {
        LogUtil.e(TAG, localDownloadRequiredException.getMessage());
      }
      catch (MaximumLanguagesException localMaximumLanguagesException)
      {
        LogUtil.e(TAG, localMaximumLanguagesException.getMessage());
      }
      catch (IOException localIOException)
      {
        LogUtil.e(TAG, localIOException.getMessage());
      }
    }
    localLanguagePackManager.onDestroy();
  }
  
  private static void installConfiguration(Context paramContext)
    throws IOException
  {
    String str = ProductConfiguration.getBundledLanguagePacks(paramContext);
    File localFile = new File(AndroidLanguagePackModelStorage.getInstance(paramContext).getStaticModelDirectory().getBaseFolder(), "languagePacks.json");
    copyConfiguration(paramContext.getAssets().open(str), localFile);
  }
  
  /* Error */
  private static void unzipLanguagePack(java.io.InputStream paramInputStream, File paramFile)
  {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual 160	java/io/File:mkdirs	()Z
    //   4: pop
    //   5: aconst_null
    //   6: astore_3
    //   7: new 162	java/util/zip/ZipInputStream
    //   10: dup
    //   11: aload_0
    //   12: invokespecial 165	java/util/zip/ZipInputStream:<init>	(Ljava/io/InputStream;)V
    //   15: astore 4
    //   17: aload 4
    //   19: invokevirtual 169	java/util/zip/ZipInputStream:getNextEntry	()Ljava/util/zip/ZipEntry;
    //   22: astore 7
    //   24: aload 7
    //   26: ifnull +210 -> 236
    //   29: aconst_null
    //   30: astore 8
    //   32: aload 7
    //   34: invokevirtual 174	java/util/zip/ZipEntry:isDirectory	()Z
    //   37: istore 11
    //   39: aconst_null
    //   40: astore 8
    //   42: iload 11
    //   44: ifne +153 -> 197
    //   47: new 92	java/io/File
    //   50: dup
    //   51: aload_1
    //   52: aload 7
    //   54: invokevirtual 177	java/util/zip/ZipEntry:getName	()Ljava/lang/String;
    //   57: invokespecial 111	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   60: astore 12
    //   62: aload 12
    //   64: invokevirtual 180	java/io/File:getParentFile	()Ljava/io/File;
    //   67: astore 13
    //   69: aconst_null
    //   70: astore 8
    //   72: aload 13
    //   74: ifnull +24 -> 98
    //   77: aload 13
    //   79: invokevirtual 183	java/io/File:exists	()Z
    //   82: istore 14
    //   84: aconst_null
    //   85: astore 8
    //   87: iload 14
    //   89: ifne +9 -> 98
    //   92: aload 13
    //   94: invokevirtual 160	java/io/File:mkdirs	()Z
    //   97: pop
    //   98: new 25	java/io/FileOutputStream
    //   101: dup
    //   102: aload 12
    //   104: invokespecial 28	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   107: astore 16
    //   109: sipush 1024
    //   112: newarray byte
    //   114: astore 17
    //   116: aload 4
    //   118: aload 17
    //   120: invokevirtual 187	java/util/zip/ZipInputStream:read	([B)I
    //   123: istore 18
    //   125: iload 18
    //   127: ifle +66 -> 193
    //   130: aload 16
    //   132: aload 17
    //   134: iconst_0
    //   135: iload 18
    //   137: invokevirtual 191	java/io/FileOutputStream:write	([BII)V
    //   140: goto -24 -> 116
    //   143: astore 9
    //   145: aload 16
    //   147: astore 8
    //   149: getstatic 16	com/touchtype_fluency/service/languagepacks/languagepackmanager/LanguagePackInstaller:TAG	Ljava/lang/String;
    //   152: aload 9
    //   154: invokevirtual 43	java/io/IOException:getMessage	()Ljava/lang/String;
    //   157: invokestatic 49	com/touchtype/util/LogUtil:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   160: aload 8
    //   162: invokestatic 40	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
    //   165: goto -148 -> 17
    //   168: astore 6
    //   170: aload 4
    //   172: astore_3
    //   173: getstatic 16	com/touchtype_fluency/service/languagepacks/languagepackmanager/LanguagePackInstaller:TAG	Ljava/lang/String;
    //   176: aload 6
    //   178: invokevirtual 43	java/io/IOException:getMessage	()Ljava/lang/String;
    //   181: invokestatic 49	com/touchtype/util/LogUtil:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   184: aload_3
    //   185: invokestatic 40	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
    //   188: aload_0
    //   189: invokestatic 40	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
    //   192: return
    //   193: aload 16
    //   195: astore 8
    //   197: aload 4
    //   199: invokevirtual 194	java/util/zip/ZipInputStream:closeEntry	()V
    //   202: aload 8
    //   204: invokestatic 40	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
    //   207: goto -190 -> 17
    //   210: astore 5
    //   212: aload 4
    //   214: astore_3
    //   215: aload_3
    //   216: invokestatic 40	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
    //   219: aload_0
    //   220: invokestatic 40	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
    //   223: aload 5
    //   225: athrow
    //   226: astore 10
    //   228: aload 8
    //   230: invokestatic 40	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
    //   233: aload 10
    //   235: athrow
    //   236: aload 4
    //   238: invokevirtual 197	java/util/zip/ZipInputStream:close	()V
    //   241: aload 4
    //   243: invokestatic 40	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
    //   246: aload_0
    //   247: invokestatic 40	com/google/common/io/Closeables:closeQuietly	(Ljava/io/Closeable;)V
    //   250: return
    //   251: astore 5
    //   253: goto -38 -> 215
    //   256: astore 6
    //   258: aconst_null
    //   259: astore_3
    //   260: goto -87 -> 173
    //   263: astore 10
    //   265: aload 16
    //   267: astore 8
    //   269: goto -41 -> 228
    //   272: astore 9
    //   274: goto -125 -> 149
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	277	0	paramInputStream	java.io.InputStream
    //   0	277	1	paramFile	File
    //   6	254	3	localObject1	Object
    //   15	227	4	localZipInputStream	java.util.zip.ZipInputStream
    //   210	14	5	localObject2	Object
    //   251	1	5	localObject3	Object
    //   168	9	6	localIOException1	IOException
    //   256	1	6	localIOException2	IOException
    //   22	31	7	localZipEntry	java.util.zip.ZipEntry
    //   30	238	8	localObject4	Object
    //   143	10	9	localIOException3	IOException
    //   272	1	9	localIOException4	IOException
    //   226	8	10	localObject5	Object
    //   263	1	10	localObject6	Object
    //   37	6	11	bool1	boolean
    //   60	43	12	localFile1	File
    //   67	26	13	localFile2	File
    //   82	6	14	bool2	boolean
    //   107	159	16	localFileOutputStream	java.io.FileOutputStream
    //   114	19	17	arrayOfByte	byte[]
    //   123	13	18	i	int
    // Exception table:
    //   from	to	target	type
    //   109	116	143	java/io/IOException
    //   116	125	143	java/io/IOException
    //   130	140	143	java/io/IOException
    //   17	24	168	java/io/IOException
    //   160	165	168	java/io/IOException
    //   202	207	168	java/io/IOException
    //   228	236	168	java/io/IOException
    //   236	241	168	java/io/IOException
    //   17	24	210	finally
    //   160	165	210	finally
    //   202	207	210	finally
    //   228	236	210	finally
    //   236	241	210	finally
    //   32	39	226	finally
    //   47	69	226	finally
    //   77	84	226	finally
    //   92	98	226	finally
    //   98	109	226	finally
    //   149	160	226	finally
    //   197	202	226	finally
    //   7	17	251	finally
    //   173	184	251	finally
    //   7	17	256	java/io/IOException
    //   109	116	263	finally
    //   116	125	263	finally
    //   130	140	263	finally
    //   32	39	272	java/io/IOException
    //   47	69	272	java/io/IOException
    //   77	84	272	java/io/IOException
    //   92	98	272	java/io/IOException
    //   98	109	272	java/io/IOException
    //   197	202	272	java/io/IOException
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.languagepacks.languagepackmanager.LanguagePackInstaller
 * JD-Core Version:    0.7.0.1
 */