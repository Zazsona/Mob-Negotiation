/*
 * This script fetches response success rates for responses associated with the given prompt IDs.
 *
 * Named Parameters:
 *    Table Name (String)
 *    Language Code (String)
 *    Prompt Ids (Array<Integer>)
 */


SELECT
    PRS.:tableNameId         AS PromptId
,   PRS.:tableNameResponseId AS PromptResponseId
,   PR.ResponseId            AS ResponseId
,   PRSR.PersonalityId       AS PersonalityId
,   PRSR.SuccessRate         AS SuccessRate
FROM :tableNameResponses PRS
INNER JOIN :tableNameResponse PR
    ON PRS.:tableNameResponseId = PR.:tableNameResponseId
INNER JOIN :tableNameResponsesSuccessRate PRSR
    ON PRSR.:tableNameResponseId = PRS.:tableNameResponseId
WHERE PRS.:tableNameId IN (:promptIds);