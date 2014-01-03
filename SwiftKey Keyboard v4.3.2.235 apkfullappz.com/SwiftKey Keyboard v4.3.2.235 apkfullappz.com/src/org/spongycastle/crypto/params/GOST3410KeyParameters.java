package org.spongycastle.crypto.params;

public class GOST3410KeyParameters
  extends AsymmetricKeyParameter
{
  private GOST3410Parameters params;
  
  public GOST3410KeyParameters(boolean paramBoolean, GOST3410Parameters paramGOST3410Parameters)
  {
    super(paramBoolean);
    this.params = paramGOST3410Parameters;
  }
  
  public GOST3410Parameters getParameters()
  {
    return this.params;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.params.GOST3410KeyParameters
 * JD-Core Version:    0.7.0.1
 */