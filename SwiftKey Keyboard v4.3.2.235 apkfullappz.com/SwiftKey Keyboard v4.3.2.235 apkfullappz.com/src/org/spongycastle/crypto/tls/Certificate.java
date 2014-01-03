package org.spongycastle.crypto.tls;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.x509.X509CertificateStructure;

public class Certificate
{
  public static final Certificate EMPTY_CHAIN = new Certificate(new X509CertificateStructure[0]);
  protected X509CertificateStructure[] certs;
  
  public Certificate(X509CertificateStructure[] paramArrayOfX509CertificateStructure)
  {
    if (paramArrayOfX509CertificateStructure == null) {
      throw new IllegalArgumentException("'certs' cannot be null");
    }
    this.certs = paramArrayOfX509CertificateStructure;
  }
  
  protected static Certificate parse(InputStream paramInputStream)
    throws IOException
  {
    int i = TlsUtils.readUint24(paramInputStream);
    if (i == 0) {
      return EMPTY_CHAIN;
    }
    Vector localVector = new Vector();
    while (i > 0)
    {
      int k = TlsUtils.readUint24(paramInputStream);
      i -= k + 3;
      byte[] arrayOfByte = new byte[k];
      TlsUtils.readFully(arrayOfByte, paramInputStream);
      ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(arrayOfByte);
      localVector.addElement(X509CertificateStructure.getInstance(new ASN1InputStream(localByteArrayInputStream).readObject()));
      if (localByteArrayInputStream.available() > 0) {
        throw new IllegalArgumentException("Sorry, there is garbage data left after the certificate");
      }
    }
    X509CertificateStructure[] arrayOfX509CertificateStructure = new X509CertificateStructure[localVector.size()];
    for (int j = 0; j < localVector.size(); j++) {
      arrayOfX509CertificateStructure[j] = ((X509CertificateStructure)localVector.elementAt(j));
    }
    return new Certificate(arrayOfX509CertificateStructure);
  }
  
  protected void encode(OutputStream paramOutputStream)
    throws IOException
  {
    Vector localVector = new Vector();
    int i = 0;
    for (int j = 0; j < this.certs.length; j++)
    {
      byte[] arrayOfByte = this.certs[j].getEncoded("DER");
      localVector.addElement(arrayOfByte);
      i += 3 + arrayOfByte.length;
    }
    TlsUtils.writeUint24(i, paramOutputStream);
    for (int k = 0; k < localVector.size(); k++) {
      TlsUtils.writeOpaque24((byte[])localVector.elementAt(k), paramOutputStream);
    }
  }
  
  public X509CertificateStructure[] getCerts()
  {
    X509CertificateStructure[] arrayOfX509CertificateStructure = new X509CertificateStructure[this.certs.length];
    System.arraycopy(this.certs, 0, arrayOfX509CertificateStructure, 0, this.certs.length);
    return arrayOfX509CertificateStructure;
  }
  
  public boolean isEmpty()
  {
    return this.certs.length == 0;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.tls.Certificate
 * JD-Core Version:    0.7.0.1
 */