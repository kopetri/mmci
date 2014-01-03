package org.spongycastle.asn1.mozilla;

import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERIA5String;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;

public class PublicKeyAndChallenge
  extends ASN1Object
{
  private DERIA5String challenge;
  private ASN1Sequence pkacSeq;
  private SubjectPublicKeyInfo spki;
  
  private PublicKeyAndChallenge(ASN1Sequence paramASN1Sequence)
  {
    this.pkacSeq = paramASN1Sequence;
    this.spki = SubjectPublicKeyInfo.getInstance(paramASN1Sequence.getObjectAt(0));
    this.challenge = DERIA5String.getInstance(paramASN1Sequence.getObjectAt(1));
  }
  
  public static PublicKeyAndChallenge getInstance(Object paramObject)
  {
    if ((paramObject instanceof PublicKeyAndChallenge)) {
      return (PublicKeyAndChallenge)paramObject;
    }
    if (paramObject != null) {
      return new PublicKeyAndChallenge(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public DERIA5String getChallenge()
  {
    return this.challenge;
  }
  
  public SubjectPublicKeyInfo getSubjectPublicKeyInfo()
  {
    return this.spki;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return this.pkacSeq;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.mozilla.PublicKeyAndChallenge
 * JD-Core Version:    0.7.0.1
 */