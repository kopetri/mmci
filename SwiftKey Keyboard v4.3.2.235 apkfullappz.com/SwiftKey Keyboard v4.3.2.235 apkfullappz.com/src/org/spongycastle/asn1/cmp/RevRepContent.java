package org.spongycastle.asn1.cmp;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.crmf.CertId;
import org.spongycastle.asn1.x509.CertificateList;

public class RevRepContent
  extends ASN1Object
{
  private ASN1Sequence crls;
  private ASN1Sequence revCerts;
  private ASN1Sequence status;
  
  private RevRepContent(ASN1Sequence paramASN1Sequence)
  {
    Enumeration localEnumeration = paramASN1Sequence.getObjects();
    this.status = ASN1Sequence.getInstance(localEnumeration.nextElement());
    while (localEnumeration.hasMoreElements())
    {
      ASN1TaggedObject localASN1TaggedObject = ASN1TaggedObject.getInstance(localEnumeration.nextElement());
      if (localASN1TaggedObject.getTagNo() == 0) {
        this.revCerts = ASN1Sequence.getInstance(localASN1TaggedObject, true);
      } else {
        this.crls = ASN1Sequence.getInstance(localASN1TaggedObject, true);
      }
    }
  }
  
  private void addOptional(ASN1EncodableVector paramASN1EncodableVector, int paramInt, ASN1Encodable paramASN1Encodable)
  {
    if (paramASN1Encodable != null) {
      paramASN1EncodableVector.add(new DERTaggedObject(true, paramInt, paramASN1Encodable));
    }
  }
  
  public static RevRepContent getInstance(Object paramObject)
  {
    if ((paramObject instanceof RevRepContent)) {
      return (RevRepContent)paramObject;
    }
    if (paramObject != null) {
      return new RevRepContent(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public CertificateList[] getCrls()
  {
    CertificateList[] arrayOfCertificateList;
    if (this.crls == null) {
      arrayOfCertificateList = null;
    }
    for (;;)
    {
      return arrayOfCertificateList;
      arrayOfCertificateList = new CertificateList[this.crls.size()];
      for (int i = 0; i != arrayOfCertificateList.length; i++) {
        arrayOfCertificateList[i] = CertificateList.getInstance(this.crls.getObjectAt(i));
      }
    }
  }
  
  public CertId[] getRevCerts()
  {
    CertId[] arrayOfCertId;
    if (this.revCerts == null) {
      arrayOfCertId = null;
    }
    for (;;)
    {
      return arrayOfCertId;
      arrayOfCertId = new CertId[this.revCerts.size()];
      for (int i = 0; i != arrayOfCertId.length; i++) {
        arrayOfCertId[i] = CertId.getInstance(this.revCerts.getObjectAt(i));
      }
    }
  }
  
  public PKIStatusInfo[] getStatus()
  {
    PKIStatusInfo[] arrayOfPKIStatusInfo = new PKIStatusInfo[this.status.size()];
    for (int i = 0; i != arrayOfPKIStatusInfo.length; i++) {
      arrayOfPKIStatusInfo[i] = PKIStatusInfo.getInstance(this.status.getObjectAt(i));
    }
    return arrayOfPKIStatusInfo;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.status);
    addOptional(localASN1EncodableVector, 0, this.revCerts);
    addOptional(localASN1EncodableVector, 1, this.crls);
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.cmp.RevRepContent
 * JD-Core Version:    0.7.0.1
 */