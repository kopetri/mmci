package com.touchtype.sync.client;

import java.io.File;

public abstract interface SyncStorage
{
  public abstract File getPushDeltaModelDirectory();
  
  public abstract File getSyncFilesDirectory();
  
  public abstract File getTempDirectory();
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.sync.client.SyncStorage
 * JD-Core Version:    0.7.0.1
 */