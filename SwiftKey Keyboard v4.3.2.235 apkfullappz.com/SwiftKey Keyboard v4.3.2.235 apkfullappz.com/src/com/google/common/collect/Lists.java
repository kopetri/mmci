package com.google.common.collect;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public final class Lists
{
  static int computeArrayListCapacity(int paramInt)
  {
    if (paramInt >= 0) {}
    for (boolean bool = true;; bool = false)
    {
      Preconditions.checkArgument(bool);
      return Ints.saturatedCast(5L + paramInt + paramInt / 10);
    }
  }
  
  static boolean equalsImpl(List<?> paramList, Object paramObject)
  {
    if (paramObject == Preconditions.checkNotNull(paramList)) {}
    List localList;
    do
    {
      return true;
      if (!(paramObject instanceof List)) {
        return false;
      }
      localList = (List)paramObject;
    } while ((paramList.size() == localList.size()) && (Iterators.elementsEqual(paramList.iterator(), localList.iterator())));
    return false;
  }
  
  static int hashCodeImpl(List<?> paramList)
  {
    int i = 1;
    Iterator localIterator = paramList.iterator();
    if (localIterator.hasNext())
    {
      Object localObject = localIterator.next();
      int j = i * 31;
      if (localObject == null) {}
      for (int k = 0;; k = localObject.hashCode())
      {
        i = j + k;
        break;
      }
    }
    return i;
  }
  
  static int indexOfImpl(List<?> paramList, Object paramObject)
  {
    ListIterator localListIterator = paramList.listIterator();
    while (localListIterator.hasNext()) {
      if (Objects.equal(paramObject, localListIterator.next())) {
        return localListIterator.previousIndex();
      }
    }
    return -1;
  }
  
  static int lastIndexOfImpl(List<?> paramList, Object paramObject)
  {
    ListIterator localListIterator = paramList.listIterator(paramList.size());
    while (localListIterator.hasPrevious()) {
      if (Objects.equal(paramObject, localListIterator.previous())) {
        return localListIterator.nextIndex();
      }
    }
    return -1;
  }
  
  public static <E> ArrayList<E> newArrayList()
  {
    return new ArrayList();
  }
  
  public static <E> ArrayList<E> newArrayList(Iterable<? extends E> paramIterable)
  {
    Preconditions.checkNotNull(paramIterable);
    if ((paramIterable instanceof Collection)) {
      return new ArrayList(Collections2.cast(paramIterable));
    }
    return newArrayList(paramIterable.iterator());
  }
  
  public static <E> ArrayList<E> newArrayList(Iterator<? extends E> paramIterator)
  {
    Preconditions.checkNotNull(paramIterator);
    ArrayList localArrayList = newArrayList();
    while (paramIterator.hasNext()) {
      localArrayList.add(paramIterator.next());
    }
    return localArrayList;
  }
  
  public static <E> ArrayList<E> newArrayList(E... paramVarArgs)
  {
    Preconditions.checkNotNull(paramVarArgs);
    ArrayList localArrayList = new ArrayList(computeArrayListCapacity(paramVarArgs.length));
    Collections.addAll(localArrayList, paramVarArgs);
    return localArrayList;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.common.collect.Lists
 * JD-Core Version:    0.7.0.1
 */