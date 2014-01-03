package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.Person.AgeRange;
import com.google.android.gms.plus.model.people.Person.Cover;
import com.google.android.gms.plus.model.people.Person.Cover.CoverInfo;
import com.google.android.gms.plus.model.people.Person.Cover.CoverPhoto;
import com.google.android.gms.plus.model.people.Person.Emails;
import com.google.android.gms.plus.model.people.Person.Image;
import com.google.android.gms.plus.model.people.Person.Name;
import com.google.android.gms.plus.model.people.Person.Organizations;
import com.google.android.gms.plus.model.people.Person.PlacesLived;
import com.google.android.gms.plus.model.people.Person.Urls;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public final class eq
  extends an
  implements ae, Person
{
  public static final er CREATOR = new er();
  private static final HashMap<String, an.a<?, ?>> hY;
  private final int T;
  private String bp;
  private String hL;
  private final Set<Integer> hZ;
  private String iD;
  private String ja;
  private a jb;
  private String jc;
  private String jd;
  private int je;
  private b jf;
  private String jg;
  private List<c> jh;
  private String ji;
  private int jj;
  private boolean jk;
  private d jl;
  private boolean jm;
  private String jn;
  private e jo;
  private String jp;
  private int jq;
  private List<g> jr;
  private List<h> js;
  private int jt;
  private int ju;
  private String jv;
  private List<i> jw;
  private boolean jx;
  
  static
  {
    HashMap localHashMap = new HashMap();
    hY = localHashMap;
    localHashMap.put("aboutMe", an.a.f("aboutMe", 2));
    hY.put("ageRange", an.a.a("ageRange", 3, a.class));
    hY.put("birthday", an.a.f("birthday", 4));
    hY.put("braggingRights", an.a.f("braggingRights", 5));
    hY.put("circledByCount", an.a.c("circledByCount", 6));
    hY.put("cover", an.a.a("cover", 7, b.class));
    hY.put("currentLocation", an.a.f("currentLocation", 8));
    hY.put("displayName", an.a.f("displayName", 9));
    hY.put("emails", an.a.b("emails", 10, c.class));
    hY.put("etag", an.a.f("etag", 11));
    hY.put("gender", an.a.a("gender", 12, new ak().b("male", 0).b("female", 1).b("other", 2), false));
    hY.put("hasApp", an.a.e("hasApp", 13));
    hY.put("id", an.a.f("id", 14));
    hY.put("image", an.a.a("image", 15, d.class));
    hY.put("isPlusUser", an.a.e("isPlusUser", 16));
    hY.put("language", an.a.f("language", 18));
    hY.put("name", an.a.a("name", 19, e.class));
    hY.put("nickname", an.a.f("nickname", 20));
    hY.put("objectType", an.a.a("objectType", 21, new ak().b("person", 0).b("page", 1), false));
    hY.put("organizations", an.a.b("organizations", 22, g.class));
    hY.put("placesLived", an.a.b("placesLived", 23, h.class));
    hY.put("plusOneCount", an.a.c("plusOneCount", 24));
    hY.put("relationshipStatus", an.a.a("relationshipStatus", 25, new ak().b("single", 0).b("in_a_relationship", 1).b("engaged", 2).b("married", 3).b("its_complicated", 4).b("open_relationship", 5).b("widowed", 6).b("in_domestic_partnership", 7).b("in_civil_union", 8), false));
    hY.put("tagline", an.a.f("tagline", 26));
    hY.put("url", an.a.f("url", 27));
    hY.put("urls", an.a.b("urls", 28, i.class));
    hY.put("verified", an.a.e("verified", 29));
  }
  
  public eq()
  {
    this.T = 1;
    this.hZ = new HashSet();
  }
  
  eq(Set<Integer> paramSet, int paramInt1, String paramString1, a parama, String paramString2, String paramString3, int paramInt2, b paramb, String paramString4, String paramString5, List<c> paramList, String paramString6, int paramInt3, boolean paramBoolean1, String paramString7, d paramd, boolean paramBoolean2, String paramString8, e parame, String paramString9, int paramInt4, List<g> paramList1, List<h> paramList2, int paramInt5, int paramInt6, String paramString10, String paramString11, List<i> paramList3, boolean paramBoolean3)
  {
    this.hZ = paramSet;
    this.T = paramInt1;
    this.ja = paramString1;
    this.jb = parama;
    this.jc = paramString2;
    this.jd = paramString3;
    this.je = paramInt2;
    this.jf = paramb;
    this.jg = paramString4;
    this.bp = paramString5;
    this.jh = paramList;
    this.ji = paramString6;
    this.jj = paramInt3;
    this.jk = paramBoolean1;
    this.iD = paramString7;
    this.jl = paramd;
    this.jm = paramBoolean2;
    this.jn = paramString8;
    this.jo = parame;
    this.jp = paramString9;
    this.jq = paramInt4;
    this.jr = paramList1;
    this.js = paramList2;
    this.jt = paramInt5;
    this.ju = paramInt6;
    this.jv = paramString10;
    this.hL = paramString11;
    this.jw = paramList3;
    this.jx = paramBoolean3;
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
    case 17: 
    default: 
      throw new IllegalStateException("Unknown safe parcelable id=" + parama.N());
    case 2: 
      return this.ja;
    case 3: 
      return this.jb;
    case 4: 
      return this.jc;
    case 5: 
      return this.jd;
    case 6: 
      return Integer.valueOf(this.je);
    case 7: 
      return this.jf;
    case 8: 
      return this.jg;
    case 9: 
      return this.bp;
    case 10: 
      return this.jh;
    case 11: 
      return this.ji;
    case 12: 
      return Integer.valueOf(this.jj);
    case 13: 
      return Boolean.valueOf(this.jk);
    case 14: 
      return this.iD;
    case 15: 
      return this.jl;
    case 16: 
      return Boolean.valueOf(this.jm);
    case 18: 
      return this.jn;
    case 19: 
      return this.jo;
    case 20: 
      return this.jp;
    case 21: 
      return Integer.valueOf(this.jq);
    case 22: 
      return this.jr;
    case 23: 
      return this.js;
    case 24: 
      return Integer.valueOf(this.jt);
    case 25: 
      return Integer.valueOf(this.ju);
    case 26: 
      return this.jv;
    case 27: 
      return this.hL;
    case 28: 
      return this.jw;
    }
    return Boolean.valueOf(this.jx);
  }
  
  a bU()
  {
    return this.jb;
  }
  
  b bV()
  {
    return this.jf;
  }
  
  List<c> bW()
  {
    return this.jh;
  }
  
  public String bX()
  {
    return this.ji;
  }
  
  d bY()
  {
    return this.jl;
  }
  
  e bZ()
  {
    return this.jo;
  }
  
  Set<Integer> bz()
  {
    return this.hZ;
  }
  
  List<g> ca()
  {
    return this.jr;
  }
  
  List<h> cb()
  {
    return this.js;
  }
  
  List<i> cc()
  {
    return this.jw;
  }
  
  public eq cd()
  {
    return this;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof eq)) {
      return false;
    }
    if (this == paramObject) {
      return true;
    }
    eq localeq = (eq)paramObject;
    Iterator localIterator = hY.values().iterator();
    while (localIterator.hasNext())
    {
      an.a locala = (an.a)localIterator.next();
      if (a(locala))
      {
        if (localeq.a(locala))
        {
          if (!b(locala).equals(localeq.b(locala))) {
            return false;
          }
        }
        else {
          return false;
        }
      }
      else if (localeq.a(locala)) {
        return false;
      }
    }
    return true;
  }
  
  public String getAboutMe()
  {
    return this.ja;
  }
  
  public String getBirthday()
  {
    return this.jc;
  }
  
  public String getBraggingRights()
  {
    return this.jd;
  }
  
  public int getCircledByCount()
  {
    return this.je;
  }
  
  public String getCurrentLocation()
  {
    return this.jg;
  }
  
  public String getDisplayName()
  {
    return this.bp;
  }
  
  public int getGender()
  {
    return this.jj;
  }
  
  public String getId()
  {
    return this.iD;
  }
  
  public String getLanguage()
  {
    return this.jn;
  }
  
  public String getNickname()
  {
    return this.jp;
  }
  
  public int getObjectType()
  {
    return this.jq;
  }
  
  public int getPlusOneCount()
  {
    return this.jt;
  }
  
  public int getRelationshipStatus()
  {
    return this.ju;
  }
  
  public String getTagline()
  {
    return this.jv;
  }
  
  public String getUrl()
  {
    return this.hL;
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
  
  public boolean isHasApp()
  {
    return this.jk;
  }
  
  public boolean isPlusUser()
  {
    return this.jm;
  }
  
  public boolean isVerified()
  {
    return this.jx;
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
    er.a(this, paramParcel, paramInt);
  }
  
  public static final class a
    extends an
    implements ae, Person.AgeRange
  {
    public static final ei CREATOR = new ei();
    private static final HashMap<String, an.a<?, ?>> hY;
    private final int T;
    private final Set<Integer> hZ;
    private int jy;
    private int jz;
    
    static
    {
      HashMap localHashMap = new HashMap();
      hY = localHashMap;
      localHashMap.put("max", an.a.c("max", 2));
      hY.put("min", an.a.c("min", 3));
    }
    
    public a()
    {
      this.T = 1;
      this.hZ = new HashSet();
    }
    
    a(Set<Integer> paramSet, int paramInt1, int paramInt2, int paramInt3)
    {
      this.hZ = paramSet;
      this.T = paramInt1;
      this.jy = paramInt2;
      this.jz = paramInt3;
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
      default: 
        throw new IllegalStateException("Unknown safe parcelable id=" + parama.N());
      case 2: 
        return Integer.valueOf(this.jy);
      }
      return Integer.valueOf(this.jz);
    }
    
    Set<Integer> bz()
    {
      return this.hZ;
    }
    
    public a ce()
    {
      return this;
    }
    
    public int describeContents()
    {
      return 0;
    }
    
    public int getMax()
    {
      return this.jy;
    }
    
    public int getMin()
    {
      return this.jz;
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
      ei.a(this, paramParcel, paramInt);
    }
  }
  
  public static final class b
    extends an
    implements ae, Person.Cover
  {
    public static final ej CREATOR = new ej();
    private static final HashMap<String, an.a<?, ?>> hY;
    private final int T;
    private final Set<Integer> hZ;
    private a jA;
    private b jB;
    private int jC;
    
    static
    {
      HashMap localHashMap = new HashMap();
      hY = localHashMap;
      localHashMap.put("coverInfo", an.a.a("coverInfo", 2, a.class));
      hY.put("coverPhoto", an.a.a("coverPhoto", 3, b.class));
      hY.put("layout", an.a.a("layout", 4, new ak().b("banner", 0), false));
    }
    
    public b()
    {
      this.T = 1;
      this.hZ = new HashSet();
    }
    
    b(Set<Integer> paramSet, int paramInt1, a parama, b paramb, int paramInt2)
    {
      this.hZ = paramSet;
      this.T = paramInt1;
      this.jA = parama;
      this.jB = paramb;
      this.jC = paramInt2;
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
      default: 
        throw new IllegalStateException("Unknown safe parcelable id=" + parama.N());
      case 2: 
        return this.jA;
      case 3: 
        return this.jB;
      }
      return Integer.valueOf(this.jC);
    }
    
    Set<Integer> bz()
    {
      return this.hZ;
    }
    
    a cf()
    {
      return this.jA;
    }
    
    b cg()
    {
      return this.jB;
    }
    
    public b ch()
    {
      return this;
    }
    
    public int describeContents()
    {
      return 0;
    }
    
    public boolean equals(Object paramObject)
    {
      if (!(paramObject instanceof b)) {
        return false;
      }
      if (this == paramObject) {
        return true;
      }
      b localb = (b)paramObject;
      Iterator localIterator = hY.values().iterator();
      while (localIterator.hasNext())
      {
        an.a locala = (an.a)localIterator.next();
        if (a(locala))
        {
          if (localb.a(locala))
          {
            if (!b(locala).equals(localb.b(locala))) {
              return false;
            }
          }
          else {
            return false;
          }
        }
        else if (localb.a(locala)) {
          return false;
        }
      }
      return true;
    }
    
    public int getLayout()
    {
      return this.jC;
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
      ej.a(this, paramParcel, paramInt);
    }
    
    public static final class a
      extends an
      implements ae, Person.Cover.CoverInfo
    {
      public static final ek CREATOR = new ek();
      private static final HashMap<String, an.a<?, ?>> hY;
      private final int T;
      private final Set<Integer> hZ;
      private int jD;
      private int jE;
      
      static
      {
        HashMap localHashMap = new HashMap();
        hY = localHashMap;
        localHashMap.put("leftImageOffset", an.a.c("leftImageOffset", 2));
        hY.put("topImageOffset", an.a.c("topImageOffset", 3));
      }
      
      public a()
      {
        this.T = 1;
        this.hZ = new HashSet();
      }
      
      a(Set<Integer> paramSet, int paramInt1, int paramInt2, int paramInt3)
      {
        this.hZ = paramSet;
        this.T = paramInt1;
        this.jD = paramInt2;
        this.jE = paramInt3;
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
        default: 
          throw new IllegalStateException("Unknown safe parcelable id=" + parama.N());
        case 2: 
          return Integer.valueOf(this.jD);
        }
        return Integer.valueOf(this.jE);
      }
      
      Set<Integer> bz()
      {
        return this.hZ;
      }
      
      public a ci()
      {
        return this;
      }
      
      public int describeContents()
      {
        return 0;
      }
      
      public boolean equals(Object paramObject)
      {
        if (!(paramObject instanceof a)) {
          return false;
        }
        if (this == paramObject) {
          return true;
        }
        a locala = (a)paramObject;
        Iterator localIterator = hY.values().iterator();
        while (localIterator.hasNext())
        {
          an.a locala1 = (an.a)localIterator.next();
          if (a(locala1))
          {
            if (locala.a(locala1))
            {
              if (!b(locala1).equals(locala.b(locala1))) {
                return false;
              }
            }
            else {
              return false;
            }
          }
          else if (locala.a(locala1)) {
            return false;
          }
        }
        return true;
      }
      
      public int getLeftImageOffset()
      {
        return this.jD;
      }
      
      public int getTopImageOffset()
      {
        return this.jE;
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
        ek.a(this, paramParcel, paramInt);
      }
    }
    
    public static final class b
      extends an
      implements ae, Person.Cover.CoverPhoto
    {
      public static final el CREATOR = new el();
      private static final HashMap<String, an.a<?, ?>> hY;
      private final int T;
      private int gE;
      private int gF;
      private String hL;
      private final Set<Integer> hZ;
      
      static
      {
        HashMap localHashMap = new HashMap();
        hY = localHashMap;
        localHashMap.put("height", an.a.c("height", 2));
        hY.put("url", an.a.f("url", 3));
        hY.put("width", an.a.c("width", 4));
      }
      
      public b()
      {
        this.T = 1;
        this.hZ = new HashSet();
      }
      
      b(Set<Integer> paramSet, int paramInt1, int paramInt2, String paramString, int paramInt3)
      {
        this.hZ = paramSet;
        this.T = paramInt1;
        this.gF = paramInt2;
        this.hL = paramString;
        this.gE = paramInt3;
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
        default: 
          throw new IllegalStateException("Unknown safe parcelable id=" + parama.N());
        case 2: 
          return Integer.valueOf(this.gF);
        case 3: 
          return this.hL;
        }
        return Integer.valueOf(this.gE);
      }
      
      Set<Integer> bz()
      {
        return this.hZ;
      }
      
      public b cj()
      {
        return this;
      }
      
      public int describeContents()
      {
        return 0;
      }
      
      public boolean equals(Object paramObject)
      {
        if (!(paramObject instanceof b)) {
          return false;
        }
        if (this == paramObject) {
          return true;
        }
        b localb = (b)paramObject;
        Iterator localIterator = hY.values().iterator();
        while (localIterator.hasNext())
        {
          an.a locala = (an.a)localIterator.next();
          if (a(locala))
          {
            if (localb.a(locala))
            {
              if (!b(locala).equals(localb.b(locala))) {
                return false;
              }
            }
            else {
              return false;
            }
          }
          else if (localb.a(locala)) {
            return false;
          }
        }
        return true;
      }
      
      public int getHeight()
      {
        return this.gF;
      }
      
      public String getUrl()
      {
        return this.hL;
      }
      
      public int getWidth()
      {
        return this.gE;
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
        el.a(this, paramParcel, paramInt);
      }
    }
  }
  
  public static final class c
    extends an
    implements ae, Person.Emails
  {
    public static final em CREATOR = new em();
    private static final HashMap<String, an.a<?, ?>> hY;
    private final int T;
    private int bl;
    private final Set<Integer> hZ;
    private boolean jF;
    private String mValue;
    
    static
    {
      HashMap localHashMap = new HashMap();
      hY = localHashMap;
      localHashMap.put("primary", an.a.e("primary", 2));
      hY.put("type", an.a.a("type", 3, new ak().b("home", 0).b("work", 1).b("other", 2), false));
      hY.put("value", an.a.f("value", 4));
    }
    
    public c()
    {
      this.T = 1;
      this.hZ = new HashSet();
    }
    
    c(Set<Integer> paramSet, int paramInt1, boolean paramBoolean, int paramInt2, String paramString)
    {
      this.hZ = paramSet;
      this.T = paramInt1;
      this.jF = paramBoolean;
      this.bl = paramInt2;
      this.mValue = paramString;
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
      default: 
        throw new IllegalStateException("Unknown safe parcelable id=" + parama.N());
      case 2: 
        return Boolean.valueOf(this.jF);
      case 3: 
        return Integer.valueOf(this.bl);
      }
      return this.mValue;
    }
    
    Set<Integer> bz()
    {
      return this.hZ;
    }
    
    public c ck()
    {
      return this;
    }
    
    public int describeContents()
    {
      return 0;
    }
    
    public boolean equals(Object paramObject)
    {
      if (!(paramObject instanceof c)) {
        return false;
      }
      if (this == paramObject) {
        return true;
      }
      c localc = (c)paramObject;
      Iterator localIterator = hY.values().iterator();
      while (localIterator.hasNext())
      {
        an.a locala = (an.a)localIterator.next();
        if (a(locala))
        {
          if (localc.a(locala))
          {
            if (!b(locala).equals(localc.b(locala))) {
              return false;
            }
          }
          else {
            return false;
          }
        }
        else if (localc.a(locala)) {
          return false;
        }
      }
      return true;
    }
    
    public int getType()
    {
      return this.bl;
    }
    
    public String getValue()
    {
      return this.mValue;
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
    
    public boolean isPrimary()
    {
      return this.jF;
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
      em.a(this, paramParcel, paramInt);
    }
  }
  
  public static final class d
    extends an
    implements ae, Person.Image
  {
    public static final en CREATOR = new en();
    private static final HashMap<String, an.a<?, ?>> hY;
    private final int T;
    private String hL;
    private final Set<Integer> hZ;
    
    static
    {
      HashMap localHashMap = new HashMap();
      hY = localHashMap;
      localHashMap.put("url", an.a.f("url", 2));
    }
    
    public d()
    {
      this.T = 1;
      this.hZ = new HashSet();
    }
    
    d(Set<Integer> paramSet, int paramInt, String paramString)
    {
      this.hZ = paramSet;
      this.T = paramInt;
      this.hL = paramString;
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
      default: 
        throw new IllegalStateException("Unknown safe parcelable id=" + parama.N());
      }
      return this.hL;
    }
    
    Set<Integer> bz()
    {
      return this.hZ;
    }
    
    public d cl()
    {
      return this;
    }
    
    public int describeContents()
    {
      return 0;
    }
    
    public boolean equals(Object paramObject)
    {
      if (!(paramObject instanceof d)) {
        return false;
      }
      if (this == paramObject) {
        return true;
      }
      d locald = (d)paramObject;
      Iterator localIterator = hY.values().iterator();
      while (localIterator.hasNext())
      {
        an.a locala = (an.a)localIterator.next();
        if (a(locala))
        {
          if (locald.a(locala))
          {
            if (!b(locala).equals(locald.b(locala))) {
              return false;
            }
          }
          else {
            return false;
          }
        }
        else if (locald.a(locala)) {
          return false;
        }
      }
      return true;
    }
    
    public String getUrl()
    {
      return this.hL;
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
      en.a(this, paramParcel, paramInt);
    }
  }
  
  public static final class e
    extends an
    implements ae, Person.Name
  {
    public static final eo CREATOR = new eo();
    private static final HashMap<String, an.a<?, ?>> hY;
    private final int T;
    private final Set<Integer> hZ;
    private String iB;
    private String iy;
    private String jG;
    private String jH;
    private String jI;
    private String jJ;
    
    static
    {
      HashMap localHashMap = new HashMap();
      hY = localHashMap;
      localHashMap.put("familyName", an.a.f("familyName", 2));
      hY.put("formatted", an.a.f("formatted", 3));
      hY.put("givenName", an.a.f("givenName", 4));
      hY.put("honorificPrefix", an.a.f("honorificPrefix", 5));
      hY.put("honorificSuffix", an.a.f("honorificSuffix", 6));
      hY.put("middleName", an.a.f("middleName", 7));
    }
    
    public e()
    {
      this.T = 1;
      this.hZ = new HashSet();
    }
    
    e(Set<Integer> paramSet, int paramInt, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6)
    {
      this.hZ = paramSet;
      this.T = paramInt;
      this.iy = paramString1;
      this.jG = paramString2;
      this.iB = paramString3;
      this.jH = paramString4;
      this.jI = paramString5;
      this.jJ = paramString6;
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
      default: 
        throw new IllegalStateException("Unknown safe parcelable id=" + parama.N());
      case 2: 
        return this.iy;
      case 3: 
        return this.jG;
      case 4: 
        return this.iB;
      case 5: 
        return this.jH;
      case 6: 
        return this.jI;
      }
      return this.jJ;
    }
    
    Set<Integer> bz()
    {
      return this.hZ;
    }
    
    public e cm()
    {
      return this;
    }
    
    public int describeContents()
    {
      return 0;
    }
    
    public boolean equals(Object paramObject)
    {
      if (!(paramObject instanceof e)) {
        return false;
      }
      if (this == paramObject) {
        return true;
      }
      e locale = (e)paramObject;
      Iterator localIterator = hY.values().iterator();
      while (localIterator.hasNext())
      {
        an.a locala = (an.a)localIterator.next();
        if (a(locala))
        {
          if (locale.a(locala))
          {
            if (!b(locala).equals(locale.b(locala))) {
              return false;
            }
          }
          else {
            return false;
          }
        }
        else if (locale.a(locala)) {
          return false;
        }
      }
      return true;
    }
    
    public String getFamilyName()
    {
      return this.iy;
    }
    
    public String getFormatted()
    {
      return this.jG;
    }
    
    public String getGivenName()
    {
      return this.iB;
    }
    
    public String getHonorificPrefix()
    {
      return this.jH;
    }
    
    public String getHonorificSuffix()
    {
      return this.jI;
    }
    
    public String getMiddleName()
    {
      return this.jJ;
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
      eo.a(this, paramParcel, paramInt);
    }
  }
  
  public static final class g
    extends an
    implements ae, Person.Organizations
  {
    public static final ep CREATOR = new ep();
    private static final HashMap<String, an.a<?, ?>> hY;
    private final int T;
    private int bl;
    private String ck;
    private String gp;
    private final Set<Integer> hZ;
    private String iO;
    private String ix;
    private boolean jF;
    private String jK;
    private String jL;
    private String mName;
    
    static
    {
      HashMap localHashMap = new HashMap();
      hY = localHashMap;
      localHashMap.put("department", an.a.f("department", 2));
      hY.put("description", an.a.f("description", 3));
      hY.put("endDate", an.a.f("endDate", 4));
      hY.put("location", an.a.f("location", 5));
      hY.put("name", an.a.f("name", 6));
      hY.put("primary", an.a.e("primary", 7));
      hY.put("startDate", an.a.f("startDate", 8));
      hY.put("title", an.a.f("title", 9));
      hY.put("type", an.a.a("type", 10, new ak().b("work", 0).b("school", 1), false));
    }
    
    public g()
    {
      this.T = 1;
      this.hZ = new HashSet();
    }
    
    g(Set<Integer> paramSet, int paramInt1, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, boolean paramBoolean, String paramString6, String paramString7, int paramInt2)
    {
      this.hZ = paramSet;
      this.T = paramInt1;
      this.jK = paramString1;
      this.ck = paramString2;
      this.ix = paramString3;
      this.jL = paramString4;
      this.mName = paramString5;
      this.jF = paramBoolean;
      this.iO = paramString6;
      this.gp = paramString7;
      this.bl = paramInt2;
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
      default: 
        throw new IllegalStateException("Unknown safe parcelable id=" + parama.N());
      case 2: 
        return this.jK;
      case 3: 
        return this.ck;
      case 4: 
        return this.ix;
      case 5: 
        return this.jL;
      case 6: 
        return this.mName;
      case 7: 
        return Boolean.valueOf(this.jF);
      case 8: 
        return this.iO;
      case 9: 
        return this.gp;
      }
      return Integer.valueOf(this.bl);
    }
    
    Set<Integer> bz()
    {
      return this.hZ;
    }
    
    public g cn()
    {
      return this;
    }
    
    public int describeContents()
    {
      return 0;
    }
    
    public boolean equals(Object paramObject)
    {
      if (!(paramObject instanceof g)) {
        return false;
      }
      if (this == paramObject) {
        return true;
      }
      g localg = (g)paramObject;
      Iterator localIterator = hY.values().iterator();
      while (localIterator.hasNext())
      {
        an.a locala = (an.a)localIterator.next();
        if (a(locala))
        {
          if (localg.a(locala))
          {
            if (!b(locala).equals(localg.b(locala))) {
              return false;
            }
          }
          else {
            return false;
          }
        }
        else if (localg.a(locala)) {
          return false;
        }
      }
      return true;
    }
    
    public String getDepartment()
    {
      return this.jK;
    }
    
    public String getDescription()
    {
      return this.ck;
    }
    
    public String getEndDate()
    {
      return this.ix;
    }
    
    public String getLocation()
    {
      return this.jL;
    }
    
    public String getName()
    {
      return this.mName;
    }
    
    public String getStartDate()
    {
      return this.iO;
    }
    
    public String getTitle()
    {
      return this.gp;
    }
    
    public int getType()
    {
      return this.bl;
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
    
    public boolean isPrimary()
    {
      return this.jF;
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
      ep.a(this, paramParcel, paramInt);
    }
  }
  
  public static final class h
    extends an
    implements ae, Person.PlacesLived
  {
    public static final et CREATOR = new et();
    private static final HashMap<String, an.a<?, ?>> hY;
    private final int T;
    private final Set<Integer> hZ;
    private boolean jF;
    private String mValue;
    
    static
    {
      HashMap localHashMap = new HashMap();
      hY = localHashMap;
      localHashMap.put("primary", an.a.e("primary", 2));
      hY.put("value", an.a.f("value", 3));
    }
    
    public h()
    {
      this.T = 1;
      this.hZ = new HashSet();
    }
    
    h(Set<Integer> paramSet, int paramInt, boolean paramBoolean, String paramString)
    {
      this.hZ = paramSet;
      this.T = paramInt;
      this.jF = paramBoolean;
      this.mValue = paramString;
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
      default: 
        throw new IllegalStateException("Unknown safe parcelable id=" + parama.N());
      case 2: 
        return Boolean.valueOf(this.jF);
      }
      return this.mValue;
    }
    
    Set<Integer> bz()
    {
      return this.hZ;
    }
    
    public h co()
    {
      return this;
    }
    
    public int describeContents()
    {
      return 0;
    }
    
    public boolean equals(Object paramObject)
    {
      if (!(paramObject instanceof h)) {
        return false;
      }
      if (this == paramObject) {
        return true;
      }
      h localh = (h)paramObject;
      Iterator localIterator = hY.values().iterator();
      while (localIterator.hasNext())
      {
        an.a locala = (an.a)localIterator.next();
        if (a(locala))
        {
          if (localh.a(locala))
          {
            if (!b(locala).equals(localh.b(locala))) {
              return false;
            }
          }
          else {
            return false;
          }
        }
        else if (localh.a(locala)) {
          return false;
        }
      }
      return true;
    }
    
    public String getValue()
    {
      return this.mValue;
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
    
    public boolean isPrimary()
    {
      return this.jF;
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
      et.a(this, paramParcel, paramInt);
    }
  }
  
  public static final class i
    extends an
    implements ae, Person.Urls
  {
    public static final eu CREATOR = new eu();
    private static final HashMap<String, an.a<?, ?>> hY;
    private final int T;
    private int bl;
    private final Set<Integer> hZ;
    private boolean jF;
    private String mValue;
    
    static
    {
      HashMap localHashMap = new HashMap();
      hY = localHashMap;
      localHashMap.put("primary", an.a.e("primary", 2));
      hY.put("type", an.a.a("type", 3, new ak().b("home", 0).b("work", 1).b("blog", 2).b("profile", 3).b("other", 4), false));
      hY.put("value", an.a.f("value", 4));
    }
    
    public i()
    {
      this.T = 1;
      this.hZ = new HashSet();
    }
    
    i(Set<Integer> paramSet, int paramInt1, boolean paramBoolean, int paramInt2, String paramString)
    {
      this.hZ = paramSet;
      this.T = paramInt1;
      this.jF = paramBoolean;
      this.bl = paramInt2;
      this.mValue = paramString;
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
      default: 
        throw new IllegalStateException("Unknown safe parcelable id=" + parama.N());
      case 2: 
        return Boolean.valueOf(this.jF);
      case 3: 
        return Integer.valueOf(this.bl);
      }
      return this.mValue;
    }
    
    Set<Integer> bz()
    {
      return this.hZ;
    }
    
    public i cp()
    {
      return this;
    }
    
    public int describeContents()
    {
      return 0;
    }
    
    public boolean equals(Object paramObject)
    {
      if (!(paramObject instanceof i)) {
        return false;
      }
      if (this == paramObject) {
        return true;
      }
      i locali = (i)paramObject;
      Iterator localIterator = hY.values().iterator();
      while (localIterator.hasNext())
      {
        an.a locala = (an.a)localIterator.next();
        if (a(locala))
        {
          if (locali.a(locala))
          {
            if (!b(locala).equals(locali.b(locala))) {
              return false;
            }
          }
          else {
            return false;
          }
        }
        else if (locali.a(locala)) {
          return false;
        }
      }
      return true;
    }
    
    public int getType()
    {
      return this.bl;
    }
    
    public String getValue()
    {
      return this.mValue;
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
    
    public boolean isPrimary()
    {
      return this.jF;
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
      eu.a(this, paramParcel, paramInt);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.eq
 * JD-Core Version:    0.7.0.1
 */