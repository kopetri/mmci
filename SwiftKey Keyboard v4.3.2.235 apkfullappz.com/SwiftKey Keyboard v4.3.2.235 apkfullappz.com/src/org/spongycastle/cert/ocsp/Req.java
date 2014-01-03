package org.spongycastle.cert.ocsp;

import org.spongycastle.asn1.ocsp.Request;
import org.spongycastle.asn1.x509.Extensions;

public class Req
{
  private Request req;
  
  public Req(Request paramRequest)
  {
    this.req = paramRequest;
  }
  
  public CertificateID getCertID()
  {
    return new CertificateID(this.req.getReqCert());
  }
  
  public Extensions getSingleRequestExtensions()
  {
    return this.req.getSingleRequestExtensions();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.ocsp.Req
 * JD-Core Version:    0.7.0.1
 */