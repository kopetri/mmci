package org.spongycastle.asn1.icao;

import org.spongycastle.asn1.ASN1ObjectIdentifier;

public abstract interface ICAOObjectIdentifiers
{
  public static final ASN1ObjectIdentifier id_icao;
  public static final ASN1ObjectIdentifier id_icao_aaProtocolObject;
  public static final ASN1ObjectIdentifier id_icao_cscaMasterList;
  public static final ASN1ObjectIdentifier id_icao_cscaMasterListSigningKey;
  public static final ASN1ObjectIdentifier id_icao_documentTypeList;
  public static final ASN1ObjectIdentifier id_icao_extensions;
  public static final ASN1ObjectIdentifier id_icao_extensions_namechangekeyrollover;
  public static final ASN1ObjectIdentifier id_icao_ldsSecurityObject;
  public static final ASN1ObjectIdentifier id_icao_mrtd;
  public static final ASN1ObjectIdentifier id_icao_mrtd_security;
  
  static
  {
    ASN1ObjectIdentifier localASN1ObjectIdentifier1 = new ASN1ObjectIdentifier("2.23.136");
    id_icao = localASN1ObjectIdentifier1;
    ASN1ObjectIdentifier localASN1ObjectIdentifier2 = localASN1ObjectIdentifier1.branch("1");
    id_icao_mrtd = localASN1ObjectIdentifier2;
    ASN1ObjectIdentifier localASN1ObjectIdentifier3 = localASN1ObjectIdentifier2.branch("1");
    id_icao_mrtd_security = localASN1ObjectIdentifier3;
    id_icao_ldsSecurityObject = localASN1ObjectIdentifier3.branch("1");
    id_icao_cscaMasterList = id_icao_mrtd_security.branch("2");
    id_icao_cscaMasterListSigningKey = id_icao_mrtd_security.branch("3");
    id_icao_documentTypeList = id_icao_mrtd_security.branch("4");
    id_icao_aaProtocolObject = id_icao_mrtd_security.branch("5");
    ASN1ObjectIdentifier localASN1ObjectIdentifier4 = id_icao_mrtd_security.branch("6");
    id_icao_extensions = localASN1ObjectIdentifier4;
    id_icao_extensions_namechangekeyrollover = localASN1ObjectIdentifier4.branch("1");
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.icao.ICAOObjectIdentifiers
 * JD-Core Version:    0.7.0.1
 */