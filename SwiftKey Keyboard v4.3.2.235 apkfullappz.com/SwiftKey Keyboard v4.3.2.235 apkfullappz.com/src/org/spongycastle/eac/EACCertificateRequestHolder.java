package org.spongycastle.eac;

import java.io.IOException;
import java.io.OutputStream;
import org.spongycastle.asn1.ASN1ParsingException;
import org.spongycastle.asn1.eac.CVCertificateRequest;
import org.spongycastle.asn1.eac.CertificateBody;
import org.spongycastle.asn1.eac.PublicKeyDataObject;
import org.spongycastle.eac.operator.EACSignatureVerifier;

public class EACCertificateRequestHolder
{
  private CVCertificateRequest request;
  
  public EACCertificateRequestHolder(CVCertificateRequest paramCVCertificateRequest)
  {
    this.request = paramCVCertificateRequest;
  }
  
  public EACCertificateRequestHolder(byte[] paramArrayOfByte)
    throws IOException
  {
    this(parseBytes(paramArrayOfByte));
  }
  
  private static CVCertificateRequest parseBytes(byte[] paramArrayOfByte)
    throws IOException
  {
    try
    {
      CVCertificateRequest localCVCertificateRequest = CVCertificateRequest.getInstance(paramArrayOfByte);
      return localCVCertificateRequest;
    }
    catch (ClassCastException localClassCastException)
    {
      throw new EACIOException("malformed data: " + localClassCastException.getMessage(), localClassCastException);
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      throw new EACIOException("malformed data: " + localIllegalArgumentException.getMessage(), localIllegalArgumentException);
    }
    catch (ASN1ParsingException localASN1ParsingException)
    {
      if ((localASN1ParsingException.getCause() instanceof IOException)) {
        throw ((IOException)localASN1ParsingException.getCause());
      }
      throw new EACIOException("malformed data: " + localASN1ParsingException.getMessage(), localASN1ParsingException);
    }
  }
  
  public PublicKeyDataObject getPublicKeyDataObject()
  {
    return this.request.getPublicKey();
  }
  
  public boolean isInnerSignatureValid(EACSignatureVerifier paramEACSignatureVerifier)
    throws EACException
  {
    try
    {
      OutputStream localOutputStream = paramEACSignatureVerifier.getOutputStream();
      localOutputStream.write(this.request.getCertificateBody().getEncoded("DER"));
      localOutputStream.close();
      boolean bool = paramEACSignatureVerifier.verify(this.request.getInnerSignature());
      return bool;
    }
    catch (Exception localException)
    {
      throw new EACException("unable to process signature: " + localException.getMessage(), localException);
    }
  }
  
  public CVCertificateRequest toASN1Structure()
  {
    return this.request;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.eac.EACCertificateRequestHolder
 * JD-Core Version:    0.7.0.1
 */