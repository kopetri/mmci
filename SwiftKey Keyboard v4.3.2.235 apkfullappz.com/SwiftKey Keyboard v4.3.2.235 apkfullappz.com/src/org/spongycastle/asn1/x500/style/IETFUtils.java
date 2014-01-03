package org.spongycastle.asn1.x500.style;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1String;
import org.spongycastle.asn1.DERUniversalString;
import org.spongycastle.asn1.x500.AttributeTypeAndValue;
import org.spongycastle.asn1.x500.RDN;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.asn1.x500.X500NameBuilder;
import org.spongycastle.asn1.x500.X500NameStyle;
import org.spongycastle.util.Strings;
import org.spongycastle.util.encoders.Hex;

public class IETFUtils
{
  public static void appendTypeAndValue(StringBuffer paramStringBuffer, AttributeTypeAndValue paramAttributeTypeAndValue, Hashtable paramHashtable)
  {
    String str = (String)paramHashtable.get(paramAttributeTypeAndValue.getType());
    if (str != null) {
      paramStringBuffer.append(str);
    }
    for (;;)
    {
      paramStringBuffer.append('=');
      paramStringBuffer.append(valueToString(paramAttributeTypeAndValue.getValue()));
      return;
      paramStringBuffer.append(paramAttributeTypeAndValue.getType().getId());
    }
  }
  
  private static String bytesToString(byte[] paramArrayOfByte)
  {
    char[] arrayOfChar = new char[paramArrayOfByte.length];
    for (int i = 0; i != arrayOfChar.length; i++) {
      arrayOfChar[i] = ((char)(0xFF & paramArrayOfByte[i]));
    }
    return new String(arrayOfChar);
  }
  
  public static String canonicalize(String paramString)
  {
    String str = Strings.toLowerCase(paramString.trim());
    if ((str.length() > 0) && (str.charAt(0) == '#'))
    {
      ASN1Primitive localASN1Primitive = decodeObject(str);
      if ((localASN1Primitive instanceof ASN1String)) {
        str = Strings.toLowerCase(((ASN1String)localASN1Primitive).getString().trim());
      }
    }
    return stripInternalSpaces(str);
  }
  
  public static ASN1ObjectIdentifier decodeAttrName(String paramString, Hashtable paramHashtable)
  {
    ASN1ObjectIdentifier localASN1ObjectIdentifier;
    if (Strings.toUpperCase(paramString).startsWith("OID.")) {
      localASN1ObjectIdentifier = new ASN1ObjectIdentifier(paramString.substring(4));
    }
    do
    {
      return localASN1ObjectIdentifier;
      if ((paramString.charAt(0) >= '0') && (paramString.charAt(0) <= '9')) {
        return new ASN1ObjectIdentifier(paramString);
      }
      localASN1ObjectIdentifier = (ASN1ObjectIdentifier)paramHashtable.get(Strings.toLowerCase(paramString));
    } while (localASN1ObjectIdentifier != null);
    throw new IllegalArgumentException("Unknown object id - " + paramString + " - passed to distinguished name");
  }
  
  private static ASN1Primitive decodeObject(String paramString)
  {
    try
    {
      ASN1Primitive localASN1Primitive = ASN1Primitive.fromByteArray(Hex.decode(paramString.substring(1)));
      return localASN1Primitive;
    }
    catch (IOException localIOException)
    {
      throw new IllegalStateException("unknown encoding in name: " + localIOException);
    }
  }
  
  public static RDN[] rDNsFromString(String paramString, X500NameStyle paramX500NameStyle)
  {
    X500NameTokenizer localX500NameTokenizer1 = new X500NameTokenizer(paramString);
    X500NameBuilder localX500NameBuilder = new X500NameBuilder(paramX500NameStyle);
    while (localX500NameTokenizer1.hasMoreTokens())
    {
      String str1 = localX500NameTokenizer1.nextToken();
      int i = str1.indexOf('=');
      if (i == -1) {
        throw new IllegalArgumentException("badly formated directory string");
      }
      String str2 = str1.substring(0, i);
      String str3 = str1.substring(i + 1);
      ASN1ObjectIdentifier localASN1ObjectIdentifier = paramX500NameStyle.attrNameToOID(str2);
      if (str3.indexOf('+') > 0)
      {
        X500NameTokenizer localX500NameTokenizer2 = new X500NameTokenizer(str3, '+');
        String str4 = localX500NameTokenizer2.nextToken();
        Vector localVector1 = new Vector();
        Vector localVector2 = new Vector();
        localVector1.addElement(localASN1ObjectIdentifier);
        localVector2.addElement(str4);
        while (localX500NameTokenizer2.hasMoreTokens())
        {
          String str5 = localX500NameTokenizer2.nextToken();
          int j = str5.indexOf('=');
          String str6 = str5.substring(0, j);
          String str7 = str5.substring(j + 1);
          localVector1.addElement(paramX500NameStyle.attrNameToOID(str6));
          localVector2.addElement(str7);
        }
        localX500NameBuilder.addMultiValuedRDN(toOIDArray(localVector1), toValueArray(localVector2));
      }
      else
      {
        localX500NameBuilder.addRDN(localASN1ObjectIdentifier, str3);
      }
    }
    return localX500NameBuilder.build().getRDNs();
  }
  
