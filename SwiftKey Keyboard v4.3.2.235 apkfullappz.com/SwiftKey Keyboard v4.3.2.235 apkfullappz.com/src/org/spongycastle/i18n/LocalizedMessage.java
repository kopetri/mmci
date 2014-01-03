package org.spongycastle.i18n;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.Format;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.TimeZone;
import org.spongycastle.i18n.filter.Filter;
import org.spongycastle.i18n.filter.TrustedInput;
import org.spongycastle.i18n.filter.UntrustedInput;
import org.spongycastle.i18n.filter.UntrustedUrlInput;

public class LocalizedMessage
{
  public static final String DEFAULT_ENCODING = "ISO-8859-1";
  protected FilteredArguments arguments;
  protected String encoding = "ISO-8859-1";
  protected FilteredArguments extraArgs = null;
  protected Filter filter = null;
  protected final String id;
  protected ClassLoader loader = null;
  protected final String resource;
  
  public LocalizedMessage(String paramString1, String paramString2)
    throws NullPointerException
  {
    if ((paramString1 == null) || (paramString2 == null)) {
      throw new NullPointerException();
    }
    this.id = paramString2;
    this.resource = paramString1;
    this.arguments = new FilteredArguments();
  }
  
  public LocalizedMessage(String paramString1, String paramString2, String paramString3)
    throws NullPointerException, UnsupportedEncodingException
  {
    if ((paramString1 == null) || (paramString2 == null)) {
      throw new NullPointerException();
    }
    this.id = paramString2;
    this.resource = paramString1;
    this.arguments = new FilteredArguments();
    if (!Charset.isSupported(paramString3)) {
      throw new UnsupportedEncodingException("The encoding \"" + paramString3 + "\" is not supported.");
    }
    this.encoding = paramString3;
  }
  
  public LocalizedMessage(String paramString1, String paramString2, String paramString3, Object[] paramArrayOfObject)
    throws NullPointerException, UnsupportedEncodingException
  {
    if ((paramString1 == null) || (paramString2 == null) || (paramArrayOfObject == null)) {
      throw new NullPointerException();
    }
    this.id = paramString2;
    this.resource = paramString1;
    this.arguments = new FilteredArguments(paramArrayOfObject);
    if (!Charset.isSupported(paramString3)) {
      throw new UnsupportedEncodingException("The encoding \"" + paramString3 + "\" is not supported.");
    }
    this.encoding = paramString3;
  }
  
  public LocalizedMessage(String paramString1, String paramString2, Object[] paramArrayOfObject)
    throws NullPointerException
  {
    if ((paramString1 == null) || (paramString2 == null) || (paramArrayOfObject == null)) {
      throw new NullPointerException();
    }
    this.id = paramString2;
    this.resource = paramString1;
    this.arguments = new FilteredArguments(paramArrayOfObject);
  }
  
  protected String addExtraArgs(String paramString, Locale paramLocale)
  {
    if (this.extraArgs != null)
    {
      StringBuffer localStringBuffer = new StringBuffer(paramString);
      Object[] arrayOfObject = this.extraArgs.getFilteredArgs(paramLocale);
      for (int i = 0; i < arrayOfObject.length; i++) {
        localStringBuffer.append(arrayOfObject[i]);
      }
      paramString = localStringBuffer.toString();
    }
    return paramString;
  }
  
  protected String formatWithTimeZone(String paramString, Object[] paramArrayOfObject, Locale paramLocale, TimeZone paramTimeZone)
  {
    MessageFormat localMessageFormat = new MessageFormat(" ");
    localMessageFormat.setLocale(paramLocale);
    localMessageFormat.applyPattern(paramString);
    if (!paramTimeZone.equals(TimeZone.getDefault()))
    {
      Format[] arrayOfFormat = localMessageFormat.getFormats();
      for (int i = 0; i < arrayOfFormat.length; i++) {
        if ((arrayOfFormat[i] instanceof DateFormat))
        {
          DateFormat localDateFormat = (DateFormat)arrayOfFormat[i];
          localDateFormat.setTimeZone(paramTimeZone);
          localMessageFormat.setFormat(i, localDateFormat);
        }
      }
    }
    return localMessageFormat.format(paramArrayOfObject);
  }
  
  public Object[] getArguments()
  {
    return this.arguments.getArguments();
  }
  
