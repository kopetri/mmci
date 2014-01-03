package org.spongycastle.eac;

import java.io.OutputStream;
import org.spongycastle.asn1.DERApplicationSpecific;
import org.spongycastle.asn1.eac.CVCertificate;
import org.spongycastle.asn1.eac.CertificateBody;
import org.spongycastle.asn1.eac.CertificateHolderAuthorization;
import org.spongycastle.asn1.eac.CertificateHolderReference;
import org.spongycastle.asn1.eac.CertificationAuthorityReference;
import org.spongycastle.asn1.eac.PackedDate;
import org.spongycastle.asn1.eac.PublicKeyDataObject;
import org.spongycastle.eac.operator.EACSigner;

public class EACCertificateBuilder
{
  private static final byte[] ZeroArray = { 0 };
  private PackedDate certificateEffectiveDate;
  private PackedDate certificateExpirationDate;
  private CertificateHolderAuthorization certificateHolderAuthorization;
  private CertificateHolderReference certificateHolderReference;
  private CertificationAuthorityReference certificationAuthorityReference;
  private PublicKeyDataObject publicKey;
  
  public EACCertificateBuilder(CertificationAuthorityReference paramCertificationAuthorityReference, PublicKeyDataObject paramPublicKeyDataObject, CertificateHolderReference paramCertificateHolderReference, CertificateHolderAuthorization paramCertificateHolderAuthorization, PackedDate paramPackedDate1, PackedDate paramPackedDate2)
  {
    this.certificationAuthorityReference = paramCertificationAuthorityReference;
    this.publicKey = paramPublicKeyDataObject;
    this.certificateHolderReference = paramCertificateHolderReference;
    this.certificateHolderAuthorization = paramCertificateHolderAuthorization;
    this.certificateEffectiveDate = paramPackedDate1;
    this.certificateExpirationDate = paramPackedDate2;
  }
  
  private CertificateBody buildBody()
  {
    return new CertificateBody(new DERApplicationSpecific(41, ZeroArray), this.certificationAuthorityReference, this.publicKey, this.certificateHolderReference, this.certificateHolderAuthorization, this.certificateEffectiveDate, this.certificateExpirationDate);
  }
  
  public EACCertificateHolder build(EACSigner paramEACSigner)
    throws EACException
  {
    try
    {
      CertificateBody localCertificateBody = buildBody();
      OutputStream localOutputStream = paramEACSigner.getOutputStream();
      localOutputStream.write(localCertificateBody.getEncoded("DER"));
      localOutputStream.close();
      EACCertificateHolder localEACCertificateHolder = new EACCertificateHolder(new CVCertificate(localCertificateBody, paramEACSigner.getSignature()));
      return localEACCertificateHolder;
    }
    catch (Exception localException)
    {
      throw new EACException("unable to process signature: " + localException.getMessage(), localException);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.eac.EACCertificateBuilder
 * JD-Core Version:    0.7.0.1
 */