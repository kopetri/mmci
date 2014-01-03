package org.spongycastle.cms;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.PublicKey;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.DERObjectIdentifier;
import org.spongycastle.asn1.DERSet;
import org.spongycastle.asn1.cms.Attribute;
import org.spongycastle.asn1.cms.AttributeTable;
import org.spongycastle.asn1.cms.CMSAttributes;
import org.spongycastle.asn1.cms.IssuerAndSerialNumber;
import org.spongycastle.asn1.cms.SignerIdentifier;
import org.spongycastle.asn1.cms.SignerInfo;
import org.spongycastle.asn1.cms.Time;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.DigestInfo;
import org.spongycastle.cert.X509CertificateHolder;
import org.spongycastle.operator.ContentVerifier;
import org.spongycastle.operator.DigestCalculator;
import org.spongycastle.operator.OperatorCreationException;
import org.spongycastle.operator.RawContentVerifier;
import org.spongycastle.util.Arrays;

public class SignerInformation
{
  private CMSProcessable content;
  private ASN1ObjectIdentifier contentType;
  private AlgorithmIdentifier digestAlgorithm;
  private AlgorithmIdentifier encryptionAlgorithm;
  private SignerInfo info;
  private boolean isCounterSignature;
  private byte[] resultDigest;
  private SignerId sid;
  private byte[] signature;
  private final ASN1Set signedAttributeSet;
  private AttributeTable signedAttributeValues;
  private final ASN1Set unsignedAttributeSet;
  private AttributeTable unsignedAttributeValues;
  
  SignerInformation(SignerInfo paramSignerInfo, ASN1ObjectIdentifier paramASN1ObjectIdentifier, CMSProcessable paramCMSProcessable, byte[] paramArrayOfByte)
  {
    this.info = paramSignerInfo;
    this.contentType = paramASN1ObjectIdentifier;
    boolean bool;
    SignerIdentifier localSignerIdentifier;
    if (paramASN1ObjectIdentifier == null)
    {
      bool = true;
      this.isCounterSignature = bool;
      localSignerIdentifier = paramSignerInfo.getSID();
      if (!localSignerIdentifier.isTagged()) {
        break label124;
      }
    }
    label124:
    IssuerAndSerialNumber localIssuerAndSerialNumber;
    for (this.sid = new SignerId(ASN1OctetString.getInstance(localSignerIdentifier.getId()).getOctets());; this.sid = new SignerId(localIssuerAndSerialNumber.getName(), localIssuerAndSerialNumber.getSerialNumber().getValue()))
    {
      this.digestAlgorithm = paramSignerInfo.getDigestAlgorithm();
      this.signedAttributeSet = paramSignerInfo.getAuthenticatedAttributes();
      this.unsignedAttributeSet = paramSignerInfo.getUnauthenticatedAttributes();
      this.encryptionAlgorithm = paramSignerInfo.getDigestEncryptionAlgorithm();
      this.signature = paramSignerInfo.getEncryptedDigest().getOctets();
      this.content = paramCMSProcessable;
      this.resultDigest = paramArrayOfByte;
      return;
      bool = false;
      break;
      localIssuerAndSerialNumber = IssuerAndSerialNumber.getInstance(localSignerIdentifier.getId());
    }
  }
  
