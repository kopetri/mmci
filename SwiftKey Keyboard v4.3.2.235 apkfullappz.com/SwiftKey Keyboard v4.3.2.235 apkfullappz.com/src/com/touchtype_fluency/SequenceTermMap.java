package com.touchtype_fluency;

public class SequenceTermMap
{
  private final Sequence seq;
  private final TermPosition[] termMap;
  
  public SequenceTermMap(Sequence paramSequence, TermPosition[] paramArrayOfTermPosition)
  {
    this.seq = paramSequence;
    this.termMap = paramArrayOfTermPosition;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool1 = paramObject instanceof SequenceTermMap;
    boolean bool2 = false;
    if (bool1)
    {
      SequenceTermMap localSequenceTermMap = (SequenceTermMap)paramObject;
      boolean bool3 = this.seq.equals(localSequenceTermMap.seq);
      bool2 = false;
      if (bool3)
      {
        boolean bool4 = this.termMap.equals(localSequenceTermMap.termMap);
        bool2 = false;
        if (bool4) {
          bool2 = true;
        }
      }
    }
    return bool2;
  }
  
  public Sequence getSeq()
  {
    return this.seq;
  }
  
  public TermPosition[] getTermMap()
  {
    return this.termMap;
  }
  
  public int hashCode()
  {
    return 149 * this.seq.hashCode() + this.termMap.hashCode();
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    for (TermPosition localTermPosition : this.termMap) {
      localStringBuilder.append("(").append(localTermPosition).append("), ");
    }
    localStringBuilder.append("seq: ").append(this.seq.toString()).append(", termMap: [").append(localStringBuilder).append("]");
    return localStringBuilder.toString();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.SequenceTermMap
 * JD-Core Version:    0.7.0.1
 */