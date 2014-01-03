package org.spongycastle.asn1;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ASN1StreamParser
{
  private final InputStream _in;
  private final int _limit;
  private final byte[][] tmpBuffers;
  
  public ASN1StreamParser(InputStream paramInputStream)
  {
    this(paramInputStream, StreamUtil.findLimit(paramInputStream));
  }
  
  public ASN1StreamParser(InputStream paramInputStream, int paramInt)
  {
    this._in = paramInputStream;
    this._limit = paramInt;
    this.tmpBuffers = new byte[11][];
  }
  
  public ASN1StreamParser(byte[] paramArrayOfByte)
  {
    this(new ByteArrayInputStream(paramArrayOfByte), paramArrayOfByte.length);
  }
  
  private void set00Check(boolean paramBoolean)
  {
    if ((this._in instanceof IndefiniteLengthInputStream)) {
      ((IndefiniteLengthInputStream)this._in).setEofOn00(paramBoolean);
    }
  }
  
  ASN1Encodable readImplicit(boolean paramBoolean, int paramInt)
    throws IOException
  {
    if ((this._in instanceof IndefiniteLengthInputStream))
    {
      if (!paramBoolean) {
        throw new IOException("indefinite length primitive encoding encountered");
      }
      return readIndef(paramInt);
    }
    if (paramBoolean) {
      switch (paramInt)
      {
      }
    }
    for (;;)
    {
      throw new RuntimeException("implicit tagging not implemented");
      return new DERSetParser(this);
      return new DERSequenceParser(this);
      return new BEROctetStringParser(this);
      switch (paramInt)
      {
      }
    }
    return new DEROctetStringParser((DefiniteLengthInputStream)this._in);
    throw new ASN1Exception("sequences must use constructed encoding (see X.690 8.9.1/8.10.1)");
    throw new ASN1Exception("sets must use constructed encoding (see X.690 8.11.1/8.12.1)");
  }
  
  ASN1Encodable readIndef(int paramInt)
    throws IOException
  {
    switch (paramInt)
    {
    default: 
      throw new ASN1Exception("unknown BER object encountered: 0x" + Integer.toHexString(paramInt));
    case 8: 
      return new DERExternalParser(this);
    case 4: 
      return new BEROctetStringParser(this);
    case 16: 
      return new BERSequenceParser(this);
    }
    return new BERSetParser(this);
  }
  
  public ASN1Encodable readObject()
    throws IOException
  {
    int i = this._in.read();
    if (i == -1) {
      return null;
    }
    set00Check(false);
    int j = ASN1InputStream.readTagNumber(this._in, i);
    int k = i & 0x20;
    boolean bool = false;
    if (k != 0) {
      bool = true;
    }
    int m = ASN1InputStream.readLength(this._in, this._limit);
    if (m < 0)
    {
      if (!bool) {
        throw new IOException("indefinite length primitive encoding encountered");
      }
      ASN1StreamParser localASN1StreamParser = new ASN1StreamParser(new IndefiniteLengthInputStream(this._in, this._limit), this._limit);
      if ((i & 0x40) != 0) {
        return new BERApplicationSpecificParser(j, localASN1StreamParser);
      }
      if ((i & 0x80) != 0) {
        return new BERTaggedObjectParser(true, j, localASN1StreamParser);
      }
      return localASN1StreamParser.readIndef(j);
    }
    DefiniteLengthInputStream localDefiniteLengthInputStream = new DefiniteLengthInputStream(this._in, m);
    if ((i & 0x40) != 0) {
      return new DERApplicationSpecific(bool, j, localDefiniteLengthInputStream.toByteArray());
    }
    if ((i & 0x80) != 0) {
      return new BERTaggedObjectParser(bool, j, new ASN1StreamParser(localDefiniteLengthInputStream));
    }
    if (bool)
    {
      switch (j)
      {
      default: 
        throw new IOException("unknown tag " + j + " encountered");
      case 4: 
        return new BEROctetStringParser(new ASN1StreamParser(localDefiniteLengthInputStream));
      case 16: 
        return new DERSequenceParser(new ASN1StreamParser(localDefiniteLengthInputStream));
      case 17: 
        return new DERSetParser(new ASN1StreamParser(localDefiniteLengthInputStream));
      }
      return new DERExternalParser(new ASN1StreamParser(localDefiniteLengthInputStream));
    }
    switch (j)
    {
    }
    try
    {
      ASN1Primitive localASN1Primitive = ASN1InputStream.createPrimitiveDERObject(j, localDefiniteLengthInputStream, this.tmpBuffers);
      return localASN1Primitive;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      throw new ASN1Exception("corrupted stream detected", localIllegalArgumentException);
    }
    return new DEROctetStringParser(localDefiniteLengthInputStream);
  }
  
  ASN1Primitive readTaggedObject(boolean paramBoolean, int paramInt)
    throws IOException
  {
    if (!paramBoolean) {
      return new DERTaggedObject(false, paramInt, new DEROctetString(((DefiniteLengthInputStream)this._in).toByteArray()));
    }
    ASN1EncodableVector localASN1EncodableVector = readVector();
    if ((this._in instanceof IndefiniteLengthInputStream))
    {
      if (localASN1EncodableVector.size() == 1) {
        return new BERTaggedObject(true, paramInt, localASN1EncodableVector.get(0));
      }
      return new BERTaggedObject(false, paramInt, BERFactory.createSequence(localASN1EncodableVector));
    }
    if (localASN1EncodableVector.size() == 1) {
      return new DERTaggedObject(true, paramInt, localASN1EncodableVector.get(0));
    }
    return new DERTaggedObject(false, paramInt, DERFactory.createSequence(localASN1EncodableVector));
  }
  
  ASN1EncodableVector readVector()
    throws IOException
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    for (;;)
    {
      ASN1Encodable localASN1Encodable = readObject();
      if (localASN1Encodable == null) {
        break;
      }
      if ((localASN1Encodable instanceof InMemoryRepresentable)) {
        localASN1EncodableVector.add(((InMemoryRepresentable)localASN1Encodable).getLoadedObject());
      } else {
        localASN1EncodableVector.add(localASN1Encodable.toASN1Primitive());
      }
    }
    return localASN1EncodableVector;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.ASN1StreamParser
 * JD-Core Version:    0.7.0.1
 */