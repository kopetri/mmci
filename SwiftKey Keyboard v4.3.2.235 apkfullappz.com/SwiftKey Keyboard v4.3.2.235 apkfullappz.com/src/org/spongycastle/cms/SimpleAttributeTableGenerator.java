package org.spongycastle.cms;

import java.util.Map;
import org.spongycastle.asn1.cms.AttributeTable;

public class SimpleAttributeTableGenerator
  implements CMSAttributeTableGenerator
{
  private final AttributeTable attributes;
  
  public SimpleAttributeTableGenerator(AttributeTable paramAttributeTable)
  {
    this.attributes = paramAttributeTable;
  }
  
  public AttributeTable getAttributes(Map paramMap)
  {
    return this.attributes;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.SimpleAttributeTableGenerator
 * JD-Core Version:    0.7.0.1
 */