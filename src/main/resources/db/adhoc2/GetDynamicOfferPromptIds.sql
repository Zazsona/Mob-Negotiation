/*
 * This script fetches all Dynamic Offer Prompt IDs from a given table.
 *
 * Named Parameters:
 *    Table Name (String)
 *    Require Can Be Initial Offer (Boolean)
 *    Require Can Be Revised Offer (Boolean)
 *    Require Can Be Repeat Offer (Boolean)
 *    Require Can Be Rejection (Boolean)
 *    Require Can Be Acceptance (Boolean)
 */
SELECT
    DOP.:tableNameId AS PromptId
FROM :tableName DOP
WHERE (:requireCanBeInitialOffer = 0 OR (:requireCanBeInitialOffer = 1 AND DOP.CanBeInitialOffer = 1))
AND   (:requireCanBeRevisedOffer = 0 OR (:requireCanBeRevisedOffer = 1 AND DOP.CanBeRevisedOffer = 1))
AND   (:requireCanBeRepeatOffer = 0 OR (:requireCanBeRepeatOffer = 1 AND DOP.CanBeRepeatOffer = 1))
AND   (:requireCanBeRejection = 0 OR (:requireCanBeRejection = 1 AND DOP.CanBeRejection = 1))
AND   (:requireCanBeAcceptance = 0 OR (:requireCanBeAcceptance = 1 AND DOP.CanBeAcceptance = 1))
;