package org.spongycastle.asn1.x509;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERObjectIdentifier;
import org.spongycastle.asn1.DEROctetString;

public class X509ExtensionsGenerator
{
  private Vector extOrdering = new Vector();
  private Hashtable extensions = new Hashtable();
  
  public void addExtension(ASN1ObjectIdentifier paramASN1ObjectIdentifier, boolean paramBoolean, ASN1Encodable paramASN1Encodable)
  {
    try
    {
      addExtension(paramASN1ObjectIdentifier, paramBoolean, paramASN1Encodable.toASN1Primitive().getEncoded("DER"));
      return;
    }
    catch (IOException localIOException)
    {
      throw new IllegalArgumentException("error encoding value: " + localIOException);
    }
  }
  
  public void addExtension(ASN1ObjectIdentifier paramASN1ObjectIdentifier, boolean paramBoolean, byte[] paramArrayOfByte)
  {
    if (this.extensions.containsKey(paramASN1ObjectIdentifier)) {
      throw new IllegalArgumentException("extension " + paramASN1ObjectIdentifier + " already added");
    }
    this.extOrdering.addElement(paramASN1ObjectIdentifier);
    this.extensions.put(paramASN1ObjectIdentifier, new X509Extension(paramBoolean, new DEROctetString(paramArrayOfByte)));
  }
  
  public void addExtension(DERObjectIdentifier paramDERObjectIdentifier, boolean paramBoolean, ASN1Encodable paramASN1Encodable)
  {
    addExtension(new ASN1ObjectIdentifier(paramDERObjectIdentifier.getId()), paramBoolean, paramASN1Encodable);
  }
  
  public void addExtension(DERObjectIdentifier paramDERObjectIdentifier, boolean paramBoolean, byte[] paramArrayOfByte)
  {
    addExtension(new ASN1ObjectIdentifier(paramDERObjectIdentifier.getId()), paramBoolean, paramArrayOfByte);
  }
  
  public X509Extensions generate()
  {
    return new X509Extensions(this.extOrdering, this.extensions);
  }
  
  public boolean isEmpty()
  {
    return this.extOrdering.isEmpty();
  }
  
  public void reset()
  {
    this.extensions = new Hashtable();
    this.extOrdering = new Vector();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x509.X509ExtensionsGenerator
 * JD-Core Version:    0.7.0.1
 */