package org.spongycastle.pkcs;

import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.pkcs.SafeBag;
import org.spongycastle.cms.CMSEncryptedData;
import org.spongycastle.cms.CMSException;
import org.spongycastle.operator.InputDecryptorProvider;

public class PKCS12SafeBagFactory
{
  private ASN1Sequence safeBagSeq;
  
  public PKCS12SafeBagFactory(org.spongycastle.asn1.pkcs.ContentInfo paramContentInfo)
  {
    if (paramContentInfo.getContentType().equals(PKCSObjectIdentifiers.encryptedData)) {
      throw new IllegalArgumentException("encryptedData requires constructor with decryptor.");
    }
    this.safeBagSeq = ASN1Sequence.getInstance(ASN1OctetString.getInstance(paramContentInfo.getContent()).getOctets());
  }
  
  public PKCS12SafeBagFactory(org.spongycastle.asn1.pkcs.ContentInfo paramContentInfo, InputDecryptorProvider paramInputDecryptorProvider)
    throws PKCSException
  {
    if (paramContentInfo.getContentType().equals(PKCSObjectIdentifiers.encryptedData))
    {
      CMSEncryptedData localCMSEncryptedData = new CMSEncryptedData(org.spongycastle.asn1.cms.ContentInfo.getInstance(paramContentInfo));
      try
      {
        this.safeBagSeq = ASN1Sequence.getInstance(localCMSEncryptedData.getContent(paramInputDecryptorProvider));
        return;
      }
      catch (CMSException localCMSException)
      {
        throw new PKCSException("unable to extract data: " + localCMSException.getMessage(), localCMSException);
      }
    }
    throw new IllegalArgumentException("encryptedData requires constructor with decryptor.");
  }
  
  public PKCS12SafeBag[] getSafeBags()
  {
    PKCS12SafeBag[] arrayOfPKCS12SafeBag = new PKCS12SafeBag[this.safeBagSeq.size()];
    for (int i = 0; i != this.safeBagSeq.size(); i++) {
      arrayOfPKCS12SafeBag[i] = new PKCS12SafeBag(SafeBag.getInstance(this.safeBagSeq.getObjectAt(i)));
    }
    return arrayOfPKCS12SafeBag;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.pkcs.PKCS12SafeBagFactory
 * JD-Core Version:    0.7.0.1
 */