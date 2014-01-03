package com.google.android.gms.auth;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesUtil;
import java.io.IOException;

public final class GoogleAuthUtil
{
  private static final ComponentName w = new ComponentName("com.google.android.gms", "com.google.android.gms.auth.GetToken");
  private static final ComponentName x = new ComponentName("com.google.android.gms", "com.google.android.gms.recovery.RecoveryService");
  private static final Intent y = new Intent().setComponent(w);
  private static final Intent z = new Intent().setComponent(x);
  
  private static void a(Context paramContext)
    throws GooglePlayServicesAvailabilityException, GoogleAuthException
  {
    int i = GooglePlayServicesUtil.isGooglePlayServicesAvailable(paramContext);
    if (i != 0)
    {
      Intent localIntent = GooglePlayServicesUtil.a(paramContext, i, -1);
      String str = "GooglePlayServices not available due to error " + i;
      Log.e("GoogleAuthUtil", str);
      if (localIntent == null) {
        throw new GoogleAuthException(str);
      }
      throw new GooglePlayServicesAvailabilityException(i, "GooglePlayServicesNotAvailable", localIntent);
    }
  }
  
  private static boolean a(String paramString)
  {
    return ("NetworkError".equals(paramString)) || ("ServiceUnavailable".equals(paramString)) || ("Timeout".equals(paramString));
  }
  
  private static void b(Context paramContext)
  {
    Looper localLooper = Looper.myLooper();
    if ((localLooper != null) && (localLooper == paramContext.getMainLooper()))
    {
      IllegalStateException localIllegalStateException = new IllegalStateException("calling this from your main thread can lead to deadlock");
      Log.e("GoogleAuthUtil", "Calling this from your main thread can lead to deadlock and/or ANRs", localIllegalStateException);
      throw localIllegalStateException;
    }
  }
  
  private static boolean b(String paramString)
  {
    return ("BadAuthentication".equals(paramString)) || ("CaptchaRequired".equals(paramString)) || ("DeviceManagementRequiredOrSyncDisabled".equals(paramString)) || ("NeedPermission".equals(paramString)) || ("NeedsBrowser".equals(paramString)) || ("UserCancel".equals(paramString)) || ("AppDownloadRequired".equals(paramString));
  }
  
  public static String getToken(Context paramContext, String paramString1, String paramString2)
    throws IOException, UserRecoverableAuthException, GoogleAuthException
  {
    return getToken(paramContext, paramString1, paramString2, new Bundle());
  }
  
