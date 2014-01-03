package com.touchtype.storage;

import android.content.Context;
import android.os.Environment;
import java.io.File;

public final class AndroidStorageUtils
{
  public static File getExternalDirectory(Context paramContext)
  {
    return new File(Environment.getExternalStorageDirectory(), "Android" + File.separator + "data" + File.separator + paramContext.getPackageName() + File.separator + "files");
  }
  
  public static boolean isRemovableMediaMounted()
  {
    return Environment.getExternalStorageState().equals("mounted");
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.storage.AndroidStorageUtils
 * JD-Core Version:    0.7.0.1
 */