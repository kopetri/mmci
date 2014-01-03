package org.spongycastle.cms;

import java.io.InputStream;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.operator.InputDecryptor;
import org.spongycastle.operator.MacCalculator;
import org.spongycastle.util.io.TeeInputStream;

public class RecipientOperator
{
  private final AlgorithmIdentifier algorithmIdentifier;
  private final Object operator;
  
  public RecipientOperator(InputDecryptor paramInputDecryptor)
  {
    this.algorithmIdentifier = paramInputDecryptor.getAlgorithmIdentifier();
    this.operator = paramInputDecryptor;
  }
  
  public RecipientOperator(MacCalculator paramMacCalculator)
  {
    this.algorithmIdentifier = paramMacCalculator.getAlgorithmIdentifier();
    this.operator = paramMacCalculator;
  }
  
  public InputStream getInputStream(InputStream paramInputStream)
  {
    if ((this.operator instanceof InputDecryptor)) {
      return ((InputDecryptor)this.operator).getInputStream(paramInputStream);
    }
    return new TeeInputStream(paramInputStream, ((MacCalculator)this.operator).getOutputStream());
  }
  
  public byte[] getMac()
  {
    return ((MacCalculator)this.operator).getMac();
  }
  
  public boolean isMacBased()
  {
    return this.operator instanceof MacCalculator;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.RecipientOperator
 * JD-Core Version:    0.7.0.1
 */