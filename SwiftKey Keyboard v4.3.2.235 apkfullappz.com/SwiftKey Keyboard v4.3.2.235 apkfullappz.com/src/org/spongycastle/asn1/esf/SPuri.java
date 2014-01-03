package org.spongycastle.asn1.esf;

import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERIA5String;

public class SPuri
{
  private DERIA5String uri;
  
  public SPuri(DERIA5String paramDERIA5String)
  {
    this.uri = paramDERIA5String;
  }
  
  public static SPuri getInstance(Object paramObject)
  {
    if ((paramObject instanceof SPuri)) {
      return (SPuri)paramObject;
    }
    if ((paramObject instanceof DERIA5String)) {
      return new SPuri(DERIA5String.getInstance(paramObject));
    }
    return null;
  }
  
  public DERIA5String getUri()
  {
    return this.uri;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return this.uri.toASN1Primitive();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.esf.SPuri
 * JD-Core Version:    0.7.0.1
 */