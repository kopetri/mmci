package com.touchtype_fluency.service.languagepacks.languagepackmanager;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.touchtype.social.UserNotificationManager;
import com.touchtype.storage.FolderDecorator;
import com.touchtype.storage.TmpDirectoryHandler;
import com.touchtype.util.LogUtil;
import com.touchtype_fluency.service.DownloadListener;
import com.touchtype_fluency.service.DownloadListener.Result;
import com.touchtype_fluency.service.ProgressListener;
import com.touchtype_fluency.service.languagepacks.layouts.LayoutData;
import com.touchtype_fluency.service.languagepacks.layouts.LayoutData.LayoutMap;
import com.touchtype_fluency.service.util.JsonUtil;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.Bidi;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import junit.framework.Assert;
import org.apache.commons.io.FileUtils;

public class LanguagePack
{
  public static final String CONFIG_FILENAME = ".config";
  public static final String EXTRA_DATA_FILENAME = "extraData.json";
  private static final String TAG = LanguagePack.class.getSimpleName();
  private String archive;
  private AvailableLayouts availableLayouts;
  private boolean beta;
  private final String country;
  private List<String> files;
  private final String language;
  private final LanguagePacks languagePackManager;
  private final FolderDecorator languagePacksFolder;
  private List<ProgressListener> listeners = new LinkedList();
  Map<ProgressListener, ProgressListener> listenersMap = new HashMap();
  private boolean loadingFailed;
  private boolean mDownloadInProgress = false;
  private boolean mEnabled;
  private final ExecutorService mExecutor;
  private String mInstallerContext = null;
  private LanguagePackDownloader mLPDownloader;
  private LiveLanguagePack mLive = null;
  private LanguagePackDownloader mLiveLPDownloader;
  private String name;
  private String sha1;
  private final TmpDirectoryHandler temporaryDirectoryHandler;
  private boolean updateAvailable;
  private final UserNotificationManager userNotificationManager;
  private int version;
  
  public LanguagePack(JsonObject paramJsonObject, LanguagePacks paramLanguagePacks, FolderDecorator paramFolderDecorator, TmpDirectoryHandler paramTmpDirectoryHandler, UserNotificationManager paramUserNotificationManager)
  {
    this.languagePackManager = paramLanguagePacks;
    this.languagePacksFolder = paramFolderDecorator;
    this.userNotificationManager = paramUserNotificationManager;
    this.temporaryDirectoryHandler = paramTmpDirectoryHandler;
    this.mExecutor = Executors.newSingleThreadExecutor();
    this.language = JsonUtil.getOrThrow(paramJsonObject, "language").getAsString();
    this.country = JsonUtil.getOpt(paramJsonObject, "country", null);
    try
    {
      this.version = JsonUtil.getOpt(paramJsonObject, "version", 0);
      this.name = JsonUtil.getOrThrow(paramJsonObject, "name").getAsString();
      if (paramJsonObject.has("default-layout"))
      {
        localLayoutMap = LayoutData.get(paramJsonObject.get("default-layout").getAsString());
        if (localLayoutMap == null)
        {
          LogUtil.w(TAG, "Couldn't find a default layout from " + paramJsonObject.get("default-layout").getAsString());
          localLayoutMap = LayoutData.LayoutMap.QWERTY;
        }
        this.availableLayouts = new AvailableLayouts(localLayoutMap);
        this.availableLayouts.loadFromFile(imeFile());
        this.archive = JsonUtil.getOrThrow(paramJsonObject, "archive").getAsString();
        this.sha1 = JsonUtil.getOrThrow(paramJsonObject, "sha1").getAsString();
        JsonElement localJsonElement1 = paramJsonObject.get("live");
        if (localJsonElement1 != null) {
          this.mLive = new LiveLanguagePack(localJsonElement1.getAsJsonObject(), null);
        }
        this.beta = JsonUtil.getOpt(paramJsonObject, "beta", false);
        this.updateAvailable = JsonUtil.getOpt(paramJsonObject, "updateAvailable", false);
        this.mEnabled = paramLanguagePacks.isLanguagePackEnabled(this.language + "_" + this.country, false);
        this.loadingFailed = false;
        JsonElement localJsonElement2 = paramJsonObject.get("files");
        if (localJsonElement2 == null) {
          return;
        }
        JsonArray localJsonArray = localJsonElement2.getAsJsonArray();
        int i = localJsonArray.size();
        this.files = new ArrayList(i);
        for (int j = 0; j < i; j++) {
          this.files.add(localJsonArray.get(j).getAsString());
        }
      }
    }
    catch (NumberFormatException localNumberFormatException)
    {
      for (;;)
      {
        this.version = 0;
        continue;
        LayoutData.LayoutMap localLayoutMap = LayoutData.getLayoutFromLanguage(this.language, this.country);
      }
    }
  }
  
