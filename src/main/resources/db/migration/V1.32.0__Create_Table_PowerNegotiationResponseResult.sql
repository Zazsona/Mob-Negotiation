CREATE TABLE PowerNegotiationResponseResult
(
, PowerNegotiationResponseId         INT     NOT NULL    PRIMARY KEY
, PersonalityId                      INT     NOT NULL    PRIMARY KEY
, SuccessPowerNegotiationPromptId    INT     NOT NULL
, FailurePowerNegotiationPromptId    INT     NOT NULL
, SuccessRate                        REAL    NOT NULL
, FOREIGN KEY(PowerNegotiationResponseId) REFERENCES PowerNegotiationResponse(PowerNegotiationResponseId)
, FOREIGN KEY(PersonalityId) REFERENCES Personality(PersonalityId)
, FOREIGN KEY(SuccessReplyScriptLineId) REFERENCES ScriptLine(ScriptLineId)
, FOREIGN KEY(SuccessNextPowerNegotiationPromptId) REFERENCES PowerNegotiationPrompt(PowerNegotiationPromptId)
, FOREIGN KEY(FailureReplyScriptLineId) REFERENCES ScriptLine(ScriptLineId)
, FOREIGN KEY(FailureNextPowerNegotiationPromptId) REFERENCES PowerNegotiationPrompt(PowerNegotiationPromptId)
);