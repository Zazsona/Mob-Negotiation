/*
 * This script fetches all Prompt Links for the given entities.
 *
 * Named Parameters:
 *    Prompt Table Name (String)
 *    Response Table Name (String)
 *    Entity Type Ids (Array<Integer>)
 */

SELECT
    PNPL.:promptTableNameId AS PromptId
,   PNPL.:responseTableNameId AS PromptResponseId
,   PNPL.Next:promptTableNameId AS NextPromptId
,   PNPL.FollowOnSuccess
,   PNPL.FollowOnFailure
FROM :promptTableNameLink PNPL
INNER JOIN :promptTableNameEntities PNE
    ON PNPL.Next:promptTableNameId = PNE.:promptTableNameId
    AND PNE.EntityTypeId IN (:entityTypeIds)