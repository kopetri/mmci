package org.spongycastle.tsp;

import java.io.IOException;
import java.math.BigInteger;
import org.spongycastle.asn1.ASN1Boolean;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.tsp.MessageImprint;
import org.spongycastle.asn1.tsp.TimeStampReq;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.Extensions;
import org.spongycastle.asn1.x509.ExtensionsGenerator;

public class TimeStampRequestGenerator
{
  private ASN1Boolean certReq;
  private ExtensionsGenerator extGenerator = new ExtensionsGenerator();
  private ASN1ObjectIdentifier reqPolicy;
  
  public void addExtension(String paramString, boolean paramBoolean, ASN1Encodable paramASN1Encodable)
    throws IOException
  {
    addExtension(paramString, paramBoolean, paramASN1Encodable.toASN1Primitive().getEncoded());
  }
  
  public void addExtension(String paramString, boolean paramBoolean, byte[] paramArrayOfByte)
  {
    this.extGenerator.addExtension(new ASN1ObjectIdentifier(paramString), paramBoolean, paramArrayOfByte);
  }
  
  public void addExtension(ASN1ObjectIdentifier paramASN1ObjectIdentifier, boolean paramBoolean, ASN1Encodable paramASN1Encodable)
    throws TSPIOException
  {
    TSPUtil.addExtension(this.extGenerator, paramASN1ObjectIdentifier, paramBoolean, paramASN1Encodable);
  }
  
  public void addExtension(ASN1ObjectIdentifier paramASN1ObjectIdentifier, boolean paramBoolean, byte[] paramArrayOfByte)
  {
    this.extGenerator.addExtension(paramASN1ObjectIdentifier, paramBoolean, paramArrayOfByte);
  }
  
  public TimeStampRequest generate(String paramString, byte[] paramArrayOfByte)
  {
    return generate(paramString, paramArrayOfByte, null);
  }
  
  public TimeStampRequest generate(String paramString, byte[] paramArrayOfByte, BigInteger paramBigInteger)
  {
    if (paramString == null) {
      throw new IllegalArgumentException("No digest algorithm specified");
    }
    MessageImprint localMessageImprint = new MessageImprint(new AlgorithmIdentifier(new ASN1ObjectIdentifier(paramString), new DERNull()), paramArrayOfByte);
    boolean bool = this.extGenerator.isEmpty();
    Extensions localExtensions = null;
    if (!bool) {
      localExtensions = this.extGenerator.generate();
    }
    if (paramBigInteger != null) {
      return new TimeStampRequest(new TimeStampReq(localMessageImprint, this.reqPolicy, new ASN1Integer(paramBigInteger), this.certReq, localExtensions));
    }
    return new TimeStampRequest(new TimeStampReq(localMessageImprint, this.reqPolicy, null, this.certReq, localExtensions));
  }
  
  public TimeStampRequest generate(ASN1ObjectIdentifier paramASN1ObjectIdentifier, byte[] paramArrayOfByte)
  {
    return generate(paramASN1ObjectIdentifier.getId(), paramArrayOfByte);
  }
  
  public TimeStampRequest generate(ASN1ObjectIdentifier paramASN1ObjectIdentifier, byte[] paramArrayOfByte, BigInteger paramBigInteger)
  {
    return generate(paramASN1ObjectIdentifier.getId(), paramArrayOfByte, paramBigInteger);
  }
  
  public void setCertReq(boolean paramBoolean)
  {
    this.certReq = ASN1Boolean.getInstance(paramBoolean);
  }
  
  public void setReqPolicy(String paramString)
  {
    this.reqPolicy = new ASN1ObjectIdentifier(paramString);
  }
  
  public void setReqPolicy(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
  {
    this.reqPolicy = paramASN1ObjectIdentifier;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.tsp.TimeStampRequestGenerator
 * JD-Core Version:    0.7.0.1
 */