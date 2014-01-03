package org.spongycastle.asn1.crmf;

import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;

public abstract interface CRMFObjectIdentifiers
{
  public static final ASN1ObjectIdentifier id_ct_encKeyWithID = new ASN1ObjectIdentifier(PKCSObjectIdentifiers.id_ct + ".21");
  public static final ASN1ObjectIdentifier id_pkip;
  public static final ASN1ObjectIdentifier id_pkix;
  public static final ASN1ObjectIdentifier id_regCtrl;
  public static final ASN1ObjectIdentifier id_regCtrl_authenticator;
  public static final ASN1ObjectIdentifier id_regCtrl_pkiArchiveOptions;
  public static final ASN1ObjectIdentifier id_regCtrl_pkiPublicationInfo;
  public static final ASN1ObjectIdentifier id_regCtrl_regToken;
  
  static
  {
    ASN1ObjectIdentifier localASN1ObjectIdentifier1 = new ASN1ObjectIdentifier("1.3.6.1.5.5.7");
    id_pkix = localASN1ObjectIdentifier1;
    ASN1ObjectIdentifier localASN1ObjectIdentifier2 = localASN1ObjectIdentifier1.branch("5");
    id_pkip = localASN1ObjectIdentifier2;
    ASN1ObjectIdentifier localASN1ObjectIdentifier3 = localASN1ObjectIdentifier2.branch("1");
    id_regCtrl = localASN1ObjectIdentifier3;
    id_regCtrl_regToken = localASN1ObjectIdentifier3.branch("1");
    id_regCtrl_authenticator = id_regCtrl.branch("2");
    id_regCtrl_pkiPublicationInfo = id_regCtrl.branch("3");
    id_regCtrl_pkiArchiveOptions = id_regCtrl.branch("4");
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.crmf.CRMFObjectIdentifiers
 * JD-Core Version:    0.7.0.1
 */