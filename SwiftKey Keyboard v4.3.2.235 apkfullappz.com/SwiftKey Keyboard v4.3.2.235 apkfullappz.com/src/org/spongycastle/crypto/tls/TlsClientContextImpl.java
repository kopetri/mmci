package org.spongycastle.crypto.tls;

import java.security.SecureRandom;

class TlsClientContextImpl
  implements TlsClientContext
{
  private ProtocolVersion clientVersion = null;
  private SecureRandom secureRandom;
  private SecurityParameters securityParameters;
  private ProtocolVersion serverVersion = null;
  private Object userObject = null;
  
  TlsClientContextImpl(SecureRandom paramSecureRandom, SecurityParameters paramSecurityParameters)
  {
    this.secureRandom = paramSecureRandom;
    this.securityParameters = paramSecurityParameters;
  }
  
  public ProtocolVersion getClientVersion()
  {
    return this.clientVersion;
  }
  
  public SecureRandom getSecureRandom()
  {
    return this.secureRandom;
  }
  
  public SecurityParameters getSecurityParameters()
  {
    return this.securityParameters;
  }
  
  public ProtocolVersion getServerVersion()
  {
    return this.serverVersion;
  }
  
  public Object getUserObject()
  {
    return this.userObject;
  }
  
  public void setClientVersion(ProtocolVersion paramProtocolVersion)
  {
    this.clientVersion = paramProtocolVersion;
  }
  
  public void setServerVersion(ProtocolVersion paramProtocolVersion)
  {
    this.serverVersion = paramProtocolVersion;
  }
  
  public void setUserObject(Object paramObject)
  {
    this.userObject = paramObject;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.tls.TlsClientContextImpl
 * JD-Core Version:    0.7.0.1
 */