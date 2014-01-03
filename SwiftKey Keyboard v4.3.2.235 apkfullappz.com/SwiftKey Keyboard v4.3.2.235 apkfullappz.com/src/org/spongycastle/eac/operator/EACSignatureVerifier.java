package org.spongycastle.eac.operator;

import java.io.OutputStream;
import org.spongycastle.asn1.ASN1ObjectIdentifier;

public abstract interface EACSignatureVerifier
{
  public abstract OutputStream getOutputStream();
  
  public abstract ASN1ObjectIdentifier getUsageIdentifier();
  
  public abstract boolean verify(byte[] paramArrayOfByte);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.eac.operator.EACSignatureVerifier
 * JD-Core Version:    0.7.0.1
 */