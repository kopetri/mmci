package org.spongycastle.asn1.x509;

import java.math.BigInteger;
import java.util.Enumeration;
import java.util.Vector;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class NoticeReference
  extends ASN1Object
{
  private ASN1Sequence noticeNumbers;
  private DisplayText organization;
  
  public NoticeReference(String paramString, Vector paramVector)
  {
    this(paramString, convertVector(paramVector));
  }
  
  public NoticeReference(String paramString, ASN1EncodableVector paramASN1EncodableVector)
  {
    this(new DisplayText(paramString), paramASN1EncodableVector);
  }
  
  private NoticeReference(ASN1Sequence paramASN1Sequence)
  {
    if (paramASN1Sequence.size() != 2) {
      throw new IllegalArgumentException("Bad sequence size: " + paramASN1Sequence.size());
    }
    this.organization = DisplayText.getInstance(paramASN1Sequence.getObjectAt(0));
    this.noticeNumbers = ASN1Sequence.getInstance(paramASN1Sequence.getObjectAt(1));
  }
  
  public NoticeReference(DisplayText paramDisplayText, ASN1EncodableVector paramASN1EncodableVector)
  {
    this.organization = paramDisplayText;
    this.noticeNumbers = new DERSequence(paramASN1EncodableVector);
  }
  
  private static ASN1EncodableVector convertVector(Vector paramVector)
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    Enumeration localEnumeration = paramVector.elements();
    if (localEnumeration.hasMoreElements())
    {
      Object localObject = localEnumeration.nextElement();
      if ((localObject instanceof BigInteger)) {}
      for (ASN1Integer localASN1Integer = new ASN1Integer((BigInteger)localObject);; localASN1Integer = new ASN1Integer(((Integer)localObject).intValue()))
      {
        localASN1EncodableVector.add(localASN1Integer);
        break;
        if (!(localObject instanceof Integer)) {
          break label84;
        }
      }
      label84:
      throw new IllegalArgumentException();
    }
    return localASN1EncodableVector;
  }
  
  public static NoticeReference getInstance(Object paramObject)
  {
    if ((paramObject instanceof NoticeReference)) {
      return (NoticeReference)paramObject;
    }
    if (paramObject != null) {
      return new NoticeReference(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public ASN1Integer[] getNoticeNumbers()
  {
    ASN1Integer[] arrayOfASN1Integer = new ASN1Integer[this.noticeNumbers.size()];
    for (int i = 0; i != this.noticeNumbers.size(); i++) {
      arrayOfASN1Integer[i] = ASN1Integer.getInstance(this.noticeNumbers.getObjectAt(i));
    }
    return arrayOfASN1Integer;
  }
  
  public DisplayText getOrganization()
  {
    return this.organization;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.organization);
    localASN1EncodableVector.add(this.noticeNumbers);
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x509.NoticeReference
 * JD-Core Version:    0.7.0.1
 */