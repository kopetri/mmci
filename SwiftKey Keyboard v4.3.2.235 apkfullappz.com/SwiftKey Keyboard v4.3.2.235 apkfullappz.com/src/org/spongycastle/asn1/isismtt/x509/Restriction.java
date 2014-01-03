package org.spongycastle.asn1.isismtt.x509;

import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.x500.DirectoryString;

public class Restriction
  extends ASN1Object
{
  private DirectoryString restriction;
  
  public Restriction(String paramString)
  {
    this.restriction = new DirectoryString(paramString);
  }
  
  private Restriction(DirectoryString paramDirectoryString)
  {
    this.restriction = paramDirectoryString;
  }
  
  public static Restriction getInstance(Object paramObject)
  {
    if ((paramObject instanceof Restriction)) {
      return (Restriction)paramObject;
    }
    if (paramObject != null) {
      return new Restriction(DirectoryString.getInstance(paramObject));
    }
    return null;
  }
  
  public DirectoryString getRestriction()
  {
    return this.restriction;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return this.restriction.toASN1Primitive();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.isismtt.x509.Restriction
 * JD-Core Version:    0.7.0.1
 */