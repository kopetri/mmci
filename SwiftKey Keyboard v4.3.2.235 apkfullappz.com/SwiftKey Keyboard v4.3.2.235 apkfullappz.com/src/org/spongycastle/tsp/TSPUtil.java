package org.spongycastle.tsp;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.cms.Attribute;
import org.spongycastle.asn1.cms.AttributeTable;
import org.spongycastle.asn1.cms.ContentInfo;
import org.spongycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.spongycastle.asn1.nist.NISTObjectIdentifiers;
import org.spongycastle.asn1.oiw.OIWObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import org.spongycastle.asn1.x509.Certificate;
import org.spongycastle.asn1.x509.ExtendedKeyUsage;
import org.spongycastle.asn1.x509.Extension;
import org.spongycastle.asn1.x509.Extensions;
import org.spongycastle.asn1.x509.ExtensionsGenerator;
import org.spongycastle.asn1.x509.KeyPurposeId;
import org.spongycastle.asn1.x509.X509Extensions;
import org.spongycastle.cert.X509CertificateHolder;
import org.spongycastle.cms.SignerInformation;
import org.spongycastle.operator.DigestCalculator;
import org.spongycastle.operator.DigestCalculatorProvider;
import org.spongycastle.operator.OperatorCreationException;

public class TSPUtil
{
  private static List EMPTY_LIST;
  private static Set EMPTY_SET = Collections.unmodifiableSet(new HashSet());
  private static final Map digestLengths;
  private static final Map digestNames;
  
  static
  {
    EMPTY_LIST = Collections.unmodifiableList(new ArrayList());
    digestLengths = new HashMap();
    digestNames = new HashMap();
    digestLengths.put(PKCSObjectIdentifiers.md5.getId(), new Integer(16));
    digestLengths.put(OIWObjectIdentifiers.idSHA1.getId(), new Integer(20));
    digestLengths.put(NISTObjectIdentifiers.id_sha224.getId(), new Integer(28));
    digestLengths.put(NISTObjectIdentifiers.id_sha256.getId(), new Integer(32));
    digestLengths.put(NISTObjectIdentifiers.id_sha384.getId(), new Integer(48));
    digestLengths.put(NISTObjectIdentifiers.id_sha512.getId(), new Integer(64));
    digestLengths.put(TeleTrusTObjectIdentifiers.ripemd128.getId(), new Integer(16));
    digestLengths.put(TeleTrusTObjectIdentifiers.ripemd160.getId(), new Integer(20));
    digestLengths.put(TeleTrusTObjectIdentifiers.ripemd256.getId(), new Integer(32));
    digestLengths.put(CryptoProObjectIdentifiers.gostR3411.getId(), new Integer(32));
    digestNames.put(PKCSObjectIdentifiers.md5.getId(), "MD5");
    digestNames.put(OIWObjectIdentifiers.idSHA1.getId(), "SHA1");
    digestNames.put(NISTObjectIdentifiers.id_sha224.getId(), "SHA224");
    digestNames.put(NISTObjectIdentifiers.id_sha256.getId(), "SHA256");
    digestNames.put(NISTObjectIdentifiers.id_sha384.getId(), "SHA384");
    digestNames.put(NISTObjectIdentifiers.id_sha512.getId(), "SHA512");
    digestNames.put(PKCSObjectIdentifiers.sha1WithRSAEncryption.getId(), "SHA1");
    digestNames.put(PKCSObjectIdentifiers.sha224WithRSAEncryption.getId(), "SHA224");
    digestNames.put(PKCSObjectIdentifiers.sha256WithRSAEncryption.getId(), "SHA256");
    digestNames.put(PKCSObjectIdentifiers.sha384WithRSAEncryption.getId(), "SHA384");
    digestNames.put(PKCSObjectIdentifiers.sha512WithRSAEncryption.getId(), "SHA512");
    digestNames.put(TeleTrusTObjectIdentifiers.ripemd128.getId(), "RIPEMD128");
    digestNames.put(TeleTrusTObjectIdentifiers.ripemd160.getId(), "RIPEMD160");
    digestNames.put(TeleTrusTObjectIdentifiers.ripemd256.getId(), "RIPEMD256");
    digestNames.put(CryptoProObjectIdentifiers.gostR3411.getId(), "GOST3411");
  }
  
