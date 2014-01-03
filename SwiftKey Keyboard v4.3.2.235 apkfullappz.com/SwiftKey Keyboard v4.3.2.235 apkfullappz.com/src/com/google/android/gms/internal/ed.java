package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.plus.model.moments.ItemScope;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public final class ed
  extends an
  implements ae, ItemScope
{
  public static final ee CREATOR = new ee();
  private static final HashMap<String, an.a<?, ?>> hY;
  private final int T;
  private String ck;
  private double eA;
  private double eB;
  private String hL;
  private final Set<Integer> hZ;
  private ed iA;
  private String iB;
  private String iC;
  private String iD;
  private String iE;
  private ed iF;
  private ed iG;
  private ed iH;
  private List<ed> iI;
  private String iJ;
  private String iK;
  private String iL;
  private String iM;
  private ed iN;
  private String iO;
  private String iP;
  private String iQ;
  private ed iR;
  private String iS;
  private String iT;
  private String iU;
  private String iV;
  private String iW;
  private ed ia;
  private List<String> ib;
  private ed ic;
  private String id;
  private String ie;
  private String jdField_if;
  private List<ed> ig;
  private int ih;
  private List<ed> ii;
  private ed ij;
  private List<ed> ik;
  private String il;
  private String im;
  private ed in;
  private String io;
  private String ip;
  private String iq;
  private List<ed> ir;
  private String is;
  private String it;
  private String iu;
  private String iv;
  private String iw;
  private String ix;
  private String iy;
  private String iz;
  private String mName;
  
  static
  {
    HashMap localHashMap = new HashMap();
    hY = localHashMap;
    localHashMap.put("about", an.a.a("about", 2, ed.class));
    hY.put("additionalName", an.a.g("additionalName", 3));
    hY.put("address", an.a.a("address", 4, ed.class));
    hY.put("addressCountry", an.a.f("addressCountry", 5));
    hY.put("addressLocality", an.a.f("addressLocality", 6));
    hY.put("addressRegion", an.a.f("addressRegion", 7));
    hY.put("associated_media", an.a.b("associated_media", 8, ed.class));
    hY.put("attendeeCount", an.a.c("attendeeCount", 9));
    hY.put("attendees", an.a.b("attendees", 10, ed.class));
    hY.put("audio", an.a.a("audio", 11, ed.class));
    hY.put("author", an.a.b("author", 12, ed.class));
    hY.put("bestRating", an.a.f("bestRating", 13));
    hY.put("birthDate", an.a.f("birthDate", 14));
    hY.put("byArtist", an.a.a("byArtist", 15, ed.class));
    hY.put("caption", an.a.f("caption", 16));
    hY.put("contentSize", an.a.f("contentSize", 17));
    hY.put("contentUrl", an.a.f("contentUrl", 18));
    hY.put("contributor", an.a.b("contributor", 19, ed.class));
    hY.put("dateCreated", an.a.f("dateCreated", 20));
    hY.put("dateModified", an.a.f("dateModified", 21));
    hY.put("datePublished", an.a.f("datePublished", 22));
    hY.put("description", an.a.f("description", 23));
    hY.put("duration", an.a.f("duration", 24));
    hY.put("embedUrl", an.a.f("embedUrl", 25));
    hY.put("endDate", an.a.f("endDate", 26));
    hY.put("familyName", an.a.f("familyName", 27));
    hY.put("gender", an.a.f("gender", 28));
    hY.put("geo", an.a.a("geo", 29, ed.class));
    hY.put("givenName", an.a.f("givenName", 30));
    hY.put("height", an.a.f("height", 31));
    hY.put("id", an.a.f("id", 32));
    hY.put("image", an.a.f("image", 33));
    hY.put("inAlbum", an.a.a("inAlbum", 34, ed.class));
    hY.put("latitude", an.a.d("latitude", 36));
    hY.put("location", an.a.a("location", 37, ed.class));
    hY.put("longitude", an.a.d("longitude", 38));
    hY.put("name", an.a.f("name", 39));
    hY.put("partOfTVSeries", an.a.a("partOfTVSeries", 40, ed.class));
    hY.put("performers", an.a.b("performers", 41, ed.class));
    hY.put("playerType", an.a.f("playerType", 42));
    hY.put("postOfficeBoxNumber", an.a.f("postOfficeBoxNumber", 43));
    hY.put("postalCode", an.a.f("postalCode", 44));
    hY.put("ratingValue", an.a.f("ratingValue", 45));
    hY.put("reviewRating", an.a.a("reviewRating", 46, ed.class));
    hY.put("startDate", an.a.f("startDate", 47));
    hY.put("streetAddress", an.a.f("streetAddress", 48));
    hY.put("text", an.a.f("text", 49));
    hY.put("thumbnail", an.a.a("thumbnail", 50, ed.class));
    hY.put("thumbnailUrl", an.a.f("thumbnailUrl", 51));
    hY.put("tickerSymbol", an.a.f("tickerSymbol", 52));
    hY.put("type", an.a.f("type", 53));
    hY.put("url", an.a.f("url", 54));
    hY.put("width", an.a.f("width", 55));
    hY.put("worstRating", an.a.f("worstRating", 56));
  }
  
  public ed()
  {
    this.T = 1;
    this.hZ = new HashSet();
  }
  
  ed(Set<Integer> paramSet, int paramInt1, ed paramed1, List<String> paramList, ed paramed2, String paramString1, String paramString2, String paramString3, List<ed> paramList1, int paramInt2, List<ed> paramList2, ed paramed3, List<ed> paramList3, String paramString4, String paramString5, ed paramed4, String paramString6, String paramString7, String paramString8, List<ed> paramList4, String paramString9, String paramString10, String paramString11, String paramString12, String paramString13, String paramString14, String paramString15, String paramString16, String paramString17, ed paramed5, String paramString18, String paramString19, String paramString20, String paramString21, ed paramed6, double paramDouble1, ed paramed7, double paramDouble2, String paramString22, ed paramed8, List<ed> paramList5, String paramString23, String paramString24, String paramString25, String paramString26, ed paramed9, String paramString27, String paramString28, String paramString29, ed paramed10, String paramString30, String paramString31, String paramString32, String paramString33, String paramString34, String paramString35)
  {
    this.hZ = paramSet;
    this.T = paramInt1;
    this.ia = paramed1;
    this.ib = paramList;
    this.ic = paramed2;
    this.id = paramString1;
    this.ie = paramString2;
    this.jdField_if = paramString3;
    this.ig = paramList1;
    this.ih = paramInt2;
    this.ii = paramList2;
    this.ij = paramed3;
    this.ik = paramList3;
    this.il = paramString4;
    this.im = paramString5;
    this.in = paramed4;
    this.io = paramString6;
    this.ip = paramString7;
    this.iq = paramString8;
    this.ir = paramList4;
    this.is = paramString9;
    this.it = paramString10;
    this.iu = paramString11;
    this.ck = paramString12;
    this.iv = paramString13;
    this.iw = paramString14;
    this.ix = paramString15;
    this.iy = paramString16;
    this.iz = paramString17;
    this.iA = paramed5;
    this.iB = paramString18;
    this.iC = paramString19;
    this.iD = paramString20;
    this.iE = paramString21;
    this.iF = paramed6;
    this.eA = paramDouble1;
    this.iG = paramed7;
    this.eB = paramDouble2;
    this.mName = paramString22;
    this.iH = paramed8;
    this.iI = paramList5;
    this.iJ = paramString23;
    this.iK = paramString24;
    this.iL = paramString25;
    this.iM = paramString26;
    this.iN = paramed9;
    this.iO = paramString27;
    this.iP = paramString28;
    this.iQ = paramString29;
    this.iR = paramed10;
    this.iS = paramString30;
    this.iT = paramString31;
    this.iU = paramString32;
    this.hL = paramString33;
    this.iV = paramString34;
    this.iW = paramString35;
  }
  
  public HashMap<String, an.a<?, ?>> G()
  {
    return hY;
  }
  
  protected boolean a(an.a parama)
  {
    return this.hZ.contains(Integer.valueOf(parama.N()));
  }
  
  protected Object b(an.a parama)
  {
    switch (parama.N())
    {
    case 35: 
    default: 
      throw new IllegalStateException("Unknown safe parcelable id=" + parama.N());
    case 2: 
      return this.ia;
    case 3: 
      return this.ib;
    case 4: 
      return this.ic;
    case 5: 
      return this.id;
    case 6: 
      return this.ie;
    case 7: 
      return this.jdField_if;
    case 8: 
      return this.ig;
    case 9: 
      return Integer.valueOf(this.ih);
    case 10: 
      return this.ii;
    case 11: 
      return this.ij;
    case 12: 
      return this.ik;
    case 13: 
      return this.il;
    case 14: 
      return this.im;
    case 15: 
      return this.in;
    case 16: 
      return this.io;
    case 17: 
      return this.ip;
    case 18: 
      return this.iq;
    case 19: 
      return this.ir;
    case 20: 
      return this.is;
    case 21: 
      return this.it;
    case 22: 
      return this.iu;
    case 23: 
      return this.ck;
    case 24: 
      return this.iv;
    case 25: 
      return this.iw;
    case 26: 
      return this.ix;
    case 27: 
      return this.iy;
    case 28: 
      return this.iz;
    case 29: 
      return this.iA;
    case 30: 
      return this.iB;
    case 31: 
      return this.iC;
    case 32: 
      return this.iD;
    case 33: 
      return this.iE;
    case 34: 
      return this.iF;
    case 36: 
      return Double.valueOf(this.eA);
    case 37: 
      return this.iG;
    case 38: 
      return Double.valueOf(this.eB);
    case 39: 
      return this.mName;
    case 40: 
      return this.iH;
    case 41: 
      return this.iI;
    case 42: 
      return this.iJ;
    case 43: 
      return this.iK;
    case 44: 
      return this.iL;
    case 45: 
      return this.iM;
    case 46: 
      return this.iN;
    case 47: 
      return this.iO;
    case 48: 
      return this.iP;
    case 49: 
      return this.iQ;
    case 50: 
      return this.iR;
    case 51: 
      return this.iS;
    case 52: 
      return this.iT;
    case 53: 
      return this.iU;
    case 54: 
      return this.hL;
    case 55: 
      return this.iV;
    }
    return this.iW;
  }
  
  ed bA()
  {
    return this.ia;
  }
  
  ed bB()
  {
    return this.ic;
  }
  
  List<ed> bC()
  {
    return this.ig;
  }
  
  List<ed> bD()
  {
    return this.ii;
  }
  
  ed bE()
  {
    return this.ij;
  }
  
  List<ed> bF()
  {
    return this.ik;
  }
  
  ed bG()
  {
    return this.in;
  }
  
  List<ed> bH()
  {
    return this.ir;
  }
  
  ed bI()
  {
    return this.iA;
  }
  
  ed bJ()
  {
    return this.iF;
  }
  
  ed bK()
  {
    return this.iG;
  }
  
  ed bL()
  {
    return this.iH;
  }
  
  List<ed> bM()
  {
    return this.iI;
  }
  
  ed bN()
  {
    return this.iN;
  }
  
  ed bO()
  {
    return this.iR;
  }
  
  public ed bP()
  {
    return this;
  }
  
  Set<Integer> bz()
  {
    return this.hZ;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof ed)) {
      return false;
    }
    if (this == paramObject) {
      return true;
    }
    ed localed = (ed)paramObject;
    Iterator localIterator = hY.values().iterator();
    while (localIterator.hasNext())
    {
      an.a locala = (an.a)localIterator.next();
      if (a(locala))
      {
        if (localed.a(locala))
        {
          if (!b(locala).equals(localed.b(locala))) {
            return false;
          }
        }
        else {
          return false;
        }
      }
      else if (localed.a(locala)) {
        return false;
      }
    }
    return true;
  }
  
  public List<String> getAdditionalName()
  {
    return this.ib;
  }
  
  public String getAddressCountry()
  {
    return this.id;
  }
  
  public String getAddressLocality()
  {
    return this.ie;
  }
  
  public String getAddressRegion()
  {
    return this.jdField_if;
  }
  
  public int getAttendeeCount()
  {
    return this.ih;
  }
  
  public String getBestRating()
  {
    return this.il;
  }
  
  public String getBirthDate()
  {
    return this.im;
  }
  
  public String getCaption()
  {
    return this.io;
  }
  
  public String getContentSize()
  {
    return this.ip;
  }
  
  public String getContentUrl()
  {
    return this.iq;
  }
  
  public String getDateCreated()
  {
    return this.is;
  }
  
  public String getDateModified()
  {
    return this.it;
  }
  
  public String getDatePublished()
  {
    return this.iu;
  }
  
  public String getDescription()
  {
    return this.ck;
  }
  
  public String getDuration()
  {
    return this.iv;
  }
  
  public String getEmbedUrl()
  {
    return this.iw;
  }
  
  public String getEndDate()
  {
    return this.ix;
  }
  
  public String getFamilyName()
  {
    return this.iy;
  }
  
  public String getGender()
  {
    return this.iz;
  }
  
  public String getGivenName()
  {
    return this.iB;
  }
  
  public String getHeight()
  {
    return this.iC;
  }
  
  public String getId()
  {
    return this.iD;
  }
  
  public String getImage()
  {
    return this.iE;
  }
  
  public double getLatitude()
  {
    return this.eA;
  }
  
  public double getLongitude()
  {
    return this.eB;
  }
  
  public String getName()
  {
    return this.mName;
  }
  
  public String getPlayerType()
  {
    return this.iJ;
  }
  
  public String getPostOfficeBoxNumber()
  {
    return this.iK;
  }
  
  public String getPostalCode()
  {
    return this.iL;
  }
  
  public String getRatingValue()
  {
    return this.iM;
  }
  
  public String getStartDate()
  {
    return this.iO;
  }
  
  public String getStreetAddress()
  {
    return this.iP;
  }
  
  public String getText()
  {
    return this.iQ;
  }
  
  public String getThumbnailUrl()
  {
    return this.iS;
  }
  
  public String getTickerSymbol()
  {
    return this.iT;
  }
  
  public String getType()
  {
    return this.iU;
  }
  
  public String getUrl()
  {
    return this.hL;
  }
  
  public String getWidth()
  {
    return this.iV;
  }
  
  public String getWorstRating()
  {
    return this.iW;
  }
  
  public int hashCode()
  {
    Iterator localIterator = hY.values().iterator();
    int i = 0;
    an.a locala;
    if (localIterator.hasNext())
    {
      locala = (an.a)localIterator.next();
      if (!a(locala)) {
        break label66;
      }
    }
    label66:
    for (int j = i + locala.N() + b(locala).hashCode();; j = i)
    {
      i = j;
      break;
      return i;
    }
  }
  
  protected Object j(String paramString)
  {
    return null;
  }
  
  protected boolean k(String paramString)
  {
    return false;
  }
  
  int u()
  {
    return this.T;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    ee.a(this, paramParcel, paramInt);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.ed
 * JD-Core Version:    0.7.0.1
 */