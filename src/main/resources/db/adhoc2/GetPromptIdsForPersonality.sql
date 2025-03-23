/*
 * This script fetches Prompt Ids for Prompts associated with the provided personality types.
 *
 * Named Parameters:
 *    Table Name (String)
 *    Personality Ids (Array<Integer>)
 */
SELECT
    P.:tableNameId AS PromptId
FROM :tableName P
WHERE P.PersonalityId IN (:personalityTypeIds);