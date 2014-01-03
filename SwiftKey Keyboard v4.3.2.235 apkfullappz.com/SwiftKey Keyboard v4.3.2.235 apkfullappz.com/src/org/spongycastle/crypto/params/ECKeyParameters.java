package org.spongycastle.crypto.params;

public class ECKeyParameters
  extends AsymmetricKeyParameter
{
  ECDomainParameters params;
  
  protected ECKeyParameters(boolean paramBoolean, ECDomainParameters paramECDomainParameters)
  {
    super(paramBoolean);
    this.params = paramECDomainParameters;
  }
  
  public ECDomainParameters getParameters()
  {
    return this.params;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.params.ECKeyParameters
 * JD-Core Version:    0.7.0.1
 */