  public ClassLoader getClassLoader()
  {
    return this.loader;
  }
  
  /* Error */
  public String getEntry(String paramString, Locale paramLocale, TimeZone paramTimeZone)
    throws MissingEntryException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 36	org/spongycastle/i18n/LocalizedMessage:id	Ljava/lang/String;
    //   4: astore 4
    //   6: aload_1
    //   7: ifnull +29 -> 36
    //   10: new 56	java/lang/StringBuilder
    //   13: dup
    //   14: invokespecial 145	java/lang/StringBuilder:<init>	()V
    //   17: aload 4
    //   19: invokevirtual 65	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   22: ldc 147
    //   24: invokevirtual 65	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   27: aload_1
    //   28: invokevirtual 65	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   31: invokevirtual 71	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   34: astore 4
    //   36: aload_0
    //   37: getfield 33	org/spongycastle/i18n/LocalizedMessage:loader	Ljava/lang/ClassLoader;
    //   40: ifnonnull +90 -> 130
    //   43: aload_0
    //   44: getfield 38	org/spongycastle/i18n/LocalizedMessage:resource	Ljava/lang/String;
    //   47: aload_2
    //   48: invokestatic 153	java/util/ResourceBundle:getBundle	(Ljava/lang/String;Ljava/util/Locale;)Ljava/util/ResourceBundle;
    //   51: astore 11
    //   53: aload 11
    //   55: aload 4
    //   57: invokevirtual 157	java/util/ResourceBundle:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   60: astore 12
    //   62: aload_0
    //   63: getfield 27	org/spongycastle/i18n/LocalizedMessage:encoding	Ljava/lang/String;
    //   66: ldc 8
    //   68: invokevirtual 160	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   71: ifne +23 -> 94
    //   74: new 159	java/lang/String
    //   77: dup
    //   78: aload 12
    //   80: ldc 8
    //   82: invokevirtual 164	java/lang/String:getBytes	(Ljava/lang/String;)[B
    //   85: aload_0
    //   86: getfield 27	org/spongycastle/i18n/LocalizedMessage:encoding	Ljava/lang/String;
    //   89: invokespecial 167	java/lang/String:<init>	([BLjava/lang/String;)V
    //   92: astore 12
    //   94: aload_0
    //   95: getfield 45	org/spongycastle/i18n/LocalizedMessage:arguments	Lorg/spongycastle/i18n/LocalizedMessage$FilteredArguments;
    //   98: invokevirtual 171	org/spongycastle/i18n/LocalizedMessage$FilteredArguments:isEmpty	()Z
    //   101: ifne +21 -> 122
    //   104: aload_0
    //   105: aload 12
    //   107: aload_0
    //   108: getfield 45	org/spongycastle/i18n/LocalizedMessage:arguments	Lorg/spongycastle/i18n/LocalizedMessage$FilteredArguments;
    //   111: aload_2
    //   112: invokevirtual 86	org/spongycastle/i18n/LocalizedMessage$FilteredArguments:getFilteredArgs	(Ljava/util/Locale;)[Ljava/lang/Object;
    //   115: aload_2
    //   116: aload_3
    //   117: invokevirtual 173	org/spongycastle/i18n/LocalizedMessage:formatWithTimeZone	(Ljava/lang/String;[Ljava/lang/Object;Ljava/util/Locale;Ljava/util/TimeZone;)Ljava/lang/String;
    //   120: astore 12
    //   122: aload_0
    //   123: aload 12
    //   125: aload_2
    //   126: invokevirtual 175	org/spongycastle/i18n/LocalizedMessage:addExtraArgs	(Ljava/lang/String;Ljava/util/Locale;)Ljava/lang/String;
    //   129: areturn
    //   130: aload_0
    //   131: getfield 38	org/spongycastle/i18n/LocalizedMessage:resource	Ljava/lang/String;
    //   134: aload_2
    //   135: aload_0
    //   136: getfield 33	org/spongycastle/i18n/LocalizedMessage:loader	Ljava/lang/ClassLoader;
    //   139: invokestatic 178	java/util/ResourceBundle:getBundle	(Ljava/lang/String;Ljava/util/Locale;Ljava/lang/ClassLoader;)Ljava/util/ResourceBundle;
    //   142: astore 10
    //   144: aload 10
    //   146: astore 11
    //   148: goto -95 -> 53
    //   151: astore 6
    //   153: new 56	java/lang/StringBuilder
    //   156: dup
    //   157: ldc 180
    //   159: invokespecial 61	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   162: aload 4
    //   164: invokevirtual 65	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   167: ldc 182
    //   169: invokevirtual 65	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   172: aload_0
    //   173: getfield 38	org/spongycastle/i18n/LocalizedMessage:resource	Ljava/lang/String;
    //   176: invokevirtual 65	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   179: ldc 147
    //   181: invokevirtual 65	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   184: invokevirtual 71	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   187: astore 7
    //   189: aload_0
    //   190: getfield 38	org/spongycastle/i18n/LocalizedMessage:resource	Ljava/lang/String;
    //   193: astore 8
    //   195: aload_0
    //   196: getfield 33	org/spongycastle/i18n/LocalizedMessage:loader	Ljava/lang/ClassLoader;
    //   199: ifnull +26 -> 225
    //   202: aload_0
    //   203: getfield 33	org/spongycastle/i18n/LocalizedMessage:loader	Ljava/lang/ClassLoader;
    //   206: astore 9
    //   208: new 142	org/spongycastle/i18n/MissingEntryException
    //   211: dup
    //   212: aload 7
    //   214: aload 8
    //   216: aload 4
    //   218: aload_2
    //   219: aload 9
    //   221: invokespecial 185	org/spongycastle/i18n/MissingEntryException:<init>	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Locale;Ljava/lang/ClassLoader;)V
    //   224: athrow
    //   225: aload_0
    //   226: invokevirtual 187	org/spongycastle/i18n/LocalizedMessage:getClassLoader	()Ljava/lang/ClassLoader;
    //   229: astore 9
    //   231: goto -23 -> 208
    //   234: astore 5
    //   236: new 189	java/lang/RuntimeException
    //   239: dup
    //   240: aload 5
    //   242: invokespecial 192	java/lang/RuntimeException:<init>	(Ljava/lang/Throwable;)V
    //   245: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	246	0	this	LocalizedMessage
    //   0	246	1	paramString	String
    //   0	246	2	paramLocale	Locale
    //   0	246	3	paramTimeZone	TimeZone
    //   4	213	4	str1	String
    //   234	7	5	localUnsupportedEncodingException	UnsupportedEncodingException
    //   151	1	6	localMissingResourceException	java.util.MissingResourceException
    //   187	26	7	str2	String
    //   193	22	8	str3	String
    //   206	24	9	localClassLoader	ClassLoader
    //   142	3	10	localResourceBundle	java.util.ResourceBundle
    //   51	96	11	localObject	Object
    //   60	64	12	str4	String
    // Exception table:
    //   from	to	target	type
    //   36	53	151	java/util/MissingResourceException
    //   53	94	151	java/util/MissingResourceException
    //   94	122	151	java/util/MissingResourceException
    //   122	130	151	java/util/MissingResourceException
    //   130	144	151	java/util/MissingResourceException
    //   36	53	234	java/io/UnsupportedEncodingException
    //   53	94	234	java/io/UnsupportedEncodingException
    //   94	122	234	java/io/UnsupportedEncodingException
    //   122	130	234	java/io/UnsupportedEncodingException
    //   130	144	234	java/io/UnsupportedEncodingException
  }
  
  public Object[] getExtraArgs()
  {
    if (this.extraArgs == null) {
      return null;
    }
    return this.extraArgs.getArguments();
  }
  
  public Filter getFilter()
  {
    return this.filter;
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public String getResource()
  {
    return this.resource;
  }
  
  public void setClassLoader(ClassLoader paramClassLoader)
  {
    this.loader = paramClassLoader;
  }
  
  public void setExtraArgument(Object paramObject)
  {
    setExtraArguments(new Object[] { paramObject });
  }
  
  public void setExtraArguments(Object[] paramArrayOfObject)
  {
    if (paramArrayOfObject != null)
    {
      this.extraArgs = new FilteredArguments(paramArrayOfObject);
      this.extraArgs.setFilter(this.filter);
      return;
    }
    this.extraArgs = null;
  }
  
  public void setFilter(Filter paramFilter)
  {
    this.arguments.setFilter(paramFilter);
    if (this.extraArgs != null) {
      this.extraArgs.setFilter(paramFilter);
    }
    this.filter = paramFilter;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("Resource: \"").append(this.resource);
    localStringBuffer.append("\" Id: \"").append(this.id).append("\"");
    localStringBuffer.append(" Arguments: ").append(this.arguments.getArguments().length).append(" normal");
    if ((this.extraArgs != null) && (this.extraArgs.getArguments().length > 0)) {
      localStringBuffer.append(", ").append(this.extraArgs.getArguments().length).append(" extra");
    }
    localStringBuffer.append(" Encoding: ").append(this.encoding);
    localStringBuffer.append(" ClassLoader: ").append(this.loader);
    return localStringBuffer.toString();
  }
  
  protected class FilteredArguments
  {
    protected static final int FILTER = 1;
    protected static final int FILTER_URL = 2;
    protected static final int NO_FILTER;
    protected int[] argFilterType;
    protected Object[] arguments;
    protected Filter filter = null;
    protected Object[] filteredArgs;
    protected boolean[] isLocaleSpecific;
    protected Object[] unpackedArgs;
    
    FilteredArguments()
    {
      this(new Object[0]);
    }
    
    FilteredArguments(Object[] paramArrayOfObject)
    {
      this.arguments = paramArrayOfObject;
      this.unpackedArgs = new Object[paramArrayOfObject.length];
      this.filteredArgs = new Object[paramArrayOfObject.length];
      this.isLocaleSpecific = new boolean[paramArrayOfObject.length];
      this.argFilterType = new int[paramArrayOfObject.length];
      int i = 0;
      if (i < paramArrayOfObject.length)
      {
        if ((paramArrayOfObject[i] instanceof TrustedInput))
        {
          this.unpackedArgs[i] = ((TrustedInput)paramArrayOfObject[i]).getInput();
          this.argFilterType[i] = 0;
        }
        for (;;)
        {
          this.isLocaleSpecific[i] = (this.unpackedArgs[i] instanceof LocaleString);
          i++;
          break;
          if ((paramArrayOfObject[i] instanceof UntrustedInput))
          {
            this.unpackedArgs[i] = ((UntrustedInput)paramArrayOfObject[i]).getInput();
            if ((paramArrayOfObject[i] instanceof UntrustedUrlInput)) {
              this.argFilterType[i] = 2;
            } else {
              this.argFilterType[i] = 1;
            }
          }
          else
          {
            this.unpackedArgs[i] = paramArrayOfObject[i];
            this.argFilterType[i] = 1;
          }
        }
      }
    }
    
    private Object filter(int paramInt, Object paramObject)
    {
      if (this.filter != null)
      {
        if (paramObject == null) {}
        for (Object localObject = "null";; localObject = paramObject) {
          switch (paramInt)
          {
          default: 
            localObject = null;
          case 0: 
            return localObject;
          }
        }
        return this.filter.doFilter(localObject.toString());
        return this.filter.doFilterUrl(localObject.toString());
      }
      return paramObject;
    }
    
    public Object[] getArguments()
    {
      return this.arguments;
    }
    
    public Filter getFilter()
    {
      return this.filter;
    }
    
    public Object[] getFilteredArgs(Locale paramLocale)
    {
      Object[] arrayOfObject = new Object[this.unpackedArgs.length];
      int i = 0;
      if (i < this.unpackedArgs.length)
      {
        Object localObject2;
        if (this.filteredArgs[i] != null) {
          localObject2 = this.filteredArgs[i];
        }
        for (;;)
        {
          arrayOfObject[i] = localObject2;
          i++;
          break;
          Object localObject1 = this.unpackedArgs[i];
          if (this.isLocaleSpecific[i] != 0)
          {
            String str = ((LocaleString)localObject1).getLocaleString(paramLocale);
            localObject2 = filter(this.argFilterType[i], str);
          }
          else
          {
            localObject2 = filter(this.argFilterType[i], localObject1);
            this.filteredArgs[i] = localObject2;
          }
        }
      }
      return arrayOfObject;
    }
    
    public boolean isEmpty()
    {
      return this.unpackedArgs.length == 0;
    }
    
    public void setFilter(Filter paramFilter)
    {
      if (paramFilter != this.filter) {
        for (int i = 0; i < this.unpackedArgs.length; i++) {
          this.filteredArgs[i] = null;
        }
      }
      this.filter = paramFilter;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.i18n.LocalizedMessage
 * JD-Core Version:    0.7.0.1
 */