package org.spongycastle.asn1.x509;

import java.io.IOException;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERPrintableString;
import org.spongycastle.util.Strings;

public abstract class X509NameEntryConverter
{
  protected boolean canBePrintable(String paramString)
  {
    return DERPrintableString.isPrintableString(paramString);
  }
  
  protected ASN1Primitive convertHexEncoded(String paramString, int paramInt)
    throws IOException
  {
    String str = Strings.toLowerCase(paramString);
    byte[] arrayOfByte = new byte[(str.length() - paramInt) / 2];
    int i = 0;
    if (i != arrayOfByte.length)
    {
      int j = str.charAt(paramInt + i * 2);
      int k = str.charAt(1 + (paramInt + i * 2));
      if (j < 97)
      {
        arrayOfByte[i] = ((byte)(j - 48 << 4));
        label74:
        if (k >= 97) {
          break label124;
        }
        arrayOfByte[i] = ((byte)(arrayOfByte[i] | (byte)(k - 48)));
      }
      for (;;)
      {
        i++;
        break;
        arrayOfByte[i] = ((byte)(10 + (j - 97) << 4));
        break label74;
        label124:
        arrayOfByte[i] = ((byte)(arrayOfByte[i] | (byte)(10 + (k - 97))));
      }
    }
    return new ASN1InputStream(arrayOfByte).readObject();
  }
  
  public abstract ASN1Primitive getConvertedValue(ASN1ObjectIdentifier paramASN1ObjectIdentifier, String paramString);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x509.X509NameEntryConverter
 * JD-Core Version:    0.7.0.1
 */