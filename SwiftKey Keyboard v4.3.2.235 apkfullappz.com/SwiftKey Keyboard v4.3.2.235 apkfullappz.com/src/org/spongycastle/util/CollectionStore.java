package org.spongycastle.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class CollectionStore
  implements Store
{
  private Collection _local;
  
  public CollectionStore(Collection paramCollection)
  {
    this._local = new ArrayList(paramCollection);
  }
  
  public Collection getMatches(Selector paramSelector)
  {
    ArrayList localArrayList;
    if (paramSelector == null) {
      localArrayList = new ArrayList(this._local);
    }
    for (;;)
    {
      return localArrayList;
      localArrayList = new ArrayList();
      Iterator localIterator = this._local.iterator();
      while (localIterator.hasNext())
      {
        Object localObject = localIterator.next();
        if (paramSelector.match(localObject)) {
          localArrayList.add(localObject);
        }
      }
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.util.CollectionStore
 * JD-Core Version:    0.7.0.1
 */