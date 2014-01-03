package com.touchtype_fluency;

public class TermPosition
{
  private final Integer begin;
  private final Integer size;
  
  public TermPosition(int paramInt1, int paramInt2)
  {
    this.begin = Integer.valueOf(paramInt1);
    this.size = Integer.valueOf(paramInt2);
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool1 = paramObject instanceof TermPosition;
    boolean bool2 = false;
    if (bool1)
    {
      TermPosition localTermPosition = (TermPosition)paramObject;
      boolean bool3 = this.begin.equals(localTermPosition.begin);
      bool2 = false;
      if (bool3)
      {
        boolean bool4 = this.size.equals(localTermPosition.size);
        bool2 = false;
        if (bool4) {
          bool2 = true;
        }
      }
    }
    return bool2;
  }
  
  public int getBegin()
  {
    return this.begin.intValue();
  }
  
  public int getEnd()
  {
    return this.begin.intValue() + this.size.intValue();
  }
  
  public int getSize()
  {
    return this.size.intValue();
  }
  
  public int hashCode()
  {
    return 149 * this.begin.hashCode() + this.size.hashCode();
  }
  
  public String toString()
  {
    return "begin: " + this.begin + ", size: " + this.size;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.TermPosition
 * JD-Core Version:    0.7.0.1
 */