package org.spongycastle.openssl;

import java.io.IOException;
import java.security.Provider;
import java.security.Security;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.DERObjectIdentifier;
import org.spongycastle.asn1.nist.NISTObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.crypto.PBEParametersGenerator;
import org.spongycastle.crypto.generators.OpenSSLPBEParametersGenerator;
import org.spongycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.spongycastle.crypto.params.KeyParameter;

final class PEMUtilities
{
  private static final Map KEYSIZES = new HashMap();
  private static final Set PKCS5_SCHEME_1 = new HashSet();
  private static final Set PKCS5_SCHEME_2 = new HashSet();
  
  static
  {
    PKCS5_SCHEME_1.add(PKCSObjectIdentifiers.pbeWithMD2AndDES_CBC);
    PKCS5_SCHEME_1.add(PKCSObjectIdentifiers.pbeWithMD2AndRC2_CBC);
    PKCS5_SCHEME_1.add(PKCSObjectIdentifiers.pbeWithMD5AndDES_CBC);
    PKCS5_SCHEME_1.add(PKCSObjectIdentifiers.pbeWithMD5AndRC2_CBC);
    PKCS5_SCHEME_1.add(PKCSObjectIdentifiers.pbeWithSHA1AndDES_CBC);
    PKCS5_SCHEME_1.add(PKCSObjectIdentifiers.pbeWithSHA1AndRC2_CBC);
    PKCS5_SCHEME_2.add(PKCSObjectIdentifiers.id_PBES2);
    PKCS5_SCHEME_2.add(PKCSObjectIdentifiers.des_EDE3_CBC);
    PKCS5_SCHEME_2.add(NISTObjectIdentifiers.id_aes128_CBC);
    PKCS5_SCHEME_2.add(NISTObjectIdentifiers.id_aes192_CBC);
    PKCS5_SCHEME_2.add(NISTObjectIdentifiers.id_aes256_CBC);
    KEYSIZES.put(PKCSObjectIdentifiers.des_EDE3_CBC.getId(), new Integer(192));
    KEYSIZES.put(NISTObjectIdentifiers.id_aes128_CBC.getId(), new Integer(128));
    KEYSIZES.put(NISTObjectIdentifiers.id_aes192_CBC.getId(), new Integer(192));
    KEYSIZES.put(NISTObjectIdentifiers.id_aes256_CBC.getId(), new Integer(256));
  }
  
  static byte[] crypt(boolean paramBoolean, String paramString1, byte[] paramArrayOfByte1, char[] paramArrayOfChar, String paramString2, byte[] paramArrayOfByte2)
    throws IOException
  {
    Provider localProvider = null;
    if (paramString1 != null)
    {
      localProvider = Security.getProvider(paramString1);
      if (localProvider == null) {
        throw new EncryptionException("cannot find provider: " + paramString1);
      }
    }
    return crypt(paramBoolean, localProvider, paramArrayOfByte1, paramArrayOfChar, paramString2, paramArrayOfByte2);
  }
  
