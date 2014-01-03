package org.spongycastle.tsp.cms;

import java.net.URI;
import org.spongycastle.asn1.DERBoolean;
import org.spongycastle.asn1.DERIA5String;
import org.spongycastle.asn1.DERUTF8String;
import org.spongycastle.asn1.cms.Attributes;
import org.spongycastle.asn1.cms.MetaData;
import org.spongycastle.cms.CMSException;
import org.spongycastle.operator.DigestCalculator;

public class CMSTimeStampedGenerator
{
  protected URI dataUri;
  protected MetaData metaData;
  
  private void setMetaData(boolean paramBoolean, DERUTF8String paramDERUTF8String, DERIA5String paramDERIA5String, Attributes paramAttributes)
  {
    this.metaData = new MetaData(new DERBoolean(paramBoolean), paramDERUTF8String, paramDERIA5String, paramAttributes);
  }
  
  public void initialiseMessageImprintDigestCalculator(DigestCalculator paramDigestCalculator)
    throws CMSException
  {
    new MetaDataUtil(this.metaData).initialiseMessageImprintDigestCalculator(paramDigestCalculator);
  }
  
  public void setDataUri(URI paramURI)
  {
    this.dataUri = paramURI;
  }
  
  public void setMetaData(boolean paramBoolean, String paramString1, String paramString2)
  {
    setMetaData(paramBoolean, paramString1, paramString2, null);
  }
  
  public void setMetaData(boolean paramBoolean, String paramString1, String paramString2, Attributes paramAttributes)
  {
    DERUTF8String localDERUTF8String = null;
    if (paramString1 != null) {
      localDERUTF8String = new DERUTF8String(paramString1);
    }
    DERIA5String localDERIA5String = null;
    if (paramString2 != null) {
      localDERIA5String = new DERIA5String(paramString2);
    }
    setMetaData(paramBoolean, localDERUTF8String, localDERIA5String, paramAttributes);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.tsp.cms.CMSTimeStampedGenerator
 * JD-Core Version:    0.7.0.1
 */