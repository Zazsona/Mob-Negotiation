/*
 * This script fetches PowerNegotiationPrompts with responses.
 *
 * Named Parameters:
 *    Prompt Ids (Array<Integer>)
 *    Personality Id (Integer)
 *    Language Code (String)
 */

WITH PowerNegotiationResponseCTE AS (
    SELECT
        PNRS.PowerNegotiationPromptId
    ,   PNR.PowerNegotiationResponseId
    ,   PNRSR.SuccessRate
    ,   R.ResponseId
    ,   R.ResponseTypeId
    ,   SLTO.ScriptLineToneId
    ,   SLTY.ScriptLineTypeId
    ,   COALESCE(TCT.Text, TC.BaseText)                 AS Text
    ,   COALESCE(TCT.LanguageCode, TC.BaseLanguageCode) AS LanguageCode
    FROM PowerNegotiationResponse PNR
    INNER JOIN PowerNegotiationResponseSuccessRate PNRSR
        ON PNR.PowerNegotiationResponseId = PNRSR.PowerNegotiationResponseId
        AND PNRSR.PersonalityId = :personalityId
    INNER JOIN PowerNegotiationPromptResponses PNRS
        PNR.PowerNegotiationResponseId = PNRS.PowerNegotiationResponseId
    INNER JOIN Response R
        ON PNR.ResponseId = R.ResponseId
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
    PN.PowerNegotiationPromptId                     AS PromptId
,   PNE.EntityTypeId                                AS PromptEntityTypeId
,   SLTO.ScriptLineToneId                           AS PromptScriptLineToneId
,   SLTY.ScriptLineTypeId                           AS PromptScriptLineTypeId
,   COALESCE(TCT.Text, TC.BaseText)                 AS PromptText
,   COALESCE(TCT.LanguageCode, TC.BaseLanguageCode) AS PromptLanguageCode
,   CTE.PowerNegotiationResponseId                  AS PromptResponseId
,   CTE.ResponseId
,   CTE.ResponseTypeId
,   CTE.ScriptLineToneId                            AS ResponseScriptLineToneId
,   CTE.ScriptLineTypeId                            AS ResponseScriptLineTypeId
,   CTE.Text                                        AS ResponseText
,   CTE.LanguageCode                                AS ResponseLanguageCode
,   CTE.SuccessRate                                 AS ResponseSuccessRate
FROM PowerNegotiationPrompt PN
INNER JOIN ScriptLine SL
    ON PN.ScriptLineId = SL.ScriptLineId
INNER JOIN ScriptLineTone SLTO
    ON SL.ScriptLineToneId = SLTO.ScriptLineToneId
INNER JOIN ScriptLineType SLTY
    ON SL.ScriptLineTypeId = SLTY.ScriptLineTypeId
INNER JOIN TextContent TC
    ON SL.TextContentId = TC.TextContentId
LEFT JOIN TextContentTranslation TCT
    ON TC.TextContentId = TCT.TextContentId
    AND TCT.LanguageCode = :languageCode
LEFT JOIN PowerNegotiationResponseCTE CTE
    ON PN.PowerNegotiationPromptId = CTE.PowerNegotiationPromptId
WHERE PN.PowerNegotiationPromptId IN (:promptIds);