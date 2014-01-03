package com.touchtype_fluency.internal;

import com.touchtype_fluency.TagSelector;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class TaggedWithSelector
  implements TagSelector
{
  private Collection<String> withTags;
  
  public TaggedWithSelector(Collection<String> paramCollection)
  {
    this.withTags = paramCollection;
  }
  
  public boolean apply(Set<String> paramSet)
  {
    Iterator localIterator = this.withTags.iterator();
    while (localIterator.hasNext()) {
      if (paramSet.contains((String)localIterator.next())) {
        return true;
      }
    }
    return false;
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject instanceof TaggedWithSelector))
    {
      TaggedWithSelector localTaggedWithSelector = (TaggedWithSelector)paramObject;
      Iterator localIterator1 = this.withTags.iterator();
      bool = true;
      while (localIterator1.hasNext())
      {
        String str2 = (String)localIterator1.next();
        bool &= localTaggedWithSelector.withTags.contains(str2);
      }
      Iterator localIterator2 = localTaggedWithSelector.withTags.iterator();
      while (localIterator2.hasNext())
      {
        String str1 = (String)localIterator2.next();
        bool &= this.withTags.contains(str1);
      }
    }
    boolean bool = false;
    return bool;
  }
  
  public int hashCode()
  {
    Iterator localIterator = this.withTags.iterator();
    int i = 0;
    while (localIterator.hasNext()) {
      i ^= ((String)localIterator.next()).hashCode();
    }
    return i;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.internal.TaggedWithSelector
 * JD-Core Version:    0.7.0.1
 */