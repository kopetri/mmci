package org.spongycastle.asn1.x509;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DEROctetString;

public class ExtensionsGenerator
{
  private Vector extOrdering = new Vector();
  private Hashtable extensions = new Hashtable();
  
  public void addExtension(ASN1ObjectIdentifier paramASN1ObjectIdentifier, boolean paramBoolean, ASN1Encodable paramASN1Encodable)
    throws IOException
  {
    addExtension(paramASN1ObjectIdentifier, paramBoolean, paramASN1Encodable.toASN1Primitive().getEncoded("DER"));
  }
  
  public void addExtension(ASN1ObjectIdentifier paramASN1ObjectIdentifier, boolean paramBoolean, byte[] paramArrayOfByte)
  {
    if (this.extensions.containsKey(paramASN1ObjectIdentifier)) {
      throw new IllegalArgumentException("extension " + paramASN1ObjectIdentifier + " already added");
    }
    this.extOrdering.addElement(paramASN1ObjectIdentifier);
    this.extensions.put(paramASN1ObjectIdentifier, new Extension(paramASN1ObjectIdentifier, paramBoolean, new DEROctetString(paramArrayOfByte)));
  }
  
  public Extensions generate()
  {
    Extension[] arrayOfExtension = new Extension[this.extOrdering.size()];
    for (int i = 0; i != this.extOrdering.size(); i++) {
      arrayOfExtension[i] = ((Extension)this.extensions.get(this.extOrdering.elementAt(i)));
    }
    return new Extensions(arrayOfExtension);
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
 * Qualified Name:     org.spongycastle.asn1.x509.ExtensionsGenerator
 * JD-Core Version:    0.7.0.1
 */