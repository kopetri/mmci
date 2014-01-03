package org.spongycastle.asn1;

public class OIDTokenizer
{
  private int index;
  private String oid;
  
  public OIDTokenizer(String paramString)
  {
    this.oid = paramString;
    this.index = 0;
  }
  
  public boolean hasMoreTokens()
  {
    return this.index != -1;
  }
  
  public String nextToken()
  {
    if (this.index == -1) {
      return null;
    }
    int i = this.oid.indexOf('.', this.index);
    if (i == -1)
    {
      String str2 = this.oid.substring(this.index);
      this.index = -1;
      return str2;
    }
    String str1 = this.oid.substring(this.index, i);
    this.index = (i + 1);
    return str1;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.OIDTokenizer
 * JD-Core Version:    0.7.0.1
 */