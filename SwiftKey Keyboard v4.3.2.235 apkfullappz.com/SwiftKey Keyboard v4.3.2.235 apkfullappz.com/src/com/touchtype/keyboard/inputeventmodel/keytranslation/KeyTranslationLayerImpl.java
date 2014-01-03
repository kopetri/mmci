package com.touchtype.keyboard.inputeventmodel.keytranslation;

import android.content.Context;
import android.util.SparseArray;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.touchtype.preferences.SwiftKeyPreferences;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class KeyTranslationLayerImpl
  implements KeyTranslationLayer
{
  private static final int COMBINING_ACUTE;
  private static final int COMBINING_CEDILLA;
  private static final int COMBINING_CIRCUMFLEX;
  private static final int COMBINING_GRAVE;
  private static final int COMBINING_TILDE;
  private static final int COMBINING_UMLAUT;
  public static final Map<Integer, String> TRANSLATOR_LAYOUT;
  private int mCurrentTranslator = 0;
  private SwiftKeyPreferences mPreferences;
  private final SparseArray<List<EventTranslator>> mTranslationTable = new SparseArray();
  
  static
  {
    if (!KeyTranslationLayerImpl.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      COMBINING_GRAVE = makeCombining('`');
      COMBINING_ACUTE = makeCombining('´');
      COMBINING_UMLAUT = makeCombining('¨');
      COMBINING_CIRCUMFLEX = makeCombining('^');
      COMBINING_TILDE = makeCombining('~');
      COMBINING_CEDILLA = makeCombining('¸');
      TRANSLATOR_LAYOUT = ImmutableMap.builder().put(Integer.valueOf(0), "en_US").put(Integer.valueOf(1), "en_GB").put(Integer.valueOf(2), "it_IT").put(Integer.valueOf(3), "es_ES").put(Integer.valueOf(4), "de_DE").put(Integer.valueOf(5), "fr_FR").put(Integer.valueOf(6), "fr_CA").put(Integer.valueOf(7), "pt_PT").put(Integer.valueOf(8), "pt_BR").put(Integer.valueOf(9), "sv_SE").put(Integer.valueOf(10), "nl_NL").put(Integer.valueOf(11), "nb_NO").build();
      return;
    }
  }
  
  public KeyTranslationLayerImpl(SwiftKeyPreferences paramSwiftKeyPreferences)
  {
    this.mPreferences = paramSwiftKeyPreferences;
  }
  
  private void addTranslator(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    addTranslator(paramInt1, new EventTranslator(new MetaMatcher(paramInt2, paramInt3), paramInt4));
  }
  
  private void addTranslator(int paramInt, EventTranslator paramEventTranslator)
  {
    Object localObject = (List)this.mTranslationTable.get(paramInt);
    if (localObject == null) {
      localObject = new ArrayList();
    }
    ((List)localObject).add(paramEventTranslator);
    this.mTranslationTable.put(paramInt, localObject);
  }
  
  private int getDefaultUnicode(KeyTranslationLayer.TranslationKeyEvent paramTranslationKeyEvent, int paramInt)
  {
    return paramTranslationKeyEvent.getUnicodeChar(paramInt | paramTranslationKeyEvent.getMetaState());
  }
  
  private static int makeCombining(char paramChar)
  {
    return 0x80000000 | paramChar;
  }
  
  private void reloadIfPreferenceChanged()
  {
    int i = this.mPreferences.getHardKeyboardLayout();
    if (this.mCurrentTranslator != i)
    {
      loadTranslator(i);
      this.mCurrentTranslator = i;
    }
  }
  
  public int getUnicodeChar(KeyTranslationLayer.TranslationKeyEvent paramTranslationKeyEvent, int paramInt)
  {
    reloadIfPreferenceChanged();
    List localList = (List)this.mTranslationTable.get(paramTranslationKeyEvent.getKeyCode());
    int i = getDefaultUnicode(paramTranslationKeyEvent, paramInt);
    if (localList == null) {}
    EventTranslator localEventTranslator;
    do
    {
      Iterator localIterator;
      while (!localIterator.hasNext())
      {
        return i;
        localIterator = localList.iterator();
      }
      localEventTranslator = (EventTranslator)localIterator.next();
    } while (!localEventTranslator.metaMatcher.matches(paramTranslationKeyEvent));
    if (paramTranslationKeyEvent.isCapsLockOn()) {
      return Character.toUpperCase(localEventTranslator.unicodeChar);
    }
    return localEventTranslator.unicodeChar;
  }
  
  public void loadTranslator(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      if (!$assertionsDisabled) {
        throw new AssertionError();
      }
    case 0: 
      setEnglishUSTranslator();
      return;
    case 1: 
      setEnglishGBTranslator();
      return;
    case 2: 
      setItalianTranslator();
      return;
    case 3: 
      setSpanishTranslator();
      return;
    case 4: 
      setGermanTranslator();
      return;
    case 5: 
      setFrenchTranslator();
      return;
    case 6: 
      setFrenchCanadianTranslator();
      return;
    case 7: 
      setPortugueseTranslator();
      return;
    case 8: 
      setPortugueseBrazilianTranslator();
      return;
    case 9: 
      setSwedishTranslator();
      return;
    case 10: 
      setDutchTranslator();
      return;
    }
    setNorwegianTranslator();
  }
  
  public void onCreate(Context paramContext)
  {
    loadTranslator(this.mPreferences.getHardKeyboardLayout());
  }
  
  public void setDutchTranslator()
  {
    this.mTranslationTable.clear();
    addTranslator(68, 33, 0, 64);
    addTranslator(68, 33, 1, 167);
    addTranslator(68, 33, 32, 172);
    addTranslator(8, 33, 32, 185);
    addTranslator(9, 33, 1, 34);
    addTranslator(9, 33, 32, 178);
    addTranslator(10, 33, 32, 179);
    addTranslator(11, 33, 32, 188);
    addTranslator(12, 33, 32, 189);
    addTranslator(13, 33, 1, 38);
    addTranslator(13, 33, 32, 190);
    addTranslator(14, 33, 1, 95);
    addTranslator(14, 33, 32, 163);
    addTranslator(15, 33, 1, 40);
    addTranslator(15, 33, 32, 123);
    addTranslator(16, 33, 1, 41);
    addTranslator(16, 33, 32, 125);
    addTranslator(7, 33, 1, 39);
    addTranslator(69, 33, 0, 47);
    addTranslator(69, 33, 1, 63);
    addTranslator(69, 33, 32, 92);
    addTranslator(70, 33, 0, 176);
    addTranslator(70, 33, 1, COMBINING_TILDE);
    addTranslator(70, 33, 32, COMBINING_CEDILLA);
    addTranslator(33, 33, 32, 8364);
    addTranslator(46, 33, 32, 182);
    addTranslator(47, 33, 32, 223);
    addTranslator(54, 33, 32, 171);
    addTranslator(52, 33, 32, 187);
    addTranslator(31, 33, 32, 162);
    addTranslator(41, 33, 32, 181);
    addTranslator(71, 33, 0, COMBINING_UMLAUT);
    addTranslator(71, 33, 1, COMBINING_CIRCUMFLEX);
    addTranslator(72, 33, 0, 42);
    addTranslator(72, 33, 1, 124);
    addTranslator(74, 33, 0, 43);
    addTranslator(74, 33, 1, 177);
    addTranslator(75, 33, 0, COMBINING_ACUTE);
    addTranslator(75, 33, 1, COMBINING_GRAVE);
    addTranslator(73, 33, 0, 60);
    addTranslator(73, 33, 1, 62);
    addTranslator(500, 33, 0, 93);
    addTranslator(500, 33, 1, 91);
    addTranslator(500, 33, 32, 124);
    addTranslator(55, 33, 1, 59);
    addTranslator(56, 33, 1, 58);
    addTranslator(56, 33, 32, 183);
    addTranslator(76, 33, 0, 45);
    addTranslator(76, 33, 1, 61);
  }
  
  public void setEnglishGBTranslator()
  {
    this.mTranslationTable.clear();
    addTranslator(68, 33, 0, 96);
    addTranslator(68, 33, 1, 172);
    addTranslator(68, 33, 32, 124);
    addTranslator(9, 1, 1, 34);
    addTranslator(10, 1, 1, 163);
    addTranslator(11, 32, 32, 8364);
    addTranslator(75, 1, 1, 64);
    addTranslator(73, 1, 0, 35);
    addTranslator(73, 1, 1, 126);
    addTranslator(500, 1, 0, 92);
    addTranslator(500, 1, 1, 124);
  }
  
  public void setEnglishUSTranslator()
  {
    this.mTranslationTable.clear();
  }
  
  public void setFrenchCanadianTranslator()
  {
    this.mTranslationTable.clear();
    addTranslator(68, 33, 0, 35);
    addTranslator(68, 33, 1, 124);
    addTranslator(68, 33, 32, 92);
    addTranslator(68, 33, 33, 126);
    addTranslator(8, 33, 1, 33);
    addTranslator(8, 33, 32, 177);
    addTranslator(9, 33, 1, 34);
    addTranslator(9, 33, 32, 64);
    addTranslator(10, 33, 32, 163);
    addTranslator(10, 33, 1, 47);
    addTranslator(11, 33, 32, 162);
    addTranslator(11, 33, 1, 36);
    addTranslator(12, 33, 32, 164);
    addTranslator(12, 33, 1, 37);
    addTranslator(13, 33, 32, 172);
    addTranslator(13, 33, 1, 63);
    addTranslator(13, 33, 33, 94);
    addTranslator(14, 33, 1, 38);
    addTranslator(14, 33, 32, 166);
    addTranslator(15, 33, 32, 178);
    addTranslator(15, 33, 1, 42);
    addTranslator(16, 33, 32, 179);
    addTranslator(16, 33, 1, 40);
    addTranslator(7, 33, 32, 188);
    addTranslator(7, 33, 1, 41);
    addTranslator(69, 33, 0, 45);
    addTranslator(69, 33, 1, 95);
    addTranslator(69, 33, 32, 189);
    addTranslator(70, 33, 0, 61);
    addTranslator(70, 33, 1, 43);
    addTranslator(70, 33, 32, 190);
    addTranslator(43, 32, 32, 167);
    addTranslator(44, 32, 32, 182);
    addTranslator(71, 33, 0, COMBINING_CIRCUMFLEX);
    addTranslator(71, 33, 32, 91);
    addTranslator(72, 33, 0, COMBINING_CEDILLA);
    addTranslator(72, 33, 32, 93);
    addTranslator(72, 33, 1, COMBINING_UMLAUT);
    addTranslator(74, 33, 0, 59);
    addTranslator(74, 33, 1, 58);
    addTranslator(74, 33, 32, COMBINING_TILDE);
    addTranslator(75, 32, 0, COMBINING_GRAVE);
    addTranslator(75, 32, 32, 123);
    addTranslator(73, 33, 0, 60);
    addTranslator(73, 1, 1, 62);
    addTranslator(73, 32, 32, 125);
    addTranslator(500, 33, 0, 171);
    addTranslator(500, 1, 1, 187);
    addTranslator(500, 32, 32, 176);
    addTranslator(41, 32, 32, 181);
    addTranslator(55, 1, 1, 39);
    addTranslator(55, 32, 32, 175);
    addTranslator(56, 1, 1, 46);
    addTranslator(56, 32, 32, 45);
    addTranslator(76, 33, 0, 233);
    addTranslator(76, 33, 1, 201);
    addTranslator(76, 33, 32, COMBINING_ACUTE);
  }
  
  public void setFrenchTranslator()
  {
    this.mTranslationTable.clear();
    addTranslator(68, 1, 0, 178);
    addTranslator(68, 1, 1, 126);
    addTranslator(8, 1, 0, 38);
    addTranslator(8, 1, 1, 49);
    addTranslator(9, 33, 0, 233);
    addTranslator(9, 33, 1, 50);
    addTranslator(9, 33, 32, 126);
    addTranslator(10, 33, 0, 34);
    addTranslator(10, 33, 1, 51);
    addTranslator(10, 33, 32, 35);
    addTranslator(11, 33, 0, 39);
    addTranslator(11, 33, 1, 52);
    addTranslator(11, 33, 32, 123);
    addTranslator(12, 33, 0, 40);
    addTranslator(12, 33, 1, 53);
    addTranslator(12, 33, 32, 91);
    addTranslator(13, 33, 0, 45);
    addTranslator(13, 33, 1, 54);
    addTranslator(13, 33, 32, 124);
    addTranslator(14, 33, 0, 232);
    addTranslator(14, 33, 1, 55);
    addTranslator(14, 33, 32, 96);
    addTranslator(15, 33, 0, 95);
    addTranslator(15, 33, 1, 56);
    addTranslator(15, 33, 32, 92);
    addTranslator(16, 33, 0, 231);
    addTranslator(16, 33, 1, 57);
    addTranslator(16, 33, 32, 94);
    addTranslator(7, 33, 0, 224);
    addTranslator(7, 33, 1, 48);
    addTranslator(7, 33, 32, 64);
    addTranslator(69, 33, 0, 41);
    addTranslator(69, 33, 1, 176);
    addTranslator(69, 33, 32, 93);
    addTranslator(70, 33, 32, 125);
    addTranslator(45, 1, 0, 97);
    addTranslator(45, 1, 1, 65);
    addTranslator(51, 1, 0, 122);
    addTranslator(51, 1, 1, 90);
    addTranslator(33, 32, 32, 8364);
    addTranslator(71, 1, 0, COMBINING_CIRCUMFLEX);
    addTranslator(71, 1, 1, COMBINING_UMLAUT);
    addTranslator(72, 33, 0, 36);
    addTranslator(72, 33, 1, 163);
    addTranslator(72, 33, 32, 164);
    addTranslator(29, 1, 0, 113);
    addTranslator(29, 1, 1, 81);
    addTranslator(74, 1, 0, 109);
    addTranslator(74, 1, 1, 77);
    addTranslator(75, 1, 0, 249);
    addTranslator(75, 1, 1, 37);
    addTranslator(73, 1, 0, 42);
    addTranslator(73, 1, 1, 181);
    addTranslator(500, 1, 0, 60);
    addTranslator(500, 1, 1, 62);
    addTranslator(54, 1, 0, 119);
    addTranslator(54, 1, 1, 87);
    addTranslator(41, 33, 0, 44);
    addTranslator(41, 33, 1, 63);
    addTranslator(41, 33, 32, COMBINING_ACUTE);
    addTranslator(55, 1, 0, 59);
    addTranslator(55, 1, 1, 46);
    addTranslator(56, 33, 0, 58);
    addTranslator(56, 33, 1, 47);
    addTranslator(56, 33, 32, 183);
    addTranslator(56, 33, 33, 37);
    addTranslator(76, 1, 0, 33);
    addTranslator(76, 1, 1, 167);
  }
  
  public void setGermanTranslator()
  {
    this.mTranslationTable.clear();
    addTranslator(68, 1, 0, COMBINING_CIRCUMFLEX);
    addTranslator(68, 1, 1, 176);
    addTranslator(9, 1, 1, 34);
    addTranslator(9, 32, 32, 178);
    addTranslator(10, 1, 1, 167);
    addTranslator(10, 32, 32, 179);
    addTranslator(10, 1, 1, 167);
    addTranslator(13, 1, 1, 38);
    addTranslator(14, 1, 1, 47);
    addTranslator(14, 32, 32, 123);
    addTranslator(15, 1, 1, 40);
    addTranslator(15, 32, 32, 91);
    addTranslator(16, 1, 1, 41);
    addTranslator(16, 32, 32, 93);
    addTranslator(7, 1, 1, 61);
    addTranslator(7, 32, 32, 125);
    addTranslator(69, 33, 0, 223);
    addTranslator(69, 1, 1, 63);
    addTranslator(69, 32, 32, 92);
    addTranslator(70, 33, 0, COMBINING_ACUTE);
    addTranslator(70, 33, 1, COMBINING_GRAVE);
    addTranslator(70, 33, 32, COMBINING_CIRCUMFLEX);
    addTranslator(45, 32, 32, 64);
    addTranslator(33, 32, 32, 8364);
    addTranslator(53, 1, 0, 122);
    addTranslator(53, 1, 1, 90);
    addTranslator(71, 33, 0, 252);
    addTranslator(71, 33, 1, 220);
    addTranslator(71, 33, 32, COMBINING_UMLAUT);
    addTranslator(72, 33, 0, 43);
    addTranslator(72, 33, 1, 42);
    addTranslator(72, 33, 32, COMBINING_TILDE);
    addTranslator(74, 1, 0, 246);
    addTranslator(74, 1, 1, 214);
    addTranslator(75, 1, 0, 228);
    addTranslator(75, 1, 1, 196);
    addTranslator(73, 1, 0, 35);
    addTranslator(73, 1, 1, 39);
    addTranslator(500, 33, 0, 60);
    addTranslator(500, 33, 1, 62);
    addTranslator(500, 33, 32, 124);
    addTranslator(54, 1, 0, 121);
    addTranslator(54, 1, 1, 89);
    addTranslator(41, 32, 32, 181);
    addTranslator(55, 1, 1, 59);
    addTranslator(56, 1, 1, 58);
    addTranslator(76, 1, 0, 45);
    addTranslator(76, 1, 1, 95);
  }
  
  public void setItalianTranslator()
  {
    this.mTranslationTable.clear();
    addTranslator(68, 1, 0, 92);
    addTranslator(68, 1, 1, 124);
    addTranslator(9, 1, 1, 34);
    addTranslator(10, 1, 1, 163);
    addTranslator(13, 1, 1, 38);
    addTranslator(14, 1, 1, 47);
    addTranslator(15, 1, 1, 40);
    addTranslator(16, 1, 1, 41);
    addTranslator(7, 1, 1, 61);
    addTranslator(69, 1, 0, 39);
    addTranslator(69, 1, 1, 63);
    addTranslator(70, 1, 0, 236);
    addTranslator(70, 1, 1, 94);
    addTranslator(33, 32, 32, 8364);
    addTranslator(71, 33, 0, 232);
    addTranslator(71, 33, 1, 233);
    addTranslator(71, 33, 32, 91);
    addTranslator(71, 33, 33, 123);
    addTranslator(72, 33, 0, 43);
    addTranslator(72, 33, 1, 42);
    addTranslator(72, 33, 32, 93);
    addTranslator(72, 33, 33, 125);
    addTranslator(74, 33, 0, 242);
    addTranslator(74, 33, 1, 231);
    addTranslator(74, 33, 32, 64);
    addTranslator(75, 33, 0, 224);
    addTranslator(75, 33, 1, 176);
    addTranslator(75, 33, 32, 35);
    addTranslator(73, 33, 0, 249);
    addTranslator(73, 33, 1, 167);
    addTranslator(73, 33, 32, COMBINING_GRAVE);
    addTranslator(500, 1, 0, 60);
    addTranslator(500, 1, 1, 62);
    addTranslator(55, 1, 1, 59);
    addTranslator(56, 1, 1, 58);
    addTranslator(76, 1, 0, 45);
    addTranslator(76, 1, 1, 95);
  }
  
  public void setNorwegianTranslator()
  {
    this.mTranslationTable.clear();
    addTranslator(68, 33, 0, 124);
    addTranslator(68, 33, 1, 167);
    addTranslator(9, 33, 1, 34);
    addTranslator(9, 33, 32, 64);
    addTranslator(10, 33, 32, 163);
    addTranslator(11, 33, 1, 164);
    addTranslator(11, 33, 32, 36);
    addTranslator(12, 33, 32, 8364);
    addTranslator(13, 33, 1, 38);
    addTranslator(14, 33, 1, 47);
    addTranslator(14, 33, 32, 123);
    addTranslator(15, 33, 1, 40);
    addTranslator(15, 33, 32, 91);
    addTranslator(16, 33, 1, 41);
    addTranslator(16, 33, 32, 93);
    addTranslator(7, 33, 1, 61);
    addTranslator(7, 33, 32, 125);
    addTranslator(69, 33, 0, 43);
    addTranslator(69, 33, 1, 63);
    addTranslator(70, 33, 0, 92);
    addTranslator(70, 33, 1, COMBINING_GRAVE);
    addTranslator(70, 33, 32, COMBINING_ACUTE);
    addTranslator(33, 33, 32, 8364);
    addTranslator(41, 33, 32, 181);
    addTranslator(71, 33, 0, 229);
    addTranslator(71, 33, 1, 197);
    addTranslator(72, 33, 0, COMBINING_UMLAUT);
    addTranslator(72, 33, 1, COMBINING_CIRCUMFLEX);
    addTranslator(72, 33, 32, COMBINING_TILDE);
    addTranslator(74, 33, 0, 248);
    addTranslator(74, 33, 1, 216);
    addTranslator(75, 33, 0, 230);
    addTranslator(75, 33, 1, 198);
    addTranslator(73, 33, 0, 39);
    addTranslator(73, 33, 1, 42);
    addTranslator(500, 33, 0, 60);
    addTranslator(500, 33, 1, 62);
    addTranslator(55, 33, 1, 59);
    addTranslator(56, 33, 1, 58);
    addTranslator(76, 33, 0, 45);
    addTranslator(76, 33, 1, 95);
  }
  
  public void setPortugueseBrazilianTranslator()
  {
    this.mTranslationTable.clear();
    addTranslator(68, 33, 0, 39);
    addTranslator(68, 33, 1, 34);
    addTranslator(8, 33, 32, 185);
    addTranslator(9, 33, 32, 178);
    addTranslator(10, 33, 32, 179);
    addTranslator(11, 33, 32, 163);
    addTranslator(12, 33, 32, 162);
    addTranslator(13, 33, 1, COMBINING_UMLAUT);
    addTranslator(13, 33, 32, 172);
    addTranslator(70, 33, 32, 167);
    addTranslator(45, 33, 32, 47);
    addTranslator(51, 33, 32, 63);
    addTranslator(33, 33, 32, 8364);
    addTranslator(31, 33, 32, 8354);
    addTranslator(71, 33, 0, COMBINING_ACUTE);
    addTranslator(71, 33, 1, COMBINING_GRAVE);
    addTranslator(72, 33, 0, 91);
    addTranslator(72, 33, 1, 123);
    addTranslator(72, 33, 32, 170);
    addTranslator(74, 33, 0, 231);
    addTranslator(74, 33, 1, 199);
    addTranslator(75, 33, 0, COMBINING_TILDE);
    addTranslator(75, 33, 1, COMBINING_CIRCUMFLEX);
    addTranslator(73, 33, 0, 93);
    addTranslator(73, 33, 1, 125);
    addTranslator(73, 33, 32, 186);
    addTranslator(500, 33, 0, 92);
    addTranslator(500, 33, 1, 124);
    addTranslator(76, 33, 0, 59);
    addTranslator(76, 33, 1, 58);
  }
  
  public void setPortugueseTranslator()
  {
    this.mTranslationTable.clear();
    addTranslator(68, 33, 0, 92);
    addTranslator(68, 33, 1, 124);
    addTranslator(9, 33, 1, 34);
    addTranslator(9, 33, 32, 64);
    addTranslator(10, 33, 32, 163);
    addTranslator(11, 33, 32, 167);
    addTranslator(13, 33, 1, 38);
    addTranslator(14, 33, 1, 47);
    addTranslator(14, 33, 32, 123);
    addTranslator(15, 33, 1, 40);
    addTranslator(15, 33, 32, 91);
    addTranslator(16, 33, 1, 41);
    addTranslator(16, 33, 32, 93);
    addTranslator(7, 33, 1, 61);
    addTranslator(7, 33, 32, 125);
    addTranslator(69, 33, 0, 45);
    addTranslator(69, 33, 1, 63);
    addTranslator(70, 33, 0, 171);
    addTranslator(70, 33, 1, 187);
    addTranslator(33, 32, 32, 8364);
    addTranslator(71, 33, 0, 43);
    addTranslator(71, 33, 1, 42);
    addTranslator(71, 33, 32, COMBINING_UMLAUT);
    addTranslator(72, 33, 0, COMBINING_ACUTE);
    addTranslator(72, 33, 1, COMBINING_GRAVE);
    addTranslator(74, 1, 0, 231);
    addTranslator(74, 1, 1, 199);
    addTranslator(75, 33, 0, 186);
    addTranslator(75, 33, 1, 170);
    addTranslator(73, 33, 0, COMBINING_TILDE);
    addTranslator(73, 33, 1, COMBINING_CIRCUMFLEX);
    addTranslator(500, 33, 0, 60);
    addTranslator(500, 33, 1, 62);
    addTranslator(500, 33, 32, 92);
    addTranslator(55, 33, 1, 59);
    addTranslator(56, 33, 1, 58);
    addTranslator(76, 33, 0, 45);
    addTranslator(76, 33, 1, 95);
  }
  
  public void setSpanishTranslator()
  {
    this.mTranslationTable.clear();
    addTranslator(68, 33, 0, 186);
    addTranslator(68, 33, 1, 170);
    addTranslator(68, 32, 32, 92);
    addTranslator(8, 33, 32, 124);
    addTranslator(8, 33, 33, 161);
    addTranslator(9, 33, 1, 34);
    addTranslator(9, 33, 32, 64);
    addTranslator(10, 33, 1, 183);
    addTranslator(10, 33, 32, 35);
    addTranslator(10, 33, 33, 163);
    addTranslator(11, 1, 1, 36);
    addTranslator(11, 33, 32, 126);
    addTranslator(12, 33, 32, 8364);
    addTranslator(13, 33, 1, 38);
    addTranslator(13, 33, 32, 172);
    addTranslator(14, 1, 1, 47);
    addTranslator(15, 1, 1, 40);
    addTranslator(16, 1, 1, 41);
    addTranslator(7, 1, 1, 61);
    addTranslator(69, 1, 0, 39);
    addTranslator(69, 1, 1, 63);
    addTranslator(70, 1, 0, 161);
    addTranslator(70, 1, 1, 191);
    addTranslator(33, 32, 32, 8364);
    addTranslator(71, 33, 0, COMBINING_GRAVE);
    addTranslator(71, 33, 1, COMBINING_CIRCUMFLEX);
    addTranslator(71, 33, 32, 91);
    addTranslator(72, 33, 0, 43);
    addTranslator(72, 33, 32, 93);
    addTranslator(72, 33, 1, 42);
    addTranslator(74, 1, 0, 241);
    addTranslator(74, 1, 1, 209);
    addTranslator(75, 33, 0, COMBINING_ACUTE);
    addTranslator(75, 33, 1, COMBINING_UMLAUT);
    addTranslator(75, 33, 32, 123);
    addTranslator(73, 33, 0, 231);
    addTranslator(73, 33, 1, 199);
    addTranslator(73, 33, 32, 125);
    addTranslator(500, 1, 0, 60);
    addTranslator(500, 1, 1, 62);
    addTranslator(55, 1, 1, 59);
    addTranslator(56, 1, 1, 58);
    addTranslator(76, 1, 0, 45);
    addTranslator(76, 1, 1, 95);
  }
  
  public void setSwedishTranslator()
  {
    this.mTranslationTable.clear();
    addTranslator(68, 33, 0, 167);
    addTranslator(68, 33, 1, 189);
    addTranslator(9, 33, 1, 34);
    addTranslator(9, 33, 32, 64);
    addTranslator(10, 33, 32, 163);
    addTranslator(11, 33, 1, 164);
    addTranslator(11, 33, 32, 36);
    addTranslator(12, 33, 32, 8364);
    addTranslator(13, 33, 1, 38);
    addTranslator(14, 33, 1, 47);
    addTranslator(14, 33, 32, 123);
    addTranslator(15, 33, 1, 40);
    addTranslator(15, 33, 32, 91);
    addTranslator(16, 33, 1, 41);
    addTranslator(16, 33, 32, 93);
    addTranslator(7, 33, 1, 61);
    addTranslator(7, 33, 32, 125);
    addTranslator(69, 33, 0, 43);
    addTranslator(69, 33, 1, 63);
    addTranslator(69, 33, 32, 92);
    addTranslator(70, 33, 0, COMBINING_ACUTE);
    addTranslator(70, 33, 1, COMBINING_GRAVE);
    addTranslator(33, 33, 32, 8364);
    addTranslator(41, 33, 32, 181);
    addTranslator(71, 33, 0, 229);
    addTranslator(71, 33, 1, 197);
    addTranslator(72, 33, 0, COMBINING_UMLAUT);
    addTranslator(72, 33, 1, COMBINING_CIRCUMFLEX);
    addTranslator(72, 33, 32, COMBINING_TILDE);
    addTranslator(74, 33, 0, 246);
    addTranslator(74, 33, 1, 214);
    addTranslator(75, 33, 0, 228);
    addTranslator(75, 33, 1, 196);
    addTranslator(73, 33, 0, 39);
    addTranslator(73, 33, 1, 42);
    addTranslator(500, 33, 0, 60);
    addTranslator(500, 33, 1, 62);
    addTranslator(500, 33, 32, 124);
    addTranslator(55, 33, 1, 59);
    addTranslator(56, 33, 1, 58);
    addTranslator(76, 33, 0, 45);
    addTranslator(76, 33, 1, 95);
  }
  
  public final class EventTranslator
  {
    public final KeyTranslationLayerImpl.MetaMatcher metaMatcher;
    public final int unicodeChar;
    
    public EventTranslator(KeyTranslationLayerImpl.MetaMatcher paramMetaMatcher, int paramInt)
    {
      this.metaMatcher = paramMetaMatcher;
      this.unicodeChar = paramInt;
    }
  }
  
  public final class MetaMatcher
  {
    public final int mMetaMask;
    public final int mMetaState;
    
    public MetaMatcher(int paramInt1, int paramInt2)
    {
      this.mMetaMask = paramInt1;
      this.mMetaState = paramInt2;
    }
    
    public boolean matches(KeyTranslationLayer.TranslationKeyEvent paramTranslationKeyEvent)
    {
      return (paramTranslationKeyEvent.getMetaState() & this.mMetaMask) == this.mMetaState;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.keytranslation.KeyTranslationLayerImpl
 * JD-Core Version:    0.7.0.1
 */