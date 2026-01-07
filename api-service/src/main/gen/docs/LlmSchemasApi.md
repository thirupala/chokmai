# LlmSchemasApi

All URIs are relative to *https://api.chokma.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**llmSchemasPost**](LlmSchemasApi.md#llmSchemasPost) | **POST** /llm/schemas | Create extraction schema |


<a id="llmSchemasPost"></a>
# **llmSchemasPost**
> SchemaResponse llmSchemasPost(createSchemaRequest)

Create extraction schema

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.LlmSchemasApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.chokma.com");
    
    // Configure HTTP bearer authorization: bearerAuth
    HttpBearerAuth bearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("bearerAuth");
    bearerAuth.setBearerToken("BEARER TOKEN");

    LlmSchemasApi apiInstance = new LlmSchemasApi(defaultClient);
    CreateSchemaRequest createSchemaRequest = new CreateSchemaRequest(); // CreateSchemaRequest | 
    try {
      SchemaResponse result = apiInstance.llmSchemasPost(createSchemaRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling LlmSchemasApi#llmSchemasPost");
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
| **createSchemaRequest** | [**CreateSchemaRequest**](CreateSchemaRequest.md)|  | |

### Return type

[**SchemaResponse**](SchemaResponse.md)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Schema created successfully |  -  |
| **400** | Bad request - invalid input |  -  |
| **401** | Unauthorized - authentication required |  -  |
| **403** | Forbidden - insufficient permissions |  -  |
| **500** | Internal server error |  -  |

