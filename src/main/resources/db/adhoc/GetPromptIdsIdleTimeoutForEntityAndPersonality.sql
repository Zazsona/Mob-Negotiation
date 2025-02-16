/*
 * This script fetches Prompt Ids for Idle Timeout Prompts based on the criteria.
 *
 * Named Parameters:
 *    Entity Type Ids (Array<Integer>)
 *    Personality Ids (Array<Integer>)
 */

SELECT
    IT.IdleTimeoutPromptId AS PromptId
FROM IdleTimeoutPrompt IT
INNER JOIN IdleTimeoutPromptEntities ITE
    ON IT.IdleTimeoutPromptId = ITE.IdleTimeoutPromptId
    AND ITE.EntityTypeId IN (:entityTypeIds)
WHERE IT.PersonalityId IN (:personalityTypeIds);