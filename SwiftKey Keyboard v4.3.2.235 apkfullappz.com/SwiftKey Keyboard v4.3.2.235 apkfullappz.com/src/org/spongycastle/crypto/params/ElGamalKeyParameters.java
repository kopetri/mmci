package org.spongycastle.crypto.params;

public class ElGamalKeyParameters
  extends AsymmetricKeyParameter
{
  private ElGamalParameters params;
  
  protected ElGamalKeyParameters(boolean paramBoolean, ElGamalParameters paramElGamalParameters)
  {
    super(paramBoolean);
    this.params = paramElGamalParameters;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof ElGamalKeyParameters)) {}
    ElGamalKeyParameters localElGamalKeyParameters;
    do
    {
      return false;
      localElGamalKeyParameters = (ElGamalKeyParameters)paramObject;
      if (this.params != null) {
        break;
      }
    } while (localElGamalKeyParameters.getParameters() != null);
    return true;
    return this.params.equals(localElGamalKeyParameters.getParameters());
  }
  
  public ElGamalParameters getParameters()
  {
    return this.params;
  }
  
  public int hashCode()
  {
    if (this.params != null) {
      return this.params.hashCode();
    }
    return 0;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.params.ElGamalKeyParameters
 * JD-Core Version:    0.7.0.1
 */