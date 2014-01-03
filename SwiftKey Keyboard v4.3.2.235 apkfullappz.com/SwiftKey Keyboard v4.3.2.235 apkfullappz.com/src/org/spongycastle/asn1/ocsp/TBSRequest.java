package org.spongycastle.asn1.ocsp;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x509.Extensions;
import org.spongycastle.asn1.x509.GeneralName;
import org.spongycastle.asn1.x509.X509Extensions;

public class TBSRequest
  extends ASN1Object
{
  private static final ASN1Integer V1 = new ASN1Integer(0);
  Extensions requestExtensions;
  ASN1Sequence requestList;
  GeneralName requestorName;
  ASN1Integer version;
  boolean versionSet;
  
  private TBSRequest(ASN1Sequence paramASN1Sequence)
  {
    int i;
    if ((paramASN1Sequence.getObjectAt(0) instanceof ASN1TaggedObject)) {
      if (((ASN1TaggedObject)paramASN1Sequence.getObjectAt(0)).getTagNo() == 0)
      {
        this.versionSet = true;
        this.version = ASN1Integer.getInstance((ASN1TaggedObject)paramASN1Sequence.getObjectAt(0), true);
        i = 0 + 1;
      }
    }
    for (;;)
    {
      if ((paramASN1Sequence.getObjectAt(i) instanceof ASN1TaggedObject))
      {
        int k = i + 1;
        this.requestorName = GeneralName.getInstance((ASN1TaggedObject)paramASN1Sequence.getObjectAt(i), true);
        i = k;
      }
      int j = i + 1;
      this.requestList = ((ASN1Sequence)paramASN1Sequence.getObjectAt(i));
      if (paramASN1Sequence.size() == j + 1) {
        this.requestExtensions = Extensions.getInstance((ASN1TaggedObject)paramASN1Sequence.getObjectAt(j), true);
      }
      return;
      this.version = V1;
      i = 0;
      continue;
      this.version = V1;
      i = 0;
    }
  }
  
  public TBSRequest(GeneralName paramGeneralName, ASN1Sequence paramASN1Sequence, Extensions paramExtensions)
  {
    this.version = V1;
    this.requestorName = paramGeneralName;
    this.requestList = paramASN1Sequence;
    this.requestExtensions = paramExtensions;
  }
  
  public TBSRequest(GeneralName paramGeneralName, ASN1Sequence paramASN1Sequence, X509Extensions paramX509Extensions)
  {
    this.version = V1;
    this.requestorName = paramGeneralName;
    this.requestList = paramASN1Sequence;
    this.requestExtensions = Extensions.getInstance(paramX509Extensions);
  }
  
  public static TBSRequest getInstance(Object paramObject)
  {
    if ((paramObject instanceof TBSRequest)) {
      return (TBSRequest)paramObject;
    }
    if (paramObject != null) {
      return new TBSRequest(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public static TBSRequest getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    return getInstance(ASN1Sequence.getInstance(paramASN1TaggedObject, paramBoolean));
  }
  
  public Extensions getRequestExtensions()
  {
    return this.requestExtensions;
  }
  
  public ASN1Sequence getRequestList()
  {
    return this.requestList;
  }
  
  public GeneralName getRequestorName()
  {
    return this.requestorName;
  }
  
  public ASN1Integer getVersion()
  {
    return this.version;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    if ((!this.version.equals(V1)) || (this.versionSet)) {
      localASN1EncodableVector.add(new DERTaggedObject(true, 0, this.version));
    }
    if (this.requestorName != null) {
      localASN1EncodableVector.add(new DERTaggedObject(true, 1, this.requestorName));
    }
    localASN1EncodableVector.add(this.requestList);
    if (this.requestExtensions != null) {
      localASN1EncodableVector.add(new DERTaggedObject(true, 2, this.requestExtensions));
    }
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.ocsp.TBSRequest
 * JD-Core Version:    0.7.0.1
 */