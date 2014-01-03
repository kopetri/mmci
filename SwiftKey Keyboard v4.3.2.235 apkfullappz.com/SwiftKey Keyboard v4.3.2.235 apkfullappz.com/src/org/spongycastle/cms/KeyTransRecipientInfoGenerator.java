package org.spongycastle.cms;

import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.cms.IssuerAndSerialNumber;
import org.spongycastle.asn1.cms.KeyTransRecipientInfo;
import org.spongycastle.asn1.cms.RecipientIdentifier;
import org.spongycastle.asn1.cms.RecipientInfo;
import org.spongycastle.operator.AsymmetricKeyWrapper;
import org.spongycastle.operator.GenericKey;
import org.spongycastle.operator.OperatorException;

public abstract class KeyTransRecipientInfoGenerator
  implements RecipientInfoGenerator
{
  private IssuerAndSerialNumber issuerAndSerial;
  private byte[] subjectKeyIdentifier;
  protected final AsymmetricKeyWrapper wrapper;
  
  protected KeyTransRecipientInfoGenerator(IssuerAndSerialNumber paramIssuerAndSerialNumber, AsymmetricKeyWrapper paramAsymmetricKeyWrapper)
  {
    this.issuerAndSerial = paramIssuerAndSerialNumber;
    this.wrapper = paramAsymmetricKeyWrapper;
  }
  
  protected KeyTransRecipientInfoGenerator(byte[] paramArrayOfByte, AsymmetricKeyWrapper paramAsymmetricKeyWrapper)
  {
    this.subjectKeyIdentifier = paramArrayOfByte;
    this.wrapper = paramAsymmetricKeyWrapper;
  }
  
  public final RecipientInfo generate(GenericKey paramGenericKey)
    throws CMSException
  {
    for (;;)
    {
      try
      {
        byte[] arrayOfByte = this.wrapper.generateWrappedKey(paramGenericKey);
        if (this.issuerAndSerial != null)
        {
          localRecipientIdentifier = new RecipientIdentifier(this.issuerAndSerial);
          return new RecipientInfo(new KeyTransRecipientInfo(localRecipientIdentifier, this.wrapper.getAlgorithmIdentifier(), new DEROctetString(arrayOfByte)));
        }
      }
      catch (OperatorException localOperatorException)
      {
        throw new CMSException("exception wrapping content key: " + localOperatorException.getMessage(), localOperatorException);
      }
      RecipientIdentifier localRecipientIdentifier = new RecipientIdentifier(new DEROctetString(this.subjectKeyIdentifier));
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.KeyTransRecipientInfoGenerator
 * JD-Core Version:    0.7.0.1
 */