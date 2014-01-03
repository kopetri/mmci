package org.spongycastle.asn1.cms;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERBoolean;
import org.spongycastle.asn1.DERIA5String;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERUTF8String;

public class MetaData
  extends ASN1Object
{
  private DERUTF8String fileName;
  private DERBoolean hashProtected;
  private DERIA5String mediaType;
  private Attributes otherMetaData;
  
  private MetaData(ASN1Sequence paramASN1Sequence)
  {
    this.hashProtected = DERBoolean.getInstance(paramASN1Sequence.getObjectAt(0));
    int i = 1;
    if ((1 < paramASN1Sequence.size()) && ((paramASN1Sequence.getObjectAt(1) instanceof DERUTF8String)))
    {
      i++;
      this.fileName = DERUTF8String.getInstance(paramASN1Sequence.getObjectAt(1));
    }
    if ((i < paramASN1Sequence.size()) && ((paramASN1Sequence.getObjectAt(i) instanceof DERIA5String)))
    {
      int j = i + 1;
      this.mediaType = DERIA5String.getInstance(paramASN1Sequence.getObjectAt(i));
      i = j;
    }
    if (i < paramASN1Sequence.size()) {
      this.otherMetaData = Attributes.getInstance(paramASN1Sequence.getObjectAt(i));
    }
  }
  
  public MetaData(DERBoolean paramDERBoolean, DERUTF8String paramDERUTF8String, DERIA5String paramDERIA5String, Attributes paramAttributes)
  {
    this.hashProtected = paramDERBoolean;
    this.fileName = paramDERUTF8String;
    this.mediaType = paramDERIA5String;
    this.otherMetaData = paramAttributes;
  }
  
  public static MetaData getInstance(Object paramObject)
  {
    if ((paramObject instanceof MetaData)) {
      return (MetaData)paramObject;
    }
    if (paramObject != null) {
      return new MetaData(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public DERUTF8String getFileName()
  {
    return this.fileName;
  }
  
  public DERIA5String getMediaType()
  {
    return this.mediaType;
  }
  
  public Attributes getOtherMetaData()
  {
    return this.otherMetaData;
  }
  
  public boolean isHashProtected()
  {
    return this.hashProtected.isTrue();
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.hashProtected);
    if (this.fileName != null) {
      localASN1EncodableVector.add(this.fileName);
    }
    if (this.mediaType != null) {
      localASN1EncodableVector.add(this.mediaType);
    }
    if (this.otherMetaData != null) {
      localASN1EncodableVector.add(this.otherMetaData);
    }
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.cms.MetaData
 * JD-Core Version:    0.7.0.1
 */