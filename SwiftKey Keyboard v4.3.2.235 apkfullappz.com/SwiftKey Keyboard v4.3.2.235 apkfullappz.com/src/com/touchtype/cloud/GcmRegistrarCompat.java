package com.touchtype.cloud;

import android.content.Context;
import android.os.AsyncTask;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.touchtype.preferences.TouchTypePreferences;
import com.touchtype.util.LogUtil;
import java.io.IOException;

public class GcmRegistrarCompat
{
  private static final String TAG = GcmRegistrarCompat.class.getSimpleName();
  
  private GcmRegistrarCompat()
  {
    throw new UnsupportedOperationException();
  }
  
  public static boolean checkPlayServices(Context paramContext)
  {
    if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(paramContext) != 0) {
      throw new IllegalStateException("PlayServices NOT available");
    }
    return true;
  }
  
  public static String clearRegistrationId(Context paramContext)
  {
    return setRegistrationId(paramContext, "");
  }
  
  public static String getRegistrationId(Context paramContext)
  {
    TouchTypePreferences localTouchTypePreferences = TouchTypePreferences.getInstance(paramContext);
    String str = localTouchTypePreferences.getString("gcm_reg_id", "");
    int i = localTouchTypePreferences.getInt("gcm_stored_app_version", -2147483648);
    int j = localTouchTypePreferences.getInt("stored_app_version", -2147483648);
    if ((i != -2147483648) && (i != j))
    {
      clearRegistrationId(paramContext);
      str = "";
    }
    while (!isRegistrationExpired(paramContext)) {
      return str;
    }
    clearRegistrationId(paramContext);
    return "";
  }
  
  private static boolean isRegistrationExpired(Context paramContext)
  {
    long l = TouchTypePreferences.getInstance(paramContext).getLong("gcm_on_server_expiration_time", -1L);
    return System.currentTimeMillis() > l;
  }
  
  private static String setRegistrationId(Context paramContext, String paramString)
  {
    TouchTypePreferences localTouchTypePreferences = TouchTypePreferences.getInstance(paramContext);
    String str = localTouchTypePreferences.getString("gcm_reg_id", "");
    long l = 604800000L + System.currentTimeMillis();
    localTouchTypePreferences.putString("gcm_reg_id", paramString);
    localTouchTypePreferences.putLong("gcm_on_server_expiration_time", l);
    return str;
  }
  
  public static class BaseRegisterTask
    extends AsyncTask<String, Void, String>
  {
    protected final Context context;
    
    BaseRegisterTask(Context paramContext)
    {
      this.context = paramContext;
    }
    
    protected String doInBackground(String... paramVarArgs)
    {
      GoogleCloudMessaging localGoogleCloudMessaging = GoogleCloudMessaging.getInstance(this.context);
      String str = null;
      try
      {
        String[] arrayOfString = new String[1];
        arrayOfString[0] = paramVarArgs[0];
        str = localGoogleCloudMessaging.register(arrayOfString);
        GcmRegistrarCompat.setRegistrationId(this.context, str);
        sendRegistrationIdToServer(str);
        return str;
      }
      catch (IOException localIOException)
      {
        LogUtil.e(GcmRegistrarCompat.TAG, "GCM registration problem: " + localIOException.getLocalizedMessage());
        localIOException.printStackTrace();
      }
      return str;
    }
    
    protected void sendRegistrationIdToServer(String paramString) {}
  }
  
  public static final class BaseUnRegisterTask
    extends AsyncTask<String, Void, Void>
  {
    protected final Context context;
    
    BaseUnRegisterTask(Context paramContext)
    {
      this.context = paramContext;
    }
    
    protected Void doInBackground(String... paramVarArgs)
    {
      GoogleCloudMessaging localGoogleCloudMessaging = GoogleCloudMessaging.getInstance(this.context);
      try
      {
        localGoogleCloudMessaging.unregister();
        GcmRegistrarCompat.setRegistrationId(this.context, "");
        return null;
      }
      catch (IOException localIOException)
      {
        for (;;)
        {
          LogUtil.e(GcmRegistrarCompat.TAG, "GCM un-registration problem: " + localIOException.getLocalizedMessage());
          localIOException.printStackTrace();
        }
      }
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.cloud.GcmRegistrarCompat
 * JD-Core Version:    0.7.0.1
 */