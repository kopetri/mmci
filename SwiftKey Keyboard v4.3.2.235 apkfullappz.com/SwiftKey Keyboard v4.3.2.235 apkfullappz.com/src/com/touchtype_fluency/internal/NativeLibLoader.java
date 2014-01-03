package com.touchtype_fluency.internal;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class NativeLibLoader
{
  private static String getArchName()
  {
    String str = System.getProperty("os.arch").toLowerCase();
    if ((str.equals("amd64")) || (str.equals("x86_64"))) {
      return "x86_64";
    }
    return "x86";
  }
  
  private static String getJarLibraryPath(String paramString)
  {
    String str1 = getPlatformName();
    String str2 = "/native/" + str1 + "/" + getArchName() + "/";
    if (str1.equals("macosx")) {
      return str2 + "lib" + paramString + ".dylib";
    }
    if (str1.equals("windows")) {
      return str2 + paramString + ".dll";
    }
    return str2 + "lib" + paramString + ".so";
  }
  
  private static String getPlatformName()
  {
    String str = System.getProperty("os.name").toLowerCase();
    if (str.contains("mac")) {
      return "macosx";
    }
    if (str.contains("win")) {
      return "windows";
    }
    return "linux";
  }
  
  public static void loadFullPath(String paramString1, String paramString2)
  {
    try
    {
      System.load(paramString1);
      return;
    }
    catch (UnsatisfiedLinkError localUnsatisfiedLinkError)
    {
      throw ((UnsatisfiedLinkError)new UnsatisfiedLinkError(paramString2).initCause(localUnsatisfiedLinkError));
    }
  }
  
  public static void loadLibrary(String paramString1, String paramString2)
  {
    try
    {
      System.loadLibrary(paramString1);
      return;
    }
    catch (UnsatisfiedLinkError localUnsatisfiedLinkError)
    {
      throw ((UnsatisfiedLinkError)new UnsatisfiedLinkError(paramString2).initCause(localUnsatisfiedLinkError));
    }
  }
  
  public static void loadOrUnpack(String paramString1, String paramString2)
  {
    try
    {
      System.loadLibrary(paramString1);
      return;
    }
    catch (UnsatisfiedLinkError localUnsatisfiedLinkError)
    {
      unpackAndLoad(paramString1, paramString2);
    }
  }
  
  private static void unpack(InputStream paramInputStream, File paramFile)
    throws IOException
  {
    FileOutputStream localFileOutputStream = new FileOutputStream(paramFile);
    try
    {
      byte[] arrayOfByte = new byte[4096];
      for (;;)
      {
        int i = paramInputStream.read(arrayOfByte);
        if (i <= 0) {
          break;
        }
        localFileOutputStream.write(arrayOfByte, 0, i);
      }
    }
    finally
    {
      localFileOutputStream.close();
    }
  }
  
  private static void unpackAndLoad(String paramString1, String paramString2)
    throws UnsatisfiedLinkError
  {
    InputStream localInputStream = NativeLibLoader.class.getResourceAsStream(getJarLibraryPath(paramString1));
    if (localInputStream != null) {
      try
      {
        File localFile = File.createTempFile(paramString1, null);
        localFile.deleteOnExit();
        unpack(localInputStream, localFile);
        System.load(localFile.getPath());
        throw new UnsatisfiedLinkError(paramString2);
      }
      finally
      {
        try
        {
          localInputStream.close();
          return;
        }
        catch (UnsatisfiedLinkError localUnsatisfiedLinkError)
        {
          throw new UnsatisfiedLinkError(paramString2);
        }
        catch (IOException localIOException)
        {
          throw new UnsatisfiedLinkError(paramString2);
        }
        localObject = finally;
        localInputStream.close();
      }
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.internal.NativeLibLoader
 * JD-Core Version:    0.7.0.1
 */