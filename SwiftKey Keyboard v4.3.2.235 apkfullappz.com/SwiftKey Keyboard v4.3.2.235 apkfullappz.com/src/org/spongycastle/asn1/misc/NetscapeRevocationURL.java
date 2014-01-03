package org.spongycastle.asn1.misc;

import org.spongycastle.asn1.DERIA5String;

public class NetscapeRevocationURL
  extends DERIA5String
{
  public NetscapeRevocationURL(DERIA5String paramDERIA5String)
  {
    super(paramDERIA5String.getString());
  }
  
  public String toString()
  {
    return "NetscapeRevocationURL: " + getString();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.misc.NetscapeRevocationURL
 * JD-Core Version:    0.7.0.1
 */