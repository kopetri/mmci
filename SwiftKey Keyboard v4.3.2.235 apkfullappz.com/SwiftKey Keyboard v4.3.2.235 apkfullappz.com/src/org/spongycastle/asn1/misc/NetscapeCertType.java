package org.spongycastle.asn1.misc;

import org.spongycastle.asn1.DERBitString;

public class NetscapeCertType
  extends DERBitString
{
  public static final int objectSigning = 16;
  public static final int objectSigningCA = 1;
  public static final int reserved = 8;
  public static final int smime = 32;
  public static final int smimeCA = 2;
  public static final int sslCA = 4;
  public static final int sslClient = 128;
  public static final int sslServer = 64;
  
  public NetscapeCertType(int paramInt)
  {
    super(getBytes(paramInt), getPadBits(paramInt));
  }
  
  public NetscapeCertType(DERBitString paramDERBitString)
  {
    super(paramDERBitString.getBytes(), paramDERBitString.getPadBits());
  }
  
  public String toString()
  {
    return "NetscapeCertType: 0x" + Integer.toHexString(0xFF & this.data[0]);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.misc.NetscapeCertType
 * JD-Core Version:    0.7.0.1
 */