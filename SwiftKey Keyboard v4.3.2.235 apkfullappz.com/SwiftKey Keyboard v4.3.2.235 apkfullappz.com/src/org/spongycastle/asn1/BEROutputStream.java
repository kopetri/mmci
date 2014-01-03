package org.spongycastle.asn1;

import java.io.IOException;
import java.io.OutputStream;

public class BEROutputStream
  extends DEROutputStream
{
  public BEROutputStream(OutputStream paramOutputStream)
  {
    super(paramOutputStream);
  }
  
  public void writeObject(Object paramObject)
    throws IOException
  {
    if (paramObject == null)
    {
      writeNull();
      return;
    }
    if ((paramObject instanceof ASN1Primitive))
    {
      ((ASN1Primitive)paramObject).encode(this);
      return;
    }
    if ((paramObject instanceof ASN1Encodable))
    {
      ((ASN1Encodable)paramObject).toASN1Primitive().encode(this);
      return;
    }
    throw new IOException("object not BEREncodable");
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.BEROutputStream
 * JD-Core Version:    0.7.0.1
 */