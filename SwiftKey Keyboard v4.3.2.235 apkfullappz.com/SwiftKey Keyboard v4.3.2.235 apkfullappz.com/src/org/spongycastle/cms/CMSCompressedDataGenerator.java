package org.spongycastle.cms;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.DeflaterOutputStream;
import org.spongycastle.asn1.BERConstructedOctetString;
import org.spongycastle.asn1.DERObjectIdentifier;
import org.spongycastle.asn1.cms.CMSObjectIdentifiers;
import org.spongycastle.asn1.cms.CompressedData;
import org.spongycastle.asn1.cms.ContentInfo;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.operator.OutputCompressor;

public class CMSCompressedDataGenerator
{
  public static final String ZLIB = "1.2.840.113549.1.9.16.3.8";
  
  public CMSCompressedData generate(CMSProcessable paramCMSProcessable, String paramString)
    throws CMSException
  {
    try
    {
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
      DeflaterOutputStream localDeflaterOutputStream = new DeflaterOutputStream(localByteArrayOutputStream);
      paramCMSProcessable.write(localDeflaterOutputStream);
      localDeflaterOutputStream.close();
      AlgorithmIdentifier localAlgorithmIdentifier = new AlgorithmIdentifier(new DERObjectIdentifier(paramString));
      BERConstructedOctetString localBERConstructedOctetString = new BERConstructedOctetString(localByteArrayOutputStream.toByteArray());
      ContentInfo localContentInfo = new ContentInfo(CMSObjectIdentifiers.data, localBERConstructedOctetString);
      return new CMSCompressedData(new ContentInfo(CMSObjectIdentifiers.compressedData, new CompressedData(localAlgorithmIdentifier, localContentInfo)));
    }
    catch (IOException localIOException)
    {
      throw new CMSException("exception encoding data.", localIOException);
    }
  }
  
  public CMSCompressedData generate(CMSTypedData paramCMSTypedData, OutputCompressor paramOutputCompressor)
    throws CMSException
  {
    try
    {
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
      OutputStream localOutputStream = paramOutputCompressor.getOutputStream(localByteArrayOutputStream);
      paramCMSTypedData.write(localOutputStream);
      localOutputStream.close();
      AlgorithmIdentifier localAlgorithmIdentifier = paramOutputCompressor.getAlgorithmIdentifier();
      BERConstructedOctetString localBERConstructedOctetString = new BERConstructedOctetString(localByteArrayOutputStream.toByteArray());
      ContentInfo localContentInfo = new ContentInfo(paramCMSTypedData.getContentType(), localBERConstructedOctetString);
      return new CMSCompressedData(new ContentInfo(CMSObjectIdentifiers.compressedData, new CompressedData(localAlgorithmIdentifier, localContentInfo)));
    }
    catch (IOException localIOException)
    {
      throw new CMSException("exception encoding data.", localIOException);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.CMSCompressedDataGenerator
 * JD-Core Version:    0.7.0.1
 */