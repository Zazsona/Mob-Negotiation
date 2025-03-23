/*
 * This script fetches Power Negotiation Prompt IDs from a given table.
 *
 * Named Parameters:
 *    Table Name (String)
 *    Can Be Initial Prompt (Boolean)
 */

SELECT
    PN.:tableNameId AS PromptId
FROM :tableName PN
WHERE (:requireCanBeInitialPrompt = 0 OR (:requireCanBeInitialPrompt = 1 AND PN.CanBeInitialPrompt = 1))
;