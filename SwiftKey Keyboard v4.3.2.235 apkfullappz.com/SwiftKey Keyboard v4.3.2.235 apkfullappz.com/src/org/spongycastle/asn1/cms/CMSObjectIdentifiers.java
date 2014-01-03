package org.spongycastle.asn1.cms;

import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;

public abstract interface CMSObjectIdentifiers
{
  public static final ASN1ObjectIdentifier authEnvelopedData = PKCSObjectIdentifiers.id_ct_authEnvelopedData;
  public static final ASN1ObjectIdentifier authenticatedData;
  public static final ASN1ObjectIdentifier compressedData;
  public static final ASN1ObjectIdentifier data = PKCSObjectIdentifiers.data;
  public static final ASN1ObjectIdentifier digestedData;
  public static final ASN1ObjectIdentifier encryptedData;
  public static final ASN1ObjectIdentifier envelopedData;
  public static final ASN1ObjectIdentifier signedAndEnvelopedData;
  public static final ASN1ObjectIdentifier signedData = PKCSObjectIdentifiers.signedData;
  public static final ASN1ObjectIdentifier timestampedData = PKCSObjectIdentifiers.id_ct_timestampedData;
  
  static
  {
    envelopedData = PKCSObjectIdentifiers.envelopedData;
    signedAndEnvelopedData = PKCSObjectIdentifiers.signedAndEnvelopedData;
    digestedData = PKCSObjectIdentifiers.digestedData;
    encryptedData = PKCSObjectIdentifiers.encryptedData;
    authenticatedData = PKCSObjectIdentifiers.id_ct_authData;
    compressedData = PKCSObjectIdentifiers.id_ct_compressedData;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.cms.CMSObjectIdentifiers
 * JD-Core Version:    0.7.0.1
 */