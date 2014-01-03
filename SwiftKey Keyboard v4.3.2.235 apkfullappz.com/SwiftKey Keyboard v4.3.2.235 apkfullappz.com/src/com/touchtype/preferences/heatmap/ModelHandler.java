package com.touchtype.preferences.heatmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import com.touchtype.preferences.heatmap.util.JsonUtil;
import com.touchtype.util.LogUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import org.json.JSONException;

public class ModelHandler
{
  private static final String TAG = ModelHandler.class.getSimpleName();
  private final int mBackShade;
  private final Context mContext;
  private final File mExternalStorage;
  private final String mFreshnessFile;
  private final int mHashCode;
  private final int mHeight;
  private final int mKeyShade;
  private final String mName;
  private final int mOrientation;
  private final String mOutName;
  private final File mPath;
  private final int mWidth;
  
  private ModelHandler(File paramFile, Context paramContext, int paramInt1, int paramInt2)
    throws IOException, JSONException
  {
    this.mContext = paramContext;
    this.mFreshnessFile = paramContext.getString(2131296649);
    this.mExternalStorage = getExternalDir(paramContext);
    this.mPath = paramFile;
    this.mOutName = paramContext.getString(2131296657);
    this.mName = getHashname(paramFile);
    KeyData localKeyData = new KeyData(getIMpath());
    this.mHashCode = localKeyData.getIMhash();
    this.mWidth = localKeyData.getKeyboardWidth();
    this.mHeight = localKeyData.getKeyboardHeight();
    this.mOrientation = 0;
    this.mKeyShade = paramInt1;
    this.mBackShade = paramInt2;
  }
  
  private Bitmap addBranding(Bitmap paramBitmap)
  {
    Paint localPaint = new Paint();
    localPaint.setAntiAlias(false);
    localPaint.setFilterBitmap(false);
    localPaint.setColor(this.mBackShade);
    Bitmap localBitmap1 = addPadding(paramBitmap, 5, 5, 10, 5, localPaint);
    Bitmap localBitmap2 = addPadding(drawBanner(paramBitmap.getWidth(), (int)(localBitmap1.getHeight() / 3.0F), this.mBackShade), 10, 10, 5, 5, localPaint);
    Bitmap localBitmap3 = Bitmap.createBitmap(localBitmap1.getWidth(), localBitmap1.getHeight() + localBitmap2.getHeight(), Bitmap.Config.ARGB_8888);
    Canvas localCanvas = new Canvas(localBitmap3);
    localCanvas.drawBitmap(localBitmap1, new Matrix(), localPaint);
    localCanvas.drawBitmap(localBitmap2, 0.0F, localBitmap1.getHeight(), localPaint);
    localPaint.setColor(-1);
    return addPadding(localBitmap3, 10, 10, 10, 10, localPaint);
  }
  
  private Bitmap addPadding(Bitmap paramBitmap, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Paint paramPaint)
  {
    Bitmap localBitmap = Bitmap.createBitmap(paramInt3 + (paramInt1 + paramBitmap.getWidth()), paramInt4 + (paramInt2 + paramBitmap.getHeight()), paramBitmap.getConfig());
    Canvas localCanvas = new Canvas(localBitmap);
    localCanvas.drawColor(paramPaint.getColor());
    localCanvas.translate(paramInt1, paramInt2);
    localCanvas.drawBitmap(paramBitmap, new Matrix(), paramPaint);
    paramBitmap.recycle();
    return localBitmap;
  }
  
  public static ModelHandler createKeyPressModel(Context paramContext)
    throws IOException, JSONException
  {
    return createKeyPressModel(paramContext, -1, -16777216);
  }
  
  public static ModelHandler createKeyPressModel(Context paramContext, int paramInt1, int paramInt2)
    throws IOException, JSONException
  {
    return createKeyPressModel(paramContext, paramInt1, paramInt2, 0);
  }
  
  public static ModelHandler createKeyPressModel(Context paramContext, int paramInt1, int paramInt2, int paramInt3)
    throws IOException, JSONException
  {
    String str = paramContext.getString(2131296648);
    File localFile = selectModel(getExternalDir(paramContext), str, paramInt3);
    if (localFile != null) {
      return new ModelHandler(localFile, paramContext, paramInt1, paramInt2);
    }
    return null;
  }
  
