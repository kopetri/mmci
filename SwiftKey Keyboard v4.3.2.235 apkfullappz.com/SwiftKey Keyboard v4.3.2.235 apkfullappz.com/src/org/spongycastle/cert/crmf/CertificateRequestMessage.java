package org.spongycastle.cert.crmf;

import java.io.IOException;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERUTF8String;
import org.spongycastle.asn1.crmf.AttributeTypeAndValue;
import org.spongycastle.asn1.crmf.CRMFObjectIdentifiers;
import org.spongycastle.asn1.crmf.CertReqMsg;
import org.spongycastle.asn1.crmf.CertRequest;
import org.spongycastle.asn1.crmf.CertTemplate;
import org.spongycastle.asn1.crmf.Controls;
import org.spongycastle.asn1.crmf.PKIArchiveOptions;
import org.spongycastle.asn1.crmf.PKMACValue;
import org.spongycastle.asn1.crmf.POPOSigningKey;
import org.spongycastle.asn1.crmf.POPOSigningKeyInput;
import org.spongycastle.asn1.crmf.ProofOfPossession;
import org.spongycastle.cert.CertIOException;
import org.spongycastle.operator.ContentVerifier;
import org.spongycastle.operator.ContentVerifierProvider;
import org.spongycastle.operator.OperatorCreationException;

public class CertificateRequestMessage
{
  public static final int popKeyAgreement = 3;
  public static final int popKeyEncipherment = 2;
  public static final int popRaVerified = 0;
  public static final int popSigningKey = 1;
  private final CertReqMsg certReqMsg;
  private final Controls controls;
  
  public CertificateRequestMessage(CertReqMsg paramCertReqMsg)
  {
    this.certReqMsg = paramCertReqMsg;
    this.controls = paramCertReqMsg.getCertReq().getControls();
  }
  
  public CertificateRequestMessage(byte[] paramArrayOfByte)
    throws IOException
  {
    this(parseBytes(paramArrayOfByte));
  }
  
