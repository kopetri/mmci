package org.spongycastle.cms;

import java.util.Map;
import org.spongycastle.asn1.cms.AttributeTable;

public abstract interface CMSAttributeTableGenerator
{
  public static final String CONTENT_TYPE = "contentType";
  public static final String DIGEST = "digest";
  public static final String DIGEST_ALGORITHM_IDENTIFIER = "digestAlgID";
  public static final String SIGNATURE = "encryptedDigest";
  
  public abstract AttributeTable getAttributes(Map paramMap)
    throws CMSAttributeTableGenerationException;
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.CMSAttributeTableGenerator
 * JD-Core Version:    0.7.0.1
 */