  private void addListener(ProgressListener paramProgressListener)
  {
    synchronized (this.listeners)
    {
      this.listeners.add(paramProgressListener);
      return;
    }
  }
  
  private File getBaseDirectoryForID(String paramString)
  {
    return new File(this.languagePacksFolder.getBaseFolder(), paramString);
  }
  
  private File getDirectoryForID(String paramString)
  {
    return getBaseDirectoryForID(paramString);
  }
  
  private File imeFile()
  {
    return new File(getBaseDirectoryForID(getID()), "ime.json");
  }
  
  private void notifyListenersWithProgress(int paramInt1, int paramInt2, ProgressListener paramProgressListener)
  {
    synchronized (this.listenersMap)
    {
      if (this.listenersMap.containsKey(paramProgressListener)) {
        ((ProgressListener)this.listenersMap.get(paramProgressListener)).onProgress(paramInt1, paramInt2);
      }
      return;
    }
  }
  
  private void onDownloadAndUnzipComplete(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, ProgressListener paramProgressListener)
  {
    this.mDownloadInProgress = false;
    boolean bool;
    if ((!paramBoolean1) && (!paramBoolean2))
    {
      bool = true;
      this.loadingFailed = bool;
      if (!paramBoolean1) {
        break label157;
      }
      this.files = this.mLPDownloader.getFiles();
      this.availableLayouts.loadFromFile(imeFile());
      this.updateAvailable = false;
      this.languagePackManager.languagePackDownloaded();
    }
    ProgressListener localProgressListener;
    label157:
    synchronized (this.listenersMap)
    {
      if (this.listenersMap.containsKey(paramProgressListener))
      {
        localProgressListener = (ProgressListener)this.listenersMap.get(paramProgressListener);
        localProgressListener.onComplete(paramBoolean1, paramBoolean2, paramBoolean3, paramBoolean4);
        this.listenersMap.remove(paramProgressListener);
      }
    }
  }
  
