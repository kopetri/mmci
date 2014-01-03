package org.spongycastle.crypto.generators;

import org.spongycastle.crypto.Digest;

public class KDF2BytesGenerator
  extends BaseKDFBytesGenerator
{
  public KDF2BytesGenerator(Digest paramDigest)
  {
    super(1, paramDigest);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.generators.KDF2BytesGenerator
 * JD-Core Version:    0.7.0.1
 */