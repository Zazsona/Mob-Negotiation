CREATE TABLE ItemNegotiationPrompt
(
  ItemNegotiationPromptId    INT        NOT NULL     PRIMARY KEY
, ScriptLineId               INT        NOT NULL
, PersonalityId              INT        NOT NULL
, CanBeInitialOffer          BIT        NOT NULL
, CanBeRevisedOffer          BIT        NOT NULL
, CanBeRepeatOffer           BIT        NOT NULL
, CanBeRejection             BIT        NOT NULL
, CanBeAcceptance            BIT        NOT NULL
, FOREIGN KEY(ScriptLineId) REFERENCES ScriptLine(ScriptLineId)
, FOREIGN KEY(PersonalityId) REFERENCES Personality(PersonalityId)
);