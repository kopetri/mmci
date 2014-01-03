package org.spongycastle.jce.provider;

import java.io.OutputStream;
import java.security.KeyStore.LoadStoreParameter;
import java.security.KeyStore.PasswordProtection;
import java.security.KeyStore.ProtectionParameter;

public class JDKPKCS12StoreParameter
  implements KeyStore.LoadStoreParameter
{
  private OutputStream outputStream;
  private KeyStore.ProtectionParameter protectionParameter;
  private boolean useDEREncoding;
  
  public OutputStream getOutputStream()
  {
    return this.outputStream;
  }
  
  public KeyStore.ProtectionParameter getProtectionParameter()
  {
    return this.protectionParameter;
  }
  
  public boolean isUseDEREncoding()
  {
    return this.useDEREncoding;
  }
  
  public void setOutputStream(OutputStream paramOutputStream)
  {
    this.outputStream = paramOutputStream;
  }
  
  public void setPassword(char[] paramArrayOfChar)
  {
    this.protectionParameter = new KeyStore.PasswordProtection(paramArrayOfChar);
  }
  
  public void setProtectionParameter(KeyStore.ProtectionParameter paramProtectionParameter)
  {
    this.protectionParameter = paramProtectionParameter;
  }
  
  public void setUseDEREncoding(boolean paramBoolean)
  {
    this.useDEREncoding = paramBoolean;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jce.provider.JDKPKCS12StoreParameter
 * JD-Core Version:    0.7.0.1
 */