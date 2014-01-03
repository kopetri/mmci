package org.spongycastle.cert;

import java.math.BigInteger;
import java.util.Date;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.DERGeneralizedTime;
import org.spongycastle.asn1.DERSet;
import org.spongycastle.asn1.x509.AttCertIssuer;
import org.spongycastle.asn1.x509.Attribute;
import org.spongycastle.asn1.x509.ExtensionsGenerator;
import org.spongycastle.asn1.x509.V2AttributeCertificateInfoGenerator;
import org.spongycastle.operator.ContentSigner;

public class X509v2AttributeCertificateBuilder
{
  private V2AttributeCertificateInfoGenerator acInfoGen = new V2AttributeCertificateInfoGenerator();
  private ExtensionsGenerator extGenerator = new ExtensionsGenerator();
  
  public X509v2AttributeCertificateBuilder(AttributeCertificateHolder paramAttributeCertificateHolder, AttributeCertificateIssuer paramAttributeCertificateIssuer, BigInteger paramBigInteger, Date paramDate1, Date paramDate2)
  {
    this.acInfoGen.setHolder(paramAttributeCertificateHolder.holder);
    this.acInfoGen.setIssuer(AttCertIssuer.getInstance(paramAttributeCertificateIssuer.form));
    this.acInfoGen.setSerialNumber(new ASN1Integer(paramBigInteger));
    this.acInfoGen.setStartDate(new DERGeneralizedTime(paramDate1));
    this.acInfoGen.setEndDate(new DERGeneralizedTime(paramDate2));
  }
  
  public X509v2AttributeCertificateBuilder addAttribute(ASN1ObjectIdentifier paramASN1ObjectIdentifier, ASN1Encodable paramASN1Encodable)
  {
    this.acInfoGen.addAttribute(new Attribute(paramASN1ObjectIdentifier, new DERSet(paramASN1Encodable)));
    return this;
  }
  
  public X509v2AttributeCertificateBuilder addAttribute(ASN1ObjectIdentifier paramASN1ObjectIdentifier, ASN1Encodable[] paramArrayOfASN1Encodable)
  {
    this.acInfoGen.addAttribute(new Attribute(paramASN1ObjectIdentifier, new DERSet(paramArrayOfASN1Encodable)));
    return this;
  }
  
  public X509v2AttributeCertificateBuilder addExtension(ASN1ObjectIdentifier paramASN1ObjectIdentifier, boolean paramBoolean, ASN1Encodable paramASN1Encodable)
    throws CertIOException
  {
    CertUtils.addExtension(this.extGenerator, paramASN1ObjectIdentifier, paramBoolean, paramASN1Encodable);
    return this;
  }
  
  public X509AttributeCertificateHolder build(ContentSigner paramContentSigner)
  {
    this.acInfoGen.setSignature(paramContentSigner.getAlgorithmIdentifier());
    if (!this.extGenerator.isEmpty()) {
      this.acInfoGen.setExtensions(this.extGenerator.generate());
    }
    return CertUtils.generateFullAttrCert(paramContentSigner, this.acInfoGen.generateAttributeCertificateInfo());
  }
  
  public void setIssuerUniqueId(boolean[] paramArrayOfBoolean)
  {
    this.acInfoGen.setIssuerUniqueID(CertUtils.booleanToBitString(paramArrayOfBoolean));
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.X509v2AttributeCertificateBuilder
 * JD-Core Version:    0.7.0.1
 */