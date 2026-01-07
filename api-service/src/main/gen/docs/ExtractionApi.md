# ExtractionApi

All URIs are relative to *https://api.chokma.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**apiExtractionClassifyPost**](ExtractionApi.md#apiExtractionClassifyPost) | **POST** /api/extraction/classify | Classify document pages |
| [**apiExtractionExtractPost**](ExtractionApi.md#apiExtractionExtractPost) | **POST** /api/extraction/extract | Create extraction job |
| [**apiExtractionFilesFileIdDelete**](ExtractionApi.md#apiExtractionFilesFileIdDelete) | **DELETE** /api/extraction/files/{fileId} | Delete file |
| [**apiExtractionFilesFileIdMetadataGet**](ExtractionApi.md#apiExtractionFilesFileIdMetadataGet) | **GET** /api/extraction/files/{fileId}/metadata | Get file metadata |
| [**apiExtractionFilesGet**](ExtractionApi.md#apiExtractionFilesGet) | **GET** /api/extraction/files | List files |
| [**apiExtractionFilesUploadPost**](ExtractionApi.md#apiExtractionFilesUploadPost) | **POST** /api/extraction/files/upload | Upload file to Third party |
| [**apiExtractionParseGet**](ExtractionApi.md#apiExtractionParseGet) | **GET** /api/extraction/parse | List parse jobs |
| [**apiExtractionParseParseIdDelete**](ExtractionApi.md#apiExtractionParseParseIdDelete) | **DELETE** /api/extraction/parse/{parseId} | Delete parse job |
| [**apiExtractionParseParseIdGet**](ExtractionApi.md#apiExtractionParseParseIdGet) | **GET** /api/extraction/parse/{parseId} | Get parse status |
| [**apiExtractionParsePost**](ExtractionApi.md#apiExtractionParsePost) | **POST** /api/extraction/parse | Create parse job |
| [**apiExtractionReadPost**](ExtractionApi.md#apiExtractionReadPost) | **POST** /api/extraction/read | Read document |


<a id="apiExtractionClassifyPost"></a>
# **apiExtractionClassifyPost**
> ClassifyResponse apiExtractionClassifyPost(classifyRequest)

Classify document pages

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ExtractionApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.chokma.com");
    
    // Configure HTTP bearer authorization: bearerAuth
    HttpBearerAuth bearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("bearerAuth");
    bearerAuth.setBearerToken("BEARER TOKEN");

    ExtractionApi apiInstance = new ExtractionApi(defaultClient);
    ClassifyRequest classifyRequest = new ClassifyRequest(); // ClassifyRequest | 
    try {
      ClassifyResponse result = apiInstance.apiExtractionClassifyPost(classifyRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ExtractionApi#apiExtractionClassifyPost");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **classifyRequest** | [**ClassifyRequest**](ClassifyRequest.md)|  | |

### Return type

[**ClassifyResponse**](ClassifyResponse.md)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Classification completed |  -  |
| **400** | Bad request - invalid input |  -  |
| **401** | Unauthorized - authentication required |  -  |
| **500** | Internal server error |  -  |

<a id="apiExtractionExtractPost"></a>
# **apiExtractionExtractPost**
> ExtractionResponse apiExtractionExtractPost(extractionRequest)

Create extraction job

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ExtractionApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.chokma.com");
    
    // Configure HTTP bearer authorization: bearerAuth
    HttpBearerAuth bearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("bearerAuth");
    bearerAuth.setBearerToken("BEARER TOKEN");

    ExtractionApi apiInstance = new ExtractionApi(defaultClient);
    ExtractionRequest extractionRequest = new ExtractionRequest(); // ExtractionRequest | 
    try {
      ExtractionResponse result = apiInstance.apiExtractionExtractPost(extractionRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ExtractionApi#apiExtractionExtractPost");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **extractionRequest** | [**ExtractionRequest**](ExtractionRequest.md)|  | |

### Return type

[**ExtractionResponse**](ExtractionResponse.md)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Extraction job created |  -  |
| **400** | Bad request - invalid input |  -  |
| **401** | Unauthorized - authentication required |  -  |
| **500** | Internal server error |  -  |

<a id="apiExtractionFilesFileIdDelete"></a>
# **apiExtractionFilesFileIdDelete**
> apiExtractionFilesFileIdDelete(fileId)

Delete file

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ExtractionApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.chokma.com");
    
    // Configure HTTP bearer authorization: bearerAuth
    HttpBearerAuth bearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("bearerAuth");
    bearerAuth.setBearerToken("BEARER TOKEN");

    ExtractionApi apiInstance = new ExtractionApi(defaultClient);
    String fileId = "fileId_example"; // String | 
    try {
      apiInstance.apiExtractionFilesFileIdDelete(fileId);
    } catch (ApiException e) {
      System.err.println("Exception when calling ExtractionApi#apiExtractionFilesFileIdDelete");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **fileId** | **String**|  | |

### Return type

null (empty response body)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **204** | File deleted successfully |  -  |
| **401** | Unauthorized - authentication required |  -  |
| **404** | Resource not found |  -  |
| **500** | Internal server error |  -  |

<a id="apiExtractionFilesFileIdMetadataGet"></a>
# **apiExtractionFilesFileIdMetadataGet**
> FileMetadata apiExtractionFilesFileIdMetadataGet(fileId)

Get file metadata

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ExtractionApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.chokma.com");
    
    // Configure HTTP bearer authorization: bearerAuth
    HttpBearerAuth bearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("bearerAuth");
    bearerAuth.setBearerToken("BEARER TOKEN");

    ExtractionApi apiInstance = new ExtractionApi(defaultClient);
    String fileId = "fileId_example"; // String | 
    try {
      FileMetadata result = apiInstance.apiExtractionFilesFileIdMetadataGet(fileId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ExtractionApi#apiExtractionFilesFileIdMetadataGet");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **fileId** | **String**|  | |

### Return type

[**FileMetadata**](FileMetadata.md)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | File metadata retrieved |  -  |
| **401** | Unauthorized - authentication required |  -  |
| **404** | Resource not found |  -  |
| **500** | Internal server error |  -  |

<a id="apiExtractionFilesGet"></a>
# **apiExtractionFilesGet**
> FileListResponse apiExtractionFilesGet(cursor, limit)

List files

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ExtractionApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.chokma.com");
    
    // Configure HTTP bearer authorization: bearerAuth
    HttpBearerAuth bearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("bearerAuth");
    bearerAuth.setBearerToken("BEARER TOKEN");

    ExtractionApi apiInstance = new ExtractionApi(defaultClient);
    String cursor = "cursor_example"; // String | 
    Integer limit = 20; // Integer | 
    try {
      FileListResponse result = apiInstance.apiExtractionFilesGet(cursor, limit);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ExtractionApi#apiExtractionFilesGet");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **cursor** | **String**|  | [optional] |
| **limit** | **Integer**|  | [optional] [default to 20] |

### Return type

[**FileListResponse**](FileListResponse.md)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Files listed successfully |  -  |
| **401** | Unauthorized - authentication required |  -  |
| **500** | Internal server error |  -  |

<a id="apiExtractionFilesUploadPost"></a>
# **apiExtractionFilesUploadPost**
> FileUploadResponse apiExtractionFilesUploadPost(_file, labels)

Upload file to Third party

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ExtractionApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.chokma.com");
    
    // Configure HTTP bearer authorization: bearerAuth
    HttpBearerAuth bearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("bearerAuth");
    bearerAuth.setBearerToken("BEARER TOKEN");

    ExtractionApi apiInstance = new ExtractionApi(defaultClient);
    File _file = new File("/path/to/file"); // File | 
    String labels = "labels_example"; // String | JSON string of labels
    try {
      FileUploadResponse result = apiInstance.apiExtractionFilesUploadPost(_file, labels);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ExtractionApi#apiExtractionFilesUploadPost");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **_file** | **File**|  | |
| **labels** | **String**| JSON string of labels | [optional] |

### Return type

[**FileUploadResponse**](FileUploadResponse.md)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

 - **Content-Type**: multipart/form-data
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | File uploaded successfully |  -  |
| **400** | Bad request - invalid input |  -  |
| **401** | Unauthorized - authentication required |  -  |
| **500** | Internal server error |  -  |

<a id="apiExtractionParseGet"></a>
# **apiExtractionParseGet**
> ParseListResponse apiExtractionParseGet(cursor, limit)

List parse jobs

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ExtractionApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.chokma.com");
    
    // Configure HTTP bearer authorization: bearerAuth
    HttpBearerAuth bearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("bearerAuth");
    bearerAuth.setBearerToken("BEARER TOKEN");

    ExtractionApi apiInstance = new ExtractionApi(defaultClient);
    String cursor = "cursor_example"; // String | 
    Integer limit = 20; // Integer | 
    try {
      ParseListResponse result = apiInstance.apiExtractionParseGet(cursor, limit);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ExtractionApi#apiExtractionParseGet");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **cursor** | **String**|  | [optional] |
| **limit** | **Integer**|  | [optional] [default to 20] |

### Return type

[**ParseListResponse**](ParseListResponse.md)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Parse jobs listed |  -  |
| **401** | Unauthorized - authentication required |  -  |
| **500** | Internal server error |  -  |

<a id="apiExtractionParseParseIdDelete"></a>
# **apiExtractionParseParseIdDelete**
> ParseDeleteResponse apiExtractionParseParseIdDelete(parseId)

Delete parse job

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ExtractionApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.chokma.com");
    
    // Configure HTTP bearer authorization: bearerAuth
    HttpBearerAuth bearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("bearerAuth");
    bearerAuth.setBearerToken("BEARER TOKEN");

    ExtractionApi apiInstance = new ExtractionApi(defaultClient);
    String parseId = "parseId_example"; // String | 
    try {
      ParseDeleteResponse result = apiInstance.apiExtractionParseParseIdDelete(parseId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ExtractionApi#apiExtractionParseParseIdDelete");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **parseId** | **String**|  | |

### Return type

[**ParseDeleteResponse**](ParseDeleteResponse.md)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Parse deleted |  -  |
| **401** | Unauthorized - authentication required |  -  |
| **404** | Resource not found |  -  |
| **500** | Internal server error |  -  |

<a id="apiExtractionParseParseIdGet"></a>
# **apiExtractionParseParseIdGet**
> ParseStatusResponse apiExtractionParseParseIdGet(parseId)

Get parse status

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ExtractionApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.chokma.com");
    
    // Configure HTTP bearer authorization: bearerAuth
    HttpBearerAuth bearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("bearerAuth");
    bearerAuth.setBearerToken("BEARER TOKEN");

    ExtractionApi apiInstance = new ExtractionApi(defaultClient);
    String parseId = "parseId_example"; // String | 
    try {
      ParseStatusResponse result = apiInstance.apiExtractionParseParseIdGet(parseId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ExtractionApi#apiExtractionParseParseIdGet");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **parseId** | **String**|  | |

### Return type

[**ParseStatusResponse**](ParseStatusResponse.md)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Parse status retrieved |  -  |
| **401** | Unauthorized - authentication required |  -  |
| **404** | Resource not found |  -  |
| **500** | Internal server error |  -  |

<a id="apiExtractionParsePost"></a>
# **apiExtractionParsePost**
> ParseResponse apiExtractionParsePost(parseRequest)

Create parse job

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ExtractionApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.chokma.com");
    
    // Configure HTTP bearer authorization: bearerAuth
    HttpBearerAuth bearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("bearerAuth");
    bearerAuth.setBearerToken("BEARER TOKEN");

    ExtractionApi apiInstance = new ExtractionApi(defaultClient);
    ParseRequest parseRequest = new ParseRequest(); // ParseRequest | 
    try {
      ParseResponse result = apiInstance.apiExtractionParsePost(parseRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ExtractionApi#apiExtractionParsePost");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **parseRequest** | [**ParseRequest**](ParseRequest.md)|  | |

### Return type

[**ParseResponse**](ParseResponse.md)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Parse job created |  -  |
| **400** | Bad request - invalid input |  -  |
| **401** | Unauthorized - authentication required |  -  |
| **500** | Internal server error |  -  |

<a id="apiExtractionReadPost"></a>
# **apiExtractionReadPost**
> ReadResponse apiExtractionReadPost(readRequest)

Read document

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ExtractionApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.chokma.com");
    
    // Configure HTTP bearer authorization: bearerAuth
    HttpBearerAuth bearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("bearerAuth");
    bearerAuth.setBearerToken("BEARER TOKEN");

    ExtractionApi apiInstance = new ExtractionApi(defaultClient);
    ReadRequest readRequest = new ReadRequest(); // ReadRequest | 
    try {
      ReadResponse result = apiInstance.apiExtractionReadPost(readRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ExtractionApi#apiExtractionReadPost");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **readRequest** | [**ReadRequest**](ReadRequest.md)|  | |

### Return type

[**ReadResponse**](ReadResponse.md)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Document read initiated |  -  |
| **400** | Bad request - invalid input |  -  |
| **401** | Unauthorized - authentication required |  -  |
| **500** | Internal server error |  -  |

