package org.spongycastle.asn1.eac;

import org.spongycastle.asn1.DERApplicationSpecific;

public class EACTags
{
  public static final int ADDRESS = 24386;
  public static final int ANSWER_TO_RESET = 24401;
  public static final int APPLICATION_EFFECTIVE_DATE = 37;
  public static final int APPLICATION_EXPIRATION_DATE = 36;
  public static final int APPLICATION_IDENTIFIER = 79;
  public static final int APPLICATION_IMAGE = 24388;
  public static final int APPLICATION_IMAGE_TEMPLATE = 109;
  public static final int APPLICATION_LABEL = 80;
  public static final int APPLICATION_RELATED_DATA = 110;
  public static final int APPLICATION_TEMPLATE = 97;
  public static final int AUTHENTIFICATION_DATA = 103;
  public static final int BIOMETRIC_DATA_TEMPLATE = 32558;
  public static final int BIOMETRIC_INFORMATION_GROUP_TEMPLATE = 32609;
  public static final int BIOMETRIC_INFORMATION_TEMPLATE = 32608;
  public static final int CARDHOLDER_BIOMETRIC_DATA = 24366;
  public static final int CARDHOLDER_CERTIFICATE = 33;
  public static final int CARDHOLDER_HANDWRITTEN_SIGNATURE = 24387;
  public static final int CARDHOLDER_IMAGE_TEMPLATE = 108;
  public static final int CARDHOLDER_NAME = 32;
  public static final int CARDHOLDER_NATIONALITY = 24364;
  public static final int CARDHOLDER_PORTRAIT_IMAGE = 24384;
  public static final int CARDHOLDER_PRIVATE_KEY = 24392;
  public static final int CARDHOLDER_PRIVATE_KEY_TEMPLATE = 32584;
  public static final int CARDHOLDER_PUBLIC_KEY = 24393;
  public static final int CARDHOLDER_PUBLIC_KEY_TEMPLATE = 73;
  public static final int CARDHOLDER_RELATIVE_DATA = 101;
  public static final int CARDHOLER_REQUIREMENTS_EXCLUDED_FEATURES = 32547;
  public static final int CARDHOLER_REQUIREMENTS_INCLUDED_FEATURES = 32546;
  public static final int CARD_CAPABILITIES = 71;
  public static final int CARD_DATA = 102;
  public static final int CARD_EFFECTIVE_DATE = 24358;
  public static final int CARD_EXPIRATION_DATA = 89;
  public static final int CARD_ISSUER_DATA = 69;
  public static final int CARD_SEQUENCE_NUMBER = 24372;
  public static final int CARD_SERVICE_DATA = 67;
  public static final int CERTIFICATE_BODY = 78;
  public static final int CERTIFICATE_CONTENT = 24398;
  public static final int CERTIFICATE_CONTENT_TEMPLATE = 78;
  public static final int CERTIFICATE_HOLDER_AUTHORIZATION = 24396;
  public static final int CERTIFICATE_HOLDER_AUTHORIZATION_TEMPLATE = 76;
  public static final int CERTIFICATION_AUTHORITY_PUBLIC_KEY = 24394;
  public static final int COEXISTANT_TAG_ALLOCATION_AUTHORITY = 121;
  public static final int COMMAND_TO_PERFORM = 82;
  public static final int COMPATIBLE_TAG_ALLOCATION_AUTHORITY = 120;
  public static final int COUNTRY_CODE = 24360;
  public static final int COUNTRY_CODE_NATIONAL_DATA = 65;
  public static final int CURRENCY_CODE = 24362;
  public static final int CURRENCY_EXPONENT = 24374;
  public static final int CV_CERTIFICATE = 32545;
  public static final int DATE_OF_BIRTH = 24363;
  public static final int DEPRECATED = 24395;
  public static final int DIGITAL_SIGNATURE = 24381;
  public static final int DIGITAL_SIGNATURE_BLOCK = 32573;
  public static final int DISCRETIONARY_DATA = 83;
  public static final int DISCRETIONARY_DATA_OBJECTS = 115;
  public static final int DISPLAY_CONTROL = 32544;
  public static final int DISPLAY_IMAGE = 24389;
  public static final int DYNAMIC_AUTHENTIFICATION_TEMPLATE = 124;
  public static final int DYNAMIC_EXTERNAL_AUTHENTIFICATION = 24379;
  public static final int DYNAMIC_INTERNAL_AUTHENTIFICATION = 24378;
  public static final int DYNAMIC_MUTUAL_AUTHENTIFICATION = 24380;
  public static final int ELEMENT_LIST = 24385;
  public static final int EXTENDED_HEADER_LIST = 77;
  public static final int FCI_TEMPLATE = 111;
  public static final int FCP_TEMPLATE = 98;
  public static final int FILE_REFERENCE = 81;
  public static final int FMD_TEMPLATE = 100;
  public static final int HEADER_LIST = 93;
  public static final int HISTORICAL_BYTES = 24402;
  public static final int INITIAL_ACCESS_DATA = 68;
  public static final int INTEGRATED_CIRCUIT_MANUFACTURER_ID = 24397;
  public static final int INTERCHANGE_CONTROL = 24359;
  public static final int INTERCHANGE_PROFILE = 41;
  public static final int ISSUER_IDENTIFICATION_NUMBER = 2;
  public static final int LANGUAGE_PREFERENCES = 24365;
  public static final int LOGIN_DATA = 94;
  public static final int LOGIN_TEMPLATE = 106;
  public static final int MESSAGE_REFERENCE = 24391;
  public static final int NAME = 91;
  public static final int NON_INTERINDUSTRY_DATA_OBJECT_NESTING_TEMPLATE = 126;
  public static final int OBJECT_IDENTIFIER = 6;
  public static final int OFFSET_DATA_OBJECT = 84;
  public static final int PIN_USAGE_POLICY = 24367;
  public static final int PRE_ISSUING_DATA = 70;
  public static final int PRIMARY_ACCOUNT_NUMBER = 90;
  public static final int QUALIFIED_NAME = 107;
  public static final int SECURE_MESSAGING_TEMPLATE = 125;
  public static final int SECURITY_ENVIRONMENT_TEMPLATE = 123;
  public static final int SECURITY_SUPPORT_TEMPLATE = 122;
  public static final int SERVICE_CODE = 24368;
  public static final int SEX = 24373;
  public static final int SIGNATURE = 24375;
  public static final int SPECIAL_USER_REQUIREMENTS = 104;
  public static final int STATIC_INTERNAL_AUTHENTIFICATION_FIRST_DATA = 24376;
  public static final int STATIC_INTERNAL_AUTHENTIFICATION_ONE_STEP = 55;
  public static final int STATIC_INTERNAL_AUTHENTIFICATION_SECOND_DATA = 24377;
  public static final int STATUS_INFORMATION = 72;
  public static final int TAG_LIST = 92;
  public static final int TIMER = 24390;
  public static final int TRACK1_APPLICATION = 86;
  public static final int TRACK1_CARD = 24353;
  public static final int TRACK2_APPLICATION = 87;
  public static final int TRACK2_CARD = 24354;
  public static final int TRACK3_APPLICATION = 88;
  public static final int TRACK3_CARD = 24355;
  public static final int TRANSACTION_COUNTER = 24370;
  public static final int TRANSACTION_DATE = 24371;
  public static final int UNIFORM_RESOURCE_LOCATOR = 24400;
  public static final int WRAPPER = 99;
  
