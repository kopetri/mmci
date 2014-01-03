package org.spongycastle.cms;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import org.spongycastle.asn1.DERObjectIdentifier;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public class CMSAuthenticatedGenerator
  extends CMSEnvelopedGenerator
{
  protected CMSAttributeTableGenerator authGen;
  protected CMSAttributeTableGenerator unauthGen;
  
  public CMSAuthenticatedGenerator() {}
  
  public CMSAuthenticatedGenerator(SecureRandom paramSecureRandom)
  {
    super(paramSecureRandom);
  }
  
  protected Map getBaseParameters(DERObjectIdentifier paramDERObjectIdentifier, AlgorithmIdentifier paramAlgorithmIdentifier, byte[] paramArrayOfByte)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("contentType", paramDERObjectIdentifier);
    localHashMap.put("digestAlgID", paramAlgorithmIdentifier);
    localHashMap.put("digest", paramArrayOfByte.clone());
    return localHashMap;
  }
  
  public void setAuthenticatedAttributeGenerator(CMSAttributeTableGenerator paramCMSAttributeTableGenerator)
  {
    this.authGen = paramCMSAttributeTableGenerator;
  }
  
  public void setUnauthenticatedAttributeGenerator(CMSAttributeTableGenerator paramCMSAttributeTableGenerator)
  {
    this.unauthGen = paramCMSAttributeTableGenerator;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.CMSAuthenticatedGenerator
 * JD-Core Version:    0.7.0.1
 */