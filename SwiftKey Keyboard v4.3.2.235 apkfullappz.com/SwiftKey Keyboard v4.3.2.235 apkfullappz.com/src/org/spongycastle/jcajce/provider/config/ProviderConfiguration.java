package org.spongycastle.jcajce.provider.config;

import javax.crypto.spec.DHParameterSpec;
import org.spongycastle.jce.spec.ECParameterSpec;

public abstract interface ProviderConfiguration
{
  public abstract DHParameterSpec getDHDefaultParameters();
  
  public abstract ECParameterSpec getEcImplicitlyCa();
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jcajce.provider.config.ProviderConfiguration
 * JD-Core Version:    0.7.0.1
 */