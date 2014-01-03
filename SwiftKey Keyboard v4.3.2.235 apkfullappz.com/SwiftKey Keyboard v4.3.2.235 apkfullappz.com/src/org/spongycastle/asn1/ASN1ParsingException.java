package org.spongycastle.asn1;

public class ASN1ParsingException
  extends IllegalStateException
{
  private Throwable cause;
  
  public ASN1ParsingException(String paramString)
  {
    super(paramString);
  }
  
  public ASN1ParsingException(String paramString, Throwable paramThrowable)
  {
    super(paramString);
    this.cause = paramThrowable;
  }
  
  public Throwable getCause()
  {
    return this.cause;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.ASN1ParsingException
 * JD-Core Version:    0.7.0.1
 */