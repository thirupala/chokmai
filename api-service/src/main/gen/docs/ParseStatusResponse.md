

# ParseStatusResponse


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**parseId** | **String** |  |  |
|**status** | [**StatusEnum**](#StatusEnum) |  |  |
|**createdAt** | **OffsetDateTime** |  |  |
|**datasetId** | **String** |  |  [optional] |
|**parsedPagesCount** | **Integer** |  |  [optional] |
|**totalPages** | **Integer** |  |  [optional] |
|**error** | **String** |  |  [optional] |
|**pages** | **List&lt;Map&lt;String, Object&gt;&gt;** |  |  [optional] |
|**chunks** | **List&lt;Map&lt;String, Object&gt;&gt;** |  |  [optional] |
|**structuredData** | **Map&lt;String, Object&gt;** |  |  [optional] |
|**pageClasses** | **Map&lt;String, Object&gt;** |  |  [optional] |
|**pdfBase64** | **byte[]** |  |  [optional] |
|**tasksCompletedCount** | **Integer** |  |  [optional] |
|**tasksTotalCount** | **Integer** |  |  [optional] |
|**finishedAt** | **OffsetDateTime** |  |  [optional] |
|**labels** | **Map&lt;String, Object&gt;** |  |  [optional] |
|**options** | **Map&lt;String, Object&gt;** |  |  [optional] |
|**usage** | **Map&lt;String, Object&gt;** |  |  [optional] |
|**messageUpdate** | **String** |  |  [optional] |



## Enum: StatusEnum

| Name | Value |
|---- | -----|
| PENDING | &quot;pending&quot; |
| PROCESSING | &quot;processing&quot; |
| COMPLETED | &quot;completed&quot; |
| FAILED | &quot;failed&quot; |