  private Bitmap drawBanner(int paramInt1, int paramInt2, int paramInt3)
  {
    Bitmap localBitmap1 = BitmapFactory.decodeResource(this.mContext.getResources(), 2130838237);
    float f1 = paramInt1 / 2 / localBitmap1.getWidth();
    int i = (int)(f1 * localBitmap1.getHeight());
    if (i > paramInt2)
    {
      f1 = paramInt2 / localBitmap1.getHeight();
      i = paramInt2;
    }
    Bitmap localBitmap2 = Bitmap.createBitmap(paramInt1, i, Bitmap.Config.ARGB_8888);
    Canvas localCanvas = new Canvas(localBitmap2);
    localCanvas.drawColor(paramInt3);
    Paint localPaint = new Paint();
    localPaint.setAntiAlias(true);
    localPaint.setFilterBitmap(true);
    localPaint.setDither(true);
    float f2 = 0.4F * localBitmap2.getHeight();
    localPaint.setTextSize(f2);
    String str1 = this.mContext.getString(2131296659);
    String str2 = this.mContext.getString(2131296660);
    if (localPaint.measureText(str1 + str2) > 0.8F * (paramInt1 - f1 * localBitmap1.getWidth())) {
      localPaint.setTextSize(0.8F * (f2 / localPaint.measureText(str1 + str2)) * (paramInt1 - f1 * localBitmap1.getWidth()));
    }
    localPaint.setColor(this.mKeyShade);
    localPaint.setAlpha(128);
    localCanvas.drawText(str1, 0.0F, (int)(localBitmap2.getHeight() / 2.0F + localPaint.getTextSize() / 2.0F), localPaint);
    localPaint.setAlpha(255);
    localCanvas.drawText(str2, localPaint.measureText(str1), (int)(localBitmap2.getHeight() / 2.0F + localPaint.getTextSize() / 2.0F), localPaint);
    Matrix localMatrix = new Matrix();
    localMatrix.setScale(f1, f1);
    localMatrix.postTranslate(paramInt1 - f1 * localBitmap1.getWidth(), 0.0F);
    localCanvas.drawBitmap(localBitmap1, localMatrix, localPaint);
    return localBitmap2;
  }
  
  private boolean exportTo(File paramFile, boolean paramBoolean)
  {
    String str = Environment.getExternalStorageState();
    if ("mounted".equals(str))
    {
      try
      {
        FileOutputStream localFileOutputStream = new FileOutputStream(paramFile);
        if (paramBoolean) {
          addBranding(fetch()).compress(Bitmap.CompressFormat.PNG, 100, localFileOutputStream);
        }
        for (;;)
        {
          localFileOutputStream.close();
          return true;
          fetch().compress(Bitmap.CompressFormat.PNG, 100, localFileOutputStream);
        }
        if (!"mounted_ro".equals(str)) {
          break label107;
        }
      }
      catch (IOException localIOException)
      {
        LogUtil.w(TAG, "Unable to export", localIOException);
        return false;
      }
    }
    else
    {
      LogUtil.w(TAG, "Cannot export without write permission");
      return false;
    }
    label107:
    LogUtil.w(TAG, "ExternalStorage not mounted");
    return false;
  }
  
  private Bitmap fetch()
  {
    if (isCached()) {
      return overlay(paint(BitmapFactory.decodeFile(getPNGpath().getAbsolutePath())));
    }
    return getDummy();
  }
  
  private static File firstValid(File[] paramArrayOfFile, int paramInt)
  {
    int i = paramArrayOfFile.length;
    for (int j = 0; j < i; j++)
    {
      File localFile = paramArrayOfFile[j];
      if ((JsonUtil.isAlphabeticLayoutModel(localFile)) && (oriented(localFile, paramInt))) {
        return localFile;
      }
    }
    return null;
  }
  
  private File getClonePath()
  {
    return new File(this.mExternalStorage, this.mOutName + ".png");
  }
  
  private Bitmap getDummy()
  {
    Bitmap localBitmap = Bitmap.createBitmap(this.mWidth, this.mHeight, Bitmap.Config.RGB_565);
    new Canvas(localBitmap).drawColor(getBackground());
    return localBitmap;
  }
  
  private static File getExternalDir(Context paramContext)
  {
    File localFile = Environment.getExternalStorageDirectory();
    String str = paramContext.getString(2131296656);
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = paramContext.getPackageName();
    return new File(localFile, String.format(str, arrayOfObject));
  }
  
  private File getFreshPath()
  {
    return new File(this.mExternalStorage, this.mFreshnessFile);
  }
  
  private String getHashname(File paramFile)
  {
    String str = paramFile.getName().substring(1 + paramFile.getName().lastIndexOf("-"));
    return str.substring(0, str.indexOf('.'));
  }
  
  private static int getOrientation(File paramFile)
  {
    return 0;
  }
  
  private File getSavePath()
  {
    File localFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
    localFile.mkdirs();
    return uniquePath(localFile, this.mOutName + "-" + DateFormat.getDateInstance().format(new Date()), ".png");
  }
  
