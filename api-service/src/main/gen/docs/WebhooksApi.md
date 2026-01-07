# WebhooksApi

All URIs are relative to *https://api.chokma.com*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**webhooksStripePost**](WebhooksApi.md#webhooksStripePost) | **POST** /webhooks/stripe | Stripe webhook receiver |


<a id="webhooksStripePost"></a>
# **webhooksStripePost**
> WebhooksStripePost200Response webhooksStripePost(stripeSignature, body)

Stripe webhook receiver

Receives Stripe webhook events (no authentication required)

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.WebhooksApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.chokma.com");

    WebhooksApi apiInstance = new WebhooksApi(defaultClient);
    String stripeSignature = "stripeSignature_example"; // String | Stripe webhook signature for verification
    Object body = null; // Object | 
    try {
      WebhooksStripePost200Response result = apiInstance.webhooksStripePost(stripeSignature, body);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling WebhooksApi#webhooksStripePost");
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
| **stripeSignature** | **String**| Stripe webhook signature for verification | |
| **body** | **Object**|  | |

### Return type

[**WebhooksStripePost200Response**](WebhooksStripePost200Response.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Event processed successfully |  -  |
| **400** | Invalid signature or payload |  -  |
| **500** | Internal server error |  -  |

