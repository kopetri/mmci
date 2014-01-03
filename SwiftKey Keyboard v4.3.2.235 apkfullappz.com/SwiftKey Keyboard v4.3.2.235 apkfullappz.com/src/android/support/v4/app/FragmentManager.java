package android.support.v4.app;

public abstract class FragmentManager
{
  public abstract FragmentTransaction beginTransaction();
  
  public abstract boolean executePendingTransactions();
  
  public abstract Fragment findFragmentByTag(String paramString);
  
  public abstract void popBackStack(int paramInt1, int paramInt2);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     android.support.v4.app.FragmentManager
 * JD-Core Version:    0.7.0.1
 */