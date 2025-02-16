/*
 * This script fetches Prompt Ids for Hold Up Prompts based on the criteria.
 *
 * Named Parameters:
 *    Entity Type Ids (Array<Integer>)
 *    Personality Ids (Array<Integer>)
 */
SELECT
    HUP.HoldUpPromptId AS PromptId
FROM HoldUpPrompt HUP
INNER JOIN HoldUpPromptEntities HUPE
    ON HUP.HoldUpPromptId = HUPE.HoldUpPromptId
    AND HUPE.EntityTypeId IN (:entityTypeIds)
WHERE HUP.PersonalityId IN (:personalityTypeIds);