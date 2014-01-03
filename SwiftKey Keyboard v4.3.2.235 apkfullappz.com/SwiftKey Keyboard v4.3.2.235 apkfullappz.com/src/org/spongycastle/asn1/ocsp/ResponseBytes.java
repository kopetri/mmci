package org.spongycastle.asn1.ocsp;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;

public class ResponseBytes
  extends ASN1Object
{
  ASN1OctetString response;
  ASN1ObjectIdentifier responseType;
  
  public ResponseBytes(ASN1ObjectIdentifier paramASN1ObjectIdentifier, ASN1OctetString paramASN1OctetString)
  {
    this.responseType = paramASN1ObjectIdentifier;
    this.response = paramASN1OctetString;
  }
  
  public ResponseBytes(ASN1Sequence paramASN1Sequence)
  {
    this.responseType = ((ASN1ObjectIdentifier)paramASN1Sequence.getObjectAt(0));
    this.response = ((ASN1OctetString)paramASN1Sequence.getObjectAt(1));
  }
  
  public static ResponseBytes getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof ResponseBytes))) {
      return (ResponseBytes)paramObject;
    }
    if ((paramObject instanceof ASN1Sequence)) {
      return new ResponseBytes((ASN1Sequence)paramObject);
    }
    throw new IllegalArgumentException("unknown object in factory: " + paramObject.getClass().getName());
  }
  
  public static ResponseBytes getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    return getInstance(ASN1Sequence.getInstance(paramASN1TaggedObject, paramBoolean));
  }
  
  public ASN1OctetString getResponse()
  {
    return this.response;
  }
  
  public ASN1ObjectIdentifier getResponseType()
  {
    return this.responseType;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.responseType);
    localASN1EncodableVector.add(this.response);
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.ocsp.ResponseBytes
 * JD-Core Version:    0.7.0.1
 */