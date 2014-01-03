package org.spongycastle.cert.crmf;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Null;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.crmf.AttributeTypeAndValue;
import org.spongycastle.asn1.crmf.CertReqMsg;
import org.spongycastle.asn1.crmf.CertRequest;
import org.spongycastle.asn1.crmf.CertTemplate;
import org.spongycastle.asn1.crmf.CertTemplateBuilder;
import org.spongycastle.asn1.crmf.POPOPrivKey;
import org.spongycastle.asn1.crmf.ProofOfPossession;
import org.spongycastle.asn1.crmf.SubsequentMessage;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.asn1.x509.ExtensionsGenerator;
import org.spongycastle.asn1.x509.GeneralName;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.cert.CertIOException;
import org.spongycastle.operator.ContentSigner;

public class CertificateRequestMessageBuilder
{
  private final BigInteger certReqId;
  private List controls;
  private ExtensionsGenerator extGenerator;
  private char[] password;
  private PKMACBuilder pkmacBuilder;
  private ASN1Null popRaVerified;
  private ContentSigner popSigner;
  private POPOPrivKey popoPrivKey;
  private GeneralName sender;
  private CertTemplateBuilder templateBuilder;
  
  public CertificateRequestMessageBuilder(BigInteger paramBigInteger)
  {
    this.certReqId = paramBigInteger;
    this.extGenerator = new ExtensionsGenerator();
    this.templateBuilder = new CertTemplateBuilder();
    this.controls = new ArrayList();
  }
  
  public CertificateRequestMessageBuilder addControl(Control paramControl)
  {
    this.controls.add(paramControl);
    return this;
  }
  
  public CertificateRequestMessageBuilder addExtension(ASN1ObjectIdentifier paramASN1ObjectIdentifier, boolean paramBoolean, ASN1Encodable paramASN1Encodable)
    throws CertIOException
  {
    CRMFUtil.addExtension(this.extGenerator, paramASN1ObjectIdentifier, paramBoolean, paramASN1Encodable);
    return this;
  }
  
  public CertificateRequestMessageBuilder addExtension(ASN1ObjectIdentifier paramASN1ObjectIdentifier, boolean paramBoolean, byte[] paramArrayOfByte)
  {
    this.extGenerator.addExtension(paramASN1ObjectIdentifier, paramBoolean, paramArrayOfByte);
    return this;
  }
  
  public CertificateRequestMessage build()
    throws CRMFException
  {
    ASN1EncodableVector localASN1EncodableVector1 = new ASN1EncodableVector();
    localASN1EncodableVector1.add(new ASN1Integer(this.certReqId));
    if (!this.extGenerator.isEmpty()) {
      this.templateBuilder.setExtensions(this.extGenerator.generate());
    }
    localASN1EncodableVector1.add(this.templateBuilder.build());
    if (!this.controls.isEmpty())
    {
      ASN1EncodableVector localASN1EncodableVector2 = new ASN1EncodableVector();
      Iterator localIterator = this.controls.iterator();
      while (localIterator.hasNext())
      {
        Control localControl = (Control)localIterator.next();
        localASN1EncodableVector2.add(new AttributeTypeAndValue(localControl.getType(), localControl.getValue()));
      }
      localASN1EncodableVector1.add(new DERSequence(localASN1EncodableVector2));
    }
    CertRequest localCertRequest = CertRequest.getInstance(new DERSequence(localASN1EncodableVector1));
    ASN1EncodableVector localASN1EncodableVector3 = new ASN1EncodableVector();
    localASN1EncodableVector3.add(localCertRequest);
    ProofOfPossessionSigningKeyBuilder localProofOfPossessionSigningKeyBuilder;
    if (this.popSigner != null)
    {
      CertTemplate localCertTemplate = localCertRequest.getCertTemplate();
      if ((localCertTemplate.getSubject() == null) || (localCertTemplate.getPublicKey() == null))
      {
        localProofOfPossessionSigningKeyBuilder = new ProofOfPossessionSigningKeyBuilder(localCertRequest.getCertTemplate().getPublicKey());
        if (this.sender != null)
        {
          localProofOfPossessionSigningKeyBuilder.setSender(this.sender);
          localASN1EncodableVector3.add(new ProofOfPossession(localProofOfPossessionSigningKeyBuilder.build(this.popSigner)));
        }
      }
    }
    for (;;)
    {
      return new CertificateRequestMessage(CertReqMsg.getInstance(new DERSequence(localASN1EncodableVector3)));
      localProofOfPossessionSigningKeyBuilder.setPublicKeyMac(new PKMACValueGenerator(this.pkmacBuilder), this.password);
      break;
      localASN1EncodableVector3.add(new ProofOfPossession(new ProofOfPossessionSigningKeyBuilder(localCertRequest).build(this.popSigner)));
      continue;
      if (this.popoPrivKey != null) {
        localASN1EncodableVector3.add(new ProofOfPossession(2, this.popoPrivKey));
      } else if (this.popRaVerified != null) {
        localASN1EncodableVector3.add(new ProofOfPossession());
      }
    }
  }
  
