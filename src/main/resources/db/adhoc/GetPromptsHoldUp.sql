/*
 * This script fetches HoldUpPrompts with responses.
 *
 * Named Parameters:
 *    Filter Plugin Dependencies (Boolean)
 *    Plugin Keys (Array<String>)
 *    Filter Permission Dependencies (Boolean)
 *    Permission Keys (Array<String>)
 *    Language Code (String)
 *    Prompt Ids (Array<Integer>)
 */

WITH HoldUpResponseCTE AS (
    SELECT
        HUPRS.HoldUpPromptId
    ,   HUPR.HoldUpResponseId
    ,   R.ResponseId
    ,   R.ResponseTypeId
    ,   SLTO.ScriptLineToneId
    ,   SLTY.ScriptLineTypeId
    ,   COALESCE(TCT.Text, TC.BaseText)                 AS Text
    ,   COALESCE(TCT.LanguageCode, TC.BaseLanguageCode) AS LanguageCode
    FROM HoldUpResponse HUPR
    INNER JOIN HoldUpPromptResponses HUPRS
        HUPR.HoldUpResponseId = HUPRS.HoldUpResponseId
    INNER JOIN Response R
        ON HUPR.ResponseId = R.ResponseId
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
    WHERE (
        :filterPluginDependencies AND (
            NOT EXISTS (SELECT ResponseId FROM ResponsePluginDependency RPLD WHERE RPLD.ResponseId = R.ResponseId)
            OR EXISTS (SELECT ResponseId FROM ResponsePluginDependency RPLD WHERE RPLD.ResponseId = R.ResponseId AND RPLD.PluginKey IN (:pluginKeys))
        )
    )
    AND (
       :filterPermissionDependencies AND (
           NOT EXISTS (SELECT ResponseId FROM ResponsePermissionDependency RPED WHERE RPED.ResponseId = R.ResponseId)
           OR EXISTS (SELECT ResponseId FROM ResponsePermissionDependency RPED WHERE RPED.ResponseId = R.ResponseId AND RPED.PermissionKey IN (:permissionKeys))
       )
    )
)
SELECT
    HUP.HoldUpPromptId                              AS PromptId
,   HUPE.EntityTypeId                               AS PromptEntityTypeId
,   HUP.PersonalityId                               AS PromptPersonalityId
,   SLTO.ScriptLineToneId                           AS PromptScriptLineToneId
,   SLTY.ScriptLineTypeId                           AS PromptScriptLineTypeId
,   COALESCE(TCT.Text, TC.BaseText)                 AS PromptText
,   COALESCE(TCT.LanguageCode, TC.BaseLanguageCode) AS PromptLanguageCode
,   CTE.HoldUpResponseId                            AS PromptResponseId
,   CTE.ResponseId
,   CTE.ResponseTypeId
,   CTE.ScriptLineToneId                            AS ResponseScriptLineToneId
,   CTE.ScriptLineTypeId                            AS ResponseScriptLineTypeId
,   CTE.Text                                        AS ResponseText
,   CTE.LanguageCode                                AS ResponseLanguageCode
FROM HoldUpPrompt HUP
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
LEFT JOIN HoldUpResponseCTE CTE
    ON HUP.HoldUpPromptId = CTE.HoldUpPromptId
WHERE HUP.HoldUpPromptId IN (:promptIds);