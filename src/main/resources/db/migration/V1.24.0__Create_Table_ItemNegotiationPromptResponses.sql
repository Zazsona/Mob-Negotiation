CREATE TABLE ItemNegotiationPromptResponses
(
  ItemNegotiationPromptId            INT        NOT NULL
, ItemNegotiationPromptResponseId    INT        NOT NULL
, FOREIGN KEY(ItemNegotiationPromptId) REFERENCES ItemNegotiationPrompt(ItemNegotiationPromptId)
, FOREIGN KEY(ItemNegotiationPromptResponseId) REFERENCES ItemNegotiationPromptResponse(ItemNegotiationPromptResponseId)
);