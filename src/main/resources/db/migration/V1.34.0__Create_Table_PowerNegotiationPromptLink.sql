CREATE TABLE PowerNegotiationPromptLink
(
, PowerNegotiationPromptId            INT     NOT NULL
, PowerNegotiationPromptResponseId    INT     NULL
, PersonalityId                       INT     NOT NULL
, NextPowerNegotiationPromptId        INT     NOT NULL
, FollowOnSuccess                     BIT     NOT NULL
, FollowOnFailure                     BIT     NOT NULL
, FOREIGN KEY(PowerNegotiationPromptId) REFERENCES PowerNegotiationPrompt(PowerNegotiationPromptId)
, FOREIGN KEY(PowerNegotiationPromptResponseId) REFERENCES PowerNegotiationPromptResponse(PowerNegotiationPromptResponseId)
, FOREIGN KEY(PersonalityId) REFERENCES Personality(PersonalityId)
);