  public static SignerInformation addCounterSigners(SignerInformation paramSignerInformation, SignerInformationStore paramSignerInformationStore)
  {
    SignerInfo localSignerInfo = paramSignerInformation.info;
    AttributeTable localAttributeTable = paramSignerInformation.getUnsignedAttributes();
    if (localAttributeTable != null) {}
    ASN1EncodableVector localASN1EncodableVector2;
    for (ASN1EncodableVector localASN1EncodableVector1 = localAttributeTable.toASN1EncodableVector();; localASN1EncodableVector1 = new ASN1EncodableVector())
    {
      localASN1EncodableVector2 = new ASN1EncodableVector();
      Iterator localIterator = paramSignerInformationStore.getSigners().iterator();
      while (localIterator.hasNext()) {
        localASN1EncodableVector2.add(((SignerInformation)localIterator.next()).toSignerInfo());
      }
    }
    localASN1EncodableVector1.add(new Attribute(CMSAttributes.counterSignature, new DERSet(localASN1EncodableVector2)));
    return new SignerInformation(new SignerInfo(localSignerInfo.getSID(), localSignerInfo.getDigestAlgorithm(), localSignerInfo.getAuthenticatedAttributes(), localSignerInfo.getDigestEncryptionAlgorithm(), localSignerInfo.getEncryptedDigest(), new DERSet(localASN1EncodableVector1)), paramSignerInformation.contentType, paramSignerInformation.content, null);
  }
  
  /* Error */
  private boolean doVerify(PublicKey paramPublicKey, Provider paramProvider)
    throws CMSException, NoSuchAlgorithmException
  {
    // Byte code:
    //   0: aload_2
    //   1: ifnull +69 -> 70
    //   4: aload_2
    //   5: invokevirtual 201	java/security/Provider:getName	()Ljava/lang/String;
    //   8: ldc 203
    //   10: invokevirtual 209	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   13: ifne +37 -> 50
    //   16: new 211	org/spongycastle/cms/jcajce/JcaSignerInfoVerifierBuilder
    //   19: dup
    //   20: new 213	org/spongycastle/operator/jcajce/JcaDigestCalculatorProviderBuilder
    //   23: dup
    //   24: invokespecial 214	org/spongycastle/operator/jcajce/JcaDigestCalculatorProviderBuilder:<init>	()V
    //   27: invokevirtual 218	org/spongycastle/operator/jcajce/JcaDigestCalculatorProviderBuilder:build	()Lorg/spongycastle/operator/DigestCalculatorProvider;
    //   30: invokespecial 221	org/spongycastle/cms/jcajce/JcaSignerInfoVerifierBuilder:<init>	(Lorg/spongycastle/operator/DigestCalculatorProvider;)V
    //   33: aload_2
    //   34: invokevirtual 225	org/spongycastle/cms/jcajce/JcaSignerInfoVerifierBuilder:setProvider	(Ljava/security/Provider;)Lorg/spongycastle/cms/jcajce/JcaSignerInfoVerifierBuilder;
    //   37: aload_1
    //   38: invokevirtual 228	org/spongycastle/cms/jcajce/JcaSignerInfoVerifierBuilder:build	(Ljava/security/PublicKey;)Lorg/spongycastle/cms/SignerInformationVerifier;
    //   41: astore 5
    //   43: aload_0
    //   44: aload 5
    //   46: invokespecial 231	org/spongycastle/cms/SignerInformation:doVerify	(Lorg/spongycastle/cms/SignerInformationVerifier;)Z
    //   49: ireturn
    //   50: new 233	org/spongycastle/cms/jcajce/JcaSimpleSignerInfoVerifierBuilder
    //   53: dup
    //   54: invokespecial 234	org/spongycastle/cms/jcajce/JcaSimpleSignerInfoVerifierBuilder:<init>	()V
    //   57: aload_2
    //   58: invokevirtual 237	org/spongycastle/cms/jcajce/JcaSimpleSignerInfoVerifierBuilder:setProvider	(Ljava/security/Provider;)Lorg/spongycastle/cms/jcajce/JcaSimpleSignerInfoVerifierBuilder;
    //   61: aload_1
    //   62: invokevirtual 238	org/spongycastle/cms/jcajce/JcaSimpleSignerInfoVerifierBuilder:build	(Ljava/security/PublicKey;)Lorg/spongycastle/cms/SignerInformationVerifier;
    //   65: astore 5
    //   67: goto -24 -> 43
    //   70: new 233	org/spongycastle/cms/jcajce/JcaSimpleSignerInfoVerifierBuilder
    //   73: dup
    //   74: invokespecial 234	org/spongycastle/cms/jcajce/JcaSimpleSignerInfoVerifierBuilder:<init>	()V
    //   77: aload_1
    //   78: invokevirtual 238	org/spongycastle/cms/jcajce/JcaSimpleSignerInfoVerifierBuilder:build	(Ljava/security/PublicKey;)Lorg/spongycastle/cms/SignerInformationVerifier;
    //   81: astore 4
    //   83: aload 4
    //   85: astore 5
    //   87: goto -44 -> 43
    //   90: astore_3
    //   91: new 192	org/spongycastle/cms/CMSException
    //   94: dup
    //   95: new 240	java/lang/StringBuilder
    //   98: dup
    //   99: ldc 242
    //   101: invokespecial 245	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   104: aload_3
    //   105: invokevirtual 248	org/spongycastle/operator/OperatorCreationException:getMessage	()Ljava/lang/String;
    //   108: invokevirtual 252	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   111: invokevirtual 255	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   114: aload_3
    //   115: invokespecial 258	org/spongycastle/cms/CMSException:<init>	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   118: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	119	0	this	SignerInformation
    //   0	119	1	paramPublicKey	PublicKey
    //   0	119	2	paramProvider	Provider
    //   90	25	3	localOperatorCreationException	OperatorCreationException
    //   81	3	4	localSignerInformationVerifier	SignerInformationVerifier
    //   41	45	5	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   4	43	90	org/spongycastle/operator/OperatorCreationException
    //   43	50	90	org/spongycastle/operator/OperatorCreationException
    //   50	67	90	org/spongycastle/operator/OperatorCreationException
    //   70	83	90	org/spongycastle/operator/OperatorCreationException
  }
  
