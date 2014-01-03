package org.spongycastle.crypto.tls;

import java.io.IOException;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;

public abstract interface TlsAgreementCredentials
  extends TlsCredentials
{
  public abstract byte[] generateAgreement(AsymmetricKeyParameter paramAsymmetricKeyParameter)
    throws IOException;
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.tls.TlsAgreementCredentials
 * JD-Core Version:    0.7.0.1
 */