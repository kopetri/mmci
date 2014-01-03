package org.spongycastle.asn1;

public class ASN1ObjectIdentifier
  extends DERObjectIdentifier
{
  public ASN1ObjectIdentifier(String paramString)
  {
    super(paramString);
  }
  
  ASN1ObjectIdentifier(byte[] paramArrayOfByte)
  {
    super(paramArrayOfByte);
  }
  
  public ASN1ObjectIdentifier branch(String paramString)
  {
    return new ASN1ObjectIdentifier(getId() + "." + paramString);
  }
  
  public boolean on(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
  {
    String str1 = getId();
    String str2 = paramASN1ObjectIdentifier.getId();
    return (str1.length() > str2.length()) && (str1.charAt(str2.length()) == '.') && (str1.startsWith(str2));
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.ASN1ObjectIdentifier
 * JD-Core Version:    0.7.0.1
 */