  private void onLiveDownloadAndUnzipComplete(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
  {
    LiveLanguagePack localLiveLanguagePack = this.mLive;
    if ((!paramBoolean1) && (!paramBoolean2)) {}
    for (boolean bool = true;; bool = false)
    {
      LiveLanguagePack.access$302(localLiveLanguagePack, bool);
      if (paramBoolean1)
      {
        LiveLanguagePack.access$502(this.mLive, false);
        this.languagePackManager.liveLanguagePackDownloaded();
      }
      LiveLanguagePack.access$402(this.mLive, false);
      return;
    }
  }
  
  public void cancelDownload()
  {
    this.mDownloadInProgress = false;
    if (this.mLPDownloader != null)
    {
      this.mLPDownloader.cancel();
      this.mLPDownloader.resetProgress();
    }
  }
  
  public void cancelLiveDownload()
  {
    if ((this.mLiveLPDownloader != null) && (this.mLiveLPDownloader.isInProgress()))
    {
      this.mLiveLPDownloader.cancel();
      this.mLiveLPDownloader.resetProgress();
    }
  }
  
  public void download(ProgressListener paramProgressListener)
  {
    LanguagePackProgressListener localLanguagePackProgressListener = new LanguagePackProgressListener(null);
    Map localMap = this.listenersMap;
    if (paramProgressListener != null) {}
    try
    {
      this.listenersMap.put(localLanguagePackProgressListener, paramProgressListener);
      addListener(paramProgressListener);
      this.mLPDownloader = new LanguagePackDownloader(getID(), this.archive, localLanguagePackProgressListener, this.temporaryDirectoryHandler, this.languagePacksFolder, this.sha1, this);
      this.mExecutor.execute(this.mLPDownloader);
      this.mDownloadInProgress = true;
      return;
    }
    finally {}
  }
  
  public void downloadLive(DownloadListener paramDownloadListener)
  {
    LiveLanguagePackProgressListener localLiveLanguagePackProgressListener = new LiveLanguagePackProgressListener(paramDownloadListener);
    this.mLiveLPDownloader = new LanguagePackDownloader(getLiveID(), this.mLive.mArchive, localLiveLanguagePackProgressListener, this.temporaryDirectoryHandler, this.languagePacksFolder, this.mLive.mSha1, this);
    this.mExecutor.execute(this.mLiveLPDownloader);
    LiveLanguagePack.access$402(this.mLive, true);
  }
  
  public List<LayoutData.LayoutMap> getAvailableLayouts()
  {
    return this.availableLayouts.getAvailableLayouts();
  }
  
  public String getCountry()
  {
    return this.country;
  }
  
  public int getCurrentMax()
  {
    if (this.mLPDownloader != null) {
      return this.mLPDownloader.getCurrentMax();
    }
    return -1;
  }
  
  public int getCurrentProgress()
  {
    if (this.mLPDownloader != null) {
      return this.mLPDownloader.getCurrentProgress();
    }
    return -1;
  }
  
  public LayoutData.LayoutMap getDefaultLayout()
  {
    return this.availableLayouts.getDefaultLayout();
  }
  
  public String getDefaultLayoutName()
  {
    return this.availableLayouts.getDefaultLayout().getLayoutName();
  }
  
  public File getDirectory()
    throws IOException
  {
    return getDirectoryForID(getID());
  }
  
  public List<String> getExtraPuncCharsIfExtendedLatinLayout()
  {
    if (hasExtendedLatinLayout()) {
      return this.availableLayouts.getExtraPunctuationChars();
    }
    return null;
  }
  
  public Collection<String> getFiles()
  {
    return Collections.unmodifiableCollection(this.files);
  }
  
  public String getID()
  {
    if (this.country != null) {
      return this.language + "_" + this.country;
    }
    return this.language;
  }
  
  public String getLanguage()
  {
    return this.language;
  }
  
  public File getLiveDirectory()
    throws IOException
  {
    return getDirectoryForID(getLiveID());
  }
  
  public String getLiveID()
  {
    return getID() + "-live";
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public int getVersion()
  {
    return this.version;
  }
  
  public boolean hasExtendedLatinLayout()
  {
    Iterator localIterator = getAvailableLayouts().iterator();
    while (localIterator.hasNext())
    {
      LayoutData.LayoutMap localLayoutMap = (LayoutData.LayoutMap)localIterator.next();
      if ((localLayoutMap.providesLatin()) && (localLayoutMap.extendsQwerty())) {
        return true;
      }
    }
    return false;
  }
  
  public boolean hasListener(ProgressListener paramProgressListener)
  {
    synchronized (this.listeners)
    {
      boolean bool = this.listeners.contains(paramProgressListener);
      return bool;
    }
  }
  
  public boolean hasLiveLanguageSupport()
  {
    return this.mLive != null;
  }
  
  public boolean hasMultipleLayouts()
  {
    return this.availableLayouts.getAlternateLayouts().size() > 0;
  }
  
  public boolean hasUnextendedLatinLayout()
  {
    Iterator localIterator = getAvailableLayouts().iterator();
    while (localIterator.hasNext())
    {
      LayoutData.LayoutMap localLayoutMap = (LayoutData.LayoutMap)localIterator.next();
      if ((localLayoutMap.providesLatin()) && (!localLayoutMap.extendsQwerty())) {
        return true;
      }
    }
    return false;
  }
  
  public boolean isBeta()
  {
    return this.beta;
  }
  
  /* Error */
  public boolean isBroken()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 226	com/touchtype_fluency/service/languagepacks/languagepackmanager/LanguagePack:loadingFailed	Z
    //   6: ifne +19 -> 25
    //   9: aload_0
    //   10: getfield 224	com/touchtype_fluency/service/languagepacks/languagepackmanager/LanguagePack:mEnabled	Z
    //   13: ifeq +18 -> 31
    //   16: aload_0
    //   17: invokevirtual 487	com/touchtype_fluency/service/languagepacks/languagepackmanager/LanguagePack:isDownloaded	()Z
    //   20: istore_3
    //   21: iload_3
    //   22: ifne +9 -> 31
    //   25: iconst_1
    //   26: istore_2
    //   27: aload_0
    //   28: monitorexit
    //   29: iload_2
    //   30: ireturn
    //   31: iconst_0
    //   32: istore_2
    //   33: goto -6 -> 27
    //   36: astore_1
    //   37: aload_0
    //   38: monitorexit
    //   39: aload_1
    //   40: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	41	0	this	LanguagePack
    //   36	4	1	localObject	Object
    //   26	7	2	bool1	boolean
    //   20	2	3	bool2	boolean
    // Exception table:
    //   from	to	target	type
    //   2	21	36	finally
  }
  
  public boolean isDownloadInProgress()
  {
    return this.mDownloadInProgress;
  }
  
  public boolean isDownloaded()
  {
    try
    {
      boolean bool1 = new File(getDirectory(), ".config").exists();
      boolean bool2 = false;
      if (bool1)
      {
        boolean bool3 = this.mDownloadInProgress;
        bool2 = false;
        if (!bool3) {
          bool2 = true;
        }
      }
      return bool2;
    }
    catch (IOException localIOException)
    {
      LogUtil.e(TAG, localIOException.getMessage(), localIOException);
    }
    return false;
  }
  
  public boolean isEnabled()
  {
    return this.mEnabled;
  }
  
  public boolean isIsInstallerContext()
  {
    return this.mInstallerContext != null;
  }
  
  public boolean isLiveDownloaded()
  {
    if (this.mLive == null) {}
    for (;;)
    {
      return false;
      try
      {
        if (new File(getLiveDirectory(), ".config").exists())
        {
          boolean bool = this.mLive.mDownloadInProgress;
          if (!bool) {
            return true;
          }
        }
      }
      catch (IOException localIOException)
      {
        LogUtil.e(TAG, localIOException.getMessage(), localIOException);
      }
    }
    return false;
  }
  
  /* Error */
  public boolean isLiveLoadable()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 73	com/touchtype_fluency/service/languagepacks/languagepackmanager/LanguagePack:mLive	Lcom/touchtype_fluency/service/languagepacks/languagepackmanager/LanguagePack$LiveLanguagePack;
    //   6: astore_2
    //   7: iconst_0
    //   8: istore_3
    //   9: aload_2
    //   10: ifnonnull +7 -> 17
    //   13: aload_0
    //   14: monitorexit
    //   15: iload_3
    //   16: ireturn
    //   17: new 282	java/io/File
    //   20: dup
    //   21: aload_0
    //   22: invokevirtual 504	com/touchtype_fluency/service/languagepacks/languagepackmanager/LanguagePack:getLiveDirectory	()Ljava/io/File;
    //   25: ldc 8
    //   27: invokespecial 290	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   30: invokevirtual 493	java/io/File:exists	()Z
    //   33: istore 5
    //   35: iload 5
    //   37: istore_3
    //   38: goto -25 -> 13
    //   41: astore 4
    //   43: getstatic 61	com/touchtype_fluency/service/languagepacks/languagepackmanager/LanguagePack:TAG	Ljava/lang/String;
    //   46: aload 4
    //   48: invokevirtual 496	java/io/IOException:getMessage	()Ljava/lang/String;
    //   51: aload 4
    //   53: invokestatic 500	com/touchtype/util/LogUtil:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   56: iconst_0
    //   57: istore_3
    //   58: goto -45 -> 13
    //   61: astore_1
    //   62: aload_0
    //   63: monitorexit
    //   64: aload_1
    //   65: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	66	0	this	LanguagePack
    //   61	4	1	localObject	Object
    //   6	4	2	localLiveLanguagePack	LiveLanguagePack
    //   8	50	3	bool1	boolean
    //   41	11	4	localIOException	IOException
    //   33	3	5	bool2	boolean
    // Exception table:
    //   from	to	target	type
    //   17	35	41	java/io/IOException
    //   2	7	61	finally
    //   17	35	61	finally
    //   43	56	61	finally
  }
  
