package org.spongycastle.cert.crmf;

import java.io.IOException;
import java.io.OutputStream;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.DEROutputStream;
import org.spongycastle.asn1.x509.ExtensionsGenerator;
import org.spongycastle.cert.CertIOException;

class CRMFUtil
{
  static void addExtension(ExtensionsGenerator paramExtensionsGenerator, ASN1ObjectIdentifier paramASN1ObjectIdentifier, boolean paramBoolean, ASN1Encodable paramASN1Encodable)
    throws CertIOException
  {
    try
    {
      paramExtensionsGenerator.addExtension(paramASN1ObjectIdentifier, paramBoolean, paramASN1Encodable);
      return;
    }
    catch (IOException localIOException)
    {
      throw new CertIOException("cannot encode extension: " + localIOException.getMessage(), localIOException);
    }
  }
  
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
      throw new CRMFRuntimeException("unable to DER encode object: " + localIOException.getMessage(), localIOException);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.crmf.CRMFUtil
 * JD-Core Version:    0.7.0.1
 */