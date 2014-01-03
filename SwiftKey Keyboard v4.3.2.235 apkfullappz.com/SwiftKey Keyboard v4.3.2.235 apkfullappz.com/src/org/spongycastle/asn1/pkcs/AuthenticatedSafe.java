package org.spongycastle.asn1.pkcs;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.BERSequence;
import org.spongycastle.asn1.DLSequence;

public class AuthenticatedSafe
  extends ASN1Object
{
  private ContentInfo[] info;
  private boolean isBer = true;
  
  private AuthenticatedSafe(ASN1Sequence paramASN1Sequence)
  {
    this.info = new ContentInfo[paramASN1Sequence.size()];
    for (int i = 0; i != this.info.length; i++) {
      this.info[i] = ContentInfo.getInstance(paramASN1Sequence.getObjectAt(i));
    }
    this.isBer = (paramASN1Sequence instanceof BERSequence);
  }
  
  public AuthenticatedSafe(ContentInfo[] paramArrayOfContentInfo)
  {
    this.info = paramArrayOfContentInfo;
  }
  
  public static AuthenticatedSafe getInstance(Object paramObject)
  {
    if ((paramObject instanceof AuthenticatedSafe)) {
      return (AuthenticatedSafe)paramObject;
    }
    if (paramObject != null) {
      return new AuthenticatedSafe(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public ContentInfo[] getContentInfo()
  {
    return this.info;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    for (int i = 0; i != this.info.length; i++) {
      localASN1EncodableVector.add(this.info[i]);
    }
    if (this.isBer) {
      return new BERSequence(localASN1EncodableVector);
    }
    return new DLSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.pkcs.AuthenticatedSafe
 * JD-Core Version:    0.7.0.1
 */