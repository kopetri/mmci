package org.spongycastle.util.io.pem;

import java.io.IOException;

public abstract interface PemObjectParser
{
  public abstract Object parseObject(PemObject paramPemObject)
    throws IOException;
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.util.io.pem.PemObjectParser
 * JD-Core Version:    0.7.0.1
 */