package com.touchtype_fluency.service.personalize.auth;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Pair;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.GooglePlayServicesAvailabilityException;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.touchtype.util.LogUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class GooglePlayTokenRetriever
  extends TokenRetriever
{
  private static final int ACCOUNT_OTHER = 2131297138;
  private static final String REQUEST_RECOVER_ACTIVITY = "recover activity launched";
  private static final String SCOPE = "oauth2:https://mail.google.com https://www.googleapis.com/auth/userinfo.email";
  private static final String TAG = GooglePlayTokenRetriever.class.getSimpleName();
  private String mCurrentAccountSelected;
  
  public GooglePlayTokenRetriever(Activity paramActivity, TokenRetriever.TokenRetrieverListener paramTokenRetrieverListener)
  {
    super(paramActivity, paramTokenRetrieverListener);
  }
  
  private String[] getAccountNames(List<String> paramList)
  {
    Account[] arrayOfAccount = AccountManager.get(this.mCallerActivity).getAccountsByType("com.google");
    ArrayList localArrayList = new ArrayList();
    for (int i = 0; i < arrayOfAccount.length; i++) {
      if ((paramList != null) && (!paramList.contains(arrayOfAccount[i].name))) {
        localArrayList.add(arrayOfAccount[i].name);
      }
    }
    localArrayList.add(this.mCallerActivity.getResources().getString(2131297138));
    return (String[])localArrayList.toArray(new String[localArrayList.size()]);
  }
  
  private boolean isRegisteredOnDevice(String paramString)
  {
    Account[] arrayOfAccount = AccountManager.get(this.mCallerActivity).getAccountsByType("com.google");
    for (int i = 0; i < arrayOfAccount.length; i++) {
      if (arrayOfAccount[i].name.equals(paramString)) {
        return true;
      }
    }
    return false;
  }
  
  private void retrieveAccessToken()
  {
    String str = this.mCallerActivity.getResources().getString(2131297138);
    if (this.mCurrentAccountSelected.equals(str))
    {
      this.mListener.onAuthenticationRequired();
      return;
    }
    AsyncTask local3 = new AsyncTask()
    {
      protected Pair<String, String> doInBackground(String... paramAnonymousVarArgs)
      {
        String str1 = paramAnonymousVarArgs[0];
        try
        {
          String str3 = GoogleAuthUtil.getToken(GooglePlayTokenRetriever.this.mCallerActivity, str1, "oauth2:https://mail.google.com https://www.googleapis.com/auth/userinfo.email");
          str2 = str3;
        }
        catch (GooglePlayServicesAvailabilityException localGooglePlayServicesAvailabilityException)
        {
          for (;;)
          {
            LogUtil.e(GooglePlayTokenRetriever.TAG, localGooglePlayServicesAvailabilityException.getMessage(), localGooglePlayServicesAvailabilityException);
            str2 = null;
          }
        }
        catch (UserRecoverableAuthException localUserRecoverableAuthException)
        {
          try
          {
            GooglePlayTokenRetriever.this.mCallerActivity.startActivityForResult(localUserRecoverableAuthException.getIntent(), 1001);
            str2 = "recover activity launched";
          }
          catch (NullPointerException localNullPointerException)
          {
            for (;;)
            {
              LogUtil.w(GooglePlayTokenRetriever.TAG, "UserRecoverableAuthException without intent, requesting web authentication");
              GooglePlayTokenRetriever.this.mListener.onAuthenticationRequired();
            }
          }
        }
        catch (IOException localIOException)
        {
          for (;;)
          {
            LogUtil.e(GooglePlayTokenRetriever.TAG, localIOException.getMessage(), localIOException);
            str2 = null;
          }
        }
        catch (GoogleAuthException localGoogleAuthException)
        {
          for (;;)
          {
            LogUtil.e(GooglePlayTokenRetriever.TAG, "Unrecoverable error in google auth: " + localGoogleAuthException.getMessage(), localGoogleAuthException);
            str2 = null;
          }
        }
        catch (RuntimeException localRuntimeException)
        {
          for (;;)
          {
            LogUtil.e(GooglePlayTokenRetriever.TAG, localRuntimeException.getMessage(), localRuntimeException);
            String str2 = null;
          }
        }
        return new Pair(str1, str2);
      }
      
      protected void onPostExecute(Pair<String, String> paramAnonymousPair)
      {
        String str1 = (String)paramAnonymousPair.first;
        String str2 = (String)paramAnonymousPair.second;
        if (str2 == null) {
          GooglePlayTokenRetriever.this.mListener.onError("Access Token Retrieving failed");
        }
        while (str2.equals("recover activity launched")) {
          return;
        }
        GooglePlayTokenRetriever.this.mListener.onTokenRetrieved(str2, str1);
      }
    };
    String[] arrayOfString = new String[1];
    arrayOfString[0] = this.mCurrentAccountSelected;
    local3.execute(arrayOfString);
  }
  
  public void refreshCredentials(String paramString)
  {
    if (isRegisteredOnDevice(paramString))
    {
      this.mCurrentAccountSelected = paramString;
      startTokenRetrieving();
      return;
    }
    this.mListener.onAuthenticationRequired();
  }
  
  public void startTokenRetrieving()
  {
    startTokenRetrieving(null);
  }
  
  public void startTokenRetrieving(List<String> paramList)
  {
    if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(this.mCallerActivity) != 0)
    {
      this.mListener.onAuthenticationRequired();
      return;
    }
    if (this.mCurrentAccountSelected == null)
    {
      final String[] arrayOfString = getAccountNames(paramList);
      new AlertDialog.Builder(this.mCallerActivity).setTitle("Pick an Account").setItems(arrayOfString, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          GooglePlayTokenRetriever.access$002(GooglePlayTokenRetriever.this, arrayOfString[paramAnonymousInt]);
          GooglePlayTokenRetriever.this.retrieveAccessToken();
          paramAnonymousDialogInterface.dismiss();
        }
      }).setOnCancelListener(new DialogInterface.OnCancelListener()
      {
        public void onCancel(DialogInterface paramAnonymousDialogInterface)
        {
          GooglePlayTokenRetriever.this.mListener.onCancel();
        }
      }).create().show();
      return;
    }
    retrieveAccessToken();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.personalize.auth.GooglePlayTokenRetriever
 * JD-Core Version:    0.7.0.1
 */