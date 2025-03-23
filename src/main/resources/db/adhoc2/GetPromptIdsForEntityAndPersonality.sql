/*
 * This script fetches Prompt Ids for Prompts associated with the provided entities & personality types.
 *
 * Named Parameters:
 *    Table Name (String)
 *    Entity Type Ids (Array<Integer>)
 *    Personality Ids (Array<Integer>)
 */
SELECT
    P.:tableNameId AS PromptId
FROM :tableName P
INNER JOIN :tableNameEntities PE
    ON P.:tableNameId = PE.:tableNameId
    AND PE.EntityTypeId IN (:entityTypeIds)
WHERE P.PersonalityId IN (:personalityTypeIds);