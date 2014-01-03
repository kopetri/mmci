package org.spongycastle.asn1.cmp;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class ErrorMsgContent
  extends ASN1Object
{
  private ASN1Integer errorCode;
  private PKIFreeText errorDetails;
  private PKIStatusInfo pkiStatusInfo;
  
  private ErrorMsgContent(ASN1Sequence paramASN1Sequence)
  {
    Enumeration localEnumeration = paramASN1Sequence.getObjects();
    this.pkiStatusInfo = PKIStatusInfo.getInstance(localEnumeration.nextElement());
    while (localEnumeration.hasMoreElements())
    {
      Object localObject = localEnumeration.nextElement();
      if ((localObject instanceof ASN1Integer)) {
        this.errorCode = ASN1Integer.getInstance(localObject);
      } else {
        this.errorDetails = PKIFreeText.getInstance(localObject);
      }
    }
  }
  
  public ErrorMsgContent(PKIStatusInfo paramPKIStatusInfo)
  {
    this(paramPKIStatusInfo, null, null);
  }
  
  public ErrorMsgContent(PKIStatusInfo paramPKIStatusInfo, ASN1Integer paramASN1Integer, PKIFreeText paramPKIFreeText)
  {
    if (paramPKIStatusInfo == null) {
      throw new IllegalArgumentException("'pkiStatusInfo' cannot be null");
    }
    this.pkiStatusInfo = paramPKIStatusInfo;
    this.errorCode = paramASN1Integer;
    this.errorDetails = paramPKIFreeText;
  }
  
  private void addOptional(ASN1EncodableVector paramASN1EncodableVector, ASN1Encodable paramASN1Encodable)
  {
    if (paramASN1Encodable != null) {
      paramASN1EncodableVector.add(paramASN1Encodable);
    }
  }
  
  public static ErrorMsgContent getInstance(Object paramObject)
  {
    if ((paramObject instanceof ErrorMsgContent)) {
      return (ErrorMsgContent)paramObject;
    }
    if (paramObject != null) {
      return new ErrorMsgContent(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public ASN1Integer getErrorCode()
  {
    return this.errorCode;
  }
  
  public PKIFreeText getErrorDetails()
  {
    return this.errorDetails;
  }
  
  public PKIStatusInfo getPKIStatusInfo()
  {
    return this.pkiStatusInfo;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.pkiStatusInfo);
    addOptional(localASN1EncodableVector, this.errorCode);
    addOptional(localASN1EncodableVector, this.errorDetails);
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.cmp.ErrorMsgContent
 * JD-Core Version:    0.7.0.1
 */