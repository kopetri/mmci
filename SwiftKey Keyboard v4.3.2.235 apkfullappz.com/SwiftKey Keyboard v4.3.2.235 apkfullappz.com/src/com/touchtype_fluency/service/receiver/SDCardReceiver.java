package com.touchtype_fluency.service.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import com.touchtype.storage.AndroidStorageUtils;
import com.touchtype.util.LogUtil;
import java.io.File;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class SDCardReceiver
  extends BroadcastReceiver
{
  private static final Set<SDCardListener> LISTENERS = new CopyOnWriteArraySet();
  private static final String TAG = "SDCardReceiver";
  
  public static void addListener(SDCardListener paramSDCardListener)
  {
    LISTENERS.add(paramSDCardListener);
  }
  
  public static SDCardListener addMountedListenerGuaranteedOnce(SDCardMountedListener paramSDCardMountedListener, Context paramContext)
  {
    return new SafeListenerAdder(null).addMountedListenerGuaranteedOnce(paramSDCardMountedListener, paramContext);
  }
  
  public static void removeListener(SDCardListener paramSDCardListener)
  {
    LISTENERS.remove(paramSDCardListener);
  }
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    Uri localUri = paramIntent.getData();
    if (localUri != null)
    {
      String str1 = localUri.getPath();
      if ((str1 != null) && (str1.equals(Environment.getExternalStorageDirectory().getAbsolutePath())))
      {
        String str2 = paramIntent.getAction();
        if (str2.equals("android.intent.action.MEDIA_MOUNTED"))
        {
          Iterator localIterator2 = LISTENERS.iterator();
          while (localIterator2.hasNext()) {
            ((SDCardListener)localIterator2.next()).onMediaMounted();
          }
        }
        Iterator localIterator1;
        if ((str2.equals("android.intent.action.MEDIA_EJECT")) || (str2.equals("android.intent.action.MEDIA_REMOVED")) || (str2.equals("android.intent.action.MEDIA_BAD_REMOVAL")) || (str2.equals("android.intent.action.MEDIA_SHARED")) || (str2.equals("android.intent.action.MEDIA_UNMOUNTED"))) {
          localIterator1 = LISTENERS.iterator();
        }
        while (localIterator1.hasNext())
        {
          ((SDCardListener)localIterator1.next()).onMediaUnmounted();
          continue;
          LogUtil.w("SDCardReceiver", "Unrecognised intent");
        }
      }
      return;
    }
    LogUtil.w("SDCardReceiver", "No data field provided in intent - ignoring");
  }
  
  private static class SafeListenerAdder
  {
    boolean ranUpdate = false;
    
    public SDCardListener addMountedListenerGuaranteedOnce(final SDCardMountedListener paramSDCardMountedListener, Context paramContext)
    {
      SDCardListener local1 = new SDCardListener()
      {
        public void onMediaMounted()
        {
          synchronized (SDCardReceiver.SafeListenerAdder.this)
          {
            if (!SDCardReceiver.SafeListenerAdder.this.ranUpdate)
            {
              SDCardReceiver.SafeListenerAdder.this.ranUpdate = true;
              paramSDCardMountedListener.sdCardIsMounted();
              SDCardReceiver.removeListener(this);
            }
            return;
          }
        }
        
        public void onMediaUnmounted() {}
      };
      SDCardReceiver.addListener(local1);
      try
      {
        if ((!this.ranUpdate) && (AndroidStorageUtils.isRemovableMediaMounted()))
        {
          paramSDCardMountedListener.sdCardIsMounted();
          this.ranUpdate = true;
          SDCardReceiver.removeListener(local1);
        }
        if (this.ranUpdate) {
          local1 = null;
        }
        return local1;
      }
      finally {}
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.receiver.SDCardReceiver
 * JD-Core Version:    0.7.0.1
 */