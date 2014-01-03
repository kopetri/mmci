package org.spongycastle.eac.jcajce;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

class NamedEACHelper
  implements EACHelper
{
  private final String providerName;
  
  NamedEACHelper(String paramString)
  {
    this.providerName = paramString;
  }
  
  public KeyFactory createKeyFactory(String paramString)
    throws NoSuchProviderException, NoSuchAlgorithmException
  {
    return KeyFactory.getInstance(paramString, this.providerName);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.eac.jcajce.NamedEACHelper
 * JD-Core Version:    0.7.0.1
 */