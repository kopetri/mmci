package org.spongycastle.cert.cmp;

import java.io.IOException;
import java.io.OutputStream;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.cmp.CMPCertificate;
import org.spongycastle.asn1.cmp.CMPObjectIdentifiers;
import org.spongycastle.asn1.cmp.PBMParameter;
import org.spongycastle.asn1.cmp.PKIBody;
import org.spongycastle.asn1.cmp.PKIHeader;
import org.spongycastle.asn1.cmp.PKIMessage;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.cert.X509CertificateHolder;
import org.spongycastle.cert.crmf.PKMACBuilder;
import org.spongycastle.operator.ContentVerifier;
import org.spongycastle.operator.ContentVerifierProvider;
import org.spongycastle.operator.MacCalculator;
import org.spongycastle.util.Arrays;

public class ProtectedPKIMessage
{
  private PKIMessage pkiMessage;
  
  ProtectedPKIMessage(PKIMessage paramPKIMessage)
  {
    if (paramPKIMessage.getHeader().getProtectionAlg() == null) {
      throw new IllegalArgumentException("PKIMessage not protected");
    }
    this.pkiMessage = paramPKIMessage;
  }
  
  public ProtectedPKIMessage(GeneralPKIMessage paramGeneralPKIMessage)
  {
    if (!paramGeneralPKIMessage.hasProtection()) {
      throw new IllegalArgumentException("PKIMessage not protected");
    }
    this.pkiMessage = paramGeneralPKIMessage.toASN1Structure();
  }
  
  private boolean verifySignature(byte[] paramArrayOfByte, ContentVerifier paramContentVerifier)
    throws IOException
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.pkiMessage.getHeader());
    localASN1EncodableVector.add(this.pkiMessage.getBody());
    OutputStream localOutputStream = paramContentVerifier.getOutputStream();
    localOutputStream.write(new DERSequence(localASN1EncodableVector).getEncoded("DER"));
    localOutputStream.close();
    return paramContentVerifier.verify(paramArrayOfByte);
  }
  
  public PKIBody getBody()
  {
    return this.pkiMessage.getBody();
  }
  
  public X509CertificateHolder[] getCertificates()
  {
    CMPCertificate[] arrayOfCMPCertificate = this.pkiMessage.getExtraCerts();
    X509CertificateHolder[] arrayOfX509CertificateHolder;
    if (arrayOfCMPCertificate == null) {
      arrayOfX509CertificateHolder = new X509CertificateHolder[0];
    }
    for (;;)
    {
      return arrayOfX509CertificateHolder;
      arrayOfX509CertificateHolder = new X509CertificateHolder[arrayOfCMPCertificate.length];
      for (int i = 0; i != arrayOfCMPCertificate.length; i++) {
        arrayOfX509CertificateHolder[i] = new X509CertificateHolder(arrayOfCMPCertificate[i].getX509v3PKCert());
      }
    }
  }
  
  public PKIHeader getHeader()
  {
    return this.pkiMessage.getHeader();
  }
  
  public boolean hasPasswordBasedMacProtection()
  {
    return this.pkiMessage.getHeader().getProtectionAlg().getAlgorithm().equals(CMPObjectIdentifiers.passwordBasedMac);
  }
  
  public PKIMessage toASN1Structure()
  {
    return this.pkiMessage;
  }
  
  public boolean verify(PKMACBuilder paramPKMACBuilder, char[] paramArrayOfChar)
    throws CMPException
  {
    if (!CMPObjectIdentifiers.passwordBasedMac.equals(this.pkiMessage.getHeader().getProtectionAlg().getAlgorithm())) {
      throw new CMPException("protection algorithm not mac based");
    }
    try
    {
      paramPKMACBuilder.setParameters(PBMParameter.getInstance(this.pkiMessage.getHeader().getProtectionAlg().getParameters()));
      MacCalculator localMacCalculator = paramPKMACBuilder.build(paramArrayOfChar);
      OutputStream localOutputStream = localMacCalculator.getOutputStream();
      ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
      localASN1EncodableVector.add(this.pkiMessage.getHeader());
      localASN1EncodableVector.add(this.pkiMessage.getBody());
      localOutputStream.write(new DERSequence(localASN1EncodableVector).getEncoded("DER"));
      localOutputStream.close();
      boolean bool = Arrays.areEqual(localMacCalculator.getMac(), this.pkiMessage.getProtection().getBytes());
      return bool;
    }
    catch (Exception localException)
    {
      throw new CMPException("unable to verify MAC: " + localException.getMessage(), localException);
    }
  }
  
  public boolean verify(ContentVerifierProvider paramContentVerifierProvider)
    throws CMPException
  {
    try
    {
      ContentVerifier localContentVerifier = paramContentVerifierProvider.get(this.pkiMessage.getHeader().getProtectionAlg());
      boolean bool = verifySignature(this.pkiMessage.getProtection().getBytes(), localContentVerifier);
      return bool;
    }
    catch (Exception localException)
    {
      throw new CMPException("unable to verify signature: " + localException.getMessage(), localException);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.cmp.ProtectedPKIMessage
 * JD-Core Version:    0.7.0.1
 */