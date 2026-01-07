

# AuditLog


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**actor** | **String** | User or system that performed the action |  |
|**tenantId** | **UUID** |  |  |
|**action** | **String** | Action performed |  |
|**resource** | **String** | Resource affected |  |
|**outcome** | [**OutcomeEnum**](#OutcomeEnum) |  |  |
|**timestamp** | **OffsetDateTime** |  |  |



## Enum: OutcomeEnum

| Name | Value |
|---- | -----|
| SUCCESS | &quot;success&quot; |
| FAILURE | &quot;failure&quot; |



