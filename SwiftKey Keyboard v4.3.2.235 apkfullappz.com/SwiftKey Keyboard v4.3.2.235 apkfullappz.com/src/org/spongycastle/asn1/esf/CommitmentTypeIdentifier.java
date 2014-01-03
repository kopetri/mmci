package org.spongycastle.asn1.esf;

import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;

public abstract interface CommitmentTypeIdentifier
{
  public static final ASN1ObjectIdentifier proofOfApproval = PKCSObjectIdentifiers.id_cti_ets_proofOfApproval;
  public static final ASN1ObjectIdentifier proofOfCreation = PKCSObjectIdentifiers.id_cti_ets_proofOfCreation;
  public static final ASN1ObjectIdentifier proofOfDelivery;
  public static final ASN1ObjectIdentifier proofOfOrigin = PKCSObjectIdentifiers.id_cti_ets_proofOfOrigin;
  public static final ASN1ObjectIdentifier proofOfReceipt = PKCSObjectIdentifiers.id_cti_ets_proofOfReceipt;
  public static final ASN1ObjectIdentifier proofOfSender;
  
  static
  {
    proofOfDelivery = PKCSObjectIdentifiers.id_cti_ets_proofOfDelivery;
    proofOfSender = PKCSObjectIdentifiers.id_cti_ets_proofOfSender;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.esf.CommitmentTypeIdentifier
 * JD-Core Version:    0.7.0.1
 */