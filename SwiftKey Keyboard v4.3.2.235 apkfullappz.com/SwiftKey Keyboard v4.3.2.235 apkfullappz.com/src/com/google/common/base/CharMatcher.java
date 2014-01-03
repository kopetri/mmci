package com.google.common.base;

import java.util.Arrays;

public abstract class CharMatcher
  implements Predicate<Character>
{
  public static final CharMatcher ANY = new CharMatcher("CharMatcher.ANY")
  {
    public boolean matches(char paramAnonymousChar)
    {
      return true;
    }
    
    public CharMatcher or(CharMatcher paramAnonymousCharMatcher)
    {
      Preconditions.checkNotNull(paramAnonymousCharMatcher);
      return this;
    }
    
    public CharMatcher precomputed()
    {
      return this;
    }
  };
  public static final CharMatcher ASCII;
  public static final CharMatcher BREAKING_WHITESPACE = anyOf("\t\n\013\f\r     　").or(inRange(' ', ' ')).or(inRange(' ', ' ')).withToString("CharMatcher.BREAKING_WHITESPACE").precomputed();
  public static final CharMatcher DIGIT;
  public static final CharMatcher INVISIBLE;
  public static final CharMatcher JAVA_DIGIT;
  public static final CharMatcher JAVA_ISO_CONTROL;
  public static final CharMatcher JAVA_LETTER;
  public static final CharMatcher JAVA_LETTER_OR_DIGIT;
  public static final CharMatcher JAVA_LOWER_CASE;
  public static final CharMatcher JAVA_UPPER_CASE;
  public static final CharMatcher NONE = new CharMatcher("CharMatcher.NONE")
  {
    public boolean matches(char paramAnonymousChar)
    {
      return false;
    }
    
    public CharMatcher or(CharMatcher paramAnonymousCharMatcher)
    {
      return (CharMatcher)Preconditions.checkNotNull(paramAnonymousCharMatcher);
    }
    
    public CharMatcher precomputed()
    {
      return this;
    }
    
    void setBits(CharMatcher.LookupTable paramAnonymousLookupTable) {}
  };
  public static final CharMatcher SINGLE_WIDTH;
  public static final CharMatcher WHITESPACE = new CharMatcher("CharMatcher.WHITESPACE")
  {
    private final char[] table = { 1, 0, 160, 0, 0, 0, 0, 0, 0, 9, 10, 11, 12, 13, 0, 0, 8232, 8233, 0, 0, 0, 0, 0, 8239, 0, 0, 0, 0, 0, 0, 0, 0, 32, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12288, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 133, 8192, 8193, 8194, 8195, 8196, 8197, 8198, 8199, 8200, 8201, 8202, 0, 0, 0, 0, 0, 8287, 5760, 0, 0, 6158, 0, 0, 0 };
    
    public boolean matches(char paramAnonymousChar)
    {
      return this.table[(paramAnonymousChar % 'O')] == paramAnonymousChar;
    }
    
    public CharMatcher precomputed()
    {
      return this;
    }
  };
  final String description;
  
  static
  {
    ASCII = inRange('\000', '', "CharMatcher.ASCII");
    CharMatcher localCharMatcher = inRange('0', '9');
    for (char c : "٠۰߀०০੦૦୦௦౦೦൦๐໐༠၀႐០᠐᥆᧐᭐᮰᱀᱐꘠꣐꤀꩐０".toCharArray()) {
      localCharMatcher = localCharMatcher.or(inRange(c, (char)(c + '\t')));
    }
    DIGIT = localCharMatcher.withToString("CharMatcher.DIGIT").precomputed();
    JAVA_DIGIT = new CharMatcher("CharMatcher.JAVA_DIGIT")
    {
      public boolean matches(char paramAnonymousChar)
      {
        return Character.isDigit(paramAnonymousChar);
      }
    };
    JAVA_LETTER = new CharMatcher("CharMatcher.JAVA_LETTER")
    {
      public boolean matches(char paramAnonymousChar)
      {
        return Character.isLetter(paramAnonymousChar);
      }
      
      public CharMatcher precomputed()
      {
        return this;
      }
    };
    JAVA_LETTER_OR_DIGIT = new CharMatcher("CharMatcher.JAVA_LETTER_OR_DIGIT")
    {
      public boolean matches(char paramAnonymousChar)
      {
        return Character.isLetterOrDigit(paramAnonymousChar);
      }
    };
    JAVA_UPPER_CASE = new CharMatcher("CharMatcher.JAVA_UPPER_CASE")
    {
      public boolean matches(char paramAnonymousChar)
      {
        return Character.isUpperCase(paramAnonymousChar);
      }
    };
    JAVA_LOWER_CASE = new CharMatcher("CharMatcher.JAVA_LOWER_CASE")
    {
      public boolean matches(char paramAnonymousChar)
      {
        return Character.isLowerCase(paramAnonymousChar);
      }
    };
    JAVA_ISO_CONTROL = inRange('\000', '\037').or(inRange('', '')).withToString("CharMatcher.JAVA_ISO_CONTROL");
    INVISIBLE = inRange('\000', ' ').or(inRange('', ' ')).or(is('­')).or(inRange('؀', '؄')).or(anyOf("۝܏ ᠎")).or(inRange(' ', '‏')).or(inRange(' ', ' ')).or(inRange(' ', '⁤')).or(inRange('⁪', '⁯')).or(is('　')).or(inRange(55296, 63743)).or(anyOf("﻿￹￺￻")).withToString("CharMatcher.INVISIBLE").precomputed();
    SINGLE_WIDTH = inRange('\000', 'ӹ').or(is('־')).or(inRange('א', 'ת')).or(is('׳')).or(is('״')).or(inRange('؀', 'ۿ')).or(inRange('ݐ', 'ݿ')).or(inRange('฀', '๿')).or(inRange('Ḁ', '₯')).or(inRange('℀', '℺')).or(inRange(64336, 65023)).or(inRange(65136, 65279)).or(inRange(65377, 65500)).withToString("CharMatcher.SINGLE_WIDTH").precomputed();
  }
  
  protected CharMatcher()
  {
    this.description = "UnknownCharMatcher";
  }
  
  CharMatcher(String paramString)
  {
    this.description = paramString;
  }
  
  public static CharMatcher anyOf(CharSequence paramCharSequence)
  {
    switch (paramCharSequence.length())
    {
    default: 
      final char[] arrayOfChar = paramCharSequence.toString().toCharArray();
      Arrays.sort(arrayOfChar);
      new CharMatcher("CharMatcher.anyOf(\"" + arrayOfChar + "\")")
      {
        public boolean matches(char paramAnonymousChar)
        {
          return Arrays.binarySearch(arrayOfChar, paramAnonymousChar) >= 0;
        }
      };
    case 0: 
      return NONE;
    case 1: 
      return is(paramCharSequence.charAt(0));
    }
    final char c1 = paramCharSequence.charAt(0);
    final char c2 = paramCharSequence.charAt(1);
    new CharMatcher("CharMatcher.anyOf(\"" + paramCharSequence + "\")")
    {
      public boolean matches(char paramAnonymousChar)
      {
        return (paramAnonymousChar == c1) || (paramAnonymousChar == c2);
      }
      
      public CharMatcher precomputed()
      {
        return this;
      }
      
      void setBits(CharMatcher.LookupTable paramAnonymousLookupTable)
      {
        paramAnonymousLookupTable.set(c1);
        paramAnonymousLookupTable.set(c2);
      }
    };
  }
  
  public static CharMatcher inRange(char paramChar1, char paramChar2)
  {
    if (paramChar2 >= paramChar1) {}
    for (boolean bool = true;; bool = false)
    {
      Preconditions.checkArgument(bool);
      return inRange(paramChar1, paramChar2, "CharMatcher.inRange(" + Integer.toHexString(paramChar1) + ", " + Integer.toHexString(paramChar2) + ")");
    }
  }
  
  static CharMatcher inRange(final char paramChar1, final char paramChar2, String paramString)
  {
    new CharMatcher(paramString)
    {
      public boolean matches(char paramAnonymousChar)
      {
        return (paramChar1 <= paramAnonymousChar) && (paramAnonymousChar <= paramChar2);
      }
      
      public CharMatcher precomputed()
      {
        return this;
      }
      
      void setBits(CharMatcher.LookupTable paramAnonymousLookupTable)
      {
        char c2;
        for (char c1 = paramChar1;; c1 = c2)
        {
          paramAnonymousLookupTable.set(c1);
          c2 = (char)(c1 + '\001');
          if (c1 == paramChar2) {
            return;
          }
        }
      }
    };
  }
  
  public static CharMatcher is(final char paramChar)
  {
    new CharMatcher("CharMatcher.is(" + Integer.toHexString(paramChar) + ")")
    {
      public boolean matches(char paramAnonymousChar)
      {
        return paramAnonymousChar == paramChar;
      }
      
      public CharMatcher or(CharMatcher paramAnonymousCharMatcher)
      {
        if (paramAnonymousCharMatcher.matches(paramChar)) {
          return paramAnonymousCharMatcher;
        }
        return super.or(paramAnonymousCharMatcher);
      }
      
      public CharMatcher precomputed()
      {
        return this;
      }
      
      void setBits(CharMatcher.LookupTable paramAnonymousLookupTable)
      {
        paramAnonymousLookupTable.set(paramChar);
      }
    };
  }
  
  public boolean apply(Character paramCharacter)
  {
    return matches(paramCharacter.charValue());
  }
  
  public abstract boolean matches(char paramChar);
  
  public CharMatcher or(CharMatcher paramCharMatcher)
  {
    return new Or(this, (CharMatcher)Preconditions.checkNotNull(paramCharMatcher));
  }
  
  public CharMatcher precomputed()
  {
    return Platform.precomputeCharMatcher(this);
  }
  
  CharMatcher precomputedInternal()
  {
    char[] arrayOfChar = slowGetChars();
    int i = arrayOfChar.length;
    if (i == 0) {
      return NONE;
    }
    if (i == 1) {
      return is(arrayOfChar[0]);
    }
    if (i < 63) {
      return SmallCharMatcher.from(arrayOfChar, toString());
    }
    if (i < 1023) {
      return MediumCharMatcher.from(arrayOfChar, toString());
    }
    final LookupTable localLookupTable = new LookupTable(null);
    setBits(localLookupTable);
    new CharMatcher(toString())
    {
      public boolean matches(char paramAnonymousChar)
      {
        return localLookupTable.get(paramAnonymousChar);
      }
      
      public CharMatcher precomputed()
      {
        return this;
      }
    };
  }
  
  void setBits(LookupTable paramLookupTable)
  {
    int j;
    for (int i = 0;; i = j)
    {
      if (matches(i)) {
        paramLookupTable.set(i);
      }
      j = (char)(i + 1);
      if (i == 65535) {
        return;
      }
    }
  }
  
  char[] slowGetChars()
  {
    char[] arrayOfChar1 = new char[65536];
    int i = 0;
    int j = 0;
    int k;
    if (i <= 65535)
    {
      if (!matches((char)i)) {
        break label62;
      }
      k = j + 1;
      arrayOfChar1[j] = ((char)i);
    }
    for (;;)
    {
      i++;
      j = k;
      break;
      char[] arrayOfChar2 = new char[j];
      System.arraycopy(arrayOfChar1, 0, arrayOfChar2, 0, j);
      return arrayOfChar2;
      label62:
      k = j;
    }
  }
  
  public String toString()
  {
    return this.description;
  }
  
  CharMatcher withToString(String paramString)
  {
    throw new UnsupportedOperationException();
  }
  
  private static final class LookupTable
  {
    int[] data = new int[2048];
    
    boolean get(char paramChar)
    {
      return (this.data[(paramChar >> '\005')] & '\001' << paramChar) != 0;
    }
    
    void set(char paramChar)
    {
      int[] arrayOfInt = this.data;
      int i = paramChar >> '\005';
      arrayOfInt[i] |= '\001' << paramChar;
    }
  }
  
  private static final class Or
    extends CharMatcher
  {
    final CharMatcher first;
    final CharMatcher second;
    
    Or(CharMatcher paramCharMatcher1, CharMatcher paramCharMatcher2)
    {
      this(paramCharMatcher1, paramCharMatcher2, "CharMatcher.or(" + paramCharMatcher1 + ", " + paramCharMatcher2 + ")");
    }
    
    Or(CharMatcher paramCharMatcher1, CharMatcher paramCharMatcher2, String paramString)
    {
      super();
      this.first = ((CharMatcher)Preconditions.checkNotNull(paramCharMatcher1));
      this.second = ((CharMatcher)Preconditions.checkNotNull(paramCharMatcher2));
    }
    
    public boolean matches(char paramChar)
    {
      return (this.first.matches(paramChar)) || (this.second.matches(paramChar));
    }
    
    public CharMatcher or(CharMatcher paramCharMatcher)
    {
      return new Or(this, (CharMatcher)Preconditions.checkNotNull(paramCharMatcher));
    }
    
    CharMatcher withToString(String paramString)
    {
      return new Or(this.first, this.second, paramString);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.common.base.CharMatcher
 * JD-Core Version:    0.7.0.1
 */