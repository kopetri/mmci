package org.spongycastle.jce;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.DERObjectIdentifier;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DEROutputStream;
import org.spongycastle.asn1.pkcs.ContentInfo;
import org.spongycastle.asn1.pkcs.MacData;
import org.spongycastle.asn1.pkcs.Pfx;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.DigestInfo;

public class PKCS12Util
{
  private static byte[] calculatePbeMac(DERObjectIdentifier paramDERObjectIdentifier, byte[] paramArrayOfByte1, int paramInt, char[] paramArrayOfChar, byte[] paramArrayOfByte2, String paramString)
    throws Exception
  {
    SecretKeyFactory localSecretKeyFactory = SecretKeyFactory.getInstance(paramDERObjectIdentifier.getId(), paramString);
    PBEParameterSpec localPBEParameterSpec = new PBEParameterSpec(paramArrayOfByte1, paramInt);
    SecretKey localSecretKey = localSecretKeyFactory.generateSecret(new PBEKeySpec(paramArrayOfChar));
    Mac localMac = Mac.getInstance(paramDERObjectIdentifier.getId(), paramString);
    localMac.init(localSecretKey, localPBEParameterSpec);
    localMac.update(paramArrayOfByte2);
    return localMac.doFinal();
  }
  
  public static byte[] convertToDefiniteLength(byte[] paramArrayOfByte)
    throws IOException
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    DEROutputStream localDEROutputStream = new DEROutputStream(localByteArrayOutputStream);
    Pfx localPfx = Pfx.getInstance(paramArrayOfByte);
    localByteArrayOutputStream.reset();
    localDEROutputStream.writeObject(localPfx);
    return localByteArrayOutputStream.toByteArray();
  }
  
  public static byte[] convertToDefiniteLength(byte[] paramArrayOfByte, char[] paramArrayOfChar, String paramString)
    throws IOException
  {
    Pfx localPfx1 = Pfx.getInstance(paramArrayOfByte);
    ContentInfo localContentInfo1 = localPfx1.getAuthSafe();
    ASN1OctetString localASN1OctetString = ASN1OctetString.getInstance(localContentInfo1.getContent());
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    DEROutputStream localDEROutputStream = new DEROutputStream(localByteArrayOutputStream);
    localDEROutputStream.writeObject(new ASN1InputStream(localASN1OctetString.getOctets()).readObject());
    ContentInfo localContentInfo2 = new ContentInfo(localContentInfo1.getContentType(), new DEROctetString(localByteArrayOutputStream.toByteArray()));
    MacData localMacData1 = localPfx1.getMacData();
    try
    {
      int i = localMacData1.getIterationCount().intValue();
      byte[] arrayOfByte1 = ASN1OctetString.getInstance(localContentInfo2.getContent()).getOctets();
      byte[] arrayOfByte2 = calculatePbeMac(localMacData1.getMac().getAlgorithmId().getObjectId(), localMacData1.getSalt(), i, paramArrayOfChar, arrayOfByte1, paramString);
      DigestInfo localDigestInfo = new DigestInfo(new AlgorithmIdentifier(localMacData1.getMac().getAlgorithmId().getObjectId(), new DERNull()), arrayOfByte2);
      MacData localMacData2 = new MacData(localDigestInfo, localMacData1.getSalt(), i);
      Pfx localPfx2 = new Pfx(localContentInfo2, localMacData2);
      localByteArrayOutputStream.reset();
      localDEROutputStream.writeObject(localPfx2);
      return localByteArrayOutputStream.toByteArray();
    }
    catch (Exception localException)
    {
      throw new IOException("error constructing MAC: " + localException.toString());
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jce.PKCS12Util
 * JD-Core Version:    0.7.0.1
 */