  static void addExtension(ExtensionsGenerator paramExtensionsGenerator, ASN1ObjectIdentifier paramASN1ObjectIdentifier, boolean paramBoolean, ASN1Encodable paramASN1Encodable)
    throws TSPIOException
  {
    try
    {
      paramExtensionsGenerator.addExtension(paramASN1ObjectIdentifier, paramBoolean, paramASN1Encodable);
      return;
    }
    catch (IOException localIOException)
    {
      throw new TSPIOException("cannot encode extension: " + localIOException.getMessage(), localIOException);
    }
  }
  
  static MessageDigest createDigestInstance(String paramString, Provider paramProvider)
    throws NoSuchAlgorithmException
  {
    String str = getDigestAlgName(paramString);
    if (paramProvider != null) {
      try
      {
        MessageDigest localMessageDigest = MessageDigest.getInstance(str, paramProvider);
        return localMessageDigest;
      }
      catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {}
    }
    return MessageDigest.getInstance(str);
  }
  
  static Set getCriticalExtensionOIDs(X509Extensions paramX509Extensions)
  {
    if (paramX509Extensions == null) {
      return EMPTY_SET;
    }
    return Collections.unmodifiableSet(new HashSet(java.util.Arrays.asList(paramX509Extensions.getCriticalExtensionOIDs())));
  }
  
  static String getDigestAlgName(String paramString)
  {
    String str = (String)digestNames.get(paramString);
    if (str != null) {
      return str;
    }
    return paramString;
  }
  
  static int getDigestLength(String paramString)
    throws TSPException
  {
    Integer localInteger = (Integer)digestLengths.get(paramString);
    if (localInteger != null) {
      return localInteger.intValue();
    }
    throw new TSPException("digest algorithm cannot be found.");
  }
  
  static List getExtensionOIDs(Extensions paramExtensions)
  {
    if (paramExtensions == null) {
      return EMPTY_LIST;
    }
    return Collections.unmodifiableList(java.util.Arrays.asList(paramExtensions.getExtensionOIDs()));
  }
  
  static Set getNonCriticalExtensionOIDs(X509Extensions paramX509Extensions)
  {
    if (paramX509Extensions == null) {
      return EMPTY_SET;
    }
    return Collections.unmodifiableSet(new HashSet(java.util.Arrays.asList(paramX509Extensions.getNonCriticalExtensionOIDs())));
  }
  
  public static Collection getSignatureTimestamps(SignerInformation paramSignerInformation, Provider paramProvider)
    throws TSPValidationException
  {
    ArrayList localArrayList = new ArrayList();
    AttributeTable localAttributeTable = paramSignerInformation.getUnsignedAttributes();
    if (localAttributeTable != null)
    {
      ASN1EncodableVector localASN1EncodableVector = localAttributeTable.getAll(PKCSObjectIdentifiers.id_aa_signatureTimeStampToken);
      for (int i = 0; i < localASN1EncodableVector.size(); i++)
      {
        ASN1Set localASN1Set = ((Attribute)localASN1EncodableVector.get(i)).getAttrValues();
        int j = 0;
        while (j < localASN1Set.size()) {
          try
          {
            localTimeStampToken = new TimeStampToken(ContentInfo.getInstance(localASN1Set.getObjectAt(j)));
            TimeStampTokenInfo localTimeStampTokenInfo = localTimeStampToken.getTimeStampInfo();
            if (!org.spongycastle.util.Arrays.constantTimeAreEqual(createDigestInstance(localTimeStampTokenInfo.getMessageImprintAlgOID().getId(), paramProvider).digest(paramSignerInformation.getSignature()), localTimeStampTokenInfo.getMessageImprintDigest())) {
              throw new TSPValidationException("Incorrect digest in message imprint");
            }
          }
          catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
          {
            TimeStampToken localTimeStampToken;
            throw new TSPValidationException("Unknown hash algorithm specified in timestamp");
            localArrayList.add(localTimeStampToken);
            j++;
          }
          catch (Exception localException)
          {
            throw new TSPValidationException("Timestamp could not be parsed");
          }
        }
      }
    }
    return localArrayList;
  }
  
