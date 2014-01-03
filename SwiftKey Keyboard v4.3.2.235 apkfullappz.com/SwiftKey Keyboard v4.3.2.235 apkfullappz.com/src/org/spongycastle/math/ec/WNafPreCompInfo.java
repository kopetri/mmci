package org.spongycastle.math.ec;

class WNafPreCompInfo
  implements PreCompInfo
{
  private ECPoint[] preComp = null;
  private ECPoint twiceP = null;
  
  protected ECPoint[] getPreComp()
  {
    return this.preComp;
  }
  
  protected ECPoint getTwiceP()
  {
    return this.twiceP;
  }
  
  protected void setPreComp(ECPoint[] paramArrayOfECPoint)
  {
    this.preComp = paramArrayOfECPoint;
  }
  
  protected void setTwiceP(ECPoint paramECPoint)
  {
    this.twiceP = paramECPoint;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.math.ec.WNafPreCompInfo
 * JD-Core Version:    0.7.0.1
 */