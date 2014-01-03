package com.touchtype.keyboard.inputeventmodel;

import android.content.pm.PackageInfo;
import android.os.Build.VERSION;
import android.view.inputmethod.EditorInfo;
import com.google.common.collect.Sets;
import com.touchtype.util.EnvironmentInfoUtil;
import com.touchtype.util.PackageInfoUtil;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class EditorInfoUtils
{
  private static final int MANUFACTURER_SAMSUNG_HASHCODE;
  private static final int PKG_ANDROID_APPS_DOCS;
  private static final int PKG_ANDROID_BROWSER_ICS_HASHCODE;
  private static final int PKG_ANDROID_BROWSER_JB_HASHCODE;
  private static final int PKG_ANDROID_CHROME;
  private static final int PKG_ANDROID_MAIL_HASHCODE;
  private static final int PKG_ANDROID_SCREEN_LOCK;
  private static final int PKG_ANDROID_TALK;
  private static final int PKG_CHROME_BETA;
  private static final Set<Integer> PKG_CHROME_BROWSER_HASHCODES;
  private static final int PKG_CTS_TESTS_HASHCODE;
  private static final int PKG_DOLPHIN_BROWSER_HASHCODE;
  private static final int PKG_DOLPHIN_HD_BROWSER_HASHCODE;
  private static final int PKG_EVERNOTE_HASHCODE;
  private static final int PKG_EVERNOTE_WORLD_HASHCODE;
  private static final int PKG_FACEBOOK_APP_HASHCODE;
  private static final int PKG_FIREFOX_BETA_HASHCODE;
  private static final Set<Integer> PKG_FIREFOX_BROWSER_HASHCODES;
  private static final int PKG_FIREFOX_HASHCODE;
  private static final int PKG_GOOGLE_MAPS;
  private static final int PKG_GOOGLE_PLUS_HASHCODE;
  private static final int PKG_GOOGLE_QUICK_SEARCH_HASHCODE;
  private static final int PKG_HTC_CALENDAR;
  private static final int PKG_HTC_HTMLEDITOR;
  private static final Set<Integer> PKG_HTC_HTML_EDITOR_HASHCODES;
  private static final int PKG_HTC_MAIL_HASHCODE;
  private static final int PKG_HTC_NOTES_APP_HASHCODE;
  private static final int PKG_KINGSOFT_OFFICE_FREE_HASHCODE;
  private static final Set<Integer> PKG_KINGSOFT_OFFICE_HASHCODES;
  private static final int PKG_KINGSOFT_OFFICE_MULTI_LAN_HASHCODE;
  private static final int PKG_MODOOHUT_DIALLER;
  private static final int PKG_OPERA_MOBILE_WEB_BROWSER_HASHCODE;
  private static final int PKG_PLECO_CN_HASHCODE;
  private static final int PKG_PLECO_HASHCODE;
  private static final Set<Integer> PKG_PLECO_HASHCODES;
  private static final int PKG_POLARIS_OFFICE_APP_HASHCODE;
  private static final int PKG_SEC_ANDROID_APP_CALCULATOR;
  private static final int PKG_SEC_ANDROID_APP_POPUPCALCULATOR;
  private static final int PKG_SEC_ANDROID_SNOTEBOOK;
  private static final int PKG_UC_BROWSER_MOBILE_HASHCODE;
  private static final int PKG_VAULTY_FREE_HASHCODE;
  private static final Set<Integer> PKG_VAULTY_HASHCODES;
  private static final int PKG_VAULTY_PAID_HASHCODE;
  private static final Set<Integer> PKG_WEBKIT_BROWSER_HASHCODES;
  private static final String TAG = EditorInfoUtils.class.getSimpleName();
  private static Pattern WEBKIT_FIELDNAME_CREDITCARDNUMBER_PATTERN = Pattern.compile("(.*(credit|debit).*|(cc|cvv)[_\\-]?(number)?)\\\\.*", 2);
  private static Pattern WEBKIT_FIELDNAME_EMAIL_PATTERN;
  private static Pattern WEBKIT_FIELDNAME_PASSWORD_PATTERN;
  
  static
  {
    PKG_HTC_MAIL_HASHCODE = "com.htc.android.mail".hashCode();
    PKG_GOOGLE_QUICK_SEARCH_HASHCODE = "com.google.android.googlequicksearchbox".hashCode();
    PKG_GOOGLE_PLUS_HASHCODE = "com.google.android.apps.plus".hashCode();
    PKG_ANDROID_MAIL_HASHCODE = "com.android.email".hashCode();
    PKG_ANDROID_BROWSER_ICS_HASHCODE = "com.android.browser".hashCode();
    PKG_ANDROID_BROWSER_JB_HASHCODE = "com.google.android.browser".hashCode();
    PKG_DOLPHIN_BROWSER_HASHCODE = "com.dolphin.browser".hashCode();
    PKG_DOLPHIN_HD_BROWSER_HASHCODE = "mobi.mgeek.TunnyBrowser".hashCode();
    PKG_FIREFOX_HASHCODE = "org.mozilla.firefox".hashCode();
    PKG_FIREFOX_BETA_HASHCODE = "org.mozilla.firefox_beta".hashCode();
    PKG_FACEBOOK_APP_HASHCODE = "com.facebook.katana".hashCode();
    PKG_POLARIS_OFFICE_APP_HASHCODE = "com.infraware.docmaster".hashCode();
    PKG_HTC_NOTES_APP_HASHCODE = "com.htc.notes".hashCode();
    PKG_CTS_TESTS_HASHCODE = "com.android.cts.stub".hashCode();
    PKG_ANDROID_CHROME = "com.android.chrome".hashCode();
    PKG_CHROME_BETA = "com.chrome.beta".hashCode();
    PKG_ANDROID_TALK = "com.google.android.talk".hashCode();
    PKG_SEC_ANDROID_SNOTEBOOK = "com.sec.android.app.snotebook".hashCode();
    PKG_SEC_ANDROID_APP_CALCULATOR = "com.sec.android.app.calculator".hashCode();
    PKG_SEC_ANDROID_APP_POPUPCALCULATOR = "com.sec.android.app.popupcalculator".hashCode();
    PKG_MODOOHUT_DIALLER = "com.modoohut.dialer".hashCode();
    PKG_KINGSOFT_OFFICE_FREE_HASHCODE = "cn.wps.moffice_eng".hashCode();
    PKG_KINGSOFT_OFFICE_MULTI_LAN_HASHCODE = "cn.wps.moffice_i18n".hashCode();
    PKG_UC_BROWSER_MOBILE_HASHCODE = "com.UCMobile.intl".hashCode();
    PKG_HTC_HTMLEDITOR = "com.htc.htmleditor".hashCode();
    PKG_HTC_CALENDAR = "com.htc.calendar".hashCode();
    PKG_OPERA_MOBILE_WEB_BROWSER_HASHCODE = "com.opera.browser".hashCode();
    PKG_GOOGLE_MAPS = "com.google.android.apps.maps".hashCode();
    PKG_EVERNOTE_HASHCODE = "com.evernote".hashCode();
    PKG_EVERNOTE_WORLD_HASHCODE = "com.evernote.world".hashCode();
    PKG_VAULTY_FREE_HASHCODE = "com.theronrogers.vaultyfree".hashCode();
    PKG_VAULTY_PAID_HASHCODE = "com.theronrogers.vaultypro".hashCode();
    PKG_PLECO_HASHCODE = "com.pleco.chinesesystem".hashCode();
    PKG_PLECO_CN_HASHCODE = "com.pleco.chinesesystem.cn".hashCode();
    PKG_ANDROID_APPS_DOCS = "com.google.android.apps.docs".hashCode();
    PKG_ANDROID_SCREEN_LOCK = "android".hashCode();
    Integer[] arrayOfInteger1 = new Integer[2];
    arrayOfInteger1[0] = Integer.valueOf(PKG_PLECO_HASHCODE);
    arrayOfInteger1[1] = Integer.valueOf(PKG_PLECO_CN_HASHCODE);
    PKG_PLECO_HASHCODES = Sets.newHashSet(arrayOfInteger1);
    Integer[] arrayOfInteger2 = new Integer[2];
    arrayOfInteger2[0] = Integer.valueOf(PKG_VAULTY_FREE_HASHCODE);
    arrayOfInteger2[1] = Integer.valueOf(PKG_VAULTY_PAID_HASHCODE);
    PKG_VAULTY_HASHCODES = Sets.newHashSet(arrayOfInteger2);
    Integer[] arrayOfInteger3 = new Integer[4];
    arrayOfInteger3[0] = Integer.valueOf(PKG_HTC_HTMLEDITOR);
    arrayOfInteger3[1] = Integer.valueOf(PKG_HTC_MAIL_HASHCODE);
    arrayOfInteger3[2] = Integer.valueOf(PKG_HTC_CALENDAR);
    arrayOfInteger3[3] = Integer.valueOf(PKG_HTC_NOTES_APP_HASHCODE);
    PKG_HTC_HTML_EDITOR_HASHCODES = Sets.newHashSet(arrayOfInteger3);
    Integer[] arrayOfInteger4 = new Integer[4];
    arrayOfInteger4[0] = Integer.valueOf(PKG_ANDROID_BROWSER_ICS_HASHCODE);
    arrayOfInteger4[1] = Integer.valueOf(PKG_ANDROID_BROWSER_JB_HASHCODE);
    arrayOfInteger4[2] = Integer.valueOf(PKG_DOLPHIN_BROWSER_HASHCODE);
    arrayOfInteger4[3] = Integer.valueOf(PKG_DOLPHIN_HD_BROWSER_HASHCODE);
    PKG_WEBKIT_BROWSER_HASHCODES = Sets.newHashSet(arrayOfInteger4);
    Integer[] arrayOfInteger5 = new Integer[2];
    arrayOfInteger5[0] = Integer.valueOf(PKG_FIREFOX_HASHCODE);
    arrayOfInteger5[1] = Integer.valueOf(PKG_FIREFOX_BETA_HASHCODE);
    PKG_FIREFOX_BROWSER_HASHCODES = Sets.newHashSet(arrayOfInteger5);
    Integer[] arrayOfInteger6 = new Integer[2];
    arrayOfInteger6[0] = Integer.valueOf(PKG_ANDROID_CHROME);
    arrayOfInteger6[1] = Integer.valueOf(PKG_CHROME_BETA);
    PKG_CHROME_BROWSER_HASHCODES = Sets.newHashSet(arrayOfInteger6);
    Integer[] arrayOfInteger7 = new Integer[2];
    arrayOfInteger7[0] = Integer.valueOf(PKG_KINGSOFT_OFFICE_FREE_HASHCODE);
    arrayOfInteger7[1] = Integer.valueOf(PKG_KINGSOFT_OFFICE_MULTI_LAN_HASHCODE);
    PKG_KINGSOFT_OFFICE_HASHCODES = Sets.newHashSet(arrayOfInteger7);
    MANUFACTURER_SAMSUNG_HASHCODE = "samsung".hashCode();
    WEBKIT_FIELDNAME_EMAIL_PATTERN = Pattern.compile("(email|session\\[username_or_email\\])\\\\.*", 2);
    WEBKIT_FIELDNAME_PASSWORD_PATTERN = Pattern.compile("(pass|password)\\\\.*", 2);
  }
  
  public static int compareVersionStrings(String paramString1, String paramString2)
    throws NumberFormatException
  {
    int i = 1;
    String[] arrayOfString1 = paramString1.split("\\.");
    String[] arrayOfString2 = paramString2.split("\\.");
    for (int j = 0;; j++)
    {
      int k;
      int m;
      if (arrayOfString1.length == j)
      {
        k = i;
        if (arrayOfString2.length != j) {
          break label63;
        }
        m = i;
        label43:
        if ((k == 0) || (m == 0)) {
          break label69;
        }
        i = 0;
      }
      label63:
      label69:
      int n;
      int i1;
      do
      {
        do
        {
          return i;
          k = 0;
          break;
          m = 0;
          break label43;
          if (k != 0) {
            return -1;
          }
        } while (m != 0);
        n = Integer.parseInt(arrayOfString1[j]);
        i1 = Integer.parseInt(arrayOfString2[j]);
        if (n < i1) {
          return -1;
        }
      } while (i1 < n);
    }
  }
  
  public static AnnotatedEditorInfo correctEditorInfo(EditorInfo paramEditorInfo, PackageInfoUtil paramPackageInfoUtil)
  {
    int i = Build.VERSION.SDK_INT;
    boolean bool1 = false;
    boolean bool2 = false;
    boolean bool3 = true;
    if (paramEditorInfo == null) {
      return null;
    }
    if ((paramEditorInfo.inputType == 0) || (paramEditorInfo.packageName == null)) {
      return new AnnotatedEditorInfo(paramEditorInfo, paramPackageInfoUtil, i, false, true, false, false, false, false, true, false, true, false, false, false, false, false, false, false, false, false, true);
    }
    int j = paramEditorInfo.packageName.hashCode();
    int k = 0xF & paramEditorInfo.inputType;
    int m = 0xFF0 & paramEditorInfo.inputType;
    int n = 0xFFF000 & paramEditorInfo.inputType;
    int i1 = EnvironmentInfoUtil.getManufacturer().hashCode();
    boolean bool4;
    boolean bool17;
    boolean bool16;
    boolean bool15;
    boolean bool14;
    boolean bool6;
    boolean bool13;
    boolean bool7;
    boolean bool12;
    boolean bool11;
    boolean bool10;
    boolean bool8;
    boolean bool9;
    if ((m == 16) || ((PKG_CHROME_BROWSER_HASHCODES.contains(Integer.valueOf(j))) && ((0x80000 & n) == 0) && (m == 176)))
    {
      bool4 = true;
      if ((j != PKG_HTC_MAIL_HASHCODE) || (k != 1) || (m != 208) || ((0x20000 & n) == 0)) {
        break label448;
      }
      k = 1;
      m = 80;
      n = 131072;
      bool17 = false;
      bool16 = false;
      bool5 = false;
      bool15 = false;
      bool14 = false;
      bool6 = true;
      bool13 = false;
      bool7 = true;
      bool12 = false;
      bool11 = false;
      bool10 = false;
      bool8 = true;
      bool9 = false;
    }
    boolean bool18;
    boolean bool19;
    for (;;)
    {
      if (i >= 16)
      {
        bool18 = true;
        if (PKG_WEBKIT_BROWSER_HASHCODES.contains(Integer.valueOf(j)))
        {
          bool10 = true;
          bool7 = false;
          bool9 = true;
        }
        if ((m != 160) && (m != 208))
        {
          int i2 = m;
          bool19 = false;
          if (i2 != 224) {}
        }
        else
        {
          bool19 = true;
        }
        label301:
        if (paramEditorInfo.fieldName != null)
        {
          if (WEBKIT_FIELDNAME_EMAIL_PATTERN.matcher(paramEditorInfo.fieldName).matches()) {
            m = 208;
          }
          if (WEBKIT_FIELDNAME_PASSWORD_PATTERN.matcher(paramEditorInfo.fieldName).matches()) {
            m = 224;
          }
          if (WEBKIT_FIELDNAME_CREDITCARDNUMBER_PATTERN.matcher(paramEditorInfo.fieldName).matches())
          {
            k = 2;
            m = 16;
            n = 0;
          }
        }
        paramEditorInfo.inputType = (n | k | m);
        return new AnnotatedEditorInfo(paramEditorInfo, paramPackageInfoUtil, Math.min(i, Build.VERSION.SDK_INT), bool9, bool8, bool10, bool11, bool12, bool18, bool7, bool13, bool6, bool14, bool15, bool5, bool4, bool16, bool17, bool1, bool19, bool2, bool3);
        bool4 = false;
        break;
        label448:
        if (((PKG_HTC_HTML_EDITOR_HASHCODES.contains(Integer.valueOf(j))) && (Build.VERSION.SDK_INT < 17)) || ((paramEditorInfo.packageName.startsWith("com.htc.")) && (Build.VERSION.SDK_INT > 17) && (k == 1) && (m == 160) && ((0x20000 & n) != 0)))
        {
          bool11 = true;
          bool6 = true;
          bool7 = true;
          bool8 = true;
          bool9 = false;
          bool10 = false;
          bool12 = false;
          bool13 = false;
          bool14 = false;
          bool15 = false;
          bool5 = false;
          bool16 = false;
          bool17 = false;
          bool1 = false;
          bool2 = false;
        }
        else if ((paramEditorInfo.packageName.startsWith("com.htc.")) && (k == 1) && (m == 160) && ((0x20000 & n) != 0) && (Build.VERSION.SDK_INT == 17))
        {
          bool10 = true;
          bool6 = true;
          bool7 = true;
          bool8 = true;
          bool9 = false;
          bool11 = false;
          bool12 = false;
          bool13 = false;
          bool14 = false;
          bool15 = false;
          bool5 = false;
          bool16 = false;
          bool17 = false;
          bool1 = false;
          bool2 = false;
        }
        else if ((j == PKG_GOOGLE_QUICK_SEARCH_HASHCODE) && (k == 1) && (m == 0) && (((0x10000 & n) != 0) || ((0x80000 & n) != 0)))
        {
          bool6 = true;
          bool7 = true;
          bool8 = true;
          bool9 = false;
          bool10 = false;
          bool11 = false;
          bool12 = false;
          bool13 = false;
          bool14 = false;
          bool15 = false;
          bool5 = false;
          bool16 = false;
          bool17 = false;
          bool1 = false;
          bool2 = false;
          n = 0;
        }
        else if ((j == PKG_ANDROID_APPS_DOCS) && (k == 1) && (m == 0))
        {
          bool10 = true;
          bool6 = true;
          bool7 = true;
          bool8 = true;
          bool9 = false;
          bool11 = false;
          bool12 = false;
          bool13 = false;
          bool14 = false;
          bool15 = false;
          bool5 = false;
          bool16 = false;
          bool17 = false;
          bool1 = false;
          bool2 = false;
        }
        else if ((j == PKG_ANDROID_MAIL_HASHCODE) && (i1 == MANUFACTURER_SAMSUNG_HASHCODE) && ((k == 15) || ((k == 1) && (m == 0))))
        {
          bool13 = true;
          if (i >= 14)
          {
            bool10 = true;
            bool11 = false;
            bool8 = true;
            label859:
            bool16 = true;
            bool12 = true;
            if ((0x20000 & n) == 0) {
              break label2594;
            }
            bool15 = true;
          }
        }
      }
    }
    for (boolean bool5 = true;; bool5 = false)
    {
      for (;;)
      {
        k = 1;
        bool6 = true;
        bool7 = true;
        bool9 = false;
        bool14 = false;
        bool17 = false;
        bool1 = false;
        bool2 = false;
        break;
        bool11 = true;
        bool8 = false;
        bool10 = false;
        break label859;
        if (PKG_FIREFOX_BROWSER_HASHCODES.contains(Integer.valueOf(j)))
        {
          if (i >= 9)
          {
            bool10 = true;
            bool11 = false;
          }
          for (;;)
          {
            bool16 = true;
            bool6 = true;
            bool9 = false;
            bool8 = false;
            bool12 = false;
            bool7 = false;
            bool13 = false;
            bool14 = false;
            bool15 = false;
            bool5 = false;
            bool17 = false;
            bool1 = false;
            bool2 = false;
            break;
            bool11 = true;
            bool10 = false;
          }
        }
        if ((j == PKG_FACEBOOK_APP_HASHCODE) && (k == 1) && (m == 0) && ((0x20000 & n) != 0))
        {
          bool9 = true;
          bool6 = true;
          bool7 = true;
          bool8 = true;
          bool10 = false;
          bool11 = false;
          bool12 = false;
          bool13 = false;
          bool14 = false;
          bool15 = false;
          bool5 = false;
          bool16 = false;
          bool17 = false;
          bool1 = false;
          bool2 = false;
          break;
        }
        if ((PKG_WEBKIT_BROWSER_HASHCODES.contains(Integer.valueOf(j))) && (k == 1) && ((0x10000 & n) != 0))
        {
          n &= 0xFFFEFFFF;
          bool6 = true;
          bool7 = true;
          bool8 = true;
          bool9 = false;
          bool10 = false;
          bool11 = false;
          bool12 = false;
          bool13 = false;
          bool14 = false;
          bool15 = false;
          bool5 = false;
          bool16 = false;
          bool17 = false;
          bool1 = false;
          bool2 = false;
          break;
        }
        if ((j == PKG_POLARIS_OFFICE_APP_HASHCODE) && (k == 1) && (m == 0))
        {
          bool14 = true;
          bool6 = true;
          bool7 = true;
          bool8 = true;
          bool9 = false;
          bool10 = false;
          bool11 = false;
          bool12 = false;
          bool13 = false;
          bool15 = false;
          bool5 = false;
          bool16 = false;
          bool17 = false;
          bool1 = false;
          bool2 = false;
          break;
        }
        if ((j == PKG_SEC_ANDROID_SNOTEBOOK) && (k == 1) && (m == 0))
        {
          bool14 = true;
          bool6 = true;
          bool7 = true;
          bool8 = true;
          bool9 = false;
          bool10 = false;
          bool11 = false;
          bool12 = false;
          bool13 = false;
          bool15 = false;
          bool5 = false;
          bool16 = false;
          bool17 = false;
          bool1 = false;
          bool2 = false;
          break;
        }
        if ((j == PKG_HTC_NOTES_APP_HASHCODE) && (k == 1) && (m == 0) && ((0x20000 & n) != 0) && (i < 16))
        {
          bool11 = true;
          bool12 = true;
          bool6 = true;
          bool7 = true;
          bool8 = true;
          bool9 = false;
          bool10 = false;
          bool13 = false;
          bool14 = false;
          bool15 = false;
          bool5 = false;
          bool16 = false;
          bool17 = false;
          bool1 = false;
          bool2 = false;
          break;
        }
        if (j == PKG_CTS_TESTS_HASHCODE)
        {
          n = 524288;
          bool6 = true;
          bool7 = true;
          bool8 = true;
          bool9 = false;
          bool10 = false;
          bool11 = false;
          bool12 = false;
          bool13 = false;
          bool14 = false;
          bool15 = false;
          bool5 = false;
          bool16 = false;
          bool17 = false;
          bool1 = false;
          bool2 = false;
          break;
        }
        if (PKG_CHROME_BROWSER_HASHCODES.contains(Integer.valueOf(j)))
        {
          if (i >= 9)
          {
            bool10 = true;
            bool11 = false;
          }
          for (;;)
          {
            bool16 = true;
            bool15 = true;
            bool1 = true;
            bool7 = true;
            bool8 = true;
            bool9 = false;
            bool12 = false;
            bool13 = false;
            bool6 = false;
            bool14 = false;
            bool5 = false;
            bool17 = false;
            bool2 = false;
            break;
            bool11 = true;
            bool10 = false;
          }
        }
        if ((j == PKG_ANDROID_TALK) && (k == 1) && (m == 64))
        {
          if (paramPackageInfoUtil != null) {}
          for (PackageInfo localPackageInfo3 = paramPackageInfoUtil.getPackageInfo("com.google.android.talk");; localPackageInfo3 = null)
          {
            if ((localPackageInfo3 != null) && (localPackageInfo3.versionCode < 600000)) {
              paramEditorInfo.imeOptions = (0xFF | paramEditorInfo.imeOptions);
            }
            bool6 = true;
            bool7 = true;
            bool8 = true;
            bool9 = false;
            bool10 = false;
            bool11 = false;
            bool12 = false;
            bool13 = false;
            bool14 = false;
            bool15 = false;
            bool5 = false;
            bool16 = false;
            bool17 = false;
            bool1 = false;
            bool2 = false;
            break;
          }
        }
        if (((j != PKG_SEC_ANDROID_APP_POPUPCALCULATOR) && (j != PKG_SEC_ANDROID_APP_CALCULATOR)) || ((k == 1) || ((j == PKG_MODOOHUT_DIALLER) && (k == 3))))
        {
          bool5 = true;
          bool6 = true;
          bool7 = true;
          bool8 = true;
          bool9 = false;
          bool10 = false;
          bool11 = false;
          bool12 = false;
          bool13 = false;
          bool14 = false;
          bool15 = false;
          bool16 = false;
          bool17 = false;
          bool1 = false;
          bool2 = false;
          break;
        }
        if ((!PKG_KINGSOFT_OFFICE_HASHCODES.contains(Integer.valueOf(j))) || (k != 1) || (m != 0)) {
          break label1971;
        }
        if (j != PKG_KINGSOFT_OFFICE_FREE_HASHCODE) {
          break label1924;
        }
        PackageInfo localPackageInfo2;
        if (paramPackageInfoUtil != null)
        {
          localPackageInfo2 = paramPackageInfoUtil.getPackageInfo("cn.wps.moffice_eng");
          label1752:
          if (localPackageInfo2 == null) {
            break label1828;
          }
        }
        try
        {
          int i3 = compareVersionStrings(localPackageInfo2.versionName, "5.6");
          if (i3 >= 0)
          {
            bool11 = true;
            bool6 = true;
            bool7 = true;
            bool8 = true;
            bool9 = false;
            bool10 = false;
            bool12 = false;
            bool13 = false;
            bool14 = false;
            bool15 = false;
            bool5 = false;
            bool16 = false;
            bool17 = false;
            bool1 = false;
            bool2 = false;
            break;
            localPackageInfo2 = null;
            break label1752;
          }
          label1828:
          bool14 = true;
          bool6 = true;
          bool7 = true;
          bool8 = true;
          bool9 = false;
          bool10 = false;
          bool11 = false;
          bool12 = false;
          bool13 = false;
          bool15 = false;
          bool5 = false;
          bool16 = false;
          bool17 = false;
          bool1 = false;
          bool2 = false;
        }
        catch (NumberFormatException localNumberFormatException)
        {
          bool14 = true;
          bool6 = true;
          bool7 = true;
          bool8 = true;
          bool9 = false;
          bool10 = false;
          bool11 = false;
          bool12 = false;
          bool13 = false;
          bool15 = false;
          bool5 = false;
          bool16 = false;
          bool17 = false;
          bool1 = false;
          bool2 = false;
        }
      }
      break;
      label1924:
      bool14 = true;
      bool6 = true;
      bool7 = true;
      bool8 = true;
      bool9 = false;
      bool10 = false;
      bool11 = false;
      bool12 = false;
      bool13 = false;
      bool15 = false;
      bool5 = false;
      bool16 = false;
      bool17 = false;
      bool1 = false;
      bool2 = false;
      break;
      label1971:
      if ((j == PKG_UC_BROWSER_MOBILE_HASHCODE) && (k == 1) && ((m == 0) || (m == 16)))
      {
        bool14 = true;
        bool6 = true;
        bool7 = true;
        bool8 = true;
        bool9 = false;
        bool10 = false;
        bool11 = false;
        bool12 = false;
        bool13 = false;
        bool15 = false;
        bool5 = false;
        bool16 = false;
        bool17 = false;
        bool1 = false;
        bool2 = false;
        break;
      }
      PackageInfo localPackageInfo1;
      if ((j == PKG_OPERA_MOBILE_WEB_BROWSER_HASHCODE) && (k == 1) && (m == 160)) {
        if (paramPackageInfoUtil != null)
        {
          localPackageInfo1 = paramPackageInfoUtil.getPackageInfo("com.opera.browser");
          label2078:
          if ((localPackageInfo1 == null) || (!localPackageInfo1.versionName.startsWith("14."))) {
            break label2588;
          }
        }
      }
      label2588:
      for (bool10 = true;; bool10 = false)
      {
        bool6 = true;
        bool7 = true;
        bool8 = true;
        bool9 = false;
        bool11 = false;
        bool12 = false;
        bool13 = false;
        bool14 = false;
        bool15 = false;
        bool5 = false;
        bool16 = false;
        bool17 = false;
        bool1 = false;
        bool2 = false;
        break;
        localPackageInfo1 = null;
        break label2078;
        if ((j == PKG_GOOGLE_MAPS) && (k == 1) && (n == 65536))
        {
          bool17 = true;
          bool6 = true;
          bool7 = true;
          bool8 = true;
          bool9 = false;
          bool10 = false;
          bool11 = false;
          bool12 = false;
          bool13 = false;
          bool14 = false;
          bool15 = false;
          bool5 = false;
          bool16 = false;
          bool1 = false;
          bool2 = false;
          break;
        }
        if ((PKG_VAULTY_HASHCODES.contains(Integer.valueOf(j))) && (k == 1) && (m == 0) && ((0x80000 & n) != 0))
        {
          bool14 = true;
          bool6 = true;
          bool7 = true;
          bool8 = true;
          bool9 = false;
          bool10 = false;
          bool11 = false;
          bool12 = false;
          bool13 = false;
          bool15 = false;
          bool5 = false;
          bool16 = false;
          bool17 = false;
          bool1 = false;
          bool2 = false;
          break;
        }
        if ((PKG_PLECO_HASHCODES.contains(Integer.valueOf(j))) && (k == 1) && (m == 176) && ((0x80000 & n) != 0))
        {
          bool14 = true;
          bool6 = true;
          bool7 = true;
          bool8 = true;
          bool9 = false;
          bool10 = false;
          bool11 = false;
          bool12 = false;
          bool13 = false;
          bool15 = false;
          bool5 = false;
          bool16 = false;
          bool17 = false;
          bool1 = false;
          bool2 = false;
          break;
        }
        if ((j == PKG_ANDROID_SCREEN_LOCK) && (i >= 17) && (k == 1) && (m == 128) && ((0x80000000 & paramEditorInfo.imeOptions) != 0))
        {
          bool2 = true;
          bool6 = true;
          bool7 = true;
          bool8 = true;
          bool9 = false;
          bool10 = false;
          bool11 = false;
          bool12 = false;
          bool13 = false;
          bool14 = false;
          bool15 = false;
          bool5 = false;
          bool16 = false;
          bool17 = false;
          bool1 = false;
          break;
        }
        if (j == PKG_GOOGLE_PLUS_HASHCODE)
        {
          bool6 = true;
          bool7 = true;
          bool8 = true;
          bool9 = false;
          bool10 = false;
          bool11 = false;
          bool12 = false;
          bool13 = false;
          bool14 = false;
          bool15 = false;
          bool5 = false;
          bool16 = false;
          bool17 = false;
          bool1 = false;
          bool2 = false;
          bool3 = false;
          break;
          bool18 = false;
          bool19 = false;
          break label301;
        }
        bool6 = true;
        bool7 = true;
        bool8 = true;
        bool9 = false;
        bool10 = false;
        bool11 = false;
        bool12 = false;
        bool13 = false;
        bool14 = false;
        bool15 = false;
        bool5 = false;
        bool16 = false;
        bool17 = false;
        bool1 = false;
        bool2 = false;
        break;
      }
      label2594:
      bool15 = false;
    }
  }
  
  public static boolean isChromeBrowser(String paramString)
  {
    return PKG_CHROME_BROWSER_HASHCODES.contains(Integer.valueOf(paramString.hashCode()));
  }
  
  public static boolean isValidEditorInfo(EditorInfo paramEditorInfo)
  {
    if ((paramEditorInfo == null) || (paramEditorInfo.inputType == 0)) {
      return false;
    }
    switch (0xF & paramEditorInfo.inputType)
    {
    default: 
      return false;
    }
    return true;
  }
  
  public static final class AnnotatedEditorInfo
  {
    public final boolean allowMoveCursorForInsertingPredictions;
    public final int apiCompatibilityLevel;
    public final boolean cursorMovementUpdatesSelection;
    public final boolean deleteKeyDeletesTwoCharacters;
    public final boolean disablePredictionSpaceReuse;
    public final boolean doesntSupportMoveCursor;
    public final boolean dontUseJumpingCursorMarkers;
    public final EditorInfo editorInfo;
    public final boolean enablePredictionsWhenOnlyTextBeforeCursorWorks;
    public final boolean evaluateInputShownUsedInsteadOfUpdateSelection;
    public final boolean forceDisablePredictions;
    public final boolean isUrlBar;
    public final boolean movingOverTrailingSpaceDoesntWork;
    public final boolean neverSetComposingRegion;
    public final boolean setComposingRegionOnlyBeforeEdits;
    public boolean shouldFixWebKitInputConnection;
    public final boolean shouldReplaceSelectionWithoutDeleting;
    public boolean spaceAllowedToInsertPredictionAfterZeroWidthSpace;
    public final boolean textBoxSwitchingUpdatesSelection;
    public final boolean useShortTextBeforeAfterCursor;
    public final boolean useTransactionsForSelectionEvents;
    
    public AnnotatedEditorInfo(EditorInfo paramEditorInfo, PackageInfoUtil paramPackageInfoUtil, int paramInt, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, boolean paramBoolean6, boolean paramBoolean7, boolean paramBoolean8, boolean paramBoolean9, boolean paramBoolean10, boolean paramBoolean11, boolean paramBoolean12, boolean paramBoolean13, boolean paramBoolean14, boolean paramBoolean15, boolean paramBoolean16, boolean paramBoolean17, boolean paramBoolean18, boolean paramBoolean19)
    {
      this.editorInfo = paramEditorInfo;
      this.apiCompatibilityLevel = paramInt;
      this.disablePredictionSpaceReuse = paramBoolean1;
      this.cursorMovementUpdatesSelection = paramBoolean2;
      this.setComposingRegionOnlyBeforeEdits = paramBoolean3;
      this.neverSetComposingRegion = paramBoolean4;
      this.movingOverTrailingSpaceDoesntWork = paramBoolean5;
      this.enablePredictionsWhenOnlyTextBeforeCursorWorks = paramBoolean6;
      this.useTransactionsForSelectionEvents = paramBoolean7;
      this.useShortTextBeforeAfterCursor = paramBoolean8;
      this.textBoxSwitchingUpdatesSelection = paramBoolean9;
      this.forceDisablePredictions = paramBoolean10;
      this.doesntSupportMoveCursor = paramBoolean11;
      this.dontUseJumpingCursorMarkers = paramBoolean12;
      this.isUrlBar = paramBoolean13;
      this.evaluateInputShownUsedInsteadOfUpdateSelection = paramBoolean14;
      this.shouldReplaceSelectionWithoutDeleting = paramBoolean15;
      this.allowMoveCursorForInsertingPredictions = paramBoolean16;
      this.shouldFixWebKitInputConnection = paramBoolean17;
      this.deleteKeyDeletesTwoCharacters = paramBoolean18;
      this.spaceAllowedToInsertPredictionAfterZeroWidthSpace = paramBoolean19;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.EditorInfoUtils
 * JD-Core Version:    0.7.0.1
 */