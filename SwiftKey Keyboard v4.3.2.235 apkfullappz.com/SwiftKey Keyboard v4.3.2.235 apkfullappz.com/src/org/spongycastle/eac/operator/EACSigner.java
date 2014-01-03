package org.spongycastle.eac.operator;

import java.io.OutputStream;
import org.spongycastle.asn1.ASN1ObjectIdentifier;

public abstract interface EACSigner
{
  public abstract OutputStream getOutputStream();
  
  public abstract byte[] getSignature();
  
  public abstract ASN1ObjectIdentifier getUsageIdentifier();
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.eac.operator.EACSigner
 * JD-Core Version:    0.7.0.1
 */