  /* Error */
  static byte[] crypt(boolean paramBoolean, Provider paramProvider, byte[] paramArrayOfByte1, char[] paramArrayOfChar, String paramString, byte[] paramArrayOfByte2)
    throws IOException
  {
    // Byte code:
    //   0: new 121	javax/crypto/spec/IvParameterSpec
    //   3: dup
    //   4: aload 5
    //   6: invokespecial 124	javax/crypto/spec/IvParameterSpec:<init>	([B)V
    //   9: astore 6
    //   11: ldc 126
    //   13: astore 7
    //   15: ldc 128
    //   17: astore 8
    //   19: aload 4
    //   21: ldc 130
    //   23: invokevirtual 136	java/lang/String:endsWith	(Ljava/lang/String;)Z
    //   26: ifeq +11 -> 37
    //   29: ldc 138
    //   31: astore 7
    //   33: ldc 140
    //   35: astore 8
    //   37: aload 4
    //   39: ldc 142
    //   41: invokevirtual 136	java/lang/String:endsWith	(Ljava/lang/String;)Z
    //   44: ifne +23 -> 67
    //   47: ldc 144
    //   49: aload 4
    //   51: invokevirtual 147	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   54: ifne +13 -> 67
    //   57: ldc 149
    //   59: aload 4
    //   61: invokevirtual 147	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   64: ifeq +10 -> 74
    //   67: ldc 151
    //   69: astore 7
    //   71: aconst_null
    //   72: astore 6
    //   74: aload 4
    //   76: ldc 153
    //   78: invokevirtual 136	java/lang/String:endsWith	(Ljava/lang/String;)Z
    //   81: ifeq +11 -> 92
    //   84: ldc 155
    //   86: astore 7
    //   88: ldc 140
    //   90: astore 8
    //   92: aload 4
    //   94: ldc 144
    //   96: invokevirtual 158	java/lang/String:startsWith	(Ljava/lang/String;)Z
    //   99: ifeq +117 -> 216
    //   102: ldc 160
    //   104: astore 9
    //   106: aload 4
    //   108: ldc 149
    //   110: invokevirtual 158	java/lang/String:startsWith	(Ljava/lang/String;)Z
    //   113: ifne +97 -> 210
    //   116: iconst_1
    //   117: istore 19
    //   119: aload_3
    //   120: aload 9
    //   122: bipush 24
    //   124: aload 5
    //   126: iload 19
    //   128: invokestatic 164	org/spongycastle/openssl/PEMUtilities:getKey	([CLjava/lang/String;I[BZ)Ljavax/crypto/SecretKey;
    //   131: astore 12
    //   133: new 101	java/lang/StringBuilder
    //   136: dup
    //   137: invokespecial 165	java/lang/StringBuilder:<init>	()V
    //   140: aload 9
    //   142: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   145: ldc 167
    //   147: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   150: aload 7
    //   152: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   155: ldc 167
    //   157: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   160: aload 8
    //   162: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   165: invokevirtual 113	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   168: astore 13
    //   170: aload 13
    //   172: aload_1
    //   173: invokestatic 173	javax/crypto/Cipher:getInstance	(Ljava/lang/String;Ljava/security/Provider;)Ljavax/crypto/Cipher;
    //   176: astore 15
    //   178: iload_0
    //   179: ifeq +327 -> 506
    //   182: iconst_1
    //   183: istore 16
    //   185: aload 6
    //   187: ifnonnull +325 -> 512
    //   190: aload 15
    //   192: iload 16
    //   194: aload 12
    //   196: invokevirtual 177	javax/crypto/Cipher:init	(ILjava/security/Key;)V
    //   199: aload 15
    //   201: aload_2
    //   202: invokevirtual 181	javax/crypto/Cipher:doFinal	([B)[B
    //   205: astore 17
    //   207: aload 17
    //   209: areturn
    //   210: iconst_0
    //   211: istore 19
    //   213: goto -94 -> 119
    //   216: aload 4
    //   218: ldc 183
    //   220: invokevirtual 158	java/lang/String:startsWith	(Ljava/lang/String;)Z
    //   223: ifeq +22 -> 245
    //   226: ldc 185
    //   228: astore 9
    //   230: aload_3
    //   231: aload 9
    //   233: bipush 8
    //   235: aload 5
    //   237: invokestatic 188	org/spongycastle/openssl/PEMUtilities:getKey	([CLjava/lang/String;I[B)Ljavax/crypto/SecretKey;
    //   240: astore 12
    //   242: goto -109 -> 133
    //   245: aload 4
    //   247: ldc 190
    //   249: invokevirtual 158	java/lang/String:startsWith	(Ljava/lang/String;)Z
    //   252: ifeq +22 -> 274
    //   255: ldc 192
    //   257: astore 9
    //   259: aload_3
    //   260: aload 9
    //   262: bipush 16
    //   264: aload 5
    //   266: invokestatic 188	org/spongycastle/openssl/PEMUtilities:getKey	([CLjava/lang/String;I[B)Ljavax/crypto/SecretKey;
    //   269: astore 12
    //   271: goto -138 -> 133
    //   274: aload 4
    //   276: ldc 194
    //   278: invokevirtual 158	java/lang/String:startsWith	(Ljava/lang/String;)Z
    //   281: ifeq +93 -> 374
    //   284: ldc 196
    //   286: astore 9
    //   288: sipush 128
    //   291: istore 18
    //   293: aload 4
    //   295: ldc 198
    //   297: invokevirtual 158	java/lang/String:startsWith	(Ljava/lang/String;)Z
    //   300: ifeq +41 -> 341
    //   303: bipush 40
    //   305: istore 18
    //   307: aload_3
    //   308: aload 9
    //   310: iload 18
    //   312: bipush 8
    //   314: idiv
    //   315: aload 5
    //   317: invokestatic 188	org/spongycastle/openssl/PEMUtilities:getKey	([CLjava/lang/String;I[B)Ljavax/crypto/SecretKey;
    //   320: astore 12
    //   322: aload 6
    //   324: ifnonnull +34 -> 358
    //   327: new 200	javax/crypto/spec/RC2ParameterSpec
    //   330: dup
    //   331: iload 18
    //   333: invokespecial 201	javax/crypto/spec/RC2ParameterSpec:<init>	(I)V
    //   336: astore 6
    //   338: goto -205 -> 133
    //   341: aload 4
    //   343: ldc 203
    //   345: invokevirtual 158	java/lang/String:startsWith	(Ljava/lang/String;)Z
    //   348: ifeq -41 -> 307
    //   351: bipush 64
    //   353: istore 18
    //   355: goto -48 -> 307
    //   358: new 200	javax/crypto/spec/RC2ParameterSpec
    //   361: dup
    //   362: iload 18
    //   364: aload 5
    //   366: invokespecial 206	javax/crypto/spec/RC2ParameterSpec:<init>	(I[B)V
    //   369: astore 6
    //   371: goto -238 -> 133
    //   374: aload 4
    //   376: ldc 208
    //   378: invokevirtual 158	java/lang/String:startsWith	(Ljava/lang/String;)Z
    //   381: ifeq +115 -> 496
    //   384: ldc 210
    //   386: astore 9
    //   388: aload 5
    //   390: astore 10
    //   392: aload 5
    //   394: arraylength
    //   395: bipush 8
    //   397: if_icmple +20 -> 417
    //   400: bipush 8
    //   402: newarray byte
    //   404: astore 10
    //   406: aload 5
    //   408: iconst_0
    //   409: aload 10
    //   411: iconst_0
    //   412: bipush 8
    //   414: invokestatic 216	java/lang/System:arraycopy	(Ljava/lang/Object;ILjava/lang/Object;II)V
    //   417: aload 4
    //   419: ldc 218
    //   421: invokevirtual 158	java/lang/String:startsWith	(Ljava/lang/String;)Z
    //   424: ifeq +26 -> 450
    //   427: sipush 128
    //   430: istore 11
    //   432: aload_3
    //   433: ldc 210
    //   435: iload 11
    //   437: bipush 8
    //   439: idiv
    //   440: aload 10
    //   442: invokestatic 188	org/spongycastle/openssl/PEMUtilities:getKey	([CLjava/lang/String;I[B)Ljavax/crypto/SecretKey;
    //   445: astore 12
    //   447: goto -314 -> 133
    //   450: aload 4
    //   452: ldc 220
    //   454: invokevirtual 158	java/lang/String:startsWith	(Ljava/lang/String;)Z
    //   457: ifeq +11 -> 468
    //   460: sipush 192
    //   463: istore 11
    //   465: goto -33 -> 432
    //   468: aload 4
    //   470: ldc 222
    //   472: invokevirtual 158	java/lang/String:startsWith	(Ljava/lang/String;)Z
    //   475: ifeq +11 -> 486
    //   478: sipush 256
    //   481: istore 11
    //   483: goto -51 -> 432
    //   486: new 99	org/spongycastle/openssl/EncryptionException
    //   489: dup
    //   490: ldc 224
    //   492: invokespecial 114	org/spongycastle/openssl/EncryptionException:<init>	(Ljava/lang/String;)V
    //   495: athrow
    //   496: new 99	org/spongycastle/openssl/EncryptionException
    //   499: dup
    //   500: ldc 226
    //   502: invokespecial 114	org/spongycastle/openssl/EncryptionException:<init>	(Ljava/lang/String;)V
    //   505: athrow
    //   506: iconst_2
    //   507: istore 16
    //   509: goto -324 -> 185
    //   512: aload 15
    //   514: iload 16
    //   516: aload 12
    //   518: aload 6
    //   520: invokevirtual 229	javax/crypto/Cipher:init	(ILjava/security/Key;Ljava/security/spec/AlgorithmParameterSpec;)V
    //   523: goto -324 -> 199
    //   526: astore 14
    //   528: new 99	org/spongycastle/openssl/EncryptionException
    //   531: dup
    //   532: ldc 231
    //   534: aload 14
    //   536: invokespecial 234	org/spongycastle/openssl/EncryptionException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   539: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	540	0	paramBoolean	boolean
    //   0	540	1	paramProvider	Provider
    //   0	540	2	paramArrayOfByte1	byte[]
    //   0	540	3	paramArrayOfChar	char[]
    //   0	540	4	paramString	String
    //   0	540	5	paramArrayOfByte2	byte[]
    //   9	510	6	localObject	Object
    //   13	138	7	str1	String
    //   17	144	8	str2	String
    //   104	283	9	str3	String
    //   390	51	10	arrayOfByte1	byte[]
    //   430	52	11	i	int
    //   131	386	12	localSecretKey	SecretKey
    //   168	3	13	str4	String
    //   526	9	14	localException	java.lang.Exception
    //   176	337	15	localCipher	javax.crypto.Cipher
    //   183	332	16	j	int
    //   205	3	17	arrayOfByte2	byte[]
    //   291	72	18	k	int
    //   117	95	19	bool	boolean
    // Exception table:
    //   from	to	target	type
    //   170	178	526	java/lang/Exception
    //   190	199	526	java/lang/Exception
    //   199	207	526	java/lang/Exception
    //   512	523	526	java/lang/Exception
  }
  