  public static Collection getSignatureTimestamps(SignerInformation paramSignerInformation, DigestCalculatorProvider paramDigestCalculatorProvider)
    throws TSPValidationException
  {
    ArrayList localArrayList = new ArrayList();
    AttributeTable localAttributeTable = paramSignerInformation.getUnsignedAttributes();
    if (localAttributeTable != null)
    {
      ASN1EncodableVector localASN1EncodableVector = localAttributeTable.getAll(PKCSObjectIdentifiers.id_aa_signatureTimeStampToken);
      for (int i = 0; i < localASN1EncodableVector.size(); i++)
      {
        ASN1Set localASN1Set = ((Attribute)localASN1EncodableVector.get(i)).getAttrValues();
        int j = 0;
        while (j < localASN1Set.size()) {
          try
          {
            localTimeStampToken = new TimeStampToken(ContentInfo.getInstance(localASN1Set.getObjectAt(j)));
            TimeStampTokenInfo localTimeStampTokenInfo = localTimeStampToken.getTimeStampInfo();
            DigestCalculator localDigestCalculator = paramDigestCalculatorProvider.get(localTimeStampTokenInfo.getHashAlgorithm());
            OutputStream localOutputStream = localDigestCalculator.getOutputStream();
            localOutputStream.write(paramSignerInformation.getSignature());
            localOutputStream.close();
            if (!org.spongycastle.util.Arrays.constantTimeAreEqual(localDigestCalculator.getDigest(), localTimeStampTokenInfo.getMessageImprintDigest())) {
              throw new TSPValidationException("Incorrect digest in message imprint");
            }
          }
          catch (OperatorCreationException localOperatorCreationException)
          {
            TimeStampToken localTimeStampToken;
            throw new TSPValidationException("Unknown hash algorithm specified in timestamp");
            localArrayList.add(localTimeStampToken);
            j++;
          }
          catch (Exception localException)
          {
            throw new TSPValidationException("Timestamp could not be parsed");
          }
        }
      }
    }
    return localArrayList;
  }
  
  public static void validateCertificate(X509Certificate paramX509Certificate)
    throws TSPValidationException
  {
    if (paramX509Certificate.getVersion() != 3) {
      throw new IllegalArgumentException("Certificate must have an ExtendedKeyUsage extension.");
    }
    byte[] arrayOfByte = paramX509Certificate.getExtensionValue(X509Extensions.ExtendedKeyUsage.getId());
    if (arrayOfByte == null) {
      throw new TSPValidationException("Certificate must have an ExtendedKeyUsage extension.");
    }
    if (!paramX509Certificate.getCriticalExtensionOIDs().contains(X509Extensions.ExtendedKeyUsage.getId())) {
      throw new TSPValidationException("Certificate must have an ExtendedKeyUsage extension marked as critical.");
    }
    ASN1InputStream localASN1InputStream = new ASN1InputStream(new ByteArrayInputStream(arrayOfByte));
    try
    {
      ExtendedKeyUsage localExtendedKeyUsage = ExtendedKeyUsage.getInstance(new ASN1InputStream(new ByteArrayInputStream(((ASN1OctetString)localASN1InputStream.readObject()).getOctets())).readObject());
      if ((!localExtendedKeyUsage.hasKeyPurposeId(KeyPurposeId.id_kp_timeStamping)) || (localExtendedKeyUsage.size() != 1)) {
        throw new TSPValidationException("ExtendedKeyUsage not solely time stamping.");
      }
    }
    catch (IOException localIOException)
    {
      throw new TSPValidationException("cannot process ExtendedKeyUsage extension");
    }
  }
  
  public static void validateCertificate(X509CertificateHolder paramX509CertificateHolder)
    throws TSPValidationException
  {
    if (paramX509CertificateHolder.toASN1Structure().getVersionNumber() != 3) {
      throw new IllegalArgumentException("Certificate must have an ExtendedKeyUsage extension.");
    }
    Extension localExtension = paramX509CertificateHolder.getExtension(Extension.extendedKeyUsage);
    if (localExtension == null) {
      throw new TSPValidationException("Certificate must have an ExtendedKeyUsage extension.");
    }
    if (!localExtension.isCritical()) {
      throw new TSPValidationException("Certificate must have an ExtendedKeyUsage extension marked as critical.");
    }
    ExtendedKeyUsage localExtendedKeyUsage = ExtendedKeyUsage.getInstance(localExtension.getParsedValue());
    if ((!localExtendedKeyUsage.hasKeyPurposeId(KeyPurposeId.id_kp_timeStamping)) || (localExtendedKeyUsage.size() != 1)) {
      throw new TSPValidationException("ExtendedKeyUsage not solely time stamping.");
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.tsp.TSPUtil
 * JD-Core Version:    0.7.0.1
 */