# AuditApi

All URIs are relative to *https://api.chokma.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**auditLogsGet**](AuditApi.md#auditLogsGet) | **GET** /audit/logs | List audit logs |


<a id="auditLogsGet"></a>
# **auditLogsGet**
> List&lt;AuditLog&gt; auditLogsGet(limit, offset)

List audit logs

Returns audit logs filtered by tenant

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AuditApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.chokma.com");
    
    // Configure HTTP bearer authorization: bearerAuth
    HttpBearerAuth bearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("bearerAuth");
    bearerAuth.setBearerToken("BEARER TOKEN");

    AuditApi apiInstance = new AuditApi(defaultClient);
    Integer limit = 50; // Integer | 
    Integer offset = 0; // Integer | 
    try {
      List<AuditLog> result = apiInstance.auditLogsGet(limit, offset);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AuditApi#auditLogsGet");
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
| **limit** | **Integer**|  | [optional] [default to 50] |
| **offset** | **Integer**|  | [optional] [default to 0] |

### Return type

[**List&lt;AuditLog&gt;**](AuditLog.md)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Audit logs returned successfully |  -  |
| **400** | Bad request - invalid input |  -  |
| **401** | Unauthorized - authentication required |  -  |
| **403** | Forbidden - insufficient permissions |  -  |
| **500** | Internal server error |  -  |

