/*
 * This script fetches Prompt Ids for Prompts
 *
 * Named Parameters:
 *    Table Name (String)
 */
SELECT
    P.:tableNameId AS PromptId
FROM :tableName P;