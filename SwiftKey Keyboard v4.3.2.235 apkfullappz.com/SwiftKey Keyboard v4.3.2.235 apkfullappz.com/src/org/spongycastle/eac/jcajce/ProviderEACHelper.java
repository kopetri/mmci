package org.spongycastle.eac.jcajce;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;

class ProviderEACHelper
  implements EACHelper
{
  private final Provider provider;
  
  ProviderEACHelper(Provider paramProvider)
  {
    this.provider = paramProvider;
  }
  
  public KeyFactory createKeyFactory(String paramString)
    throws NoSuchAlgorithmException
  {
    return KeyFactory.getInstance(paramString, this.provider);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.eac.jcajce.ProviderEACHelper
 * JD-Core Version:    0.7.0.1
 */