package org.spongycastle.pkcs;

import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.pkcs.Attribute;
import org.spongycastle.asn1.pkcs.CRLBag;
import org.spongycastle.asn1.pkcs.CertBag;
import org.spongycastle.asn1.pkcs.EncryptedPrivateKeyInfo;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.pkcs.SafeBag;
import org.spongycastle.asn1.x509.Certificate;
import org.spongycastle.asn1.x509.CertificateList;
import org.spongycastle.cert.X509CRLHolder;
import org.spongycastle.cert.X509CertificateHolder;

public class PKCS12SafeBag
{
  private SafeBag safeBag;
  
  public PKCS12SafeBag(SafeBag paramSafeBag)
  {
    this.safeBag = paramSafeBag;
  }
  
  public Attribute[] getAttributes()
  {
    ASN1Set localASN1Set = this.safeBag.getBagAttributes();
    Attribute[] arrayOfAttribute;
    if (localASN1Set == null) {
      arrayOfAttribute = null;
    }
    for (;;)
    {
      return arrayOfAttribute;
      arrayOfAttribute = new Attribute[localASN1Set.size()];
      for (int i = 0; i != localASN1Set.size(); i++) {
        arrayOfAttribute[i] = Attribute.getInstance(localASN1Set.getObjectAt(i));
      }
    }
  }
  
  public Object getBagValue()
  {
    if (getType().equals(PKCSObjectIdentifiers.pkcs8ShroudedKeyBag)) {
      return new PKCS8EncryptedPrivateKeyInfo(EncryptedPrivateKeyInfo.getInstance(this.safeBag.getBagValue()));
    }
    if (getType().equals(PKCSObjectIdentifiers.certBag)) {
      return new X509CertificateHolder(Certificate.getInstance(ASN1OctetString.getInstance(CertBag.getInstance(this.safeBag.getBagValue()).getCertValue()).getOctets()));
    }
    if (getType().equals(PKCSObjectIdentifiers.keyBag)) {
      return PrivateKeyInfo.getInstance(this.safeBag.getBagValue());
    }
    if (getType().equals(PKCSObjectIdentifiers.crlBag)) {
      return new X509CRLHolder(CertificateList.getInstance(ASN1OctetString.getInstance(CRLBag.getInstance(this.safeBag.getBagValue()).getCRLValue()).getOctets()));
    }
    return this.safeBag.getBagValue();
  }
  
  public ASN1ObjectIdentifier getType()
  {
    return this.safeBag.getBagId();
  }
  
  public SafeBag toASN1Structure()
  {
    return this.safeBag;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.pkcs.PKCS12SafeBag
 * JD-Core Version:    0.7.0.1
 */