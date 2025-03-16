CREATE TABLE MoneyNegotiationPromptResponses
(
  MoneyNegotiationPromptId            INT        NOT NULL
, MoneyNegotiationPromptResponseId    INT        NOT NULL
, FOREIGN KEY(MoneyNegotiationPromptId) REFERENCES MoneyNegotiationPrompt(MoneyNegotiationPromptId)
, FOREIGN KEY(MoneyNegotiationPromptResponseId) REFERENCES MoneyNegotiationPromptResponse(MoneyNegotiationPromptResponseId)
);