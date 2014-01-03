package org.spongycastle.asn1.eac;

import java.io.IOException;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1ParsingException;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERApplicationSpecific;
import org.spongycastle.asn1.DEROctetString;

public class CVCertificate
  extends ASN1Object
{
  public static String ReferenceEncoding = "ISO-8859-1";
  private static int bodyValid = 1;
  private static int signValid = 2;
  public static final byte version_1;
  private CertificateBody certificateBody;
  private byte[] signature;
  private int valid;
  
  public CVCertificate(ASN1InputStream paramASN1InputStream)
    throws IOException
  {
    initFrom(paramASN1InputStream);
  }
  
  private CVCertificate(DERApplicationSpecific paramDERApplicationSpecific)
    throws IOException
  {
    setPrivateData(paramDERApplicationSpecific);
  }
  
  public CVCertificate(CertificateBody paramCertificateBody, byte[] paramArrayOfByte)
    throws IOException
  {
    this.certificateBody = paramCertificateBody;
    this.signature = paramArrayOfByte;
    this.valid |= bodyValid;
    this.valid |= signValid;
  }
  
  public static CVCertificate getInstance(Object paramObject)
  {
    if ((paramObject instanceof CVCertificate)) {
      return (CVCertificate)paramObject;
    }
    if (paramObject != null) {
      try
      {
        CVCertificate localCVCertificate = new CVCertificate(DERApplicationSpecific.getInstance(paramObject));
        return localCVCertificate;
      }
      catch (IOException localIOException)
      {
        throw new ASN1ParsingException("unable to parse data: " + localIOException.getMessage(), localIOException);
      }
    }
    return null;
  }
  
  private void initFrom(ASN1InputStream paramASN1InputStream)
    throws IOException
  {
    for (;;)
    {
      ASN1Primitive localASN1Primitive = paramASN1InputStream.readObject();
      if (localASN1Primitive == null) {
        return;
      }
      if (!(localASN1Primitive instanceof DERApplicationSpecific)) {
        break;
      }
      setPrivateData((DERApplicationSpecific)localASN1Primitive);
    }
    throw new IOException("Invalid Input Stream for creating an Iso7816CertificateStructure");
  }
  
  private void setPrivateData(DERApplicationSpecific paramDERApplicationSpecific)
    throws IOException
  {
    this.valid = 0;
    if (paramDERApplicationSpecific.getApplicationTag() == 33)
    {
      ASN1InputStream localASN1InputStream = new ASN1InputStream(paramDERApplicationSpecific.getContents());
      for (;;)
      {
        ASN1Primitive localASN1Primitive = localASN1InputStream.readObject();
        if (localASN1Primitive == null) {
          return;
        }
        if (!(localASN1Primitive instanceof DERApplicationSpecific)) {
          break;
        }
        DERApplicationSpecific localDERApplicationSpecific = (DERApplicationSpecific)localASN1Primitive;
        switch (localDERApplicationSpecific.getApplicationTag())
        {
        default: 
          throw new IOException("Invalid tag, not an Iso7816CertificateStructure :" + localDERApplicationSpecific.getApplicationTag());
        case 78: 
          this.certificateBody = CertificateBody.getInstance(localDERApplicationSpecific);
          this.valid |= bodyValid;
          break;
        case 55: 
          this.signature = localDERApplicationSpecific.getContents();
          this.valid |= signValid;
        }
      }
      throw new IOException("Invalid Object, not an Iso7816CertificateStructure");
    }
    throw new IOException("not a CARDHOLDER_CERTIFICATE :" + paramDERApplicationSpecific.getApplicationTag());
  }
  
  public CertificationAuthorityReference getAuthorityReference()
    throws IOException
  {
    return this.certificateBody.getCertificationAuthorityReference();
  }
  
  public CertificateBody getBody()
  {
    return this.certificateBody;
  }
  
  public int getCertificateType()
  {
    return this.certificateBody.getCertificateType();
  }
  
  public PackedDate getEffectiveDate()
    throws IOException
  {
    return this.certificateBody.getCertificateEffectiveDate();
  }
  
  public PackedDate getExpirationDate()
    throws IOException
  {
    return this.certificateBody.getCertificateExpirationDate();
  }
  
  public ASN1ObjectIdentifier getHolderAuthorization()
    throws IOException
  {
    return this.certificateBody.getCertificateHolderAuthorization().getOid();
  }
  
  public Flags getHolderAuthorizationRights()
    throws IOException
  {
    return new Flags(0x1F & this.certificateBody.getCertificateHolderAuthorization().getAccessRights());
  }
  
  public int getHolderAuthorizationRole()
    throws IOException
  {
    return 0xC0 & this.certificateBody.getCertificateHolderAuthorization().getAccessRights();
  }
  
  public CertificateHolderReference getHolderReference()
    throws IOException
  {
    return this.certificateBody.getCertificateHolderReference();
  }
  
  public int getRole()
    throws IOException
  {
    return this.certificateBody.getCertificateHolderAuthorization().getAccessRights();
  }
  
  public byte[] getSignature()
  {
    return this.signature;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    if (this.valid != (signValid | bodyValid)) {
      return null;
    }
    localASN1EncodableVector.add(this.certificateBody);
    try
    {
      localASN1EncodableVector.add(new DERApplicationSpecific(false, 55, new DEROctetString(this.signature)));
      return new DERApplicationSpecific(33, localASN1EncodableVector);
    }
    catch (IOException localIOException)
    {
      throw new IllegalStateException("unable to convert signature!");
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.eac.CVCertificate
 * JD-Core Version:    0.7.0.1
 */