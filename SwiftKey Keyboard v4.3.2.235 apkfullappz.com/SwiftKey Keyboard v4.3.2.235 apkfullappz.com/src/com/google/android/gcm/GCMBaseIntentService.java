package com.google.android.gcm;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public abstract class GCMBaseIntentService
  extends IntentService
{
  private static final Object LOCK = GCMBaseIntentService.class;
  private static final int MAX_BACKOFF_MS = (int)TimeUnit.SECONDS.toMillis(3600L);
  private static final String TOKEN = Long.toBinaryString(sRandom.nextLong());
  private static int sCounter = 0;
  private static final Random sRandom = new Random();
  private static PowerManager.WakeLock sWakeLock;
  private final String[] mSenderIds;
  
  protected GCMBaseIntentService()
  {
    this(getName("DynamicSenderIds"), null);
  }
  
  private GCMBaseIntentService(String paramString, String[] paramArrayOfString)
  {
    super(paramString);
    this.mSenderIds = paramArrayOfString;
  }
  
  private static String getName(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder("GCMIntentService-").append(paramString).append("-");
    int i = 1 + sCounter;
    sCounter = i;
    String str = i;
    new StringBuilder("Intent service name: ").append(str).toString();
    return str;
  }
  
  static void runIntentInService(Context paramContext, Intent paramIntent, String paramString)
  {
    synchronized (LOCK)
    {
      if (sWakeLock == null) {
        sWakeLock = ((PowerManager)paramContext.getSystemService("power")).newWakeLock(1, "GCM_LIB");
      }
      sWakeLock.acquire();
      paramIntent.setClassName(paramContext, paramString);
      paramContext.startService(paramIntent);
      return;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gcm.GCMBaseIntentService
 * JD-Core Version:    0.7.0.1
 */