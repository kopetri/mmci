package org.spongycastle.asn1.cmp;

import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;

public class CertConfirmContent
  extends ASN1Object
{
  private ASN1Sequence content;
  
  private CertConfirmContent(ASN1Sequence paramASN1Sequence)
  {
    this.content = paramASN1Sequence;
  }
  
  public static CertConfirmContent getInstance(Object paramObject)
  {
    if ((paramObject instanceof CertConfirmContent)) {
      return (CertConfirmContent)paramObject;
    }
    if (paramObject != null) {
      return new CertConfirmContent(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return this.content;
  }
  
  public CertStatus[] toCertStatusArray()
  {
    CertStatus[] arrayOfCertStatus = new CertStatus[this.content.size()];
    for (int i = 0; i != arrayOfCertStatus.length; i++) {
      arrayOfCertStatus[i] = CertStatus.getInstance(this.content.getObjectAt(i));
    }
    return arrayOfCertStatus;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.cmp.CertConfirmContent
 * JD-Core Version:    0.7.0.1
 */