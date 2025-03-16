CREATE TABLE PowerNegotiationPromptResponses
(
  PowerNegotiationPromptId            INT        NOT NULL
, PowerNegotiationPromptResponseId    INT        NOT NULL
, FOREIGN KEY(PowerNegotiationPromptId) REFERENCES PowerNegotiationPrompt(PowerNegotiationPromptId)
, FOREIGN KEY(PowerNegotiationPromptResponseId) REFERENCES PowerNegotiationPromptResponse(PowerNegotiationPromptResponseId)
);