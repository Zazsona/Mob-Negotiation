/*
 * This script fetches ItemNegotiationPrompts with responses.
 *
 * Named Parameters:
 *    Prompt Ids (Array<Integer>)
 *    Language Code (String)
 */

WITH ItemNegotiationResponseCTE AS (
    SELECT
        ITNRS.ItemNegotiationPromptId
    ,   ITNR.ItemNegotiationResponseId
    ,   R.ResponseId
    ,   R.ResponseTypeId
    ,   SLTO.ScriptLineToneId
    ,   SLTY.ScriptLineTypeId
    ,   COALESCE(TCT.Text, TC.BaseText)                 AS Text
    ,   COALESCE(TCT.LanguageCode, TC.BaseLanguageCode) AS LanguageCode
    FROM ItemNegotiationResponse ITNR
    INNER JOIN ItemNegotiationPromptResponses ITNRS
        ITNR.ItemNegotiationResponseId = ITNRS.ItemNegotiationResponseId
    INNER JOIN Response R
        ON ITNR.ResponseId = R.ResponseId
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
)
SELECT
    ITN.ItemNegotiationPromptId                     AS PromptId
,   ITNE.EntityTypeId                               AS PromptEntityTypeId
,   ITN.PersonalityId                               AS PromptPersonalityId
,   SLTO.ScriptLineToneId                           AS PromptScriptLineToneId
,   SLTY.ScriptLineTypeId                           AS PromptScriptLineTypeId
,   COALESCE(TCT.Text, TC.BaseText)                 AS PromptText
,   COALESCE(TCT.LanguageCode, TC.BaseLanguageCode) AS PromptLanguageCode
,   CTE.ItemNegotiationResponseId                   AS PromptResponseId
,   CTE.ResponseId
,   CTE.ResponseTypeId
,   CTE.ScriptLineToneId                            AS ResponseScriptLineToneId
,   CTE.ScriptLineTypeId                            AS ResponseScriptLineTypeId
,   CTE.Text                                        AS ResponseText
,   CTE.LanguageCode                                AS ResponseLanguageCode
FROM ItemNegotiationPrompt ITN
INNER JOIN ScriptLine SL
    ON ITN.ScriptLineId = SL.ScriptLineId
INNER JOIN ScriptLineTone SLTO
    ON SL.ScriptLineToneId = SLTO.ScriptLineToneId
INNER JOIN ScriptLineType SLTY
    ON SL.ScriptLineTypeId = SLTY.ScriptLineTypeId
INNER JOIN TextContent TC
    ON SL.TextContentId = TC.TextContentId
LEFT JOIN TextContentTranslation TCT
    ON TC.TextContentId = TCT.TextContentId
    AND TCT.LanguageCode = :languageCode
LEFT JOIN ItemNegotiationResponseCTE CTE
    ON ITN.ItemNegotiationPromptId = CTE.ItemNegotiationPromptId
WHERE ITN.ItemNegotiationPromptId IN (:promptIds);