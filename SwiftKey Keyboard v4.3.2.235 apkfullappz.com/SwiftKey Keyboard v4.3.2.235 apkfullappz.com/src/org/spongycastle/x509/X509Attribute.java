package org.spongycastle.x509;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.DERSet;
import org.spongycastle.asn1.x509.Attribute;

public class X509Attribute
  extends ASN1Object
{
  Attribute attr;
  
  public X509Attribute(String paramString, ASN1Encodable paramASN1Encodable)
  {
    this.attr = new Attribute(new ASN1ObjectIdentifier(paramString), new DERSet(paramASN1Encodable));
  }
  
  public X509Attribute(String paramString, ASN1EncodableVector paramASN1EncodableVector)
  {
    this.attr = new Attribute(new ASN1ObjectIdentifier(paramString), new DERSet(paramASN1EncodableVector));
  }
  
  X509Attribute(ASN1Encodable paramASN1Encodable)
  {
    this.attr = Attribute.getInstance(paramASN1Encodable);
  }
  
  public String getOID()
  {
    return this.attr.getAttrType().getId();
  }
  
  public ASN1Encodable[] getValues()
  {
    ASN1Set localASN1Set = this.attr.getAttrValues();
    ASN1Encodable[] arrayOfASN1Encodable = new ASN1Encodable[localASN1Set.size()];
    for (int i = 0; i != localASN1Set.size(); i++) {
      arrayOfASN1Encodable[i] = localASN1Set.getObjectAt(i);
    }
    return arrayOfASN1Encodable;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return this.attr.toASN1Primitive();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.x509.X509Attribute
 * JD-Core Version:    0.7.0.1
 */