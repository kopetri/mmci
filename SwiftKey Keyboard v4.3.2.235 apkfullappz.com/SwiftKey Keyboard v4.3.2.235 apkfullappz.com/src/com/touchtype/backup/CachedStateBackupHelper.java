package com.touchtype.backup;

import java.io.File;
import java.io.IOException;
import java.util.List;

abstract class CachedStateBackupHelper
  implements SwiftkeyBackupAgent.BackupHelper
{
  private Long cachedState;
  
  public List<File> backupData()
    throws IOException
  {
    List localList = backupDataInternal();
    this.cachedState = Long.valueOf(getState());
    return localList;
  }
  
  protected abstract List<File> backupDataInternal()
    throws IOException;
  
  public long getState()
    throws IOException
  {
    if (this.cachedState != null) {
      return this.cachedState.longValue();
    }
    return getStateInternal();
  }
  
  protected abstract long getStateInternal()
    throws IOException;
  
  public void restoreData(byte[] paramArrayOfByte)
    throws IOException
  {
    this.cachedState = Long.valueOf(restoreDataInternal(paramArrayOfByte));
  }
  
  protected abstract long restoreDataInternal(byte[] paramArrayOfByte)
    throws IOException;
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.backup.CachedStateBackupHelper
 * JD-Core Version:    0.7.0.1
 */