  public boolean isLiveUpdateNecessary()
  {
    return (this.mEnabled) && (this.mLive != null) && ((this.mLive.mUpdateAvailable) || ((!isLiveDownloaded()) && (!this.mLive.mDownloadInProgress)));
  }
  
  public boolean isLoadable()
  {
    try
    {
      boolean bool2 = new File(getDirectory(), ".config").exists();
      bool1 = bool2;
    }
    catch (IOException localIOException)
    {
      for (;;)
      {
        LogUtil.e(TAG, localIOException.getMessage(), localIOException);
        boolean bool1 = false;
      }
    }
    finally {}
    return bool1;
  }
  
  public boolean isPreinstalled()
  {
    URI localURI = URI.create(this.archive);
    return (localURI.getScheme().contentEquals("file")) && (new File(localURI.getPath()).exists());
  }
  
  public boolean isRightToLeft()
  {
    return Bidi.requiresBidi(this.name.toCharArray(), 0, this.name.length());
  }
  
  public boolean isUpdateAvailable()
  {
    return this.updateAvailable;
  }
  
  public void merge(LanguagePack paramLanguagePack, boolean paramBoolean)
  {
    boolean bool;
    if ((paramLanguagePack.language.equals(this.language)) && (((paramLanguagePack.country == null) && (this.country == null)) || ((paramLanguagePack.country != null) && (paramLanguagePack.country.equals(this.country)))))
    {
      bool = true;
      Assert.assertTrue(bool);
      if ((!isPreinstalled()) || (isDownloaded())) {
        break label75;
      }
    }
    label75:
    do
    {
      return;
      bool = false;
      break;
      if (!paramLanguagePack.sha1.equals(this.sha1))
      {
        this.name = paramLanguagePack.name;
        this.archive = paramLanguagePack.archive;
        this.sha1 = paramLanguagePack.sha1;
        this.beta = paramLanguagePack.beta;
        if (paramLanguagePack.availableLayouts.usingLegacyDefaultLayout()) {
          this.availableLayouts.setLegacyDefaultLayout(paramLanguagePack.availableLayouts.getDefaultLayout());
        }
        this.updateAvailable = true;
        if ((paramLanguagePack.isDownloaded()) && (paramLanguagePack.isEnabled()) && (paramBoolean)) {
          this.userNotificationManager.updateAvailable();
        }
      }
    } while (paramLanguagePack.mLive == null);
    if (this.mLive == null)
    {
      this.mLive = new LiveLanguagePack(paramLanguagePack.mLive.mArchive, paramLanguagePack.mLive.mSha1, true);
      return;
    }
    this.mLive.merge(paramLanguagePack.mLive);
  }
  
