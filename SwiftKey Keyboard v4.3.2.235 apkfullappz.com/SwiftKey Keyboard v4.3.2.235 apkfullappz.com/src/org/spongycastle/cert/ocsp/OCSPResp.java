package org.spongycastle.cert.ocsp;

import java.io.IOException;
import java.math.BigInteger;
import org.spongycastle.asn1.ASN1Exception;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ocsp.BasicOCSPResponse;
import org.spongycastle.asn1.ocsp.OCSPObjectIdentifiers;
import org.spongycastle.asn1.ocsp.OCSPResponse;
import org.spongycastle.asn1.ocsp.OCSPResponseStatus;
import org.spongycastle.asn1.ocsp.ResponseBytes;
import org.spongycastle.cert.CertIOException;

public class OCSPResp
{
  private OCSPResponse resp;
  
  private OCSPResp(ASN1InputStream paramASN1InputStream)
    throws IOException
  {
    try
    {
      this.resp = OCSPResponse.getInstance(paramASN1InputStream.readObject());
      return;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      throw new CertIOException("malformed response: " + localIllegalArgumentException.getMessage(), localIllegalArgumentException);
    }
    catch (ClassCastException localClassCastException)
    {
      throw new CertIOException("malformed response: " + localClassCastException.getMessage(), localClassCastException);
    }
    catch (ASN1Exception localASN1Exception)
    {
      throw new CertIOException("malformed response: " + localASN1Exception.getMessage(), localASN1Exception);
    }
  }
  
  public OCSPResp(OCSPResponse paramOCSPResponse)
  {
    this.resp = paramOCSPResponse;
  }
  
  public OCSPResp(byte[] paramArrayOfByte)
    throws IOException
  {
    this(new ASN1InputStream(paramArrayOfByte));
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == this) {
      return true;
    }
    if (!(paramObject instanceof OCSPResp)) {
      return false;
    }
    OCSPResp localOCSPResp = (OCSPResp)paramObject;
    return this.resp.equals(localOCSPResp.resp);
  }
  
  public byte[] getEncoded()
    throws IOException
  {
    return this.resp.getEncoded();
  }
  
  public Object getResponseObject()
    throws OCSPException
  {
    ResponseBytes localResponseBytes = this.resp.getResponseBytes();
    if (localResponseBytes == null) {
      return null;
    }
    if (localResponseBytes.getResponseType().equals(OCSPObjectIdentifiers.id_pkix_ocsp_basic)) {
      try
      {
        BasicOCSPResp localBasicOCSPResp = new BasicOCSPResp(BasicOCSPResponse.getInstance(ASN1Primitive.fromByteArray(localResponseBytes.getResponse().getOctets())));
        return localBasicOCSPResp;
      }
      catch (Exception localException)
      {
        throw new OCSPException("problem decoding object: " + localException, localException);
      }
    }
    return localResponseBytes.getResponse();
  }
  
  public int getStatus()
  {
    return this.resp.getResponseStatus().getValue().intValue();
  }
  
  public int hashCode()
  {
    return this.resp.hashCode();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.ocsp.OCSPResp
 * JD-Core Version:    0.7.0.1
 */