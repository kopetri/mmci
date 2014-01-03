package org.spongycastle.asn1.smime;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.DERSequence;

public class SMIMECapabilityVector
{
  private ASN1EncodableVector capabilities = new ASN1EncodableVector();
  
  public void addCapability(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
  {
    this.capabilities.add(new DERSequence(paramASN1ObjectIdentifier));
  }
  
  public void addCapability(ASN1ObjectIdentifier paramASN1ObjectIdentifier, int paramInt)
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(paramASN1ObjectIdentifier);
    localASN1EncodableVector.add(new ASN1Integer(paramInt));
    this.capabilities.add(new DERSequence(localASN1EncodableVector));
  }
  
  public void addCapability(ASN1ObjectIdentifier paramASN1ObjectIdentifier, ASN1Encodable paramASN1Encodable)
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(paramASN1ObjectIdentifier);
    localASN1EncodableVector.add(paramASN1Encodable);
    this.capabilities.add(new DERSequence(localASN1EncodableVector));
  }
  
  public ASN1EncodableVector toASN1EncodableVector()
  {
    return this.capabilities;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.smime.SMIMECapabilityVector
 * JD-Core Version:    0.7.0.1
 */