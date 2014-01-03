package org.spongycastle.asn1.isismtt;

import org.spongycastle.asn1.ASN1ObjectIdentifier;

public abstract interface ISISMTTObjectIdentifiers
{
  public static final ASN1ObjectIdentifier id_isismtt;
  public static final ASN1ObjectIdentifier id_isismtt_at;
  public static final ASN1ObjectIdentifier id_isismtt_at_PKReference;
  public static final ASN1ObjectIdentifier id_isismtt_at_additionalInformation = id_isismtt_at.branch("15");
  public static final ASN1ObjectIdentifier id_isismtt_at_admission;
  public static final ASN1ObjectIdentifier id_isismtt_at_certHash;
  public static final ASN1ObjectIdentifier id_isismtt_at_certInDirSince;
  public static final ASN1ObjectIdentifier id_isismtt_at_dateOfCertGen;
  public static final ASN1ObjectIdentifier id_isismtt_at_declarationOfMajority;
  public static final ASN1ObjectIdentifier id_isismtt_at_iCCSN;
  public static final ASN1ObjectIdentifier id_isismtt_at_liabilityLimitationFlag = new ASN1ObjectIdentifier("0.2.262.1.10.12.0");
  public static final ASN1ObjectIdentifier id_isismtt_at_monetaryLimit;
  public static final ASN1ObjectIdentifier id_isismtt_at_nameAtBirth;
  public static final ASN1ObjectIdentifier id_isismtt_at_namingAuthorities;
  public static final ASN1ObjectIdentifier id_isismtt_at_procuration;
  public static final ASN1ObjectIdentifier id_isismtt_at_requestedCertificate;
  public static final ASN1ObjectIdentifier id_isismtt_at_restriction;
  public static final ASN1ObjectIdentifier id_isismtt_at_retrieveIfAllowed;
  public static final ASN1ObjectIdentifier id_isismtt_cp;
  public static final ASN1ObjectIdentifier id_isismtt_cp_accredited;
  
  static
  {
    ASN1ObjectIdentifier localASN1ObjectIdentifier1 = new ASN1ObjectIdentifier("1.3.36.8");
    id_isismtt = localASN1ObjectIdentifier1;
    ASN1ObjectIdentifier localASN1ObjectIdentifier2 = localASN1ObjectIdentifier1.branch("1");
    id_isismtt_cp = localASN1ObjectIdentifier2;
    id_isismtt_cp_accredited = localASN1ObjectIdentifier2.branch("1");
    ASN1ObjectIdentifier localASN1ObjectIdentifier3 = id_isismtt.branch("3");
    id_isismtt_at = localASN1ObjectIdentifier3;
    id_isismtt_at_dateOfCertGen = localASN1ObjectIdentifier3.branch("1");
    id_isismtt_at_procuration = id_isismtt_at.branch("2");
    id_isismtt_at_admission = id_isismtt_at.branch("3");
    id_isismtt_at_monetaryLimit = id_isismtt_at.branch("4");
    id_isismtt_at_declarationOfMajority = id_isismtt_at.branch("5");
    id_isismtt_at_iCCSN = id_isismtt_at.branch("6");
    id_isismtt_at_PKReference = id_isismtt_at.branch("7");
    id_isismtt_at_restriction = id_isismtt_at.branch("8");
    id_isismtt_at_retrieveIfAllowed = id_isismtt_at.branch("9");
    id_isismtt_at_requestedCertificate = id_isismtt_at.branch("10");
    id_isismtt_at_namingAuthorities = id_isismtt_at.branch("11");
    id_isismtt_at_certInDirSince = id_isismtt_at.branch("12");
    id_isismtt_at_certHash = id_isismtt_at.branch("13");
    id_isismtt_at_nameAtBirth = id_isismtt_at.branch("14");
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.isismtt.ISISMTTObjectIdentifiers
 * JD-Core Version:    0.7.0.1
 */