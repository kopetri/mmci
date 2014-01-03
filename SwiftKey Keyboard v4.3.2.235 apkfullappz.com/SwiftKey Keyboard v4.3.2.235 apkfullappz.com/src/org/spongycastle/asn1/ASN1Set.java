package org.spongycastle.asn1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;

public abstract class ASN1Set
  extends ASN1Primitive
{
  private boolean isSorted = false;
  private Vector set = new Vector();
  
  protected ASN1Set() {}
  
  protected ASN1Set(ASN1Encodable paramASN1Encodable)
  {
    this.set.addElement(paramASN1Encodable);
  }
  
  protected ASN1Set(ASN1EncodableVector paramASN1EncodableVector, boolean paramBoolean)
  {
    for (int i = 0; i != paramASN1EncodableVector.size(); i++) {
      this.set.addElement(paramASN1EncodableVector.get(i));
    }
    if (paramBoolean) {
      sort();
    }
  }
  
  protected ASN1Set(ASN1Encodable[] paramArrayOfASN1Encodable, boolean paramBoolean)
  {
    for (int i = 0; i != paramArrayOfASN1Encodable.length; i++) {
      this.set.addElement(paramArrayOfASN1Encodable[i]);
    }
    if (paramBoolean) {
      sort();
    }
  }
  
  private byte[] getEncoded(ASN1Encodable paramASN1Encodable)
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    ASN1OutputStream localASN1OutputStream = new ASN1OutputStream(localByteArrayOutputStream);
    try
    {
      localASN1OutputStream.writeObject(paramASN1Encodable);
      return localByteArrayOutputStream.toByteArray();
    }
    catch (IOException localIOException)
    {
      throw new IllegalArgumentException("cannot encode object added to SET");
    }
  }
  
  public static ASN1Set getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof ASN1Set))) {
      return (ASN1Set)paramObject;
    }
    if ((paramObject instanceof ASN1SetParser)) {
      return getInstance(((ASN1SetParser)paramObject).toASN1Primitive());
    }
    if ((paramObject instanceof byte[])) {
      try
      {
        ASN1Set localASN1Set = getInstance(ASN1Primitive.fromByteArray((byte[])paramObject));
        return localASN1Set;
      }
      catch (IOException localIOException)
      {
        throw new IllegalArgumentException("failed to construct set from byte[]: " + localIOException.getMessage());
      }
    }
    if ((paramObject instanceof ASN1Encodable))
    {
      ASN1Primitive localASN1Primitive = ((ASN1Encodable)paramObject).toASN1Primitive();
      if ((localASN1Primitive instanceof ASN1Set)) {
        return (ASN1Set)localASN1Primitive;
      }
    }
    throw new IllegalArgumentException("unknown object in getInstance: " + paramObject.getClass().getName());
  }
  
  public static ASN1Set getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    if (paramBoolean)
    {
      if (!paramASN1TaggedObject.isExplicit()) {
        throw new IllegalArgumentException("object implicit - explicit expected.");
      }
      return (ASN1Set)paramASN1TaggedObject.getObject();
    }
    if (paramASN1TaggedObject.isExplicit())
    {
      if ((paramASN1TaggedObject instanceof BERTaggedObject)) {
        return new BERSet(paramASN1TaggedObject.getObject());
      }
      return new DLSet(paramASN1TaggedObject.getObject());
    }
    if ((paramASN1TaggedObject.getObject() instanceof ASN1Set)) {
      return (ASN1Set)paramASN1TaggedObject.getObject();
    }
    new ASN1EncodableVector();
    if ((paramASN1TaggedObject.getObject() instanceof ASN1Sequence))
    {
      ASN1Sequence localASN1Sequence = (ASN1Sequence)paramASN1TaggedObject.getObject();
      if ((paramASN1TaggedObject instanceof BERTaggedObject)) {
        return new BERSet(localASN1Sequence.toArray());
      }
      return new DLSet(localASN1Sequence.toArray());
    }
    throw new IllegalArgumentException("unknown object in getInstance: " + paramASN1TaggedObject.getClass().getName());
  }
  
  private ASN1Encodable getNext(Enumeration paramEnumeration)
  {
    Object localObject = (ASN1Encodable)paramEnumeration.nextElement();
    if (localObject == null) {
      localObject = DERNull.INSTANCE;
    }
    return localObject;
  }
  
  private boolean lessThanOrEqual(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    int i = Math.min(paramArrayOfByte1.length, paramArrayOfByte2.length);
    int j = 0;
    if (j != i) {
      if (paramArrayOfByte1[j] != paramArrayOfByte2[j]) {
        if ((0xFF & paramArrayOfByte1[j]) >= (0xFF & paramArrayOfByte2[j])) {}
      }
    }
    while (i == paramArrayOfByte1.length)
    {
      return true;
      return false;
      j++;
      break;
    }
    return false;
  }
  
  boolean asn1Equals(ASN1Primitive paramASN1Primitive)
  {
    if (!(paramASN1Primitive instanceof ASN1Set)) {}
    ASN1Set localASN1Set;
    do
    {
      return false;
      localASN1Set = (ASN1Set)paramASN1Primitive;
    } while (size() != localASN1Set.size());
    Enumeration localEnumeration1 = getObjects();
    Enumeration localEnumeration2 = localASN1Set.getObjects();
    while (localEnumeration1.hasMoreElements())
    {
      ASN1Encodable localASN1Encodable1 = getNext(localEnumeration1);
      ASN1Encodable localASN1Encodable2 = getNext(localEnumeration2);
      ASN1Primitive localASN1Primitive1 = localASN1Encodable1.toASN1Primitive();
      ASN1Primitive localASN1Primitive2 = localASN1Encodable2.toASN1Primitive();
      if ((localASN1Primitive1 != localASN1Primitive2) && (!localASN1Primitive1.equals(localASN1Primitive2))) {
        return false;
      }
    }
    return true;
  }
  
  abstract void encode(ASN1OutputStream paramASN1OutputStream)
    throws IOException;
  
  public ASN1Encodable getObjectAt(int paramInt)
  {
    return (ASN1Encodable)this.set.elementAt(paramInt);
  }
  
  public Enumeration getObjects()
  {
    return this.set.elements();
  }
  
  public int hashCode()
  {
    Enumeration localEnumeration = getObjects();
    ASN1Encodable localASN1Encodable;
    for (int i = size(); localEnumeration.hasMoreElements(); i = i * 17 ^ localASN1Encodable.hashCode()) {
      localASN1Encodable = getNext(localEnumeration);
    }
    return i;
  }
  
  boolean isConstructed()
  {
    return true;
  }
  
  public ASN1SetParser parser()
  {
    new ASN1SetParser()
    {
      private int index;
      private final int max = ASN1Set.this.size();
      
      public ASN1Primitive getLoadedObject()
      {
        return jdField_this;
      }
      
      public ASN1Encodable readObject()
        throws IOException
      {
        ASN1Encodable localASN1Encodable;
        if (this.index == this.max) {
          localASN1Encodable = null;
        }
        do
        {
          return localASN1Encodable;
          ASN1Set localASN1Set = ASN1Set.this;
          int i = this.index;
          this.index = (i + 1);
          localASN1Encodable = localASN1Set.getObjectAt(i);
          if ((localASN1Encodable instanceof ASN1Sequence)) {
            return ((ASN1Sequence)localASN1Encodable).parser();
          }
        } while (!(localASN1Encodable instanceof ASN1Set));
        return ((ASN1Set)localASN1Encodable).parser();
      }
      
      public ASN1Primitive toASN1Primitive()
      {
        return jdField_this;
      }
    };
  }
  
  public int size()
  {
    return this.set.size();
  }
  
  protected void sort()
  {
    if (!this.isSorted)
    {
      this.isSorted = true;
      if (this.set.size() > 1)
      {
        int i = 1;
        int m;
        for (int j = -1 + this.set.size(); i != 0; j = m)
        {
          int k = 0;
          m = 0;
          Object localObject1 = getEncoded((ASN1Encodable)this.set.elementAt(0));
          i = 0;
          if (k != j)
          {
            byte[] arrayOfByte = getEncoded((ASN1Encodable)this.set.elementAt(k + 1));
            if (lessThanOrEqual((byte[])localObject1, arrayOfByte)) {
              localObject1 = arrayOfByte;
            }
            for (;;)
            {
              k++;
              break;
              Object localObject2 = this.set.elementAt(k);
              this.set.setElementAt(this.set.elementAt(k + 1), k);
              this.set.setElementAt(localObject2, k + 1);
              i = 1;
              m = k;
            }
          }
        }
      }
    }
  }
  
  public ASN1Encodable[] toArray()
  {
    ASN1Encodable[] arrayOfASN1Encodable = new ASN1Encodable[size()];
    for (int i = 0; i != size(); i++) {
      arrayOfASN1Encodable[i] = getObjectAt(i);
    }
    return arrayOfASN1Encodable;
  }
  
  ASN1Primitive toDERObject()
  {
    if (this.isSorted)
    {
      DERSet localDERSet1 = new DERSet();
      localDERSet1.set = this.set;
      return localDERSet1;
    }
    Vector localVector = new Vector();
    for (int i = 0; i != this.set.size(); i++) {
      localVector.addElement(this.set.elementAt(i));
    }
    DERSet localDERSet2 = new DERSet();
    localDERSet2.set = localVector;
    localDERSet2.sort();
    return localDERSet2;
  }
  
  ASN1Primitive toDLObject()
  {
    DLSet localDLSet = new DLSet();
    localDLSet.set = this.set;
    return localDLSet;
  }
  
  public String toString()
  {
    return this.set.toString();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.ASN1Set
 * JD-Core Version:    0.7.0.1
 */