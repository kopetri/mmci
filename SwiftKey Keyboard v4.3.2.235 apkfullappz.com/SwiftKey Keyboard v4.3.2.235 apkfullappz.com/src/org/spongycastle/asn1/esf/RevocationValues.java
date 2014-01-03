package org.spongycastle.asn1.esf;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.ocsp.BasicOCSPResponse;
import org.spongycastle.asn1.x509.CertificateList;

public class RevocationValues
  extends ASN1Object
{
  private ASN1Sequence crlVals;
  private ASN1Sequence ocspVals;
  private OtherRevVals otherRevVals;
  
  private RevocationValues(ASN1Sequence paramASN1Sequence)
  {
    if (paramASN1Sequence.size() > 3) {
      throw new IllegalArgumentException("Bad sequence size: " + paramASN1Sequence.size());
    }
    Enumeration localEnumeration1 = paramASN1Sequence.getObjects();
    while (localEnumeration1.hasMoreElements())
    {
      DERTaggedObject localDERTaggedObject = (DERTaggedObject)localEnumeration1.nextElement();
      switch (localDERTaggedObject.getTagNo())
      {
      default: 
        throw new IllegalArgumentException("invalid tag: " + localDERTaggedObject.getTagNo());
      case 0: 
        ASN1Sequence localASN1Sequence2 = (ASN1Sequence)localDERTaggedObject.getObject();
        Enumeration localEnumeration3 = localASN1Sequence2.getObjects();
        while (localEnumeration3.hasMoreElements()) {
          CertificateList.getInstance(localEnumeration3.nextElement());
        }
        this.crlVals = localASN1Sequence2;
        break;
      case 1: 
        ASN1Sequence localASN1Sequence1 = (ASN1Sequence)localDERTaggedObject.getObject();
        Enumeration localEnumeration2 = localASN1Sequence1.getObjects();
        while (localEnumeration2.hasMoreElements()) {
          BasicOCSPResponse.getInstance(localEnumeration2.nextElement());
        }
        this.ocspVals = localASN1Sequence1;
        break;
      case 2: 
        this.otherRevVals = OtherRevVals.getInstance(localDERTaggedObject.getObject());
      }
    }
  }
  
  public RevocationValues(CertificateList[] paramArrayOfCertificateList, BasicOCSPResponse[] paramArrayOfBasicOCSPResponse, OtherRevVals paramOtherRevVals)
  {
    if (paramArrayOfCertificateList != null) {
      this.crlVals = new DERSequence(paramArrayOfCertificateList);
    }
    if (paramArrayOfBasicOCSPResponse != null) {
      this.ocspVals = new DERSequence(paramArrayOfBasicOCSPResponse);
    }
    this.otherRevVals = paramOtherRevVals;
  }
  
  public static RevocationValues getInstance(Object paramObject)
  {
    if ((paramObject instanceof RevocationValues)) {
      return (RevocationValues)paramObject;
    }
    if (paramObject != null) {
      return new RevocationValues(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public CertificateList[] getCrlVals()
  {
    CertificateList[] arrayOfCertificateList;
    if (this.crlVals == null) {
      arrayOfCertificateList = new CertificateList[0];
    }
    for (;;)
    {
      return arrayOfCertificateList;
      arrayOfCertificateList = new CertificateList[this.crlVals.size()];
      for (int i = 0; i < arrayOfCertificateList.length; i++) {
        arrayOfCertificateList[i] = CertificateList.getInstance(this.crlVals.getObjectAt(i));
      }
    }
  }
  
  public BasicOCSPResponse[] getOcspVals()
  {
    BasicOCSPResponse[] arrayOfBasicOCSPResponse;
    if (this.ocspVals == null) {
      arrayOfBasicOCSPResponse = new BasicOCSPResponse[0];
    }
    for (;;)
    {
      return arrayOfBasicOCSPResponse;
      arrayOfBasicOCSPResponse = new BasicOCSPResponse[this.ocspVals.size()];
      for (int i = 0; i < arrayOfBasicOCSPResponse.length; i++) {
        arrayOfBasicOCSPResponse[i] = BasicOCSPResponse.getInstance(this.ocspVals.getObjectAt(i));
      }
    }
  }
  
  public OtherRevVals getOtherRevVals()
  {
    return this.otherRevVals;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    if (this.crlVals != null) {
      localASN1EncodableVector.add(new DERTaggedObject(true, 0, this.crlVals));
    }
    if (this.ocspVals != null) {
      localASN1EncodableVector.add(new DERTaggedObject(true, 1, this.ocspVals));
    }
    if (this.otherRevVals != null) {
      localASN1EncodableVector.add(new DERTaggedObject(true, 2, this.otherRevVals.toASN1Primitive()));
    }
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.esf.RevocationValues
 * JD-Core Version:    0.7.0.1
 */