package org.spongycastle.asn1;

import java.util.Date;

public class ASN1GeneralizedTime
  extends DERGeneralizedTime
{
  public ASN1GeneralizedTime(String paramString)
  {
    super(paramString);
  }
  
  public ASN1GeneralizedTime(Date paramDate)
  {
    super(paramDate);
  }
  
  ASN1GeneralizedTime(byte[] paramArrayOfByte)
  {
    super(paramArrayOfByte);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.ASN1GeneralizedTime
 * JD-Core Version:    0.7.0.1
 */