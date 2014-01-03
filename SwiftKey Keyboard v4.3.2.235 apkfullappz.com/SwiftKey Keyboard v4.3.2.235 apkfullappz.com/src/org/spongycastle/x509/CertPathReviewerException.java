package org.spongycastle.x509;

import java.security.cert.CertPath;
import java.util.List;
import org.spongycastle.i18n.ErrorBundle;
import org.spongycastle.i18n.LocalizedException;

public class CertPathReviewerException
  extends LocalizedException
{
  private CertPath certPath = null;
  private int index = -1;
  
  public CertPathReviewerException(ErrorBundle paramErrorBundle)
  {
    super(paramErrorBundle);
  }
  
  public CertPathReviewerException(ErrorBundle paramErrorBundle, Throwable paramThrowable)
  {
    super(paramErrorBundle, paramThrowable);
  }
  
  public CertPathReviewerException(ErrorBundle paramErrorBundle, Throwable paramThrowable, CertPath paramCertPath, int paramInt)
  {
    super(paramErrorBundle, paramThrowable);
    if ((paramCertPath == null) || (paramInt == -1)) {
      throw new IllegalArgumentException();
    }
    if ((paramInt < -1) || ((paramCertPath != null) && (paramInt >= paramCertPath.getCertificates().size()))) {
      throw new IndexOutOfBoundsException();
    }
    this.certPath = paramCertPath;
    this.index = paramInt;
  }
  
  public CertPathReviewerException(ErrorBundle paramErrorBundle, CertPath paramCertPath, int paramInt)
  {
    super(paramErrorBundle);
    if ((paramCertPath == null) || (paramInt == -1)) {
      throw new IllegalArgumentException();
    }
    if ((paramInt < -1) || ((paramCertPath != null) && (paramInt >= paramCertPath.getCertificates().size()))) {
      throw new IndexOutOfBoundsException();
    }
    this.certPath = paramCertPath;
    this.index = paramInt;
  }
  
  public CertPath getCertPath()
  {
    return this.certPath;
  }
  
  public int getIndex()
  {
    return this.index;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.x509.CertPathReviewerException
 * JD-Core Version:    0.7.0.1
 */