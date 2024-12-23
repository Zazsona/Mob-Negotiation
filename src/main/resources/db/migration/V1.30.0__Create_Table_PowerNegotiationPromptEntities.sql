CREATE TABLE PowerNegotiationPromptEntities
(
  PowerNegotiationPromptId    INT        NOT NULL
, EntityTypeId                    INT        NOT NULL
, FOREIGN KEY(PowerNegotiationPromptId) REFERENCES PowerNegotiationPrompt(PowerNegotiationPromptId)
, FOREIGN KEY(EntityTypeId) REFERENCES EntityType(EntityTypeId)
);