package com.touchtype.installer.x;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.os.AsyncTask;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.touchtype.CustomUpdaterScheduledJob;
import com.touchtype.JobScheduler;
import com.touchtype.Launcher;
import com.touchtype.preferences.TouchTypePreferences;
import com.touchtype.referral.ReferralSource;
import com.touchtype.social.UserNotificationManager;
import com.touchtype.util.LogUtil;
import com.touchtype.util.NetworkUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class RunTimeUpdater
{
  private static final String TAG = RunTimeUpdater.class.getSimpleName();
  private final JobScheduler jobScheduler;
  private boolean mAlertDialogAlreadyShown = false;
  private final Context mContext;
  private final String mCurrentVersionUrl;
  private CustomUpdaterStatus mStatus = CustomUpdaterStatus.NONE;
  private boolean mUseAlertDialog = false;
  private final boolean testing;
  private AsyncTask<Void, Void, Void> updateChecker;
  
  public RunTimeUpdater(Context paramContext)
  {
    this.mContext = paramContext;
    this.mCurrentVersionUrl = this.mContext.getString(2131296331);
    this.jobScheduler = new JobScheduler();
    this.testing = false;
  }
  
  public void cancelAndReschedule()
  {
    CustomUpdaterScheduledJob localCustomUpdaterScheduledJob = new CustomUpdaterScheduledJob();
    if (!this.mAlertDialogAlreadyShown) {
      this.jobScheduler.scheduleJob(localCustomUpdaterScheduledJob, this.mContext, true, 1000L);
    }
    while (this.updateChecker == null)
    {
      return;
      this.jobScheduler.scheduleJobDefaultInterval(localCustomUpdaterScheduledJob, this.mContext, true);
    }
    this.updateChecker.cancel(true);
  }
  
  public void checkVersionAndUpgradeIfNeeded()
  {
    if (isInstalledFromPlayStore())
    {
      this.mStatus = CustomUpdaterStatus.INSTALLED_FROM_PLAYSTORE;
      return;
    }
    if (!NetworkUtil.isReallyConnected(this.mContext))
    {
      LogUtil.w(TAG, "Network is currently unavailable!");
      CustomUpdaterScheduledJob localCustomUpdaterScheduledJob = new CustomUpdaterScheduledJob();
      this.jobScheduler.scheduleJob(localCustomUpdaterScheduledJob, this.mContext, true, 3600000L);
      this.mStatus = CustomUpdaterStatus.RESCHEDULED_SHORTLY;
      return;
    }
    this.updateChecker = new AsyncVersionChecker(null).execute(new Void[0]);
  }
  
  public boolean isInstalledFromPlayStore()
  {
    return TouchTypePreferences.getInstance(this.mContext).getBoolean("installed_from_playstore", false);
  }
  
  public void useAlertDialog()
  {
    this.mUseAlertDialog = true;
  }
  
  private final class AsyncVersionChecker
    extends AsyncTask<Void, Void, Void>
  {
    RunTimeUpdater.CurrentVersionInfo info;
    boolean shouldUpgrade = false;
    
    private AsyncVersionChecker() {}
    
    private int getPackageVersion(Context paramContext)
    {
      PackageManager localPackageManager = paramContext.getPackageManager();
      try
      {
        PackageInfo localPackageInfo2 = localPackageManager.getPackageInfo(paramContext.getPackageName(), 0);
        localPackageInfo1 = localPackageInfo2;
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException)
      {
        for (;;)
        {
          PackageInfo localPackageInfo1 = null;
        }
      }
      if (localPackageInfo1 != null) {
        return localPackageInfo1.versionCode;
      }
      return -1;
    }
    
    private boolean isNewVersionAvailable()
    {
      InputStream localInputStream = retrieveStream(RunTimeUpdater.this.mCurrentVersionUrl);
      if (localInputStream == null) {
        return false;
      }
      Gson localGson = new Gson();
      InputStreamReader localInputStreamReader = new InputStreamReader(localInputStream);
      try
      {
        this.info = ((RunTimeUpdater.CurrentVersionInfo)localGson.fromJson(localInputStreamReader, RunTimeUpdater.CurrentVersionInfo.class));
        int i = this.info.currentVersionCode;
        if (getPackageVersion(RunTimeUpdater.this.mContext) < i) {
          return true;
        }
      }
      catch (Exception localException)
      {
        LogUtil.e(RunTimeUpdater.TAG, "Error parsing JSON file: " + localException.getLocalizedMessage());
        reSchedule();
        return false;
      }
      return false;
    }
    
    private void launchUpgradeFromPlayStore()
    {
      try
      {
        Intent localIntent1 = new Intent("android.intent.action.VIEW", Launcher.getMarketUri(RunTimeUpdater.this.mContext, RunTimeUpdater.this.mContext.getPackageName(), ReferralSource.SETTINGS));
        RunTimeUpdater.this.mContext.startActivity(localIntent1);
        return;
      }
      catch (ActivityNotFoundException localActivityNotFoundException)
      {
        Intent localIntent2 = new Intent("android.intent.action.VIEW", Launcher.getWebViewUri(RunTimeUpdater.this.mContext, RunTimeUpdater.this.mContext.getPackageName(), ReferralSource.SETTINGS));
        RunTimeUpdater.this.mContext.startActivity(localIntent2);
      }
    }
    
    private void reSchedule()
    {
      CustomUpdaterScheduledJob localCustomUpdaterScheduledJob = new CustomUpdaterScheduledJob();
      RunTimeUpdater.this.jobScheduler.scheduleJobDefaultInterval(localCustomUpdaterScheduledJob, RunTimeUpdater.this.mContext, true);
      RunTimeUpdater.access$402(RunTimeUpdater.this, RunTimeUpdater.CustomUpdaterStatus.RESCHEDULED);
    }
    
    private InputStream retrieveStream(String paramString)
    {
      DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient();
      HttpGet localHttpGet = new HttpGet(paramString);
      try
      {
        HttpResponse localHttpResponse = localDefaultHttpClient.execute(localHttpGet);
        int i = localHttpResponse.getStatusLine().getStatusCode();
        if (i != 200)
        {
          LogUtil.e(RunTimeUpdater.TAG, "Error " + i + " for URL " + paramString);
          reSchedule();
          return null;
        }
        InputStream localInputStream = localHttpResponse.getEntity().getContent();
        return localInputStream;
      }
      catch (IOException localIOException)
      {
        localHttpGet.abort();
        LogUtil.e(RunTimeUpdater.TAG, "Error for URL " + paramString, localIOException);
        reSchedule();
      }
      return null;
    }
    
    private void setPkgNameUpgrade()
    {
      if ((this.info != null) && (!Strings.isNullOrEmpty(this.info.pkgnameUpgrade))) {
        TouchTypePreferences.getInstance(RunTimeUpdater.this.mContext).putString("pkgname_upgrade", this.info.pkgnameUpgrade);
      }
    }
    
    private void showUpdateAlert()
    {
      String str1 = RunTimeUpdater.this.mContext.getResources().getString(2131297275);
      String str2 = RunTimeUpdater.this.mContext.getResources().getString(2131297276);
      String str3 = RunTimeUpdater.this.mContext.getResources().getString(2131297099);
      String str4 = RunTimeUpdater.this.mContext.getResources().getString(2131297294);
      new AlertDialog.Builder(RunTimeUpdater.this.mContext).setTitle(str3).setMessage(str4).setPositiveButton(str1, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          ((Activity)RunTimeUpdater.this.mContext).finish();
          RunTimeUpdater.AsyncVersionChecker.this.launchUpgradeFromPlayStore();
        }
      }).setNegativeButton(str2, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          RunTimeUpdater.AsyncVersionChecker.this.reSchedule();
        }
      }).show();
      RunTimeUpdater.access$902(RunTimeUpdater.this, true);
    }
    
    protected Void doInBackground(Void... paramVarArgs)
    {
      this.shouldUpgrade = isNewVersionAvailable();
      setPkgNameUpgrade();
      return null;
    }
    
    protected void onPostExecute(Void paramVoid)
    {
      if (this.shouldUpgrade)
      {
        RunTimeUpdater.access$402(RunTimeUpdater.this, RunTimeUpdater.CustomUpdaterStatus.SHOULD_UPGRADE);
        if (!RunTimeUpdater.this.testing)
        {
          if (!RunTimeUpdater.this.mUseAlertDialog) {
            break label43;
          }
          showUpdateAlert();
        }
      }
      label43:
      while (RunTimeUpdater.this.mStatus == RunTimeUpdater.CustomUpdaterStatus.RESCHEDULED)
      {
        return;
        UserNotificationManager.getInstance(RunTimeUpdater.this.mContext).updatedAppAvaialble();
        return;
      }
      RunTimeUpdater.access$402(RunTimeUpdater.this, RunTimeUpdater.CustomUpdaterStatus.SHOULD_NOT_UPGRADE);
    }
  }
  
  private static class CurrentVersionInfo
  {
    @SerializedName("current-version-code")
    public int currentVersionCode;
    @SerializedName("pkgname-upgrade")
    public String pkgnameUpgrade;
  }
  
  public static enum CustomUpdaterStatus
  {
    static
    {
      INSTALLED_FROM_PLAYSTORE = new CustomUpdaterStatus("INSTALLED_FROM_PLAYSTORE", 1);
      RESCHEDULED_SHORTLY = new CustomUpdaterStatus("RESCHEDULED_SHORTLY", 2);
      RESCHEDULED = new CustomUpdaterStatus("RESCHEDULED", 3);
      SHOULD_UPGRADE = new CustomUpdaterStatus("SHOULD_UPGRADE", 4);
      SHOULD_NOT_UPGRADE = new CustomUpdaterStatus("SHOULD_NOT_UPGRADE", 5);
      CustomUpdaterStatus[] arrayOfCustomUpdaterStatus = new CustomUpdaterStatus[6];
      arrayOfCustomUpdaterStatus[0] = NONE;
      arrayOfCustomUpdaterStatus[1] = INSTALLED_FROM_PLAYSTORE;
      arrayOfCustomUpdaterStatus[2] = RESCHEDULED_SHORTLY;
      arrayOfCustomUpdaterStatus[3] = RESCHEDULED;
      arrayOfCustomUpdaterStatus[4] = SHOULD_UPGRADE;
      arrayOfCustomUpdaterStatus[5] = SHOULD_NOT_UPGRADE;
      $VALUES = arrayOfCustomUpdaterStatus;
    }
    
    private CustomUpdaterStatus() {}
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.installer.x.RunTimeUpdater
 * JD-Core Version:    0.7.0.1
 */