  /* Error */
  public static String getToken(Context paramContext, String paramString1, String paramString2, Bundle paramBundle)
    throws IOException, UserRecoverableAuthException, GoogleAuthException
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 153	android/content/Context:getApplicationContext	()Landroid/content/Context;
    //   4: astore 4
    //   6: aload 4
    //   8: invokestatic 155	com/google/android/gms/auth/GoogleAuthUtil:b	(Landroid/content/Context;)V
    //   11: aload 4
    //   13: invokestatic 157	com/google/android/gms/auth/GoogleAuthUtil:a	(Landroid/content/Context;)V
    //   16: aload_3
    //   17: ifnonnull +104 -> 121
    //   20: new 141	android/os/Bundle
    //   23: dup
    //   24: invokespecial 142	android/os/Bundle:<init>	()V
    //   27: astore 5
    //   29: aload 5
    //   31: ldc 159
    //   33: invokevirtual 162	android/os/Bundle:containsKey	(Ljava/lang/String;)Z
    //   36: ifne +14 -> 50
    //   39: aload 5
    //   41: ldc 159
    //   43: aload_0
    //   44: invokevirtual 165	android/content/Context:getPackageName	()Ljava/lang/String;
    //   47: invokevirtual 168	android/os/Bundle:putString	(Ljava/lang/String;Ljava/lang/String;)V
    //   50: new 170	com/google/android/gms/internal/h
    //   53: dup
    //   54: invokespecial 171	com/google/android/gms/internal/h:<init>	()V
    //   57: astore 6
    //   59: aload_0
    //   60: getstatic 38	com/google/android/gms/auth/GoogleAuthUtil:y	Landroid/content/Intent;
    //   63: aload 6
    //   65: iconst_1
    //   66: invokevirtual 175	android/content/Context:bindService	(Landroid/content/Intent;Landroid/content/ServiceConnection;I)Z
    //   69: ifeq +169 -> 238
    //   72: aload 6
    //   74: invokevirtual 179	com/google/android/gms/internal/h:d	()Landroid/os/IBinder;
    //   77: invokestatic 184	com/google/android/gms/internal/a$a:a	(Landroid/os/IBinder;)Lcom/google/android/gms/internal/a;
    //   80: aload_1
    //   81: aload_2
    //   82: aload 5
    //   84: invokeinterface 189 4 0
    //   89: astore 10
    //   91: aload 10
    //   93: ldc 191
    //   95: invokevirtual 195	android/os/Bundle:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   98: astore 11
    //   100: aload 11
    //   102: invokestatic 201	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   105: istore 12
    //   107: iload 12
    //   109: ifne +25 -> 134
    //   112: aload_0
    //   113: aload 6
    //   115: invokevirtual 205	android/content/Context:unbindService	(Landroid/content/ServiceConnection;)V
    //   118: aload 11
    //   120: areturn
    //   121: new 141	android/os/Bundle
    //   124: dup
    //   125: aload_3
    //   126: invokespecial 208	android/os/Bundle:<init>	(Landroid/os/Bundle;)V
    //   129: astore 5
    //   131: goto -102 -> 29
    //   134: aload 10
    //   136: ldc 210
    //   138: invokevirtual 195	android/os/Bundle:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   141: astore 13
    //   143: aload 10
    //   145: ldc 212
    //   147: invokevirtual 216	android/os/Bundle:getParcelable	(Ljava/lang/String;)Landroid/os/Parcelable;
    //   150: checkcast 30	android/content/Intent
    //   153: astore 14
    //   155: aload 13
    //   157: invokestatic 218	com/google/android/gms/auth/GoogleAuthUtil:b	(Ljava/lang/String;)Z
    //   160: ifeq +38 -> 198
    //   163: new 139	com/google/android/gms/auth/UserRecoverableAuthException
    //   166: dup
    //   167: aload 13
    //   169: aload 14
    //   171: invokespecial 221	com/google/android/gms/auth/UserRecoverableAuthException:<init>	(Ljava/lang/String;Landroid/content/Intent;)V
    //   174: athrow
    //   175: astore 9
    //   177: new 137	java/io/IOException
    //   180: dup
    //   181: ldc 223
    //   183: invokespecial 224	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   186: athrow
    //   187: astore 8
    //   189: aload_0
    //   190: aload 6
    //   192: invokevirtual 205	android/content/Context:unbindService	(Landroid/content/ServiceConnection;)V
    //   195: aload 8
    //   197: athrow
    //   198: aload 13
    //   200: invokestatic 226	com/google/android/gms/auth/GoogleAuthUtil:a	(Ljava/lang/String;)Z
    //   203: ifeq +25 -> 228
    //   206: new 137	java/io/IOException
    //   209: dup
    //   210: aload 13
    //   212: invokespecial 224	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   215: athrow
    //   216: astore 7
    //   218: new 46	com/google/android/gms/auth/GoogleAuthException
    //   221: dup
    //   222: ldc 228
    //   224: invokespecial 79	com/google/android/gms/auth/GoogleAuthException:<init>	(Ljava/lang/String;)V
    //   227: athrow
    //   228: new 46	com/google/android/gms/auth/GoogleAuthException
    //   231: dup
    //   232: aload 13
    //   234: invokespecial 79	com/google/android/gms/auth/GoogleAuthException:<init>	(Ljava/lang/String;)V
    //   237: athrow
    //   238: new 139	com/google/android/gms/auth/UserRecoverableAuthException
    //   241: dup
    //   242: ldc 133
    //   244: aconst_null
    //   245: invokespecial 221	com/google/android/gms/auth/UserRecoverableAuthException:<init>	(Ljava/lang/String;Landroid/content/Intent;)V
    //   248: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	249	0	paramContext	Context
    //   0	249	1	paramString1	String
    //   0	249	2	paramString2	String
    //   0	249	3	paramBundle	Bundle
    //   4	8	4	localContext	Context
    //   27	103	5	localBundle1	Bundle
    //   57	134	6	localh	com.google.android.gms.internal.h
    //   216	1	7	localInterruptedException	java.lang.InterruptedException
    //   187	9	8	localObject	Object
    //   175	1	9	localRemoteException	android.os.RemoteException
    //   89	55	10	localBundle2	Bundle
    //   98	21	11	str1	String
    //   105	3	12	bool	boolean
    //   141	92	13	str2	String
    //   153	17	14	localIntent	Intent
    // Exception table:
    //   from	to	target	type
    //   72	107	175	android/os/RemoteException
    //   134	175	175	android/os/RemoteException
    //   198	216	175	android/os/RemoteException
    //   228	238	175	android/os/RemoteException
    //   72	107	187	finally
    //   134	175	187	finally
    //   177	187	187	finally
    //   198	216	187	finally
    //   218	228	187	finally
    //   228	238	187	finally
    //   72	107	216	java/lang/InterruptedException
    //   134	175	216	java/lang/InterruptedException
    //   198	216	216	java/lang/InterruptedException
    //   228	238	216	java/lang/InterruptedException
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.auth.GoogleAuthUtil
 * JD-Core Version:    0.7.0.1
 */