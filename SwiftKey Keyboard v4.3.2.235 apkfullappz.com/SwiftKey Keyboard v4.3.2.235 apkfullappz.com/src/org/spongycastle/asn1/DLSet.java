package org.spongycastle.asn1;

import java.io.IOException;
import java.util.Enumeration;

public class DLSet
  extends ASN1Set
{
  private int bodyLength = -1;
  
  public DLSet() {}
  
  public DLSet(ASN1Encodable paramASN1Encodable)
  {
    super(paramASN1Encodable);
  }
  
  public DLSet(ASN1EncodableVector paramASN1EncodableVector)
  {
    super(paramASN1EncodableVector, false);
  }
  
  public DLSet(ASN1Encodable[] paramArrayOfASN1Encodable)
  {
    super(paramArrayOfASN1Encodable, false);
  }
  
  private int getBodyLength()
    throws IOException
  {
    if (this.bodyLength < 0)
    {
      int i = 0;
      Enumeration localEnumeration = getObjects();
      while (localEnumeration.hasMoreElements()) {
        i += ((ASN1Encodable)localEnumeration.nextElement()).toASN1Primitive().toDLObject().encodedLength();
      }
      this.bodyLength = i;
    }
    return this.bodyLength;
  }
  
  void encode(ASN1OutputStream paramASN1OutputStream)
    throws IOException
  {
    ASN1OutputStream localASN1OutputStream = paramASN1OutputStream.getDLSubStream();
    int i = getBodyLength();
    paramASN1OutputStream.write(49);
    paramASN1OutputStream.writeLength(i);
    Enumeration localEnumeration = getObjects();
    while (localEnumeration.hasMoreElements()) {
      localASN1OutputStream.writeObject((ASN1Encodable)localEnumeration.nextElement());
    }
  }
  
  int encodedLength()
    throws IOException
  {
    int i = getBodyLength();
    return i + (1 + StreamUtil.calculateBodyLength(i));
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.DLSet
 * JD-Core Version:    0.7.0.1
 */