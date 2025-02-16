/*
 * This script fetches all ItemNegotiationPrompts with responses.
 *
 * Named Parameters:
 *    Entity Type Ids (Array<Integer>)
 *    Personality Ids (Array<Integer>)
 *    Can Be Initial Offer (Boolean)
 *    Can Be Revised Offer (Boolean)
 *    Can Be Repeat Offer (Boolean)
 *    Can Be Rejection (Boolean)
 *    Can Be Acceptance (Boolean)
 */
SELECT
    ITN.ItemNegotiationPromptId AS PromptId
FROM ItemNegotiationPrompt ITN
INNER JOIN ItemNegotiationPromptEntities ITNE
    ON ITN.ItemNegotiationPromptId = ITNE.ItemNegotiationPromptId
    AND ITNE.EntityTypeId IN (:entityTypeIds)
WHERE ITN.PersonalityId IN (:personalityTypeIds)
AND ITN.CanBeInitialOffer = :canBeInitialOffer
AND ITN.CanBeRevisedOffer = :canBeRevisedOffer
AND ITN.CanBeRepeatOffer = :canBeRepeatOffer
AND ITN.CanBeRejection = :canBeRejection
AND ITN.CanBeAcceptance = :canBeAcceptance;