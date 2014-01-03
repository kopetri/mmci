package org.spongycastle.crypto.params;

public class NTRUEncryptionKeyParameters
  extends AsymmetricKeyParameter
{
  protected final NTRUEncryptionParameters params;
  
  public NTRUEncryptionKeyParameters(boolean paramBoolean, NTRUEncryptionParameters paramNTRUEncryptionParameters)
  {
    super(paramBoolean);
    this.params = paramNTRUEncryptionParameters;
  }
  
  public NTRUEncryptionParameters getParameters()
  {
    return this.params;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.params.NTRUEncryptionKeyParameters
 * JD-Core Version:    0.7.0.1
 */