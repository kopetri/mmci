package org.spongycastle.crypto.engines;

public class SEEDWrapEngine
  extends RFC3394WrapEngine
{
  public SEEDWrapEngine()
  {
    super(new SEEDEngine());
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.engines.SEEDWrapEngine
 * JD-Core Version:    0.7.0.1
 */