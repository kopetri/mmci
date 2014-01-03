package org.spongycastle.asn1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class DERExternal
  extends ASN1Primitive
{
  private ASN1Primitive dataValueDescriptor;
  private ASN1ObjectIdentifier directReference;
  private int encoding;
  private ASN1Primitive externalContent;
  private ASN1Integer indirectReference;
  
  public DERExternal(ASN1EncodableVector paramASN1EncodableVector)
  {
    ASN1Primitive localASN1Primitive = getObjFromVector(paramASN1EncodableVector, 0);
    boolean bool = localASN1Primitive instanceof ASN1ObjectIdentifier;
    int i = 0;
    if (bool)
    {
      this.directReference = ((ASN1ObjectIdentifier)localASN1Primitive);
      i = 0 + 1;
      localASN1Primitive = getObjFromVector(paramASN1EncodableVector, 1);
    }
    if ((localASN1Primitive instanceof ASN1Integer))
    {
      this.indirectReference = ((ASN1Integer)localASN1Primitive);
      i++;
      localASN1Primitive = getObjFromVector(paramASN1EncodableVector, i);
    }
    if (!(localASN1Primitive instanceof DERTaggedObject))
    {
      this.dataValueDescriptor = localASN1Primitive;
      i++;
      localASN1Primitive = getObjFromVector(paramASN1EncodableVector, i);
    }
    if (paramASN1EncodableVector.size() != i + 1) {
      throw new IllegalArgumentException("input vector too large");
    }
    if (!(localASN1Primitive instanceof DERTaggedObject)) {
      throw new IllegalArgumentException("No tagged object found in vector. Structure doesn't seem to be of type External");
    }
    DERTaggedObject localDERTaggedObject = (DERTaggedObject)localASN1Primitive;
    setEncoding(localDERTaggedObject.getTagNo());
    this.externalContent = localDERTaggedObject.getObject();
  }
  
  public DERExternal(ASN1ObjectIdentifier paramASN1ObjectIdentifier, ASN1Integer paramASN1Integer, ASN1Primitive paramASN1Primitive1, int paramInt, ASN1Primitive paramASN1Primitive2)
  {
    setDirectReference(paramASN1ObjectIdentifier);
    setIndirectReference(paramASN1Integer);
    setDataValueDescriptor(paramASN1Primitive1);
    setEncoding(paramInt);
    setExternalContent(paramASN1Primitive2.toASN1Primitive());
  }
  
  public DERExternal(ASN1ObjectIdentifier paramASN1ObjectIdentifier, ASN1Integer paramASN1Integer, ASN1Primitive paramASN1Primitive, DERTaggedObject paramDERTaggedObject)
  {
    this(paramASN1ObjectIdentifier, paramASN1Integer, paramASN1Primitive, paramDERTaggedObject.getTagNo(), paramDERTaggedObject.toASN1Primitive());
  }
  
  private ASN1Primitive getObjFromVector(ASN1EncodableVector paramASN1EncodableVector, int paramInt)
  {
    if (paramASN1EncodableVector.size() <= paramInt) {
      throw new IllegalArgumentException("too few objects in input vector");
    }
    return paramASN1EncodableVector.get(paramInt).toASN1Primitive();
  }
  
  private void setDataValueDescriptor(ASN1Primitive paramASN1Primitive)
  {
    this.dataValueDescriptor = paramASN1Primitive;
  }
  
  private void setDirectReference(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
  {
    this.directReference = paramASN1ObjectIdentifier;
  }
  
  private void setEncoding(int paramInt)
  {
    if ((paramInt < 0) || (paramInt > 2)) {
      throw new IllegalArgumentException("invalid encoding value: " + paramInt);
    }
    this.encoding = paramInt;
  }
  
  private void setExternalContent(ASN1Primitive paramASN1Primitive)
  {
    this.externalContent = paramASN1Primitive;
  }
  
  private void setIndirectReference(ASN1Integer paramASN1Integer)
  {
    this.indirectReference = paramASN1Integer;
  }
  
  boolean asn1Equals(ASN1Primitive paramASN1Primitive)
  {
    if (!(paramASN1Primitive instanceof DERExternal)) {}
    DERExternal localDERExternal;
    do
    {
      return false;
      if (this == paramASN1Primitive) {
        return true;
      }
      localDERExternal = (DERExternal)paramASN1Primitive;
    } while (((this.directReference != null) && ((localDERExternal.directReference == null) || (!localDERExternal.directReference.equals(this.directReference)))) || ((this.indirectReference != null) && ((localDERExternal.indirectReference == null) || (!localDERExternal.indirectReference.equals(this.indirectReference)))) || ((this.dataValueDescriptor != null) && ((localDERExternal.dataValueDescriptor == null) || (!localDERExternal.dataValueDescriptor.equals(this.dataValueDescriptor)))));
    return this.externalContent.equals(localDERExternal.externalContent);
  }
  
  void encode(ASN1OutputStream paramASN1OutputStream)
    throws IOException
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    if (this.directReference != null) {
      localByteArrayOutputStream.write(this.directReference.getEncoded("DER"));
    }
    if (this.indirectReference != null) {
      localByteArrayOutputStream.write(this.indirectReference.getEncoded("DER"));
    }
    if (this.dataValueDescriptor != null) {
      localByteArrayOutputStream.write(this.dataValueDescriptor.getEncoded("DER"));
    }
    localByteArrayOutputStream.write(new DERTaggedObject(true, this.encoding, this.externalContent).getEncoded("DER"));
    paramASN1OutputStream.writeEncoded(32, 8, localByteArrayOutputStream.toByteArray());
  }
  
  int encodedLength()
    throws IOException
  {
    return getEncoded().length;
  }
  
  public ASN1Primitive getDataValueDescriptor()
  {
    return this.dataValueDescriptor;
  }
  
  public ASN1ObjectIdentifier getDirectReference()
  {
    return this.directReference;
  }
  
  public int getEncoding()
  {
    return this.encoding;
  }
  
  public ASN1Primitive getExternalContent()
  {
    return this.externalContent;
  }
  
  public ASN1Integer getIndirectReference()
  {
    return this.indirectReference;
  }
  
  public int hashCode()
  {
    ASN1ObjectIdentifier localASN1ObjectIdentifier = this.directReference;
    int i = 0;
    if (localASN1ObjectIdentifier != null) {
      i = this.directReference.hashCode();
    }
    if (this.indirectReference != null) {
      i ^= this.indirectReference.hashCode();
    }
    if (this.dataValueDescriptor != null) {
      i ^= this.dataValueDescriptor.hashCode();
    }
    return i ^ this.externalContent.hashCode();
  }
  
  boolean isConstructed()
  {
    return true;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.DERExternal
 * JD-Core Version:    0.7.0.1
 */