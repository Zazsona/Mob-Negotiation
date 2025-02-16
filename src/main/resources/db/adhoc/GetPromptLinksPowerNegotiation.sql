/*
 * This script fetches all Prompt Links for the given Power Negotiation Prompt Id and criteria.
 *
 * Named Parameters:
 *    Entity Type Ids (Array<Integer>)
 *    Power Negotiation Prompt Id (Integer)
 */

SELECT
    PNPL.PowerNegotiationPromptId AS PromptId
,   PNPL.PowerNegotationResponseId AS PromptResponseId
,   PNPL.NextPowerNegotiationPromptId AS NextPromptId
,   PNPL.FollowOnSuccess
,   PNPL.FollowOnFailure
FROM PowerNegotiationPromptLink PNPL
INNER JOIN PowerNegotiationPromptEntities PNE
    ON PNPL.NextPowerNegotiationPromptId = PNE.PowerNegotiationPromptId
    AND PNE.EntityTypeId IN (:entityTypeIds)
WHERE PowerNegotiationPromptId = :powerNegotiationPromptId