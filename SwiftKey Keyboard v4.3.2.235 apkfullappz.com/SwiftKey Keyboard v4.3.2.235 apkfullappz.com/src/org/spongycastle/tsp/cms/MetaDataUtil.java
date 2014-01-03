package org.spongycastle.tsp.cms;

import java.io.IOException;
import java.io.OutputStream;
import org.spongycastle.asn1.ASN1String;
import org.spongycastle.asn1.cms.Attributes;
import org.spongycastle.asn1.cms.MetaData;
import org.spongycastle.cms.CMSException;
import org.spongycastle.operator.DigestCalculator;

class MetaDataUtil
{
  private final MetaData metaData;
  
  MetaDataUtil(MetaData paramMetaData)
  {
    this.metaData = paramMetaData;
  }
  
  private String convertString(ASN1String paramASN1String)
  {
    if (paramASN1String != null) {
      return paramASN1String.toString();
    }
    return null;
  }
  
  String getFileName()
  {
    if (this.metaData != null) {
      return convertString(this.metaData.getFileName());
    }
    return null;
  }
  
  String getMediaType()
  {
    if (this.metaData != null) {
      return convertString(this.metaData.getMediaType());
    }
    return null;
  }
  
  Attributes getOtherMetaData()
  {
    if (this.metaData != null) {
      return this.metaData.getOtherMetaData();
    }
    return null;
  }
  
  void initialiseMessageImprintDigestCalculator(DigestCalculator paramDigestCalculator)
    throws CMSException
  {
    if ((this.metaData != null) && (this.metaData.isHashProtected())) {}
    try
    {
      paramDigestCalculator.getOutputStream().write(this.metaData.getEncoded("DER"));
      return;
    }
    catch (IOException localIOException)
    {
      throw new CMSException("unable to initialise calculator from metaData: " + localIOException.getMessage(), localIOException);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.tsp.cms.MetaDataUtil
 * JD-Core Version:    0.7.0.1
 */