  private boolean doVerify(SignerInformationVerifier paramSignerInformationVerifier)
    throws CMSException
  {
    String str = CMSSignedHelper.INSTANCE.getEncryptionAlgName(getEncryptionAlgOID());
    ASN1Primitive localASN1Primitive1;
    try
    {
      DigestCalculator localDigestCalculator;
      if (this.resultDigest == null)
      {
        localDigestCalculator = paramSignerInformationVerifier.getDigestCalculator(getDigestAlgorithmID());
        if (this.content == null) {
          break label113;
        }
        localOutputStream2 = localDigestCalculator.getOutputStream();
        this.content.write(localOutputStream2);
        localOutputStream2.close();
      }
      label113:
      while (this.signedAttributeSet != null)
      {
        OutputStream localOutputStream2;
        this.resultDigest = localDigestCalculator.getDigest();
        localASN1Primitive1 = getSingleValuedSignedAttribute(CMSAttributes.contentType, "content-type");
        if (localASN1Primitive1 != null) {
          break;
        }
        if ((this.isCounterSignature) || (this.signedAttributeSet == null)) {
          break label247;
        }
        throw new CMSException("The content-type attribute type MUST be present whenever signed attributes are present in signed-data");
      }
      throw new CMSException("data not encapsulated in signature - use detached constructor.");
    }
    catch (IOException localIOException1)
    {
      CMSException localCMSException2 = new CMSException("can't process mime object to create signature.", localIOException1);
      throw localCMSException2;
    }
    catch (OperatorCreationException localOperatorCreationException1)
    {
      CMSException localCMSException1 = new CMSException("can't create digest calculator: " + localOperatorCreationException1.getMessage(), localOperatorCreationException1);
      throw localCMSException1;
    }
    if (this.isCounterSignature) {
      throw new CMSException("[For counter signatures,] the signedAttributes field MUST NOT contain a content-type attribute");
    }
    if (!(localASN1Primitive1 instanceof DERObjectIdentifier)) {
      throw new CMSException("content-type attribute value not of ASN.1 type 'OBJECT IDENTIFIER'");
    }
    if (!((DERObjectIdentifier)localASN1Primitive1).equals(this.contentType)) {
      throw new CMSException("content-type attribute value does not match eContentType");
    }
    label247:
    ASN1Primitive localASN1Primitive2 = getSingleValuedSignedAttribute(CMSAttributes.messageDigest, "message-digest");
    if (localASN1Primitive2 == null)
    {
      if (this.signedAttributeSet != null) {
        throw new CMSException("the message-digest signed attribute type MUST be present when there are any signed attributes present");
      }
    }
    else
    {
      if (!(localASN1Primitive2 instanceof ASN1OctetString)) {
        throw new CMSException("message-digest attribute value not of ASN.1 type 'OCTET STRING'");
      }
      ASN1OctetString localASN1OctetString = (ASN1OctetString)localASN1Primitive2;
      if (!Arrays.constantTimeAreEqual(this.resultDigest, localASN1OctetString.getOctets())) {
        throw new CMSSignerDigestMismatchException("message-digest attribute value does not match calculated value");
      }
    }
    AttributeTable localAttributeTable1 = getSignedAttributes();
    if ((localAttributeTable1 != null) && (localAttributeTable1.getAll(CMSAttributes.counterSignature).size() > 0)) {
      throw new CMSException("A countersignature attribute MUST NOT be a signed attribute");
    }
    AttributeTable localAttributeTable2 = getUnsignedAttributes();
    if (localAttributeTable2 != null)
    {
      ASN1EncodableVector localASN1EncodableVector = localAttributeTable2.getAll(CMSAttributes.counterSignature);
      for (int i = 0; i < localASN1EncodableVector.size(); i++) {
        if (((Attribute)localASN1EncodableVector.get(i)).getAttrValues().size() <= 0) {
          throw new CMSException("A countersignature attribute MUST contain at least one AttributeValue");
        }
      }
    }
    try
    {
      localContentVerifier = paramSignerInformationVerifier.getContentVerifier(this.encryptionAlgorithm, this.info.getDigestAlgorithm());
      localOutputStream1 = localContentVerifier.getOutputStream();
      if (this.signedAttributeSet == null) {
        if (this.resultDigest != null)
        {
          if ((localContentVerifier instanceof RawContentVerifier))
          {
            RawContentVerifier localRawContentVerifier = (RawContentVerifier)localContentVerifier;
            if (str.equals("RSA")) {
              return localRawContentVerifier.verify(new DigestInfo(this.digestAlgorithm, this.resultDigest).getEncoded("DER"), getSignature());
            }
            return localRawContentVerifier.verify(this.resultDigest, getSignature());
          }
          throw new CMSException("verifier unable to process raw signature");
        }
      }
    }
    catch (IOException localIOException2)
    {
      ContentVerifier localContentVerifier;
      OutputStream localOutputStream1;
      CMSException localCMSException4 = new CMSException("can't process mime object to create signature.", localIOException2);
      throw localCMSException4;
      if (this.content != null) {
        this.content.write(localOutputStream1);
      }
      for (;;)
      {
        localOutputStream1.close();
        return localContentVerifier.verify(getSignature());
        localOutputStream1.write(getEncodedSignedAttributes());
      }
    }
    catch (OperatorCreationException localOperatorCreationException2)
    {
      CMSException localCMSException3 = new CMSException("can't create content verifier: " + localOperatorCreationException2.getMessage(), localOperatorCreationException2);
      throw localCMSException3;
    }
  }
  
