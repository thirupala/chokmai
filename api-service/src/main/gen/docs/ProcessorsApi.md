# ProcessorsApi

All URIs are relative to *https://api.chokma.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**processorsGet**](ProcessorsApi.md#processorsGet) | **GET** /processors | List processors |
| [**processorsPost**](ProcessorsApi.md#processorsPost) | **POST** /processors | Create processor |


<a id="processorsGet"></a>
# **processorsGet**
> List&lt;Processor&gt; processorsGet()

List processors

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ProcessorsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.chokma.com");
    
    // Configure HTTP bearer authorization: bearerAuth
    HttpBearerAuth bearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("bearerAuth");
    bearerAuth.setBearerToken("BEARER TOKEN");

    ProcessorsApi apiInstance = new ProcessorsApi(defaultClient);
    try {
      List<Processor> result = apiInstance.processorsGet();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ProcessorsApi#processorsGet");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;Processor&gt;**](Processor.md)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | List of processors |  -  |
| **401** | Unauthorized - authentication required |  -  |
| **500** | Internal server error |  -  |

<a id="processorsPost"></a>
# **processorsPost**
> IdResponse processorsPost(createProcessorRequest)

Create processor

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ProcessorsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.chokma.com");
    
    // Configure HTTP bearer authorization: bearerAuth
    HttpBearerAuth bearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("bearerAuth");
    bearerAuth.setBearerToken("BEARER TOKEN");

    ProcessorsApi apiInstance = new ProcessorsApi(defaultClient);
    CreateProcessorRequest createProcessorRequest = new CreateProcessorRequest(); // CreateProcessorRequest | 
    try {
      IdResponse result = apiInstance.processorsPost(createProcessorRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ProcessorsApi#processorsPost");
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
| **createProcessorRequest** | [**CreateProcessorRequest**](CreateProcessorRequest.md)|  | |

### Return type

[**IdResponse**](IdResponse.md)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Processor created successfully |  -  |
| **400** | Bad request - invalid input |  -  |
| **401** | Unauthorized - authentication required |  -  |
| **403** | Forbidden - insufficient permissions |  -  |
| **500** | Internal server error |  -  |

