package org.spongycastle.asn1.x509.sigi;

import org.spongycastle.asn1.ASN1ObjectIdentifier;

public abstract interface SigIObjectIdentifiers
{
  public static final ASN1ObjectIdentifier id_sigi = new ASN1ObjectIdentifier("1.3.36.8");
  public static final ASN1ObjectIdentifier id_sigi_cp;
  public static final ASN1ObjectIdentifier id_sigi_cp_sigconform = new ASN1ObjectIdentifier(id_sigi_cp + ".1");
  public static final ASN1ObjectIdentifier id_sigi_kp = new ASN1ObjectIdentifier(id_sigi + ".2");
  public static final ASN1ObjectIdentifier id_sigi_kp_directoryService;
  public static final ASN1ObjectIdentifier id_sigi_on;
  public static final ASN1ObjectIdentifier id_sigi_on_personalData;
  
  static
  {
    id_sigi_cp = new ASN1ObjectIdentifier(id_sigi + ".1");
    id_sigi_on = new ASN1ObjectIdentifier(id_sigi + ".4");
    id_sigi_kp_directoryService = new ASN1ObjectIdentifier(id_sigi_kp + ".1");
    id_sigi_on_personalData = new ASN1ObjectIdentifier(id_sigi_on + ".1");
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x509.sigi.SigIObjectIdentifiers
 * JD-Core Version:    0.7.0.1
 */