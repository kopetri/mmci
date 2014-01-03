package org.spongycastle.asn1.x509;

import java.io.IOException;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERGeneralizedTime;
import org.spongycastle.asn1.DERIA5String;
import org.spongycastle.asn1.DERPrintableString;
import org.spongycastle.asn1.DERUTF8String;

public class X509DefaultEntryConverter
  extends X509NameEntryConverter
{
  public ASN1Primitive getConvertedValue(ASN1ObjectIdentifier paramASN1ObjectIdentifier, String paramString)
  {
    if ((paramString.length() != 0) && (paramString.charAt(0) == '#')) {
      try
      {
        ASN1Primitive localASN1Primitive = convertHexEncoded(paramString, 1);
        return localASN1Primitive;
      }
      catch (IOException localIOException)
      {
        throw new RuntimeException("can't recode value for oid " + paramASN1ObjectIdentifier.getId());
      }
    }
    if ((paramString.length() != 0) && (paramString.charAt(0) == '\\')) {
      paramString = paramString.substring(1);
    }
    if ((paramASN1ObjectIdentifier.equals(X509Name.EmailAddress)) || (paramASN1ObjectIdentifier.equals(X509Name.DC))) {
      return new DERIA5String(paramString);
    }
    if (paramASN1ObjectIdentifier.equals(X509Name.DATE_OF_BIRTH)) {
      return new DERGeneralizedTime(paramString);
    }
    if ((paramASN1ObjectIdentifier.equals(X509Name.C)) || (paramASN1ObjectIdentifier.equals(X509Name.SN)) || (paramASN1ObjectIdentifier.equals(X509Name.DN_QUALIFIER)) || (paramASN1ObjectIdentifier.equals(X509Name.TELEPHONE_NUMBER))) {
      return new DERPrintableString(paramString);
    }
    return new DERUTF8String(paramString);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x509.X509DefaultEntryConverter
 * JD-Core Version:    0.7.0.1
 */