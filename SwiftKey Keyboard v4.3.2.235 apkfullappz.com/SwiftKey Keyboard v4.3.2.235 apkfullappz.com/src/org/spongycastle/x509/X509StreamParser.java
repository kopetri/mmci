package org.spongycastle.x509;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.util.Collection;
import org.spongycastle.x509.util.StreamParser;
import org.spongycastle.x509.util.StreamParsingException;

public class X509StreamParser
  implements StreamParser
{
  private Provider _provider;
  private X509StreamParserSpi _spi;
  
  private X509StreamParser(Provider paramProvider, X509StreamParserSpi paramX509StreamParserSpi)
  {
    this._provider = paramProvider;
    this._spi = paramX509StreamParserSpi;
  }
  
  private static X509StreamParser createParser(X509Util.Implementation paramImplementation)
  {
    X509StreamParserSpi localX509StreamParserSpi = (X509StreamParserSpi)paramImplementation.getEngine();
    return new X509StreamParser(paramImplementation.getProvider(), localX509StreamParserSpi);
  }
  
  public static X509StreamParser getInstance(String paramString)
    throws NoSuchParserException
  {
    try
    {
      X509StreamParser localX509StreamParser = createParser(X509Util.getImplementation("X509StreamParser", paramString));
      return localX509StreamParser;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      throw new NoSuchParserException(localNoSuchAlgorithmException.getMessage());
    }
  }
  
  public static X509StreamParser getInstance(String paramString1, String paramString2)
    throws NoSuchParserException, NoSuchProviderException
  {
    return getInstance(paramString1, X509Util.getProvider(paramString2));
  }
  
  public static X509StreamParser getInstance(String paramString, Provider paramProvider)
    throws NoSuchParserException
  {
    try
    {
      X509StreamParser localX509StreamParser = createParser(X509Util.getImplementation("X509StreamParser", paramString, paramProvider));
      return localX509StreamParser;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      throw new NoSuchParserException(localNoSuchAlgorithmException.getMessage());
    }
  }
  
  public Provider getProvider()
  {
    return this._provider;
  }
  
  public void init(InputStream paramInputStream)
  {
    this._spi.engineInit(paramInputStream);
  }
  
  public void init(byte[] paramArrayOfByte)
  {
    this._spi.engineInit(new ByteArrayInputStream(paramArrayOfByte));
  }
  
  public Object read()
    throws StreamParsingException
  {
    return this._spi.engineRead();
  }
  
  public Collection readAll()
    throws StreamParsingException
  {
    return this._spi.engineReadAll();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.x509.X509StreamParser
 * JD-Core Version:    0.7.0.1
 */