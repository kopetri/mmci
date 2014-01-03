package org.spongycastle.asn1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;

public class BEROctetString
  extends ASN1OctetString
{
  private static final int MAX_LENGTH = 1000;
  private ASN1OctetString[] octs;
  
  public BEROctetString(byte[] paramArrayOfByte)
  {
    super(paramArrayOfByte);
  }
  
  public BEROctetString(ASN1OctetString[] paramArrayOfASN1OctetString)
  {
    super(toBytes(paramArrayOfASN1OctetString));
    this.octs = paramArrayOfASN1OctetString;
  }
  
  static BEROctetString fromSequence(ASN1Sequence paramASN1Sequence)
  {
    ASN1OctetString[] arrayOfASN1OctetString = new ASN1OctetString[paramASN1Sequence.size()];
    Enumeration localEnumeration = paramASN1Sequence.getObjects();
    int j;
    for (int i = 0; localEnumeration.hasMoreElements(); i = j)
    {
      j = i + 1;
      arrayOfASN1OctetString[i] = ((ASN1OctetString)localEnumeration.nextElement());
    }
    return new BEROctetString(arrayOfASN1OctetString);
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
  
  private static byte[] toBytes(ASN1OctetString[] paramArrayOfASN1OctetString)
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    int i = 0;
    while (i != paramArrayOfASN1OctetString.length) {
      try
      {
        localByteArrayOutputStream.write(((DEROctetString)paramArrayOfASN1OctetString[i]).getOctets());
        i++;
      }
      catch (ClassCastException localClassCastException)
      {
        throw new IllegalArgumentException(paramArrayOfASN1OctetString[i].getClass().getName() + " found in input should only contain DEROctetString");
      }
      catch (IOException localIOException)
      {
        throw new IllegalArgumentException("exception converting octets " + localIOException.toString());
      }
    }
    return localByteArrayOutputStream.toByteArray();
  }
  
  public void encode(ASN1OutputStream paramASN1OutputStream)
    throws IOException
  {
    paramASN1OutputStream.write(36);
    paramASN1OutputStream.write(128);
    Enumeration localEnumeration = getObjects();
    while (localEnumeration.hasMoreElements()) {
      paramASN1OutputStream.writeObject((ASN1Encodable)localEnumeration.nextElement());
    }
    paramASN1OutputStream.write(0);
    paramASN1OutputStream.write(0);
  }
  
  int encodedLength()
    throws IOException
  {
    int i = 0;
    Enumeration localEnumeration = getObjects();
    while (localEnumeration.hasMoreElements()) {
      i += ((ASN1Encodable)localEnumeration.nextElement()).toASN1Primitive().encodedLength();
    }
    return 2 + (i + 2);
  }
  
  public Enumeration getObjects()
  {
    if (this.octs == null) {
      return generateOcts().elements();
    }
    new Enumeration()
    {
      int counter = 0;
      
      public boolean hasMoreElements()
      {
        return this.counter < BEROctetString.this.octs.length;
      }
      
      public Object nextElement()
      {
        ASN1OctetString[] arrayOfASN1OctetString = BEROctetString.this.octs;
        int i = this.counter;
        this.counter = (i + 1);
        return arrayOfASN1OctetString[i];
      }
    };
  }
  
  public byte[] getOctets()
  {
    return this.string;
  }
  
  boolean isConstructed()
  {
    return true;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.BEROctetString
 * JD-Core Version:    0.7.0.1
 */