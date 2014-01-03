package org.spongycastle.tsp;

import java.math.BigInteger;
import org.spongycastle.asn1.DERInteger;
import org.spongycastle.asn1.tsp.Accuracy;

public class GenTimeAccuracy
{
  private Accuracy accuracy;
  
  public GenTimeAccuracy(Accuracy paramAccuracy)
  {
    this.accuracy = paramAccuracy;
  }
  
  private String format(int paramInt)
  {
    if (paramInt < 10) {
      return "00" + paramInt;
    }
    if (paramInt < 100) {
      return "0" + paramInt;
    }
    return Integer.toString(paramInt);
  }
  
  private int getTimeComponent(DERInteger paramDERInteger)
  {
    if (paramDERInteger != null) {
      return paramDERInteger.getValue().intValue();
    }
    return 0;
  }
  
  public int getMicros()
  {
    return getTimeComponent(this.accuracy.getMicros());
  }
  
  public int getMillis()
  {
    return getTimeComponent(this.accuracy.getMillis());
  }
  
  public int getSeconds()
  {
    return getTimeComponent(this.accuracy.getSeconds());
  }
  
  public String toString()
  {
    return getSeconds() + "." + format(getMillis()) + format(getMicros());
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.tsp.GenTimeAccuracy
 * JD-Core Version:    0.7.0.1
 */