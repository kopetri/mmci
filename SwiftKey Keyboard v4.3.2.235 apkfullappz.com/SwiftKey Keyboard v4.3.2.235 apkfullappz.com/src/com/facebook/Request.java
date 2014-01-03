package com.facebook;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.location.Location;
import android.net.Uri.Builder;
import android.os.Bundle;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.util.Pair;
import com.facebook.internal.Logger;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import com.facebook.model.GraphMultiResult;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphObjectList;
import com.facebook.model.GraphPlace;
import com.facebook.model.GraphUser;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Request
{
  private static final String ACCESS_TOKEN_PARAM = "access_token";
  private static final String ATTACHED_FILES_PARAM = "attached_files";
  private static final String ATTACHMENT_FILENAME_PREFIX = "file";
  private static final String BATCH_APP_ID_PARAM = "batch_app_id";
  private static final String BATCH_BODY_PARAM = "body";
  private static final String BATCH_ENTRY_DEPENDS_ON_PARAM = "depends_on";
  private static final String BATCH_ENTRY_NAME_PARAM = "name";
  private static final String BATCH_ENTRY_OMIT_RESPONSE_ON_SUCCESS_PARAM = "omit_response_on_success";
  private static final String BATCH_METHOD_PARAM = "method";
  private static final String BATCH_PARAM = "batch";
  private static final String BATCH_RELATIVE_URL_PARAM = "relative_url";
  private static final String CONTENT_TYPE_HEADER = "Content-Type";
  private static final String FORMAT_JSON = "json";
  private static final String FORMAT_PARAM = "format";
  private static final String ISO_8601_FORMAT_STRING = "yyyy-MM-dd'T'HH:mm:ssZ";
  public static final int MAXIMUM_BATCH_SIZE = 50;
  private static final String ME = "me";
  private static final String MIGRATION_BUNDLE_PARAM = "migration_bundle";
  private static final String MIME_BOUNDARY = "3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f";
  private static final String MY_FEED = "me/feed";
  private static final String MY_FRIENDS = "me/friends";
  private static final String MY_PHOTOS = "me/photos";
  private static final String MY_VIDEOS = "me/videos";
  private static final String PICTURE_PARAM = "picture";
  private static final String SDK_ANDROID = "android";
  private static final String SDK_PARAM = "sdk";
  private static final String SEARCH = "search";
  private static final String USER_AGENT_BASE = "FBAndroidSDK";
  private static final String USER_AGENT_HEADER = "User-Agent";
  private static String defaultBatchApplicationId;
  private static volatile String userAgent;
  private String batchEntryDependsOn;
  private String batchEntryName;
  private boolean batchEntryOmitResultOnSuccess = true;
  private Callback callback;
  private GraphObject graphObject;
  private String graphPath;
  private HttpMethod httpMethod;
  private String overriddenURL;
  private Bundle parameters;
  private String restMethod;
  private Session session;
  
  public Request()
  {
    this(null, null, null, null, null);
  }
  
  public Request(Session paramSession, String paramString)
  {
    this(paramSession, paramString, null, null, null);
  }
  
  public Request(Session paramSession, String paramString, Bundle paramBundle, HttpMethod paramHttpMethod)
  {
    this(paramSession, paramString, paramBundle, paramHttpMethod, null);
  }
  
  public Request(Session paramSession, String paramString, Bundle paramBundle, HttpMethod paramHttpMethod, Callback paramCallback)
  {
    this.session = paramSession;
    this.graphPath = paramString;
    this.callback = paramCallback;
    setHttpMethod(paramHttpMethod);
    if (paramBundle != null) {}
    for (this.parameters = new Bundle(paramBundle);; this.parameters = new Bundle())
    {
      if (!this.parameters.containsKey("migration_bundle")) {
        this.parameters.putString("migration_bundle", "fbsdk:20121026");
      }
      return;
    }
  }
  
  Request(Session paramSession, URL paramURL)
  {
    this.session = paramSession;
    this.overriddenURL = paramURL.toString();
    setHttpMethod(HttpMethod.GET);
    this.parameters = new Bundle();
  }
  
  private void addCommonParameters()
  {
    if (this.session != null)
    {
      if (!this.session.isOpened()) {
        throw new FacebookException("Session provided to a Request in un-opened state.");
      }
      if (!this.parameters.containsKey("access_token"))
      {
        String str = this.session.getAccessToken();
        Logger.registerAccessToken(str);
        this.parameters.putString("access_token", str);
      }
    }
    this.parameters.putString("sdk", "android");
    this.parameters.putString("format", "json");
  }
  
  private String appendParametersToBaseUrl(String paramString)
  {
    Uri.Builder localBuilder = new Uri.Builder().encodedPath(paramString);
    Iterator localIterator = this.parameters.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      Object localObject = this.parameters.get(str);
      if (localObject == null) {
        localObject = "";
      }
      if (isSupportedParameterType(localObject))
      {
        localBuilder.appendQueryParameter(str, parameterToString(localObject).toString());
      }
      else if (this.httpMethod == HttpMethod.GET)
      {
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = localObject.getClass().getSimpleName();
        throw new IllegalArgumentException(String.format("Unsupported parameter type for GET request: %s", arrayOfObject));
      }
    }
    return localBuilder.toString();
  }
  
  static HttpURLConnection createConnection(URL paramURL)
    throws IOException
  {
    HttpURLConnection localHttpURLConnection = (HttpURLConnection)paramURL.openConnection();
    localHttpURLConnection.setRequestProperty("User-Agent", getUserAgent());
    localHttpURLConnection.setRequestProperty("Content-Type", getMimeContentType());
    localHttpURLConnection.setChunkedStreamingMode(0);
    return localHttpURLConnection;
  }
  
  public static Response executeAndWait(Request paramRequest)
  {
    List localList = executeBatchAndWait(new Request[] { paramRequest });
    if ((localList == null) || (localList.size() != 1)) {
      throw new FacebookException("invalid state: expected a single response");
    }
    return (Response)localList.get(0);
  }
  
  public static List<Response> executeBatchAndWait(RequestBatch paramRequestBatch)
  {
    Validate.notEmptyAndContainsNoNulls(paramRequestBatch, "requests");
    try
    {
      HttpURLConnection localHttpURLConnection = toHttpConnection(paramRequestBatch);
      return executeConnectionAndWait(localHttpURLConnection, paramRequestBatch);
    }
    catch (Exception localException)
    {
      List localList = Response.constructErrorResponses(paramRequestBatch.getRequests(), null, new FacebookException(localException));
      runCallbacks(paramRequestBatch, localList);
      return localList;
    }
  }
  
  public static List<Response> executeBatchAndWait(Collection<Request> paramCollection)
  {
    return executeBatchAndWait(new RequestBatch(paramCollection));
  }
  
  public static List<Response> executeBatchAndWait(Request... paramVarArgs)
  {
    Validate.notNull(paramVarArgs, "requests");
    return executeBatchAndWait(Arrays.asList(paramVarArgs));
  }
  
  public static RequestAsyncTask executeBatchAsync(RequestBatch paramRequestBatch)
  {
    Validate.notEmptyAndContainsNoNulls(paramRequestBatch, "requests");
    RequestAsyncTask localRequestAsyncTask = new RequestAsyncTask(paramRequestBatch);
    localRequestAsyncTask.executeOnSettingsExecutor();
    return localRequestAsyncTask;
  }
  
  public static RequestAsyncTask executeBatchAsync(Collection<Request> paramCollection)
  {
    return executeBatchAsync(new RequestBatch(paramCollection));
  }
  
  public static RequestAsyncTask executeBatchAsync(Request... paramVarArgs)
  {
    Validate.notNull(paramVarArgs, "requests");
    return executeBatchAsync(Arrays.asList(paramVarArgs));
  }
  
  public static List<Response> executeConnectionAndWait(HttpURLConnection paramHttpURLConnection, RequestBatch paramRequestBatch)
  {
    List localList = Response.fromHttpConnection(paramHttpURLConnection, paramRequestBatch);
    Utility.disconnectQuietly(paramHttpURLConnection);
    int i = paramRequestBatch.size();
    if (i != localList.size())
    {
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = Integer.valueOf(localList.size());
      arrayOfObject[1] = Integer.valueOf(i);
      throw new FacebookException(String.format("Received %d responses while expecting %d", arrayOfObject));
    }
    runCallbacks(paramRequestBatch, localList);
    HashSet localHashSet = new HashSet();
    Iterator localIterator1 = paramRequestBatch.iterator();
    while (localIterator1.hasNext())
    {
      Request localRequest = (Request)localIterator1.next();
      if (localRequest.session != null) {
        localHashSet.add(localRequest.session);
      }
    }
    Iterator localIterator2 = localHashSet.iterator();
    while (localIterator2.hasNext()) {
      ((Session)localIterator2.next()).extendAccessTokenIfNeeded();
    }
    return localList;
  }
  
  public static List<Response> executeConnectionAndWait(HttpURLConnection paramHttpURLConnection, Collection<Request> paramCollection)
  {
    return executeConnectionAndWait(paramHttpURLConnection, new RequestBatch(paramCollection));
  }
  
  public static RequestAsyncTask executeConnectionAsync(Handler paramHandler, HttpURLConnection paramHttpURLConnection, RequestBatch paramRequestBatch)
  {
    Validate.notNull(paramHttpURLConnection, "connection");
    RequestAsyncTask localRequestAsyncTask = new RequestAsyncTask(paramHttpURLConnection, paramRequestBatch);
    paramRequestBatch.setCallbackHandler(paramHandler);
    localRequestAsyncTask.executeOnSettingsExecutor();
    return localRequestAsyncTask;
  }
  
  public static RequestAsyncTask executeConnectionAsync(HttpURLConnection paramHttpURLConnection, RequestBatch paramRequestBatch)
  {
    return executeConnectionAsync(null, paramHttpURLConnection, paramRequestBatch);
  }
  
  public static RequestAsyncTask executeGraphPathRequestAsync(Session paramSession, String paramString, Callback paramCallback)
  {
    return newGraphPathRequest(paramSession, paramString, paramCallback).executeAsync();
  }
  
  public static RequestAsyncTask executeMeRequestAsync(Session paramSession, GraphUserCallback paramGraphUserCallback)
  {
    return newMeRequest(paramSession, paramGraphUserCallback).executeAsync();
  }
  
  public static RequestAsyncTask executeMyFriendsRequestAsync(Session paramSession, GraphUserListCallback paramGraphUserListCallback)
  {
    return newMyFriendsRequest(paramSession, paramGraphUserListCallback).executeAsync();
  }
  
  public static RequestAsyncTask executePlacesSearchRequestAsync(Session paramSession, Location paramLocation, int paramInt1, int paramInt2, String paramString, GraphPlaceListCallback paramGraphPlaceListCallback)
  {
    return newPlacesSearchRequest(paramSession, paramLocation, paramInt1, paramInt2, paramString, paramGraphPlaceListCallback).executeAsync();
  }
  
  public static RequestAsyncTask executePostRequestAsync(Session paramSession, String paramString, GraphObject paramGraphObject, Callback paramCallback)
  {
    return newPostRequest(paramSession, paramString, paramGraphObject, paramCallback).executeAsync();
  }
  
  public static RequestAsyncTask executeRestRequestAsync(Session paramSession, String paramString, Bundle paramBundle, HttpMethod paramHttpMethod)
  {
    return newRestRequest(paramSession, paramString, paramBundle, paramHttpMethod).executeAsync();
  }
  
  public static RequestAsyncTask executeStatusUpdateRequestAsync(Session paramSession, String paramString, Callback paramCallback)
  {
    return newStatusUpdateRequest(paramSession, paramString, paramCallback).executeAsync();
  }
  
  public static RequestAsyncTask executeUploadPhotoRequestAsync(Session paramSession, Bitmap paramBitmap, Callback paramCallback)
  {
    return newUploadPhotoRequest(paramSession, paramBitmap, paramCallback).executeAsync();
  }
  
  public static RequestAsyncTask executeUploadPhotoRequestAsync(Session paramSession, File paramFile, Callback paramCallback)
    throws FileNotFoundException
  {
    return newUploadPhotoRequest(paramSession, paramFile, paramCallback).executeAsync();
  }
  
  private static String getBatchAppId(RequestBatch paramRequestBatch)
  {
    if (!Utility.isNullOrEmpty(paramRequestBatch.getBatchApplicationId())) {
      return paramRequestBatch.getBatchApplicationId();
    }
    Iterator localIterator = paramRequestBatch.iterator();
    while (localIterator.hasNext())
    {
      Session localSession = ((Request)localIterator.next()).session;
      if (localSession != null) {
        return localSession.getApplicationId();
      }
    }
    return defaultBatchApplicationId;
  }
  
  public static final String getDefaultBatchApplicationId()
  {
    return defaultBatchApplicationId;
  }
  
  private static String getMimeContentType()
  {
    return String.format("multipart/form-data; boundary=%s", new Object[] { "3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f" });
  }
  
  private static String getUserAgent()
  {
    if (userAgent == null) {
      userAgent = String.format("%s.%s", new Object[] { "FBAndroidSDK", "3.0.1" });
    }
    return userAgent;
  }
  
  private static boolean isSupportedAttachmentType(Object paramObject)
  {
    return ((paramObject instanceof Bitmap)) || ((paramObject instanceof byte[])) || ((paramObject instanceof ParcelFileDescriptor));
  }
  
  private static boolean isSupportedParameterType(Object paramObject)
  {
    return ((paramObject instanceof String)) || ((paramObject instanceof Boolean)) || ((paramObject instanceof Number)) || ((paramObject instanceof Date));
  }
  
  public static Request newGraphPathRequest(Session paramSession, String paramString, Callback paramCallback)
  {
    return new Request(paramSession, paramString, null, null, paramCallback);
  }
  
  public static Request newMeRequest(Session paramSession, GraphUserCallback paramGraphUserCallback)
  {
    new Request(paramSession, "me", null, null, new Callback()
    {
      public void onCompleted(Response paramAnonymousResponse)
      {
        if (this.val$callback != null) {
          this.val$callback.onCompleted((GraphUser)paramAnonymousResponse.getGraphObjectAs(GraphUser.class), paramAnonymousResponse);
        }
      }
    });
  }
  
  public static Request newMyFriendsRequest(Session paramSession, GraphUserListCallback paramGraphUserListCallback)
  {
    new Request(paramSession, "me/friends", null, null, new Callback()
    {
      public void onCompleted(Response paramAnonymousResponse)
      {
        if (this.val$callback != null) {
          this.val$callback.onCompleted(Request.typedListFromResponse(paramAnonymousResponse, GraphUser.class), paramAnonymousResponse);
        }
      }
    });
  }
  
  public static Request newPlacesSearchRequest(Session paramSession, Location paramLocation, int paramInt1, int paramInt2, String paramString, GraphPlaceListCallback paramGraphPlaceListCallback)
  {
    if ((paramLocation == null) && (Utility.isNullOrEmpty(paramString))) {
      throw new FacebookException("Either location or searchText must be specified.");
    }
    Bundle localBundle = new Bundle(5);
    localBundle.putString("type", "place");
    localBundle.putInt("limit", paramInt2);
    if (paramLocation != null)
    {
      Locale localLocale = Locale.US;
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = Double.valueOf(paramLocation.getLatitude());
      arrayOfObject[1] = Double.valueOf(paramLocation.getLongitude());
      localBundle.putString("center", String.format(localLocale, "%f,%f", arrayOfObject));
      localBundle.putInt("distance", paramInt1);
    }
    if (!Utility.isNullOrEmpty(paramString)) {
      localBundle.putString("q", paramString);
    }
    Callback local3 = new Callback()
    {
      public void onCompleted(Response paramAnonymousResponse)
      {
        if (this.val$callback != null) {
          this.val$callback.onCompleted(Request.typedListFromResponse(paramAnonymousResponse, GraphPlace.class), paramAnonymousResponse);
        }
      }
    };
    return new Request(paramSession, "search", localBundle, HttpMethod.GET, local3);
  }
  
  public static Request newPostRequest(Session paramSession, String paramString, GraphObject paramGraphObject, Callback paramCallback)
  {
    Request localRequest = new Request(paramSession, paramString, null, HttpMethod.POST, paramCallback);
    localRequest.setGraphObject(paramGraphObject);
    return localRequest;
  }
  
  public static Request newRestRequest(Session paramSession, String paramString, Bundle paramBundle, HttpMethod paramHttpMethod)
  {
    Request localRequest = new Request(paramSession, null, paramBundle, paramHttpMethod);
    localRequest.setRestMethod(paramString);
    return localRequest;
  }
  
  public static Request newStatusUpdateRequest(Session paramSession, String paramString, Callback paramCallback)
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("message", paramString);
    return new Request(paramSession, "me/feed", localBundle, HttpMethod.POST, paramCallback);
  }
  
  public static Request newUploadPhotoRequest(Session paramSession, Bitmap paramBitmap, Callback paramCallback)
  {
    Bundle localBundle = new Bundle(1);
    localBundle.putParcelable("picture", paramBitmap);
    return new Request(paramSession, "me/photos", localBundle, HttpMethod.POST, paramCallback);
  }
  
  public static Request newUploadPhotoRequest(Session paramSession, File paramFile, Callback paramCallback)
    throws FileNotFoundException
  {
    ParcelFileDescriptor localParcelFileDescriptor = ParcelFileDescriptor.open(paramFile, 268435456);
    Bundle localBundle = new Bundle(1);
    localBundle.putParcelable("picture", localParcelFileDescriptor);
    return new Request(paramSession, "me/photos", localBundle, HttpMethod.POST, paramCallback);
  }
  
  public static Request newUploadVideoRequest(Session paramSession, File paramFile, Callback paramCallback)
    throws FileNotFoundException
  {
    ParcelFileDescriptor localParcelFileDescriptor = ParcelFileDescriptor.open(paramFile, 268435456);
    Bundle localBundle = new Bundle(1);
    localBundle.putParcelable(paramFile.getName(), localParcelFileDescriptor);
    return new Request(paramSession, "me/videos", localBundle, HttpMethod.POST, paramCallback);
  }
  
  private static String parameterToString(Object paramObject)
  {
    if ((paramObject instanceof String)) {
      return (String)paramObject;
    }
    if (((paramObject instanceof Boolean)) || ((paramObject instanceof Number))) {
      return paramObject.toString();
    }
    if ((paramObject instanceof Date)) {
      return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US).format(paramObject);
    }
    throw new IllegalArgumentException("Unsupported parameter type.");
  }
  
  private static void processGraphObject(GraphObject paramGraphObject, String paramString, KeyValueSerializer paramKeyValueSerializer)
    throws IOException
  {
    int k;
    if (!paramString.startsWith("me/"))
    {
      boolean bool2 = paramString.startsWith("/me/");
      k = 0;
      if (!bool2) {}
    }
    else
    {
      int i = paramString.indexOf(":");
      int j = paramString.indexOf("?");
      if ((i <= 3) || ((j != -1) && (i >= j))) {
        break label157;
      }
      k = 1;
    }
    Iterator localIterator = paramGraphObject.asMap().entrySet().iterator();
    label82:
    if (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      if ((k != 0) && (((String)localEntry.getKey()).equalsIgnoreCase("image"))) {}
      for (boolean bool1 = true;; bool1 = false)
      {
        processGraphObjectProperty((String)localEntry.getKey(), localEntry.getValue(), paramKeyValueSerializer, bool1);
        break label82;
        k = 0;
        break;
      }
    }
    label157:
  }
  
  private static void processGraphObjectProperty(String paramString, Object paramObject, KeyValueSerializer paramKeyValueSerializer, boolean paramBoolean)
    throws IOException
  {
    Class localClass = paramObject.getClass();
    if (GraphObject.class.isAssignableFrom(localClass))
    {
      paramObject = ((GraphObject)paramObject).getInnerJSONObject();
      localClass = paramObject.getClass();
    }
    JSONObject localJSONObject;
    for (;;)
    {
      if (JSONObject.class.isAssignableFrom(localClass))
      {
        localJSONObject = (JSONObject)paramObject;
        if (paramBoolean)
        {
          Iterator localIterator = localJSONObject.keys();
          while (localIterator.hasNext())
          {
            String str = (String)localIterator.next();
            processGraphObjectProperty(String.format("%s[%s]", new Object[] { paramString, str }), localJSONObject.opt(str), paramKeyValueSerializer, paramBoolean);
          }
          if (GraphObjectList.class.isAssignableFrom(localClass))
          {
            paramObject = ((GraphObjectList)paramObject).getInnerJSONArray();
            localClass = paramObject.getClass();
          }
        }
        else if (localJSONObject.has("id"))
        {
          processGraphObjectProperty(paramString, localJSONObject.optString("id"), paramKeyValueSerializer, paramBoolean);
        }
      }
    }
    do
    {
      for (;;)
      {
        return;
        if (localJSONObject.has("url"))
        {
          processGraphObjectProperty(paramString, localJSONObject.optString("url"), paramKeyValueSerializer, paramBoolean);
          return;
          if (!JSONArray.class.isAssignableFrom(localClass)) {
            break;
          }
          JSONArray localJSONArray = (JSONArray)paramObject;
          int i = localJSONArray.length();
          for (int j = 0; j < i; j++)
          {
            Object[] arrayOfObject = new Object[2];
            arrayOfObject[0] = paramString;
            arrayOfObject[1] = Integer.valueOf(j);
            processGraphObjectProperty(String.format("%s[%d]", arrayOfObject), localJSONArray.opt(j), paramKeyValueSerializer, paramBoolean);
          }
        }
      }
      if ((String.class.isAssignableFrom(localClass)) || (Number.class.isAssignableFrom(localClass)) || (Boolean.class.isAssignableFrom(localClass)))
      {
        paramKeyValueSerializer.writeString(paramString, paramObject.toString());
        return;
      }
    } while (!Date.class.isAssignableFrom(localClass));
    Date localDate = (Date)paramObject;
    paramKeyValueSerializer.writeString(paramString, new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US).format(localDate));
  }
  
  static void runCallbacks(final RequestBatch paramRequestBatch, List<Response> paramList)
  {
    int i = paramRequestBatch.size();
    ArrayList localArrayList = new ArrayList();
    for (int j = 0; j < i; j++)
    {
      Request localRequest = paramRequestBatch.get(j);
      if (localRequest.callback != null) {
        localArrayList.add(new Pair(localRequest.callback, paramList.get(j)));
      }
    }
    Runnable local4;
    Handler localHandler;
    if (localArrayList.size() > 0)
    {
      local4 = new Runnable()
      {
        public void run()
        {
          Iterator localIterator1 = this.val$callbacks.iterator();
          while (localIterator1.hasNext())
          {
            Pair localPair = (Pair)localIterator1.next();
            ((Request.Callback)localPair.first).onCompleted((Response)localPair.second);
          }
          Iterator localIterator2 = paramRequestBatch.getCallbacks().iterator();
          while (localIterator2.hasNext()) {
            ((RequestBatch.Callback)localIterator2.next()).onBatchCompleted(paramRequestBatch);
          }
        }
      };
      localHandler = paramRequestBatch.getCallbackHandler();
      if (localHandler == null) {
        local4.run();
      }
    }
    else
    {
      return;
    }
    localHandler.post(local4);
  }
  
  private static void serializeAttachments(Bundle paramBundle, Serializer paramSerializer)
    throws IOException
  {
    Iterator localIterator = paramBundle.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      Object localObject = paramBundle.get(str);
      if (isSupportedAttachmentType(localObject)) {
        paramSerializer.writeObject(str, localObject);
      }
    }
  }
  
  private static void serializeParameters(Bundle paramBundle, Serializer paramSerializer)
    throws IOException
  {
    Iterator localIterator = paramBundle.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      Object localObject = paramBundle.get(str);
      if (isSupportedParameterType(localObject)) {
        paramSerializer.writeObject(str, localObject);
      }
    }
  }
  
  private static void serializeRequestsAsJSON(Serializer paramSerializer, Collection<Request> paramCollection, Bundle paramBundle)
    throws JSONException, IOException
  {
    JSONArray localJSONArray = new JSONArray();
    Iterator localIterator = paramCollection.iterator();
    while (localIterator.hasNext()) {
      ((Request)localIterator.next()).serializeToBatch(localJSONArray, paramBundle);
    }
    paramSerializer.writeString("batch", localJSONArray.toString());
  }
  
  private void serializeToBatch(JSONArray paramJSONArray, Bundle paramBundle)
    throws JSONException, IOException
  {
    JSONObject localJSONObject = new JSONObject();
    if (this.batchEntryName != null)
    {
      localJSONObject.put("name", this.batchEntryName);
      localJSONObject.put("omit_response_on_success", this.batchEntryOmitResultOnSuccess);
    }
    if (this.batchEntryDependsOn != null) {
      localJSONObject.put("depends_on", this.batchEntryDependsOn);
    }
    String str1 = getUrlForBatchedRequest();
    localJSONObject.put("relative_url", str1);
    localJSONObject.put("method", this.httpMethod);
    if (this.session != null) {
      Logger.registerAccessToken(this.session.getAccessToken());
    }
    ArrayList localArrayList1 = new ArrayList();
    Iterator localIterator = this.parameters.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str2 = (String)localIterator.next();
      Object localObject = this.parameters.get(str2);
      if (isSupportedAttachmentType(localObject))
      {
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = "file";
        arrayOfObject[1] = Integer.valueOf(paramBundle.size());
        String str3 = String.format("%s%d", arrayOfObject);
        localArrayList1.add(str3);
        Utility.putObjectInBundle(paramBundle, str3, localObject);
      }
    }
    if (!localArrayList1.isEmpty()) {
      localJSONObject.put("attached_files", TextUtils.join(",", localArrayList1));
    }
    if (this.graphObject != null)
    {
      final ArrayList localArrayList2 = new ArrayList();
      processGraphObject(this.graphObject, str1, new KeyValueSerializer()
      {
        public void writeString(String paramAnonymousString1, String paramAnonymousString2)
          throws IOException
        {
          ArrayList localArrayList = localArrayList2;
          Object[] arrayOfObject = new Object[2];
          arrayOfObject[0] = paramAnonymousString1;
          arrayOfObject[1] = URLEncoder.encode(paramAnonymousString2, "UTF-8");
          localArrayList.add(String.format("%s=%s", arrayOfObject));
        }
      });
      localJSONObject.put("body", TextUtils.join("&", localArrayList2));
    }
    paramJSONArray.put(localJSONObject);
  }
  
  static final void serializeToUrlConnection(RequestBatch paramRequestBatch, HttpURLConnection paramHttpURLConnection)
    throws IOException, JSONException
  {
    Logger localLogger = new Logger(LoggingBehavior.REQUESTS, "Request");
    int i = paramRequestBatch.size();
    if (i == 1) {}
    URL localURL;
    for (HttpMethod localHttpMethod1 = paramRequestBatch.get(0).httpMethod;; localHttpMethod1 = HttpMethod.POST)
    {
      paramHttpURLConnection.setRequestMethod(localHttpMethod1.name());
      localURL = paramHttpURLConnection.getURL();
      localLogger.append("Request:\n");
      localLogger.appendKeyValue("Id", paramRequestBatch.getId());
      localLogger.appendKeyValue("URL", localURL);
      localLogger.appendKeyValue("Method", paramHttpURLConnection.getRequestMethod());
      localLogger.appendKeyValue("User-Agent", paramHttpURLConnection.getRequestProperty("User-Agent"));
      localLogger.appendKeyValue("Content-Type", paramHttpURLConnection.getRequestProperty("Content-Type"));
      paramHttpURLConnection.setConnectTimeout(paramRequestBatch.getTimeout());
      paramHttpURLConnection.setReadTimeout(paramRequestBatch.getTimeout());
      HttpMethod localHttpMethod2 = HttpMethod.POST;
      int j = 0;
      if (localHttpMethod1 == localHttpMethod2) {
        j = 1;
      }
      if (j != 0) {
        break;
      }
      localLogger.log();
      return;
    }
    paramHttpURLConnection.setDoOutput(true);
    BufferedOutputStream localBufferedOutputStream = new BufferedOutputStream(paramHttpURLConnection.getOutputStream());
    for (;;)
    {
      Serializer localSerializer;
      String str;
      try
      {
        localSerializer = new Serializer(localBufferedOutputStream, localLogger);
        if (i == 1)
        {
          Request localRequest = paramRequestBatch.get(0);
          localLogger.append("  Parameters:\n");
          serializeParameters(localRequest.parameters, localSerializer);
          localLogger.append("  Attachments:\n");
          serializeAttachments(localRequest.parameters, localSerializer);
          if (localRequest.graphObject != null) {
            processGraphObject(localRequest.graphObject, localURL.getPath(), localSerializer);
          }
          localBufferedOutputStream.close();
          return;
        }
        str = getBatchAppId(paramRequestBatch);
        if (Utility.isNullOrEmpty(str)) {
          throw new FacebookException("At least one request in a batch must have an open Session, or a default app ID must be specified.");
        }
      }
      finally
      {
        localBufferedOutputStream.close();
      }
      localSerializer.writeString("batch_app_id", str);
      Bundle localBundle = new Bundle();
      serializeRequestsAsJSON(localSerializer, paramRequestBatch, localBundle);
      localLogger.append("  Attachments:\n");
      serializeAttachments(localBundle, localSerializer);
    }
  }
  
  public static final void setDefaultBatchApplicationId(String paramString)
  {
    defaultBatchApplicationId = paramString;
  }
  
  public static HttpURLConnection toHttpConnection(RequestBatch paramRequestBatch)
  {
    Iterator localIterator = paramRequestBatch.iterator();
    while (localIterator.hasNext()) {
      ((Request)localIterator.next()).validate();
    }
    for (;;)
    {
      try
      {
        if (paramRequestBatch.size() == 1) {
          localURL = new URL(paramRequestBatch.get(0).getUrlForSingleRequest());
        }
      }
      catch (MalformedURLException localMalformedURLException)
      {
        URL localURL;
        HttpURLConnection localHttpURLConnection;
        throw new FacebookException("could not construct URL for request", localMalformedURLException);
      }
      try
      {
        localHttpURLConnection = createConnection(localURL);
        serializeToUrlConnection(paramRequestBatch, localHttpURLConnection);
        return localHttpURLConnection;
      }
      catch (IOException localIOException)
      {
        throw new FacebookException("could not construct request body", localIOException);
      }
      catch (JSONException localJSONException)
      {
        throw new FacebookException("could not construct request body", localJSONException);
      }
      localURL = new URL("https://graph.facebook.com");
    }
  }
  
  public static HttpURLConnection toHttpConnection(Collection<Request> paramCollection)
  {
    Validate.notEmptyAndContainsNoNulls(paramCollection, "requests");
    return toHttpConnection(new RequestBatch(paramCollection));
  }
  
  public static HttpURLConnection toHttpConnection(Request... paramVarArgs)
  {
    return toHttpConnection(Arrays.asList(paramVarArgs));
  }
  
  private static <T extends GraphObject> List<T> typedListFromResponse(Response paramResponse, Class<T> paramClass)
  {
    GraphMultiResult localGraphMultiResult = (GraphMultiResult)paramResponse.getGraphObjectAs(GraphMultiResult.class);
    if (localGraphMultiResult == null) {}
    GraphObjectList localGraphObjectList;
    do
    {
      return null;
      localGraphObjectList = localGraphMultiResult.getData();
    } while (localGraphObjectList == null);
    return localGraphObjectList.castToListOf(paramClass);
  }
  
  private void validate()
  {
    if ((this.graphPath != null) && (this.restMethod != null)) {
      throw new IllegalArgumentException("Only one of a graph path or REST method may be specified per request.");
    }
  }
  
  public final Response executeAndWait()
  {
    return executeAndWait(this);
  }
  
  public final RequestAsyncTask executeAsync()
  {
    return executeBatchAsync(new Request[] { this });
  }
  
  public final String getBatchEntryDependsOn()
  {
    return this.batchEntryDependsOn;
  }
  
  public final String getBatchEntryName()
  {
    return this.batchEntryName;
  }
  
  public final boolean getBatchEntryOmitResultOnSuccess()
  {
    return this.batchEntryOmitResultOnSuccess;
  }
  
  public final Callback getCallback()
  {
    return this.callback;
  }
  
  public final GraphObject getGraphObject()
  {
    return this.graphObject;
  }
  
  public final String getGraphPath()
  {
    return this.graphPath;
  }
  
  public final HttpMethod getHttpMethod()
  {
    return this.httpMethod;
  }
  
  public final Bundle getParameters()
  {
    return this.parameters;
  }
  
  public final String getRestMethod()
  {
    return this.restMethod;
  }
  
  public final Session getSession()
  {
    return this.session;
  }
  
  final String getUrlForBatchedRequest()
  {
    if (this.overriddenURL != null) {
      throw new FacebookException("Can't override URL for a batch request");
    }
    if (this.restMethod != null) {}
    for (String str = "method/" + this.restMethod;; str = this.graphPath)
    {
      addCommonParameters();
      return appendParametersToBaseUrl(str);
    }
  }
  
  final String getUrlForSingleRequest()
  {
    if (this.overriddenURL != null) {
      return this.overriddenURL.toString();
    }
    if (this.restMethod != null) {}
    for (String str = "https://api.facebook.com/method/" + this.restMethod;; str = "https://graph.facebook.com/" + this.graphPath)
    {
      addCommonParameters();
      return appendParametersToBaseUrl(str);
    }
  }
  
  public final void setBatchEntryDependsOn(String paramString)
  {
    this.batchEntryDependsOn = paramString;
  }
  
  public final void setBatchEntryName(String paramString)
  {
    this.batchEntryName = paramString;
  }
  
  public final void setBatchEntryOmitResultOnSuccess(boolean paramBoolean)
  {
    this.batchEntryOmitResultOnSuccess = paramBoolean;
  }
  
  public final void setCallback(Callback paramCallback)
  {
    this.callback = paramCallback;
  }
  
  public final void setGraphObject(GraphObject paramGraphObject)
  {
    this.graphObject = paramGraphObject;
  }
  
  public final void setGraphPath(String paramString)
  {
    this.graphPath = paramString;
  }
  
  public final void setHttpMethod(HttpMethod paramHttpMethod)
  {
    if ((this.overriddenURL != null) && (paramHttpMethod != HttpMethod.GET)) {
      throw new FacebookException("Can't change HTTP method on request with overridden URL.");
    }
    if (paramHttpMethod != null) {}
    for (;;)
    {
      this.httpMethod = paramHttpMethod;
      return;
      paramHttpMethod = HttpMethod.GET;
    }
  }
  
  public final void setParameters(Bundle paramBundle)
  {
    this.parameters = paramBundle;
  }
  
  public final void setRestMethod(String paramString)
  {
    this.restMethod = paramString;
  }
  
  public final void setSession(Session paramSession)
  {
    this.session = paramSession;
  }
  
  public String toString()
  {
    return "{Request:  session: " + this.session + ", graphPath: " + this.graphPath + ", graphObject: " + this.graphObject + ", restMethod: " + this.restMethod + ", httpMethod: " + this.httpMethod + ", parameters: " + this.parameters + "}";
  }
  
  public static abstract interface Callback
  {
    public abstract void onCompleted(Response paramResponse);
  }
  
  public static abstract interface GraphPlaceListCallback
  {
    public abstract void onCompleted(List<GraphPlace> paramList, Response paramResponse);
  }
  
  public static abstract interface GraphUserCallback
  {
    public abstract void onCompleted(GraphUser paramGraphUser, Response paramResponse);
  }
  
  public static abstract interface GraphUserListCallback
  {
    public abstract void onCompleted(List<GraphUser> paramList, Response paramResponse);
  }
  
  private static abstract interface KeyValueSerializer
  {
    public abstract void writeString(String paramString1, String paramString2)
      throws IOException;
  }
  
  private static class Serializer
    implements Request.KeyValueSerializer
  {
    private boolean firstWrite = true;
    private final Logger logger;
    private final BufferedOutputStream outputStream;
    
    public Serializer(BufferedOutputStream paramBufferedOutputStream, Logger paramLogger)
    {
      this.outputStream = paramBufferedOutputStream;
      this.logger = paramLogger;
    }
    
    public void write(String paramString, Object... paramVarArgs)
      throws IOException
    {
      if (this.firstWrite)
      {
        this.outputStream.write("--".getBytes());
        this.outputStream.write("3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f".getBytes());
        this.outputStream.write("\r\n".getBytes());
        this.firstWrite = false;
      }
      this.outputStream.write(String.format(paramString, paramVarArgs).getBytes());
    }
    
    public void writeBitmap(String paramString, Bitmap paramBitmap)
      throws IOException
    {
      writeContentDisposition(paramString, paramString, "image/png");
      paramBitmap.compress(Bitmap.CompressFormat.PNG, 100, this.outputStream);
      writeLine("", new Object[0]);
      writeRecordBoundary();
      this.logger.appendKeyValue("    " + paramString, "<Image>");
    }
    
    public void writeBytes(String paramString, byte[] paramArrayOfByte)
      throws IOException
    {
      writeContentDisposition(paramString, paramString, "content/unknown");
      this.outputStream.write(paramArrayOfByte);
      writeLine("", new Object[0]);
      writeRecordBoundary();
      Logger localLogger = this.logger;
      String str = "    " + paramString;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(paramArrayOfByte.length);
      localLogger.appendKeyValue(str, String.format("<Data: %d>", arrayOfObject));
    }
    
    public void writeContentDisposition(String paramString1, String paramString2, String paramString3)
      throws IOException
    {
      write("Content-Disposition: form-data; name=\"%s\"", new Object[] { paramString1 });
      if (paramString2 != null) {
        write("; filename=\"%s\"", new Object[] { paramString2 });
      }
      writeLine("", new Object[0]);
      if (paramString3 != null) {
        writeLine("%s: %s", new Object[] { "Content-Type", paramString3 });
      }
      writeLine("", new Object[0]);
    }
    
    /* Error */
    public void writeFile(String paramString, ParcelFileDescriptor paramParcelFileDescriptor)
      throws IOException
    {
      // Byte code:
      //   0: aload_0
      //   1: aload_1
      //   2: aload_1
      //   3: ldc 103
      //   5: invokevirtual 56	com/facebook/Request$Serializer:writeContentDisposition	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
      //   8: aconst_null
      //   9: astore_3
      //   10: aconst_null
      //   11: astore 4
      //   13: iconst_0
      //   14: istore 5
      //   16: new 125	android/os/ParcelFileDescriptor$AutoCloseInputStream
      //   19: dup
      //   20: aload_2
      //   21: invokespecial 128	android/os/ParcelFileDescriptor$AutoCloseInputStream:<init>	(Landroid/os/ParcelFileDescriptor;)V
      //   24: astore 6
      //   26: new 130	java/io/BufferedInputStream
      //   29: dup
      //   30: aload 6
      //   32: invokespecial 133	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
      //   35: astore 7
      //   37: sipush 8192
      //   40: newarray byte
      //   42: astore 9
      //   44: aload 7
      //   46: aload 9
      //   48: invokevirtual 137	java/io/BufferedInputStream:read	([B)I
      //   51: istore 10
      //   53: iload 10
      //   55: iconst_m1
      //   56: if_icmpeq +25 -> 81
      //   59: aload_0
      //   60: getfield 21	com/facebook/Request$Serializer:outputStream	Ljava/io/BufferedOutputStream;
      //   63: aload 9
      //   65: iconst_0
      //   66: iload 10
      //   68: invokevirtual 140	java/io/BufferedOutputStream:write	([BII)V
      //   71: iload 5
      //   73: iload 10
      //   75: iadd
      //   76: istore 5
      //   78: goto -34 -> 44
      //   81: aload 7
      //   83: invokevirtual 143	java/io/BufferedInputStream:close	()V
      //   86: aload 6
      //   88: invokevirtual 144	android/os/ParcelFileDescriptor$AutoCloseInputStream:close	()V
      //   91: aload_0
      //   92: ldc 70
      //   94: iconst_0
      //   95: anewarray 4	java/lang/Object
      //   98: invokevirtual 73	com/facebook/Request$Serializer:writeLine	(Ljava/lang/String;[Ljava/lang/Object;)V
      //   101: aload_0
      //   102: invokevirtual 76	com/facebook/Request$Serializer:writeRecordBoundary	()V
      //   105: aload_0
      //   106: getfield 23	com/facebook/Request$Serializer:logger	Lcom/facebook/internal/Logger;
      //   109: astore 11
      //   111: new 78	java/lang/StringBuilder
      //   114: dup
      //   115: ldc 80
      //   117: invokespecial 83	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
      //   120: aload_1
      //   121: invokevirtual 87	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   124: invokevirtual 91	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   127: astore 12
      //   129: iconst_1
      //   130: anewarray 4	java/lang/Object
      //   133: astore 13
      //   135: aload 13
      //   137: iconst_0
      //   138: iload 5
      //   140: invokestatic 109	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   143: aastore
      //   144: aload 11
      //   146: aload 12
      //   148: ldc 111
      //   150: aload 13
      //   152: invokestatic 48	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
      //   155: invokevirtual 99	com/facebook/internal/Logger:appendKeyValue	(Ljava/lang/String;Ljava/lang/Object;)V
      //   158: return
      //   159: astore 8
      //   161: aload 4
      //   163: ifnull +8 -> 171
      //   166: aload 4
      //   168: invokevirtual 143	java/io/BufferedInputStream:close	()V
      //   171: aload_3
      //   172: ifnull +7 -> 179
      //   175: aload_3
      //   176: invokevirtual 144	android/os/ParcelFileDescriptor$AutoCloseInputStream:close	()V
      //   179: aload 8
      //   181: athrow
      //   182: astore 8
      //   184: aload 6
      //   186: astore_3
      //   187: aconst_null
      //   188: astore 4
      //   190: goto -29 -> 161
      //   193: astore 8
      //   195: aload 7
      //   197: astore 4
      //   199: aload 6
      //   201: astore_3
      //   202: goto -41 -> 161
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	205	0	this	Serializer
      //   0	205	1	paramString	String
      //   0	205	2	paramParcelFileDescriptor	ParcelFileDescriptor
      //   9	193	3	localObject1	Object
      //   11	187	4	localObject2	Object
      //   14	125	5	i	int
      //   24	176	6	localAutoCloseInputStream	android.os.ParcelFileDescriptor.AutoCloseInputStream
      //   35	161	7	localBufferedInputStream	java.io.BufferedInputStream
      //   159	21	8	localObject3	Object
      //   182	1	8	localObject4	Object
      //   193	1	8	localObject5	Object
      //   42	22	9	arrayOfByte	byte[]
      //   51	25	10	j	int
      //   109	36	11	localLogger	Logger
      //   127	20	12	str	String
      //   133	18	13	arrayOfObject	Object[]
      // Exception table:
      //   from	to	target	type
      //   16	26	159	finally
      //   26	37	182	finally
      //   37	44	193	finally
      //   44	53	193	finally
      //   59	71	193	finally
    }
    
    public void writeLine(String paramString, Object... paramVarArgs)
      throws IOException
    {
      write(paramString, paramVarArgs);
      write("\r\n", new Object[0]);
    }
    
    public void writeObject(String paramString, Object paramObject)
      throws IOException
    {
      if (Request.isSupportedParameterType(paramObject))
      {
        writeString(paramString, Request.parameterToString(paramObject));
        return;
      }
      if ((paramObject instanceof Bitmap))
      {
        writeBitmap(paramString, (Bitmap)paramObject);
        return;
      }
      if ((paramObject instanceof byte[]))
      {
        writeBytes(paramString, (byte[])paramObject);
        return;
      }
      if ((paramObject instanceof ParcelFileDescriptor))
      {
        writeFile(paramString, (ParcelFileDescriptor)paramObject);
        return;
      }
      throw new IllegalArgumentException("value is not a supported type: String, Bitmap, byte[]");
    }
    
    public void writeRecordBoundary()
      throws IOException
    {
      writeLine("--%s", new Object[] { "3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f" });
    }
    
    public void writeString(String paramString1, String paramString2)
      throws IOException
    {
      writeContentDisposition(paramString1, null, null);
      writeLine("%s", new Object[] { paramString2 });
      writeRecordBoundary();
      if (this.logger != null) {
        this.logger.appendKeyValue("    " + paramString1, paramString2);
      }
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.facebook.Request
 * JD-Core Version:    0.7.0.1
 */