package org.apache.commons.io;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class FileUtils
{
  public static final File[] EMPTY_FILE_ARRAY = new File[0];
  
  public static void cleanDirectory(File paramFile)
    throws IOException
  {
    if (!paramFile.exists()) {
      throw new IllegalArgumentException(paramFile + " does not exist");
    }
    if (!paramFile.isDirectory()) {
      throw new IllegalArgumentException(paramFile + " is not a directory");
    }
    File[] arrayOfFile = paramFile.listFiles();
    if (arrayOfFile == null) {
      throw new IOException("Failed to list contents of " + paramFile);
    }
    Object localObject = null;
    int i = 0;
    for (;;)
    {
      if (i < arrayOfFile.length)
      {
        File localFile = arrayOfFile[i];
        try
        {
          forceDelete(localFile);
          label121:
          i++;
        }
        catch (IOException localIOException)
        {
          break label121;
        }
      }
    }
    if (localIOException != null) {
      throw localIOException;
    }
  }
  
  public static void copyDirectory(File paramFile1, File paramFile2)
    throws IOException
  {
    copyDirectory(paramFile1, paramFile2, true);
  }
  
  public static void copyDirectory(File paramFile1, File paramFile2, FileFilter paramFileFilter, boolean paramBoolean)
    throws IOException
  {
    if (paramFile1 == null) {
      throw new NullPointerException("Source must not be null");
    }
    if (paramFile2 == null) {
      throw new NullPointerException("Destination must not be null");
    }
    if (!paramFile1.exists()) {
      throw new FileNotFoundException("Source '" + paramFile1 + "' does not exist");
    }
    if (!paramFile1.isDirectory()) {
      throw new IOException("Source '" + paramFile1 + "' exists but is not a directory");
    }
    if (paramFile1.getCanonicalPath().equals(paramFile2.getCanonicalPath())) {
      throw new IOException("Source '" + paramFile1 + "' and destination '" + paramFile2 + "' are the same");
    }
    boolean bool = paramFile2.getCanonicalPath().startsWith(paramFile1.getCanonicalPath());
    ArrayList localArrayList = null;
    if (bool)
    {
      if (paramFileFilter == null) {}
      for (File[] arrayOfFile = paramFile1.listFiles();; arrayOfFile = paramFile1.listFiles(paramFileFilter))
      {
        localArrayList = null;
        if (arrayOfFile == null) {
          break;
        }
        int i = arrayOfFile.length;
        localArrayList = null;
        if (i <= 0) {
          break;
        }
        localArrayList = new ArrayList(arrayOfFile.length);
        for (int j = 0; j < arrayOfFile.length; j++) {
          localArrayList.add(new File(paramFile2, arrayOfFile[j].getName()).getCanonicalPath());
        }
      }
    }
    doCopyDirectory(paramFile1, paramFile2, paramFileFilter, paramBoolean, localArrayList);
  }
  
  public static void copyDirectory(File paramFile1, File paramFile2, boolean paramBoolean)
    throws IOException
  {
    copyDirectory(paramFile1, paramFile2, null, paramBoolean);
  }
  
  public static void copyFile(File paramFile1, File paramFile2)
    throws IOException
  {
    copyFile(paramFile1, paramFile2, true);
  }
  
  public static void copyFile(File paramFile1, File paramFile2, boolean paramBoolean)
    throws IOException
  {
    if (paramFile1 == null) {
      throw new NullPointerException("Source must not be null");
    }
    if (paramFile2 == null) {
      throw new NullPointerException("Destination must not be null");
    }
    if (!paramFile1.exists()) {
      throw new FileNotFoundException("Source '" + paramFile1 + "' does not exist");
    }
    if (paramFile1.isDirectory()) {
      throw new IOException("Source '" + paramFile1 + "' exists but is a directory");
    }
    if (paramFile1.getCanonicalPath().equals(paramFile2.getCanonicalPath())) {
      throw new IOException("Source '" + paramFile1 + "' and destination '" + paramFile2 + "' are the same");
    }
    if ((paramFile2.getParentFile() != null) && (!paramFile2.getParentFile().exists()) && (!paramFile2.getParentFile().mkdirs())) {
      throw new IOException("Destination '" + paramFile2 + "' directory cannot be created");
    }
    if ((paramFile2.exists()) && (!paramFile2.canWrite())) {
      throw new IOException("Destination '" + paramFile2 + "' exists but is read-only");
    }
    doCopyFile(paramFile1, paramFile2, paramBoolean);
  }
  
  public static void deleteDirectory(File paramFile)
    throws IOException
  {
    if (!paramFile.exists()) {}
    do
    {
      return;
      cleanDirectory(paramFile);
    } while (paramFile.delete());
    throw new IOException("Unable to delete directory " + paramFile + ".");
  }
  
  public static boolean deleteQuietly(File paramFile)
  {
    if (paramFile == null) {
      return false;
    }
    try
    {
      if (paramFile.isDirectory()) {
        cleanDirectory(paramFile);
      }
      try
      {
        label17:
        boolean bool = paramFile.delete();
        return bool;
      }
      catch (Exception localException2)
      {
        return false;
      }
    }
    catch (Exception localException1)
    {
      break label17;
    }
  }
  
  private static void doCopyDirectory(File paramFile1, File paramFile2, FileFilter paramFileFilter, boolean paramBoolean, List paramList)
    throws IOException
  {
    if (paramFile2.exists())
    {
      if (!paramFile2.isDirectory()) {
        throw new IOException("Destination '" + paramFile2 + "' exists but is not a directory");
      }
    }
    else
    {
      if (!paramFile2.mkdirs()) {
        throw new IOException("Destination '" + paramFile2 + "' directory cannot be created");
      }
      if (paramBoolean) {
        paramFile2.setLastModified(paramFile1.lastModified());
      }
    }
    if (!paramFile2.canWrite()) {
      throw new IOException("Destination '" + paramFile2 + "' cannot be written to");
    }
    if (paramFileFilter == null) {}
    for (File[] arrayOfFile = paramFile1.listFiles(); arrayOfFile == null; arrayOfFile = paramFile1.listFiles(paramFileFilter)) {
      throw new IOException("Failed to list contents of " + paramFile1);
    }
    int i = 0;
    if (i < arrayOfFile.length)
    {
      File localFile = new File(paramFile2, arrayOfFile[i].getName());
      if ((paramList == null) || (!paramList.contains(arrayOfFile[i].getCanonicalPath())))
      {
        if (!arrayOfFile[i].isDirectory()) {
          break label260;
        }
        doCopyDirectory(arrayOfFile[i], localFile, paramFileFilter, paramBoolean, paramList);
      }
      for (;;)
      {
        i++;
        break;
        label260:
        doCopyFile(arrayOfFile[i], localFile, paramBoolean);
      }
    }
  }
  
  private static void doCopyFile(File paramFile1, File paramFile2, boolean paramBoolean)
    throws IOException
  {
    if ((paramFile2.exists()) && (paramFile2.isDirectory())) {
      throw new IOException("Destination '" + paramFile2 + "' exists but is a directory");
    }
    FileInputStream localFileInputStream = new FileInputStream(paramFile1);
    try
    {
      FileOutputStream localFileOutputStream = new FileOutputStream(paramFile2);
      try
      {
        IOUtils.copy(localFileInputStream, localFileOutputStream);
        IOUtils.closeQuietly(localFileOutputStream);
        IOUtils.closeQuietly(localFileInputStream);
        if (paramFile1.length() != paramFile2.length()) {
          throw new IOException("Failed to copy full contents from '" + paramFile1 + "' to '" + paramFile2 + "'");
        }
      }
      finally {}
      if (!paramBoolean) {
        return;
      }
    }
    finally
    {
      IOUtils.closeQuietly(localFileInputStream);
    }
    paramFile2.setLastModified(paramFile1.lastModified());
  }
  
  public static void forceDelete(File paramFile)
    throws IOException
  {
    if (paramFile.isDirectory()) {
      deleteDirectory(paramFile);
    }
    boolean bool;
    do
    {
      return;
      bool = paramFile.exists();
    } while (paramFile.delete());
    if (!bool) {
      throw new FileNotFoundException("File does not exist: " + paramFile);
    }
    throw new IOException("Unable to delete file: " + paramFile);
  }
  
  public static void forceMkdir(File paramFile)
    throws IOException
  {
    if (paramFile.exists())
    {
      if (paramFile.isFile()) {
        throw new IOException("File " + paramFile + " exists and is not a directory. Unable to create directory.");
      }
    }
    else if (!paramFile.mkdirs()) {
      throw new IOException("Unable to create directory " + paramFile);
    }
  }
  
  public static void moveDirectory(File paramFile1, File paramFile2)
    throws IOException
  {
    if (paramFile1 == null) {
      throw new NullPointerException("Source must not be null");
    }
    if (paramFile2 == null) {
      throw new NullPointerException("Destination must not be null");
    }
    if (!paramFile1.exists()) {
      throw new FileNotFoundException("Source '" + paramFile1 + "' does not exist");
    }
    if (!paramFile1.isDirectory()) {
      throw new IOException("Source '" + paramFile1 + "' is not a directory");
    }
    if (paramFile2.exists()) {
      throw new IOException("Destination '" + paramFile2 + "' already exists");
    }
    if (!paramFile1.renameTo(paramFile2))
    {
      copyDirectory(paramFile1, paramFile2);
      deleteDirectory(paramFile1);
      if (paramFile1.exists()) {
        throw new IOException("Failed to delete original directory '" + paramFile1 + "' after copy to '" + paramFile2 + "'");
      }
    }
  }
  
  public static FileInputStream openInputStream(File paramFile)
    throws IOException
  {
    if (paramFile.exists())
    {
      if (paramFile.isDirectory()) {
        throw new IOException("File '" + paramFile + "' exists but is a directory");
      }
      if (!paramFile.canRead()) {
        throw new IOException("File '" + paramFile + "' cannot be read");
      }
    }
    else
    {
      throw new FileNotFoundException("File '" + paramFile + "' does not exist");
    }
    return new FileInputStream(paramFile);
  }
  
  public static FileOutputStream openOutputStream(File paramFile)
    throws IOException
  {
    if (paramFile.exists())
    {
      if (paramFile.isDirectory()) {
        throw new IOException("File '" + paramFile + "' exists but is a directory");
      }
      if (!paramFile.canWrite()) {
        throw new IOException("File '" + paramFile + "' cannot be written to");
      }
    }
    else
    {
      File localFile = paramFile.getParentFile();
      if ((localFile != null) && (!localFile.exists()) && (!localFile.mkdirs())) {
        throw new IOException("File '" + paramFile + "' could not be created");
      }
    }
    return new FileOutputStream(paramFile);
  }
  
  public static String readFileToString(File paramFile)
    throws IOException
  {
    return readFileToString(paramFile, null);
  }
  
  public static String readFileToString(File paramFile, String paramString)
    throws IOException
  {
    FileInputStream localFileInputStream = null;
    try
    {
      localFileInputStream = openInputStream(paramFile);
      String str = IOUtils.toString(localFileInputStream, paramString);
      return str;
    }
    finally
    {
      IOUtils.closeQuietly(localFileInputStream);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.apache.commons.io.FileUtils
 * JD-Core Version:    0.7.0.1
 */