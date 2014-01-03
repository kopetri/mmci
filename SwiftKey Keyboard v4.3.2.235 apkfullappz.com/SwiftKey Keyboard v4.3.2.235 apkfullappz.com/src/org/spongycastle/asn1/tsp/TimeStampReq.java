package org.spongycastle.asn1.tsp;

import org.spongycastle.asn1.ASN1Boolean;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERBoolean;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x509.Extensions;

public class TimeStampReq
  extends ASN1Object
{
  ASN1Boolean certReq;
  Extensions extensions;
  MessageImprint messageImprint;
  ASN1Integer nonce;
  ASN1ObjectIdentifier tsaPolicy;
  ASN1Integer version;
  
  private TimeStampReq(ASN1Sequence paramASN1Sequence)
  {
    int i = paramASN1Sequence.size();
    this.version = ASN1Integer.getInstance(paramASN1Sequence.getObjectAt(0));
    this.messageImprint = MessageImprint.getInstance(paramASN1Sequence.getObjectAt(1));
    int j = 2;
    if (j < i)
    {
      if ((paramASN1Sequence.getObjectAt(j) instanceof ASN1ObjectIdentifier)) {
        this.tsaPolicy = ASN1ObjectIdentifier.getInstance(paramASN1Sequence.getObjectAt(j));
      }
      for (;;)
      {
        j++;
        break;
        if ((paramASN1Sequence.getObjectAt(j) instanceof ASN1Integer))
        {
          this.nonce = ASN1Integer.getInstance(paramASN1Sequence.getObjectAt(j));
        }
        else if ((paramASN1Sequence.getObjectAt(j) instanceof DERBoolean))
        {
          this.certReq = DERBoolean.getInstance(paramASN1Sequence.getObjectAt(j));
        }
        else if ((paramASN1Sequence.getObjectAt(j) instanceof ASN1TaggedObject))
        {
          ASN1TaggedObject localASN1TaggedObject = (ASN1TaggedObject)paramASN1Sequence.getObjectAt(j);
          if (localASN1TaggedObject.getTagNo() == 0) {
            this.extensions = Extensions.getInstance(localASN1TaggedObject, false);
          }
        }
      }
    }
  }
  
  public TimeStampReq(MessageImprint paramMessageImprint, ASN1ObjectIdentifier paramASN1ObjectIdentifier, ASN1Integer paramASN1Integer, ASN1Boolean paramASN1Boolean, Extensions paramExtensions)
  {
    this.version = new ASN1Integer(1);
    this.messageImprint = paramMessageImprint;
    this.tsaPolicy = paramASN1ObjectIdentifier;
    this.nonce = paramASN1Integer;
    this.certReq = paramASN1Boolean;
    this.extensions = paramExtensions;
  }
  
  public static TimeStampReq getInstance(Object paramObject)
  {
    if ((paramObject instanceof TimeStampReq)) {
      return (TimeStampReq)paramObject;
    }
    if (paramObject != null) {
      return new TimeStampReq(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public ASN1Boolean getCertReq()
  {
    return this.certReq;
  }
  
  public Extensions getExtensions()
  {
    return this.extensions;
  }
  
  public MessageImprint getMessageImprint()
  {
    return this.messageImprint;
  }
  
  public ASN1Integer getNonce()
  {
    return this.nonce;
  }
  
  public ASN1ObjectIdentifier getReqPolicy()
  {
    return this.tsaPolicy;
  }
  
  public ASN1Integer getVersion()
  {
    return this.version;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.version);
    localASN1EncodableVector.add(this.messageImprint);
    if (this.tsaPolicy != null) {
      localASN1EncodableVector.add(this.tsaPolicy);
    }
    if (this.nonce != null) {
      localASN1EncodableVector.add(this.nonce);
    }
    if ((this.certReq != null) && (this.certReq.isTrue())) {
      localASN1EncodableVector.add(this.certReq);
    }
    if (this.extensions != null) {
      localASN1EncodableVector.add(new DERTaggedObject(false, 0, this.extensions));
    }
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.tsp.TimeStampReq
 * JD-Core Version:    0.7.0.1
 */