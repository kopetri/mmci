package org.spongycastle.cms;

import java.math.BigInteger;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Selector;

class OriginatorId
  implements Selector
{
  private X500Name issuer;
  private BigInteger serialNumber;
  private byte[] subjectKeyId;
  
  public OriginatorId(X500Name paramX500Name, BigInteger paramBigInteger)
  {
    setIssuerAndSerial(paramX500Name, paramBigInteger);
  }
  
  public OriginatorId(X500Name paramX500Name, BigInteger paramBigInteger, byte[] paramArrayOfByte)
  {
    setIssuerAndSerial(paramX500Name, paramBigInteger);
    setSubjectKeyID(paramArrayOfByte);
  }
  
  public OriginatorId(byte[] paramArrayOfByte)
  {
    setSubjectKeyID(paramArrayOfByte);
  }
  
  private boolean equalsObj(Object paramObject1, Object paramObject2)
  {
    if (paramObject1 != null) {
      return paramObject1.equals(paramObject2);
    }
    return paramObject2 == null;
  }
  
  private void setIssuerAndSerial(X500Name paramX500Name, BigInteger paramBigInteger)
  {
    this.issuer = paramX500Name;
    this.serialNumber = paramBigInteger;
  }
  
  private void setSubjectKeyID(byte[] paramArrayOfByte)
  {
    this.subjectKeyId = paramArrayOfByte;
  }
  
  public Object clone()
  {
    return new OriginatorId(this.issuer, this.serialNumber, this.subjectKeyId);
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof OriginatorId)) {}
    OriginatorId localOriginatorId;
    do
    {
      return false;
      localOriginatorId = (OriginatorId)paramObject;
    } while ((!Arrays.areEqual(this.subjectKeyId, localOriginatorId.subjectKeyId)) || (!equalsObj(this.serialNumber, localOriginatorId.serialNumber)) || (!equalsObj(this.issuer, localOriginatorId.issuer)));
    return true;
  }
  
  public X500Name getIssuer()
  {
    return this.issuer;
  }
  
  public int hashCode()
  {
    int i = Arrays.hashCode(this.subjectKeyId);
    if (this.serialNumber != null) {
      i ^= this.serialNumber.hashCode();
    }
    if (this.issuer != null) {
      i ^= this.issuer.hashCode();
    }
    return i;
  }
  
  public boolean match(Object paramObject)
  {
    return false;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.OriginatorId
 * JD-Core Version:    0.7.0.1
 */