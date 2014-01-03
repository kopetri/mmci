package org.spongycastle.math.ec;

class WTauNafPreCompInfo
  implements PreCompInfo
{
  private ECPoint.F2m[] preComp = null;
  
  WTauNafPreCompInfo(ECPoint.F2m[] paramArrayOfF2m)
  {
    this.preComp = paramArrayOfF2m;
  }
  
  protected ECPoint.F2m[] getPreComp()
  {
    return this.preComp;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.math.ec.WTauNafPreCompInfo
 * JD-Core Version:    0.7.0.1
 */