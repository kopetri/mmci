package com.touchtype.keyboard.inputeventmodel;

import android.content.Context;
import java.util.regex.Pattern;

public final class TextUtilsImpl
  implements TextUtils
{
  private static final Pattern EMAIL_PATTERN = Pattern.compile("[a-z0-9\\+\\.\\_\\%\\-]{1,256}\\@[a-z0-9][a-z0-9\\_\\-]{0,64}(\\.[a-z0-9][a-z0-9\\-]{0,25})*", 2);
  private static final Pattern GENERIC_URL_PATTERN;
  private static final Pattern KNOWN_SCHEME_PATTERN = Pattern.compile("^(https?|ftp|mailto|news|nntp|gopher|file|telnet):", 2);
  private static final Pattern NUMBER_PATTERN = Pattern.compile("\\p{Sc}?[0-9]([\\.,:0-9])*", 2);
  private static final Pattern WWW_PATTERN;
  private final String mAutocompleteSeparators;
  private final String mDeleteSpaceBeforePunctuation;
  private final String mSentenceSeparators;
  private final String mSpacedPunctuation;
  private final String mWordSeparators;
  
  static
  {
    GENERIC_URL_PATTERN = Pattern.compile("[^:/?#]+:\\/\\/(((:{0,2}[a-f0-9]{1,4}((\\.|:{1,2})[a-f0-9]{1,4})*)|([a-z0-9][a-z0-9\\_\\-]{0,64}(\\.[a-z0-9][a-z0-9\\-]{0,25})*))(\\/.*)?)", 2);
    WWW_PATTERN = Pattern.compile("((www|ftp)(\\.[a-z0-9][a-z0-9\\-]{0,25})*)(\\/.*)?", 2);
  }
  
  public TextUtilsImpl(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    this.mWordSeparators = paramString1;
    this.mSentenceSeparators = paramString2;
    this.mAutocompleteSeparators = paramString3;
    this.mSpacedPunctuation = paramString4;
    this.mDeleteSpaceBeforePunctuation = paramString5;
  }
  
  public static TextUtilsImpl createDefault(Context paramContext)
  {
    return new TextUtilsImpl(paramContext.getString(2131296344), paramContext.getString(2131296345), paramContext.getString(2131296346), paramContext.getString(2131296347), paramContext.getString(2131296348));
  }
  
  public boolean isAutocompleteSeparator(String paramString)
  {
    return this.mAutocompleteSeparators.indexOf(paramString) != -1;
  }
  
  public boolean isSentenceSeparator(String paramString)
  {
    return this.mSentenceSeparators.indexOf(paramString) != -1;
  }
  
  public boolean isWordSeparator(String paramString)
  {
    if (paramString.codePointCount(0, paramString.length()) > 1) {}
    while ((this.mWordSeparators.indexOf(paramString) == -1) && (!isAutocompleteSeparator(paramString)) && (!isSentenceSeparator(paramString))) {
      return false;
    }
    return true;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.TextUtilsImpl
 * JD-Core Version:    0.7.0.1
 */