  private byte[] encodeObj(ASN1Encodable paramASN1Encodable)
    throws IOException
  {
    if (paramASN1Encodable != null) {
      return paramASN1Encodable.toASN1Primitive().getEncoded();
    }
    return null;
  }
  
  private Time getSigningTime()
    throws CMSException
  {
    ASN1Primitive localASN1Primitive = getSingleValuedSignedAttribute(CMSAttributes.signingTime, "signing-time");
    if (localASN1Primitive == null) {
      return null;
    }
    try
    {
      Time localTime = Time.getInstance(localASN1Primitive);
      return localTime;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      throw new CMSException("signing-time attribute value not a valid 'Time' structure");
    }
  }
  
  private ASN1Primitive getSingleValuedSignedAttribute(ASN1ObjectIdentifier paramASN1ObjectIdentifier, String paramString)
    throws CMSException
  {
    AttributeTable localAttributeTable1 = getUnsignedAttributes();
    if ((localAttributeTable1 != null) && (localAttributeTable1.getAll(paramASN1ObjectIdentifier).size() > 0)) {
      throw new CMSException("The " + paramString + " attribute MUST NOT be an unsigned attribute");
    }
    AttributeTable localAttributeTable2 = getSignedAttributes();
    if (localAttributeTable2 == null) {
      return null;
    }
    ASN1EncodableVector localASN1EncodableVector = localAttributeTable2.getAll(paramASN1ObjectIdentifier);
    switch (localASN1EncodableVector.size())
    {
    case 0: 
    default: 
      throw new CMSException("The SignedAttributes in a signerInfo MUST NOT include multiple instances of the " + paramString + " attribute");
    }
    ASN1Set localASN1Set = ((Attribute)localASN1EncodableVector.get(0)).getAttrValues();
    if (localASN1Set.size() != 1) {
      throw new CMSException("A " + paramString + " attribute MUST have a single attribute value");
    }
    return localASN1Set.getObjectAt(0).toASN1Primitive();
  }
  