  public static String stripInternalSpaces(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (paramString.length() != 0)
    {
      char c1 = paramString.charAt(0);
      localStringBuffer.append(c1);
      for (int i = 1; i < paramString.length(); i++)
      {
        char c2 = paramString.charAt(i);
        if ((c1 != ' ') || (c2 != ' ')) {
          localStringBuffer.append(c2);
        }
        c1 = c2;
      }
    }
    return localStringBuffer.toString();
  }
  
  private static ASN1ObjectIdentifier[] toOIDArray(Vector paramVector)
  {
    ASN1ObjectIdentifier[] arrayOfASN1ObjectIdentifier = new ASN1ObjectIdentifier[paramVector.size()];
    for (int i = 0; i != arrayOfASN1ObjectIdentifier.length; i++) {
      arrayOfASN1ObjectIdentifier[i] = ((ASN1ObjectIdentifier)paramVector.elementAt(i));
    }
    return arrayOfASN1ObjectIdentifier;
  }
  
  private static String[] toValueArray(Vector paramVector)
  {
    String[] arrayOfString = new String[paramVector.size()];
    for (int i = 0; i != arrayOfString.length; i++) {
      arrayOfString[i] = ((String)paramVector.elementAt(i));
    }
    return arrayOfString;
  }
  
  public static ASN1Encodable valueFromHexString(String paramString, int paramInt)
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
        label71:
        if (k >= 97) {
          break label118;
        }
        arrayOfByte[i] = ((byte)(arrayOfByte[i] | (byte)(k - 48)));
      }
      for (;;)
      {
        i++;
        break;
        arrayOfByte[i] = ((byte)(10 + (j - 97) << 4));
        break label71;
        label118:
        arrayOfByte[i] = ((byte)(arrayOfByte[i] | (byte)(10 + (k - 97))));
      }
    }
    return ASN1Primitive.fromByteArray(arrayOfByte);
  }
  
  public static String valueToString(ASN1Encodable paramASN1Encodable)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    String str;
    if (((paramASN1Encodable instanceof ASN1String)) && (!(paramASN1Encodable instanceof DERUniversalString)))
    {
      str = ((ASN1String)paramASN1Encodable).getString();
      if ((str.length() > 0) && (str.charAt(0) == '#')) {
        localStringBuffer.append("\\" + str);
      }
    }
    for (;;)
    {
      int i = localStringBuffer.length();
      int j = localStringBuffer.length();
      int k = 0;
      if (j >= 2)
      {
        int m = localStringBuffer.charAt(0);
        k = 0;
        if (m == 92)
        {
          int n = localStringBuffer.charAt(1);
          k = 0;
          if (n == 35) {
            k = 0 + 2;
          }
        }
      }
      for (;;)
      {
        if (k != i)
        {
          if ((localStringBuffer.charAt(k) == ',') || (localStringBuffer.charAt(k) == '"') || (localStringBuffer.charAt(k) == '\\') || (localStringBuffer.charAt(k) == '+') || (localStringBuffer.charAt(k) == '=') || (localStringBuffer.charAt(k) == '<') || (localStringBuffer.charAt(k) == '>') || (localStringBuffer.charAt(k) == ';'))
          {
            localStringBuffer.insert(k, "\\");
            k++;
            i++;
          }
          k++;
          continue;
          localStringBuffer.append(str);
          break;
          try
          {
            localStringBuffer.append("#" + bytesToString(Hex.encode(paramASN1Encodable.toASN1Primitive().getEncoded("DER"))));
          }
          catch (IOException localIOException)
          {
            throw new IllegalArgumentException("Other value has no encoded form");
          }
        }
      }
    }
    return localStringBuffer.toString();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x500.style.IETFUtils
 * JD-Core Version:    0.7.0.1
 */