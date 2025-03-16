CREATE TABLE HoldUpPromptResponse
(
  HoldUpPromptResponseId   INT        NOT NULL     PRIMARY KEY
, ResponseId               INT        NOT NULL
, FOREIGN KEY(ResponseId) REFERENCES Response(ResponseId)
);