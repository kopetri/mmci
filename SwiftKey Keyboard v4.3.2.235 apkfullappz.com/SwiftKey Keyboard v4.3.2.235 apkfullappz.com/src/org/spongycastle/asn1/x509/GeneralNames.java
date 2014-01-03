package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;

public class GeneralNames
  extends ASN1Object
{
  private final GeneralName[] names;
  
  private GeneralNames(ASN1Sequence paramASN1Sequence)
  {
    this.names = new GeneralName[paramASN1Sequence.size()];
    for (int i = 0; i != paramASN1Sequence.size(); i++) {
      this.names[i] = GeneralName.getInstance(paramASN1Sequence.getObjectAt(i));
    }
  }
  
  public GeneralNames(GeneralName paramGeneralName)
  {
    this.names = new GeneralName[] { paramGeneralName };
  }
  
  public GeneralNames(GeneralName[] paramArrayOfGeneralName)
  {
    this.names = paramArrayOfGeneralName;
  }
  
  public static GeneralNames getInstance(Object paramObject)
  {
    if ((paramObject instanceof GeneralNames)) {
      return (GeneralNames)paramObject;
    }
    if (paramObject != null) {
      return new GeneralNames(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public static GeneralNames getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    return getInstance(ASN1Sequence.getInstance(paramASN1TaggedObject, paramBoolean));
  }
  
  public GeneralName[] getNames()
  {
    GeneralName[] arrayOfGeneralName = new GeneralName[this.names.length];
    System.arraycopy(this.names, 0, arrayOfGeneralName, 0, this.names.length);
    return arrayOfGeneralName;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return new DERSequence(this.names);
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    String str = System.getProperty("line.separator");
    localStringBuffer.append("GeneralNames:");
    localStringBuffer.append(str);
    for (int i = 0; i != this.names.length; i++)
    {
      localStringBuffer.append("    ");
      localStringBuffer.append(this.names[i]);
      localStringBuffer.append(str);
    }
    return localStringBuffer.toString();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x509.GeneralNames
 * JD-Core Version:    0.7.0.1
 */