/*
 * This script fetches Idle Warning Prompts.
 *
 * Named Parameters:
 *    Prompt Ids (Array<Integer>)
 *    Language Code (String)
 */

SELECT
    IW.IdleWarningPromptId                          AS PromptId
,   IWE.EntityTypeId                                AS PromptEntityTypeId
,   IW.PersonalityId                                AS PromptPersonalityId
,   SLTO.ScriptLineToneId                           AS PromptScriptLineToneId
,   SLTY.ScriptLineTypeId                           AS PromptScriptLineTypeId
,   COALESCE(TCT.Text, TC.BaseText)                 AS PromptText
,   COALESCE(TCT.LanguageCode, TC.BaseLanguageCode) AS PromptLanguageCode
FROM IdleWarningPrompt IW
INNER JOIN ScriptLine SL
    ON IW.ScriptLineId = SL.ScriptLineId
INNER JOIN ScriptLineTone SLTO
    ON SL.ScriptLineToneId = SLTO.ScriptLineToneId
INNER JOIN ScriptLineType SLTY
    ON SL.ScriptLineTypeId = SLTY.ScriptLineTypeId
INNER JOIN TextContent TC
    ON SL.TextContentId = TC.TextContentId
LEFT JOIN TextContentTranslation TCT
    ON TC.TextContentId = TCT.TextContentId
    AND TCT.LanguageCode = :languageCode
WHERE IW.IdleWarningPromptId IN (:promptIds);