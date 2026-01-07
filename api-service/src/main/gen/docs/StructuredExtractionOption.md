

# StructuredExtractionOption


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**schemaName** | **String** |  |  |
|**jsonSchema** | **Map&lt;String, Object&gt;** | JSON Schema for extraction |  [optional] |
|**skipOcr** | **Boolean** |  |  [optional] |
|**prompt** | **String** |  |  [optional] |
|**modelProvider** | **String** |  |  [optional] |
|**partitionStrategy** | [**PartitionStrategyEnum**](#PartitionStrategyEnum) |  |  [optional] |
|**pageClasses** | **List&lt;String&gt;** |  |  [optional] |
|**provideCitations** | **Boolean** |  |  [optional] |



## Enum: PartitionStrategyEnum

| Name | Value |
|---- | -----|
| NONE | &quot;none&quot; |
| PAGE | &quot;page&quot; |
| SECTION | &quot;section&quot; |



