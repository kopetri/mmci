package org.spongycastle.pkcs;

import java.io.IOException;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DLSequence;
import org.spongycastle.asn1.pkcs.AuthenticatedSafe;
import org.spongycastle.asn1.pkcs.ContentInfo;
import org.spongycastle.asn1.pkcs.MacData;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.pkcs.Pfx;
import org.spongycastle.cms.CMSEncryptedData;
import org.spongycastle.cms.CMSEncryptedDataGenerator;
import org.spongycastle.cms.CMSException;
import org.spongycastle.cms.CMSProcessableByteArray;
import org.spongycastle.operator.OutputEncryptor;

public class PKCS12PfxPduBuilder
{
  private ASN1EncodableVector dataVector = new ASN1EncodableVector();
  
  private PKCS12PfxPduBuilder addEncryptedData(OutputEncryptor paramOutputEncryptor, ASN1Sequence paramASN1Sequence)
    throws IOException
  {
    CMSEncryptedDataGenerator localCMSEncryptedDataGenerator = new CMSEncryptedDataGenerator();
    try
    {
      this.dataVector.add(localCMSEncryptedDataGenerator.generate(new CMSProcessableByteArray(paramASN1Sequence.getEncoded()), paramOutputEncryptor).toASN1Structure());
      return this;
    }
    catch (CMSException localCMSException)
    {
      throw new PKCSIOException(localCMSException.getMessage(), localCMSException.getCause());
    }
  }
  
  public PKCS12PfxPduBuilder addData(PKCS12SafeBag paramPKCS12SafeBag)
    throws IOException
  {
    this.dataVector.add(new ContentInfo(PKCSObjectIdentifiers.data, new DEROctetString(new DLSequence(paramPKCS12SafeBag.toASN1Structure()).getEncoded())));
    return this;
  }
  
  public PKCS12PfxPduBuilder addEncryptedData(OutputEncryptor paramOutputEncryptor, PKCS12SafeBag paramPKCS12SafeBag)
    throws IOException
  {
    return addEncryptedData(paramOutputEncryptor, new DERSequence(paramPKCS12SafeBag.toASN1Structure()));
  }
  
  public PKCS12PfxPduBuilder addEncryptedData(OutputEncryptor paramOutputEncryptor, PKCS12SafeBag[] paramArrayOfPKCS12SafeBag)
    throws IOException
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    for (int i = 0; i != paramArrayOfPKCS12SafeBag.length; i++) {
      localASN1EncodableVector.add(paramArrayOfPKCS12SafeBag[i].toASN1Structure());
    }
    return addEncryptedData(paramOutputEncryptor, new DLSequence(localASN1EncodableVector));
  }
  
  public PKCS12PfxPdu build(PKCS12MacCalculatorBuilder paramPKCS12MacCalculatorBuilder, char[] paramArrayOfChar)
    throws PKCSException
  {
    AuthenticatedSafe localAuthenticatedSafe = AuthenticatedSafe.getInstance(new DLSequence(this.dataVector));
    try
    {
      byte[] arrayOfByte = localAuthenticatedSafe.getEncoded();
      ContentInfo localContentInfo = new ContentInfo(PKCSObjectIdentifiers.data, new DEROctetString(arrayOfByte));
      MacData localMacData = null;
      if (paramPKCS12MacCalculatorBuilder != null) {
        localMacData = new MacDataGenerator(paramPKCS12MacCalculatorBuilder).build(paramArrayOfChar, arrayOfByte);
      }
      return new PKCS12PfxPdu(new Pfx(localContentInfo, localMacData));
    }
    catch (IOException localIOException)
    {
      throw new PKCSException("unable to encode AuthenticatedSafe: " + localIOException.getMessage(), localIOException);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.pkcs.PKCS12PfxPduBuilder
 * JD-Core Version:    0.7.0.1
 */