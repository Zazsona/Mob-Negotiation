/*
 * This script fetches responses for the provided prompt IDs.
 *
 * Named Parameters:
 *    Table Name (String)
 *    Language Code (String)
 *    Prompt Ids (Array<Integer>)
 */


SELECT
    PRS.:tableNameId                                AS PromptId
,   PRS.:tableNameResponseId                        AS PromptResponseId
,   R.ResponseId                                    AS ResponseId
,   R.ResponseTypeId                                AS ResponseTypeId
,   R.ScriptLineToneId                              AS ResponseScriptLineToneId
,   R.ScriptLineTypeId                              AS ResponseScriptLineTypeId
,   COALESCE(TCT.Text, TC.BaseText)                 AS ResponseText
,   COALESCE(TCT.LanguageCode, TC.BaseLanguageCode) AS ResponseLanguageCode
FROM :tableNameResponses PRS
INNER JOIN :tableNameResponse PR
    ON PRS.:tableNameResponseId = PR.:tableNameResponseId
INNER JOIN Response R
    ON PR.ResponseId = R.ResponseId
INNER JOIN ScriptLine SL
    ON R.ScriptLineId = SL.ScriptLineId
INNER JOIN ScriptLineTone SLTO
    ON SL.ScriptLineToneId = SLTO.ScriptLineToneId
INNER JOIN ScriptLineType SLTY
    ON SL.ScriptLineTypeId = SLTY.ScriptLineTypeId
INNER JOIN TextContent TC
    ON SL.TextContentId = TC.TextContentId
LEFT JOIN TextContentTranslation TCT
    ON TC.TextContentId = TCT.TextContentId
    AND TCT.LanguageCode = :languageCode
WHERE PRS.:tableNameId IN (:promptIds);