package org.spongycastle.asn1.eac;

import java.io.IOException;
import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1ParsingException;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERApplicationSpecific;
import org.spongycastle.asn1.DEROctetString;

public class CVCertificateRequest
  extends ASN1Object
{
  public static byte[] ZeroArray = { 0 };
  private static int bodyValid = 1;
  private static int signValid = 2;
  int ProfileId;
  byte[] certificate = null;
  private CertificateBody certificateBody;
  byte[] encoded;
  byte[] encodedAuthorityReference;
  private byte[] innerSignature = null;
  PublicKeyDataObject iso7816PubKey = null;
  ASN1ObjectIdentifier keyOid = null;
  private byte[] outerSignature = null;
  protected String overSignerReference = null;
  ASN1ObjectIdentifier signOid = null;
  String strCertificateHolderReference;
  private int valid;
  
  private CVCertificateRequest(DERApplicationSpecific paramDERApplicationSpecific)
    throws IOException
  {
    if (paramDERApplicationSpecific.getApplicationTag() == 103)
    {
      ASN1Sequence localASN1Sequence = ASN1Sequence.getInstance(paramDERApplicationSpecific.getObject(16));
      initCertBody(DERApplicationSpecific.getInstance(localASN1Sequence.getObjectAt(0)));
      this.outerSignature = DERApplicationSpecific.getInstance(localASN1Sequence.getObjectAt(-1 + localASN1Sequence.size())).getContents();
      return;
    }
    initCertBody(paramDERApplicationSpecific);
  }
  
  public static CVCertificateRequest getInstance(Object paramObject)
  {
    if ((paramObject instanceof CVCertificateRequest)) {
      return (CVCertificateRequest)paramObject;
    }
    if (paramObject != null) {
      try
      {
        CVCertificateRequest localCVCertificateRequest = new CVCertificateRequest(DERApplicationSpecific.getInstance(paramObject));
        return localCVCertificateRequest;
      }
      catch (IOException localIOException)
      {
        throw new ASN1ParsingException("unable to parse data: " + localIOException.getMessage(), localIOException);
      }
    }
    return null;
  }
  
  private void initCertBody(DERApplicationSpecific paramDERApplicationSpecific)
    throws IOException
  {
    if (paramDERApplicationSpecific.getApplicationTag() == 33)
    {
      Enumeration localEnumeration = ASN1Sequence.getInstance(paramDERApplicationSpecific.getObject(16)).getObjects();
      while (localEnumeration.hasMoreElements())
      {
        DERApplicationSpecific localDERApplicationSpecific = DERApplicationSpecific.getInstance(localEnumeration.nextElement());
        switch (localDERApplicationSpecific.getApplicationTag())
        {
        default: 
          throw new IOException("Invalid tag, not an CV Certificate Request element:" + localDERApplicationSpecific.getApplicationTag());
        case 78: 
          this.certificateBody = CertificateBody.getInstance(localDERApplicationSpecific);
          this.valid |= bodyValid;
          break;
        case 55: 
          this.innerSignature = localDERApplicationSpecific.getContents();
          this.valid |= signValid;
        }
      }
    }
    throw new IOException("not a CARDHOLDER_CERTIFICATE in request:" + paramDERApplicationSpecific.getApplicationTag());
  }
  
  public CertificateBody getCertificateBody()
  {
    return this.certificateBody;
  }
  
  public byte[] getInnerSignature()
  {
    return this.innerSignature;
  }
  
  public byte[] getOuterSignature()
  {
    return this.outerSignature;
  }
  
  public PublicKeyDataObject getPublicKey()
  {
    return this.certificateBody.getPublicKey();
  }
  
  public boolean hasOuterSignature()
  {
    return this.outerSignature != null;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.certificateBody);
    try
    {
      localASN1EncodableVector.add(new DERApplicationSpecific(false, 55, new DEROctetString(this.innerSignature)));
      return new DERApplicationSpecific(33, localASN1EncodableVector);
    }
    catch (IOException localIOException)
    {
      throw new IllegalStateException("unable to convert signature!");
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.eac.CVCertificateRequest
 * JD-Core Version:    0.7.0.1
 */