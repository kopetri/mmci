package org.spongycastle.asn1.smime;

import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERSet;
import org.spongycastle.asn1.cms.Attribute;

public class SMIMECapabilitiesAttribute
  extends Attribute
{
  public SMIMECapabilitiesAttribute(SMIMECapabilityVector paramSMIMECapabilityVector)
  {
    super(SMIMEAttributes.smimeCapabilities, new DERSet(new DERSequence(paramSMIMECapabilityVector.toASN1EncodableVector())));
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.smime.SMIMECapabilitiesAttribute
 * JD-Core Version:    0.7.0.1
 */