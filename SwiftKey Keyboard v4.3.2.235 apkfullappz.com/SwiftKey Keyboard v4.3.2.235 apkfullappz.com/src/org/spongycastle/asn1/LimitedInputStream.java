package org.spongycastle.asn1;

import java.io.InputStream;

abstract class LimitedInputStream
  extends InputStream
{
  protected final InputStream _in;
  private int _limit;
  
  LimitedInputStream(InputStream paramInputStream, int paramInt)
  {
    this._in = paramInputStream;
    this._limit = paramInt;
  }
  
  int getRemaining()
  {
    return this._limit;
  }
  
  protected void setParentEofDetect(boolean paramBoolean)
  {
    if ((this._in instanceof IndefiniteLengthInputStream)) {
      ((IndefiniteLengthInputStream)this._in).setEofOn00(paramBoolean);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.LimitedInputStream
 * JD-Core Version:    0.7.0.1
 */