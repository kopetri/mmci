package org.spongycastle.cert.cmp;

import java.io.IOException;
import java.io.OutputStream;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.DEROutputStream;

class CMPUtil
{
  static void derEncodeToStream(ASN1Encodable paramASN1Encodable, OutputStream paramOutputStream)
  {
    DEROutputStream localDEROutputStream = new DEROutputStream(paramOutputStream);
    try
    {
      localDEROutputStream.writeObject(paramASN1Encodable);
      localDEROutputStream.close();
      return;
    }
    catch (IOException localIOException)
    {
      throw new CMPRuntimeException("unable to DER encode object: " + localIOException.getMessage(), localIOException);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.cmp.CMPUtil
 * JD-Core Version:    0.7.0.1
 */