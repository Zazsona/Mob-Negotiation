/*
 * This script fetches all MoneyNegotiationPrompts with responses.
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
    MN.MoneyNegotiationPromptId AS PromptId
FROM MoneyNegotiationPrompt MN
INNER JOIN MoneyNegotiationPromptEntities MNE
    ON MN.MoneyNegotiationPromptId = MNE.MoneyNegotiationPromptId
    AND MNE.EntityTypeId IN (:entityTypeIds)
WHERE MN.PersonalityId IN (:personalityTypeIds)
AND MN.CanBeInitialOffer = :canBeInitialOffer
AND MN.CanBeRevisedOffer = :canBeRevisedOffer
AND MN.CanBeRepeatOffer = :canBeRepeatOffer
AND MN.CanBeRejection = :canBeRejection
AND MN.CanBeAcceptance = :canBeAcceptance;