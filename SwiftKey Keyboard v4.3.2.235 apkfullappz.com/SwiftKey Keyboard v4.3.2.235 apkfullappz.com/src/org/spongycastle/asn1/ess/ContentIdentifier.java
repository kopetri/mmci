package org.spongycastle.asn1.ess;

import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DEROctetString;

public class ContentIdentifier
  extends ASN1Object
{
  ASN1OctetString value;
  
  private ContentIdentifier(ASN1OctetString paramASN1OctetString)
  {
    this.value = paramASN1OctetString;
  }
  
  public ContentIdentifier(byte[] paramArrayOfByte)
  {
    this(new DEROctetString(paramArrayOfByte));
  }
  
  public static ContentIdentifier getInstance(Object paramObject)
  {
    if ((paramObject instanceof ContentIdentifier)) {
      return (ContentIdentifier)paramObject;
    }
    if (paramObject != null) {
      return new ContentIdentifier(ASN1OctetString.getInstance(paramObject));
    }
    return null;
  }
  
  public ASN1OctetString getValue()
  {
    return this.value;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return this.value;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.ess.ContentIdentifier
 * JD-Core Version:    0.7.0.1
 */