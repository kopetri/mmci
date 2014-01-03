package org.spongycastle.asn1.eac;

import java.util.Enumeration;
import java.util.Hashtable;

public class Flags
{
  int value = 0;
  
  public Flags() {}
  
  public Flags(int paramInt)
  {
    this.value = paramInt;
  }
  
  String decode(Hashtable paramHashtable)
  {
    StringJoiner localStringJoiner = new StringJoiner(" ");
    Enumeration localEnumeration = paramHashtable.keys();
    while (localEnumeration.hasMoreElements())
    {
      Integer localInteger = (Integer)localEnumeration.nextElement();
      if (isSet(localInteger.intValue())) {
        localStringJoiner.add((String)paramHashtable.get(localInteger));
      }
    }
    return localStringJoiner.toString();
  }
  
  public int getFlags()
  {
    return this.value;
  }
  
  public boolean isSet(int paramInt)
  {
    return (paramInt & this.value) != 0;
  }
  
  public void set(int paramInt)
  {
    this.value = (paramInt | this.value);
  }
  
  private class StringJoiner
  {
    boolean First = true;
    StringBuffer b = new StringBuffer();
    String mSeparator;
    
    public StringJoiner(String paramString)
    {
      this.mSeparator = paramString;
    }
    
    public void add(String paramString)
    {
      if (this.First) {
        this.First = false;
      }
      for (;;)
      {
        this.b.append(paramString);
        return;
        this.b.append(this.mSeparator);
      }
    }
    
    public String toString()
    {
      return this.b.toString();
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.eac.Flags
 * JD-Core Version:    0.7.0.1
 */