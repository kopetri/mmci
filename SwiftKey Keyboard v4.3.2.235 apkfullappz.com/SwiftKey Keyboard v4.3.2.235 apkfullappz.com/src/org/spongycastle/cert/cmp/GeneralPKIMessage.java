package org.spongycastle.cert.cmp;

import java.io.IOException;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.cmp.PKIBody;
import org.spongycastle.asn1.cmp.PKIHeader;
import org.spongycastle.asn1.cmp.PKIMessage;
import org.spongycastle.cert.CertIOException;

public class GeneralPKIMessage
{
  private final PKIMessage pkiMessage;
  
  public GeneralPKIMessage(PKIMessage paramPKIMessage)
  {
    this.pkiMessage = paramPKIMessage;
  }
  
  public GeneralPKIMessage(byte[] paramArrayOfByte)
    throws IOException
  {
    this(parseBytes(paramArrayOfByte));
  }
  
  private static PKIMessage parseBytes(byte[] paramArrayOfByte)
    throws IOException
  {
    try
    {
      PKIMessage localPKIMessage = PKIMessage.getInstance(ASN1Primitive.fromByteArray(paramArrayOfByte));
      return localPKIMessage;
    }
    catch (ClassCastException localClassCastException)
    {
      throw new CertIOException("malformed data: " + localClassCastException.getMessage(), localClassCastException);
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      throw new CertIOException("malformed data: " + localIllegalArgumentException.getMessage(), localIllegalArgumentException);
    }
  }
  
  public PKIBody getBody()
  {
    return this.pkiMessage.getBody();
  }
  
  public PKIHeader getHeader()
  {
    return this.pkiMessage.getHeader();
  }
  
  public boolean hasProtection()
  {
    return this.pkiMessage.getHeader().getProtectionAlg() != null;
  }
  
  public PKIMessage toASN1Structure()
  {
    return this.pkiMessage;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.cmp.GeneralPKIMessage
 * JD-Core Version:    0.7.0.1
 */