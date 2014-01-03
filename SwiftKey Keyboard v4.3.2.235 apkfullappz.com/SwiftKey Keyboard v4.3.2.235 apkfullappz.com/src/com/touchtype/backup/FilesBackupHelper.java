package com.touchtype.backup;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

abstract class FilesBackupHelper
  extends CachedStateBackupHelper
{
  protected List<File> backupDataInternal()
    throws IOException
  {
    LinkedHashMap localLinkedHashMap = getBackupFiles();
    ArrayList localArrayList = new ArrayList(localLinkedHashMap.size());
    Iterator localIterator = localLinkedHashMap.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      File localFile = BackupUtil.backupSingleFile((String)localEntry.getKey(), (File)localEntry.getValue());
      if (localFile != null) {
        localArrayList.add(localFile);
      }
    }
    return localArrayList;
  }
  
  protected abstract LinkedHashMap<String, File> getBackupFiles()
    throws IOException;
  
  protected long getStateInternal()
    throws IOException
  {
    return BackupUtil.getState(new ArrayList(getBackupFiles().values()));
  }
  
  protected long restoreDataInternal(byte[] paramArrayOfByte)
    throws IOException
  {
    return BackupUtil.restoreMultipleFiles(paramArrayOfByte);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.backup.FilesBackupHelper
 * JD-Core Version:    0.7.0.1
 */