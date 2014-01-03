package org.spongycastle.asn1.x509;

import java.io.IOException;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERBoolean;

public class X509Extension
{
  public static final ASN1ObjectIdentifier auditIdentity = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.4");
  public static final ASN1ObjectIdentifier authorityInfoAccess;
  public static final ASN1ObjectIdentifier authorityKeyIdentifier;
  public static final ASN1ObjectIdentifier basicConstraints;
  public static final ASN1ObjectIdentifier biometricInfo;
  public static final ASN1ObjectIdentifier cRLDistributionPoints;
  public static final ASN1ObjectIdentifier cRLNumber;
  public static final ASN1ObjectIdentifier certificateIssuer;
  public static final ASN1ObjectIdentifier certificatePolicies;
  public static final ASN1ObjectIdentifier deltaCRLIndicator;
  public static final ASN1ObjectIdentifier extendedKeyUsage;
  public static final ASN1ObjectIdentifier freshestCRL;
  public static final ASN1ObjectIdentifier inhibitAnyPolicy;
  public static final ASN1ObjectIdentifier instructionCode;
  public static final ASN1ObjectIdentifier invalidityDate;
  public static final ASN1ObjectIdentifier issuerAlternativeName;
  public static final ASN1ObjectIdentifier issuingDistributionPoint;
  public static final ASN1ObjectIdentifier keyUsage;
  public static final ASN1ObjectIdentifier logoType;
  public static final ASN1ObjectIdentifier nameConstraints;
  public static final ASN1ObjectIdentifier noRevAvail = new ASN1ObjectIdentifier("2.5.29.56");
  public static final ASN1ObjectIdentifier policyConstraints;
  public static final ASN1ObjectIdentifier policyMappings;
  public static final ASN1ObjectIdentifier privateKeyUsagePeriod;
  public static final ASN1ObjectIdentifier qCStatements;
  public static final ASN1ObjectIdentifier reasonCode;
  public static final ASN1ObjectIdentifier subjectAlternativeName;
  public static final ASN1ObjectIdentifier subjectDirectoryAttributes = new ASN1ObjectIdentifier("2.5.29.9");
  public static final ASN1ObjectIdentifier subjectInfoAccess;
  public static final ASN1ObjectIdentifier subjectKeyIdentifier = new ASN1ObjectIdentifier("2.5.29.14");
  public static final ASN1ObjectIdentifier targetInformation = new ASN1ObjectIdentifier("2.5.29.55");
  boolean critical;
  ASN1OctetString value;
  
  static
  {
    keyUsage = new ASN1ObjectIdentifier("2.5.29.15");
    privateKeyUsagePeriod = new ASN1ObjectIdentifier("2.5.29.16");
    subjectAlternativeName = new ASN1ObjectIdentifier("2.5.29.17");
    issuerAlternativeName = new ASN1ObjectIdentifier("2.5.29.18");
    basicConstraints = new ASN1ObjectIdentifier("2.5.29.19");
    cRLNumber = new ASN1ObjectIdentifier("2.5.29.20");
    reasonCode = new ASN1ObjectIdentifier("2.5.29.21");
    instructionCode = new ASN1ObjectIdentifier("2.5.29.23");
    invalidityDate = new ASN1ObjectIdentifier("2.5.29.24");
    deltaCRLIndicator = new ASN1ObjectIdentifier("2.5.29.27");
    issuingDistributionPoint = new ASN1ObjectIdentifier("2.5.29.28");
    certificateIssuer = new ASN1ObjectIdentifier("2.5.29.29");
    nameConstraints = new ASN1ObjectIdentifier("2.5.29.30");
    cRLDistributionPoints = new ASN1ObjectIdentifier("2.5.29.31");
    certificatePolicies = new ASN1ObjectIdentifier("2.5.29.32");
    policyMappings = new ASN1ObjectIdentifier("2.5.29.33");
    authorityKeyIdentifier = new ASN1ObjectIdentifier("2.5.29.35");
    policyConstraints = new ASN1ObjectIdentifier("2.5.29.36");
    extendedKeyUsage = new ASN1ObjectIdentifier("2.5.29.37");
    freshestCRL = new ASN1ObjectIdentifier("2.5.29.46");
    inhibitAnyPolicy = new ASN1ObjectIdentifier("2.5.29.54");
    authorityInfoAccess = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.1");
    subjectInfoAccess = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.11");
    logoType = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.12");
    biometricInfo = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.2");
    qCStatements = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.3");
  }
  
  public X509Extension(DERBoolean paramDERBoolean, ASN1OctetString paramASN1OctetString)
  {
    this.critical = paramDERBoolean.isTrue();
    this.value = paramASN1OctetString;
  }
  
  public X509Extension(boolean paramBoolean, ASN1OctetString paramASN1OctetString)
  {
    this.critical = paramBoolean;
    this.value = paramASN1OctetString;
  }
  
  public static ASN1Primitive convertValueToObject(X509Extension paramX509Extension)
    throws IllegalArgumentException
  {
    try
    {
      ASN1Primitive localASN1Primitive = ASN1Primitive.fromByteArray(paramX509Extension.getValue().getOctets());
      return localASN1Primitive;
    }
    catch (IOException localIOException)
    {
      throw new IllegalArgumentException("can't convert extension: " + localIOException);
    }
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof X509Extension)) {}
    X509Extension localX509Extension;
    do
    {
      return false;
      localX509Extension = (X509Extension)paramObject;
    } while ((!localX509Extension.getValue().equals(getValue())) || (localX509Extension.isCritical() != isCritical()));
    return true;
  }
  
  public ASN1Encodable getParsedValue()
  {
    return convertValueToObject(this);
  }
  
  public ASN1OctetString getValue()
  {
    return this.value;
  }
  
  public int hashCode()
  {
    if (isCritical()) {
      return getValue().hashCode();
    }
    return 0xFFFFFFFF ^ getValue().hashCode();
  }
  
  public boolean isCritical()
  {
    return this.critical;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x509.X509Extension
 * JD-Core Version:    0.7.0.1
 */