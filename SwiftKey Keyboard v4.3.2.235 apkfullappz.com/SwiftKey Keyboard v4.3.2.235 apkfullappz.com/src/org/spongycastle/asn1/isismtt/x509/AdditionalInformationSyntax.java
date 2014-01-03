package org.spongycastle.asn1.isismtt.x509;

import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.x500.DirectoryString;

public class AdditionalInformationSyntax
  extends ASN1Object
{
  private DirectoryString information;
  
  public AdditionalInformationSyntax(String paramString)
  {
    this(new DirectoryString(paramString));
  }
  
  private AdditionalInformationSyntax(DirectoryString paramDirectoryString)
  {
    this.information = paramDirectoryString;
  }
  
  public static AdditionalInformationSyntax getInstance(Object paramObject)
  {
    if ((paramObject instanceof AdditionalInformationSyntax)) {
      return (AdditionalInformationSyntax)paramObject;
    }
    if (paramObject != null) {
      return new AdditionalInformationSyntax(DirectoryString.getInstance(paramObject));
    }
    return null;
  }
  
  public DirectoryString getInformation()
  {
    return this.information;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return this.information.toASN1Primitive();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.isismtt.x509.AdditionalInformationSyntax
 * JD-Core Version:    0.7.0.1
 */