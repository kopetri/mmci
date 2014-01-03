package com.touchtype_fluency.service.personalize;

import android.text.Html;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.touchtype.util.LogUtil;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class RssSearchEngine
{
  private static final List<RssSearchResult> NO_RESULTS = ;
  private static final String TAG = "RssSearchEngine";
  private final String feedSearchFormat;
  private final int maxResults;
  private final String siteSearchFormat;
  
  public RssSearchEngine(int paramInt, String paramString1, String paramString2)
  {
    this.maxResults = paramInt;
    this.feedSearchFormat = paramString1;
    this.siteSearchFormat = paramString2;
  }
  
  private RssSearchResult decodeSearchResult(JSONObject paramJSONObject)
    throws JSONException
  {
    return new RssSearchResult(fromHtml(paramJSONObject.getString("title")), fromHtml(paramJSONObject.getString("url")));
  }
  
  private String formatSearchString(String paramString1, String paramString2)
    throws UnsupportedEncodingException
  {
    return String.format(paramString1, new Object[] { URLEncoder.encode(paramString2, "UTF-8") });
  }
  
  private String fromHtml(String paramString)
  {
    return Html.fromHtml(paramString).toString();
  }
  
  private List<RssSearchResult> getSearchResults(String paramString1, String paramString2)
  {
    String str = null;
    try
    {
      str = Resources.toString(new URL(formatSearchString(paramString2, paramString1)), Charsets.UTF_8);
      List localList = parseSearchResponse(str);
      return localList;
    }
    catch (IOException localIOException)
    {
      LogUtil.e("RssSearchEngine", "Exception occurred while fetching search results: " + localIOException.getMessage(), localIOException);
      return NO_RESULTS;
    }
    catch (JSONException localJSONException)
    {
      LogUtil.e("RssSearchEngine", "Could not decode JSON search response " + str, localJSONException);
    }
    return NO_RESULTS;
  }
  
  private boolean isUrl(String paramString)
  {
    try
    {
      new URL(paramString);
      return true;
    }
    catch (MalformedURLException localMalformedURLException) {}
    return false;
  }
  
  private List<RssSearchResult> parseSearchResponse(String paramString)
    throws JSONException
  {
    JSONArray localJSONArray = new JSONObject(paramString).getJSONObject("responseData").getJSONArray("entries");
    int i = Math.min(localJSONArray.length(), this.maxResults);
    ArrayList localArrayList = new ArrayList(i);
    for (int j = 0; j < i; j++)
    {
      JSONObject localJSONObject = localJSONArray.getJSONObject(j);
      if (localJSONObject.getString("url").length() != 0) {
        localArrayList.add(decodeSearchResult(localJSONObject));
      }
    }
    return localArrayList;
  }
  
  private List<RssSearchResult> resultsFromFeed(String paramString)
  {
    try
    {
      List localList = singleItemResultsList(paramString, new BasicFeedParser(Resources.toString(new URL(paramString), Charsets.UTF_8), null).getTitle());
      return localList;
    }
    catch (IOException localIOException)
    {
      return new ArrayList();
    }
    catch (ParserConfigurationException localParserConfigurationException)
    {
      break label37;
    }
    catch (SAXException localSAXException)
    {
      label37:
      break label37;
    }
  }
  
  private List<RssSearchResult> resultsFromSearchTerm(String paramString)
  {
    return getSearchResults(paramString, this.feedSearchFormat);
  }
  
  private List<RssSearchResult> resultsFromSiteSearch(String paramString)
  {
    return getSearchResults(paramString, this.siteSearchFormat);
  }
  
  private List<RssSearchResult> singleItemResultsList(String paramString1, String paramString2)
  {
    RssSearchResult localRssSearchResult = new RssSearchResult(paramString2, paramString1);
    ArrayList localArrayList = new ArrayList(1);
    localArrayList.add(localRssSearchResult);
    return localArrayList;
  }
  
  public List<RssSearchResult> getSearchResults(String paramString)
  {
    if (isUrl(paramString)) {}
    for (List localList = resultsFromFeed(paramString);; localList = resultsFromSearchTerm(paramString))
    {
      if (localList.isEmpty()) {
        localList = resultsFromSiteSearch(paramString);
      }
      return localList;
    }
  }
  
  private static final class BasicFeedParser
  {
    public static final String DEFAULT_TITLE = "";
    private boolean bTitle;
    private String title = "";
    
    private BasicFeedParser(String paramString)
      throws SAXException, ParserConfigurationException, IOException
    {
      SAXParserFactory.newInstance().newSAXParser().parse(new InputSource(new StringReader(paramString)), new DefaultHandler()
      {
        public void characters(char[] paramAnonymousArrayOfChar, int paramAnonymousInt1, int paramAnonymousInt2)
          throws SAXException
        {
          if (RssSearchEngine.BasicFeedParser.this.bTitle)
          {
            RssSearchEngine.BasicFeedParser.access$202(RssSearchEngine.BasicFeedParser.this, new String(paramAnonymousArrayOfChar, paramAnonymousInt1, paramAnonymousInt2));
            RssSearchEngine.BasicFeedParser.access$102(RssSearchEngine.BasicFeedParser.this, false);
          }
        }
        
        public void startElement(String paramAnonymousString1, String paramAnonymousString2, String paramAnonymousString3, Attributes paramAnonymousAttributes)
          throws SAXException
        {
          if (paramAnonymousString3.equalsIgnoreCase("title")) {
            RssSearchEngine.BasicFeedParser.access$102(RssSearchEngine.BasicFeedParser.this, true);
          }
        }
      });
    }
    
    public String getTitle()
    {
      return this.title.trim();
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.personalize.RssSearchEngine
 * JD-Core Version:    0.7.0.1
 */