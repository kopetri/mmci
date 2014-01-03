package org.spongycastle.asn1.x9;

public abstract class X9ECParametersHolder
{
  private X9ECParameters params;
  
  protected abstract X9ECParameters createParameters();
  
  public X9ECParameters getParameters()
  {
    if (this.params == null) {
      this.params = createParameters();
    }
    return this.params;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x9.X9ECParametersHolder
 * JD-Core Version:    0.7.0.1
 */