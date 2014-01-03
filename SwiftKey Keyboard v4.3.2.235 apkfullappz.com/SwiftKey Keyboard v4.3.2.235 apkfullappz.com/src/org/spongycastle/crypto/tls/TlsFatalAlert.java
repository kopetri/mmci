package org.spongycastle.crypto.tls;

import java.io.IOException;

public class TlsFatalAlert
  extends IOException
{
  private static final long serialVersionUID = 3584313123679111168L;
  private short alertDescription;
  
  public TlsFatalAlert(short paramShort)
  {
    this.alertDescription = paramShort;
  }
  
  public short getAlertDescription()
  {
    return this.alertDescription;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.tls.TlsFatalAlert
 * JD-Core Version:    0.7.0.1
 */