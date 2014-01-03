package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.Serializable;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOCase;

public final class WildcardFileFilter
  extends AbstractFileFilter
  implements Serializable
{
  private final IOCase caseSensitivity;
  private final String[] wildcards;
  
  public WildcardFileFilter(String paramString)
  {
    this(paramString, null);
  }
  
  public WildcardFileFilter(String paramString, IOCase paramIOCase)
  {
    if (paramString == null) {
      throw new IllegalArgumentException("The wildcard must not be null");
    }
    this.wildcards = new String[] { paramString };
    if (paramIOCase == null) {
      paramIOCase = IOCase.SENSITIVE;
    }
    this.caseSensitivity = paramIOCase;
  }
  
  public boolean accept(File paramFile)
  {
    String str = paramFile.getName();
    for (int i = 0; i < this.wildcards.length; i++) {
      if (FilenameUtils.wildcardMatch(str, this.wildcards[i], this.caseSensitivity)) {
        return true;
      }
    }
    return false;
  }
  
  public boolean accept(File paramFile, String paramString)
  {
    for (int i = 0; i < this.wildcards.length; i++) {
      if (FilenameUtils.wildcardMatch(paramString, this.wildcards[i], this.caseSensitivity)) {
        return true;
      }
    }
    return false;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(super.toString());
    localStringBuffer.append("(");
    if (this.wildcards != null) {
      for (int i = 0; i < this.wildcards.length; i++)
      {
        if (i > 0) {
          localStringBuffer.append(",");
        }
        localStringBuffer.append(this.wildcards[i]);
      }
    }
    localStringBuffer.append(")");
    return localStringBuffer.toString();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.apache.commons.io.filefilter.WildcardFileFilter
 * JD-Core Version:    0.7.0.1
 */