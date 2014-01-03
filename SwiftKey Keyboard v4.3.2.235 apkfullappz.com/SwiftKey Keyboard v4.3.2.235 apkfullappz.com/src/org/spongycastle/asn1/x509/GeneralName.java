package org.spongycastle.asn1.x509;

import java.io.IOException;
import java.util.StringTokenizer;
import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERIA5String;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.util.IPAddress;

public class GeneralName
  extends ASN1Object
  implements ASN1Choice
{
  public static final int dNSName = 2;
  public static final int directoryName = 4;
  public static final int ediPartyName = 5;
  public static final int iPAddress = 7;
  public static final int otherName = 0;
  public static final int registeredID = 8;
  public static final int rfc822Name = 1;
  public static final int uniformResourceIdentifier = 6;
  public static final int x400Address = 3;
  private ASN1Encodable obj;
  private int tag;
  
  public GeneralName(int paramInt, String paramString)
  {
    this.tag = paramInt;
    if ((paramInt == 1) || (paramInt == 2) || (paramInt == 6))
    {
      this.obj = new DERIA5String(paramString);
      return;
    }
    if (paramInt == 8)
    {
      this.obj = new ASN1ObjectIdentifier(paramString);
      return;
    }
    if (paramInt == 4)
    {
      this.obj = new X500Name(paramString);
      return;
    }
    if (paramInt == 7)
    {
      byte[] arrayOfByte = toGeneralNameEncoding(paramString);
      if (arrayOfByte != null)
      {
        this.obj = new DEROctetString(arrayOfByte);
        return;
      }
      throw new IllegalArgumentException("IP Address is invalid");
    }
    throw new IllegalArgumentException("can't process String for tag: " + paramInt);
  }
  
  public GeneralName(int paramInt, ASN1Encodable paramASN1Encodable)
  {
    this.obj = paramASN1Encodable;
    this.tag = paramInt;
  }
  
  public GeneralName(X500Name paramX500Name)
  {
    this.obj = paramX500Name;
    this.tag = 4;
  }
  
  public GeneralName(X509Name paramX509Name)
  {
    this.obj = X500Name.getInstance(paramX509Name);
    this.tag = 4;
  }
  
  private void copyInts(int[] paramArrayOfInt, byte[] paramArrayOfByte, int paramInt)
  {
    for (int i = 0; i != paramArrayOfInt.length; i++)
    {
      paramArrayOfByte[(paramInt + i * 2)] = ((byte)(paramArrayOfInt[i] >> 8));
      paramArrayOfByte[(paramInt + (1 + i * 2))] = ((byte)paramArrayOfInt[i]);
    }
  }
  
  public static GeneralName getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof GeneralName))) {
      return (GeneralName)paramObject;
    }
    ASN1TaggedObject localASN1TaggedObject;
    int i;
    if ((paramObject instanceof ASN1TaggedObject))
    {
      localASN1TaggedObject = (ASN1TaggedObject)paramObject;
      i = localASN1TaggedObject.getTagNo();
    }
    switch (i)
    {
    default: 
      if (!(paramObject instanceof byte[])) {
        break;
      }
    case 0: 
    case 1: 
    case 2: 
    case 3: 
    case 4: 
    case 5: 
    case 6: 
    case 7: 
    case 8: 
      try
      {
        GeneralName localGeneralName = getInstance(ASN1Primitive.fromByteArray((byte[])paramObject));
        return localGeneralName;
      }
      catch (IOException localIOException)
      {
        throw new IllegalArgumentException("unable to parse encoded general name");
      }
      return new GeneralName(i, ASN1Sequence.getInstance(localASN1TaggedObject, false));
      return new GeneralName(i, DERIA5String.getInstance(localASN1TaggedObject, false));
      return new GeneralName(i, DERIA5String.getInstance(localASN1TaggedObject, false));
      throw new IllegalArgumentException("unknown tag: " + i);
      return new GeneralName(i, X500Name.getInstance(localASN1TaggedObject, true));
      return new GeneralName(i, ASN1Sequence.getInstance(localASN1TaggedObject, false));
      return new GeneralName(i, DERIA5String.getInstance(localASN1TaggedObject, false));
      return new GeneralName(i, ASN1OctetString.getInstance(localASN1TaggedObject, false));
      return new GeneralName(i, ASN1ObjectIdentifier.getInstance(localASN1TaggedObject, false));
    }
    throw new IllegalArgumentException("unknown object in getInstance: " + paramObject.getClass().getName());
  }
  
  public static GeneralName getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    return getInstance(ASN1TaggedObject.getInstance(paramASN1TaggedObject, true));
  }
  
  private void parseIPv4(String paramString, byte[] paramArrayOfByte, int paramInt)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "./");
    int j;
    for (int i = 0; localStringTokenizer.hasMoreTokens(); i = j)
    {
      j = i + 1;
      paramArrayOfByte[(paramInt + i)] = ((byte)Integer.parseInt(localStringTokenizer.nextToken()));
    }
  }
  
  private void parseIPv4Mask(String paramString, byte[] paramArrayOfByte, int paramInt)
  {
    int i = Integer.parseInt(paramString);
    for (int j = 0; j != i; j++)
    {
      int k = paramInt + j / 8;
      paramArrayOfByte[k] = ((byte)(paramArrayOfByte[k] | 1 << 7 - j % 8));
    }
  }
  
  private int[] parseIPv6(String paramString)
  {
    StringTokenizer localStringTokenizer1 = new StringTokenizer(paramString, ":", true);
    int i = 0;
    int[] arrayOfInt = new int[8];
    if ((paramString.charAt(0) == ':') && (paramString.charAt(1) == ':')) {
      localStringTokenizer1.nextToken();
    }
    int j = -1;
    for (;;)
    {
      int n;
      if (localStringTokenizer1.hasMoreTokens())
      {
        String str = localStringTokenizer1.nextToken();
        if (str.equals(":"))
        {
          j = i;
          int i1 = i + 1;
          arrayOfInt[i] = 0;
          i = i1;
          continue;
        }
        if (str.indexOf('.') < 0)
        {
          n = i + 1;
          arrayOfInt[i] = Integer.parseInt(str, 16);
          if (localStringTokenizer1.hasMoreTokens())
          {
            localStringTokenizer1.nextToken();
            i = n;
          }
        }
        else
        {
          StringTokenizer localStringTokenizer2 = new StringTokenizer(str, ".");
          int m = i + 1;
          arrayOfInt[i] = (Integer.parseInt(localStringTokenizer2.nextToken()) << 8 | Integer.parseInt(localStringTokenizer2.nextToken()));
          i = m + 1;
          arrayOfInt[m] = (Integer.parseInt(localStringTokenizer2.nextToken()) << 8 | Integer.parseInt(localStringTokenizer2.nextToken()));
        }
      }
      else
      {
        if (i != arrayOfInt.length)
        {
          System.arraycopy(arrayOfInt, j, arrayOfInt, arrayOfInt.length - (i - j), i - j);
          for (int k = j; k != arrayOfInt.length - (i - j); k++) {
            arrayOfInt[k] = 0;
          }
        }
        return arrayOfInt;
      }
      i = n;
    }
  }
  
  private int[] parseMask(String paramString)
  {
    int[] arrayOfInt = new int[8];
    int i = Integer.parseInt(paramString);
    for (int j = 0; j != i; j++)
    {
      int k = j / 16;
      arrayOfInt[k] |= 1 << 15 - j % 16;
    }
    return arrayOfInt;
  }
  
  private byte[] toGeneralNameEncoding(String paramString)
  {
    if ((IPAddress.isValidIPv6WithNetmask(paramString)) || (IPAddress.isValidIPv6(paramString)))
    {
      int i = paramString.indexOf('/');
      if (i < 0)
      {
        byte[] arrayOfByte2 = new byte[16];
        copyInts(parseIPv6(paramString), arrayOfByte2, 0);
        return arrayOfByte2;
      }
      byte[] arrayOfByte1 = new byte[32];
      copyInts(parseIPv6(paramString.substring(0, i)), arrayOfByte1, 0);
      String str1 = paramString.substring(i + 1);
      if (str1.indexOf(':') > 0) {}
      for (int[] arrayOfInt = parseIPv6(str1);; arrayOfInt = parseMask(str1))
      {
        copyInts(arrayOfInt, arrayOfByte1, 16);
        return arrayOfByte1;
      }
    }
    if ((IPAddress.isValidIPv4WithNetmask(paramString)) || (IPAddress.isValidIPv4(paramString)))
    {
      int j = paramString.indexOf('/');
      if (j < 0)
      {
        byte[] arrayOfByte4 = new byte[4];
        parseIPv4(paramString, arrayOfByte4, 0);
        return arrayOfByte4;
      }
      byte[] arrayOfByte3 = new byte[8];
      parseIPv4(paramString.substring(0, j), arrayOfByte3, 0);
      String str2 = paramString.substring(j + 1);
      if (str2.indexOf('.') > 0)
      {
        parseIPv4(str2, arrayOfByte3, 4);
        return arrayOfByte3;
      }
      parseIPv4Mask(str2, arrayOfByte3, 4);
      return arrayOfByte3;
    }
    return null;
  }
  
  public ASN1Encodable getName()
  {
    return this.obj;
  }
  
  public int getTagNo()
  {
    return this.tag;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    if (this.tag == 4) {
      return new DERTaggedObject(true, this.tag, this.obj);
    }
    return new DERTaggedObject(false, this.tag, this.obj);
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(this.tag);
    localStringBuffer.append(": ");
    switch (this.tag)
    {
    case 3: 
    case 5: 
    default: 
      localStringBuffer.append(this.obj.toString());
    }
    for (;;)
    {
      return localStringBuffer.toString();
      localStringBuffer.append(DERIA5String.getInstance(this.obj).getString());
      continue;
      localStringBuffer.append(X500Name.getInstance(this.obj).toString());
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x509.GeneralName
 * JD-Core Version:    0.7.0.1
 */