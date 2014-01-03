package com.touchtype_fluency.service.personalize;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.touchtype.util.LogUtil;
import java.util.Collections;
import java.util.List;

public class RssSearchActivity
  extends Activity
{
  private static final String FEED_SEARCH_FORMAT = "http://ajax.googleapis.com/ajax/services/feed/find?v=1.0&q=%s";
  private static final int MAX_RESULTS = 10;
  private static final String SITE_SEARCH_FORMAT = "http://ajax.googleapis.com/ajax/services/feed/find?v=1.0&q=site:%s";
  private static final String TAG = "RssSearchActivity";
  private View loadingView;
  private ListView resultsList;
  private View resultsView;
  private Button searchButton;
  private final View.OnClickListener searchButtonClickAction = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      RssSearchActivity.this.performSearch();
    }
  };
  private final RssSearchEngine searchEngine = new RssSearchEngine(10, "http://ajax.googleapis.com/ajax/services/feed/find?v=1.0&q=%s", "http://ajax.googleapis.com/ajax/services/feed/find?v=1.0&q=site:%s");
  private TextView searchField;
  private final View.OnKeyListener searchFieldClickAction = new View.OnKeyListener()
  {
    public boolean onKey(View paramAnonymousView, int paramAnonymousInt, KeyEvent paramAnonymousKeyEvent)
    {
      if ((paramAnonymousKeyEvent.getAction() == 0) && (paramAnonymousInt == 66))
      {
        RssSearchActivity.this.performSearch();
        return true;
      }
      return false;
    }
  };
  private final AdapterView.OnItemClickListener searchResultsClickAction = new AdapterView.OnItemClickListener()
  {
    public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
    {
      RssSearchResult localRssSearchResult = (RssSearchResult)paramAnonymousAdapterView.getItemAtPosition(paramAnonymousInt);
      if (localRssSearchResult != null)
      {
        RssSearchActivity.this.finishWithSuccess(localRssSearchResult.getUrl());
        return;
      }
      LogUtil.e("RssSearchActivity", "Could not extract url.");
      RssSearchActivity.this.finish();
    }
  };
  
  private static PostParams createParams(String paramString)
  {
    PostParams localPostParams = new PostParams();
    localPostParams.add("url", paramString);
    return localPostParams;
  }
  
  private void hideKeyboard()
  {
    this.searchField.clearFocus();
    ((InputMethodManager)getSystemService("input_method")).hideSoftInputFromWindow(this.searchField.getWindowToken(), 0);
  }
  
  private void performSearch()
  {
    showLoading();
    String str = this.searchField.getText().toString();
    new SearchAsyncTask(null).execute(new String[] { str });
    hideKeyboard();
  }
  
  private void showLoading()
  {
    this.resultsView.setVisibility(8);
    this.loadingView.setVisibility(0);
  }
  
  private void showResults()
  {
    this.loadingView.setVisibility(8);
    this.resultsView.setVisibility(0);
  }
  
  public void finish()
  {
    setResult(0, null);
    super.finish();
  }
  
  public void finishWithSuccess(String paramString)
  {
    Intent localIntent = new Intent();
    localIntent.putExtra("params", createParams(paramString).toString());
    setResult(-1, localIntent);
    super.finish();
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903111);
    this.searchButton = ((Button)findViewById(2131230918));
    this.searchField = ((TextView)findViewById(2131230919));
    this.resultsView = findViewById(2131230920);
    this.resultsList = ((ListView)findViewById(2131230921));
    this.loadingView = findViewById(2131230923);
    TextView localTextView = (TextView)findViewById(2131230922);
    getWindow().setLayout(-2, -2);
    getWindow().setSoftInputMode(4);
    setTitle(PersonalizationUtils.getPersonalizationDialogTitle(getResources()));
    this.resultsList.setOnItemClickListener(this.searchResultsClickAction);
    this.resultsList.setEmptyView(localTextView);
    this.searchButton.setOnClickListener(this.searchButtonClickAction);
    this.searchField.setOnKeyListener(this.searchFieldClickAction);
  }
  
  private class SearchAsyncTask
    extends AsyncTask<String, Void, List<RssSearchResult>>
  {
    private SearchAsyncTask() {}
    
    protected List<RssSearchResult> doInBackground(String... paramVarArgs)
    {
      if (paramVarArgs.length == 1)
      {
        String str = paramVarArgs[0];
        return RssSearchActivity.this.searchEngine.getSearchResults(str);
      }
      LogUtil.e("RssSearchActivity", "Invalid number of search terms provided: " + paramVarArgs.length);
      return Collections.emptyList();
    }
    
    protected void onPostExecute(List<RssSearchResult> paramList)
    {
      RssSearchActivity.this.resultsList.setAdapter(new SimpleAdapter(RssSearchActivity.this, paramList, 2130903112, new String[] { "title", "url" }, new int[] { 2131230924, 2131230925 }));
      RssSearchActivity.this.showResults();
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.personalize.RssSearchActivity
 * JD-Core Version:    0.7.0.1
 */