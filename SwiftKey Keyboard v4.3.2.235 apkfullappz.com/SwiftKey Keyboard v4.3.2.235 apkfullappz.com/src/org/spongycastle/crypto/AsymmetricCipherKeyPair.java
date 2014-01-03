package org.spongycastle.crypto;

public class AsymmetricCipherKeyPair
{
  private CipherParameters privateParam;
  private CipherParameters publicParam;
  
  public AsymmetricCipherKeyPair(CipherParameters paramCipherParameters1, CipherParameters paramCipherParameters2)
  {
    this.publicParam = paramCipherParameters1;
    this.privateParam = paramCipherParameters2;
  }
  
  public CipherParameters getPrivate()
  {
    return this.privateParam;
  }
  
  public CipherParameters getPublic()
  {
    return this.publicParam;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.AsymmetricCipherKeyPair
 * JD-Core Version:    0.7.0.1
 */