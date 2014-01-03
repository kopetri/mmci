package org.spongycastle.jcajce.provider.asymmetric.x509;

import java.io.IOException;
import java.io.InputStream;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.util.encoders.Base64;

public class PEMUtil
{
  private final String _footer1;
  private final String _footer2;
  private final String _header1;
  private final String _header2;
  
  PEMUtil(String paramString)
  {
    this._header1 = ("-----BEGIN " + paramString + "-----");
    this._header2 = ("-----BEGIN X509 " + paramString + "-----");
    this._footer1 = ("-----END " + paramString + "-----");
    this._footer2 = ("-----END X509 " + paramString + "-----");
  }
  
  private String readLine(InputStream paramInputStream)
    throws IOException
  {
    StringBuffer localStringBuffer = new StringBuffer();
    int i;
    do
    {
      for (;;)
      {
        i = paramInputStream.read();
        if ((i == 13) || (i == 10) || (i < 0)) {
          break;
        }
        if (i != 13) {
          localStringBuffer.append((char)i);
        }
      }
    } while ((i >= 0) && (localStringBuffer.length() == 0));
    if (i < 0) {
      return null;
    }
    return localStringBuffer.toString();
  }
  
  ASN1Sequence readPEMObject(InputStream paramInputStream)
    throws IOException
  {
    StringBuffer localStringBuffer = new StringBuffer();
    String str1;
    do
    {
      str1 = readLine(paramInputStream);
    } while ((str1 != null) && (!str1.startsWith(this._header1)) && (!str1.startsWith(this._header2)));
    for (;;)
    {
      String str2 = readLine(paramInputStream);
      if ((str2 == null) || (str2.startsWith(this._footer1)) || (str2.startsWith(this._footer2))) {
        break;
      }
      localStringBuffer.append(str2);
    }
    if (localStringBuffer.length() != 0) {
      try
      {
        ASN1Sequence localASN1Sequence = ASN1Sequence.getInstance(Base64.decode(localStringBuffer.toString()));
        return localASN1Sequence;
      }
      catch (Exception localException)
      {
        throw new IOException("malformed PEM data encountered");
      }
    }
    return null;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jcajce.provider.asymmetric.x509.PEMUtil
 * JD-Core Version:    0.7.0.1
 */