  public static int decodeTag(int paramInt)
  {
    int i = 0;
    int j = 0;
    int k = 24;
    if (k >= 0)
    {
      int m = 0xFF & paramInt >> k;
      if (m != 0)
      {
        if (j == 0) {
          break label46;
        }
        i = i << 7 | m & 0x7F;
      }
      for (;;)
      {
        k -= 8;
        break;
        label46:
        if ((m & 0x1F) != 31) {
          break label61;
        }
        j = 1;
      }
      label61:
      i = m & 0x1F;
    }
    return i;
  }
  
  public static int encodeTag(DERApplicationSpecific paramDERApplicationSpecific)
  {
    int i = 64;
    if (paramDERApplicationSpecific.isConstructed()) {
      i = 96;
    }
    int j = paramDERApplicationSpecific.getApplicationTag();
    if (j > 31)
    {
      k = (i | 0x1F) << 8 | j & 0x7F;
      int m = j >> 7;
      while (m > 0)
      {
        k = (k | 0x80) << 8;
        m >>= 7;
      }
    }
    int k = i | j;
    return k;
  }
  
  public static int getTag(int paramInt)
  {
    return decodeTag(paramInt);
  }
  
  public static int getTagNo(int paramInt)
  {
    for (int i = 24; i >= 0; i -= 8) {
      if ((paramInt & 255 << i) != 0) {
        return paramInt & (0xFFFFFFFF ^ 255 << i);
      }
    }
    return 0;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.eac.EACTags
 * JD-Core Version:    0.7.0.1
 */