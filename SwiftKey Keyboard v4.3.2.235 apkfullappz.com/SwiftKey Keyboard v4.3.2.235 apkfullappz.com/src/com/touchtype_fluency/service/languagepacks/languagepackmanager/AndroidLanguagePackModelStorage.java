package com.touchtype_fluency.service.languagepacks.languagepackmanager;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.touchtype.storage.FolderDecorator;
import com.touchtype.storage.FolderDecoratorFactory;
import java.io.File;
import java.io.IOException;

public class AndroidLanguagePackModelStorage
  implements LanguagePackModelStorage
{
  private static AndroidLanguagePackModelStorage instance;
  private final FolderDecorator mDynamicModelDirectory;
  private final FolderDecorator mPreinstallDirectory;
  private final FolderDecorator mStaticModelDirectory;
  
  private AndroidLanguagePackModelStorage(File paramFile1, File paramFile2, File paramFile3)
  {
    this.mStaticModelDirectory = FolderDecoratorFactory.create(paramFile1);
    this.mDynamicModelDirectory = FolderDecoratorFactory.create(paramFile2);
    this.mPreinstallDirectory = FolderDecoratorFactory.create(paramFile3);
  }
  
  /* Error */
  public static AndroidLanguagePackModelStorage getInstance(android.content.Context paramContext)
  {
    // Byte code:
    //   0: ldc 2
    //   2: monitorenter
    //   3: getstatic 33	com/touchtype_fluency/service/languagepacks/languagepackmanager/AndroidLanguagePackModelStorage:instance	Lcom/touchtype_fluency/service/languagepacks/languagepackmanager/AndroidLanguagePackModelStorage;
    //   6: ifnonnull +82 -> 88
    //   9: aload_0
    //   10: invokevirtual 39	android/content/Context:getResources	()Landroid/content/res/Resources;
    //   13: astore_3
    //   14: aload_0
    //   15: invokevirtual 43	android/content/Context:getFilesDir	()Ljava/io/File;
    //   18: astore 4
    //   20: aload_0
    //   21: invokestatic 49	com/touchtype/storage/AndroidStorageUtils:getExternalDirectory	(Landroid/content/Context;)Ljava/io/File;
    //   24: astore 5
    //   26: aload_3
    //   27: ldc 50
    //   29: invokevirtual 56	android/content/res/Resources:getString	(I)Ljava/lang/String;
    //   32: astore 6
    //   34: aload_3
    //   35: ldc 57
    //   37: invokevirtual 61	android/content/res/Resources:getBoolean	(I)Z
    //   40: ifeq +57 -> 97
    //   43: aload 5
    //   45: astore 7
    //   47: aload_3
    //   48: ldc 62
    //   50: invokevirtual 61	android/content/res/Resources:getBoolean	(I)Z
    //   53: ifeq +51 -> 104
    //   56: aload 5
    //   58: astore 8
    //   60: aload 6
    //   62: invokevirtual 68	java/lang/String:length	()I
    //   65: ifne +46 -> 111
    //   68: aload 5
    //   70: astore 9
    //   72: new 2	com/touchtype_fluency/service/languagepacks/languagepackmanager/AndroidLanguagePackModelStorage
    //   75: dup
    //   76: aload 7
    //   78: aload 8
    //   80: aload 9
    //   82: invokespecial 70	com/touchtype_fluency/service/languagepacks/languagepackmanager/AndroidLanguagePackModelStorage:<init>	(Ljava/io/File;Ljava/io/File;Ljava/io/File;)V
    //   85: putstatic 33	com/touchtype_fluency/service/languagepacks/languagepackmanager/AndroidLanguagePackModelStorage:instance	Lcom/touchtype_fluency/service/languagepacks/languagepackmanager/AndroidLanguagePackModelStorage;
    //   88: getstatic 33	com/touchtype_fluency/service/languagepacks/languagepackmanager/AndroidLanguagePackModelStorage:instance	Lcom/touchtype_fluency/service/languagepacks/languagepackmanager/AndroidLanguagePackModelStorage;
    //   91: astore_2
    //   92: ldc 2
    //   94: monitorexit
    //   95: aload_2
    //   96: areturn
    //   97: aload 4
    //   99: astore 7
    //   101: goto -54 -> 47
    //   104: aload 4
    //   106: astore 8
    //   108: goto -48 -> 60
    //   111: new 72	java/io/File
    //   114: dup
    //   115: aload 6
    //   117: invokespecial 75	java/io/File:<init>	(Ljava/lang/String;)V
    //   120: astore 9
    //   122: goto -50 -> 72
    //   125: astore_1
    //   126: ldc 2
    //   128: monitorexit
    //   129: aload_1
    //   130: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	131	0	paramContext	android.content.Context
    //   125	5	1	localObject	Object
    //   91	5	2	localAndroidLanguagePackModelStorage	AndroidLanguagePackModelStorage
    //   13	35	3	localResources	android.content.res.Resources
    //   18	87	4	localFile1	File
    //   24	45	5	localFile2	File
    //   32	84	6	str	String
    //   45	55	7	localFile3	File
    //   58	49	8	localFile4	File
    //   70	51	9	localFile5	File
    // Exception table:
    //   from	to	target	type
    //   3	43	125	finally
    //   47	56	125	finally
    //   60	68	125	finally
    //   72	88	125	finally
    //   88	92	125	finally
    //   111	122	125	finally
  }
  
  public boolean areAllModelsAvailable()
  {
    return (this.mStaticModelDirectory.isAvailable()) && (this.mDynamicModelDirectory.isAvailable()) && (this.mPreinstallDirectory.isAvailable());
  }
  
  public FolderDecorator getDynamicModelDirectory()
  {
    return this.mDynamicModelDirectory;
  }
  
  public FolderDecorator getPreinstallDirectory()
  {
    return this.mPreinstallDirectory;
  }
  
  public FolderDecorator getStaticModelDirectory()
  {
    return this.mStaticModelDirectory;
  }
  
  public boolean isConfigurationAvailable(String paramString)
  {
    if (!this.mStaticModelDirectory.isAvailable()) {
      return false;
    }
    return new File(this.mStaticModelDirectory.getBaseFolder(), paramString).exists();
  }
  
  public String loadConfiguration(String paramString)
    throws IOException
  {
    try
    {
      String str = Files.toString(new File(getStaticModelDirectory().getBaseFolder(), paramString), Charsets.UTF_8);
      return str;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public void saveConfiguration(String paramString1, String paramString2)
    throws IOException
  {
    try
    {
      Files.write(paramString1, new File(getStaticModelDirectory().getBaseFolder(), paramString2), Charsets.UTF_8);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.languagepacks.languagepackmanager.AndroidLanguagePackModelStorage
 * JD-Core Version:    0.7.0.1
 */