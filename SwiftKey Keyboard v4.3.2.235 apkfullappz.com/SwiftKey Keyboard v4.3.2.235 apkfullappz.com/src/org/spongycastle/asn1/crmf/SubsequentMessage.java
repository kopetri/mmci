package org.spongycastle.asn1.crmf;

import org.spongycastle.asn1.ASN1Integer;

public class SubsequentMessage
  extends ASN1Integer
{
  public static final SubsequentMessage challengeResp = new SubsequentMessage(1);
  public static final SubsequentMessage encrCert = new SubsequentMessage(0);
  
  private SubsequentMessage(int paramInt)
  {
    super(paramInt);
  }
  
  public static SubsequentMessage valueOf(int paramInt)
  {
    if (paramInt == 0) {
      return encrCert;
    }
    if (paramInt == 1) {
      return challengeResp;
    }
    throw new IllegalArgumentException("unknown value: " + paramInt);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.crmf.SubsequentMessage
 * JD-Core Version:    0.7.0.1
 */