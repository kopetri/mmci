package com.touchtype.storage;

import android.os.Environment;
import java.io.File;

public final class FolderDecoratorFactory
{
  private static final String TAG = FolderDecoratorFactory.class.getSimpleName();
  
  private static void checkNotFile(File paramFile)
  {
    if (paramFile.isFile()) {
      throw new IllegalStateException(paramFile + " should not be a file.");
    }
  }
  
  public static FolderDecorator create(File paramFile)
  {
    checkNotFile(paramFile);
    if (isOnSDCard(paramFile)) {
      return new SDStorageDecorator(paramFile);
    }
    return new BaseStorageDecorator(paramFile);
  }
  
  private static boolean isOnSDCard(File paramFile)
  {
    return paramFile.getAbsolutePath().startsWith(Environment.getExternalStorageDirectory().getAbsolutePath());
  }
  
  private static final class BaseStorageDecorator
    implements FolderDecorator
  {
    private final File baseFolder;
    
    public BaseStorageDecorator(File paramFile)
    {
      this.baseFolder = paramFile;
    }
    
    public File getBaseFolder()
    {
      this.baseFolder.mkdirs();
      return this.baseFolder;
    }
    
    public boolean isAvailable()
    {
      return true;
    }
  }
  
  protected static final class SDStorageDecorator
    implements FolderDecorator
  {
    private final File baseFolder;
    
    public SDStorageDecorator(File paramFile)
    {
      this.baseFolder = paramFile;
    }
    
    public File getBaseFolder()
    {
      this.baseFolder.mkdirs();
      return this.baseFolder;
    }
    
    public boolean isAvailable()
    {
      return Environment.getExternalStorageState().equals("mounted");
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.storage.FolderDecoratorFactory
 * JD-Core Version:    0.7.0.1
 */