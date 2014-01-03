package com.touchtype_fluency.service;

import com.touchtype_fluency.Sequence;

public abstract interface ImmutableExtractedText
{
  public abstract char getCharAtIndexInField(int paramInt);
  
  public abstract Sequence getContext();
  
  public abstract CharSequence getCurrentWord();
  
  public abstract char getFirstSelectedCharacter();
  
  public abstract char getLastCharacter();
  
  public abstract char getLastNonSpaceCharacter();
  
  public abstract CharSequence getLastWord();
  
  public abstract char getNextCharacter();
  
  public abstract int getSelectionEndInField();
  
  public abstract int getSelectionStartInField();
  
  public abstract int getSpacesBeforeCursor();
  
  public abstract int getStartOffset();
  
  public abstract String getText();
  
  public abstract boolean isLastCharacterWhitespace();
  
  public abstract boolean isLastCharacterZeroWidthSpace();
  
  public abstract int lengthOfCodePointBeforeIndexInField(int paramInt);
  
  public abstract String substringInField(int paramInt1, int paramInt2);
  
  public abstract String textBeforeSelectionStart(int paramInt);
  
  public abstract boolean textEmpty();
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.ImmutableExtractedText
 * JD-Core Version:    0.7.0.1
 */