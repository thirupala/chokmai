

# ParsingOptions


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**tableOutputMode** | [**TableOutputModeEnum**](#TableOutputModeEnum) |  |  [optional] |
|**tableParsingFormat** | **String** |  |  [optional] |
|**chunkingStrategy** | [**ChunkingStrategyEnum**](#ChunkingStrategyEnum) |  |  [optional] |
|**signatureDetection** | **Boolean** |  |  [optional] |
|**removeStrikethroughLines** | **Boolean** |  |  [optional] |
|**skewDetection** | **Boolean** |  |  [optional] |
|**disableLayoutDetection** | **Boolean** |  |  [optional] |
|**ignoreSections** | **List&lt;String&gt;** |  |  [optional] |
|**crossPageHeaderDetection** | **Boolean** |  |  [optional] |
|**includeImages** | **Boolean** |  |  [optional] |
|**barcodeDetection** | **Boolean** |  |  [optional] |
|**ocrModel** | **String** |  |  [optional] |



## Enum: TableOutputModeEnum

| Name | Value |
|---- | -----|
| HTML | &quot;html&quot; |
| MARKDOWN | &quot;markdown&quot; |
| TEXT | &quot;text&quot; |



## Enum: ChunkingStrategyEnum

| Name | Value |
|---- | -----|
| NONE | &quot;none&quot; |
| SEMANTIC | &quot;semantic&quot; |
| FIXED | &quot;fixed&quot; |



