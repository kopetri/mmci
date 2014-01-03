package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LazilyParsedNumber;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.UUID;

public final class TypeAdapters
{
  public static final TypeAdapter<BigDecimal> BIG_DECIMAL;
  public static final TypeAdapter<BigInteger> BIG_INTEGER;
  public static final TypeAdapter<BitSet> BIT_SET;
  public static final TypeAdapterFactory BIT_SET_FACTORY;
  public static final TypeAdapter<Boolean> BOOLEAN;
  public static final TypeAdapter<Boolean> BOOLEAN_AS_STRING;
  public static final TypeAdapterFactory BOOLEAN_FACTORY;
  public static final TypeAdapter<Number> BYTE;
  public static final TypeAdapterFactory BYTE_FACTORY;
  public static final TypeAdapter<Calendar> CALENDAR;
  public static final TypeAdapterFactory CALENDAR_FACTORY;
  public static final TypeAdapter<Character> CHARACTER;
  public static final TypeAdapterFactory CHARACTER_FACTORY;
  public static final TypeAdapter<Class> CLASS = new TypeAdapter()
  {
    public Class read(JsonReader paramAnonymousJsonReader)
      throws IOException
    {
      if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
      {
        paramAnonymousJsonReader.nextNull();
        return null;
      }
      throw new UnsupportedOperationException("Attempted to deserialize a java.lang.Class. Forgot to register a type adapter?");
    }
    
    public void write(JsonWriter paramAnonymousJsonWriter, Class paramAnonymousClass)
      throws IOException
    {
      if (paramAnonymousClass == null)
      {
        paramAnonymousJsonWriter.nullValue();
        return;
      }
      throw new UnsupportedOperationException("Attempted to serialize java.lang.Class: " + paramAnonymousClass.getName() + ". Forgot to register a type adapter?");
    }
  };
  public static final TypeAdapterFactory CLASS_FACTORY = newFactory(Class.class, CLASS);
  public static final TypeAdapter<Number> DOUBLE;
  public static final TypeAdapterFactory ENUM_FACTORY = newEnumTypeHierarchyFactory();
  public static final TypeAdapter<Number> FLOAT;
  public static final TypeAdapter<InetAddress> INET_ADDRESS;
  public static final TypeAdapterFactory INET_ADDRESS_FACTORY;
  public static final TypeAdapter<Number> INTEGER;
  public static final TypeAdapterFactory INTEGER_FACTORY;
  public static final TypeAdapter<JsonElement> JSON_ELEMENT;
  public static final TypeAdapterFactory JSON_ELEMENT_FACTORY;
  public static final TypeAdapter<Locale> LOCALE;
  public static final TypeAdapterFactory LOCALE_FACTORY;
  public static final TypeAdapter<Number> LONG;
  public static final TypeAdapter<Number> NUMBER;
  public static final TypeAdapterFactory NUMBER_FACTORY;
  public static final TypeAdapter<Number> SHORT;
  public static final TypeAdapterFactory SHORT_FACTORY;
  public static final TypeAdapter<String> STRING;
  public static final TypeAdapter<StringBuffer> STRING_BUFFER;
  public static final TypeAdapterFactory STRING_BUFFER_FACTORY;
  public static final TypeAdapter<StringBuilder> STRING_BUILDER;
  public static final TypeAdapterFactory STRING_BUILDER_FACTORY;
  public static final TypeAdapterFactory STRING_FACTORY;
  public static final TypeAdapterFactory TIMESTAMP_FACTORY;
  public static final TypeAdapter<URI> URI;
  public static final TypeAdapterFactory URI_FACTORY;
  public static final TypeAdapter<URL> URL;
  public static final TypeAdapterFactory URL_FACTORY;
  public static final TypeAdapter<UUID> UUID;
  public static final TypeAdapterFactory UUID_FACTORY;
  
