package org.spongycastle.asn1.eac;

import java.util.Hashtable;

public class BidirectionalMap
  extends Hashtable
{
  private static final long serialVersionUID = -7457289971962812909L;
  Hashtable reverseMap = new Hashtable();
  
  public Object getReverse(Object paramObject)
  {
    return this.reverseMap.get(paramObject);
  }
  
  public Object put(Object paramObject1, Object paramObject2)
  {
    this.reverseMap.put(paramObject2, paramObject1);
    return super.put(paramObject1, paramObject2);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.eac.BidirectionalMap
 * JD-Core Version:    0.7.0.1
 */