  private static boolean oriented(File paramFile, int paramInt)
  {
    if (paramInt == 0) {}
    while (getOrientation(paramFile) == paramInt) {
      return true;
    }
    return false;
  }
  
  private Bitmap overlay(Bitmap paramBitmap)
  {
    if (paramBitmap == null) {
      return null;
    }
    try
    {
      KeyData localKeyData = new KeyData(getIMpath());
      Bitmap localBitmap = localKeyData.genOverlay(20, paramBitmap.getWidth(), paramBitmap.getHeight(), this.mBackShade);
      new Canvas(paramBitmap).drawBitmap(localBitmap, new Matrix(), null);
      return paramBitmap;
    }
    catch (Exception localException)
    {
      LogUtil.e(TAG, "Unable to create overlay", localException);
    }
    return paramBitmap;
  }
  
  private Bitmap paint(Bitmap paramBitmap)
  {
    if (paramBitmap == null) {
      return null;
    }
    Bitmap localBitmap = Bitmap.createBitmap(paramBitmap.getWidth(), paramBitmap.getHeight(), Bitmap.Config.ARGB_8888);
    Canvas localCanvas = new Canvas(localBitmap);
    Paint localPaint = new Paint();
    localPaint.setFilterBitmap(false);
    localCanvas.drawColor(this.mKeyShade);
    localPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
    localCanvas.drawBitmap(paramBitmap, new Matrix(), localPaint);
    localCanvas.drawColor(this.mBackShade, PorterDuff.Mode.DST_ATOP);
    return localBitmap;
  }
  
  private static File selectModel(File paramFile, String paramString, int paramInt)
  {
    String str = Environment.getExternalStorageState();
    if (("mounted".equals(str)) || ("mounted_ro".equals(str)))
    {
      if (paramFile.exists())
      {
        File[] arrayOfFile = paramFile.listFiles(new OnlyExt(paramString));
        Arrays.sort(arrayOfFile, Collections.reverseOrder(new Comparator()
        {
          public int compare(File paramAnonymousFile1, File paramAnonymousFile2)
          {
            return Long.valueOf(paramAnonymousFile1.lastModified()).compareTo(Long.valueOf(paramAnonymousFile2.lastModified()));
          }
        }));
        return firstValid(arrayOfFile, paramInt);
      }
      LogUtil.w(TAG, "Package folder not yet created");
      return null;
    }
    LogUtil.w(TAG, "Unable to read External Storage");
    return null;
  }
  
  private File uniquePath(File paramFile, String paramString1, String paramString2)
  {
    File localFile = new File(paramFile, paramString1 + paramString2);
    if (localFile.exists()) {
      for (int i = 1; i < 2147483647; i++)
      {
        localFile = new File(paramFile, paramString1 + "(" + i + ")" + paramString2);
        if (!localFile.exists()) {
          return localFile;
        }
      }
    }
    return localFile;
  }
  
  public boolean cache(Bitmap paramBitmap)
  {
    try
    {
      FileOutputStream localFileOutputStream = new FileOutputStream(getPNGpath());
      paramBitmap.compress(Bitmap.CompressFormat.PNG, 100, localFileOutputStream);
      localFileOutputStream.close();
      JsonUtil.setFreshRec(getFreshPath(), this.mName, this.mHashCode);
      return true;
    }
    catch (JSONException localJSONException)
    {
      LogUtil.w(TAG, "Unable to update " + this.mFreshnessFile, localJSONException);
      return false;
    }
    catch (IOException localIOException)
    {
      LogUtil.w(TAG, "Unable to cache", localIOException);
    }
    return false;
  }
  
  public Bitmap fetch(ModelReceiver paramModelReceiver)
  {
    if ((!isCached()) || (!isFresh()))
    {
      if (!paramModelReceiver.hasLoader())
      {
        HeatmapAsync localHeatmapAsync = new HeatmapAsync(paramModelReceiver);
        localHeatmapAsync.execute(new ModelHandler[0]);
        paramModelReceiver.setLoader(localHeatmapAsync);
      }
      paramModelReceiver.refresh();
    }
    return fetch();
  }
  
  public int getBackground()
  {
    return this.mBackShade;
  }
  
  public File getClone()
  {
    if (exportTo(getClonePath(), true)) {
      return getClonePath();
    }
    return null;
  }
  
  public int getHeight()
  {
    return this.mHeight;
  }
  
  public File getIMpath()
  {
    return this.mPath;
  }
  
  public int getOrientation()
  {
    return this.mOrientation;
  }
  
  public File getPNGpath()
  {
    String str = this.mPath.getAbsolutePath();
    return new File(str.substring(0, str.lastIndexOf('.')) + ".png");
  }
  
  public int getWidth()
  {
    return this.mWidth;
  }
  