  static
  {
    BIT_SET = new TypeAdapter()
    {
      public BitSet read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
        {
          paramAnonymousJsonReader.nextNull();
          return null;
        }
        BitSet localBitSet = new BitSet();
        paramAnonymousJsonReader.beginArray();
        int i = 0;
        JsonToken localJsonToken = paramAnonymousJsonReader.peek();
        if (localJsonToken != JsonToken.END_ARRAY)
        {
          boolean bool;
          switch (TypeAdapters.32.$SwitchMap$com$google$gson$stream$JsonToken[localJsonToken.ordinal()])
          {
          default: 
            throw new JsonSyntaxException("Invalid bitset value type: " + localJsonToken);
          case 1: 
            if (paramAnonymousJsonReader.nextInt() != 0) {
              bool = true;
            }
            break;
          }
          for (;;)
          {
            if (bool) {
              localBitSet.set(i);
            }
            i++;
            localJsonToken = paramAnonymousJsonReader.peek();
            break;
            bool = false;
            continue;
            bool = paramAnonymousJsonReader.nextBoolean();
            continue;
            String str = paramAnonymousJsonReader.nextString();
            try
            {
              int j = Integer.parseInt(str);
              if (j != 0) {}
              for (bool = true;; bool = false) {
                break;
              }
              paramAnonymousJsonReader.endArray();
            }
            catch (NumberFormatException localNumberFormatException)
            {
              throw new JsonSyntaxException("Error: Expecting: bitset number value (1, 0), Found: " + str);
            }
          }
        }
        return localBitSet;
      }
      
      public void write(JsonWriter paramAnonymousJsonWriter, BitSet paramAnonymousBitSet)
        throws IOException
      {
        if (paramAnonymousBitSet == null)
        {
          paramAnonymousJsonWriter.nullValue();
          return;
        }
        paramAnonymousJsonWriter.beginArray();
        int i = 0;
        if (i < paramAnonymousBitSet.length())
        {
          if (paramAnonymousBitSet.get(i)) {}
          for (int j = 1;; j = 0)
          {
            paramAnonymousJsonWriter.value(j);
            i++;
            break;
          }
        }
        paramAnonymousJsonWriter.endArray();
      }
    };
    BIT_SET_FACTORY = newFactory(BitSet.class, BIT_SET);
    BOOLEAN = new TypeAdapter()
    {
      public Boolean read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
        {
          paramAnonymousJsonReader.nextNull();
          return null;
        }
        if (paramAnonymousJsonReader.peek() == JsonToken.STRING) {
          return Boolean.valueOf(Boolean.parseBoolean(paramAnonymousJsonReader.nextString()));
        }
        return Boolean.valueOf(paramAnonymousJsonReader.nextBoolean());
      }
      
