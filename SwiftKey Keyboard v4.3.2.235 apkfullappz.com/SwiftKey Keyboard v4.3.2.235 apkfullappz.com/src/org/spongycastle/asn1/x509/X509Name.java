package org.spongycastle.asn1.x509;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.ASN1String;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERSet;
import org.spongycastle.asn1.DERUniversalString;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.util.Strings;
import org.spongycastle.util.encoders.Hex;

public class X509Name
  extends ASN1Object
{
  public static final ASN1ObjectIdentifier BUSINESS_CATEGORY;
  public static final ASN1ObjectIdentifier C = new ASN1ObjectIdentifier("2.5.4.6");
  public static final ASN1ObjectIdentifier CN;
  public static final ASN1ObjectIdentifier COUNTRY_OF_CITIZENSHIP;
  public static final ASN1ObjectIdentifier COUNTRY_OF_RESIDENCE;
  public static final ASN1ObjectIdentifier DATE_OF_BIRTH;
  public static final ASN1ObjectIdentifier DC;
  public static final ASN1ObjectIdentifier DMD_NAME;
  public static final ASN1ObjectIdentifier DN_QUALIFIER;
  public static final Hashtable DefaultLookUp;
  public static boolean DefaultReverse;
  public static final Hashtable DefaultSymbols;
  public static final ASN1ObjectIdentifier E;
  public static final ASN1ObjectIdentifier EmailAddress;
  private static final Boolean FALSE;
  public static final ASN1ObjectIdentifier GENDER;
  public static final ASN1ObjectIdentifier GENERATION;
  public static final ASN1ObjectIdentifier GIVENNAME;
  public static final ASN1ObjectIdentifier INITIALS;
  public static final ASN1ObjectIdentifier L;
  public static final ASN1ObjectIdentifier NAME;
  public static final ASN1ObjectIdentifier NAME_AT_BIRTH;
  public static final ASN1ObjectIdentifier O = new ASN1ObjectIdentifier("2.5.4.10");
  public static final Hashtable OIDLookUp;
  public static final ASN1ObjectIdentifier OU = new ASN1ObjectIdentifier("2.5.4.11");
  public static final ASN1ObjectIdentifier PLACE_OF_BIRTH;
  public static final ASN1ObjectIdentifier POSTAL_ADDRESS;
  public static final ASN1ObjectIdentifier POSTAL_CODE;
  public static final ASN1ObjectIdentifier PSEUDONYM;
  public static final Hashtable RFC1779Symbols;
  public static final Hashtable RFC2253Symbols;
  public static final ASN1ObjectIdentifier SERIALNUMBER;
  public static final ASN1ObjectIdentifier SN;
  public static final ASN1ObjectIdentifier ST;
  public static final ASN1ObjectIdentifier STREET;
  public static final ASN1ObjectIdentifier SURNAME;
  public static final Hashtable SymbolLookUp;
  public static final ASN1ObjectIdentifier T = new ASN1ObjectIdentifier("2.5.4.12");
  public static final ASN1ObjectIdentifier TELEPHONE_NUMBER;
  private static final Boolean TRUE;
  public static final ASN1ObjectIdentifier UID;
  public static final ASN1ObjectIdentifier UNIQUE_IDENTIFIER;
  public static final ASN1ObjectIdentifier UnstructuredAddress;
  public static final ASN1ObjectIdentifier UnstructuredName;
  private Vector added = new Vector();
  private X509NameEntryConverter converter = null;
  private int hashCodeValue;
  private boolean isHashCodeCalculated;
  private Vector ordering = new Vector();
  private ASN1Sequence seq;
  private Vector values = new Vector();
  
  static
  {
    CN = new ASN1ObjectIdentifier("2.5.4.3");
    SN = new ASN1ObjectIdentifier("2.5.4.5");
    STREET = new ASN1ObjectIdentifier("2.5.4.9");
    SERIALNUMBER = SN;
    L = new ASN1ObjectIdentifier("2.5.4.7");
    ST = new ASN1ObjectIdentifier("2.5.4.8");
    SURNAME = new ASN1ObjectIdentifier("2.5.4.4");
    GIVENNAME = new ASN1ObjectIdentifier("2.5.4.42");
    INITIALS = new ASN1ObjectIdentifier("2.5.4.43");
    GENERATION = new ASN1ObjectIdentifier("2.5.4.44");
    UNIQUE_IDENTIFIER = new ASN1ObjectIdentifier("2.5.4.45");
    BUSINESS_CATEGORY = new ASN1ObjectIdentifier("2.5.4.15");
    POSTAL_CODE = new ASN1ObjectIdentifier("2.5.4.17");
    DN_QUALIFIER = new ASN1ObjectIdentifier("2.5.4.46");
    PSEUDONYM = new ASN1ObjectIdentifier("2.5.4.65");
    DATE_OF_BIRTH = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.9.1");
    PLACE_OF_BIRTH = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.9.2");
    GENDER = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.9.3");
    COUNTRY_OF_CITIZENSHIP = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.9.4");
    COUNTRY_OF_RESIDENCE = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.9.5");
    NAME_AT_BIRTH = new ASN1ObjectIdentifier("1.3.36.8.3.14");
    POSTAL_ADDRESS = new ASN1ObjectIdentifier("2.5.4.16");
    DMD_NAME = new ASN1ObjectIdentifier("2.5.4.54");
    TELEPHONE_NUMBER = X509ObjectIdentifiers.id_at_telephoneNumber;
    NAME = X509ObjectIdentifiers.id_at_name;
    EmailAddress = PKCSObjectIdentifiers.pkcs_9_at_emailAddress;
    UnstructuredName = PKCSObjectIdentifiers.pkcs_9_at_unstructuredName;
    UnstructuredAddress = PKCSObjectIdentifiers.pkcs_9_at_unstructuredAddress;
    E = EmailAddress;
    DC = new ASN1ObjectIdentifier("0.9.2342.19200300.100.1.25");
    UID = new ASN1ObjectIdentifier("0.9.2342.19200300.100.1.1");
    DefaultReverse = false;
    DefaultSymbols = new Hashtable();
    RFC2253Symbols = new Hashtable();
    RFC1779Symbols = new Hashtable();
    DefaultLookUp = new Hashtable();
    OIDLookUp = DefaultSymbols;
    SymbolLookUp = DefaultLookUp;
    TRUE = new Boolean(true);
    FALSE = new Boolean(false);
    DefaultSymbols.put(C, "C");
    DefaultSymbols.put(O, "O");
    DefaultSymbols.put(T, "T");
    DefaultSymbols.put(OU, "OU");
    DefaultSymbols.put(CN, "CN");
    DefaultSymbols.put(L, "L");
    DefaultSymbols.put(ST, "ST");
    DefaultSymbols.put(SN, "SERIALNUMBER");
    DefaultSymbols.put(EmailAddress, "E");
    DefaultSymbols.put(DC, "DC");
    DefaultSymbols.put(UID, "UID");
    DefaultSymbols.put(STREET, "STREET");
    DefaultSymbols.put(SURNAME, "SURNAME");
    DefaultSymbols.put(GIVENNAME, "GIVENNAME");
    DefaultSymbols.put(INITIALS, "INITIALS");
    DefaultSymbols.put(GENERATION, "GENERATION");
    DefaultSymbols.put(UnstructuredAddress, "unstructuredAddress");
    DefaultSymbols.put(UnstructuredName, "unstructuredName");
    DefaultSymbols.put(UNIQUE_IDENTIFIER, "UniqueIdentifier");
    DefaultSymbols.put(DN_QUALIFIER, "DN");
    DefaultSymbols.put(PSEUDONYM, "Pseudonym");
    DefaultSymbols.put(POSTAL_ADDRESS, "PostalAddress");
    DefaultSymbols.put(NAME_AT_BIRTH, "NameAtBirth");
    DefaultSymbols.put(COUNTRY_OF_CITIZENSHIP, "CountryOfCitizenship");
    DefaultSymbols.put(COUNTRY_OF_RESIDENCE, "CountryOfResidence");
    DefaultSymbols.put(GENDER, "Gender");
    DefaultSymbols.put(PLACE_OF_BIRTH, "PlaceOfBirth");
    DefaultSymbols.put(DATE_OF_BIRTH, "DateOfBirth");
    DefaultSymbols.put(POSTAL_CODE, "PostalCode");
    DefaultSymbols.put(BUSINESS_CATEGORY, "BusinessCategory");
    DefaultSymbols.put(TELEPHONE_NUMBER, "TelephoneNumber");
    DefaultSymbols.put(NAME, "Name");
    RFC2253Symbols.put(C, "C");
    RFC2253Symbols.put(O, "O");
    RFC2253Symbols.put(OU, "OU");
    RFC2253Symbols.put(CN, "CN");
    RFC2253Symbols.put(L, "L");
    RFC2253Symbols.put(ST, "ST");
    RFC2253Symbols.put(STREET, "STREET");
    RFC2253Symbols.put(DC, "DC");
    RFC2253Symbols.put(UID, "UID");
    RFC1779Symbols.put(C, "C");
    RFC1779Symbols.put(O, "O");
    RFC1779Symbols.put(OU, "OU");
    RFC1779Symbols.put(CN, "CN");
    RFC1779Symbols.put(L, "L");
    RFC1779Symbols.put(ST, "ST");
    RFC1779Symbols.put(STREET, "STREET");
    DefaultLookUp.put("c", C);
    DefaultLookUp.put("o", O);
    DefaultLookUp.put("t", T);
    DefaultLookUp.put("ou", OU);
    DefaultLookUp.put("cn", CN);
    DefaultLookUp.put("l", L);
    DefaultLookUp.put("st", ST);
    DefaultLookUp.put("sn", SN);
    DefaultLookUp.put("serialnumber", SN);
    DefaultLookUp.put("street", STREET);
    DefaultLookUp.put("emailaddress", E);
    DefaultLookUp.put("dc", DC);
    DefaultLookUp.put("e", E);
    DefaultLookUp.put("uid", UID);
    DefaultLookUp.put("surname", SURNAME);
    DefaultLookUp.put("givenname", GIVENNAME);
    DefaultLookUp.put("initials", INITIALS);
    DefaultLookUp.put("generation", GENERATION);
    DefaultLookUp.put("unstructuredaddress", UnstructuredAddress);
    DefaultLookUp.put("unstructuredname", UnstructuredName);
    DefaultLookUp.put("uniqueidentifier", UNIQUE_IDENTIFIER);
    DefaultLookUp.put("dn", DN_QUALIFIER);
    DefaultLookUp.put("pseudonym", PSEUDONYM);
    DefaultLookUp.put("postaladdress", POSTAL_ADDRESS);
    DefaultLookUp.put("nameofbirth", NAME_AT_BIRTH);
    DefaultLookUp.put("countryofcitizenship", COUNTRY_OF_CITIZENSHIP);
    DefaultLookUp.put("countryofresidence", COUNTRY_OF_RESIDENCE);
    DefaultLookUp.put("gender", GENDER);
    DefaultLookUp.put("placeofbirth", PLACE_OF_BIRTH);
    DefaultLookUp.put("dateofbirth", DATE_OF_BIRTH);
    DefaultLookUp.put("postalcode", POSTAL_CODE);
    DefaultLookUp.put("businesscategory", BUSINESS_CATEGORY);
    DefaultLookUp.put("telephonenumber", TELEPHONE_NUMBER);
    DefaultLookUp.put("name", NAME);
  }
  
  protected X509Name() {}
  
  public X509Name(String paramString)
  {
    this(DefaultReverse, DefaultLookUp, paramString);
  }
  
  public X509Name(String paramString, X509NameEntryConverter paramX509NameEntryConverter)
  {
    this(DefaultReverse, DefaultLookUp, paramString, paramX509NameEntryConverter);
  }
  
  public X509Name(Hashtable paramHashtable)
  {
    this(null, paramHashtable);
  }
  
  public X509Name(Vector paramVector, Hashtable paramHashtable)
  {
    this(paramVector, paramHashtable, new X509DefaultEntryConverter());
  }
  
  public X509Name(Vector paramVector, Hashtable paramHashtable, X509NameEntryConverter paramX509NameEntryConverter)
  {
    this.converter = paramX509NameEntryConverter;
    if (paramVector != null) {
      for (int j = 0; j != paramVector.size(); j++)
      {
        this.ordering.addElement(paramVector.elementAt(j));
        this.added.addElement(FALSE);
      }
    }
    Enumeration localEnumeration = paramHashtable.keys();
    while (localEnumeration.hasMoreElements())
    {
      this.ordering.addElement(localEnumeration.nextElement());
      this.added.addElement(FALSE);
    }
    for (int i = 0; i != this.ordering.size(); i++)
    {
      ASN1ObjectIdentifier localASN1ObjectIdentifier = (ASN1ObjectIdentifier)this.ordering.elementAt(i);
      if (paramHashtable.get(localASN1ObjectIdentifier) == null) {
        throw new IllegalArgumentException("No attribute for object id - " + localASN1ObjectIdentifier.getId() + " - passed to distinguished name");
      }
      this.values.addElement(paramHashtable.get(localASN1ObjectIdentifier));
    }
  }
  
  public X509Name(Vector paramVector1, Vector paramVector2)
  {
    this(paramVector1, paramVector2, new X509DefaultEntryConverter());
  }
  
  public X509Name(Vector paramVector1, Vector paramVector2, X509NameEntryConverter paramX509NameEntryConverter)
  {
    this.converter = paramX509NameEntryConverter;
    if (paramVector1.size() != paramVector2.size()) {
      throw new IllegalArgumentException("oids vector must be same length as values.");
    }
    for (int i = 0; i < paramVector1.size(); i++)
    {
      this.ordering.addElement(paramVector1.elementAt(i));
      this.values.addElement(paramVector2.elementAt(i));
      this.added.addElement(FALSE);
    }
  }
  
  public X509Name(ASN1Sequence paramASN1Sequence)
  {
    this.seq = paramASN1Sequence;
    Enumeration localEnumeration = paramASN1Sequence.getObjects();
    if (localEnumeration.hasMoreElements())
    {
      ASN1Set localASN1Set = ASN1Set.getInstance(((ASN1Encodable)localEnumeration.nextElement()).toASN1Primitive());
      int i = 0;
      label82:
      ASN1Encodable localASN1Encodable;
      String str;
      label223:
      Vector localVector;
      if (i < localASN1Set.size())
      {
        ASN1Sequence localASN1Sequence = ASN1Sequence.getInstance(localASN1Set.getObjectAt(i).toASN1Primitive());
        if (localASN1Sequence.size() != 2) {
          throw new IllegalArgumentException("badly sized pair");
        }
        this.ordering.addElement(ASN1ObjectIdentifier.getInstance(localASN1Sequence.getObjectAt(0)));
        localASN1Encodable = localASN1Sequence.getObjectAt(1);
        if ((!(localASN1Encodable instanceof ASN1String)) || ((localASN1Encodable instanceof DERUniversalString))) {
          break label264;
        }
        str = ((ASN1String)localASN1Encodable).getString();
        if ((str.length() <= 0) || (str.charAt(0) != '#')) {
          break label252;
        }
        this.values.addElement("\\" + str);
        localVector = this.added;
        if (i == 0) {
          break label323;
        }
      }
      label264:
      label323:
      for (Boolean localBoolean = TRUE;; localBoolean = FALSE) {
        for (;;)
        {
          localVector.addElement(localBoolean);
          i++;
          break label82;
          break;
          label252:
          this.values.addElement(str);
          break label223;
          try
          {
            this.values.addElement("#" + bytesToString(Hex.encode(localASN1Encodable.toASN1Primitive().getEncoded("DER"))));
          }
          catch (IOException localIOException)
          {
            throw new IllegalArgumentException("cannot encode value");
          }
        }
      }
    }
  }
  
  public X509Name(boolean paramBoolean, String paramString)
  {
    this(paramBoolean, DefaultLookUp, paramString);
  }
  
  public X509Name(boolean paramBoolean, String paramString, X509NameEntryConverter paramX509NameEntryConverter)
  {
    this(paramBoolean, DefaultLookUp, paramString, paramX509NameEntryConverter);
  }
  
  public X509Name(boolean paramBoolean, Hashtable paramHashtable, String paramString)
  {
    this(paramBoolean, paramHashtable, paramString, new X509DefaultEntryConverter());
  }
  
  public X509Name(boolean paramBoolean, Hashtable paramHashtable, String paramString, X509NameEntryConverter paramX509NameEntryConverter)
  {
    this.converter = paramX509NameEntryConverter;
    X509NameTokenizer localX509NameTokenizer1 = new X509NameTokenizer(paramString);
    while (localX509NameTokenizer1.hasMoreTokens())
    {
      String str1 = localX509NameTokenizer1.nextToken();
      int k = str1.indexOf('=');
      if (k == -1) {
        throw new IllegalArgumentException("badly formated directory string");
      }
      String str2 = str1.substring(0, k);
      String str3 = str1.substring(k + 1);
      ASN1ObjectIdentifier localASN1ObjectIdentifier = decodeOID(str2, paramHashtable);
      if (str3.indexOf('+') > 0)
      {
        X509NameTokenizer localX509NameTokenizer2 = new X509NameTokenizer(str3, '+');
        String str4 = localX509NameTokenizer2.nextToken();
        this.ordering.addElement(localASN1ObjectIdentifier);
        this.values.addElement(str4);
        this.added.addElement(FALSE);
        while (localX509NameTokenizer2.hasMoreTokens())
        {
          String str5 = localX509NameTokenizer2.nextToken();
          int m = str5.indexOf('=');
          String str6 = str5.substring(0, m);
          String str7 = str5.substring(m + 1);
          this.ordering.addElement(decodeOID(str6, paramHashtable));
          this.values.addElement(str7);
          this.added.addElement(TRUE);
        }
      }
      else
      {
        this.ordering.addElement(localASN1ObjectIdentifier);
        this.values.addElement(str3);
        this.added.addElement(FALSE);
      }
    }
    if (paramBoolean)
    {
      Vector localVector1 = new Vector();
      Vector localVector2 = new Vector();
      Vector localVector3 = new Vector();
      int i = 1;
      int j = 0;
      if (j < this.ordering.size())
      {
        if (((Boolean)this.added.elementAt(j)).booleanValue())
        {
          localVector1.insertElementAt(this.ordering.elementAt(j), i);
          localVector2.insertElementAt(this.values.elementAt(j), i);
          localVector3.insertElementAt(this.added.elementAt(j), i);
          i++;
        }
        for (;;)
        {
          j++;
          break;
          localVector1.insertElementAt(this.ordering.elementAt(j), 0);
          localVector2.insertElementAt(this.values.elementAt(j), 0);
          localVector3.insertElementAt(this.added.elementAt(j), 0);
          i = 1;
        }
      }
      this.ordering = localVector1;
      this.values = localVector2;
      this.added = localVector3;
    }
  }
  
  private void appendValue(StringBuffer paramStringBuffer, Hashtable paramHashtable, ASN1ObjectIdentifier paramASN1ObjectIdentifier, String paramString)
  {
    String str = (String)paramHashtable.get(paramASN1ObjectIdentifier);
    if (str != null) {
      paramStringBuffer.append(str);
    }
    for (;;)
    {
      paramStringBuffer.append('=');
      int i = paramStringBuffer.length();
      paramStringBuffer.append(paramString);
      int j = paramStringBuffer.length();
      if ((paramString.length() >= 2) && (paramString.charAt(0) == '\\') && (paramString.charAt(1) == '#')) {
        i += 2;
      }
      while (i != j)
      {
        if ((paramStringBuffer.charAt(i) == ',') || (paramStringBuffer.charAt(i) == '"') || (paramStringBuffer.charAt(i) == '\\') || (paramStringBuffer.charAt(i) == '+') || (paramStringBuffer.charAt(i) == '=') || (paramStringBuffer.charAt(i) == '<') || (paramStringBuffer.charAt(i) == '>') || (paramStringBuffer.charAt(i) == ';'))
        {
          paramStringBuffer.insert(i, "\\");
          i++;
          j++;
        }
        i++;
      }
      paramStringBuffer.append(paramASN1ObjectIdentifier.getId());
    }
  }
  
  private String bytesToString(byte[] paramArrayOfByte)
  {
    char[] arrayOfChar = new char[paramArrayOfByte.length];
    for (int i = 0; i != arrayOfChar.length; i++) {
      arrayOfChar[i] = ((char)(0xFF & paramArrayOfByte[i]));
    }
    return new String(arrayOfChar);
  }
  
  private String canonicalize(String paramString)
  {
    String str = Strings.toLowerCase(paramString.trim());
    if ((str.length() > 0) && (str.charAt(0) == '#'))
    {
      ASN1Primitive localASN1Primitive = decodeObject(str);
      if ((localASN1Primitive instanceof ASN1String)) {
        str = Strings.toLowerCase(((ASN1String)localASN1Primitive).getString().trim());
      }
    }
    return str;
  }
  
  private ASN1ObjectIdentifier decodeOID(String paramString, Hashtable paramHashtable)
  {
    ASN1ObjectIdentifier localASN1ObjectIdentifier;
    if (Strings.toUpperCase(paramString).startsWith("OID.")) {
      localASN1ObjectIdentifier = new ASN1ObjectIdentifier(paramString.substring(4));
    }
    do
    {
      return localASN1ObjectIdentifier;
      if ((paramString.charAt(0) >= '0') && (paramString.charAt(0) <= '9')) {
        return new ASN1ObjectIdentifier(paramString);
      }
      localASN1ObjectIdentifier = (ASN1ObjectIdentifier)paramHashtable.get(Strings.toLowerCase(paramString));
    } while (localASN1ObjectIdentifier != null);
    throw new IllegalArgumentException("Unknown object id - " + paramString + " - passed to distinguished name");
  }
  
  private ASN1Primitive decodeObject(String paramString)
  {
    try
    {
      ASN1Primitive localASN1Primitive = ASN1Primitive.fromByteArray(Hex.decode(paramString.substring(1)));
      return localASN1Primitive;
    }
    catch (IOException localIOException)
    {
      throw new IllegalStateException("unknown encoding in name: " + localIOException);
    }
  }
  
  private boolean equivalentStrings(String paramString1, String paramString2)
  {
    String str1 = canonicalize(paramString1);
    String str2 = canonicalize(paramString2);
    return (str1.equals(str2)) || (stripInternalSpaces(str1).equals(stripInternalSpaces(str2)));
  }
  
  public static X509Name getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof X509Name))) {
      return (X509Name)paramObject;
    }
    if ((paramObject instanceof X500Name)) {
      return new X509Name(ASN1Sequence.getInstance(((X500Name)paramObject).toASN1Primitive()));
    }
    if (paramObject != null) {
      return new X509Name(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public static X509Name getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    return getInstance(ASN1Sequence.getInstance(paramASN1TaggedObject, paramBoolean));
  }
  
  private String stripInternalSpaces(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (paramString.length() != 0)
    {
      char c1 = paramString.charAt(0);
      localStringBuffer.append(c1);
      for (int i = 1; i < paramString.length(); i++)
      {
        char c2 = paramString.charAt(i);
        if ((c1 != ' ') || (c2 != ' ')) {
          localStringBuffer.append(c2);
        }
        c1 = c2;
      }
    }
    return localStringBuffer.toString();
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == this) {
      return true;
    }
    if ((!(paramObject instanceof X509Name)) && (!(paramObject instanceof ASN1Sequence))) {
      return false;
    }
    ASN1Primitive localASN1Primitive = ((ASN1Encodable)paramObject).toASN1Primitive();
    if (toASN1Primitive().equals(localASN1Primitive)) {
      return true;
    }
    X509Name localX509Name;
    int i;
    try
    {
      localX509Name = getInstance(paramObject);
      i = this.ordering.size();
      if (i != localX509Name.ordering.size()) {
        return false;
      }
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      return false;
    }
    boolean[] arrayOfBoolean = new boolean[i];
    int j;
    int k;
    int m;
    int n;
    if (this.ordering.elementAt(0).equals(localX509Name.ordering.elementAt(0)))
    {
      j = 0;
      k = i;
      m = 1;
      n = j;
    }
    for (;;)
    {
      if (n == k) {
        break label268;
      }
      ASN1ObjectIdentifier localASN1ObjectIdentifier = (ASN1ObjectIdentifier)this.ordering.elementAt(n);
      String str = (String)this.values.elementAt(n);
      for (int i1 = 0;; i1++)
      {
        int i2 = 0;
        if (i1 < i)
        {
          if ((arrayOfBoolean[i1] == 0) && (localASN1ObjectIdentifier.equals((ASN1ObjectIdentifier)localX509Name.ordering.elementAt(i1))) && (equivalentStrings(str, (String)localX509Name.values.elementAt(i1))))
          {
            arrayOfBoolean[i1] = true;
            i2 = 1;
          }
        }
        else
        {
          if (i2 != 0) {
            break label258;
          }
          return false;
          j = i - 1;
          k = -1;
          m = -1;
          break;
        }
      }
      label258:
      n += m;
    }
    label268:
    return true;
  }
  
  public boolean equals(Object paramObject, boolean paramBoolean)
  {
    if (!paramBoolean) {
      return equals(paramObject);
    }
    if (paramObject == this) {
      return true;
    }
    if ((!(paramObject instanceof X509Name)) && (!(paramObject instanceof ASN1Sequence))) {
      return false;
    }
    ASN1Primitive localASN1Primitive = ((ASN1Encodable)paramObject).toASN1Primitive();
    if (toASN1Primitive().equals(localASN1Primitive)) {
      return true;
    }
    X509Name localX509Name;
    int i;
    try
    {
      localX509Name = getInstance(paramObject);
      i = this.ordering.size();
      if (i != localX509Name.ordering.size()) {
        return false;
      }
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      return false;
    }
    for (int j = 0; j < i; j++) {
      if (((ASN1ObjectIdentifier)this.ordering.elementAt(j)).equals((ASN1ObjectIdentifier)localX509Name.ordering.elementAt(j)))
      {
        if (!equivalentStrings((String)this.values.elementAt(j), (String)localX509Name.values.elementAt(j))) {
          return false;
        }
      }
      else {
        return false;
      }
    }
    return true;
  }
  
  public Vector getOIDs()
  {
    Vector localVector = new Vector();
    for (int i = 0; i != this.ordering.size(); i++) {
      localVector.addElement(this.ordering.elementAt(i));
    }
    return localVector;
  }
  
  public Vector getValues()
  {
    Vector localVector = new Vector();
    for (int i = 0; i != this.values.size(); i++) {
      localVector.addElement(this.values.elementAt(i));
    }
    return localVector;
  }
  
  public Vector getValues(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
  {
    Vector localVector = new Vector();
    int i = 0;
    if (i != this.values.size())
    {
      String str;
      if (this.ordering.elementAt(i).equals(paramASN1ObjectIdentifier))
      {
        str = (String)this.values.elementAt(i);
        if ((str.length() <= 2) || (str.charAt(0) != '\\') || (str.charAt(1) != '#')) {
          break label96;
        }
        localVector.addElement(str.substring(1));
      }
      for (;;)
      {
        i++;
        break;
        label96:
        localVector.addElement(str);
      }
    }
    return localVector;
  }
  
  public int hashCode()
  {
    if (this.isHashCodeCalculated) {
      return this.hashCodeValue;
    }
    this.isHashCodeCalculated = true;
    for (int i = 0; i != this.ordering.size(); i++)
    {
      String str = stripInternalSpaces(canonicalize((String)this.values.elementAt(i)));
      this.hashCodeValue ^= this.ordering.elementAt(i).hashCode();
      this.hashCodeValue ^= str.hashCode();
    }
    return this.hashCodeValue;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    if (this.seq == null)
    {
      ASN1EncodableVector localASN1EncodableVector1 = new ASN1EncodableVector();
      ASN1EncodableVector localASN1EncodableVector2 = new ASN1EncodableVector();
      Object localObject = null;
      int i = 0;
      if (i != this.ordering.size())
      {
        ASN1EncodableVector localASN1EncodableVector3 = new ASN1EncodableVector();
        ASN1ObjectIdentifier localASN1ObjectIdentifier = (ASN1ObjectIdentifier)this.ordering.elementAt(i);
        localASN1EncodableVector3.add(localASN1ObjectIdentifier);
        String str = (String)this.values.elementAt(i);
        localASN1EncodableVector3.add(this.converter.getConvertedValue(localASN1ObjectIdentifier, str));
        if ((localObject == null) || (((Boolean)this.added.elementAt(i)).booleanValue())) {
          localASN1EncodableVector2.add(new DERSequence(localASN1EncodableVector3));
        }
        for (;;)
        {
          localObject = localASN1ObjectIdentifier;
          i++;
          break;
          localASN1EncodableVector1.add(new DERSet(localASN1EncodableVector2));
          localASN1EncodableVector2 = new ASN1EncodableVector();
          localASN1EncodableVector2.add(new DERSequence(localASN1EncodableVector3));
        }
      }
      localASN1EncodableVector1.add(new DERSet(localASN1EncodableVector2));
      this.seq = new DERSequence(localASN1EncodableVector1);
    }
    return this.seq;
  }
  
  public String toString()
  {
    return toString(DefaultReverse, DefaultSymbols);
  }
  
  public String toString(boolean paramBoolean, Hashtable paramHashtable)
  {
    StringBuffer localStringBuffer1 = new StringBuffer();
    Vector localVector = new Vector();
    int i = 1;
    StringBuffer localStringBuffer2 = null;
    int j = 0;
    if (j < this.ordering.size())
    {
      if (((Boolean)this.added.elementAt(j)).booleanValue())
      {
        localStringBuffer2.append('+');
        appendValue(localStringBuffer2, paramHashtable, (ASN1ObjectIdentifier)this.ordering.elementAt(j), (String)this.values.elementAt(j));
      }
      for (;;)
      {
        j++;
        break;
        localStringBuffer2 = new StringBuffer();
        appendValue(localStringBuffer2, paramHashtable, (ASN1ObjectIdentifier)this.ordering.elementAt(j), (String)this.values.elementAt(j));
        localVector.addElement(localStringBuffer2);
      }
    }
    if (paramBoolean)
    {
      int m = -1 + localVector.size();
      if (m >= 0)
      {
        if (i != 0) {
          i = 0;
        }
        for (;;)
        {
          localStringBuffer1.append(localVector.elementAt(m).toString());
          m--;
          break;
          localStringBuffer1.append(',');
        }
      }
    }
    else
    {
      int k = 0;
      if (k < localVector.size())
      {
        if (i != 0) {
          i = 0;
        }
        for (;;)
        {
          localStringBuffer1.append(localVector.elementAt(k).toString());
          k++;
          break;
          localStringBuffer1.append(',');
        }
      }
    }
    return localStringBuffer1.toString();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x509.X509Name
 * JD-Core Version:    0.7.0.1
 */