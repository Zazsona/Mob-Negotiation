/*
 * This script fetches all PowerNegotiationPrompts with responses.
 *
 * Named Parameters:
 *    Entity Type Ids (Array<Integer>)
 *    Can Be Initial Prompt (Boolean)
 */

SELECT
    PN.PowerNegotiationPromptId AS PromptId
FROM PowerNegotiationPrompt PN
INNER JOIN PowerNegotiationPromptEntities PNE
    ON PN.PowerNegotiationPromptId = PNE.PowerNegotiationPromptId
    AND PNE.EntityTypeId IN (:entityTypeIds)
WHERE PN.CanBeInitialPrompt = :canBeInitialPrompt;