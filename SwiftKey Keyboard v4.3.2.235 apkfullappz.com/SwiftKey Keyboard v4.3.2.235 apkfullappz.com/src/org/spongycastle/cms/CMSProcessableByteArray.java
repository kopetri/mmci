package org.spongycastle.cms;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.cms.CMSObjectIdentifiers;
import org.spongycastle.util.Arrays;

public class CMSProcessableByteArray
  implements CMSReadable, CMSTypedData
{
  private final byte[] bytes;
  private final ASN1ObjectIdentifier type;
  
  public CMSProcessableByteArray(ASN1ObjectIdentifier paramASN1ObjectIdentifier, byte[] paramArrayOfByte)
  {
    this.type = paramASN1ObjectIdentifier;
    this.bytes = paramArrayOfByte;
  }
  
  public CMSProcessableByteArray(byte[] paramArrayOfByte)
  {
    this(new ASN1ObjectIdentifier(CMSObjectIdentifiers.data.getId()), paramArrayOfByte);
  }
  
  public Object getContent()
  {
    return Arrays.clone(this.bytes);
  }
  
  public ASN1ObjectIdentifier getContentType()
  {
    return this.type;
  }
  
  public InputStream getInputStream()
  {
    return new ByteArrayInputStream(this.bytes);
  }
  
  public void write(OutputStream paramOutputStream)
    throws IOException, CMSException
  {
    paramOutputStream.write(this.bytes);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.CMSProcessableByteArray
 * JD-Core Version:    0.7.0.1
 */