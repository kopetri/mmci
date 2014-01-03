package org.spongycastle.asn1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import org.spongycastle.util.Arrays;

public class DERObjectIdentifier
  extends ASN1Primitive
{
  private static ASN1ObjectIdentifier[][] cache = new ASN1ObjectIdentifier['ÿ'][];
  private byte[] body;
  String identifier;
  
  public DERObjectIdentifier(String paramString)
  {
    if (!isValidIdentifier(paramString)) {
      throw new IllegalArgumentException("string " + paramString + " not an OID");
    }
    this.identifier = paramString;
  }
  
  DERObjectIdentifier(byte[] paramArrayOfByte)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    long l = 0L;
    BigInteger localBigInteger = null;
    int i = 1;
    int j = 0;
    if (j != paramArrayOfByte.length)
    {
      int k = 0xFF & paramArrayOfByte[j];
      if (l < 36028797018963968L)
      {
        l = 128L * l + (k & 0x7F);
        if ((k & 0x80) == 0)
        {
          if (i != 0) {}
          switch ((int)l / 40)
          {
          default: 
            localStringBuffer.append('2');
            l -= 80L;
            label117:
            i = 0;
            localStringBuffer.append('.');
            localStringBuffer.append(l);
            l = 0L;
          }
        }
      }
      for (;;)
      {
        j++;
        break;
        localStringBuffer.append('0');
        break label117;
        localStringBuffer.append('1');
        l -= 40L;
        break label117;
        if (localBigInteger == null) {
          localBigInteger = BigInteger.valueOf(l);
        }
        localBigInteger = localBigInteger.shiftLeft(7).or(BigInteger.valueOf(k & 0x7F));
        if ((k & 0x80) == 0)
        {
          localStringBuffer.append('.');
          localStringBuffer.append(localBigInteger);
          l = 0L;
          localBigInteger = null;
        }
      }
    }
    this.identifier = localStringBuffer.toString();
  }
  
  private void doOutput(ByteArrayOutputStream paramByteArrayOutputStream)
  {
    OIDTokenizer localOIDTokenizer = new OIDTokenizer(this.identifier);
    writeField(paramByteArrayOutputStream, 40 * Integer.parseInt(localOIDTokenizer.nextToken()) + Integer.parseInt(localOIDTokenizer.nextToken()));
    while (localOIDTokenizer.hasMoreTokens())
    {
      String str = localOIDTokenizer.nextToken();
      if (str.length() < 18) {
        writeField(paramByteArrayOutputStream, Long.parseLong(str));
      } else {
        writeField(paramByteArrayOutputStream, new BigInteger(str));
      }
    }
  }
  
  static ASN1ObjectIdentifier fromOctetString(byte[] paramArrayOfByte)
  {
    ASN1ObjectIdentifier localASN1ObjectIdentifier1;
    if (paramArrayOfByte.length < 3) {
      localASN1ObjectIdentifier1 = new ASN1ObjectIdentifier(paramArrayOfByte);
    }
    do
    {
      int j;
      ASN1ObjectIdentifier[] arrayOfASN1ObjectIdentifier2;
      do
      {
        int i;
        do
        {
          return localASN1ObjectIdentifier1;
          i = 0xFF & paramArrayOfByte[(-2 + paramArrayOfByte.length)];
          ASN1ObjectIdentifier[] arrayOfASN1ObjectIdentifier1 = cache[i];
          if (arrayOfASN1ObjectIdentifier1 == null)
          {
            ASN1ObjectIdentifier[][] arrayOfASN1ObjectIdentifier4 = cache;
            arrayOfASN1ObjectIdentifier1 = new ASN1ObjectIdentifier['ÿ'];
            arrayOfASN1ObjectIdentifier4[i] = arrayOfASN1ObjectIdentifier1;
          }
          j = 0xFF & paramArrayOfByte[(-1 + paramArrayOfByte.length)];
          localASN1ObjectIdentifier1 = arrayOfASN1ObjectIdentifier1[j];
          if (localASN1ObjectIdentifier1 == null)
          {
            ASN1ObjectIdentifier localASN1ObjectIdentifier2 = new ASN1ObjectIdentifier(paramArrayOfByte);
            arrayOfASN1ObjectIdentifier1[j] = localASN1ObjectIdentifier2;
            return localASN1ObjectIdentifier2;
          }
        } while (Arrays.areEqual(paramArrayOfByte, localASN1ObjectIdentifier1.getBody()));
        int k = (i + 1) % 256;
        arrayOfASN1ObjectIdentifier2 = cache[k];
        if (arrayOfASN1ObjectIdentifier2 == null)
        {
          ASN1ObjectIdentifier[][] arrayOfASN1ObjectIdentifier3 = cache;
          arrayOfASN1ObjectIdentifier2 = new ASN1ObjectIdentifier['ÿ'];
          arrayOfASN1ObjectIdentifier3[k] = arrayOfASN1ObjectIdentifier2;
        }
        localASN1ObjectIdentifier1 = arrayOfASN1ObjectIdentifier2[j];
        if (localASN1ObjectIdentifier1 == null)
        {
          ASN1ObjectIdentifier localASN1ObjectIdentifier3 = new ASN1ObjectIdentifier(paramArrayOfByte);
          arrayOfASN1ObjectIdentifier2[j] = localASN1ObjectIdentifier3;
          return localASN1ObjectIdentifier3;
        }
      } while (Arrays.areEqual(paramArrayOfByte, localASN1ObjectIdentifier1.getBody()));
      int m = (j + 1) % 256;
      localASN1ObjectIdentifier1 = arrayOfASN1ObjectIdentifier2[m];
      if (localASN1ObjectIdentifier1 == null)
      {
        ASN1ObjectIdentifier localASN1ObjectIdentifier4 = new ASN1ObjectIdentifier(paramArrayOfByte);
        arrayOfASN1ObjectIdentifier2[m] = localASN1ObjectIdentifier4;
        return localASN1ObjectIdentifier4;
      }
    } while (Arrays.areEqual(paramArrayOfByte, localASN1ObjectIdentifier1.getBody()));
    return new ASN1ObjectIdentifier(paramArrayOfByte);
  }
  
  public static ASN1ObjectIdentifier getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof ASN1ObjectIdentifier))) {
      return (ASN1ObjectIdentifier)paramObject;
    }
    if ((paramObject instanceof DERObjectIdentifier)) {
      return new ASN1ObjectIdentifier(((DERObjectIdentifier)paramObject).getId());
    }
    throw new IllegalArgumentException("illegal object in getInstance: " + paramObject.getClass().getName());
  }
  
  public static ASN1ObjectIdentifier getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    ASN1Primitive localASN1Primitive = paramASN1TaggedObject.getObject();
    if ((paramBoolean) || ((localASN1Primitive instanceof DERObjectIdentifier))) {
      return getInstance(localASN1Primitive);
    }
    return ASN1ObjectIdentifier.fromOctetString(ASN1OctetString.getInstance(paramASN1TaggedObject.getObject()).getOctets());
  }
  
  private static boolean isValidIdentifier(String paramString)
  {
    if ((paramString.length() < 3) || (paramString.charAt(1) != '.'))
    {
      bool = false;
      return bool;
    }
    int i = paramString.charAt(0);
    if ((i < 48) || (i > 50)) {
      return false;
    }
    boolean bool = false;
    int j = -1 + paramString.length();
    label51:
    int k;
    if (j >= 2)
    {
      k = paramString.charAt(j);
      if ((48 > k) || (k > 57)) {
        break label85;
      }
    }
    for (bool = true;; bool = false)
    {
      j--;
      break label51;
      break;
      label85:
      if (k != 46) {
        break label103;
      }
      if (!bool) {
        return false;
      }
    }
    label103:
    return false;
  }
  
  private void writeField(ByteArrayOutputStream paramByteArrayOutputStream, long paramLong)
  {
    byte[] arrayOfByte = new byte[9];
    int i = 8;
    arrayOfByte[8] = ((byte)(0x7F & (int)paramLong));
    while (paramLong >= 128L)
    {
      paramLong >>= 7;
      i--;
      arrayOfByte[i] = ((byte)(0x80 | 0x7F & (int)paramLong));
    }
    paramByteArrayOutputStream.write(arrayOfByte, i, 9 - i);
  }
  
  private void writeField(ByteArrayOutputStream paramByteArrayOutputStream, BigInteger paramBigInteger)
  {
    int i = (6 + paramBigInteger.bitLength()) / 7;
    if (i == 0)
    {
      paramByteArrayOutputStream.write(0);
      return;
    }
    BigInteger localBigInteger = paramBigInteger;
    byte[] arrayOfByte = new byte[i];
    for (int j = i - 1; j >= 0; j--)
    {
      arrayOfByte[j] = ((byte)(0x80 | 0x7F & localBigInteger.intValue()));
      localBigInteger = localBigInteger.shiftRight(7);
    }
    int k = i - 1;
    arrayOfByte[k] = ((byte)(0x7F & arrayOfByte[k]));
    paramByteArrayOutputStream.write(arrayOfByte, 0, arrayOfByte.length);
  }
  
  boolean asn1Equals(ASN1Primitive paramASN1Primitive)
  {
    if (!(paramASN1Primitive instanceof DERObjectIdentifier)) {
      return false;
    }
    return this.identifier.equals(((DERObjectIdentifier)paramASN1Primitive).identifier);
  }
  
  void encode(ASN1OutputStream paramASN1OutputStream)
    throws IOException
  {
    byte[] arrayOfByte = getBody();
    paramASN1OutputStream.write(6);
    paramASN1OutputStream.writeLength(arrayOfByte.length);
    paramASN1OutputStream.write(arrayOfByte);
  }
  
  int encodedLength()
    throws IOException
  {
    int i = getBody().length;
    return i + (1 + StreamUtil.calculateBodyLength(i));
  }
  
  protected byte[] getBody()
  {
    if (this.body == null)
    {
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
      doOutput(localByteArrayOutputStream);
      this.body = localByteArrayOutputStream.toByteArray();
    }
    return this.body;
  }
  
  public String getId()
  {
    return this.identifier;
  }
  
  public int hashCode()
  {
    return this.identifier.hashCode();
  }
  
  boolean isConstructed()
  {
    return false;
  }
  
  public String toString()
  {
    return getId();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.DERObjectIdentifier
 * JD-Core Version:    0.7.0.1
 */