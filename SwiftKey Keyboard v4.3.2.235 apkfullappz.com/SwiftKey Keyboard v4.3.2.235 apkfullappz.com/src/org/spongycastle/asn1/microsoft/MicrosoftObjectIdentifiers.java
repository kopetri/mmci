package org.spongycastle.asn1.microsoft;

import org.spongycastle.asn1.ASN1ObjectIdentifier;

public abstract interface MicrosoftObjectIdentifiers
{
  public static final ASN1ObjectIdentifier microsoft;
  public static final ASN1ObjectIdentifier microsoftAppPolicies = microsoft.branch("21.10");
  public static final ASN1ObjectIdentifier microsoftCaVersion;
  public static final ASN1ObjectIdentifier microsoftCertTemplateV1;
  public static final ASN1ObjectIdentifier microsoftCertTemplateV2;
  public static final ASN1ObjectIdentifier microsoftPrevCaCertHash;
  
  static
  {
    ASN1ObjectIdentifier localASN1ObjectIdentifier = new ASN1ObjectIdentifier("1.3.6.1.4.1.311");
    microsoft = localASN1ObjectIdentifier;
    microsoftCertTemplateV1 = localASN1ObjectIdentifier.branch("20.2");
    microsoftCaVersion = microsoft.branch("21.1");
    microsoftPrevCaCertHash = microsoft.branch("21.2");
    microsoftCertTemplateV2 = microsoft.branch("21.7");
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.microsoft.MicrosoftObjectIdentifiers
 * JD-Core Version:    0.7.0.1
 */