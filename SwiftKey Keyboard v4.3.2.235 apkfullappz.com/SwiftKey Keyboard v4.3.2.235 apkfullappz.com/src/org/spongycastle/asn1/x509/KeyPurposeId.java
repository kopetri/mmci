package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1ObjectIdentifier;

public class KeyPurposeId
  extends ASN1ObjectIdentifier
{
  public static final KeyPurposeId anyExtendedKeyUsage = new KeyPurposeId(X509Extensions.ExtendedKeyUsage.getId() + ".0");
  private static final String id_kp = "1.3.6.1.5.5.7.3";
  public static final KeyPurposeId id_kp_OCSPSigning;
  public static final KeyPurposeId id_kp_capwapAC = new KeyPurposeId("1.3.6.1.5.5.7.3.18");
  public static final KeyPurposeId id_kp_capwapWTP = new KeyPurposeId("1.3.6.1.5.5.7.3.19");
  public static final KeyPurposeId id_kp_clientAuth;
  public static final KeyPurposeId id_kp_codeSigning;
  public static final KeyPurposeId id_kp_dvcs;
  public static final KeyPurposeId id_kp_eapOverLAN;
  public static final KeyPurposeId id_kp_eapOverPPP;
  public static final KeyPurposeId id_kp_emailProtection;
  public static final KeyPurposeId id_kp_ipsecEndSystem;
  public static final KeyPurposeId id_kp_ipsecIKE;
  public static final KeyPurposeId id_kp_ipsecTunnel;
  public static final KeyPurposeId id_kp_ipsecUser;
  public static final KeyPurposeId id_kp_sbgpCertAAServerAuth;
  public static final KeyPurposeId id_kp_scvpClient;
  public static final KeyPurposeId id_kp_scvpServer;
  public static final KeyPurposeId id_kp_scvp_responder;
  public static final KeyPurposeId id_kp_serverAuth = new KeyPurposeId("1.3.6.1.5.5.7.3.1");
  public static final KeyPurposeId id_kp_smartcardlogon = new KeyPurposeId("1.3.6.1.4.1.311.20.2.2");
  public static final KeyPurposeId id_kp_timeStamping;
  
  static
  {
    id_kp_clientAuth = new KeyPurposeId("1.3.6.1.5.5.7.3.2");
    id_kp_codeSigning = new KeyPurposeId("1.3.6.1.5.5.7.3.3");
    id_kp_emailProtection = new KeyPurposeId("1.3.6.1.5.5.7.3.4");
    id_kp_ipsecEndSystem = new KeyPurposeId("1.3.6.1.5.5.7.3.5");
    id_kp_ipsecTunnel = new KeyPurposeId("1.3.6.1.5.5.7.3.6");
    id_kp_ipsecUser = new KeyPurposeId("1.3.6.1.5.5.7.3.7");
    id_kp_timeStamping = new KeyPurposeId("1.3.6.1.5.5.7.3.8");
    id_kp_OCSPSigning = new KeyPurposeId("1.3.6.1.5.5.7.3.9");
    id_kp_dvcs = new KeyPurposeId("1.3.6.1.5.5.7.3.10");
    id_kp_sbgpCertAAServerAuth = new KeyPurposeId("1.3.6.1.5.5.7.3.11");
    id_kp_scvp_responder = new KeyPurposeId("1.3.6.1.5.5.7.3.12");
    id_kp_eapOverPPP = new KeyPurposeId("1.3.6.1.5.5.7.3.13");
    id_kp_eapOverLAN = new KeyPurposeId("1.3.6.1.5.5.7.3.14");
    id_kp_scvpServer = new KeyPurposeId("1.3.6.1.5.5.7.3.15");
    id_kp_scvpClient = new KeyPurposeId("1.3.6.1.5.5.7.3.16");
    id_kp_ipsecIKE = new KeyPurposeId("1.3.6.1.5.5.7.3.17");
  }
  
  public KeyPurposeId(String paramString)
  {
    super(paramString);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x509.KeyPurposeId
 * JD-Core Version:    0.7.0.1
 */