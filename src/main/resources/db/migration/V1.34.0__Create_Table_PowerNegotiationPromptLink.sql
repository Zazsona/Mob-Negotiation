CREATE TABLE PowerNegotiationPromptLink
(
, PowerNegotiationPromptId        INT     NOT NULL
, PowerNegotiationResponseId      INT     NULL
, PersonalityId                   INT     NOT NULL
, NextPowerNegotiationPromptId    INT     NOT NULL
, FollowOnSuccess                 BIT     NOT NULL
, FollowOnFailure                 BIT     NOT NULL
, FOREIGN KEY(PowerNegotiationPromptId) REFERENCES PowerNegotiationPrompt(PowerNegotiationPromptId)
, FOREIGN KEY(PowerNegotiationResponseId) REFERENCES PowerNegotiationResponse(PowerNegotiationResponseId)
, FOREIGN KEY(PersonalityId) REFERENCES Personality(PersonalityId)
);