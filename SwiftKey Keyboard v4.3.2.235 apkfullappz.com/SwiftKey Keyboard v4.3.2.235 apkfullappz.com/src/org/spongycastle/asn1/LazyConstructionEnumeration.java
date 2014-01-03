package org.spongycastle.asn1;

import java.io.IOException;
import java.util.Enumeration;

class LazyConstructionEnumeration
  implements Enumeration
{
  private ASN1InputStream aIn;
  private Object nextObj;
  
  public LazyConstructionEnumeration(byte[] paramArrayOfByte)
  {
    this.aIn = new ASN1InputStream(paramArrayOfByte, true);
    this.nextObj = readObject();
  }
  
  private Object readObject()
  {
    try
    {
      ASN1Primitive localASN1Primitive = this.aIn.readObject();
      return localASN1Primitive;
    }
    catch (IOException localIOException)
    {
      throw new ASN1ParsingException("malformed DER construction: " + localIOException, localIOException);
    }
  }
  
  public boolean hasMoreElements()
  {
    return this.nextObj != null;
  }
  
  public Object nextElement()
  {
    Object localObject = this.nextObj;
    this.nextObj = readObject();
    return localObject;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.LazyConstructionEnumeration
 * JD-Core Version:    0.7.0.1
 */