package org.spongycastle.asn1.ocsp;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1Enumerated;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;

public class OCSPResponseStatus
  extends ASN1Object
{
  public static final int INTERNAL_ERROR = 2;
  public static final int MALFORMED_REQUEST = 1;
  public static final int SIG_REQUIRED = 5;
  public static final int SUCCESSFUL = 0;
  public static final int TRY_LATER = 3;
  public static final int UNAUTHORIZED = 6;
  private ASN1Enumerated value;
  
  public OCSPResponseStatus(int paramInt)
  {
    this(new ASN1Enumerated(paramInt));
  }
  
  private OCSPResponseStatus(ASN1Enumerated paramASN1Enumerated)
  {
    this.value = paramASN1Enumerated;
  }
  
  public static OCSPResponseStatus getInstance(Object paramObject)
  {
    if ((paramObject instanceof OCSPResponseStatus)) {
      return (OCSPResponseStatus)paramObject;
    }
    if (paramObject != null) {
      return new OCSPResponseStatus(ASN1Enumerated.getInstance(paramObject));
    }
    return null;
  }
  
  public BigInteger getValue()
  {
    return this.value.getValue();
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return this.value;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.ocsp.OCSPResponseStatus
 * JD-Core Version:    0.7.0.1
 */