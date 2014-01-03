package org.spongycastle.cms;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.crypto.KeyGenerator;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.cms.KEKRecipientInfo;
import org.spongycastle.asn1.cms.KeyAgreeRecipientInfo;
import org.spongycastle.asn1.cms.KeyTransRecipientInfo;
import org.spongycastle.asn1.cms.PasswordRecipientInfo;
import org.spongycastle.asn1.cms.RecipientInfo;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.operator.DigestCalculator;

class CMSEnvelopedHelper
{
  private static final Map BASE_CIPHER_NAMES;
  private static final Map CIPHER_ALG_NAMES;
  static final CMSEnvelopedHelper INSTANCE = new CMSEnvelopedHelper();
  private static final Map KEYSIZES = new HashMap();
  private static final Map MAC_ALG_NAMES;
  
  static
  {
    BASE_CIPHER_NAMES = new HashMap();
    CIPHER_ALG_NAMES = new HashMap();
    MAC_ALG_NAMES = new HashMap();
    KEYSIZES.put(CMSEnvelopedGenerator.DES_EDE3_CBC, new Integer(192));
    KEYSIZES.put(CMSEnvelopedGenerator.AES128_CBC, new Integer(128));
    KEYSIZES.put(CMSEnvelopedGenerator.AES192_CBC, new Integer(192));
    KEYSIZES.put(CMSEnvelopedGenerator.AES256_CBC, new Integer(256));
    BASE_CIPHER_NAMES.put(CMSEnvelopedGenerator.DES_EDE3_CBC, "DESEDE");
    BASE_CIPHER_NAMES.put(CMSEnvelopedGenerator.AES128_CBC, "AES");
    BASE_CIPHER_NAMES.put(CMSEnvelopedGenerator.AES192_CBC, "AES");
    BASE_CIPHER_NAMES.put(CMSEnvelopedGenerator.AES256_CBC, "AES");
    CIPHER_ALG_NAMES.put(CMSEnvelopedGenerator.DES_EDE3_CBC, "DESEDE/CBC/PKCS5Padding");
    CIPHER_ALG_NAMES.put(CMSEnvelopedGenerator.AES128_CBC, "AES/CBC/PKCS5Padding");
    CIPHER_ALG_NAMES.put(CMSEnvelopedGenerator.AES192_CBC, "AES/CBC/PKCS5Padding");
    CIPHER_ALG_NAMES.put(CMSEnvelopedGenerator.AES256_CBC, "AES/CBC/PKCS5Padding");
    MAC_ALG_NAMES.put(CMSEnvelopedGenerator.DES_EDE3_CBC, "DESEDEMac");
    MAC_ALG_NAMES.put(CMSEnvelopedGenerator.AES128_CBC, "AESMac");
    MAC_ALG_NAMES.put(CMSEnvelopedGenerator.AES192_CBC, "AESMac");
    MAC_ALG_NAMES.put(CMSEnvelopedGenerator.AES256_CBC, "AESMac");
  }
  
  static RecipientInformationStore buildRecipientInformationStore(ASN1Set paramASN1Set, AlgorithmIdentifier paramAlgorithmIdentifier, CMSSecureReadable paramCMSSecureReadable)
  {
    return buildRecipientInformationStore(paramASN1Set, paramAlgorithmIdentifier, paramCMSSecureReadable, null);
  }
  
  static RecipientInformationStore buildRecipientInformationStore(ASN1Set paramASN1Set, AlgorithmIdentifier paramAlgorithmIdentifier, CMSSecureReadable paramCMSSecureReadable, AuthAttributesProvider paramAuthAttributesProvider)
  {
    ArrayList localArrayList = new ArrayList();
    for (int i = 0; i != paramASN1Set.size(); i++) {
      readRecipientInfo(localArrayList, RecipientInfo.getInstance(paramASN1Set.getObjectAt(i)), paramAlgorithmIdentifier, paramCMSSecureReadable, paramAuthAttributesProvider);
    }
    return new RecipientInformationStore(localArrayList);
  }
  
  private KeyGenerator createKeyGenerator(String paramString, Provider paramProvider)
    throws NoSuchAlgorithmException
  {
    if (paramProvider != null) {
      return KeyGenerator.getInstance(paramString, paramProvider);
    }
    return KeyGenerator.getInstance(paramString);
  }
  
