package org.spongycastle.jce;

import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.x509.KeyUsage;

public class X509KeyUsage
  extends ASN1Object
{
  public static final int cRLSign = 2;
  public static final int dataEncipherment = 16;
  public static final int decipherOnly = 32768;
  public static final int digitalSignature = 128;
  public static final int encipherOnly = 1;
  public static final int keyAgreement = 8;
  public static final int keyCertSign = 4;
  public static final int keyEncipherment = 32;
  public static final int nonRepudiation = 64;
  private int usage = 0;
  
  public X509KeyUsage(int paramInt)
  {
    this.usage = paramInt;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return new KeyUsage(this.usage);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jce.X509KeyUsage
 * JD-Core Version:    0.7.0.1
 */