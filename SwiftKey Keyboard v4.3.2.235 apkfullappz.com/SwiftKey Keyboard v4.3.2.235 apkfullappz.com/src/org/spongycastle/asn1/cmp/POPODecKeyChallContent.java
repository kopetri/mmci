package org.spongycastle.asn1.cmp;

import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;

public class POPODecKeyChallContent
  extends ASN1Object
{
  private ASN1Sequence content;
  
  private POPODecKeyChallContent(ASN1Sequence paramASN1Sequence)
  {
    this.content = paramASN1Sequence;
  }
  
  public static POPODecKeyChallContent getInstance(Object paramObject)
  {
    if ((paramObject instanceof POPODecKeyChallContent)) {
      return (POPODecKeyChallContent)paramObject;
    }
    if (paramObject != null) {
      return new POPODecKeyChallContent(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return this.content;
  }
  
  public Challenge[] toChallengeArray()
  {
    Challenge[] arrayOfChallenge = new Challenge[this.content.size()];
    for (int i = 0; i != arrayOfChallenge.length; i++) {
      arrayOfChallenge[i] = Challenge.getInstance(this.content.getObjectAt(i));
    }
    return arrayOfChallenge;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.cmp.POPODecKeyChallContent
 * JD-Core Version:    0.7.0.1
 */