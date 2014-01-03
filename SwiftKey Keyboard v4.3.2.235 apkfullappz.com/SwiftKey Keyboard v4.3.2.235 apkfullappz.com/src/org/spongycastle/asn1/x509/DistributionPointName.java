package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERTaggedObject;

public class DistributionPointName
  extends ASN1Object
  implements ASN1Choice
{
  public static final int FULL_NAME = 0;
  public static final int NAME_RELATIVE_TO_CRL_ISSUER = 1;
  ASN1Encodable name;
  int type;
  
  public DistributionPointName(int paramInt, ASN1Encodable paramASN1Encodable)
  {
    this.type = paramInt;
    this.name = paramASN1Encodable;
  }
  
  public DistributionPointName(ASN1TaggedObject paramASN1TaggedObject)
  {
    this.type = paramASN1TaggedObject.getTagNo();
    if (this.type == 0)
    {
      this.name = GeneralNames.getInstance(paramASN1TaggedObject, false);
      return;
    }
    this.name = ASN1Set.getInstance(paramASN1TaggedObject, false);
  }
  
  public DistributionPointName(GeneralNames paramGeneralNames)
  {
    this(0, paramGeneralNames);
  }
  
  private void appendObject(StringBuffer paramStringBuffer, String paramString1, String paramString2, String paramString3)
  {
    paramStringBuffer.append("    ");
    paramStringBuffer.append(paramString2);
    paramStringBuffer.append(":");
    paramStringBuffer.append(paramString1);
    paramStringBuffer.append("    ");
    paramStringBuffer.append("    ");
    paramStringBuffer.append(paramString3);
    paramStringBuffer.append(paramString1);
  }
  
  public static DistributionPointName getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof DistributionPointName))) {
      return (DistributionPointName)paramObject;
    }
    if ((paramObject instanceof ASN1TaggedObject)) {
      return new DistributionPointName((ASN1TaggedObject)paramObject);
    }
    throw new IllegalArgumentException("unknown object in factory: " + paramObject.getClass().getName());
  }
  
  public static DistributionPointName getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    return getInstance(ASN1TaggedObject.getInstance(paramASN1TaggedObject, true));
  }
  
  public ASN1Encodable getName()
  {
    return this.name;
  }
  
  public int getType()
  {
    return this.type;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return new DERTaggedObject(false, this.type, this.name);
  }
  
  public String toString()
  {
    String str = System.getProperty("line.separator");
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("DistributionPointName: [");
    localStringBuffer.append(str);
    if (this.type == 0) {
      appendObject(localStringBuffer, str, "fullName", this.name.toString());
    }
    for (;;)
    {
      localStringBuffer.append("]");
      localStringBuffer.append(str);
      return localStringBuffer.toString();
      appendObject(localStringBuffer, str, "nameRelativeToCRLIssuer", this.name.toString());
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x509.DistributionPointName
 * JD-Core Version:    0.7.0.1
 */