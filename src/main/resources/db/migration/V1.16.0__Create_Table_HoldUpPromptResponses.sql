CREATE TABLE HoldUpPromptResponses
(
  HoldUpPromptId      INT        NOT NULL
, HoldUpResponseId    INT        NOT NULL
, FOREIGN KEY(HoldUpPromptId) REFERENCES HoldUpPrompt(HoldUpPromptId)
, FOREIGN KEY(HoldUpResponseId) REFERENCES HoldUpResponse(HoldUpResponseId)
);