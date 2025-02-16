/*
 * This script fetches Idle Timeout Prompts.
 *
 * Named Parameters:
 *    Prompt Ids (Array<Integer>)
 *    Language Code (String)
 */

SELECT
    IT.IdleTimeoutPromptId                          AS PromptId
,   ITE.EntityTypeId                                AS PromptEntityTypeId
,   IT.PersonalityId                                AS PromptPersonalityId
,   SLTO.ScriptLineToneId                           AS PromptScriptLineToneId
,   SLTY.ScriptLineTypeId                           AS PromptScriptLineTypeId
,   COALESCE(TCT.Text, TC.BaseText)                 AS PromptText
,   COALESCE(TCT.LanguageCode, TC.BaseLanguageCode) AS PromptLanguageCode
FROM IdleTimeoutPrompt IT
INNER JOIN ScriptLine SL
    ON IT.ScriptLineId = SL.ScriptLineId
INNER JOIN ScriptLineTone SLTO
    ON SL.ScriptLineToneId = SLTO.ScriptLineToneId
INNER JOIN ScriptLineType SLTY
    ON SL.ScriptLineTypeId = SLTY.ScriptLineTypeId
INNER JOIN TextContent TC
    ON SL.TextContentId = TC.TextContentId
LEFT JOIN TextContentTranslation TCT
    ON TC.TextContentId = TCT.TextContentId
    AND TCT.LanguageCode = :languageCode
WHERE IT.IdleTimeoutPromptId IN (:promptIds);