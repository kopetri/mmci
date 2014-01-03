package com.touchtype.cloud;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.touchtype.sync.client.RequestListener;
import com.touchtype.ui.TaskProgressDialogFragment;
import com.touchtype.ui.TaskProgressDialogFragment.ProgressDialogTaskWrapper;
import com.touchtype.ui.TaskProgressDialogFragment.TaskProgressDialogCallback;
import com.touchtype.util.LogUtil;
import java.io.IOException;

final class CloudSetupTasks
{
  public static CloudRequestProgressDialogFragment createProgressDialogFragment(RequestListener paramRequestListener, int paramInt)
  {
    CloudRequestProgressDialogFragment localCloudRequestProgressDialogFragment = new CloudRequestProgressDialogFragment();
    localCloudRequestProgressDialogFragment.setExternalListener(paramRequestListener);
    localCloudRequestProgressDialogFragment.setProgressMessageResId(paramInt);
    localCloudRequestProgressDialogFragment.setCancelable(false);
    return localCloudRequestProgressDialogFragment;
  }
  
  public static String[] getGoogleAccounts(Context paramContext)
  {
    Account[] arrayOfAccount = AccountManager.get(paramContext).getAccountsByType("com.google");
    String[] arrayOfString = new String[arrayOfAccount.length];
    for (int i = 0; i < arrayOfAccount.length; i++) {
      arrayOfString[i] = arrayOfAccount[i].name;
    }
    return arrayOfString;
  }
  
  public static TaskProgressDialogFragment<String, Void, GetGoogleAccessTokenResult> newGetGoogleAccessTokenTaskFragment(Context paramContext, String paramString, TaskProgressDialogFragment.TaskProgressDialogCallback<GetGoogleAccessTokenResult> paramTaskProgressDialogCallback)
  {
    TaskProgressDialogFragment localTaskProgressDialogFragment = new TaskProgressDialogFragment();
    localTaskProgressDialogFragment.setTask(new GetGoogleAccessTokenTask(paramContext, null), new String[] { paramString });
    localTaskProgressDialogFragment.setCallback(paramTaskProgressDialogCallback);
    localTaskProgressDialogFragment.setProgressMessageResId(2131297269);
    localTaskProgressDialogFragment.setCancelable(false);
    return localTaskProgressDialogFragment;
  }
  
  public static final class GetGoogleAccessTokenResult
  {
    public boolean shouldRetryIfTokenMissing;
    public String token;
  }
  
  private static final class GetGoogleAccessTokenTask
    extends TaskProgressDialogFragment.ProgressDialogTaskWrapper<String, Void, CloudSetupTasks.GetGoogleAccessTokenResult>
  {
    private Context context;
    
    private GetGoogleAccessTokenTask(Context paramContext)
    {
      this.context = paramContext;
    }
    
    protected CloudSetupTasks.GetGoogleAccessTokenResult doBackgroundWork(String... paramVarArgs)
    {
      String str = paramVarArgs[0];
      CloudSetupTasks.GetGoogleAccessTokenResult localGetGoogleAccessTokenResult = new CloudSetupTasks.GetGoogleAccessTokenResult();
      try
      {
        localGetGoogleAccessTokenResult.token = GoogleAuthUtil.getToken(this.context, str, CloudUtils.getGoogleAuthScope(this.context));
        return localGetGoogleAccessTokenResult;
      }
      catch (GoogleAuthException localGoogleAuthException)
      {
        LogUtil.w("GetGoogleAccessTokenTask", "GoogleAuthException retrieving Google access token: " + localGoogleAuthException.getMessage());
        localGetGoogleAccessTokenResult.shouldRetryIfTokenMissing = false;
        return localGetGoogleAccessTokenResult;
      }
      catch (IOException localIOException)
      {
        LogUtil.e("GetGoogleAccessTokenTask", "IOException retrieving Google access token: " + localIOException.getMessage());
        localGetGoogleAccessTokenResult.shouldRetryIfTokenMissing = true;
      }
      return localGetGoogleAccessTokenResult;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.cloud.CloudSetupTasks
 * JD-Core Version:    0.7.0.1
 */