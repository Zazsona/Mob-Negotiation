CREATE TABLE IdleTimeoutPrompt
(
  IdleTimeoutPromptId    INT        NOT NULL     PRIMARY KEY
, ScriptLineId           INT        NOT NULL
, PersonalityId          INT        NOT NULL
, FOREIGN KEY(ScriptLineId) REFERENCES ScriptLine(ScriptLineId)
, FOREIGN KEY(PersonalityId) REFERENCES Personality(PersonalityId)
);