package org.spongycastle.asn1.util;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Primitive;

public class DERDump
  extends ASN1Dump
{
  public static String dumpAsString(ASN1Encodable paramASN1Encodable)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    _dumpAsString("", false, paramASN1Encodable.toASN1Primitive(), localStringBuffer);
    return localStringBuffer.toString();
  }
  
  public static String dumpAsString(ASN1Primitive paramASN1Primitive)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    _dumpAsString("", false, paramASN1Primitive, localStringBuffer);
    return localStringBuffer.toString();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.util.DERDump
 * JD-Core Version:    0.7.0.1
 */