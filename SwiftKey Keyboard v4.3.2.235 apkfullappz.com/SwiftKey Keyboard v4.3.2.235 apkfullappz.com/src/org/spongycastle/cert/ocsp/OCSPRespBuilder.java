package org.spongycastle.cert.ocsp;

import java.io.IOException;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.ocsp.OCSPObjectIdentifiers;
import org.spongycastle.asn1.ocsp.OCSPResponse;
import org.spongycastle.asn1.ocsp.OCSPResponseStatus;
import org.spongycastle.asn1.ocsp.ResponseBytes;

public class OCSPRespBuilder
{
  public static final int INTERNAL_ERROR = 2;
  public static final int MALFORMED_REQUEST = 1;
  public static final int SIG_REQUIRED = 5;
  public static final int SUCCESSFUL = 0;
  public static final int TRY_LATER = 3;
  public static final int UNAUTHORIZED = 6;
  
  public OCSPResp build(int paramInt, Object paramObject)
    throws OCSPException
  {
    if (paramObject == null) {
      return new OCSPResp(new OCSPResponse(new OCSPResponseStatus(paramInt), null));
    }
    if ((paramObject instanceof BasicOCSPResp))
    {
      BasicOCSPResp localBasicOCSPResp = (BasicOCSPResp)paramObject;
      try
      {
        DEROctetString localDEROctetString = new DEROctetString(localBasicOCSPResp.getEncoded());
        ResponseBytes localResponseBytes = new ResponseBytes(OCSPObjectIdentifiers.id_pkix_ocsp_basic, localDEROctetString);
        return new OCSPResp(new OCSPResponse(new OCSPResponseStatus(paramInt), localResponseBytes));
      }
      catch (IOException localIOException)
      {
        throw new OCSPException("can't encode object.", localIOException);
      }
    }
    throw new OCSPException("unknown response object");
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.ocsp.OCSPRespBuilder
 * JD-Core Version:    0.7.0.1
 */