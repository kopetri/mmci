package org.spongycastle.cert.crmf;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.crmf.EncryptedValue;
import org.spongycastle.asn1.x509.Certificate;
import org.spongycastle.cert.X509CertificateHolder;
import org.spongycastle.operator.InputDecryptor;
import org.spongycastle.util.Strings;
import org.spongycastle.util.io.Streams;

public class EncryptedValueParser
{
  private EncryptedValuePadder padder;
  private EncryptedValue value;
  
  public EncryptedValueParser(EncryptedValue paramEncryptedValue)
  {
    this.value = paramEncryptedValue;
  }
  
  public EncryptedValueParser(EncryptedValue paramEncryptedValue, EncryptedValuePadder paramEncryptedValuePadder)
  {
    this.value = paramEncryptedValue;
    this.padder = paramEncryptedValuePadder;
  }
  
  private byte[] decryptValue(ValueDecryptorGenerator paramValueDecryptorGenerator)
    throws CRMFException
  {
    if (this.value.getIntendedAlg() != null) {
      throw new UnsupportedOperationException();
    }
    if (this.value.getValueHint() != null) {
      throw new UnsupportedOperationException();
    }
    InputStream localInputStream = paramValueDecryptorGenerator.getValueDecryptor(this.value.getKeyAlg(), this.value.getSymmAlg(), this.value.getEncSymmKey().getBytes()).getInputStream(new ByteArrayInputStream(this.value.getEncValue().getBytes()));
    try
    {
      Object localObject = Streams.readAll(localInputStream);
      if (this.padder != null)
      {
        byte[] arrayOfByte = this.padder.getUnpaddedData((byte[])localObject);
        localObject = arrayOfByte;
      }
      return localObject;
    }
    catch (IOException localIOException)
    {
      throw new CRMFException("Cannot parse decrypted data: " + localIOException.getMessage(), localIOException);
    }
  }
  
  public X509CertificateHolder readCertificateHolder(ValueDecryptorGenerator paramValueDecryptorGenerator)
    throws CRMFException
  {
    return new X509CertificateHolder(Certificate.getInstance(decryptValue(paramValueDecryptorGenerator)));
  }
  
  public char[] readPassphrase(ValueDecryptorGenerator paramValueDecryptorGenerator)
    throws CRMFException
  {
    return Strings.fromUTF8ByteArray(decryptValue(paramValueDecryptorGenerator)).toCharArray();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.crmf.EncryptedValueParser
 * JD-Core Version:    0.7.0.1
 */