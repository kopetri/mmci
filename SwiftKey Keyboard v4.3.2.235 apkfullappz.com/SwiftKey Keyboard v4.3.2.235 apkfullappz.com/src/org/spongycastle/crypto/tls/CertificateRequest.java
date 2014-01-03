package org.spongycastle.crypto.tls;

import java.util.Vector;

public class CertificateRequest
{
  private Vector certificateAuthorities;
  private short[] certificateTypes;
  
  public CertificateRequest(short[] paramArrayOfShort, Vector paramVector)
  {
    this.certificateTypes = paramArrayOfShort;
    this.certificateAuthorities = paramVector;
  }
  
  public Vector getCertificateAuthorities()
  {
    return this.certificateAuthorities;
  }
  
  public short[] getCertificateTypes()
  {
    return this.certificateTypes;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.tls.CertificateRequest
 * JD-Core Version:    0.7.0.1
 */