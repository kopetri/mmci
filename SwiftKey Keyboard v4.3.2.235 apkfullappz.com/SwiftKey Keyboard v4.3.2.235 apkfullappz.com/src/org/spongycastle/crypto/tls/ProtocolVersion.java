package org.spongycastle.crypto.tls;

import java.io.IOException;

public class ProtocolVersion
{
  public static final ProtocolVersion SSLv3 = new ProtocolVersion(768);
  public static final ProtocolVersion TLSv10 = new ProtocolVersion(769);
  public static final ProtocolVersion TLSv11 = new ProtocolVersion(770);
  public static final ProtocolVersion TLSv12 = new ProtocolVersion(771);
  private int version;
  
  private ProtocolVersion(int paramInt)
  {
    this.version = (0xFFFF & paramInt);
  }
  
  public static ProtocolVersion get(int paramInt1, int paramInt2)
    throws IOException
  {
    switch (paramInt1)
    {
    }
    for (;;)
    {
      throw new TlsFatalAlert((short)47);
      switch (paramInt2)
      {
      }
    }
    return SSLv3;
    return TLSv10;
    return TLSv11;
    return TLSv12;
  }
  
  public boolean equals(Object paramObject)
  {
    return this == paramObject;
  }
  
  public int getFullVersion()
  {
    return this.version;
  }
  
  public int getMajorVersion()
  {
    return this.version >> 8;
  }
  
  public int getMinorVersion()
  {
    return 0xFF & this.version;
  }
  
  public int hashCode()
  {
    return this.version;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.tls.ProtocolVersion
 * JD-Core Version:    0.7.0.1
 */