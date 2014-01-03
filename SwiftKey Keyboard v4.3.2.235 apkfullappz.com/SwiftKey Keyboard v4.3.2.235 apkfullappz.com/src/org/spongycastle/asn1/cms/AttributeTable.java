package org.spongycastle.asn1.cms;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.DERObjectIdentifier;
import org.spongycastle.asn1.DERSet;

public class AttributeTable
{
  private Hashtable attributes = new Hashtable();
  
  public AttributeTable(Hashtable paramHashtable)
  {
    this.attributes = copyTable(paramHashtable);
  }
  
  public AttributeTable(ASN1EncodableVector paramASN1EncodableVector)
  {
    for (int i = 0; i != paramASN1EncodableVector.size(); i++)
    {
      Attribute localAttribute = Attribute.getInstance(paramASN1EncodableVector.get(i));
      addAttribute(localAttribute.getAttrType(), localAttribute);
    }
  }
  
  public AttributeTable(ASN1Set paramASN1Set)
  {
    for (int i = 0; i != paramASN1Set.size(); i++)
    {
      Attribute localAttribute = Attribute.getInstance(paramASN1Set.getObjectAt(i));
      addAttribute(localAttribute.getAttrType(), localAttribute);
    }
  }
  
  public AttributeTable(Attributes paramAttributes)
  {
    this(ASN1Set.getInstance(paramAttributes.toASN1Primitive()));
  }
  
  private void addAttribute(ASN1ObjectIdentifier paramASN1ObjectIdentifier, Attribute paramAttribute)
  {
    Object localObject = this.attributes.get(paramASN1ObjectIdentifier);
    if (localObject == null)
    {
      this.attributes.put(paramASN1ObjectIdentifier, paramAttribute);
      return;
    }
    Vector localVector;
    if ((localObject instanceof Attribute))
    {
      localVector = new Vector();
      localVector.addElement(localObject);
      localVector.addElement(paramAttribute);
    }
    for (;;)
    {
      this.attributes.put(paramASN1ObjectIdentifier, localVector);
      return;
      localVector = (Vector)localObject;
      localVector.addElement(paramAttribute);
    }
  }
  
  private Hashtable copyTable(Hashtable paramHashtable)
  {
    Hashtable localHashtable = new Hashtable();
    Enumeration localEnumeration = paramHashtable.keys();
    while (localEnumeration.hasMoreElements())
    {
      Object localObject = localEnumeration.nextElement();
      localHashtable.put(localObject, paramHashtable.get(localObject));
    }
    return localHashtable;
  }
  
  public AttributeTable add(ASN1ObjectIdentifier paramASN1ObjectIdentifier, ASN1Encodable paramASN1Encodable)
  {
    AttributeTable localAttributeTable = new AttributeTable(this.attributes);
    localAttributeTable.addAttribute(paramASN1ObjectIdentifier, new Attribute(paramASN1ObjectIdentifier, new DERSet(paramASN1Encodable)));
    return localAttributeTable;
  }
  
  public Attribute get(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
  {
    Object localObject = this.attributes.get(paramASN1ObjectIdentifier);
    if ((localObject instanceof Vector)) {
      return (Attribute)((Vector)localObject).elementAt(0);
    }
    return (Attribute)localObject;
  }
  
  public Attribute get(DERObjectIdentifier paramDERObjectIdentifier)
  {
    return get(new ASN1ObjectIdentifier(paramDERObjectIdentifier.getId()));
  }
  
  public ASN1EncodableVector getAll(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    Object localObject = this.attributes.get(paramASN1ObjectIdentifier);
    if ((localObject instanceof Vector))
    {
      Enumeration localEnumeration = ((Vector)localObject).elements();
      while (localEnumeration.hasMoreElements()) {
        localASN1EncodableVector.add((Attribute)localEnumeration.nextElement());
      }
    }
    if (localObject != null) {
      localASN1EncodableVector.add((Attribute)localObject);
    }
    return localASN1EncodableVector;
  }
  
  public ASN1EncodableVector getAll(DERObjectIdentifier paramDERObjectIdentifier)
  {
    return getAll(new ASN1ObjectIdentifier(paramDERObjectIdentifier.getId()));
  }
  
  public AttributeTable remove(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
  {
    AttributeTable localAttributeTable = new AttributeTable(this.attributes);
    localAttributeTable.attributes.remove(paramASN1ObjectIdentifier);
    return localAttributeTable;
  }
  
  public int size()
  {
    int i = 0;
    Enumeration localEnumeration = this.attributes.elements();
    while (localEnumeration.hasMoreElements())
    {
      Object localObject = localEnumeration.nextElement();
      if ((localObject instanceof Vector)) {
        i += ((Vector)localObject).size();
      } else {
        i++;
      }
    }
    return i;
  }
  
  public ASN1EncodableVector toASN1EncodableVector()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    Enumeration localEnumeration1 = this.attributes.elements();
    while (localEnumeration1.hasMoreElements())
    {
      Object localObject = localEnumeration1.nextElement();
      if ((localObject instanceof Vector))
      {
        Enumeration localEnumeration2 = ((Vector)localObject).elements();
        while (localEnumeration2.hasMoreElements()) {
          localASN1EncodableVector.add(Attribute.getInstance(localEnumeration2.nextElement()));
        }
      }
      else
      {
        localASN1EncodableVector.add(Attribute.getInstance(localObject));
      }
    }
    return localASN1EncodableVector;
  }
  
  public Attributes toASN1Structure()
  {
    return new Attributes(toASN1EncodableVector());
  }
  
  public Hashtable toHashtable()
  {
    return copyTable(this.attributes);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.cms.AttributeTable
 * JD-Core Version:    0.7.0.1
 */