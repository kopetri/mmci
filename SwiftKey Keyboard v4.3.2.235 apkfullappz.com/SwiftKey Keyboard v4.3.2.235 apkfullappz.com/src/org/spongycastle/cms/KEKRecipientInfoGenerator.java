package org.spongycastle.cms;

import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.cms.KEKIdentifier;
import org.spongycastle.asn1.cms.KEKRecipientInfo;
import org.spongycastle.asn1.cms.RecipientInfo;
import org.spongycastle.operator.GenericKey;
import org.spongycastle.operator.OperatorException;
import org.spongycastle.operator.SymmetricKeyWrapper;

public abstract class KEKRecipientInfoGenerator
  implements RecipientInfoGenerator
{
  private final KEKIdentifier kekIdentifier;
  protected final SymmetricKeyWrapper wrapper;
  
  protected KEKRecipientInfoGenerator(KEKIdentifier paramKEKIdentifier, SymmetricKeyWrapper paramSymmetricKeyWrapper)
  {
    this.kekIdentifier = paramKEKIdentifier;
    this.wrapper = paramSymmetricKeyWrapper;
  }
  
  public final RecipientInfo generate(GenericKey paramGenericKey)
    throws CMSException
  {
    try
    {
      DEROctetString localDEROctetString = new DEROctetString(this.wrapper.generateWrappedKey(paramGenericKey));
      RecipientInfo localRecipientInfo = new RecipientInfo(new KEKRecipientInfo(this.kekIdentifier, this.wrapper.getAlgorithmIdentifier(), localDEROctetString));
      return localRecipientInfo;
    }
    catch (OperatorException localOperatorException)
    {
      throw new CMSException("exception wrapping content key: " + localOperatorException.getMessage(), localOperatorException);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.KEKRecipientInfoGenerator
 * JD-Core Version:    0.7.0.1
 */