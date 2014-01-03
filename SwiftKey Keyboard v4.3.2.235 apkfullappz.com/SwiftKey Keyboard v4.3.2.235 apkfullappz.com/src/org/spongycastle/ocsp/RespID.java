package org.spongycastle.ocsp;

import java.security.MessageDigest;
import java.security.PublicKey;
import javax.security.auth.x500.X500Principal;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.ocsp.ResponderID;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;

public class RespID
{
  ResponderID id;
  
  public RespID(PublicKey paramPublicKey)
    throws OCSPException
  {
    try
    {
      MessageDigest localMessageDigest = OCSPUtil.createDigestInstance("SHA1", null);
      localMessageDigest.update(SubjectPublicKeyInfo.getInstance(new ASN1InputStream(paramPublicKey.getEncoded()).readObject()).getPublicKeyData().getBytes());
      this.id = new ResponderID(new DEROctetString(localMessageDigest.digest()));
      return;
    }
    catch (Exception localException)
    {
      throw new OCSPException("problem creating ID: " + localException, localException);
    }
  }
  
  public RespID(X500Principal paramX500Principal)
  {
    this.id = new ResponderID(X500Name.getInstance(paramX500Principal.getEncoded()));
  }
  
  public RespID(ResponderID paramResponderID)
  {
    this.id = paramResponderID;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof RespID)) {
      return false;
    }
    RespID localRespID = (RespID)paramObject;
    return this.id.equals(localRespID.id);
  }
  
  public int hashCode()
  {
    return this.id.hashCode();
  }
  
  public ResponderID toASN1Object()
  {
    return this.id;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.ocsp.RespID
 * JD-Core Version:    0.7.0.1
 */