package org.spongycastle.crypto.tls;

import java.security.SecureRandom;

public abstract interface TlsClientContext
{
  public abstract ProtocolVersion getClientVersion();
  
  public abstract SecureRandom getSecureRandom();
  
  public abstract SecurityParameters getSecurityParameters();
  
  public abstract ProtocolVersion getServerVersion();
  
  public abstract Object getUserObject();
  
  public abstract void setUserObject(Object paramObject);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.tls.TlsClientContext
 * JD-Core Version:    0.7.0.1
 */