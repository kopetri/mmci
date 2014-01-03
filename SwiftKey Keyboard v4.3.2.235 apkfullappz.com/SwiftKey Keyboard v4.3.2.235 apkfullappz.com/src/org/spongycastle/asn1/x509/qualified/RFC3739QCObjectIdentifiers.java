package org.spongycastle.asn1.x509.qualified;

import org.spongycastle.asn1.ASN1ObjectIdentifier;

public abstract interface RFC3739QCObjectIdentifiers
{
  public static final ASN1ObjectIdentifier id_qcs;
  public static final ASN1ObjectIdentifier id_qcs_pkixQCSyntax_v1;
  public static final ASN1ObjectIdentifier id_qcs_pkixQCSyntax_v2 = id_qcs.branch("2");
  
  static
  {
    ASN1ObjectIdentifier localASN1ObjectIdentifier = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.11");
    id_qcs = localASN1ObjectIdentifier;
    id_qcs_pkixQCSyntax_v1 = localASN1ObjectIdentifier.branch("1");
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x509.qualified.RFC3739QCObjectIdentifiers
 * JD-Core Version:    0.7.0.1
 */