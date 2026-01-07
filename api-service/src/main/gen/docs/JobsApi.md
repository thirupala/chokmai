# JobsApi

All URIs are relative to *https://api.chokma.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**jobsAsyncPost**](JobsApi.md#jobsAsyncPost) | **POST** /jobs/async | Submit async job |
| [**jobsJobIdDelete**](JobsApi.md#jobsJobIdDelete) | **DELETE** /jobs/{jobId} | Cancel job |
| [**jobsJobIdGet**](JobsApi.md#jobsJobIdGet) | **GET** /jobs/{jobId} | Get job status |
| [**jobsJobIdStartParsePost**](JobsApi.md#jobsJobIdStartParsePost) | **POST** /jobs/{jobId}/start-parse | Start parse job |
| [**jobsJobIdStatusRealtimeGet**](JobsApi.md#jobsJobIdStatusRealtimeGet) | **GET** /jobs/{jobId}/status/realtime | Get realtime job status |
| [**jobsSyncPost**](JobsApi.md#jobsSyncPost) | **POST** /jobs/sync | Submit synchronous job |


<a id="jobsAsyncPost"></a>
# **jobsAsyncPost**
> UploadUrlResponse jobsAsyncPost(createJobRequest)

Submit async job

Create job and get pre-signed upload URL

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.JobsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.chokma.com");
    
    // Configure HTTP bearer authorization: bearerAuth
    HttpBearerAuth bearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("bearerAuth");
    bearerAuth.setBearerToken("BEARER TOKEN");

    JobsApi apiInstance = new JobsApi(defaultClient);
    CreateJobRequest createJobRequest = new CreateJobRequest(); // CreateJobRequest | 
    try {
      UploadUrlResponse result = apiInstance.jobsAsyncPost(createJobRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling JobsApi#jobsAsyncPost");
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
| **createJobRequest** | [**CreateJobRequest**](CreateJobRequest.md)|  | |

### Return type

[**UploadUrlResponse**](UploadUrlResponse.md)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Job created with upload URL |  -  |
| **400** | Bad request - invalid input |  -  |
| **401** | Unauthorized - authentication required |  -  |
| **403** | Forbidden - insufficient permissions |  -  |
| **500** | Internal server error |  -  |

<a id="jobsJobIdDelete"></a>
# **jobsJobIdDelete**
> jobsJobIdDelete(jobId)

Cancel job

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.JobsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.chokma.com");
    
    // Configure HTTP bearer authorization: bearerAuth
    HttpBearerAuth bearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("bearerAuth");
    bearerAuth.setBearerToken("BEARER TOKEN");

    JobsApi apiInstance = new JobsApi(defaultClient);
    UUID jobId = UUID.randomUUID(); // UUID | 
    try {
      apiInstance.jobsJobIdDelete(jobId);
    } catch (ApiException e) {
      System.err.println("Exception when calling JobsApi#jobsJobIdDelete");
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
| **jobId** | **UUID**|  | |

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
| **204** | Job cancelled successfully |  -  |
| **401** | Unauthorized - authentication required |  -  |
| **404** | Resource not found |  -  |
| **409** | Job cannot be cancelled in current state |  -  |
| **500** | Internal server error |  -  |

<a id="jobsJobIdGet"></a>
# **jobsJobIdGet**
> JobResponse jobsJobIdGet(jobId)

Get job status

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.JobsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.chokma.com");
    
    // Configure HTTP bearer authorization: bearerAuth
    HttpBearerAuth bearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("bearerAuth");
    bearerAuth.setBearerToken("BEARER TOKEN");

    JobsApi apiInstance = new JobsApi(defaultClient);
    UUID jobId = UUID.randomUUID(); // UUID | 
    try {
      JobResponse result = apiInstance.jobsJobIdGet(jobId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling JobsApi#jobsJobIdGet");
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
| **jobId** | **UUID**|  | |

### Return type

[**JobResponse**](JobResponse.md)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Job details retrieved |  -  |
| **401** | Unauthorized - authentication required |  -  |
| **404** | Resource not found |  -  |
| **500** | Internal server error |  -  |

<a id="jobsJobIdStartParsePost"></a>
# **jobsJobIdStartParsePost**
> ParseResponse jobsJobIdStartParsePost(jobId)

Start parse job

Trigger parsing for uploaded file

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.JobsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.chokma.com");
    
    // Configure HTTP bearer authorization: bearerAuth
    HttpBearerAuth bearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("bearerAuth");
    bearerAuth.setBearerToken("BEARER TOKEN");

    JobsApi apiInstance = new JobsApi(defaultClient);
    UUID jobId = UUID.randomUUID(); // UUID | 
    try {
      ParseResponse result = apiInstance.jobsJobIdStartParsePost(jobId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling JobsApi#jobsJobIdStartParsePost");
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
| **jobId** | **UUID**|  | |

### Return type

[**ParseResponse**](ParseResponse.md)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Parse started |  -  |
| **401** | Unauthorized - authentication required |  -  |
| **404** | Resource not found |  -  |
| **409** | Job already parsed or in invalid state |  -  |
| **500** | Internal server error |  -  |

<a id="jobsJobIdStatusRealtimeGet"></a>
# **jobsJobIdStatusRealtimeGet**
> JobResponse jobsJobIdStatusRealtimeGet(jobId)

Get realtime job status

Poll for current job status from Third party

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.JobsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.chokma.com");
    
    // Configure HTTP bearer authorization: bearerAuth
    HttpBearerAuth bearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("bearerAuth");
    bearerAuth.setBearerToken("BEARER TOKEN");

    JobsApi apiInstance = new JobsApi(defaultClient);
    UUID jobId = UUID.randomUUID(); // UUID | 
    try {
      JobResponse result = apiInstance.jobsJobIdStatusRealtimeGet(jobId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling JobsApi#jobsJobIdStatusRealtimeGet");
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
| **jobId** | **UUID**|  | |

### Return type

[**JobResponse**](JobResponse.md)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Realtime status retrieved |  -  |
| **401** | Unauthorized - authentication required |  -  |
| **404** | Resource not found |  -  |
| **500** | Internal server error |  -  |

<a id="jobsSyncPost"></a>
# **jobsSyncPost**
> ReadResponse jobsSyncPost(projectId, processorId, _file)

Submit synchronous job

Upload file and process immediately

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.JobsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.chokma.com");
    
    // Configure HTTP bearer authorization: bearerAuth
    HttpBearerAuth bearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("bearerAuth");
    bearerAuth.setBearerToken("BEARER TOKEN");

    JobsApi apiInstance = new JobsApi(defaultClient);
    UUID projectId = UUID.randomUUID(); // UUID | 
    UUID processorId = UUID.randomUUID(); // UUID | 
    File _file = new File("/path/to/file"); // File | 
    try {
      ReadResponse result = apiInstance.jobsSyncPost(projectId, processorId, _file);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling JobsApi#jobsSyncPost");
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
| **projectId** | **UUID**|  | |
| **processorId** | **UUID**|  | |
| **_file** | **File**|  | |

### Return type

[**ReadResponse**](ReadResponse.md)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

 - **Content-Type**: multipart/form-data
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Job processed successfully |  -  |
| **400** | Bad request - invalid input |  -  |
| **401** | Unauthorized - authentication required |  -  |
| **403** | Forbidden - insufficient permissions |  -  |
| **500** | Internal server error |  -  |