  public static SignerInformation replaceUnsignedAttributes(SignerInformation paramSignerInformation, AttributeTable paramAttributeTable)
  {
    SignerInfo localSignerInfo = paramSignerInformation.info;
    DERSet localDERSet = null;
    if (paramAttributeTable != null) {
      localDERSet = new DERSet(paramAttributeTable.toASN1EncodableVector());
    }
    return new SignerInformation(new SignerInfo(localSignerInfo.getSID(), localSignerInfo.getDigestAlgorithm(), localSignerInfo.getAuthenticatedAttributes(), localSignerInfo.getDigestEncryptionAlgorithm(), localSignerInfo.getEncryptedDigest(), localDERSet), paramSignerInformation.contentType, paramSignerInformation.content, null);
  }
  
  public byte[] getContentDigest()
  {
    if (this.resultDigest == null) {
      throw new IllegalStateException("method can only be called after verify.");
    }
    return (byte[])this.resultDigest.clone();
  }
  
  public ASN1ObjectIdentifier getContentType()
  {
    return this.contentType;
  }
  
  public SignerInformationStore getCounterSignatures()
  {
    AttributeTable localAttributeTable = getUnsignedAttributes();
    if (localAttributeTable == null) {
      return new SignerInformationStore(new ArrayList(0));
    }
    ArrayList localArrayList = new ArrayList();
    ASN1EncodableVector localASN1EncodableVector = localAttributeTable.getAll(CMSAttributes.counterSignature);
    for (int i = 0; i < localASN1EncodableVector.size(); i++)
    {
      ASN1Set localASN1Set = ((Attribute)localASN1EncodableVector.get(i)).getAttrValues();
      localASN1Set.size();
      Enumeration localEnumeration = localASN1Set.getObjects();
      while (localEnumeration.hasMoreElements()) {
        localArrayList.add(new SignerInformation(SignerInfo.getInstance(localEnumeration.nextElement()), null, new CMSProcessableByteArray(getSignature()), null));
      }
    }
    return new SignerInformationStore(localArrayList);
  }
  
  public String getDigestAlgOID()
  {
    return this.digestAlgorithm.getObjectId().getId();
  }
  
