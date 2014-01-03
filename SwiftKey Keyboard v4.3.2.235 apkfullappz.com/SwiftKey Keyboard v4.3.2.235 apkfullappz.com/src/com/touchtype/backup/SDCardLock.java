package com.touchtype.backup;

import com.touchtype_fluency.service.receiver.SDCardListener;
import com.touchtype_fluency.service.receiver.SDCardReceiver;
import java.util.concurrent.locks.ReentrantLock;

public final class SDCardLock
  implements SDCardListener
{
  private ReentrantLock lock = new ReentrantLock();
  
  private void lockInternal()
  {
    this.lock.lock();
  }
  
  private void unlockInternal()
  {
    this.lock.unlock();
  }
  
  public void lock()
  {
    SDCardReceiver.addListener(this);
    lockInternal();
  }
  
  public void onMediaMounted() {}
  
  public void onMediaUnmounted()
  {
    lockInternal();
    unlockInternal();
  }
  
  public void unlock()
  {
    unlockInternal();
    SDCardReceiver.removeListener(this);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.backup.SDCardLock
 * JD-Core Version:    0.7.0.1
 */