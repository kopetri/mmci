package org.spongycastle.asn1.util;

import java.io.IOException;
import java.util.Enumeration;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.BERApplicationSpecific;
import org.spongycastle.asn1.BERConstructedOctetString;
import org.spongycastle.asn1.BERSequence;
import org.spongycastle.asn1.BERSet;
import org.spongycastle.asn1.BERTaggedObject;
import org.spongycastle.asn1.DERApplicationSpecific;
import org.spongycastle.asn1.DERBMPString;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERBoolean;
import org.spongycastle.asn1.DEREnumerated;
import org.spongycastle.asn1.DERExternal;
import org.spongycastle.asn1.DERGeneralizedTime;
import org.spongycastle.asn1.DERIA5String;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERPrintableString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERSet;
import org.spongycastle.asn1.DERT61String;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.DERUTCTime;
import org.spongycastle.asn1.DERUTF8String;
import org.spongycastle.asn1.DERVisibleString;
import org.spongycastle.util.encoders.Hex;

public class ASN1Dump
{
  private static final int SAMPLE_SIZE = 32;
  private static final String TAB = "    ";
  
  static void _dumpAsString(String paramString, boolean paramBoolean, ASN1Primitive paramASN1Primitive, StringBuffer paramStringBuffer)
  {
    String str1 = System.getProperty("line.separator");
    if ((paramASN1Primitive instanceof ASN1Sequence))
    {
      Enumeration localEnumeration3 = ((ASN1Sequence)paramASN1Primitive).getObjects();
      String str6 = paramString + "    ";
      paramStringBuffer.append(paramString);
      if ((paramASN1Primitive instanceof BERSequence))
      {
        paramStringBuffer.append("BER Sequence");
        paramStringBuffer.append(str1);
      }
      for (;;)
      {
        if (!localEnumeration3.hasMoreElements()) {
          break label329;
        }
        Object localObject3 = localEnumeration3.nextElement();
        if ((localObject3 == null) || (localObject3.equals(new DERNull())))
        {
          paramStringBuffer.append(str6);
          paramStringBuffer.append("NULL");
          paramStringBuffer.append(str1);
          continue;
          if ((paramASN1Primitive instanceof DERSequence))
          {
            paramStringBuffer.append("DER Sequence");
            break;
          }
          paramStringBuffer.append("Sequence");
          break;
        }
        if ((localObject3 instanceof ASN1Primitive)) {
          _dumpAsString(str6, paramBoolean, (ASN1Primitive)localObject3, paramStringBuffer);
        } else {
          _dumpAsString(str6, paramBoolean, ((ASN1Encodable)localObject3).toASN1Primitive(), paramStringBuffer);
        }
      }
    }
    String str5;
    DERTaggedObject localDERTaggedObject;
    if ((paramASN1Primitive instanceof DERTaggedObject))
    {
      str5 = paramString + "    ";
      paramStringBuffer.append(paramString);
      if ((paramASN1Primitive instanceof BERTaggedObject))
      {
        paramStringBuffer.append("BER Tagged [");
        localDERTaggedObject = (DERTaggedObject)paramASN1Primitive;
        paramStringBuffer.append(Integer.toString(localDERTaggedObject.getTagNo()));
        paramStringBuffer.append(']');
        if (!localDERTaggedObject.isExplicit()) {
          paramStringBuffer.append(" IMPLICIT ");
        }
        paramStringBuffer.append(str1);
        if (!localDERTaggedObject.isEmpty()) {
          break label340;
        }
        paramStringBuffer.append(str5);
        paramStringBuffer.append("EMPTY");
        paramStringBuffer.append(str1);
      }
    }
    for (;;)
    {
      label329:
      return;
      paramStringBuffer.append("Tagged [");
      break;
      label340:
      _dumpAsString(str5, paramBoolean, localDERTaggedObject.getObject(), paramStringBuffer);
      return;
      if ((paramASN1Primitive instanceof BERSet))
      {
        Enumeration localEnumeration2 = ((ASN1Set)paramASN1Primitive).getObjects();
        String str4 = paramString + "    ";
        paramStringBuffer.append(paramString);
        paramStringBuffer.append("BER Set");
        paramStringBuffer.append(str1);
        while (localEnumeration2.hasMoreElements())
        {
          Object localObject2 = localEnumeration2.nextElement();
          if (localObject2 == null)
          {
            paramStringBuffer.append(str4);
            paramStringBuffer.append("NULL");
            paramStringBuffer.append(str1);
          }
          else if ((localObject2 instanceof ASN1Primitive))
          {
            _dumpAsString(str4, paramBoolean, (ASN1Primitive)localObject2, paramStringBuffer);
          }
          else
          {
            _dumpAsString(str4, paramBoolean, ((ASN1Encodable)localObject2).toASN1Primitive(), paramStringBuffer);
          }
        }
      }
      else
      {
        if (!(paramASN1Primitive instanceof DERSet)) {
          break label649;
        }
        Enumeration localEnumeration1 = ((ASN1Set)paramASN1Primitive).getObjects();
        String str3 = paramString + "    ";
        paramStringBuffer.append(paramString);
        paramStringBuffer.append("DER Set");
        paramStringBuffer.append(str1);
        while (localEnumeration1.hasMoreElements())
        {
          Object localObject1 = localEnumeration1.nextElement();
          if (localObject1 == null)
          {
            paramStringBuffer.append(str3);
            paramStringBuffer.append("NULL");
            paramStringBuffer.append(str1);
          }
          else if ((localObject1 instanceof ASN1Primitive))
          {
            _dumpAsString(str3, paramBoolean, (ASN1Primitive)localObject1, paramStringBuffer);
          }
          else
          {
            _dumpAsString(str3, paramBoolean, ((ASN1Encodable)localObject1).toASN1Primitive(), paramStringBuffer);
          }
        }
      }
    }
    label649:
    if ((paramASN1Primitive instanceof ASN1ObjectIdentifier))
    {
      paramStringBuffer.append(paramString + "ObjectIdentifier(" + ((ASN1ObjectIdentifier)paramASN1Primitive).getId() + ")" + str1);
      return;
    }
    if ((paramASN1Primitive instanceof DERBoolean))
    {
      paramStringBuffer.append(paramString + "Boolean(" + ((DERBoolean)paramASN1Primitive).isTrue() + ")" + str1);
      return;
    }
    if ((paramASN1Primitive instanceof ASN1Integer))
    {
      paramStringBuffer.append(paramString + "Integer(" + ((ASN1Integer)paramASN1Primitive).getValue() + ")" + str1);
      return;
    }
    if ((paramASN1Primitive instanceof BERConstructedOctetString))
    {
      ASN1OctetString localASN1OctetString2 = (ASN1OctetString)paramASN1Primitive;
      paramStringBuffer.append(paramString + "BER Constructed Octet String[" + localASN1OctetString2.getOctets().length + "] ");
      if (paramBoolean)
      {
        paramStringBuffer.append(dumpBinaryDataAsString(paramString, localASN1OctetString2.getOctets()));
        return;
      }
      paramStringBuffer.append(str1);
      return;
    }
    if ((paramASN1Primitive instanceof DEROctetString))
    {
      ASN1OctetString localASN1OctetString1 = (ASN1OctetString)paramASN1Primitive;
      paramStringBuffer.append(paramString + "DER Octet String[" + localASN1OctetString1.getOctets().length + "] ");
      if (paramBoolean)
      {
        paramStringBuffer.append(dumpBinaryDataAsString(paramString, localASN1OctetString1.getOctets()));
        return;
      }
      paramStringBuffer.append(str1);
      return;
    }
    if ((paramASN1Primitive instanceof DERBitString))
    {
      DERBitString localDERBitString = (DERBitString)paramASN1Primitive;
      paramStringBuffer.append(paramString + "DER Bit String[" + localDERBitString.getBytes().length + ", " + localDERBitString.getPadBits() + "] ");
      if (paramBoolean)
      {
        paramStringBuffer.append(dumpBinaryDataAsString(paramString, localDERBitString.getBytes()));
        return;
      }
      paramStringBuffer.append(str1);
      return;
    }
    if ((paramASN1Primitive instanceof DERIA5String))
    {
      paramStringBuffer.append(paramString + "IA5String(" + ((DERIA5String)paramASN1Primitive).getString() + ") " + str1);
      return;
    }
    if ((paramASN1Primitive instanceof DERUTF8String))
    {
      paramStringBuffer.append(paramString + "UTF8String(" + ((DERUTF8String)paramASN1Primitive).getString() + ") " + str1);
      return;
    }
    if ((paramASN1Primitive instanceof DERPrintableString))
    {
      paramStringBuffer.append(paramString + "PrintableString(" + ((DERPrintableString)paramASN1Primitive).getString() + ") " + str1);
      return;
    }
    if ((paramASN1Primitive instanceof DERVisibleString))
    {
      paramStringBuffer.append(paramString + "VisibleString(" + ((DERVisibleString)paramASN1Primitive).getString() + ") " + str1);
      return;
    }
    if ((paramASN1Primitive instanceof DERBMPString))
    {
      paramStringBuffer.append(paramString + "BMPString(" + ((DERBMPString)paramASN1Primitive).getString() + ") " + str1);
      return;
    }
    if ((paramASN1Primitive instanceof DERT61String))
    {
      paramStringBuffer.append(paramString + "T61String(" + ((DERT61String)paramASN1Primitive).getString() + ") " + str1);
      return;
    }
    if ((paramASN1Primitive instanceof DERUTCTime))
    {
      paramStringBuffer.append(paramString + "UTCTime(" + ((DERUTCTime)paramASN1Primitive).getTime() + ") " + str1);
      return;
    }
    if ((paramASN1Primitive instanceof DERGeneralizedTime))
    {
      paramStringBuffer.append(paramString + "GeneralizedTime(" + ((DERGeneralizedTime)paramASN1Primitive).getTime() + ") " + str1);
      return;
    }
    if ((paramASN1Primitive instanceof BERApplicationSpecific))
    {
      paramStringBuffer.append(outputApplicationSpecific("BER", paramString, paramBoolean, paramASN1Primitive, str1));
      return;
    }
    if ((paramASN1Primitive instanceof DERApplicationSpecific))
    {
      paramStringBuffer.append(outputApplicationSpecific("DER", paramString, paramBoolean, paramASN1Primitive, str1));
      return;
    }
    if ((paramASN1Primitive instanceof DEREnumerated))
    {
      DEREnumerated localDEREnumerated = (DEREnumerated)paramASN1Primitive;
      paramStringBuffer.append(paramString + "DER Enumerated(" + localDEREnumerated.getValue() + ")" + str1);
      return;
    }
    if ((paramASN1Primitive instanceof DERExternal))
    {
      DERExternal localDERExternal = (DERExternal)paramASN1Primitive;
      paramStringBuffer.append(paramString + "External " + str1);
      String str2 = paramString + "    ";
      if (localDERExternal.getDirectReference() != null) {
        paramStringBuffer.append(str2 + "Direct Reference: " + localDERExternal.getDirectReference().getId() + str1);
      }
      if (localDERExternal.getIndirectReference() != null) {
        paramStringBuffer.append(str2 + "Indirect Reference: " + localDERExternal.getIndirectReference().toString() + str1);
      }
      if (localDERExternal.getDataValueDescriptor() != null) {
        _dumpAsString(str2, paramBoolean, localDERExternal.getDataValueDescriptor(), paramStringBuffer);
      }
      paramStringBuffer.append(str2 + "Encoding: " + localDERExternal.getEncoding() + str1);
      _dumpAsString(str2, paramBoolean, localDERExternal.getExternalContent(), paramStringBuffer);
      return;
    }
    paramStringBuffer.append(paramString + paramASN1Primitive.toString() + str1);
  }
  
