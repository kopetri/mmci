package com.touchtype.storage;

import java.io.File;

public abstract interface FolderDecorator
{
  public abstract File getBaseFolder();
  
  public abstract boolean isAvailable();
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.storage.FolderDecorator
 * JD-Core Version:    0.7.0.1
 */