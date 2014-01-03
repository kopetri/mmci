package com.touchtype_fluency;

import com.touchtype_fluency.internal.TaggedWithSelector;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class TagSelectors
{
  public static TagSelector allModels()
  {
    new TagSelector()
    {
      public boolean apply(Set<String> paramAnonymousSet)
      {
        return true;
      }
      
      public boolean equals(Object paramAnonymousObject)
      {
        return paramAnonymousObject.getClass().equals(getClass());
      }
      
      public int hashCode()
      {
        return 888899248;
      }
    };
  }
  
  public static TagSelector disabledModels()
  {
    return taggedWith("disabled");
  }
  
  public static TagSelector dynamicModels()
  {
    return taggedWith("dynamic");
  }
  
  public static TagSelector enabledModels()
  {
    return taggedWith("enabled");
  }
  
  public static TagSelector filePath(File paramFile)
  {
    return filePath(paramFile.getPath());
  }
  
  public static TagSelector filePath(String paramString)
  {
    return taggedWith("file:" + paramString.replace('\\', '/'));
  }
  
  public static TagSelector liveLanguageModels()
  {
    return taggedWith("ll");
  }
  
  public static TagSelector noModels()
  {
    new TagSelector()
    {
      public boolean apply(Set<String> paramAnonymousSet)
      {
        return false;
      }
      
      public boolean equals(Object paramAnonymousObject)
      {
        return paramAnonymousObject.getClass().equals(getClass());
      }
      
      public int hashCode()
      {
        return -1895447184;
      }
    };
  }
  
  public static TagSelector persistentDynamicModels()
  {
    return taggedWith("persistent");
  }
  
  public static TagSelector staticModels()
  {
    return taggedWith("static");
  }
  
  public static TagSelector taggedWith(String paramString)
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(paramString);
    return taggedWith(localArrayList);
  }
  
  public static TagSelector taggedWith(Collection<String> paramCollection)
  {
    return new TaggedWithSelector(paramCollection);
  }
  
  public static TagSelector temporaryDynamicModels()
  {
    return taggedWith("temporary");
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.TagSelectors
 * JD-Core Version:    0.7.0.1
 */