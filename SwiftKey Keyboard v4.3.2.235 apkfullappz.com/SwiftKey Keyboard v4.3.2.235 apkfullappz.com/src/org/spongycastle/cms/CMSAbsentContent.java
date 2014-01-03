package org.spongycastle.cms;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.cms.CMSObjectIdentifiers;

public class CMSAbsentContent
  implements CMSReadable, CMSTypedData
{
  private final ASN1ObjectIdentifier type;
  
  public CMSAbsentContent()
  {
    this(new ASN1ObjectIdentifier(CMSObjectIdentifiers.data.getId()));
  }
  
  public CMSAbsentContent(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
  {
    this.type = paramASN1ObjectIdentifier;
  }
  
  public Object getContent()
  {
    return null;
  }
  
  public ASN1ObjectIdentifier getContentType()
  {
    return this.type;
  }
  
  public InputStream getInputStream()
  {
    return null;
  }
  
  public void write(OutputStream paramOutputStream)
    throws IOException, CMSException
  {}
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.CMSAbsentContent
 * JD-Core Version:    0.7.0.1
 */