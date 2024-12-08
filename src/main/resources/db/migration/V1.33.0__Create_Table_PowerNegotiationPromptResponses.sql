CREATE TABLE PowerNegotiationPromptResponses
(
  PowerNegotiationPromptId      INT        NOT NULL
, PowerNegotiationResponseId    INT        NOT NULL
, FOREIGN KEY(PowerNegotiationPromptId) REFERENCES PowerNegotiationPrompt(PowerNegotiationPromptId)
, FOREIGN KEY(PowerNegotiationResponseId) REFERENCES PowerNegotiationResponse(PowerNegotiationResponseId)
);