package org.spongycastle.asn1.esf;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.DisplayText;
import org.spongycastle.asn1.x509.NoticeReference;

public class SPUserNotice
  extends ASN1Object
{
  private DisplayText explicitText;
  private NoticeReference noticeRef;
  
  private SPUserNotice(ASN1Sequence paramASN1Sequence)
  {
    Enumeration localEnumeration = paramASN1Sequence.getObjects();
    while (localEnumeration.hasMoreElements())
    {
      ASN1Encodable localASN1Encodable = (ASN1Encodable)localEnumeration.nextElement();
      if ((localASN1Encodable instanceof NoticeReference)) {
        this.noticeRef = NoticeReference.getInstance(localASN1Encodable);
      } else if ((localASN1Encodable instanceof DisplayText)) {
        this.explicitText = DisplayText.getInstance(localASN1Encodable);
      } else {
        throw new IllegalArgumentException("Invalid element in 'SPUserNotice'.");
      }
    }
  }
  
  public SPUserNotice(NoticeReference paramNoticeReference, DisplayText paramDisplayText)
  {
    this.noticeRef = paramNoticeReference;
    this.explicitText = paramDisplayText;
  }
  
  public static SPUserNotice getInstance(Object paramObject)
  {
    if ((paramObject instanceof SPUserNotice)) {
      return (SPUserNotice)paramObject;
    }
    if (paramObject != null) {
      return new SPUserNotice(ASN1Sequence.getInstance(paramObject));
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
 * Qualified Name:     org.spongycastle.asn1.esf.SPUserNotice
 * JD-Core Version:    0.7.0.1
 */