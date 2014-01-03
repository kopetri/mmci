package org.spongycastle.crypto.tls;

import java.io.IOException;
import java.util.Hashtable;

public class PSKTlsClient
  implements TlsClient
{
  protected TlsCipherFactory cipherFactory;
  protected TlsClientContext context;
  protected TlsPSKIdentity pskIdentity;
  protected int selectedCipherSuite;
  protected int selectedCompressionMethod;
  
  public PSKTlsClient(TlsCipherFactory paramTlsCipherFactory, TlsPSKIdentity paramTlsPSKIdentity)
  {
    this.cipherFactory = paramTlsCipherFactory;
    this.pskIdentity = paramTlsPSKIdentity;
  }
  
  public PSKTlsClient(TlsPSKIdentity paramTlsPSKIdentity)
  {
    this(new DefaultTlsCipherFactory(), paramTlsPSKIdentity);
  }
  
  protected TlsKeyExchange createPSKKeyExchange(int paramInt)
  {
    return new TlsPSKKeyExchange(this.context, paramInt, this.pskIdentity);
  }
  
  public TlsAuthentication getAuthentication()
    throws IOException
  {
    return null;
  }
  
  public TlsCipher getCipher()
    throws IOException
  {
    switch (this.selectedCipherSuite)
    {
    case 142: 
    case 146: 
    default: 
      throw new TlsFatalAlert((short)80);
    case 139: 
    case 143: 
    case 147: 
      return this.cipherFactory.createCipher(this.context, 7, 2);
    case 140: 
    case 144: 
    case 148: 
      return this.cipherFactory.createCipher(this.context, 8, 2);
    }
    return this.cipherFactory.createCipher(this.context, 9, 2);
  }
  
  public int[] getCipherSuites()
  {
    return new int[] { 145, 144, 143, 149, 148, 147, 141, 140, 139 };
  }
  
  public Hashtable getClientExtensions()
    throws IOException
  {
    return null;
  }
  
  public ProtocolVersion getClientVersion()
  {
    return ProtocolVersion.TLSv10;
  }
  
  public TlsCompression getCompression()
    throws IOException
  {
    switch (this.selectedCompressionMethod)
    {
    default: 
      throw new TlsFatalAlert((short)80);
    }
    return new TlsNullCompression();
  }
  
  public short[] getCompressionMethods()
  {
    return new short[] { 0 };
  }
  
  public TlsKeyExchange getKeyExchange()
    throws IOException
  {
    switch (this.selectedCipherSuite)
    {
    case 142: 
    case 146: 
    default: 
      throw new TlsFatalAlert((short)80);
    case 139: 
    case 140: 
    case 141: 
      return createPSKKeyExchange(13);
    case 147: 
    case 148: 
    case 149: 
      return createPSKKeyExchange(15);
    }
    return createPSKKeyExchange(14);
  }
  
  public void init(TlsClientContext paramTlsClientContext)
  {
    this.context = paramTlsClientContext;
  }
  
  public void notifySecureRenegotiation(boolean paramBoolean)
    throws IOException
  {}
  
  public void notifySelectedCipherSuite(int paramInt)
  {
    this.selectedCipherSuite = paramInt;
  }
  
  public void notifySelectedCompressionMethod(short paramShort)
  {
    this.selectedCompressionMethod = paramShort;
  }
  
  public void notifyServerVersion(ProtocolVersion paramProtocolVersion)
    throws IOException
  {
    if (!ProtocolVersion.TLSv10.equals(paramProtocolVersion)) {
      throw new TlsFatalAlert((short)47);
    }
  }
  
  public void notifySessionID(byte[] paramArrayOfByte) {}
  
  public void processServerExtensions(Hashtable paramHashtable) {}
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.tls.PSKTlsClient
 * JD-Core Version:    0.7.0.1
 */