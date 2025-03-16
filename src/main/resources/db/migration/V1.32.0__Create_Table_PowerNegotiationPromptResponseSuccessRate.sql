CREATE TABLE PowerNegotiationPromptResponseSuccessRate
(
, PowerNegotiationPromptResponseId   INT     NOT NULL    PRIMARY KEY
, PersonalityId                      INT     NOT NULL    PRIMARY KEY
, SuccessRate                        REAL    NOT NULL
, FOREIGN KEY(PowerNegotiationPromptResponseId) REFERENCES PowerNegotiationPromptResponse(PowerNegotiationPromptResponseId)
, FOREIGN KEY(PersonalityId) REFERENCES Personality(PersonalityId)
);