  public void removeListener(ProgressListener paramProgressListener)
  {
    synchronized (this.listenersMap)
    {
      Iterator localIterator = this.listenersMap.entrySet().iterator();
      while (localIterator.hasNext()) {
        if (paramProgressListener.equals(((Map.Entry)localIterator.next()).getValue())) {
          localIterator.remove();
        }
      }
    }
    boolean bool;
    if (!this.listenersMap.containsValue(paramProgressListener)) {
      bool = true;
    }
    for (;;)
    {
      Assert.assertTrue(bool);
      synchronized (this.listeners)
      {
        Assert.assertTrue(this.listeners.remove(paramProgressListener));
        return;
        bool = false;
      }
    }
  }
  
  public void resetProgress()
  {
    if (this.mLPDownloader != null) {
      this.mLPDownloader.resetProgress();
    }
  }
  
  protected void setEnabled(boolean paramBoolean1, boolean paramBoolean2)
    throws DownloadRequiredException, MaximumLanguagesException
  {
    this.mEnabled = paramBoolean1;
    this.loadingFailed = false;
    if (this.mLive != null) {
      LiveLanguagePack.access$302(this.mLive, false);
    }
    if (paramBoolean1)
    {
      bool = isDownloaded();
      i = 0;
      if (!bool)
      {
        download(null);
        i = 1;
      }
      if ((isLiveUpdateNecessary()) && (this.languagePackManager.liveLanguagesEnabled())) {
        downloadLive(this.languagePackManager.scheduledRetryLiveListener());
      }
      if (i != 0) {
        throw new DownloadRequiredException();
      }
      if (paramBoolean2) {
        this.languagePackManager.notifyListeners();
      }
    }
    while (!paramBoolean2)
    {
      boolean bool;
      int i;
      return;
    }
    this.languagePackManager.notifyListeners();
  }
  
