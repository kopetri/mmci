package com.touchtype_fluency.service.personalize;

import java.util.Locale;

public enum PersonalizationFailedReason
{
  static
  {
    AUTHENTICATION_FAILED = new PersonalizationFailedReason("AUTHENTICATION_FAILED", 3);
    IMAP_DISABLED = new PersonalizationFailedReason("IMAP_DISABLED", 4);
    SERVER_BUSY = new PersonalizationFailedReason("SERVER_BUSY", 5);
    OTHER = new PersonalizationFailedReason("OTHER", 6);
    PersonalizationFailedReason[] arrayOfPersonalizationFailedReason = new PersonalizationFailedReason[7];
    arrayOfPersonalizationFailedReason[0] = NO_LOCAL_CONTENT;
    arrayOfPersonalizationFailedReason[1] = NO_MESSAGES;
    arrayOfPersonalizationFailedReason[2] = SENT_FOLDER_NOT_FOUND;
    arrayOfPersonalizationFailedReason[3] = AUTHENTICATION_FAILED;
    arrayOfPersonalizationFailedReason[4] = IMAP_DISABLED;
    arrayOfPersonalizationFailedReason[5] = SERVER_BUSY;
    arrayOfPersonalizationFailedReason[6] = OTHER;
    $VALUES = arrayOfPersonalizationFailedReason;
  }
  
  private PersonalizationFailedReason() {}
  
  public static PersonalizationFailedReason fromString(String paramString)
  {
    try
    {
      PersonalizationFailedReason localPersonalizationFailedReason = valueOf(paramString.toUpperCase(Locale.ENGLISH));
      return localPersonalizationFailedReason;
    }
    catch (Exception localException) {}
    return OTHER;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.personalize.PersonalizationFailedReason
 * JD-Core Version:    0.7.0.1
 */