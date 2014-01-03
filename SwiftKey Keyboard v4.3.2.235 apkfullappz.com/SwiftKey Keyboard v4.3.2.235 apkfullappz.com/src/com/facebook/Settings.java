package com.facebook;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphObject.Factory;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONException;
import org.json.JSONObject;

public final class Settings
{
  private static final String ANALYTICS_EVENT = "event";
  private static final String ATTRIBUTION_ID_COLUMN_NAME = "aid";
  private static final Uri ATTRIBUTION_ID_CONTENT_URI;
  private static final String ATTRIBUTION_KEY = "attribution";
  private static final String ATTRIBUTION_PREFERENCES = "com.facebook.sdk.attributionTracking";
  private static final int DEFAULT_CORE_POOL_SIZE = 5;
  private static final int DEFAULT_KEEP_ALIVE = 1;
  private static final int DEFAULT_MAXIMUM_POOL_SIZE = 128;
  private static final ThreadFactory DEFAULT_THREAD_FACTORY = new ThreadFactory()
  {
    private final AtomicInteger counter = new AtomicInteger(0);
    
    public Thread newThread(Runnable paramAnonymousRunnable)
    {
      return new Thread(paramAnonymousRunnable, "FacebookSdk #" + this.counter.incrementAndGet());
    }
  };
  private static final BlockingQueue<Runnable> DEFAULT_WORK_QUEUE;
  private static final Object LOCK;
  private static final String MOBILE_INSTALL_EVENT = "MOBILE_APP_INSTALL";
  private static final String PUBLISH_ACTIVITY_PATH = "%s/activities";
  private static volatile Executor executor;
  private static final HashSet<LoggingBehavior> loggingBehaviors;
  private static volatile boolean shouldAutoPublishInstall;
  
  static
  {
    LoggingBehavior[] arrayOfLoggingBehavior = new LoggingBehavior[1];
    arrayOfLoggingBehavior[0] = LoggingBehavior.DEVELOPER_ERRORS;
    loggingBehaviors = new HashSet(Arrays.asList(arrayOfLoggingBehavior));
    LOCK = new Object();
    ATTRIBUTION_ID_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.AttributionIdProvider");
    DEFAULT_WORK_QUEUE = new LinkedBlockingQueue(10);
  }
  
  public static final void addLoggingBehavior(LoggingBehavior paramLoggingBehavior)
  {
    synchronized (loggingBehaviors)
    {
      loggingBehaviors.add(paramLoggingBehavior);
      return;
    }
  }
  
  public static final void clearLoggingBehaviors()
  {
    synchronized (loggingBehaviors)
    {
      loggingBehaviors.clear();
      return;
    }
  }
  
  private static Executor getAsyncTaskExecutor()
  {
    Field localField;
    try
    {
      localField = AsyncTask.class.getField("THREAD_POOL_EXECUTOR");
      if (localField == null) {
        return null;
      }
    }
    catch (NoSuchFieldException localNoSuchFieldException)
    {
      return null;
    }
    Object localObject;
    try
    {
      localObject = localField.get(null);
      if (localObject == null) {
        return null;
      }
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      return null;
    }
    if (!(localObject instanceof Executor)) {
      return null;
    }
    return (Executor)localObject;
  }
  
  public static String getAttributionId(ContentResolver paramContentResolver)
  {
    String[] arrayOfString = { "aid" };
    Cursor localCursor = paramContentResolver.query(ATTRIBUTION_ID_CONTENT_URI, arrayOfString, null, null, null);
    if ((localCursor == null) || (!localCursor.moveToFirst())) {
      return null;
    }
    String str = localCursor.getString(localCursor.getColumnIndex("aid"));
    localCursor.close();
    return str;
  }
  
  public static Executor getExecutor()
  {
    synchronized (LOCK)
    {
      if (executor == null)
      {
        Object localObject3 = getAsyncTaskExecutor();
        if (localObject3 == null) {
          localObject3 = new ThreadPoolExecutor(5, 128, 1L, TimeUnit.SECONDS, DEFAULT_WORK_QUEUE, DEFAULT_THREAD_FACTORY);
        }
        executor = (Executor)localObject3;
      }
      return executor;
    }
  }
  
  public static final Set<LoggingBehavior> getLoggingBehaviors()
  {
    synchronized (loggingBehaviors)
    {
      Set localSet = Collections.unmodifiableSet(new HashSet(loggingBehaviors));
      return localSet;
    }
  }
  
  public static String getMigrationBundle()
  {
    return "fbsdk:20121026";
  }
  
  public static String getSdkVersion()
  {
    return "3.0.1";
  }
  
