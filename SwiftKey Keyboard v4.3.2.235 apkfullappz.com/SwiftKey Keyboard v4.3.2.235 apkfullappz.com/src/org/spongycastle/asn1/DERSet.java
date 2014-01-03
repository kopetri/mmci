package org.spongycastle.asn1;

import java.io.IOException;
import java.util.Enumeration;

public class DERSet
  extends ASN1Set
{
  private int bodyLength = -1;
  
  public DERSet() {}
  
  public DERSet(ASN1Encodable paramASN1Encodable)
  {
    super(paramASN1Encodable);
  }
  
  public DERSet(ASN1EncodableVector paramASN1EncodableVector)
  {
    super(paramASN1EncodableVector, true);
  }
  
  DERSet(ASN1EncodableVector paramASN1EncodableVector, boolean paramBoolean)
  {
    super(paramASN1EncodableVector, paramBoolean);
  }
  
  public DERSet(ASN1Encodable[] paramArrayOfASN1Encodable)
  {
    super(paramArrayOfASN1Encodable, true);
  }
  
  private int getBodyLength()
    throws IOException
  {
    if (this.bodyLength < 0)
    {
      int i = 0;
      Enumeration localEnumeration = getObjects();
      while (localEnumeration.hasMoreElements()) {
        i += ((ASN1Encodable)localEnumeration.nextElement()).toASN1Primitive().toDERObject().encodedLength();
      }
      this.bodyLength = i;
    }
    return this.bodyLength;
  }
  
  void encode(ASN1OutputStream paramASN1OutputStream)
    throws IOException
  {
    ASN1OutputStream localASN1OutputStream = paramASN1OutputStream.getDERSubStream();
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
 * Qualified Name:     org.spongycastle.asn1.DERSet
 * JD-Core Version:    0.7.0.1
 */