package org.spongycastle.asn1.eac;

import java.io.UnsupportedEncodingException;

public class CertificateHolderReference
{
  private static final String ReferenceEncoding = "ISO-8859-1";
  private String countryCode;
  private String holderMnemonic;
  private String sequenceNumber;
  
  public CertificateHolderReference(String paramString1, String paramString2, String paramString3)
  {
    this.countryCode = paramString1;
    this.holderMnemonic = paramString2;
    this.sequenceNumber = paramString3;
  }
  
  CertificateHolderReference(byte[] paramArrayOfByte)
  {
    try
    {
      String str = new String(paramArrayOfByte, "ISO-8859-1");
      this.countryCode = str.substring(0, 2);
      this.holderMnemonic = str.substring(2, -5 + str.length());
      this.sequenceNumber = str.substring(-5 + str.length());
      return;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      throw new IllegalStateException(localUnsupportedEncodingException.toString());
    }
  }
  
  public String getCountryCode()
  {
    return this.countryCode;
  }
  
  public byte[] getEncoded()
  {
    String str = this.countryCode + this.holderMnemonic + this.sequenceNumber;
    try
    {
      byte[] arrayOfByte = str.getBytes("ISO-8859-1");
      return arrayOfByte;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      throw new IllegalStateException(localUnsupportedEncodingException.toString());
    }
  }
  
  public String getHolderMnemonic()
  {
    return this.holderMnemonic;
  }
  
  public String getSequenceNumber()
  {
    return this.sequenceNumber;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.eac.CertificateHolderReference
 * JD-Core Version:    0.7.0.1
 */