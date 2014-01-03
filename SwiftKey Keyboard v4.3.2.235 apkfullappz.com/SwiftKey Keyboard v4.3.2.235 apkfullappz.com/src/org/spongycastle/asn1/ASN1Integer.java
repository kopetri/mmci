package org.spongycastle.asn1;

import java.math.BigInteger;

public class ASN1Integer
  extends DERInteger
{
  public ASN1Integer(int paramInt)
  {
    super(paramInt);
  }
  
  public ASN1Integer(BigInteger paramBigInteger)
  {
    super(paramBigInteger);
  }
  
  ASN1Integer(byte[] paramArrayOfByte)
  {
    super(paramArrayOfByte);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.ASN1Integer
 * JD-Core Version:    0.7.0.1
 */