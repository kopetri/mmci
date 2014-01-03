package org.spongycastle.asn1.x509.qualified;

import org.spongycastle.asn1.ASN1ObjectIdentifier;

public abstract interface ETSIQCObjectIdentifiers
{
  public static final ASN1ObjectIdentifier id_etsi_qcs;
  public static final ASN1ObjectIdentifier id_etsi_qcs_LimiteValue;
  public static final ASN1ObjectIdentifier id_etsi_qcs_QcCompliance;
  public static final ASN1ObjectIdentifier id_etsi_qcs_QcSSCD = id_etsi_qcs.branch("4");
  public static final ASN1ObjectIdentifier id_etsi_qcs_RetentionPeriod;
  
  static
  {
    ASN1ObjectIdentifier localASN1ObjectIdentifier = new ASN1ObjectIdentifier("0.4.0.1862.1");
    id_etsi_qcs = localASN1ObjectIdentifier;
    id_etsi_qcs_QcCompliance = localASN1ObjectIdentifier.branch("1");
    id_etsi_qcs_LimiteValue = id_etsi_qcs.branch("2");
    id_etsi_qcs_RetentionPeriod = id_etsi_qcs.branch("3");
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x509.qualified.ETSIQCObjectIdentifiers
 * JD-Core Version:    0.7.0.1
 */