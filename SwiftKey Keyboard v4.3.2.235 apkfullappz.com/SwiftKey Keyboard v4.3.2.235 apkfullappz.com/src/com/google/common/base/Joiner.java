package com.google.common.base;

import java.io.IOException;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Joiner
{
  private final String separator;
  
  private Joiner(Joiner paramJoiner)
  {
    this.separator = paramJoiner.separator;
  }
  
  private Joiner(String paramString)
  {
    this.separator = ((String)Preconditions.checkNotNull(paramString));
  }
  
  private static Iterable<Object> iterable(final Object paramObject1, final Object paramObject2, Object[] paramArrayOfObject)
  {
    Preconditions.checkNotNull(paramArrayOfObject);
    new AbstractList()
    {
      public Object get(int paramAnonymousInt)
      {
        switch (paramAnonymousInt)
        {
        default: 
          return this.val$rest[(paramAnonymousInt - 2)];
        case 0: 
          return paramObject1;
        }
        return paramObject2;
      }
      
      public int size()
      {
        return 2 + this.val$rest.length;
      }
    };
  }
  
  public static Joiner on(char paramChar)
  {
    return new Joiner(String.valueOf(paramChar));
  }
  
  public static Joiner on(String paramString)
  {
    return new Joiner(paramString);
  }
  
  public <A extends Appendable> A appendTo(A paramA, Iterator<?> paramIterator)
    throws IOException
  {
    Preconditions.checkNotNull(paramA);
    if (paramIterator.hasNext())
    {
      paramA.append(toString(paramIterator.next()));
      while (paramIterator.hasNext())
      {
        paramA.append(this.separator);
        paramA.append(toString(paramIterator.next()));
      }
    }
    return paramA;
  }
  
  public final StringBuilder appendTo(StringBuilder paramStringBuilder, Iterable<?> paramIterable)
  {
    return appendTo(paramStringBuilder, paramIterable.iterator());
  }
  
  public final StringBuilder appendTo(StringBuilder paramStringBuilder, Iterator<?> paramIterator)
  {
    try
    {
      appendTo(paramStringBuilder, paramIterator);
      return paramStringBuilder;
    }
    catch (IOException localIOException)
    {
      throw new AssertionError(localIOException);
    }
  }
  
  public final StringBuilder appendTo(StringBuilder paramStringBuilder, Object[] paramArrayOfObject)
  {
    return appendTo(paramStringBuilder, Arrays.asList(paramArrayOfObject));
  }
  
  public final String join(Iterable<?> paramIterable)
  {
    return join(paramIterable.iterator());
  }
  
  public final String join(Object paramObject1, Object paramObject2, Object... paramVarArgs)
  {
    return join(iterable(paramObject1, paramObject2, paramVarArgs));
  }
  
  public final String join(Iterator<?> paramIterator)
  {
    return appendTo(new StringBuilder(), paramIterator).toString();
  }
  
  public final String join(Object[] paramArrayOfObject)
  {
    return join(Arrays.asList(paramArrayOfObject));
  }
  
  CharSequence toString(Object paramObject)
  {
    Preconditions.checkNotNull(paramObject);
    if ((paramObject instanceof CharSequence)) {
      return (CharSequence)paramObject;
    }
    return paramObject.toString();
  }
  
  public Joiner useForNull(final String paramString)
  {
    Preconditions.checkNotNull(paramString);
    new Joiner(this, paramString)
    {
      CharSequence toString(Object paramAnonymousObject)
      {
        if (paramAnonymousObject == null) {
          return paramString;
        }
        return Joiner.this.toString(paramAnonymousObject);
      }
      
      public Joiner useForNull(String paramAnonymousString)
      {
        Preconditions.checkNotNull(paramAnonymousString);
        throw new UnsupportedOperationException("already specified useForNull");
      }
    };
  }
  
  public MapJoiner withKeyValueSeparator(String paramString)
  {
    return new MapJoiner(this, paramString, null);
  }
  
  public static final class MapJoiner
  {
    private final Joiner joiner;
    private final String keyValueSeparator;
    
    private MapJoiner(Joiner paramJoiner, String paramString)
    {
      this.joiner = paramJoiner;
      this.keyValueSeparator = ((String)Preconditions.checkNotNull(paramString));
    }
    
    public <A extends Appendable> A appendTo(A paramA, Iterator<? extends Map.Entry<?, ?>> paramIterator)
      throws IOException
    {
      Preconditions.checkNotNull(paramA);
      if (paramIterator.hasNext())
      {
        Map.Entry localEntry1 = (Map.Entry)paramIterator.next();
        paramA.append(this.joiner.toString(localEntry1.getKey()));
        paramA.append(this.keyValueSeparator);
        paramA.append(this.joiner.toString(localEntry1.getValue()));
        while (paramIterator.hasNext())
        {
          paramA.append(this.joiner.separator);
          Map.Entry localEntry2 = (Map.Entry)paramIterator.next();
          paramA.append(this.joiner.toString(localEntry2.getKey()));
          paramA.append(this.keyValueSeparator);
          paramA.append(this.joiner.toString(localEntry2.getValue()));
        }
      }
      return paramA;
    }
    
    public StringBuilder appendTo(StringBuilder paramStringBuilder, Iterable<? extends Map.Entry<?, ?>> paramIterable)
    {
      return appendTo(paramStringBuilder, paramIterable.iterator());
    }
    
    public StringBuilder appendTo(StringBuilder paramStringBuilder, Iterator<? extends Map.Entry<?, ?>> paramIterator)
    {
      try
      {
        appendTo(paramStringBuilder, paramIterator);
        return paramStringBuilder;
      }
      catch (IOException localIOException)
      {
        throw new AssertionError(localIOException);
      }
    }
    
    public StringBuilder appendTo(StringBuilder paramStringBuilder, Map<?, ?> paramMap)
    {
      return appendTo(paramStringBuilder, paramMap.entrySet());
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.common.base.Joiner
 * JD-Core Version:    0.7.0.1
 */