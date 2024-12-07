CREATE TABLE ItemNegotiationPromptResponses
(
  ItemNegotiationPromptId      INT        NOT NULL
, ItemNegotiationResponseId    INT        NOT NULL
, FOREIGN KEY(ItemNegotiationPromptId) REFERENCES ItemNegotiationPrompt(ItemNegotiationPromptId)
, FOREIGN KEY(ItemNegotiationResponseId) REFERENCES ItemNegotiationResponse(ItemNegotiationResponseId)
);