  public CertificateRequestMessageBuilder setAuthInfoPKMAC(PKMACBuilder paramPKMACBuilder, char[] paramArrayOfChar)
  {
    this.pkmacBuilder = paramPKMACBuilder;
    this.password = paramArrayOfChar;
    return this;
  }
  
  public CertificateRequestMessageBuilder setAuthInfoSender(X500Name paramX500Name)
  {
    return setAuthInfoSender(new GeneralName(paramX500Name));
  }
  
  public CertificateRequestMessageBuilder setAuthInfoSender(GeneralName paramGeneralName)
  {
    this.sender = paramGeneralName;
    return this;
  }
  
  public CertificateRequestMessageBuilder setIssuer(X500Name paramX500Name)
  {
    if (paramX500Name != null) {
      this.templateBuilder.setIssuer(paramX500Name);
    }
    return this;
  }
  
  public CertificateRequestMessageBuilder setProofOfPossessionRaVerified()
  {
    if ((this.popSigner != null) || (this.popoPrivKey != null)) {
      throw new IllegalStateException("only one proof of possession allowed");
    }
    this.popRaVerified = DERNull.INSTANCE;
    return this;
  }
  
  public CertificateRequestMessageBuilder setProofOfPossessionSigningKeySigner(ContentSigner paramContentSigner)
  {
    if ((this.popoPrivKey != null) || (this.popRaVerified != null)) {
      throw new IllegalStateException("only one proof of possession allowed");
    }
    this.popSigner = paramContentSigner;
    return this;
  }
  
  public CertificateRequestMessageBuilder setProofOfPossessionSubsequentMessage(SubsequentMessage paramSubsequentMessage)
  {
    if ((this.popSigner != null) || (this.popRaVerified != null)) {
      throw new IllegalStateException("only one proof of possession allowed");
    }
    this.popoPrivKey = new POPOPrivKey(paramSubsequentMessage);
    return this;
  }
  
  public CertificateRequestMessageBuilder setPublicKey(SubjectPublicKeyInfo paramSubjectPublicKeyInfo)
  {
    if (paramSubjectPublicKeyInfo != null) {
      this.templateBuilder.setPublicKey(paramSubjectPublicKeyInfo);
    }
    return this;
  }
  
  public CertificateRequestMessageBuilder setSerialNumber(BigInteger paramBigInteger)
  {
    if (paramBigInteger != null) {
      this.templateBuilder.setSerialNumber(new ASN1Integer(paramBigInteger));
    }
    return this;
  }
  
  public CertificateRequestMessageBuilder setSubject(X500Name paramX500Name)
  {
    if (paramX500Name != null) {
      this.templateBuilder.setSubject(paramX500Name);
    }
    return this;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.crmf.CertificateRequestMessageBuilder
 * JD-Core Version:    0.7.0.1
 */