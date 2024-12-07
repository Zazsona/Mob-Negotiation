CREATE TABLE MoneyNegotiationPromptResponses
(
  MoneyNegotiationPromptId      INT        NOT NULL
, MoneyNegotiationResponseId    INT        NOT NULL
, FOREIGN KEY(MoneyNegotiationPromptId) REFERENCES MoneyNegotiationPrompt(MoneyNegotiationPromptId)
, FOREIGN KEY(MoneyNegotiationResponseId) REFERENCES MoneyNegotiationResponse(MoneyNegotiationResponseId)
);