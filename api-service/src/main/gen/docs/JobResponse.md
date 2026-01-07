

# JobResponse


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**jobId** | **UUID** |  |  |
|**status** | [**StatusEnum**](#StatusEnum) |  |  |
|**projectId** | **UUID** |  |  |
|**processorId** | **UUID** |  |  |
|**createdAt** | **OffsetDateTime** |  |  |
|**completedAt** | **OffsetDateTime** |  |  [optional] |



## Enum: StatusEnum

| Name | Value |
|---- | -----|
| PENDING | &quot;pending&quot; |
| PROCESSING | &quot;processing&quot; |
| COMPLETED | &quot;completed&quot; |
| FAILED | &quot;failed&quot; |
| CANCELLED | &quot;cancelled&quot; |