  public void setInstallerContext(String paramString)
  {
    this.mInstallerContext = paramString;
    if (this.userNotificationManager != null) {
      this.userNotificationManager.setInstallerContext(this.mInstallerContext);
    }
  }
  
  public void setLoadingFailed(boolean paramBoolean)
  {
    this.loadingFailed = paramBoolean;
  }
  
  public JsonElement toJSON()
  {
    JsonObject localJsonObject = new JsonObject();
    localJsonObject.addProperty("language", this.language);
    JsonUtil.addOpt(localJsonObject, "country", this.country);
    localJsonObject.addProperty("name", this.name);
    if (this.availableLayouts.usingLegacyDefaultLayout()) {
      localJsonObject.addProperty("default-layout", this.availableLayouts.getDefaultLayout().getLayoutName());
    }
    localJsonObject.addProperty("archive", this.archive.toString());
    localJsonObject.addProperty("sha1", this.sha1);
    localJsonObject.addProperty("beta", Boolean.valueOf(this.beta));
    localJsonObject.addProperty("updateAvailable", Boolean.valueOf(this.updateAvailable));
    if (this.mLive != null) {
      localJsonObject.add("live", this.mLive.toJSON());
    }
    if (this.files != null) {
      localJsonObject.add("files", JsonUtil.buildJsonArray(this.files));
    }
    return localJsonObject;
  }
  
  public String toString()
  {
    return "<" + this.language + "_" + this.country + " '" + this.name + "' " + this.archive + " " + this.sha1 + " " + this.files + ">";
  }
  
  static class AvailableLayouts
  {
    private List<LayoutData.LayoutMap> mAlternateLayouts;
    private LayoutData.LayoutMap mDefaultLayout;
    private List<String> mExtraPuncChars;
    private LayoutData.LayoutMap mLegacyDefaultLayout;
    
    AvailableLayouts(LayoutData.LayoutMap paramLayoutMap)
    {
      this.mLegacyDefaultLayout = paramLayoutMap;
      this.mAlternateLayouts = new ArrayList();
      this.mExtraPuncChars = new ArrayList();
    }
    
    private void appendOtherNonExtendedLatinLayouts()
    {
      try
      {
        Iterator localIterator = getAvailableLayouts().iterator();
        LayoutData.LayoutMap localLayoutMap1;
        do
        {
          boolean bool = localIterator.hasNext();
          i = 0;
          if (!bool) {
            break;
          }
          localLayoutMap1 = (LayoutData.LayoutMap)localIterator.next();
        } while ((!localLayoutMap1.providesLatin()) || (localLayoutMap1.extendsQwerty()));
        int i = 1;
        if ((i != 0) && (getDefaultLayout().isLayoutSelectable())) {
          for (LayoutData.LayoutMap localLayoutMap2 : LayoutData.LayoutMap.values()) {
            if ((localLayoutMap2.isLayoutSelectable()) && (localLayoutMap2.providesLatin()) && (!localLayoutMap2.equals(getDefaultLayout())) && (!localLayoutMap2.extendsQwerty()) && (!this.mAlternateLayouts.contains(localLayoutMap2))) {
              this.mAlternateLayouts.add(localLayoutMap2);
            }
          }
        }
        return;
      }
      finally {}
    }
    
    private void loadFromFile(File paramFile)
    {
      try
      {
        String str;
        if (paramFile.canRead())
        {
          str = FileUtils.readFileToString(paramFile);
          JsonElement localJsonElement = new JsonParser().parse(str.trim());
          if (!localJsonElement.isJsonObject()) {
            break label52;
          }
          loadFromJSON(localJsonElement.getAsJsonObject());
        }
        for (;;)
        {
          label49:
          return;
          label52:
          LogUtil.e(LanguagePack.TAG, "Contents of " + paramFile + " not a json object: '" + str + "'");
        }
      }
      catch (IOException localIOException)
      {
        break label49;
      }
      finally {}
    }
    
    List<LayoutData.LayoutMap> getAlternateLayouts()
    {
      try
      {
        List localList = this.mAlternateLayouts;
        return localList;
      }
      finally
      {
        localObject = finally;
        throw localObject;
      }
    }
    
