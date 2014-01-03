package org.spongycastle.eac.operator.jcajce;

import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Signature;

class ProviderEACHelper
  extends EACHelper
{
  private final Provider provider;
  
  ProviderEACHelper(Provider paramProvider)
  {
    this.provider = paramProvider;
  }
  
  protected Signature createSignature(String paramString)
    throws NoSuchAlgorithmException
  {
    return Signature.getInstance(paramString, this.provider);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.eac.operator.jcajce.ProviderEACHelper
 * JD-Core Version:    0.7.0.1
 */