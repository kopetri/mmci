package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class AuthorityInformationAccess
  extends ASN1Object
{
  private AccessDescription[] descriptions;
  
  public AuthorityInformationAccess(ASN1ObjectIdentifier paramASN1ObjectIdentifier, GeneralName paramGeneralName)
  {
    this.descriptions = new AccessDescription[1];
    this.descriptions[0] = new AccessDescription(paramASN1ObjectIdentifier, paramGeneralName);
  }
  
  private AuthorityInformationAccess(ASN1Sequence paramASN1Sequence)
  {
    if (paramASN1Sequence.size() <= 0) {
      throw new IllegalArgumentException("sequence may not be empty");
    }
    this.descriptions = new AccessDescription[paramASN1Sequence.size()];
    for (int i = 0; i != paramASN1Sequence.size(); i++) {
      this.descriptions[i] = AccessDescription.getInstance(paramASN1Sequence.getObjectAt(i));
    }
  }
  
  public static AuthorityInformationAccess getInstance(Object paramObject)
  {
    if ((paramObject instanceof AuthorityInformationAccess)) {
      return (AuthorityInformationAccess)paramObject;
    }
    if (paramObject != null) {
      return new AuthorityInformationAccess(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public AccessDescription[] getAccessDescriptions()
  {
    return this.descriptions;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    for (int i = 0; i != this.descriptions.length; i++) {
      localASN1EncodableVector.add(this.descriptions[i]);
    }
    return new DERSequence(localASN1EncodableVector);
  }
  
  public String toString()
  {
    return "AuthorityInformationAccess: Oid(" + this.descriptions[0].getAccessMethod().getId() + ")";
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x509.AuthorityInformationAccess
 * JD-Core Version:    0.7.0.1
 */