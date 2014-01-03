package org.spongycastle.crypto.params;

import org.spongycastle.crypto.CipherParameters;

public class AsymmetricKeyParameter
  implements CipherParameters
{
  boolean privateKey;
  
  public AsymmetricKeyParameter(boolean paramBoolean)
  {
    this.privateKey = paramBoolean;
  }
  
  public boolean isPrivate()
  {
    return this.privateKey;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.params.AsymmetricKeyParameter
 * JD-Core Version:    0.7.0.1
 */