package org.spongycastle.jce.provider;

import org.spongycastle.asn1.x509.ReasonFlags;

class ReasonsMask
{
  static final ReasonsMask allReasons = new ReasonsMask(33023);
  private int _reasons;
  
  ReasonsMask()
  {
    this(0);
  }
  
  private ReasonsMask(int paramInt)
  {
    this._reasons = paramInt;
  }
  
  ReasonsMask(ReasonFlags paramReasonFlags)
  {
    this._reasons = paramReasonFlags.intValue();
  }
  
  void addReasons(ReasonsMask paramReasonsMask)
  {
    this._reasons |= paramReasonsMask.getReasons();
  }
  
  int getReasons()
  {
    return this._reasons;
  }
  
  boolean hasNewReasons(ReasonsMask paramReasonsMask)
  {
    return (this._reasons | paramReasonsMask.getReasons() ^ this._reasons) != 0;
  }
  
  ReasonsMask intersect(ReasonsMask paramReasonsMask)
  {
    ReasonsMask localReasonsMask = new ReasonsMask();
    localReasonsMask.addReasons(new ReasonsMask(this._reasons & paramReasonsMask.getReasons()));
    return localReasonsMask;
  }
  
  boolean isAllReasons()
  {
    return this._reasons == allReasons._reasons;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jce.provider.ReasonsMask
 * JD-Core Version:    0.7.0.1
 */