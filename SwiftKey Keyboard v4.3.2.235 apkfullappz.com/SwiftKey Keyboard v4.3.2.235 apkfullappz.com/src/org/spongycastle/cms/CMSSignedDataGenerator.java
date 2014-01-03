package org.spongycastle.cms;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.BEROctetString;
import org.spongycastle.asn1.DERSet;
import org.spongycastle.asn1.cms.AttributeTable;
import org.spongycastle.asn1.cms.CMSObjectIdentifiers;
import org.spongycastle.asn1.cms.ContentInfo;
import org.spongycastle.asn1.cms.SignedData;
import org.spongycastle.asn1.cms.SignerInfo;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder;
import org.spongycastle.operator.ContentSigner;
import org.spongycastle.operator.OperatorCreationException;
import org.spongycastle.operator.bc.BcDigestCalculatorProvider;
import org.spongycastle.operator.jcajce.JcaContentSignerBuilder;

public class CMSSignedDataGenerator
  extends CMSSignedGenerator
{
  private List signerInfs = new ArrayList();
  
  public CMSSignedDataGenerator() {}
  
  public CMSSignedDataGenerator(SecureRandom paramSecureRandom)
  {
    super(paramSecureRandom);
  }
  
  private void doAddSigner(PrivateKey paramPrivateKey, Object paramObject, String paramString1, String paramString2, CMSAttributeTableGenerator paramCMSAttributeTableGenerator1, CMSAttributeTableGenerator paramCMSAttributeTableGenerator2, AttributeTable paramAttributeTable)
    throws IllegalArgumentException
  {
    this.signerInfs.add(new SignerInf(paramPrivateKey, paramObject, paramString2, paramString1, paramCMSAttributeTableGenerator1, paramCMSAttributeTableGenerator2, paramAttributeTable));
  }
  
  public void addSigner(PrivateKey paramPrivateKey, X509Certificate paramX509Certificate, String paramString)
    throws IllegalArgumentException
  {
    addSigner(paramPrivateKey, paramX509Certificate, getEncOID(paramPrivateKey, paramString), paramString);
  }
  
  public void addSigner(PrivateKey paramPrivateKey, X509Certificate paramX509Certificate, String paramString1, String paramString2)
    throws IllegalArgumentException
  {
    doAddSigner(paramPrivateKey, paramX509Certificate, paramString1, paramString2, new DefaultSignedAttributeTableGenerator(), null, null);
  }
  
  public void addSigner(PrivateKey paramPrivateKey, X509Certificate paramX509Certificate, String paramString1, String paramString2, AttributeTable paramAttributeTable1, AttributeTable paramAttributeTable2)
    throws IllegalArgumentException
  {
    doAddSigner(paramPrivateKey, paramX509Certificate, paramString1, paramString2, new DefaultSignedAttributeTableGenerator(paramAttributeTable1), new SimpleAttributeTableGenerator(paramAttributeTable2), paramAttributeTable1);
  }
  
  public void addSigner(PrivateKey paramPrivateKey, X509Certificate paramX509Certificate, String paramString1, String paramString2, CMSAttributeTableGenerator paramCMSAttributeTableGenerator1, CMSAttributeTableGenerator paramCMSAttributeTableGenerator2)
    throws IllegalArgumentException
  {
    doAddSigner(paramPrivateKey, paramX509Certificate, paramString1, paramString2, paramCMSAttributeTableGenerator1, paramCMSAttributeTableGenerator2, null);
  }
  
  public void addSigner(PrivateKey paramPrivateKey, X509Certificate paramX509Certificate, String paramString, AttributeTable paramAttributeTable1, AttributeTable paramAttributeTable2)
    throws IllegalArgumentException
  {
    addSigner(paramPrivateKey, paramX509Certificate, getEncOID(paramPrivateKey, paramString), paramString, paramAttributeTable1, paramAttributeTable2);
  }
  
  public void addSigner(PrivateKey paramPrivateKey, X509Certificate paramX509Certificate, String paramString, CMSAttributeTableGenerator paramCMSAttributeTableGenerator1, CMSAttributeTableGenerator paramCMSAttributeTableGenerator2)
    throws IllegalArgumentException
  {
    addSigner(paramPrivateKey, paramX509Certificate, getEncOID(paramPrivateKey, paramString), paramString, paramCMSAttributeTableGenerator1, paramCMSAttributeTableGenerator2);
  }
  
  public void addSigner(PrivateKey paramPrivateKey, byte[] paramArrayOfByte, String paramString)
    throws IllegalArgumentException
  {
    addSigner(paramPrivateKey, paramArrayOfByte, getEncOID(paramPrivateKey, paramString), paramString);
  }
  
  public void addSigner(PrivateKey paramPrivateKey, byte[] paramArrayOfByte, String paramString1, String paramString2)
    throws IllegalArgumentException
  {
    doAddSigner(paramPrivateKey, paramArrayOfByte, paramString1, paramString2, new DefaultSignedAttributeTableGenerator(), null, null);
  }
  
  public void addSigner(PrivateKey paramPrivateKey, byte[] paramArrayOfByte, String paramString1, String paramString2, AttributeTable paramAttributeTable1, AttributeTable paramAttributeTable2)
    throws IllegalArgumentException
  {
    doAddSigner(paramPrivateKey, paramArrayOfByte, paramString1, paramString2, new DefaultSignedAttributeTableGenerator(paramAttributeTable1), new SimpleAttributeTableGenerator(paramAttributeTable2), paramAttributeTable1);
  }
  
  public void addSigner(PrivateKey paramPrivateKey, byte[] paramArrayOfByte, String paramString1, String paramString2, CMSAttributeTableGenerator paramCMSAttributeTableGenerator1, CMSAttributeTableGenerator paramCMSAttributeTableGenerator2)
    throws IllegalArgumentException
  {
    doAddSigner(paramPrivateKey, paramArrayOfByte, paramString1, paramString2, paramCMSAttributeTableGenerator1, paramCMSAttributeTableGenerator2, null);
  }
  
  public void addSigner(PrivateKey paramPrivateKey, byte[] paramArrayOfByte, String paramString, AttributeTable paramAttributeTable1, AttributeTable paramAttributeTable2)
    throws IllegalArgumentException
  {
    addSigner(paramPrivateKey, paramArrayOfByte, getEncOID(paramPrivateKey, paramString), paramString, paramAttributeTable1, paramAttributeTable2);
  }
  
  public void addSigner(PrivateKey paramPrivateKey, byte[] paramArrayOfByte, String paramString, CMSAttributeTableGenerator paramCMSAttributeTableGenerator1, CMSAttributeTableGenerator paramCMSAttributeTableGenerator2)
    throws IllegalArgumentException
  {
    addSigner(paramPrivateKey, paramArrayOfByte, getEncOID(paramPrivateKey, paramString), paramString, paramCMSAttributeTableGenerator1, paramCMSAttributeTableGenerator2);
  }
  
  public CMSSignedData generate(String paramString1, CMSProcessable paramCMSProcessable, boolean paramBoolean, String paramString2)
    throws NoSuchAlgorithmException, NoSuchProviderException, CMSException
  {
    return generate(paramString1, paramCMSProcessable, paramBoolean, CMSUtils.getProvider(paramString2), true);
  }
  
  public CMSSignedData generate(String paramString1, CMSProcessable paramCMSProcessable, boolean paramBoolean1, String paramString2, boolean paramBoolean2)
    throws NoSuchAlgorithmException, NoSuchProviderException, CMSException
  {
    return generate(paramString1, paramCMSProcessable, paramBoolean1, CMSUtils.getProvider(paramString2), paramBoolean2);
  }
  
  public CMSSignedData generate(String paramString, CMSProcessable paramCMSProcessable, boolean paramBoolean, Provider paramProvider)
    throws NoSuchAlgorithmException, CMSException
  {
    return generate(paramString, paramCMSProcessable, paramBoolean, paramProvider, true);
  }
  
  public CMSSignedData generate(String paramString, final CMSProcessable paramCMSProcessable, boolean paramBoolean1, Provider paramProvider, boolean paramBoolean2)
    throws NoSuchAlgorithmException, CMSException
  {
    int i;
    if (paramString == null) {
      i = 1;
    }
    final ASN1ObjectIdentifier localASN1ObjectIdentifier;
    for (;;)
    {
      label15:
      SignerInf localSignerInf;
      if (i != 0)
      {
        localASN1ObjectIdentifier = null;
        Iterator localIterator = this.signerInfs.iterator();
        if (localIterator.hasNext()) {
          localSignerInf = (SignerInf)localIterator.next();
        }
      }
      else
      {
        try
        {
          this.signerGens.add(localSignerInf.toSignerInfoGenerator(this.rand, paramProvider, paramBoolean2));
        }
        catch (OperatorCreationException localOperatorCreationException)
        {
          throw new CMSException("exception creating signerInf", localOperatorCreationException);
          i = 0;
          continue;
          localASN1ObjectIdentifier = new ASN1ObjectIdentifier(paramString);
          break label15;
        }
        catch (IOException localIOException)
        {
          throw new CMSException("exception encoding attributes", localIOException);
        }
        catch (CertificateEncodingException localCertificateEncodingException)
        {
          throw new CMSException("error creating sid.", localCertificateEncodingException);
        }
      }
    }
    this.signerInfs.clear();
    if (paramCMSProcessable != null) {
      generate(new CMSTypedData()
      {
        public Object getContent()
        {
          return paramCMSProcessable.getContent();
        }
        
        public ASN1ObjectIdentifier getContentType()
        {
          return localASN1ObjectIdentifier;
        }
        
        public void write(OutputStream paramAnonymousOutputStream)
          throws IOException, CMSException
        {
          paramCMSProcessable.write(paramAnonymousOutputStream);
        }
      }, paramBoolean1);
    }
    return generate(new CMSAbsentContent(localASN1ObjectIdentifier), paramBoolean1);
  }
  
  public CMSSignedData generate(CMSProcessable paramCMSProcessable, String paramString)
    throws NoSuchAlgorithmException, NoSuchProviderException, CMSException
  {
    return generate(paramCMSProcessable, CMSUtils.getProvider(paramString));
  }
  
  public CMSSignedData generate(CMSProcessable paramCMSProcessable, Provider paramProvider)
    throws NoSuchAlgorithmException, CMSException
  {
    return generate(paramCMSProcessable, false, paramProvider);
  }
  
  public CMSSignedData generate(CMSProcessable paramCMSProcessable, boolean paramBoolean, String paramString)
    throws NoSuchAlgorithmException, NoSuchProviderException, CMSException
  {
    if ((paramCMSProcessable instanceof CMSTypedData)) {
      return generate(((CMSTypedData)paramCMSProcessable).getContentType().getId(), paramCMSProcessable, paramBoolean, paramString);
    }
    return generate(DATA, paramCMSProcessable, paramBoolean, paramString);
  }
  
  public CMSSignedData generate(CMSProcessable paramCMSProcessable, boolean paramBoolean, Provider paramProvider)
    throws NoSuchAlgorithmException, CMSException
  {
    if ((paramCMSProcessable instanceof CMSTypedData)) {
      return generate(((CMSTypedData)paramCMSProcessable).getContentType().getId(), paramCMSProcessable, paramBoolean, paramProvider);
    }
    return generate(DATA, paramCMSProcessable, paramBoolean, paramProvider);
  }
  
  public CMSSignedData generate(CMSTypedData paramCMSTypedData)
    throws CMSException
  {
    return generate(paramCMSTypedData, false);
  }
  
  public CMSSignedData generate(CMSTypedData paramCMSTypedData, boolean paramBoolean)
    throws CMSException
  {
    if (!this.signerInfs.isEmpty()) {
      throw new IllegalStateException("this method can only be used with SignerInfoGenerator");
    }
    ASN1EncodableVector localASN1EncodableVector1 = new ASN1EncodableVector();
    ASN1EncodableVector localASN1EncodableVector2 = new ASN1EncodableVector();
    this.digests.clear();
    Iterator localIterator1 = this._signers.iterator();
    while (localIterator1.hasNext())
    {
      SignerInformation localSignerInformation = (SignerInformation)localIterator1.next();
      localASN1EncodableVector1.add(CMSSignedHelper.INSTANCE.fixAlgID(localSignerInformation.getDigestAlgorithmID()));
      localASN1EncodableVector2.add(localSignerInformation.toASN1Structure());
    }
    ASN1ObjectIdentifier localASN1ObjectIdentifier = paramCMSTypedData.getContentType();
    BEROctetString localBEROctetString = null;
    ByteArrayOutputStream localByteArrayOutputStream;
    OutputStream localOutputStream;
    if (paramCMSTypedData != null)
    {
      localByteArrayOutputStream = null;
      if (paramBoolean) {
        localByteArrayOutputStream = new ByteArrayOutputStream();
      }
      localOutputStream = CMSUtils.getSafeOutputStream(CMSUtils.attachSignersToOutputStream(this.signerGens, localByteArrayOutputStream));
    }
    try
    {
      paramCMSTypedData.write(localOutputStream);
      localOutputStream.close();
      localBEROctetString = null;
      if (paramBoolean)
      {
        byte[] arrayOfByte2 = localByteArrayOutputStream.toByteArray();
        localBEROctetString = new BEROctetString(arrayOfByte2);
      }
      Iterator localIterator2 = this.signerGens.iterator();
      while (localIterator2.hasNext())
      {
        SignerInfoGenerator localSignerInfoGenerator = (SignerInfoGenerator)localIterator2.next();
        SignerInfo localSignerInfo = localSignerInfoGenerator.generate(localASN1ObjectIdentifier);
        localASN1EncodableVector1.add(localSignerInfo.getDigestAlgorithm());
        localASN1EncodableVector2.add(localSignerInfo);
        byte[] arrayOfByte1 = localSignerInfoGenerator.getCalculatedDigest();
        if (arrayOfByte1 != null) {
          this.digests.put(localSignerInfo.getDigestAlgorithm().getAlgorithm().getId(), arrayOfByte1);
        }
      }
      i = this.certs.size();
    }
    catch (IOException localIOException)
    {
      throw new CMSException("data processing exception: " + localIOException.getMessage(), localIOException);
    }
    int i;
    ASN1Set localASN1Set1 = null;
    if (i != 0) {
      localASN1Set1 = CMSUtils.createBerSetFromList(this.certs);
    }
    int j = this.crls.size();
    ASN1Set localASN1Set2 = null;
    if (j != 0) {
      localASN1Set2 = CMSUtils.createBerSetFromList(this.crls);
    }
    ContentInfo localContentInfo = new ContentInfo(localASN1ObjectIdentifier, localBEROctetString);
    SignedData localSignedData = new SignedData(new DERSet(localASN1EncodableVector1), localContentInfo, localASN1Set1, localASN1Set2, new DERSet(localASN1EncodableVector2));
    return new CMSSignedData(paramCMSTypedData, new ContentInfo(CMSObjectIdentifiers.signedData, localSignedData));
  }
  
  public SignerInformationStore generateCounterSigners(SignerInformation paramSignerInformation)
    throws CMSException
  {
    return generate(new CMSProcessableByteArray(null, paramSignerInformation.getSignature()), false).getSignerInfos();
  }
  
  public SignerInformationStore generateCounterSigners(SignerInformation paramSignerInformation, String paramString)
    throws NoSuchAlgorithmException, NoSuchProviderException, CMSException
  {
    return generate(null, new CMSProcessableByteArray(paramSignerInformation.getSignature()), false, CMSUtils.getProvider(paramString)).getSignerInfos();
  }
  
  public SignerInformationStore generateCounterSigners(SignerInformation paramSignerInformation, Provider paramProvider)
    throws NoSuchAlgorithmException, CMSException
  {
    return generate(null, new CMSProcessableByteArray(paramSignerInformation.getSignature()), false, paramProvider).getSignerInfos();
  }
  
  private class SignerInf
  {
    final AttributeTable baseSignedTable;
    final String digestOID;
    final String encOID;
    final PrivateKey key;
    final CMSAttributeTableGenerator sAttr;
    final Object signerIdentifier;
    final CMSAttributeTableGenerator unsAttr;
    
    SignerInf(PrivateKey paramPrivateKey, Object paramObject, String paramString1, String paramString2, CMSAttributeTableGenerator paramCMSAttributeTableGenerator1, CMSAttributeTableGenerator paramCMSAttributeTableGenerator2, AttributeTable paramAttributeTable)
    {
      this.key = paramPrivateKey;
      this.signerIdentifier = paramObject;
      this.digestOID = paramString1;
      this.encOID = paramString2;
      this.sAttr = paramCMSAttributeTableGenerator1;
      this.unsAttr = paramCMSAttributeTableGenerator2;
      this.baseSignedTable = paramAttributeTable;
    }
    
    SignerInfoGenerator toSignerInfoGenerator(SecureRandom paramSecureRandom, Provider paramProvider, boolean paramBoolean)
      throws IOException, CertificateEncodingException, CMSException, OperatorCreationException, NoSuchAlgorithmException
    {
      String str1 = CMSSignedHelper.INSTANCE.getDigestAlgName(this.digestOID);
      String str2 = str1 + "with" + CMSSignedHelper.INSTANCE.getEncryptionAlgName(this.encOID);
      JcaSignerInfoGeneratorBuilder localJcaSignerInfoGeneratorBuilder = new JcaSignerInfoGeneratorBuilder(new BcDigestCalculatorProvider());
      if (paramBoolean) {
        localJcaSignerInfoGeneratorBuilder.setSignedAttributeGenerator(this.sAttr);
      }
      if (!paramBoolean) {}
      ContentSigner localContentSigner;
      for (boolean bool = true;; bool = false)
      {
        localJcaSignerInfoGeneratorBuilder.setDirectSignature(bool);
        localJcaSignerInfoGeneratorBuilder.setUnsignedAttributeGenerator(this.unsAttr);
        try
        {
          JcaContentSignerBuilder localJcaContentSignerBuilder = new JcaContentSignerBuilder(str2).setSecureRandom(paramSecureRandom);
          if (paramProvider != null) {
            localJcaContentSignerBuilder.setProvider(paramProvider);
          }
          localContentSigner = localJcaContentSignerBuilder.build(this.key);
          if (!(this.signerIdentifier instanceof X509Certificate)) {
            break;
          }
          return localJcaSignerInfoGeneratorBuilder.build(localContentSigner, (X509Certificate)this.signerIdentifier);
        }
        catch (IllegalArgumentException localIllegalArgumentException)
        {
          throw new NoSuchAlgorithmException(localIllegalArgumentException.getMessage());
        }
      }
      return localJcaSignerInfoGeneratorBuilder.build(localContentSigner, (byte[])this.signerIdentifier);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.CMSSignedDataGenerator
 * JD-Core Version:    0.7.0.1
 */