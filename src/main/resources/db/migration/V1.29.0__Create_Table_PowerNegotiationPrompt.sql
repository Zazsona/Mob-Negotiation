CREATE TABLE PowerNegotiationPrompt
(
  PowerNegotiationPromptId    INT        NOT NULL     PRIMARY KEY
, ScriptLineId                INT        NOT NULL
, CanBeInitialPrompt          BIT        NOT NULL
, FOREIGN KEY(ScriptLineId) REFERENCES ScriptLine(ScriptLineId)
);