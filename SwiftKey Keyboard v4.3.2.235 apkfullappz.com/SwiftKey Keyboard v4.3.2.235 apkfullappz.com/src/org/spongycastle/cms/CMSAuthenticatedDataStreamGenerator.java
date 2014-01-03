package org.spongycastle.cms;

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
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.BERSequenceGenerator;
import org.spongycastle.asn1.BERSet;
import org.spongycastle.asn1.DERInteger;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSet;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.cms.AttributeTable;
import org.spongycastle.asn1.cms.AuthenticatedData;
import org.spongycastle.asn1.cms.CMSObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.cms.jcajce.JceCMSMacCalculatorBuilder;
import org.spongycastle.operator.DigestCalculator;
import org.spongycastle.operator.MacCalculator;
import org.spongycastle.util.io.TeeOutputStream;

public class CMSAuthenticatedDataStreamGenerator
  extends CMSAuthenticatedGenerator
{
  private boolean berEncodeRecipientSet;
  private int bufferSize;
  private MacCalculator macCalculator;
  
  public CMSAuthenticatedDataStreamGenerator() {}
  
  public CMSAuthenticatedDataStreamGenerator(SecureRandom paramSecureRandom)
  {
    super(paramSecureRandom);
  }
  
  public OutputStream open(OutputStream paramOutputStream, String paramString1, int paramInt, String paramString2)
    throws NoSuchAlgorithmException, NoSuchProviderException, CMSException, IOException
  {
    convertOldRecipients(this.rand, CMSUtils.getProvider(paramString2));
    return open(paramOutputStream, new JceCMSMacCalculatorBuilder(new ASN1ObjectIdentifier(paramString1), paramInt).setSecureRandom(this.rand).setProvider(paramString2).build());
  }
  
  public OutputStream open(OutputStream paramOutputStream, String paramString, int paramInt, Provider paramProvider)
    throws NoSuchAlgorithmException, CMSException, IOException
  {
    convertOldRecipients(this.rand, paramProvider);
    return open(paramOutputStream, new JceCMSMacCalculatorBuilder(new ASN1ObjectIdentifier(paramString), paramInt).setSecureRandom(this.rand).setProvider(paramProvider).build());
  }
  
  public OutputStream open(OutputStream paramOutputStream, String paramString1, String paramString2)
    throws NoSuchAlgorithmException, NoSuchProviderException, CMSException, IOException
  {
    convertOldRecipients(this.rand, CMSUtils.getProvider(paramString2));
    return open(paramOutputStream, new JceCMSMacCalculatorBuilder(new ASN1ObjectIdentifier(paramString1)).setSecureRandom(this.rand).setProvider(paramString2).build());
  }
  
  public OutputStream open(OutputStream paramOutputStream, String paramString, Provider paramProvider)
    throws NoSuchAlgorithmException, CMSException, IOException
  {
    convertOldRecipients(this.rand, paramProvider);
    return open(paramOutputStream, new JceCMSMacCalculatorBuilder(new ASN1ObjectIdentifier(paramString)).setSecureRandom(this.rand).setProvider(paramProvider).build());
  }
  
  public OutputStream open(OutputStream paramOutputStream, MacCalculator paramMacCalculator)
    throws CMSException
  {
    return open(CMSObjectIdentifiers.data, paramOutputStream, paramMacCalculator);
  }
  
  public OutputStream open(OutputStream paramOutputStream, MacCalculator paramMacCalculator, DigestCalculator paramDigestCalculator)
    throws CMSException
  {
    return open(CMSObjectIdentifiers.data, paramOutputStream, paramMacCalculator, paramDigestCalculator);
  }
  
  public OutputStream open(ASN1ObjectIdentifier paramASN1ObjectIdentifier, OutputStream paramOutputStream, MacCalculator paramMacCalculator)
    throws CMSException
  {
    return open(paramASN1ObjectIdentifier, paramOutputStream, paramMacCalculator, null);
  }
  
  public OutputStream open(ASN1ObjectIdentifier paramASN1ObjectIdentifier, OutputStream paramOutputStream, MacCalculator paramMacCalculator, DigestCalculator paramDigestCalculator)
    throws CMSException
  {
    this.macCalculator = paramMacCalculator;
    ASN1EncodableVector localASN1EncodableVector;
    try
    {
      localASN1EncodableVector = new ASN1EncodableVector();
      Iterator localIterator = this.recipientInfoGenerators.iterator();
      while (localIterator.hasNext()) {
        localASN1EncodableVector.add(((RecipientInfoGenerator)localIterator.next()).generate(paramMacCalculator.getKey()));
      }
      localBERSequenceGenerator1 = new BERSequenceGenerator(paramOutputStream);
    }
    catch (IOException localIOException)
    {
      throw new CMSException("exception decoding algorithm parameters.", localIOException);
    }
    BERSequenceGenerator localBERSequenceGenerator1;
    localBERSequenceGenerator1.addObject(CMSObjectIdentifiers.authenticatedData);
    BERSequenceGenerator localBERSequenceGenerator2 = new BERSequenceGenerator(localBERSequenceGenerator1.getRawOutputStream(), 0, true);
    localBERSequenceGenerator2.addObject(new DERInteger(AuthenticatedData.calculateVersion(this.originatorInfo)));
    if (this.originatorInfo != null) {
      localBERSequenceGenerator2.addObject(new DERTaggedObject(false, 0, this.originatorInfo));
    }
    BERSequenceGenerator localBERSequenceGenerator3;
    OutputStream localOutputStream;
    if (this.berEncodeRecipientSet)
    {
      localBERSequenceGenerator2.getRawOutputStream().write(new BERSet(localASN1EncodableVector).getEncoded());
      AlgorithmIdentifier localAlgorithmIdentifier = paramMacCalculator.getAlgorithmIdentifier();
      localBERSequenceGenerator2.getRawOutputStream().write(localAlgorithmIdentifier.getEncoded());
      if (paramDigestCalculator != null) {
        localBERSequenceGenerator2.addObject(new DERTaggedObject(false, 1, paramDigestCalculator.getAlgorithmIdentifier()));
      }
      localBERSequenceGenerator3 = new BERSequenceGenerator(localBERSequenceGenerator2.getRawOutputStream());
      localBERSequenceGenerator3.addObject(paramASN1ObjectIdentifier);
      localOutputStream = CMSUtils.createBEROctetOutputStream(localBERSequenceGenerator3.getRawOutputStream(), 0, false, this.bufferSize);
      if (paramDigestCalculator == null) {
        break label333;
      }
    }
    label333:
    for (TeeOutputStream localTeeOutputStream = new TeeOutputStream(localOutputStream, paramDigestCalculator.getOutputStream());; localTeeOutputStream = new TeeOutputStream(localOutputStream, paramMacCalculator.getOutputStream()))
    {
      return new CmsAuthenticatedDataOutputStream(paramMacCalculator, paramDigestCalculator, paramASN1ObjectIdentifier, localTeeOutputStream, localBERSequenceGenerator1, localBERSequenceGenerator2, localBERSequenceGenerator3);
      localBERSequenceGenerator2.getRawOutputStream().write(new DERSet(localASN1EncodableVector).getEncoded());
      break;
    }
  }
  
  public void setBEREncodeRecipients(boolean paramBoolean)
  {
    this.berEncodeRecipientSet = paramBoolean;
  }
  
  public void setBufferSize(int paramInt)
  {
    this.bufferSize = paramInt;
  }
  
  private class CmsAuthenticatedDataOutputStream
    extends OutputStream
  {
    private BERSequenceGenerator cGen;
    private ASN1ObjectIdentifier contentType;
    private OutputStream dataStream;
    private DigestCalculator digestCalculator;
    private BERSequenceGenerator eiGen;
    private BERSequenceGenerator envGen;
    private MacCalculator macCalculator;
    
    public CmsAuthenticatedDataOutputStream(MacCalculator paramMacCalculator, DigestCalculator paramDigestCalculator, ASN1ObjectIdentifier paramASN1ObjectIdentifier, OutputStream paramOutputStream, BERSequenceGenerator paramBERSequenceGenerator1, BERSequenceGenerator paramBERSequenceGenerator2, BERSequenceGenerator paramBERSequenceGenerator3)
    {
      this.macCalculator = paramMacCalculator;
      this.digestCalculator = paramDigestCalculator;
      this.contentType = paramASN1ObjectIdentifier;
      this.dataStream = paramOutputStream;
      this.cGen = paramBERSequenceGenerator1;
      this.envGen = paramBERSequenceGenerator2;
      this.eiGen = paramBERSequenceGenerator3;
    }
    
    public void close()
      throws IOException
    {
      this.dataStream.close();
      this.eiGen.close();
      Map localMap;
      if (this.digestCalculator != null)
      {
        localMap = Collections.unmodifiableMap(CMSAuthenticatedDataStreamGenerator.this.getBaseParameters(this.contentType, this.digestCalculator.getAlgorithmIdentifier(), this.digestCalculator.getDigest()));
        if (CMSAuthenticatedDataStreamGenerator.this.authGen == null) {
          CMSAuthenticatedDataStreamGenerator.this.authGen = new DefaultAuthenticatedAttributeTableGenerator();
        }
        DERSet localDERSet = new DERSet(CMSAuthenticatedDataStreamGenerator.this.authGen.getAttributes(localMap).toASN1EncodableVector());
        OutputStream localOutputStream = this.macCalculator.getOutputStream();
        localOutputStream.write(localDERSet.getEncoded("DER"));
        localOutputStream.close();
        this.envGen.addObject(new DERTaggedObject(false, 2, localDERSet));
      }
      for (;;)
      {
        this.envGen.addObject(new DEROctetString(this.macCalculator.getMac()));
        if (CMSAuthenticatedDataStreamGenerator.this.unauthGen != null) {
          this.envGen.addObject(new DERTaggedObject(false, 3, new BERSet(CMSAuthenticatedDataStreamGenerator.this.unauthGen.getAttributes(localMap).toASN1EncodableVector())));
        }
        this.envGen.close();
        this.cGen.close();
        return;
        localMap = Collections.unmodifiableMap(new HashMap());
      }
    }
    
    public void write(int paramInt)
      throws IOException
    {
      this.dataStream.write(paramInt);
    }
    
    public void write(byte[] paramArrayOfByte)
      throws IOException
    {
      this.dataStream.write(paramArrayOfByte);
    }
    
    public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
      throws IOException
    {
      this.dataStream.write(paramArrayOfByte, paramInt1, paramInt2);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.CMSAuthenticatedDataStreamGenerator
 * JD-Core Version:    0.7.0.1
 */