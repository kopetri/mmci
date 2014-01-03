package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class UserNotice
  extends ASN1Object
{
  private DisplayText explicitText;
  private NoticeReference noticeRef;
  
  private UserNotice(ASN1Sequence paramASN1Sequence)
  {
    if (paramASN1Sequence.size() == 2)
    {
      this.noticeRef = NoticeReference.getInstance(paramASN1Sequence.getObjectAt(0));
      this.explicitText = DisplayText.getInstance(paramASN1Sequence.getObjectAt(1));
      return;
    }
    if (paramASN1Sequence.size() == 1)
    {
      if ((paramASN1Sequence.getObjectAt(0).toASN1Primitive() instanceof ASN1Sequence))
      {
        this.noticeRef = NoticeReference.getInstance(paramASN1Sequence.getObjectAt(0));
        return;
      }
      this.explicitText = DisplayText.getInstance(paramASN1Sequence.getObjectAt(0));
      return;
    }
    throw new IllegalArgumentException("Bad sequence size: " + paramASN1Sequence.size());
  }
  
  public UserNotice(NoticeReference paramNoticeReference, String paramString)
  {
    this(paramNoticeReference, new DisplayText(paramString));
  }
  
  public UserNotice(NoticeReference paramNoticeReference, DisplayText paramDisplayText)
  {
    this.noticeRef = paramNoticeReference;
    this.explicitText = paramDisplayText;
  }
  
  public static UserNotice getInstance(Object paramObject)
  {
    if ((paramObject instanceof UserNotice)) {
      return (UserNotice)paramObject;
    }
    if (paramObject != null) {
      return new UserNotice(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public DisplayText getExplicitText()
  {
    return this.explicitText;
  }
  
  public NoticeReference getNoticeRef()
  {
    return this.noticeRef;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    if (this.noticeRef != null) {
      localASN1EncodableVector.add(this.noticeRef);
    }
    if (this.explicitText != null) {
      localASN1EncodableVector.add(this.explicitText);
    }
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x509.UserNotice
 * JD-Core Version:    0.7.0.1
 */