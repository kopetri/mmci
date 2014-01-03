package org.spongycastle.cms;

import java.io.IOException;
import java.io.InputStream;
import org.spongycastle.asn1.ASN1SequenceParser;
import org.spongycastle.asn1.ASN1StreamParser;
import org.spongycastle.asn1.cms.ContentInfoParser;

public class CMSContentInfoParser
{
  protected ContentInfoParser _contentInfo;
  protected InputStream _data;
  
  protected CMSContentInfoParser(InputStream paramInputStream)
    throws CMSException
  {
    this._data = paramInputStream;
    try
    {
      this._contentInfo = new ContentInfoParser((ASN1SequenceParser)new ASN1StreamParser(paramInputStream).readObject());
      return;
    }
    catch (IOException localIOException)
    {
      throw new CMSException("IOException reading content.", localIOException);
    }
    catch (ClassCastException localClassCastException)
    {
      throw new CMSException("Unexpected object reading content.", localClassCastException);
    }
  }
  
  public void close()
    throws IOException
  {
    this._data.close();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.CMSContentInfoParser
 * JD-Core Version:    0.7.0.1
 */