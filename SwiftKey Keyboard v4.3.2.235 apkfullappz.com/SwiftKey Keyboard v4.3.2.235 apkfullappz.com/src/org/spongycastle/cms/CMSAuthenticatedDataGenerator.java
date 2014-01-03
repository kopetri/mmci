package org.spongycastle.cms;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.crypto.KeyGenerator;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.BEROctetString;
import org.spongycastle.asn1.BERSet;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSet;
import org.spongycastle.asn1.cms.AttributeTable;
import org.spongycastle.asn1.cms.AuthenticatedData;
import org.spongycastle.asn1.cms.CMSObjectIdentifiers;
import org.spongycastle.asn1.cms.ContentInfo;
import org.spongycastle.asn1.cms.OriginatorInfo;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.cms.jcajce.JceCMSMacCalculatorBuilder;
import org.spongycastle.operator.DigestCalculator;
import org.spongycastle.operator.DigestCalculatorProvider;
import org.spongycastle.operator.MacCalculator;
import org.spongycastle.operator.OperatorCreationException;
import org.spongycastle.util.io.TeeOutputStream;

public class CMSAuthenticatedDataGenerator
  extends CMSAuthenticatedGenerator
{
  public CMSAuthenticatedDataGenerator() {}
  
  public CMSAuthenticatedDataGenerator(SecureRandom paramSecureRandom)
  {
    super(paramSecureRandom);
  }
  
  private CMSAuthenticatedData generate(final CMSProcessable paramCMSProcessable, String paramString, KeyGenerator paramKeyGenerator, Provider paramProvider)
    throws NoSuchAlgorithmException, CMSException
  {
    Provider localProvider = paramKeyGenerator.getProvider();
    convertOldRecipients(this.rand, paramProvider);
    generate(new CMSTypedData()new JceCMSMacCalculatorBuildernew ASN1ObjectIdentifier
    {
      public Object getContent()
      {
        return paramCMSProcessable;
      }
      
      public ASN1ObjectIdentifier getContentType()
      {
        return CMSObjectIdentifiers.data;
      }
      
      public void write(OutputStream paramAnonymousOutputStream)
        throws IOException, CMSException
      {
        paramCMSProcessable.write(paramAnonymousOutputStream);
      }
    }, new JceCMSMacCalculatorBuilder(new ASN1ObjectIdentifier(paramString)).setProvider(localProvider).setSecureRandom(this.rand).build());
  }
  
  public CMSAuthenticatedData generate(CMSProcessable paramCMSProcessable, String paramString1, String paramString2)
    throws NoSuchAlgorithmException, NoSuchProviderException, CMSException
  {
    return generate(paramCMSProcessable, paramString1, CMSUtils.getProvider(paramString2));
  }
  
  public CMSAuthenticatedData generate(CMSProcessable paramCMSProcessable, String paramString, Provider paramProvider)
    throws NoSuchAlgorithmException, CMSException
  {
    return generate(paramCMSProcessable, paramString, CMSEnvelopedHelper.INSTANCE.createSymmetricKeyGenerator(paramString, paramProvider), paramProvider);
  }
  
  public CMSAuthenticatedData generate(CMSTypedData paramCMSTypedData, MacCalculator paramMacCalculator)
    throws CMSException
  {
    return generate(paramCMSTypedData, paramMacCalculator, null);
  }
  
  public CMSAuthenticatedData generate(CMSTypedData paramCMSTypedData, MacCalculator paramMacCalculator, final DigestCalculator paramDigestCalculator)
    throws CMSException
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    Iterator localIterator = this.recipientInfoGenerators.iterator();
    while (localIterator.hasNext()) {
      localASN1EncodableVector.add(((RecipientInfoGenerator)localIterator.next()).generate(paramMacCalculator.getKey()));
    }
    AuthenticatedData localAuthenticatedData;
    if (paramDigestCalculator != null) {
      label394:
      for (;;)
      {
        try
        {
          ByteArrayOutputStream localByteArrayOutputStream2 = new ByteArrayOutputStream();
          TeeOutputStream localTeeOutputStream2 = new TeeOutputStream(paramDigestCalculator.getOutputStream(), localByteArrayOutputStream2);
          paramCMSTypedData.write(localTeeOutputStream2);
          localTeeOutputStream2.close();
          BEROctetString localBEROctetString2 = new BEROctetString(localByteArrayOutputStream2.toByteArray());
          Map localMap = getBaseParameters(paramCMSTypedData.getContentType(), paramDigestCalculator.getAlgorithmIdentifier(), paramDigestCalculator.getDigest());
          if (this.authGen == null) {
            this.authGen = new DefaultAuthenticatedAttributeTableGenerator();
          }
          DERSet localDERSet2 = new DERSet(this.authGen.getAttributes(Collections.unmodifiableMap(localMap)).toASN1EncodableVector());
          OutputStream localOutputStream;
          DEROctetString localDEROctetString2;
          ContentInfo localContentInfo3;
          ContentInfo localContentInfo2;
          BERSet localBERSet2 = null;
        }
        catch (IOException localIOException2)
        {
          try
          {
            localOutputStream = paramMacCalculator.getOutputStream();
            localOutputStream.write(localDERSet2.getEncoded("DER"));
            localOutputStream.close();
            localDEROctetString2 = new DEROctetString(paramMacCalculator.getMac());
            if (this.unauthGen == null) {
              break label394;
            }
            localBERSet2 = new BERSet(this.unauthGen.getAttributes(Collections.unmodifiableMap(localMap)).toASN1EncodableVector());
            localContentInfo3 = new ContentInfo(CMSObjectIdentifiers.data, localBEROctetString2);
            localAuthenticatedData = new AuthenticatedData(this.originatorInfo, new DERSet(localASN1EncodableVector), paramMacCalculator.getAlgorithmIdentifier(), paramDigestCalculator.getAlgorithmIdentifier(), localContentInfo3, localDERSet2, localDEROctetString2, localBERSet2);
            localContentInfo2 = new ContentInfo(CMSObjectIdentifiers.authenticatedData, localAuthenticatedData);
            new CMSAuthenticatedData(localContentInfo2, new DigestCalculatorProvider()
            {
              public DigestCalculator get(AlgorithmIdentifier paramAnonymousAlgorithmIdentifier)
                throws OperatorCreationException
              {
                return paramDigestCalculator;
              }
            });
          }
          catch (IOException localIOException3)
          {
            throw new CMSException("exception decoding algorithm parameters.", localIOException3);
          }
          localIOException2 = localIOException2;
          throw new CMSException("unable to perform digest calculation: " + localIOException2.getMessage(), localIOException2);
        }
      }
    }
    for (;;)
    {
      try
      {
        ByteArrayOutputStream localByteArrayOutputStream1 = new ByteArrayOutputStream();
        TeeOutputStream localTeeOutputStream1 = new TeeOutputStream(localByteArrayOutputStream1, paramMacCalculator.getOutputStream());
        paramCMSTypedData.write(localTeeOutputStream1);
        localTeeOutputStream1.close();
        BEROctetString localBEROctetString1 = new BEROctetString(localByteArrayOutputStream1.toByteArray());
        DEROctetString localDEROctetString1 = new DEROctetString(paramMacCalculator.getMac());
        if (this.unauthGen != null)
        {
          localBERSet1 = new BERSet(this.unauthGen.getAttributes(new HashMap()).toASN1EncodableVector());
          ContentInfo localContentInfo1 = new ContentInfo(CMSObjectIdentifiers.data, localBEROctetString1);
          OriginatorInfo localOriginatorInfo = this.originatorInfo;
          DERSet localDERSet1 = new DERSet(localASN1EncodableVector);
          AlgorithmIdentifier localAlgorithmIdentifier = paramMacCalculator.getAlgorithmIdentifier();
          localAuthenticatedData = new AuthenticatedData(localOriginatorInfo, localDERSet1, localAlgorithmIdentifier, null, localContentInfo1, null, localDEROctetString1, localBERSet1);
        }
      }
      catch (IOException localIOException1)
      {
        throw new CMSException("exception decoding algorithm parameters.", localIOException1);
      }
      BERSet localBERSet1 = null;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.CMSAuthenticatedDataGenerator
 * JD-Core Version:    0.7.0.1
 */