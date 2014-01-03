package org.spongycastle.jce.provider;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CertificateParsingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.x509.CertificatePair;
import org.spongycastle.x509.X509CertificatePair;
import org.spongycastle.x509.X509StreamParserSpi;
import org.spongycastle.x509.util.StreamParsingException;

public class X509CertPairParser
  extends X509StreamParserSpi
{
  private InputStream currentStream = null;
  
  private X509CertificatePair readDERCrossCertificatePair(InputStream paramInputStream)
    throws IOException, CertificateParsingException
  {
    return new X509CertificatePair(CertificatePair.getInstance((ASN1Sequence)new ASN1InputStream(paramInputStream).readObject()));
  }
  
  public void engineInit(InputStream paramInputStream)
  {
    this.currentStream = paramInputStream;
    if (!this.currentStream.markSupported()) {
      this.currentStream = new BufferedInputStream(this.currentStream);
    }
  }
  
  public Object engineRead()
    throws StreamParsingException
  {
    try
    {
      this.currentStream.mark(10);
      if (this.currentStream.read() == -1) {
        return null;
      }
      this.currentStream.reset();
      X509CertificatePair localX509CertificatePair = readDERCrossCertificatePair(this.currentStream);
      return localX509CertificatePair;
    }
    catch (Exception localException)
    {
      throw new StreamParsingException(localException.toString(), localException);
    }
  }
  
  public Collection engineReadAll()
    throws StreamParsingException
  {
    ArrayList localArrayList = new ArrayList();
    for (;;)
    {
      X509CertificatePair localX509CertificatePair = (X509CertificatePair)engineRead();
      if (localX509CertificatePair == null) {
        break;
      }
      localArrayList.add(localX509CertificatePair);
    }
    return localArrayList;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jce.provider.X509CertPairParser
 * JD-Core Version:    0.7.0.1
 */