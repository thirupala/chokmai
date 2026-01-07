# TenantsApi

All URIs are relative to *https://api.chokma.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**tenantsGet**](TenantsApi.md#tenantsGet) | **GET** /tenants | List tenants |
| [**tenantsPost**](TenantsApi.md#tenantsPost) | **POST** /tenants | Create tenant |
| [**tenantsTenantIdDelete**](TenantsApi.md#tenantsTenantIdDelete) | **DELETE** /tenants/{tenantId} | Delete tenant |


<a id="tenantsGet"></a>
# **tenantsGet**
> List&lt;Tenant&gt; tenantsGet()

List tenants

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.TenantsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.chokma.com");
    
    // Configure HTTP bearer authorization: bearerAuth
    HttpBearerAuth bearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("bearerAuth");
    bearerAuth.setBearerToken("BEARER TOKEN");

    TenantsApi apiInstance = new TenantsApi(defaultClient);
    try {
      List<Tenant> result = apiInstance.tenantsGet();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling TenantsApi#tenantsGet");
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

[**List&lt;Tenant&gt;**](Tenant.md)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | List of tenants |  -  |
| **401** | Unauthorized - authentication required |  -  |
| **403** | Forbidden - insufficient permissions |  -  |
| **500** | Internal server error |  -  |

<a id="tenantsPost"></a>
# **tenantsPost**
> IdResponse tenantsPost(createTenantRequest)

Create tenant

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.TenantsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.chokma.com");
    
    // Configure HTTP bearer authorization: bearerAuth
    HttpBearerAuth bearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("bearerAuth");
    bearerAuth.setBearerToken("BEARER TOKEN");

    TenantsApi apiInstance = new TenantsApi(defaultClient);
    CreateTenantRequest createTenantRequest = new CreateTenantRequest(); // CreateTenantRequest | 
    try {
      IdResponse result = apiInstance.tenantsPost(createTenantRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling TenantsApi#tenantsPost");
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
| **createTenantRequest** | [**CreateTenantRequest**](CreateTenantRequest.md)|  | |

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
| **201** | Tenant created successfully |  -  |
| **400** | Bad request - invalid input |  -  |
| **401** | Unauthorized - authentication required |  -  |
| **403** | Forbidden - insufficient permissions |  -  |
| **409** | Tenant key already exists |  -  |
| **500** | Internal server error |  -  |

<a id="tenantsTenantIdDelete"></a>
# **tenantsTenantIdDelete**
> tenantsTenantIdDelete(tenantId)

Delete tenant

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.TenantsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.chokma.com");
    
    // Configure HTTP bearer authorization: bearerAuth
    HttpBearerAuth bearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("bearerAuth");
    bearerAuth.setBearerToken("BEARER TOKEN");

    TenantsApi apiInstance = new TenantsApi(defaultClient);
    UUID tenantId = UUID.randomUUID(); // UUID | 
    try {
      apiInstance.tenantsTenantIdDelete(tenantId);
    } catch (ApiException e) {
      System.err.println("Exception when calling TenantsApi#tenantsTenantIdDelete");
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
| **204** | Tenant deleted successfully |  -  |
| **401** | Unauthorized - authentication required |  -  |
| **403** | Forbidden - insufficient permissions |  -  |
| **404** | Resource not found |  -  |
| **500** | Internal server error |  -  |

