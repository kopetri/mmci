package org.spongycastle.asn1;

class DERFactory
{
  static final ASN1Sequence EMPTY_SEQUENCE = new DERSequence();
  static final ASN1Set EMPTY_SET = new DERSet();
  
  static ASN1Sequence createSequence(ASN1EncodableVector paramASN1EncodableVector)
  {
    if (paramASN1EncodableVector.size() <= 0) {
      return EMPTY_SEQUENCE;
    }
    return new DLSequence(paramASN1EncodableVector);
  }
  
  static ASN1Set createSet(ASN1EncodableVector paramASN1EncodableVector)
  {
    if (paramASN1EncodableVector.size() <= 0) {
      return EMPTY_SET;
    }
    return new DLSet(paramASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.DERFactory
 * JD-Core Version:    0.7.0.1
 */