package org.spongycastle.asn1.x509.qualified;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;

public class TypeOfBiometricData
  extends ASN1Object
  implements ASN1Choice
{
  public static final int HANDWRITTEN_SIGNATURE = 1;
  public static final int PICTURE;
  ASN1Encodable obj;
  
  public TypeOfBiometricData(int paramInt)
  {
    if ((paramInt == 0) || (paramInt == 1))
    {
      this.obj = new ASN1Integer(paramInt);
      return;
    }
    throw new IllegalArgumentException("unknow PredefinedBiometricType : " + paramInt);
  }
  
  public TypeOfBiometricData(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
  {
    this.obj = paramASN1ObjectIdentifier;
  }
  
  public static TypeOfBiometricData getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof TypeOfBiometricData))) {
      return (TypeOfBiometricData)paramObject;
    }
    if ((paramObject instanceof ASN1Integer)) {
      return new TypeOfBiometricData(ASN1Integer.getInstance(paramObject).getValue().intValue());
    }
    if ((paramObject instanceof ASN1ObjectIdentifier)) {
      return new TypeOfBiometricData(ASN1ObjectIdentifier.getInstance(paramObject));
    }
    throw new IllegalArgumentException("unknown object in getInstance");
  }
  
  public ASN1ObjectIdentifier getBiometricDataOid()
  {
    return (ASN1ObjectIdentifier)this.obj;
  }
  
  public int getPredefinedBiometricType()
  {
    return ((ASN1Integer)this.obj).getValue().intValue();
  }
  
  public boolean isPredefined()
  {
    return this.obj instanceof ASN1Integer;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return this.obj.toASN1Primitive();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x509.qualified.TypeOfBiometricData
 * JD-Core Version:    0.7.0.1
 */