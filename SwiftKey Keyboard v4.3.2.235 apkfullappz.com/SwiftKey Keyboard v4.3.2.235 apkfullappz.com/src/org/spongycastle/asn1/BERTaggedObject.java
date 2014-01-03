package org.spongycastle.asn1;

import java.io.IOException;
import java.util.Enumeration;

public class BERTaggedObject
  extends ASN1TaggedObject
{
  public BERTaggedObject(int paramInt)
  {
    super(false, paramInt, new BERSequence());
  }
  
  public BERTaggedObject(int paramInt, ASN1Encodable paramASN1Encodable)
  {
    super(true, paramInt, paramASN1Encodable);
  }
  
  public BERTaggedObject(boolean paramBoolean, int paramInt, ASN1Encodable paramASN1Encodable)
  {
    super(paramBoolean, paramInt, paramASN1Encodable);
  }
  
  void encode(ASN1OutputStream paramASN1OutputStream)
    throws IOException
  {
    paramASN1OutputStream.writeTag(160, this.tagNo);
    paramASN1OutputStream.write(128);
    if (!this.empty)
    {
      if (!this.explicit)
      {
        Enumeration localEnumeration;
        if ((this.obj instanceof ASN1OctetString)) {
          if ((this.obj instanceof BEROctetString)) {
            localEnumeration = ((BEROctetString)this.obj).getObjects();
          }
        }
        for (;;)
        {
          if (!localEnumeration.hasMoreElements()) {
            break label201;
          }
          paramASN1OutputStream.writeObject((ASN1Encodable)localEnumeration.nextElement());
          continue;
          localEnumeration = new BEROctetString(((ASN1OctetString)this.obj).getOctets()).getObjects();
          continue;
          if ((this.obj instanceof ASN1Sequence))
          {
            localEnumeration = ((ASN1Sequence)this.obj).getObjects();
          }
          else
          {
            if (!(this.obj instanceof ASN1Set)) {
              break;
            }
            localEnumeration = ((ASN1Set)this.obj).getObjects();
          }
        }
        throw new RuntimeException("not implemented: " + this.obj.getClass().getName());
      }
      paramASN1OutputStream.writeObject(this.obj);
    }
    label201:
    paramASN1OutputStream.write(0);
    paramASN1OutputStream.write(0);
  }
  
  int encodedLength()
    throws IOException
  {
    if (!this.empty)
    {
      int i = this.obj.toASN1Primitive().encodedLength();
      if (this.explicit) {
        return i + (StreamUtil.calculateTagLength(this.tagNo) + StreamUtil.calculateBodyLength(i));
      }
      return i - 1 + StreamUtil.calculateTagLength(this.tagNo);
    }
    return 1 + StreamUtil.calculateTagLength(this.tagNo);
  }
  
  boolean isConstructed()
  {
    if ((this.empty) || (this.explicit)) {
      return true;
    }
    return this.obj.toASN1Primitive().toDERObject().isConstructed();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.BERTaggedObject
 * JD-Core Version:    0.7.0.1
 */