  static SecretKey generateSecretKeyForPKCS5Scheme2(String paramString, char[] paramArrayOfChar, byte[] paramArrayOfByte, int paramInt)
  {
    PKCS5S2ParametersGenerator localPKCS5S2ParametersGenerator = new PKCS5S2ParametersGenerator();
    localPKCS5S2ParametersGenerator.init(PBEParametersGenerator.PKCS5PasswordToBytes(paramArrayOfChar), paramArrayOfByte, paramInt);
    return new SecretKeySpec(((KeyParameter)localPKCS5S2ParametersGenerator.generateDerivedParameters(getKeySize(paramString))).getKey(), paramString);
  }
  
  private static SecretKey getKey(char[] paramArrayOfChar, String paramString, int paramInt, byte[] paramArrayOfByte)
  {
    return getKey(paramArrayOfChar, paramString, paramInt, paramArrayOfByte, false);
  }
  
  private static SecretKey getKey(char[] paramArrayOfChar, String paramString, int paramInt, byte[] paramArrayOfByte, boolean paramBoolean)
  {
    OpenSSLPBEParametersGenerator localOpenSSLPBEParametersGenerator = new OpenSSLPBEParametersGenerator();
    localOpenSSLPBEParametersGenerator.init(PBEParametersGenerator.PKCS5PasswordToBytes(paramArrayOfChar), paramArrayOfByte);
    byte[] arrayOfByte = ((KeyParameter)localOpenSSLPBEParametersGenerator.generateDerivedParameters(paramInt * 8)).getKey();
    if ((paramBoolean) && (arrayOfByte.length >= 24)) {
      System.arraycopy(arrayOfByte, 0, arrayOfByte, 16, 8);
    }
    return new SecretKeySpec(arrayOfByte, paramString);
  }
  
  static int getKeySize(String paramString)
  {
    if (!KEYSIZES.containsKey(paramString)) {
      throw new IllegalStateException("no key size for algorithm: " + paramString);
    }
    return ((Integer)KEYSIZES.get(paramString)).intValue();
  }
  
  static boolean isPKCS12(DERObjectIdentifier paramDERObjectIdentifier)
  {
    return paramDERObjectIdentifier.getId().startsWith(PKCSObjectIdentifiers.pkcs_12PbeIds.getId());
  }
  
  static boolean isPKCS5Scheme1(DERObjectIdentifier paramDERObjectIdentifier)
  {
    return PKCS5_SCHEME_1.contains(paramDERObjectIdentifier);
  }
  
  static boolean isPKCS5Scheme2(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
  {
    return PKCS5_SCHEME_2.contains(paramASN1ObjectIdentifier);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.openssl.PEMUtilities
 * JD-Core Version:    0.7.0.1
 */