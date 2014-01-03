package org.spongycastle.asn1;

import java.io.IOException;

public class DLTaggedObject
  extends ASN1TaggedObject
{
  private static final byte[] ZERO_BYTES = new byte[0];
  
  public DLTaggedObject(boolean paramBoolean, int paramInt, ASN1Encodable paramASN1Encodable)
  {
    super(paramBoolean, paramInt, paramASN1Encodable);
  }
  
  void encode(ASN1OutputStream paramASN1OutputStream)
    throws IOException
  {
    if (!this.empty)
    {
      ASN1Primitive localASN1Primitive = this.obj.toASN1Primitive().toDLObject();
      if (this.explicit)
      {
        paramASN1OutputStream.writeTag(160, this.tagNo);
        paramASN1OutputStream.writeLength(localASN1Primitive.encodedLength());
        paramASN1OutputStream.writeObject(localASN1Primitive);
        return;
      }
      if (localASN1Primitive.isConstructed()) {}
      for (int i = 160;; i = 128)
      {
        paramASN1OutputStream.writeTag(i, this.tagNo);
        paramASN1OutputStream.writeImplicitObject(localASN1Primitive);
        return;
      }
    }
    paramASN1OutputStream.writeEncoded(160, this.tagNo, ZERO_BYTES);
  }
  
  int encodedLength()
    throws IOException
  {
    if (!this.empty)
    {
      int i = this.obj.toASN1Primitive().toDLObject().encodedLength();
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
    return this.obj.toASN1Primitive().toDLObject().isConstructed();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.DLTaggedObject
 * JD-Core Version:    0.7.0.1
 */