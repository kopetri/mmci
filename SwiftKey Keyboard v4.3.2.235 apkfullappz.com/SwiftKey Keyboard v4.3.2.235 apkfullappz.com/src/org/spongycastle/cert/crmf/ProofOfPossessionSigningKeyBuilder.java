package org.spongycastle.cert.crmf;

import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.crmf.CertRequest;
import org.spongycastle.asn1.crmf.PKMACValue;
import org.spongycastle.asn1.crmf.POPOSigningKey;
import org.spongycastle.asn1.crmf.POPOSigningKeyInput;
import org.spongycastle.asn1.x509.GeneralName;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.operator.ContentSigner;

public class ProofOfPossessionSigningKeyBuilder
{
  private CertRequest certRequest;
  private GeneralName name;
  private SubjectPublicKeyInfo pubKeyInfo;
  private PKMACValue publicKeyMAC;
  
  public ProofOfPossessionSigningKeyBuilder(CertRequest paramCertRequest)
  {
    this.certRequest = paramCertRequest;
  }
  
  public ProofOfPossessionSigningKeyBuilder(SubjectPublicKeyInfo paramSubjectPublicKeyInfo)
  {
    this.pubKeyInfo = paramSubjectPublicKeyInfo;
  }
  
  public POPOSigningKey build(ContentSigner paramContentSigner)
  {
    if ((this.name != null) && (this.publicKeyMAC != null)) {
      throw new IllegalStateException("name and publicKeyMAC cannot both be set.");
    }
    POPOSigningKeyInput localPOPOSigningKeyInput;
    if (this.certRequest != null)
    {
      localPOPOSigningKeyInput = null;
      CRMFUtil.derEncodeToStream(this.certRequest, paramContentSigner.getOutputStream());
    }
    for (;;)
    {
      return new POPOSigningKey(localPOPOSigningKeyInput, paramContentSigner.getAlgorithmIdentifier(), new DERBitString(paramContentSigner.getSignature()));
      if (this.name != null)
      {
        localPOPOSigningKeyInput = new POPOSigningKeyInput(this.name, this.pubKeyInfo);
        CRMFUtil.derEncodeToStream(localPOPOSigningKeyInput, paramContentSigner.getOutputStream());
      }
      else
      {
        localPOPOSigningKeyInput = new POPOSigningKeyInput(this.publicKeyMAC, this.pubKeyInfo);
        CRMFUtil.derEncodeToStream(localPOPOSigningKeyInput, paramContentSigner.getOutputStream());
      }
    }
  }
  
  public ProofOfPossessionSigningKeyBuilder setPublicKeyMac(PKMACValueGenerator paramPKMACValueGenerator, char[] paramArrayOfChar)
    throws CRMFException
  {
    this.publicKeyMAC = paramPKMACValueGenerator.generate(paramArrayOfChar, this.pubKeyInfo);
    return this;
  }
  
  public ProofOfPossessionSigningKeyBuilder setSender(GeneralName paramGeneralName)
  {
    this.name = paramGeneralName;
    return this;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.crmf.ProofOfPossessionSigningKeyBuilder
 * JD-Core Version:    0.7.0.1
 */