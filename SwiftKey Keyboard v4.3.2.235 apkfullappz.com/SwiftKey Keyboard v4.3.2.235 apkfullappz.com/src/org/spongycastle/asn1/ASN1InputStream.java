package org.spongycastle.asn1;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.spongycastle.util.io.Streams;

public class ASN1InputStream
  extends FilterInputStream
  implements BERTags
{
  private final boolean lazyEvaluate;
  private final int limit;
  private final byte[][] tmpBuffers;
  
  public ASN1InputStream(InputStream paramInputStream)
  {
    this(paramInputStream, StreamUtil.findLimit(paramInputStream));
  }
  
  public ASN1InputStream(InputStream paramInputStream, int paramInt)
  {
    this(paramInputStream, paramInt, false);
  }
  
  public ASN1InputStream(InputStream paramInputStream, int paramInt, boolean paramBoolean)
  {
    super(paramInputStream);
    this.limit = paramInt;
    this.lazyEvaluate = paramBoolean;
    this.tmpBuffers = new byte[11][];
  }
  
  public ASN1InputStream(InputStream paramInputStream, boolean paramBoolean)
  {
    this(paramInputStream, StreamUtil.findLimit(paramInputStream), paramBoolean);
  }
  
  public ASN1InputStream(byte[] paramArrayOfByte)
  {
    this(new ByteArrayInputStream(paramArrayOfByte), paramArrayOfByte.length);
  }
  
  public ASN1InputStream(byte[] paramArrayOfByte, boolean paramBoolean)
  {
    this(new ByteArrayInputStream(paramArrayOfByte), paramArrayOfByte.length, paramBoolean);
  }
  
  static ASN1Primitive createPrimitiveDERObject(int paramInt, DefiniteLengthInputStream paramDefiniteLengthInputStream, byte[][] paramArrayOfByte)
    throws IOException
  {
    switch (paramInt)
    {
    case 7: 
    case 8: 
    case 9: 
    case 11: 
    case 13: 
    case 14: 
    case 15: 
    case 16: 
    case 17: 
    case 21: 
    case 25: 
    case 29: 
    default: 
      throw new IOException("unknown tag " + paramInt + " encountered");
    case 3: 
      return DERBitString.fromInputStream(paramDefiniteLengthInputStream.getRemaining(), paramDefiniteLengthInputStream);
    case 30: 
      return new DERBMPString(getBMPCharBuffer(paramDefiniteLengthInputStream));
    case 1: 
      return ASN1Boolean.fromOctetString(getBuffer(paramDefiniteLengthInputStream, paramArrayOfByte));
    case 10: 
      return ASN1Enumerated.fromOctetString(getBuffer(paramDefiniteLengthInputStream, paramArrayOfByte));
    case 24: 
      return new ASN1GeneralizedTime(paramDefiniteLengthInputStream.toByteArray());
    case 27: 
      return new DERGeneralString(paramDefiniteLengthInputStream.toByteArray());
    case 22: 
      return new DERIA5String(paramDefiniteLengthInputStream.toByteArray());
    case 2: 
      return new ASN1Integer(paramDefiniteLengthInputStream.toByteArray());
    case 5: 
      return DERNull.INSTANCE;
    case 18: 
      return new DERNumericString(paramDefiniteLengthInputStream.toByteArray());
    case 6: 
      return ASN1ObjectIdentifier.fromOctetString(getBuffer(paramDefiniteLengthInputStream, paramArrayOfByte));
    case 4: 
      return new DEROctetString(paramDefiniteLengthInputStream.toByteArray());
    case 19: 
      return new DERPrintableString(paramDefiniteLengthInputStream.toByteArray());
    case 20: 
      return new DERT61String(paramDefiniteLengthInputStream.toByteArray());
    case 28: 
      return new DERUniversalString(paramDefiniteLengthInputStream.toByteArray());
    case 23: 
      return new ASN1UTCTime(paramDefiniteLengthInputStream.toByteArray());
    case 12: 
      return new DERUTF8String(paramDefiniteLengthInputStream.toByteArray());
    }
    return new DERVisibleString(paramDefiniteLengthInputStream.toByteArray());
  }
  
  private static char[] getBMPCharBuffer(DefiniteLengthInputStream paramDefiniteLengthInputStream)
    throws IOException
  {
    int i = paramDefiniteLengthInputStream.getRemaining() / 2;
    char[] arrayOfChar = new char[i];
    int n;
    for (int j = 0; j < i; j = n)
    {
      int k = paramDefiniteLengthInputStream.read();
      if (k < 0) {
        break;
      }
      int m = paramDefiniteLengthInputStream.read();
      if (m < 0) {
        break;
      }
      n = j + 1;
      arrayOfChar[j] = ((char)(k << 8 | m & 0xFF));
    }
    return arrayOfChar;
  }
  
  private static byte[] getBuffer(DefiniteLengthInputStream paramDefiniteLengthInputStream, byte[][] paramArrayOfByte)
    throws IOException
  {
    int i = paramDefiniteLengthInputStream.getRemaining();
    if (paramDefiniteLengthInputStream.getRemaining() < paramArrayOfByte.length)
    {
      byte[] arrayOfByte = paramArrayOfByte[i];
      if (arrayOfByte == null)
      {
        arrayOfByte = new byte[i];
        paramArrayOfByte[i] = arrayOfByte;
      }
      Streams.readFully(paramDefiniteLengthInputStream, arrayOfByte);
      return arrayOfByte;
    }
    return paramDefiniteLengthInputStream.toByteArray();
  }
  
  static int readLength(InputStream paramInputStream, int paramInt)
    throws IOException
  {
    int i = paramInputStream.read();
    if (i < 0) {
      throw new EOFException("EOF found when length expected");
    }
    if (i == 128) {
      return -1;
    }
    if (i > 127)
    {
      int j = i & 0x7F;
      if (j > 4) {
        throw new IOException("DER length more than 4 bytes: " + j);
      }
      i = 0;
      for (int k = 0; k < j; k++)
      {
        int m = paramInputStream.read();
        if (m < 0) {
          throw new EOFException("EOF found reading length");
        }
        i = m + (i << 8);
      }
      if (i < 0) {
        throw new IOException("corrupted stream - negative length found");
      }
      if (i >= paramInt) {
        throw new IOException("corrupted stream - out of bounds length found");
      }
    }
    return i;
  }
  
  static int readTagNumber(InputStream paramInputStream, int paramInt)
    throws IOException
  {
    int i = paramInt & 0x1F;
    if (i == 31)
    {
      int j = paramInputStream.read();
      int k = j & 0x7F;
      int m = 0;
      if (k == 0) {
        throw new IOException("corrupted stream - invalid high tag number found");
      }
      while ((j >= 0) && ((j & 0x80) != 0))
      {
        m = (m | j & 0x7F) << 7;
        j = paramInputStream.read();
      }
      if (j < 0) {
        throw new EOFException("EOF found inside tag value.");
      }
      i = m | j & 0x7F;
    }
    return i;
  }
  
  ASN1EncodableVector buildDEREncodableVector(DefiniteLengthInputStream paramDefiniteLengthInputStream)
    throws IOException
  {
    return new ASN1InputStream(paramDefiniteLengthInputStream).buildEncodableVector();
  }
  
  ASN1EncodableVector buildEncodableVector()
    throws IOException
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    for (;;)
    {
      ASN1Primitive localASN1Primitive = readObject();
      if (localASN1Primitive == null) {
        break;
      }
      localASN1EncodableVector.add(localASN1Primitive);
    }
    return localASN1EncodableVector;
  }
  
  protected ASN1Primitive buildObject(int paramInt1, int paramInt2, int paramInt3)
    throws IOException
  {
    if ((paramInt1 & 0x20) != 0) {}
    DefiniteLengthInputStream localDefiniteLengthInputStream;
    for (boolean bool = true;; bool = false)
    {
      localDefiniteLengthInputStream = new DefiniteLengthInputStream(this, paramInt3);
      if ((paramInt1 & 0x40) == 0) {
        break;
      }
      return new DERApplicationSpecific(bool, paramInt2, localDefiniteLengthInputStream.toByteArray());
    }
    if ((paramInt1 & 0x80) != 0) {
      return new ASN1StreamParser(localDefiniteLengthInputStream).readTaggedObject(bool, paramInt2);
    }
    if (bool)
    {
      switch (paramInt2)
      {
      default: 
        throw new IOException("unknown tag " + paramInt2 + " encountered");
      case 4: 
        ASN1EncodableVector localASN1EncodableVector = buildDEREncodableVector(localDefiniteLengthInputStream);
        ASN1OctetString[] arrayOfASN1OctetString = new ASN1OctetString[localASN1EncodableVector.size()];
        for (int i = 0; i != arrayOfASN1OctetString.length; i++) {
          arrayOfASN1OctetString[i] = ((ASN1OctetString)localASN1EncodableVector.get(i));
        }
        return new BEROctetString(arrayOfASN1OctetString);
      case 16: 
        if (this.lazyEvaluate) {
          return new LazyEncodedSequence(localDefiniteLengthInputStream.toByteArray());
        }
        return DERFactory.createSequence(buildDEREncodableVector(localDefiniteLengthInputStream));
      case 17: 
        return DERFactory.createSet(buildDEREncodableVector(localDefiniteLengthInputStream));
      }
      return new DERExternal(buildDEREncodableVector(localDefiniteLengthInputStream));
    }
    return createPrimitiveDERObject(paramInt2, localDefiniteLengthInputStream, this.tmpBuffers);
  }
  
  int getLimit()
  {
    return this.limit;
  }
  
  protected void readFully(byte[] paramArrayOfByte)
    throws IOException
  {
    if (Streams.readFully(this, paramArrayOfByte) != paramArrayOfByte.length) {
      throw new EOFException("EOF encountered in middle of object");
    }
  }
  
  protected int readLength()
    throws IOException
  {
    return readLength(this, this.limit);
  }
  
  public ASN1Primitive readObject()
    throws IOException
  {
    int i = read();
    if (i <= 0)
    {
      if (i == 0) {
        throw new IOException("unexpected end-of-contents marker");
      }
      return null;
    }
    int j = readTagNumber(this, i);
    if ((i & 0x20) != 0) {}
    int m;
    for (int k = 1;; k = 0)
    {
      m = readLength();
      if (m >= 0) {
        break label247;
      }
      if (k != 0) {
        break;
      }
      throw new IOException("indefinite length primitive encoding encountered");
    }
    ASN1StreamParser localASN1StreamParser = new ASN1StreamParser(new IndefiniteLengthInputStream(this, this.limit), this.limit);
    if ((i & 0x40) != 0) {
      return new BERApplicationSpecificParser(j, localASN1StreamParser).getLoadedObject();
    }
    if ((i & 0x80) != 0) {
      return new BERTaggedObjectParser(true, j, localASN1StreamParser).getLoadedObject();
    }
    switch (j)
    {
    default: 
      throw new IOException("unknown BER object encountered");
    case 4: 
      return new BEROctetStringParser(localASN1StreamParser).getLoadedObject();
    case 16: 
      return new BERSequenceParser(localASN1StreamParser).getLoadedObject();
    case 17: 
      return new BERSetParser(localASN1StreamParser).getLoadedObject();
    }
    return new DERExternalParser(localASN1StreamParser).getLoadedObject();
    try
    {
      label247:
      ASN1Primitive localASN1Primitive = buildObject(i, j, m);
      return localASN1Primitive;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      throw new ASN1Exception("corrupted stream detected", localIllegalArgumentException);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.ASN1InputStream
 * JD-Core Version:    0.7.0.1
 */