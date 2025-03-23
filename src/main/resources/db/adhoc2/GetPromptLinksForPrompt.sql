/*
 * This script fetches all Prompt Links for the given Power Negotiation Prompt Ids.
 *
 * Named Parameters:
 *    Prompt Table Name (String)
 *    Response Table Name (String)
 *    Prompt Ids (Array<Integer>)
 */

SELECT
    PNPL.:promptTableNameId AS PromptId
,   PNPL.:responseTableNameId AS PromptResponseId
,   PNPL.Next:promptTableNameId AS NextPromptId
,   PNPL.FollowOnSuccess
,   PNPL.FollowOnFailure
FROM :promptTableNameLink PNPL
WHERE PR.:promptTableNameId IN (:promptIds);