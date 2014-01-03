package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERTaggedObject;

public class Target
  extends ASN1Object
  implements ASN1Choice
{
  public static final int targetGroup = 1;
  public static final int targetName;
  private GeneralName targGroup;
  private GeneralName targName;
  
  public Target(int paramInt, GeneralName paramGeneralName)
  {
    this(new DERTaggedObject(paramInt, paramGeneralName));
  }
  
  private Target(ASN1TaggedObject paramASN1TaggedObject)
  {
    switch (paramASN1TaggedObject.getTagNo())
    {
    default: 
      throw new IllegalArgumentException("unknown tag: " + paramASN1TaggedObject.getTagNo());
    case 0: 
      this.targName = GeneralName.getInstance(paramASN1TaggedObject, true);
      return;
    }
    this.targGroup = GeneralName.getInstance(paramASN1TaggedObject, true);
  }
  
  public static Target getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof Target))) {
      return (Target)paramObject;
    }
    if ((paramObject instanceof ASN1TaggedObject)) {
      return new Target((ASN1TaggedObject)paramObject);
    }
    throw new IllegalArgumentException("unknown object in factory: " + paramObject.getClass());
  }
  
  public GeneralName getTargetGroup()
  {
    return this.targGroup;
  }
  
  public GeneralName getTargetName()
  {
    return this.targName;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    if (this.targName != null) {
      return new DERTaggedObject(true, 0, this.targName);
    }
    return new DERTaggedObject(true, 1, this.targGroup);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x509.Target
 * JD-Core Version:    0.7.0.1
 */