package org.spongycastle.jce.provider;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyStoreException;
import java.security.KeyStoreSpi;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.PBEParametersGenerator;
import org.spongycastle.crypto.digests.SHA1Digest;
import org.spongycastle.crypto.generators.PKCS12ParametersGenerator;
import org.spongycastle.crypto.io.DigestInputStream;
import org.spongycastle.crypto.io.DigestOutputStream;
import org.spongycastle.crypto.io.MacInputStream;
import org.spongycastle.crypto.io.MacOutputStream;
import org.spongycastle.crypto.macs.HMac;
import org.spongycastle.jce.interfaces.BCKeyStore;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.io.Streams;
import org.spongycastle.util.io.TeeOutputStream;

public class JDKKeyStore
  extends KeyStoreSpi
  implements BCKeyStore
{
  static final int CERTIFICATE = 1;
  static final int KEY = 2;
  private static final String KEY_CIPHER = "PBEWithSHAAnd3-KeyTripleDES-CBC";
  static final int KEY_PRIVATE = 0;
  static final int KEY_PUBLIC = 1;
  private static final int KEY_SALT_SIZE = 20;
  static final int KEY_SECRET = 2;
  private static final int MIN_ITERATIONS = 1024;
  static final int NULL = 0;
  static final int SEALED = 4;
  static final int SECRET = 3;
  private static final String STORE_CIPHER = "PBEWithSHAAndTwofish-CBC";
  private static final int STORE_SALT_SIZE = 20;
  private static final int STORE_VERSION = 2;
  protected SecureRandom random = new SecureRandom();
  protected Hashtable table = new Hashtable();
  
  private Certificate decodeCertificate(DataInputStream paramDataInputStream)
    throws IOException
  {
    String str = paramDataInputStream.readUTF();
    byte[] arrayOfByte = new byte[paramDataInputStream.readInt()];
    paramDataInputStream.readFully(arrayOfByte);
    try
    {
      Certificate localCertificate = CertificateFactory.getInstance(str, BouncyCastleProvider.PROVIDER_NAME).generateCertificate(new ByteArrayInputStream(arrayOfByte));
      return localCertificate;
    }
    catch (NoSuchProviderException localNoSuchProviderException)
    {
      throw new IOException(localNoSuchProviderException.toString());
    }
    catch (CertificateException localCertificateException)
    {
      throw new IOException(localCertificateException.toString());
    }
  }
  
  private Key decodeKey(DataInputStream paramDataInputStream)
    throws IOException
  {
    int i = paramDataInputStream.read();
    String str1 = paramDataInputStream.readUTF();
    String str2 = paramDataInputStream.readUTF();
    byte[] arrayOfByte = new byte[paramDataInputStream.readInt()];
    paramDataInputStream.readFully(arrayOfByte);
    if ((str1.equals("PKCS#8")) || (str1.equals("PKCS8"))) {}
    for (Object localObject = new PKCS8EncodedKeySpec(arrayOfByte);; localObject = new X509EncodedKeySpec(arrayOfByte)) {
      switch (i)
      {
      default: 
        try
        {
          throw new IOException("Key type " + i + " not recognised!");
        }
        catch (Exception localException)
        {
          throw new IOException("Exception creating key: " + localException.toString());
        }
        if ((!str1.equals("X.509")) && (!str1.equals("X509"))) {
          break label179;
        }
      }
    }
    label179:
    if (str1.equals("RAW")) {
      return new SecretKeySpec(arrayOfByte, str2);
    }
    throw new IOException("Key format " + str1 + " not recognised!");
    return KeyFactory.getInstance(str2, BouncyCastleProvider.PROVIDER_NAME).generatePrivate((KeySpec)localObject);
    return KeyFactory.getInstance(str2, BouncyCastleProvider.PROVIDER_NAME).generatePublic((KeySpec)localObject);
    SecretKey localSecretKey = SecretKeyFactory.getInstance(str2, BouncyCastleProvider.PROVIDER_NAME).generateSecret((KeySpec)localObject);
    return localSecretKey;
  }
  
  private void encodeCertificate(Certificate paramCertificate, DataOutputStream paramDataOutputStream)
    throws IOException
  {
    try
    {
      byte[] arrayOfByte = paramCertificate.getEncoded();
      paramDataOutputStream.writeUTF(paramCertificate.getType());
      paramDataOutputStream.writeInt(arrayOfByte.length);
      paramDataOutputStream.write(arrayOfByte);
      return;
    }
    catch (CertificateEncodingException localCertificateEncodingException)
    {
      throw new IOException(localCertificateEncodingException.toString());
    }
  }
  
  private void encodeKey(Key paramKey, DataOutputStream paramDataOutputStream)
    throws IOException
  {
    byte[] arrayOfByte = paramKey.getEncoded();
    if ((paramKey instanceof PrivateKey)) {
      paramDataOutputStream.write(0);
    }
    for (;;)
    {
      paramDataOutputStream.writeUTF(paramKey.getFormat());
      paramDataOutputStream.writeUTF(paramKey.getAlgorithm());
      paramDataOutputStream.writeInt(arrayOfByte.length);
      paramDataOutputStream.write(arrayOfByte);
      return;
      if ((paramKey instanceof PublicKey)) {
        paramDataOutputStream.write(1);
      } else {
        paramDataOutputStream.write(2);
      }
    }
  }
  
  public Enumeration engineAliases()
  {
    return this.table.keys();
  }
  
  public boolean engineContainsAlias(String paramString)
  {
    return this.table.get(paramString) != null;
  }
  
  public void engineDeleteEntry(String paramString)
    throws KeyStoreException
  {
    if (this.table.get(paramString) == null) {
      throw new KeyStoreException("no such entry as " + paramString);
    }
    this.table.remove(paramString);
  }
  
  public Certificate engineGetCertificate(String paramString)
  {
    StoreEntry localStoreEntry = (StoreEntry)this.table.get(paramString);
    if (localStoreEntry != null)
    {
      if (localStoreEntry.getType() == 1) {
        return (Certificate)localStoreEntry.getObject();
      }
      Certificate[] arrayOfCertificate = localStoreEntry.getCertificateChain();
      if (arrayOfCertificate != null) {
        return arrayOfCertificate[0];
      }
    }
    return null;
  }
  
  public String engineGetCertificateAlias(Certificate paramCertificate)
  {
    Enumeration localEnumeration = this.table.elements();
    while (localEnumeration.hasMoreElements())
    {
      StoreEntry localStoreEntry = (StoreEntry)localEnumeration.nextElement();
      if ((localStoreEntry.getObject() instanceof Certificate))
      {
        if (((Certificate)localStoreEntry.getObject()).equals(paramCertificate)) {
          return localStoreEntry.getAlias();
        }
      }
      else
      {
        Certificate[] arrayOfCertificate = localStoreEntry.getCertificateChain();
        if ((arrayOfCertificate != null) && (arrayOfCertificate[0].equals(paramCertificate))) {
          return localStoreEntry.getAlias();
        }
      }
    }
    return null;
  }
  
  public Certificate[] engineGetCertificateChain(String paramString)
  {
    StoreEntry localStoreEntry = (StoreEntry)this.table.get(paramString);
    if (localStoreEntry != null) {
      return localStoreEntry.getCertificateChain();
    }
    return null;
  }
  
  public Date engineGetCreationDate(String paramString)
  {
    StoreEntry localStoreEntry = (StoreEntry)this.table.get(paramString);
    if (localStoreEntry != null) {
      return localStoreEntry.getDate();
    }
    return null;
  }
  
  public Key engineGetKey(String paramString, char[] paramArrayOfChar)
    throws NoSuchAlgorithmException, UnrecoverableKeyException
  {
    StoreEntry localStoreEntry = (StoreEntry)this.table.get(paramString);
    if ((localStoreEntry == null) || (localStoreEntry.getType() == 1)) {
      return null;
    }
    return (Key)localStoreEntry.getObject(paramArrayOfChar);
  }
  
  public boolean engineIsCertificateEntry(String paramString)
  {
    StoreEntry localStoreEntry = (StoreEntry)this.table.get(paramString);
    return (localStoreEntry != null) && (localStoreEntry.getType() == 1);
  }
  
  public boolean engineIsKeyEntry(String paramString)
  {
    StoreEntry localStoreEntry = (StoreEntry)this.table.get(paramString);
    return (localStoreEntry != null) && (localStoreEntry.getType() != 1);
  }
  
  public void engineLoad(InputStream paramInputStream, char[] paramArrayOfChar)
    throws IOException
  {
    this.table.clear();
    if (paramInputStream == null) {
      return;
    }
    DataInputStream localDataInputStream = new DataInputStream(paramInputStream);
    int i = localDataInputStream.readInt();
    if ((i != 2) && (i != 0) && (i != 1)) {
      throw new IOException("Wrong version of key store.");
    }
    int j = localDataInputStream.readInt();
    if (j <= 0) {
      throw new IOException("Invalid salt detected");
    }
    byte[] arrayOfByte1 = new byte[j];
    localDataInputStream.readFully(arrayOfByte1);
    int k = localDataInputStream.readInt();
    HMac localHMac = new HMac(new SHA1Digest());
    if ((paramArrayOfChar != null) && (paramArrayOfChar.length != 0))
    {
      byte[] arrayOfByte2 = PBEParametersGenerator.PKCS12PasswordToBytes(paramArrayOfChar);
      PKCS12ParametersGenerator localPKCS12ParametersGenerator = new PKCS12ParametersGenerator(new SHA1Digest());
      localPKCS12ParametersGenerator.init(arrayOfByte2, arrayOfByte1, k);
      if (i != 2) {}
      for (CipherParameters localCipherParameters = localPKCS12ParametersGenerator.generateDerivedMacParameters(localHMac.getMacSize());; localCipherParameters = localPKCS12ParametersGenerator.generateDerivedMacParameters(8 * localHMac.getMacSize()))
      {
        Arrays.fill(arrayOfByte2, (byte)0);
        localHMac.init(localCipherParameters);
        loadStore(new MacInputStream(localDataInputStream, localHMac));
        byte[] arrayOfByte3 = new byte[localHMac.getMacSize()];
        localHMac.doFinal(arrayOfByte3, 0);
        byte[] arrayOfByte4 = new byte[localHMac.getMacSize()];
        localDataInputStream.readFully(arrayOfByte4);
        if (Arrays.constantTimeAreEqual(arrayOfByte3, arrayOfByte4)) {
          break;
        }
        this.table.clear();
        throw new IOException("KeyStore integrity check failed.");
      }
    }
    loadStore(localDataInputStream);
    localDataInputStream.readFully(new byte[localHMac.getMacSize()]);
  }
  
  public void engineSetCertificateEntry(String paramString, Certificate paramCertificate)
    throws KeyStoreException
  {
    StoreEntry localStoreEntry = (StoreEntry)this.table.get(paramString);
    if ((localStoreEntry != null) && (localStoreEntry.getType() != 1)) {
      throw new KeyStoreException("key store already has a key entry with alias " + paramString);
    }
    this.table.put(paramString, new StoreEntry(paramString, paramCertificate));
  }
  
  public void engineSetKeyEntry(String paramString, Key paramKey, char[] paramArrayOfChar, Certificate[] paramArrayOfCertificate)
    throws KeyStoreException
  {
    if (((paramKey instanceof PrivateKey)) && (paramArrayOfCertificate == null)) {
      throw new KeyStoreException("no certificate chain for private key");
    }
    try
    {
      this.table.put(paramString, new StoreEntry(paramString, paramKey, paramArrayOfChar, paramArrayOfCertificate));
      return;
    }
    catch (Exception localException)
    {
      throw new KeyStoreException(localException.toString());
    }
  }
  
  public void engineSetKeyEntry(String paramString, byte[] paramArrayOfByte, Certificate[] paramArrayOfCertificate)
    throws KeyStoreException
  {
    this.table.put(paramString, new StoreEntry(paramString, paramArrayOfByte, paramArrayOfCertificate));
  }
  
  public int engineSize()
  {
    return this.table.size();
  }
  
  public void engineStore(OutputStream paramOutputStream, char[] paramArrayOfChar)
    throws IOException
  {
    DataOutputStream localDataOutputStream = new DataOutputStream(paramOutputStream);
    byte[] arrayOfByte1 = new byte[20];
    int i = 1024 + (0x3FF & this.random.nextInt());
    this.random.nextBytes(arrayOfByte1);
    localDataOutputStream.writeInt(2);
    localDataOutputStream.writeInt(arrayOfByte1.length);
    localDataOutputStream.write(arrayOfByte1);
    localDataOutputStream.writeInt(i);
    HMac localHMac = new HMac(new SHA1Digest());
    MacOutputStream localMacOutputStream = new MacOutputStream(localHMac);
    PKCS12ParametersGenerator localPKCS12ParametersGenerator = new PKCS12ParametersGenerator(new SHA1Digest());
    byte[] arrayOfByte2 = PBEParametersGenerator.PKCS12PasswordToBytes(paramArrayOfChar);
    localPKCS12ParametersGenerator.init(arrayOfByte2, arrayOfByte1, i);
    localHMac.init(localPKCS12ParametersGenerator.generateDerivedMacParameters(8 * localHMac.getMacSize()));
    for (int j = 0; j != arrayOfByte2.length; j++) {
      arrayOfByte2[j] = 0;
    }
    saveStore(new TeeOutputStream(localDataOutputStream, localMacOutputStream));
    byte[] arrayOfByte3 = new byte[localHMac.getMacSize()];
    localHMac.doFinal(arrayOfByte3, 0);
    localDataOutputStream.write(arrayOfByte3);
    localDataOutputStream.close();
  }
  
  protected void loadStore(InputStream paramInputStream)
    throws IOException
  {
    DataInputStream localDataInputStream = new DataInputStream(paramInputStream);
    int i = localDataInputStream.read();
    if (i > 0)
    {
      String str = localDataInputStream.readUTF();
      Date localDate = new Date(localDataInputStream.readLong());
      int j = localDataInputStream.readInt();
      Certificate[] arrayOfCertificate = null;
      if (j != 0)
      {
        arrayOfCertificate = new Certificate[j];
        for (int k = 0; k != j; k++) {
          arrayOfCertificate[k] = decodeCertificate(localDataInputStream);
        }
      }
      switch (i)
      {
      default: 
        throw new RuntimeException("Unknown object type in store.");
      case 1: 
        Certificate localCertificate = decodeCertificate(localDataInputStream);
        this.table.put(str, new StoreEntry(str, localDate, 1, localCertificate));
      }
      for (;;)
      {
        i = localDataInputStream.read();
        break;
        Key localKey = decodeKey(localDataInputStream);
        this.table.put(str, new StoreEntry(str, localDate, 2, localKey, arrayOfCertificate));
        continue;
        byte[] arrayOfByte = new byte[localDataInputStream.readInt()];
        localDataInputStream.readFully(arrayOfByte);
        this.table.put(str, new StoreEntry(str, localDate, i, arrayOfByte, arrayOfCertificate));
      }
    }
  }
  
  protected Cipher makePBECipher(String paramString, int paramInt1, char[] paramArrayOfChar, byte[] paramArrayOfByte, int paramInt2)
    throws IOException
  {
    try
    {
      PBEKeySpec localPBEKeySpec = new PBEKeySpec(paramArrayOfChar);
      SecretKeyFactory localSecretKeyFactory = SecretKeyFactory.getInstance(paramString, BouncyCastleProvider.PROVIDER_NAME);
      PBEParameterSpec localPBEParameterSpec = new PBEParameterSpec(paramArrayOfByte, paramInt2);
      Cipher localCipher = Cipher.getInstance(paramString, BouncyCastleProvider.PROVIDER_NAME);
      localCipher.init(paramInt1, localSecretKeyFactory.generateSecret(localPBEKeySpec), localPBEParameterSpec);
      return localCipher;
    }
    catch (Exception localException)
    {
      throw new IOException("Error initialising store of key store: " + localException);
    }
  }
  
  protected void saveStore(OutputStream paramOutputStream)
    throws IOException
  {
    Enumeration localEnumeration = this.table.elements();
    DataOutputStream localDataOutputStream = new DataOutputStream(paramOutputStream);
    while (localEnumeration.hasMoreElements())
    {
      StoreEntry localStoreEntry = (StoreEntry)localEnumeration.nextElement();
      localDataOutputStream.write(localStoreEntry.getType());
      localDataOutputStream.writeUTF(localStoreEntry.getAlias());
      localDataOutputStream.writeLong(localStoreEntry.getDate().getTime());
      Certificate[] arrayOfCertificate = localStoreEntry.getCertificateChain();
      if (arrayOfCertificate == null) {
        localDataOutputStream.writeInt(0);
      }
      switch (localStoreEntry.getType())
      {
      default: 
        throw new RuntimeException("Unknown object type in store.");
        localDataOutputStream.writeInt(arrayOfCertificate.length);
        for (int i = 0; i != arrayOfCertificate.length; i++) {
          encodeCertificate(arrayOfCertificate[i], localDataOutputStream);
        }
      case 1: 
        encodeCertificate((Certificate)localStoreEntry.getObject(), localDataOutputStream);
        break;
      case 2: 
        encodeKey((Key)localStoreEntry.getObject(), localDataOutputStream);
        break;
      case 3: 
      case 4: 
        byte[] arrayOfByte = (byte[])localStoreEntry.getObject();
        localDataOutputStream.writeInt(arrayOfByte.length);
        localDataOutputStream.write(arrayOfByte);
      }
    }
    localDataOutputStream.write(0);
  }
  
  public void setRandom(SecureRandom paramSecureRandom)
  {
    this.random = paramSecureRandom;
  }
  
  public static class BouncyCastleStore
    extends JDKKeyStore
  {
    public void engineLoad(InputStream paramInputStream, char[] paramArrayOfChar)
      throws IOException
    {
      this.table.clear();
      if (paramInputStream == null) {
        return;
      }
      DataInputStream localDataInputStream = new DataInputStream(paramInputStream);
      int i = localDataInputStream.readInt();
      if ((i != 2) && (i != 0) && (i != 1)) {
        throw new IOException("Wrong version of key store.");
      }
      byte[] arrayOfByte1 = new byte[localDataInputStream.readInt()];
      if (arrayOfByte1.length != 20) {
        throw new IOException("Key store corrupted.");
      }
      localDataInputStream.readFully(arrayOfByte1);
      int j = localDataInputStream.readInt();
      if ((j < 0) || (j > 4096)) {
        throw new IOException("Key store corrupted.");
      }
      if (i == 0) {}
      for (String str = "OldPBEWithSHAAndTwofish-CBC";; str = "PBEWithSHAAndTwofish-CBC")
      {
        CipherInputStream localCipherInputStream = new CipherInputStream(localDataInputStream, makePBECipher(str, 2, paramArrayOfChar, arrayOfByte1, j));
        SHA1Digest localSHA1Digest = new SHA1Digest();
        loadStore(new DigestInputStream(localCipherInputStream, localSHA1Digest));
        byte[] arrayOfByte2 = new byte[localSHA1Digest.getDigestSize()];
        localSHA1Digest.doFinal(arrayOfByte2, 0);
        byte[] arrayOfByte3 = new byte[localSHA1Digest.getDigestSize()];
        Streams.readFully(localCipherInputStream, arrayOfByte3);
        if (Arrays.constantTimeAreEqual(arrayOfByte2, arrayOfByte3)) {
          break;
        }
        this.table.clear();
        throw new IOException("KeyStore integrity check failed.");
      }
    }
    
    public void engineStore(OutputStream paramOutputStream, char[] paramArrayOfChar)
      throws IOException
    {
      DataOutputStream localDataOutputStream = new DataOutputStream(paramOutputStream);
      byte[] arrayOfByte = new byte[20];
      int i = 1024 + (0x3FF & this.random.nextInt());
      this.random.nextBytes(arrayOfByte);
      localDataOutputStream.writeInt(2);
      localDataOutputStream.writeInt(arrayOfByte.length);
      localDataOutputStream.write(arrayOfByte);
      localDataOutputStream.writeInt(i);
      CipherOutputStream localCipherOutputStream = new CipherOutputStream(localDataOutputStream, makePBECipher("PBEWithSHAAndTwofish-CBC", 1, paramArrayOfChar, arrayOfByte, i));
      DigestOutputStream localDigestOutputStream = new DigestOutputStream(new SHA1Digest());
      saveStore(new TeeOutputStream(localCipherOutputStream, localDigestOutputStream));
      localCipherOutputStream.write(localDigestOutputStream.getDigest());
      localCipherOutputStream.close();
    }
  }
  
  private class StoreEntry
  {
    String alias;
    Certificate[] certChain;
    Date date = new Date();
    Object obj;
    int type;
    
    StoreEntry(String paramString, Key paramKey, char[] paramArrayOfChar, Certificate[] paramArrayOfCertificate)
      throws Exception
    {
      this.type = 4;
      this.alias = paramString;
      this.certChain = paramArrayOfCertificate;
      byte[] arrayOfByte = new byte[20];
      JDKKeyStore.this.random.setSeed(System.currentTimeMillis());
      JDKKeyStore.this.random.nextBytes(arrayOfByte);
      int i = 1024 + (0x3FF & JDKKeyStore.this.random.nextInt());
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
      DataOutputStream localDataOutputStream1 = new DataOutputStream(localByteArrayOutputStream);
      localDataOutputStream1.writeInt(arrayOfByte.length);
      localDataOutputStream1.write(arrayOfByte);
      localDataOutputStream1.writeInt(i);
      DataOutputStream localDataOutputStream2 = new DataOutputStream(new CipherOutputStream(localDataOutputStream1, JDKKeyStore.this.makePBECipher("PBEWithSHAAnd3-KeyTripleDES-CBC", 1, paramArrayOfChar, arrayOfByte, i)));
      JDKKeyStore.this.encodeKey(paramKey, localDataOutputStream2);
      localDataOutputStream2.close();
      this.obj = localByteArrayOutputStream.toByteArray();
    }
    
    StoreEntry(String paramString, Certificate paramCertificate)
    {
      this.type = 1;
      this.alias = paramString;
      this.obj = paramCertificate;
      this.certChain = null;
    }
    
    StoreEntry(String paramString, Date paramDate, int paramInt, Object paramObject)
    {
      this.alias = paramString;
      this.date = paramDate;
      this.type = paramInt;
      this.obj = paramObject;
    }
    
    StoreEntry(String paramString, Date paramDate, int paramInt, Object paramObject, Certificate[] paramArrayOfCertificate)
    {
      this.alias = paramString;
      this.date = paramDate;
      this.type = paramInt;
      this.obj = paramObject;
      this.certChain = paramArrayOfCertificate;
    }
    
    StoreEntry(String paramString, byte[] paramArrayOfByte, Certificate[] paramArrayOfCertificate)
    {
      this.type = 3;
      this.alias = paramString;
      this.obj = paramArrayOfByte;
      this.certChain = paramArrayOfCertificate;
    }
    
    String getAlias()
    {
      return this.alias;
    }
    
    Certificate[] getCertificateChain()
    {
      return this.certChain;
    }
    
    Date getDate()
    {
      return this.date;
    }
    
    Object getObject()
    {
      return this.obj;
    }
    
    /* Error */
    Object getObject(char[] paramArrayOfChar)
      throws NoSuchAlgorithmException, UnrecoverableKeyException
    {
      // Byte code:
      //   0: aload_1
      //   1: ifnull +8 -> 9
      //   4: aload_1
      //   5: arraylength
      //   6: ifne +18 -> 24
      //   9: aload_0
      //   10: getfield 101	org/spongycastle/jce/provider/JDKKeyStore$StoreEntry:obj	Ljava/lang/Object;
      //   13: instanceof 120
      //   16: ifeq +8 -> 24
      //   19: aload_0
      //   20: getfield 101	org/spongycastle/jce/provider/JDKKeyStore$StoreEntry:obj	Ljava/lang/Object;
      //   23: areturn
      //   24: aload_0
      //   25: getfield 32	org/spongycastle/jce/provider/JDKKeyStore$StoreEntry:type	I
      //   28: iconst_4
      //   29: if_icmpne +428 -> 457
      //   32: new 122	java/io/DataInputStream
      //   35: dup
      //   36: new 124	java/io/ByteArrayInputStream
      //   39: dup
      //   40: aload_0
      //   41: getfield 101	org/spongycastle/jce/provider/JDKKeyStore$StoreEntry:obj	Ljava/lang/Object;
      //   44: checkcast 126	[B
      //   47: checkcast 126	[B
      //   50: invokespecial 128	java/io/ByteArrayInputStream:<init>	([B)V
      //   53: invokespecial 131	java/io/DataInputStream:<init>	(Ljava/io/InputStream;)V
      //   56: astore_2
      //   57: aload_2
      //   58: invokevirtual 134	java/io/DataInputStream:readInt	()I
      //   61: newarray byte
      //   63: astore 4
      //   65: aload_2
      //   66: aload 4
      //   68: invokevirtual 137	java/io/DataInputStream:readFully	([B)V
      //   71: aload_2
      //   72: invokevirtual 134	java/io/DataInputStream:readInt	()I
      //   75: istore 5
      //   77: new 139	javax/crypto/CipherInputStream
      //   80: dup
      //   81: aload_2
      //   82: aload_0
      //   83: getfield 22	org/spongycastle/jce/provider/JDKKeyStore$StoreEntry:this$0	Lorg/spongycastle/jce/provider/JDKKeyStore;
      //   86: ldc 81
      //   88: iconst_2
      //   89: aload_1
      //   90: aload 4
      //   92: iload 5
      //   94: invokevirtual 85	org/spongycastle/jce/provider/JDKKeyStore:makePBECipher	(Ljava/lang/String;I[C[BI)Ljavax/crypto/Cipher;
      //   97: invokespecial 142	javax/crypto/CipherInputStream:<init>	(Ljava/io/InputStream;Ljavax/crypto/Cipher;)V
      //   100: astore 6
      //   102: aload_0
      //   103: getfield 22	org/spongycastle/jce/provider/JDKKeyStore$StoreEntry:this$0	Lorg/spongycastle/jce/provider/JDKKeyStore;
      //   106: new 122	java/io/DataInputStream
      //   109: dup
      //   110: aload 6
      //   112: invokespecial 131	java/io/DataInputStream:<init>	(Ljava/io/InputStream;)V
      //   115: invokestatic 146	org/spongycastle/jce/provider/JDKKeyStore:access$100	(Lorg/spongycastle/jce/provider/JDKKeyStore;Ljava/io/DataInputStream;)Ljava/security/Key;
      //   118: astore 25
      //   120: aload 25
      //   122: areturn
      //   123: astore 7
      //   125: new 124	java/io/ByteArrayInputStream
      //   128: dup
      //   129: aload_0
      //   130: getfield 101	org/spongycastle/jce/provider/JDKKeyStore$StoreEntry:obj	Ljava/lang/Object;
      //   133: checkcast 126	[B
      //   136: checkcast 126	[B
      //   139: invokespecial 128	java/io/ByteArrayInputStream:<init>	([B)V
      //   142: astore 8
      //   144: new 122	java/io/DataInputStream
      //   147: dup
      //   148: aload 8
      //   150: invokespecial 131	java/io/DataInputStream:<init>	(Ljava/io/InputStream;)V
      //   153: astore 9
      //   155: aload 9
      //   157: invokevirtual 134	java/io/DataInputStream:readInt	()I
      //   160: newarray byte
      //   162: astore 11
      //   164: aload 9
      //   166: aload 11
      //   168: invokevirtual 137	java/io/DataInputStream:readFully	([B)V
      //   171: aload 9
      //   173: invokevirtual 134	java/io/DataInputStream:readInt	()I
      //   176: istore 12
      //   178: new 139	javax/crypto/CipherInputStream
      //   181: dup
      //   182: aload 9
      //   184: aload_0
      //   185: getfield 22	org/spongycastle/jce/provider/JDKKeyStore$StoreEntry:this$0	Lorg/spongycastle/jce/provider/JDKKeyStore;
      //   188: ldc 148
      //   190: iconst_2
      //   191: aload_1
      //   192: aload 11
      //   194: iload 12
      //   196: invokevirtual 85	org/spongycastle/jce/provider/JDKKeyStore:makePBECipher	(Ljava/lang/String;I[C[BI)Ljavax/crypto/Cipher;
      //   199: invokespecial 142	javax/crypto/CipherInputStream:<init>	(Ljava/io/InputStream;Ljavax/crypto/Cipher;)V
      //   202: astore 13
      //   204: aload_0
      //   205: getfield 22	org/spongycastle/jce/provider/JDKKeyStore$StoreEntry:this$0	Lorg/spongycastle/jce/provider/JDKKeyStore;
      //   208: new 122	java/io/DataInputStream
      //   211: dup
      //   212: aload 13
      //   214: invokespecial 131	java/io/DataInputStream:<init>	(Ljava/io/InputStream;)V
      //   217: invokestatic 146	org/spongycastle/jce/provider/JDKKeyStore:access$100	(Lorg/spongycastle/jce/provider/JDKKeyStore;Ljava/io/DataInputStream;)Ljava/security/Key;
      //   220: astore 23
      //   222: aload 23
      //   224: astore 18
      //   226: aload 18
      //   228: ifnull +219 -> 447
      //   231: new 64	java/io/ByteArrayOutputStream
      //   234: dup
      //   235: invokespecial 65	java/io/ByteArrayOutputStream:<init>	()V
      //   238: astore 19
      //   240: new 67	java/io/DataOutputStream
      //   243: dup
      //   244: aload 19
      //   246: invokespecial 70	java/io/DataOutputStream:<init>	(Ljava/io/OutputStream;)V
      //   249: astore 20
      //   251: aload 20
      //   253: aload 11
      //   255: arraylength
      //   256: invokevirtual 74	java/io/DataOutputStream:writeInt	(I)V
      //   259: aload 20
      //   261: aload 11
      //   263: invokevirtual 77	java/io/DataOutputStream:write	([B)V
      //   266: aload 20
      //   268: iload 12
      //   270: invokevirtual 74	java/io/DataOutputStream:writeInt	(I)V
      //   273: new 67	java/io/DataOutputStream
      //   276: dup
      //   277: new 79	javax/crypto/CipherOutputStream
      //   280: dup
      //   281: aload 20
      //   283: aload_0
      //   284: getfield 22	org/spongycastle/jce/provider/JDKKeyStore$StoreEntry:this$0	Lorg/spongycastle/jce/provider/JDKKeyStore;
      //   287: ldc 81
      //   289: iconst_1
      //   290: aload_1
      //   291: aload 11
      //   293: iload 12
      //   295: invokevirtual 85	org/spongycastle/jce/provider/JDKKeyStore:makePBECipher	(Ljava/lang/String;I[C[BI)Ljavax/crypto/Cipher;
      //   298: invokespecial 88	javax/crypto/CipherOutputStream:<init>	(Ljava/io/OutputStream;Ljavax/crypto/Cipher;)V
      //   301: invokespecial 70	java/io/DataOutputStream:<init>	(Ljava/io/OutputStream;)V
      //   304: astore 21
      //   306: aload_0
      //   307: getfield 22	org/spongycastle/jce/provider/JDKKeyStore$StoreEntry:this$0	Lorg/spongycastle/jce/provider/JDKKeyStore;
      //   310: aload 18
      //   312: aload 21
      //   314: invokestatic 92	org/spongycastle/jce/provider/JDKKeyStore:access$000	(Lorg/spongycastle/jce/provider/JDKKeyStore;Ljava/security/Key;Ljava/io/DataOutputStream;)V
      //   317: aload 21
      //   319: invokevirtual 95	java/io/DataOutputStream:close	()V
      //   322: aload_0
      //   323: aload 19
      //   325: invokevirtual 99	java/io/ByteArrayOutputStream:toByteArray	()[B
      //   328: putfield 101	org/spongycastle/jce/provider/JDKKeyStore$StoreEntry:obj	Ljava/lang/Object;
      //   331: aload 18
      //   333: areturn
      //   334: astore_3
      //   335: new 118	java/security/UnrecoverableKeyException
      //   338: dup
      //   339: ldc 150
      //   341: invokespecial 153	java/security/UnrecoverableKeyException:<init>	(Ljava/lang/String;)V
      //   344: athrow
      //   345: astore 14
      //   347: new 124	java/io/ByteArrayInputStream
      //   350: dup
      //   351: aload_0
      //   352: getfield 101	org/spongycastle/jce/provider/JDKKeyStore$StoreEntry:obj	Ljava/lang/Object;
      //   355: checkcast 126	[B
      //   358: checkcast 126	[B
      //   361: invokespecial 128	java/io/ByteArrayInputStream:<init>	([B)V
      //   364: astore 15
      //   366: new 122	java/io/DataInputStream
      //   369: dup
      //   370: aload 15
      //   372: invokespecial 131	java/io/DataInputStream:<init>	(Ljava/io/InputStream;)V
      //   375: astore 16
      //   377: aload 16
      //   379: invokevirtual 134	java/io/DataInputStream:readInt	()I
      //   382: newarray byte
      //   384: astore 11
      //   386: aload 16
      //   388: aload 11
      //   390: invokevirtual 137	java/io/DataInputStream:readFully	([B)V
      //   393: aload 16
      //   395: invokevirtual 134	java/io/DataInputStream:readInt	()I
      //   398: istore 12
      //   400: new 139	javax/crypto/CipherInputStream
      //   403: dup
      //   404: aload 16
      //   406: aload_0
      //   407: getfield 22	org/spongycastle/jce/provider/JDKKeyStore$StoreEntry:this$0	Lorg/spongycastle/jce/provider/JDKKeyStore;
      //   410: ldc 155
      //   412: iconst_2
      //   413: aload_1
      //   414: aload 11
      //   416: iload 12
      //   418: invokevirtual 85	org/spongycastle/jce/provider/JDKKeyStore:makePBECipher	(Ljava/lang/String;I[C[BI)Ljavax/crypto/Cipher;
      //   421: invokespecial 142	javax/crypto/CipherInputStream:<init>	(Ljava/io/InputStream;Ljavax/crypto/Cipher;)V
      //   424: astore 17
      //   426: aload_0
      //   427: getfield 22	org/spongycastle/jce/provider/JDKKeyStore$StoreEntry:this$0	Lorg/spongycastle/jce/provider/JDKKeyStore;
      //   430: new 122	java/io/DataInputStream
      //   433: dup
      //   434: aload 17
      //   436: invokespecial 131	java/io/DataInputStream:<init>	(Ljava/io/InputStream;)V
      //   439: invokestatic 146	org/spongycastle/jce/provider/JDKKeyStore:access$100	(Lorg/spongycastle/jce/provider/JDKKeyStore;Ljava/io/DataInputStream;)Ljava/security/Key;
      //   442: astore 18
      //   444: goto -218 -> 226
      //   447: new 118	java/security/UnrecoverableKeyException
      //   450: dup
      //   451: ldc 150
      //   453: invokespecial 153	java/security/UnrecoverableKeyException:<init>	(Ljava/lang/String;)V
      //   456: athrow
      //   457: new 157	java/lang/RuntimeException
      //   460: dup
      //   461: ldc 159
      //   463: invokespecial 160	java/lang/RuntimeException:<init>	(Ljava/lang/String;)V
      //   466: athrow
      //   467: astore 24
      //   469: goto -134 -> 335
      //   472: astore 10
      //   474: goto -139 -> 335
      //   477: astore 22
      //   479: goto -144 -> 335
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	482	0	this	StoreEntry
      //   0	482	1	paramArrayOfChar	char[]
      //   56	26	2	localDataInputStream1	DataInputStream
      //   334	1	3	localException1	Exception
      //   63	28	4	arrayOfByte1	byte[]
      //   75	18	5	i	int
      //   100	11	6	localCipherInputStream1	CipherInputStream
      //   123	1	7	localException2	Exception
      //   142	7	8	localByteArrayInputStream1	ByteArrayInputStream
      //   153	30	9	localDataInputStream2	DataInputStream
      //   472	1	10	localException3	Exception
      //   162	253	11	arrayOfByte2	byte[]
      //   176	241	12	j	int
      //   202	11	13	localCipherInputStream2	CipherInputStream
      //   345	1	14	localException4	Exception
      //   364	7	15	localByteArrayInputStream2	ByteArrayInputStream
      //   375	30	16	localDataInputStream3	DataInputStream
      //   424	11	17	localCipherInputStream3	CipherInputStream
      //   224	219	18	localKey1	Key
      //   238	86	19	localByteArrayOutputStream	ByteArrayOutputStream
      //   249	33	20	localDataOutputStream1	DataOutputStream
      //   304	14	21	localDataOutputStream2	DataOutputStream
      //   477	1	22	localException5	Exception
      //   220	3	23	localKey2	Key
      //   467	1	24	localException6	Exception
      //   118	3	25	localKey3	Key
      // Exception table:
      //   from	to	target	type
      //   102	120	123	java/lang/Exception
      //   57	102	334	java/lang/Exception
      //   125	144	334	java/lang/Exception
      //   231	331	334	java/lang/Exception
      //   377	444	334	java/lang/Exception
      //   447	457	334	java/lang/Exception
      //   204	222	345	java/lang/Exception
      //   144	155	467	java/lang/Exception
      //   155	204	472	java/lang/Exception
      //   347	366	472	java/lang/Exception
      //   366	377	477	java/lang/Exception
    }
    
    int getType()
    {
      return this.type;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jce.provider.JDKKeyStore
 * JD-Core Version:    0.7.0.1
 */