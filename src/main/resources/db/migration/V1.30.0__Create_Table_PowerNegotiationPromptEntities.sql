CREATE TABLE PowerNegotiationPromptEntities
(
  RootPowerNegotiationPromptId    INT        NOT NULL
, EntityTypeId                    INT        NOT NULL
, FOREIGN KEY(RootPowerNegotiationPromptId) REFERENCES PowerNegotiationPrompt(PowerNegotiationPromptId)
, FOREIGN KEY(EntityTypeId) REFERENCES EntityType(EntityTypeId)
);