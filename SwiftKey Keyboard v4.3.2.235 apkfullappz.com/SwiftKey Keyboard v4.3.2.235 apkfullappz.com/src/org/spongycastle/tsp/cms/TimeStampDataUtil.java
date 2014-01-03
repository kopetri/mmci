package org.spongycastle.tsp.cms;

import java.io.IOException;
import java.io.OutputStream;
import org.spongycastle.asn1.cms.AttributeTable;
import org.spongycastle.asn1.cms.ContentInfo;
import org.spongycastle.asn1.cms.Evidence;
import org.spongycastle.asn1.cms.TimeStampAndCRL;
import org.spongycastle.asn1.cms.TimeStampTokenEvidence;
import org.spongycastle.asn1.cms.TimeStampedData;
import org.spongycastle.asn1.cms.TimeStampedDataParser;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.cms.CMSException;
import org.spongycastle.operator.DigestCalculator;
import org.spongycastle.operator.DigestCalculatorProvider;
import org.spongycastle.operator.OperatorCreationException;
import org.spongycastle.tsp.TSPException;
import org.spongycastle.tsp.TimeStampToken;
import org.spongycastle.tsp.TimeStampTokenInfo;
import org.spongycastle.util.Arrays;

class TimeStampDataUtil
{
  private final MetaDataUtil metaDataUtil;
  private final TimeStampAndCRL[] timeStamps;
  
  TimeStampDataUtil(TimeStampedData paramTimeStampedData)
  {
    this.metaDataUtil = new MetaDataUtil(paramTimeStampedData.getMetaData());
    this.timeStamps = paramTimeStampedData.getTemporalEvidence().getTstEvidence().toTimeStampAndCRLArray();
  }
  
  TimeStampDataUtil(TimeStampedDataParser paramTimeStampedDataParser)
    throws IOException
  {
    this.metaDataUtil = new MetaDataUtil(paramTimeStampedDataParser.getMetaData());
    this.timeStamps = paramTimeStampedDataParser.getTemporalEvidence().getTstEvidence().toTimeStampAndCRLArray();
  }
  
  private void compareDigest(TimeStampToken paramTimeStampToken, byte[] paramArrayOfByte)
    throws ImprintDigestInvalidException
  {
    if (!Arrays.areEqual(paramArrayOfByte, paramTimeStampToken.getTimeStampInfo().getMessageImprintDigest())) {
      throw new ImprintDigestInvalidException("hash calculated is different from MessageImprintDigest found in TimeStampToken", paramTimeStampToken);
    }
  }
  
  byte[] calculateNextHash(DigestCalculator paramDigestCalculator)
    throws CMSException
  {
    TimeStampAndCRL localTimeStampAndCRL = this.timeStamps[(-1 + this.timeStamps.length)];
    OutputStream localOutputStream = paramDigestCalculator.getOutputStream();
    try
    {
      localOutputStream.write(localTimeStampAndCRL.getEncoded("DER"));
      localOutputStream.close();
      byte[] arrayOfByte = paramDigestCalculator.getDigest();
      return arrayOfByte;
    }
    catch (IOException localIOException)
    {
      throw new CMSException("exception calculating hash: " + localIOException.getMessage(), localIOException);
    }
  }
  
  String getFileName()
  {
    return this.metaDataUtil.getFileName();
  }
  
  String getMediaType()
  {
    return this.metaDataUtil.getMediaType();
  }
  
  DigestCalculator getMessageImprintDigestCalculator(DigestCalculatorProvider paramDigestCalculatorProvider)
    throws OperatorCreationException
  {
    try
    {
      DigestCalculator localDigestCalculator = paramDigestCalculatorProvider.get(new AlgorithmIdentifier(getTimeStampToken(this.timeStamps[0]).getTimeStampInfo().getMessageImprintAlgOID()));
      initialiseMessageImprintDigestCalculator(localDigestCalculator);
      return localDigestCalculator;
    }
    catch (CMSException localCMSException)
    {
      throw new OperatorCreationException("unable to extract algorithm ID: " + localCMSException.getMessage(), localCMSException);
    }
  }
  
  AttributeTable getOtherMetaData()
  {
    return new AttributeTable(this.metaDataUtil.getOtherMetaData());
  }
  
