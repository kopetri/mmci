package org.spongycastle.asn1.icao;

import java.math.BigInteger;
import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class DataGroupHash
  extends ASN1Object
{
  ASN1OctetString dataGroupHashValue;
  ASN1Integer dataGroupNumber;
  
  public DataGroupHash(int paramInt, ASN1OctetString paramASN1OctetString)
  {
    this.dataGroupNumber = new ASN1Integer(paramInt);
    this.dataGroupHashValue = paramASN1OctetString;
  }
  
  private DataGroupHash(ASN1Sequence paramASN1Sequence)
  {
    Enumeration localEnumeration = paramASN1Sequence.getObjects();
    this.dataGroupNumber = ASN1Integer.getInstance(localEnumeration.nextElement());
    this.dataGroupHashValue = ASN1OctetString.getInstance(localEnumeration.nextElement());
  }
  
  public static DataGroupHash getInstance(Object paramObject)
  {
    if ((paramObject instanceof DataGroupHash)) {
      return (DataGroupHash)paramObject;
    }
    if (paramObject != null) {
      return new DataGroupHash(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public ASN1OctetString getDataGroupHashValue()
  {
    return this.dataGroupHashValue;
  }
  
  public int getDataGroupNumber()
  {
    return this.dataGroupNumber.getValue().intValue();
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.dataGroupNumber);
    localASN1EncodableVector.add(this.dataGroupHashValue);
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.icao.DataGroupHash
 * JD-Core Version:    0.7.0.1
 */