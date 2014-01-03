package org.spongycastle.asn1.util;

import java.io.FileInputStream;
import java.io.PrintStream;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1Primitive;

public class Dump
{
  public static void main(String[] paramArrayOfString)
    throws Exception
  {
    ASN1InputStream localASN1InputStream = new ASN1InputStream(new FileInputStream(paramArrayOfString[0]));
    for (;;)
    {
      ASN1Primitive localASN1Primitive = localASN1InputStream.readObject();
      if (localASN1Primitive == null) {
        break;
      }
      System.out.println(ASN1Dump.dumpAsString(localASN1Primitive));
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.util.Dump
 * JD-Core Version:    0.7.0.1
 */