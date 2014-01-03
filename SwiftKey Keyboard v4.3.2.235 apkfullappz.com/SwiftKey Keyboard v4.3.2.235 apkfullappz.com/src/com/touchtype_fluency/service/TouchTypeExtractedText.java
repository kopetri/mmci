package com.touchtype_fluency.service;

import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.view.inputmethod.ExtractedText;
import com.google.common.collect.Range;
import com.google.common.collect.Ranges;
import com.touchtype.util.LogUtil;
import com.touchtype_fluency.Sequence;
import com.touchtype_fluency.Sequence.Type;
import com.touchtype_fluency.WordBreakIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TouchTypeExtractedText
  extends ExtractedText
  implements ImmutableExtractedText
{
  private static final int ZERO_WIDTH_SPACE = 8203;
  private static final Pattern lastWordRegex = Pattern.compile("((\\p{Sc}|\\p{Punct}+|[\\S&&[^\\p{Sc}\\p{Punct}\\u200b]']+)|\\r\\n|\\n|\\r|\\u0085|\\u2028|\\u2029)( |\t|\\u00a0|\\u200b)*\\z");
  private TokenizationProvider.ContextCurrentWord mCachedCurrentWord = null;
  private final TokenizationProvider mTokenizationProvider;
  private CharSequence mTokenizedText = null;
  private int mTokenizedTextCursorStart = 0;
  
  public TouchTypeExtractedText(ExtractedText paramExtractedText, TokenizationProvider paramTokenizationProvider)
  {
    if (paramExtractedText == null)
    {
      this.flags = 0;
      this.partialEndOffset = -1;
      this.partialStartOffset = -1;
      this.selectionEnd = -1;
      this.selectionStart = -1;
      this.startOffset = 0;
    }
    for (this.text = "";; this.text = paramExtractedText.text)
    {
      this.mTokenizationProvider = paramTokenizationProvider;
      return;
      this.flags = paramExtractedText.flags;
      this.partialEndOffset = paramExtractedText.partialEndOffset;
      this.partialStartOffset = paramExtractedText.partialStartOffset;
      this.selectionEnd = paramExtractedText.selectionEnd;
      this.selectionStart = paramExtractedText.selectionStart;
      this.startOffset = paramExtractedText.startOffset;
    }
  }
  
  public static void copyCursorPosition(ExtractedText paramExtractedText1, ExtractedText paramExtractedText2)
  {
    paramExtractedText2.partialStartOffset = paramExtractedText1.partialStartOffset;
    paramExtractedText2.partialEndOffset = paramExtractedText1.partialEndOffset;
    paramExtractedText2.selectionStart = paramExtractedText1.selectionStart;
    paramExtractedText2.selectionEnd = paramExtractedText1.selectionEnd;
    paramExtractedText2.startOffset = paramExtractedText1.startOffset;
  }
  
  private void ensureValidSelection()
  {
    if (this.selectionStart < 0) {
      this.selectionStart = 0;
    }
    if (this.selectionEnd < 0) {
      this.selectionEnd = 0;
    }
  }
  
  private char getCharAtIndexInText(int paramInt)
  {
    if ((paramInt >= 0) && (paramInt < this.text.length())) {
      return this.text.charAt(paramInt);
    }
    return '\000';
  }
  
  private TokenizationProvider.ContextCurrentWord getContextCurrentWord()
  {
    if (!isCacheValid())
    {
      int i = getSelectionStartInText();
      if ((this.text == null) || (i < 0) || (i > this.text.length()) || (this.text.length() == 0)) {
        break label104;
      }
      this.mCachedCurrentWord = this.mTokenizationProvider.getContextCurrentWord(this.text.toString().substring(0, i));
      this.mCachedCurrentWord.getContext().setType(Sequence.Type.MESSAGE_START);
      this.mTokenizedText = this.text;
      this.mTokenizedTextCursorStart = i;
    }
    for (;;)
    {
      return this.mCachedCurrentWord;
      label104:
      this.mCachedCurrentWord = null;
    }
  }
  
  private boolean isCacheValid()
  {
    return (this.mTokenizedText == this.text) && (this.mTokenizedTextCursorStart == getSelectionStartInText()) && (this.mCachedCurrentWord != null);
  }
  
  private boolean isPunctuation(CharSequence paramCharSequence)
  {
    return (paramCharSequence.length() == 1) && (!Character.isLetterOrDigit(paramCharSequence.charAt(0)));
  }
  
  public static boolean isSpace(int paramInt)
  {
    return (paramInt == 32) || (paramInt == 160) || (paramInt == 8203);
  }
  
  private void resetSelectionTo(int paramInt)
  {
    this.selectionEnd = paramInt;
    this.selectionStart = this.selectionEnd;
  }
  
  public int boundsCheckedIndex(int paramInt)
  {
    if (this.text == null) {
      paramInt = 0;
    }
    do
    {
      return paramInt;
      if (paramInt < 0) {
        return 0;
      }
    } while (paramInt <= this.text.length());
    return this.text.length();
  }
  
  public int boundsCheckedOffset()
  {
    if (this.startOffset >= 0) {
      return this.startOffset;
    }
    return 0;
  }
  
  public void boundsCheckedSetSelectionEnd(int paramInt)
  {
    this.selectionEnd = boundsCheckedIndex(paramInt);
  }
  
  public void boundsCheckedSetSelectionStart(int paramInt)
  {
    this.selectionStart = boundsCheckedIndex(paramInt);
  }
  
  public TouchTypeExtractedText deleteCharacters(int paramInt)
  {
    ensureValidSelection();
    SpannableStringBuilder localSpannableStringBuilder = new SpannableStringBuilder(this.text);
    localSpannableStringBuilder.delete(getSelectionEndInText() - paramInt, getSelectionEndInText());
    resetSelectionTo(getSelectionEndInText() - paramInt);
    if ((this.text instanceof SpannableStringBuilder))
    {
      this.text = localSpannableStringBuilder;
      return this;
    }
    if ((this.text instanceof SpannableString))
    {
      this.text = new SpannableString(localSpannableStringBuilder);
      return this;
    }
    this.text = localSpannableStringBuilder.toString();
    return this;
  }
  
  public TouchTypeExtractedText deleteCurrentWord()
  {
    return setCurrentWord("");
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    TouchTypeExtractedText localTouchTypeExtractedText;
    do
    {
      return true;
      if (!(paramObject instanceof TouchTypeExtractedText)) {
        return false;
      }
      localTouchTypeExtractedText = (TouchTypeExtractedText)paramObject;
    } while ((this.flags == localTouchTypeExtractedText.flags) && (this.partialEndOffset == localTouchTypeExtractedText.partialEndOffset) && (this.partialStartOffset == localTouchTypeExtractedText.partialStartOffset) && (this.selectionEnd == localTouchTypeExtractedText.selectionEnd) && (this.selectionStart == localTouchTypeExtractedText.selectionStart) && (this.startOffset == localTouchTypeExtractedText.startOffset) && (((this.text == null) && ((localTouchTypeExtractedText.text == null) || (localTouchTypeExtractedText.text.length() == 0))) || ((this.text != null) && (this.text.equals(localTouchTypeExtractedText.text)))));
    return false;
  }
  
  public char getCharAtIndexInField(int paramInt)
  {
    return getCharAtIndexInText(paramInt - this.startOffset);
  }
  
  public Sequence getContext()
  {
    TokenizationProvider.ContextCurrentWord localContextCurrentWord = getContextCurrentWord();
    Sequence localSequence;
    if (localContextCurrentWord == null)
    {
      localSequence = new Sequence();
      localSequence.setType(Sequence.Type.MESSAGE_START);
    }
    String str;
    do
    {
      return localSequence;
      localSequence = localContextCurrentWord.getContext();
      str = localContextCurrentWord.getCurrentWord();
    } while (!isPunctuation(str));
    localSequence.append(str);
    return localSequence;
  }
  
  public CharSequence getCurrentWord()
  {
    String str;
    if (this.text == null) {
      str = "";
    }
    do
    {
      return str;
      ensureValidSelection();
      TokenizationProvider.ContextCurrentWord localContextCurrentWord = getContextCurrentWord();
      if (localContextCurrentWord == null) {
        break;
      }
      str = localContextCurrentWord.getCurrentWord();
    } while (!isPunctuation(str));
    return "";
  }
  
  public Range<Integer> getCurrentWordBounds()
  {
    String str = this.text.toString();
    int i = getSelectionStartInText();
    int j = getCurrentWord().length();
    int k = i - j;
    int m = str.codePointCount(0, k);
    int n = str.codePointCount(0, i);
    if (j == 0) {
      return Ranges.openClosed(Integer.valueOf(k), Integer.valueOf(k));
    }
    WordBreakIterator localWordBreakIterator = new WordBreakIterator();
    localWordBreakIterator.setText(this.text.subSequence(k, this.text.length()).toString());
    int i3;
    for (int i1 = 0; i1 + m < n; i1 = i3)
    {
      i3 = localWordBreakIterator.next();
      if (i3 == -1) {
        break;
      }
    }
    int i2 = str.offsetByCodePoints(k, i1);
    return Ranges.openClosed(Integer.valueOf(k), Integer.valueOf(i2));
  }
  
  public char getFirstSelectedCharacter()
  {
    ensureValidSelection();
    return getCharAtIndexInText(getSelectionStartInText());
  }
  
  public char getLastCharacter()
  {
    ensureValidSelection();
    return getCharAtIndexInText(-1 + getSelectionStartInText());
  }
  
  public char getLastNonSpaceCharacter()
  {
    ensureValidSelection();
    char c = ' ';
    for (int i = -1 + getSelectionStartInText(); (isSpace(c)) && (i >= 0); i--) {
      c = this.text.charAt(i);
    }
    if (isSpace(c)) {
      c = '\000';
    }
    return c;
  }
  
  public CharSequence getLastWord()
  {
    ensureValidSelection();
    Object localObject = this.text.subSequence(0, getSelectionStartInText());
    Matcher localMatcher = lastWordRegex.matcher((CharSequence)localObject);
    if (localMatcher.find()) {
      localObject = localMatcher.group();
    }
    return localObject;
  }
  
  public char getNextCharacter()
  {
    ensureValidSelection();
    return getCharAtIndexInText(getSelectionEndInText());
  }
  
  public int getSelectionEndInField()
  {
    int i = getSelectionEndInText();
    if ((this.startOffset >= 0) && (i >= 0)) {
      i += this.startOffset;
    }
    return i;
  }
  
  public int getSelectionEndInText()
  {
    return Math.max(this.selectionStart, this.selectionEnd);
  }
  
  public int getSelectionStartInField()
  {
    int i = getSelectionStartInText();
    if ((this.startOffset >= 0) && (i >= 0)) {
      i += this.startOffset;
    }
    return i;
  }
  
  public int getSelectionStartInText()
  {
    return Math.min(this.selectionStart, this.selectionEnd);
  }
  
  public int getSpacesBeforeCursor()
  {
    ensureValidSelection();
    int i = 0;
    for (int j = -1 + getSelectionStartInText(); (j >= 0) && (isSpace(this.text.charAt(j))); j--) {
      i++;
    }
    return i;
  }
  
  public int getStartOffset()
  {
    return this.startOffset;
  }
  
  public String getText()
  {
    if (this.text == null) {
      return "";
    }
    return this.text.toString();
  }
  
  public int hashCode()
  {
    int i = 31 * (31 * (31 * (31 * (31 * (527 + this.flags) + this.partialEndOffset) + this.partialStartOffset) + this.selectionEnd) + this.selectionStart) + this.startOffset;
    if (this.text != null) {
      i = i * 31 + this.text.toString().hashCode();
    }
    return i;
  }
  
  public TouchTypeExtractedText insertText(CharSequence paramCharSequence)
  {
    SpannableStringBuilder localSpannableStringBuilder = new SpannableStringBuilder(this.text);
    if (getSelectionEndInText() - getSelectionStartInText() != 0)
    {
      localSpannableStringBuilder.delete(Math.max(getSelectionStartInText(), 0), Math.min(getSelectionEndInText(), localSpannableStringBuilder.length()));
      resetSelectionTo(getSelectionStartInText());
    }
    localSpannableStringBuilder.insert(getSelectionEndInText(), paramCharSequence);
    resetSelectionTo(getSelectionStartInText() + paramCharSequence.length());
    if ((this.text instanceof SpannableStringBuilder))
    {
      this.text = localSpannableStringBuilder;
      return this;
    }
    if ((this.text instanceof SpannableString))
    {
      this.text = new SpannableString(localSpannableStringBuilder);
      return this;
    }
    this.text = localSpannableStringBuilder.toString();
    return this;
  }
  
  public boolean isLastCharacterWhitespace()
  {
    char c = getLastCharacter();
    return (Character.isWhitespace(c)) || (isSpace(c));
  }
  
  public boolean isLastCharacterZeroWidthSpace()
  {
    return getLastCharacter() == '​';
  }
  
  public int lengthOfCodePointBeforeIndexInField(int paramInt)
  {
    return UnicodeUtils.lengthOfCodePointBeforeIndex(this.text, paramInt - this.startOffset);
  }
  
  public TouchTypeExtractedText setCurrentWord(CharSequence paramCharSequence)
  {
    ensureValidSelection();
    SpannableStringBuilder localSpannableStringBuilder = new SpannableStringBuilder(this.text);
    int i = getCurrentWord().length();
    int j = getSelectionEndInText();
    int k = localSpannableStringBuilder.length();
    localSpannableStringBuilder.delete(Math.max(j - i, 0), Math.min(j, k));
    if (k - i != localSpannableStringBuilder.length()) {
      LogUtil.e("ARGH", "It's all gone wrong");
    }
    resetSelectionTo(getSelectionEndInText() - i);
    localSpannableStringBuilder.insert(getSelectionEndInText(), paramCharSequence);
    resetSelectionTo(getSelectionEndInText() + paramCharSequence.length());
    if ((this.text instanceof SpannableStringBuilder))
    {
      this.text = localSpannableStringBuilder;
      return this;
    }
    if ((this.text instanceof SpannableString))
    {
      this.text = new SpannableString(localSpannableStringBuilder);
      return this;
    }
    this.text = localSpannableStringBuilder.toString();
    return this;
  }
  
  public String substringInField(int paramInt1, int paramInt2)
  {
    if (this.text == null) {
      return "";
    }
    int i = boundsCheckedIndex(paramInt1 - this.startOffset);
    int j = boundsCheckedIndex(paramInt2 - this.startOffset);
    return this.text.toString().substring(i, j);
  }
  
  public String textBeforeSelectionStart(int paramInt)
  {
    int i = getSelectionStartInText();
    if (i > paramInt) {}
    for (int j = i - paramInt;; j = 0) {
      return this.text.toString().substring(j, i);
    }
  }
  
  public boolean textEmpty()
  {
    return (this.text == null) || (this.text.length() == 0);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.TouchTypeExtractedText
 * JD-Core Version:    0.7.0.1
 */