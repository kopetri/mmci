package com.touchtype.sync.client;

import java.util.LinkedList;
import java.util.List;

final class f
{
  private static long d = 600000L;
  private List<RequestListener> a = new LinkedList();
  private boolean b = false;
  private long c = 0L;
  
  final void a()
  {
    try
    {
      this.b = false;
      this.a.clear();
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  /* Error */
  final boolean a(RequestListener paramRequestListener)
  {
    // Byte code:
    //   0: iconst_1
    //   1: istore_2
    //   2: aload_0
    //   3: monitorenter
    //   4: invokestatic 42	java/lang/System:currentTimeMillis	()J
    //   7: lstore 4
    //   9: aload_0
    //   10: getfield 23	com/touchtype/sync/client/f:b	Z
    //   13: ifeq +17 -> 30
    //   16: aload_0
    //   17: getfield 25	com/touchtype/sync/client/f:c	J
    //   20: getstatic 18	com/touchtype/sync/client/f:d	J
    //   23: ladd
    //   24: lload 4
    //   26: lcmp
    //   27: ifge +42 -> 69
    //   30: aload_0
    //   31: iconst_1
    //   32: putfield 23	com/touchtype/sync/client/f:b	Z
    //   35: aload_0
    //   36: lload 4
    //   38: putfield 25	com/touchtype/sync/client/f:c	J
    //   41: aload_0
    //   42: getfield 30	com/touchtype/sync/client/f:a	Ljava/util/List;
    //   45: invokeinterface 35 1 0
    //   50: aload_1
    //   51: ifnull +14 -> 65
    //   54: aload_0
    //   55: getfield 30	com/touchtype/sync/client/f:a	Ljava/util/List;
    //   58: aload_1
    //   59: invokeinterface 46 2 0
    //   64: pop
    //   65: aload_0
    //   66: monitorexit
    //   67: iload_2
    //   68: ireturn
    //   69: iconst_0
    //   70: istore_2
    //   71: goto -6 -> 65
    //   74: astore_3
    //   75: aload_0
    //   76: monitorexit
    //   77: aload_3
    //   78: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	79	0	this	f
    //   0	79	1	paramRequestListener	RequestListener
    //   1	70	2	bool	boolean
    //   74	4	3	localObject	Object
    //   7	30	4	l	long
    // Exception table:
    //   from	to	target	type
    //   4	30	74	finally
    //   30	50	74	finally
    //   54	65	74	finally
  }
  
  final boolean b()
  {
    try
    {
      boolean bool = this.b;
      return bool;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  /* Error */
  final boolean b(RequestListener paramRequestListener)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 23	com/touchtype/sync/client/f:b	Z
    //   6: ifeq +20 -> 26
    //   9: aload_0
    //   10: getfield 30	com/touchtype/sync/client/f:a	Ljava/util/List;
    //   13: aload_1
    //   14: invokeinterface 46 2 0
    //   19: pop
    //   20: iconst_1
    //   21: istore_3
    //   22: aload_0
    //   23: monitorexit
    //   24: iload_3
    //   25: ireturn
    //   26: iconst_0
    //   27: istore_3
    //   28: goto -6 -> 22
    //   31: astore_2
    //   32: aload_0
    //   33: monitorexit
    //   34: aload_2
    //   35: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	36	0	this	f
    //   0	36	1	paramRequestListener	RequestListener
    //   31	4	2	localObject	Object
    //   21	7	3	bool	boolean
    // Exception table:
    //   from	to	target	type
    //   2	20	31	finally
  }
  
  public final List<RequestListener> c()
  {
    return new LinkedList(this.a);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.sync.client.f
 * JD-Core Version:    0.7.0.1
 */