  public boolean isCached()
  {
    return getPNGpath().exists();
  }
  
  public boolean isFresh()
  {
    try
    {
      Integer localInteger = Integer.valueOf(JsonUtil.getFreshRec(getFreshPath(), this.mName));
      int i = localInteger.intValue();
      int j = this.mHashCode;
      boolean bool = false;
      if (i == j) {
        bool = true;
      }
      return bool;
    }
    catch (IOException localIOException)
    {
      LogUtil.w(TAG, "Unable to open freshness record", localIOException);
      return false;
    }
    catch (JSONException localJSONException) {}
    return false;
  }
  
  public boolean save()
  {
    File localFile = getSavePath();
    if (exportTo(localFile, true))
    {
      new ModelScanner(this.mContext, localFile);
      return true;
    }
    return false;
  }
  
  private final class HeatmapAsync
    extends AsyncTask<ModelHandler, Integer, Boolean>
    implements ModelMaker
  {
    private final String TAG = "HeatmapAsync";
    private ModelReceiver mActivity = null;
    private boolean mDone = false;
    private int mProgress = 0;
    
    HeatmapAsync(ModelReceiver paramModelReceiver)
    {
      attach(paramModelReceiver);
    }
    
    public void attach(ModelReceiver paramModelReceiver)
    {
      this.mActivity = paramModelReceiver;
    }
    
    public void detach()
    {
      this.mActivity = null;
    }
    
    protected Boolean doInBackground(ModelHandler... paramVarArgs)
    {
      ModelHandler localModelHandler = ModelHandler.this;
      HeatmapBuilder localHeatmapBuilder;
      try
      {
        localHeatmapBuilder = new HeatmapBuilder(localModelHandler);
        i = localHeatmapBuilder.startGen();
        j = 0;
      }
      catch (Exception localException)
      {
        try
        {
          int i;
          int j;
          Integer[] arrayOfInteger = new Integer[1];
          arrayOfInteger[0] = Integer.valueOf(localHeatmapBuilder.runGen());
          publishProgress(arrayOfInteger);
          j++;
        }
        catch (IllegalStateException localIllegalStateException)
        {
          LogUtil.e("HeatmapAsync", "Cannot continue building heatmap: " + localIllegalStateException.getMessage(), localIllegalStateException);
          return Boolean.valueOf(false);
        }
        localException = localException;
        LogUtil.e("HeatmapAsync", "Unable to create Builder: " + localException.getMessage(), localException);
        return Boolean.valueOf(false);
      }
      if (j < i)
      {
        if (!isCancelled()) {}
        return Boolean.valueOf(false);
      }
      return Boolean.valueOf(localModelHandler.cache(localHeatmapBuilder.finishGen()));
    }
    
    public int getProgress()
    {
      return this.mProgress;
    }
    
    public boolean isDone()
    {
      return this.mDone;
    }
    
    protected void onPostExecute(Boolean paramBoolean)
    {
      this.mDone = true;
      if (this.mActivity == null)
      {
        LogUtil.w("HeatmapAsync", "onPostExecute() skipped -- no activity");
        return;
      }
      this.mActivity.markAsDone(paramBoolean.booleanValue());
    }
    
    protected void onPreExecute()
    {
      this.mDone = false;
    }
    
    protected void onProgressUpdate(Integer... paramVarArgs)
    {
      if (this.mActivity == null)
      {
        LogUtil.w("HeatmapAsync", "onProgressUpdate() skipped -- no activity");
        return;
      }
      this.mActivity.updateProgress(paramVarArgs[0].intValue());
    }
    
    public void stop(boolean paramBoolean)
    {
      super.cancel(paramBoolean);
    }
  }
  
  public final class ModelScanner
    implements MediaScannerConnection.MediaScannerConnectionClient
  {
    private File mFile;
    private MediaScannerConnection mMs;
    
    public ModelScanner(Context paramContext, File paramFile)
    {
      this.mFile = paramFile;
      this.mMs = new MediaScannerConnection(paramContext, this);
      this.mMs.connect();
    }
    
    public void onMediaScannerConnected()
    {
      this.mMs.scanFile(this.mFile.getAbsolutePath(), "img/png");
    }
    
    public void onScanCompleted(String paramString, Uri paramUri)
    {
      this.mMs.disconnect();
    }
  }
  
  private static final class OnlyExt
    implements FilenameFilter
  {
    private final String ext;
    
    public OnlyExt(String paramString)
    {
      this.ext = ("." + paramString);
    }
    
    public boolean accept(File paramFile, String paramString)
    {
      return paramString.endsWith(this.ext);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.preferences.heatmap.ModelHandler
 * JD-Core Version:    0.7.0.1
 */