/*
 * This script fetches Prompt Ids for Idle Warning Prompts based on the criteria.
 *
 * Named Parameters:
 *    Entity Type Ids (Array<Integer>)
 *    Personality Ids (Array<Integer>)
 */

SELECT
    IW.IdleWarningPromptId AS PromptId
FROM IdleWarningPrompt IW
INNER JOIN IdleWarningPromptEntities IWE
    ON IW.IdleWarningPromptId = IWE.IdleWarningPromptId
    AND IWE.EntityTypeId IN (:entityTypeIds)
WHERE IW.PersonalityId IN (:personalityTypeIds);