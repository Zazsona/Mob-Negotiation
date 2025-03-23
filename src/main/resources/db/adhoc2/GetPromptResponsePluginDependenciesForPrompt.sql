/*
 * This script fetches plugin dependencies for responses associated with the given prompt IDs.
 *
 * Named Parameters:
 *    Table Name (String)
 *    Prompt Ids (Array<Integer>)
 */


SELECT
    PRS.:tableNameId         AS PromptId
,   PRS.:tableNameResponseId AS PromptResponseId
,   PR.ResponseId            AS ResponseId
,   RPD.PluginNamespacedKey  AS DependencyKey
FROM :tableNameResponses PRS
INNER JOIN :tableNameResponse PR
    ON PRS.:tableNameResponseId = PR.:tableNameResponseId
LEFT JOIN ResponsePluginDependency RPD
    ON PR.ResponseId = RPD.ResponseId
WHERE PRS.:tableNameId IN (:promptIds);