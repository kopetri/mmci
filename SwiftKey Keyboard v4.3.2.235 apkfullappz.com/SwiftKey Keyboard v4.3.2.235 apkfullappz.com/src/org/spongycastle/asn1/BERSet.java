package org.spongycastle.asn1;

import java.io.IOException;
import java.util.Enumeration;

public class BERSet
  extends ASN1Set
{
  public BERSet() {}
  
  public BERSet(ASN1Encodable paramASN1Encodable)
  {
    super(paramASN1Encodable);
  }
  
  public BERSet(ASN1EncodableVector paramASN1EncodableVector)
  {
    super(paramASN1EncodableVector, false);
  }
  
  public BERSet(ASN1Encodable[] paramArrayOfASN1Encodable)
  {
    super(paramArrayOfASN1Encodable, false);
  }
  
  void encode(ASN1OutputStream paramASN1OutputStream)
    throws IOException
  {
    paramASN1OutputStream.write(49);
    paramASN1OutputStream.write(128);
    Enumeration localEnumeration = getObjects();
    while (localEnumeration.hasMoreElements()) {
      paramASN1OutputStream.writeObject((ASN1Encodable)localEnumeration.nextElement());
    }
    paramASN1OutputStream.write(0);
    paramASN1OutputStream.write(0);
  }
  
  int encodedLength()
    throws IOException
  {
    int i = 0;
    Enumeration localEnumeration = getObjects();
    while (localEnumeration.hasMoreElements()) {
      i += ((ASN1Encodable)localEnumeration.nextElement()).toASN1Primitive().encodedLength();
    }
    return 2 + (i + 2);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.BERSet
 * JD-Core Version:    0.7.0.1
 */