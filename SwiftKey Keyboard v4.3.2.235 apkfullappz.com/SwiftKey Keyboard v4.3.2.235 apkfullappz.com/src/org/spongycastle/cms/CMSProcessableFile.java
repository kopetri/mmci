package org.spongycastle.cms;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.cms.CMSObjectIdentifiers;

public class CMSProcessableFile
  implements CMSReadable, CMSTypedData
{
  private static final int DEFAULT_BUF_SIZE = 32768;
  private final byte[] buf;
  private final File file;
  private final ASN1ObjectIdentifier type;
  
  public CMSProcessableFile(File paramFile)
  {
    this(paramFile, 32768);
  }
  
  public CMSProcessableFile(File paramFile, int paramInt)
  {
    this(new ASN1ObjectIdentifier(CMSObjectIdentifiers.data.getId()), paramFile, paramInt);
  }
  
  public CMSProcessableFile(ASN1ObjectIdentifier paramASN1ObjectIdentifier, File paramFile, int paramInt)
  {
    this.type = paramASN1ObjectIdentifier;
    this.file = paramFile;
    this.buf = new byte[paramInt];
  }
  
  public Object getContent()
  {
    return this.file;
  }
  
  public ASN1ObjectIdentifier getContentType()
  {
    return this.type;
  }
  
  public InputStream getInputStream()
    throws IOException, CMSException
  {
    return new BufferedInputStream(new FileInputStream(this.file), 32768);
  }
  
  public void write(OutputStream paramOutputStream)
    throws IOException, CMSException
  {
    FileInputStream localFileInputStream = new FileInputStream(this.file);
    for (;;)
    {
      int i = localFileInputStream.read(this.buf, 0, this.buf.length);
      if (i <= 0) {
        break;
      }
      paramOutputStream.write(this.buf, 0, i);
    }
    localFileInputStream.close();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.CMSProcessableFile
 * JD-Core Version:    0.7.0.1
 */