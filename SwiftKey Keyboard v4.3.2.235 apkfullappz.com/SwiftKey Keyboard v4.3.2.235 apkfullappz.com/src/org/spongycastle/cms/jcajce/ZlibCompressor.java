package org.spongycastle.cms.jcajce;

import java.io.OutputStream;
import java.util.zip.DeflaterOutputStream;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.operator.OutputCompressor;

public class ZlibCompressor
  implements OutputCompressor
{
  private static final String ZLIB = "1.2.840.113549.1.9.16.3.8";
  
  public AlgorithmIdentifier getAlgorithmIdentifier()
  {
    return new AlgorithmIdentifier(new ASN1ObjectIdentifier("1.2.840.113549.1.9.16.3.8"));
  }
  
  public OutputStream getOutputStream(OutputStream paramOutputStream)
  {
    return new DeflaterOutputStream(paramOutputStream);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.jcajce.ZlibCompressor
 * JD-Core Version:    0.7.0.1
 */