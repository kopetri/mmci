package org.spongycastle.asn1;

import java.io.IOException;
import java.io.OutputStream;

public class BERSequenceGenerator
  extends BERGenerator
{
  public BERSequenceGenerator(OutputStream paramOutputStream)
    throws IOException
  {
    super(paramOutputStream);
    writeBERHeader(48);
  }
  
  public BERSequenceGenerator(OutputStream paramOutputStream, int paramInt, boolean paramBoolean)
    throws IOException
  {
    super(paramOutputStream, paramInt, paramBoolean);
    writeBERHeader(48);
  }
  
  public void addObject(ASN1Encodable paramASN1Encodable)
    throws IOException
  {
    paramASN1Encodable.toASN1Primitive().encode(new BEROutputStream(this._out));
  }
  
  public void close()
    throws IOException
  {
    writeBEREnd();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.BERSequenceGenerator
 * JD-Core Version:    0.7.0.1
 */