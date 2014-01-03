package com.touchtype.common.iris;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Pair;
import android.webkit.URLUtil;
import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.common.math.LongMath;
import com.google.common.primitives.Ints;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.touchtype.common.iris.json.Header;
import com.touchtype.common.iris.json.IrisErrorResponse;
import com.touchtype.common.util.FileUtils;
import com.touchtype.util.LogUtil;
import com.touchtype_fluency.service.http.SSLClientFactory;
import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

public class IrisDataSenderService
  extends IntentService
{
  private static final Pair<String, String> CONTENT_TYPE = new Pair("content-type", "application/json");
  private static final String TAG = IrisDataSenderService.class.getSimpleName();
  
  public IrisDataSenderService()
  {
    super(IrisDataSenderService.class.getSimpleName());
  }
  
  private String attachIrisHeader(String paramString)
  {
    return String.format("{\"header\":%s,\"body\":%s}", new Object[] { new Gson().toJson(Header.newInstance(getApplicationContext()), Header.class), paramString });
  }
  
  protected static long calculateNextTrigger(long paramLong, SendInterval paramSendInterval)
  {
    if (!paramSendInterval.requiresScheduler()) {}
    long l;
    do
    {
      return paramLong;
      l = System.currentTimeMillis();
    } while (l <= paramLong);
    return paramLong + paramSendInterval.getTimedelta() * LongMath.divide(l - paramLong, paramSendInterval.getTimedelta(), RoundingMode.CEILING);
  }
  
  private void firstTimeConfiguration(SharedPreferences paramSharedPreferences)
  {
    if (paramSharedPreferences.contains("first_run")) {
      return;
    }
    long l = System.currentTimeMillis();
    Random localRandom = new Random(l);
    SharedPreferences.Editor localEditor = paramSharedPreferences.edit();
    for (SendInterval localSendInterval : SendInterval.values()) {
      localEditor.putLong(localSendInterval.getFilename(), l + localRandom.nextInt(Ints.saturatedCast(1L + localSendInterval.getTimedelta())));
    }
    localEditor.putBoolean("first_run", false);
    localEditor.commit();
  }
  
  private boolean isNewMessageIntent(Intent paramIntent)
  {
    return (paramIntent.hasExtra("message")) && (paramIntent.hasExtra("url"));
  }
  
  protected static Map<String, List<String>> loadMapFromFile(File paramFile)
  {
    if (paramFile.isDirectory()) {}
    for (;;)
    {
      Object localObject;
      try
      {
        FileUtils.deleteRecursively(paramFile);
        if (!paramFile.exists())
        {
          localObject = Maps.newHashMap();
          return localObject;
        }
      }
      catch (IOException localIOException2)
      {
        LogUtil.e(TAG, localIOException2.getMessage(), localIOException2);
        continue;
      }
      try
      {
        String str = Files.toString(paramFile, Charsets.UTF_8);
        localObject = (Map)new Gson().fromJson(str, new TypeToken() {}.getType());
        if (localObject != null) {
          continue;
        }
        HashMap localHashMap = Maps.newHashMap();
        return localHashMap;
      }
      catch (JsonSyntaxException localJsonSyntaxException)
      {
        LogUtil.e(TAG, localJsonSyntaxException.getMessage(), localJsonSyntaxException);
        return Maps.newHashMap();
      }
      catch (ClassCastException localClassCastException)
      {
        for (;;)
        {
          LogUtil.e(TAG, localClassCastException.getMessage(), localClassCastException);
        }
      }
      catch (IOException localIOException1)
      {
        for (;;)
        {
          LogUtil.e(TAG, localIOException1.getMessage(), localIOException1);
        }
      }
    }
  }
  
  private void processNewMessageIntent(Intent paramIntent, File paramFile)
  {
    if (!isNewMessageIntent(paramIntent)) {}
    String str1;
    String str2;
    SendInterval localSendInterval;
    do
    {
      return;
      str1 = paramIntent.getStringExtra("url");
      str2 = attachIrisHeader(paramIntent.getStringExtra("message"));
      localSendInterval = SendInterval.stringToSendInterval(paramIntent.getStringExtra("send_interval"));
    } while (!URLUtil.isValidUrl(str1));
    File localFile = new File(paramFile, localSendInterval.getFilename());
    Map localMap = loadMapFromFile(localFile);
    if (localMap.containsKey(str1)) {
      ((List)localMap.get(str1)).add(str2);
    }
    for (;;)
    {
      saveCachedData(localMap, localFile);
      return;
      localMap.put(str1, Lists.newArrayList(new String[] { str2 }));
    }
  }
  
  static Intent retryCachedDataIntent(Context paramContext)
  {
    return new Intent(paramContext, IrisDataSenderService.class);
  }
  
  protected static void saveCachedData(Map<String, List<String>> paramMap, File paramFile)
  {
    if (paramMap.isEmpty())
    {
      paramFile.delete();
      return;
    }
    String str = new Gson().toJson(paramMap, paramMap.getClass());
    try
    {
      Files.write(str, paramFile, Charsets.UTF_8);
      return;
    }
    catch (IOException localIOException)
    {
      LogUtil.e(TAG, localIOException.getMessage(), localIOException);
    }
  }
  
  private Map<String, List<String>> sendCachedData(Map<String, List<String>> paramMap)
  {
    HashMap localHashMap = Maps.newHashMap();
    Iterator localIterator1 = paramMap.keySet().iterator();
    if (localIterator1.hasNext())
    {
      String str1 = (String)localIterator1.next();
      Iterator localIterator2 = ((List)paramMap.get(str1)).iterator();
      while (localIterator2.hasNext())
      {
        String str2 = (String)localIterator2.next();
        try
        {
          sendData(str1, str2);
        }
        catch (IOException localIOException)
        {
          LogUtil.e(TAG, localIOException.getMessage(), localIOException);
          localHashMap.put(str1, Lists.newArrayList(new String[] { str2 }));
        }
        while (localIterator2.hasNext())
        {
          String str3 = (String)localIterator2.next();
          ((List)localHashMap.get(str1)).add(str3);
        }
      }
    }
    return localHashMap;
  }
  
  private void sendData(String paramString1, String paramString2)
    throws IOException
  {
    HttpClient localHttpClient = SSLClientFactory.createHttpClient(null);
    HttpPost localHttpPost = new HttpPost(paramString1);
    localHttpPost.addHeader((String)CONTENT_TYPE.first, (String)CONTENT_TYPE.second);
    localHttpPost.setEntity(new StringEntity(paramString2, "UTF-8"));
    HttpResponse localHttpResponse = localHttpClient.execute(localHttpPost);
    if (IrisStatus.statusCodeToIrisStatus(localHttpResponse.getStatusLine().getStatusCode()).shouldReschedule())
    {
      IrisErrorResponse localIrisErrorResponse = IrisErrorResponse.createFromJson(EntityUtils.toString(localHttpResponse.getEntity()));
      setAlarm(System.currentTimeMillis() + localIrisErrorResponse.getSuggestedRetry());
      throw new IOException("Server busy.");
    }
  }
  
  public static Class<IrisDataSenderService> sendDataClass()
  {
    return IrisDataSenderService.class;
  }
  
  @Deprecated
  public static Intent sendDataIntent(Context paramContext, String paramString1, String paramString2)
  {
    return sendDataIntent(paramContext, paramString1, paramString2, SendInterval.NOW);
  }
  
  public static Intent sendDataIntent(Context paramContext, String paramString1, String paramString2, SendInterval paramSendInterval)
  {
    Intent localIntent = new Intent(paramContext, IrisDataSenderService.class);
    localIntent.putExtra("message", paramString2);
    localIntent.putExtra("url", paramString1);
    localIntent.putExtra("send_interval", paramSendInterval.name());
    return localIntent;
  }
  
  private void setAlarm(long paramLong)
  {
    Context localContext = getApplicationContext();
    ((AlarmManager)localContext.getSystemService("alarm")).set(1, paramLong, PendingIntent.getBroadcast(localContext, 0, AlarmReceiver.alarmIntent(localContext), 134217728));
  }
  
  protected boolean isPendingAlarm(Context paramContext)
  {
    PendingIntent localPendingIntent = PendingIntent.getBroadcast(paramContext, 0, AlarmReceiver.alarmIntent(paramContext), 536870912);
    boolean bool = false;
    if (localPendingIntent != null) {
      bool = true;
    }
    return bool;
  }
  
  protected void onHandleIntent(Intent paramIntent)
  {
    Context localContext = getApplicationContext();
    File localFile1 = localContext.getFilesDir();
    SharedPreferences localSharedPreferences = localContext.getSharedPreferences("iris_data_sender", 0);
    firstTimeConfiguration(localSharedPreferences);
    processNewMessageIntent(paramIntent, localFile1);
    long l1 = 9223372036854775807L;
    long l2 = System.currentTimeMillis();
    for (SendInterval localSendInterval : SendInterval.values())
    {
      String str = localSendInterval.getFilename();
      long l3 = localSharedPreferences.getLong(str, 0L);
      if (l2 >= l3)
      {
        File localFile2 = new File(localFile1, localSendInterval.getFilename());
        Map localMap = sendCachedData(loadMapFromFile(localFile2));
        saveCachedData(localMap, localFile2);
        if ((localMap.isEmpty()) && (localSendInterval.requiresScheduler())) {
          localSharedPreferences.edit().putLong(str, calculateNextTrigger(l3, localSendInterval)).commit();
        }
      }
      if (localSendInterval.requiresScheduler()) {
        l1 = Math.min(l1, localSharedPreferences.getLong(str, 0L));
      }
    }
    if ((!isPendingAlarm(localContext)) && (l1 != 9223372036854775807L) && (l1 > l2)) {
      setAlarm(l1);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.common.iris.IrisDataSenderService
 * JD-Core Version:    0.7.0.1
 */