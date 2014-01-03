package org.spongycastle.operator;

import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public abstract interface KeyWrapper
{
  public abstract byte[] generateWrappedKey(GenericKey paramGenericKey)
    throws OperatorException;
  
  public abstract AlgorithmIdentifier getAlgorithmIdentifier();
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.operator.KeyWrapper
 * JD-Core Version:    0.7.0.1
 */