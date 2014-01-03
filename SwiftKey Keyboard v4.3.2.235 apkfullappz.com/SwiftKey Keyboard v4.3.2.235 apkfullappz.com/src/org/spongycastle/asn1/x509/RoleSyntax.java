package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1String;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;

public class RoleSyntax
  extends ASN1Object
{
  private GeneralNames roleAuthority;
  private GeneralName roleName;
  
  public RoleSyntax(String paramString)
  {
    this(new GeneralName(6, paramString));
  }
  
  private RoleSyntax(ASN1Sequence paramASN1Sequence)
  {
    if ((paramASN1Sequence.size() <= 0) || (paramASN1Sequence.size() > 2)) {
      throw new IllegalArgumentException("Bad sequence size: " + paramASN1Sequence.size());
    }
    int i = 0;
    if (i != paramASN1Sequence.size())
    {
      ASN1TaggedObject localASN1TaggedObject = ASN1TaggedObject.getInstance(paramASN1Sequence.getObjectAt(i));
      switch (localASN1TaggedObject.getTagNo())
      {
      default: 
        throw new IllegalArgumentException("Unknown tag in RoleSyntax");
      case 0: 
        this.roleAuthority = GeneralNames.getInstance(localASN1TaggedObject, false);
      }
      for (;;)
      {
        i++;
        break;
        this.roleName = GeneralName.getInstance(localASN1TaggedObject, true);
      }
    }
  }
  
  public RoleSyntax(GeneralName paramGeneralName)
  {
    this(null, paramGeneralName);
  }
  
  public RoleSyntax(GeneralNames paramGeneralNames, GeneralName paramGeneralName)
  {
    if ((paramGeneralName == null) || (paramGeneralName.getTagNo() != 6) || (((ASN1String)paramGeneralName.getName()).getString().equals(""))) {
      throw new IllegalArgumentException("the role name MUST be non empty and MUST use the URI option of GeneralName");
    }
    this.roleAuthority = paramGeneralNames;
    this.roleName = paramGeneralName;
  }
  
  public static RoleSyntax getInstance(Object paramObject)
  {
    if ((paramObject instanceof RoleSyntax)) {
      return (RoleSyntax)paramObject;
    }
    if (paramObject != null) {
      return new RoleSyntax(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public GeneralNames getRoleAuthority()
  {
    return this.roleAuthority;
  }
  
  public String[] getRoleAuthorityAsString()
  {
    if (this.roleAuthority == null)
    {
      arrayOfString = new String[0];
      return arrayOfString;
    }
    GeneralName[] arrayOfGeneralName = this.roleAuthority.getNames();
    String[] arrayOfString = new String[arrayOfGeneralName.length];
    int i = 0;
    label30:
    ASN1Encodable localASN1Encodable;
    if (i < arrayOfGeneralName.length)
    {
      localASN1Encodable = arrayOfGeneralName[i].getName();
      if (!(localASN1Encodable instanceof ASN1String)) {
        break label71;
      }
      arrayOfString[i] = ((ASN1String)localASN1Encodable).getString();
    }
    for (;;)
    {
      i++;
      break label30;
      break;
      label71:
      arrayOfString[i] = localASN1Encodable.toString();
    }
  }
  
  public GeneralName getRoleName()
  {
    return this.roleName;
  }
  
  public String getRoleNameAsString()
  {
    return ((ASN1String)this.roleName.getName()).getString();
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    if (this.roleAuthority != null) {
      localASN1EncodableVector.add(new DERTaggedObject(false, 0, this.roleAuthority));
    }
    localASN1EncodableVector.add(new DERTaggedObject(true, 1, this.roleName));
    return new DERSequence(localASN1EncodableVector);
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer("Name: " + getRoleNameAsString() + " - Auth: ");
    if ((this.roleAuthority == null) || (this.roleAuthority.getNames().length == 0)) {
      localStringBuffer.append("N/A");
    }
    for (;;)
    {
      return localStringBuffer.toString();
      String[] arrayOfString = getRoleAuthorityAsString();
      localStringBuffer.append('[').append(arrayOfString[0]);
      for (int i = 1; i < arrayOfString.length; i++) {
        localStringBuffer.append(", ").append(arrayOfString[i]);
      }
      localStringBuffer.append(']');
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x509.RoleSyntax
 * JD-Core Version:    0.7.0.1
 */