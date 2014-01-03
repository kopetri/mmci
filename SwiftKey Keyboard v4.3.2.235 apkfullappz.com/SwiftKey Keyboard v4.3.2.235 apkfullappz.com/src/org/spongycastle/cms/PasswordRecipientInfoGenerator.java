package org.spongycastle.cms;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Map;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.cms.PasswordRecipientInfo;
import org.spongycastle.asn1.cms.RecipientInfo;
import org.spongycastle.asn1.pkcs.PBKDF2Params;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.crypto.PBEParametersGenerator;
import org.spongycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.operator.GenericKey;

public abstract class PasswordRecipientInfoGenerator
  implements RecipientInfoGenerator
{
  private int blockSize;
  private ASN1ObjectIdentifier kekAlgorithm;
  private AlgorithmIdentifier keyDerivationAlgorithm;
  private int keySize;
  private char[] password;
  private SecureRandom random;
  private int schemeID;
  
  protected PasswordRecipientInfoGenerator(ASN1ObjectIdentifier paramASN1ObjectIdentifier, char[] paramArrayOfChar)
  {
    this(paramASN1ObjectIdentifier, paramArrayOfChar, getKeySize(paramASN1ObjectIdentifier), ((Integer)PasswordRecipientInformation.BLOCKSIZES.get(paramASN1ObjectIdentifier)).intValue());
  }
  
  protected PasswordRecipientInfoGenerator(ASN1ObjectIdentifier paramASN1ObjectIdentifier, char[] paramArrayOfChar, int paramInt1, int paramInt2)
  {
    this.password = paramArrayOfChar;
    this.schemeID = 1;
    this.kekAlgorithm = paramASN1ObjectIdentifier;
    this.keySize = paramInt1;
    this.blockSize = paramInt2;
  }
  
  private static int getKeySize(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
  {
    Integer localInteger = (Integer)PasswordRecipientInformation.KEYSIZES.get(paramASN1ObjectIdentifier);
    if (localInteger == null) {
      throw new IllegalArgumentException("cannot find key size for algorithm: " + paramASN1ObjectIdentifier);
    }
    return localInteger.intValue();
  }
  
  public RecipientInfo generate(GenericKey paramGenericKey)
    throws CMSException
  {
    byte[] arrayOfByte1 = new byte[this.blockSize];
    if (this.random == null) {
      this.random = new SecureRandom();
    }
    this.random.nextBytes(arrayOfByte1);
    if (this.keyDerivationAlgorithm == null)
    {
      byte[] arrayOfByte3 = new byte[20];
      this.random.nextBytes(arrayOfByte3);
      this.keyDerivationAlgorithm = new AlgorithmIdentifier(PKCSObjectIdentifiers.id_PBKDF2, new PBKDF2Params(arrayOfByte3, 1024));
    }
    PBKDF2Params localPBKDF2Params = PBKDF2Params.getInstance(this.keyDerivationAlgorithm.getParameters());
    PKCS5S2ParametersGenerator localPKCS5S2ParametersGenerator1;
    if (this.schemeID == 0)
    {
      localPKCS5S2ParametersGenerator1 = new PKCS5S2ParametersGenerator();
      localPKCS5S2ParametersGenerator1.init(PBEParametersGenerator.PKCS5PasswordToBytes(this.password), localPBKDF2Params.getSalt(), localPBKDF2Params.getIterationCount().intValue());
    }
    PKCS5S2ParametersGenerator localPKCS5S2ParametersGenerator2;
    for (byte[] arrayOfByte2 = ((KeyParameter)localPKCS5S2ParametersGenerator1.generateDerivedParameters(this.keySize)).getKey();; arrayOfByte2 = ((KeyParameter)localPKCS5S2ParametersGenerator2.generateDerivedParameters(this.keySize)).getKey())
    {
      DEROctetString localDEROctetString = new DEROctetString(generateEncryptedBytes(new AlgorithmIdentifier(this.kekAlgorithm, new DEROctetString(arrayOfByte1)), arrayOfByte2, paramGenericKey));
      ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
      localASN1EncodableVector.add(this.kekAlgorithm);
      localASN1EncodableVector.add(new DEROctetString(arrayOfByte1));
      AlgorithmIdentifier localAlgorithmIdentifier = new AlgorithmIdentifier(PKCSObjectIdentifiers.id_alg_PWRI_KEK, new DERSequence(localASN1EncodableVector));
      return new RecipientInfo(new PasswordRecipientInfo(this.keyDerivationAlgorithm, localAlgorithmIdentifier, localDEROctetString));
      localPKCS5S2ParametersGenerator2 = new PKCS5S2ParametersGenerator();
      localPKCS5S2ParametersGenerator2.init(PBEParametersGenerator.PKCS5PasswordToUTF8Bytes(this.password), localPBKDF2Params.getSalt(), localPBKDF2Params.getIterationCount().intValue());
    }
  }
  
  protected abstract byte[] generateEncryptedBytes(AlgorithmIdentifier paramAlgorithmIdentifier, byte[] paramArrayOfByte, GenericKey paramGenericKey)
    throws CMSException;
  
  public PasswordRecipientInfoGenerator setPasswordConversionScheme(int paramInt)
  {
    this.schemeID = paramInt;
    return this;
  }
  
  public PasswordRecipientInfoGenerator setSaltAndIterationCount(byte[] paramArrayOfByte, int paramInt)
  {
    this.keyDerivationAlgorithm = new AlgorithmIdentifier(PKCSObjectIdentifiers.id_PBKDF2, new PBKDF2Params(paramArrayOfByte, paramInt));
    return this;
  }
  
  public PasswordRecipientInfoGenerator setSecureRandom(SecureRandom paramSecureRandom)
  {
    this.random = paramSecureRandom;
    return this;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.PasswordRecipientInfoGenerator
 * JD-Core Version:    0.7.0.1
 */