package org.spongycastle.asn1.cms;

import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;

public abstract interface CMSAttributes
{
  public static final ASN1ObjectIdentifier contentHint = PKCSObjectIdentifiers.id_aa_contentHint;
  public static final ASN1ObjectIdentifier contentType = PKCSObjectIdentifiers.pkcs_9_at_contentType;
  public static final ASN1ObjectIdentifier counterSignature;
  public static final ASN1ObjectIdentifier messageDigest = PKCSObjectIdentifiers.pkcs_9_at_messageDigest;
  public static final ASN1ObjectIdentifier signingTime = PKCSObjectIdentifiers.pkcs_9_at_signingTime;
  
  static
  {
    counterSignature = PKCSObjectIdentifiers.pkcs_9_at_counterSignature;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.cms.CMSAttributes
 * JD-Core Version:    0.7.0.1
 */