  private AttributeTypeAndValue findControl(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
  {
    if (this.controls == null) {}
    for (;;)
    {
      return null;
      AttributeTypeAndValue[] arrayOfAttributeTypeAndValue = this.controls.toAttributeTypeAndValueArray();
      for (int i = 0; i != arrayOfAttributeTypeAndValue.length; i++) {
        if (arrayOfAttributeTypeAndValue[i].getType().equals(paramASN1ObjectIdentifier)) {
          return arrayOfAttributeTypeAndValue[i];
        }
      }
    }
  }
  
  private static CertReqMsg parseBytes(byte[] paramArrayOfByte)
    throws IOException
  {
    try
    {
      CertReqMsg localCertReqMsg = CertReqMsg.getInstance(ASN1Primitive.fromByteArray(paramArrayOfByte));
      return localCertReqMsg;
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
  
  private boolean verifySignature(ContentVerifierProvider paramContentVerifierProvider, POPOSigningKey paramPOPOSigningKey)
    throws CRMFException
  {
    for (;;)
    {
      ContentVerifier localContentVerifier;
      try
      {
        localContentVerifier = paramContentVerifierProvider.get(paramPOPOSigningKey.getAlgorithmIdentifier());
        if (paramPOPOSigningKey.getPoposkInput() != null)
        {
          CRMFUtil.derEncodeToStream(paramPOPOSigningKey.getPoposkInput(), localContentVerifier.getOutputStream());
          return localContentVerifier.verify(paramPOPOSigningKey.getSignature().getBytes());
        }
      }
      catch (OperatorCreationException localOperatorCreationException)
      {
        throw new CRMFException("unable to create verifier: " + localOperatorCreationException.getMessage(), localOperatorCreationException);
      }
      CRMFUtil.derEncodeToStream(this.certReqMsg.getCertReq(), localContentVerifier.getOutputStream());
    }
  }
  
  public CertTemplate getCertTemplate()
  {
    return this.certReqMsg.getCertReq().getCertTemplate();
  }
  
  public Control getControl(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
  {
    AttributeTypeAndValue localAttributeTypeAndValue = findControl(paramASN1ObjectIdentifier);
    if (localAttributeTypeAndValue != null)
    {
      if (localAttributeTypeAndValue.getType().equals(CRMFObjectIdentifiers.id_regCtrl_pkiArchiveOptions)) {
        return new PKIArchiveControl(PKIArchiveOptions.getInstance(localAttributeTypeAndValue.getValue()));
      }
      if (localAttributeTypeAndValue.getType().equals(CRMFObjectIdentifiers.id_regCtrl_regToken)) {
        return new RegTokenControl(DERUTF8String.getInstance(localAttributeTypeAndValue.getValue()));
      }
      if (localAttributeTypeAndValue.getType().equals(CRMFObjectIdentifiers.id_regCtrl_authenticator)) {
        return new AuthenticatorControl(DERUTF8String.getInstance(localAttributeTypeAndValue.getValue()));
      }
    }
    return null;
  }
  
  public byte[] getEncoded()
    throws IOException
  {
    return this.certReqMsg.getEncoded();
  }
  
  public int getProofOfPossessionType()
  {
    return this.certReqMsg.getPopo().getType();
  }
  
  public boolean hasControl(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
  {
    return findControl(paramASN1ObjectIdentifier) != null;
  }
  
  public boolean hasControls()
  {
    return this.controls != null;
  }
  
  public boolean hasProofOfPossession()
  {
    return this.certReqMsg.getPopo() != null;
  }
  
  public boolean hasSigningKeyProofOfPossessionWithPKMAC()
  {
    ProofOfPossession localProofOfPossession = this.certReqMsg.getPopo();
    if (localProofOfPossession.getType() == 1) {
      return POPOSigningKey.getInstance(localProofOfPossession.getObject()).getPoposkInput().getPublicKeyMAC() != null;
    }
    return false;
  }
  
  public boolean isValidSigningKeyPOP(ContentVerifierProvider paramContentVerifierProvider)
    throws CRMFException, IllegalStateException
  {
    ProofOfPossession localProofOfPossession = this.certReqMsg.getPopo();
    if (localProofOfPossession.getType() == 1)
    {
      POPOSigningKey localPOPOSigningKey = POPOSigningKey.getInstance(localProofOfPossession.getObject());
      if ((localPOPOSigningKey.getPoposkInput() != null) && (localPOPOSigningKey.getPoposkInput().getPublicKeyMAC() != null)) {
        throw new IllegalStateException("verification requires password check");
      }
      return verifySignature(paramContentVerifierProvider, localPOPOSigningKey);
    }
    throw new IllegalStateException("not Signing Key type of proof of possession");
  }
  
  public boolean isValidSigningKeyPOP(ContentVerifierProvider paramContentVerifierProvider, PKMACBuilder paramPKMACBuilder, char[] paramArrayOfChar)
    throws CRMFException, IllegalStateException
  {
    ProofOfPossession localProofOfPossession = this.certReqMsg.getPopo();
    if (localProofOfPossession.getType() == 1)
    {
      POPOSigningKey localPOPOSigningKey = POPOSigningKey.getInstance(localProofOfPossession.getObject());
      if ((localPOPOSigningKey.getPoposkInput() == null) || (localPOPOSigningKey.getPoposkInput().getSender() != null)) {
        throw new IllegalStateException("no PKMAC present in proof of possession");
      }
      PKMACValue localPKMACValue = localPOPOSigningKey.getPoposkInput().getPublicKeyMAC();
      if (new PKMACValueVerifier(paramPKMACBuilder).isValid(localPKMACValue, paramArrayOfChar, getCertTemplate().getPublicKey())) {
        return verifySignature(paramContentVerifierProvider, localPOPOSigningKey);
      }
      return false;
    }
    throw new IllegalStateException("not Signing Key type of proof of possession");
  }
  
  public CertReqMsg toASN1Structure()
  {
    return this.certReqMsg;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.crmf.CertificateRequestMessage
 * JD-Core Version:    0.7.0.1
 */