package org.spongycastle.asn1.x500.style;

class X500NameTokenizer
{
  private StringBuffer buf = new StringBuffer();
  private int index;
  private char seperator;
  private String value;
  
  public X500NameTokenizer(String paramString)
  {
    this(paramString, ',');
  }
  
  public X500NameTokenizer(String paramString, char paramChar)
  {
    this.value = paramString;
    this.index = -1;
    this.seperator = paramChar;
  }
  
  public boolean hasMoreTokens()
  {
    return this.index != this.value.length();
  }
  
  public String nextToken()
  {
    if (this.index == this.value.length()) {
      return null;
    }
    int i = 1 + this.index;
    int j = 0;
    int k = 0;
    this.buf.setLength(0);
    if (i != this.value.length())
    {
      char c = this.value.charAt(i);
      if (c == '"') {
        if (k == 0) {
          if (j == 0)
          {
            j = 1;
            label73:
            k = 0;
          }
        }
      }
      for (;;)
      {
        i++;
        break;
        j = 0;
        break label73;
        this.buf.append(c);
        break label73;
        if ((k != 0) || (j != 0))
        {
          if ((c == '#') && (this.buf.charAt(-1 + this.buf.length()) == '=')) {
            this.buf.append('\\');
          }
          for (;;)
          {
            this.buf.append(c);
            k = 0;
            break;
            if ((c == '+') && (this.seperator != '+')) {
              this.buf.append('\\');
            }
          }
        }
        if (c == '\\')
        {
          k = 1;
        }
        else
        {
          if (c == this.seperator) {
            break label223;
          }
          this.buf.append(c);
        }
      }
    }
    label223:
    this.index = i;
    return this.buf.toString().trim();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x500.style.X500NameTokenizer
 * JD-Core Version:    0.7.0.1
 */