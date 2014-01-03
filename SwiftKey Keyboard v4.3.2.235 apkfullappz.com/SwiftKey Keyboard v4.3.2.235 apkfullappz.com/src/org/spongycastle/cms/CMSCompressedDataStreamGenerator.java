package org.spongycastle.cms;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.DeflaterOutputStream;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.BERSequenceGenerator;
import org.spongycastle.asn1.DERInteger;
import org.spongycastle.asn1.DERObjectIdentifier;
import org.spongycastle.asn1.DERSequenceGenerator;
import org.spongycastle.asn1.cms.CMSObjectIdentifiers;
import org.spongycastle.operator.OutputCompressor;

public class CMSCompressedDataStreamGenerator
{
  public static final String ZLIB = "1.2.840.113549.1.9.16.3.8";
  private int _bufferSize;
  
  public OutputStream open(OutputStream paramOutputStream, String paramString)
    throws IOException
  {
    return open(paramOutputStream, CMSObjectIdentifiers.data.getId(), paramString);
  }
  
  public OutputStream open(OutputStream paramOutputStream, String paramString1, String paramString2)
    throws IOException
  {
    BERSequenceGenerator localBERSequenceGenerator1 = new BERSequenceGenerator(paramOutputStream);
    localBERSequenceGenerator1.addObject(CMSObjectIdentifiers.compressedData);
    BERSequenceGenerator localBERSequenceGenerator2 = new BERSequenceGenerator(localBERSequenceGenerator1.getRawOutputStream(), 0, true);
    localBERSequenceGenerator2.addObject(new DERInteger(0));
    DERSequenceGenerator localDERSequenceGenerator = new DERSequenceGenerator(localBERSequenceGenerator2.getRawOutputStream());
    localDERSequenceGenerator.addObject(new DERObjectIdentifier("1.2.840.113549.1.9.16.3.8"));
    localDERSequenceGenerator.close();
    BERSequenceGenerator localBERSequenceGenerator3 = new BERSequenceGenerator(localBERSequenceGenerator2.getRawOutputStream());
    localBERSequenceGenerator3.addObject(new DERObjectIdentifier(paramString1));
    return new CmsCompressedOutputStream(new DeflaterOutputStream(CMSUtils.createBEROctetOutputStream(localBERSequenceGenerator3.getRawOutputStream(), 0, true, this._bufferSize)), localBERSequenceGenerator1, localBERSequenceGenerator2, localBERSequenceGenerator3);
  }
  
  public OutputStream open(OutputStream paramOutputStream, OutputCompressor paramOutputCompressor)
    throws IOException
  {
    return open(CMSObjectIdentifiers.data, paramOutputStream, paramOutputCompressor);
  }
  
  public OutputStream open(ASN1ObjectIdentifier paramASN1ObjectIdentifier, OutputStream paramOutputStream, OutputCompressor paramOutputCompressor)
    throws IOException
  {
    BERSequenceGenerator localBERSequenceGenerator1 = new BERSequenceGenerator(paramOutputStream);
    localBERSequenceGenerator1.addObject(CMSObjectIdentifiers.compressedData);
    BERSequenceGenerator localBERSequenceGenerator2 = new BERSequenceGenerator(localBERSequenceGenerator1.getRawOutputStream(), 0, true);
    localBERSequenceGenerator2.addObject(new DERInteger(0));
    localBERSequenceGenerator2.addObject(paramOutputCompressor.getAlgorithmIdentifier());
    BERSequenceGenerator localBERSequenceGenerator3 = new BERSequenceGenerator(localBERSequenceGenerator2.getRawOutputStream());
    localBERSequenceGenerator3.addObject(paramASN1ObjectIdentifier);
    return new CmsCompressedOutputStream(paramOutputCompressor.getOutputStream(CMSUtils.createBEROctetOutputStream(localBERSequenceGenerator3.getRawOutputStream(), 0, true, this._bufferSize)), localBERSequenceGenerator1, localBERSequenceGenerator2, localBERSequenceGenerator3);
  }
  
  public void setBufferSize(int paramInt)
  {
    this._bufferSize = paramInt;
  }
  
  private class CmsCompressedOutputStream
    extends OutputStream
  {
    private BERSequenceGenerator _cGen;
    private BERSequenceGenerator _eiGen;
    private OutputStream _out;
    private BERSequenceGenerator _sGen;
    
    CmsCompressedOutputStream(OutputStream paramOutputStream, BERSequenceGenerator paramBERSequenceGenerator1, BERSequenceGenerator paramBERSequenceGenerator2, BERSequenceGenerator paramBERSequenceGenerator3)
    {
      this._out = paramOutputStream;
      this._sGen = paramBERSequenceGenerator1;
      this._cGen = paramBERSequenceGenerator2;
      this._eiGen = paramBERSequenceGenerator3;
    }
    
    public void close()
      throws IOException
    {
      this._out.close();
      this._eiGen.close();
      this._cGen.close();
      this._sGen.close();
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
 * Qualified Name:     org.spongycastle.cms.CMSCompressedDataStreamGenerator
 * JD-Core Version:    0.7.0.1
 */