    List<LayoutData.LayoutMap> getAvailableLayouts()
    {
      try
      {
        ArrayList localArrayList = new ArrayList(getAlternateLayouts());
        Collections.sort(localArrayList, new Comparator()
        {
          public int compare(LayoutData.LayoutMap paramAnonymousLayoutMap1, LayoutData.LayoutMap paramAnonymousLayoutMap2)
          {
            return paramAnonymousLayoutMap1.getLayoutName().compareTo(paramAnonymousLayoutMap2.getLayoutName());
          }
        });
        localArrayList.add(0, getDefaultLayout());
        List localList = Collections.unmodifiableList(localArrayList);
        return localList;
      }
      finally
      {
        localObject = finally;
        throw localObject;
      }
    }
    
    /* Error */
    LayoutData.LayoutMap getDefaultLayout()
    {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: aload_0
      //   3: invokevirtual 180	com/touchtype_fluency/service/languagepacks/languagepackmanager/LanguagePack$AvailableLayouts:usingLegacyDefaultLayout	()Z
      //   6: ifeq +12 -> 18
      //   9: aload_0
      //   10: getfield 19	com/touchtype_fluency/service/languagepacks/languagepackmanager/LanguagePack$AvailableLayouts:mLegacyDefaultLayout	Lcom/touchtype_fluency/service/languagepacks/layouts/LayoutData$LayoutMap;
      //   13: astore_2
      //   14: aload_0
      //   15: monitorexit
      //   16: aload_2
      //   17: areturn
      //   18: aload_0
      //   19: getfield 182	com/touchtype_fluency/service/languagepacks/languagepackmanager/LanguagePack$AvailableLayouts:mDefaultLayout	Lcom/touchtype_fluency/service/languagepacks/layouts/LayoutData$LayoutMap;
      //   22: astore_2
      //   23: goto -9 -> 14
      //   26: astore_1
      //   27: aload_0
      //   28: monitorexit
      //   29: aload_1
      //   30: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	31	0	this	AvailableLayouts
      //   26	4	1	localObject	Object
      //   13	10	2	localLayoutMap	LayoutData.LayoutMap
      // Exception table:
      //   from	to	target	type
      //   2	14	26	finally
      //   18	23	26	finally
    }
    
    List<String> getExtraPunctuationChars()
    {
      try
      {
        List localList = this.mExtraPuncChars;
        return localList;
      }
      finally
      {
        localObject = finally;
        throw localObject;
      }
    }
    
    void loadFromJSON(JsonObject paramJsonObject)
    {
      for (;;)
      {
        int i;
        try
        {
          this.mDefaultLayout = LayoutData.get(JsonUtil.getOrThrow(paramJsonObject, "default-layout").getAsString());
          this.mExtraPuncChars.clear();
          JsonArray localJsonArray1 = paramJsonObject.getAsJsonArray("extra-punctuation");
          if (localJsonArray1 != null)
          {
            int j = 0;
            if (j < localJsonArray1.size())
            {
              this.mExtraPuncChars.add(localJsonArray1.get(j).getAsString());
              j++;
              continue;
            }
          }
          this.mAlternateLayouts.clear();
          JsonArray localJsonArray2 = paramJsonObject.getAsJsonArray("alternate-layouts");
          if (localJsonArray2 != null)
          {
            i = 0;
            if (i < localJsonArray2.size())
            {
              LayoutData.LayoutMap localLayoutMap = LayoutData.get(localJsonArray2.get(i).getAsString());
              if (localLayoutMap != null) {
                this.mAlternateLayouts.add(localLayoutMap);
              } else {
                LogUtil.w(LanguagePack.TAG, "Discarded alternate layout " + localLayoutMap + " as it couldn't be found");
              }
            }
          }
        }
        finally {}
        appendOtherNonExtendedLatinLayouts();
        return;
        i++;
      }
    }
    
    void setLegacyDefaultLayout(LayoutData.LayoutMap paramLayoutMap)
    {
      try
      {
        this.mLegacyDefaultLayout = paramLayoutMap;
        return;
      }
      finally
      {
        localObject = finally;
        throw localObject;
      }
    }
    
