CREATE TABLE HoldUpPromptResponses
(
  HoldUpPromptId            INT        NOT NULL
, HoldUpPromptResponseId    INT        NOT NULL
, FOREIGN KEY(HoldUpPromptId) REFERENCES HoldUpPrompt(HoldUpPromptId)
, FOREIGN KEY(HoldUpPromptResponseId) REFERENCES HoldUpPromptResponse(HoldUpPromptResponseId)
);