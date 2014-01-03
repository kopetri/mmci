package com.touchtype.storage;

import java.io.File;
import java.io.IOException;

public abstract interface TmpDirectoryHandler
{
  public abstract boolean deleteRoot();
  
  public abstract File getOrCreateTmpRoot()
    throws IOException;
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.storage.TmpDirectoryHandler
 * JD-Core Version:    0.7.0.1
 */