  public byte[] getDigestAlgParams()
  {
    try
    {
      byte[] arrayOfByte = encodeObj(this.digestAlgorithm.getParameters());
      return arrayOfByte;
    }
    catch (Exception localException)
    {
      throw new RuntimeException("exception getting digest parameters " + localException);
    }
  }
  
  public AlgorithmIdentifier getDigestAlgorithmID()
  {
    return this.digestAlgorithm;
  }
  
  public byte[] getEncodedSignedAttributes()
    throws IOException
  {
    if (this.signedAttributeSet != null) {
      return this.signedAttributeSet.getEncoded();
    }
    return null;
  }
  
  public String getEncryptionAlgOID()
  {
    return this.encryptionAlgorithm.getObjectId().getId();
  }
  
  public byte[] getEncryptionAlgParams()
  {
    try
    {
      byte[] arrayOfByte = encodeObj(this.encryptionAlgorithm.getParameters());
      return arrayOfByte;
    }
    catch (Exception localException)
    {
      throw new RuntimeException("exception getting encryption parameters " + localException);
    }
  }
  
  public SignerId getSID()
  {
    return this.sid;
  }
  
  public byte[] getSignature()
  {
    return (byte[])this.signature.clone();
  }
  
  public AttributeTable getSignedAttributes()
  {
    if ((this.signedAttributeSet != null) && (this.signedAttributeValues == null)) {
      this.signedAttributeValues = new AttributeTable(this.signedAttributeSet);
    }
    return this.signedAttributeValues;
  }
  
  public AttributeTable getUnsignedAttributes()
  {
    if ((this.unsignedAttributeSet != null) && (this.unsignedAttributeValues == null)) {
      this.unsignedAttributeValues = new AttributeTable(this.unsignedAttributeSet);
    }
    return this.unsignedAttributeValues;
  }
  
  public int getVersion()
  {
    return this.info.getVersion().getValue().intValue();
  }
  
  public boolean isCounterSignature()
  {
    return this.isCounterSignature;
  }
  
  public SignerInfo toASN1Structure()
  {
    return this.info;
  }
  
  public SignerInfo toSignerInfo()
  {
    return this.info;
  }
  
  public boolean verify(PublicKey paramPublicKey, String paramString)
    throws NoSuchAlgorithmException, NoSuchProviderException, CMSException
  {
    return verify(paramPublicKey, CMSUtils.getProvider(paramString));
  }
  
  public boolean verify(PublicKey paramPublicKey, Provider paramProvider)
    throws NoSuchAlgorithmException, NoSuchProviderException, CMSException
  {
    getSigningTime();
    return doVerify(paramPublicKey, paramProvider);
  }
  
  public boolean verify(X509Certificate paramX509Certificate, String paramString)
    throws NoSuchAlgorithmException, NoSuchProviderException, CertificateExpiredException, CertificateNotYetValidException, CMSException
  {
    return verify(paramX509Certificate, CMSUtils.getProvider(paramString));
  }
  
  public boolean verify(X509Certificate paramX509Certificate, Provider paramProvider)
    throws NoSuchAlgorithmException, CertificateExpiredException, CertificateNotYetValidException, CMSException
  {
    Time localTime = getSigningTime();
    if (localTime != null) {
      paramX509Certificate.checkValidity(localTime.getDate());
    }
    return doVerify(paramX509Certificate.getPublicKey(), paramProvider);
  }
  
  public boolean verify(SignerInformationVerifier paramSignerInformationVerifier)
    throws CMSException
  {
    Time localTime = getSigningTime();
    if ((paramSignerInformationVerifier.hasAssociatedCertificate()) && (localTime != null) && (!paramSignerInformationVerifier.getAssociatedCertificate().isValidOn(localTime.getDate()))) {
      throw new CMSVerifierCertificateNotValidException("verifier not valid at signingTime");
    }
    return doVerify(paramSignerInformationVerifier);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.SignerInformation
 * JD-Core Version:    0.7.0.1
 */