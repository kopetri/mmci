package org.spongycastle.asn1;

import java.util.Vector;

public class ASN1EncodableVector
{
  Vector v = new Vector();
  
  public void add(ASN1Encodable paramASN1Encodable)
  {
    this.v.addElement(paramASN1Encodable);
  }
  
  public ASN1Encodable get(int paramInt)
  {
    return (ASN1Encodable)this.v.elementAt(paramInt);
  }
  
  public int size()
  {
    return this.v.size();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.ASN1EncodableVector
 * JD-Core Version:    0.7.0.1
 */