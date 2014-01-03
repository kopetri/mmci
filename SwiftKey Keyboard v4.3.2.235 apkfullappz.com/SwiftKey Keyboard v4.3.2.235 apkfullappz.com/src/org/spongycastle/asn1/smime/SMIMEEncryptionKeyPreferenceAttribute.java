package org.spongycastle.asn1.smime;

import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.DERSet;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.cms.Attribute;
import org.spongycastle.asn1.cms.IssuerAndSerialNumber;
import org.spongycastle.asn1.cms.RecipientKeyIdentifier;

public class SMIMEEncryptionKeyPreferenceAttribute
  extends Attribute
{
  public SMIMEEncryptionKeyPreferenceAttribute(ASN1OctetString paramASN1OctetString)
  {
    super(SMIMEAttributes.encrypKeyPref, new DERSet(new DERTaggedObject(false, 2, paramASN1OctetString)));
  }
  
  public SMIMEEncryptionKeyPreferenceAttribute(IssuerAndSerialNumber paramIssuerAndSerialNumber)
  {
    super(SMIMEAttributes.encrypKeyPref, new DERSet(new DERTaggedObject(false, 0, paramIssuerAndSerialNumber)));
  }
  
  public SMIMEEncryptionKeyPreferenceAttribute(RecipientKeyIdentifier paramRecipientKeyIdentifier)
  {
    super(SMIMEAttributes.encrypKeyPref, new DERSet(new DERTaggedObject(false, 1, paramRecipientKeyIdentifier)));
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.smime.SMIMEEncryptionKeyPreferenceAttribute
 * JD-Core Version:    0.7.0.1
 */