  private static String calculateAscString(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    for (int i = paramInt1; i != paramInt1 + paramInt2; i++) {
      if ((paramArrayOfByte[i] >= 32) && (paramArrayOfByte[i] <= 126)) {
        localStringBuffer.append((char)paramArrayOfByte[i]);
      }
    }
    return localStringBuffer.toString();
  }
  
  public static String dumpAsString(Object paramObject)
  {
    return dumpAsString(paramObject, false);
  }
  
  public static String dumpAsString(Object paramObject, boolean paramBoolean)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if ((paramObject instanceof ASN1Primitive)) {
      _dumpAsString("", paramBoolean, (ASN1Primitive)paramObject, localStringBuffer);
    }
    for (;;)
    {
      return localStringBuffer.toString();
      if (!(paramObject instanceof ASN1Encodable)) {
        break;
      }
      _dumpAsString("", paramBoolean, ((ASN1Encodable)paramObject).toASN1Primitive(), localStringBuffer);
    }
    return "unknown object type " + paramObject.toString();
  }
  
  private static String dumpBinaryDataAsString(String paramString, byte[] paramArrayOfByte)
  {
    String str1 = System.getProperty("line.separator");
    StringBuffer localStringBuffer = new StringBuffer();
    String str2 = paramString + "    ";
    localStringBuffer.append(str1);
    int i = 0;
    if (i < paramArrayOfByte.length)
    {
      if (paramArrayOfByte.length - i > 32)
      {
        localStringBuffer.append(str2);
        localStringBuffer.append(new String(Hex.encode(paramArrayOfByte, i, 32)));
        localStringBuffer.append("    ");
        localStringBuffer.append(calculateAscString(paramArrayOfByte, i, 32));
        localStringBuffer.append(str1);
      }
      for (;;)
      {
        i += 32;
        break;
        localStringBuffer.append(str2);
        localStringBuffer.append(new String(Hex.encode(paramArrayOfByte, i, paramArrayOfByte.length - i)));
        for (int j = paramArrayOfByte.length - i; j != 32; j++) {
          localStringBuffer.append("  ");
        }
        localStringBuffer.append("    ");
        localStringBuffer.append(calculateAscString(paramArrayOfByte, i, paramArrayOfByte.length - i));
        localStringBuffer.append(str1);
      }
    }
    return localStringBuffer.toString();
  }
  
  private static String outputApplicationSpecific(String paramString1, String paramString2, boolean paramBoolean, ASN1Primitive paramASN1Primitive, String paramString3)
  {
    DERApplicationSpecific localDERApplicationSpecific = (DERApplicationSpecific)paramASN1Primitive;
    StringBuffer localStringBuffer = new StringBuffer();
    if (localDERApplicationSpecific.isConstructed()) {
      try
      {
        ASN1Sequence localASN1Sequence = ASN1Sequence.getInstance(localDERApplicationSpecific.getObject(16));
        localStringBuffer.append(paramString2 + paramString1 + " ApplicationSpecific[" + localDERApplicationSpecific.getApplicationTag() + "]" + paramString3);
        Enumeration localEnumeration = localASN1Sequence.getObjects();
        while (localEnumeration.hasMoreElements()) {
          _dumpAsString(paramString2 + "    ", paramBoolean, (ASN1Primitive)localEnumeration.nextElement(), localStringBuffer);
        }
        return localStringBuffer.toString();
      }
      catch (IOException localIOException)
      {
        localStringBuffer.append(localIOException);
      }
    }
    return paramString2 + paramString1 + " ApplicationSpecific[" + localDERApplicationSpecific.getApplicationTag() + "] (" + new String(Hex.encode(localDERApplicationSpecific.getContents())) + ")" + paramString3;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.util.ASN1Dump
 * JD-Core Version:    0.7.0.1
 */