package org.spongycastle.util.encoders;

public class UrlBase64Encoder
  extends Base64Encoder
{
  public UrlBase64Encoder()
  {
    this.encodingTable[(-2 + this.encodingTable.length)] = 45;
    this.encodingTable[(-1 + this.encodingTable.length)] = 95;
    this.padding = 46;
    initialiseDecodingTable();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.util.encoders.UrlBase64Encoder
 * JD-Core Version:    0.7.0.1
 */