package org.spongycastle.asn1;

import java.io.IOException;

public abstract class ASN1TaggedObject
  extends ASN1Primitive
  implements ASN1TaggedObjectParser
{
  boolean empty = false;
  boolean explicit = true;
  ASN1Encodable obj = null;
  int tagNo;
  
  public ASN1TaggedObject(boolean paramBoolean, int paramInt, ASN1Encodable paramASN1Encodable)
  {
    if ((paramASN1Encodable instanceof ASN1Choice)) {}
    for (this.explicit = true;; this.explicit = paramBoolean)
    {
      this.tagNo = paramInt;
      if (!this.explicit) {
        break;
      }
      this.obj = paramASN1Encodable;
      return;
    }
    paramASN1Encodable.toASN1Primitive();
    this.obj = paramASN1Encodable;
  }
  
  public static ASN1TaggedObject getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof ASN1TaggedObject))) {
      return (ASN1TaggedObject)paramObject;
    }
    if ((paramObject instanceof byte[])) {
      try
      {
        ASN1TaggedObject localASN1TaggedObject = getInstance(fromByteArray((byte[])paramObject));
        return localASN1TaggedObject;
      }
      catch (IOException localIOException)
      {
        throw new IllegalArgumentException("failed to construct tagged object from byte[]: " + localIOException.getMessage());
      }
    }
    throw new IllegalArgumentException("unknown object in getInstance: " + paramObject.getClass().getName());
  }
  
  public static ASN1TaggedObject getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    if (paramBoolean) {
      return (ASN1TaggedObject)paramASN1TaggedObject.getObject();
    }
    throw new IllegalArgumentException("implicitly tagged tagged object");
  }
  
  boolean asn1Equals(ASN1Primitive paramASN1Primitive)
  {
    if (!(paramASN1Primitive instanceof ASN1TaggedObject)) {}
    ASN1TaggedObject localASN1TaggedObject;
    do
    {
      do
      {
        return false;
        localASN1TaggedObject = (ASN1TaggedObject)paramASN1Primitive;
      } while ((this.tagNo != localASN1TaggedObject.tagNo) || (this.empty != localASN1TaggedObject.empty) || (this.explicit != localASN1TaggedObject.explicit));
      if (this.obj != null) {
        break;
      }
    } while (localASN1TaggedObject.obj != null);
    while (this.obj.toASN1Primitive().equals(localASN1TaggedObject.obj.toASN1Primitive())) {
      return true;
    }
    return false;
  }
  
  abstract void encode(ASN1OutputStream paramASN1OutputStream)
    throws IOException;
  
  public ASN1Primitive getLoadedObject()
  {
    return toASN1Primitive();
  }
  
  public ASN1Primitive getObject()
  {
    if (this.obj != null) {
      return this.obj.toASN1Primitive();
    }
    return null;
  }
  
  public ASN1Encodable getObjectParser(int paramInt, boolean paramBoolean)
  {
    switch (paramInt)
    {
    default: 
      if (paramBoolean) {
        return getObject();
      }
      break;
    case 17: 
      return ASN1Set.getInstance(this, paramBoolean).parser();
    case 16: 
      return ASN1Sequence.getInstance(this, paramBoolean).parser();
    case 4: 
      return ASN1OctetString.getInstance(this, paramBoolean).parser();
    }
    throw new RuntimeException("implicit tagging not implemented for tag: " + paramInt);
  }
  
  public int getTagNo()
  {
    return this.tagNo;
  }
  
  public int hashCode()
  {
    int i = this.tagNo;
    if (this.obj != null) {
      i ^= this.obj.hashCode();
    }
    return i;
  }
  
  public boolean isEmpty()
  {
    return this.empty;
  }
  
  public boolean isExplicit()
  {
    return this.explicit;
  }
  
  ASN1Primitive toDERObject()
  {
    return new DERTaggedObject(this.explicit, this.tagNo, this.obj);
  }
  
  ASN1Primitive toDLObject()
  {
    return new DLTaggedObject(this.explicit, this.tagNo, this.obj);
  }
  
  public String toString()
  {
    return "[" + this.tagNo + "]" + this.obj;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.ASN1TaggedObject
 * JD-Core Version:    0.7.0.1
 */