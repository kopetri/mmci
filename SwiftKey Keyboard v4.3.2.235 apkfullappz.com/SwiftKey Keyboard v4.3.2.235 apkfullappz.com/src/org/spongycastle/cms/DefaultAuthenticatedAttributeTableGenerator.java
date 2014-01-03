package org.spongycastle.cms;

import java.util.Hashtable;
import java.util.Map;
import org.spongycastle.asn1.DERObjectIdentifier;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSet;
import org.spongycastle.asn1.cms.Attribute;
import org.spongycastle.asn1.cms.AttributeTable;
import org.spongycastle.asn1.cms.CMSAttributes;

public class DefaultAuthenticatedAttributeTableGenerator
  implements CMSAttributeTableGenerator
{
  private final Hashtable table;
  
  public DefaultAuthenticatedAttributeTableGenerator()
  {
    this.table = new Hashtable();
  }
  
  public DefaultAuthenticatedAttributeTableGenerator(AttributeTable paramAttributeTable)
  {
    if (paramAttributeTable != null)
    {
      this.table = paramAttributeTable.toHashtable();
      return;
    }
    this.table = new Hashtable();
  }
  
  protected Hashtable createStandardAttributeTable(Map paramMap)
  {
    Hashtable localHashtable = (Hashtable)this.table.clone();
    if (!localHashtable.containsKey(CMSAttributes.contentType))
    {
      DERObjectIdentifier localDERObjectIdentifier = (DERObjectIdentifier)paramMap.get("contentType");
      Attribute localAttribute2 = new Attribute(CMSAttributes.contentType, new DERSet(localDERObjectIdentifier));
      localHashtable.put(localAttribute2.getAttrType(), localAttribute2);
    }
    if (!localHashtable.containsKey(CMSAttributes.messageDigest))
    {
      byte[] arrayOfByte = (byte[])paramMap.get("digest");
      Attribute localAttribute1 = new Attribute(CMSAttributes.messageDigest, new DERSet(new DEROctetString(arrayOfByte)));
      localHashtable.put(localAttribute1.getAttrType(), localAttribute1);
    }
    return localHashtable;
  }
  
  public AttributeTable getAttributes(Map paramMap)
  {
    return new AttributeTable(createStandardAttributeTable(paramMap));
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.DefaultAuthenticatedAttributeTableGenerator
 * JD-Core Version:    0.7.0.1
 */