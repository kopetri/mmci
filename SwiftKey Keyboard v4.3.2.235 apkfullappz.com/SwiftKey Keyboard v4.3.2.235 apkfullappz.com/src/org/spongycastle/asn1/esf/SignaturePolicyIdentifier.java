package org.spongycastle.asn1.esf;

import org.spongycastle.asn1.ASN1Null;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERNull;

public class SignaturePolicyIdentifier
  extends ASN1Object
{
  private boolean isSignaturePolicyImplied;
  private SignaturePolicyId signaturePolicyId;
  
  public SignaturePolicyIdentifier()
  {
    this.isSignaturePolicyImplied = true;
  }
  
  public SignaturePolicyIdentifier(SignaturePolicyId paramSignaturePolicyId)
  {
    this.signaturePolicyId = paramSignaturePolicyId;
    this.isSignaturePolicyImplied = false;
  }
  
  public static SignaturePolicyIdentifier getInstance(Object paramObject)
  {
    if ((paramObject instanceof SignaturePolicyIdentifier)) {
      return (SignaturePolicyIdentifier)paramObject;
    }
    if (((paramObject instanceof ASN1Null)) || (hasEncodedTagValue(paramObject, 5))) {
      return new SignaturePolicyIdentifier();
    }
    if (paramObject != null) {
      return new SignaturePolicyIdentifier(SignaturePolicyId.getInstance(paramObject));
    }
    return null;
  }
  
  public SignaturePolicyId getSignaturePolicyId()
  {
    return this.signaturePolicyId;
  }
  
  public boolean isSignaturePolicyImplied()
  {
    return this.isSignaturePolicyImplied;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    if (this.isSignaturePolicyImplied) {
      return new DERNull();
    }
    return this.signaturePolicyId.toASN1Primitive();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.esf.SignaturePolicyIdentifier
 * JD-Core Version:    0.7.0.1
 */