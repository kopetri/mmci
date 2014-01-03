package org.spongycastle.asn1.misc;

import org.spongycastle.asn1.ASN1ObjectIdentifier;

public abstract interface MiscObjectIdentifiers
{
  public static final ASN1ObjectIdentifier entrust;
  public static final ASN1ObjectIdentifier entrustVersionExtension;
  public static final ASN1ObjectIdentifier netscape;
  public static final ASN1ObjectIdentifier netscapeBaseURL;
  public static final ASN1ObjectIdentifier netscapeCARevocationURL;
  public static final ASN1ObjectIdentifier netscapeCApolicyURL;
  public static final ASN1ObjectIdentifier netscapeCertComment;
  public static final ASN1ObjectIdentifier netscapeCertType;
  public static final ASN1ObjectIdentifier netscapeRenewalURL;
  public static final ASN1ObjectIdentifier netscapeRevocationURL;
  public static final ASN1ObjectIdentifier netscapeSSLServerName;
  public static final ASN1ObjectIdentifier novell;
  public static final ASN1ObjectIdentifier novellSecurityAttribs;
  public static final ASN1ObjectIdentifier verisign;
  public static final ASN1ObjectIdentifier verisignCzagExtension;
  public static final ASN1ObjectIdentifier verisignDnbDunsNumber;
  
  static
  {
    ASN1ObjectIdentifier localASN1ObjectIdentifier1 = new ASN1ObjectIdentifier("2.16.840.1.113730.1");
    netscape = localASN1ObjectIdentifier1;
    netscapeCertType = localASN1ObjectIdentifier1.branch("1");
    netscapeBaseURL = netscape.branch("2");
    netscapeRevocationURL = netscape.branch("3");
    netscapeCARevocationURL = netscape.branch("4");
    netscapeRenewalURL = netscape.branch("7");
    netscapeCApolicyURL = netscape.branch("8");
    netscapeSSLServerName = netscape.branch("12");
    netscapeCertComment = netscape.branch("13");
    ASN1ObjectIdentifier localASN1ObjectIdentifier2 = new ASN1ObjectIdentifier("2.16.840.1.113733.1");
    verisign = localASN1ObjectIdentifier2;
    verisignCzagExtension = localASN1ObjectIdentifier2.branch("6.3");
    verisignDnbDunsNumber = verisign.branch("6.15");
    ASN1ObjectIdentifier localASN1ObjectIdentifier3 = new ASN1ObjectIdentifier("2.16.840.1.113719");
    novell = localASN1ObjectIdentifier3;
    novellSecurityAttribs = localASN1ObjectIdentifier3.branch("1.9.4.1");
    ASN1ObjectIdentifier localASN1ObjectIdentifier4 = new ASN1ObjectIdentifier("1.2.840.113533.7");
    entrust = localASN1ObjectIdentifier4;
    entrustVersionExtension = localASN1ObjectIdentifier4.branch("65.0");
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.misc.MiscObjectIdentifiers
 * JD-Core Version:    0.7.0.1
 */