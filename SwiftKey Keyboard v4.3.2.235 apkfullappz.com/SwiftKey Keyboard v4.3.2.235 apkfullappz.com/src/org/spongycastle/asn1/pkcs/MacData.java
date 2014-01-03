package org.spongycastle.asn1.pkcs;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.DigestInfo;

public class MacData
  extends ASN1Object
{
  private static final BigInteger ONE = BigInteger.valueOf(1L);
  DigestInfo digInfo;
  BigInteger iterationCount;
  byte[] salt;
  
  private MacData(ASN1Sequence paramASN1Sequence)
  {
    this.digInfo = DigestInfo.getInstance(paramASN1Sequence.getObjectAt(0));
    this.salt = ((ASN1OctetString)paramASN1Sequence.getObjectAt(1)).getOctets();
    if (paramASN1Sequence.size() == 3)
    {
      this.iterationCount = ((ASN1Integer)paramASN1Sequence.getObjectAt(2)).getValue();
      return;
    }
    this.iterationCount = ONE;
  }
  
  public MacData(DigestInfo paramDigestInfo, byte[] paramArrayOfByte, int paramInt)
  {
    this.digInfo = paramDigestInfo;
    this.salt = paramArrayOfByte;
    this.iterationCount = BigInteger.valueOf(paramInt);
  }
  
  public static MacData getInstance(Object paramObject)
  {
    if ((paramObject instanceof MacData)) {
      return (MacData)paramObject;
    }
    if (paramObject != null) {
      return new MacData(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public BigInteger getIterationCount()
  {
    return this.iterationCount;
  }
  
  public DigestInfo getMac()
  {
    return this.digInfo;
  }
  
  public byte[] getSalt()
  {
    return this.salt;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.digInfo);
    localASN1EncodableVector.add(new DEROctetString(this.salt));
    if (!this.iterationCount.equals(ONE)) {
      localASN1EncodableVector.add(new ASN1Integer(this.iterationCount));
    }
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.pkcs.MacData
 * JD-Core Version:    0.7.0.1
 */