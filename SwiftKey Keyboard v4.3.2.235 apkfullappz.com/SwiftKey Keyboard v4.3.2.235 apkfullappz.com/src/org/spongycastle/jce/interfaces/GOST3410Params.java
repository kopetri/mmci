package org.spongycastle.jce.interfaces;

import org.spongycastle.jce.spec.GOST3410PublicKeyParameterSetSpec;

public abstract interface GOST3410Params
{
  public abstract String getDigestParamSetOID();
  
  public abstract String getEncryptionParamSetOID();
  
  public abstract String getPublicKeyParamSetOID();
  
  public abstract GOST3410PublicKeyParameterSetSpec getPublicKeyParameters();
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jce.interfaces.GOST3410Params
 * JD-Core Version:    0.7.0.1
 */