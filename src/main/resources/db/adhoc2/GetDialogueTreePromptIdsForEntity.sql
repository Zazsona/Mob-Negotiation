/*
 * This script fetches Power Negotiation Prompt IDs from a given table and for the provided entities.
 *
 * Named Parameters:
 *    Table Name (String)
 *    Entity Type Ids (Array<Integer>)
 *    Can Be Initial Prompt (Boolean)
 */

SELECT
    PN.:tableNameId AS PromptId
FROM :tableName PN
INNER JOIN :tableNameEntities PNE
    ON PN.:tableNameId = PNE.:tableNameId
    AND PNE.EntityTypeId IN (:entityTypeIds)
WHERE (:requireCanBeInitialPrompt = 0 OR (:requireCanBeInitialPrompt = 1 AND PN.CanBeInitialPrompt = 1))
;