  TimeStampToken getTimeStampToken(TimeStampAndCRL paramTimeStampAndCRL)
    throws CMSException
  {
    ContentInfo localContentInfo = paramTimeStampAndCRL.getTimeStampToken();
    try
    {
      TimeStampToken localTimeStampToken = new TimeStampToken(localContentInfo);
      return localTimeStampToken;
    }
    catch (IOException localIOException)
    {
      throw new CMSException("unable to parse token data: " + localIOException.getMessage(), localIOException);
    }
    catch (TSPException localTSPException)
    {
      if ((localTSPException.getCause() instanceof CMSException)) {
        throw ((CMSException)localTSPException.getCause());
      }
      throw new CMSException("token data invalid: " + localTSPException.getMessage(), localTSPException);
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      throw new CMSException("token data invalid: " + localIllegalArgumentException.getMessage(), localIllegalArgumentException);
    }
  }
  
  TimeStampToken[] getTimeStampTokens()
    throws CMSException
  {
    TimeStampToken[] arrayOfTimeStampToken = new TimeStampToken[this.timeStamps.length];
    for (int i = 0; i < this.timeStamps.length; i++) {
      arrayOfTimeStampToken[i] = getTimeStampToken(this.timeStamps[i]);
    }
    return arrayOfTimeStampToken;
  }
  
  TimeStampAndCRL[] getTimeStamps()
  {
    return this.timeStamps;
  }
  
  void initialiseMessageImprintDigestCalculator(DigestCalculator paramDigestCalculator)
    throws CMSException
  {
    this.metaDataUtil.initialiseMessageImprintDigestCalculator(paramDigestCalculator);
  }
  
  void validate(DigestCalculatorProvider paramDigestCalculatorProvider, byte[] paramArrayOfByte)
    throws ImprintDigestInvalidException, CMSException
  {
    byte[] arrayOfByte = paramArrayOfByte;
    int i = 0;
    while (i < this.timeStamps.length) {
      try
      {
        TimeStampToken localTimeStampToken = getTimeStampToken(this.timeStamps[i]);
        if (i > 0)
        {
          DigestCalculator localDigestCalculator = paramDigestCalculatorProvider.get(localTimeStampToken.getTimeStampInfo().getHashAlgorithm());
          localDigestCalculator.getOutputStream().write(this.timeStamps[(i - 1)].getEncoded("DER"));
          arrayOfByte = localDigestCalculator.getDigest();
        }
        compareDigest(localTimeStampToken, arrayOfByte);
        i++;
      }
      catch (IOException localIOException)
      {
        throw new CMSException("exception calculating hash: " + localIOException.getMessage(), localIOException);
      }
      catch (OperatorCreationException localOperatorCreationException)
      {
        throw new CMSException("cannot create digest: " + localOperatorCreationException.getMessage(), localOperatorCreationException);
      }
    }
  }
  
