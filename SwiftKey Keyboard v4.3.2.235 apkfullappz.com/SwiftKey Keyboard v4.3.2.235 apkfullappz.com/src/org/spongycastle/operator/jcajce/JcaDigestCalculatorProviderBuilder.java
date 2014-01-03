package org.spongycastle.operator.jcajce;

import java.io.IOException;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.Provider;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.jcajce.DefaultJcaJceHelper;
import org.spongycastle.jcajce.NamedJcaJceHelper;
import org.spongycastle.jcajce.ProviderJcaJceHelper;
import org.spongycastle.operator.DigestCalculator;
import org.spongycastle.operator.DigestCalculatorProvider;
import org.spongycastle.operator.OperatorCreationException;

public class JcaDigestCalculatorProviderBuilder
{
  private OperatorHelper helper = new OperatorHelper(new DefaultJcaJceHelper());
  
  public DigestCalculatorProvider build()
    throws OperatorCreationException
  {
    new DigestCalculatorProvider()
    {
      public DigestCalculator get(final AlgorithmIdentifier paramAnonymousAlgorithmIdentifier)
        throws OperatorCreationException
      {
        try
        {
          MessageDigest localMessageDigest = JcaDigestCalculatorProviderBuilder.this.helper.createDigest(paramAnonymousAlgorithmIdentifier);
          final JcaDigestCalculatorProviderBuilder.DigestOutputStream localDigestOutputStream = new JcaDigestCalculatorProviderBuilder.DigestOutputStream(JcaDigestCalculatorProviderBuilder.this, localMessageDigest);
          new DigestCalculator()
          {
            public AlgorithmIdentifier getAlgorithmIdentifier()
            {
              return paramAnonymousAlgorithmIdentifier;
            }
            
            public byte[] getDigest()
            {
              return localDigestOutputStream.getDigest();
            }
            
            public OutputStream getOutputStream()
            {
              return localDigestOutputStream;
            }
          };
        }
        catch (GeneralSecurityException localGeneralSecurityException)
        {
          throw new OperatorCreationException("exception on setup: " + localGeneralSecurityException, localGeneralSecurityException);
        }
      }
    };
  }
  
  public JcaDigestCalculatorProviderBuilder setProvider(String paramString)
  {
    this.helper = new OperatorHelper(new NamedJcaJceHelper(paramString));
    return this;
  }
  
  public JcaDigestCalculatorProviderBuilder setProvider(Provider paramProvider)
  {
    this.helper = new OperatorHelper(new ProviderJcaJceHelper(paramProvider));
    return this;
  }
  
  private class DigestOutputStream
    extends OutputStream
  {
    private MessageDigest dig;
    
    DigestOutputStream(MessageDigest paramMessageDigest)
    {
      this.dig = paramMessageDigest;
    }
    
    byte[] getDigest()
    {
      return this.dig.digest();
    }
    
    public void write(int paramInt)
      throws IOException
    {
      this.dig.update((byte)paramInt);
    }
    
    public void write(byte[] paramArrayOfByte)
      throws IOException
    {
      this.dig.update(paramArrayOfByte);
    }
    
    public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
      throws IOException
    {
      this.dig.update(paramArrayOfByte, paramInt1, paramInt2);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder
 * JD-Core Version:    0.7.0.1
 */