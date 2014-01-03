package org.spongycastle.asn1;

import java.io.IOException;

public class DERNull
  extends ASN1Null
{
  public static final DERNull INSTANCE = new DERNull();
  private static final byte[] zeroBytes = new byte[0];
  
  void encode(ASN1OutputStream paramASN1OutputStream)
    throws IOException
  {
    paramASN1OutputStream.writeEncoded(5, zeroBytes);
  }
  
  int encodedLength()
  {
    return 2;
  }
  
  boolean isConstructed()
  {
    return false;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.DERNull
 * JD-Core Version:    0.7.0.1
 */