package com.touchtype.report;

import android.content.Context;
import android.content.Intent;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.touchtype.common.iris.IrisDataSenderService;
import com.touchtype.report.json.ForceClose;
import com.touchtype.util.LogUtil;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public final class ForceCloseMonitor
  implements Thread.UncaughtExceptionHandler
{
  private static final String TAG = ForceCloseMonitor.class.getSimpleName();
  private final Context context;
  private final File errorFile;
  private final Thread.UncaughtExceptionHandler originalExceptionHandler;
  private final String url;
  
  protected ForceCloseMonitor(Thread.UncaughtExceptionHandler paramUncaughtExceptionHandler, Context paramContext, String paramString)
  {
    this.context = paramContext;
    this.originalExceptionHandler = paramUncaughtExceptionHandler;
    this.url = paramString;
    this.errorFile = new File(paramContext.getCacheDir(), "cached_errors");
  }
  
  public static void setUp(Context paramContext, String paramString)
  {
    Thread.UncaughtExceptionHandler localUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
    ForceCloseMonitor localForceCloseMonitor;
    if (!(localUncaughtExceptionHandler instanceof ForceCloseMonitor))
    {
      localForceCloseMonitor = new ForceCloseMonitor(localUncaughtExceptionHandler, paramContext, paramString);
      Thread.setDefaultUncaughtExceptionHandler(localForceCloseMonitor);
    }
    for (;;)
    {
      localForceCloseMonitor.reportCachedError();
      return;
      localForceCloseMonitor = (ForceCloseMonitor)localUncaughtExceptionHandler;
    }
  }
  
  public void reportCachedError()
  {
    if (!this.errorFile.exists()) {
      return;
    }
    try
    {
      String str = Files.toString(this.errorFile, Charsets.UTF_8);
      Intent localIntent = IrisDataSenderService.sendDataIntent(this.context, this.url, str);
      this.context.startService(localIntent);
      return;
    }
    catch (IOException localIOException)
    {
      LogUtil.e(TAG, localIOException.getMessage(), localIOException);
      return;
    }
    finally
    {
      this.errorFile.delete();
    }
  }
  
  public void uncaughtException(Thread paramThread, Throwable paramThrowable)
  {
    StringWriter localStringWriter = new StringWriter();
    paramThrowable.printStackTrace(new PrintWriter(localStringWriter));
    String str = localStringWriter.toString();
    ForceClose localForceClose = ForceClose.newInstance(this.context, str);
    try
    {
      Files.write(new Gson().toJson(localForceClose, ForceClose.class), this.errorFile, Charsets.UTF_8);
      this.originalExceptionHandler.uncaughtException(paramThread, paramThrowable);
      return;
    }
    catch (IOException localIOException)
    {
      for (;;)
      {
        LogUtil.e(TAG, localIOException.getMessage(), localIOException);
        this.errorFile.delete();
      }
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.report.ForceCloseMonitor
 * JD-Core Version:    0.7.0.1
 */