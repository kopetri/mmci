package org.spongycastle.asn1.x509.qualified;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERIA5String;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public class BiometricData
  extends ASN1Object
{
  private ASN1OctetString biometricDataHash;
  private AlgorithmIdentifier hashAlgorithm;
  private DERIA5String sourceDataUri;
  private TypeOfBiometricData typeOfBiometricData;
  
  private BiometricData(ASN1Sequence paramASN1Sequence)
  {
    Enumeration localEnumeration = paramASN1Sequence.getObjects();
    this.typeOfBiometricData = TypeOfBiometricData.getInstance(localEnumeration.nextElement());
    this.hashAlgorithm = AlgorithmIdentifier.getInstance(localEnumeration.nextElement());
    this.biometricDataHash = ASN1OctetString.getInstance(localEnumeration.nextElement());
    if (localEnumeration.hasMoreElements()) {
      this.sourceDataUri = DERIA5String.getInstance(localEnumeration.nextElement());
    }
  }
  
  public BiometricData(TypeOfBiometricData paramTypeOfBiometricData, AlgorithmIdentifier paramAlgorithmIdentifier, ASN1OctetString paramASN1OctetString)
  {
    this.typeOfBiometricData = paramTypeOfBiometricData;
    this.hashAlgorithm = paramAlgorithmIdentifier;
    this.biometricDataHash = paramASN1OctetString;
    this.sourceDataUri = null;
  }
  
  public BiometricData(TypeOfBiometricData paramTypeOfBiometricData, AlgorithmIdentifier paramAlgorithmIdentifier, ASN1OctetString paramASN1OctetString, DERIA5String paramDERIA5String)
  {
    this.typeOfBiometricData = paramTypeOfBiometricData;
    this.hashAlgorithm = paramAlgorithmIdentifier;
    this.biometricDataHash = paramASN1OctetString;
    this.sourceDataUri = paramDERIA5String;
  }
  
  public static BiometricData getInstance(Object paramObject)
  {
    if ((paramObject instanceof BiometricData)) {
      return (BiometricData)paramObject;
    }
    if (paramObject != null) {
      return new BiometricData(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public ASN1OctetString getBiometricDataHash()
  {
    return this.biometricDataHash;
  }
  
  public AlgorithmIdentifier getHashAlgorithm()
  {
    return this.hashAlgorithm;
  }
  
  public DERIA5String getSourceDataUri()
  {
    return this.sourceDataUri;
  }
  
  public TypeOfBiometricData getTypeOfBiometricData()
  {
    return this.typeOfBiometricData;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.typeOfBiometricData);
    localASN1EncodableVector.add(this.hashAlgorithm);
    localASN1EncodableVector.add(this.biometricDataHash);
    if (this.sourceDataUri != null) {
      localASN1EncodableVector.add(this.sourceDataUri);
    }
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x509.qualified.BiometricData
 * JD-Core Version:    0.7.0.1
 */