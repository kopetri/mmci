package com.touchtype_fluency.service.languagepacks.layouts;

import android.content.Context;
import android.content.res.Resources;
import com.touchtype.preferences.TouchTypePreferences;
import com.touchtype.util.LogUtil;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class LayoutData
{
  private static final int DEFAULT_LAYOUT_ICON = 2130837972;
  public static final int LAYOUT_STYLE_COMPACT = 3;
  public static final int LAYOUT_STYLE_REGULAR = 1;
  public static final int LAYOUT_STYLE_SPLIT = 2;
  public static final int LAYOUT_STYLE_UNSET;
  public static final String TAG = LayoutData.class.getSimpleName();
  
  public static LayoutMap get(String paramString)
  {
    for (LayoutMap localLayoutMap : ) {
      if (localLayoutMap.getLayoutName().equals(paramString)) {
        return localLayoutMap;
      }
    }
    LogUtil.w(TAG, "Couldn't find a layout map which matched the name " + paramString);
    return null;
  }
  
  public static LayoutMap getLayoutForCurrentLocale()
  {
    Locale localLocale = Locale.getDefault();
    return getLayoutFromLanguage(localLocale.getLanguage(), localLocale.getCountry());
  }
  
  public static LayoutMap getLayoutFromLanguage(String paramString1, String paramString2)
  {
    Object localObject1;
    Object localObject2;
    LayoutMap[] arrayOfLayoutMap;
    int i;
    if ((paramString2 == null) || (paramString2.equals("")))
    {
      localObject1 = null;
      localObject2 = null;
      arrayOfLayoutMap = LayoutMap.values();
      i = arrayOfLayoutMap.length;
    }
    for (int j = 0;; j++)
    {
      if (j >= i) {
        break label196;
      }
      LayoutMap localLayoutMap = arrayOfLayoutMap[j];
      String str1 = localLayoutMap.getLayoutLanguagesList();
      if ((str1 != null) && (!str1.equals("")))
      {
        Iterator localIterator = Arrays.asList(str1.split(",")).iterator();
        for (;;)
        {
          if (localIterator.hasNext())
          {
            String str2 = ((String)localIterator.next()).trim();
            if ((localObject1 != null) && (str2.equals(localObject1)))
            {
              return localLayoutMap;
              localObject1 = paramString1 + "_" + paramString2;
              break;
            }
            if ((localObject1 != null) && (str2.equals(paramString1)) && (localObject2 == null)) {
              localObject2 = localLayoutMap;
            } else if ((localObject1 == null) && (str2.equals(paramString1))) {
              return localLayoutMap;
            }
          }
        }
      }
    }
    label196:
    if (localObject2 != null) {
      return localObject2;
    }
    return LayoutMap.QWERTY;
  }
  
  public static LayoutMap getLayoutWhichContainsResource(int paramInt)
  {
    for (LayoutMap localLayoutMap : ) {
      if ((paramInt == localLayoutMap.getLayoutResId(false)) || (paramInt == localLayoutMap.getLayoutResId(true)) || (paramInt == localLayoutMap.getCompactLayoutResId()) || (paramInt == localLayoutMap.getSplitLayoutResId(false)) || (paramInt == localLayoutMap.getSplitLayoutResId(true)) || (paramInt == localLayoutMap.getSplitLeftLayoutResId()) || (paramInt == localLayoutMap.getSplitRightLayoutResId())) {
        return localLayoutMap;
      }
    }
    return null;
  }
  
  public static LayoutMap getLayoutWhichContainsSecondaryResource(int paramInt)
  {
    for (LayoutMap localLayoutMap : ) {
      if ((paramInt == localLayoutMap.getSecondaryLayoutResId(false)) || (paramInt == localLayoutMap.getSecondaryLayoutResId(true)) || (paramInt == localLayoutMap.getSecondaryCompactLayoutResId()) || (paramInt == localLayoutMap.getSecondarySplitLayoutResId(false)) || (paramInt == localLayoutMap.getSecondarySplitLayoutResId(true)) || (paramInt == localLayoutMap.getSecondarySplitLeftLayoutResId()) || (paramInt == localLayoutMap.getSecondarySplitRightLayoutResId())) {
        return localLayoutMap;
      }
    }
    LogUtil.w(TAG, "Couldn't find a secondary layout map which matched the resource " + paramInt);
    return null;
  }
  
  public static int[] getParentLayout(Context paramContext, TouchTypePreferences paramTouchTypePreferences, int paramInt)
  {
    boolean bool1 = paramTouchTypePreferences.getKeyboardDockedState(paramContext);
    int i = paramTouchTypePreferences.getKeyboardLayoutStyle(paramContext);
    boolean bool2 = paramTouchTypePreferences.getUsePCLayoutStyle(paramContext.getResources().getBoolean(2131492874));
    boolean bool3 = paramTouchTypePreferences.getShowSplitNumpad(paramContext, paramContext.getResources().getBoolean(2131492877));
    LayoutMap localLayoutMap = getLayoutWhichContainsResource(paramInt);
    boolean bool4 = false;
    if (localLayoutMap == null)
    {
      localLayoutMap = getLayoutWhichContainsSecondaryResource(paramInt);
      bool4 = true;
    }
    if (localLayoutMap != null) {
      return localLayoutMap.getLayoutForStyle(bool1, i, bool4, bool2, bool3);
    }
    return new int[] { paramInt, -1 };
  }
  
  public static enum LatinState
  {
    static
    {
      DOES_NOT_PROVIDE_LATIN = new LatinState("DOES_NOT_PROVIDE_LATIN", 1);
      PROVIDES_UNEXTENDED_LATIN = new LatinState("PROVIDES_UNEXTENDED_LATIN", 2);
      PROVIDES_UNEXTENDED_LATIN_UNSELECTABLE = new LatinState("PROVIDES_UNEXTENDED_LATIN_UNSELECTABLE", 3);
      PROVIDES_EXTENDED_LATIN = new LatinState("PROVIDES_EXTENDED_LATIN", 4);
      PROVIDES_EXTENDED_LATIN_UNSELECTABLE = new LatinState("PROVIDES_EXTENDED_LATIN_UNSELECTABLE", 5);
      LatinState[] arrayOfLatinState = new LatinState[6];
      arrayOfLatinState[0] = NOT_ALPHA_LAYOUT;
      arrayOfLatinState[1] = DOES_NOT_PROVIDE_LATIN;
      arrayOfLatinState[2] = PROVIDES_UNEXTENDED_LATIN;
      arrayOfLatinState[3] = PROVIDES_UNEXTENDED_LATIN_UNSELECTABLE;
      arrayOfLatinState[4] = PROVIDES_EXTENDED_LATIN;
      arrayOfLatinState[5] = PROVIDES_EXTENDED_LATIN_UNSELECTABLE;
      $VALUES = arrayOfLatinState;
    }
    
    private LatinState() {}
  }
  
  public static enum LayoutMap
  {
    private final int mCompactLayoutResId;
    private final int mIconResourceId;
    private final LayoutData.LatinState mLatinState;
    private final String mLayoutLanguagesList;
    private final String mLayoutName;
    private final int mLayoutResId;
    private final int mNameResourceId;
    private final int mPcLayoutResId;
    private final int mSecondaryCompactLayoutResId;
    private final int mSecondaryLayoutResId;
    private final int mSecondaryPcLayoutResId;
    private final int mSecondarySplitLayoutResId;
    private final int mSecondarySplitLeftLayoutResId;
    private final int mSecondarySplitNumpadLayoutResId;
    private final int mSecondarySplitRightLayoutResId;
    private final int mSplitLayoutResId;
    private final int mSplitLeftLayoutResId;
    private final int mSplitNumpadLayoutResId;
    private final int mSplitRightLayoutResId;
    private final LayoutMap mSymbolsAltLayout;
    private final LayoutMap mSymbolsLayout;
    
    static
    {
      HARDKEYBOARD = new LayoutMap("HARDKEYBOARD", 1, "hardkeyboard", LayoutData.LatinState.NOT_ALPHA_LAYOUT, -1, -1, -1, -1, -1, -1, -1, -1, -1, null, null, null);
      SYMBOLS = new LayoutMap("SYMBOLS", 2, "symbols", LayoutData.LatinState.NOT_ALPHA_LAYOUT, 2131297005, 2130837972, 2131034654, 2131034738, 2131034654, 2131034769, 2131034769, 2131034770, 2131034771, NULL_LAYOUT, NULL_LAYOUT, null);
      SYMBOLS_ASIAN = new LayoutMap("SYMBOLS_ASIAN", 3, "symbols (asian)", LayoutData.LatinState.NOT_ALPHA_LAYOUT, 2131297007, 2130837972, 2131034726, 2131034727, 2131034726, 2131034729, 2131034729, 2131034730, 2131034731, NULL_LAYOUT, NULL_LAYOUT, null);
      SYMBOLS_ALT = new LayoutMap("SYMBOLS_ALT", 4, "symbols_alt", LayoutData.LatinState.NOT_ALPHA_LAYOUT, 2131297006, 2130837972, 2131034655, 2131034672, 2131034655, 2131034703, 2131034703, 2131034704, 2131034705, NULL_LAYOUT, NULL_LAYOUT, null);
      SYMBOLS_ALT_ASIAN = new LayoutMap("SYMBOLS_ALT_ASIAN", 5, "symbols_alt (asian)", LayoutData.LatinState.NOT_ALPHA_LAYOUT, 2131297008, 2130837972, 2131034666, 2131034666, 2131034666, 2131034666, 2131034666, 2131034666, 2131034666, NULL_LAYOUT, NULL_LAYOUT, null);
      SYMBOLS_HINDI = new LayoutMap("SYMBOLS_HINDI", 6, "symbols_hindi", LayoutData.LatinState.NOT_ALPHA_LAYOUT, 2131297005, 2130837972, 2131034744, 2131034745, 2131034744, 2131034746, 2131034746, 2131034747, 2131034748, NULL_LAYOUT, NULL_LAYOUT, null);
      SYMBOLS_BENGALI = new LayoutMap("SYMBOLS_BENGALI", 7, "symbols_bengali", LayoutData.LatinState.NOT_ALPHA_LAYOUT, 2131297005, 2130837972, 2131034733, 2131034734, 2131034733, 2131034735, 2131034735, 2131034736, 2131034737, NULL_LAYOUT, NULL_LAYOUT, null);
      SYMBOLS_GUJARATI = new LayoutMap("SYMBOLS_GUJARATI", 8, "symbols_gujarati", LayoutData.LatinState.NOT_ALPHA_LAYOUT, 2131297005, 2130837972, 2131034739, 2131034740, 2131034739, 2131034741, 2131034741, 2131034742, 2131034743, NULL_LAYOUT, NULL_LAYOUT, null);
      SYMBOLS_TELUGU = new LayoutMap("SYMBOLS_TELUGU", 9, "symbols_telugu", LayoutData.LatinState.NOT_ALPHA_LAYOUT, 2131297005, 2130837972, 2131034777, 2131034778, 2131034777, 2131034779, 2131034779, 2131034780, 2131034781, NULL_LAYOUT, NULL_LAYOUT, null);
      SYMBOLS_PUNJABI = new LayoutMap("SYMBOLS_PUNJABI", 10, "symbols_punjabi", LayoutData.LatinState.NOT_ALPHA_LAYOUT, 2131297005, 2130837972, 2131034764, 2131034765, 2131034764, 2131034766, 2131034766, 2131034767, 2131034768, NULL_LAYOUT, NULL_LAYOUT, null);
      SYMBOLS_MARATHI = new LayoutMap("SYMBOLS_MARATHI", 11, "symbols_marathi", LayoutData.LatinState.NOT_ALPHA_LAYOUT, 2131297005, 2130837972, 2131034759, 2131034760, 2131034759, 2131034761, 2131034761, 2131034762, 2131034763, NULL_LAYOUT, NULL_LAYOUT, null);
      SYMBOLS_MALAYALAM = new LayoutMap("SYMBOLS_MALAYALAM", 12, "symbols_malayalam", LayoutData.LatinState.NOT_ALPHA_LAYOUT, 2131297005, 2130837972, 2131034754, 2131034755, 2131034754, 2131034756, 2131034756, 2131034757, 2131034758, NULL_LAYOUT, NULL_LAYOUT, null);
      SYMBOLS_KANNADA = new LayoutMap("SYMBOLS_KANNADA", 13, "symbols_kannada", LayoutData.LatinState.NOT_ALPHA_LAYOUT, 2131297005, 2130837972, 2131034749, 2131034750, 2131034749, 2131034751, 2131034751, 2131034752, 2131034753, NULL_LAYOUT, NULL_LAYOUT, null);
      SYMBOLS_TAMIL = new LayoutMap("SYMBOLS_TAMIL", 14, "symbols_tamil", LayoutData.LatinState.NOT_ALPHA_LAYOUT, 2131297005, 2130837972, 2131034772, 2131034773, 2131034772, 2131034774, 2131034774, 2131034775, 2131034776, NULL_LAYOUT, NULL_LAYOUT, null);
      SYMBOLS_ARABIC = new LayoutMap("SYMBOLS_ARABIC", 15, "symbols_arabic", LayoutData.LatinState.NOT_ALPHA_LAYOUT, 2131297005, 2130837972, 2131034716, 2131034717, 2131034716, 2131034723, 2131034723, 2131034724, 2131034725, NULL_LAYOUT, NULL_LAYOUT, null);
      SYMBOLS_ARABIC_FARSI = new LayoutMap("SYMBOLS_ARABIC_FARSI", 16, "symbols_arabic_farsi", LayoutData.LatinState.NOT_ALPHA_LAYOUT, 2131297005, 2130837972, 2131034718, 2131034719, 2131034718, 2131034720, 2131034720, 2131034721, 2131034722, NULL_LAYOUT, NULL_LAYOUT, null);
      SYMBOLS_ALT_HINDI = new LayoutMap("SYMBOLS_ALT_HINDI", 17, "symbols_alt_hindi", LayoutData.LatinState.NOT_ALPHA_LAYOUT, 2131297006, 2130837972, 2131034678, 2131034679, 2131034678, 2131034680, 2131034680, 2131034681, 2131034682, NULL_LAYOUT, NULL_LAYOUT, null);
      SYMBOLS_ALT_BENGALI = new LayoutMap("SYMBOLS_ALT_BENGALI", 18, "symbols_alt_bengali", LayoutData.LatinState.NOT_ALPHA_LAYOUT, 2131297006, 2130837972, 2131034667, 2131034668, 2131034667, 2131034669, 2131034669, 2131034670, 2131034671, NULL_LAYOUT, NULL_LAYOUT, null);
      SYMBOLS_ALT_GUJARATI = new LayoutMap("SYMBOLS_ALT_GUJARATI", 19, "symbols_alt_gujarati", LayoutData.LatinState.NOT_ALPHA_LAYOUT, 2131297006, 2130837972, 2131034673, 2131034674, 2131034673, 2131034675, 2131034675, 2131034676, 2131034677, NULL_LAYOUT, NULL_LAYOUT, null);
      SYMBOLS_ALT_TELUGU = new LayoutMap("SYMBOLS_ALT_TELUGU", 20, "symbols_alt_telugu", LayoutData.LatinState.NOT_ALPHA_LAYOUT, 2131297006, 2130837972, 2131034711, 2131034712, 2131034711, 2131034713, 2131034713, 2131034714, 2131034715, NULL_LAYOUT, NULL_LAYOUT, null);
      SYMBOLS_ALT_MARATHI = new LayoutMap("SYMBOLS_ALT_MARATHI", 21, "symbols_alt_marathi", LayoutData.LatinState.NOT_ALPHA_LAYOUT, 2131297006, 2130837972, 2131034693, 2131034694, 2131034693, 2131034695, 2131034695, 2131034696, 2131034697, NULL_LAYOUT, NULL_LAYOUT, null);
      SYMBOLS_ALT_PUNJABI = new LayoutMap("SYMBOLS_ALT_PUNJABI", 22, "symbols_alt_punjabi", LayoutData.LatinState.NOT_ALPHA_LAYOUT, 2131297006, 2130837972, 2131034698, 2131034699, 2131034698, 2131034700, 2131034700, 2131034701, 2131034702, NULL_LAYOUT, NULL_LAYOUT, null);
      SYMBOLS_ALT_MALAYALAM = new LayoutMap("SYMBOLS_ALT_MALAYALAM", 23, "symbols_alt_malayalam", LayoutData.LatinState.NOT_ALPHA_LAYOUT, 2131297006, 2130837972, 2131034688, 2131034689, 2131034688, 2131034690, 2131034690, 2131034691, 2131034692, NULL_LAYOUT, NULL_LAYOUT, null);
      SYMBOLS_ALT_KANNADA = new LayoutMap("SYMBOLS_ALT_KANNADA", 24, "symbols_alt_kannada", LayoutData.LatinState.NOT_ALPHA_LAYOUT, 2131297006, 2130837972, 2131034683, 2131034684, 2131034683, 2131034685, 2131034685, 2131034686, 2131034687, NULL_LAYOUT, NULL_LAYOUT, null);
      SYMBOLS_ALT_ARABIC_FARSI = new LayoutMap("SYMBOLS_ALT_ARABIC_FARSI", 25, "symbols_alt_arabic_farsi", LayoutData.LatinState.NOT_ALPHA_LAYOUT, 2131297006, 2130837972, 2131034658, 2131034659, 2131034658, 2131034660, 2131034660, 2131034661, 2131034662, NULL_LAYOUT, NULL_LAYOUT, null);
      SYMBOLS_ALT_ARABIC = new LayoutMap("SYMBOLS_ALT_ARABIC", 26, "symbols_alt_arabic", LayoutData.LatinState.NOT_ALPHA_LAYOUT, 2131297006, 2130837972, 2131034656, 2131034657, 2131034656, 2131034663, 2131034663, 2131034664, 2131034665, NULL_LAYOUT, NULL_LAYOUT, null);
      SYMBOLS_ALT_TAMIL = new LayoutMap("SYMBOLS_ALT_TAMIL", 27, "symbols_alt_tamil", LayoutData.LatinState.NOT_ALPHA_LAYOUT, 2131297006, 2130837972, 2131034706, 2131034707, 2131034706, 2131034708, 2131034708, 2131034709, 2131034710, NULL_LAYOUT, NULL_LAYOUT, null);
      SMILEYS = new LayoutMap("SMILEYS", 28, "smileys", LayoutData.LatinState.NOT_ALPHA_LAYOUT, 2131297009, 2130837972, 2131034567, 2131034568, 2131034569, 2131034570, 2131034570, 2131034571, 2131034572, NULL_LAYOUT, NULL_LAYOUT, null);
      PHONE = new LayoutMap("PHONE", 29, "phone", LayoutData.LatinState.NOT_ALPHA_LAYOUT, 2131297045, 2130837972, 2131034115, 2131034115, 2131034115, 2131034117, 2131034117, 2131034118, 2131034119, NULL_LAYOUT, NULL_LAYOUT, null);
      PIN = new LayoutMap("PIN", 30, "pin", LayoutData.LatinState.NOT_ALPHA_LAYOUT, 2131297045, 2130837972, 2131034380, 2131034380, 2131034380, 2131034381, 2131034381, 2131034382, 2131034383, NULL_LAYOUT, NULL_LAYOUT, null);
      QWERTY = new LayoutMap("QWERTY", 31, "qwerty", LayoutData.LatinState.PROVIDES_UNEXTENDED_LATIN, 2131296981, 2130837972, 2131034440, 2131034441, 2131034477, 2131034492, 2131034495, 2131034493, 2131034494, SYMBOLS, SYMBOLS_ALT, "en");
      QWERTZ = new LayoutMap("QWERTZ", 32, "qwertz", LayoutData.LatinState.PROVIDES_UNEXTENDED_LATIN, 2131296984, 2130837972, 2131034517, 2131034525, 2131034526, 2131034534, 2131034537, 2131034535, 2131034536, SYMBOLS, SYMBOLS_ALT, "cs,de,hu,sk,sl,hr");
      AZERTY = new LayoutMap("AZERTY", 33, "azerty", LayoutData.LatinState.PROVIDES_UNEXTENDED_LATIN, 2131296982, 2130837972, 2131034197, 2131034198, 2131034199, 2131034200, 2131034203, 2131034201, 2131034202, SYMBOLS, SYMBOLS_ALT, "fr");
      QWERTY_LITHUANIAN = new LayoutMap("QWERTY_LITHUANIAN", 34, "qwerty (lithuanian)", LayoutData.LatinState.PROVIDES_EXTENDED_LATIN, 2131296990, 2130837972, 2131034463, 2131034464, 2131034465, 2131034466, 2131034469, 2131034467, 2131034468, SYMBOLS, SYMBOLS_ALT, "lt");
      QWERTY_VIETNAMESE = new LayoutMap("QWERTY_VIETNAMESE", 35, "qwerty (vietnamese)", LayoutData.LatinState.DOES_NOT_PROVIDE_LATIN, 2131297022, 2130837972, 2131034510, 2131034511, 2131034512, 2131034513, 2131034516, 2131034514, 2131034515, SYMBOLS, SYMBOLS_ALT, "vi");
      QWERTY_SPANISH = new LayoutMap("QWERTY_SPANISH", 36, "qwerty (spanish)", LayoutData.LatinState.PROVIDES_EXTENDED_LATIN, 2131296993, 2130837972, 2131034485, 2131034486, 2131034487, 2131034488, 2131034491, 2131034489, 2131034490, SYMBOLS, SYMBOLS_ALT, "es");
      COLEMAK = new LayoutMap("COLEMAK", 37, "colemak", LayoutData.LatinState.PROVIDES_UNEXTENDED_LATIN, 2131297016, 2130837972, 2131034246, 2131034247, 2131034248, 2131034249, 2131034252, 2131034250, 2131034251, SYMBOLS, SYMBOLS_ALT, null);
      QWERTY_SERBIAN = new LayoutMap("QWERTY_SERBIAN", 38, "qwerty (serbo-croat)", LayoutData.LatinState.PROVIDES_EXTENDED_LATIN, 2131296991, 2130837972, 2131034478, 2131034479, 2131034480, 2131034481, 2131034484, 2131034482, 2131034483, SYMBOLS, SYMBOLS_ALT, "sr");
      QWERTZ_SERBIAN = new LayoutMap("QWERTZ_SERBIAN", 39, "qwertz (serbo-croat)", LayoutData.LatinState.PROVIDES_EXTENDED_LATIN, 2131296992, 2130837972, 2131034527, 2131034528, 2131034529, 2131034530, 2131034533, 2131034531, 2131034532, SYMBOLS, SYMBOLS_ALT, null);
      QZERTY = new LayoutMap("QZERTY", 40, "qzerty", LayoutData.LatinState.PROVIDES_UNEXTENDED_LATIN, 2131296983, 2130837972, 2131034538, 2131034539, 2131034540, 2131034541, 2131034544, 2131034542, 2131034543, SYMBOLS, SYMBOLS_ALT, null);
      QWERTY_DANISH = new LayoutMap("QWERTY_DANISH", 41, "qwerty (danish)", LayoutData.LatinState.PROVIDES_EXTENDED_LATIN, 2131296985, 2130837972, 2131034442, 2131034443, 2131034444, 2131034445, 2131034448, 2131034446, 2131034447, SYMBOLS, SYMBOLS_ALT, "da");
      QWERTY_NORWEGIAN = new LayoutMap("QWERTY_NORWEGIAN", 42, "qwerty (norwegian)", LayoutData.LatinState.PROVIDES_EXTENDED_LATIN, 2131296986, 2130837972, 2131034470, 2131034471, 2131034472, 2131034473, 2131034476, 2131034474, 2131034475, SYMBOLS, SYMBOLS_ALT, "nn,no,nb");
      QWERTY_SWEDISH = new LayoutMap("QWERTY_SWEDISH", 43, "qwerty (swedish)", LayoutData.LatinState.PROVIDES_EXTENDED_LATIN, 2131296987, 2130837972, 2131034496, 2131034497, 2131034498, 2131034499, 2131034502, 2131034500, 2131034501, SYMBOLS, SYMBOLS_ALT, "sv,fi");
      SVORAK = new LayoutMap("SVORAK", 44, "svorak (swedish)", LayoutData.LatinState.PROVIDES_EXTENDED_LATIN, 2131297010, 2130837972, 2131034573, 2131034574, 2131034575, 2131034576, 2131034579, 2131034577, 2131034578, SYMBOLS, SYMBOLS_ALT, null);
      QWERTY_ESTONIAN = new LayoutMap("QWERTY_ESTONIAN", 45, "qwerty (estonian)", LayoutData.LatinState.PROVIDES_EXTENDED_LATIN, 2131296989, 2130837972, 2131034449, 2131034450, 2131034451, 2131034452, 2131034455, 2131034453, 2131034454, SYMBOLS, SYMBOLS_ALT, "et");
      QWERTY_ICELANDIC = new LayoutMap("QWERTY_ICELANDIC", 46, "qwerty (icelandic)", LayoutData.LatinState.PROVIDES_EXTENDED_LATIN, 2131296988, 2130837972, 2131034456, 2131034457, 2131034458, 2131034459, 2131034462, 2131034460, 2131034461, SYMBOLS, SYMBOLS_ALT, "is");
      QWERTZ_ALBANIAN = new LayoutMap("QWERTZ_ALBANIAN", 47, "qwertz (albanian)", LayoutData.LatinState.PROVIDES_EXTENDED_LATIN, 2131297023, 2130837972, 2131034518, 2131034519, 2131034520, 2131034521, 2131034524, 2131034522, 2131034523, SYMBOLS, SYMBOLS_ALT, "sq");
      DVORAK = new LayoutMap("DVORAK", 48, "dvorak", LayoutData.LatinState.PROVIDES_UNEXTENDED_LATIN, 2131297015, 2130837972, 2131034253, 2131034254, 2131034255, 2131034256, 2131034259, 2131034257, 2131034258, SYMBOLS, SYMBOLS_ALT, null);
      QWERTY_TURKISH = new LayoutMap("QWERTY_TURKISH", 49, "qwerty (turkish)", LayoutData.LatinState.PROVIDES_EXTENDED_LATIN, 2131297026, 2130837972, 2131034503, 2131034504, 2131034505, 2131034506, 2131034509, 2131034507, 2131034508, SYMBOLS, SYMBOLS_ALT, "tr");
      BEPO = new LayoutMap("BEPO", 50, "bepo", LayoutData.LatinState.PROVIDES_EXTENDED_LATIN, 2131297011, 2130837972, 2131034260, 2131034261, 2131034262, 2131034263, 2131034266, 2131034264, 2131034265, SYMBOLS, SYMBOLS_ALT, null);
      GREEK = new LayoutMap("GREEK", 51, "greek", LayoutData.LatinState.DOES_NOT_PROVIDE_LATIN, 2131296997, 2130837972, 2131034274, 2131034275, 2131034276, 2131034277, 2131034280, 2131034278, 2131034279, SYMBOLS, SYMBOLS_ALT, "el");
      KOREAN = new LayoutMap("KOREAN", 52, "korean", LayoutData.LatinState.DOES_NOT_PROVIDE_LATIN, 2131297002, 2130837972, 2131034331, 2131034332, 2131034333, 2131034341, 2131034344, 2131034342, 2131034343, 2131034334, 2131034335, 2131034336, 2131034337, 2131034340, 2131034338, 2131034339, SYMBOLS, SYMBOLS_ALT, "ko");
      RUSSIAN_WIN = new LayoutMap("RUSSIAN_WIN", 53, "russian", LayoutData.LatinState.DOES_NOT_PROVIDE_LATIN, 2131296994, 2130837972, 2131034560, 2131034561, 2131034562, 2131034563, 2131034566, 2131034564, 2131034565, SYMBOLS, SYMBOLS_ALT, "ru,kk");
      RUSSIAN_COMPACT = new LayoutMap("RUSSIAN_COMPACT", 54, "russian (compact)", LayoutData.LatinState.DOES_NOT_PROVIDE_LATIN, 2131297014, 2130837972, 2131034546, 2131034547, 2131034548, 2131034549, 2131034552, 2131034550, 2131034551, SYMBOLS, SYMBOLS_ALT, null);
      RUSSIAN_PHONETIC = new LayoutMap("RUSSIAN_PHONETIC", 55, "russian (phonetic)", LayoutData.LatinState.DOES_NOT_PROVIDE_LATIN, 2131297025, 2130837972, 2131034553, 2131034554, 2131034555, 2131034556, 2131034559, 2131034557, 2131034558, SYMBOLS, SYMBOLS_ALT, null);
      BULGARIAN_PHONETIC = new LayoutMap("BULGARIAN_PHONETIC", 56, "bulgarian (phonetic)", LayoutData.LatinState.DOES_NOT_PROVIDE_LATIN, 2131296995, 2130837972, 2131034225, 2131034226, 2131034227, 2131034228, 2131034231, 2131034229, 2131034230, SYMBOLS, SYMBOLS_ALT, "bg");
      BULGARIAN_BDS = new LayoutMap("BULGARIAN_BDS", 57, "bulgarian (bds)", LayoutData.LatinState.DOES_NOT_PROVIDE_LATIN, 2131297012, 2130837972, 2131034218, 2131034219, 2131034220, 2131034221, 2131034224, 2131034222, 2131034223, SYMBOLS, SYMBOLS_ALT, null);
      UKRAINIAN = new LayoutMap("UKRAINIAN", 58, "ukrainian", LayoutData.LatinState.DOES_NOT_PROVIDE_LATIN, 2131296996, 2130837972, 2131034622, 2131034623, 2131034631, 2131034632, 2131034635, 2131034633, 2131034634, SYMBOLS, SYMBOLS_ALT, "uk");
      UKRAINIAN_COMPACT = new LayoutMap("UKRAINIAN_COMPACT", 59, "ukrainian (compact)", LayoutData.LatinState.DOES_NOT_PROVIDE_LATIN, 2131297013, 2130837972, 2131034624, 2131034625, 2131034626, 2131034627, 2131034630, 2131034628, 2131034629, SYMBOLS, SYMBOLS_ALT, null);
      HEBREW = new LayoutMap("HEBREW", 60, "hebrew", LayoutData.LatinState.DOES_NOT_PROVIDE_LATIN, 2131296998, 2130837972, 2131034295, 2131034296, 2131034297, 2131034298, 2131034301, 2131034299, 2131034300, SYMBOLS, SYMBOLS_ALT, "he");
      PERSIAN_FARSI = new LayoutMap("PERSIAN_FARSI", 61, "persian (farsi)", LayoutData.LatinState.DOES_NOT_PROVIDE_LATIN, 2131297001, 2130837972, 2131034164, 2131034165, 2131034166, 2131034167, 2131034170, 2131034168, 2131034169, SYMBOLS, SYMBOLS_ALT, "fa");
      ARABIC_URDU = new LayoutMap("ARABIC_URDU", 62, "arabic_urdu", LayoutData.LatinState.DOES_NOT_PROVIDE_LATIN, 2131297000, 2130837972, 2131034176, 2131034177, 2131034178, 2131034179, 2131034182, 2131034180, 2131034181, SYMBOLS_ARABIC, SYMBOLS_ALT_ARABIC, "ur");
      ARABIC = new LayoutMap("ARABIC", 63, "arabic", LayoutData.LatinState.DOES_NOT_PROVIDE_LATIN, 2131296999, 2130837972, 2131034162, 2131034163, 2131034171, 2131034172, 2131034175, 2131034173, 2131034174, SYMBOLS_ARABIC, SYMBOLS_ALT_ARABIC, "ar");
      HINDI = new LayoutMap("HINDI", 64, "hindi", LayoutData.LatinState.DOES_NOT_PROVIDE_LATIN, 2131297017, 2130837972, 2131034302, 2131034303, 2131034304, 2131034312, 2131034315, 2131034313, 2131034314, 2131034305, 2131034306, 2131034307, 2131034308, 2131034311, 2131034309, 2131034310, SYMBOLS_HINDI, SYMBOLS_ALT_HINDI, "hi");
      GEORGIAN = new LayoutMap("GEORGIAN", 65, "georgian (win)", LayoutData.LatinState.DOES_NOT_PROVIDE_LATIN, 2131297018, 2130837972, 2131034267, 2131034268, 2131034269, 2131034270, 2131034273, 2131034271, 2131034272, SYMBOLS, SYMBOLS_ALT, "ka");
      ARMENIAN = new LayoutMap("ARMENIAN", 66, "armenian", LayoutData.LatinState.DOES_NOT_PROVIDE_LATIN, 2131297019, 2130837972, 2131034183, 2131034184, 2131034185, 2131034186, 2131034189, 2131034187, 2131034188, SYMBOLS, SYMBOLS_ALT, "hy");
      MACEDONIAN = new LayoutMap("MACEDONIAN", 67, "macedonian", LayoutData.LatinState.DOES_NOT_PROVIDE_LATIN, 2131297020, 2130837972, 2131034345, 2131034346, 2131034347, 2131034348, 2131034351, 2131034349, 2131034350, SYMBOLS, SYMBOLS_ALT, "mk");
      AZERBAIJANI = new LayoutMap("AZERBAIJANI", 68, "azerbaijani", LayoutData.LatinState.DOES_NOT_PROVIDE_LATIN, 2131297021, 2130837972, 2131034190, 2131034191, 2131034192, 2131034193, 2131034196, 2131034194, 2131034195, SYMBOLS, SYMBOLS_ALT, "az");
      TAMIL = new LayoutMap("TAMIL", 69, "tamil", LayoutData.LatinState.DOES_NOT_PROVIDE_LATIN, 2131297024, 2130837972, 2131034580, 2131034581, 2131034582, 2131034590, 2131034593, 2131034591, 2131034592, 2131034583, 2131034584, 2131034585, 2131034586, 2131034589, 2131034587, 2131034588, SYMBOLS_TAMIL, SYMBOLS_ALT_TAMIL, "ta");
      BENGALI = new LayoutMap("BENGALI", 70, "bengali", LayoutData.LatinState.DOES_NOT_PROVIDE_LATIN, 2131297027, 2130837972, 2131034204, 2131034205, 2131034206, 2131034214, 2131034217, 2131034215, 2131034216, 2131034207, 2131034208, 2131034209, 2131034210, 2131034213, 2131034211, 2131034212, SYMBOLS_BENGALI, SYMBOLS_ALT_BENGALI, "bn");
      GUJARATI = new LayoutMap("GUJARATI", 71, "gujarati", LayoutData.LatinState.DOES_NOT_PROVIDE_LATIN, 2131297028, 2130837972, 2131034281, 2131034282, 2131034283, 2131034291, 2131034294, 2131034292, 2131034293, 2131034284, 2131034285, 2131034286, 2131034287, 2131034290, 2131034288, 2131034289, SYMBOLS_GUJARATI, SYMBOLS_ALT_GUJARATI, "gu");
      MARATHI = new LayoutMap("MARATHI", 72, "marathi", LayoutData.LatinState.DOES_NOT_PROVIDE_LATIN, 2131297029, 2130837972, 2131034366, 2131034367, 2131034368, 2131034376, 2131034379, 2131034377, 2131034378, 2131034369, 2131034370, 2131034371, 2131034372, 2131034375, 2131034373, 2131034374, SYMBOLS_MARATHI, SYMBOLS_ALT_MARATHI, "mr");
      PUNJABI = new LayoutMap("PUNJABI", 73, "punjabi", LayoutData.LatinState.DOES_NOT_PROVIDE_LATIN, 2131297043, 2130837972, 2131034412, 2131034413, 2131034414, 2131034422, 2131034425, 2131034423, 2131034424, 2131034415, 2131034416, 2131034417, 2131034418, 2131034421, 2131034419, 2131034420, SYMBOLS_PUNJABI, SYMBOLS_ALT_PUNJABI, "pa");
      TELUGU = new LayoutMap("TELUGU", 74, "telugu", LayoutData.LatinState.DOES_NOT_PROVIDE_LATIN, 2131297040, 2130837972, 2131034594, 2131034595, 2131034596, 2131034604, 2131034607, 2131034605, 2131034606, 2131034597, 2131034598, 2131034599, 2131034600, 2131034603, 2131034601, 2131034602, SYMBOLS_TELUGU, SYMBOLS_ALT_TELUGU, "te");
      MALAYALAM = new LayoutMap("MALAYALAM", 75, "malayalam", LayoutData.LatinState.DOES_NOT_PROVIDE_LATIN, 2131297041, 2130837972, 2131034352, 2131034353, 2131034354, 2131034362, 2131034365, 2131034363, 2131034364, 2131034355, 2131034356, 2131034357, 2131034358, 2131034361, 2131034359, 2131034360, SYMBOLS_MALAYALAM, SYMBOLS_ALT_MALAYALAM, "ml");
      KANNADA = new LayoutMap("KANNADA", 76, "kannada", LayoutData.LatinState.DOES_NOT_PROVIDE_LATIN, 2131297042, 2130837972, 2131034317, 2131034318, 2131034319, 2131034327, 2131034330, 2131034328, 2131034329, 2131034320, 2131034321, 2131034322, 2131034323, 2131034326, 2131034324, 2131034325, SYMBOLS_KANNADA, SYMBOLS_ALT_KANNADA, "kn");
      THAI = new LayoutMap("THAI", 77, "thai", LayoutData.LatinState.DOES_NOT_PROVIDE_LATIN, 2131297004, 2130837972, 2131034608, 2131034609, 2131034610, 2131034618, 2131034621, 2131034619, 2131034620, 2131034611, 2131034612, 2131034613, 2131034614, 2131034617, 2131034615, 2131034616, SYMBOLS, SYMBOLS_ALT, "th");
      PINYIN = new LayoutMap("PINYIN", 78, "pinyin", LayoutData.LatinState.DOES_NOT_PROVIDE_LATIN, 2131297030, 2130837972, 2131034384, 2131034399, 2131034407, 2131034408, 2131034411, 2131034409, 2131034410, SYMBOLS_ASIAN, SYMBOLS_ALT_ASIAN, "zh");
      PINYIN12 = new LayoutMap("PINYIN12", 79, "pinyin12", LayoutData.LatinState.DOES_NOT_PROVIDE_LATIN, 2131297031, 2130837972, 2131034385, 2131034386, 2131034394, 2131034395, 2131034395, 2131034396, 2131034397, SYMBOLS_ASIAN, SYMBOLS_ALT_ASIAN, "zh_CN");
      PINYIN_LATIN = new LayoutMap("PINYIN_LATIN", 80, "pinyin_latin", LayoutData.LatinState.PROVIDES_UNEXTENDED_LATIN_UNSELECTABLE, 2131297030, 2130837972, 2131034400, 2131034401, 2131034402, 2131034403, 2131034406, 2131034404, 2131034405, SYMBOLS_ASIAN, SYMBOLS_ALT_ASIAN, null);
      PINYIN12_LATIN = new LayoutMap("PINYIN12_LATIN", 81, "pinyin12_latin", LayoutData.LatinState.DOES_NOT_PROVIDE_LATIN, 2131297031, 2130837972, 2131034387, 2131034388, 2131034389, 2131034390, 2131034393, 2131034391, 2131034392, SYMBOLS_ASIAN, SYMBOLS_ALT_ASIAN, null);
      JYUTPING = new LayoutMap("JYUTPING", 82, "jyutping", LayoutData.LatinState.DOES_NOT_PROVIDE_LATIN, 2131297032, 2130837972, 2131034384, 2131034384, 2131034384, 2131034408, 2131034408, 2131034384, 2131034384, SYMBOLS_ASIAN, SYMBOLS_ALT_ASIAN, null);
      JYUTPING12 = new LayoutMap("JYUTPING12", 83, "jyutping12", LayoutData.LatinState.DOES_NOT_PROVIDE_LATIN, 2131297033, 2130837972, 2131034385, 2131034385, 2131034385, 2131034395, 2131034395, 2131034385, 2131034385, SYMBOLS_ASIAN, SYMBOLS_ALT_ASIAN, null);
      ZHUYIN = new LayoutMap("ZHUYIN", 84, "zhuyin", LayoutData.LatinState.DOES_NOT_PROVIDE_LATIN, 2131297034, 2130837972, 2131034636, 2131034636, 2131034636, 2131034653, 2131034653, 2131034636, 2131034636, SYMBOLS_ASIAN, SYMBOLS_ALT_ASIAN, "zh_TW");
      ZHUYIN_LATIN = new LayoutMap("ZHUYIN_LATIN", 85, "zhuyin_latin", LayoutData.LatinState.PROVIDES_UNEXTENDED_LATIN_UNSELECTABLE, 2131297034, 2130837972, 2131034646, 2131034647, 2131034648, 2131034649, 2131034652, 2131034650, 2131034651, SYMBOLS_ASIAN, SYMBOLS_ALT_ASIAN, null);
      ZHUYIN12 = new LayoutMap("ZHUYIN12", 86, "zhuyin12", LayoutData.LatinState.DOES_NOT_PROVIDE_LATIN, 2131297035, 2130837972, 2131034637, 2131034637, 2131034637, 2131034637, 2131034637, 2131034637, 2131034637, SYMBOLS_ASIAN, SYMBOLS_ALT_ASIAN, null);
      ZHUYIN12_LATIN = new LayoutMap("ZHUYIN12_LATIN", 87, "zhuyin12_latin", LayoutData.LatinState.PROVIDES_UNEXTENDED_LATIN_UNSELECTABLE, 2131297034, 2130837972, 2131034638, 2131034639, 2131034640, 2131034641, 2131034644, 2131034642, 2131034643, SYMBOLS_ASIAN, SYMBOLS_ALT_ASIAN, null);
      CANGJIE = new LayoutMap("CANGJIE", 88, "cangjie", LayoutData.LatinState.DOES_NOT_PROVIDE_LATIN, 2131297037, 2130837972, 2131034232, 2131034233, 2131034241, 2131034242, 2131034245, 2131034243, 2131034244, SYMBOLS_ASIAN, SYMBOLS_ALT_ASIAN, null);
      QCANGJIE = new LayoutMap("QCANGJIE", 89, "qcangjie", LayoutData.LatinState.DOES_NOT_PROVIDE_LATIN, 2131297038, 2130837972, 2131034426, 2131034427, 2131034435, 2131034436, 2131034439, 2131034437, 2131034438, SYMBOLS_ASIAN, SYMBOLS_ALT_ASIAN, null);
      CANGJIE_LATIN = new LayoutMap("CANGJIE_LATIN", 90, "cangjie_latin", LayoutData.LatinState.PROVIDES_UNEXTENDED_LATIN_UNSELECTABLE, 2131297037, 2130837972, 2131034234, 2131034235, 2131034236, 2131034237, 2131034240, 2131034238, 2131034239, SYMBOLS_ASIAN, SYMBOLS_ALT_ASIAN, null);
      QCANGJIE_LATIN = new LayoutMap("QCANGJIE_LATIN", 91, "cangjie_latin", LayoutData.LatinState.PROVIDES_UNEXTENDED_LATIN_UNSELECTABLE, 2131297037, 2130837972, 2131034428, 2131034429, 2131034430, 2131034431, 2131034434, 2131034432, 2131034433, SYMBOLS_ASIAN, SYMBOLS_ALT_ASIAN, null);
      FIVESTROKE_CN = new LayoutMap("FIVESTROKE_CN", 92, "5stroke_cn", LayoutData.LatinState.DOES_NOT_PROVIDE_LATIN, 2131297036, 2130837972, 2131034120, 2131034121, 2131034129, 2131034130, 2131034133, 2131034131, 2131034132, SYMBOLS_ASIAN, SYMBOLS_ALT_ASIAN, null);
      FIVESTROKE_CN_LATIN = new LayoutMap("FIVESTROKE_CN_LATIN", 93, "5stroke_cn_latin", LayoutData.LatinState.PROVIDES_UNEXTENDED_LATIN_UNSELECTABLE, 2131297036, 2130837972, 2131034122, 2131034123, 2131034124, 2131034125, 2131034128, 2131034126, 2131034127, SYMBOLS_ASIAN, SYMBOLS_ALT_ASIAN, null);
      FIVESTROKE_HK = new LayoutMap("FIVESTROKE_HK", 94, "5stroke_hk", LayoutData.LatinState.DOES_NOT_PROVIDE_LATIN, 2131297036, 2130837972, 2131034134, 2131034135, 2131034143, 2131034144, 2131034147, 2131034145, 2131034146, SYMBOLS_ASIAN, SYMBOLS_ALT_ASIAN, "zh_HK");
      FIVESTROKE_HK_LATIN = new LayoutMap("FIVESTROKE_HK_LATIN", 95, "5stroke_hk_latin", LayoutData.LatinState.PROVIDES_UNEXTENDED_LATIN_UNSELECTABLE, 2131297036, 2130837972, 2131034136, 2131034137, 2131034138, 2131034139, 2131034142, 2131034140, 2131034141, SYMBOLS_ASIAN, SYMBOLS_ALT_ASIAN, null);
      FIVESTROKE_TW = new LayoutMap("FIVESTROKE_TW", 96, "5stroke_tw", LayoutData.LatinState.DOES_NOT_PROVIDE_LATIN, 2131297036, 2130837972, 2131034150, 2131034151, 2131034152, 2131034153, 2131034156, 2131034154, 2131034155, SYMBOLS_ASIAN, SYMBOLS_ALT_ASIAN, null);
      FIVESTROKE_TW_LATIN = new LayoutMap("FIVESTROKE_TW_LATIN", 97, "5stroke_tw_latin", LayoutData.LatinState.PROVIDES_UNEXTENDED_LATIN_UNSELECTABLE, 2131297036, 2130837972, 2131034150, 2131034151, 2131034152, 2131034153, 2131034156, 2131034154, 2131034155, SYMBOLS_ASIAN, SYMBOLS_ALT_ASIAN, null);
      ROMAJI = new LayoutMap("ROMAJI", 98, "romaji", LayoutData.LatinState.DOES_NOT_PROVIDE_LATIN, 2131297039, 2130837972, 2131034545, 2131034545, 2131034545, 2131034545, 2131034545, 2131034545, 2131034545, SYMBOLS, SYMBOLS_ALT, "ja");
      HIRAGANA = new LayoutMap("HIRAGANA", 99, "hiragana", LayoutData.LatinState.DOES_NOT_PROVIDE_LATIN, 2131297044, 2130837972, 2131034316, 2131034316, 2131034316, 2131034316, 2131034316, 2131034316, 2131034316, SYMBOLS, SYMBOLS_ALT, null);
      LayoutMap[] arrayOfLayoutMap = new LayoutMap[100];
      arrayOfLayoutMap[0] = NULL_LAYOUT;
      arrayOfLayoutMap[1] = HARDKEYBOARD;
      arrayOfLayoutMap[2] = SYMBOLS;
      arrayOfLayoutMap[3] = SYMBOLS_ASIAN;
      arrayOfLayoutMap[4] = SYMBOLS_ALT;
      arrayOfLayoutMap[5] = SYMBOLS_ALT_ASIAN;
      arrayOfLayoutMap[6] = SYMBOLS_HINDI;
      arrayOfLayoutMap[7] = SYMBOLS_BENGALI;
      arrayOfLayoutMap[8] = SYMBOLS_GUJARATI;
      arrayOfLayoutMap[9] = SYMBOLS_TELUGU;
      arrayOfLayoutMap[10] = SYMBOLS_PUNJABI;
      arrayOfLayoutMap[11] = SYMBOLS_MARATHI;
      arrayOfLayoutMap[12] = SYMBOLS_MALAYALAM;
      arrayOfLayoutMap[13] = SYMBOLS_KANNADA;
      arrayOfLayoutMap[14] = SYMBOLS_TAMIL;
      arrayOfLayoutMap[15] = SYMBOLS_ARABIC;
      arrayOfLayoutMap[16] = SYMBOLS_ARABIC_FARSI;
      arrayOfLayoutMap[17] = SYMBOLS_ALT_HINDI;
      arrayOfLayoutMap[18] = SYMBOLS_ALT_BENGALI;
      arrayOfLayoutMap[19] = SYMBOLS_ALT_GUJARATI;
      arrayOfLayoutMap[20] = SYMBOLS_ALT_TELUGU;
      arrayOfLayoutMap[21] = SYMBOLS_ALT_MARATHI;
      arrayOfLayoutMap[22] = SYMBOLS_ALT_PUNJABI;
      arrayOfLayoutMap[23] = SYMBOLS_ALT_MALAYALAM;
      arrayOfLayoutMap[24] = SYMBOLS_ALT_KANNADA;
      arrayOfLayoutMap[25] = SYMBOLS_ALT_ARABIC_FARSI;
      arrayOfLayoutMap[26] = SYMBOLS_ALT_ARABIC;
      arrayOfLayoutMap[27] = SYMBOLS_ALT_TAMIL;
      arrayOfLayoutMap[28] = SMILEYS;
      arrayOfLayoutMap[29] = PHONE;
      arrayOfLayoutMap[30] = PIN;
      arrayOfLayoutMap[31] = QWERTY;
      arrayOfLayoutMap[32] = QWERTZ;
      arrayOfLayoutMap[33] = AZERTY;
      arrayOfLayoutMap[34] = QWERTY_LITHUANIAN;
      arrayOfLayoutMap[35] = QWERTY_VIETNAMESE;
      arrayOfLayoutMap[36] = QWERTY_SPANISH;
      arrayOfLayoutMap[37] = COLEMAK;
      arrayOfLayoutMap[38] = QWERTY_SERBIAN;
      arrayOfLayoutMap[39] = QWERTZ_SERBIAN;
      arrayOfLayoutMap[40] = QZERTY;
      arrayOfLayoutMap[41] = QWERTY_DANISH;
      arrayOfLayoutMap[42] = QWERTY_NORWEGIAN;
      arrayOfLayoutMap[43] = QWERTY_SWEDISH;
      arrayOfLayoutMap[44] = SVORAK;
      arrayOfLayoutMap[45] = QWERTY_ESTONIAN;
      arrayOfLayoutMap[46] = QWERTY_ICELANDIC;
      arrayOfLayoutMap[47] = QWERTZ_ALBANIAN;
      arrayOfLayoutMap[48] = DVORAK;
      arrayOfLayoutMap[49] = QWERTY_TURKISH;
      arrayOfLayoutMap[50] = BEPO;
      arrayOfLayoutMap[51] = GREEK;
      arrayOfLayoutMap[52] = KOREAN;
      arrayOfLayoutMap[53] = RUSSIAN_WIN;
      arrayOfLayoutMap[54] = RUSSIAN_COMPACT;
      arrayOfLayoutMap[55] = RUSSIAN_PHONETIC;
      arrayOfLayoutMap[56] = BULGARIAN_PHONETIC;
      arrayOfLayoutMap[57] = BULGARIAN_BDS;
      arrayOfLayoutMap[58] = UKRAINIAN;
      arrayOfLayoutMap[59] = UKRAINIAN_COMPACT;
      arrayOfLayoutMap[60] = HEBREW;
      arrayOfLayoutMap[61] = PERSIAN_FARSI;
      arrayOfLayoutMap[62] = ARABIC_URDU;
      arrayOfLayoutMap[63] = ARABIC;
      arrayOfLayoutMap[64] = HINDI;
      arrayOfLayoutMap[65] = GEORGIAN;
      arrayOfLayoutMap[66] = ARMENIAN;
      arrayOfLayoutMap[67] = MACEDONIAN;
      arrayOfLayoutMap[68] = AZERBAIJANI;
      arrayOfLayoutMap[69] = TAMIL;
      arrayOfLayoutMap[70] = BENGALI;
      arrayOfLayoutMap[71] = GUJARATI;
      arrayOfLayoutMap[72] = MARATHI;
      arrayOfLayoutMap[73] = PUNJABI;
      arrayOfLayoutMap[74] = TELUGU;
      arrayOfLayoutMap[75] = MALAYALAM;
      arrayOfLayoutMap[76] = KANNADA;
      arrayOfLayoutMap[77] = THAI;
      arrayOfLayoutMap[78] = PINYIN;
      arrayOfLayoutMap[79] = PINYIN12;
      arrayOfLayoutMap[80] = PINYIN_LATIN;
      arrayOfLayoutMap[81] = PINYIN12_LATIN;
      arrayOfLayoutMap[82] = JYUTPING;
      arrayOfLayoutMap[83] = JYUTPING12;
      arrayOfLayoutMap[84] = ZHUYIN;
      arrayOfLayoutMap[85] = ZHUYIN_LATIN;
      arrayOfLayoutMap[86] = ZHUYIN12;
      arrayOfLayoutMap[87] = ZHUYIN12_LATIN;
      arrayOfLayoutMap[88] = CANGJIE;
      arrayOfLayoutMap[89] = QCANGJIE;
      arrayOfLayoutMap[90] = CANGJIE_LATIN;
      arrayOfLayoutMap[91] = QCANGJIE_LATIN;
      arrayOfLayoutMap[92] = FIVESTROKE_CN;
      arrayOfLayoutMap[93] = FIVESTROKE_CN_LATIN;
      arrayOfLayoutMap[94] = FIVESTROKE_HK;
      arrayOfLayoutMap[95] = FIVESTROKE_HK_LATIN;
      arrayOfLayoutMap[96] = FIVESTROKE_TW;
      arrayOfLayoutMap[97] = FIVESTROKE_TW_LATIN;
      arrayOfLayoutMap[98] = ROMAJI;
      arrayOfLayoutMap[99] = HIRAGANA;
      $VALUES = arrayOfLayoutMap;
    }
    
    private LayoutMap(String paramString1, LayoutData.LatinState paramLatinState, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11, int paramInt12, int paramInt13, int paramInt14, int paramInt15, int paramInt16, LayoutMap paramLayoutMap1, LayoutMap paramLayoutMap2, String paramString2)
    {
      this.mLayoutName = paramString1;
      this.mLatinState = paramLatinState;
      this.mNameResourceId = paramInt1;
      this.mIconResourceId = paramInt2;
      this.mLayoutResId = paramInt3;
      this.mCompactLayoutResId = paramInt4;
      this.mSplitNumpadLayoutResId = paramInt7;
      this.mPcLayoutResId = paramInt5;
      this.mSplitLayoutResId = paramInt6;
      this.mSplitLeftLayoutResId = paramInt8;
      this.mSplitRightLayoutResId = paramInt9;
      this.mSymbolsLayout = paramLayoutMap1;
      this.mSymbolsAltLayout = paramLayoutMap2;
      this.mSecondaryLayoutResId = paramInt10;
      this.mSecondaryCompactLayoutResId = paramInt11;
      this.mSecondarySplitNumpadLayoutResId = paramInt14;
      this.mSecondaryPcLayoutResId = paramInt12;
      this.mSecondarySplitLayoutResId = paramInt13;
      this.mSecondarySplitLeftLayoutResId = paramInt15;
      this.mSecondarySplitRightLayoutResId = paramInt16;
      this.mLayoutLanguagesList = paramString2;
    }
    
    private LayoutMap(String paramString1, LayoutData.LatinState paramLatinState, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, LayoutMap paramLayoutMap1, LayoutMap paramLayoutMap2, String paramString2)
    {
      this.mLayoutName = paramString1;
      this.mLatinState = paramLatinState;
      this.mNameResourceId = paramInt1;
      this.mIconResourceId = paramInt2;
      this.mLayoutResId = paramInt3;
      this.mCompactLayoutResId = paramInt4;
      this.mSplitNumpadLayoutResId = paramInt7;
      this.mPcLayoutResId = paramInt5;
      this.mSplitLayoutResId = paramInt6;
      this.mSplitLeftLayoutResId = paramInt8;
      this.mSplitRightLayoutResId = paramInt9;
      this.mSymbolsLayout = paramLayoutMap1;
      this.mSymbolsAltLayout = paramLayoutMap2;
      this.mSecondaryLayoutResId = -1;
      this.mSecondaryCompactLayoutResId = -1;
      this.mSecondarySplitNumpadLayoutResId = -1;
      this.mSecondaryPcLayoutResId = -1;
      this.mSecondarySplitLayoutResId = -1;
      this.mSecondarySplitLeftLayoutResId = -1;
      this.mSecondarySplitRightLayoutResId = -1;
      this.mLayoutLanguagesList = paramString2;
    }
    
    public boolean definesLayout(int paramInt)
    {
      return (paramInt == this.mLayoutResId) || (paramInt == this.mCompactLayoutResId) || (paramInt == this.mPcLayoutResId) || (paramInt == this.mSplitLayoutResId) || (paramInt == this.mSplitNumpadLayoutResId) || (paramInt == this.mSecondaryLayoutResId) || (paramInt == this.mSecondaryCompactLayoutResId) || (paramInt == this.mSecondarySplitLayoutResId) || (paramInt == this.mSecondarySplitNumpadLayoutResId);
    }
    
    public boolean extendsQwerty()
    {
      return (this.mLatinState == LayoutData.LatinState.PROVIDES_EXTENDED_LATIN) || (this.mLatinState == LayoutData.LatinState.PROVIDES_EXTENDED_LATIN_UNSELECTABLE);
    }
    
    public int getCompactLayoutResId()
    {
      return this.mCompactLayoutResId;
    }
    
    public int getIconResourceId()
    {
      return this.mIconResourceId;
    }
    
    public int getLayout(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
    {
      if (paramBoolean1)
      {
        if (paramBoolean4) {
          return this.mSplitNumpadLayoutResId;
        }
        return this.mSplitLayoutResId;
      }
      if (paramBoolean2) {
        return this.mPcLayoutResId;
      }
      if (paramBoolean3) {
        return this.mCompactLayoutResId;
      }
      return this.mLayoutResId;
    }
    
    public int[] getLayoutForStyle(boolean paramBoolean1, int paramInt, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
    {
      int i;
      int j;
      if (paramInt == 2)
      {
        if (paramBoolean1)
        {
          if (paramBoolean2) {
            if (paramBoolean4) {
              i = this.mSecondarySplitNumpadLayoutResId;
            }
          }
          for (;;)
          {
            j = -1;
            return new int[] { i, j };
            i = this.mSecondarySplitLayoutResId;
            continue;
            if (paramBoolean4) {
              i = this.mSplitNumpadLayoutResId;
            } else {
              i = this.mSplitLayoutResId;
            }
          }
        }
        if (paramBoolean2)
        {
          i = this.mSecondarySplitLeftLayoutResId;
          label83:
          if (!paramBoolean2) {
            break label105;
          }
        }
        label105:
        for (j = this.mSecondarySplitRightLayoutResId;; j = this.mSplitRightLayoutResId)
        {
          break;
          i = this.mSplitLeftLayoutResId;
          break label83;
        }
      }
      if (paramInt == 3)
      {
        if (paramBoolean2) {}
        for (i = this.mSecondaryCompactLayoutResId;; i = this.mCompactLayoutResId)
        {
          j = -1;
          break;
        }
      }
      if (paramBoolean2) {
        if (paramBoolean3) {
          i = this.mSecondaryPcLayoutResId;
        }
      }
      for (;;)
      {
        j = -1;
        break;
        i = this.mSecondaryLayoutResId;
        continue;
        if (paramBoolean3) {
          i = this.mPcLayoutResId;
        } else {
          i = this.mLayoutResId;
        }
      }
    }
    
    public String getLayoutLanguagesList()
    {
      return this.mLayoutLanguagesList;
    }
    
    public String getLayoutName()
    {
      return this.mLayoutName;
    }
    
    public int getLayoutResId(boolean paramBoolean)
    {
      if (paramBoolean) {
        return this.mPcLayoutResId;
      }
      return this.mLayoutResId;
    }
    
    public int getNameResourceId()
    {
      return this.mNameResourceId;
    }
    
    public int getSecondaryCompactLayoutResId()
    {
      return this.mSecondaryCompactLayoutResId;
    }
    
    public int getSecondaryLayoutResId(boolean paramBoolean)
    {
      if (paramBoolean) {
        return this.mSecondaryPcLayoutResId;
      }
      return this.mSecondaryLayoutResId;
    }
    
    public int getSecondarySplitLayoutResId(boolean paramBoolean)
    {
      if (paramBoolean) {
        return this.mSecondarySplitNumpadLayoutResId;
      }
      return this.mSecondarySplitLayoutResId;
    }
    
    public int getSecondarySplitLeftLayoutResId()
    {
      return this.mSecondarySplitLeftLayoutResId;
    }
    
    public int getSecondarySplitRightLayoutResId()
    {
      return this.mSecondarySplitRightLayoutResId;
    }
    
    public int getSplitLayoutResId(boolean paramBoolean)
    {
      if (paramBoolean) {
        return this.mSplitNumpadLayoutResId;
      }
      return this.mSplitLayoutResId;
    }
    
    public int getSplitLeftLayoutResId()
    {
      return this.mSplitLeftLayoutResId;
    }
    
    public int getSplitRightLayoutResId()
    {
      return this.mSplitRightLayoutResId;
    }
    
    public LayoutMap getSymbolsAltLayout()
    {
      return this.mSymbolsAltLayout;
    }
    
    public LayoutMap getSymbolsLayout()
    {
      return this.mSymbolsLayout;
    }
    
    public boolean isLayoutSelectable()
    {
      return (this.mLatinState != LayoutData.LatinState.PROVIDES_EXTENDED_LATIN_UNSELECTABLE) && (this.mLatinState != LayoutData.LatinState.PROVIDES_UNEXTENDED_LATIN_UNSELECTABLE);
    }
    
    public boolean providesLatin()
    {
      return (this.mLatinState == LayoutData.LatinState.PROVIDES_UNEXTENDED_LATIN) || (this.mLatinState == LayoutData.LatinState.PROVIDES_UNEXTENDED_LATIN_UNSELECTABLE) || (this.mLatinState == LayoutData.LatinState.PROVIDES_EXTENDED_LATIN) || (this.mLatinState == LayoutData.LatinState.PROVIDES_EXTENDED_LATIN_UNSELECTABLE);
    }
    
    public String toString()
    {
      return this.mLayoutName + " [enum: " + name() + " " + this.mLatinState + "]";
    }
    
    public String toString(Context paramContext)
    {
      return paramContext.getString(this.mNameResourceId);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.languagepacks.layouts.LayoutData
 * JD-Core Version:    0.7.0.1
 */