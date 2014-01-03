package org.spongycastle.crypto.tls;

public class SecurityParameters
{
  byte[] clientRandom = null;
  byte[] masterSecret = null;
  byte[] serverRandom = null;
  
  public byte[] getClientRandom()
  {
    return this.clientRandom;
  }
  
  public byte[] getMasterSecret()
  {
    return this.masterSecret;
  }
  
  public byte[] getServerRandom()
  {
    return this.serverRandom;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.tls.SecurityParameters
 * JD-Core Version:    0.7.0.1
 */