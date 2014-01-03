package org.spongycastle.cms;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.InflaterInputStream;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.cms.CompressedData;
import org.spongycastle.asn1.cms.ContentInfo;
import org.spongycastle.operator.InputExpander;
import org.spongycastle.operator.InputExpanderProvider;

public class CMSCompressedData
{
  CompressedData comData;
  ContentInfo contentInfo;
  
  public CMSCompressedData(InputStream paramInputStream)
    throws CMSException
  {
    this(CMSUtils.readContentInfo(paramInputStream));
  }
  
  public CMSCompressedData(ContentInfo paramContentInfo)
    throws CMSException
  {
    this.contentInfo = paramContentInfo;
    try
    {
      this.comData = CompressedData.getInstance(paramContentInfo.getContent());
      return;
    }
    catch (ClassCastException localClassCastException)
    {
      throw new CMSException("Malformed content.", localClassCastException);
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      throw new CMSException("Malformed content.", localIllegalArgumentException);
    }
  }
  
  public CMSCompressedData(byte[] paramArrayOfByte)
    throws CMSException
  {
    this(CMSUtils.readContentInfo(paramArrayOfByte));
  }
  
  public byte[] getContent()
    throws CMSException
  {
    InflaterInputStream localInflaterInputStream = new InflaterInputStream(((ASN1OctetString)this.comData.getEncapContentInfo().getContent()).getOctetStream());
    try
    {
      byte[] arrayOfByte = CMSUtils.streamToByteArray(localInflaterInputStream);
      return arrayOfByte;
    }
    catch (IOException localIOException)
    {
      throw new CMSException("exception reading compressed stream.", localIOException);
    }
  }
  
  public byte[] getContent(int paramInt)
    throws CMSException
  {
    InflaterInputStream localInflaterInputStream = new InflaterInputStream(((ASN1OctetString)this.comData.getEncapContentInfo().getContent()).getOctetStream());
    try
    {
      byte[] arrayOfByte = CMSUtils.streamToByteArray(localInflaterInputStream, paramInt);
      return arrayOfByte;
    }
    catch (IOException localIOException)
    {
      throw new CMSException("exception reading compressed stream.", localIOException);
    }
  }
  
  public byte[] getContent(InputExpanderProvider paramInputExpanderProvider)
    throws CMSException
  {
    ASN1OctetString localASN1OctetString = (ASN1OctetString)this.comData.getEncapContentInfo().getContent();
    InputStream localInputStream = paramInputExpanderProvider.get(this.comData.getCompressionAlgorithmIdentifier()).getInputStream(localASN1OctetString.getOctetStream());
    try
    {
      byte[] arrayOfByte = CMSUtils.streamToByteArray(localInputStream);
      return arrayOfByte;
    }
    catch (IOException localIOException)
    {
      throw new CMSException("exception reading compressed stream.", localIOException);
    }
  }
  
  public ContentInfo getContentInfo()
  {
    return this.contentInfo;
  }
  
  public ASN1ObjectIdentifier getContentType()
  {
    return this.contentInfo.getContentType();
  }
  
  public byte[] getEncoded()
    throws IOException
  {
    return this.contentInfo.getEncoded();
  }
  
  public ContentInfo toASN1Structure()
  {
    return this.contentInfo;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.CMSCompressedData
 * JD-Core Version:    0.7.0.1
 */