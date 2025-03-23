/*
 * This script fetches prompts for the given prompt IDs.
 *
 * Named Parameters:
 *    Table Name (String)
 *    Language Code (String)
 *    Prompt Ids (Array<Integer>)
 */


SELECT
    HUP.:tableNameId                                AS PromptId
,   HUP.PersonalityId                               AS PromptPersonalityId
,   SLTO.ScriptLineToneId                           AS PromptScriptLineToneId
,   SLTY.ScriptLineTypeId                           AS PromptScriptLineTypeId
,   COALESCE(TCT.Text, TC.BaseText)                 AS PromptText
,   COALESCE(TCT.LanguageCode, TC.BaseLanguageCode) AS PromptLanguageCode
FROM :tableName HUP
INNER JOIN ScriptLine SL
    ON HUP.ScriptLineId = SL.ScriptLineId
INNER JOIN ScriptLineTone SLTO
    ON SL.ScriptLineToneId = SLTO.ScriptLineToneId
INNER JOIN ScriptLineType SLTY
    ON SL.ScriptLineTypeId = SLTY.ScriptLineTypeId
INNER JOIN TextContent TC
    ON SL.TextContentId = TC.TextContentId
LEFT JOIN TextContentTranslation TCT
    ON TC.TextContentId = TCT.TextContentId
    AND TCT.LanguageCode = :languageCode
WHERE HUP.:tableNameId IN (:promptIds);