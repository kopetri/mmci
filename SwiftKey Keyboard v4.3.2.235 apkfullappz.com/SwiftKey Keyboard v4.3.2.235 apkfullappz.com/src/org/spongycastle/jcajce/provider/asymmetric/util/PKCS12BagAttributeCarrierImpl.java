package org.spongycastle.jcajce.provider.asymmetric.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OutputStream;
import org.spongycastle.asn1.DERObjectIdentifier;
import org.spongycastle.jce.interfaces.PKCS12BagAttributeCarrier;

public class PKCS12BagAttributeCarrierImpl
  implements PKCS12BagAttributeCarrier
{
  private Hashtable pkcs12Attributes;
  private Vector pkcs12Ordering;
  
  public PKCS12BagAttributeCarrierImpl()
  {
    this(new Hashtable(), new Vector());
  }
  
  PKCS12BagAttributeCarrierImpl(Hashtable paramHashtable, Vector paramVector)
  {
    this.pkcs12Attributes = paramHashtable;
    this.pkcs12Ordering = paramVector;
  }
  
  Hashtable getAttributes()
  {
    return this.pkcs12Attributes;
  }
  
  public ASN1Encodable getBagAttribute(DERObjectIdentifier paramDERObjectIdentifier)
  {
    return (ASN1Encodable)this.pkcs12Attributes.get(paramDERObjectIdentifier);
  }
  
  public Enumeration getBagAttributeKeys()
  {
    return this.pkcs12Ordering.elements();
  }
  
  Vector getOrdering()
  {
    return this.pkcs12Ordering;
  }
  
  public void readObject(ObjectInputStream paramObjectInputStream)
    throws IOException, ClassNotFoundException
  {
    Object localObject = paramObjectInputStream.readObject();
    if ((localObject instanceof Hashtable))
    {
      this.pkcs12Attributes = ((Hashtable)localObject);
      this.pkcs12Ordering = ((Vector)paramObjectInputStream.readObject());
      return;
    }
    ASN1InputStream localASN1InputStream = new ASN1InputStream((byte[])localObject);
    for (;;)
    {
      ASN1ObjectIdentifier localASN1ObjectIdentifier = (ASN1ObjectIdentifier)localASN1InputStream.readObject();
      if (localASN1ObjectIdentifier == null) {
        break;
      }
      setBagAttribute(localASN1ObjectIdentifier, localASN1InputStream.readObject());
    }
  }
  
  public void setBagAttribute(ASN1ObjectIdentifier paramASN1ObjectIdentifier, ASN1Encodable paramASN1Encodable)
  {
    if (this.pkcs12Attributes.containsKey(paramASN1ObjectIdentifier))
    {
      this.pkcs12Attributes.put(paramASN1ObjectIdentifier, paramASN1Encodable);
      return;
    }
    this.pkcs12Attributes.put(paramASN1ObjectIdentifier, paramASN1Encodable);
    this.pkcs12Ordering.addElement(paramASN1ObjectIdentifier);
  }
  
  int size()
  {
    return this.pkcs12Ordering.size();
  }
  
  public void writeObject(ObjectOutputStream paramObjectOutputStream)
    throws IOException
  {
    if (this.pkcs12Ordering.size() == 0)
    {
      paramObjectOutputStream.writeObject(new Hashtable());
      paramObjectOutputStream.writeObject(new Vector());
      return;
    }
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    ASN1OutputStream localASN1OutputStream = new ASN1OutputStream(localByteArrayOutputStream);
    Enumeration localEnumeration = getBagAttributeKeys();
    while (localEnumeration.hasMoreElements())
    {
      DERObjectIdentifier localDERObjectIdentifier = (DERObjectIdentifier)localEnumeration.nextElement();
      localASN1OutputStream.writeObject(localDERObjectIdentifier);
      localASN1OutputStream.writeObject((ASN1Encodable)this.pkcs12Attributes.get(localDERObjectIdentifier));
    }
    paramObjectOutputStream.writeObject(localByteArrayOutputStream.toByteArray());
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl
 * JD-Core Version:    0.7.0.1
 */