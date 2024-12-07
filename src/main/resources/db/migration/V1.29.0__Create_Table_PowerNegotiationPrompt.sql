CREATE TABLE PowerNegotiationPrompt
(
  PowerNegotiationPromptId    INT        NOT NULL     PRIMARY KEY
, ScriptLineId                INT        NOT NULL
, FOREIGN KEY(ScriptLineId) REFERENCES ScriptLine(ScriptLineId)
);