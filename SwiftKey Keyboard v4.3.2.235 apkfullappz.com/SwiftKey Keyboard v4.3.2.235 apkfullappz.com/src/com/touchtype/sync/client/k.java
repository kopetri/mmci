package com.touchtype.sync.client;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

final class k
{
  private h a;
  private final SyncStorage b;
  private final LoggingListener c;
  
  k(SyncStorage paramSyncStorage, LoggingListener paramLoggingListener)
  {
    this.b = paramSyncStorage;
    this.c = paramLoggingListener;
    File localFile = new File(this.b.getSyncFilesDirectory(), "sync_state.json");
    try
    {
      String str = Files.toString(localFile, Charsets.UTF_8);
      this.a = ((h)new Gson().fromJson(str, h.class));
      return;
    }
    catch (IOException localIOException)
    {
      e.a(this.c, "com.touchtype.sync.client", "Unable to read sync state: " + localIOException);
      this.a = new h();
    }
  }
  
  private void h()
  {
    if (this.a.mNotificationsEnabled) {
      if (this.a.mLastPull >= this.a.mLastPullFail) {
        this.a.mLastSync = Math.max(this.a.mLastPull, this.a.mLastSync);
      }
    }
    for (;;)
    {
      i();
      return;
      long l1 = this.a.mLastPullFail;
      if ((this.a.mLastPull <= l1) && (this.a.mLastPull > this.a.mLastSync)) {}
      for (long l2 = this.a.mLastPull;; l2 = this.a.mLastSync)
      {
        if ((this.a.mLastPush <= l1) && (this.a.mLastPull > l2)) {
          l2 = this.a.mLastPush;
        }
        this.a.mLastSync = l2;
        break;
      }
      this.a.mLastSync = Math.min(this.a.mLastPull, this.a.mLastPush);
    }
  }
  
  private void i()
  {
    Gson localGson = new GsonBuilder().create();
    File localFile = new File(this.b.getSyncFilesDirectory(), "sync_state.json");
    String str = localGson.toJson(this.a, h.class);
    try
    {
      Files.write(str.getBytes(), localFile);
      return;
    }
    catch (IOException localIOException)
    {
      e.c(this.c, "com.touchtype.sync.client", "Error when writing sync state: " + localIOException);
    }
  }
  
