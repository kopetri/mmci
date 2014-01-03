package org.spongycastle.cms;

import org.spongycastle.util.Selector;

public abstract class RecipientId
  implements Selector
{
  public static final int kek = 1;
  public static final int keyAgree = 2;
  public static final int keyTrans = 0;
  public static final int password = 3;
  private final int type;
  
  protected RecipientId(int paramInt)
  {
    this.type = paramInt;
  }
  
  public abstract Object clone();
  
  public int getType()
  {
    return this.type;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.RecipientId
 * JD-Core Version:    0.7.0.1
 */