# ProjectsApi

All URIs are relative to *https://api.chokma.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**tenantsTenantIdProjectsPost**](ProjectsApi.md#tenantsTenantIdProjectsPost) | **POST** /tenants/{tenantId}/projects | Create project |


<a id="tenantsTenantIdProjectsPost"></a>
# **tenantsTenantIdProjectsPost**
> IdResponse tenantsTenantIdProjectsPost(tenantId, createProjectRequest)

Create project

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ProjectsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.chokma.com");
    
    // Configure HTTP bearer authorization: bearerAuth
    HttpBearerAuth bearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("bearerAuth");
    bearerAuth.setBearerToken("BEARER TOKEN");

    ProjectsApi apiInstance = new ProjectsApi(defaultClient);
    UUID tenantId = UUID.randomUUID(); // UUID | 
    CreateProjectRequest createProjectRequest = new CreateProjectRequest(); // CreateProjectRequest | 
    try {
      IdResponse result = apiInstance.tenantsTenantIdProjectsPost(tenantId, createProjectRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ProjectsApi#tenantsTenantIdProjectsPost");
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
| **tenantId** | **UUID**|  | |
| **createProjectRequest** | [**CreateProjectRequest**](CreateProjectRequest.md)|  | |

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
| **201** | Project created successfully |  -  |
| **400** | Bad request - invalid input |  -  |
| **401** | Unauthorized - authentication required |  -  |
| **403** | Forbidden - insufficient permissions |  -  |
| **404** | Resource not found |  -  |
| **500** | Internal server error |  -  |

