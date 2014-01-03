package org.spongycastle.asn1;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;

public abstract class ASN1Sequence
  extends ASN1Primitive
{
  protected Vector seq = new Vector();
  
  protected ASN1Sequence() {}
  
  protected ASN1Sequence(ASN1Encodable paramASN1Encodable)
  {
    this.seq.addElement(paramASN1Encodable);
  }
  
  protected ASN1Sequence(ASN1EncodableVector paramASN1EncodableVector)
  {
    for (int i = 0; i != paramASN1EncodableVector.size(); i++) {
      this.seq.addElement(paramASN1EncodableVector.get(i));
    }
  }
  
  protected ASN1Sequence(ASN1Encodable[] paramArrayOfASN1Encodable)
  {
    for (int i = 0; i != paramArrayOfASN1Encodable.length; i++) {
      this.seq.addElement(paramArrayOfASN1Encodable[i]);
    }
  }
  
  public static ASN1Sequence getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof ASN1Sequence))) {
      return (ASN1Sequence)paramObject;
    }
    if ((paramObject instanceof ASN1SequenceParser)) {
      return getInstance(((ASN1SequenceParser)paramObject).toASN1Primitive());
    }
    if ((paramObject instanceof byte[])) {
      try
      {
        ASN1Sequence localASN1Sequence = getInstance(fromByteArray((byte[])paramObject));
        return localASN1Sequence;
      }
      catch (IOException localIOException)
      {
        throw new IllegalArgumentException("failed to construct sequence from byte[]: " + localIOException.getMessage());
      }
    }
    if ((paramObject instanceof ASN1Encodable))
    {
      ASN1Primitive localASN1Primitive = ((ASN1Encodable)paramObject).toASN1Primitive();
      if ((localASN1Primitive instanceof ASN1Sequence)) {
        return (ASN1Sequence)localASN1Primitive;
      }
    }
    throw new IllegalArgumentException("unknown object in getInstance: " + paramObject.getClass().getName());
  }
  
  public static ASN1Sequence getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    if (paramBoolean)
    {
      if (!paramASN1TaggedObject.isExplicit()) {
        throw new IllegalArgumentException("object implicit - explicit expected.");
      }
      return getInstance(paramASN1TaggedObject.getObject().toASN1Primitive());
    }
    if (paramASN1TaggedObject.isExplicit())
    {
      if ((paramASN1TaggedObject instanceof BERTaggedObject)) {
        return new BERSequence(paramASN1TaggedObject.getObject());
      }
      return new DLSequence(paramASN1TaggedObject.getObject());
    }
    if ((paramASN1TaggedObject.getObject() instanceof ASN1Sequence)) {
      return (ASN1Sequence)paramASN1TaggedObject.getObject();
    }
    throw new IllegalArgumentException("unknown object in getInstance: " + paramASN1TaggedObject.getClass().getName());
  }
  
  private ASN1Encodable getNext(Enumeration paramEnumeration)
  {
    return (ASN1Encodable)paramEnumeration.nextElement();
  }
  
  boolean asn1Equals(ASN1Primitive paramASN1Primitive)
  {
    if (!(paramASN1Primitive instanceof ASN1Sequence)) {}
    ASN1Sequence localASN1Sequence;
    do
    {
      return false;
      localASN1Sequence = (ASN1Sequence)paramASN1Primitive;
    } while (size() != localASN1Sequence.size());
    Enumeration localEnumeration1 = getObjects();
    Enumeration localEnumeration2 = localASN1Sequence.getObjects();
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
    return (ASN1Encodable)this.seq.elementAt(paramInt);
  }
  
  public Enumeration getObjects()
  {
    return this.seq.elements();
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
  
  public ASN1SequenceParser parser()
  {
    new ASN1SequenceParser()
    {
      private int index;
      private final int max = ASN1Sequence.this.size();
      
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
          ASN1Sequence localASN1Sequence = ASN1Sequence.this;
          int i = this.index;
          this.index = (i + 1);
          localASN1Encodable = localASN1Sequence.getObjectAt(i);
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
    return this.seq.size();
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
    DERSequence localDERSequence = new DERSequence();
    localDERSequence.seq = this.seq;
    return localDERSequence;
  }
  
  ASN1Primitive toDLObject()
  {
    DLSequence localDLSequence = new DLSequence();
    localDLSequence.seq = this.seq;
    return localDLSequence;
  }
  
  public String toString()
  {
    return this.seq.toString();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.ASN1Sequence
 * JD-Core Version:    0.7.0.1
 */