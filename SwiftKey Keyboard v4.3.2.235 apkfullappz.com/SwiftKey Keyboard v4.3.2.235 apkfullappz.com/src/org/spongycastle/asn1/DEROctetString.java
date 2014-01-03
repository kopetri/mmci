package org.spongycastle.asn1;

import java.io.IOException;

public class DEROctetString
  extends ASN1OctetString
{
  public DEROctetString(ASN1Encodable paramASN1Encodable)
    throws IOException
  {
    super(paramASN1Encodable.toASN1Primitive().getEncoded("DER"));
  }
  
  public DEROctetString(byte[] paramArrayOfByte)
  {
    super(paramArrayOfByte);
  }
  
  static void encode(DEROutputStream paramDEROutputStream, byte[] paramArrayOfByte)
    throws IOException
  {
    paramDEROutputStream.writeEncoded(4, paramArrayOfByte);
  }
  
  void encode(ASN1OutputStream paramASN1OutputStream)
    throws IOException
  {
    paramASN1OutputStream.writeEncoded(4, this.string);
  }
  
  int encodedLength()
  {
    return 1 + StreamUtil.calculateBodyLength(this.string.length) + this.string.length;
  }
  
  boolean isConstructed()
  {
    return false;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.DEROctetString
 * JD-Core Version:    0.7.0.1
 */