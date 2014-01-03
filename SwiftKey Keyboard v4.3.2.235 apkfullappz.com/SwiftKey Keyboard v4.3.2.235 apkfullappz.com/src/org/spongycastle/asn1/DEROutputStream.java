package org.spongycastle.asn1;

import java.io.IOException;
import java.io.OutputStream;

public class DEROutputStream
  extends ASN1OutputStream
{
  public DEROutputStream(OutputStream paramOutputStream)
  {
    super(paramOutputStream);
  }
  
  ASN1OutputStream getDERSubStream()
  {
    return this;
  }
  
  ASN1OutputStream getDLSubStream()
  {
    return this;
  }
  
  public void writeObject(ASN1Encodable paramASN1Encodable)
    throws IOException
  {
    if (paramASN1Encodable != null)
    {
      paramASN1Encodable.toASN1Primitive().toDERObject().encode(this);
      return;
    }
    throw new IOException("null object detected");
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.DEROutputStream
 * JD-Core Version:    0.7.0.1
 */