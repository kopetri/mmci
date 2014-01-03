package org.spongycastle.asn1;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;

class LazyEncodedSequence
  extends ASN1Sequence
{
  private byte[] encoded;
  
  LazyEncodedSequence(byte[] paramArrayOfByte)
    throws IOException
  {
    this.encoded = paramArrayOfByte;
  }
  
  private void parse()
  {
    LazyConstructionEnumeration localLazyConstructionEnumeration = new LazyConstructionEnumeration(this.encoded);
    while (localLazyConstructionEnumeration.hasMoreElements()) {
      this.seq.addElement(localLazyConstructionEnumeration.nextElement());
    }
    this.encoded = null;
  }
  
  void encode(ASN1OutputStream paramASN1OutputStream)
    throws IOException
  {
    if (this.encoded != null)
    {
      paramASN1OutputStream.writeEncoded(48, this.encoded);
      return;
    }
    super.toDLObject().encode(paramASN1OutputStream);
  }
  
  int encodedLength()
    throws IOException
  {
    if (this.encoded != null) {
      return 1 + StreamUtil.calculateBodyLength(this.encoded.length) + this.encoded.length;
    }
    return super.toDLObject().encodedLength();
  }
  
  public ASN1Encodable getObjectAt(int paramInt)
  {
    try
    {
      if (this.encoded != null) {
        parse();
      }
      ASN1Encodable localASN1Encodable = super.getObjectAt(paramInt);
      return localASN1Encodable;
    }
    finally {}
  }
  
  /* Error */
  public Enumeration getObjects()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 15	org/spongycastle/asn1/LazyEncodedSequence:encoded	[B
    //   6: ifnonnull +14 -> 20
    //   9: aload_0
    //   10: invokespecial 76	org/spongycastle/asn1/ASN1Sequence:getObjects	()Ljava/util/Enumeration;
    //   13: astore_3
    //   14: aload_3
    //   15: astore_2
    //   16: aload_0
    //   17: monitorexit
    //   18: aload_2
    //   19: areturn
    //   20: new 18	org/spongycastle/asn1/LazyConstructionEnumeration
    //   23: dup
    //   24: aload_0
    //   25: getfield 15	org/spongycastle/asn1/LazyEncodedSequence:encoded	[B
    //   28: invokespecial 20	org/spongycastle/asn1/LazyConstructionEnumeration:<init>	([B)V
    //   31: astore_2
    //   32: goto -16 -> 16
    //   35: astore_1
    //   36: aload_0
    //   37: monitorexit
    //   38: aload_1
    //   39: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	40	0	this	LazyEncodedSequence
    //   35	4	1	localObject1	Object
    //   15	17	2	localObject2	Object
    //   13	2	3	localEnumeration	Enumeration
    // Exception table:
    //   from	to	target	type
    //   2	14	35	finally
    //   20	32	35	finally
  }
  
  public int size()
  {
    try
    {
      if (this.encoded != null) {
        parse();
      }
      int i = super.size();
      return i;
    }
    finally {}
  }
  
  ASN1Primitive toDERObject()
  {
    if (this.encoded != null) {
      parse();
    }
    return super.toDERObject();
  }
  
  ASN1Primitive toDLObject()
  {
    if (this.encoded != null) {
      parse();
    }
    return super.toDLObject();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.LazyEncodedSequence
 * JD-Core Version:    0.7.0.1
 */