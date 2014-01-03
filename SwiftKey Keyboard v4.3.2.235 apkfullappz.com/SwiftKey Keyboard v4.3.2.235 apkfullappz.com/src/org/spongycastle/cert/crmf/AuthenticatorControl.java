package org.spongycastle.cert.crmf;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.DERUTF8String;
import org.spongycastle.asn1.crmf.CRMFObjectIdentifiers;

public class AuthenticatorControl
  implements Control
{
  private static final ASN1ObjectIdentifier type = CRMFObjectIdentifiers.id_regCtrl_authenticator;
  private final DERUTF8String token;
  
  public AuthenticatorControl(String paramString)
  {
    this.token = new DERUTF8String(paramString);
  }
  
  public AuthenticatorControl(DERUTF8String paramDERUTF8String)
  {
    this.token = paramDERUTF8String;
  }
  
  public ASN1ObjectIdentifier getType()
  {
    return type;
  }
  
  public ASN1Encodable getValue()
  {
    return this.token;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.crmf.AuthenticatorControl
 * JD-Core Version:    0.7.0.1
 */