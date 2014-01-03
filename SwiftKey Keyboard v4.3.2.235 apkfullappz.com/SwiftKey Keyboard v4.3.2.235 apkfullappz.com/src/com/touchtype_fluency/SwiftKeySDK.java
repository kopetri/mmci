package com.touchtype_fluency;

import com.touchtype_fluency.internal.CharacterMapImpl;
import com.touchtype_fluency.internal.KeyPressModelImpl;
import com.touchtype_fluency.internal.NativeLibLoader;
import com.touchtype_fluency.internal.ParameterImpl;
import com.touchtype_fluency.internal.ParameterSetImpl;
import com.touchtype_fluency.internal.PredictorImpl;
import com.touchtype_fluency.internal.PunctuatorImpl;
import com.touchtype_fluency.internal.SentenceSegmenterImpl;
import com.touchtype_fluency.internal.TokenizerImpl;

public class SwiftKeySDK
{
  public static final String customLibName = "com.touchtype_fluency.nativeLibraryName";
  public static final String customLocation = "com.touchtype_fluency.nativeLibrary";
  public static final String libName = "swiftkeysdk-java";
  public static final String libNameInternal = "swiftkeysdk-java-internal";
  
  static
  {
    String str1 = System.getProperty("com.touchtype_fluency.nativeLibrary");
    String str2 = System.getProperty("com.touchtype_fluency.nativeLibraryName");
    if (str1 != null) {
      NativeLibLoader.loadFullPath(str1, "Failed to load SwiftKey SDK's native library from com.touchtype_fluency.nativeLibrary = \"" + str1 + "\"");
    }
    for (;;)
    {
      initIDs();
      ModelSetDescription.initIDs();
      Sequence.initIDs();
      TouchHistory.initIDs();
      ResultsFilter.initIDs();
      WordBreakIterator.initIDs();
      PredictorImpl.initIDs();
      CharacterMapImpl.initIDs();
      ParameterSetImpl.initIDs();
      ParameterImpl.initIDs();
      KeyPressModelImpl.initIDs();
      TokenizerImpl.initIDs();
      SentenceSegmenterImpl.initIDs();
      PunctuatorImpl.initIDs();
      return;
      if (str2 != null) {
        NativeLibLoader.loadLibrary(str2, "Failed to load SwiftKey SDK's native library as com.touchtype_fluency.nativeLibraryName = \"" + str2 + "\"");
      } else {
        try
        {
          NativeLibLoader.loadOrUnpack("swiftkeysdk-java-internal", "Didn't find internal library");
        }
        catch (UnsatisfiedLinkError localUnsatisfiedLinkError)
        {
          NativeLibLoader.loadOrUnpack("swiftkeysdk-java", "Failed to load SwiftKey SDK's native library \"swiftkeysdk-java\" (or \"swiftkeysdk-java-internal\")");
        }
      }
    }
  }
  
  public static native Session createSession(String paramString)
    throws LicenseException;
  
  public static void forceInit() {}
  
  public static native long getExpiry(String paramString);
  
  public static native String getSourceVersion();
  
  public static native String getVersion();
  
  private static native void initIDs();
  
  public static native void setLoggingListener(LoggingListener paramLoggingListener);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.SwiftKeySDK
 * JD-Core Version:    0.7.0.1
 */