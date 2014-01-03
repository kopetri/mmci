package org.spongycastle.cms;

public class CMSAttributeTableGenerationException
  extends CMSRuntimeException
{
  Exception e;
  
  public CMSAttributeTableGenerationException(String paramString)
  {
    super(paramString);
  }
  
  public CMSAttributeTableGenerationException(String paramString, Exception paramException)
  {
    super(paramString);
    this.e = paramException;
  }
  
  public Throwable getCause()
  {
    return this.e;
  }
  
  public Exception getUnderlyingException()
  {
    return this.e;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.CMSAttributeTableGenerationException
 * JD-Core Version:    0.7.0.1
 */