  private static void readRecipientInfo(List paramList, RecipientInfo paramRecipientInfo, AlgorithmIdentifier paramAlgorithmIdentifier, CMSSecureReadable paramCMSSecureReadable, AuthAttributesProvider paramAuthAttributesProvider)
  {
    ASN1Encodable localASN1Encodable = paramRecipientInfo.getInfo();
    if ((localASN1Encodable instanceof KeyTransRecipientInfo)) {
      paramList.add(new KeyTransRecipientInformation((KeyTransRecipientInfo)localASN1Encodable, paramAlgorithmIdentifier, paramCMSSecureReadable, paramAuthAttributesProvider));
    }
    do
    {
      return;
      if ((localASN1Encodable instanceof KEKRecipientInfo))
      {
        paramList.add(new KEKRecipientInformation((KEKRecipientInfo)localASN1Encodable, paramAlgorithmIdentifier, paramCMSSecureReadable, paramAuthAttributesProvider));
        return;
      }
      if ((localASN1Encodable instanceof KeyAgreeRecipientInfo))
      {
        KeyAgreeRecipientInformation.readRecipientInfo(paramList, (KeyAgreeRecipientInfo)localASN1Encodable, paramAlgorithmIdentifier, paramCMSSecureReadable, paramAuthAttributesProvider);
        return;
      }
    } while (!(localASN1Encodable instanceof PasswordRecipientInfo));
    paramList.add(new PasswordRecipientInformation((PasswordRecipientInfo)localASN1Encodable, paramAlgorithmIdentifier, paramCMSSecureReadable, paramAuthAttributesProvider));
  }
  
  KeyGenerator createSymmetricKeyGenerator(String paramString, Provider paramProvider)
    throws NoSuchAlgorithmException
  {
    try
    {
      KeyGenerator localKeyGenerator2 = createKeyGenerator(paramString, paramProvider);
      return localKeyGenerator2;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException1)
    {
      try
      {
        String str = (String)BASE_CIPHER_NAMES.get(paramString);
        if (str != null)
        {
          KeyGenerator localKeyGenerator1 = createKeyGenerator(str, paramProvider);
          return localKeyGenerator1;
        }
      }
      catch (NoSuchAlgorithmException localNoSuchAlgorithmException2)
      {
        if (paramProvider != null) {
          return createSymmetricKeyGenerator(paramString, null);
        }
        throw localNoSuchAlgorithmException1;
      }
    }
  }
  
  int getKeySize(String paramString)
  {
    Integer localInteger = (Integer)KEYSIZES.get(paramString);
    if (localInteger == null) {
      throw new IllegalArgumentException("no keysize for " + paramString);
    }
    return localInteger.intValue();
  }
  
  static class CMSAuthenticatedSecureReadable
    implements CMSSecureReadable
  {
    private AlgorithmIdentifier algorithm;
    private CMSReadable readable;
    
    CMSAuthenticatedSecureReadable(AlgorithmIdentifier paramAlgorithmIdentifier, CMSReadable paramCMSReadable)
    {
      this.algorithm = paramAlgorithmIdentifier;
      this.readable = paramCMSReadable;
    }
    
    public InputStream getInputStream()
      throws IOException, CMSException
    {
      return this.readable.getInputStream();
    }
  }
  
  static class CMSDigestAuthenticatedSecureReadable
    implements CMSSecureReadable
  {
    private DigestCalculator digestCalculator;
    private CMSReadable readable;
    
    public CMSDigestAuthenticatedSecureReadable(DigestCalculator paramDigestCalculator, CMSReadable paramCMSReadable)
    {
      this.digestCalculator = paramDigestCalculator;
      this.readable = paramCMSReadable;
    }
    
    public byte[] getDigest()
    {
      return this.digestCalculator.getDigest();
    }
    
    public InputStream getInputStream()
      throws IOException, CMSException
    {
      new FilterInputStream(this.readable.getInputStream())
      {
        public int read()
          throws IOException
        {
          int i = this.in.read();
          if (i >= 0) {
            CMSEnvelopedHelper.CMSDigestAuthenticatedSecureReadable.this.digestCalculator.getOutputStream().write(i);
          }
          return i;
        }
        
        public int read(byte[] paramAnonymousArrayOfByte, int paramAnonymousInt1, int paramAnonymousInt2)
          throws IOException
        {
          int i = this.in.read(paramAnonymousArrayOfByte, paramAnonymousInt1, paramAnonymousInt2);
          if (i >= 0) {
            CMSEnvelopedHelper.CMSDigestAuthenticatedSecureReadable.this.digestCalculator.getOutputStream().write(paramAnonymousArrayOfByte, paramAnonymousInt1, i);
          }
          return i;
        }
      };
    }
  }
  
  static class CMSEnvelopedSecureReadable
    implements CMSSecureReadable
  {
    private AlgorithmIdentifier algorithm;
    private CMSReadable readable;
    
    CMSEnvelopedSecureReadable(AlgorithmIdentifier paramAlgorithmIdentifier, CMSReadable paramCMSReadable)
    {
      this.algorithm = paramAlgorithmIdentifier;
      this.readable = paramCMSReadable;
    }
    
    public InputStream getInputStream()
      throws IOException, CMSException
    {
      return this.readable.getInputStream();
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.CMSEnvelopedHelper
 * JD-Core Version:    0.7.0.1
 */