  final long a()
  {
    try
    {
      long l = this.a.mLastPush;
      return l;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  final void a(long paramLong)
  {
    try
    {
      this.a.mLastPush = paramLong;
      h();
      i();
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  final void a(String paramString)
  {
    try
    {
      this.a.mLastRemoteVersion = paramString;
      i();
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  /* Error */
  final void a(String paramString1, String paramString2)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 57	com/touchtype/sync/client/k:a	Lcom/touchtype/sync/client/h;
    //   6: getfield 147	com/touchtype/sync/client/h:mSyncDevices	Ljava/util/List;
    //   9: invokeinterface 153 1 0
    //   14: istore 4
    //   16: iconst_0
    //   17: istore 5
    //   19: iload 5
    //   21: iload 4
    //   23: if_icmpge +44 -> 67
    //   26: aload_0
    //   27: getfield 57	com/touchtype/sync/client/k:a	Lcom/touchtype/sync/client/h;
    //   30: getfield 147	com/touchtype/sync/client/h:mSyncDevices	Ljava/util/List;
    //   33: iload 5
    //   35: invokeinterface 157 2 0
    //   40: checkcast 159	com/touchtype/sync/client/Device
    //   43: astore 6
    //   45: aload 6
    //   47: invokevirtual 162	com/touchtype/sync/client/Device:getId	()Ljava/lang/String;
    //   50: aload_1
    //   51: invokevirtual 166	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   54: ifeq +16 -> 70
    //   57: aload 6
    //   59: aload_2
    //   60: invokevirtual 168	com/touchtype/sync/client/Device:a	(Ljava/lang/String;)V
    //   63: aload_0
    //   64: invokespecial 103	com/touchtype/sync/client/k:i	()V
    //   67: aload_0
    //   68: monitorexit
    //   69: return
    //   70: iinc 5 1
    //   73: goto -54 -> 19
    //   76: astore_3
    //   77: aload_0
    //   78: monitorexit
    //   79: aload_3
    //   80: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	81	0	this	k
    //   0	81	1	paramString1	String
    //   0	81	2	paramString2	String
    //   76	4	3	localObject	Object
    //   14	10	4	i	int
    //   17	54	5	j	int
    //   43	15	6	localDevice	Device
    // Exception table:
    //   from	to	target	type
    //   2	16	76	finally
    //   26	67	76	finally
  }
  
  final void a(List<Device> paramList)
  {
    try
    {
      this.a.mSyncDevices = paramList;
      i();
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  final void a(boolean paramBoolean)
  {
    try
    {
      this.a.mNotificationsEnabled = paramBoolean;
      h();
      i();
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  final long b()
  {
    try
    {
      long l = this.a.mLastPull;
      return l;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  final void b(long paramLong)
  {
    try
    {
      this.a.mLastPull = paramLong;
      h();
      i();
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  /* Error */
  final void b(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 57	com/touchtype/sync/client/k:a	Lcom/touchtype/sync/client/h;
    //   6: getfield 147	com/touchtype/sync/client/h:mSyncDevices	Ljava/util/List;
    //   9: invokeinterface 153 1 0
    //   14: istore_3
    //   15: iconst_0
    //   16: istore 4
    //   18: iload 4
    //   20: iload_3
    //   21: if_icmpge +45 -> 66
    //   24: aload_0
    //   25: getfield 57	com/touchtype/sync/client/k:a	Lcom/touchtype/sync/client/h;
    //   28: getfield 147	com/touchtype/sync/client/h:mSyncDevices	Ljava/util/List;
    //   31: iload 4
    //   33: invokeinterface 157 2 0
    //   38: checkcast 159	com/touchtype/sync/client/Device
    //   41: invokevirtual 162	com/touchtype/sync/client/Device:getId	()Ljava/lang/String;
    //   44: aload_1
    //   45: invokevirtual 166	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   48: ifeq +43 -> 91
    //   51: aload_0
    //   52: getfield 57	com/touchtype/sync/client/k:a	Lcom/touchtype/sync/client/h;
    //   55: getfield 147	com/touchtype/sync/client/h:mSyncDevices	Ljava/util/List;
    //   58: iload 4
    //   60: invokeinterface 173 2 0
    //   65: pop
    //   66: iload_3
    //   67: iconst_1
    //   68: aload_0
    //   69: getfield 57	com/touchtype/sync/client/k:a	Lcom/touchtype/sync/client/h;
    //   72: getfield 147	com/touchtype/sync/client/h:mSyncDevices	Ljava/util/List;
    //   75: invokeinterface 153 1 0
    //   80: iadd
    //   81: if_icmpne +7 -> 88
    //   84: aload_0
    //   85: invokespecial 103	com/touchtype/sync/client/k:i	()V
    //   88: aload_0
    //   89: monitorexit
    //   90: return
    //   91: iinc 4 1
    //   94: goto -76 -> 18
    //   97: astore_2
    //   98: aload_0
    //   99: monitorexit
    //   100: aload_2
    //   101: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	102	0	this	k
    //   0	102	1	paramString	String
    //   97	4	2	localObject	Object
    //   14	68	3	i	int
    //   16	76	4	j	int
    // Exception table:
    //   from	to	target	type
    //   2	15	97	finally
    //   24	66	97	finally
    //   66	88	97	finally
  }
  
  final String c()
  {
    try
    {
      String str = this.a.mLastRemoteVersion;
      return str;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  final void c(long paramLong)
  {
    try
    {
      if (paramLong > this.a.mLastPullFail)
      {
        this.a.mLastPullFail = paramLong;
        i();
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  final long d()
  {
    try
    {
      long l = this.a.mLastSync;
      return l;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  final List<Device> e()
  {
    try
    {
      List localList = Collections.unmodifiableList(this.a.mSyncDevices);
      return localList;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  final void f()
  {
    try
    {
      this.a.mLastPush = 0L;
      this.a.mLastPull = 0L;
      this.a.mLastSync = 0L;
      this.a.mLastRemoteVersion = null;
      this.a.mLastPullFail = 0L;
      this.a.mNotificationsEnabled = false;
      this.a.mSyncDevices.clear();
      i();
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  final boolean g()
  {
    try
    {
      boolean bool = this.a.mNotificationsEnabled;
      return bool;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.sync.client.k
 * JD-Core Version:    0.7.0.1
 */