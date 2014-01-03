package org.spongycastle.asn1.tsp;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1Boolean;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1GeneralizedTime;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x509.Extensions;
import org.spongycastle.asn1.x509.GeneralName;

public class TSTInfo
  extends ASN1Object
{
  private Accuracy accuracy;
  private Extensions extensions;
  private ASN1GeneralizedTime genTime;
  private MessageImprint messageImprint;
  private ASN1Integer nonce;
  private ASN1Boolean ordering;
  private ASN1Integer serialNumber;
  private GeneralName tsa;
  private ASN1ObjectIdentifier tsaPolicyId;
  private ASN1Integer version;
  
  public TSTInfo(ASN1ObjectIdentifier paramASN1ObjectIdentifier, MessageImprint paramMessageImprint, ASN1Integer paramASN1Integer1, ASN1GeneralizedTime paramASN1GeneralizedTime, Accuracy paramAccuracy, ASN1Boolean paramASN1Boolean, ASN1Integer paramASN1Integer2, GeneralName paramGeneralName, Extensions paramExtensions)
  {
    this.version = new ASN1Integer(1);
    this.tsaPolicyId = paramASN1ObjectIdentifier;
    this.messageImprint = paramMessageImprint;
    this.serialNumber = paramASN1Integer1;
    this.genTime = paramASN1GeneralizedTime;
    this.accuracy = paramAccuracy;
    this.ordering = paramASN1Boolean;
    this.nonce = paramASN1Integer2;
    this.tsa = paramGeneralName;
    this.extensions = paramExtensions;
  }
  
  private TSTInfo(ASN1Sequence paramASN1Sequence)
  {
    Enumeration localEnumeration = paramASN1Sequence.getObjects();
    this.version = ASN1Integer.getInstance(localEnumeration.nextElement());
    this.tsaPolicyId = ASN1ObjectIdentifier.getInstance(localEnumeration.nextElement());
    this.messageImprint = MessageImprint.getInstance(localEnumeration.nextElement());
    this.serialNumber = ASN1Integer.getInstance(localEnumeration.nextElement());
    this.genTime = ASN1GeneralizedTime.getInstance(localEnumeration.nextElement());
    this.ordering = ASN1Boolean.getInstance(false);
    while (localEnumeration.hasMoreElements())
    {
      ASN1Object localASN1Object = (ASN1Object)localEnumeration.nextElement();
      if ((localASN1Object instanceof ASN1TaggedObject))
      {
        DERTaggedObject localDERTaggedObject = (DERTaggedObject)localASN1Object;
        switch (localDERTaggedObject.getTagNo())
        {
        default: 
          throw new IllegalArgumentException("Unknown tag value " + localDERTaggedObject.getTagNo());
        case 0: 
          this.tsa = GeneralName.getInstance(localDERTaggedObject, true);
          break;
        case 1: 
          this.extensions = Extensions.getInstance(localDERTaggedObject, false);
          break;
        }
      }
      else if (((localASN1Object instanceof ASN1Sequence)) || ((localASN1Object instanceof Accuracy)))
      {
        this.accuracy = Accuracy.getInstance(localASN1Object);
      }
      else if ((localASN1Object instanceof ASN1Boolean))
      {
        this.ordering = ASN1Boolean.getInstance(localASN1Object);
      }
      else if ((localASN1Object instanceof ASN1Integer))
      {
        this.nonce = ASN1Integer.getInstance(localASN1Object);
      }
    }
  }
  
  public static TSTInfo getInstance(Object paramObject)
  {
    if ((paramObject instanceof TSTInfo)) {
      return (TSTInfo)paramObject;
    }
    if (paramObject != null) {
      return new TSTInfo(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public Accuracy getAccuracy()
  {
    return this.accuracy;
  }
  
  public Extensions getExtensions()
  {
    return this.extensions;
  }
  
  public ASN1GeneralizedTime getGenTime()
  {
    return this.genTime;
  }
  
  public MessageImprint getMessageImprint()
  {
    return this.messageImprint;
  }
  
  public ASN1Integer getNonce()
  {
    return this.nonce;
  }
  
  public ASN1Boolean getOrdering()
  {
    return this.ordering;
  }
  
  public ASN1ObjectIdentifier getPolicy()
  {
    return this.tsaPolicyId;
  }
  
  public ASN1Integer getSerialNumber()
  {
    return this.serialNumber;
  }
  
  public GeneralName getTsa()
  {
    return this.tsa;
  }
  
  public ASN1Integer getVersion()
  {
    return this.version;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.version);
    localASN1EncodableVector.add(this.tsaPolicyId);
    localASN1EncodableVector.add(this.messageImprint);
    localASN1EncodableVector.add(this.serialNumber);
    localASN1EncodableVector.add(this.genTime);
    if (this.accuracy != null) {
      localASN1EncodableVector.add(this.accuracy);
    }
    if ((this.ordering != null) && (this.ordering.isTrue())) {
      localASN1EncodableVector.add(this.ordering);
    }
    if (this.nonce != null) {
      localASN1EncodableVector.add(this.nonce);
    }
    if (this.tsa != null) {
      localASN1EncodableVector.add(new DERTaggedObject(true, 0, this.tsa));
    }
    if (this.extensions != null) {
      localASN1EncodableVector.add(new DERTaggedObject(false, 1, this.extensions));
    }
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.tsp.TSTInfo
 * JD-Core Version:    0.7.0.1
 */