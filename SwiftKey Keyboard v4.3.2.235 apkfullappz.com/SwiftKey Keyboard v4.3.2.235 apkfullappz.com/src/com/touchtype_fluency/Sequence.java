package com.touchtype_fluency;

import java.util.AbstractList;

public final class Sequence
  extends AbstractList<String>
{
  public static final String unspecifiedContact = "";
  public static final String unspecifiedFieldHint = "";
  private long peer;
  
  static {}
  
  public Sequence()
  {
    createPeer();
  }
  
  private Sequence(long paramLong)
  {
    this.peer = paramLong;
  }
  
  private native void createPeer();
  
  private native void destroyPeer();
  
  private native boolean equalTo(Sequence paramSequence);
  
  static native void initIDs();
  
  public native void add(int paramInt, String paramString);
  
  public native void add(int paramInt, String paramString1, String paramString2);
  
  public void append(CharSequence paramCharSequence)
  {
    append(paramCharSequence.toString());
  }
  
  public native void append(String paramString);
  
  public native void append(String paramString1, String paramString2);
  
  public Object clone()
    throws CloneNotSupportedException
  {
    throw new CloneNotSupportedException();
  }
  
  public native Sequence dropFirst(int paramInt);
  
  public native Sequence dropLast(int paramInt);
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject instanceof Sequence)) {
      return equalTo((Sequence)paramObject);
    }
    return false;
  }
  
  protected void finalize()
    throws Throwable
  {
    try
    {
      destroyPeer();
      return;
    }
    finally
    {
      super.finalize();
    }
  }
  
  public String get(int paramInt)
  {
    return termAt(paramInt);
  }
  
  public native String getContact();
  
  public native String getFieldHint();
  
  public native Type getType();
  
  public int hashCode()
  {
    return 149 * (getType().hashCode() + 149 * (getContact().hashCode() + 149 * (149 + (getFieldHint().hashCode() + 149 * super.hashCode()))));
  }
  
  public void prepend(CharSequence paramCharSequence)
  {
    prepend(paramCharSequence.toString());
  }
  
  public native void prepend(String paramString);
  
  public native void prepend(String paramString1, String paramString2);
  
  public native String pronunciationAt(int paramInt);
  
  public native String remove(int paramInt);
  
  public native String set(int paramInt, String paramString);
  
  public native String set(int paramInt, String paramString1, String paramString2);
  
  public native void setContact(String paramString);
  
  public native void setFieldHint(String paramString);
  
  public native void setType(Type paramType);
  
  public native int size();
  
  public native Sequence takeFirst(int paramInt);
  
  public native Sequence takeLast(int paramInt);
  
  public native String termAt(int paramInt);
  
  public native String toString();
  
  public static enum Type
  {
    static
    {
      MESSAGE_START = new Type("MESSAGE_START", 1);
      NEWLINE_START = new Type("NEWLINE_START", 2);
      Type[] arrayOfType = new Type[3];
      arrayOfType[0] = NORMAL;
      arrayOfType[1] = MESSAGE_START;
      arrayOfType[2] = NEWLINE_START;
      $VALUES = arrayOfType;
    }
    
    private Type() {}
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.Sequence
 * JD-Core Version:    0.7.0.1
 */