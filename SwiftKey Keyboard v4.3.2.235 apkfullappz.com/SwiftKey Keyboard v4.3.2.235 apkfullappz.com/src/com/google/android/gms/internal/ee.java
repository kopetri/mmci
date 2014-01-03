package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public final class ee
  implements Parcelable.Creator<ed>
{
  static void a(ed paramed, Parcel paramParcel, int paramInt)
  {
    int i = ad.d(paramParcel);
    Set localSet = paramed.bz();
    if (localSet.contains(Integer.valueOf(1))) {
      ad.c(paramParcel, 1, paramed.u());
    }
    if (localSet.contains(Integer.valueOf(2))) {
      ad.a(paramParcel, 2, paramed.bA(), paramInt, true);
    }
    if (localSet.contains(Integer.valueOf(3))) {
      ad.a(paramParcel, 3, paramed.getAdditionalName(), true);
    }
    if (localSet.contains(Integer.valueOf(4))) {
      ad.a(paramParcel, 4, paramed.bB(), paramInt, true);
    }
    if (localSet.contains(Integer.valueOf(5))) {
      ad.a(paramParcel, 5, paramed.getAddressCountry(), true);
    }
    if (localSet.contains(Integer.valueOf(6))) {
      ad.a(paramParcel, 6, paramed.getAddressLocality(), true);
    }
    if (localSet.contains(Integer.valueOf(7))) {
      ad.a(paramParcel, 7, paramed.getAddressRegion(), true);
    }
    if (localSet.contains(Integer.valueOf(8))) {
      ad.b(paramParcel, 8, paramed.bC(), true);
    }
    if (localSet.contains(Integer.valueOf(9))) {
      ad.c(paramParcel, 9, paramed.getAttendeeCount());
    }
    if (localSet.contains(Integer.valueOf(10))) {
      ad.b(paramParcel, 10, paramed.bD(), true);
    }
    if (localSet.contains(Integer.valueOf(11))) {
      ad.a(paramParcel, 11, paramed.bE(), paramInt, true);
    }
    if (localSet.contains(Integer.valueOf(12))) {
      ad.b(paramParcel, 12, paramed.bF(), true);
    }
    if (localSet.contains(Integer.valueOf(13))) {
      ad.a(paramParcel, 13, paramed.getBestRating(), true);
    }
    if (localSet.contains(Integer.valueOf(14))) {
      ad.a(paramParcel, 14, paramed.getBirthDate(), true);
    }
    if (localSet.contains(Integer.valueOf(15))) {
      ad.a(paramParcel, 15, paramed.bG(), paramInt, true);
    }
    if (localSet.contains(Integer.valueOf(17))) {
      ad.a(paramParcel, 17, paramed.getContentSize(), true);
    }
    if (localSet.contains(Integer.valueOf(16))) {
      ad.a(paramParcel, 16, paramed.getCaption(), true);
    }
    if (localSet.contains(Integer.valueOf(19))) {
      ad.b(paramParcel, 19, paramed.bH(), true);
    }
    if (localSet.contains(Integer.valueOf(18))) {
      ad.a(paramParcel, 18, paramed.getContentUrl(), true);
    }
    if (localSet.contains(Integer.valueOf(21))) {
      ad.a(paramParcel, 21, paramed.getDateModified(), true);
    }
    if (localSet.contains(Integer.valueOf(20))) {
      ad.a(paramParcel, 20, paramed.getDateCreated(), true);
    }
    if (localSet.contains(Integer.valueOf(23))) {
      ad.a(paramParcel, 23, paramed.getDescription(), true);
    }
    if (localSet.contains(Integer.valueOf(22))) {
      ad.a(paramParcel, 22, paramed.getDatePublished(), true);
    }
    if (localSet.contains(Integer.valueOf(25))) {
      ad.a(paramParcel, 25, paramed.getEmbedUrl(), true);
    }
    if (localSet.contains(Integer.valueOf(24))) {
      ad.a(paramParcel, 24, paramed.getDuration(), true);
    }
    if (localSet.contains(Integer.valueOf(27))) {
      ad.a(paramParcel, 27, paramed.getFamilyName(), true);
    }
    if (localSet.contains(Integer.valueOf(26))) {
      ad.a(paramParcel, 26, paramed.getEndDate(), true);
    }
    if (localSet.contains(Integer.valueOf(29))) {
      ad.a(paramParcel, 29, paramed.bI(), paramInt, true);
    }
    if (localSet.contains(Integer.valueOf(28))) {
      ad.a(paramParcel, 28, paramed.getGender(), true);
    }
    if (localSet.contains(Integer.valueOf(31))) {
      ad.a(paramParcel, 31, paramed.getHeight(), true);
    }
    if (localSet.contains(Integer.valueOf(30))) {
      ad.a(paramParcel, 30, paramed.getGivenName(), true);
    }
    if (localSet.contains(Integer.valueOf(34))) {
      ad.a(paramParcel, 34, paramed.bJ(), paramInt, true);
    }
    if (localSet.contains(Integer.valueOf(32))) {
      ad.a(paramParcel, 32, paramed.getId(), true);
    }
    if (localSet.contains(Integer.valueOf(33))) {
      ad.a(paramParcel, 33, paramed.getImage(), true);
    }
    if (localSet.contains(Integer.valueOf(38))) {
      ad.a(paramParcel, 38, paramed.getLongitude());
    }
    if (localSet.contains(Integer.valueOf(39))) {
      ad.a(paramParcel, 39, paramed.getName(), true);
    }
    if (localSet.contains(Integer.valueOf(36))) {
      ad.a(paramParcel, 36, paramed.getLatitude());
    }
    if (localSet.contains(Integer.valueOf(37))) {
      ad.a(paramParcel, 37, paramed.bK(), paramInt, true);
    }
    if (localSet.contains(Integer.valueOf(42))) {
      ad.a(paramParcel, 42, paramed.getPlayerType(), true);
    }
    if (localSet.contains(Integer.valueOf(43))) {
      ad.a(paramParcel, 43, paramed.getPostOfficeBoxNumber(), true);
    }
    if (localSet.contains(Integer.valueOf(40))) {
      ad.a(paramParcel, 40, paramed.bL(), paramInt, true);
    }
    if (localSet.contains(Integer.valueOf(41))) {
      ad.b(paramParcel, 41, paramed.bM(), true);
    }
    if (localSet.contains(Integer.valueOf(46))) {
      ad.a(paramParcel, 46, paramed.bN(), paramInt, true);
    }
    if (localSet.contains(Integer.valueOf(47))) {
      ad.a(paramParcel, 47, paramed.getStartDate(), true);
    }
    if (localSet.contains(Integer.valueOf(44))) {
      ad.a(paramParcel, 44, paramed.getPostalCode(), true);
    }
    if (localSet.contains(Integer.valueOf(45))) {
      ad.a(paramParcel, 45, paramed.getRatingValue(), true);
    }
    if (localSet.contains(Integer.valueOf(51))) {
      ad.a(paramParcel, 51, paramed.getThumbnailUrl(), true);
    }
    if (localSet.contains(Integer.valueOf(50))) {
      ad.a(paramParcel, 50, paramed.bO(), paramInt, true);
    }
    if (localSet.contains(Integer.valueOf(49))) {
      ad.a(paramParcel, 49, paramed.getText(), true);
    }
    if (localSet.contains(Integer.valueOf(48))) {
      ad.a(paramParcel, 48, paramed.getStreetAddress(), true);
    }
    if (localSet.contains(Integer.valueOf(55))) {
      ad.a(paramParcel, 55, paramed.getWidth(), true);
    }
    if (localSet.contains(Integer.valueOf(54))) {
      ad.a(paramParcel, 54, paramed.getUrl(), true);
    }
    if (localSet.contains(Integer.valueOf(53))) {
      ad.a(paramParcel, 53, paramed.getType(), true);
    }
    if (localSet.contains(Integer.valueOf(52))) {
      ad.a(paramParcel, 52, paramed.getTickerSymbol(), true);
    }
    if (localSet.contains(Integer.valueOf(56))) {
      ad.a(paramParcel, 56, paramed.getWorstRating(), true);
    }
    ad.C(paramParcel, i);
  }
  
  public ed[] Q(int paramInt)
  {
    return new ed[paramInt];
  }
  
  public ed v(Parcel paramParcel)
  {
    int i = ac.c(paramParcel);
    HashSet localHashSet = new HashSet();
    int j = 0;
    Object localObject1 = null;
    ArrayList localArrayList1 = null;
    Object localObject2 = null;
    String str1 = null;
    String str2 = null;
    String str3 = null;
    ArrayList localArrayList2 = null;
    int k = 0;
    ArrayList localArrayList3 = null;
    Object localObject3 = null;
    ArrayList localArrayList4 = null;
    String str4 = null;
    String str5 = null;
    Object localObject4 = null;
    String str6 = null;
    String str7 = null;
    String str8 = null;
    ArrayList localArrayList5 = null;
    String str9 = null;
    String str10 = null;
    String str11 = null;
    String str12 = null;
    String str13 = null;
    String str14 = null;
    String str15 = null;
    String str16 = null;
    String str17 = null;
    Object localObject5 = null;
    String str18 = null;
    String str19 = null;
    String str20 = null;
    String str21 = null;
    Object localObject6 = null;
    double d1 = 0.0D;
    Object localObject7 = null;
    double d2 = 0.0D;
    String str22 = null;
    Object localObject8 = null;
    ArrayList localArrayList6 = null;
    String str23 = null;
    String str24 = null;
    String str25 = null;
    String str26 = null;
    Object localObject9 = null;
    String str27 = null;
    String str28 = null;
    String str29 = null;
    Object localObject10 = null;
    String str30 = null;
    String str31 = null;
    String str32 = null;
    String str33 = null;
    String str34 = null;
    String str35 = null;
    while (paramParcel.dataPosition() < i)
    {
      int m = ac.b(paramParcel);
      switch (ac.j(m))
      {
      case 35: 
      default: 
        ac.b(paramParcel, m);
        break;
      case 1: 
        j = ac.f(paramParcel, m);
        localHashSet.add(Integer.valueOf(1));
        break;
      case 2: 
        ed localed10 = (ed)ac.a(paramParcel, m, ed.CREATOR);
        localHashSet.add(Integer.valueOf(2));
        localObject1 = localed10;
        break;
      case 3: 
        localArrayList1 = ac.x(paramParcel, m);
        localHashSet.add(Integer.valueOf(3));
        break;
      case 4: 
        ed localed9 = (ed)ac.a(paramParcel, m, ed.CREATOR);
        localHashSet.add(Integer.valueOf(4));
        localObject2 = localed9;
        break;
      case 5: 
        str1 = ac.l(paramParcel, m);
        localHashSet.add(Integer.valueOf(5));
        break;
      case 6: 
        str2 = ac.l(paramParcel, m);
        localHashSet.add(Integer.valueOf(6));
        break;
      case 7: 
        str3 = ac.l(paramParcel, m);
        localHashSet.add(Integer.valueOf(7));
        break;
      case 8: 
        localArrayList2 = ac.c(paramParcel, m, ed.CREATOR);
        localHashSet.add(Integer.valueOf(8));
        break;
      case 9: 
        k = ac.f(paramParcel, m);
        localHashSet.add(Integer.valueOf(9));
        break;
      case 10: 
        localArrayList3 = ac.c(paramParcel, m, ed.CREATOR);
        localHashSet.add(Integer.valueOf(10));
        break;
      case 11: 
        ed localed8 = (ed)ac.a(paramParcel, m, ed.CREATOR);
        localHashSet.add(Integer.valueOf(11));
        localObject3 = localed8;
        break;
      case 12: 
        localArrayList4 = ac.c(paramParcel, m, ed.CREATOR);
        localHashSet.add(Integer.valueOf(12));
        break;
      case 13: 
        str4 = ac.l(paramParcel, m);
        localHashSet.add(Integer.valueOf(13));
        break;
      case 14: 
        str5 = ac.l(paramParcel, m);
        localHashSet.add(Integer.valueOf(14));
        break;
      case 15: 
        ed localed7 = (ed)ac.a(paramParcel, m, ed.CREATOR);
        localHashSet.add(Integer.valueOf(15));
        localObject4 = localed7;
        break;
      case 17: 
        str7 = ac.l(paramParcel, m);
        localHashSet.add(Integer.valueOf(17));
        break;
      case 16: 
        str6 = ac.l(paramParcel, m);
        localHashSet.add(Integer.valueOf(16));
        break;
      case 19: 
        localArrayList5 = ac.c(paramParcel, m, ed.CREATOR);
        localHashSet.add(Integer.valueOf(19));
        break;
      case 18: 
        str8 = ac.l(paramParcel, m);
        localHashSet.add(Integer.valueOf(18));
        break;
      case 21: 
        str10 = ac.l(paramParcel, m);
        localHashSet.add(Integer.valueOf(21));
        break;
      case 20: 
        str9 = ac.l(paramParcel, m);
        localHashSet.add(Integer.valueOf(20));
        break;
      case 23: 
        str12 = ac.l(paramParcel, m);
        localHashSet.add(Integer.valueOf(23));
        break;
      case 22: 
        str11 = ac.l(paramParcel, m);
        localHashSet.add(Integer.valueOf(22));
        break;
      case 25: 
        str14 = ac.l(paramParcel, m);
        localHashSet.add(Integer.valueOf(25));
        break;
      case 24: 
        str13 = ac.l(paramParcel, m);
        localHashSet.add(Integer.valueOf(24));
        break;
      case 27: 
        str16 = ac.l(paramParcel, m);
        localHashSet.add(Integer.valueOf(27));
        break;
      case 26: 
        str15 = ac.l(paramParcel, m);
        localHashSet.add(Integer.valueOf(26));
        break;
      case 29: 
        ed localed6 = (ed)ac.a(paramParcel, m, ed.CREATOR);
        localHashSet.add(Integer.valueOf(29));
        localObject5 = localed6;
        break;
      case 28: 
        str17 = ac.l(paramParcel, m);
        localHashSet.add(Integer.valueOf(28));
        break;
      case 31: 
        str19 = ac.l(paramParcel, m);
        localHashSet.add(Integer.valueOf(31));
        break;
      case 30: 
        str18 = ac.l(paramParcel, m);
        localHashSet.add(Integer.valueOf(30));
        break;
      case 34: 
        ed localed5 = (ed)ac.a(paramParcel, m, ed.CREATOR);
        localHashSet.add(Integer.valueOf(34));
        localObject6 = localed5;
        break;
      case 32: 
        str20 = ac.l(paramParcel, m);
        localHashSet.add(Integer.valueOf(32));
        break;
      case 33: 
        str21 = ac.l(paramParcel, m);
        localHashSet.add(Integer.valueOf(33));
        break;
      case 38: 
        d2 = ac.j(paramParcel, m);
        localHashSet.add(Integer.valueOf(38));
        break;
      case 39: 
        str22 = ac.l(paramParcel, m);
        localHashSet.add(Integer.valueOf(39));
        break;
      case 36: 
        d1 = ac.j(paramParcel, m);
        localHashSet.add(Integer.valueOf(36));
        break;
      case 37: 
        ed localed4 = (ed)ac.a(paramParcel, m, ed.CREATOR);
        localHashSet.add(Integer.valueOf(37));
        localObject7 = localed4;
        break;
      case 42: 
        str23 = ac.l(paramParcel, m);
        localHashSet.add(Integer.valueOf(42));
        break;
      case 43: 
        str24 = ac.l(paramParcel, m);
        localHashSet.add(Integer.valueOf(43));
        break;
      case 40: 
        ed localed3 = (ed)ac.a(paramParcel, m, ed.CREATOR);
        localHashSet.add(Integer.valueOf(40));
        localObject8 = localed3;
        break;
      case 41: 
        localArrayList6 = ac.c(paramParcel, m, ed.CREATOR);
        localHashSet.add(Integer.valueOf(41));
        break;
      case 46: 
        ed localed2 = (ed)ac.a(paramParcel, m, ed.CREATOR);
        localHashSet.add(Integer.valueOf(46));
        localObject9 = localed2;
        break;
      case 47: 
        str27 = ac.l(paramParcel, m);
        localHashSet.add(Integer.valueOf(47));
        break;
      case 44: 
        str25 = ac.l(paramParcel, m);
        localHashSet.add(Integer.valueOf(44));
        break;
      case 45: 
        str26 = ac.l(paramParcel, m);
        localHashSet.add(Integer.valueOf(45));
        break;
      case 51: 
        str30 = ac.l(paramParcel, m);
        localHashSet.add(Integer.valueOf(51));
        break;
      case 50: 
        ed localed1 = (ed)ac.a(paramParcel, m, ed.CREATOR);
        localHashSet.add(Integer.valueOf(50));
        localObject10 = localed1;
        break;
      case 49: 
        str29 = ac.l(paramParcel, m);
        localHashSet.add(Integer.valueOf(49));
        break;
      case 48: 
        str28 = ac.l(paramParcel, m);
        localHashSet.add(Integer.valueOf(48));
        break;
      case 55: 
        str34 = ac.l(paramParcel, m);
        localHashSet.add(Integer.valueOf(55));
        break;
      case 54: 
        str33 = ac.l(paramParcel, m);
        localHashSet.add(Integer.valueOf(54));
        break;
      case 53: 
        str32 = ac.l(paramParcel, m);
        localHashSet.add(Integer.valueOf(53));
        break;
      case 52: 
        str31 = ac.l(paramParcel, m);
        localHashSet.add(Integer.valueOf(52));
        break;
      case 56: 
        str35 = ac.l(paramParcel, m);
        localHashSet.add(Integer.valueOf(56));
      }
    }
    if (paramParcel.dataPosition() != i) {
      throw new ac.a("Overread allowed size end=" + i, paramParcel);
    }
    return new ed(localHashSet, j, localObject1, localArrayList1, localObject2, str1, str2, str3, localArrayList2, k, localArrayList3, localObject3, localArrayList4, str4, str5, localObject4, str6, str7, str8, localArrayList5, str9, str10, str11, str12, str13, str14, str15, str16, str17, localObject5, str18, str19, str20, str21, localObject6, d1, localObject7, d2, str22, localObject8, localArrayList6, str23, str24, str25, str26, localObject9, str27, str28, str29, localObject10, str30, str31, str32, str33, str34, str35);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.ee
 * JD-Core Version:    0.7.0.1
 */