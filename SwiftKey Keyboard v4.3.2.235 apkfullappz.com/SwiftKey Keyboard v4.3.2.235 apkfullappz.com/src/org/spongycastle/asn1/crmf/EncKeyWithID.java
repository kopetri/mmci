package org.spongycastle.asn1.crmf;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERUTF8String;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.x509.GeneralName;

public class EncKeyWithID
  extends ASN1Object
{
  private final ASN1Encodable identifier;
  private final PrivateKeyInfo privKeyInfo;
  
  private EncKeyWithID(ASN1Sequence paramASN1Sequence)
  {
    this.privKeyInfo = PrivateKeyInfo.getInstance(paramASN1Sequence.getObjectAt(0));
    if (paramASN1Sequence.size() > 1)
    {
      if (!(paramASN1Sequence.getObjectAt(1) instanceof DERUTF8String))
      {
        this.identifier = GeneralName.getInstance(paramASN1Sequence.getObjectAt(1));
        return;
      }
      this.identifier = paramASN1Sequence.getObjectAt(1);
      return;
    }
    this.identifier = null;
  }
  
  public EncKeyWithID(PrivateKeyInfo paramPrivateKeyInfo)
  {
    this.privKeyInfo = paramPrivateKeyInfo;
    this.identifier = null;
  }
  
  public EncKeyWithID(PrivateKeyInfo paramPrivateKeyInfo, DERUTF8String paramDERUTF8String)
  {
    this.privKeyInfo = paramPrivateKeyInfo;
    this.identifier = paramDERUTF8String;
  }
  
  public EncKeyWithID(PrivateKeyInfo paramPrivateKeyInfo, GeneralName paramGeneralName)
  {
    this.privKeyInfo = paramPrivateKeyInfo;
    this.identifier = paramGeneralName;
  }
  
  public static EncKeyWithID getInstance(Object paramObject)
  {
    if ((paramObject instanceof EncKeyWithID)) {
      return (EncKeyWithID)paramObject;
    }
    if (paramObject != null) {
      return new EncKeyWithID(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public ASN1Encodable getIdentifier()
  {
    return this.identifier;
  }
  
  public PrivateKeyInfo getPrivateKey()
  {
    return this.privKeyInfo;
  }
  
  public boolean hasIdentifier()
  {
    return this.identifier != null;
  }
  
  public boolean isIdentifierUTF8String()
  {
    return this.identifier instanceof DERUTF8String;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.privKeyInfo);
    if (this.identifier != null) {
      localASN1EncodableVector.add(this.identifier);
    }
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.crmf.EncKeyWithID
 * JD-Core Version:    0.7.0.1
 */