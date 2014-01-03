package org.spongycastle.cert.ocsp;

import java.io.OutputStream;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.ocsp.ResponderID;
import org.spongycastle.asn1.oiw.OIWObjectIdentifiers;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.operator.DigestCalculator;

public class RespID
{
  public static final AlgorithmIdentifier HASH_SHA1 = new AlgorithmIdentifier(OIWObjectIdentifiers.idSHA1, DERNull.INSTANCE);
  ResponderID id;
  
  public RespID(ResponderID paramResponderID)
  {
    this.id = paramResponderID;
  }
  
  public RespID(X500Name paramX500Name)
  {
    this.id = new ResponderID(paramX500Name);
  }
  
  public RespID(SubjectPublicKeyInfo paramSubjectPublicKeyInfo, DigestCalculator paramDigestCalculator)
    throws OCSPException
  {
    try
    {
      if (!paramDigestCalculator.getAlgorithmIdentifier().equals(HASH_SHA1)) {
        throw new IllegalArgumentException("only SHA-1 can be used with RespID");
      }
    }
    catch (Exception localException)
    {
      throw new OCSPException("problem creating ID: " + localException, localException);
    }
    OutputStream localOutputStream = paramDigestCalculator.getOutputStream();
    localOutputStream.write(paramSubjectPublicKeyInfo.getPublicKeyData().getBytes());
    localOutputStream.close();
    this.id = new ResponderID(new DEROctetString(paramDigestCalculator.getDigest()));
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
 * Qualified Name:     org.spongycastle.cert.ocsp.RespID
 * JD-Core Version:    0.7.0.1
 */