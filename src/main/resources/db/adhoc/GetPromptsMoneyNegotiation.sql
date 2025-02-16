/*
 * This script fetches MoneyNegotiationPrompts with responses.
 *
 * Named Parameters:
 *    Prompt Ids (Array<Integer>)
 *    Language Code (String)
 */

WITH MoneyNegotiationResponseCTE AS (
    SELECT
        MNRS.MoneyNegotiationPromptId
    ,   MNR.MoneyNegotiationResponseId
    ,   R.ResponseId
    ,   R.ResponseTypeId
    ,   SLTO.ScriptLineToneId
    ,   SLTY.ScriptLineTypeId
    ,   COALESCE(TCT.Text, TC.BaseText)                 AS Text
    ,   COALESCE(TCT.LanguageCode, TC.BaseLanguageCode) AS LanguageCode
    FROM MoneyNegotiationResponse MNR
    INNER JOIN MoneyNegotiationPromptResponses MNRS
        MNR.MoneyNegotiationResponseId = MNRS.MoneyNegotiationResponseId
    INNER JOIN Response R
        ON MNR.ResponseId = R.ResponseId
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
    MN.MoneyNegotiationPromptId                     AS PromptId
,   MNE.EntityTypeId                                AS PromptEntityTypeId
,   MN.PersonalityId                                AS PromptPersonalityId
,   SLTO.ScriptLineToneId                           AS PromptScriptLineToneId
,   SLTY.ScriptLineTypeId                           AS PromptScriptLineTypeId
,   COALESCE(TCT.Text, TC.BaseText)                 AS PromptText
,   COALESCE(TCT.LanguageCode, TC.BaseLanguageCode) AS PromptLanguageCode
,   CTE.MoneyNegotiationResponseId                  AS PromptResponseId
,   CTE.ResponseId
,   CTE.ResponseTypeId
,   CTE.ScriptLineToneId                            AS ResponseScriptLineToneId
,   CTE.ScriptLineTypeId                            AS ResponseScriptLineTypeId
,   CTE.Text                                        AS ResponseText
,   CTE.LanguageCode                                AS ResponseLanguageCode
FROM MoneyNegotiationPrompt MN
INNER JOIN ScriptLine SL
    ON MN.ScriptLineId = SL.ScriptLineId
INNER JOIN ScriptLineTone SLTO
    ON SL.ScriptLineToneId = SLTO.ScriptLineToneId
INNER JOIN ScriptLineType SLTY
    ON SL.ScriptLineTypeId = SLTY.ScriptLineTypeId
INNER JOIN TextContent TC
    ON SL.TextContentId = TC.TextContentId
LEFT JOIN TextContentTranslation TCT
    ON TC.TextContentId = TCT.TextContentId
    AND TCT.LanguageCode = :languageCode
LEFT JOIN MoneyNegotiationResponseCTE CTE
    ON MN.MoneyNegotiationPromptId = CTE.MoneyNegotiationPromptId
WHERE MN.MoneyNegotiationPromptId IN (:promptIds);