  public static boolean getShouldAutoPublishInstall()
  {
    return shouldAutoPublishInstall;
  }
  
  public static final boolean isLoggingBehaviorEnabled(LoggingBehavior paramLoggingBehavior)
  {
    HashSet localHashSet = loggingBehaviors;
    return false;
  }
  
  public static boolean publishInstallAndWait(Context paramContext, String paramString)
  {
    Response localResponse = publishInstallAndWaitForResponse(paramContext, paramString);
    return (localResponse != null) && (localResponse.getError() == null);
  }
  
  public static Response publishInstallAndWaitForResponse(Context paramContext, String paramString)
  {
    if ((paramContext == null) || (paramString == null)) {
      try
      {
        throw new IllegalArgumentException("Both context and applicationId must be non-null");
      }
      catch (Exception localException)
      {
        Utility.logd("Facebook-publish", localException);
        FacebookRequestError localFacebookRequestError = new FacebookRequestError(null, localException);
        return new Response(null, null, localFacebookRequestError);
      }
    }
    String str1 = getAttributionId(paramContext.getContentResolver());
    SharedPreferences localSharedPreferences = paramContext.getSharedPreferences("com.facebook.sdk.attributionTracking", 0);
    String str2 = paramString + "ping";
    String str3 = paramString + "json";
    long l = localSharedPreferences.getLong(str2, 0L);
    String str4 = localSharedPreferences.getString(str3, null);
    GraphObject localGraphObject1 = GraphObject.Factory.create();
    localGraphObject1.setProperty("event", "MOBILE_APP_INSTALL");
    localGraphObject1.setProperty("attribution", str1);
    Request localRequest = Request.newPostRequest(null, String.format("%s/activities", new Object[] { paramString }), localGraphObject1, null);
    if (l != 0L)
    {
      localObject = null;
      if (str4 == null) {}
    }
    try
    {
      JSONObject localJSONObject = new JSONObject(str4);
      GraphObject localGraphObject2 = GraphObject.Factory.create(localJSONObject);
      localObject = localGraphObject2;
    }
    catch (JSONException localJSONException)
    {
      for (;;)
      {
        Response localResponse1;
        Response localResponse2;
        SharedPreferences.Editor localEditor;
        localObject = null;
      }
    }
    if (localObject == null) {
      return (Response)Response.createResponsesFromString("true", null, new RequestBatch(new Request[] { localRequest }), true).get(0);
    }
    localResponse1 = new Response(null, null, localObject, true);
    return localResponse1;
    if (str1 == null) {
      throw new FacebookException("No attribution id returned from the Facebook application");
    }
    if (!Utility.queryAppAttributionSupportAndWait(paramString)) {
      throw new FacebookException("Install attribution has been disabled on the server.");
    }
    localResponse2 = localRequest.executeAndWait();
    localEditor = localSharedPreferences.edit();
    localEditor.putLong(str2, System.currentTimeMillis());
    if ((localResponse2.getGraphObject() != null) && (localResponse2.getGraphObject().getInnerJSONObject() != null)) {
      localEditor.putString(str3, localResponse2.getGraphObject().getInnerJSONObject().toString());
    }
    localEditor.commit();
    return localResponse2;
  }
  
  public static void publishInstallAsync(Context paramContext, String paramString)
  {
    publishInstallAsync(paramContext, paramString, null);
  }
  
  public static void publishInstallAsync(Context paramContext, final String paramString, final Request.Callback paramCallback)
  {
    Context localContext = paramContext.getApplicationContext();
    getExecutor().execute(new Runnable()
    {
      public void run()
      {
        final Response localResponse = Settings.publishInstallAndWaitForResponse(this.val$applicationContext, paramString);
        if (paramCallback != null) {
          new Handler(Looper.getMainLooper()).post(new Runnable()
          {
            public void run()
            {
              Settings.2.this.val$callback.onCompleted(localResponse);
            }
          });
        }
      }
    });
  }
  
  public static final void removeLoggingBehavior(LoggingBehavior paramLoggingBehavior)
  {
    synchronized (loggingBehaviors)
    {
      loggingBehaviors.remove(paramLoggingBehavior);
      return;
    }
  }
  
  public static void setExecutor(Executor paramExecutor)
  {
    Validate.notNull(paramExecutor, "executor");
    synchronized (LOCK)
    {
      executor = paramExecutor;
      return;
    }
  }
  
  public static void setShouldAutoPublishInstall(boolean paramBoolean)
  {
    shouldAutoPublishInstall = paramBoolean;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.facebook.Settings
 * JD-Core Version:    0.7.0.1
 */