      public void write(JsonWriter paramAnonymousJsonWriter, Boolean paramAnonymousBoolean)
        throws IOException
      {
        if (paramAnonymousBoolean == null)
        {
          paramAnonymousJsonWriter.nullValue();
          return;
        }
        paramAnonymousJsonWriter.value(paramAnonymousBoolean.booleanValue());
      }
    };
    BOOLEAN_AS_STRING = new TypeAdapter()
    {
      public Boolean read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
        {
          paramAnonymousJsonReader.nextNull();
          return null;
        }
        return Boolean.valueOf(paramAnonymousJsonReader.nextString());
      }
      
      public void write(JsonWriter paramAnonymousJsonWriter, Boolean paramAnonymousBoolean)
        throws IOException
      {
        if (paramAnonymousBoolean == null) {}
        for (String str = "null";; str = paramAnonymousBoolean.toString())
        {
          paramAnonymousJsonWriter.value(str);
          return;
        }
      }
    };
    BOOLEAN_FACTORY = newFactory(Boolean.TYPE, Boolean.class, BOOLEAN);
    BYTE = new TypeAdapter()
    {
      public Number read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
        {
          paramAnonymousJsonReader.nextNull();
          return null;
        }
        try
        {
          Byte localByte = Byte.valueOf((byte)paramAnonymousJsonReader.nextInt());
          return localByte;
        }
        catch (NumberFormatException localNumberFormatException)
        {
          throw new JsonSyntaxException(localNumberFormatException);
        }
      }
      
      public void write(JsonWriter paramAnonymousJsonWriter, Number paramAnonymousNumber)
        throws IOException
      {
        paramAnonymousJsonWriter.value(paramAnonymousNumber);
      }
    };
    BYTE_FACTORY = newFactory(Byte.TYPE, Byte.class, BYTE);
    SHORT = new TypeAdapter()
    {
      public Number read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
        {
          paramAnonymousJsonReader.nextNull();
          return null;
        }
        try
        {
          Short localShort = Short.valueOf((short)paramAnonymousJsonReader.nextInt());
          return localShort;
        }
        catch (NumberFormatException localNumberFormatException)
        {
          throw new JsonSyntaxException(localNumberFormatException);
        }
      }
      
      public void write(JsonWriter paramAnonymousJsonWriter, Number paramAnonymousNumber)
        throws IOException
      {
        paramAnonymousJsonWriter.value(paramAnonymousNumber);
      }
    };
    SHORT_FACTORY = newFactory(Short.TYPE, Short.class, SHORT);
    INTEGER = new TypeAdapter()
    {
      public Number read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
        {
          paramAnonymousJsonReader.nextNull();
          return null;
        }
        try
        {
          Integer localInteger = Integer.valueOf(paramAnonymousJsonReader.nextInt());
          return localInteger;
        }
        catch (NumberFormatException localNumberFormatException)
        {
          throw new JsonSyntaxException(localNumberFormatException);
        }
      }
      
      public void write(JsonWriter paramAnonymousJsonWriter, Number paramAnonymousNumber)
        throws IOException
      {
        paramAnonymousJsonWriter.value(paramAnonymousNumber);
      }
    };
    INTEGER_FACTORY = newFactory(Integer.TYPE, Integer.class, INTEGER);
    LONG = new TypeAdapter()
    {
      public Number read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
        {
          paramAnonymousJsonReader.nextNull();
          return null;
        }
        try
        {
          Long localLong = Long.valueOf(paramAnonymousJsonReader.nextLong());
          return localLong;
        }
        catch (NumberFormatException localNumberFormatException)
        {
          throw new JsonSyntaxException(localNumberFormatException);
        }
      }
      
      public void write(JsonWriter paramAnonymousJsonWriter, Number paramAnonymousNumber)
        throws IOException
      {
        paramAnonymousJsonWriter.value(paramAnonymousNumber);
      }
    };
    FLOAT = new TypeAdapter()
    {
      public Number read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
        {
          paramAnonymousJsonReader.nextNull();
          return null;
        }
        return Float.valueOf((float)paramAnonymousJsonReader.nextDouble());
      }
      
      public void write(JsonWriter paramAnonymousJsonWriter, Number paramAnonymousNumber)
        throws IOException
      {
        paramAnonymousJsonWriter.value(paramAnonymousNumber);
      }
    };
    DOUBLE = new TypeAdapter()
    {
      public Number read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
        {
          paramAnonymousJsonReader.nextNull();
          return null;
        }
        return Double.valueOf(paramAnonymousJsonReader.nextDouble());
      }
      
      public void write(JsonWriter paramAnonymousJsonWriter, Number paramAnonymousNumber)
        throws IOException
      {
        paramAnonymousJsonWriter.value(paramAnonymousNumber);
      }
    };
    NUMBER = new TypeAdapter()
    {
      public Number read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        JsonToken localJsonToken = paramAnonymousJsonReader.peek();
        switch (TypeAdapters.32.$SwitchMap$com$google$gson$stream$JsonToken[localJsonToken.ordinal()])
        {
        case 2: 
        case 3: 
        default: 
          throw new JsonSyntaxException("Expecting number, got: " + localJsonToken);
        case 4: 
          paramAnonymousJsonReader.nextNull();
          return null;
        }
        return new LazilyParsedNumber(paramAnonymousJsonReader.nextString());
      }
      
      public void write(JsonWriter paramAnonymousJsonWriter, Number paramAnonymousNumber)
        throws IOException
      {
        paramAnonymousJsonWriter.value(paramAnonymousNumber);
      }
    };
    NUMBER_FACTORY = newFactory(Number.class, NUMBER);
    CHARACTER = new TypeAdapter()
    {
      public Character read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
        {
          paramAnonymousJsonReader.nextNull();
          return null;
        }
        String str = paramAnonymousJsonReader.nextString();
        if (str.length() != 1) {
          throw new JsonSyntaxException("Expecting character, got: " + str);
        }
        return Character.valueOf(str.charAt(0));
      }
      
      public void write(JsonWriter paramAnonymousJsonWriter, Character paramAnonymousCharacter)
        throws IOException
      {
        if (paramAnonymousCharacter == null) {}
        for (String str = null;; str = String.valueOf(paramAnonymousCharacter))
        {
          paramAnonymousJsonWriter.value(str);
          return;
        }
      }
    };
    CHARACTER_FACTORY = newFactory(Character.TYPE, Character.class, CHARACTER);
    STRING = new TypeAdapter()
    {
      public String read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        JsonToken localJsonToken = paramAnonymousJsonReader.peek();
        if (localJsonToken == JsonToken.NULL)
        {
          paramAnonymousJsonReader.nextNull();
          return null;
        }
        if (localJsonToken == JsonToken.BOOLEAN) {
          return Boolean.toString(paramAnonymousJsonReader.nextBoolean());
        }
        return paramAnonymousJsonReader.nextString();
      }
      
      public void write(JsonWriter paramAnonymousJsonWriter, String paramAnonymousString)
        throws IOException
      {
        paramAnonymousJsonWriter.value(paramAnonymousString);
      }
    };
    BIG_DECIMAL = new TypeAdapter()
    {
      public BigDecimal read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
        {
          paramAnonymousJsonReader.nextNull();
          return null;
        }
        try
        {
          BigDecimal localBigDecimal = new BigDecimal(paramAnonymousJsonReader.nextString());
          return localBigDecimal;
        }
        catch (NumberFormatException localNumberFormatException)
        {
          throw new JsonSyntaxException(localNumberFormatException);
        }
      }
      
      public void write(JsonWriter paramAnonymousJsonWriter, BigDecimal paramAnonymousBigDecimal)
        throws IOException
      {
        paramAnonymousJsonWriter.value(paramAnonymousBigDecimal);
      }
    };
    BIG_INTEGER = new TypeAdapter()
    {
      public BigInteger read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
        {
          paramAnonymousJsonReader.nextNull();
          return null;
        }
        try
        {
          BigInteger localBigInteger = new BigInteger(paramAnonymousJsonReader.nextString());
          return localBigInteger;
        }
        catch (NumberFormatException localNumberFormatException)
        {
          throw new JsonSyntaxException(localNumberFormatException);
        }
      }
      
      public void write(JsonWriter paramAnonymousJsonWriter, BigInteger paramAnonymousBigInteger)
        throws IOException
      {
        paramAnonymousJsonWriter.value(paramAnonymousBigInteger);
      }
    };
    STRING_FACTORY = newFactory(String.class, STRING);
    STRING_BUILDER = new TypeAdapter()
    {
      public StringBuilder read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
        {
          paramAnonymousJsonReader.nextNull();
          return null;
        }
        return new StringBuilder(paramAnonymousJsonReader.nextString());
      }
      
      public void write(JsonWriter paramAnonymousJsonWriter, StringBuilder paramAnonymousStringBuilder)
        throws IOException
      {
        if (paramAnonymousStringBuilder == null) {}
        for (String str = null;; str = paramAnonymousStringBuilder.toString())
        {
          paramAnonymousJsonWriter.value(str);
          return;
        }
      }
    };
    STRING_BUILDER_FACTORY = newFactory(StringBuilder.class, STRING_BUILDER);
    STRING_BUFFER = new TypeAdapter()
    {
      public StringBuffer read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
        {
          paramAnonymousJsonReader.nextNull();
          return null;
        }
        return new StringBuffer(paramAnonymousJsonReader.nextString());
      }
      
      public void write(JsonWriter paramAnonymousJsonWriter, StringBuffer paramAnonymousStringBuffer)
        throws IOException
      {
        if (paramAnonymousStringBuffer == null) {}
        for (String str = null;; str = paramAnonymousStringBuffer.toString())
        {
          paramAnonymousJsonWriter.value(str);
          return;
        }
      }
    };
    STRING_BUFFER_FACTORY = newFactory(StringBuffer.class, STRING_BUFFER);
    URL = new TypeAdapter()
    {
      public URL read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        if (paramAnonymousJsonReader.peek() == JsonToken.NULL) {
          paramAnonymousJsonReader.nextNull();
        }
        String str;
        do
        {
          return null;
          str = paramAnonymousJsonReader.nextString();
        } while ("null".equals(str));
        return new URL(str);
      }
      
      public void write(JsonWriter paramAnonymousJsonWriter, URL paramAnonymousURL)
        throws IOException
      {
        if (paramAnonymousURL == null) {}
        for (String str = null;; str = paramAnonymousURL.toExternalForm())
        {
          paramAnonymousJsonWriter.value(str);
          return;
        }
      }
    };
    URL_FACTORY = newFactory(URL.class, URL);
    URI = new TypeAdapter()
    {
      public URI read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        if (paramAnonymousJsonReader.peek() == JsonToken.NULL) {
          paramAnonymousJsonReader.nextNull();
        }
        for (;;)
        {
          return null;
          try
          {
            String str = paramAnonymousJsonReader.nextString();
            if ("null".equals(str)) {
              continue;
            }
            URI localURI = new URI(str);
            return localURI;
          }
          catch (URISyntaxException localURISyntaxException)
          {
            throw new JsonIOException(localURISyntaxException);
          }
        }
      }
      
      public void write(JsonWriter paramAnonymousJsonWriter, URI paramAnonymousURI)
        throws IOException
      {
        if (paramAnonymousURI == null) {}
        for (String str = null;; str = paramAnonymousURI.toASCIIString())
        {
          paramAnonymousJsonWriter.value(str);
          return;
        }
      }
    };
    URI_FACTORY = newFactory(URI.class, URI);
    INET_ADDRESS = new TypeAdapter()
    {
      public InetAddress read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
        {
          paramAnonymousJsonReader.nextNull();
          return null;
        }
        return InetAddress.getByName(paramAnonymousJsonReader.nextString());
      }
      
      public void write(JsonWriter paramAnonymousJsonWriter, InetAddress paramAnonymousInetAddress)
        throws IOException
      {
        if (paramAnonymousInetAddress == null) {}
        for (String str = null;; str = paramAnonymousInetAddress.getHostAddress())
        {
          paramAnonymousJsonWriter.value(str);
          return;
        }
      }
    };
    INET_ADDRESS_FACTORY = newTypeHierarchyFactory(InetAddress.class, INET_ADDRESS);
    UUID = new TypeAdapter()
    {
      public UUID read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
        {
          paramAnonymousJsonReader.nextNull();
          return null;
        }
        return UUID.fromString(paramAnonymousJsonReader.nextString());
      }
      
      public void write(JsonWriter paramAnonymousJsonWriter, UUID paramAnonymousUUID)
        throws IOException
      {
        if (paramAnonymousUUID == null) {}
        for (String str = null;; str = paramAnonymousUUID.toString())
        {
          paramAnonymousJsonWriter.value(str);
          return;
        }
      }
    };
    UUID_FACTORY = newFactory(UUID.class, UUID);
    TIMESTAMP_FACTORY = new TypeAdapterFactory()
    {
      public <T> TypeAdapter<T> create(Gson paramAnonymousGson, TypeToken<T> paramAnonymousTypeToken)
      {
        if (paramAnonymousTypeToken.getRawType() != Timestamp.class) {
          return null;
        }
        new TypeAdapter()
        {
          public Timestamp read(JsonReader paramAnonymous2JsonReader)
            throws IOException
          {
            Date localDate = (Date)this.val$dateTypeAdapter.read(paramAnonymous2JsonReader);
            if (localDate != null) {
              return new Timestamp(localDate.getTime());
            }
            return null;
          }
          
          public void write(JsonWriter paramAnonymous2JsonWriter, Timestamp paramAnonymous2Timestamp)
            throws IOException
          {
            this.val$dateTypeAdapter.write(paramAnonymous2JsonWriter, paramAnonymous2Timestamp);
          }
        };
      }
    };
    CALENDAR = new TypeAdapter()
    {
      public Calendar read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
        {
          paramAnonymousJsonReader.nextNull();
          return null;
        }
        paramAnonymousJsonReader.beginObject();
        int i = 0;
        int j = 0;
        int k = 0;
        int m = 0;
        int n = 0;
        int i1 = 0;
        while (paramAnonymousJsonReader.peek() != JsonToken.END_OBJECT)
        {
          String str = paramAnonymousJsonReader.nextName();
          int i2 = paramAnonymousJsonReader.nextInt();
          if ("year".equals(str)) {
            i = i2;
          } else if ("month".equals(str)) {
            j = i2;
          } else if ("dayOfMonth".equals(str)) {
            k = i2;
          } else if ("hourOfDay".equals(str)) {
            m = i2;
          } else if ("minute".equals(str)) {
            n = i2;
          } else if ("second".equals(str)) {
            i1 = i2;
          }
        }
        paramAnonymousJsonReader.endObject();
        return new GregorianCalendar(i, j, k, m, n, i1);
      }
      
      public void write(JsonWriter paramAnonymousJsonWriter, Calendar paramAnonymousCalendar)
        throws IOException
      {
        if (paramAnonymousCalendar == null)
        {
          paramAnonymousJsonWriter.nullValue();
          return;
        }
        paramAnonymousJsonWriter.beginObject();
        paramAnonymousJsonWriter.name("year");
        paramAnonymousJsonWriter.value(paramAnonymousCalendar.get(1));
        paramAnonymousJsonWriter.name("month");
        paramAnonymousJsonWriter.value(paramAnonymousCalendar.get(2));
        paramAnonymousJsonWriter.name("dayOfMonth");
        paramAnonymousJsonWriter.value(paramAnonymousCalendar.get(5));
        paramAnonymousJsonWriter.name("hourOfDay");
        paramAnonymousJsonWriter.value(paramAnonymousCalendar.get(11));
        paramAnonymousJsonWriter.name("minute");
        paramAnonymousJsonWriter.value(paramAnonymousCalendar.get(12));
        paramAnonymousJsonWriter.name("second");
        paramAnonymousJsonWriter.value(paramAnonymousCalendar.get(13));
        paramAnonymousJsonWriter.endObject();
      }
    };
    CALENDAR_FACTORY = newFactoryForMultipleTypes(Calendar.class, GregorianCalendar.class, CALENDAR);
    LOCALE = new TypeAdapter()
    {
      public Locale read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
        {
          paramAnonymousJsonReader.nextNull();
          return null;
        }
        StringTokenizer localStringTokenizer = new StringTokenizer(paramAnonymousJsonReader.nextString(), "_");
        boolean bool1 = localStringTokenizer.hasMoreElements();
        String str1 = null;
        if (bool1) {
          str1 = localStringTokenizer.nextToken();
        }
        boolean bool2 = localStringTokenizer.hasMoreElements();
        String str2 = null;
        if (bool2) {
          str2 = localStringTokenizer.nextToken();
        }
        boolean bool3 = localStringTokenizer.hasMoreElements();
        String str3 = null;
        if (bool3) {
          str3 = localStringTokenizer.nextToken();
        }
        if ((str2 == null) && (str3 == null)) {
          return new Locale(str1);
        }
        if (str3 == null) {
          return new Locale(str1, str2);
        }
        return new Locale(str1, str2, str3);
      }
      
      public void write(JsonWriter paramAnonymousJsonWriter, Locale paramAnonymousLocale)
        throws IOException
      {
        if (paramAnonymousLocale == null) {}
        for (String str = null;; str = paramAnonymousLocale.toString())
        {
          paramAnonymousJsonWriter.value(str);
          return;
        }
      }
    };
    LOCALE_FACTORY = newFactory(Locale.class, LOCALE);
    JSON_ELEMENT = new TypeAdapter()
    {
      public JsonElement read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        switch (TypeAdapters.32.$SwitchMap$com$google$gson$stream$JsonToken[paramAnonymousJsonReader.peek().ordinal()])
        {
        default: 
          throw new IllegalArgumentException();
        case 3: 
          return new JsonPrimitive(paramAnonymousJsonReader.nextString());
        case 1: 
          return new JsonPrimitive(new LazilyParsedNumber(paramAnonymousJsonReader.nextString()));
        case 2: 
          return new JsonPrimitive(Boolean.valueOf(paramAnonymousJsonReader.nextBoolean()));
        case 4: 
          paramAnonymousJsonReader.nextNull();
          return JsonNull.INSTANCE;
        case 5: 
          JsonArray localJsonArray = new JsonArray();
          paramAnonymousJsonReader.beginArray();
          while (paramAnonymousJsonReader.hasNext()) {
            localJsonArray.add(read(paramAnonymousJsonReader));
          }
          paramAnonymousJsonReader.endArray();
          return localJsonArray;
        }
        JsonObject localJsonObject = new JsonObject();
        paramAnonymousJsonReader.beginObject();
        while (paramAnonymousJsonReader.hasNext()) {
          localJsonObject.add(paramAnonymousJsonReader.nextName(), read(paramAnonymousJsonReader));
        }
        paramAnonymousJsonReader.endObject();
        return localJsonObject;
      }
      
      public void write(JsonWriter paramAnonymousJsonWriter, JsonElement paramAnonymousJsonElement)
        throws IOException
      {
        if ((paramAnonymousJsonElement == null) || (paramAnonymousJsonElement.isJsonNull()))
        {
          paramAnonymousJsonWriter.nullValue();
          return;
        }
        if (paramAnonymousJsonElement.isJsonPrimitive())
        {
          JsonPrimitive localJsonPrimitive = paramAnonymousJsonElement.getAsJsonPrimitive();
          if (localJsonPrimitive.isNumber())
          {
            paramAnonymousJsonWriter.value(localJsonPrimitive.getAsNumber());
            return;
          }
          if (localJsonPrimitive.isBoolean())
          {
            paramAnonymousJsonWriter.value(localJsonPrimitive.getAsBoolean());
            return;
          }
          paramAnonymousJsonWriter.value(localJsonPrimitive.getAsString());
          return;
        }
        if (paramAnonymousJsonElement.isJsonArray())
        {
          paramAnonymousJsonWriter.beginArray();
          Iterator localIterator2 = paramAnonymousJsonElement.getAsJsonArray().iterator();
          while (localIterator2.hasNext()) {
            write(paramAnonymousJsonWriter, (JsonElement)localIterator2.next());
          }
          paramAnonymousJsonWriter.endArray();
          return;
        }
        if (paramAnonymousJsonElement.isJsonObject())
        {
          paramAnonymousJsonWriter.beginObject();
          Iterator localIterator1 = paramAnonymousJsonElement.getAsJsonObject().entrySet().iterator();
          while (localIterator1.hasNext())
          {
            Map.Entry localEntry = (Map.Entry)localIterator1.next();
            paramAnonymousJsonWriter.name((String)localEntry.getKey());
            write(paramAnonymousJsonWriter, (JsonElement)localEntry.getValue());
          }
          paramAnonymousJsonWriter.endObject();
          return;
        }
        throw new IllegalArgumentException("Couldn't write " + paramAnonymousJsonElement.getClass());
      }
    };
    JSON_ELEMENT_FACTORY = newTypeHierarchyFactory(JsonElement.class, JSON_ELEMENT);
  }
  
  public static TypeAdapterFactory newEnumTypeHierarchyFactory()
  {
    new TypeAdapterFactory()
    {
      public <T> TypeAdapter<T> create(Gson paramAnonymousGson, TypeToken<T> paramAnonymousTypeToken)
      {
        Class localClass = paramAnonymousTypeToken.getRawType();
        if ((!Enum.class.isAssignableFrom(localClass)) || (localClass == Enum.class)) {
          return null;
        }
        if (!localClass.isEnum()) {
          localClass = localClass.getSuperclass();
        }
        return new TypeAdapters.EnumTypeAdapter(localClass);
      }
    };
  }
  
  public static <TT> TypeAdapterFactory newFactory(Class<TT> paramClass, final TypeAdapter<TT> paramTypeAdapter)
  {
    new TypeAdapterFactory()
    {
      public <T> TypeAdapter<T> create(Gson paramAnonymousGson, TypeToken<T> paramAnonymousTypeToken)
      {
        if (paramAnonymousTypeToken.getRawType() == this.val$type) {
          return paramTypeAdapter;
        }
        return null;
      }
      
      public String toString()
      {
        return "Factory[type=" + this.val$type.getName() + ",adapter=" + paramTypeAdapter + "]";
      }
    };
  }
  
  public static <TT> TypeAdapterFactory newFactory(Class<TT> paramClass1, final Class<TT> paramClass2, final TypeAdapter<? super TT> paramTypeAdapter)
  {
    new TypeAdapterFactory()
    {
      public <T> TypeAdapter<T> create(Gson paramAnonymousGson, TypeToken<T> paramAnonymousTypeToken)
      {
        Class localClass = paramAnonymousTypeToken.getRawType();
        if ((localClass == this.val$unboxed) || (localClass == paramClass2)) {
          return paramTypeAdapter;
        }
        return null;
      }
      
      public String toString()
      {
        return "Factory[type=" + paramClass2.getName() + "+" + this.val$unboxed.getName() + ",adapter=" + paramTypeAdapter + "]";
      }
    };
  }
  
  public static <TT> TypeAdapterFactory newFactoryForMultipleTypes(Class<TT> paramClass, final Class<? extends TT> paramClass1, final TypeAdapter<? super TT> paramTypeAdapter)
  {
    new TypeAdapterFactory()
    {
      public <T> TypeAdapter<T> create(Gson paramAnonymousGson, TypeToken<T> paramAnonymousTypeToken)
      {
        Class localClass = paramAnonymousTypeToken.getRawType();
        if ((localClass == this.val$base) || (localClass == paramClass1)) {
          return paramTypeAdapter;
        }
        return null;
      }
      
      public String toString()
      {
        return "Factory[type=" + this.val$base.getName() + "+" + paramClass1.getName() + ",adapter=" + paramTypeAdapter + "]";
      }
    };
  }
  
  public static <TT> TypeAdapterFactory newTypeHierarchyFactory(Class<TT> paramClass, final TypeAdapter<TT> paramTypeAdapter)
  {
    new TypeAdapterFactory()
    {
      public <T> TypeAdapter<T> create(Gson paramAnonymousGson, TypeToken<T> paramAnonymousTypeToken)
      {
        if (this.val$clazz.isAssignableFrom(paramAnonymousTypeToken.getRawType())) {
          return paramTypeAdapter;
        }
        return null;
      }
      
      public String toString()
      {
        return "Factory[typeHierarchy=" + this.val$clazz.getName() + ",adapter=" + paramTypeAdapter + "]";
      }
    };
  }
  
  private static final class EnumTypeAdapter<T extends Enum<T>>
    extends TypeAdapter<T>
  {
    private final Map<T, String> constantToName = new HashMap();
    private final Map<String, T> nameToConstant = new HashMap();
    
    public EnumTypeAdapter(Class<T> paramClass)
    {
      try
      {
        for (Enum localEnum : (Enum[])paramClass.getEnumConstants())
        {
          String str = localEnum.name();
          SerializedName localSerializedName = (SerializedName)paramClass.getField(str).getAnnotation(SerializedName.class);
          if (localSerializedName != null) {
            str = localSerializedName.value();
          }
          this.nameToConstant.put(str, localEnum);
          this.constantToName.put(localEnum, str);
        }
        return;
      }
      catch (NoSuchFieldException localNoSuchFieldException)
      {
        throw new AssertionError();
      }
    }
    
    public T read(JsonReader paramJsonReader)
      throws IOException
    {
      if (paramJsonReader.peek() == JsonToken.NULL)
      {
        paramJsonReader.nextNull();
        return null;
      }
      return (Enum)this.nameToConstant.get(paramJsonReader.nextString());
    }
    
    public void write(JsonWriter paramJsonWriter, T paramT)
      throws IOException
    {
      if (paramT == null) {}
      for (String str = null;; str = (String)this.constantToName.get(paramT))
      {
        paramJsonWriter.value(str);
        return;
      }
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.gson.internal.bind.TypeAdapters
 * JD-Core Version:    0.7.0.1
 */