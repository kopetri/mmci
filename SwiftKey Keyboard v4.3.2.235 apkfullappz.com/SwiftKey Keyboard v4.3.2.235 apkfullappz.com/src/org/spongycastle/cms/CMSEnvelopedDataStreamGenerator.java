package org.spongycastle.cms;

import java.io.IOException;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.crypto.KeyGenerator;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.BERSequenceGenerator;
import org.spongycastle.asn1.BERSet;
import org.spongycastle.asn1.DERInteger;
import org.spongycastle.asn1.DERSet;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.cms.AttributeTable;
import org.spongycastle.asn1.cms.CMSObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.cms.jcajce.JceCMSContentEncryptorBuilder;
import org.spongycastle.operator.GenericKey;
import org.spongycastle.operator.OutputEncryptor;

public class CMSEnvelopedDataStreamGenerator
  extends CMSEnvelopedGenerator
{
  private boolean _berEncodeRecipientSet;
  private int _bufferSize;
  private ASN1Set _unprotectedAttributes = null;
  
  public CMSEnvelopedDataStreamGenerator() {}
  
  public CMSEnvelopedDataStreamGenerator(SecureRandom paramSecureRandom)
  {
    super(paramSecureRandom);
  }
  
  private OutputStream doOpen(ASN1ObjectIdentifier paramASN1ObjectIdentifier, OutputStream paramOutputStream, OutputEncryptor paramOutputEncryptor)
    throws IOException, CMSException
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    GenericKey localGenericKey = paramOutputEncryptor.getKey();
    Iterator localIterator = this.recipientInfoGenerators.iterator();
    while (localIterator.hasNext()) {
      localASN1EncodableVector.add(((RecipientInfoGenerator)localIterator.next()).generate(localGenericKey));
    }
    return open(paramASN1ObjectIdentifier, paramOutputStream, localASN1EncodableVector, paramOutputEncryptor);
  }
  
  private DERInteger getVersion()
  {
    if ((this.originatorInfo != null) || (this._unprotectedAttributes != null)) {
      return new DERInteger(2);
    }
    return new DERInteger(0);
  }
  
  private OutputStream open(OutputStream paramOutputStream, String paramString, int paramInt, Provider paramProvider1, Provider paramProvider2)
    throws NoSuchAlgorithmException, CMSException, IOException
  {
    convertOldRecipients(this.rand, paramProvider2);
    if (paramInt != -1) {}
    for (JceCMSContentEncryptorBuilder localJceCMSContentEncryptorBuilder = new JceCMSContentEncryptorBuilder(new ASN1ObjectIdentifier(paramString), paramInt);; localJceCMSContentEncryptorBuilder = new JceCMSContentEncryptorBuilder(new ASN1ObjectIdentifier(paramString)))
    {
      localJceCMSContentEncryptorBuilder.setProvider(paramProvider1);
      localJceCMSContentEncryptorBuilder.setSecureRandom(this.rand);
      return doOpen(CMSObjectIdentifiers.data, paramOutputStream, localJceCMSContentEncryptorBuilder.build());
    }
  }
  
  public OutputStream open(OutputStream paramOutputStream, String paramString1, int paramInt, String paramString2)
    throws NoSuchAlgorithmException, NoSuchProviderException, CMSException, IOException
  {
    return open(paramOutputStream, paramString1, paramInt, CMSUtils.getProvider(paramString2));
  }
  
  public OutputStream open(OutputStream paramOutputStream, String paramString, int paramInt, Provider paramProvider)
    throws NoSuchAlgorithmException, CMSException, IOException
  {
    KeyGenerator localKeyGenerator = CMSEnvelopedHelper.INSTANCE.createSymmetricKeyGenerator(paramString, paramProvider);
    localKeyGenerator.init(paramInt, this.rand);
    return open(paramOutputStream, paramString, -1, localKeyGenerator.getProvider(), paramProvider);
  }
  
  public OutputStream open(OutputStream paramOutputStream, String paramString1, String paramString2)
    throws NoSuchAlgorithmException, NoSuchProviderException, CMSException, IOException
  {
    return open(paramOutputStream, paramString1, CMSUtils.getProvider(paramString2));
  }
  
  public OutputStream open(OutputStream paramOutputStream, String paramString, Provider paramProvider)
    throws NoSuchAlgorithmException, CMSException, IOException
  {
    KeyGenerator localKeyGenerator = CMSEnvelopedHelper.INSTANCE.createSymmetricKeyGenerator(paramString, paramProvider);
    localKeyGenerator.init(this.rand);
    return open(paramOutputStream, paramString, -1, localKeyGenerator.getProvider(), paramProvider);
  }
  
  /* Error */
  protected OutputStream open(OutputStream paramOutputStream, ASN1EncodableVector paramASN1EncodableVector, OutputEncryptor paramOutputEncryptor)
    throws CMSException
  {
    // Byte code:
    //   0: new 165	org/spongycastle/asn1/BERSequenceGenerator
    //   3: dup
    //   4: aload_1
    //   5: invokespecial 168	org/spongycastle/asn1/BERSequenceGenerator:<init>	(Ljava/io/OutputStream;)V
    //   8: astore 4
    //   10: aload 4
    //   12: getstatic 171	org/spongycastle/asn1/cms/CMSObjectIdentifiers:envelopedData	Lorg/spongycastle/asn1/ASN1ObjectIdentifier;
    //   15: invokevirtual 174	org/spongycastle/asn1/BERSequenceGenerator:addObject	(Lorg/spongycastle/asn1/ASN1Encodable;)V
    //   18: new 165	org/spongycastle/asn1/BERSequenceGenerator
    //   21: dup
    //   22: aload 4
    //   24: invokevirtual 178	org/spongycastle/asn1/BERSequenceGenerator:getRawOutputStream	()Ljava/io/OutputStream;
    //   27: iconst_0
    //   28: iconst_1
    //   29: invokespecial 181	org/spongycastle/asn1/BERSequenceGenerator:<init>	(Ljava/io/OutputStream;IZ)V
    //   32: astore 6
    //   34: aload_0
    //   35: getfield 183	org/spongycastle/cms/CMSEnvelopedDataStreamGenerator:_berEncodeRecipientSet	Z
    //   38: ifeq +154 -> 192
    //   41: new 185	org/spongycastle/asn1/BERSet
    //   44: dup
    //   45: aload_2
    //   46: invokespecial 188	org/spongycastle/asn1/BERSet:<init>	(Lorg/spongycastle/asn1/ASN1EncodableVector;)V
    //   49: astore 7
    //   51: aload 6
    //   53: new 190	org/spongycastle/asn1/ASN1Integer
    //   56: dup
    //   57: aload_0
    //   58: getfield 74	org/spongycastle/cms/CMSEnvelopedDataStreamGenerator:originatorInfo	Lorg/spongycastle/asn1/cms/OriginatorInfo;
    //   61: aload 7
    //   63: aload_0
    //   64: getfield 16	org/spongycastle/cms/CMSEnvelopedDataStreamGenerator:_unprotectedAttributes	Lorg/spongycastle/asn1/ASN1Set;
    //   67: invokestatic 196	org/spongycastle/asn1/cms/EnvelopedData:calculateVersion	(Lorg/spongycastle/asn1/cms/OriginatorInfo;Lorg/spongycastle/asn1/ASN1Set;Lorg/spongycastle/asn1/ASN1Set;)I
    //   70: invokespecial 197	org/spongycastle/asn1/ASN1Integer:<init>	(I)V
    //   73: invokevirtual 174	org/spongycastle/asn1/BERSequenceGenerator:addObject	(Lorg/spongycastle/asn1/ASN1Encodable;)V
    //   76: aload_0
    //   77: getfield 74	org/spongycastle/cms/CMSEnvelopedDataStreamGenerator:originatorInfo	Lorg/spongycastle/asn1/cms/OriginatorInfo;
    //   80: ifnull +21 -> 101
    //   83: aload 6
    //   85: new 199	org/spongycastle/asn1/DERTaggedObject
    //   88: dup
    //   89: iconst_0
    //   90: iconst_0
    //   91: aload_0
    //   92: getfield 74	org/spongycastle/cms/CMSEnvelopedDataStreamGenerator:originatorInfo	Lorg/spongycastle/asn1/cms/OriginatorInfo;
    //   95: invokespecial 202	org/spongycastle/asn1/DERTaggedObject:<init>	(ZILorg/spongycastle/asn1/ASN1Encodable;)V
    //   98: invokevirtual 174	org/spongycastle/asn1/BERSequenceGenerator:addObject	(Lorg/spongycastle/asn1/ASN1Encodable;)V
    //   101: aload 6
    //   103: invokevirtual 178	org/spongycastle/asn1/BERSequenceGenerator:getRawOutputStream	()Ljava/io/OutputStream;
    //   106: aload 7
    //   108: invokevirtual 208	org/spongycastle/asn1/ASN1Set:getEncoded	()[B
    //   111: invokevirtual 214	java/io/OutputStream:write	([B)V
    //   114: new 165	org/spongycastle/asn1/BERSequenceGenerator
    //   117: dup
    //   118: aload 6
    //   120: invokevirtual 178	org/spongycastle/asn1/BERSequenceGenerator:getRawOutputStream	()Ljava/io/OutputStream;
    //   123: invokespecial 168	org/spongycastle/asn1/BERSequenceGenerator:<init>	(Ljava/io/OutputStream;)V
    //   126: astore 8
    //   128: aload 8
    //   130: getstatic 114	org/spongycastle/asn1/cms/CMSObjectIdentifiers:data	Lorg/spongycastle/asn1/ASN1ObjectIdentifier;
    //   133: invokevirtual 174	org/spongycastle/asn1/BERSequenceGenerator:addObject	(Lorg/spongycastle/asn1/ASN1Encodable;)V
    //   136: aload_3
    //   137: invokeinterface 218 1 0
    //   142: astore 9
    //   144: aload 8
    //   146: invokevirtual 178	org/spongycastle/asn1/BERSequenceGenerator:getRawOutputStream	()Ljava/io/OutputStream;
    //   149: aload 9
    //   151: invokevirtual 221	org/spongycastle/asn1/x509/AlgorithmIdentifier:getEncoded	()[B
    //   154: invokevirtual 214	java/io/OutputStream:write	([B)V
    //   157: new 223	org/spongycastle/cms/CMSEnvelopedDataStreamGenerator$CmsEnvelopedDataOutputStream
    //   160: dup
    //   161: aload_0
    //   162: aload_3
    //   163: aload 8
    //   165: invokevirtual 178	org/spongycastle/asn1/BERSequenceGenerator:getRawOutputStream	()Ljava/io/OutputStream;
    //   168: iconst_0
    //   169: iconst_0
    //   170: aload_0
    //   171: getfield 225	org/spongycastle/cms/CMSEnvelopedDataStreamGenerator:_bufferSize	I
    //   174: invokestatic 229	org/spongycastle/cms/CMSUtils:createBEROctetOutputStream	(Ljava/io/OutputStream;IZI)Ljava/io/OutputStream;
    //   177: invokeinterface 233 2 0
    //   182: aload 4
    //   184: aload 6
    //   186: aload 8
    //   188: invokespecial 236	org/spongycastle/cms/CMSEnvelopedDataStreamGenerator$CmsEnvelopedDataOutputStream:<init>	(Lorg/spongycastle/cms/CMSEnvelopedDataStreamGenerator;Ljava/io/OutputStream;Lorg/spongycastle/asn1/BERSequenceGenerator;Lorg/spongycastle/asn1/BERSequenceGenerator;Lorg/spongycastle/asn1/BERSequenceGenerator;)V
    //   191: areturn
    //   192: new 238	org/spongycastle/asn1/DERSet
    //   195: dup
    //   196: aload_2
    //   197: invokespecial 239	org/spongycastle/asn1/DERSet:<init>	(Lorg/spongycastle/asn1/ASN1EncodableVector;)V
    //   200: astore 7
    //   202: goto -151 -> 51
    //   205: astore 5
    //   207: new 25	org/spongycastle/cms/CMSException
    //   210: dup
    //   211: ldc 241
    //   213: aload 5
    //   215: invokespecial 244	org/spongycastle/cms/CMSException:<init>	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   218: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	219	0	this	CMSEnvelopedDataStreamGenerator
    //   0	219	1	paramOutputStream	OutputStream
    //   0	219	2	paramASN1EncodableVector	ASN1EncodableVector
    //   0	219	3	paramOutputEncryptor	OutputEncryptor
    //   8	175	4	localBERSequenceGenerator1	BERSequenceGenerator
    //   205	9	5	localIOException	IOException
    //   32	153	6	localBERSequenceGenerator2	BERSequenceGenerator
    //   49	152	7	localObject	java.lang.Object
    //   126	61	8	localBERSequenceGenerator3	BERSequenceGenerator
    //   142	8	9	localAlgorithmIdentifier	AlgorithmIdentifier
    // Exception table:
    //   from	to	target	type
    //   0	51	205	java/io/IOException
    //   51	101	205	java/io/IOException
    //   101	192	205	java/io/IOException
    //   192	202	205	java/io/IOException
  }
  
  public OutputStream open(OutputStream paramOutputStream, OutputEncryptor paramOutputEncryptor)
    throws CMSException, IOException
  {
    return doOpen(new ASN1ObjectIdentifier(CMSObjectIdentifiers.data.getId()), paramOutputStream, paramOutputEncryptor);
  }
  
  protected OutputStream open(ASN1ObjectIdentifier paramASN1ObjectIdentifier, OutputStream paramOutputStream, ASN1EncodableVector paramASN1EncodableVector, OutputEncryptor paramOutputEncryptor)
    throws IOException
  {
    BERSequenceGenerator localBERSequenceGenerator1 = new BERSequenceGenerator(paramOutputStream);
    localBERSequenceGenerator1.addObject(CMSObjectIdentifiers.envelopedData);
    BERSequenceGenerator localBERSequenceGenerator2 = new BERSequenceGenerator(localBERSequenceGenerator1.getRawOutputStream(), 0, true);
    localBERSequenceGenerator2.addObject(getVersion());
    if (this.originatorInfo != null) {
      localBERSequenceGenerator2.addObject(new DERTaggedObject(false, 0, this.originatorInfo));
    }
    if (this._berEncodeRecipientSet) {
      localBERSequenceGenerator2.getRawOutputStream().write(new BERSet(paramASN1EncodableVector).getEncoded());
    }
    for (;;)
    {
      BERSequenceGenerator localBERSequenceGenerator3 = new BERSequenceGenerator(localBERSequenceGenerator2.getRawOutputStream());
      localBERSequenceGenerator3.addObject(paramASN1ObjectIdentifier);
      AlgorithmIdentifier localAlgorithmIdentifier = paramOutputEncryptor.getAlgorithmIdentifier();
      localBERSequenceGenerator3.getRawOutputStream().write(localAlgorithmIdentifier.getEncoded());
      return new CmsEnvelopedDataOutputStream(paramOutputEncryptor.getOutputStream(CMSUtils.createBEROctetOutputStream(localBERSequenceGenerator3.getRawOutputStream(), 0, false, this._bufferSize)), localBERSequenceGenerator1, localBERSequenceGenerator2, localBERSequenceGenerator3);
      localBERSequenceGenerator2.getRawOutputStream().write(new DERSet(paramASN1EncodableVector).getEncoded());
    }
  }
  
  public OutputStream open(ASN1ObjectIdentifier paramASN1ObjectIdentifier, OutputStream paramOutputStream, OutputEncryptor paramOutputEncryptor)
    throws CMSException, IOException
  {
    return doOpen(paramASN1ObjectIdentifier, paramOutputStream, paramOutputEncryptor);
  }
  
  public void setBEREncodeRecipients(boolean paramBoolean)
  {
    this._berEncodeRecipientSet = paramBoolean;
  }
  
  public void setBufferSize(int paramInt)
  {
    this._bufferSize = paramInt;
  }
  
  private class CmsEnvelopedDataOutputStream
    extends OutputStream
  {
    private BERSequenceGenerator _cGen;
    private BERSequenceGenerator _eiGen;
    private BERSequenceGenerator _envGen;
    private OutputStream _out;
    
    public CmsEnvelopedDataOutputStream(OutputStream paramOutputStream, BERSequenceGenerator paramBERSequenceGenerator1, BERSequenceGenerator paramBERSequenceGenerator2, BERSequenceGenerator paramBERSequenceGenerator3)
    {
      this._out = paramOutputStream;
      this._cGen = paramBERSequenceGenerator1;
      this._envGen = paramBERSequenceGenerator2;
      this._eiGen = paramBERSequenceGenerator3;
    }
    
    public void close()
      throws IOException
    {
      this._out.close();
      this._eiGen.close();
      if (CMSEnvelopedDataStreamGenerator.this.unprotectedAttributeGenerator != null)
      {
        BERSet localBERSet = new BERSet(CMSEnvelopedDataStreamGenerator.this.unprotectedAttributeGenerator.getAttributes(new HashMap()).toASN1EncodableVector());
        this._envGen.addObject(new DERTaggedObject(false, 1, localBERSet));
      }
      this._envGen.close();
      this._cGen.close();
    }
    
    public void write(int paramInt)
      throws IOException
    {
      this._out.write(paramInt);
    }
    
    public void write(byte[] paramArrayOfByte)
      throws IOException
    {
      this._out.write(paramArrayOfByte);
    }
    
    public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
      throws IOException
    {
      this._out.write(paramArrayOfByte, paramInt1, paramInt2);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.CMSEnvelopedDataStreamGenerator
 * JD-Core Version:    0.7.0.1
 */