  /* Error */
  void validate(DigestCalculatorProvider paramDigestCalculatorProvider, byte[] paramArrayOfByte, TimeStampToken paramTimeStampToken)
    throws ImprintDigestInvalidException, CMSException
  {
    // Byte code:
    //   0: aload_2
    //   1: astore 4
    //   3: aload_3
    //   4: invokevirtual 216	org/spongycastle/tsp/TimeStampToken:getEncoded	()[B
    //   7: astore 6
    //   9: iconst_0
    //   10: istore 7
    //   12: iload 7
    //   14: aload_0
    //   15: getfield 44	org/spongycastle/tsp/cms/TimeStampDataUtil:timeStamps	[Lorg/spongycastle/asn1/cms/TimeStampAndCRL;
    //   18: arraylength
    //   19: if_icmpge +198 -> 217
    //   22: aload_0
    //   23: aload_0
    //   24: getfield 44	org/spongycastle/tsp/cms/TimeStampDataUtil:timeStamps	[Lorg/spongycastle/asn1/cms/TimeStampAndCRL;
    //   27: iload 7
    //   29: aaload
    //   30: invokevirtual 145	org/spongycastle/tsp/cms/TimeStampDataUtil:getTimeStampToken	(Lorg/spongycastle/asn1/cms/TimeStampAndCRL;)Lorg/spongycastle/tsp/TimeStampToken;
    //   33: astore 10
    //   35: iload 7
    //   37: ifle +52 -> 89
    //   40: aload_1
    //   41: aload 10
    //   43: invokevirtual 61	org/spongycastle/tsp/TimeStampToken:getTimeStampInfo	()Lorg/spongycastle/tsp/TimeStampTokenInfo;
    //   46: invokevirtual 208	org/spongycastle/tsp/TimeStampTokenInfo:getHashAlgorithm	()Lorg/spongycastle/asn1/x509/AlgorithmIdentifier;
    //   49: invokeinterface 158 2 0
    //   54: astore 11
    //   56: aload 11
    //   58: invokeinterface 88 1 0
    //   63: aload_0
    //   64: getfield 44	org/spongycastle/tsp/cms/TimeStampDataUtil:timeStamps	[Lorg/spongycastle/asn1/cms/TimeStampAndCRL;
    //   67: iload 7
    //   69: iconst_1
    //   70: isub
    //   71: aaload
    //   72: ldc 90
    //   74: invokevirtual 96	org/spongycastle/asn1/cms/TimeStampAndCRL:getEncoded	(Ljava/lang/String;)[B
    //   77: invokevirtual 102	java/io/OutputStream:write	([B)V
    //   80: aload 11
    //   82: invokeinterface 108 1 0
    //   87: astore 4
    //   89: aload_0
    //   90: aload 10
    //   92: aload 4
    //   94: invokespecial 210	org/spongycastle/tsp/cms/TimeStampDataUtil:compareDigest	(Lorg/spongycastle/tsp/TimeStampToken;[B)V
    //   97: aload 10
    //   99: invokevirtual 216	org/spongycastle/tsp/TimeStampToken:getEncoded	()[B
    //   102: aload 6
    //   104: invokestatic 73	org/spongycastle/util/Arrays:areEqual	([B[B)Z
    //   107: istore 12
    //   109: iload 12
    //   111: ifeq +100 -> 211
    //   114: return
    //   115: astore 5
    //   117: new 82	org/spongycastle/cms/CMSException
    //   120: dup
    //   121: new 110	java/lang/StringBuilder
    //   124: dup
    //   125: ldc 218
    //   127: invokespecial 115	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   130: aload 5
    //   132: invokevirtual 119	java/io/IOException:getMessage	()Ljava/lang/String;
    //   135: invokevirtual 123	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   138: invokevirtual 126	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   141: aload 5
    //   143: invokespecial 129	org/spongycastle/cms/CMSException:<init>	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   146: athrow
    //   147: astore 9
    //   149: new 82	org/spongycastle/cms/CMSException
    //   152: dup
    //   153: new 110	java/lang/StringBuilder
    //   156: dup
    //   157: ldc 112
    //   159: invokespecial 115	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   162: aload 9
    //   164: invokevirtual 119	java/io/IOException:getMessage	()Ljava/lang/String;
    //   167: invokevirtual 123	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   170: invokevirtual 126	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   173: aload 9
    //   175: invokespecial 129	org/spongycastle/cms/CMSException:<init>	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   178: athrow
    //   179: astore 8
    //   181: new 82	org/spongycastle/cms/CMSException
    //   184: dup
    //   185: new 110	java/lang/StringBuilder
    //   188: dup
    //   189: ldc 212
    //   191: invokespecial 115	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   194: aload 8
    //   196: invokevirtual 213	org/spongycastle/operator/OperatorCreationException:getMessage	()Ljava/lang/String;
    //   199: invokevirtual 123	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   202: invokevirtual 126	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   205: aload 8
    //   207: invokespecial 129	org/spongycastle/cms/CMSException:<init>	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   210: athrow
    //   211: iinc 7 1
    //   214: goto -202 -> 12
    //   217: new 55	org/spongycastle/tsp/cms/ImprintDigestInvalidException
    //   220: dup
    //   221: ldc 220
    //   223: aload_3
    //   224: invokespecial 78	org/spongycastle/tsp/cms/ImprintDigestInvalidException:<init>	(Ljava/lang/String;Lorg/spongycastle/tsp/TimeStampToken;)V
    //   227: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	228	0	this	TimeStampDataUtil
    //   0	228	1	paramDigestCalculatorProvider	DigestCalculatorProvider
    //   0	228	2	paramArrayOfByte	byte[]
    //   0	228	3	paramTimeStampToken	TimeStampToken
    //   1	92	4	arrayOfByte1	byte[]
    //   115	27	5	localIOException1	IOException
    //   7	96	6	arrayOfByte2	byte[]
    //   10	202	7	i	int
    //   179	27	8	localOperatorCreationException	OperatorCreationException
    //   147	27	9	localIOException2	IOException
    //   33	65	10	localTimeStampToken	TimeStampToken
    //   54	27	11	localDigestCalculator	DigestCalculator
    //   107	3	12	bool	boolean
    // Exception table:
    //   from	to	target	type
    //   3	9	115	java/io/IOException
    //   22	35	147	java/io/IOException
    //   40	89	147	java/io/IOException
    //   89	109	147	java/io/IOException
    //   22	35	179	org/spongycastle/operator/OperatorCreationException
    //   40	89	179	org/spongycastle/operator/OperatorCreationException
    //   89	109	179	org/spongycastle/operator/OperatorCreationException
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.tsp.cms.TimeStampDataUtil
 * JD-Core Version:    0.7.0.1
 */