    /* Error */
    boolean usingLegacyDefaultLayout()
    {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: aload_0
      //   3: getfield 182	com/touchtype_fluency/service/languagepacks/languagepackmanager/LanguagePack$AvailableLayouts:mDefaultLayout	Lcom/touchtype_fluency/service/languagepacks/layouts/LayoutData$LayoutMap;
      //   6: astore_2
      //   7: aload_2
      //   8: ifnonnull +9 -> 17
      //   11: iconst_1
      //   12: istore_3
      //   13: aload_0
      //   14: monitorexit
      //   15: iload_3
      //   16: ireturn
      //   17: iconst_0
      //   18: istore_3
      //   19: goto -6 -> 13
      //   22: astore_1
      //   23: aload_0
      //   24: monitorexit
      //   25: aload_1
      //   26: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	27	0	this	AvailableLayouts
      //   22	4	1	localObject	Object
      //   6	2	2	localLayoutMap	LayoutData.LayoutMap
      //   12	7	3	bool	boolean
      // Exception table:
      //   from	to	target	type
      //   2	7	22	finally
    }
  }
  
  private final class LanguagePackProgressListener
    implements ProgressListener
  {
    private LanguagePackProgressListener() {}
    
    public void onComplete(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
    {
      LanguagePack.this.onDownloadAndUnzipComplete(paramBoolean1, paramBoolean2, paramBoolean3, paramBoolean4, this);
    }
    
    public void onProgress(int paramInt1, int paramInt2)
    {
      LanguagePack.this.notifyListenersWithProgress(paramInt1, paramInt2, this);
    }
  }
  
  private static class LiveLanguagePack
  {
    private String mArchive;
    private boolean mDownloadInProgress;
    private boolean mLoadingFailed;
    private String mSha1;
    private boolean mUpdateAvailable;
    
    private LiveLanguagePack(JsonObject paramJsonObject)
    {
      this.mArchive = JsonUtil.getOrThrow(paramJsonObject, "archive").getAsString();
      this.mSha1 = JsonUtil.getOrThrow(paramJsonObject, "sha1").getAsString();
      this.mUpdateAvailable = JsonUtil.getOpt(paramJsonObject, "updateAvailable", false);
      this.mLoadingFailed = false;
      this.mDownloadInProgress = false;
    }
    
    LiveLanguagePack(String paramString1, String paramString2, boolean paramBoolean)
    {
      this.mArchive = paramString1;
      this.mSha1 = paramString2;
      this.mUpdateAvailable = paramBoolean;
      this.mLoadingFailed = false;
      this.mDownloadInProgress = false;
    }
    
    void merge(LiveLanguagePack paramLiveLanguagePack)
    {
      if (!paramLiveLanguagePack.mSha1.equals(this.mSha1))
      {
        this.mArchive = paramLiveLanguagePack.mArchive;
        this.mSha1 = paramLiveLanguagePack.mSha1;
        this.mUpdateAvailable = true;
      }
    }
    
    JsonElement toJSON()
    {
      JsonObject localJsonObject = new JsonObject();
      localJsonObject.addProperty("archive", this.mArchive);
      localJsonObject.addProperty("sha1", this.mSha1);
      localJsonObject.addProperty("updateAvailable", Boolean.valueOf(this.mUpdateAvailable));
      return localJsonObject;
    }
  }
  
  private final class LiveLanguagePackProgressListener
    implements ProgressListener
  {
    private DownloadListener mDownloadListener;
    
    public LiveLanguagePackProgressListener(DownloadListener paramDownloadListener)
    {
      this.mDownloadListener = paramDownloadListener;
    }
    
    public void onComplete(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
    {
      LanguagePack.this.onLiveDownloadAndUnzipComplete(paramBoolean1, paramBoolean2, paramBoolean3, paramBoolean4);
      if (paramBoolean1)
      {
        this.mDownloadListener.onCompletion(DownloadListener.Result.SUCCESS);
        return;
      }
      if (paramBoolean2)
      {
        this.mDownloadListener.onCompletion(DownloadListener.Result.CANCELLED);
        return;
      }
      if (paramBoolean4)
      {
        this.mDownloadListener.onCompletion(DownloadListener.Result.CONFIG_FAILURE);
        return;
      }
      this.mDownloadListener.onCompletion(DownloadListener.Result.NETWORK_FAILURE);
    }
    
    public void onProgress(int paramInt1, int paramInt2) {}
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.languagepacks.languagepackmanager.LanguagePack
 * JD-Core Version:    0.7.0.1
 */