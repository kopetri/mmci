package org.spongycastle.asn1.cmp;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;

public class PKIStatus
  extends ASN1Object
{
  public static final int GRANTED = 0;
  public static final int GRANTED_WITH_MODS = 1;
  public static final int KEY_UPDATE_WARNING = 6;
  public static final int REJECTION = 2;
  public static final int REVOCATION_NOTIFICATION = 5;
  public static final int REVOCATION_WARNING = 4;
  public static final int WAITING = 3;
  public static final PKIStatus granted = new PKIStatus(0);
  public static final PKIStatus grantedWithMods = new PKIStatus(1);
  public static final PKIStatus keyUpdateWaiting = new PKIStatus(6);
  public static final PKIStatus rejection = new PKIStatus(2);
  public static final PKIStatus revocationNotification;
  public static final PKIStatus revocationWarning;
  public static final PKIStatus waiting = new PKIStatus(3);
  private ASN1Integer value;
  
  static
  {
    revocationWarning = new PKIStatus(4);
    revocationNotification = new PKIStatus(5);
  }
  
  private PKIStatus(int paramInt)
  {
    this(new ASN1Integer(paramInt));
  }
  
  private PKIStatus(ASN1Integer paramASN1Integer)
  {
    this.value = paramASN1Integer;
  }
  
  public static PKIStatus getInstance(Object paramObject)
  {
    if ((paramObject instanceof PKIStatus)) {
      return (PKIStatus)paramObject;
    }
    if (paramObject != null) {
      return new PKIStatus(ASN1Integer.getInstance(paramObject));
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
 * Qualified Name:     org.spongycastle.asn1.cmp.PKIStatus
 * JD-Core Version:    0.7.0.1
 */