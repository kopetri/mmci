package org.spongycastle.asn1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;

public class BERConstructedOctetString
  extends BEROctetString
{
  private static final int MAX_LENGTH = 1000;
  private Vector octs;
  
  public BERConstructedOctetString(Vector paramVector)
  {
    super(toBytes(paramVector));
    this.octs = paramVector;
  }
  
  public BERConstructedOctetString(ASN1Encodable paramASN1Encodable)
  {
    this(paramASN1Encodable.toASN1Primitive());
  }
  
  public BERConstructedOctetString(ASN1Primitive paramASN1Primitive)
  {
    super(toByteArray(paramASN1Primitive));
  }
  
  public BERConstructedOctetString(byte[] paramArrayOfByte)
  {
    super(paramArrayOfByte);
  }
  
  public static BEROctetString fromSequence(ASN1Sequence paramASN1Sequence)
  {
    Vector localVector = new Vector();
    Enumeration localEnumeration = paramASN1Sequence.getObjects();
    while (localEnumeration.hasMoreElements()) {
      localVector.addElement(localEnumeration.nextElement());
    }
    return new BERConstructedOctetString(localVector);
  }
  
  private Vector generateOcts()
  {
    Vector localVector = new Vector();
    int i = 0;
    if (i < this.string.length)
    {
      if (i + 1000 > this.string.length) {}
      for (int j = this.string.length;; j = i + 1000)
      {
        byte[] arrayOfByte = new byte[j - i];
        System.arraycopy(this.string, i, arrayOfByte, 0, arrayOfByte.length);
        localVector.addElement(new DEROctetString(arrayOfByte));
        i += 1000;
        break;
      }
    }
    return localVector;
  }
  
  private static byte[] toByteArray(ASN1Primitive paramASN1Primitive)
  {
    try
    {
      byte[] arrayOfByte = paramASN1Primitive.getEncoded();
      return arrayOfByte;
    }
    catch (IOException localIOException)
    {
      throw new IllegalArgumentException("Unable to encode object");
    }
  }
  
  private static byte[] toBytes(Vector paramVector)
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    int i = 0;
    while (i != paramVector.size()) {
      try
      {
        localByteArrayOutputStream.write(((DEROctetString)paramVector.elementAt(i)).getOctets());
        i++;
      }
      catch (ClassCastException localClassCastException)
      {
        throw new IllegalArgumentException(paramVector.elementAt(i).getClass().getName() + " found in input should only contain DEROctetString");
      }
      catch (IOException localIOException)
      {
        throw new IllegalArgumentException("exception converting octets " + localIOException.toString());
      }
    }
    return localByteArrayOutputStream.toByteArray();
  }
  
  public Enumeration getObjects()
  {
    if (this.octs == null) {
      return generateOcts().elements();
    }
    return this.octs.elements();
  }
  
  public byte[] getOctets()
  {
    return this.string;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.BERConstructedOctetString
 * JD-Core Version:    0.7.0.1
 */