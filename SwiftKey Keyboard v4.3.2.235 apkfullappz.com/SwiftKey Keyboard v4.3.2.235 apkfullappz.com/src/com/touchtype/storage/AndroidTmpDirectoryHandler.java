package com.touchtype.storage;

import android.content.Context;
import com.touchtype.common.util.FileUtils;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public final class AndroidTmpDirectoryHandler
  implements TmpDirectoryHandler
{
  private final File temporaryDirectory;
  
  public AndroidTmpDirectoryHandler(Context paramContext)
  {
    String str = UUID.randomUUID().toString();
    this.temporaryDirectory = new File(paramContext.getCacheDir(), str);
  }
  
  public boolean deleteRoot()
  {
    if (!this.temporaryDirectory.exists()) {}
    for (;;)
    {
      return false;
      try
      {
        FileUtils.deleteRecursively(this.temporaryDirectory);
        boolean bool = this.temporaryDirectory.exists();
        if (!bool) {
          return true;
        }
      }
      catch (IOException localIOException) {}
    }
    return false;
  }
  
  public File getOrCreateTmpRoot()
    throws IOException
  {
    this.temporaryDirectory.mkdirs();
    if (!this.temporaryDirectory.isDirectory()) {
      throw new IOException();
    }
    return this.temporaryDirectory;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.storage.AndroidTmpDirectoryHandler
 * JD-Core Version:    0.7.0.1
 */