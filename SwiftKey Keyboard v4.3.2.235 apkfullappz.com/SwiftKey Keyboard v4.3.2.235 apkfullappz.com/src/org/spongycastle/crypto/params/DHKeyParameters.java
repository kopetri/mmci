package org.spongycastle.crypto.params;

public class DHKeyParameters
  extends AsymmetricKeyParameter
{
  private DHParameters params;
  
  protected DHKeyParameters(boolean paramBoolean, DHParameters paramDHParameters)
  {
    super(paramBoolean);
    this.params = paramDHParameters;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof DHKeyParameters)) {}
    DHKeyParameters localDHKeyParameters;
    do
    {
      return false;
      localDHKeyParameters = (DHKeyParameters)paramObject;
      if (this.params != null) {
        break;
      }
    } while (localDHKeyParameters.getParameters() != null);
    return true;
    return this.params.equals(localDHKeyParameters.getParameters());
  }
  
  public DHParameters getParameters()
  {
    return this.params;
  }
  
  public int hashCode()
  {
    if (isPrivate()) {}
    for (int i = 0;; i = 1)
    {
      if (this.params != null) {
        i ^= this.params.hashCode();
      }
      return i;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.params.DHKeyParameters
 * JD-Core Version:    0.7.0.1
 */