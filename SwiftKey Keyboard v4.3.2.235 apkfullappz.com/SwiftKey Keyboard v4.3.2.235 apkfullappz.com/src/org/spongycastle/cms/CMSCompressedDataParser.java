package org.spongycastle.cms;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.InflaterInputStream;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetStringParser;
import org.spongycastle.asn1.ASN1SequenceParser;
import org.spongycastle.asn1.cms.CompressedDataParser;
import org.spongycastle.asn1.cms.ContentInfoParser;
import org.spongycastle.operator.InputExpander;
import org.spongycastle.operator.InputExpanderProvider;

public class CMSCompressedDataParser
  extends CMSContentInfoParser
{
  public CMSCompressedDataParser(InputStream paramInputStream)
    throws CMSException
  {
    super(paramInputStream);
  }
  
  public CMSCompressedDataParser(byte[] paramArrayOfByte)
    throws CMSException
  {
    this(new ByteArrayInputStream(paramArrayOfByte));
  }
  
  public CMSTypedStream getContent()
    throws CMSException
  {
    try
    {
      ContentInfoParser localContentInfoParser = new CompressedDataParser((ASN1SequenceParser)this._contentInfo.getContent(16)).getEncapContentInfo();
      ASN1OctetStringParser localASN1OctetStringParser = (ASN1OctetStringParser)localContentInfoParser.getContent(4);
      CMSTypedStream localCMSTypedStream = new CMSTypedStream(localContentInfoParser.getContentType().toString(), new InflaterInputStream(localASN1OctetStringParser.getOctetStream()));
      return localCMSTypedStream;
    }
    catch (IOException localIOException)
    {
      throw new CMSException("IOException reading compressed content.", localIOException);
    }
  }
  
  public CMSTypedStream getContent(InputExpanderProvider paramInputExpanderProvider)
    throws CMSException
  {
    try
    {
      CompressedDataParser localCompressedDataParser = new CompressedDataParser((ASN1SequenceParser)this._contentInfo.getContent(16));
      ContentInfoParser localContentInfoParser = localCompressedDataParser.getEncapContentInfo();
      InputExpander localInputExpander = paramInputExpanderProvider.get(localCompressedDataParser.getCompressionAlgorithmIdentifier());
      ASN1OctetStringParser localASN1OctetStringParser = (ASN1OctetStringParser)localContentInfoParser.getContent(4);
      CMSTypedStream localCMSTypedStream = new CMSTypedStream(localContentInfoParser.getContentType().getId(), localInputExpander.getInputStream(localASN1OctetStringParser.getOctetStream()));
      return localCMSTypedStream;
    }
    catch (IOException localIOException)
    {
      throw new CMSException("IOException reading compressed content.", localIOException);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.CMSCompressedDataParser
 * JD-Core Version:    0.7.0.1
 */