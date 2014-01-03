package org.spongycastle.asn1.misc;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;

public class IDEACBCPar
  extends ASN1Object
{
  ASN1OctetString iv;
  
  public IDEACBCPar(ASN1Sequence paramASN1Sequence)
  {
    if (paramASN1Sequence.size() == 1)
    {
      this.iv = ((ASN1OctetString)paramASN1Sequence.getObjectAt(0));
      return;
    }
    this.iv = null;
  }
  
  public IDEACBCPar(byte[] paramArrayOfByte)
  {
    this.iv = new DEROctetString(paramArrayOfByte);
  }
  
  public static IDEACBCPar getInstance(Object paramObject)
  {
    if ((paramObject instanceof IDEACBCPar)) {
      return (IDEACBCPar)paramObject;
    }
    if (paramObject != null) {
      return new IDEACBCPar(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public byte[] getIV()
  {
    if (this.iv != null) {
      return this.iv.getOctets();
    }
    return null;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    if (this.iv != null) {
      localASN1EncodableVector.add(this.iv);
    }
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.misc.IDEACBCPar
 * JD-Core Version:    0.7.0.1
 */